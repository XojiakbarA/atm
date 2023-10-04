package uz.pdp.atm.validator.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import uz.pdp.atm.validator.IsValidCurrency;

import java.util.Currency;

public class IsValidCurrencyValidator implements ConstraintValidator<IsValidCurrency, String> {
    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Currency.getInstance(string);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
