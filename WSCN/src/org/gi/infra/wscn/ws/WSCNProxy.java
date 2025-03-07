package org.gi.infra.wscn.ws;

public class WSCNProxy implements org.gi.infra.wscn.ws.WSCN {
  private String _endpoint = null;
  private org.gi.infra.wscn.ws.WSCN wSCN = null;
  
  public WSCNProxy() {
    _initWSCNProxy();
  }
  
  public WSCNProxy(String endpoint) {
    _endpoint = endpoint;
    _initWSCNProxy();
  }
  
  private void _initWSCNProxy() {
    try {
      wSCN = (new org.gi.infra.wscn.ws.WSCNServiceLocator()).getWSCNPort();
      if (wSCN != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)wSCN)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)wSCN)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (wSCN != null)
      ((javax.xml.rpc.Stub)wSCN)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.gi.infra.wscn.ws.WSCN getWSCN() {
    if (wSCN == null)
      _initWSCNProxy();
    return wSCN;
  }
  
  public org.gi.infra.wscn.ws.ListadoNotificaciones consultarListadoNotificaciones(int rol, java.lang.String identificadorPoderdante, int codigoSiguienteNotificacionPropia, int codigoSiguienteNotificacionAutorizadoRED, int codigoSiguienteNotificacionApoderado) throws java.rmi.RemoteException, org.gi.infra.wscn.ws.WSCNExcepcionSistema{
    if (wSCN == null)
      _initWSCNProxy();
    return wSCN.consultarListadoNotificaciones(rol, identificadorPoderdante, codigoSiguienteNotificacionPropia, codigoSiguienteNotificacionAutorizadoRED, codigoSiguienteNotificacionApoderado);
  }
  
  public org.gi.infra.wscn.ws.AcuseNotificacion solicitarAcuseNotificacion(int rol, java.lang.String identificadorPoderdante, int codigoNotificacion, boolean esDeAceptacion) throws java.rmi.RemoteException, org.gi.infra.wscn.ws.WSCNExcepcionSistema{
    if (wSCN == null)
      _initWSCNProxy();
    return wSCN.solicitarAcuseNotificacion(rol, identificadorPoderdante, codigoNotificacion, esDeAceptacion);
  }
  
  public org.gi.infra.wscn.ws.NotificacionRecuperada enviarAcuseNotificacion(int rol, java.lang.String identificadorPoderdante, byte[] xmlAcuseFirmado) throws java.rmi.RemoteException, org.gi.infra.wscn.ws.WSCNExcepcionSistema{
    if (wSCN == null)
      _initWSCNProxy();
    return wSCN.enviarAcuseNotificacion(rol, identificadorPoderdante, xmlAcuseFirmado);
  }
  
  public org.gi.infra.wscn.ws.NotificacionRecuperada verNotificacionAceptada(int rol, java.lang.String identificadorPoderdante, int codigoNotificacion) throws java.rmi.RemoteException, org.gi.infra.wscn.ws.WSCNExcepcionSistema{
    if (wSCN == null)
      _initWSCNProxy();
    return wSCN.verNotificacionAceptada(rol, identificadorPoderdante, codigoNotificacion);
  }
  
  public org.gi.infra.wscn.ws.ListadoPoderdantes solicitarPoderdantes() throws java.rmi.RemoteException, org.gi.infra.wscn.ws.WSCNExcepcionSistema{
    if (wSCN == null)
      _initWSCNProxy();
    return wSCN.solicitarPoderdantes();
  }
  
  
}