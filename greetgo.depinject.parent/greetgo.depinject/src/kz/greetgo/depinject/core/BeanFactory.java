package kz.greetgo.depinject.core;

@SuppressWarnings("unused")
public interface BeanFactory {
  Object createBean(Class<?> beanClass);
}
