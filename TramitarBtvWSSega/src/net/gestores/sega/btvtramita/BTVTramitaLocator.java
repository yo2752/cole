/**
 * BTVTramitaLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.gestores.sega.btvtramita;

public class BTVTramitaLocator extends org.apache.axis.client.Service implements net.gestores.sega.btvtramita.BTVTramita {

    public BTVTramitaLocator() {
    }


    public BTVTramitaLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public BTVTramitaLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BTVTramita
    private java.lang.String BTVTramita_address = "https://217.130.244.72:8484/sega/BTVTramita";

    public java.lang.String getBTVTramitaAddress() {
        return BTVTramita_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BTVTramitaWSDDServiceName = "BTVTramita";

    public java.lang.String getBTVTramitaWSDDServiceName() {
        return BTVTramitaWSDDServiceName;
    }

    public void setBTVTramitaWSDDServiceName(java.lang.String name) {
        BTVTramitaWSDDServiceName = name;
    }

    public net.gestores.sega.btvtramita.BTVWS getBTVTramita() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BTVTramita_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBTVTramita(endpoint);
    }

    public net.gestores.sega.btvtramita.BTVWS getBTVTramita(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            net.gestores.sega.btvtramita.BTVWSBindingStub _stub = new net.gestores.sega.btvtramita.BTVWSBindingStub(portAddress, this);
            _stub.setPortName(getBTVTramitaWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBTVTramitaEndpointAddress(java.lang.String address) {
        BTVTramita_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (net.gestores.sega.btvtramita.BTVWS.class.isAssignableFrom(serviceEndpointInterface)) {
                net.gestores.sega.btvtramita.BTVWSBindingStub _stub = new net.gestores.sega.btvtramita.BTVWSBindingStub(new java.net.URL(BTVTramita_address), this);
                _stub.setPortName(getBTVTramitaWSDDServiceName());
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
        if ("BTVTramita".equals(inputPortName)) {
            return getBTVTramita();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://sega.gestores.net/btvtramita", "BTVTramita");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://sega.gestores.net/btvtramita", "BTVTramita"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BTVTramita".equals(portName)) {
            setBTVTramitaEndpointAddress(address);
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
