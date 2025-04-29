# 覆盖 GitHub 远程标签 v1.0.0 的操作指南（中文版）

本指南详细说明了如何覆盖 GitHub 远程仓库中已存在的标签 `v1.0.0`，以便在 `wenziyue-framework-starter` 项目中重新发布版本并触发 GitHub Actions 自动部署流程。适用于需要修正或重新发布 `v1.0.0` 版本的场景。

## 前置条件
- 确保你有 `wenziyue-framework-starter` 仓库的推送权限。
- 确保已配置 Git 环境，并可以正常访问 GitHub（建议使用 SSH 协议，避免 HTTPS 的 HTTP/2 问题）。
- 确保 `deploy.yml` 已正确配置，触发条件为 `on: push: tags: - 'v*'`。
- 如果遇到 HTTP/2 问题，建议设置 Git 使用 HTTP/1.1：
  ```bash
  git config --global http.version HTTP/1.1
  ```

## 操作步骤
### 步骤 1：确认当前代码状态
1. **切换到 `main` 分支**：
   - 确保你当前在 `main` 分支：
     ```bash
     git checkout main
     ```
   - 如果有其他分支的工作，确保已合并到 `main` 分支：
     ```bash
     git merge <feature-branch>
     ```
2. **拉取最新代码**：
   - 确保本地代码与远程仓库同步：
     ```bash
     git pull origin main
     ```
3. **检查代码状态**：
   - 确认没有未提交的更改：
     ```bash
     git status
     ```
   - 如果有更改，先提交：
     ```bash
     git add .
     git commit -m "Prepare for re-releasing v1.0.0"
     ```

### 步骤 2：更新 `pom.xml` 版本号（可选）
- 如果你需要重新发布 `1.0.0` 版本，确保 `pom.xml` 中的版本号仍然是 `1.0.0`：
  ```xml
  <version>1.0.0</version>
  ```
- 如果版本号已更改（例如变为 `1.0.1`），需要改回 `1.0.0`：
  1. 打开 `pom.xml`，修改 `<version>` 为 `1.0.0`。
  2. 提交更改：
     ```bash
     git add pom.xml
     git commit -m "Revert version to 1.0.0 for re-release"
     git push origin main
     ```

### 步骤 3：删除本地和远程的 `v1.0.0` 标签
1. **删除本地标签**：
   - 删除本地的 `v1.0.0` 标签：
     ```bash
     git tag -d v1.0.0
     ```
   - 验证本地标签已删除：
     ```bash
     git tag
     ```
     - 确保 `v1.0.0` 不在列表中。
2. **删除远程标签**：
   - 删除 GitHub 远程仓库中的 `v1.0.0` 标签：
     ```bash
     git push origin :refs/tags/v1.0.0
     ```
   - 输出应类似：
     ```
     To https://github.com/wenziyue1984/wenziyue-framework-starter.git
      - [deleted]         v1.0.0
     ```
   - 如果你使用 SSH 协议，远程 URL 应为 `git@github.com:wenziyue1984/wenziyue-framework-starter.git`。

### 步骤 4：重新创建并推送 `v1.0.0` 标签
1. **创建新的 `v1.0.0` 标签**：
   - 在当前提交上创建附注标签：
     ```bash
     git tag -a v1.0.0 -m "Re-release version 1.0.0"
     ```
2. **推送标签到远程仓库**：
   - 推送新的 `v1.0.0` 标签：
     ```bash
     git push origin v1.0.0
     ```
   - 输出应类似：
     ```
     To https://github.com/wenziyue1984/wenziyue-framework-starter.git
     * [new tag]         v1.0.0 -> v1.0.0
     ```

### 步骤 5：验证 GitHub Actions 部署
1. **检查工作流状态**：
   - 打开 `https://github.com/wenziyue1984/wenziyue-framework-starter/actions`。
   - 找到 `Deploy to GitHub Packages` 工作流，确认已触发并运行。
   - 如果成功，日志会显示包已上传：
     ```
     [INFO] Uploaded to github: https://maven.pkg.github.com/wenziyue1984/wenziyue-framework-starter/com/wenziyue/wenziyue-framework-starter/1.0.0/wenziyue-framework-starter-1.0.0.jar
     ```
2. **验证 GitHub Packages**：
   - 访问 `https://github.com/wenziyue1984/wenziyue-framework-starter/packages`。
   - 确认 `com.wenziyue:wenziyue-framework-starter:1.0.0` 已更新。

### 步骤 6：在依赖项目中测试新版本
1. **更新依赖项目**：
   - 打开 `wenziyue-blog` 项目的 `pom.xml`。
   - 确保依赖版本为 `1.0.0`：
     ```xml
     <dependency>
         <groupId>com.wenziyue</groupId>
         <artifactId>wenziyue-framework-starter</artifactId>
         <version>1.0.0</version>
     </dependency>
     ```
2. **清理本地缓存**：
   - 删除本地 Maven 缓存，确保下载新版本：
     ```bash
     rm -rf ~/.m2/repository/com/wenziyue/wenziyue-framework-starter
     ```
3. **构建并测试**：
   - 构建 `wenziyue-blog`：
     ```bash
     cd wenziyue-blog
     mvn clean install -s ~/.m2/settings-aliyun.xml -U
     ```
   - 启动项目，测试功能（例如 `/articles` 接口），确保新版本正常工作。

## 注意事项
- **避免频繁覆盖标签**：
  - 覆盖标签（例如 `v1.0.0`）可能会导致版本管理混乱，建议优先递增版本号（例如 `1.0.1`），除非有特殊需求。
- **权限问题**：
  - 如果推送后 GitHub Actions 报 `403 Forbidden`，可能是 `GITHUB_TOKEN` 权限不足：
    1. 创建 Personal Access Token（PAT）：
       - 打开 `https://github.com/settings/tokens`，生成 token（勾选 `repo` 和 `write:packages` 权限）。
       - 添加到 GitHub Secrets（名称：`GITHUB_PAT`）。
    2. 更新 `deploy.yml`：
       ```yaml
       env:
         GITHUB_TOKEN: ${{ secrets.GITHUB_PAT }}
       ```
- **网络问题**：
  - 如果再次遇到 HTTP/2 问题，确保已设置：
    ```bash
    git config --global http.version HTTP/1.1
    ```
  - 或者使用 SSH 协议：
    ```bash
    git remote set-url origin git@github.com:wenziyue1984/wenziyue-framework-starter.git
    ```

## 故障排查
- **推送失败，报 `already exists`**：
  - 说明远程标签未正确删除，重新执行步骤 3，确保删除成功。
- **GitHub Actions 未触发**：
  - 确认 `deploy.yml` 的触发条件为 `on: push: tags: - 'v*'`。
  - 检查标签名称是否以 `v` 开头（例如 `v1.0.0`）。
- **部署失败，报 `409 Conflict`**：
  - GitHub Packages 不允许覆盖同版本包，需删除远程包（需要管理员权限）或递增版本号。

通过以上步骤，你可以顺利覆盖 `v1.0.0` 标签并重新发布版本。