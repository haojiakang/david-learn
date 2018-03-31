#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2018/2/19 10:22
# @Author  : jiakang
# @File    : file_dir.py
# @Software: IntelliJ IDEA

import os

print(os.name)
print(os.uname())
print(os.environ)
print(os.environ.get('PATH'))
print(os.path.abspath('.'))
print(os.path.join('.', 'testdir'))
# os.mkdir('testdir')
# os.rmdir('testdir')

print(os.path.split('/Users/jiakang/testdir/file.txt'))
print(os.path.splitext('/path/to/file.txt'))

# os.rename('test.txt', 'test_rename.txt')
# os.remove('test_rename.txt')

import shutil

[print(x) for x in os.listdir('.') if os.path.isfile(x) and os.path.splitext(x)[1] == 'py']
