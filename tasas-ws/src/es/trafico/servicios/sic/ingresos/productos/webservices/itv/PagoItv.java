/**
 * PagoItv.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.servicios.sic.ingresos.productos.webservices.itv;

public interface PagoItv extends java.rmi.Remote {
    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.ResultadoConsultaTasas comprarTasasPorNRC(es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudPagoTasasNRC arg0) throws java.rmi.RemoteException, es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean;
    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosPedidoCompleto obtenerDatosPedido(es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudConsultaTasas solicitud) throws java.rmi.RemoteException, es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean;
    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.DocumentoJustificantePago obtenerJustificantePago(es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudJustificantePago solicitud) throws java.rmi.RemoteException, es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean;
    public java.lang.String obtenerNumeroJustificante() throws java.rmi.RemoteException, es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean;
    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.ResultadoOperacionCompraTasas realizarPagoTasas(es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudPagoTasas arg0) throws java.rmi.RemoteException, es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean;
}
