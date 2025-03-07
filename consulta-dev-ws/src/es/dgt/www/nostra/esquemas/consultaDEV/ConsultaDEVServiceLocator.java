/**
 * ConsultaDEVServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.dgt.www.nostra.esquemas.consultaDEV;

public class ConsultaDEVServiceLocator extends org.apache.axis.client.Service implements es.dgt.www.nostra.esquemas.consultaDEV.ConsultaDEVService {

    public ConsultaDEVServiceLocator() {
    }


    public ConsultaDEVServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ConsultaDEVServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ConsultaDEVSoap0
    private java.lang.String ConsultaDEVSoap0_address = "https://sedeapl.dgt.gob.es:8080/WS_NTRA_INTER/ConsultaDEVService";

    public java.lang.String getConsultaDEVSoap0Address() {
        return ConsultaDEVSoap0_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ConsultaDEVSoap0WSDDServiceName = "ConsultaDEVSoap.0";

    public java.lang.String getConsultaDEVSoap0WSDDServiceName() {
        return ConsultaDEVSoap0WSDDServiceName;
    }

    public void setConsultaDEVSoap0WSDDServiceName(java.lang.String name) {
        ConsultaDEVSoap0WSDDServiceName = name;
    }

    public es.dgt.www.nostra.esquemas.consultaDEV.ConsultaDEVPortType getConsultaDEVSoap0() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ConsultaDEVSoap0_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getConsultaDEVSoap0(endpoint);
    }

    public es.dgt.www.nostra.esquemas.consultaDEV.ConsultaDEVPortType getConsultaDEVSoap0(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            es.dgt.www.nostra.esquemas.consultaDEV.ConsultaDEVSoapBindingStub _stub = new es.dgt.www.nostra.esquemas.consultaDEV.ConsultaDEVSoapBindingStub(portAddress, this);
            _stub.setPortName(getConsultaDEVSoap0WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setConsultaDEVSoap0EndpointAddress(java.lang.String address) {
        ConsultaDEVSoap0_address = address;
    }


    // Use to get a proxy class for ConsultaDEVSoap
    private java.lang.String ConsultaDEVSoap_address = "https://sedeapl.dgt.gob.es:8080/WS_NTRA_INTER/ConsultaDEVService";

    public java.lang.String getConsultaDEVSoapAddress() {
        return ConsultaDEVSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ConsultaDEVSoapWSDDServiceName = "ConsultaDEVSoap";

    public java.lang.String getConsultaDEVSoapWSDDServiceName() {
        return ConsultaDEVSoapWSDDServiceName;
    }

    public void setConsultaDEVSoapWSDDServiceName(java.lang.String name) {
        ConsultaDEVSoapWSDDServiceName = name;
    }

    public es.dgt.www.nostra.esquemas.consultaDEV.ConsultaDEVPortType getConsultaDEVSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ConsultaDEVSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getConsultaDEVSoap(endpoint);
    }

    public es.dgt.www.nostra.esquemas.consultaDEV.ConsultaDEVPortType getConsultaDEVSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            es.dgt.www.nostra.esquemas.consultaDEV.ConsultaDEVSoapBindingStub _stub = new es.dgt.www.nostra.esquemas.consultaDEV.ConsultaDEVSoapBindingStub(portAddress, this);
            _stub.setPortName(getConsultaDEVSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setConsultaDEVSoapEndpointAddress(java.lang.String address) {
        ConsultaDEVSoap_address = address;
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
            if (es.dgt.www.nostra.esquemas.consultaDEV.ConsultaDEVPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                es.dgt.www.nostra.esquemas.consultaDEV.ConsultaDEVSoapBindingStub _stub = new es.dgt.www.nostra.esquemas.consultaDEV.ConsultaDEVSoapBindingStub(new java.net.URL(ConsultaDEVSoap0_address), this);
                _stub.setPortName(getConsultaDEVSoap0WSDDServiceName());
                return _stub;
            }
            if (es.dgt.www.nostra.esquemas.consultaDEV.ConsultaDEVPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                es.dgt.www.nostra.esquemas.consultaDEV.ConsultaDEVSoapBindingStub _stub = new es.dgt.www.nostra.esquemas.consultaDEV.ConsultaDEVSoapBindingStub(new java.net.URL(ConsultaDEVSoap_address), this);
                _stub.setPortName(getConsultaDEVSoapWSDDServiceName());
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
        if ("ConsultaDEVSoap.0".equals(inputPortName)) {
            return getConsultaDEVSoap0();
        }
        else if ("ConsultaDEVSoap".equals(inputPortName)) {
            return getConsultaDEVSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV", "ConsultaDEVService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV", "ConsultaDEVSoap.0"));
            ports.add(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV", "ConsultaDEVSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ConsultaDEVSoap0".equals(portName)) {
            setConsultaDEVSoap0EndpointAddress(address);
        }
        else 
if ("ConsultaDEVSoap".equals(portName)) {
            setConsultaDEVSoapEndpointAddress(address);
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
