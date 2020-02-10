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
    public boolean add(User user) {
        boolean result = false;
            if (!userExist(user)) {
                this.logic.add(user);
                result = true;
            }
        return result;
    }

    @Override
    public boolean update(User user) {
        boolean result = false;
            if (userExist(user)) {
                this.logic.update(user);
                result = true;
            }
        return result;
    }

    @Override
    public boolean delete(User user) {
        boolean result = false;
            if (userExist(user)) {
                this.logic.delete(user);
                result = true;
            }
        return result;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(this.logic.findAll());
    }

    @Override
    public User findById(User user) {
        User result = new User(-1, "", "", "");
            if (userExist(user)) {
                result = this.logic.findById(user);
            }
        return result;
    }

    private boolean userExist(User user) {
        boolean result = false;
            if (this.logic.userExist(user)) {
                result = true;
            }
        return result;
    }
}
