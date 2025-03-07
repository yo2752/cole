package es.trafico.www.servicios.vehiculos.comunicaciones.webservices;

public class ArrendamientoServicioWebProxy
		implements es.trafico.www.servicios.vehiculos.comunicaciones.webservices.ArrendamientoServicioWeb {
	private String _endpoint = null;
	private es.trafico.www.servicios.vehiculos.comunicaciones.webservices.ArrendamientoServicioWeb arrendamientoServicioWeb = null;

	public ArrendamientoServicioWebProxy() {
		_initArrendamientoServicioWebProxy();
	}

	public ArrendamientoServicioWebProxy(String endpoint) {
		_endpoint = endpoint;
		_initArrendamientoServicioWebProxy();
	}

	private void _initArrendamientoServicioWebProxy() {
		try {
			arrendamientoServicioWeb = (new es.trafico.www.servicios.vehiculos.comunicaciones.webservices.ServiciosArrendamientoLocator())
					.getCaycArrendamientoSoap();
			if (arrendamientoServicioWeb != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub) arrendamientoServicioWeb)
							._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
				else
					_endpoint = (String) ((javax.xml.rpc.Stub) arrendamientoServicioWeb)
							._getProperty("javax.xml.rpc.service.endpoint.address");
			}

		} catch (javax.xml.rpc.ServiceException serviceException) {
		}
	}

	public String getEndpoint() {
		return _endpoint;
	}

	public void setEndpoint(String endpoint) {
		_endpoint = endpoint;
		if (arrendamientoServicioWeb != null)
			((javax.xml.rpc.Stub) arrendamientoServicioWeb)._setProperty("javax.xml.rpc.service.endpoint.address",
					_endpoint);

	}

	public es.trafico.www.servicios.vehiculos.comunicaciones.webservices.ArrendamientoServicioWeb getArrendamientoServicioWeb() {
		if (arrendamientoServicioWeb == null)
			_initArrendamientoServicioWebProxy();
		return arrendamientoServicioWeb;
	}

	public es.trafico.www.servicios.vehiculos.comunicaciones.webservices.respuesta.ResultadoComunicacion altaArrendamiento(
			es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosArrendamiento datosArrendamiento,
			es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosPersonaCompleta datosPersona,
			es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosDomicilio datosDomicilio,
			es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosVehiculo datosVehiculo,
			java.lang.String solicitud) throws java.rmi.RemoteException {
		if (arrendamientoServicioWeb == null)
			_initArrendamientoServicioWebProxy();
		return arrendamientoServicioWeb.altaArrendamiento(datosArrendamiento, datosPersona, datosDomicilio,
				datosVehiculo, solicitud);
	}

	public es.trafico.www.servicios.vehiculos.comunicaciones.webservices.respuesta.ResultadoComunicacion modificacionArrendamiento(
			es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosArrendamiento datosArrendamiento,
			es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosVehiculo datosVehiculo,
			java.lang.String solicitud) throws java.rmi.RemoteException {
		if (arrendamientoServicioWeb == null)
			_initArrendamientoServicioWebProxy();
		return arrendamientoServicioWeb.modificacionArrendamiento(datosArrendamiento, datosVehiculo, solicitud);
	}

}