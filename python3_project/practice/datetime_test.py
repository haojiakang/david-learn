#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2018/2/20 11:26
# @Author  : jiakang
# @File    : datetime_test.py
# @Software: IntelliJ IDEA

from datetime import datetime

now = datetime.now()
print(now)
print(type(now))

dt = datetime(2015, 4, 19, 12, 20, 45)
print(dt)
print(dt.timestamp())
t = dt.timestamp()
print(datetime.fromtimestamp(t))
print(datetime.utcfromtimestamp(t))

cday = datetime.strptime('2015-6-1 18:58:12', '%Y-%m-%d %H:%M:%S')
print(cday)

now = datetime.now()
print(now.strftime('%a, %b %d %H:%M'))

from datetime import timedelta

now = datetime.now()
print(now)
print(now + timedelta(hours=10))
print(now - timedelta(days=1))
print(now + timedelta(days=2, hours=12))

from datetime import timezone

tz_utc_8 = timezone(timedelta(hours=8))
now = datetime.now()
print(now)
dt = now.replace(tzinfo=tz_utc_8)
print(dt)
dt = datetime(2015, 5, 18, 17, 2, 10, 871012)
print(dt)
dt = dt.replace(tzinfo=tz_utc_8)
print(dt)

utc_dt = datetime.utcnow().replace(tzinfo=timezone.utc)
print(utc_dt)
bj_dt = utc_dt.astimezone(timezone(timedelta(hours=8)))
print(bj_dt)
tokyo_dt = utc_dt.astimezone(timezone(timedelta(hours=9)))
print(tokyo_dt)
tokyo_dt2 = bj_dt.astimezone(timezone(timedelta(hours=9)))
print(tokyo_dt2)
