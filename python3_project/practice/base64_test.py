#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2018/2/20 12:21
# @Author  : jiakang
# @File    : base64_test.py
# @Software: IntelliJ IDEA

import base64

encode = base64.b64encode(b'binary\x00string')
print(encode)
print(base64.b64decode(encode))

encode = base64.b64encode(b'i\xb7\x1d\xfb\xef\xff')
print(encode)
encode = base64.urlsafe_b64encode(b'i\xb7\x1d\xfb\xef\xff')
print(encode)
print(base64.urlsafe_b64decode(encode))

