/**
 * RegistroServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.consejogestores.sercon.ws.model.integracion;

public class RegistroServiceServiceLocator extends org.apache.axis.client.Service implements net.consejogestores.sercon.ws.model.integracion.RegistroServiceService {

    public RegistroServiceServiceLocator() {
    }


    public RegistroServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public RegistroServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for RegistroService
    private java.lang.String RegistroService_address = "http://172.26.40.64/serconws/RegistroService";

    public java.lang.String getRegistroServiceAddress() {
        return RegistroService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String RegistroServiceWSDDServiceName = "RegistroService";

    public java.lang.String getRegistroServiceWSDDServiceName() {
        return RegistroServiceWSDDServiceName;
    }

    public void setRegistroServiceWSDDServiceName(java.lang.String name) {
        RegistroServiceWSDDServiceName = name;
    }

    public net.consejogestores.sercon.ws.model.integracion.RegistroService getRegistroService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(RegistroService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getRegistroService(endpoint);
    }

    public net.consejogestores.sercon.ws.model.integracion.RegistroService getRegistroService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            net.consejogestores.sercon.ws.model.integracion.RegistroServiceSoapBindingStub _stub = new net.consejogestores.sercon.ws.model.integracion.RegistroServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getRegistroServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setRegistroServiceEndpointAddress(java.lang.String address) {
        RegistroService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (net.consejogestores.sercon.ws.model.integracion.RegistroService.class.isAssignableFrom(serviceEndpointInterface)) {
                net.consejogestores.sercon.ws.model.integracion.RegistroServiceSoapBindingStub _stub = new net.consejogestores.sercon.ws.model.integracion.RegistroServiceSoapBindingStub(new java.net.URL(RegistroService_address), this);
                _stub.setPortName(getRegistroServiceWSDDServiceName());
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
        if ("RegistroService".equals(inputPortName)) {
            return getRegistroService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://integracion.model.ws.sercon.consejogestores.net", "RegistroServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://integracion.model.ws.sercon.consejogestores.net", "RegistroService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("RegistroService".equals(portName)) {
            setRegistroServiceEndpointAddress(address);
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
