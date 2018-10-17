package kz.greetgo.lombok.module1.beans;

import kz.greetgo.depinject.core.Bean;
import lombok.ToString;

@Bean
@ToString
public class Module1 {
  String wow = "wow value";

  public void print() {
    System.out.println("Hello from " + getClass().getSimpleName()
        + " ; wow = [[" + wow + "]]"
        + " ; toString = " + toString());
  }
}
