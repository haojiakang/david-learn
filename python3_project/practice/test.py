#!/usr/bin/env python3
# -*- coding: utf-8 -*-

print('100+200+300=', 100 + 200 + 300)
print('''line1
line2
line3''')
print(r'''hello,\nworld''')
a = 13
if a > 18:
    print('large than 18')
else:
    print('small than 18')

a = 124
print(a)
a = "ATB"
print(a)
a = 'ABC'
b = a
a = 'XYZ'
print(a)
print(b)
print(10 / 3)
print(10 // 3)
print(10 % 3)
print('%2d-%02d' % (3, 1))
print('%.2f' % 3.1415926)
print('Age: %s. Gender: %s' % (25, True))
print('growth rate: %d %%' % 7)
print('Hello, {0}, 成绩提升了 {1:.1f}%'.format('小明', 17.125))

age = 20
if age >= 6:
    print('teenager')
elif age >= 18:
    print('adult')
else:
    print('kid')

x = -1
if x:
    print('True')

# birth = input('birth: ')
# birth = int(birth)
# if birth < 2000:
#     print('00前')
# else:
#     print('00后')

names = ['Jackie', 'Tina', 'David']
for name in names:
    print(name)

sum = 0
for x in [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]:
    sum += x
print(sum)

print(list(range(5)))

sum = 0
for x in range(101):
    sum += x
print(sum)

sum = 0
n = 99
while n > 0:
    sum += n
    n -= 2
print(sum)

n = 1
while n <= 100:
    if n > 10:
        break
    print(n)
    n += 1
print('END')

d = {'Micheal': 95, 'Bob': 75, 'Tracy': 85}
print(d['Micheal'])

s = {1, 2, 3}
print(s)


def my_abs(x):
    if x >= 0:
        return x
    else:
        return -x


print(my_abs(-99))

# def my_abs(x):
#     if not isinstance(x, (int, float)):
#         raise TypeError('bad operand type')
#     if x >= 0:
#         return x
#     else:
#         return -x
#
#
# print(my_abs('A'))


import math


def move(x, y, step, angle=0):
    nx = x + step * math.cos(angle)
    ny = y - step * math.sin(angle)
    return nx, ny


x, y = move(100, 100, 60, math.pi / 6)
print(x, y)


def power(x, n=2):
    s = 1
    while n > 0:
        n -= 1
        s *= x
    return s


print(power(5, 2))
print(power(5, 3))
print(power(5))


def enroll(name, gender, age=6, city='Beijing'):
    print('name:', name)
    print('gender:', gender)
    print('age:', age)
    print('city:', city)


enroll('Sarah', 'F')
enroll('Bob', 'M', 7)
enroll('Adam', 'M', city='Tianjin')


def add_end(L=None):
    if L is None:
        L = []
    L.append('END')
    return L


print(add_end())
print(add_end())


def calc(*numbers):
    sum = 0
    for n in numbers:
        sum = sum + n * n
    return sum


print(calc(1, 2))
print(calc(1, 2, 3, 4, 5))
print(calc())

nums = [1, 2, 3]
print(calc(nums[0], nums[1]))
print(calc(*nums))


def person(name, age, **kw):
    print('name:', name, 'age:', age, 'other:', kw)


person('Micheal', 30)
person('Bob', 35, city='Beijing', gender='M')
extra = {'city': 'Beijing', 'job': 'Engineer'}
person('Jack', 24, city=extra['city'], job=extra['job'])
person('Jack', 24, **extra)


def person(name, age, **kw):
    if 'city' in kw:
        # 有city参数
        pass
    if 'job' in kw:
        # 有job参数
        pass
    print('name', name, 'age', age, 'other:', kw)


person('Jack', 24, city='Beijing', addr='Chaoyang', zipcode=123456)


def person(name, age, *, city, job):
    print(name, age, city, job)


person('Jack', 24, city='Beijing', job='Engineer')


def person(name, age, *args, city, job):
    print(name, age, args, city, job)


person('Jack', 24, 'haha', 'shabi', city='Beijing', job='Engineer')


def person(name, age, *, city='Beijing', job):
    print(name, age, city, job)


person('Jack', 24, job='Engineer')


def person(name, age, city, job):
    print(name, age, city, job)


person('Jack', 24, 'Beijing', job='Engineer')


def f1(a, b, c=0, *args, **kw):
    print('a=', a, 'b=', b, 'c=', c, 'args=', args, 'kw=', kw)


def f2(a, b, c=0, *, d, **kw):
    print('a=', a, 'b=', b, 'c=', c, 'd=', d, 'kw=', kw)


f1(1, 2)
f1(1, 2, c=3)
f1(1, 2, 3, 'a', 'b')
f1(1, 2, 3, 'a', 'b', x=99)
f2(1, 2, d=99, ext=None)

args = {1, 2, 3, 4}
kw = {'d': 99, 'x': '#'}
f1(*args, **kw)
args = {1, 2, 3}
kw = {'d': 88, 'x': '#'}
f2(*args, **kw)


def fact(n):
    if n == 1:
        return 1
    return n * fact(n - 1)


print(fact(1))
print(fact(3))
print(fact(9))


# print(fact(1000))


def fact(n):
    return fac_iter(n, 1)


def fac_iter(num, product):
    if num == 1:
        return product
    return fac_iter(num - 1, num * product)


fac_iter(1, 1)
fac_iter(3, 1)
fac_iter(9, 1)
# fac_iter(1000, 1)


L = []
n = 1
while n <= 99:
    L.append(n)
    n = n + 2

print(L)

L = ['Micheal', 'Sarah', 'Tracy', 'Bob', 'Jack']
print([L[0], L[1], L[2]])

r = []
n = 3
for i in range(n):
    r.append(L[i])

print(r)
print(L[0:3])
print(L[:3])
print(L[-2:-1])

L = list(range(100))
print(L)
print(L[:10])
print(L[-10:])
print(L[10:20])
print(L[:10:2])
print(L[::5])
print(L[:])

T = tuple(range(100))
print(T[:3])
print('ABCDEFG'[:3])
print('ABCDEFG'[::2])


def fib(max):
    n, a, b = 0, 0, 1
    while n < max:
        print(b)
        a, b = b, a + b
        n = n + 1
    return 'done'


print(fib(1))


def fib(max):
    n, a, b = 0, 0, 1
    while n < max:
        yield b
        a, b = b, a + b
        n += 1
    return 'done'


f = fib(6)
for n in f:
    print(n)


def odd():
    print('step 1')
    yield 1
    print('step 2')
    yield (3)
    print('step 3')
    yield (5)


o = odd()
print(next(o))
print(next(o))
print(next(o))

g = fib(6)
while True:
    try:
        x = next(g)
        print('g', x)
    except StopIteration as e:
        print('Generator return value:', e.value)
        break

from collections import Iterable

print(isinstance([], Iterable))
print(isinstance({}, Iterable))
print(isinstance('abc', Iterable))
print(isinstance((x for x in range(10)), Iterable))
print(isinstance(100, Iterable))

from collections import Iterator

print(isinstance((x for x in range(10)), Iterator))
print(isinstance([], Iterator))
print(isinstance({}, Iterator))
print(isinstance('abc', Iterator))

print(isinstance(iter([]), Iterator))
print(isinstance(iter({}), Iterator))

it = iter([1, 2, 3, 4, 5])
while True:
    try:
        x = next(it)
        print(x)
    except StopIteration:
        break

print(abs(-10))
print(abs)
f = abs
print(f)
print(f(10))


def add(x, y, f):
    return f(x) + f(y)


print(add(-5, 6, abs))


def f(x):
    return x * x


r = map(f, [1, 2, 3, 4, 5, 6])
print(list(r))
print(list(map(str, [1, 2, 3, 4, 5, 6])))

from functools import reduce


def add(x, y):
    return x + y


print(reduce(add, [1, 3, 5, 7, 9]))


def fn(x, y):
    return x * 10 + y


def char2num(s):
    digits = {'0': 0, '1': 1, '2': 2, '3': 3, '4': 4, '5': 5, '6': 6, '7': 7, '8': 8, '9': 9}
    return digits[s]


print(reduce(fn, map(char2num, '13579')))

DIGITS = {'0': 0, '1': 1, '2': 2, '3': 3, '4': 4, '5': 5, '6': 6, '7': 7, '8': 8, '9': 9}


def str2int(s):
    def fn(x, y):
        return x * 10 + y

    def char2num(s):
        return DIGITS[s]

    return reduce(fn, map(char2num, s))


print(str2int('1267'))


def char2num(s):
    return DIGITS[s]


def str2int(s):
    return reduce(lambda x, y: x * 10 + y, map(char2num, s))


print(str2int('2355334'))


def is_odd(n):
    return n % 2 == 1


print(list(filter(lambda x: x % 2 == 1, [1, 2, 3, 4, 5, 6, 7, 8, 9, 0])))
print(list(filter(lambda x: x and x.strip(), ['A', '', 'B', None, 'C', '    '])))


def _odd_filter():
    n = 1
    while True:
        n = n + 2
        yield n


def _not_divisible(n):
    return lambda x: x % n > 0


def primes():
    yield 2
    it = _odd_filter()
    while True:
        n = next(it)
        yield n
        it = filter(_not_divisible(n), it)


for n in primes():
    if n < 1000:
        print(n)
    else:
        break

l = [36, 5, -12, 9, -21]
print(sorted(l))
print(sorted(l, key=abs))
names = ['bob', 'about', 'Zoo', 'Credit']
print(sorted(names))
print(sorted(names, key=str.lower))
print(sorted(names, key=str.lower, reverse=True))


def lazy_sum(*args):
    def sum():
        ax = 0
        for n in args:
            ax = ax + n
        return ax

    return sum


f = lazy_sum(1, 3, 5, 7, 9)
print(f())
f2 = lazy_sum(1, 3, 5, 7, 9)
print(f == f2)


def count():
    fs = []
    for i in range(1, 4):
        def f():
            return i * i;

        fs.append(f)
    return fs


f1, f2, f3 = count()
print(f1())
print(f2())
print(f3())


def count():
    def f(j):
        def g():
            return j * j

        return g

    fs = []
    for i in range(1, 4):
        fs.append(f(i))
    return fs


f1, f2, f3 = count()
print(f1())
print(f2())
print(f3())

print(list(map(lambda x: x * x, l)))
f = lambda x: x * x
print(f(5))


def build(x, y):
    return lambda: x * x + y * y


print(build(2, 3)())


def now():
    print('2018-02-13')


f = now
f()

print(now.__name__)
print(f.__name__)


def log(func):
    def wrapper(*args, **kw):
        print('call %s():' % func.__name__)
        return func(*args, **kw)

    return wrapper


@log
def now():
    print('2018-02-13')


now()

import functools


def log(text):
    def decorator(func):
        @functools.wraps(func)
        def wrapper(*args, **kw):
            print('%s %s():' % (text, func.__name__))
            return func(*args, **kw)

        return wrapper

    return decorator


@log('execute')
def now():
    print('2018-02-14')


now()
print(now.__name__)

print(int('12356', base=8))


def int2(x, base=2):
    return int(x, base)


print(int2('100000'))

int2 = functools.partial(int, base=2)
print(int2('100000'))
print(int2('100000', base=10))

max2 = functools.partial(max, 10)
print(max2(5, 6, 7))

import sys
print(sys.path)

import moduletest
moduletest.test()

