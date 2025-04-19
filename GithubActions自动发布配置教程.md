GitHub Actions 自动发布 Maven 项目到 GitHub Packages 步骤

这份文档指导你将一个 Maven 项目通过 GitHub Actions 自动打包并发布到 GitHub Packages 仓库，适用于 Spring Boot Starter 类型应用。

⸻

一、项目相关文件组织

├─ .github/
│   ├─ workflows/
│   │   └─ deploy.yml           → GitHub Actions 清单，触发打 tag 后自动发布
│   └─ settings.xml           → Maven 配置文件，包含验证 + 镜像配置
└─ pom.xml                  → 项目主要配置，需要包含 distributionManagement



⸻

二、pom.xml 配置

<distributionManagement>
    <repository>
        <id>github</id>
        <name>GitHub Packages</name>
        <url>https://maven.pkg.github.com/你的用户名/项目名</url>
    </repository>
</distributionManagement>

注意修改 你的用户名/项目名为自己的 GitHub 东西

⸻

三、.github/settings.xml 配置

<servers>
  <server>
    <id>github</id>
    <username>${env.GITHUB_ACTOR}</username>
    <password>${env.GITHUB_TOKEN}</password>
  </server>
</servers>

<profiles>
  <profile>
    <id>aliyun</id>
    <repositories>
      <repository>
        <id>aliyun</id>
        <url>https://maven.aliyun.com/repository/public</url>
        <releases><enabled>true</enabled></releases>
        <snapshots><enabled>false</enabled></snapshots>
      </repository>
      <repository>
        <id>central</id>
        <url>https://repo.maven.apache.org/maven2</url>
        <releases><enabled>true</enabled></releases>
        <snapshots><enabled>false</enabled></snapshots>
      </repository>
    </repositories>
    <pluginRepositories>
      <pluginRepository>
        <id>aliyun</id>
        <url>https://maven.aliyun.com/repository/public</url>
        <releases><enabled>true</enabled></releases>
        <snapshots><enabled>false</enabled></snapshots>
      </pluginRepository>
      <pluginRepository>
        <id>central</id>
        <url>https://repo.maven.apache.org/maven2</url>
        <releases><enabled>true</enabled></releases>
        <snapshots><enabled>false</enabled></snapshots>
      </pluginRepository>
    </pluginRepositories>
  </profile>
</profiles>

<activeProfiles>
  <activeProfile>aliyun</activeProfile>
</activeProfiles>



⸻

四、.github/workflows/deploy.yml 配置

name: Deploy to GitHub Packages

on:
push:
tags:
- 'v*'  # 只有打 tag 时才执行自动发布

jobs:
deploy:
runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Set up JDK 8
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '8'

      - name: Display Maven version
        run: mvn -v

      - name: Deploy to GitHub Packages
        run: mvn clean deploy -s .github/settings.xml -DskipTests --batch-mode
        env:
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}  # GitHub 自动添加，用于认证 push 包



⸻

五、发布流程演示

git tag v1.0.0
git push origin v1.0.0

当你 push 这个 tag 后，GitHub Actions 会自动触发 deploy 工作流程，将项目打包并发布到 GitHub Packages

⸻

如果需要我帮你打包为 zip 或自动生成模板给你，随时请我~✨