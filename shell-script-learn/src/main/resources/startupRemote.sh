#!/usr/bin/env bash
#Program:
#   服务器自动部署项目。
#   sudo su后，将这个shell脚本拷到服务器/data1/workspace_jiakang/offlinestorm-proc/下执行
#   docker-compose kill
#   rm -rf logs/*
#   rm -rf webapps/*
#   nc -4l 4444 > webapps/ROOT.war
#   docker-compose start
#   tailf logs/catalina*.log
#History
#   2017/05/23 Jackie First release

echo "docker-compose kill..."
docker-compose kill
echo "rm -rf logs/*..."
rm -rf logs/*
echo "rm -rf webapps/*..."
rm -rf webapps/*
echo "nc -4l 4444 > webapps/ROOT.war..."
nc -4l 4444 > webapps/ROOT.war
echo "docker-compose start..."
docker-compose start

today=$(date --date='0 days ago' +%Y-%m-%d) #今天的日期
catalinaLog="logs/catalina."${today}".log"

echo ${catalinaLog}" check for exists..."
while [ ! -f ${catalinaLog} ]
do
    echo ${catalinaLog}" does not exist yet, sleep 1s"
    sleep 1s
done
echo "tail -f "${catalinaLog}"..."
tail -f ${catalinaLog}
echo "done."