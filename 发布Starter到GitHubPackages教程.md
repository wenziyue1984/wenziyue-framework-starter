# 发布 wenziyue-framework-starter 到 GitHub Packages 教程

本教程将带你一步步将自己的 Spring Boot Starter 发布到 GitHub Packages，供其他项目通过 Maven 引用。

---

## ✅ 前置条件

1. 已有 GitHub 仓库，如：`https://github.com/wenziyue1984/wenziyue-framework-starter`
2. 本地项目已配置为 Git 并成功推送到远程仓库
3. 本地已安装 Maven，建议使用 Maven 3.6+

---

## 🔐 第一步：配置 GitHub Token（身份认证）

1. 登录 GitHub → 点击右上头像 → Settings → Developer Settings → Personal Access Tokens
2. 选择 `Fine-grained tokens` 或 `Classic token`，点击 **Generate new token**
3. 设置权限勾选：

- `write:packages`
- `read:packages`
- `repo`

4. 复制生成的 Token，**只显示一次，一定保存好！**

---

## ⚙️ 第二步：配置 Maven 的 `settings.xml`

打开你的 Maven 配置文件：

- Mac/Linux 路径：`~/.m2/settings.xml`
- Windows 路径：`C:\Users\你的用户名\.m2\settings.xml`

在 `<servers>` 中添加：

```xml
<servers>
    <server>
        <id>github</id>
        <username>你的GitHub用户名</username>
        <password>你的GitHub Token</password>
    </server>
</servers>
```

---

## 🛠 第三步：修改 Starter 项目的 `pom.xml`

在 `<project>` 中添加发布配置：

```xml
<distributionManagement>
    <repository>
        <id>github</id>
        <name>GitHub Packages</name>
        <url>https://maven.pkg.github.com/wenziyue1984/wenziyue-framework-starter</url>
    </repository>
</distributionManagement>
```

同时建议添加项目基本信息（可选）：

```xml
<scm>
    <url>https://github.com/wenziyue1984/wenziyue-framework-starter</url>
    <connection>scm:git:git://github.com/wenziyue1984/wenziyue-framework-starter.git</connection>
</scm>

<developers>
    <developer>
        <id>wenziyue1984</id>
        <name>wenziyue</name>
        <email>your@email.com</email>
    </developer>
</developers>
```

---

## 📦 第四步：执行部署命令发布到 GitHub Packages

```bash
mvn clean deploy
```

如果配置正确，你将看到上传成功，并能在 GitHub 仓库 → Packages 页面看到你的 Starter。

---

## ✅ 第五步：在其他项目中使用

在需要引入该 Starter 的项目中，配置：

```xml
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/wenziyue1984/wenziyue-framework-starter</url>
    </repository>
</repositories>
```

并添加依赖：

```xml
<dependency>
    <groupId>com.wenziyue</groupId>
    <artifactId>wenziyue-framework-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

确保使用者也配置了 GitHub Token 才能拉取成功。

---
