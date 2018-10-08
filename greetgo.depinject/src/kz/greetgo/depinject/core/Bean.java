package kz.greetgo.depinject.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines bean class. This class will be instantiated by some method
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {
  /**
   * Defined singleton behavior
   *
   * @return <code>true</code> - singleton (default), <code>false</code> - not singleton
   */
  boolean singleton() default true;
}
