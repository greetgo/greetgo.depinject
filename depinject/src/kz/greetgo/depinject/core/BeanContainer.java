package kz.greetgo.depinject.core;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface BeanContainer {

  String implPostfix() default "AutomaticallyGeneratedImplementation";

  String implName() default "";

  String implPackage() default "";

}
