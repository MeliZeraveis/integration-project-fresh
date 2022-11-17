package br.dh.meli.integratorprojectfresh.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * EnumNamePattern
 * Validates that the enum value matches the given regular expression.
 *
 * @author Marcello Pires Alves <marcello.alves@mercadolivre.com>
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = EnumNamePatternValidator.class)
public @interface EnumNamePattern {
  String regexp();
  String message() default "Enum value must match the pattern \"{regexp}\".";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
