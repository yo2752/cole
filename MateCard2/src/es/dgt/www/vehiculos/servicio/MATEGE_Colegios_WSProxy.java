package es.dgt.www.vehiculos.servicio;

public class MATEGE_Colegios_WSProxy implements es.dgt.www.vehiculos.servicio.MATEGE_Colegios_WS {
  private String _endpoint = null;
  private es.dgt.www.vehiculos.servicio.MATEGE_Colegios_WS mATEGE_Colegios_WS = null;
  
  public MATEGE_Colegios_WSProxy() {
    _initMATEGE_Colegios_WSProxy();
  }
  
  public MATEGE_Colegios_WSProxy(String endpoint) {
    _endpoint = endpoint;
    _initMATEGE_Colegios_WSProxy();
  }
  
  private void _initMATEGE_Colegios_WSProxy() {
    try {
      mATEGE_Colegios_WS = (new es.dgt.www.vehiculos.servicio.MATEGELocator()).getMATEGE();
      if (mATEGE_Colegios_WS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)mATEGE_Colegios_WS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)mATEGE_Colegios_WS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (mATEGE_Colegios_WS != null)
      ((javax.xml.rpc.Stub)mATEGE_Colegios_WS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public es.dgt.www.vehiculos.servicio.MATEGE_Colegios_WS getMATEGE_Colegios_WS() {
    if (mATEGE_Colegios_WS == null)
      _initMATEGE_Colegios_WSProxy();
    return mATEGE_Colegios_WS;
  }
  
  public es.dgt.www.vehiculos.esquema.RespuestaMatriculacion enviarSolicitudMatriculacion(es.dgt.www.vehiculos.esquema.SolicitudMatriculacion entradaSolicitudMatriculacion) throws java.rmi.RemoteException{
    if (mATEGE_Colegios_WS == null)
      _initMATEGE_Colegios_WSProxy();
    return mATEGE_Colegios_WS.enviarSolicitudMatriculacion(entradaSolicitudMatriculacion);
  }
  
  
}