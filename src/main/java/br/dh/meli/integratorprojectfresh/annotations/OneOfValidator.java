package br.dh.meli.integratorprojectfresh.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The type One of validator.
 */
public class OneOfValidator implements ConstraintValidator<OneOf, String> {

    private String[] values;

    @Override
    public void initialize(OneOf constraintAnnotation) {
        System.out.println("*** OneOfValidator.initialize ***");
        this.values = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        System.out.println("*** OneOfValidator.isValid ***" + value);
        if (value == null) {
            return true;
        }
        for (String v : values) {
            if (v.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
