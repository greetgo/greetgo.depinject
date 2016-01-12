package kz.greetgo.depinject.src.gwtrpc;

import com.google.gwt.http.client.Request;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SyncAsyncConverterDefault implements SyncAsyncConverter {
  
  @Override
  public <ToServer, FromServer> Request convertInvoking(final ToServer toServer,
      final AsyncCallback<FromServer> callback, final InvokeService<ToServer, FromServer> sync) {
    
    final boolean pending[] = new boolean[] { true };
    
    final Timer timer = new Timer() {
      @Override
      public void run() {
        try {
          FromServer result = sync.invoke(toServer);
          pending[0] = false;
          callback.onSuccess(result);
        } catch (Exception e) {
          callback.onFailure(e);
          pending[0] = false;
        }
      }
    };
    
    timer.schedule(millisOfServerWorking());
    
    return new Request() {
      @Override
      public void cancel() {
        timer.cancel();
        pending[0] = false;
      }
      
      @Override
      public boolean isPending() {
        return pending[0];
      }
    };
  }
  
  protected int millisOfServerWorking() {
    return 100;
  }
}
