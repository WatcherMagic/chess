package service;

import dataAccess.AuthDAO;
import dataAccess.MemAuthDAO;
import dataAccess.MemUserDAO;
import dataAccess.UserDAO;
import model.AuthToken;
import model.User;

public class UserService {

        UserDAO userDAO;
        AuthDAO authDAO;

        int errorCode = 0;

        public UserService() {
                userDAO = new MemUserDAO();
                authDAO = new MemAuthDAO();
            }

        public UserService(UserDAO user, AuthDAO auth) {
            this.userDAO = user;
            this.authDAO = auth;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public LoginAndRegisterResponse register(User newUser) {
            errorCode = 0;
            if (newUser != null) {
                if (newUser.username() == null || newUser.password() == null || newUser.email() == null) {
                    //bad request
                    errorCode = 400;
                    return new LoginAndRegisterResponse("Error: bad request", null, null);
                }
                else {
                    if (userDAO.getUser(newUser.username()) == null) {
                        userDAO.addUser(newUser);

                        AuthToken auth = authDAO.createAuth(newUser.username());
                        authDAO.addAuth(auth);
                        return new LoginAndRegisterResponse(null, newUser.username(), auth.token());
                    }
                    else if (userDAO.getUser(newUser.username()) != null) {
                        errorCode = 403;
                        return new LoginAndRegisterResponse("Error: already taken", null, null);
                    }
                    else {
                        errorCode = 500;
                        return new LoginAndRegisterResponse("Error: description", null, null);
                    }
                }
            }
            else {
                //bad request
                errorCode = 400;
                return new LoginAndRegisterResponse("Error: bad request", null, null);
            }

        }

        private User createUser(String username, String password, String email) {
            return new User(username, password, email);
        }

        public LoginAndRegisterResponse login(User user) {
            errorCode = 0;
            if (userDAO.containsUser(user.username()) != -1) {
                if (userDAO.getUser(user.username()).password().equals(user.password())) {
                    AuthToken newAuth = authDAO.createAuth(user.username());
                    authDAO.addAuth(newAuth);
                    return new LoginAndRegisterResponse(null, user.username(), newAuth.token()); //*****
                }
                else { //wrong password
                    errorCode = 401;
                    return new LoginAndRegisterResponse("Error: unauthorized", null, null);
                }
            }
            else {
                errorCode = 401;
                return new LoginAndRegisterResponse("Error: unauthorized", null, null);
            }
        }

        public LoginAndRegisterResponse logout(AuthToken auth) {
            errorCode = 0;
            if (auth == null) {
                //unauthorized
                errorCode = 401;
                return new LoginAndRegisterResponse("Error: unauthorized", null, null);
            }
            else if (authDAO.getAuth(auth.username()) != null) {
                if (authDAO.getAuth(auth.username()).token().equals(auth.token())) {
                    //success
                    authDAO.removeAuth(auth);
                    return new LoginAndRegisterResponse(null, null, null);
                }
                //unauthorized
                errorCode = 401;
                return new LoginAndRegisterResponse("Error: unauthorized", null, null);
            }
            else {
                //error
                errorCode = 500;
                return new LoginAndRegisterResponse("Error: description", null, null);
            }

        }
}
