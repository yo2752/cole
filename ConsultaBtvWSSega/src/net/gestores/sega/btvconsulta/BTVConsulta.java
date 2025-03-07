/**
 * BTVConsulta.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.gestores.sega.btvconsulta;

public interface BTVConsulta extends javax.xml.rpc.Service {
    public java.lang.String getBTVConsultaAddress();

    public net.gestores.sega.btvconsulta.BTVWS getBTVConsulta() throws javax.xml.rpc.ServiceException;

    public net.gestores.sega.btvconsulta.BTVWS getBTVConsulta(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
