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
@Target({ElementType.FIELD, ElementType.PARAMETER,})
@Retention(RetentionPolicy.RUNTIME)
public @interface Qualifier {
  /**
   * Regular expression qualifies bean identifier to select bean from several beans.
   *
   * @return selecting bean identifier
   */
  String value();
}
