/**
 * PeticionPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.map.www.xml_schemas;

public interface PeticionPortType extends java.rmi.Remote {
    public es.map.scsp.esquemas.v2.respuesta.altaivtm.Respuesta peticionSincronaAltaIVTM(es.map.scsp.esquemas.v2.peticion.altaivtm.Peticion peticion,java.lang.String firma) throws java.rmi.RemoteException, es.map.www.scsp.esquemas.excepcionmap.ExcepcionMap;
    public es.map.scsp.esquemas.v2.respuesta.consultaivtm.Respuesta peticionSincronaConsultaIVTM(es.map.scsp.esquemas.v2.peticion.consultaivtm.Peticion peticion,java.lang.String firma) throws java.rmi.RemoteException, es.map.www.scsp.esquemas.excepcionmap.ExcepcionMap;
    public es.map.scsp.esquemas.v2.respuesta.modificacionivtm.Respuesta peticionSincronaModificacionIVTM(es.map.scsp.esquemas.v2.peticion.modificacionivtm.Peticion peticion,java.lang.String firma) throws java.rmi.RemoteException, es.map.www.scsp.esquemas.excepcionmap.ExcepcionMap;
}
