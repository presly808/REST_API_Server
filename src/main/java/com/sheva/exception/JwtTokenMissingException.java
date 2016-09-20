package com.sheva.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by vlad on 19.09.2016.
 */
public class JwtTokenMissingException extends AuthenticationException {
    public JwtTokenMissingException(String msg) {
        super(msg);
    }
}
