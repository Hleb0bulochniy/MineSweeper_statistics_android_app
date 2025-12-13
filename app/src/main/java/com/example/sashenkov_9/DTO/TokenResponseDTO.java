package com.example.sashenkov_9.DTO;

import java.io.Serializable;

public class TokenResponseDTO implements Serializable {

    private String access_token;
    private String refresh_token;
    private String username;

    public TokenResponseDTO() {
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getUsername() {
        return username;
    }
}
