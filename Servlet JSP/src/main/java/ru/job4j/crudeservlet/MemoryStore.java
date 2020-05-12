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
    public boolean add(int id, User user) {
        boolean result = false;
            if (!userExist(id)) {
                this.users.put(id, user);
                result = true;
            }
        return result;
    }

    @Override
    public boolean update(int id, User user) {
        boolean result = false;
            if (userExist(id)) {
                this.users.put(id, user);
                result = true;
            }

        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
            if (userExist(id)) {
                this.users.remove(id);
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
    public User findById(int id) {
        User result = new User("" , "", "");
            if (userExist(id)) {
                result = this.users.get(id);
            }
        return result;
    }

    @Override
    public boolean userExist(int id) {
        boolean result = false;
            if (this.users.containsKey(id)) {
                result = true;
            }
        return result;
    }
}
