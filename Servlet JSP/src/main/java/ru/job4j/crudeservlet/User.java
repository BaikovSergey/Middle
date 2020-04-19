package ru.job4j.crudeservlet;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class User {

    private Integer id;
    private String name;
    private String login;
    private String email;
    private Date createDate;

    public User(String name, String login, String email) {
        this.id = ThreadLocalRandom.current().nextInt(1000);
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

    public void setLogin(String login) {
        this.login = login;
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

