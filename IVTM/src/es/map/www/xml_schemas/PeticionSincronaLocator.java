/**
 * PeticionSincronaLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.map.www.xml_schemas;

public class PeticionSincronaLocator extends org.apache.axis.client.Service implements es.map.www.xml_schemas.PeticionSincrona {

    public PeticionSincronaLocator() {
    }


    public PeticionSincronaLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PeticionSincronaLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PeticionSincrona
    private java.lang.String PeticionSincrona_address = "https://test.munimadrid.es:444/SBAE_026_InteroperabilidadInternet/services/PeticionSincrona";

    public java.lang.String getPeticionSincronaAddress() {
        return PeticionSincrona_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PeticionSincronaWSDDServiceName = "PeticionSincrona";

    public java.lang.String getPeticionSincronaWSDDServiceName() {
        return PeticionSincronaWSDDServiceName;
    }

    public void setPeticionSincronaWSDDServiceName(java.lang.String name) {
        PeticionSincronaWSDDServiceName = name;
    }

    public es.map.www.xml_schemas.PeticionPortType getPeticionSincrona() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PeticionSincrona_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPeticionSincrona(endpoint);
    }

    public es.map.www.xml_schemas.PeticionPortType getPeticionSincrona(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            es.map.www.xml_schemas.PeticionSincronaSoapBindingStub _stub = new es.map.www.xml_schemas.PeticionSincronaSoapBindingStub(portAddress, this);
            _stub.setPortName(getPeticionSincronaWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPeticionSincronaEndpointAddress(java.lang.String address) {
        PeticionSincrona_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (es.map.www.xml_schemas.PeticionPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                es.map.www.xml_schemas.PeticionSincronaSoapBindingStub _stub = new es.map.www.xml_schemas.PeticionSincronaSoapBindingStub(new java.net.URL(PeticionSincrona_address), this);
                _stub.setPortName(getPeticionSincronaWSDDServiceName());
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
        if ("PeticionSincrona".equals(inputPortName)) {
            return getPeticionSincrona();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.map.es/xml-schemas", "PeticionSincrona");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.map.es/xml-schemas", "PeticionSincrona"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PeticionSincrona".equals(portName)) {
            setPeticionSincronaEndpointAddress(address);
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
