/**
 * MATEServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class MATEServiceLocator extends org.apache.axis.client.Service implements com.gescogroup.blackbox.MATEService {

    public MATEServiceLocator() {
    }


    public MATEServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public MATEServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for MATEService
    private java.lang.String MATEService_address = "https://192.168.72.115:8443/bbws/MATEService";

    public java.lang.String getMATEServiceAddress() {
        return MATEService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String MATEServiceWSDDServiceName = "MATEService";

    public java.lang.String getMATEServiceWSDDServiceName() {
        return MATEServiceWSDDServiceName;
    }

    public void setMATEServiceWSDDServiceName(java.lang.String name) {
        MATEServiceWSDDServiceName = name;
    }

    public com.gescogroup.blackbox.MATEWS getMATEService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MATEService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMATEService(endpoint);
    }

    public com.gescogroup.blackbox.MATEWS getMATEService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.gescogroup.blackbox.MATEWSBindingStub _stub = new com.gescogroup.blackbox.MATEWSBindingStub(portAddress, this);
            _stub.setPortName(getMATEServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMATEServiceEndpointAddress(java.lang.String address) {
        MATEService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.gescogroup.blackbox.MATEWS.class.isAssignableFrom(serviceEndpointInterface)) {
                com.gescogroup.blackbox.MATEWSBindingStub _stub = new com.gescogroup.blackbox.MATEWSBindingStub(new java.net.URL(MATEService_address), this);
                _stub.setPortName(getMATEServiceWSDDServiceName());
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
        if ("MATEService".equals(inputPortName)) {
            return getMATEService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "MATEService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "MATEService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("MATEService".equals(portName)) {
            setMATEServiceEndpointAddress(address);
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
