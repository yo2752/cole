package org.gestoresmadrid.justificanteprofesional.ws.blackbox;

public class JustificanteWSProxy implements org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteWS {
  private String _endpoint = null;
  private org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteWS justificanteWS = null;
  
  public JustificanteWSProxy() {
    _initJustificanteWSProxy();
  }
  
  public JustificanteWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initJustificanteWSProxy();
  }
  
  private void _initJustificanteWSProxy() {
    try {
      justificanteWS = (new org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteServiceLocator()).getJustificanteService();
      if (justificanteWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)justificanteWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)justificanteWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (justificanteWS != null)
      ((javax.xml.rpc.Stub)justificanteWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteWS getJustificanteWS() {
    if (justificanteWS == null)
      _initJustificanteWSProxy();
    return justificanteWS;
  }
  
  public org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteRespuestaSOAP descargarJustificante(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException{
    if (justificanteWS == null)
      _initJustificanteWSProxy();
    return justificanteWS.descargarJustificante(arg0, arg1);
  }
  
  public org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteRespuestaSOAP emitirJustificante(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException{
    if (justificanteWS == null)
      _initJustificanteWSProxy();
    return justificanteWS.emitirJustificante(arg0, arg1);
  }
  
  public org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteRespuestaSOAP verificarJustificante(java.lang.String arg0) throws java.rmi.RemoteException{
    if (justificanteWS == null)
      _initJustificanteWSProxy();
    return justificanteWS.verificarJustificante(arg0);
  }
  
  
}