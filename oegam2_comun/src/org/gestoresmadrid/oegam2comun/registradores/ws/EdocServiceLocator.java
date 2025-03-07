/**
 * EdocServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gestoresmadrid.oegam2comun.registradores.ws;

public class EdocServiceLocator extends org.apache.axis.client.Service implements EdocService {

    public EdocServiceLocator() {
    }


    public EdocServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EdocServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for edoc
    private java.lang.String edoc_address = "https://10.255.255.110/WSEdoc/services/EdocService";

    public java.lang.String getedocAddress() {
        return edoc_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String edocWSDDServiceName = "edoc";

    public java.lang.String getedocWSDDServiceName() {
        return edocWSDDServiceName;
    }

    public void setedocWSDDServiceName(java.lang.String name) {
        edocWSDDServiceName = name;
    }

    public Edoc getedoc() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(edoc_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getedoc(endpoint);
    }

    public Edoc getedoc(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            EdocSoapBindingStub _stub = new EdocSoapBindingStub(portAddress, this);
            _stub.setPortName(getedocWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setedocEndpointAddress(java.lang.String address) {
        edoc_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (Edoc.class.isAssignableFrom(serviceEndpointInterface)) {
                EdocSoapBindingStub _stub = new EdocSoapBindingStub(new java.net.URL(edoc_address), this);
                _stub.setPortName(getedocWSDDServiceName());
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
        if ("edoc".equals(inputPortName)) {
            return getedoc();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://registradores.org/ws/edoc", "EdocService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://registradores.org/ws/edoc", "edoc"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("edoc".equals(portName)) {
            setedocEndpointAddress(address);
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
