package server;

import dataAccess.MemAuthDAO;
import dataAccess.MemUserDAO;
import dataAccess.objects.AuthToken;
import dataAccess.objects.User;

public class RegistrationService {

        MemUserDAO userDAO;
        MemAuthDAO authDAO;

        RegistrationService(MemUserDAO userDAO, MemAuthDAO authDAO) {
            this.userDAO = userDAO;
            this.authDAO = authDAO;
        }

        public Response register(String username, String password, String email) {
            if (userDAO.getUser(username) == null) {
                userDAO.addUser(createUser(username, password, email));

                AuthToken auth = authDAO.createAuth(username);
                authDAO.addAuth(auth);
                return new Response(200);
            }
            return new Response(500);
        }

        private User createUser(String username, String password, String email) {
            return new User(username, password, email);
        }

}
