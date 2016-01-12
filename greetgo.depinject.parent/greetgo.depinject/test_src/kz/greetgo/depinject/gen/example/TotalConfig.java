package kz.greetgo.depinject.gen.example;

import kz.greetgo.depinject.gen.example.beans1.Beans1Config;
import kz.greetgo.depinject.gen.example.beans2.Beans2Config;
import kz.greetgo.depinject.src.BeanConfig;
import kz.greetgo.depinject.src.Include;

@BeanConfig
@Include({ Beans1Config.class, Beans2Config.class })
public class TotalConfig {}
