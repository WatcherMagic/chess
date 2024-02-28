package dataAccess;
import dataAccess.objects.User;

import java.util.List;

public interface UserDAO {

    //get: pulls requested user from list of users in DAO
    User getUser(String username);

    //add: inserts new user to list


    //update: changes user data


    int containsUser(String username);
}
