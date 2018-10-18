package kz.greetgo.depinject.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Hides constructor from depinject
 * </p>
 * <p>
 * If you do not want depinject used any constructor to create bean, then mark the constructor by this annotation
 * </p>
 */
@Documented
@Target({ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface HideFromDepinject {}
