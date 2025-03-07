/**
 * InformarEntidadesFinancierasWSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package src;

public class InformarEntidadesFinancierasWSServiceLocator extends org.apache.axis.client.Service implements src.InformarEntidadesFinancierasWSService {

    public InformarEntidadesFinancierasWSServiceLocator() {
    }


    public InformarEntidadesFinancierasWSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public InformarEntidadesFinancierasWSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for InformarEntidadesFinancierasWS
    private java.lang.String InformarEntidadesFinancierasWS_address = "https://www.e-matricula.com:443/SerialNumberService";

    public java.lang.String getInformarEntidadesFinancierasWSAddress() {
        return InformarEntidadesFinancierasWS_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String InformarEntidadesFinancierasWSWSDDServiceName = "InformarEntidadesFinancierasWS";

    public java.lang.String getInformarEntidadesFinancierasWSWSDDServiceName() {
        return InformarEntidadesFinancierasWSWSDDServiceName;
    }

    public void setInformarEntidadesFinancierasWSWSDDServiceName(java.lang.String name) {
        InformarEntidadesFinancierasWSWSDDServiceName = name;
    }

    public src.InformarEntidadesFinancierasWS getInformarEntidadesFinancierasWS() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(InformarEntidadesFinancierasWS_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getInformarEntidadesFinancierasWS(endpoint);
    }

    public src.InformarEntidadesFinancierasWS getInformarEntidadesFinancierasWS(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            src.InformarEntidadesFinancierasWSSoapBindingStub _stub = new src.InformarEntidadesFinancierasWSSoapBindingStub(portAddress, this);
            _stub.setPortName(getInformarEntidadesFinancierasWSWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setInformarEntidadesFinancierasWSEndpointAddress(java.lang.String address) {
        InformarEntidadesFinancierasWS_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (src.InformarEntidadesFinancierasWS.class.isAssignableFrom(serviceEndpointInterface)) {
                src.InformarEntidadesFinancierasWSSoapBindingStub _stub = new src.InformarEntidadesFinancierasWSSoapBindingStub(new java.net.URL(InformarEntidadesFinancierasWS_address), this);
                _stub.setPortName(getInformarEntidadesFinancierasWSWSDDServiceName());
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
        if ("InformarEntidadesFinancierasWS".equals(inputPortName)) {
            return getInformarEntidadesFinancierasWS();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://src", "InformarEntidadesFinancierasWSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://src", "InformarEntidadesFinancierasWS"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("InformarEntidadesFinancierasWS".equals(portName)) {
            setInformarEntidadesFinancierasWSEndpointAddress(address);
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
