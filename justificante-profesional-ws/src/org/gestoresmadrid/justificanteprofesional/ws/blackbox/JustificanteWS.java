/**
 * JustificanteWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gestoresmadrid.justificanteprofesional.ws.blackbox;

public interface JustificanteWS extends java.rmi.Remote {
    public org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteRespuestaSOAP descargarJustificante(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException;
    public org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteRespuestaSOAP emitirJustificante(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException;
    public org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteRespuestaSOAP verificarJustificante(java.lang.String arg0) throws java.rmi.RemoteException;
}
