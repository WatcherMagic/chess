package dataAccess.objects;

import java.util.Objects;

public record User(String username, String password, String email) {}
