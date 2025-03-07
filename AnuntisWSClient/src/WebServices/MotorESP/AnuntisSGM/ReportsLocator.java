/**
 * ReportsLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package WebServices.MotorESP.AnuntisSGM;

public class ReportsLocator extends org.apache.axis.client.Service implements WebServices.MotorESP.AnuntisSGM.Reports {

    public ReportsLocator() {
    }


    public ReportsLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ReportsLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ReportsSoap
    private java.lang.String ReportsSoap_address = "http://www.coches.net:8111/WS/Reports.asmx";

    public java.lang.String getReportsSoapAddress() {
        return ReportsSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ReportsSoapWSDDServiceName = "ReportsSoap";

    public java.lang.String getReportsSoapWSDDServiceName() {
        return ReportsSoapWSDDServiceName;
    }

    public void setReportsSoapWSDDServiceName(java.lang.String name) {
        ReportsSoapWSDDServiceName = name;
    }

    public WebServices.MotorESP.AnuntisSGM.ReportsSoap getReportsSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ReportsSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getReportsSoap(endpoint);
    }

    public WebServices.MotorESP.AnuntisSGM.ReportsSoap getReportsSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            WebServices.MotorESP.AnuntisSGM.ReportsSoapStub _stub = new WebServices.MotorESP.AnuntisSGM.ReportsSoapStub(portAddress, this);
            _stub.setPortName(getReportsSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setReportsSoapEndpointAddress(java.lang.String address) {
        ReportsSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (WebServices.MotorESP.AnuntisSGM.ReportsSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                WebServices.MotorESP.AnuntisSGM.ReportsSoapStub _stub = new WebServices.MotorESP.AnuntisSGM.ReportsSoapStub(new java.net.URL(ReportsSoap_address), this);
                _stub.setPortName(getReportsSoapWSDDServiceName());
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
        if ("ReportsSoap".equals(inputPortName)) {
            return getReportsSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("AnuntisSGM.MotorESP.WebServices", "Reports");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("AnuntisSGM.MotorESP.WebServices", "ReportsSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ReportsSoap".equals(portName)) {
            setReportsSoapEndpointAddress(address);
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
