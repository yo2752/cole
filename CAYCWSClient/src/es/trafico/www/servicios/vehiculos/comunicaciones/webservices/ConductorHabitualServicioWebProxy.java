package es.trafico.www.servicios.vehiculos.comunicaciones.webservices;

public class ConductorHabitualServicioWebProxy
		implements es.trafico.www.servicios.vehiculos.comunicaciones.webservices.ConductorHabitualServicioWeb {
	private String _endpoint = null;
	private es.trafico.www.servicios.vehiculos.comunicaciones.webservices.ConductorHabitualServicioWeb conductorHabitualServicioWeb = null;

	public ConductorHabitualServicioWebProxy() {
		_initConductorHabitualServicioWebProxy();
	}

	public ConductorHabitualServicioWebProxy(String endpoint) {
		_endpoint = endpoint;
		_initConductorHabitualServicioWebProxy();
	}

	private void _initConductorHabitualServicioWebProxy() {
		try {
			conductorHabitualServicioWeb = (new es.trafico.www.servicios.vehiculos.comunicaciones.webservices.ServiciosConductorHabitualLocator())
					.getCaycConductorHabitualSoap();
			if (conductorHabitualServicioWeb != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub) conductorHabitualServicioWeb)
							._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
				else
					_endpoint = (String) ((javax.xml.rpc.Stub) conductorHabitualServicioWeb)
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
		if (conductorHabitualServicioWeb != null)
			((javax.xml.rpc.Stub) conductorHabitualServicioWeb)._setProperty("javax.xml.rpc.service.endpoint.address",
					_endpoint);
	}

	public es.trafico.www.servicios.vehiculos.comunicaciones.webservices.ConductorHabitualServicioWeb getConductorHabitualServicioWeb() {
		if (conductorHabitualServicioWeb == null)
			_initConductorHabitualServicioWebProxy();
		return conductorHabitualServicioWeb;
	}

	public es.trafico.www.servicios.vehiculos.comunicaciones.webservices.respuesta.ResultadoComunicacion altaConductorHabitual(
			es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosConductorHabitual datosConductorHabitual,
			es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosDomicilio datosDomicilio,
			es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosVehiculo datosVehiculo,
			java.lang.String solicitud) throws java.rmi.RemoteException {
		if (conductorHabitualServicioWeb == null)
			_initConductorHabitualServicioWebProxy();
		return conductorHabitualServicioWeb.altaConductorHabitual(datosConductorHabitual, datosDomicilio, datosVehiculo,
				solicitud);
	}

	public es.trafico.www.servicios.vehiculos.comunicaciones.webservices.respuesta.ResultadoComunicacion modificacionConductorHabitual(
			es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosConductorHabitual datosConductorHabitual,
			es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosVehiculo datosVehiculo,
			java.lang.String solicitud) throws java.rmi.RemoteException {
		if (conductorHabitualServicioWeb == null)
			_initConductorHabitualServicioWebProxy();
		return conductorHabitualServicioWeb.modificacionConductorHabitual(datosConductorHabitual, datosVehiculo,
				solicitud);
	}

}