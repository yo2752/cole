package opti.presentacion.services;

public class PresentacionServicePortTypeProxy implements opti.presentacion.services.PresentacionServicePortType {
  private String _endpoint = null;
  private opti.presentacion.services.PresentacionServicePortType presentacionServicePortType = null;
  
  public PresentacionServicePortTypeProxy() {
    _initPresentacionServicePortTypeProxy();
  }
  
  public PresentacionServicePortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initPresentacionServicePortTypeProxy();
  }
  
  private void _initPresentacionServicePortTypeProxy() {
    try {
      presentacionServicePortType = (new opti.presentacion.services.PresentacionServiceLocator()).getPresentacionServiceHttpSoap12Endpoint();
      if (presentacionServicePortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)presentacionServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)presentacionServicePortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (presentacionServicePortType != null)
      ((javax.xml.rpc.Stub)presentacionServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public opti.presentacion.services.PresentacionServicePortType getPresentacionServicePortType() {
    if (presentacionServicePortType == null)
      _initPresentacionServicePortTypeProxy();
    return presentacionServicePortType;
  }
  
  public opti.presentacion.domain.xsd.Respuesta presentar(opti.presentacion.domain.xsd.Peticion args0) throws java.rmi.RemoteException, opti.presentacion.services.PresentacionServiceServiceException{
    if (presentacionServicePortType == null)
      _initPresentacionServicePortTypeProxy();
    return presentacionServicePortType.presentar(args0);
  }
  
  
}