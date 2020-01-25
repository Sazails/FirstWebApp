package com.main;

public class User {
    private int id;
    private String username;
    private String password;
    private int age;
    private String gender;

    public User(){
        this.id = 0;
        this.username = "";
        this.password = "";
        this.age = 0;
        this.gender = "male";
    }

    public User(int id, String username, String password, int age, String gender){
        this.username = username;
        this.password = password;
        this.age = age;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                '}';
    }
}
