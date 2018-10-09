package kz.greetgo.kotlin_probe.main.beans

import kz.greetgo.depinject.core.Bean
import kz.greetgo.depinject.core.BeanGetter
import kz.greetgo.kotlin_probe.module1.Bean1

@Bean
class MainBean {

  lateinit var bean1: BeanGetter<Bean1>

  fun printHelloWorld() {
    println("Hello World from " + javaClass.simpleName)
    bean1.get().printHelloWorld()
  }
}
