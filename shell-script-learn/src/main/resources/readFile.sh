#!/bin/bash
#使用方法：./script.sh filename
a=$(sed -n '$p' $1 | awk -F"," '{print $1}')
b=$(sed -n '1!P;N;$q;D' $1 | awk -F"," '{print $1}')
echo 'a='$a
echo 'b='$b