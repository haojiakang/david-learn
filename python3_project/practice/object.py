std1 = {'name': 'Micheal', 'score': 98}
std2 = {'name': 'Jack', 'score': 54}


def print_score(std):
    print('%s: %s' % (std['name'], std['score']))


print_score(std1)
print_score(std2)


class Student(object):
    def __init__(self, name, score):
        self.name = name
        self.score = score

    def print_score(self):
        print('%s: %s' % (self.name, self.score))

    def get_grade(self):
        if self.score >= 90:
            return 'A'
        elif self.score >= 60:
            return 'B'
        else:
            return 'C'


bart = Student('Bart Simpson', 59)
lisa = Student('Lisa Simpson', 71)
bart.print_score()
lisa.print_score()
print(lisa.name, lisa.get_grade())


class Student(object):
    pass


bart = Student()
print(bart)
print(Student)
bart.name = 'Bart Simpson'
print(bart.name)


class Student(object):
    def __init__(self, name, score):
        self.__name = name
        self.__score = score

    def print_score(self):
        print('%s: %s', (self.__name, self.__score))

    def get_name(self):
        return self.__name

    def get_score(self):
        return self.__score

    def set_score(self, score):
        if 0 <= score <= 100:
            self.__score = score
        else:
            raise ValueError('bad score')


bart = Student('Bart Simpson', 59)
print(bart.get_name())


class Animal(object):
    def run(self):
        print('Animal is running...')


class Dog(Animal):
    pass


class Cat(Animal):
    pass


dog = Dog()
dog.run()
cat = Cat()
cat.run()


class Dog(Animal):
    def run(self):
        print('Dog is running...')

    def eat(self):
        print('Eating meat...')


dog = Dog()
dog.run()

print(type(123))

import types


def fn():
    pass


print(type(fn) == types.FunctionType)
print(type(abs) == types.BuiltinFunctionType)
print(type(lambda x: x) == types.LambdaType)
print(type((x for x in range(10))) == types.GeneratorType)

print(dir('ABC'))


class MyDog(object):
    def __len__(self):
        return 100


dog = MyDog()
print(len(dog))
print('ABC'.lower())


class MyObject(object):
    def __init__(self):
        self.x = 9

    def power(self):
        return self.x * self.x


obj = MyObject()

print(hasattr(obj, 'x'))
print(obj.x)
print(hasattr(obj, 'y'))
setattr(obj, 'y', 19)
print(hasattr(obj, 'y'))
print(getattr(obj, 'y'))
print(obj.y)
print(getattr(obj, 'z', 404))

print(hasattr(obj, 'power'))
getattr(obj, 'power')
fn = getattr(obj, 'power')
print(fn)
print(fn())


class Student(object):
    def __init__(self, name):
        self.name = name


s = Student('Bob')
s.score = 90


class Student(object):
    name = 'Student'


s = Student()
print(s.name)
print(Student.name)
s.name = 'Micheal'
print(s.name)
print(Student.name)
del s.name
print(s.name)


def set_age(self, age):
    self.age = age


from types import MethodType

s.set_age = MethodType(set_age, s)
s.set_age(25)
print(s.age)

s2 = Student()


# s2.set_age(24)
def set_score(self, score):
    self.score = score


Student.set_score = set_score

s.set_score(100)
s2.set_score(45)
print(s.score)
print(s2.score)


class Student(object):
    __slots__ = ('name', 'age')


s = Student()
s.name = 'Micheal'
s.age = 25


# s.score = 99

class GraduateStudent(Student):
    __slots__ = ('score')
    pass


g = GraduateStudent()
g.score = 99
# g.path = 'www'
print(g.score)


# print(g.path)

class Student(object):

    @property
    def score(self):
        return self._score

    @score.setter
    def score(self, value):
        if not isinstance(value, int):
            raise ValueError('score must be an integer!')
        if value < 0 or value > 100:
            raise ValueError('score must be betwwen 0 ~ 100!')
        self._score = value


s = Student()
s.score = 60
print(s.score)


# s.score = 999
# print(s.score)


class Student(object):

    @property
    def birth(self):
        return self._birth

    @birth.setter
    def birth(self, value):
        self._birth = value

    @property
    def age(self):
        return 2015 - self._birth


s = Student()
s.birth = 2012
print(s.birth)
print(s.age)


class Student(object):
    def __init__(self, name):
        self.name = name

    def __str__(self):
        return 'Student object (name: %s)' % self.name

    __repr__ = __str__


print(Student('Micheal'))


class Fib(object):
    def __init__(self):
        self.a, self.b = 0, 1

    def __iter__(self):
        return self

    def __next__(self):
        self.a, self.b = self.b, self.a + self.b
        if self.a > 100000:
            raise StopIteration()
        return self.a

    def __getitem__(self, n):
        if isinstance(n, int):
            a, b = 1, 1
            for x in range(n):
                a, b = b, a + b
            return a
        if isinstance(n, slice):
            start = n.start
            stop = n.stop
            if start is None:
                start = 0
            a, b = 1, 1
            L = []
            for x in range(stop):
                if x >= start:
                    L.append(a)
                a, b = b, a + b
            return L


f = Fib()
for n in f:
    print(n)
print(f[5])
print(f[0:5])
print(f[:10])


class Student(object):
    def __init__(self):
        self.name = 'Michael'

    def __getattr__(self, attr):
        if attr == 'age':
            return lambda: 25
        raise AttributeError('\'Student\' object has no attributes \' %s\'')


s = Student()
print(s.name)
print(s.age())


# print(s.score)


class Chain(object):
    def __init__(self, path=''):
        self._path = path

    def __getattr__(self, path):
        return Chain('%s/%s' % (self._path, path))

    def __str__(self):
        return self._path

    __repr__ = __str__


print(Chain().status.user.timeline.list)


class Student(object):
    def __init__(self, name):
        self.name = name

    def __call__(self):
        print('My name is %s.' % self.name)


s = Student('Michael')
print(s())
print(callable(s))
print(callable(max))
print(callable([]))
print(callable(None))
print(callable('str'))

from enum import Enum, unique

Month = Enum('Month', ('Jan', 'Feb', 'Mar' 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'))

for name, member in Month.__members__.items():
    print(name, '=>', member, ',', member.value)


@unique
class Weekday(Enum):
    Sun = 0
    Mon = 1
    Tue = 2
    Wed = 3
    Thu = 4
    Fri = 5
    Sat = 6


day1 = Weekday.Mon
print(day1)
print(Weekday.Tue)
print(Weekday['Tue'])
print(Weekday.Tue.value)
print(day1 == Weekday.Mon)
print(day1 == Weekday.Tue)
print(Weekday(1))
print(day1 == Weekday(1))


# print(Weekday(7))

# from hello import Hello

def fn(self, name='world'):
    print('Hello, %s.' % name)


Hello = type('Hello', (object,), dict(hello=fn))

h = Hello()
h.hello()
print(type(Hello))
print(type(h))


class ListMetaclass(type):
    def __new__(cls, name, bases, attrs):
        attrs['add'] = lambda self, value: self.append(value)
        return type.__new__(cls, name, bases, attrs)

class MyList(list, metaclass=ListMetaclass):
    pass

L = MyList()
L.add(1)
L.append(2)
print(L)