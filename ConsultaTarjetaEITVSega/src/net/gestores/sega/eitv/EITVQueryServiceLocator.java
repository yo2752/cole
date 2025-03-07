/**
 * EITVQueryServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.gestores.sega.eitv;

public class EITVQueryServiceLocator extends org.apache.axis.client.Service implements net.gestores.sega.eitv.EITVQueryService {

    public EITVQueryServiceLocator() {
    }


    public EITVQueryServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EITVQueryServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for EITVQueryService
    private java.lang.String EITVQueryService_address = "https://217.130.244.72:8484/sega/EITVQueryService";

    public java.lang.String getEITVQueryServiceAddress() {
        return EITVQueryService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String EITVQueryServiceWSDDServiceName = "EITVQueryService";

    public java.lang.String getEITVQueryServiceWSDDServiceName() {
        return EITVQueryServiceWSDDServiceName;
    }

    public void setEITVQueryServiceWSDDServiceName(java.lang.String name) {
        EITVQueryServiceWSDDServiceName = name;
    }

    public net.gestores.sega.eitv.EITVQueryWS getEITVQueryService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EITVQueryService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEITVQueryService(endpoint);
    }

    public net.gestores.sega.eitv.EITVQueryWS getEITVQueryService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            net.gestores.sega.eitv.EITVQueryWSBindingStub _stub = new net.gestores.sega.eitv.EITVQueryWSBindingStub(portAddress, this);
            _stub.setPortName(getEITVQueryServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setEITVQueryServiceEndpointAddress(java.lang.String address) {
        EITVQueryService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (net.gestores.sega.eitv.EITVQueryWS.class.isAssignableFrom(serviceEndpointInterface)) {
                net.gestores.sega.eitv.EITVQueryWSBindingStub _stub = new net.gestores.sega.eitv.EITVQueryWSBindingStub(new java.net.URL(EITVQueryService_address), this);
                _stub.setPortName(getEITVQueryServiceWSDDServiceName());
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
        if ("EITVQueryService".equals(inputPortName)) {
            return getEITVQueryService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://sega.gestores.net/eitv", "EITVQueryService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://sega.gestores.net/eitv", "EITVQueryService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("EITVQueryService".equals(portName)) {
            setEITVQueryServiceEndpointAddress(address);
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
