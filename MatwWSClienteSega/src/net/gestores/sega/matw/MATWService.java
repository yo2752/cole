/**
 * MATWService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.gestores.sega.matw;

public interface MATWService extends javax.xml.rpc.Service {
    public java.lang.String getMATWServiceAddress();

    public net.gestores.sega.matw.MATWWS getMATWService() throws javax.xml.rpc.ServiceException;

    public net.gestores.sega.matw.MATWWS getMATWService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
