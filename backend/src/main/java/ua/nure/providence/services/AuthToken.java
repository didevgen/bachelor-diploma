package ua.nure.providence.services;

public class AuthToken {
    private String tokenValue;
    private String userUUID;

    public AuthToken(String tokenValue, String userUUID) {
        this.tokenValue = tokenValue;
        this.userUUID = userUUID;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public String getUserUUID() {
        return userUUID;
    }
}

