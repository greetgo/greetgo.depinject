package kz.greetgo.depinject.ap.engine;

import java.io.Closeable;

public interface Outer extends Closeable {

  Outer str(String str);

  default Outer stn(String str) {
    str(str).nl();
    return this;
  }

  Outer nl();

  Outer tab(int tab);

  void close();

}
