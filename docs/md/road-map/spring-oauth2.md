---
title: OAuth2
lock: need
---

# OAuth2 - 图解+案例，理解和实战OAuth2认证授权

作者：小傅哥
<br/>博客：[https://bugstack.cn](https://bugstack.cn)

> 沉淀、分享、成长，让自己和他人都能有所收获！😄

大家好，我是技术UP主小傅哥。

你知道互联网大厂最怕的是什么吗？但凡有点这样的风吹草动，我们就要花费大量的时间进行修复和上线。一点都不敢耽误，对于紧急类型的，基本当天发现，当天就要升级上线。那是什么问题呢？🤔

<div align="center">
    <img src="https://bugstack.cn/images/article/zsxq/student-learn-01.gif" width="150px">
</div>

**其实最怕的就是各类组件漏洞！**

有这么一个东西，[13scan - 安全漏洞扫描](https://bugstack.cn/md/road-map/13scan-jdumpspider.html) 它可以扫描出系统组件的各项存在的漏洞，给出整改建议。因为这些漏洞的存在，就可能让不法用户通过接口调用到系统数据。比如，随意输入个订单号，就知道是谁，什么时间、购买的什么、地址在哪。这是非常可怕的。

所以，在互联网大厂中，会有统一的安全授权认证服务 OAuth2。这样即使有外部对接的系统确实需要授权获得用户的数据，也可以在可靠的范围内进行授权和使用。

那么，OAuth2 是个啥呢？🤔 本节我们来分享下并做个代码案例运行验证。

## 一、OAuth2 是啥？

**OAuth 2.0 的标准 RFC 6749，解释了 OAuth 是什么。**

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-oauth2-01.png" width="650px">
</div>

**官网**：[https://datatracker.ietf.org/doc/html/rfc6749](https://datatracker.ietf.org/doc/html/rfc6749)

OAuth 2.0 本身是一种开放标准，不是一个具体的服务类组件，而是一种标准。旨在为用户提供授权，允许第三方应用程序访问用户在某个服务提供者（如社交网络或云服务）上的信息，而无需将用户的凭证（如用户名和密码）透露给这些应用程序。OAuth 2.0 主要用于授权，而不是身份验证。

而 Spring 中 OAuth2 就是对这套标准的具体实现，但这不是唯一实现，你甚至可以通过这套标准做一套自己的 OAuth2 授权框架。

## 二、举个例子

大家在日常的生活中使用互联网类的产品，包括；购物、视频、出行等，都可能收到活动类的短信，问你是否要参与一个这样的活动，如果参与则需要点击授权允许。那么这个过程就有 OAuth2 的授权使用。如图；

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-oauth2-02.png" width="750px">
</div>

- 这是一套用户参与小傅哥分享的星球用户活动页面。用户点击参与后，会引导进入授权验证。显示进入微信登录，之后跳转到用户数据授权使用页。
- 用户允许授权后，小傅哥的这套活动页就可以拿到用户个人的数据，通过个人的数据为唯一标识，允许用户参与本次活动。这些活动可以是一些抽奖、礼包领取、代码仓库授权等。这些场景的使用，就是 OAuth2 的授权框架作用。

## 三、授权方式

在看 OAuth2 之前，可以代入的思考下，如果是你做一个认证授权框架，你会怎么做。其实你在最开始学习编程使用账号密码在数据库里匹配验证，完成后生成一个 Token 让前端保存到 Cookie 里，之后每次请求后端都携带上这个 Cookie 进行校验。

其实这个模型就是认证授权框架。认证；使用账密证明你是你，授权，则通过账密分配一个Token，让使用放通过 Token 进行数据访问。

那么，OAuth2 作为认证授权框架，提供了四种授权访问，包括；
- 授权模型（authorization-code）
- 隐藏模式（简单授权）（implicit）
- 密码模式（password）
- 客户端凭证模式（client credentials）

这四种授权方式，逐渐减弱。不过，无论那种授权方式，在第三方应用申请可调用数据的令牌前，都需要先完成系统备案，验明自身身份。包括客户端 ID、客户端秘钥 Client Secret。

### 1. 授权模型

授权模式：指第三方应用先申请一个授权码，之后再使用该码获得令牌。授权码模式通常用于具有浏览器界面的应用程序，尤其是在需要用户交互的场景下，例如传统的Web应用。由于使用了重定向和授权码，维护了更高的安全性。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-oauth2-03.png" width="650px">
</div>

**工作流程：**

1. 用户在客户端（第三方应用）上点击登录。
2. 客户端将用户重定向到授权服务器，携带其注册的客户端ID、重定向URI和请求的权限范围。
3. 用户在授权服务器上验证身份，并同意授权后，授权服务器将用户重定向回客户端，附带一个授权码。
4. 客户端使用该授权码向授权服务器请求访问令牌，同时发送客户端ID、客户端密钥和重定向URI。
5. 授权服务器验证请求，并返回访问令牌（和可选的刷新令牌）。
6. 客户端使用访问令牌访问受保护的资源。

### 2. 隐藏模式

隐式模式主要适用于在Web浏览器中运行的单页应用（SPA）等不安全的客户端环境，因为不需要后台服务器交换授权码，简化了流程。然而，隐式模式由于直接暴露令牌，安全性较低，不建议用于敏感操作。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-oauth2-04.png" width="650px">
</div>

**工作流程：**

1. 用户在客户端上点击登录。
2. 客户端将用户重定向到授权服务器，携带客户端ID、重定向URI及请求的权限范围。
3. 用户在授权服务器进行身份验证，并同意授权后，授权服务器立即将访问令牌作为URI片段重定向回客户端。
4. 客户端在接收到重定向后，解析URI以获取访问令牌，随后可直接使用该令牌访问受保护的资源。

### 3. 密码模式

密码模式适用于用户信任客户端的情况，如用户通过原生应用（移动应用）访问服务。在此情况下，客户端直接处理用户的凭据，使用时要确保应用的安全性。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-oauth2-05.png" width="650px">
</div>

**工作流程：**

1. 用户在客户端直接输入其用户名和密码。
2. 客户端将用户的凭据（用户名和密码）发送到授权服务器，请求访问令牌。
3. 授权服务器验证凭据并返回访问令牌（和可选的刷新令牌）。
4. 客户端使用访问令牌访问受保护的资源。

### 4. 客户端凭证模式

客户端凭证模式主要用于服务器与服务器之间的通信，如后台服务相互访问API，或者服务自身需要访问其资源。适用于没有用户上下文的场景，更多用于机器对机器（M2M）通信。

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-oauth2-06.png" width="650px">
</div>

**工作流程**

1. 客户端向授权服务器发送包含其客户端ID和客户端密钥的请求，请求访问令牌。
2. 授权服务器验证客户端身份，并返回访问令牌。
3. 客户端使用访问令牌访问受保护的资源，通常是与服务器本身相关的资源。

## 四、授权代码

有了上面的概念，我们再来看个实际的案例工程，验证四种授权模式。环境信息如下；

- JDK 1.8
- Maven 3.8.*
- MySQL 5.x ~ 8.x，案例使用的是 8.x

### 1. 工程结构

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-oauth2-07.png" width="450px">
</div>

- 首先，案例工程提供了 OAuth2 的授权框架 + 数据库配置实现。
- docs 下提供了 docker compose 安装 MySQL 的脚本和导入库表的操作，这套库表就是授权框架的库表。
- 验证功能的时候需要使用到 ApiPost，你可以下载使用，并到 api-json 导入。

### 2. 核心实现

除了 OAuth2 关于 Spring Security 部分已经在前面的课程讲解过，可以补充学习。[https://bugstack.cn/md/road-map/spring-security.html](https://bugstack.cn/md/road-map/spring-security.html)

#### 2.1 账户认证

```java
@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String clientId;
    if (authentication != null) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof User) {
            User clientUser = (User) principal;
            clientId = clientUser.getUsername();
        } else if (principal instanceof OauthAccountUserDetails) {
            getClientIdByRequest();
            return (OauthAccountUserDetails) principal;
        } else {
            throw new UnsupportedOperationException();
        }
    } else {
        clientId = getClientIdByRequest();
    }
    // 校验用户 - 直接从数据库查询
    OauthAccount account = oauthAccountDao.loadUserByUsername(clientId, username);
    if (account == null || !account.getAccountNonDeleted()) {
        throw new UsernameNotFoundException("err user is not found!");
    }
    return new OauthAccountUserDetails(account, new ArrayList<>());
}
```

#### 2.2 刷新授权

```java
@Bean
public TokenEnhancer additionalInformationTokenEnhancer() {
    return (accessToken, authentication) -> {
        Map<String, Object> information = new HashMap<>(8);
        Authentication userAuthentication = authentication.getUserAuthentication();
        if (userAuthentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) userAuthentication;
            Object principal = token.getPrincipal();
            if (principal instanceof OauthAccountUserDetails) {
                OauthAccountUserDetails userDetails = (OauthAccountUserDetails) token.getPrincipal();
                OauthAccount oauthAccount = userDetails.getOauthAccount();
                information.put("account_info", UserAccountVO.builder()
                        .id(oauthAccount.getId())
                        .clientId(oauthAccount.getClientId())
                        .username(oauthAccount.getUsername())
                        .mobile(oauthAccount.getMobile())
                        .email(oauthAccount.getEmail())
                        .build());
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(information);
            }
        }
        return accessToken;
    };
}
```

#### 2.3 添加账户

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-oauth2-08.png" width="850px">
</div>

```java
@Resource
private PasswordEncoder passwordEncoder;
@Test
public void test_passwordEncoder() {
    log.info("测试结果:{}", passwordEncoder.encode("123456"));
}
```

- 这里测试可以生成一个需要的密码，账户填写到数据库中使用。

### 3. 测试验证

在测试之前，你要启动服务，确保运行没问题。启动前配置数据库连接。

```java
spring:
  datasource:
    username: root
    password: 123456
    url: jdbc:mysql://192.168.1.109:13306/xfg-dev-tech-oauth2?useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&serverTimezone=Asia/Shanghai&useSSL=true
    driver-class-name: com.mysql.cj.jdbc.Driver
```

- 关于 ApiPost 的测试，你可以直接从工程中的 json 导入的你的 ApiPost 就可以使用了。

#### 3.1 客户端凭证

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-oauth2-09.png" width="850px">
</div>

```java
{
	"access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYmlnLW1hcmtldC1hcHAiXSwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTczNjY3OTA4MCwiYXV0aG9yaXRpZXMiOlsidXNlciJdLCJqdGkiOiI4NWY0YjY2Ni1mNDliLTRiNGEtOTM1Ni0xYjRiMTVmZmI5MWEiLCJjbGllbnRfaWQiOiJ4Zmctc3R1ZGlvIn0.CqMOMbBkHMnQicpkBEeqMyJEp9HbSiGgXoYUke_PWtI",
	"token_type": "bearer",
	"expires_in": 7198,
	"scope": "read write",
	"jti": "85f4b666-f49b-4b4a-9356-1b4b15ffb91a"
}
```

- 请求：[http://127.0.0.1:8091/oauth/token?grant_type=client_credentials](http://127.0.0.1:8091/oauth/token?grant_type=client_credentials)
- 认证：`xfg-studio/123456`

#### 3.2 用户密码模式

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-oauth2-10.png" width="850px">
</div>

```java
{
	"access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYmlnLW1hcmtldC1hcHAiXSwiZXhwIjoxNzM2Njc5MTQxLCJ1c2VyX25hbWUiOiJ4aWFvZnVnZSIsImp0aSI6ImVhZWMzZmQ0LTViOTAtNGRhNy1hODQ1LTA2MDFmMjJiNDc2ZCIsImNsaWVudF9pZCI6InhmZy1zdHVkaW8iLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXX0.JgUxx6_aHqCBxuvYXvekw-ZW5pPnSw5LEKlfsd4qVyI",
	"token_type": "bearer",
	"refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYmlnLW1hcmtldC1hcHAiXSwidXNlcl9uYW1lIjoieGlhb2Z1Z2UiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiYXRpIjoiZWFlYzNmZDQtNWI5MC00ZGE3LWE4NDUtMDYwMWYyMmI0NzZkIiwiZXhwIjoxNzM5MjYzOTQyLCJqdGkiOiI5ZDc4ZjVjZS0xZTMwLTRiZTYtYWUyNi01NjY1NWQ4YjYzZjIiLCJjbGllbnRfaWQiOiJ4Zmctc3R1ZGlvIn0.8gMfqhBnc4wI9BsRENu_16RmZFqeCWVSyWcF4B9nA1I",
	"expires_in": 7198,
	"scope": "read write",
	"account_info": {
		"id": null,
		"clientId": "xfg-studio",
		"username": "xiaofuge",
		"mobile": "13500002222",
		"email": "523088136@qq.com"
	}
}
```

- 请求：[http://127.0.0.1:8091/oauth/token](http://127.0.0.1:8091/oauth/token)
- 认证：`xfg-studio/123456`
- 参数：`grant_type = password`、`username = xiaofuge`、`password = 123456`

#### 3.3 授权模式

##### 3.3.1 登录认证

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-oauth2-11.png" width="850px">
</div>

```java
{
	"status": 200,
	"message": "hi login success!"
}
```

- 请求：[http://127.0.0.1:8091/login](http://127.0.0.1:8091/login)
- 认证：`xfg-studio/123456`
- 参数：`username = xiaofuge`、`password = 123456`
- 说明：你会拿到一个 Cookie `JSESSIONID=9000E64733AA6E947054AC4326C91AF8` 这个 cookie 用于获取授权码

##### 3.3.2 获取授权码&跳转

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-oauth2-12.png" width="850px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-oauth2-13.png" width="850px">
</div>

<div align="center">
    <img src="https://bugstack.cn/images/roadmap/tutorial/road-map-oauth2-14.png" width="850px">
</div>

- 请求：[http://127.0.0.1:8091/oauth/authorize?client_id=xfg-studio&response_type=code&grant_type=authorization_code](http://127.0.0.1:8091/oauth/authorize?client_id=xfg-studio&response_type=code&grant_type=authorization_code)
- 认证：无
- 参数：`client_id = xfg-studio`、`response_type = code`、`grant_type=authorization_code`
- 注意：如果 oauth_client_details 表字段配置 autoapprove = false 则不会直接跳转页面，会进行让用户确认。

之后刷新令牌、检查令牌，就可以单独测试了。如果部署到云服务器，那么还可以走浏览器访问，单独有一个获取令牌的操作，之后再跳转地址。
