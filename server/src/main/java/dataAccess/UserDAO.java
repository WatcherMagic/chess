package dataAccess;

import model.UserData;

import java.util.List;

public interface UserDAO {

    void addUser(UserData user);

    UserData getUser(String username);

    int containsUser(String username);

    boolean clearData();
}
