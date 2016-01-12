package kz.greetgo.depinject.gwt.stand.src.somebeans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.gwt.stand.remote.SomeGetService;
import kz.greetgo.depinject.gwt.src.ClientException;

@Bean
public class SomeGetServiceFake implements SomeGetService {
  @Override
  public String invoke(String toServer) throws ClientException {
    return "OK : " + toServer;
  }
}
