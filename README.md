# MoneyTracker - 记账应用

一个功能丰富的Android记账应用，帮助你轻松记录和管理个人财务。采用极简设计理念，让记账变得快速、简单、愉快。

## ✨ 核心功能

### 🚀 快速记账
- **快捷金额按钮**: 一键添加常用金额（10/50/100/500元）
- **分类可视化**: 大图标+emoji标签，快速识别分类
- **极简输入**: 优化操作流程，减少输入步骤

### 📊 智能统计
- **饼图分析**: 直观展示支出/收入结构
- **分类统计**: 按类别分析消费习惯
- **趋势追踪**: 了解收支变化趋势

### 💰 账户管理
- **多分类支持**: 
  - 支出分类: 餐饮、交通、购物、娱乐、教育、医疗、住房、通讯、日用品、美容、运动、零食、饮品、服装、数码、礼物、旅游、咖啡、电影、书籍、保险等21种
  - 收入分类: 工资、奖金、投资、理财、兼职、红包、退款等8种
- **账户概览**: 查看整体财务状况

### 🎨 现代化界面
- **清新配色**: 采用Material Design设计语言
- **流畅体验**: 优化的交互和动画
- **编辑功能**: 点击记录即可编辑，长按删除

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

- **JDK**: 8 或更高版本
- **Gradle**: 8.0 或更高版本
- **Android SDK**: API 34 (Android 14)
- **Android Studio**: 推荐最新版本

### 📦 构建 APK

#### 方式一: 使用构建脚本（推荐）

```bash
# 克隆项目
git clone https://github.com/Lcd1994/BookkeepingAPP.git
cd BookkeepingAPP

# 运行构建脚本
chmod +x build.sh
./build.sh
```

构建成功后，APK 文件将生成在:
- `app/build/outputs/apk/debug/app-debug.apk`
- `./MoneyTracker-v1.0-debug.apk` (根目录副本)

#### 方式二: 使用 Gradle 命令

```bash
# 同步依赖
gradle build

# 构建 Debug 版本
gradle assembleDebug

# 构建 Release 版本
gradle assembleRelease
```

#### 方式三: 使用 Android Studio

1. File → Open → 选择项目根目录
2. 等待 Gradle 同步完成
3. 点击 Run 按钮 (▶️) 或使用快捷键 Shift+F10

### 📱 安装和测试

#### 在模拟器上运行

1. 打开 Android Studio Device Manager
2. 创建新的虚拟设备（推荐 Pixel 4 或更高版本）
3. 启动模拟器
4. 运行应用

#### 在真机上运行

1. 启用开发者选项和 USB 调试
2. 使用 USB 连接手机到电脑
3. 运行应用或手动安装 APK

### 🔧 构建问题排查

**问题**: Gradle 同步失败
```bash
# 清除缓存并重新同步
gradle clean --refresh-dependencies
```

**问题**: 网络连接超时
```bash
# 使用国内镜像（在 build.gradle 中配置）
maven { url 'https://maven.aliyun.com/repository/google' }
maven { url 'https://maven.aliyun.com/repository/public' }
```

## 📖 使用说明

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

**支出分类** (21种):
餐饮、交通、购物、娱乐、教育、医疗、住房、通讯、日用品、美容、运动、零食、饮品、服装、数码、礼物、旅游、咖啡、电影、书籍、保险、其他

**收入分类** (8种):
工资、奖金、投资、理财、兼职、红包、退款、其他

## 🆕 最近更新

### v1.1 (最新版本)
**2024年最新更新**:

- ✨ **快捷金额按钮**: 添加了 ¥10/¥50/¥100/¥500 快捷按钮，大幅提升记账效率
- 📂 **扩展分类**: 支出分类从8种扩展到21种，收入分类从4种扩展到8种
- ✏️ **编辑功能**: 点击任意记录即可编辑，长按删除
- 🎨 **优化配色**: 升级为现代蓝色系，提升视觉体验
- 🖼️ **emoji图标**: 为每个分类添加了emoji图标，增强可识别性
- 📱 **交互优化**: 优化编辑流程，添加更新提示

### v1.0 (初始版本)
- 基础记账功能
- 收入/支出记录
- 分类统计
- SQLite数据库存储
- 饼图数据可视化

## 📄 许可证

Apache License 2.0
