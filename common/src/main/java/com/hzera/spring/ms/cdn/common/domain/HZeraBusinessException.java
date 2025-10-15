package com.hzera.spring.ms.cdn.common.domain;

import com.hzera.spring.ms.cdn.common.utils.Ansi;
import lombok.Getter;

@Getter
public class HZeraBusinessException extends RuntimeException {
    private final String errorCode;

    public HZeraBusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;

        String formatted = String.format(
                "%s%s[HZera PROJECT]%s - Exception was thrown with <Error code: %s%s%s> and the following message:\n> %s",
                Ansi.RED, Ansi.BOLD, Ansi.RESET,
                Ansi.RED, errorCode, Ansi.RESET,
                message
        );
        System.err.println(Ansi.RED + Ansi.BOLD + formatted + Ansi.RESET);

        super.setStackTrace(new StackTraceElement[0]);
    }
}
