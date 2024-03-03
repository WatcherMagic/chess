package dataAccess;

import model.UserData;

public interface UserDAO {

    void addUser(UserData user);

    UserData getUser(String username);

    int containsUser(String username);

    void clearData();
}
