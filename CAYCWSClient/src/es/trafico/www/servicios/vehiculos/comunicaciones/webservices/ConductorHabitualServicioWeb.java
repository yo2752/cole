/**
 * ConductorHabitualServicioWeb.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.www.servicios.vehiculos.comunicaciones.webservices;

public interface ConductorHabitualServicioWeb extends java.rmi.Remote {
    public es.trafico.www.servicios.vehiculos.comunicaciones.webservices.respuesta.ResultadoComunicacion altaConductorHabitual(es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosConductorHabitual datosConductorHabitual, es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosDomicilio datosDomicilio, es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosVehiculo datosVehiculo, java.lang.String solicitud) throws java.rmi.RemoteException;
    public es.trafico.www.servicios.vehiculos.comunicaciones.webservices.respuesta.ResultadoComunicacion modificacionConductorHabitual(es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosConductorHabitual datosConductorHabitual, es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosVehiculo datosVehiculo, java.lang.String solicitud) throws java.rmi.RemoteException;
}
