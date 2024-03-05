package dataAccess;

import model.UserData;

public class SQLUserDAO implements UserDAO {
    @Override
    public void addUser(UserData user) {

    }

    @Override
    public UserData getUser(String username) {
        return null;
    }

    @Override
    public int containsUser(String username) {
        return 0;
    }

    @Override
    public boolean clearData() {
        return false;
    }
}
