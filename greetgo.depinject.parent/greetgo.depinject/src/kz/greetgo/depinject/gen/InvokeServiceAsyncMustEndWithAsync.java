package kz.greetgo.depinject.gen;

public class InvokeServiceAsyncMustEndWithAsync extends RuntimeException {
  
  public final Class<?> fieldType;
  
  public InvokeServiceAsyncMustEndWithAsync(Class<?> fieldType) {
    super(fieldType.getSimpleName());
    this.fieldType = fieldType;
  }
  
}
