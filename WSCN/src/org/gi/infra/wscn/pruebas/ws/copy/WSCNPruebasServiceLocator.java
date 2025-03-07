/**
 * WSCNPruebasServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gi.infra.wscn.pruebas.ws.copy;

import org.springframework.stereotype.Service;

@Service
public class WSCNPruebasServiceLocator extends org.apache.axis.client.Service implements org.gi.infra.wscn.pruebas.ws.WSCNPruebasService {

    public WSCNPruebasServiceLocator() {
    }


    public WSCNPruebasServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSCNPruebasServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSCNPruebasPort
    private java.lang.String WSCNPruebasPort_address = "https://ws.seg-social.gob.es/INFRWSCN_Pruebas/WSCNPruebasService";

    public java.lang.String getWSCNPruebasPortAddress() {
        return WSCNPruebasPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSCNPruebasPortWSDDServiceName = "WSCNPruebasPort";

    public java.lang.String getWSCNPruebasPortWSDDServiceName() {
        return WSCNPruebasPortWSDDServiceName;
    }

    public void setWSCNPruebasPortWSDDServiceName(java.lang.String name) {
        WSCNPruebasPortWSDDServiceName = name;
    }

    public org.gi.infra.wscn.pruebas.ws.WSCNPruebas getWSCNPruebasPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSCNPruebasPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSCNPruebasPort(endpoint);
    }

    public org.gi.infra.wscn.pruebas.ws.WSCNPruebas getWSCNPruebasPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.gi.infra.wscn.pruebas.ws.WSCNPruebasPortBindingStub _stub = new org.gi.infra.wscn.pruebas.ws.WSCNPruebasPortBindingStub(portAddress, this);
            _stub.setPortName(getWSCNPruebasPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSCNPruebasPortEndpointAddress(java.lang.String address) {
        WSCNPruebasPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.gi.infra.wscn.pruebas.ws.WSCNPruebas.class.isAssignableFrom(serviceEndpointInterface)) {
                org.gi.infra.wscn.pruebas.ws.WSCNPruebasPortBindingStub _stub = new org.gi.infra.wscn.pruebas.ws.WSCNPruebasPortBindingStub(new java.net.URL(WSCNPruebasPort_address), this);
                _stub.setPortName(getWSCNPruebasPortWSDDServiceName());
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
        if ("WSCNPruebasPort".equals(inputPortName)) {
            return getWSCNPruebasPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "WSCNPruebasService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "WSCNPruebasPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSCNPruebasPort".equals(portName)) {
            setWSCNPruebasPortEndpointAddress(address);
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
