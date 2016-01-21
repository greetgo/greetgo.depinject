package kz.greetgo.depinject.mvc;

import org.eclipse.jetty.server.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class JettyRequestTunnel implements RequestTunnel {

  private final String target;
  @SuppressWarnings({"FieldCanBeLocal", "unused"})
  private final Request baseRequest;
  private final HttpServletRequest request;
  private final HttpServletResponse response;

  public JettyRequestTunnel(String target, Request baseRequest,
                            HttpServletRequest request, HttpServletResponse response) {
    this.target = target;
    this.baseRequest = baseRequest;
    this.request = request;
    this.response = response;
  }

  @Override
  public String getTarget() {
    return target;
  }

  @Override
  public PrintWriter getResponseWriter() {
    try {
      return response.getWriter();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public OutputStream getResponseOutputStream() {
    try {
      return response.getOutputStream();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public InputStream getRequestInputStream() {
    try {
      return request.getInputStream();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String[] getParamValues(String name) {
    return request.getParameterValues(name);
  }

  @Override
  public BufferedReader getRequestReader() {
    try {
      return request.getReader();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
