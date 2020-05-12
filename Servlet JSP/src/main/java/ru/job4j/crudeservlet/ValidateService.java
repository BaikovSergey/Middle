package ru.job4j.crudeservlet;

import java.util.ArrayList;
import java.util.List;

public class ValidateService implements Validate {

    private final Store logic = MemoryStore.getInstance();

    private static final ValidateService INSTANCE = new ValidateService();

    private ValidateService() {

    }

    public static ValidateService getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(int id, User user) {
        boolean result = false;
            if (!userExist(id)) {
                this.logic.add(id, user);
                result = true;
            }
        return result;
    }

    @Override
    public boolean update(int id, User user) {
        boolean result = false;
            if (userExist(id)) {
                this.logic.update(id, user);
                result = true;
            }
        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
            if (userExist(id)) {
                this.logic.delete(id);
                result = true;
            }
        return result;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(this.logic.findAll());
    }

    @Override
    public User findById(int id) {
        User result = new User("", "", "");
            if (userExist(id)) {
                result = this.logic.findById(id);
            }
        return result;
    }

    @Override
    public boolean userExist(int id) {
        boolean result = false;
            if (this.logic.userExist(id)) {
                result = true;
            }
        return result;
    }
}
