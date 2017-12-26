package kz.greetgo.depinject.gwt.gen.example;

import kz.greetgo.depinject.gwt.gen.example.beans1.Beans1Config;
import kz.greetgo.depinject.gwt.gen.example.beans2.Beans2Config;
import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.Include;

@BeanConfig
@Include({ Beans1Config.class, Beans2Config.class })
public class TotalConfig {}
