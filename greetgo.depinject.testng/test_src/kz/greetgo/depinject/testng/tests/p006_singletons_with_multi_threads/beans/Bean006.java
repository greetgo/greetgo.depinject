package kz.greetgo.depinject.testng.tests.p006_singletons_with_multi_threads.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.tests.p006_singletons_with_multi_threads.Ping;

import java.util.concurrent.atomic.AtomicInteger;

@Bean
public class Bean006 {

  public static final AtomicInteger instanceCount = new AtomicInteger(0);

  public BeanGetter<Bean006_1> b1;
  public BeanGetter<Bean006_2> b2;
  public BeanGetter<Bean006_3> b3;
  public BeanGetter<Bean006_4> b4;

  public Bean006() {
    instanceCount.incrementAndGet();
  }

  public void ping1a() {
    b3.get().ping1a();
  }

  public void ping1b() {
    b4.get().ping1b();
  }

  public void ping2a() {
    b3.get().ping2a();
  }

  public void ping2b() {
    b3.get().ping2b();
  }

  public void ping3a() {
    b2.get().ping3a();
  }

  public void ping3b() {
    b1.get().ping3b();
  }

  public void ping4a() {
    b2.get().ping4a();
  }

  public void ping4b() {
    b3.get().ping4b();
  }

  public void ping(Ping ping) {
    switch (ping) {
      case PING1A:
        ping1a();
        return;

      case PING1B:
        ping1b();
        return;

      case PING2A:
        ping2a();
        return;

      case PING2B:
        ping2b();
        return;

      case PING3A:
        ping3a();
        return;

      case PING3B:
        ping3b();
        return;

      case PING4A:
        ping4a();
        return;

      case PING4B:
        ping4b();
        return;
    }
  }
}
