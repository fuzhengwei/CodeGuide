---
title: dump VisualVM
lock: need
---

# VisualVM 分析 Java heap space dump 日志

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

没毕业前以为学编程，以为工作后就只是写代码。工作后才发现，写代码只是一少部分工作。

`JMeter 压测`、`Remote JVM Debug - 远程调试`、`AREX - 流量录制&回放`、`ELK - 分布式日志`、`普罗米修斯监控`、`Arthas`、`Dump日志分析`等，但凡一样不会，基本就会在某一个场景踩坑。**小则是报警异常，大则是线上事故！**

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-linux-01.gif" width="150px">
</div>

**👨🏻‍💻程序员，也是高危行业呀！**

从互联网草蜢时代，到现在工作了这么多年，也是见证了很多程序员因为写Bug毕业啦🎓，即使不是被开除，往往重大的事故也会影响未来的绩效、加薪和晋升。这些事故按；照影响时长、影响用户量、造成的资损、解决的时长等，会被定级为 P0、P1、P2、P3 不同级别的事故。

所以，到目前有越来越多的辅助工具，来帮助研发提高代码交付质量，以及各类系统异常分析工具，提高问题排查效率。类似这样的系统、服务、组件，小傅哥已经在 [bugstack.cn 编程路书](https://bugstack.cn/md/road-map/road-map.html) 做了大量的案例讲解。今天小傅哥在给大家分享一个关于 VisualVM 的使用。

## 一、关于 VisualVM

VisualVM 是一款可视化 Java 故障排除工具，集成了 JDK 命令行工具和轻量级性能分析功能。专为开发和生产环境设计。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-dev-tech-visualvm-01.png" width="750px">
</div>

下载：[https://visualvm.github.io/download.html](https://visualvm.github.io/download.html)

> 接下来，小傅哥会结合 VisualVM 做一些常用的案例，方便伙伴学习。

## 二、案例 - 分析大对象

### 1. 测试工程

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-dev-tech-visualvm-02.png" width="750px">
</div>

- 地址：[https://github.com/fuzhengwei/xfg-dev-tech-visualvm](https://github.com/fuzhengwei/xfg-dev-tech-visualvm)
- 说明：这是一个简单的测试工程，通过访问接口产生大对象。之后在通过 JmapDumpController 接口，执行命令，产生 Dump 文件。之后在使用 VisualVM 分析产生的 Dump 日志，定位是哪个对象导致的问题。

### 2. 执行程序

首先，启动 xfg-dev-tech-visualvm 应用程序。之后执行 visualvm-test.sh 脚本，Windows 用户需要在 powershell 里执行，Mac 电脑可以直接在 IntelliJ IDEA 点击绿色箭头执行。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-dev-tech-visualvm-03.png" width="750px">
</div>

- 首先，点击启动程序，本地运行即可。一般公司里线上的应用，也有专门下载 dump 日志的地方。
- 之后，执行 `./visualvm-test.sh` 这部分写了测试程序的脚本和获取 dump 日志的操作。

#### 2.1 接口；创建对象

```java
@RestController
@RequestMapping("/api/memory")
public class MemoryTestController {

    // 用于存储大对象的静态变量，模拟内存泄漏
    private static final Map<String, Object> MEMORY_CACHE = new ConcurrentHashMap<>();
    private static final List<byte[]> BIG_OBJECTS = new ArrayList<>();

    /**
     * 大对象接口 - 创建大量对象占用内存
     */
    @GetMapping("/big-object")
    public Map<String, Object> bigObjectApi() {
        // 创建大对象（10MB的字节数组）
        byte[] bigData = new byte[10 * 1024 * 1024]; // 10MB
        for (int i = 0; i < bigData.length; i++) {
            bigData[i] = (byte) (i % 256);
        }
        
        // 将大对象存储到静态集合中，模拟内存泄漏
        BIG_OBJECTS.add(bigData);
        
        Map<String, Object> result = new HashMap<>();
        result.put("status", "success");
        result.put("message", "创建了一个大对象（10MB）");
        result.put("timestamp", System.currentTimeMillis());
        result.put("bigObjectsCount", BIG_OBJECTS.size());
        result.put("totalMemoryUsed", BIG_OBJECTS.size() * 10 + "MB");
        
        return result;
    }

    /**
     * 内存泄漏接口 - 持续创建对象并缓存
     */
    @GetMapping("/memory-leak")
    public Map<String, Object> memoryLeakApi() {
        String key = "data_" + System.currentTimeMillis();
        
        // 创建大量小对象并缓存
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            dataList.add("这是第" + i + "个数据对象，包含一些文本内容用于占用内存空间");
        }
        
        MEMORY_CACHE.put(key, dataList);
        
        Map<String, Object> result = new HashMap<>();
        result.put("status", "success");
        result.put("message", "创建了10000个小对象并缓存");
        result.put("timestamp", System.currentTimeMillis());
        result.put("cacheSize", MEMORY_CACHE.size());
        result.put("cacheKey", key);
        
        return result;
    }

    /**
     * 超大对象接口 - 创建超大对象
     */
    @GetMapping("/huge-object")
    public Map<String, Object> hugeObjectApi() {
        // 创建超大对象（100MB的字节数组）
        byte[] hugeData = new byte[100 * 1024 * 1024]; // 100MB
        
        // 填充数据
        for (int i = 0; i < hugeData.length; i++) {
            hugeData[i] = (byte) (Math.random() * 256);
        }
        
        BIG_OBJECTS.add(hugeData);
        
        Map<String, Object> result = new HashMap<>();
        result.put("status", "success");
        result.put("message", "创建了一个超大对象（100MB）");
        result.put("timestamp", System.currentTimeMillis());
        result.put("bigObjectsCount", BIG_OBJECTS.size());
        
        return result;
    }

}
```

#### 2.2 接口；获取日志（dump）

```java
@RestController
@RequestMapping("/api/jmap")
public class JmapDumpController {

    // 使用相对路径，基于项目根目录
    private static final String DUMP_DIR = "docs/dump";

    /**
     * 获取绝对路径的dump目录
     */
    private String getDumpDirectory() {
        // 获取项目根目录
        String userDir = System.getProperty("user.dir");
        // 如果当前目录是xfg-dev-tech-app，则需要回到上级目录
        if (userDir.endsWith("xfg-dev-tech-app")) {
            userDir = new File(userDir).getParent();
        }
        return userDir + File.separator + DUMP_DIR;
    }

    /**
     * 生成堆转储文件
     */
    @GetMapping("/dump")
    public Map<String, Object> generateHeapDump() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 获取dump目录的绝对路径
            String dumpDir = getDumpDirectory();
            
            // 确保目录存在
            File dir = new File(dumpDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            // 获取当前进程的PID
            String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
            
            // 生成文件名（包含时间戳）
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = sdf.format(new Date());
            String fileName = "heap_dump_" + timestamp + ".hprof";
            String filePath = dumpDir + File.separator + fileName;
            
            // 执行jmap命令生成堆转储
            String command = "jmap -dump:format=b,file=" + filePath + " " + pid;
            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();
            
            if (exitCode == 0) {
                result.put("status", "success");
                result.put("message", "堆转储文件生成成功");
                result.put("filePath", filePath);
                result.put("fileName", fileName);
            } else {
                result.put("status", "error");
                result.put("message", "堆转储文件生成失败");
                result.put("exitCode", exitCode);
            }
            
        } catch (IOException | InterruptedException e) {
            result.put("status", "error");
            result.put("message", "生成堆转储文件时发生异常: " + e.getMessage());
        }
        
        result.put("timestamp", System.currentTimeMillis());
        return result;
    }

}
```

#### 2.3 脚本；统一执行

```java
#!/bin/bash

# VisualVM 内存测试自动化脚本
# 作者: xiaofuge
# 用途: 自动化测试内存接口并生成dump文件

# 配置参数
BASE_URL="http://localhost:8091"
DUMP_DIR="../dump"
LOG_FILE="$DUMP_DIR/test_log_$(date +%Y%m%d_%H%M%S).txt"

# 颜色输出
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 日志函数
log() {
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] $1" | tee -a "$LOG_FILE"
}

log_info() {
    echo -e "${BLUE}[INFO]${NC} $1" | tee -a "$LOG_FILE"
}

log_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1" | tee -a "$LOG_FILE"
}

log_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1" | tee -a "$LOG_FILE"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1" | tee -a "$LOG_FILE"
}

# 检查应用是否启动
check_app_status() {
    log_info "检查应用状态..."
    response=$(curl -s -o /dev/null -w "%{http_code}" "$BASE_URL/api/memory/status")
    if [ "$response" = "200" ]; then
        log_success "应用已启动，状态正常"
        return 0
    else
        log_error "应用未启动或状态异常 (HTTP: $response)"
        return 1
    fi
}

# 等待应用启动
wait_for_app() {
    log_info "等待应用启动..."
    for i in {1..30}; do
        if check_app_status > /dev/null 2>&1; then
            log_success "应用启动成功"
            return 0
        fi
        log_info "等待中... ($i/30)"
        sleep 2
    done
    log_error "应用启动超时"
    return 1
}

# 调用API接口
call_api() {
    local endpoint=$1
    local description=$2
    local count=${3:-1}
    
    log_info "调用接口: $description"
    for ((i=1; i<=count; i++)); do
        response=$(curl -s "$BASE_URL$endpoint")
        status=$(echo "$response" | grep -o '"status":"[^"]*"' | cut -d'"' -f4)
        if [ "$status" = "success" ]; then
            log_success "[$i/$count] $description - 成功"
        else
            log_error "[$i/$count] $description - 失败: $response"
        fi
        sleep 1
    done
}

# 显示内存状态
show_memory_status() {
    log_info "获取内存状态..."
    response=$(curl -s "$BASE_URL/api/memory/status")
    echo "$response" | python3 -m json.tool 2>/dev/null || echo "$response"
    echo ""
}

# 生成dump文件
generate_dump() {
    log_info "生成堆转储文件..."
    response=$(curl -s "$BASE_URL/api/jmap/dump")
    status=$(echo "$response" | grep -o '"status":"[^"]*"' | cut -d'"' -f4)
    if [ "$status" = "success" ]; then
        filename=$(echo "$response" | grep -o '"fileName":"[^"]*"' | cut -d'"' -f4)
        log_success "堆转储文件生成成功: $filename"
    else
        log_error "堆转储文件生成失败: $response"
    fi
}

# 生成内存信息文件
generate_memory_info() {
    log_info "生成内存信息文件..."
    response=$(curl -s "$BASE_URL/api/jmap/memory-info")
    status=$(echo "$response" | grep -o '"status":"[^"]*"' | cut -d'"' -f4)
    if [ "$status" = "success" ]; then
        filename=$(echo "$response" | grep -o '"fileName":"[^"]*"' | cut -d'"' -f4)
        log_success "内存信息文件生成成功: $filename"
    else
        log_error "内存信息文件生成失败: $response"
    fi
}

# 清理缓存
clear_cache() {
    log_info "清理缓存..."
    response=$(curl -s "$BASE_URL/api/memory/clear-cache")
    status=$(echo "$response" | grep -o '"status":"[^"]*"' | cut -d'"' -f4)
    if [ "$status" = "success" ]; then
        log_success "缓存清理成功"
    else
        log_error "缓存清理失败: $response"
    fi
}

# 主测试流程
run_test() {
    log_info "开始VisualVM内存测试"
    
    # 检查dump目录
    if [ ! -d "$DUMP_DIR" ]; then
        log_info "创建dump目录: $DUMP_DIR"
        mkdir -p "$DUMP_DIR"
    fi
    
    # 等待应用启动
    if ! wait_for_app; then
        log_error "应用启动失败，退出测试"
        exit 1
    fi
    
    # 显示初始内存状态
    log_info "=== 初始内存状态 ==="
    show_memory_status
    
    # 测试普通接口
    call_api "/api/memory/normal" "普通接口测试" 5
    
    # 显示内存状态
    log_info "=== 普通接口调用后内存状态 ==="
    show_memory_status
    
    # 测试大对象接口
    call_api "/api/memory/big-object" "大对象接口测试" 10
    
    # 显示内存状态
    log_info "=== 大对象创建后内存状态 ==="
    show_memory_status
    
    # 生成第一次dump
    generate_dump
    generate_memory_info
    
    # 测试内存泄漏接口
    call_api "/api/memory/memory-leak" "内存泄漏接口测试" 20
    
    # 显示内存状态
    log_info "=== 内存泄漏测试后内存状态 ==="
    show_memory_status
    
    # 测试超大对象接口
    call_api "/api/memory/huge-object" "超大对象接口测试" 5
    
    # 显示内存状态
    log_info "=== 超大对象创建后内存状态 ==="
    show_memory_status
    
    # 生成第二次dump
    generate_dump
    generate_memory_info
    
    # 清理缓存
    clear_cache
    
    # 显示清理后内存状态
    log_info "=== 缓存清理后内存状态 ==="
    show_memory_status
    
    # 生成第三次dump
    generate_dump
    generate_memory_info
    
    log_success "VisualVM内存测试完成"
    log_info "日志文件: $LOG_FILE"
    log_info "dump文件目录: $DUMP_DIR"
}

# 显示帮助信息
show_help() {
    echo "VisualVM 内存测试脚本"
    echo ""
    echo "用法: $0 [选项]"
    echo ""
    echo "选项:"
    echo "  test          运行完整测试流程"
    echo "  check         检查应用状态"
    echo "  status        显示内存状态"
    echo "  dump          生成堆转储文件"
    echo "  memory-info   生成内存信息文件"
    echo "  clear         清理缓存"
    echo "  help          显示帮助信息"
    echo ""
    echo "示例:"
    echo "  $0 test       # 运行完整测试"
    echo "  $0 check      # 检查应用状态"
    echo "  $0 dump       # 生成dump文件"
}

# 主程序
case "${1:-test}" in
    "test")
        run_test
        ;;
    "check")
        check_app_status
        ;;
    "status")
        show_memory_status
        ;;
    "dump")
        generate_dump
        ;;
    "memory-info")
        generate_memory_info
        ;;
    "clear")
        clear_cache
        ;;
    "help")
        show_help
        ;;
    *)
        log_error "未知选项: $1"
        show_help
        exit 1
        ;;
esac
```

- 整个脚本，会帮助我们执行接口请求以及获取 dump 日志。

### 3. Dump 分析

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-dev-tech-visualvm-05.png" width="750px">
</div>

- 首先，先通过 VisualVM load dump 日志文件，之后点击 Instances By Size 大的文件。
- 之后，对大的文件对象，点击 references 这样就可以看到是哪个对象影响的问题了。很快的就能帮你分析出程序内产生大的对象的问题原因。

## 三、案例；GC 插件

VisualVM 还有类似于[普罗米修斯](https://bugstack.cn/md/road-map/grafana.html)一样的监控，可以查看到 JVM 运行情况。也可以帮助我们分析程序运行情况。

### 1. 安装插件 - VisualVM

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-dev-tech-visualvm-06.png" width="750px">
</div>

- 在 VisualVM 安装 Visual GC 插件。

### 2. 安装插件 - IntelliJ IDEA

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-dev-tech-visualvm-07.png" width="750px">
</div>

- 也可以给 Intellij IDEA 安装一个 VisualVM Launcher 插件，启动程序可以直接使用。

### 3. 进入监控

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-dev-tech-visualvm-09.png" width="750px">
</div>

- 打开 VisualVM 看到本地启动的程序，之后打开 Visual GC
- 这里还可以看见 Monitor、Threads、Profiler，方便我们分析程序

### 4. GC 说明

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/xfg-dev-tech-visualvm-08.png" width="750px">
</div>

- 如图，各个模块展示了 JVM 运行状况，从这里可以看到程序占用内存的情况。如果是压测验证，可以打开辅助分析。

