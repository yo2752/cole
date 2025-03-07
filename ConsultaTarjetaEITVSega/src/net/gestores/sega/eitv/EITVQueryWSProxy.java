package net.gestores.sega.eitv;

public class EITVQueryWSProxy implements net.gestores.sega.eitv.EITVQueryWS {
  private String _endpoint = null;
  private net.gestores.sega.eitv.EITVQueryWS eITVQueryWS = null;
  
  public EITVQueryWSProxy() {
    _initEITVQueryWSProxy();
  }
  
  public EITVQueryWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initEITVQueryWSProxy();
  }
  
  private void _initEITVQueryWSProxy() {
    try {
      eITVQueryWS = (new net.gestores.sega.eitv.EITVQueryServiceLocator()).getEITVQueryService();
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
  
  public net.gestores.sega.eitv.EITVQueryWS getEITVQueryWS() {
    if (eITVQueryWS == null)
      _initEITVQueryWSProxy();
    return eITVQueryWS;
  }
  
  public net.gestores.sega.eitv.EitvReturn getDataEITV(net.gestores.sega.eitv.EitvArgument arg0) throws java.rmi.RemoteException{
    if (eITVQueryWS == null)
      _initEITVQueryWSProxy();
    return eITVQueryWS.getDataEITV(arg0);
  }
  
  
}