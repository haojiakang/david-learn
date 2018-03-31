#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2018/2/20 12:36
# @Author  : jiakang
# @File    : hmac_test.py
# @Software: IntelliJ IDEA

import hmac

message = b'Hello, world!'
key = b'secret'
h = hmac.new(key, message, digestmod='MD5')

print(h.hexdigest())