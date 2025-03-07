/**
 * ObtenerMatriculasWSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.matriculasWS.servicioWeb;

public class ObtenerMatriculasWSServiceLocator extends org.apache.axis.client.Service implements com.matriculasWS.servicioWeb.ObtenerMatriculasWSService {

    public ObtenerMatriculasWSServiceLocator() {
    }


    public ObtenerMatriculasWSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ObtenerMatriculasWSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ObtenerMatriculasWS
    private java.lang.String ObtenerMatriculasWS_address = "http://oegampre.gestoresmadrid.org/MatriculasWS/services/ObtenerMatriculasWS";
//    private java.lang.String ObtenerMatriculasWS_address = "http://localhost:8081/MatriculasWS/services/ObtenerMatriculasWS";

    public java.lang.String getObtenerMatriculasWSAddress() {
        return ObtenerMatriculasWS_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ObtenerMatriculasWSWSDDServiceName = "ObtenerMatriculasWS";

    public java.lang.String getObtenerMatriculasWSWSDDServiceName() {
        return ObtenerMatriculasWSWSDDServiceName;
    }

    public void setObtenerMatriculasWSWSDDServiceName(java.lang.String name) {
        ObtenerMatriculasWSWSDDServiceName = name;
    }

    public com.matriculasWS.servicioWeb.ObtenerMatriculasWS getObtenerMatriculasWS() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ObtenerMatriculasWS_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getObtenerMatriculasWS(endpoint);
    }

    public com.matriculasWS.servicioWeb.ObtenerMatriculasWS getObtenerMatriculasWS(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.matriculasWS.servicioWeb.ObtenerMatriculasWSSoapBindingStub _stub = new com.matriculasWS.servicioWeb.ObtenerMatriculasWSSoapBindingStub(portAddress, this);
            _stub.setPortName(getObtenerMatriculasWSWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setObtenerMatriculasWSEndpointAddress(java.lang.String address) {
        ObtenerMatriculasWS_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.matriculasWS.servicioWeb.ObtenerMatriculasWS.class.isAssignableFrom(serviceEndpointInterface)) {
                com.matriculasWS.servicioWeb.ObtenerMatriculasWSSoapBindingStub _stub = new com.matriculasWS.servicioWeb.ObtenerMatriculasWSSoapBindingStub(new java.net.URL(ObtenerMatriculasWS_address), this);
                _stub.setPortName(getObtenerMatriculasWSWSDDServiceName());
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
        if ("ObtenerMatriculasWS".equals(inputPortName)) {
            return getObtenerMatriculasWS();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://servicioWeb.matriculasWS.com", "ObtenerMatriculasWSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://servicioWeb.matriculasWS.com", "ObtenerMatriculasWS"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ObtenerMatriculasWS".equals(portName)) {
            setObtenerMatriculasWSEndpointAddress(address);
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
