package io.github.wyllian.domain.validation.constraintvalidation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.github.wyllian.domain.validation.NotEmptyList;

public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List> {

    @Override
    public boolean isValid(List list, ConstraintValidatorContext context) {
        // TODO Auto-generated method stub
        return list != null && !list.isEmpty();
    }

    @Override
    public void initialize(NotEmptyList constraintAnnotation) {
        // TODO Auto-generated method stub
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

}
