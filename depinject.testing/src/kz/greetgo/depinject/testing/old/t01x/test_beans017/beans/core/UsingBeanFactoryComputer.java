package kz.greetgo.depinject.testing.old.t01x.test_beans017.beans.core;

import kz.greetgo.depinject.ann.Bean;

@Bean(singleton = false)
public interface UsingBeanFactoryComputer {
  void turnOn();
}
