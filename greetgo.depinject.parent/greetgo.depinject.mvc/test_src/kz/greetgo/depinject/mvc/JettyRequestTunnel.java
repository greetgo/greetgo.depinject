package kz.greetgo.depinject.mvc;

import org.eclipse.jetty.server.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

public class JettyRequestTunnel implements RequestTunnel {

  private final String target;
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
  public PrintWriter getResponseWriter()  {
    try {
      return response.getWriter();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public OutputStream getResponseOutputStream()  {
    try {
      return response.getOutputStream();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String[] getParamValues(String name) {
    return request.getParameterValues(name);
  }

}
