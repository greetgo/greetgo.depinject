package kz.greetgo.depinject.gwt.gen.example.beans2.karabas.barabas;

import java.util.Date;
import java.util.List;
import java.util.Map;

import kz.greetgo.depinject.gwt.gen.example.remote.FakedService;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.gwt.src.ClientException;

@Bean
public class FakedServiceFake implements FakedService {
  
  @Override
  public Date invoke(Map<String, List<Long>> toServer) throws ClientException {
    
    return new Date();
  }
  
}
