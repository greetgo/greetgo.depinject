package kz.greetgo.depinject.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Selects bean by id
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface Qualifier {
  /**
   * Regular expression or string qualifies bean identifier to select bean from several beans.
   *
   * @return selecting bean identifier
   */
  String value();

  /**
   * If true then value is regexp, otherwise - value is string
   *
   * @return sign of using value as regular expression
   */
  boolean regexp() default false;
}
