package kz.greetgo.depinject.gwt.gen;

public class NoMatchingBeanFor extends RuntimeException {
  
  public final Class<?> fieldType;
  
  public NoMatchingBeanFor(Class<?> fieldType) {
    super(fieldType.getName());
    this.fieldType = fieldType;
  }
  
}
