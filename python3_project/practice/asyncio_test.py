#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# @Time    : 2018/2/23 14:49
# @Author  : jiakang
# @File    : asyncio_test.py
# @Software: IntelliJ IDEA

import asyncio
import threading


async def hello():
    print('Hello World! (%s)' % threading.currentThread())
    # 异步调用asyncio.sleep(1)
    await asyncio.sleep(1)
    print('Hello again! (%s)' % threading.currentThread())


# 获取EventLoop：
loop = asyncio.get_event_loop()
tasks = [hello(), hello()]
# 执行coroutine
loop.run_until_complete(asyncio.wait(tasks))
# loop.close()


async def wget(host):
    print('wget %s...' % host)
    connect = asyncio.open_connection(host, 80)
    reader, writer = await connect
    header = 'GET / HTTP/1.0\r\nHost: %s\r\n\r\n' % host
    writer.write(header.encode('utf-8'))
    await writer.drain()
    while True:
        line = await reader.readline()
        if line == b'\r\n':
            break
        print('%s header > %s' % (host, line.decode('utf-8').rstrip()))
    # Ignore the body, close the socket
    writer.close()


# loop = asyncio.get_event_loop()
tasks = [wget(host) for host in ['www.sina.com.cn', 'www.sohu.com', 'www.163.com']]
loop.run_until_complete(asyncio.wait(tasks))
loop.close()
