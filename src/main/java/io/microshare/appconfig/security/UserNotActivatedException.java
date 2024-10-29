package io.microshare.appconfig.security;

import jakarta.ws.rs.NotAuthorizedException;

public class UserNotActivatedException extends NotAuthorizedException {

    public UserNotActivatedException(String message) {
        super(message);
    }
}
