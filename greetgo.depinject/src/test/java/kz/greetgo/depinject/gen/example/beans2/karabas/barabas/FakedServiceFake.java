package kz.greetgo.depinject.gen.example.beans2.karabas.barabas;

import java.util.Date;

import kz.greetgo.depinject.gen.example.remote.FakedService;
import kz.greetgo.depinject.src.Bean;
import kz.greetgo.depinject.src.gwtrpc.ClientException;

@Bean
public class FakedServiceFake implements FakedService {
  
  @Override
  public Date invoke(Integer toServer) throws ClientException {
    
    return new Date();
  }
  
}
