package kz.greetgo.kotlin_probe.main.launcher

import kz.greetgo.depinject.Depinject
import kz.greetgo.kotlin_probe.main.beans.MainBean

class MainLauncher {

  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      MainLauncher().launch()
    }
  }

  private fun launch() {
    val beanContainer = Depinject.newInstance(BeanContainerMain::class.java)

    val mainBean = beanContainer.main()

    mainBean.printHelloWorld()
  }
}
