/**
 * ReportsSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package WebServices.MotorESP.AnuntisSGM;

public interface ReportsSoap extends java.rmi.Remote {
    public WebServices.MotorESP.AnuntisSGM.ReportRequestData[] getReportsRequests() throws java.rmi.RemoteException;
    public WebServices.MotorESP.AnuntisSGM.ReportRequestData[] getReportsRequestsByDate(java.util.Calendar dateIni, java.util.Calendar dateFin) throws java.rmi.RemoteException;
}
