package kz.greetgo.depinject.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Priority of applying of BeanPreparation.
 * </p>
 * <p>
 * If this annotation is absent, then priority is zero.
 * </p>
 * <p>
 * Less priority is using first. More priority is wrap previous.
 * </p>
 */
@Deprecated
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanPreparationPriority {
  /**
   * Double value of priority
   *
   * @return the priority of applying of BeanPreparation
   */
  double value();
}
