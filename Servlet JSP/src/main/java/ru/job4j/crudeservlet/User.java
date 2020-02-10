package ru.job4j.crudeservlet;

import java.util.Date;

public class User {

    private Integer id;
    private String name;
    private String login;
    private String email;
    private Date createDate;

    public User(int id, String name, String login, String email) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.email = email;
        this.createDate = new Date();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return "id= " + id
                + ", name= '" + name + '\''
                + ", login= '" + login + '\''
                + ", email= '" + email + '\''
                + ", createDate= " + createDate;
    }
}

