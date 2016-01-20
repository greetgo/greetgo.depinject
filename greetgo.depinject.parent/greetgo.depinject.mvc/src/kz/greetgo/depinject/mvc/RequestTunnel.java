package kz.greetgo.depinject.mvc;

import java.io.*;

public interface RequestTunnel {

  String getTarget();

  PrintWriter getResponseWriter();

  OutputStream getResponseOutputStream();

  String[] getParamValues(String name);

  BufferedReader getRequestReader();

  InputStream getRequestInputStream();
}
