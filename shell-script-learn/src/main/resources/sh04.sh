#!/bin/bash
#Program:
#   User inputs 2 integer numbers, program will cross these two numbers.
#History:
#2016/06/12 Jackie First release

PATH=/bin:/usr/bin:/usr/local/bin:/usr/local/sbin:~/bin
export PATH

echo -e "You SHOULD input 2 numbers, I will cross them!\n"
read -p "first number: " firstnu
read -p "second number: " secondnu

total=$(($firstnu*$secondnu))
echo -e "\nThe result of $firstnu x $secondnu is ==> $total"
