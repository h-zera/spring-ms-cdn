package com.hzera.spring.ms.cdn.driver.api.rest.v1.providers;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class CryptProvider {
    public boolean match(String raw, String hash) {
        return BCrypt.checkpw(raw, hash);
    }

    public String hash(String raw) {
        return BCrypt.hashpw(raw, BCrypt.gensalt());
    }
}
