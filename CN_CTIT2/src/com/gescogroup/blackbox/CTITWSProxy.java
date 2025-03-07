package com.gescogroup.blackbox;

public class CTITWSProxy implements com.gescogroup.blackbox.CTITWS {
  private String _endpoint = null;
  private com.gescogroup.blackbox.CTITWS cTITWS = null;
  
  public CTITWSProxy() {
    _initCTITWSProxy();
  }
  
  public CTITWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initCTITWSProxy();
  }
  
  private void _initCTITWSProxy() {
    try {
      cTITWS = (new com.gescogroup.blackbox.CTITServiceLocator()).getCTITService();
      if (cTITWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)cTITWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)cTITWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (cTITWS != null)
      ((javax.xml.rpc.Stub)cTITWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.gescogroup.blackbox.CTITWS getCTITWS() {
    if (cTITWS == null)
      _initCTITWSProxy();
    return cTITWS;
  }
  
  public com.gescogroup.blackbox.CtitsoapResponse checkCTIT(com.gescogroup.blackbox.CtitsoapRequest arg0) throws java.rmi.RemoteException{
    if (cTITWS == null)
      _initCTITWSProxy();
    return cTITWS.checkCTIT(arg0);
  }
  
  public com.gescogroup.blackbox.CtitsoapFullResponse fullCTIT(com.gescogroup.blackbox.CtitRequest arg0) throws java.rmi.RemoteException{
    if (cTITWS == null)
      _initCTITWSProxy();
    return cTITWS.fullCTIT(arg0);
  }
  
  public com.gescogroup.blackbox.CtitsoapNotificationResponse notificationCTIT(com.gescogroup.blackbox.CtitRequest arg0) throws java.rmi.RemoteException{
    if (cTITWS == null)
      _initCTITWSProxy();
    return cTITWS.notificationCTIT(arg0);
  }
  
  public com.gescogroup.blackbox.CtitsoapTradeResponse tradeCTIT(com.gescogroup.blackbox.CtitRequest arg0) throws java.rmi.RemoteException{
    if (cTITWS == null)
      _initCTITWSProxy();
    return cTITWS.tradeCTIT(arg0);
  }
  
  
}