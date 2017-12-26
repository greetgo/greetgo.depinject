package kz.greetgo.depinject.core;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanConfig {
  Class<? extends BeanFactory> defaultFactoryClass() default BeanFactory.class;
}
