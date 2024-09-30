package com.example.taskmanage.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class NameValidator implements ConstraintValidator<NameConstraint, String> {

    private int min;

    @Override
    public void initialize(NameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

//        check null in other validator
        if (Objects.isNull(value))
            return true;

        return value.trim().length() >= min;
    }
}
