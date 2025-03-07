/**
 * PagoItvServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.servicios.sic.ingresos.productos.webservices.itv;

public class PagoItvServiceLocator extends org.apache.axis.client.Service implements es.trafico.servicios.sic.ingresos.productos.webservices.itv.PagoItvService {

    public PagoItvServiceLocator() {
    }


    public PagoItvServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PagoItvServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PagoItvPort
    private java.lang.String PagoItvPort_address = "http://pre-datapower.trafico.es:8080/WS_INDI_MASIVOS/PagoTasasMasivoWS";

    public java.lang.String getPagoItvPortAddress() {
        return PagoItvPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PagoItvPortWSDDServiceName = "PagoItvPort";

    public java.lang.String getPagoItvPortWSDDServiceName() {
        return PagoItvPortWSDDServiceName;
    }

    public void setPagoItvPortWSDDServiceName(java.lang.String name) {
        PagoItvPortWSDDServiceName = name;
    }

    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.PagoItv getPagoItvPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PagoItvPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPagoItvPort(endpoint);
    }

    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.PagoItv getPagoItvPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            es.trafico.servicios.sic.ingresos.productos.webservices.itv.PagoItvPortBindingStub _stub = new es.trafico.servicios.sic.ingresos.productos.webservices.itv.PagoItvPortBindingStub(portAddress, this);
            _stub.setPortName(getPagoItvPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPagoItvPortEndpointAddress(java.lang.String address) {
        PagoItvPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (es.trafico.servicios.sic.ingresos.productos.webservices.itv.PagoItv.class.isAssignableFrom(serviceEndpointInterface)) {
                es.trafico.servicios.sic.ingresos.productos.webservices.itv.PagoItvPortBindingStub _stub = new es.trafico.servicios.sic.ingresos.productos.webservices.itv.PagoItvPortBindingStub(new java.net.URL(PagoItvPort_address), this);
                _stub.setPortName(getPagoItvPortWSDDServiceName());
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
        if ("PagoItvPort".equals(inputPortName)) {
            return getPagoItvPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "PagoItvService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "PagoItvPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PagoItvPort".equals(portName)) {
            setPagoItvPortEndpointAddress(address);
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
