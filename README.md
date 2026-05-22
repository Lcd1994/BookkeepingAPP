# MoneyTracker - 记账应用

一个简单易用的Android记账应用，帮助你轻松记录和管理个人财务。

## 功能特性

- 📝 快速记录收入和支出
- 📊 分类统计和图表展示
- 💳 多账户管理
- 📱 简洁美观的界面
- 📅 按日期记录和查看

## 技术栈

- **语言**: Java
- **最低SDK**: 24 (Android 7.0)
- **目标SDK**: 34 (Android 14)
- **数据库**: SQLite
- **图表库**: MPAndroidChart

## 项目结构

```
app/
├── src/main/
│   ├── java/com/moneytracker/app/
│   │   ├── MainActivity.java          # 主页面
│   │   ├── AddTransactionActivity.java # 添加记录页面
│   │   ├── StatisticsActivity.java     # 统计页面
│   │   ├── AccountsActivity.java       # 账户页面
│   │   ├── Transaction.java           # 数据模型
│   │   ├── DatabaseHelper.java        # 数据库操作
│   │   ├── TransactionAdapter.java    # 记录列表适配器
│   │   ├── CategoryAdapter.java       # 分类列表适配器
│   │   └── CategoryStatAdapter.java   # 分类统计适配器
│   ├── res/
│   │   ├── layout/                    # 布局文件
│   │   ├── values/                    # 资源文件
│   │   └── drawable/                  # 图形资源
│   └── AndroidManifest.xml
└── build.gradle
```

## 构建和运行

### 环境要求

- JDK 8 或更高版本
- Android Studio (推荐)
- Android SDK

### 构建步骤

1. 克隆项目到本地
2. 使用 Android Studio 打开项目
3. 等待 Gradle 同步完成
4. 连接 Android 设备或启动模拟器
5. 点击运行按钮或使用命令:

```bash
./gradlew assembleDebug  # 构建 Debug 版本
./gradlew installDebug   # 安装到设备
```

## 使用说明

1. **添加记录**: 点击首页右下角的 "+" 按钮
2. **选择类型**: 选择收入或支出
3. **填写信息**: 输入金额，选择分类，添加备注，选择日期
4. **保存**: 点击保存按钮
5. **查看统计**: 点击底部导航栏的 "统计" 标签
6. **查看余额**: 点击底部导航栏的 "账户" 标签

## 开发说明

### 数据库结构

```
transactions 表
- id: 主键 (INTEGER)
- type: 类型 (0=支出, 1=收入)
- amount: 金额 (REAL)
- category: 分类 (TEXT)
- note: 备注 (TEXT)
- timestamp: 时间戳 (INTEGER)
```

### 分类设置

**支出分类**:
- 餐饮
- 交通
- 购物
- 娱乐
- 教育
- 医疗
- 住房
- 其他

**收入分类**:
- 工资
- 奖金
- 投资
- 其他

## 许可证

Apache License 2.0
