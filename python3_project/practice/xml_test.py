#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2018/2/20 13:16
# @Author  : jiakang
# @File    : xml_test.py
# @Software: IntelliJ IDEA

from xml.parsers.expat import ParserCreate


class DefaultSaxHandler(object):
    def start_element(self, name, attrs):
        print('sax:start_element: %s, attrs: %s' % (name, str(attrs)))

    def end_element(self, name):
        print('sax:end_element: %s' % name)

    def char_data(self, text):
        print('sax:char_data: %s' % text)


xml = r'''<?xml version="1.0"?>
    <ol>
        <li><a herf="/python">Python</a></li>
        <li><a herf="/ruby">Ruby</a></li>
    </ol>
'''

handler = DefaultSaxHandler()
parser = ParserCreate()
parser.StartElementHandler = handler.start_element
parser.EndElementHandler = handler.end_element
parser.CharacterDataHandler = handler.char_data
parser.Parse(xml)
