#!/bin/bash
# 这个脚本主要是用来设置数据库的用户名和密码

# 判断是否已经设置过密码
if [ -f /.mongodb_password_set ]; then
    echo "MongoDB password already set!"
    exit 0
fi

/usr/bin/mongod --smallfiles --nojournal &

PASS=${MONGODB_PASS:-$(pwgen 0s 12 1)}
_word=$( [ ${MONGODB_PASS} ] && echo "preset" || echo "random" )

RET=1
while [[ RET -ne 0 ]]; do
    echo "=> Waiting for confirmation of MongoDB service startup"
    sleep 5
    mongo admin --eval "help" >/dev/null 2>&1
    RET=$?
done
