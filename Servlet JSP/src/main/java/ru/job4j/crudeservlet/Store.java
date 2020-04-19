package ru.job4j.crudeservlet;

import java.util.List;

public interface Store {
    boolean add(int id, User user);
    boolean update(int id, User user);
    boolean delete(int id);
    List<User> findAll();
    User findById(int id);
    boolean userExist(int id);
}
