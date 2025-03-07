/**
 * WSCNServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gi.infra.wscn.ws;

public class WSCNServiceLocator extends org.apache.axis.client.Service implements org.gi.infra.wscn.ws.WSCNService {

    public WSCNServiceLocator() {
    }


    public WSCNServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSCNServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSCNPort
    private java.lang.String WSCNPort_address = "https://ws.seg-social.gob.es/INFRWSCN/WSCNService";

    public java.lang.String getWSCNPortAddress() {
        return WSCNPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSCNPortWSDDServiceName = "WSCNPort";

    public java.lang.String getWSCNPortWSDDServiceName() {
        return WSCNPortWSDDServiceName;
    }

    public void setWSCNPortWSDDServiceName(java.lang.String name) {
        WSCNPortWSDDServiceName = name;
    }

    public org.gi.infra.wscn.ws.WSCN getWSCNPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSCNPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSCNPort(endpoint);
    }

    public org.gi.infra.wscn.ws.WSCN getWSCNPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.gi.infra.wscn.ws.WSCNPortBindingStub _stub = new org.gi.infra.wscn.ws.WSCNPortBindingStub(portAddress, this);
            _stub.setPortName(getWSCNPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSCNPortEndpointAddress(java.lang.String address) {
        WSCNPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.gi.infra.wscn.ws.WSCN.class.isAssignableFrom(serviceEndpointInterface)) {
                org.gi.infra.wscn.ws.WSCNPortBindingStub _stub = new org.gi.infra.wscn.ws.WSCNPortBindingStub(new java.net.URL(WSCNPort_address), this);
                _stub.setPortName(getWSCNPortWSDDServiceName());
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
        if ("WSCNPort".equals(inputPortName)) {
            return getWSCNPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "WSCNService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "WSCNPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSCNPort".equals(portName)) {
            setWSCNPortEndpointAddress(address);
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
