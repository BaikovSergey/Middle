package userstorage;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        boolean result = false;
        int id = user.getId();
        if (userExists(user)) {
            this.users.put(id, user);
            result = true;
        }
        return result;
    }

    public synchronized boolean update(User user) {
        boolean result = false;
        int id = user.getId();
        if (userExists(user)) {
            this.users.put(id, user);
            result = true;
        }
        return result;
    }

    public synchronized boolean delete(User user) {
        boolean result = false;
        int id = user.getId();
        if (userExists(user)) {
            this.users.remove(id);
            result = true;
        }
        return result;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        if (this.users.contains(fromId) && this.users.contains(toId)) {
            if (this.users.get(fromId).getAmount() >= amount) {
                int fromAmount = this.users.get(fromId).getAmount();
                int toAmount = this.users.get(fromId).getAmount();
                this.users.get(fromId).setAmount(fromAmount - amount);
                this.users.get(toAmount).setAmount(toAmount + amount);
                result = true;
            }
        }
        return result;
    }

    private synchronized boolean userExists(User user) {
        boolean result = false;
            if (this.users.contains(user)) {
                result = true;
            }
        return result;
    }
}
