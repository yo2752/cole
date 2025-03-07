/**
 * ServiciosConductorHabitualLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.www.servicios.vehiculos.comunicaciones.webservices;

public class ServiciosConductorHabitualLocator extends org.apache.axis.client.Service
		implements es.trafico.www.servicios.vehiculos.comunicaciones.webservices.ServiciosConductorHabitual {

	public ServiciosConductorHabitualLocator() {
	}

	public ServiciosConductorHabitualLocator(org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public ServiciosConductorHabitualLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName)
			throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for CaycConductorHabitualSoap
	private java.lang.String CaycConductorHabitualSoap_address = "http://pr-apls-prep.dgt.es:8080/WS_CAYC/ServiciosConductorHabitual";

	public java.lang.String getCaycConductorHabitualSoapAddress() {
		return CaycConductorHabitualSoap_address;
	}

	// The WSDD service name defaults to the port name.
	private java.lang.String CaycConductorHabitualSoapWSDDServiceName = "CaycConductorHabitualSoap";

	public java.lang.String getCaycConductorHabitualSoapWSDDServiceName() {
		return CaycConductorHabitualSoapWSDDServiceName;
	}

	public void setCaycConductorHabitualSoapWSDDServiceName(java.lang.String name) {
		CaycConductorHabitualSoapWSDDServiceName = name;
	}

	public es.trafico.www.servicios.vehiculos.comunicaciones.webservices.ConductorHabitualServicioWeb getCaycConductorHabitualSoap()
			throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(CaycConductorHabitualSoap_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getCaycConductorHabitualSoap(endpoint);
	}

	public es.trafico.www.servicios.vehiculos.comunicaciones.webservices.ConductorHabitualServicioWeb getCaycConductorHabitualSoap(
			java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
		try {
			es.trafico.www.servicios.vehiculos.comunicaciones.webservices.CaycConductorHabitualSoapBindingStub _stub = new es.trafico.www.servicios.vehiculos.comunicaciones.webservices.CaycConductorHabitualSoapBindingStub(
					portAddress, this);
			_stub.setPortName(getCaycConductorHabitualSoapWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setCaycConductorHabitualSoapEndpointAddress(java.lang.String address) {
		CaycConductorHabitualSoap_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has no
	 * port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
		try {
			if (es.trafico.www.servicios.vehiculos.comunicaciones.webservices.ConductorHabitualServicioWeb.class
					.isAssignableFrom(serviceEndpointInterface)) {
				es.trafico.www.servicios.vehiculos.comunicaciones.webservices.CaycConductorHabitualSoapBindingStub _stub = new es.trafico.www.servicios.vehiculos.comunicaciones.webservices.CaycConductorHabitualSoapBindingStub(
						new java.net.URL(CaycConductorHabitualSoap_address), this);
				_stub.setPortName(getCaycConductorHabitualSoapWSDDServiceName());
				return _stub;
			}
		} catch (java.lang.Throwable t) {
			throw new javax.xml.rpc.ServiceException(t);
		}
		throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  "
				+ (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation. If this service has no
	 * port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		java.lang.String inputPortName = portName.getLocalPart();
		if ("CaycConductorHabitualSoap".equals(inputPortName)) {
			return getCaycConductorHabitualSoap();
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName("http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices",
				"ServiciosConductorHabitual");
	}

	private java.util.HashSet ports = null;

	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet();
			ports.add(new javax.xml.namespace.QName(
					"http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices",
					"CaycConductorHabitualSoap"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(java.lang.String portName, java.lang.String address)
			throws javax.xml.rpc.ServiceException {

		if ("CaycConductorHabitualSoap".equals(portName)) {
			setCaycConductorHabitualSoapEndpointAddress(address);
		} else { // Unknown Port Name
			throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address)
			throws javax.xml.rpc.ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}

}