/**
 * BSNServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gaa9.bsn;

public class BSNServiceLocator extends org.apache.axis.client.Service implements com.gaa9.bsn.BSNService {

    public BSNServiceLocator() {
    }


    public BSNServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public BSNServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BSNService
    private java.lang.String BSNService_address = "https://test.gacn.es/bbws/BSNService";

    public java.lang.String getBSNServiceAddress() {
        return BSNService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BSNServiceWSDDServiceName = "BSNService";

    public java.lang.String getBSNServiceWSDDServiceName() {
        return BSNServiceWSDDServiceName;
    }

    public void setBSNServiceWSDDServiceName(java.lang.String name) {
        BSNServiceWSDDServiceName = name;
    }

    public com.gaa9.bsn.BSNWS getBSNService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BSNService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBSNService(endpoint);
    }

    public com.gaa9.bsn.BSNWS getBSNService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.gaa9.bsn.BSNWSBindingStub _stub = new com.gaa9.bsn.BSNWSBindingStub(portAddress, this);
            _stub.setPortName(getBSNServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBSNServiceEndpointAddress(java.lang.String address) {
        BSNService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.gaa9.bsn.BSNWS.class.isAssignableFrom(serviceEndpointInterface)) {
                com.gaa9.bsn.BSNWSBindingStub _stub = new com.gaa9.bsn.BSNWSBindingStub(new java.net.URL(BSNService_address), this);
                _stub.setPortName(getBSNServiceWSDDServiceName());
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
        if ("BSNService".equals(inputPortName)) {
            return getBSNService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://bsn.gaa9.com/", "BSNService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://bsn.gaa9.com/", "BSNService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BSNService".equals(portName)) {
            setBSNServiceEndpointAddress(address);
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
