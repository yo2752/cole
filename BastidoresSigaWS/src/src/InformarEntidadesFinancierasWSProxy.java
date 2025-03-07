package src;

public class InformarEntidadesFinancierasWSProxy implements src.InformarEntidadesFinancierasWS {
  private String _endpoint = null;
  private src.InformarEntidadesFinancierasWS informarEntidadesFinancierasWS = null;
  
  public InformarEntidadesFinancierasWSProxy() {
    _initInformarEntidadesFinancierasWSProxy();
  }
  
  public InformarEntidadesFinancierasWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initInformarEntidadesFinancierasWSProxy();
  }
  
  private void _initInformarEntidadesFinancierasWSProxy() {
    try {
      informarEntidadesFinancierasWS = (new src.InformarEntidadesFinancierasWSServiceLocator()).getInformarEntidadesFinancierasWS();
      if (informarEntidadesFinancierasWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)informarEntidadesFinancierasWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)informarEntidadesFinancierasWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (informarEntidadesFinancierasWS != null)
      ((javax.xml.rpc.Stub)informarEntidadesFinancierasWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public src.InformarEntidadesFinancierasWS getInformarEntidadesFinancierasWS() {
    if (informarEntidadesFinancierasWS == null)
      _initInformarEntidadesFinancierasWSProxy();
    return informarEntidadesFinancierasWS;
  }
  
  public src.EntidadesFinancierasResponse enviaInformacionBastidores(src.EntidadesFinancierasRequest bastidoresRequest) throws java.rmi.RemoteException{
    if (informarEntidadesFinancierasWS == null)
      _initInformarEntidadesFinancierasWSProxy();
    return informarEntidadesFinancierasWS.enviaInformacionBastidores(bastidoresRequest);
  }
  
  
}