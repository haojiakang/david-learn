#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# @Time    : 2018/2/20 17:06
# @Author  : jiakang
# @File    : chardet_test.py
# @Software: IntelliJ IDEA

import chardet

print(chardet.detect(b'Hello, world'))
data = '离离原上草，一岁一枯荣'.encode('GBK')
print(chardet.detect(data))
data = '离离原上草，一岁一枯荣'.encode('utf-8')
print(chardet.detect(data))
data = '最新の主要ニュース'.encode('euc-jp')
print(chardet.detect(data))
