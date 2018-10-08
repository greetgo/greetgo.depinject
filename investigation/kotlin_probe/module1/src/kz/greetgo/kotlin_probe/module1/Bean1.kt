package kz.greetgo.kotlin_probe.module1

import kz.greetgo.depinject.core.Bean
import kz.greetgo.depinject.core.BeanGetter
import kz.greetgo.kotlin_probe.module2.Bean2

@Bean
class Bean1 {

  lateinit var bean2: BeanGetter<Bean2>

  fun printHelloWorld() {
    println("Hello World from " + javaClass.simpleName + " (Under Kotlin!!!)")
    bean2.get().printHelloWorld()
  }
}
