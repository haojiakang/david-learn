#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# @Time    : 2018/2/23 10:31
# @Author  : jiakang
# @File    : mysql_test.py
# @Software: IntelliJ IDEA

########## prepare ##########

# install mysql-connector-python:
# pip3 install mysql-connector-python --allow-external mysql-connector-python

# import mysql.connector
#
# # change root password to yours:
# conn = mysql.connector.connect(user='root', password='root', database='test')
#
# cursor = conn.cursor()
# # 创建user表:
# cursor.execute('create table user (id varchar(20) primary key, name varchar(20))')
# # 插入一行记录，注意MySQL的占位符是%s:
# cursor.execute('insert into user (id, name) values (%s, %s)', ('1', 'Michael'))
# print('rowcount =', cursor.rowcount)
# # 提交事务:
# conn.commit()
# cursor.close()
#
# # 运行查询:
# cursor = conn.cursor()
# cursor.execute('select * from user where id = %s', ('1',))
# values = cursor.fetchall()
# print(values)
# # 关闭Cursor和Connection:
# cursor.close()
# conn.close()

import pymysql

# 打开数据库连接
db = pymysql.connect(host='localhost', port=5884, user='root', passwd='root', db='test')
# 使用cursor()方法创建一个游标对象
cursor = db.cursor()
# 使用execute()方法执行sql查询
# cursor.execute('DROP TABLE IF EXISTS EMPLOYEE')

# sql = """CREATE TABLE EMPLOYEE (
#         FIRST_NAME CHAR(20) NOT NULL,
#         LAST_NAME CHAR(20),
#         AGE INT,
#         SEX CHAR(1),
#         INCOME FLOAT)
# """

# SQL 插入语句
sql = """INSERT INTO EMPLOYEE(FIRST_NAME,
         LAST_NAME, AGE, SEX, INCOME)
         VALUES ('Mac', 'Mohan', 20, 'M', 2000)"""
try:
    cursor.execute(sql)
    db.commit()
except Exception as e:
    print('error', e)
    db.rollback()

# print('Database version: %s' % data)
# 关闭连接
db.close()

