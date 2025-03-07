/**
 * CTITServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class CTITServiceLocator extends org.apache.axis.client.Service implements com.gescogroup.blackbox.CTITService {

    public CTITServiceLocator() {
    }


    public CTITServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CTITServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CTITService
    private java.lang.String CTITService_address = "https://test.gacn.es/bbws/CTITService";

    public java.lang.String getCTITServiceAddress() {
        return CTITService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CTITServiceWSDDServiceName = "CTITService";

    public java.lang.String getCTITServiceWSDDServiceName() {
        return CTITServiceWSDDServiceName;
    }

    public void setCTITServiceWSDDServiceName(java.lang.String name) {
        CTITServiceWSDDServiceName = name;
    }

    public com.gescogroup.blackbox.CTITWS getCTITService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CTITService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCTITService(endpoint);
    }

    public com.gescogroup.blackbox.CTITWS getCTITService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.gescogroup.blackbox.CTITWSBindingStub _stub = new com.gescogroup.blackbox.CTITWSBindingStub(portAddress, this);
            _stub.setPortName(getCTITServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCTITServiceEndpointAddress(java.lang.String address) {
        CTITService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.gescogroup.blackbox.CTITWS.class.isAssignableFrom(serviceEndpointInterface)) {
                com.gescogroup.blackbox.CTITWSBindingStub _stub = new com.gescogroup.blackbox.CTITWSBindingStub(new java.net.URL(CTITService_address), this);
                _stub.setPortName(getCTITServiceWSDDServiceName());
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
        if ("CTITService".equals(inputPortName)) {
            return getCTITService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "CTITService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "CTITService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CTITService".equals(portName)) {
            setCTITServiceEndpointAddress(address);
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
