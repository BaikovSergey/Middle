package ru.job4j.crudeservlet;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryStore implements Store {

    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

    private static final MemoryStore INSTANCE = new MemoryStore();

    private MemoryStore() {

    }

    public static MemoryStore getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(User user) {
        boolean result = false;
        Integer userId = user.getId();
            if (!userExist(user)) {
                this.users.put(userId, user);
                result = true;
            }
        return result;
    }

    @Override
    public boolean update(User user) {
        boolean result = false;
        Integer userId = user.getId();
            if (userExist(user)) {
                this.users.put(userId, user);
                result = true;
            }

        return result;
    }

    @Override
    public boolean delete(User user) {
        boolean result = false;
        Integer userId = user.getId();
            if (userExist(user)) {
                this.users.remove(userId);
                result = true;
            }
        return result;
    }

    @Override
    public List<User> findAll() {
        ConcurrentHashMap<Integer, User> map = new ConcurrentHashMap<>(this.users);
        List<User> result = new ArrayList<>();
        Set<Map.Entry<Integer, User>> entrySet = map.entrySet();
        Iterator<Map.Entry<Integer, User>> itr = entrySet.iterator();
            if (this.users.size() > 0) {
                while (itr.hasNext()) {
                    Map.Entry<Integer, User> entry = itr.next();
                    result.add(entry.getValue());
                }
            }
        return result;
    }

    @Override
    public User findById(User user) {
        User result = new User(-1, "", "", "");
        Integer userId = user.getId();
            if (userExist(user)) {
                result = this.users.get(userId);
            }
        return result;
    }

    @Override
    public boolean userExist(User user) {
        Integer userId = user.getId();
        boolean result = false;
            if (this.users.containsKey(userId)) {
                result = true;
            }
        return result;
    }
}
