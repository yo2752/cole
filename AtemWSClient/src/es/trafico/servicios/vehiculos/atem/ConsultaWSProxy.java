package es.trafico.servicios.vehiculos.atem;

public class ConsultaWSProxy implements es.trafico.servicios.vehiculos.atem.ConsultaWS {
  private String _endpoint = null;
  private es.trafico.servicios.vehiculos.atem.ConsultaWS consultaWS = null;
  
  public ConsultaWSProxy() {
    _initConsultaWSProxy();
  }
  
  public ConsultaWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initConsultaWSProxy();
  }
  
  private void _initConsultaWSProxy() {
    try {
      consultaWS = (new es.trafico.servicios.vehiculos.atem.ConsultaWSServiceLocator()).getConsultaWS();
      if (consultaWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)consultaWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)consultaWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (consultaWS != null)
      ((javax.xml.rpc.Stub)consultaWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public es.trafico.servicios.vehiculos.atem.ConsultaWS getConsultaWS() {
    if (consultaWS == null)
      _initConsultaWSProxy();
    return consultaWS;
  }
  
  public java.lang.String consultaBastidor(java.lang.String tasa, java.lang.String bastidor) throws java.rmi.RemoteException{
    if (consultaWS == null)
      _initConsultaWSProxy();
    return consultaWS.consultaBastidor(tasa, bastidor);
  }
  
  public java.lang.String consultaMasiva(java.lang.String tipo, java.lang.String xml) throws java.rmi.RemoteException{
    if (consultaWS == null)
      _initConsultaWSProxy();
    return consultaWS.consultaMasiva(tipo, xml);
  }
  
  public java.lang.String consultaMatricula(java.lang.String tasa, java.lang.String matricula) throws java.rmi.RemoteException{
    if (consultaWS == null)
      _initConsultaWSProxy();
    return consultaWS.consultaMatricula(tasa, matricula);
  }
  
  public java.lang.String consultaPersona(java.lang.String tasa, java.lang.String persona) throws java.rmi.RemoteException{
    if (consultaWS == null)
      _initConsultaWSProxy();
    return consultaWS.consultaPersona(tasa, persona);
  }
  
  public java.lang.String obtenerResultadoConsulta(java.lang.String referencia) throws java.rmi.RemoteException{
    if (consultaWS == null)
      _initConsultaWSProxy();
    return consultaWS.obtenerResultadoConsulta(referencia);
  }
  
  
}