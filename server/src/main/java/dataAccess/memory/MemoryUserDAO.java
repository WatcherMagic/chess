package dataAccess.memory;

import dataAccess.UserDAO;
import model.UserData;

import java.util.ArrayList;
import java.util.List;

public class MemoryUserDAO implements UserDAO {

    List<UserData> users;

    public MemoryUserDAO() {
        super();
        this.users = new ArrayList<>();
    }

    @Override
    public UserData getUser(String username) {
        for (UserData user : users) {
            if (user.username().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void addUser(UserData user) {
        users.add(user);
    }

    @Override
    public boolean clearData() {
        users.clear();
        if (users.size() == 0) {
            return true;
        }
        return false;
    }

}
