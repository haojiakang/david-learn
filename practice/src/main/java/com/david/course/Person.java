package com.david.course;

/**
 * Created by haojk on 11/6/16.
 */
public class Person {

    private String name;
    private int age;
    private double height;
    private String language;
    private boolean isMale;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setIsMale(boolean isMale) {
        this.isMale = isMale;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", language='" + language + '\'' +
                ", isMale=" + isMale +
                '}';
    }

    public static void main(String[] args) {
        Person tina = new Person("Tina");
//        Person david = new Person("David");
//        Person haoJiakang = new Person("Hao Jiakang");
        tina.setName("Mengsu");
        tina.setHeight(170.5);
        System.out.println(tina.name);
        System.out.println(tina.getHeight());
        System.out.println(tina);
//        System.out.println(david.name);
//        System.out.println(haoJiakang.name);
    }
}
