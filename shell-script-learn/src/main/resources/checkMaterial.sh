#!/bin/bash

current=0
passCheck=1

function usage(){
    echo "Usage: wtool matl [method] [option] <domain>"
    echo ""
    echo "  matl is a useful tool for online materials config. "
    echo "  use matl, you can show and refresh materials config on just one machine or "
    echo "  completely show and refresh materials config to all machines. "
    echo ""
    echo "  Some of the methods include: "
    echo "   show:          show materials config on one machine or all machines"
    echo "   refresh:       refresh materials config on one machine or all machines"
    echo ""
    echo "  Some of the options include: "
    echo "   -l:            output result length"
    echo "   -d:            output result details"
    echo ""
    echo "  Domain for specify which ip and port to use, with ip:port format."
    echo "   if specified domain, the operation is just on the one machine. "
    echo "   if not specified domain (not input this arg), the operation is on all the machines of message-flow pools, "
    echo "      which you can use 'wtool jpool -p message_flow' to check the pools and machines in them."
    echo ""
    echo "eg:"
    echo "  wtool matl show -l"
    echo "  wtool matl show -d"
    echo "  wtool matl refresh -l"
    echo "  wtool matl refresh -d"
    echo "  wtool matl show -l 10.77.6.220:8090"
    echo "  wtool matl refresh -d 10.75.2.158:8080"
    exit 0
}

function do_check_one(){
    host=$1
    port=$2
    method=$3
    show_level=$4

    domain=${host}":"${port}
    result=$(curl -s --connect-timeout 1 ${domain}"/materials/"${method}".json")
    if [[ ${current} = 0 ]]; then
        current=${#result}
    elif [[ ${current} != ${#result} ]]; then
        passCheck=0
    fi
    if [[ ${show_level} = '-l' ]]; then
        echo ${host}" "${#result}
    elif [[ ${show_level} = '-d' ]]; then
        echo ${host}":"
        echo ${result} | jq .
    else
        usage
    fi
}

function do_check(){
    current_pool_name=$1
    raw_host_ips_str=$2
    port=$3
    method=$4
    show_level=$5

    echo ${current_pool_name}":"
    host_ips=(${raw_host_ips_str//,/ })
    for host in ${host_ips[@]};
    do
        do_check_one ${host} ${port} ${method} ${show_level}
    done
    echo ""
}

function do_check_pool(){
    pool_name=$1
    port=$2
    method=$3
    show_level=$4

    raw_pool_result_str=$(wtool jpool -p ${pool_name})
    deal_pool_result_str=$(echo ${raw_pool_result_str//-/_})
    pool_key_arr=$(echo ${deal_pool_result_str} | jq 'keys')
#    echo ${pool_key_arr}

    for pool_key in ${pool_key_arr}
    do
    #    echo ${pool_key}
        if [[ ${pool_key} != '[' && ${pool_key} != ']' ]]; then
            one_deal=${pool_key#*\"}
            final_pool_key=${one_deal%\"*}
    #        echo ${final_pool_key}

            not_deal_ips=$(echo ${deal_pool_result_str} | jq .${final_pool_key})
            one_deal_ips=${not_deal_ips#*\"}
            final_pool_ips=${one_deal_ips%\"*}
#            echo ${final_pool_ips}
            do_check ${final_pool_key} ${final_pool_ips} ${port} ${method} ${show_level}
        fi
    done
}

function check_pass(){
    if [[ ${passCheck} = 0 ]]; then
        echo "not pass check, at least one result's length is not the same of others."
    else
        echo "pass check, all results' length are the same."
    fi
}

if [[ $# = 2 ]]; then
    if [[ $1 = 'show' || $1 = 'refresh' ]]; then
        if [[ $2 = '-d' || $2 = '-l' ]]; then
            do_check_pool "message_flow_proc" "8090" $1 $2
            do_check_pool "message_flow_web" "8080" $1 $2
            check_pass
        else
            usage
        fi
    else
        usage
    fi
elif [[ $# = 3 ]]; then
    if [[ $1 = 'show' || $1 = 'refresh' ]]; then
        if [[ $2 = '-d' || $2 = '-l' ]]; then
            domain=$3
            domain_arr=(${domain//:/ })
            if [[ ${#domain_arr[@]} = 2 ]]; then
                host=${domain_arr[0]}
                port=${domain_arr[1]}
                do_check_one ${host} ${port} $1 $2
            else
                usage
            fi
        else
            usage
        fi
    else
        usage
    fi
else
    usage
fi
