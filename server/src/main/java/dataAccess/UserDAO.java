package dataAccess;

import model.User;

public interface UserDAO {

    void addUser(User user);

    User getUser(String username);

    int containsUser(String username);

    void clearData();
}
