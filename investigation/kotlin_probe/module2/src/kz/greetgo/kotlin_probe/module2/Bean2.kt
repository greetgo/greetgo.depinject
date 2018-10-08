package kz.greetgo.kotlin_probe.module2

import kz.greetgo.depinject.core.Bean

@Bean
class Bean2 {
  fun printHelloWorld() {
    println("Hello World from " + javaClass.simpleName + " (Under Kotlin!!!)")
  }
}
