/**
 * BSNService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gaa9.bsn;

public interface BSNService extends javax.xml.rpc.Service {
    public java.lang.String getBSNServiceAddress();

    public com.gaa9.bsn.BSNWS getBSNService() throws javax.xml.rpc.ServiceException;

    public com.gaa9.bsn.BSNWS getBSNService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
