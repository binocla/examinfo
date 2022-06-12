package com.orioninc.validation;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Singleton;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Singleton
public class UrlValidation implements ConstraintValidator<URL, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.containsAnyIgnoreCase(value, "http", "ws", "gc", "tcp");
    }
}
