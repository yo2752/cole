/**
 * ProfessionalProofWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public interface ProfessionalProofWS extends java.rmi.Remote {
    public com.gescogroup.blackbox.ProfessionalProofResponse getProfessionalProof(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2) throws java.rmi.RemoteException;
    public com.gescogroup.blackbox.ProfessionalProofResponse issueProfessionalProof(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2) throws java.rmi.RemoteException;
    public com.gescogroup.blackbox.ProfessionalProofVerificationResponse verifyProfessionalProof(java.lang.String arg0) throws java.rmi.RemoteException;
}
