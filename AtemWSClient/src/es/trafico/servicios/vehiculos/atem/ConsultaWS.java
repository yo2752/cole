/**
 * ConsultaWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.servicios.vehiculos.atem;

public interface ConsultaWS extends java.rmi.Remote {
    public java.lang.String consultaBastidor(java.lang.String tasa, java.lang.String bastidor) throws java.rmi.RemoteException;
    public java.lang.String consultaMasiva(java.lang.String tipo, java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String consultaMatricula(java.lang.String tasa, java.lang.String matricula) throws java.rmi.RemoteException;
    public java.lang.String consultaPersona(java.lang.String tasa, java.lang.String persona) throws java.rmi.RemoteException;
    public java.lang.String obtenerResultadoConsulta(java.lang.String referencia) throws java.rmi.RemoteException;
}
