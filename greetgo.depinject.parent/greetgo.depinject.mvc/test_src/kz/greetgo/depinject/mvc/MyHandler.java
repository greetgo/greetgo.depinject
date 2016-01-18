package kz.greetgo.depinject.mvc;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyHandler extends AbstractHandler {
  @Override
  public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {

    if ("/asdasd1.html".equals(target)) {

      final PrintWriter writer = response.getWriter();
      writer.println("Hello from java code");
      response.flushBuffer();

      baseRequest.setHandled(true);
      return;
    }

  }
}
