package kz.greetgo.depinject.mvc;

import kz.greetgo.depinject.mvc.error.NoPathParam;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CatchResult {
  public boolean ok() {
    return false;
  }

  private final Map<String, String> params = new HashMap<>();

  public void setParam(String name, String value) {
    params.put(name, value);
  }

  public String getParam(String name) {
    if (params.containsKey(name)) return params.get(name);
    throw new NoPathParam(name, Collections.unmodifiableMap(params));
  }
}
