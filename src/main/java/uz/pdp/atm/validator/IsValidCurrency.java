package uz.pdp.atm.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import uz.pdp.atm.validator.impl.IsValidCurrencyValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsValidCurrencyValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsValidCurrency {
    String message() default "currency must be a currency code (RUB, UZS, USD...)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
