package kz.greetgo.depinject.gen2;

import java.io.PrintStream;

public class OuterToPrintStream implements Outer {
  private final String tabElement;
  private final PrintStream out;

  public OuterToPrintStream(String tabElement, PrintStream out) {
    this.tabElement = tabElement;
    this.out = out;
  }

  @Override
  public Outer str(String str) {
    out.print(str);
    return this;
  }

  @Override
  public Outer nl() {
    out.println();
    return this;
  }

  @Override
  public Outer tab(int tab) {
    for (int i = 0; i < tab; i++) {
      str(tabElement);
    }
    return this;
  }

  @Override
  public void close() {
    out.close();
  }
}
