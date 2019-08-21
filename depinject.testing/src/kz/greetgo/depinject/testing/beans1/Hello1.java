package kz.greetgo.depinject.testing.beans1;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanGetter;
import kz.greetgo.depinject.testing.beans2.Hello2;

@Bean
public class Hello1 {

  public BeanGetter<Hello2> hello2;

  public void hello() {

    System.out.println("Hello from 1");

    if (this.hello2 != null) {
      Hello2 hello2 = this.hello2.get();

      if (hello2 != null) {
        hello2.hello();
      }
    }


  }

}
