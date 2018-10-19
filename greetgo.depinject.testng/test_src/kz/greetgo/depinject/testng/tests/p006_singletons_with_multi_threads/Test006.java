package kz.greetgo.depinject.testng.tests.p006_singletons_with_multi_threads;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.AbstractDepinjectTestNg;
import kz.greetgo.depinject.testng.ContainerConfig;
import kz.greetgo.depinject.testng.tests.p006_singletons_with_multi_threads.beans.Bean006;
import kz.greetgo.depinject.testng.tests.p006_singletons_with_multi_threads.beans.Bean006_1;
import kz.greetgo.depinject.testng.tests.p006_singletons_with_multi_threads.beans.Bean006_2;
import kz.greetgo.depinject.testng.tests.p006_singletons_with_multi_threads.beans.Bean006_3;
import kz.greetgo.depinject.testng.tests.p006_singletons_with_multi_threads.beans.Bean006_4;
import kz.greetgo.depinject.testng.tests.p006_singletons_with_multi_threads.beans.BeanConfigTest006;
import org.testng.annotations.Test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.fest.assertions.api.Assertions.assertThat;

@ContainerConfig(BeanConfigTest006.class)
public class Test006 extends AbstractDepinjectTestNg {

  public BeanGetter<Bean006> bean006;

  class PingMaker extends Thread {
    final List<Ping> pingsList = new ArrayList<>();

    @Override
    public void run() {
      pingsList.forEach(bean006.get()::ping);
    }
  }

  private static final int THREAD_COUNT = 10;
  private static final int PING_PAGE_COUNT = 30;

  @Test
  public void singletons_with_multi_threads() throws InterruptedException {

    final int pingPageSize = Ping.values().length;
    Ping[] pings = new Ping[PING_PAGE_COUNT * pingPageSize];
    for (int i = 0; i < PING_PAGE_COUNT; i++) {
      for (int j = 0; j < pingPageSize; j++) {
        pings[i * pingPageSize + j] = Ping.values()[j];
      }
    }

    {
      Random random = new SecureRandom();
      for (int i = 0, L = pings.length; i < L; i++) {
        int j = random.nextInt(L);
        Ping tmp = pings[i];
        pings[i] = pings[j];
        pings[j] = tmp;
      }
    }

    PingMaker[] pingMakers = new PingMaker[THREAD_COUNT];
    for (int i = 0; i < THREAD_COUNT; i++) {
      pingMakers[i] = new PingMaker();
    }

    {
      int j = 0, maxJ = pingMakers.length - 1;
      for (Ping ping : pings) {
        pingMakers[j++].pingsList.add(ping);
        if (j > maxJ) {
          j = 0;
        }
      }
    }

    assertThat(Bean006.instanceCount.get()).isZero();
    assertThat(Bean006_1.instanceCount.get()).isZero();
    assertThat(Bean006_2.instanceCount.get()).isZero();
    assertThat(Bean006_3.instanceCount.get()).isZero();
    assertThat(Bean006_4.instanceCount.get()).isZero();

    final int expectedTotalPingCount = pings.length;

    for (PingMaker pingMaker : pingMakers) {
      pingMaker.start();
    }
    for (PingMaker pingMaker : pingMakers) {
      pingMaker.join();
    }

    assertThat(Bean006.instanceCount.get()).isEqualTo(1);
    assertThat(Bean006_1.instanceCount.get()).isEqualTo(1);
    assertThat(Bean006_2.instanceCount.get()).isEqualTo(1);
    assertThat(Bean006_3.instanceCount.get()).isEqualTo(1);
    assertThat(Bean006_4.instanceCount.get()).isEqualTo(1);

    assertThat(0

        + Bean006_1.pingCount.get()
        + Bean006_2.pingCount.get()
        + Bean006_3.pingCount.get()
        + Bean006_4.pingCount.get()

    )
        .isEqualTo(expectedTotalPingCount)

    ;


  }
}
