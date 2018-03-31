#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2018/2/20 12:30
# @Author  : jiakang
# @File    : hashlib_test.py
# @Software: IntelliJ IDEA

import hashlib

md5 = hashlib.md5()
md5.update('how to use md5 in python hashlib?'.encode('utf-8'))
print(md5.hexdigest())

sha1 = hashlib.sha1()
sha1.update('how to use sha1 in python hashlib?'.encode('utf-8'))
print(sha1.hexdigest())