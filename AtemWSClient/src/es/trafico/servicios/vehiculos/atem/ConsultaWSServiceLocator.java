/**
 * ConsultaWSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.servicios.vehiculos.atem;

public class ConsultaWSServiceLocator extends org.apache.axis.client.Service implements es.trafico.servicios.vehiculos.atem.ConsultaWSService {

    public ConsultaWSServiceLocator() {
    }


    public ConsultaWSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ConsultaWSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ConsultaWS
    private java.lang.String ConsultaWS_address = "http://pre-datapower.trafico.es:8080/WS_ATEM_INET/ConsultaWSService";

    public java.lang.String getConsultaWSAddress() {
        return ConsultaWS_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ConsultaWSWSDDServiceName = "ConsultaWS";

    public java.lang.String getConsultaWSWSDDServiceName() {
        return ConsultaWSWSDDServiceName;
    }

    public void setConsultaWSWSDDServiceName(java.lang.String name) {
        ConsultaWSWSDDServiceName = name;
    }

    public es.trafico.servicios.vehiculos.atem.ConsultaWS getConsultaWS() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ConsultaWS_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getConsultaWS(endpoint);
    }

    public es.trafico.servicios.vehiculos.atem.ConsultaWS getConsultaWS(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            es.trafico.servicios.vehiculos.atem.ConsultaWSSoapBindingStub _stub = new es.trafico.servicios.vehiculos.atem.ConsultaWSSoapBindingStub(portAddress, this);
            _stub.setPortName(getConsultaWSWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setConsultaWSEndpointAddress(java.lang.String address) {
        ConsultaWS_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (es.trafico.servicios.vehiculos.atem.ConsultaWS.class.isAssignableFrom(serviceEndpointInterface)) {
                es.trafico.servicios.vehiculos.atem.ConsultaWSSoapBindingStub _stub = new es.trafico.servicios.vehiculos.atem.ConsultaWSSoapBindingStub(new java.net.URL(ConsultaWS_address), this);
                _stub.setPortName(getConsultaWSWSDDServiceName());
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
        if ("ConsultaWS".equals(inputPortName)) {
            return getConsultaWS();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://atem.vehiculos.servicios.trafico.es", "ConsultaWSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://atem.vehiculos.servicios.trafico.es", "ConsultaWS"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ConsultaWS".equals(portName)) {
            setConsultaWSEndpointAddress(address);
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
