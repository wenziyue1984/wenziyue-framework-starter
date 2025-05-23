# wenziyue-framework-starter

✨ 一个简洁通用的 Spring Boot Starter，集成了统一响应封装、全局异常处理、自定义注解支持等基础功能，支持一键集成到你的 Spring Boot 项目中。

---

## 📦 功能简介

| 功能               | 描述                                               |
|--------------------|----------------------------------------------------|
| ✅ 全局异常处理     | 使用 `@RestControllerAdvice` 实现统一异常响应       |
| ✅ 统一响应格式     | 使用 `@ResponseResult` 注解配合 `ResponseBodyAdvice` 实现 |
| ✅ 通用响应结构类   | `ApiResult<T>` 返回结构，统一包含 code/msg/data   |
| ✅ 自定义注解支持   | 支持在 Controller 层标注 `@ResponseResult` 自动响应封装 |
| ✅ 自动装配         | 使用 `spring.factories` 实现 Spring Boot 自动生效 |
| ✅ TraceId 透传     | 提供 `MdcTaskDecorator`，支持日志 traceId 在异步/定时任务中自动透传 |
| ✅ UTF-8 字符编码统一 | 所有接口响应默认使用 `application/json;charset=UTF-8`，避免乱码 |
| ✅ MdcExecutors 工具类 | 提供线程池封装工具，便于在非 Spring Bean 场景下使用带 traceId 的异步任务 |

---

## 🚀 快速开始

### 1️⃣ 安装（通过 GitHub Packages 引入）

首先在settings.xml中添加以下认证信息
```xml
<server>
    <id>wenziyue-framework</id>
    <username>你的GitHub用户名</username>
    <password>你的GitHub Token（建议只赋予 read:packages 权限）</password>
</server>
```

再在 `pom.xml` 中添加 GitHub 仓库地址：

```xml
<!-- pom.xml 中添加仓库地址（id 要与上面保持一致） -->
<repositories>
    <repository>
        <id>wenziyue-framework</id>
        <url>https://maven.pkg.github.com/wenziyue1984/wenziyue-framework-starter</url>
    </repository>
</repositories>
```
然后引入依赖：

```xml
<dependency>
    <groupId>com.wenziyue</groupId>
    <artifactId>wenziyue-framework-starter</artifactId>
    <version>1.0.0（请使用最新版本）</version>
</dependency>
```

> 💡 注意：你需要在 Maven 的 `settings.xml` 中配置 GitHub Token 授权，才能访问私有或 GitHub Packages 的依赖。

---

### 2️⃣ 如何使用

#### ✅ 统一返回封装

只需要在 Controller 或方法上加上注解 `@ResponseResult` 即可：

```java
@ResponseResult
@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/error")
    public String error() {
        throw new RuntimeException("测试异常");
    }
}
```

返回结果会自动封装为统一结构：

```json
{
  "code": "200",
  "msg": "Success",
  "data": "Hello, World!"
}
```

---

#### ✅ 自定义业务异常

```java
throw new ApiException("400", "用户名或密码错误");
```

自动返回结构：

```json
{
  "code": "400",
  "msg": "用户名或密码错误",
  "data": null
}
```

---

#### **✅ 异步任务 TraceId 透传**

使用 starter 提供的 MdcTaskDecorator，可实现 TraceId 在异步任务、定时任务中的自动透传。

你只需在业务项目中注册线程池时注入它：

```java
@Bean("asyncExecutor")
public Executor asyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setTaskDecorator(new MdcTaskDecorator()); // 自动透传 traceId
    ...
    return executor;
}
```

定时任务示例：

```java
ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2, runnable -> {
    Runnable wrapped = mdcTaskDecorator.decorate(runnable);
    return new Thread(wrapped);
});
```

------

#### **✅ MdcExecutors：非 Spring 场景下的异步任务封装**

在工具类或非 Spring 管理的类中，也可通过 MdcExecutors 快速创建带 traceId 的线程池：

```java
ExecutorService executor = MdcExecutors.newFixedThreadPoolWithMdc(2, mdcTaskDecorator);

executor.submit(() -> {
    log.info("异步任务执行中...");
});
```



---

## 🔧 自动装配说明

已配置 `spring.factories`，引入依赖即可自动生效，无需额外配置：

```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
config.com.wenziyue.framework.WenziyueAutoConfiguration
```

---

## 📄 License

Apache License 2.0

---

## 🙋‍♀️ 作者

👤 [@wenziyue1984](https://github.com/wenziyue1984)

欢迎 Issue、Star 或提交 PR，一起成长 🌱