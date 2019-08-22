package kz.greetgo.depinject.ann.util.testing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface ExampleAnnotation {
  String name() default "Hello world";
}
