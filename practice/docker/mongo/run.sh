#!/bin/bash
if [ ! -f /.mongodb_password_set ]; then
    /set_mongodb_password.sh
fi

if [ "$AUTH" == "yes" ]; then
    # 这里读者可以自己设定MongoDB的启动参数
    export mongodb='/usr/bin/mongod --nojournal --auth --httpinterface --reset'
else
    export mongodb='/usr/bin/mongod --nojournal --httpinterface --reset'
fi

if [ ! -f /data/db/mongod.lock ]; then
    eval $mongodb
else
    export mongodb=$mongodb` --dbpath /data/db`
    rm /data/db/mongod.lock
    mongod --dbpath /data/db --repair && eval $mongodb
fi
