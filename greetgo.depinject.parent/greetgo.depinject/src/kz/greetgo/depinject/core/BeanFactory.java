package kz.greetgo.depinject.core;

public interface BeanFactory {
  Object createBean(Class<?> beanClass);
}
