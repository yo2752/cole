package com.matriculasWS.servicioWeb;

public class ObtenerMatriculasWSProxy implements com.matriculasWS.servicioWeb.ObtenerMatriculasWS {
  private String _endpoint = null;
  private com.matriculasWS.servicioWeb.ObtenerMatriculasWS obtenerMatriculasWS = null;
  
  public ObtenerMatriculasWSProxy() {
    _initObtenerMatriculasWSProxy();
  }
  
  public ObtenerMatriculasWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initObtenerMatriculasWSProxy();
  }
  
  private void _initObtenerMatriculasWSProxy() {
    try {
      obtenerMatriculasWS = (new com.matriculasWS.servicioWeb.ObtenerMatriculasWSServiceLocator()).getObtenerMatriculasWS();
      if (obtenerMatriculasWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)obtenerMatriculasWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)obtenerMatriculasWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (obtenerMatriculasWS != null)
      ((javax.xml.rpc.Stub)obtenerMatriculasWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.matriculasWS.servicioWeb.ObtenerMatriculasWS getObtenerMatriculasWS() {
    if (obtenerMatriculasWS == null)
      _initObtenerMatriculasWSProxy();
    return obtenerMatriculasWS;
  }
  
  public com.matriculasWS.beans.DatosSalida consultaMatriculas(java.lang.String authUser, java.lang.String authPass, com.matriculasWS.beans.DatosEntrada datosEntrada) throws java.rmi.RemoteException{
    if (obtenerMatriculasWS == null)
      _initObtenerMatriculasWSProxy();
    return obtenerMatriculasWS.consultaMatriculas(authUser, authPass, datosEntrada);
  }
  
  
}