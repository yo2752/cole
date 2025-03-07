package es.trafico.cliente.vehiculos.custodiaitv.webservice;

public class SolicitudOperacionesITVWSProxy implements es.trafico.cliente.vehiculos.custodiaitv.webservice.SolicitudOperacionesITVWS {
  private String _endpoint = null;
  private es.trafico.cliente.vehiculos.custodiaitv.webservice.SolicitudOperacionesITVWS solicitudOperacionesITVWS = null;
  
  public SolicitudOperacionesITVWSProxy() {
    _initSolicitudOperacionesITVWSProxy();
  }
  
  public SolicitudOperacionesITVWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initSolicitudOperacionesITVWSProxy();
  }
  
  private void _initSolicitudOperacionesITVWSProxy() {
    try {
      solicitudOperacionesITVWS = (new es.trafico.cliente.vehiculos.custodiaitv.webservice.SolicitudOperacionesITVWSServiceLocator()).getSolicitudOperacionesITVWS();
      if (solicitudOperacionesITVWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)solicitudOperacionesITVWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)solicitudOperacionesITVWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (solicitudOperacionesITVWS != null)
      ((javax.xml.rpc.Stub)solicitudOperacionesITVWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public es.trafico.cliente.vehiculos.custodiaitv.webservice.SolicitudOperacionesITVWS getSolicitudOperacionesITVWS() {
    if (solicitudOperacionesITVWS == null)
      _initSolicitudOperacionesITVWSProxy();
    return solicitudOperacionesITVWS;
  }
  
  public es.trafico.servicios.vehiculos.custodiaitv.beans.OperacionEITVRespuestaDTO anotarEITV(java.lang.String localeId, java.lang.String peticion) throws java.rmi.RemoteException{
    if (solicitudOperacionesITVWS == null)
      _initSolicitudOperacionesITVWSProxy();
    return solicitudOperacionesITVWS.anotarEITV(localeId, peticion);
  }
  
  public es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaBloquearDTO bloquearEITV(java.lang.String localeId, java.lang.String peticion) throws java.rmi.RemoteException{
    if (solicitudOperacionesITVWS == null)
      _initSolicitudOperacionesITVWSProxy();
    return solicitudOperacionesITVWS.bloquearEITV(localeId, peticion);
  }
  
  public es.trafico.servicios.vehiculos.custodiaitv.beans.ConsultaEITVRespuestaDTO consultarEITV(java.lang.String localeId, java.lang.String peticion) throws java.rmi.RemoteException{
    if (solicitudOperacionesITVWS == null)
      _initSolicitudOperacionesITVWSProxy();
    return solicitudOperacionesITVWS.consultarEITV(localeId, peticion);
  }
  
  public es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaBloquearDTO desbloquearEITV(java.lang.String localeId, java.lang.String peticion) throws java.rmi.RemoteException{
    if (solicitudOperacionesITVWS == null)
      _initSolicitudOperacionesITVWSProxy();
    return solicitudOperacionesITVWS.desbloquearEITV(localeId, peticion);
  }
  
  public es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaEntregarDTO entregarEITV(java.lang.String localeId, java.lang.String peticion) throws java.rmi.RemoteException{
    if (solicitudOperacionesITVWS == null)
      _initSolicitudOperacionesITVWSProxy();
    return solicitudOperacionesITVWS.entregarEITV(localeId, peticion);
  }
  
  public es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaLiberarDTO liberarEITV(java.lang.String localeId, java.lang.String peticion) throws java.rmi.RemoteException{
    if (solicitudOperacionesITVWS == null)
      _initSolicitudOperacionesITVWSProxy();
    return solicitudOperacionesITVWS.liberarEITV(localeId, peticion);
  }
  
  
}