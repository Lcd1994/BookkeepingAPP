#!/bin/bash

# MoneyTracker Android App Build Script

echo "================================================"
echo "MoneyTracker Android App 构建脚本"
echo "================================================"
echo ""

# Check if gradle is installed
if ! command -v gradle &> /dev/null; then
    echo "错误: Gradle 未安装"
    echo "请先安装 Gradle: https://gradle.org/install/"
    exit 1
fi

echo "1. 检查 Gradle 版本..."
gradle --version
echo ""

echo "2. 清理构建..."
gradle clean
echo ""

echo "3. 构建 Debug APK..."
gradle assembleDebug
echo ""

# Check if build was successful
if [ -f "app/build/outputs/apk/debug/app-debug.apk" ]; then
    echo "================================================"
    echo "构建成功！"
    echo "APK 文件位置: app/build/outputs/apk/debug/app-debug.apk"
    echo "================================================"
    
    # Get file size
    SIZE=$(ls -lh app/build/outputs/apk/debug/app-debug.apk | awk '{print $5}')
    echo "文件大小: $SIZE"
    
    # Copy to root directory for easy access
    cp app/build/outputs/apk/debug/app-debug.apk ./MoneyTracker-v1.0-debug.apk
    echo "已复制到: ./MoneyTracker-v1.0-debug.apk"
else
    echo "================================================"
    echo "构建失败！"
    echo "请检查上方的错误信息"
    echo "================================================"
    exit 1
fi
