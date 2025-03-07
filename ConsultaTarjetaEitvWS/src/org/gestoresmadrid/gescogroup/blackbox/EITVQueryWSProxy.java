package org.gestoresmadrid.gescogroup.blackbox;

public class EITVQueryWSProxy implements org.gestoresmadrid.gescogroup.blackbox.EITVQueryWS {
  private String _endpoint = null;
  private org.gestoresmadrid.gescogroup.blackbox.EITVQueryWS eITVQueryWS = null;
  
  public EITVQueryWSProxy() {
    _initEITVQueryWSProxy();
  }
  
  public EITVQueryWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initEITVQueryWSProxy();
  }
  
  private void _initEITVQueryWSProxy() {
    try {
      eITVQueryWS = (new org.gestoresmadrid.gescogroup.blackbox.EITVQueryServiceLocator()).getEITVQueryService();
      if (eITVQueryWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)eITVQueryWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)eITVQueryWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (eITVQueryWS != null)
      ((javax.xml.rpc.Stub)eITVQueryWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.gestoresmadrid.gescogroup.blackbox.EITVQueryWS getEITVQueryWS() {
    if (eITVQueryWS == null)
      _initEITVQueryWSProxy();
    return eITVQueryWS;
  }
  
  public org.gestoresmadrid.gescogroup.blackbox.EitvQueryWSResponse getDataEITV(org.gestoresmadrid.gescogroup.blackbox.EitvQueryWSRequest arg0) throws java.rmi.RemoteException{
    if (eITVQueryWS == null)
      _initEITVQueryWSProxy();
    return eITVQueryWS.getDataEITV(arg0);
  }
  
  
}