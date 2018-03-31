#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2018/2/20 16:32
# @Author  : jiakang
# @File    : requests_test.py
# @Software: IntelliJ IDEA

import requests

r = requests.get('http://www.baidu.com')
print(r.status_code)
print(r.text)

r = requests.get('https://www.douban.com/search', params={'q': 'python', 'cat': '1001'})
print(r.url)
print(r.encoding)
print(r.content)

r = requests.get('https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20%3D%202151330&format=json')
print(r.json())

r = requests.get('https://www.douban.com/', headers={'User-Agent': 'Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit'})
# print(r.text)

r = requests.post('https://accounts.douban.com/login', data={'from_email': 'abc@example.com', 'form_password': '123456'})
# print(r.text)

params = {'key': 'value'}
r = requests.post('https://www.baidu.com', json=params, timeout=2.5)
# print(r.text)
print(r.headers)
print(r.cookies)
# print(r.cookies['ts'])