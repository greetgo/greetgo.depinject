package kz.greetgo.depinject;

public interface BeanReplacer {
  Object replaceBean(Object originalBean, Class<?> returnClass);
}
