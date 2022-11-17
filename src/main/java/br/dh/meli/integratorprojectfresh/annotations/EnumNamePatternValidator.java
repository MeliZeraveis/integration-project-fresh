package br.dh.meli.integratorprojectfresh.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * EnumNamePatternValidator
 * Validates that the enum value matches the given regular expression.
 *
 * @author Marcello Pires Alves <marcello.alves@mercadolivre.com>
 */
public class EnumNamePatternValidator implements ConstraintValidator<EnumNamePattern, Enum<?>> {
  private Pattern pattern;

  /**
   * initialize
   * Initializes the validator in preparation for {@link #isValid(Enum, ConstraintValidatorContext)} calls.
   * @param annotation
   * @throws IllegalArgumentException if the regular expression is invalid
   */
  @Override
  public void initialize(EnumNamePattern annotation) {
    try {
      pattern = Pattern.compile(annotation.regexp());
    } catch (PatternSyntaxException err) {
      throw new IllegalArgumentException("Given regular expression is invalid", err);
    }
  }

  /**
   * isValid
   * Checks if the enum value matches the given regular expression.
   * @param value
   * @param context
   * @return true if the enum value is null, the match for the given regular expression otherwise
   */
  @Override
  public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    Matcher m = pattern.matcher(value.name());
    return m.matches();
  }
}