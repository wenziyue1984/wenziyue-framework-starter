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

---

## 🚀 快速开始

### 1️⃣ 安装（通过 GitHub Packages 引入）

首先在你的项目的 `pom.xml` 中添加 GitHub 仓库地址：

```xml
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/wenziyue1984/wenziyue-framework-starter</url>
    </repository>
</repositories>
```

然后引入依赖：

```xml
<dependency>
    <groupId>com.wenziyue</groupId>
    <artifactId>wenziyue-framework-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
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

## 🔧 自动装配说明

已配置 `spring.factories`，引入依赖即可自动生效，无需额外配置：

```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.wenziyue.framework.starter.config.WenziyueAutoConfiguration
```

---

## 📄 License

Apache License 2.0

---

## 🙋‍♀️ 作者

👤 [@wenziyue1984](https://github.com/wenziyue1984)

欢迎 Issue、Star 或提交 PR，一起成长 🌱