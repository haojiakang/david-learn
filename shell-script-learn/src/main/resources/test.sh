#!/usr/bin/env bash

domain="10.73.14.101:8080"
domain_arr=(${domain//:/ })
${domain_arr[@]}

echo ${#domain_arr[@]}
echo ${domain_arr[0]}
echo ${domain_arr[1]}
echo ${domain_arr[2]}
