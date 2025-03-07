package com.gescogroup.blackbox;

public class ProfessionalProofWSProxy implements com.gescogroup.blackbox.ProfessionalProofWS {
  private String _endpoint = null;
  private com.gescogroup.blackbox.ProfessionalProofWS professionalProofWS = null;
  
  public ProfessionalProofWSProxy() {
    _initProfessionalProofWSProxy();
  }
  
  public ProfessionalProofWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initProfessionalProofWSProxy();
  }
  
  private void _initProfessionalProofWSProxy() {
    try {
      professionalProofWS = (new com.gescogroup.blackbox.ProfessionalProofServiceLocator()).getProfessionalProofService();
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
  
  public com.gescogroup.blackbox.ProfessionalProofWS getProfessionalProofWS() {
    if (professionalProofWS == null)
      _initProfessionalProofWSProxy();
    return professionalProofWS;
  }
  
  public com.gescogroup.blackbox.ProfessionalProofResponse getProfessionalProof(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2) throws java.rmi.RemoteException{
    if (professionalProofWS == null)
      _initProfessionalProofWSProxy();
    return professionalProofWS.getProfessionalProof(arg0, arg1, arg2);
  }
  
  public com.gescogroup.blackbox.ProfessionalProofResponse issueProfessionalProof(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2) throws java.rmi.RemoteException{
    if (professionalProofWS == null)
      _initProfessionalProofWSProxy();
    return professionalProofWS.issueProfessionalProof(arg0, arg1, arg2);
  }
  
  public com.gescogroup.blackbox.ProfessionalProofVerificationResponse verifyProfessionalProof(java.lang.String arg0) throws java.rmi.RemoteException{
    if (professionalProofWS == null)
      _initProfessionalProofWSProxy();
    return professionalProofWS.verifyProfessionalProof(arg0);
  }
  
  
}