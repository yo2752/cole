/**
 * BTVConsultaLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class BTVConsultaLocator extends org.apache.axis.client.Service implements com.gescogroup.blackbox.BTVConsulta {

    public BTVConsultaLocator() {
    }


    public BTVConsultaLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public BTVConsultaLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BTVConsulta
    private java.lang.String BTVConsulta_address = "https://pysea.gescolabs.com/bbws/BTVConsulta";

    public java.lang.String getBTVConsultaAddress() {
        return BTVConsulta_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BTVConsultaWSDDServiceName = "BTVConsulta";

    public java.lang.String getBTVConsultaWSDDServiceName() {
        return BTVConsultaWSDDServiceName;
    }

    public void setBTVConsultaWSDDServiceName(java.lang.String name) {
        BTVConsultaWSDDServiceName = name;
    }

    public com.gescogroup.blackbox.BTVWS getBTVConsulta() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BTVConsulta_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBTVConsulta(endpoint);
    }

    public com.gescogroup.blackbox.BTVWS getBTVConsulta(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.gescogroup.blackbox.BTVWSBindingStub _stub = new com.gescogroup.blackbox.BTVWSBindingStub(portAddress, this);
            _stub.setPortName(getBTVConsultaWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBTVConsultaEndpointAddress(java.lang.String address) {
        BTVConsulta_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.gescogroup.blackbox.BTVWS.class.isAssignableFrom(serviceEndpointInterface)) {
                com.gescogroup.blackbox.BTVWSBindingStub _stub = new com.gescogroup.blackbox.BTVWSBindingStub(new java.net.URL(BTVConsulta_address), this);
                _stub.setPortName(getBTVConsultaWSDDServiceName());
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
        if ("BTVConsulta".equals(inputPortName)) {
            return getBTVConsulta();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "BTVConsulta");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "BTVConsulta"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BTVConsulta".equals(portName)) {
            setBTVConsultaEndpointAddress(address);
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
