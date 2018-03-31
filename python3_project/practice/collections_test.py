#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2018/2/20 11:48
# @Author  : jiakang
# @File    : collections_test.py
# @Software: IntelliJ IDEA

from collections import namedtuple

Point = namedtuple('Point', ['x', 'y'])
p = Point(1, 2)
print(p.x, p.y)
print(isinstance(p, Point))
print(isinstance(p, tuple))

Circle = namedtuple('Circle', ['x', 'y', 'z'])
c = Circle(1, 2, 4)
print(c)

from collections import deque

q = deque(['a', 'b', 'c'])
q.append('x')
q.appendleft('y')
print(q)

from collections import defaultdict

dd = defaultdict(lambda: 'N/A')
dd['key1'] = 'abc'
print(dd['key1'])
print(dd['key2'])

from collections import OrderedDict

d = dict([('a', 1), ('b', 2), ('c', 3)])
print(d)
od = OrderedDict([('a', 1), ('b', 2), ('c', 3)])
print(od)

od = OrderedDict()
od['z'] = 1
od['y'] = 2
od['x'] = 3
print(list(od.keys()))

from collections import OrderedDict


class LastUpdatedOrderedDict(OrderedDict):
    def __init__(self, capacity):
        super(LastUpdatedOrderedDict, self).__init__()
        self._capacity = capacity

    def __setitem__(self, key, value):
        containsKey = 1 if key in self else 0
        if len(self) - containsKey >= self._capacity:
            last = self.popitem(last=False)
            print('remove', last)
        if containsKey:
            del self[key]
            print('set:', (key, value))
        else:
            print('add', (key, value))
        OrderedDict.__setitem__(self, key, value)


from collections import Counter

c = Counter()
for ch in 'programming':
    c[ch] = c[ch] + 1

print(c)
