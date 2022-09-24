package com.gameapi.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Component
public class PBKDF2Encoder  implements PasswordEncoder {

    @Value("${springbootwebfluxjjwt.password.encoder.secret}")
    private String secret;

    @Value("${springbootwebfluxjjwt.password.encoder.iteration}")
    private Integer iteration;

    @Value("${springbootwebfluxjjwt.password.encoder.keylength}")
    private Integer keylength;

    @Override
    public String encode(CharSequence cs) {
        try {
            byte[] bytes = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                    .generateSecret(new PBEKeySpec(cs.toString().toCharArray(), secret.getBytes(), iteration, keylength))
                    .getEncoded();

            return Base64.getEncoder().encodeToString(bytes);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
