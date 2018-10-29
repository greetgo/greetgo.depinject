package kz.greetgo.lombok.module1.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.lombok.module2.Module2;
import kz.greetgo.lombok.module2.SuperRegister;
import kz.greetgo.lombok.module3.Module3;
import lombok.RequiredArgsConstructor;

@Bean
@RequiredArgsConstructor
public class Module1 {

  private final BeanGetter<Module2> module2;
  private final BeanGetter<SuperRegister> superRegister;

  public BeanGetter<Module3> module3;

  public void print(StringBuilder out) {
    out.append("Hello from ").append(getClass().getSimpleName()).append("\n");
    module2.get().print(out);

    String messageFromModule3 = module3.get().takeMessage();
    out.append("messageFromModule3 = ").append(messageFromModule3).append("\n");

    String coolMessage = superRegister.get().coolMessage();
    String superMessage = superRegister.get().superMessage();

    out.append("coolMessage  = ").append(coolMessage).append("\n");
    out.append("superMessage = ").append(superMessage).append("\n");
  }
}
