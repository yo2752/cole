package net.gestores.sega.ctit;

public class CTITWSProxy implements net.gestores.sega.ctit.CTITWS {
  private String _endpoint = null;
  private net.gestores.sega.ctit.CTITWS cTITWS = null;
  
  public CTITWSProxy() {
    _initCTITWSProxy();
  }
  
  public CTITWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initCTITWSProxy();
  }
  
  private void _initCTITWSProxy() {
    try {
      cTITWS = (new net.gestores.sega.ctit.CTITServiceLocator()).getCTITService();
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
  
  public net.gestores.sega.ctit.CTITWS getCTITWS() {
    if (cTITWS == null)
      _initCTITWSProxy();
    return cTITWS;
  }
  
  public net.gestores.sega.ctit.tipos.CtitReturn fullCTIT(net.gestores.sega.ctit.tipos.CtitArgument arg0) throws java.rmi.RemoteException{
    if (cTITWS == null)
      _initCTITWSProxy();
    return cTITWS.fullCTIT(arg0);
  }
  
  public net.gestores.sega.ctit.tipos.CtitReturn notificationCTIT(net.gestores.sega.ctit.tipos.CtitArgument arg0) throws java.rmi.RemoteException{
    if (cTITWS == null)
      _initCTITWSProxy();
    return cTITWS.notificationCTIT(arg0);
  }
  
  public net.gestores.sega.ctit.tipos.CtitReturn tradeCTIT(net.gestores.sega.ctit.tipos.CtitArgument arg0) throws java.rmi.RemoteException{
    if (cTITWS == null)
      _initCTITWSProxy();
    return cTITWS.tradeCTIT(arg0);
  }
  
  
}