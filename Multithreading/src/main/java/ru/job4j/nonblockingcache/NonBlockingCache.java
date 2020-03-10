package ru.job4j.nonblockingcache;

import java.util.concurrent.ConcurrentHashMap;


public class NonBlockingCache {


    private final ConcurrentHashMap<Integer, Base> cache = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        boolean result = false;
        int id = model.getId();
            if (!modelExist(model)) {
                this.cache.put(id, model);
                result = true;
            }
        return result;
    }

    public boolean update(Base model) {
        boolean result = false;
        int id = model.getId();
        int version = model.getVersion();
        if (modelExist(model)) {
            if (this.cache.get(id).getVersion() == version) {
                model.setVersion(version + 1);
                this.cache.put(id, model);
                result = true;
            } else {
                throw new OptimisticException("Model was modified");
            }
        }
        return result;
    }

    public boolean delete(Base model) {
        boolean result = false;
        int id = model.getId();
        if (modelExist(model)) {
            this.cache.remove(id);
            result = true;
        }
        return result;
    }

    public boolean modelExist(Base model) {
        boolean result = false;
        int id = model.getId();
            if (this.cache.containsKey(id)) {
                result = true;
            }
        return result;
    }
}
