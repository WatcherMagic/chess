package server;

import dataAccess.AuthDAO;
import dataAccess.MemAuthDAO;
import dataAccess.MemUserDAO;
import dataAccess.UserDAO;
import dataAccess.objects.AuthToken;
import dataAccess.objects.User;

public class UserService {

        UserDAO userDAO;
        AuthDAO authDAO;

        public UserService() {
            userDAO = new MemUserDAO();
            authDAO = new MemAuthDAO();
        }

        public UserService(UserDAO user, AuthDAO auth) {
            this.userDAO = user;
            this.authDAO = auth;
        }

        public void register(String username, String password, String email) {
            if (userDAO.getUser(username) == null) {
                userDAO.addUser(createUser(username, password, email));

                AuthToken auth = authDAO.createAuth(username);
                authDAO.addAuth(auth);
                //return
            }
            //return
        }

        private User createUser(String username, String password, String email) {
            return new User(username, password, email);
        }

        public void login(String username, String password) {
            User in = userDAO.getUser(username);
            if (in.password() == password) {
                authDAO.replaceAuth(username);
                //return
            }
            //return
        }

        public void logout(AuthToken auth) {
            if (authDAO.getAuth(auth.username()) != null) {
                authDAO.removeAuth(auth);
                //return
            }
            //return
        }
}
