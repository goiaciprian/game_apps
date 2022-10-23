package com.buildingsapi.Utils;

import org.springframework.security.crypto.password.PasswordEncoder;

public class PBKDF2Encoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence cs) {
        throw new RuntimeException("Should not encode on this service");
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
