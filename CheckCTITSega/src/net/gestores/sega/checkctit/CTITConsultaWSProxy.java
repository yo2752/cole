package net.gestores.sega.checkctit;

public class CTITConsultaWSProxy implements net.gestores.sega.checkctit.CTITConsultaWS {
  private String _endpoint = null;
  private net.gestores.sega.checkctit.CTITConsultaWS cTITConsultaWS = null;
  
  public CTITConsultaWSProxy() {
    _initCTITConsultaWSProxy();
  }
  
  public CTITConsultaWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initCTITConsultaWSProxy();
  }
  
  private void _initCTITConsultaWSProxy() {
    try {
      cTITConsultaWS = (new net.gestores.sega.checkctit.CTITConsultaServiceLocator()).getCTITConsultaService();
      if (cTITConsultaWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)cTITConsultaWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)cTITConsultaWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (cTITConsultaWS != null)
      ((javax.xml.rpc.Stub)cTITConsultaWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public net.gestores.sega.checkctit.CTITConsultaWS getCTITConsultaWS() {
    if (cTITConsultaWS == null)
      _initCTITConsultaWSProxy();
    return cTITConsultaWS;
  }
  
  public net.gestores.sega.ctit.tipos.CheckReturn checkCTIT(net.gestores.sega.ctit.tipos.CheckArgument arg0) throws java.rmi.RemoteException{
    if (cTITConsultaWS == null)
      _initCTITConsultaWSProxy();
    return cTITConsultaWS.checkCTIT(arg0);
  }
  
  
}