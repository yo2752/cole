package org.gestoresmadrid.oegam2comun.licenciasCam.model.ws.service.impl;

import org.gestoresmadrid.oegam2comun.licenciasCam.model.ws.service.ServicioLicBDCRestWS;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.LicenciasCamRest;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.LicenciasCamValidacionesRest;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.request.ValidarDireccion;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.Salida;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos.BloquePaisFin;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos.BloquePoblacionFin;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos.BloqueProvinciaFin;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos.DatosDireccionFin;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos.SalidaOkDTO;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoValidacionDireccionBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcDireccionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioLicBDCRestWSImpl implements ServicioLicBDCRestWS {

	private static final long serialVersionUID = 7018394627719037968L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLicBDCRestWSImpl.class);

	@Autowired
	LicenciasCamRest licenciasCamRest;

	@Autowired
	LicenciasCamValidacionesRest licenciasValCamRest;

	public ResultadoValidacionDireccionBean validarDireccionRest(LcDireccionDto direccion) {
		ResultadoValidacionDireccionBean resultado = new ResultadoValidacionDireccionBean(Boolean.FALSE);
		try {
			ValidarDireccion request = crearRequest(direccion);
			resultado = llamadaRest(request);
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de realizar la validaci�n de la direcci�n en Licencias CAM, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar la validaci�n de la direcci�n en Licencias CAM");
		}
		return resultado;
	}

	public ResultadoValidacionDireccionBean validarDireccionCompletaRest(LcDireccionDto direccion) {
		ResultadoValidacionDireccionBean resultado = new ResultadoValidacionDireccionBean(Boolean.FALSE);
		try {
			ValidarDireccion request = crearRequestDirCompleta(direccion);
			resultado = llamadaRest(request);
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de realizar la validaci�n de la direcci�n completa en Licencias CAM, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar la validaci�n de la direcci�n completa en Licencias CAM");
		}
		return resultado;
	}

	private ResultadoValidacionDireccionBean llamadaRest(ValidarDireccion request) {
		ResultadoValidacionDireccionBean resultado = new ResultadoValidacionDireccionBean(Boolean.FALSE);
		try {
			resultado = licenciasValCamRest.validacionDireccion(request);
			Salida response = (Salida) resultado.getAttachment("RESPONSE");
			resultado = gestionarRespuesta(response);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar la llamada REST de Registro de Solicitud de Licencias Cam, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar la llamada REST de Registro de Solicitud de Licencias Cam");

		}
		return resultado;
	}

	private ValidarDireccion crearRequest(LcDireccionDto direccion) {
		ValidarDireccion request = new ValidarDireccion();
		// TODO: en el emplazamiento no hay pais ni provincia ni pueblo
		// request.setNomPais("ESPA�A");
		// request.setNomProvincia("MADRID");
		// request.setNomPueblo("MADRID");
		request.setNomClase(direccion.getTipoVia());
		request.setNomVial(direccion.getNombreVia());
		request.setNomApp(direccion.getTipoNumeracion());

		if (direccion.getNumCalle() != null) {
			request.setNumApp(direccion.getNumCalle().toString());
		}

		request.setCalApp(direccion.getCalificador());
		request.setEscalera(direccion.getEscalera());
		request.setPlanta(direccion.getPlanta());
		request.setPuerta(direccion.getPuerta());
		request.setIntercambioBDC(direccion.getClaseDireccion());
		return request;
	}

	private ValidarDireccion crearRequestDirCompleta(LcDireccionDto direccion) {
		ValidarDireccion request = new ValidarDireccion();
		request.setNomPais(direccion.getPais());
		request.setNomProvincia(direccion.getProvincia());
		request.setNomPueblo(direccion.getPoblacion());
		request.setEscalera(direccion.getEscalera());
		request.setPlanta(direccion.getPlanta());
		request.setPuerta(direccion.getPuerta());
		request.setNomClase(direccion.getTipoVia());
		request.setNomVial(direccion.getNombreVia());
		request.setNomApp(direccion.getTipoNumeracion());
		if (direccion.getNumCalle() != null) {
			request.setNumApp(direccion.getNumCalle().toString());
		}
		request.setCalApp(direccion.getCalificador());

		request.setIntercambioBDC(direccion.getClaseDireccion());
		return request;
	}

	private ResultadoValidacionDireccionBean gestionarRespuesta(Salida response) {
		ResultadoValidacionDireccionBean resultado = new ResultadoValidacionDireccionBean(Boolean.FALSE);
		try {
			if (response != null) {
				if (response.getEstadoGlobal() != null) {
					if (response.getEstadoGlobal() == 0) {
						resultado.setMensaje("Validaci�n direcci�n correcta");
					} else if (response.getEstadoGlobal() == -1) {
						if (response.getSalidaOkDTO() != null) {
							resultado = evaluarPais(response.getSalidaOkDTO());
						}
					} else if (response.getEstadoGlobal() == -2) {
						resultado.setError(Boolean.TRUE);
						if (response.getSalidaErrorDTO() != null) {
							resultado.setMensaje(response.getSalidaErrorDTO().getDescripcionError());
						} else {
							resultado.setMensaje("Petici�n mal formulada");
						}
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No existe ningun estado conocido para la validaci�n de la direcci�n");
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No existe ningun estado para la validaci�n de la direcci�n");
				}
			}
		} catch (Exception e) {
			log.error("Error en la respuesta de la validaci�n de direcci�n");
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error en la respuesta de la validaci�n de direcci�n");
		}
		return resultado;
	}

	private ResultadoValidacionDireccionBean evaluarPais(SalidaOkDTO salida) {
		ResultadoValidacionDireccionBean resultado = new ResultadoValidacionDireccionBean(Boolean.FALSE);
		try {
			if (salida.getBloquedireccion() != null && salida.getBloquedireccion().getDatosDireccion() != null && salida.getBloquedireccion().getDatosDireccion().getEstadoPais() != null) {
				if (salida.getBloquedireccion().getDatosDireccion().getEstadoPais() == 0) {
					if (salida.getBloquedireccion().getDatosDireccion().getDatosDireccionFin() != null) {
						resultado = evaluarProvincia(salida.getBloquedireccion().getDatosDireccion().getDatosDireccionFin());
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No se puede seguir evaluando la validaci�n desde el pa�s");
					}
				} else if (salida.getBloquedireccion().getDatosDireccion().getEstadoPais() == 1) {
					resultado.setErrorPais(Boolean.TRUE);
					if (salida.getBloquedireccion().getDatosDireccion().getDatosDireccionNoFin() != null && salida.getBloquedireccion().getDatosDireccion().getDatosDireccionNoFin().getPaises() != null
							&& salida.getBloquedireccion().getDatosDireccion().getDatosDireccionNoFin().getPaises().getPais() != null && !salida.getBloquedireccion().getDatosDireccion()
									.getDatosDireccionNoFin().getPaises().getPais().isEmpty()) {
						resultado.setMensaje("Error al informar el pa�s, existen algunas sugerencias");
						resultado.setPaises(salida.getBloquedireccion().getDatosDireccion().getDatosDireccionNoFin().getPaises().getPais());
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Error al informar el pa�s, pero no hay lista informativa de pa�ses para informar");
					}
				} else if (salida.getBloquedireccion().getDatosDireccion().getEstadoPais() == -2) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("C�digo error al procesar el pa�s");
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No existe ningun estado conocido para la validaci�n del pais");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existe ningun estado para la validaci�n del pais");
			}
		} catch (Exception e) {
			log.error("Error al evaluar el pa�s en la validaci�n de direcci�n");
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al evaluar el pa�s en la validaci�n de direcci�n");
		}
		return resultado;
	}

	private ResultadoValidacionDireccionBean evaluarProvincia(DatosDireccionFin datosDireccionFin) {
		ResultadoValidacionDireccionBean resultado = new ResultadoValidacionDireccionBean(Boolean.FALSE);
		try {
			if (datosDireccionFin.getBloquePais() != null && datosDireccionFin.getBloquePais().getEstadoProvincia() != null) {
				if (datosDireccionFin.getBloquePais().getEstadoProvincia() == 0) {
					if (datosDireccionFin.getBloquePais().getBloquePaisFin() != null) {
						resultado = evaluarPoblacion(datosDireccionFin.getBloquePais().getBloquePaisFin());
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No se puede seguir evaluando la validaci�n desde la provincia");
					}
				} else if (datosDireccionFin.getBloquePais().getEstadoProvincia() == 1) {
					resultado.setErrorProvincia(Boolean.TRUE);
					if (datosDireccionFin.getBloquePais().getBloquePaisNoFin() != null && datosDireccionFin.getBloquePais().getBloquePaisNoFin().getProvincias() != null && datosDireccionFin
							.getBloquePais().getBloquePaisNoFin().getProvincias().getProvincia() != null && !datosDireccionFin.getBloquePais().getBloquePaisNoFin().getProvincias().getProvincia()
									.isEmpty()) {
						resultado.setMensaje("Error al informar la provincia, existen algunas sugerencias");
						resultado.setProvincias(datosDireccionFin.getBloquePais().getBloquePaisNoFin().getProvincias().getProvincia());
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Error al informar la provincia, pero no hay lista informativa de provincias para informar");
					}
				} else if (datosDireccionFin.getBloquePais().getEstadoProvincia() == -2) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("C�digo error al procesar la provincia");
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No existe ningun estado conocido para la validaci�n de la provincia");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existe ningun estado para la validaci�n de la provincia");
			}
		} catch (Exception e) {
			log.error("Error al evaluar la provincia en la validaci�n de direcci�n");
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al evaluar la provincia en la validaci�n de direcci�n");
		}
		return resultado;
	}

	private ResultadoValidacionDireccionBean evaluarPoblacion(BloquePaisFin bloquePaisFin) {
		ResultadoValidacionDireccionBean resultado = new ResultadoValidacionDireccionBean(Boolean.FALSE);
		try {
			if (bloquePaisFin.getBloqueProvincia() != null && bloquePaisFin.getBloqueProvincia().getEstadoPoblacion() != null) {
				if (bloquePaisFin.getBloqueProvincia().getEstadoPoblacion() == 0) {
					if (bloquePaisFin.getBloqueProvincia().getBloqueProvinciaFin() != null) {
						resultado = evaluarVial(bloquePaisFin.getBloqueProvincia().getBloqueProvinciaFin());
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No se puede seguir evaluando la validaci�n desde la poblaci�n");
					}
				} else if (bloquePaisFin.getBloqueProvincia().getEstadoPoblacion() == 1) {
					resultado.setErrorPoblacion(Boolean.TRUE);
					if (bloquePaisFin.getBloqueProvincia().getBloqueProvinciaNoFin() != null && bloquePaisFin.getBloqueProvincia().getBloqueProvinciaNoFin().getPoblaciones() != null && bloquePaisFin
							.getBloqueProvincia().getBloqueProvinciaNoFin().getPoblaciones().getPoblacion() != null && !bloquePaisFin.getBloqueProvincia().getBloqueProvinciaNoFin().getPoblaciones()
									.getPoblacion().isEmpty()) {
						resultado.setMensaje("Error al informar la poblaci�n, existen algunas sugerencias");
						resultado.setPoblaciones(bloquePaisFin.getBloqueProvincia().getBloqueProvinciaNoFin().getPoblaciones().getPoblacion());
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Error al informar la poblaci�n, pero no hay lista informativa de poblaciones para informar");
					}
				} else if (bloquePaisFin.getBloqueProvincia().getEstadoPoblacion() == -2) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("C�digo error al procesar la poblaci�n");
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No existe ningun estado conocido para la validaci�n de la poblaci�n");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existe ningun estado para la validaci�n de la poblaci�n");
			}
		} catch (Exception e) {
			log.error("Error al evaluar la poblaci�n en la validaci�n de direcci�n");
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al evaluar la poblaci�n en la validaci�n de direcci�n");
		}
		return resultado;
	}

	private ResultadoValidacionDireccionBean evaluarVial(BloqueProvinciaFin bloqueProvinciaFin) {
		ResultadoValidacionDireccionBean resultado = new ResultadoValidacionDireccionBean(Boolean.FALSE);
		try {
			if (bloqueProvinciaFin.getBloquePoblacion() != null && bloqueProvinciaFin.getBloquePoblacion().getEstadoVial() != null) {
				if (bloqueProvinciaFin.getBloquePoblacion().getEstadoVial() == 0) {
					if (bloqueProvinciaFin.getBloquePoblacion().getBloquePoblacionFin() != null) {
						resultado = evaluarNumero(bloqueProvinciaFin.getBloquePoblacion().getBloquePoblacionFin());
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No se puede seguir evaluando la validaci�n desde la v�a");
					}
				} else if (bloqueProvinciaFin.getBloquePoblacion().getEstadoVial() == 1) {
					resultado.setErrorVial(Boolean.TRUE);
					if (bloqueProvinciaFin.getBloquePoblacion().getBloquePoblacionNoFin() != null && bloqueProvinciaFin.getBloquePoblacion().getBloquePoblacionNoFin().getViales() != null
							&& bloqueProvinciaFin.getBloquePoblacion().getBloquePoblacionNoFin().getViales().getVial() != null && !bloqueProvinciaFin.getBloquePoblacion().getBloquePoblacionNoFin()
									.getViales().getVial().isEmpty()) {
						resultado.setMensaje("Error al informar la v�a, existen algunas sugerencias");
						resultado.setViales(bloqueProvinciaFin.getBloquePoblacion().getBloquePoblacionNoFin().getViales().getVial());
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Error al informar la v�a, pero no hay lista informativa de pa�ses para informar");
					}
				} else if (bloqueProvinciaFin.getBloquePoblacion().getEstadoVial() == -2) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("C�digo error al procesar la v�a");
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No existe ningun estado conocido para la validaci�n de la v�a");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existe ningun estado para la validaci�n de la v�a");
			}
		} catch (Exception e) {
			log.error("Error al evaluar la v�a en la validaci�n de direcci�n");
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al evaluar la v�a en la validaci�n de direcci�n");
		}
		return resultado;
	}

	private ResultadoValidacionDireccionBean evaluarNumero(BloquePoblacionFin bloquePoblacionFin) {
		ResultadoValidacionDireccionBean resultado = new ResultadoValidacionDireccionBean(Boolean.FALSE);
		try {
			if (bloquePoblacionFin.getBloqueVial() != null && bloquePoblacionFin.getBloqueVial().getEstadoNumero() != null) {
				if (bloquePoblacionFin.getBloqueVial().getEstadoNumero() == 0) {
					if (bloquePoblacionFin.getBloqueVial().getBloqueVialFinDTO() != null) {
						resultado.setError(Boolean.TRUE);
						resultado.setErrorLocal(Boolean.TRUE);
						resultado.setMensaje("Error en la informaci�n del local, pero no hay lista informativa de locales para informar");
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No se puede seguir evaluando la validaci�n desde el n�mero");
					}
				} else if (bloquePoblacionFin.getBloqueVial().getEstadoNumero() == 1) {
					resultado.setError(Boolean.TRUE);
					resultado.setErrorNumero(Boolean.TRUE);
					if (bloquePoblacionFin.getBloqueVial().getBloqueVialNoFinDTO() != null && bloquePoblacionFin.getBloqueVial().getBloqueVialNoFinDTO().getNumeros() != null && bloquePoblacionFin
							.getBloqueVial().getBloqueVialNoFinDTO().getNumeros().getNumero() != null && !bloquePoblacionFin.getBloqueVial().getBloqueVialNoFinDTO().getNumeros().getNumero()
									.isEmpty()) {
						resultado.setMensaje("Error al informar el pa�s, existen algunas sugerencias");
						resultado.setNumeros(bloquePoblacionFin.getBloqueVial().getBloqueVialNoFinDTO().getNumeros().getNumero());
					} else {
						resultado.setMensaje("Error al informar el pa�s, pero no hay lista informativa de pa�ses para informar");
					}
				} else if (bloquePoblacionFin.getBloqueVial().getEstadoNumero() == -2) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("C�digo error al procesar el n�mero");
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No existe ningun estado conocido para la validaci�n de el n�mero");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existe ningun estado para la validaci�n de el n�mero");
			}
		} catch (Exception e) {
			log.error("Error al evaluar el n�mero en la validaci�n de direcci�n");
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al evaluar el n�mero en la validaci�n de direcci�n");
		}
		return resultado;
	}
}
