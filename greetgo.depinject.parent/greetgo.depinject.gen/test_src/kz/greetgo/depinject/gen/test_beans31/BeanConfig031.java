package kz.greetgo.depinject.gen.test_beans31;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.test_beans31.beans1.BeanConfig031_1;
import kz.greetgo.depinject.gen.test_beans31.beans2.BeanConfig031_2;

@BeanConfig
@Include({BeanConfig031_1.class, BeanConfig031_2.class})
public class BeanConfig031 {
}
