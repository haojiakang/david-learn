#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2018/2/19 10:16
# @Author  : jiakang
# @File    : file_like_io.py
# @Software: IntelliJ IDEA

from io import StringIO

f = StringIO()
print(f.write('hello'))
f.write(' ')
f.write('world')
print(f.getvalue())

f = StringIO('Hello!\nHi!\nGoodbye!')
while True:
    s = f.readline()
    if s == '':
        break
    print(s.strip())

from io import BytesIO

f = BytesIO()
f.write('中文'.encode('utf-8'))

print(f.getvalue())

f = BytesIO(b'\xe4\xb8\xad\xe6\x96\x87')
print(str(f.read(), encoding='utf-8'))
