package kz.greetgo.depinject.core.replace;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Replacer will be applied to instances of classes, witch has specified annotation in declaration
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReplaceWithAnn {
  /**
   * An annotation to detect replace instance
   */
  Class<? extends Annotation> value();
}
