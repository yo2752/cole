package com.gescogroup.blackbox;

public class EITVQueryWSProxy implements com.gescogroup.blackbox.EITVQueryWS {
  private String _endpoint = null;
  private com.gescogroup.blackbox.EITVQueryWS eITVQueryWS = null;
  
  public EITVQueryWSProxy() {
    _initEITVQueryWSProxy();
  }
  
  public EITVQueryWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initEITVQueryWSProxy();
  }
  
  private void _initEITVQueryWSProxy() {
    try {
      eITVQueryWS = (new com.gescogroup.blackbox.EITVQueryServiceLocator()).getEITVQueryService();
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
  
  public com.gescogroup.blackbox.EITVQueryWS getEITVQueryWS() {
    if (eITVQueryWS == null)
      _initEITVQueryWSProxy();
    return eITVQueryWS;
  }
  
  public com.gescogroup.blackbox.EitvQueryWSResponse getDataEITV(com.gescogroup.blackbox.EitvQueryWSRequest arg0) throws java.rmi.RemoteException{
    if (eITVQueryWS == null)
      _initEITVQueryWSProxy();
    return eITVQueryWS.getDataEITV(arg0);
  }
  
  
}