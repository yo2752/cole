/**
 * InteroperabilidadExternaServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.iam.sbae.sbintopexterna;

public class InteroperabilidadExternaServiceLocator extends org.apache.axis.client.Service implements es.iam.sbae.sbintopexterna.InteroperabilidadExternaService {

    public InteroperabilidadExternaServiceLocator() {
    }


    public InteroperabilidadExternaServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public InteroperabilidadExternaServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for InteroperabilidadExternaPort
    private java.lang.String InteroperabilidadExternaPort_address = "https://test.munimadrid.es:444/INTOP_SBExterna/InteroperabilidadExterna";

    public java.lang.String getInteroperabilidadExternaPortAddress() {
        return InteroperabilidadExternaPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String InteroperabilidadExternaPortWSDDServiceName = "InteroperabilidadExternaPort";

    public java.lang.String getInteroperabilidadExternaPortWSDDServiceName() {
        return InteroperabilidadExternaPortWSDDServiceName;
    }

    public void setInteroperabilidadExternaPortWSDDServiceName(java.lang.String name) {
        InteroperabilidadExternaPortWSDDServiceName = name;
    }

    public es.iam.sbae.sbintopexterna.InteroperabilidadExterna getInteroperabilidadExternaPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(InteroperabilidadExternaPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getInteroperabilidadExternaPort(endpoint);
    }

    public es.iam.sbae.sbintopexterna.InteroperabilidadExterna getInteroperabilidadExternaPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            es.iam.sbae.sbintopexterna.InteroperabilidadExternaPortBindingStub _stub = new es.iam.sbae.sbintopexterna.InteroperabilidadExternaPortBindingStub(portAddress, this);
            _stub.setPortName(getInteroperabilidadExternaPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setInteroperabilidadExternaPortEndpointAddress(java.lang.String address) {
        InteroperabilidadExternaPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (es.iam.sbae.sbintopexterna.InteroperabilidadExterna.class.isAssignableFrom(serviceEndpointInterface)) {
                es.iam.sbae.sbintopexterna.InteroperabilidadExternaPortBindingStub _stub = new es.iam.sbae.sbintopexterna.InteroperabilidadExternaPortBindingStub(new java.net.URL(InteroperabilidadExternaPort_address), this);
                _stub.setPortName(getInteroperabilidadExternaPortWSDDServiceName());
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
        if ("InteroperabilidadExternaPort".equals(inputPortName)) {
            return getInteroperabilidadExternaPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://sbintopexterna.sbae.iam.es", "InteroperabilidadExternaService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://sbintopexterna.sbae.iam.es", "InteroperabilidadExternaPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("InteroperabilidadExternaPort".equals(portName)) {
            setInteroperabilidadExternaPortEndpointAddress(address);
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
