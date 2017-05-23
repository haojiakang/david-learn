#!/usr/bin/env bash
#Program:
#   本地自动部署项目。
#   在本机的offlinestorm目录下执行
#   mvn clean package -Dmaven.test.skip=true -am -pl offlinestorm-processor
#   nc 10.77.6.161 4444 < offlinestorm-processor/target/offlinestorm-processor-1.1.0.32-SNAPSHOT.war
#History
#   2017/05/23 Jackie First release

echo "mvn clean package -Dmaven.test.skip=true -am -pl offlinestorm-processor..."
mvn clean package -Dmaven.test.skip=true -am -pl offlinestorm-processor
echo "nc 10.77.6.161 4444 < offlinestorm-processor/target/offlinestorm-processor-1.1.0.32-SNAPSHOT.war..."
nc 10.77.6.161 4444 < offlinestorm-processor/target/offlinestorm-processor-1.1.0.32-SNAPSHOT.war
echo "done."
