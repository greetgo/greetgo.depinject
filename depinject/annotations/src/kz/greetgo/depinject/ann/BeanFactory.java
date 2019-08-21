package kz.greetgo.depinject.ann;

public interface BeanFactory {
  Object createBean(Class<?> beanClass) throws Exception;
}
