package com.itp.auth;

import java.security.SecureRandom;
import java.util.Base64;

public class Token {

    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    public static String token;

    public Token() {
        String token = Token.generateNewToken();
        this.token = token ;
    }

    public String getToken() {
        return token;
    }


    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }


}
