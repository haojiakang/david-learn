#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2018/2/19 16:31
# @Author  : jiakang
# @File    : regex_test.py
# @Software: IntelliJ IDEA

import re

print(re.match(r'^\d{3}\-\d{3,8}$', '010-12345'))
if re.match(r'^\d{3}\-\d{3,8}$', '010 12345'):
    print('ok')
else:
    print('failed')

print('a b  c'.split(' '))
print(re.split(r'\s+', 'a b c'))
print(re.split(r'[\s\,]+', 'a,b, c  d'))
print(re.split(r'[\s\,\;]+', 'a,b;; c   d'))

m = re.match(r'^(\d{3})-(\d{3,8})$', '010-12345')
print(m.group(0))
print(m.group(1))
print(m.group(2))
print(m.groups())

print(re.match(r'^(\d+)(0*)$', '102300').groups())
print(re.match(r'^(\d+?)(0*)$', '102300').groups())

# 编译：
re_telephone = re.compile(r'^(\d{3})-(\d{3,8})$')
# 使用：
print(re_telephone.match('010-12345').groups())
print(re_telephone.match('010-8086').groups())

