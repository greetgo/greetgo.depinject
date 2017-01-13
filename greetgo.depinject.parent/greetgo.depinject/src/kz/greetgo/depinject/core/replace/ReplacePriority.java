package kz.greetgo.depinject.core.replace;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Priority of applying of BeanReplacer.
 * </p>
 * <p>
 * If this annotation is absent, then priority is zero.
 * </p>
 * <p>
 * Less priority is using first. More priority is wrap previous.
 * </p>
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReplacePriority {
  /**
   * Double value of priority
   *
   * @return the priority of applying of BeanReplacer
   */
  double value();
}
