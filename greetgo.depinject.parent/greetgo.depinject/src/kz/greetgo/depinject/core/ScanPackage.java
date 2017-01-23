package kz.greetgo.depinject.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Full name of package to scan about beans
 * </p>
 * <p>
 * You can use relative package name started with point (.)
 * </p>
 * <p>
 * You can use (^) in the beginning of relative path for step to parent
 * <br>
 * (^^) - parent of parent
 * <br>
 * (^^^) - parent of parent of parent
 * <br>
 * ...
 * </p>
 * <p>
 * <p>
 * Examples:
 * <br>
 * <code>kz.greetgo.hello</code> - full package name
 * <br>
 * <code>.hello.world</code> - relative sub package name of current package
 * <br>
 * <code>^^.by.world</code> - relative package name in another package of parent of parent
 * </p>
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ScanPackage {
  String[] value();
}
