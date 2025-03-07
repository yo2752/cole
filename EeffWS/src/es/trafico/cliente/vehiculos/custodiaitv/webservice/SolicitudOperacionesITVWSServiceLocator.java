/**
 * SolicitudOperacionesITVWSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.cliente.vehiculos.custodiaitv.webservice;

import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class SolicitudOperacionesITVWSServiceLocator extends org.apache.axis.client.Service implements es.trafico.cliente.vehiculos.custodiaitv.webservice.SolicitudOperacionesITVWSService {

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
    public SolicitudOperacionesITVWSServiceLocator() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }


    public SolicitudOperacionesITVWSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public SolicitudOperacionesITVWSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
   }

    // Use to get a proxy class for SolicitudOperacionesITVWS
    private java.lang.String SolicitudOperacionesITVWS_address = gestorPropiedades.valorPropertie("eeff.direccionWS");

    public java.lang.String getSolicitudOperacionesITVWSAddress() {
        return SolicitudOperacionesITVWS_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SolicitudOperacionesITVWSWSDDServiceName = "SolicitudOperacionesITVWS";

    public java.lang.String getSolicitudOperacionesITVWSWSDDServiceName() {
        return SolicitudOperacionesITVWSWSDDServiceName;
    }

    public void setSolicitudOperacionesITVWSWSDDServiceName(java.lang.String name) {
        SolicitudOperacionesITVWSWSDDServiceName = name;
    }

    public es.trafico.cliente.vehiculos.custodiaitv.webservice.SolicitudOperacionesITVWS getSolicitudOperacionesITVWS() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SolicitudOperacionesITVWS_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSolicitudOperacionesITVWS(endpoint);
    }

    public es.trafico.cliente.vehiculos.custodiaitv.webservice.SolicitudOperacionesITVWS getSolicitudOperacionesITVWS(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            es.trafico.cliente.vehiculos.custodiaitv.webservice.SolicitudOperacionesITVWSSoapBindingStub _stub = new es.trafico.cliente.vehiculos.custodiaitv.webservice.SolicitudOperacionesITVWSSoapBindingStub(portAddress, this);
            _stub.setPortName(getSolicitudOperacionesITVWSWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSolicitudOperacionesITVWSEndpointAddress(java.lang.String address) {
        SolicitudOperacionesITVWS_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (es.trafico.cliente.vehiculos.custodiaitv.webservice.SolicitudOperacionesITVWS.class.isAssignableFrom(serviceEndpointInterface)) {
                es.trafico.cliente.vehiculos.custodiaitv.webservice.SolicitudOperacionesITVWSSoapBindingStub _stub = new es.trafico.cliente.vehiculos.custodiaitv.webservice.SolicitudOperacionesITVWSSoapBindingStub(new java.net.URL(SolicitudOperacionesITVWS_address), this);
                _stub.setPortName(getSolicitudOperacionesITVWSWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SolicitudOperacionesITVWS".equals(inputPortName)) {
            return getSolicitudOperacionesITVWS();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservice.custodiaitv.vehiculos.cliente.trafico.es", "SolicitudOperacionesITVWSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservice.custodiaitv.vehiculos.cliente.trafico.es", "SolicitudOperacionesITVWS"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SolicitudOperacionesITVWS".equals(portName)) {
            setSolicitudOperacionesITVWSEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
