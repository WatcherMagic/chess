package dataAccess;
import dataAccess.objects.AuthToken;

import java.util.UUID;

public interface AuthDAO {

    //list of auth objects

    public AuthToken getAuth(String username);

    public AuthToken createAuth(String username);

    public AuthToken addAuth(AuthToken auth);

    public void replaceAuth(String username);

    public String generateAuth();

}
