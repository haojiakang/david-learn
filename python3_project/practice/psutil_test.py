#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# @Time    : 2018/2/20 17:09
# @Author  : jiakang
# @File    : psutil_test.py
# @Software: IntelliJ IDEA

import psutil

# CPU逻辑数量
c = psutil.cpu_count()
print(c)
# CPU物理核心
r = psutil.cpu_count(logical=False)
print(r)
# 2说明是双核超线程，4则是4核非超线程

print(psutil.cpu_times())
# for x in range(10):
#     print(psutil.cpu_percent(interval=1, percpu=True))

print(psutil.virtual_memory())
print(psutil.swap_memory())
print(psutil.disk_partitions())
print(psutil.disk_usage("/"))
print(psutil.disk_io_counters())

print(psutil.net_io_counters())
print(psutil.net_if_addrs())
print(psutil.net_if_stats())

# print(psutil.net_connections())
print(psutil.pids())
print(psutil.test())