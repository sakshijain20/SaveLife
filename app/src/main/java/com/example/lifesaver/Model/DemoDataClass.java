package com.example.lifesaver.Model;

public class DemoDataClass {
    String name,password,secondary_number;

    public DemoDataClass() {
    }

    public DemoDataClass(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public DemoDataClass(String name, String password, String secondary_number) {
        this.name = name;
        this.password = password;
        this.secondary_number = secondary_number;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getSecondary_number() {
        return secondary_number;
    }
}
