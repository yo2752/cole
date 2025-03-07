/**
 * ProfessionalProofWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.gestores.sega.pps;

public interface ProfessionalProofWS extends java.rmi.Remote {
    public net.gestores.sega.pps.PpsReturn getProfessionalProof(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2) throws java.rmi.RemoteException;
    public net.gestores.sega.pps.PpsReturn issueProfessionalProof(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2) throws java.rmi.RemoteException;
    public net.gestores.sega.pps.VppsReturn verifyProfessionalProof(java.lang.String arg0) throws java.rmi.RemoteException;
}
