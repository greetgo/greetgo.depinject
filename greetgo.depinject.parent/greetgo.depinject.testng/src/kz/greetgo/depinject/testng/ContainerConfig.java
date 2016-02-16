package kz.greetgo.depinject.testng;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ContainerConfig {
  Class<?>[] value();

  boolean inherit() default false;
}
