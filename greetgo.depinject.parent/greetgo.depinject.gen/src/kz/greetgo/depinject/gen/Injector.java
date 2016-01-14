package kz.greetgo.depinject.gen;

import java.lang.reflect.Field;

public class Injector implements Comparable<Injector> {
  public final BeanDefinition source;
  public final Field field;
  public final BeanDefinition to;

  public Injector(BeanDefinition source, Field field, BeanDefinition to) {
    this.source = source;
    this.field = field;
    this.to = to;
  }

  @Override
  public int compareTo(Injector o) {
    return field.getName().compareTo(o.field.getName());
  }
}
