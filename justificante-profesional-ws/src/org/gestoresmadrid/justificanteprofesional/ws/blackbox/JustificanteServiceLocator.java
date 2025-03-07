/**
 * JustificanteServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gestoresmadrid.justificanteprofesional.ws.blackbox;

public class JustificanteServiceLocator extends org.apache.axis.client.Service implements org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteService {

    public JustificanteServiceLocator() {
    }


    public JustificanteServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public JustificanteServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for JustificanteService
    private java.lang.String JustificanteService_address = "https://test.gacn.es/bbws/JustificanteService";

    public java.lang.String getJustificanteServiceAddress() {
        return JustificanteService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String JustificanteServiceWSDDServiceName = "JustificanteService";

    public java.lang.String getJustificanteServiceWSDDServiceName() {
        return JustificanteServiceWSDDServiceName;
    }

    public void setJustificanteServiceWSDDServiceName(java.lang.String name) {
        JustificanteServiceWSDDServiceName = name;
    }

    public org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteWS getJustificanteService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(JustificanteService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getJustificanteService(endpoint);
    }

    public org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteWS getJustificanteService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteWSBindingStub _stub = new org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteWSBindingStub(portAddress, this);
            _stub.setPortName(getJustificanteServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setJustificanteServiceEndpointAddress(java.lang.String address) {
        JustificanteService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteWS.class.isAssignableFrom(serviceEndpointInterface)) {
                org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteWSBindingStub _stub = new org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteWSBindingStub(new java.net.URL(JustificanteService_address), this);
                _stub.setPortName(getJustificanteServiceWSDDServiceName());
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
        if ("JustificanteService".equals(inputPortName)) {
            return getJustificanteService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "JustificanteService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "JustificanteService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("JustificanteService".equals(portName)) {
            setJustificanteServiceEndpointAddress(address);
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
