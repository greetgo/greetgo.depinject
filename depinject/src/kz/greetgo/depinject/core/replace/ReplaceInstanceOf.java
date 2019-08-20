package kz.greetgo.depinject.core.replace;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Replacer will be applied to instances of specified class
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface ReplaceInstanceOf {
  /**
   * A class to detect replace instance
   */
  Class<?>[] value();
}
