package kz.greetgo.depinject.gwt.stand.src;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

public class DepinjectStandEP implements EntryPoint {
  @Override
  public void onModuleLoad() {
    
    TestBeanContainer tbc = GWT.create(TestBeanContainer.class);
    
    AsdInterface asdInterface = tbc.getAsd();
    Window.alert("asdInterface = " + asdInterface);
    
    tbc.getAsd().showHelloWorld();

  }
}
