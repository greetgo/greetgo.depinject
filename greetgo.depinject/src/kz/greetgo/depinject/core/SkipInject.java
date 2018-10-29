package kz.greetgo.depinject.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Marks {@link BeanGetter} which is not public, than it is OK, and no need to generate error.
 * </p>
 * <p>
 * When some BeanGetter is not public, then depinject generates error. But if you want to make BeanGetter to be
 * not public (private, or protected, or packaged), and let depinject does not generate error, you need mark
 * BeanGetter with this annotation.
 * </p>
 * <p>
 * User can forgot that BeanGetter must be public and get NullPointerException and think many time what is happening on.
 * In this case, depinject generates an error so that the user will quickly understand what the problem is.
 * </p>
 */
@Documented
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SkipInject {}
