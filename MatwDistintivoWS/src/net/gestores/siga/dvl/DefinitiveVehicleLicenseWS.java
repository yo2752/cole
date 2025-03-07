/**
 * DefinitiveVehicleLicenseWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.gestores.siga.dvl;

public interface DefinitiveVehicleLicenseWS extends java.rmi.Remote {
    public net.gestores.siga.dvl.DvlReturn issueEnvironmentalDistinctive(java.lang.String externalSystemFiscalId, java.lang.String associationFiscalId, java.lang.String agentFiscalId, java.lang.String tasa, net.gestores.siga.dvl.CriteriosConsultaVehiculo criteriosConsultaVehiculo, net.gestores.siga.dvl.DvlEDRequestCarSharing carSharing, java.lang.Integer offsetLeft, java.lang.Integer offsetTop) throws java.rmi.RemoteException;
    public net.gestores.siga.dvl.DvlReturn issueDefinitiveVehicleLicense(java.lang.String externalSystemFiscalId, java.lang.String xmlB64, net.gestores.siga.dvl.DvlRequestCarSharing carSharing, java.lang.Integer offsetLeft, java.lang.Integer offsetTop) throws java.rmi.RemoteException;
    public net.gestores.siga.dvl.DvlReturn issueDefinitiveVehicleLicenseEITV(net.gestores.siga.dvl.EitvArgument arg0, net.gestores.siga.dvl.DvlEitvRequestHeadOffice headOffice, net.gestores.siga.dvl.DvlEitvRequestBranchOffice branchOffice, java.lang.String titularName, java.lang.String titularFirstFamilyName, java.lang.String titularSecondFamilyName, net.gestores.siga.dvl.DvlEitvRequestService service, java.lang.String firtMatriculationDate, net.gestores.siga.dvl.DvlEitvRequestCarSharing carSharing, java.lang.Integer offsetLeft, java.lang.Integer offsetTop) throws java.rmi.RemoteException;
}
