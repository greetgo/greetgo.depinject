package kz.greetgo.depinject.mvc;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public interface RequestTunnel {

  String getTarget();

  PrintWriter getResponseWriter();

  OutputStream getResponseOutputStream();

  String[] getParamValues(String name);
}
