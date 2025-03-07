package es.iam.sbae.sbintopexterna;

public class InteroperabilidadExternaProxy implements es.iam.sbae.sbintopexterna.InteroperabilidadExterna {
  private String _endpoint = null;
  private es.iam.sbae.sbintopexterna.InteroperabilidadExterna interoperabilidadExterna = null;
  
  public InteroperabilidadExternaProxy() {
    _initInteroperabilidadExternaProxy();
  }
  
  public InteroperabilidadExternaProxy(String endpoint) {
    _endpoint = endpoint;
    _initInteroperabilidadExternaProxy();
  }
  
  private void _initInteroperabilidadExternaProxy() {
    try {
      interoperabilidadExterna = (new es.iam.sbae.sbintopexterna.InteroperabilidadExternaServiceLocator()).getInteroperabilidadExternaPort();
      if (interoperabilidadExterna != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)interoperabilidadExterna)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)interoperabilidadExterna)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (interoperabilidadExterna != null)
      ((javax.xml.rpc.Stub)interoperabilidadExterna)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public es.iam.sbae.sbintopexterna.InteroperabilidadExterna getInteroperabilidadExterna() {
    if (interoperabilidadExterna == null)
      _initInteroperabilidadExternaProxy();
    return interoperabilidadExterna;
  }
  
  public es.iam.sbae.sbintopexterna.RespuestaPeticion realizaPeticion(es.iam.sbae.sbintopexterna.PeticionExterna peticionExterna) throws java.rmi.RemoteException{
    if (interoperabilidadExterna == null)
      _initInteroperabilidadExternaProxy();
    return interoperabilidadExterna.realizaPeticion(peticionExterna);
  }
  
  
}