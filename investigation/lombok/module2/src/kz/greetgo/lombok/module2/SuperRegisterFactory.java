package kz.greetgo.lombok.module2;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import lombok.RequiredArgsConstructor;

@Bean
@RequiredArgsConstructor
public class SuperRegisterFactory {

  private final BeanGetter<Module2> module2;

  @Bean
  public SuperRegister createSuperRegister() {
    return new SuperRegister() {
      @Override
      public String superMessage() {
        return module2.get().superMessage();
      }

      @Override
      public String coolMessage() {
        return "Cool message from " + getClass().getSimpleName();
      }
    };
  }

}
