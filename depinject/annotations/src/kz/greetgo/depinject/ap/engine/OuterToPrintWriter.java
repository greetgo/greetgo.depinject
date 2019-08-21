package kz.greetgo.depinject.ap.engine;

import java.io.PrintWriter;

public class OuterToPrintWriter implements Outer {
  private final String tabElement;
  private final PrintWriter printWriter;

  public OuterToPrintWriter(String tabElement, PrintWriter printWriter) {
    this.tabElement = tabElement;
    this.printWriter = printWriter;
  }

  @Override
  public Outer str(String str) {
    printWriter.print(str);
    return this;
  }

  @Override
  public Outer nl() {
    printWriter.println();
    return this;
  }

  @Override
  public Outer tab(int tab) {
    for (int i = 0; i < tab; i++) {
      printWriter.print(tabElement);
    }
    return this;
  }

  @Override
  public void close() {
    printWriter.close();
  }

}
