package com.swp.project.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

public class DobValidator implements ConstraintValidator<DobConstraint, Date> {

    private int maxAge;
    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if(date == null) {
            return false;
        }
        LocalDate dob = date.toLocalDate();
        LocalDate now = LocalDate.now();
        int age = Period.between(dob, now).getYears();

        return age >= 0 && age <= maxAge;
    }

    @Override
    public void initialize(DobConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.maxAge = constraintAnnotation.maxAge();
    }
}
