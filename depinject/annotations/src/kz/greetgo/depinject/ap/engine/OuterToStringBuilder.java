package kz.greetgo.depinject.ap.engine;

public class OuterToStringBuilder implements Outer {
  private final String tabElement;
  private final StringBuilder destination;

  public OuterToStringBuilder(String tabElement, StringBuilder destination) {
    this.tabElement = tabElement;
    this.destination = destination;
  }

  @Override
  public Outer str(String str) {
    destination.append(str);
    return this;
  }

  @Override
  public Outer nl() {
    destination.append("\n");
    return this;
  }

  @Override
  public Outer tab(int tab) {
    for (int i = 0; i < tab; i++) {
      destination.append(tabElement);
    }
    return this;
  }

  @Override
  public void close() {}

}
