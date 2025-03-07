/**
 * ServiciosArrendamiento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.www.servicios.vehiculos.comunicaciones.webservices;

public interface ServiciosArrendamiento extends javax.xml.rpc.Service {
    public java.lang.String getCaycArrendamientoSoapAddress();

    public es.trafico.www.servicios.vehiculos.comunicaciones.webservices.ArrendamientoServicioWeb getCaycArrendamientoSoap() throws javax.xml.rpc.ServiceException;

    public es.trafico.www.servicios.vehiculos.comunicaciones.webservices.ArrendamientoServicioWeb getCaycArrendamientoSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
