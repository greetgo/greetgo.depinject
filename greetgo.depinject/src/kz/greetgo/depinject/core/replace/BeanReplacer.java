package kz.greetgo.depinject.core.replace;

public interface BeanReplacer {
  Object replaceBean(Object originalBean, Class<?> returnClass);
}
