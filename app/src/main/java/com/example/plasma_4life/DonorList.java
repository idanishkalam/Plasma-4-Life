package com.example.plasma_4life;

public class DonorList {
    String name;
    String group;
    String phone_num;

    public DonorList(String name, String group,String phone_num) {
        this.name = name;
        this.group = group;
        this.phone_num=phone_num;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }
    public String getPhone_num(){return phone_num;}
}
