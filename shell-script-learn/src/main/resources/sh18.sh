#!/bin/bash
#Program:
#   Use input dir userName, I find the permission of files.
#History
#   2016/06/16 Jackie First release

PATH=/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/local/sbin:~/bin
export PATH

#1.先看看这个目录是否存在啊
read -p "Please input a directory: " dir
if [ "$dir" == "" -o ! -d "$dir" ]; then
    echo "The $dir is NOT exit in your system."
    exit 1
fi

#2.开始测试档案咯
filelist=$(ls $dir)
for filename in $filelist
do
    perm=""
    test -r "$dir/$filename" && perm="$perm readable"
    test -w "$dir/$filename" && perm="$perm writable"
    test -x "$dir/$filename" && perm="$perm executable"
    echo "The file $dir/$filename's permission is $perm"
done
