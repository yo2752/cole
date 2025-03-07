/**
 * ArrendamientoServicioWeb.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.www.servicios.vehiculos.comunicaciones.webservices;

public interface ArrendamientoServicioWeb extends java.rmi.Remote {
	public es.trafico.www.servicios.vehiculos.comunicaciones.webservices.respuesta.ResultadoComunicacion altaArrendamiento(es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosArrendamiento datosArrendamiento, es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosPersonaCompleta datosPersona, es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosDomicilio datosDomicilio, es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosVehiculo datosVehiculo, java.lang.String solicitud) throws java.rmi.RemoteException;
	public es.trafico.www.servicios.vehiculos.comunicaciones.webservices.respuesta.ResultadoComunicacion modificacionArrendamiento(es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosArrendamiento datosArrendamiento, es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosVehiculo datosVehiculo, java.lang.String solicitud) throws java.rmi.RemoteException;
}