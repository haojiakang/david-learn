#!/bin/bash
#Program:
#   Use id, finger command to check system account's information.
#History
#   2016/06/16 Jackie First release

PATH=/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/local/sbin:~/bin
export PATH

users=$(cut -d ':' -f1 /etc/passwd)
for username in $users
do
    id $ussername
    finger $username
done
