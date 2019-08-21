package kz.greetgo.depinject;

public interface BeanFactory {
  Object createBean(Class<?> beanClass) throws Exception;
}
