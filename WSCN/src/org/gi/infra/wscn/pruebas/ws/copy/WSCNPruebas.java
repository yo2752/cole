/**
 * WSCNPruebas.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gi.infra.wscn.pruebas.ws.copy;

public interface WSCNPruebas extends java.rmi.Remote {
    public org.gi.infra.wscn.pruebas.ws.ListadoNotificaciones consultarListadoNotificaciones(int rol, java.lang.String identificadorPoderdante, int codigoSiguienteNotificacionPropia, int codigoSiguienteNotificacionAutorizadoRED, int codigoSiguienteNotificacionApoderado) throws java.rmi.RemoteException, org.gi.infra.wscn.pruebas.ws.WSCNPruebasExcepcionSistema;
    public org.gi.infra.wscn.pruebas.ws.AcuseNotificacion solicitarAcuseNotificacion(int rol, java.lang.String identificadorPoderdante, int codigoNotificacion, boolean esDeAceptacion) throws java.rmi.RemoteException, org.gi.infra.wscn.pruebas.ws.WSCNPruebasExcepcionSistema;
    public org.gi.infra.wscn.pruebas.ws.NotificacionRecuperada enviarAcuseNotificacion(int rol, java.lang.String identificadorPoderdante, byte[] xmlAcuseFirmado) throws java.rmi.RemoteException, org.gi.infra.wscn.pruebas.ws.WSCNPruebasExcepcionSistema;
    public org.gi.infra.wscn.pruebas.ws.NotificacionRecuperada verNotificacionAceptada(int rol, java.lang.String identificadorPoderdante, int codigoNotificacion) throws java.rmi.RemoteException, org.gi.infra.wscn.pruebas.ws.WSCNPruebasExcepcionSistema;
    public org.gi.infra.wscn.pruebas.ws.ListadoPoderdantes solicitarPoderdantes() throws java.rmi.RemoteException, org.gi.infra.wscn.pruebas.ws.WSCNPruebasExcepcionSistema;
}
