/**
 * ObtenerMatriculasWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.matriculasWS.servicioWeb;

public interface ObtenerMatriculasWS extends java.rmi.Remote {
    public com.matriculasWS.beans.DatosSalida consultaMatriculas(java.lang.String authUser, java.lang.String authPass, com.matriculasWS.beans.DatosEntrada datosEntrada) throws java.rmi.RemoteException;
}
