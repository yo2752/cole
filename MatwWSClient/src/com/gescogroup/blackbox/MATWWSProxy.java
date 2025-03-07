package com.gescogroup.blackbox;

public class MATWWSProxy implements com.gescogroup.blackbox.MATWWS {
  private String _endpoint = null;
  private com.gescogroup.blackbox.MATWWS mATWWS = null;
  
  public MATWWSProxy() {
    _initMATWWSProxy();
  }
  
  public MATWWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initMATWWSProxy();
  }
  
  private void _initMATWWSProxy() {
    try {
      mATWWS = (new com.gescogroup.blackbox.MATWServiceLocator()).getMATWService();
      if (mATWWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)mATWWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)mATWWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (mATWWS != null)
      ((javax.xml.rpc.Stub)mATWWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.gescogroup.blackbox.MATWWS getMATWWS() {
    if (mATWWS == null)
      _initMATWWSProxy();
    return mATWWS;
  }
  
  public com.gescogroup.blackbox.MatwResponse sendMATW(com.gescogroup.blackbox.MatwRequest arg0) throws java.rmi.RemoteException{
    if (mATWWS == null)
      _initMATWWSProxy();
    return mATWWS.sendMATW(arg0);
  }
  
  
}