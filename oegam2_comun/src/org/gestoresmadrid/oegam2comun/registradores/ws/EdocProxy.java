package org.gestoresmadrid.oegam2comun.registradores.ws;

public class EdocProxy implements Edoc {
  private String _endpoint = null;
  private Edoc edoc = null;
  
  public EdocProxy() {
    _initEdocProxy();
  }
  
  public EdocProxy(String endpoint) {
    _endpoint = endpoint;
    _initEdocProxy();
  }
  
  private void _initEdocProxy() {
    try {
      edoc = (new EdocServiceLocator()).getedoc();
      if (edoc != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)edoc)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)edoc)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (edoc != null)
      ((javax.xml.rpc.Stub)edoc)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public Edoc getEdoc() {
    if (edoc == null)
      _initEdocProxy();
    return edoc;
  }
  
  public byte[] enviar(byte[] envio) throws java.rmi.RemoteException{
    if (edoc == null)
      _initEdocProxy();
    return edoc.enviar(envio);
  }
  
  
}