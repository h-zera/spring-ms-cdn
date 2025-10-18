package com.hzera.spring.ms.cdn.domain.entity.cdn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CdnDefaultConfigEntity {
    private String param;
    private String value;
    private String altValue;

    public enum Param {
        EXPIRES,
        ALLOWED_COUNTRIES,
        BLOCKED_COUNTRIES,
        REMOTE_IP;

        public static Optional<Param> fromParam(String param) {
            return switch (param.toLowerCase()) {
                case "expires" -> Optional.of(Param.EXPIRES);
                case "allowed_countries" -> Optional.of(Param.ALLOWED_COUNTRIES);
                case "blocked_countries" -> Optional.of(Param.BLOCKED_COUNTRIES);
                case "remote_ip" -> Optional.of(Param.REMOTE_IP);
                default -> Optional.empty();
            };
        }
    }
}
