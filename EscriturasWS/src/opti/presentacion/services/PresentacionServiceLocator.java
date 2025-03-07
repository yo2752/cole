/**
 * PresentacionServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package opti.presentacion.services;

public class PresentacionServiceLocator extends org.apache.axis.client.Service implements opti.presentacion.services.PresentacionService {

    public PresentacionServiceLocator() {
    }


    public PresentacionServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PresentacionServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PresentacionServiceHttpSoap12Endpoint
    private java.lang.String PresentacionServiceHttpSoap12Endpoint_address = "http://deswebservices.madrid.org/opti_ws_presentacion/services/PresentacionService.PresentacionServiceHttpSoap12Endpoint/";

    public java.lang.String getPresentacionServiceHttpSoap12EndpointAddress() {
        return PresentacionServiceHttpSoap12Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PresentacionServiceHttpSoap12EndpointWSDDServiceName = "PresentacionServiceHttpSoap12Endpoint";

    public java.lang.String getPresentacionServiceHttpSoap12EndpointWSDDServiceName() {
        return PresentacionServiceHttpSoap12EndpointWSDDServiceName;
    }

    public void setPresentacionServiceHttpSoap12EndpointWSDDServiceName(java.lang.String name) {
        PresentacionServiceHttpSoap12EndpointWSDDServiceName = name;
    }

    public opti.presentacion.services.PresentacionServicePortType getPresentacionServiceHttpSoap12Endpoint() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PresentacionServiceHttpSoap12Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPresentacionServiceHttpSoap12Endpoint(endpoint);
    }

    public opti.presentacion.services.PresentacionServicePortType getPresentacionServiceHttpSoap12Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            opti.presentacion.services.PresentacionServiceSoap12BindingStub _stub = new opti.presentacion.services.PresentacionServiceSoap12BindingStub(portAddress, this);
            _stub.setPortName(getPresentacionServiceHttpSoap12EndpointWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPresentacionServiceHttpSoap12EndpointEndpointAddress(java.lang.String address) {
        PresentacionServiceHttpSoap12Endpoint_address = address;
    }


    // Use to get a proxy class for PresentacionServiceHttpsSoap11Endpoint
    private java.lang.String PresentacionServiceHttpsSoap11Endpoint_address = "https://deswebservices.madrid.org/opti_ws_presentacion/services/PresentacionService.PresentacionServiceHttpsSoap11Endpoint/";

    public java.lang.String getPresentacionServiceHttpsSoap11EndpointAddress() {
        return PresentacionServiceHttpsSoap11Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PresentacionServiceHttpsSoap11EndpointWSDDServiceName = "PresentacionServiceHttpsSoap11Endpoint";

    public java.lang.String getPresentacionServiceHttpsSoap11EndpointWSDDServiceName() {
        return PresentacionServiceHttpsSoap11EndpointWSDDServiceName;
    }

    public void setPresentacionServiceHttpsSoap11EndpointWSDDServiceName(java.lang.String name) {
        PresentacionServiceHttpsSoap11EndpointWSDDServiceName = name;
    }

    public opti.presentacion.services.PresentacionServicePortType getPresentacionServiceHttpsSoap11Endpoint() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PresentacionServiceHttpsSoap11Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPresentacionServiceHttpsSoap11Endpoint(endpoint);
    }

    public opti.presentacion.services.PresentacionServicePortType getPresentacionServiceHttpsSoap11Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            opti.presentacion.services.PresentacionServiceSoap11BindingStub _stub = new opti.presentacion.services.PresentacionServiceSoap11BindingStub(portAddress, this);
            _stub.setPortName(getPresentacionServiceHttpsSoap11EndpointWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPresentacionServiceHttpsSoap11EndpointEndpointAddress(java.lang.String address) {
        PresentacionServiceHttpsSoap11Endpoint_address = address;
    }


    // Use to get a proxy class for PresentacionServiceHttpsSoap12Endpoint
    private java.lang.String PresentacionServiceHttpsSoap12Endpoint_address = "https://deswebservices.madrid.org/opti_ws_presentacion/services/PresentacionService.PresentacionServiceHttpsSoap12Endpoint/";

    public java.lang.String getPresentacionServiceHttpsSoap12EndpointAddress() {
        return PresentacionServiceHttpsSoap12Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PresentacionServiceHttpsSoap12EndpointWSDDServiceName = "PresentacionServiceHttpsSoap12Endpoint";

    public java.lang.String getPresentacionServiceHttpsSoap12EndpointWSDDServiceName() {
        return PresentacionServiceHttpsSoap12EndpointWSDDServiceName;
    }

    public void setPresentacionServiceHttpsSoap12EndpointWSDDServiceName(java.lang.String name) {
        PresentacionServiceHttpsSoap12EndpointWSDDServiceName = name;
    }

    public opti.presentacion.services.PresentacionServicePortType getPresentacionServiceHttpsSoap12Endpoint() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PresentacionServiceHttpsSoap12Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPresentacionServiceHttpsSoap12Endpoint(endpoint);
    }

    public opti.presentacion.services.PresentacionServicePortType getPresentacionServiceHttpsSoap12Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            opti.presentacion.services.PresentacionServiceSoap12BindingStub _stub = new opti.presentacion.services.PresentacionServiceSoap12BindingStub(portAddress, this);
            _stub.setPortName(getPresentacionServiceHttpsSoap12EndpointWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPresentacionServiceHttpsSoap12EndpointEndpointAddress(java.lang.String address) {
        PresentacionServiceHttpsSoap12Endpoint_address = address;
    }


    // Use to get a proxy class for PresentacionServiceHttpSoap11Endpoint
    private java.lang.String PresentacionServiceHttpSoap11Endpoint_address = "http://deswebservices.madrid.org/opti_ws_presentacion/services/PresentacionService.PresentacionServiceHttpSoap11Endpoint/";

    public java.lang.String getPresentacionServiceHttpSoap11EndpointAddress() {
        return PresentacionServiceHttpSoap11Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PresentacionServiceHttpSoap11EndpointWSDDServiceName = "PresentacionServiceHttpSoap11Endpoint";

    public java.lang.String getPresentacionServiceHttpSoap11EndpointWSDDServiceName() {
        return PresentacionServiceHttpSoap11EndpointWSDDServiceName;
    }

    public void setPresentacionServiceHttpSoap11EndpointWSDDServiceName(java.lang.String name) {
        PresentacionServiceHttpSoap11EndpointWSDDServiceName = name;
    }

    public opti.presentacion.services.PresentacionServicePortType getPresentacionServiceHttpSoap11Endpoint() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PresentacionServiceHttpSoap11Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPresentacionServiceHttpSoap11Endpoint(endpoint);
    }

    public opti.presentacion.services.PresentacionServicePortType getPresentacionServiceHttpSoap11Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            opti.presentacion.services.PresentacionServiceSoap11BindingStub _stub = new opti.presentacion.services.PresentacionServiceSoap11BindingStub(portAddress, this);
            _stub.setPortName(getPresentacionServiceHttpSoap11EndpointWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPresentacionServiceHttpSoap11EndpointEndpointAddress(java.lang.String address) {
        PresentacionServiceHttpSoap11Endpoint_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (opti.presentacion.services.PresentacionServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                opti.presentacion.services.PresentacionServiceSoap12BindingStub _stub = new opti.presentacion.services.PresentacionServiceSoap12BindingStub(new java.net.URL(PresentacionServiceHttpSoap12Endpoint_address), this);
                _stub.setPortName(getPresentacionServiceHttpSoap12EndpointWSDDServiceName());
                return _stub;
            }
            if (opti.presentacion.services.PresentacionServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                opti.presentacion.services.PresentacionServiceSoap11BindingStub _stub = new opti.presentacion.services.PresentacionServiceSoap11BindingStub(new java.net.URL(PresentacionServiceHttpsSoap11Endpoint_address), this);
                _stub.setPortName(getPresentacionServiceHttpsSoap11EndpointWSDDServiceName());
                return _stub;
            }
            if (opti.presentacion.services.PresentacionServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                opti.presentacion.services.PresentacionServiceSoap12BindingStub _stub = new opti.presentacion.services.PresentacionServiceSoap12BindingStub(new java.net.URL(PresentacionServiceHttpsSoap12Endpoint_address), this);
                _stub.setPortName(getPresentacionServiceHttpsSoap12EndpointWSDDServiceName());
                return _stub;
            }
            if (opti.presentacion.services.PresentacionServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                opti.presentacion.services.PresentacionServiceSoap11BindingStub _stub = new opti.presentacion.services.PresentacionServiceSoap11BindingStub(new java.net.URL(PresentacionServiceHttpSoap11Endpoint_address), this);
                _stub.setPortName(getPresentacionServiceHttpSoap11EndpointWSDDServiceName());
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
        if ("PresentacionServiceHttpSoap12Endpoint".equals(inputPortName)) {
            return getPresentacionServiceHttpSoap12Endpoint();
        }
        else if ("PresentacionServiceHttpsSoap11Endpoint".equals(inputPortName)) {
            return getPresentacionServiceHttpsSoap11Endpoint();
        }
        else if ("PresentacionServiceHttpsSoap12Endpoint".equals(inputPortName)) {
            return getPresentacionServiceHttpsSoap12Endpoint();
        }
        else if ("PresentacionServiceHttpSoap11Endpoint".equals(inputPortName)) {
            return getPresentacionServiceHttpSoap11Endpoint();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://services.presentacion.opti", "PresentacionService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://services.presentacion.opti", "PresentacionServiceHttpSoap12Endpoint"));
            ports.add(new javax.xml.namespace.QName("http://services.presentacion.opti", "PresentacionServiceHttpsSoap11Endpoint"));
            ports.add(new javax.xml.namespace.QName("http://services.presentacion.opti", "PresentacionServiceHttpsSoap12Endpoint"));
            ports.add(new javax.xml.namespace.QName("http://services.presentacion.opti", "PresentacionServiceHttpSoap11Endpoint"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PresentacionServiceHttpSoap12Endpoint".equals(portName)) {
            setPresentacionServiceHttpSoap12EndpointEndpointAddress(address);
        }
        else 
if ("PresentacionServiceHttpsSoap11Endpoint".equals(portName)) {
            setPresentacionServiceHttpsSoap11EndpointEndpointAddress(address);
        }
        else 
if ("PresentacionServiceHttpsSoap12Endpoint".equals(portName)) {
            setPresentacionServiceHttpsSoap12EndpointEndpointAddress(address);
        }
        else 
if ("PresentacionServiceHttpSoap11Endpoint".equals(portName)) {
            setPresentacionServiceHttpSoap11EndpointEndpointAddress(address);
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
