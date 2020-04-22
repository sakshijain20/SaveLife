package com.example.lifesaver.Model;

public class DemoDataClass {
    String name,password,secondary_number,number;

    public DemoDataClass() {
    }

    public DemoDataClass(String name, String password, String secondary_number) {
        this.name = name;
        this.password = password;
        this.secondary_number = secondary_number;
    }

    public DemoDataClass(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
