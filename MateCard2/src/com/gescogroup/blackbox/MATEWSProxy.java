package com.gescogroup.blackbox;

public class MATEWSProxy implements com.gescogroup.blackbox.MATEWS {
  private String _endpoint = null;
  private com.gescogroup.blackbox.MATEWS mATEWS = null;
  
  public MATEWSProxy() {
    _initMATEWSProxy();
  }
  
  public MATEWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initMATEWSProxy();
  }
  
  private void _initMATEWSProxy() {
    try {
      mATEWS = (new com.gescogroup.blackbox.MATEServiceLocator()).getMATEService();
      if (mATEWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)mATEWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)mATEWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (mATEWS != null)
      ((javax.xml.rpc.Stub)mATEWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.gescogroup.blackbox.MATEWS getMATEWS() {
    if (mATEWS == null)
      _initMATEWSProxy();
    return mATEWS;
  }
  
  public com.gescogroup.blackbox.CardMATEResponse sendCardMATE(com.gescogroup.blackbox.CardMATERequest arg0) throws java.rmi.RemoteException{
    if (mATEWS == null)
      _initMATEWSProxy();
    return mATEWS.sendCardMATE(arg0);
  }
  
  public com.gescogroup.blackbox.ElectronicMATEResponse sendElectronicMATE(com.gescogroup.blackbox.ElectronicMATERequest arg0) throws java.rmi.RemoteException{
    if (mATEWS == null)
      _initMATEWSProxy();
    return mATEWS.sendElectronicMATE(arg0);
  }
  
  
}