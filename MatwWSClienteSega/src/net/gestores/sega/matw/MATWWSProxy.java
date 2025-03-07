package net.gestores.sega.matw;

public class MATWWSProxy implements net.gestores.sega.matw.MATWWS {
  private String _endpoint = null;
  private net.gestores.sega.matw.MATWWS mATWWS = null;
  
  public MATWWSProxy() {
    _initMATWWSProxy();
  }
  
  public MATWWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initMATWWSProxy();
  }
  
  private void _initMATWWSProxy() {
    try {
      mATWWS = (new net.gestores.sega.matw.MATWServiceLocator()).getMATWService();
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
  
  public net.gestores.sega.matw.MATWWS getMATWWS() {
    if (mATWWS == null)
      _initMATWWSProxy();
    return mATWWS;
  }
  
  public net.gestores.sega.matw.MatwReturn sendMATW(net.gestores.sega.matw.MatwArgument arg0) throws java.rmi.RemoteException{
    if (mATWWS == null)
      _initMATWWSProxy();
    return mATWWS.sendMATW(arg0);
  }
  
  
}