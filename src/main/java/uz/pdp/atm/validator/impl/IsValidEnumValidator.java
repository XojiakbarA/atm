package uz.pdp.atm.validator.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import uz.pdp.atm.validator.IsValidEnum;

import java.util.Arrays;

public class IsValidEnumValidator implements ConstraintValidator<IsValidEnum, String> {
    private Class<? extends Enum<?>> enumClazz;

    @Override
    public void initialize(IsValidEnum constraintAnnotation) {
        enumClazz = constraintAnnotation.enumClazz();
    }

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        if (string == null) return true;
        return Arrays.stream(enumClazz.getEnumConstants()).anyMatch(e -> e.name().equalsIgnoreCase(string));
    }
}
