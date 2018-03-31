#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2018/2/20 12:26
# @Author  : jiakang
# @File    : struct_test.py
# @Software: IntelliJ IDEA

import struct

print(struct.pack('>I', 10240099))
print(struct.unpack('>IH', b'\xf0\xf0\xf0\xf0\x80\x80'))