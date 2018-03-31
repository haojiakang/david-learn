#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# @Time    : 2018/3/9 11:36
# @Author  : jiakang
# @File    : test_orm.py
# @Software: IntelliJ IDEA

import orm

from models import User, Blog, Comment


async def test():
    await orm.create_pool(loop=1, user='root', pasword='root', database='awesome')
    u = User(name='Test', email='test@example.com', passwd='1234567890', image='about:blank')
    await u.save()


test()