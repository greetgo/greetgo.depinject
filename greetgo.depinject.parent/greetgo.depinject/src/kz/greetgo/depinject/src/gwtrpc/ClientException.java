package kz.greetgo.depinject.src.gwtrpc;

public class ClientException extends RuntimeException {
  public ClientException() {}
  
  public ClientException(String message, Throwable cause) {
    super(message, cause);
  }
  
  public ClientException(String message) {
    super(message);
  }
  
  public ClientException(Throwable cause) {
    super(cause);
  }
}