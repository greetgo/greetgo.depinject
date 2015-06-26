package kz.greetgo.depinject.stand.src.somebeans;

import kz.greetgo.depinject.src.Bean;
import kz.greetgo.depinject.src.gwtrpc.ClientException;
import kz.greetgo.depinject.stand.remote.SomeGetService;

@Bean
public class SomeGetServiceFake implements SomeGetService {
  
  @Override
  public String invoke(String toServer) throws ClientException {
    return "OK : " + toServer;
  }
}
