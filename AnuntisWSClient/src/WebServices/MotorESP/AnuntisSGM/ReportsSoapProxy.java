package WebServices.MotorESP.AnuntisSGM;

public class ReportsSoapProxy implements WebServices.MotorESP.AnuntisSGM.ReportsSoap {
  private String _endpoint = null;
  private WebServices.MotorESP.AnuntisSGM.ReportsSoap reportsSoap = null;
  
  public ReportsSoapProxy() {
    _initReportsSoapProxy();
  }
  
  public ReportsSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initReportsSoapProxy();
  }
  
  private void _initReportsSoapProxy() {
    try {
      reportsSoap = (new WebServices.MotorESP.AnuntisSGM.ReportsLocator()).getReportsSoap();
      if (reportsSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)reportsSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)reportsSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (reportsSoap != null)
      ((javax.xml.rpc.Stub)reportsSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public WebServices.MotorESP.AnuntisSGM.ReportsSoap getReportsSoap() {
    if (reportsSoap == null)
      _initReportsSoapProxy();
    return reportsSoap;
  }
  
  public WebServices.MotorESP.AnuntisSGM.ReportRequestData[] getReportsRequests() throws java.rmi.RemoteException{
    if (reportsSoap == null)
      _initReportsSoapProxy();
    return reportsSoap.getReportsRequests();
  }
  
  public WebServices.MotorESP.AnuntisSGM.ReportRequestData[] getReportsRequestsByDate(java.util.Calendar dateIni, java.util.Calendar dateFin) throws java.rmi.RemoteException{
    if (reportsSoap == null)
      _initReportsSoapProxy();
    return reportsSoap.getReportsRequestsByDate(dateIni, dateFin);
  }
  
  
}