package kz.greetgo.kotlin_probe.main.launcher

import kz.greetgo.depinject.core.BeanContainer
import kz.greetgo.depinject.core.Include
import kz.greetgo.kotlin_probe.main.beans.BeanConfigMain
import kz.greetgo.kotlin_probe.main.beans.MainBean

@Include(BeanConfigMain::class)
interface BeanContainerMain : BeanContainer {
  fun main(): MainBean
}
