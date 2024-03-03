package model;

import java.util.Objects;

public record AuthToken (String username, String token) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken = (AuthToken) o;
        return Objects.equals(username, authToken.username) && Objects.equals(token, authToken.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, token);
    }
}
