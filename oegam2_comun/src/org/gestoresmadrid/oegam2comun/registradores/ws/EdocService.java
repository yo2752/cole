/**
 * EdocService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gestoresmadrid.oegam2comun.registradores.ws;

public interface EdocService extends javax.xml.rpc.Service {
    public java.lang.String getedocAddress();

    public Edoc getedoc() throws javax.xml.rpc.ServiceException;

    public Edoc getedoc(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
