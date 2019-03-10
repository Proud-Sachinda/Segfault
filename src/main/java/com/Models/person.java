package com.Models;

public class person{

    private int id;

    private String name;

    private String Surname;

/*
    public person(int id, String firstName, String lastName) {
        this.id = id;
        this.name = firstName;
        this.Surname = lastName;
    }

    public person(){

    }
*/
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

    public String getSurname() {
        return Surname;
    }


    public void setSurname(String surname) {
        Surname = surname;
    }
}
