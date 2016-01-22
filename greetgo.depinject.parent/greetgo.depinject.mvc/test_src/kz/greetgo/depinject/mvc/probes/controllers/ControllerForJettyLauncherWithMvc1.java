package kz.greetgo.depinject.mvc.probes.controllers;

import kz.greetgo.depinject.mvc.Mapping;
import kz.greetgo.depinject.mvc.Redirect;

@Mapping("/lot")
@SuppressWarnings("unused")
public class ControllerForJettyLauncherWithMvc1 {

  @Mapping("/save")
  public Redirect save() {
    return Redirect.to("after_post.html");
  }

}
