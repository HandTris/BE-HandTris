package jungle.HandTris.global.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Object[] enumValues = this.enumClass.getEnumConstants();

        if (enumValues == null) {
            return false;
        }

        for (Object enumValue : enumValues) {
            if (value.equals(enumValue.toString())) {
                return true;
            }
        }
        return false;
    }
}
