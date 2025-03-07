package net.consejogestores.sercon.ws.model.integracion;

public class RegistroServiceProxy implements net.consejogestores.sercon.ws.model.integracion.RegistroService {
  private String _endpoint = null;
  private net.consejogestores.sercon.ws.model.integracion.RegistroService registroService = null;
  
  public RegistroServiceProxy() {
    _initRegistroServiceProxy();
  }
  
  public RegistroServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initRegistroServiceProxy();
  }
  
  private void _initRegistroServiceProxy() {
    try {
      registroService = (new net.consejogestores.sercon.ws.model.integracion.RegistroServiceServiceLocator()).getRegistroService();
      if (registroService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)registroService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)registroService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (registroService != null)
      ((javax.xml.rpc.Stub)registroService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public net.consejogestores.sercon.ws.model.integracion.RegistroService getRegistroService() {
    if (registroService == null)
      _initRegistroServiceProxy();
    return registroService;
  }
  
  public net.consejogestores.sercon.core.model.integracion.dto.RegistroResponse envioMensaje(net.consejogestores.sercon.core.model.integracion.dto.RegistroRequest envio) throws java.rmi.RemoteException{
    if (registroService == null)
      _initRegistroServiceProxy();
    return registroService.envioMensaje(envio);
  }
  
  
}