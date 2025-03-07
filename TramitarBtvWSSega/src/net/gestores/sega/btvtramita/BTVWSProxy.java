package net.gestores.sega.btvtramita;

public class BTVWSProxy implements net.gestores.sega.btvtramita.BTVWS {
  private String _endpoint = null;
  private net.gestores.sega.btvtramita.BTVWS bTVWS = null;
  
  public BTVWSProxy() {
    _initBTVWSProxy();
  }
  
  public BTVWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initBTVWSProxy();
  }
  
  private void _initBTVWSProxy() {
    try {
      bTVWS = (new net.gestores.sega.btvtramita.BTVTramitaLocator()).getBTVTramita();
      if (bTVWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)bTVWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)bTVWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (bTVWS != null)
      ((javax.xml.rpc.Stub)bTVWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public net.gestores.sega.btvtramita.BTVWS getBTVWS() {
    if (bTVWS == null)
      _initBTVWSProxy();
    return bTVWS;
  }
  
  public net.gestores.sega.btvtramita.BtvtramitaReturn tramitarBTV(net.gestores.sega.btvtramita.BtvtramitaArgument arg0) throws java.rmi.RemoteException{
    if (bTVWS == null)
      _initBTVWSProxy();
    return bTVWS.tramitarBTV(arg0);
  }
  
  
}