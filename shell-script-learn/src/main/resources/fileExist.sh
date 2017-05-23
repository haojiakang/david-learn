#!/usr/bin/env bash
#Program:
#   本地自动部署项目。
#   在本机的offlinestorm目录下执行
#   mvn clean package -Dmaven.test.skip=true -am -pl offlinestorm-processor
#   nc 10.77.6.161 4444 < offlinestorm-processor/target/offlinestorm-processor-1.1.0.32-SNAPSHOT.war
#History
#   2017/05/23 Jackie First release

today=$(date --date='0 days ago' +%Y-%m-%d) #今天的日期
catalinaLog="logs/catalina."${today}".log"

while [ ! -f ${catalinaLog} ]
do
    echo ${catalinaLog}" does not exist, sleep 1s"
    sleep 1s
done
tail -f ${catalinaLog}