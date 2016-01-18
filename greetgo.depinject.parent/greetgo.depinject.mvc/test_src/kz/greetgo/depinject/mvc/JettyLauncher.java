package kz.greetgo.depinject.mvc;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyLauncher {
  public static void main(String[] args) throws Exception {

    Server server = new Server(8080);

    ResourceHandler resourceHandler = new ResourceHandler();
    resourceHandler.setDirectoriesListed(true);
    resourceHandler.setWelcomeFiles(new String[]{"references.html"});
    resourceHandler.setResourceBase("/home/pompei/var");

    ResourceHandler resourceHandlerTmp = new ResourceHandler();
    resourceHandlerTmp.setDirectoriesListed(true);
    resourceHandlerTmp.setWelcomeFiles(new String[]{"references.html"});
    resourceHandlerTmp.setResourceBase("/home/pompei/tmp/resources");

    WebAppContext appContext = new WebAppContext();
    appContext.setContextPath("/");
    appContext.setWar("/home/pompei/tmp/war");

    HandlerList handlerList = new HandlerList();
    handlerList.addHandler(new MyHandler());
    handlerList.addHandler(resourceHandler);
    handlerList.addHandler(resourceHandlerTmp);
    handlerList.addHandler(appContext);

    server.setHandler(handlerList);

    server.start();
    server.join();

    System.out.println("Hello world!!!");
  }
}
