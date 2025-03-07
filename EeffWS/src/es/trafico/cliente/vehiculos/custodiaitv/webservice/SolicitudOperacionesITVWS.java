/**
 * SolicitudOperacionesITVWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.cliente.vehiculos.custodiaitv.webservice;

public interface SolicitudOperacionesITVWS extends java.rmi.Remote {
    public es.trafico.servicios.vehiculos.custodiaitv.beans.OperacionEITVRespuestaDTO anotarEITV(java.lang.String localeId, java.lang.String peticion) throws java.rmi.RemoteException;
    public es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaBloquearDTO bloquearEITV(java.lang.String localeId, java.lang.String peticion) throws java.rmi.RemoteException;
    public es.trafico.servicios.vehiculos.custodiaitv.beans.ConsultaEITVRespuestaDTO consultarEITV(java.lang.String localeId, java.lang.String peticion) throws java.rmi.RemoteException;
    public es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaBloquearDTO desbloquearEITV(java.lang.String localeId, java.lang.String peticion) throws java.rmi.RemoteException;
    public es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaEntregarDTO entregarEITV(java.lang.String localeId, java.lang.String peticion) throws java.rmi.RemoteException;
    public es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaLiberarDTO liberarEITV(java.lang.String localeId, java.lang.String peticion) throws java.rmi.RemoteException;
}
