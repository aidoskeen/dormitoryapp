package com.example.dormitoryapp.models;

import java.util.List;


public class Administrator extends User {

    private List<Dormitory> dorms;


    public Administrator() {
    }

    public Administrator(String login, String password, String name, String surname, String phone_num, String email, List<Dormitory> dorms) {
        super(login, password, name, surname, phone_num, email);
        this.dorms = dorms;
    }

    public List<Dormitory> getDorms() {
        return dorms;
    }


    public void setDorms(List<Dormitory> dorms) {
        this.dorms = dorms;
    }
}
