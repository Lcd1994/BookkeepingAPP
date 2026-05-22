# GitHub Actions 自动打包指南

## 🚀 GitHub 可以自动打包！

是的！GitHub 有内置的 GitHub Actions 功能，可以**自动构建 APK**，并且提供下载链接！

---

## 📋 使用方法

### 步骤 1：推送代码到 GitHub

首先确保代码已推送到你的 GitHub 仓库：

```bash
git add -A
git commit -m "添加 GitHub Actions 自动构建配置"
git push
```

### 步骤 2：查看 Actions 工作流

1. 打开你的 GitHub 仓库页面
2. 点击顶部的 **"Actions"** 标签
3. 你会看到名为 **"Build Android APK"** 的工作流正在运行

### 步骤 3：等待构建完成

- 构建通常需要 3-10 分钟
- 当看到绿色的 ✓ 图标，表示构建成功！

### 步骤 4：下载 APK

1. 在 Actions 页面，点击成功的工作流
2. 滚动到页面底部的 **"Artifacts"** 部分
3. 你会看到两个可下载的文件：
   - **MoneyTracker-Debug-APK**（调试版本，直接安装）
   - **MoneyTracker-Release-APK**（发布版本）
4. 点击下载即可！

---

## 🔧 工作流配置说明

### 触发条件

我已经配置了以下三种触发方式：

1. **自动触发**（Push）：当你推送代码到 `main`、`master` 或 `feature/expense-tracker-enhancement` 分支时
2. **自动触发**（Pull Request）：当创建 PR 时
3. **手动触发**（Workflow Dispatch）：你可以在 Actions 页面手动点击 "Run workflow" 来构建

### 构建内容

- ✅ 自动设置 JDK 17
- ✅ 自动配置 Gradle 8.0
- ✅ 构建 Debug APK
- ✅ 构建 Release APK（可选）
- ✅ 自动上传 APK 为 Artifact

---

## 📱 手动触发构建

如果你想现在就测试，不需要等待推送：

1. 前往 GitHub 仓库 → Actions 标签
2. 选择 "Build Android APK" 工作流
3. 点击右侧的 **"Run workflow"** 按钮
4. 选择分支（建议选 `feature/expense-tracker-enhancement`）
5. 点击 **"Run workflow"** 绿色按钮

---

## 🎯 完整操作流程示例

```
1. 在 GitHub 上打开你的仓库
2. 点击 "Actions" 标签
3. 选择 "Build Android APK" 工作流
4. 点击 "Run workflow"
5. 选择 branch: feature/expense-tracker-enhancement
6. 点击绿色的 "Run workflow" 按钮
7. 等待 3-5 分钟
8. 刷新页面，查看构建状态
9. 构建成功后，下载底部的 APK 文件！
```

---

## 💡 优势

使用 GitHub Actions 打包的好处：

- ✅ **免费**：公开仓库免费使用
- ✅ **自动化**：每次推送自动构建
- ✅ **无需本地环境**：不需要安装 Android Studio、JDK、Gradle
- ✅ **云端构建**：GitHub 的服务器帮你完成
- ✅ **直接下载**：构建好的 APK 可以直接下载安装

---

## 🛠️ 如果遇到问题

### 构建失败怎么办？

1. 在 Actions 页面点击失败的工作流
2. 点击具体的 build 步骤
3. 查看详细的错误日志
4. 常见原因：
   - Gradle 依赖下载失败（网络问题）
   - 配置文件错误
   - 代码编译错误

### 想调整配置？

你可以修改 `.github/workflows/build-apk.yml` 文件来自定义构建过程。

---

现在你可以去 GitHub 上试试了！🎉
