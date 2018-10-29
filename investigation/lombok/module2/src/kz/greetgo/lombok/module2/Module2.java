package kz.greetgo.lombok.module2;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.lombok.module3.Module3;

@Bean
public class Module2 {

  public BeanGetter<Module3> module3;

  public void print(StringBuilder out) {
    out.append("Hi from ").append(getClass().getSimpleName()).append("\n");
    module3.get().print(out);
  }

  public String superMessage() {
    return "super message from " + getClass().getSimpleName();
  }
}
