package net.gestores.siga.dvl;

public class DefinitiveVehicleLicenseWSProxy implements net.gestores.siga.dvl.DefinitiveVehicleLicenseWS {
  private String _endpoint = null;
  private net.gestores.siga.dvl.DefinitiveVehicleLicenseWS definitiveVehicleLicenseWS = null;
  
  public DefinitiveVehicleLicenseWSProxy() {
    _initDefinitiveVehicleLicenseWSProxy();
  }
  
  public DefinitiveVehicleLicenseWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initDefinitiveVehicleLicenseWSProxy();
  }
  
  private void _initDefinitiveVehicleLicenseWSProxy() {
    try {
      definitiveVehicleLicenseWS = (new net.gestores.siga.dvl.DefinitiveVehicleLicenseServiceLocator()).getDefinitiveVehicleLicenseService();
      if (definitiveVehicleLicenseWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)definitiveVehicleLicenseWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)definitiveVehicleLicenseWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (definitiveVehicleLicenseWS != null)
      ((javax.xml.rpc.Stub)definitiveVehicleLicenseWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public net.gestores.siga.dvl.DefinitiveVehicleLicenseWS getDefinitiveVehicleLicenseWS() {
    if (definitiveVehicleLicenseWS == null)
      _initDefinitiveVehicleLicenseWSProxy();
    return definitiveVehicleLicenseWS;
  }
  
  public net.gestores.siga.dvl.DvlReturn issueEnvironmentalDistinctive(java.lang.String externalSystemFiscalId, java.lang.String associationFiscalId, java.lang.String agentFiscalId, java.lang.String tasa, net.gestores.siga.dvl.CriteriosConsultaVehiculo criteriosConsultaVehiculo, net.gestores.siga.dvl.DvlEDRequestCarSharing carSharing, java.lang.Integer offsetLeft, java.lang.Integer offsetTop) throws java.rmi.RemoteException{
    if (definitiveVehicleLicenseWS == null)
      _initDefinitiveVehicleLicenseWSProxy();
    return definitiveVehicleLicenseWS.issueEnvironmentalDistinctive(externalSystemFiscalId, associationFiscalId, agentFiscalId, tasa, criteriosConsultaVehiculo, carSharing, offsetLeft, offsetTop);
  }
  
  public net.gestores.siga.dvl.DvlReturn issueDefinitiveVehicleLicense(java.lang.String externalSystemFiscalId, java.lang.String xmlB64, net.gestores.siga.dvl.DvlRequestCarSharing carSharing, java.lang.Integer offsetLeft, java.lang.Integer offsetTop) throws java.rmi.RemoteException{
    if (definitiveVehicleLicenseWS == null)
      _initDefinitiveVehicleLicenseWSProxy();
    return definitiveVehicleLicenseWS.issueDefinitiveVehicleLicense(externalSystemFiscalId, xmlB64, carSharing, offsetLeft, offsetTop);
  }
  
  public net.gestores.siga.dvl.DvlReturn issueDefinitiveVehicleLicenseEITV(net.gestores.siga.dvl.EitvArgument arg0, net.gestores.siga.dvl.DvlEitvRequestHeadOffice headOffice, net.gestores.siga.dvl.DvlEitvRequestBranchOffice branchOffice, java.lang.String titularName, java.lang.String titularFirstFamilyName, java.lang.String titularSecondFamilyName, net.gestores.siga.dvl.DvlEitvRequestService service, java.lang.String firtMatriculationDate, net.gestores.siga.dvl.DvlEitvRequestCarSharing carSharing, java.lang.Integer offsetLeft, java.lang.Integer offsetTop) throws java.rmi.RemoteException{
    if (definitiveVehicleLicenseWS == null)
      _initDefinitiveVehicleLicenseWSProxy();
    return definitiveVehicleLicenseWS.issueDefinitiveVehicleLicenseEITV(arg0, headOffice, branchOffice, titularName, titularFirstFamilyName, titularSecondFamilyName, service, firtMatriculationDate, carSharing, offsetLeft, offsetTop);
  }
  
  
}