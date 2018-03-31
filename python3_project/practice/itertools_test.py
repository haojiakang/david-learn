#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2018/2/20 12:40
# @Author  : jiakang
# @File    : itertools_test.py
# @Software: IntelliJ IDEA

import itertools

# natuals = itertools.count(1)
# for n in natuals:
#     print(n)
#

# cs = itertools.cycle('ABC')
# for c in cs:
#     print(c)


ns = itertools.repeat('A', 3)
for n in ns:
    print(n)

natuals = itertools.count(1)
ns = itertools.takewhile(lambda x: x <= 10, natuals)
print(list(ns))

for c in itertools.chain('ABC', 'XYZ'):
    print(c)

for key, group in itertools.groupby('AAABBBCCAAA'):
    print(key, list(group))

for key, group in itertools.groupby('AaaBBbcCAAa', lambda c: c.upper()):
    print(key, list(group))

