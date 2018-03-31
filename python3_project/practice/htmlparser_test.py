#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2018/2/20 13:27
# @Author  : jiakang
# @File    : htmlparser_test.py
# @Software: IntelliJ IDEA

from html.parser import HTMLParser
from html.entities import name2codepoint


class MyHTMLParser(HTMLParser):
    def handle_starttag(self, tag, attrs):
        print('<%s>' % tag)

    def handle_endtag(self, tag):
        print('</%s>' % tag)

    def handle_startendtag(self, tag, attrs):
        print('<%s>' % tag)

    def handle_data(self, data):
        print(data)

    def handle_comment(self, data):
        print('<--', data, '-->')

    def handle_entityref(self, name):
        print('&%s;' % name)

    def handle_charref(self, name):
        print('&#%s;' % name)


parser = MyHTMLParser()
parser.feed('''<html>
<head></head>
<body>
<!-- test html parser -->
    <p>Sone <a herf=\"#\">html</a> HTML&nbsp;tutorial...<br>END</p>
</body>
''')
