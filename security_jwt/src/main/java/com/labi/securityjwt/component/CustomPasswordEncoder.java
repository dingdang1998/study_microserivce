package com.labi.securityjwt.component;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @program: study_microservice
 * @description:
 * @author: dzp
 * @create: 2021-12-03 16:24
 **/
@Component
public class CustomPasswordEncoder implements PasswordEncoder {

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String encode(CharSequence rawPassword) {
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return encoder.upgradeEncoding(encodedPassword);
    }
}
