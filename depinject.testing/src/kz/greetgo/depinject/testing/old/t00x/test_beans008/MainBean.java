package kz.greetgo.depinject.testing.old.t00x.test_beans008;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanGetter;

import java.util.List;

@Bean
@SuppressWarnings("unused")
public class MainBean {

  public BeanGetter<List<Bean1>> bean1;
  public BeanGetter<Bean2> bean2;

  public BeanGetter<Bean1impl> bean1impl;
  public BeanGetter<Bean2impl> bean2impl;

}
