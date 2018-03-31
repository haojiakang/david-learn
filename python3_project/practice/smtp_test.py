#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# @Time    : 2018/2/23 08:08
# @Author  : jiakang
# @File    : smtp_test.py
# @Software: IntelliJ IDEA

from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
from email.mime.multipart import MIMEBase
from email import encoders
from email.header import Header
from email.utils import parseaddr, formataddr
import smtplib


def _format_addr(s):
    name, addr = parseaddr(s)
    return formataddr((Header(name, 'utf-8').encode(), addr))


# 输入Email的地址和口令：
# from_addr = input('From:')
from_addr = 'walewolf@163.com'
# password = input('Password:')
password = 'Jackie2016'

# 输入收件人地址：
# to_addr = input('To:')
to_addr = '946392690@qq.com'
# 输入SMTP服务器地址：
# smtp_server = input('SMTP Server:')
smtp_server = 'smtp.163.com'

# 邮件对象
# msg = MIMEText('hello, send by python...', 'plain', 'utf-8')
# msg = MIMEText('<html><body><h1>hello</h1><p>send by <a herf="http://www.python.org">Python</a>...</p></body></html>', 'html', 'utf-8')
# msg = MIMEMultipart()
msg = MIMEMultipart('alternative')
msg['Subject'] = Header('来自SMTP的问候......', 'utf-8').encode()
msg['From'] = _format_addr('Python爱好者 <%s>' % from_addr)
msg['To'] = _format_addr('管理员 <%s>' % to_addr)

# 邮件正文是MIMEText
# msg.attach(MIMEText('send with file...', 'plain', 'utf-8'))
# msg.attach(MIMEText('<html><body><h1>hello</h1>' +
#                     '<p><img src="cid:0"></p>' +
#                     '</body></html>', 'html', 'utf-8'))
msg.attach(MIMEText('hello', 'plain', 'utf-8'))
msg.attach(MIMEText('<html><body><h1>hello</h1></body></html>', 'html', 'utf-8'))


# 添加附件就是加上一个MIMEBase，从本地读取一个图片
with open('dog.jpg', 'rb') as f:
    # 设置附件的MIME和文件名，这里是jpg类型
    mime = MIMEBase('image', 'jpg', filename='dog.jpg')
    # 加上必要的头信息：
    mime.add_header('Content-Disposition', 'attachment', filename='dog.jpg')
    mime.add_header('Content-ID', '<0>')
    mime.add_header('X-Attachment-ID', '0')
    # 把附件的内容读进来：
    mime.set_payload(f.read())
    # 用Base64编码：
    encoders.encode_base64(mime)
    # 添加到MIMEMultipart
    msg.attach(mime)

# SMTP 协议默认端口是25
server = smtplib.SMTP(smtp_server)
# server.starttls()
server.set_debuglevel(1)
server.login(from_addr, password)
server.sendmail(from_addr, [to_addr], msg.as_string())
server.quit()
print('send email done.')
