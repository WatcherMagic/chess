package dataAccess;
import dataAccess.objects.User;

import java.util.List;

public interface UserDAO {

    void addUser(User user);

    User getUser(String username);

    int containsUser(String username);

    void clearData();
}
