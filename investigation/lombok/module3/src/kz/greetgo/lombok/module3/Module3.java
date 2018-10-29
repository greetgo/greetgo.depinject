package kz.greetgo.lombok.module3;

import kz.greetgo.depinject.core.Bean;

@Bean
public class Module3 {
  public void print(StringBuilder out) {
    //noinspection SpellCheckingInspection
    out.append("Priuvet from ").append(getClass().getSimpleName()).append("\n");
  }

  public String takeMessage() {
    return "Message from " + getClass().getSimpleName();
  }
}
