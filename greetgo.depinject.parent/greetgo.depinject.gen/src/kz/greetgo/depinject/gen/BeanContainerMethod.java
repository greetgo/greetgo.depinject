package kz.greetgo.depinject.gen;

import java.lang.reflect.Type;

public class BeanContainerMethod implements Comparable<BeanContainerMethod> {
  public final String name;
  public final Type referenceType;
  public final BeanDefinition beanDefinition;


  public BeanContainerMethod(String name, Type referenceType, BeanDefinition beanDefinition) {
    this.name = name;
    this.referenceType = referenceType;
    this.beanDefinition = beanDefinition;
  }

  @Override
  public int compareTo(@SuppressWarnings("NullableProblems") BeanContainerMethod o) {
    return name.compareTo(o.name);
  }
}
