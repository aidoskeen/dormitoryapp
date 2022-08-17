package com.example.dormitoryapp.models;


import java.util.List;

public class Dormitory {

    private int dorm_num;
    private String address;
    private int rooms_count;


    private List<Room> rooms;

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Dormitory(String address, List<Room> rooms) {
        this.address = address;
        this.rooms = rooms;
    }

    public Dormitory(){}

    public String getAddress() {
        return address;
    }
    public void setDorm_num(int dorm_num) {
        this.dorm_num = dorm_num;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public int getRooms_count() {
        return rooms_count;
    }
    public void setRooms_count(int rooms_count) {
        this.rooms_count = rooms_count;
    }
    public int getDorm_num() {
        return dorm_num;
    }

    @Override
    public String toString() {
        return "Dormitory{" +
                "dorm_num=" + dorm_num +
                ", address='" + address + '\'' +
                ", rooms_count=" + rooms_count +
                '}';
    }
}
