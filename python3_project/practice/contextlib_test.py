#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2018/2/20 12:50
# @Author  : jiakang
# @File    : contextlib_test.py
# @Software: IntelliJ IDEA

try:
    f = open('/Users/jiakang/test.txt', 'r')
    f.read()
finally:
    if f:
        f.close()

with open('/Users/jiakang/test.txt', 'r') as f:
    f.read()


class Query(object):
    def __init__(self, name):
        self.name = name

    def __enter__(self):
        print('Begin')
        return self

    def __exit__(self, exc_type, exc_val, exc_tb):
        if exc_type:
            print('Error')
        else:
            print('End')

    def query(self):
        print('Query info about %s...' % self.name)


with Query('Bob') as q:
    q.query()

from contextlib import contextmanager


class Query(object):
    def __init__(self, name):
        self.name = name

    def query(self):
        print('Query info about %s...' % self.name)


@contextmanager
def create_query(name):
    print('Begin')
    q = Query(name)
    yield q
    print('End')


with create_query('Bob') as q:
    q.query()


@contextmanager
def tag(name):
    print('<%s>' % name)
    yield
    print('</%s>' % name)


with tag('h1'):
    print('hello')
    print('world')

from contextlib import closing
from urllib.request import urlopen

with closing(urlopen('http://www.baidu.com')) as page:
    for line in page:
        print(line)


@closing
def closing(thing):
    try:
        yield thing
    finally:
        thing.close()
