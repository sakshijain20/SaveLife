package com.example.lifesaver.Model;

public class DemoDataClass {
    String name,password,secondary_number;
    Double latitude,longitude;

    public DemoDataClass() {
    }

    public DemoDataClass(String name, String password,Double latitude,Double longitude) {
        this.name = name;
        this.password = password;
        this.latitude=latitude;
        this.longitude = longitude;
    }

    public DemoDataClass(String name, String password, String secondary_number,Double latitude,Double longitude) {
        this.name = name;
        this.password = password;
        this.secondary_number = secondary_number;
        this.latitude=latitude;
        this.longitude = longitude;
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

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
