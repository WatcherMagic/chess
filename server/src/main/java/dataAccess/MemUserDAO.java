package dataAccess;

import model.UserData;

import java.util.ArrayList;
import java.util.List;

public class MemUserDAO implements UserDAO {

    List<UserData> users;

    //make sure you don't create a new instance of this class every time it's called!
    //otherwise user data will go poof

    public MemUserDAO() {
        this.users = new ArrayList<>();
    }

    @Override
    public UserData getUser(String username) {
        int i = containsUser(username);
        if (i != -1) {
            return users.get(i);
        }
        else {
            return null;
        }
    }

    @Override
    public int containsUser(String username) {
        for (UserData user : users) {
            if (user.username().equals(username)) {
                return users.indexOf(user);
            }
        }
        return -1;
    }

    @Override
    public void addUser(UserData user) {
        users.add(user);
    }

    @Override
    public void clearData() {
        users.clear();
    }
}
