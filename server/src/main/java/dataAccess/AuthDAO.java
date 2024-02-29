package dataAccess;
import dataAccess.objects.AuthToken;

import java.util.UUID;

public interface AuthDAO {

    public AuthToken getAuth(String username);

    public AuthToken createAuth(String username);

    public AuthToken addAuth(AuthToken auth);

    public void replaceAuth(String username);

    public void removeAuth(AuthToken auth);

    public String generateAuth();

}
