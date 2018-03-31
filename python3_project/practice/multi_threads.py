#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2018/2/19 15:31
# @Author  : jiakang
# @File    : multi_threads.py
# @Software: IntelliJ IDEA

# import time, threading
#
#
# # 新线程执行的代码：
# def loop():
#     current_thread__name = threading.current_thread().name
#     print('thread %s is running...' % current_thread__name)
#     n = 0
#     while n < 5:
#         n = n + 1
#         print('thread %s >>> %s' % (current_thread__name, n))
#         time.sleep(1)
#     print('thread %s ended.' % current_thread__name)
#
#
# print('thread %s is running...' % threading.current_thread().name)
# t = threading.Thread(target=loop, name='LoopThread')
# t.start()
# t.join()
# print('thread %s ended.' % threading.current_thread().name)

import time, threading

# 假定这是你的银行存款
balance = 0
lock = threading.Lock()


def change_it(n):
    # 先存后取，结果应该为0
    global balance
    balance = balance + n
    balance = balance - n


def run_thread(n):
    for i in range(1000000):
        # 要先获取锁：
        lock.acquire()
        try:
            # 放心的改吧：
            change_it(n)
        finally:
            # 改完了一定要释放锁：
            lock.release()


t1 = threading.Thread(target=run_thread, args=(5,))
t2 = threading.Thread(target=run_thread, args=(8,))
t1.start()
t2.start()
t1.join()
t2.join()
print(balance)

# import threading, multiprocessing
#
#
# def loop():
#     x = 0
#     while True:
#         x = x ^ 1
#
#
# for i in range(multiprocessing.cpu_count()):
#     t = threading.Thread(target=loop)
#     t.start()

import threading

# 创建全局ThreadLocal对象：
local_school = threading.local()


def process_student():
    # 获取当前线程关联的student
    std = local_school.student
    print('Hello, %s (in %s)' % (std, threading.current_thread().name))


def process_thread(name):
    # 绑定ThreadLocal的student
    local_school.student = name
    process_student()


t1 = threading.Thread(target=process_thread, args=('Alice',), name='Thread-A')
t2 = threading.Thread(target=process_thread, args=('Bob',), name='Thread-B')
t1.start()
t2.start()
t1.join()
t2.join()
