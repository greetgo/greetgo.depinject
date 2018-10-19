package kz.greetgo.depinject.testng.tests.p006_singletons_with_multi_threads.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;

import java.util.concurrent.atomic.AtomicInteger;

import static kz.greetgo.depinject.testng.tests.p006_singletons_with_multi_threads.beans.BeanConfigTest006.sleepInThread;

@Bean
public class Bean006_2 {

  public static final AtomicInteger instanceCount = new AtomicInteger(0);

  public Bean006_2() {
    sleepInThread();
    instanceCount.incrementAndGet();
  }

  public static final AtomicInteger pingCount = new AtomicInteger(0);

  public void ping() {
    sleepInThread();
    pingCount.incrementAndGet();
  }

  public BeanGetter<Bean006_1> b1;
  public BeanGetter<Bean006_3> b3;
  public BeanGetter<Bean006_4> b4;

  public void ping1a() {
    b1.get().ping1a();
  }

  public void ping1b() {
    b1.get().ping1b();
  }

  public void ping2a() {
    ping();
  }

  public void ping2b() {
    ping();
  }

  public void ping3a() {
    b4.get().ping3a();
  }

  public void ping3b() {
    b4.get().ping3b();
  }

  public void ping4a() {
    b1.get().ping4a();
  }

  public void ping4b() {
    b1.get().ping4b();
  }
}
