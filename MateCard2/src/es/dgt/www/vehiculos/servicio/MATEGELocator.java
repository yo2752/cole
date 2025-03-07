/**
 * MATEGELocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.dgt.www.vehiculos.servicio;

public class MATEGELocator extends org.apache.axis.client.Service implements es.dgt.www.vehiculos.servicio.MATEGE {

    public MATEGELocator() {
    }


    public MATEGELocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public MATEGELocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for MATEGE
    private java.lang.String MATEGE_address = "https://apl-cpd.dgt.es:443/dgtMategeWS/services/MATEGE";

    public java.lang.String getMATEGEAddress() {
        return MATEGE_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String MATEGEWSDDServiceName = "MATEGE";

    public java.lang.String getMATEGEWSDDServiceName() {
        return MATEGEWSDDServiceName;
    }

    public void setMATEGEWSDDServiceName(java.lang.String name) {
        MATEGEWSDDServiceName = name;
    }

    public es.dgt.www.vehiculos.servicio.MATEGE_Colegios_WS getMATEGE() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MATEGE_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMATEGE(endpoint);
    }

    public es.dgt.www.vehiculos.servicio.MATEGE_Colegios_WS getMATEGE(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            es.dgt.www.vehiculos.servicio.MATEGEStub _stub = new es.dgt.www.vehiculos.servicio.MATEGEStub(portAddress, this);
            _stub.setPortName(getMATEGEWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMATEGEEndpointAddress(java.lang.String address) {
        MATEGE_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (es.dgt.www.vehiculos.servicio.MATEGE_Colegios_WS.class.isAssignableFrom(serviceEndpointInterface)) {
                es.dgt.www.vehiculos.servicio.MATEGEStub _stub = new es.dgt.www.vehiculos.servicio.MATEGEStub(new java.net.URL(MATEGE_address), this);
                _stub.setPortName(getMATEGEWSDDServiceName());
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
        if ("MATEGE".equals(inputPortName)) {
            return getMATEGE();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.dgt.es/vehiculos/servicio", "MATEGE");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.dgt.es/vehiculos/servicio", "MATEGE"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("MATEGE".equals(portName)) {
            setMATEGEEndpointAddress(address);
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
