/**
 * WSCNService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gi.infra.wscn.ws;

public interface WSCNService extends javax.xml.rpc.Service {
    public java.lang.String getWSCNPortAddress();

    public org.gi.infra.wscn.ws.WSCN getWSCNPort() throws javax.xml.rpc.ServiceException;

    public org.gi.infra.wscn.ws.WSCN getWSCNPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
