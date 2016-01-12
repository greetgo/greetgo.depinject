package kz.greetgo.depinject.src.gwtrpc;

import com.google.gwt.http.client.Request;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Asynchronous part of the remote service
 * 
 * @author pompei
 * 
 * @param <ToServer>
 *          Going to server object class
 * @param <FromServer>
 *          Coming from server object class
 */
public interface InvokeServiceAsync<ToServer, FromServer> {
  Request invoke(ToServer toServer, AsyncCallback<FromServer> callback);
}
