package kz.greetgo.depinject.testing.old.t00x.test_beans009;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanGetter;

import java.util.List;

@Bean
@SuppressWarnings("unused")
public class MainBean {

  public BeanGetter<List<Bean1>> bean1;
  public BeanGetter<List<Bean2>> bean2;
  public BeanGetter<List<Bean3>> bean3;

  public BeanGetter<Bean1impl> bean1impl;
  public BeanGetter<Bean2impl> bean2impl;
  public BeanGetter<Bean3impl> bean3impl;

}
