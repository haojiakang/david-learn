package com.david.learn.spring;

/**
 * Created by jiakang on 2018/4/27
 *
 * @author jiakang
 */
public class UserInfo {

    private int id;
    private String name;
    private int age;
    private boolean male;

    public UserInfo(int id, String name, int age, boolean male) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.male = male;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", male=" + male +
                '}';
    }
}