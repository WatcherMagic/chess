package service;

import dataAccess.AuthDAO;
import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;

public class UserService extends Service {

    UserDAO userDAO;
    AuthDAO authDAO;

    public UserService(UserDAO user, AuthDAO auth) {
        super();
        this.userDAO = user;
        this.authDAO = auth;
    }

    public LoginAndRegisterResponse register(UserData newUser) {
        resetErrorCode();
        if (newUser == null || newUser.username() == null
                || newUser.password() == null || newUser.email() == null) {
            //bad request
            errorCode = 400;
            return new LoginAndRegisterResponse("Error: bad request", null, null);
        }
        else {
            if (userDAO.getUser(newUser.username()) == null) {
                userDAO.addUser(newUser);

                AuthData auth = authDAO.createAuth(newUser.username());
                authDAO.addAuth(auth);
                return new LoginAndRegisterResponse(null, newUser.username(), auth.token());
            }
            else if (userDAO.getUser(newUser.username()) != null) {
                errorCode = 403;
                return new LoginAndRegisterResponse("Error: already taken", null, null);
            }
            errorCode = 500;
            return new LoginAndRegisterResponse("Error: description", null, null);
        }
    }

    public LoginAndRegisterResponse login(UserData user) {
        resetErrorCode();
        if (userDAO.containsUser(user.username()) != -1
                && userDAO.getUser(user.username()).password().equals(user.password())) {

            AuthData newAuth = authDAO.createAuth(user.username());
            authDAO.addAuth(newAuth);
            return new LoginAndRegisterResponse(null, user.username(), newAuth.token());
        }
        else {
            errorCode = 401;
            return new LoginAndRegisterResponse("Error: unauthorized", null, null);
        }
    }

    public LoginAndRegisterResponse logout(AuthData auth) {
        resetErrorCode();
        if (auth == null || !authDAO.getAuth(auth.username()).token().equals(auth.token())) {
            //unauthorized
            errorCode = 401;
            return new LoginAndRegisterResponse("Error: unauthorized", null, null);
        }
        else {
            if (authDAO.removeAuth(auth)) { //success
                return new LoginAndRegisterResponse(null, null, null);
            }
            else {
                //error
                errorCode = 500;
                return new LoginAndRegisterResponse("Error: description", null, null);
            }
        }
    }
}
