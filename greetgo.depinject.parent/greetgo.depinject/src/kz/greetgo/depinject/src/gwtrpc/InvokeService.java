package kz.greetgo.depinject.src.gwtrpc;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * Synchronous part of the remote service
 * 
 * @author pompei
 * 
 * @param <ToServer>
 *          Going to server object class
 * @param <FromServer>
 *          Coming from server object class
 */
public interface InvokeService<ToServer, FromServer> extends RemoteService {
  FromServer invoke(ToServer toServer) throws ClientException;
}
