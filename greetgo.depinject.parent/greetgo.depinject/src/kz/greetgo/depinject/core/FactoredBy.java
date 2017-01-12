package kz.greetgo.depinject.core;

import java.lang.annotation.*;

/**
 * Defines bean class for creating this bean
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FactoredBy {
  Class<? extends BeanFactory> value();
}
