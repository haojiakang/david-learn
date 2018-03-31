#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# @Time    : 2018/2/23 14:33
# @Author  : jiakang
# @File    : coroutine_test.py
# @Software: IntelliJ IDEA

def consumer():
    r = ''
    while True:
        n = yield r
        if not n:
            return
        print('[CONSUMER] Consuming %s...' % n)
        r = '200 OK'


def producer(c):
    c.send(None)
    n = 0
    while n < 5:
        n = n + 1
        print('[PRODUCER] Producing %s...' % n)
        r = c.send(n)
        print('[PRODUCER] Consumer return: %s' % r)
    c.close()


c = consumer()
producer(c)
