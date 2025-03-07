package org.gi.infra.wscn.pruebas.ws.copy;

public class WSCNPruebasProxy implements org.gi.infra.wscn.pruebas.ws.WSCNPruebas {
  private String _endpoint = null;
  private org.gi.infra.wscn.pruebas.ws.WSCNPruebas wSCNPruebas = null;
  
  public WSCNPruebasProxy() {
    _initWSCNPruebasProxy();
  }
  
  public WSCNPruebasProxy(String endpoint) {
    _endpoint = endpoint;
    _initWSCNPruebasProxy();
  }
  
  private void _initWSCNPruebasProxy() {
    try {
      wSCNPruebas = (new org.gi.infra.wscn.pruebas.ws.WSCNPruebasServiceLocator()).getWSCNPruebasPort();
      if (wSCNPruebas != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)wSCNPruebas)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)wSCNPruebas)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (wSCNPruebas != null)
      ((javax.xml.rpc.Stub)wSCNPruebas)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.gi.infra.wscn.pruebas.ws.WSCNPruebas getWSCNPruebas() {
    if (wSCNPruebas == null)
      _initWSCNPruebasProxy();
    return wSCNPruebas;
  }
  
  public org.gi.infra.wscn.pruebas.ws.ListadoNotificaciones consultarListadoNotificaciones(int rol, java.lang.String identificadorPoderdante, int codigoSiguienteNotificacionPropia, int codigoSiguienteNotificacionAutorizadoRED, int codigoSiguienteNotificacionApoderado) throws java.rmi.RemoteException, org.gi.infra.wscn.pruebas.ws.WSCNPruebasExcepcionSistema{
    if (wSCNPruebas == null)
      _initWSCNPruebasProxy();
    return wSCNPruebas.consultarListadoNotificaciones(rol, identificadorPoderdante, codigoSiguienteNotificacionPropia, codigoSiguienteNotificacionAutorizadoRED, codigoSiguienteNotificacionApoderado);
  }
  
  public org.gi.infra.wscn.pruebas.ws.AcuseNotificacion solicitarAcuseNotificacion(int rol, java.lang.String identificadorPoderdante, int codigoNotificacion, boolean esDeAceptacion) throws java.rmi.RemoteException, org.gi.infra.wscn.pruebas.ws.WSCNPruebasExcepcionSistema{
    if (wSCNPruebas == null)
      _initWSCNPruebasProxy();
    return wSCNPruebas.solicitarAcuseNotificacion(rol, identificadorPoderdante, codigoNotificacion, esDeAceptacion);
  }
  
  public org.gi.infra.wscn.pruebas.ws.NotificacionRecuperada enviarAcuseNotificacion(int rol, java.lang.String identificadorPoderdante, byte[] xmlAcuseFirmado) throws java.rmi.RemoteException, org.gi.infra.wscn.pruebas.ws.WSCNPruebasExcepcionSistema{
    if (wSCNPruebas == null)
      _initWSCNPruebasProxy();
    return wSCNPruebas.enviarAcuseNotificacion(rol, identificadorPoderdante, xmlAcuseFirmado);
  }
  
  public org.gi.infra.wscn.pruebas.ws.NotificacionRecuperada verNotificacionAceptada(int rol, java.lang.String identificadorPoderdante, int codigoNotificacion) throws java.rmi.RemoteException, org.gi.infra.wscn.pruebas.ws.WSCNPruebasExcepcionSistema{
    if (wSCNPruebas == null)
      _initWSCNPruebasProxy();
    return wSCNPruebas.verNotificacionAceptada(rol, identificadorPoderdante, codigoNotificacion);
  }
  
  public org.gi.infra.wscn.pruebas.ws.ListadoPoderdantes solicitarPoderdantes() throws java.rmi.RemoteException, org.gi.infra.wscn.pruebas.ws.WSCNPruebasExcepcionSistema{
    if (wSCNPruebas == null)
      _initWSCNPruebasProxy();
    return wSCNPruebas.solicitarPoderdantes();
  }
  
  
}