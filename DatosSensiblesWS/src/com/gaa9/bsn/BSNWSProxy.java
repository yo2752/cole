package com.gaa9.bsn;

public class BSNWSProxy implements com.gaa9.bsn.BSNWS {
  private String _endpoint = null;
  private com.gaa9.bsn.BSNWS bSNWS = null;
  
  public BSNWSProxy() {
    _initBSNWSProxy();
  }
  
  public BSNWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initBSNWSProxy();
  }
  
  private void _initBSNWSProxy() {
    try {
      bSNWS = (new com.gaa9.bsn.BSNServiceLocator()).getBSNService();
      if (bSNWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)bSNWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)bSNWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (bSNWS != null)
      ((javax.xml.rpc.Stub)bSNWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.gaa9.bsn.BSNWS getBSNWS() {
    if (bSNWS == null)
      _initBSNWSProxy();
    return bSNWS;
  }
  
  public com.gaa9.bsn.BsnResponse gestionarBastidores(com.gaa9.bsn.BsnRequest arg0) throws java.rmi.RemoteException{
    if (bSNWS == null)
      _initBSNWSProxy();
    return bSNWS.gestionarBastidores(arg0);
  }
  
  
}