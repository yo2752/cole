package net.gestores.sega.pps;

public class ProfessionalProofWSProxy implements net.gestores.sega.pps.ProfessionalProofWS {
  private String _endpoint = null;
  private net.gestores.sega.pps.ProfessionalProofWS professionalProofWS = null;
  
  public ProfessionalProofWSProxy() {
    _initProfessionalProofWSProxy();
  }
  
  public ProfessionalProofWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initProfessionalProofWSProxy();
  }
  
  private void _initProfessionalProofWSProxy() {
    try {
      professionalProofWS = (new net.gestores.sega.pps.ProfessionalProofServiceLocator()).getProfessionalProofService();
      if (professionalProofWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)professionalProofWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)professionalProofWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (professionalProofWS != null)
      ((javax.xml.rpc.Stub)professionalProofWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public net.gestores.sega.pps.ProfessionalProofWS getProfessionalProofWS() {
    if (professionalProofWS == null)
      _initProfessionalProofWSProxy();
    return professionalProofWS;
  }
  
  public net.gestores.sega.pps.PpsReturn getProfessionalProof(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2) throws java.rmi.RemoteException{
    if (professionalProofWS == null)
      _initProfessionalProofWSProxy();
    return professionalProofWS.getProfessionalProof(arg0, arg1, arg2);
  }
  
  public net.gestores.sega.pps.PpsReturn issueProfessionalProof(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2) throws java.rmi.RemoteException{
    if (professionalProofWS == null)
      _initProfessionalProofWSProxy();
    return professionalProofWS.issueProfessionalProof(arg0, arg1, arg2);
  }
  
  public net.gestores.sega.pps.VppsReturn verifyProfessionalProof(java.lang.String arg0) throws java.rmi.RemoteException{
    if (professionalProofWS == null)
      _initProfessionalProofWSProxy();
    return professionalProofWS.verifyProfessionalProof(arg0);
  }
  
  
}