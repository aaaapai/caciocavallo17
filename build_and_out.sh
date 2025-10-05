#!/bin/bash
set -e

# 首先正常打包
mvn -B package --file pom.xml -DskipTests

rm -rf out
mkdir -p out

# 复制所有 cacio jar 文件到 out 目录
cp cacio-*/target/cacio-*-1.19.1-SNAPSHOT.jar out

# 特别处理 cacio-agent
mv out/cacio-agent-1.19.1-SNAPSHOT.jar out/cacio-agent.jar

# 安装 ProGuard（如果尚未安装）
if ! command -v proguard &> /dev/null; then
    echo "ProGuard not found, downloading..."
    wget -q https://github.com/Guardsquare/proguard/releases/download/v7.3.2/proguard-7.3.2.tar.gz
    tar -xzf proguard-7.3.2.tar.gz
    export PATH=$PWD/proguard-7.3.2/bin:$PATH
fi

# 对需要降级的 jar 文件进行处理
for jarfile in out/cacio-*.jar; do
    if [[ "$jarfile" != *"cacio-agent.jar" ]]; then
        echo "Downgrading class version for: $(basename $jarfile)"
        java -jar proguard-7.3.2/lib/proguard.jar \
            -injars "$jarfile" \
            -outjars "$jarfile.tmp" \
            -target 8 \
            -keep "public class * { public *; }" \
            -keepattributes "Signature,RuntimeVisibleAnnotations,AnnotationDefault" \
            -dontnote \
            -dontwarn \
            -dontoptimize \
            -dontobfuscate
        
        # 替换原文件
        mv "$jarfile.tmp" "$jarfile"
    fi
done

echo "All jar files have been processed and downgraded to Java 8 compatibility"
