---
title: 第1-7节：HTTP服务器与SSE流式
pay: https://t.zsxq.com/3Z7LR
---

# 《WaLiAPI - 本地 LLM API 网关》第1-7节：HTTP服务器与SSE流式

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

>沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

这是 Part 1 的最后一节，也是最关键的一节——我们要把前面所有模块串起来，启动一个真正的 HTTP 服务器，对外提供 OpenAI 兼容的 API 端点。下游的 ChatBox、NextChat、WaLiCode 就是通过这个服务器接入网关的。

## 一、本章诉求

1. 在 Tauri 应用中内嵌 Axum HTTP 服务器
2. 实现 OpenAI 兼容路由（/v1/chat/completions、/v1/models、/health）
3. 实现 API Key 鉴权与配额检查
4. 实现非流式请求处理
5. 实现 SSE 流式响应的透传与 Token 统计

## 二、服务器启动与 Tauri 集成

### 2.1 启动逻辑

**src-tauri/src/server/mod.rs**：

```rust
pub mod router;
pub mod handlers;

use crate::AppState;
use tauri::{AppHandle, Emitter};
use tauri_plugin_store::StoreExt;

pub async fn start_server(app: AppHandle, state: std::sync::Arc<AppState>) -> Result<(), anyhow::Error> {
    let host = get_server_host(&app);
    let port = get_server_port(&app);

    let addr = format!("{}:{}", host, port);
    let listener = tokio::net::TcpListener::bind(&addr).await?;
    let actual_port = listener.local_addr().port();

    // 更新共享状态（前端通过命令查询）
    *state.server_port.write().await = actual_port;
    state.server_running.store(true, std::sync::atomic::Ordering::SeqCst);

    let router = router::create_router(app.clone(), state.clone());

    // 通知前端服务器已启动（前端监听此事件更新 UI）
    app.emit("server-started", serde_json::json!({
        "port": actual_port,
        "url": format!("http://{}:{}", host, actual_port)
    })).ok();

    tracing::info!("WaLiAPI server listening on http://{}:{}", host, actual_port);

    // 启动 Axum 服务（阻塞直到服务器停止）
    axum::serve(listener, router).await?;

    state.server_running.store(false, std::sync::atomic::Ordering::SeqCst);
    Ok(())
}

fn get_server_host(app: &AppHandle) -> String {
    app.store("settings.json").ok()
        .and_then(|store| store.get("server.host"))
        .and_then(|v| v.as_str().map(|s| s.trim().to_string()))
        .filter(|s| !s.is_empty())
        .unwrap_or_else(|| "127.0.0.1".to_string())
}

fn get_server_port(app: &AppHandle) -> u16 {
    app.store("settings.json").ok()
        .and_then(|store| store.get("server.port"))
        .and_then(|v| v.as_u64())
        .map(|v| v as u16)
        .unwrap_or(8777)
}
```

### 2.2 在 Tauri 中异步启动

在 `lib.rs` 的 setup 中，数据库初始化完成后启动服务器：

```rust
tauri::async_runtime::block_on(async move {
    let db = db::Database::new(&app_handle).await;
    let state = Arc::new(AppState { /* ... */ });
    app_handle.manage(state.clone());

    let handle = app_handle.clone();
    // 在独立任务中运行服务器，不阻塞 Tauri 主流程
    tauri::async_runtime::spawn(async move {
        let _ = server::start_server(handle, state).await;
    });
});
```

**架构要点**：Tauri 和 Axum 共享同一个 Tokio 运行时（`tauri::async_runtime`）。服务器作为后台任务 spawn，与桌面窗口生命周期解耦——窗口关闭了（最小化到托盘），API 服务继续运行。

**默认绑定 127.0.0.1**：这是重要的安全决策。本地网关默认只监听回环地址，外部机器无法访问。用户如果确需局域网共享，可在设置中显式改为 `0.0.0.0`。

