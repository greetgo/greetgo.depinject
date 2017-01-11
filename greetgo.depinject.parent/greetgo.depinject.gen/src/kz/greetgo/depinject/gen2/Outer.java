package kz.greetgo.depinject.gen2;

public interface Outer {

  Outer str(String str);

  default Outer stn(String str) {
    str(str).nl();
    return this;
  }

  Outer nl();

  Outer tab(int tab);

}
