#!/usr/bin/env python3
# -*- coding: utf-8 -*-

f = open('test.txt', 'r')
print(f.read())
f.close()

try:
    f = open('test.txt', 'r')
    print(f.read())
finally:
    if f:
        f.close()

with open('test.txt', 'r') as f:
    print(f.read())

with open('test.txt', 'r') as f:
    for line in f.readlines():
        print(line.strip())


f = open('test.txt', 'rb')
print(f.read())
f.close()

f = open('test_gbk.txt', 'r', encoding='utf-8', errors='ignore')
print(f.read())
f.close()

f = open('test_write.txt', 'w')
f.write('Hello, world!\n')
f.write('haha')
f.close()

with open('test_write.txt', 'a') as f:
    f.write('Hello world')
