package kz.greetgo.depinject.ann;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines bean class for creating this bean
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface FactoredBy {
  Class<? extends BeanFactory> value();

  String qualifier() default "";

  boolean qualifierRegexp() default false;
}
