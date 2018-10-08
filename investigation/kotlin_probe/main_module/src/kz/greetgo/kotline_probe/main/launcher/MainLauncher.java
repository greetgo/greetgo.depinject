package kz.greetgo.kotline_probe.main.launcher;

import kz.greetgo.depinject.Depinject;
import kz.greetgo.kotline_probe.main.beans.MainBean;

public class MainLauncher {
  public static void main(String[] args) {
    new MainLauncher().launch();
  }

  private void launch() {
    BeanContainerMain beanContainer = Depinject.newInstance(BeanContainerMain.class);

    MainBean mainBean = beanContainer.main();

    mainBean.printHelloWorld();
  }
}
