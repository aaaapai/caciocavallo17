#!/bin/bash
set -e

mvn -B package --file pom.xml -DskipTests

rm -rf out
mkdir -p out
cp cacio-*/target/cacio-*-1.19.2-SNAPSHOT.jar out
mv out/cacio-agent-1.19.2-SNAPSHOT.jar out/cacio-agent.jar
