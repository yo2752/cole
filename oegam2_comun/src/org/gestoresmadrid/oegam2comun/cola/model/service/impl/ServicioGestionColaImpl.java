package org.gestoresmadrid.oegam2comun.cola.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioGestionCola;
import org.gestoresmadrid.oegam2comun.cola.view.bean.ConsultaGestionColaBean;
import org.gestoresmadrid.oegam2comun.cola.view.bean.GestionColaBean;
import org.gestoresmadrid.oegam2comun.cola.view.bean.ResultadoGestionColaBean;
import org.gestoresmadrid.oegam2comun.cola.view.bean.SolicitudesColaBean;
import org.gestoresmadrid.oegam2comun.envioData.model.service.ServicioEnvioData;
import org.gestoresmadrid.oegam2comun.envioData.view.dto.EnvioDataDto;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesoSolicitud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioGestionColaImpl implements ServicioGestionCola {

	private static final long serialVersionUID = 519980792364506294L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioGestionColaImpl.class);

	@Autowired
	ServicioProcesoSolicitud servicioProcesoSolicitud;

	@Autowired
	ServicioEnvioData servicioEnvioData;

	@Override
	public ResultadoGestionColaBean getListaRecargarPeticionesCola(ConsultaGestionColaBean consultaGestionColaBean, int page, String sort, String dir, int numPorPage) {
		ResultadoGestionColaBean resultado = new ResultadoGestionColaBean(Boolean.FALSE);
		try {
			List<SolicitudesColaBean> listaSolicitudesColaBBDD = servicioProcesoSolicitud.getListaSolcitudesCola(consultaGestionColaBean);
			if (listaSolicitudesColaBBDD != null && !listaSolicitudesColaBBDD.isEmpty()) {
				if (numPorPage < listaSolicitudesColaBBDD.size()) {
					int numElemtIni = 0;
					int numElemFin = numPorPage;
					if (page > 0) {
						numElemtIni = (page - 1) * numPorPage;
						numElemFin = page * numPorPage;
					}
					List<SolicitudesColaBean> listaSolicitudesPantalla = new ArrayList<SolicitudesColaBean>();
					if (numElemFin > listaSolicitudesColaBBDD.size()) {
						numElemFin = listaSolicitudesColaBBDD.size();
					}
					for (int i = numElemtIni; i < numElemFin; i++) {
						listaSolicitudesPantalla.add(listaSolicitudesColaBBDD.get(i));
					}
					resultado.setListaSolicitudesCola(listaSolicitudesPantalla);
				} else {
					resultado.setListaSolicitudesCola(listaSolicitudesColaBBDD);
				}
				resultado.setTamListaSolicitudes(listaSolicitudesColaBBDD.size());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de recargar la lista con las peticiones, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeLista("Ha sucedido un error a la hora de recargar la lista con las peticiones.");
		}
		return resultado;
	}

	@Override
	public ResultadoGestionColaBean getListaRecargarUltimasEjecuciones(ConsultaGestionColaBean consultaGestionCola, int page, String sort, String dir, int numPorPage) {
		ResultadoGestionColaBean resultado = new ResultadoGestionColaBean(Boolean.FALSE);
		try {
			List<EnvioDataDto> listaUltimosEnvioDataCorrectos = servicioEnvioData.getListaUltimosEnvioDataCorrectos(consultaGestionCola, sort, dir);
			if (listaUltimosEnvioDataCorrectos != null && !listaUltimosEnvioDataCorrectos.isEmpty()) {
				if (numPorPage < listaUltimosEnvioDataCorrectos.size()) {
					int numElemtIni = 0;
					int numElemFin = numPorPage;
					if (page > 0) {
						numElemtIni = (page - 1) * numPorPage;
						numElemFin = page * numPorPage;
					}
					List<EnvioDataDto> listaPantalla = new ArrayList<EnvioDataDto>();
					if (numElemFin > listaUltimosEnvioDataCorrectos.size()) {
						numElemFin = listaUltimosEnvioDataCorrectos.size();
					}
					for (int i = numElemtIni; i < numElemFin; i++) {
						listaPantalla.add(listaUltimosEnvioDataCorrectos.get(i));
					}
					resultado.setListaUltimaPeticionCorrecta(listaPantalla);
				} else {
					resultado.setListaUltimaPeticionCorrecta(listaUltimosEnvioDataCorrectos);
				}
				resultado.setTamListaUltEjecuciones(listaUltimosEnvioDataCorrectos.size());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de recargar la lista con las ultimas ejecuciones correctas, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeLista("Ha sucedido un error a la hora de recargar la lista con las ultimas ejecuciones correctas.");
		}
		return resultado;
	}

	@Override
	public ResultadoGestionColaBean getListasGestion(ConsultaGestionColaBean consultaGestionCola) {
		ResultadoGestionColaBean resultado = new ResultadoGestionColaBean(Boolean.FALSE);
		try {
			if (consultaGestionCola != null) {
				List<SolicitudesColaBean> listaSolicitudesCola = servicioProcesoSolicitud.getListaSolcitudesCola(consultaGestionCola);
				if (listaSolicitudesCola != null && !listaSolicitudesCola.isEmpty()) {
					resultado.setTamListaSolicitudes(listaSolicitudesCola.size());
					if (consultaGestionCola.getGestionCola() == null) {
						consultaGestionCola.setGestionCola(new GestionColaBean());
					}
					// if (listaSolicitudesCola.size() > 5) {
					if (listaSolicitudesCola.size() > consultaGestionCola.getGestionCola().getResultadosPorPaginaSolicitudesCola()) {
						resultado.setListaSolicitudesCola(listaSolicitudesCola.subList(0, consultaGestionCola.getGestionCola().getResultadosPorPaginaSolicitudesCola()));
						// resultado.setListaSolicitudesCola(listaSolicitudesCola.subList(0, 5));
						List<SolicitudesColaBean> listaAux = new ArrayList<SolicitudesColaBean>();
						// for (int i = 0; i < 5; i++) {
						for (int i = 0; i < consultaGestionCola.getGestionCola().getResultadosPorPaginaSolicitudesCola(); i++) {
							listaAux.add(listaSolicitudesCola.get(i));
						}
						resultado.setListaSolicitudesCola(listaAux);
					} else {
						resultado.setListaSolicitudesCola(listaSolicitudesCola);
					}
				}
				if (consultaGestionCola.getProceso() != null && !consultaGestionCola.getProceso().isEmpty()) {
					List<EnvioDataDto> listaEnvioDataProcesoBBDD = servicioEnvioData.getListaEnviosDataProceso(consultaGestionCola.getProceso());
					if (listaEnvioDataProcesoBBDD != null && !listaEnvioDataProcesoBBDD.isEmpty()) {
						resultado.setListaEjecucionesProceso(listaEnvioDataProcesoBBDD);
					}
					List<EnvioDataDto> listaEnvioDataProcesoPorColaBBDD = servicioEnvioData.getListaEnviosDataProcesoPorCola(consultaGestionCola.getProceso());
					if (listaEnvioDataProcesoPorColaBBDD != null && !listaEnvioDataProcesoPorColaBBDD.isEmpty()) {
						resultado.setListaEjecucionesProcesoPorCola(listaEnvioDataProcesoPorColaBBDD);
					}
				}
				List<EnvioDataDto> listaUltimosEnvioDataCorrectos = servicioEnvioData.getListaUltimosEnvioDataCorrectos(consultaGestionCola, null, null);
				if (listaUltimosEnvioDataCorrectos != null && !listaUltimosEnvioDataCorrectos.isEmpty()) {
					resultado.setTamListaUltEjecuciones(listaUltimosEnvioDataCorrectos.size());
					if (consultaGestionCola.getGestionCola() == null) {
						consultaGestionCola.setGestionCola(new GestionColaBean());
					}
					// if (listaSolicitudesCola.size() > 5) {
					if (listaUltimosEnvioDataCorrectos.size() > consultaGestionCola.getGestionCola().getResultadosPorPaginaUltimaEjecucion()) {
						List<EnvioDataDto> listaAux = new ArrayList<EnvioDataDto>();
						// for (int i = 0; i < 5; i++) {
						for (int i = 0; i < consultaGestionCola.getGestionCola().getResultadosPorPaginaUltimaEjecucion(); i++) {
							listaAux.add(listaUltimosEnvioDataCorrectos.get(i));
						}
						resultado.setListaUltimaPeticionCorrecta(listaAux);
					} else {
						resultado.setListaUltimaPeticionCorrecta(listaUltimosEnvioDataCorrectos);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeLista("No existen datos para obtener los datos de las solicitudes.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener las listas de la gestion, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeLista("Ha sucedido un error a la hora de obtener las listas de la gestion.");
		}
		return resultado;
	}

	@Override
	public ResultadoGestionColaBean eliminarColas(String idsEnvios, ConsultaGestionColaBean consultaGestionCola) {
		ResultadoGestionColaBean resultado = new ResultadoGestionColaBean(Boolean.FALSE);
		try {
			if (idsEnvios != null && !idsEnvios.isEmpty()) {
				String[] idsEnviosArray = idsEnvios.split("-");
				for (String idEnvio : idsEnviosArray) {
					try {
						ResultBean resultEliminar = servicioProcesoSolicitud.eliminarSolicitudes(idEnvio);
						if (resultEliminar.getError()) {
							resultado.addNumError();
							resultado.addListaErrores(resultEliminar.getMensaje());
						} else {
							resultado.addListaCorrectas("La cola con id Envio: " + idEnvio + ", se ha eliminado correctamente.");
							resultado.addNumOk();
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de eliminar la cola con idEnvio: " + idEnvio + ", error: ", e);
						resultado.addNumError();
						resultado.addListaErrores("Ha sucedido un error a la hora de eliminar la cola con idEnvio: " + idEnvio);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeLista("Debe de seleccionar alguna cola para su eliminación.");
			}
			obtenerListasPantallas(resultado, consultaGestionCola);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar las colas seleccionadas, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeLista("Ha sucedido un error a la hora de eliminar las colas seleccionadas.");
		}
		return resultado;
	}

	@Override
	public ResultadoGestionColaBean finalizarConErrorServicioEnvios(String idsEnvios, ConsultaGestionColaBean consultaGestionCola) {
		ResultadoGestionColaBean resultado = new ResultadoGestionColaBean(Boolean.FALSE);
		try {
			if (idsEnvios != null && !idsEnvios.isEmpty()) {
				String[] idsEnviosArray = idsEnvios.split("-");
				for (String idEnvio : idsEnviosArray) {
					try {
						ResultBean resultFinErrorServ = servicioProcesoSolicitud.finalizarErrorServicio(idEnvio);
						if (resultFinErrorServ.getError()) {
							resultado.addNumError();
							resultado.addListaErrores(resultFinErrorServ.getMensaje());
						} else {
							resultado.addListaCorrectas("La cola con id Envio: " + idEnvio + ", se ha finalizado con error servicio correctamente.");
							resultado.addNumOk();
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de finalizar con error servicio la cola con idEnvio: " + idEnvio + ", error: ", e);
						resultado.addNumError();
						resultado.addListaErrores("Ha sucedido un error a la hora de finalizar con error servicio la cola con idEnvio: " + idEnvio);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeLista("Debe de seleccionar alguna cola para poder finalizarlas con error servicio.");
			}
			obtenerListasPantallas(resultado, consultaGestionCola);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de finalizar con error servicio las colas seleccionadas, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeLista("Ha sucedido un error a la hora de finalizar con error servicio las colas seleccionadas.");
		}
		return resultado;
	}

	@Override
	public ResultadoGestionColaBean finalizarConErrorEnvios(String idsEnvios, ConsultaGestionColaBean consultaGestionCola) {
		ResultadoGestionColaBean resultado = new ResultadoGestionColaBean(Boolean.FALSE);
		try {
			if (idsEnvios != null && !idsEnvios.isEmpty()) {
				String[] idsEnviosArray = idsEnvios.split("-");
				for (String idEnvio : idsEnviosArray) {
					try {
						ResultBean resultFinError = servicioProcesoSolicitud.finalizarError(idEnvio);
						if (resultFinError.getError()) {
							resultado.addNumError();
							resultado.addListaErrores(resultFinError.getMensaje());
						} else {
							resultado.addListaCorrectas("La cola con id Envio: " + idEnvio + ", se ha finalizado con error correctamente.");
							resultado.addNumOk();
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de finalizar con error la cola con idEnvio: " + idEnvio + ", error: ", e);
						resultado.addNumError();
						resultado.addListaErrores("Ha sucedido un error a la hora de finalizar con error la cola con idEnvio: " + idEnvio);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeLista("Debe de seleccionar alguna cola para poder finalizarlas con error.");
			}
			obtenerListasPantallas(resultado, consultaGestionCola);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de finalizar con error las colas seleccionadas, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeLista("Ha sucedido un error a la hora de finalizar con error las colas seleccionadas.");
		}
		return resultado;
	}

	@Override
	public ResultadoGestionColaBean reactivarEnvios(String idsEnvios, ConsultaGestionColaBean consultaGestionCola) {
		ResultadoGestionColaBean resultado = new ResultadoGestionColaBean(Boolean.FALSE);
		try {
			if (idsEnvios != null && !idsEnvios.isEmpty()) {
				String[] idsEnviosArray = idsEnvios.split("-");
				for (String idEnvio : idsEnviosArray) {
					try {
						ResultBean resultReactivar = servicioProcesoSolicitud.reactivarSolicitud(idEnvio);
						if (resultReactivar.getError()) {
							resultado.addNumError();
							resultado.addListaErrores(resultReactivar.getMensaje());
						} else {
							resultado.addListaCorrectas("La cola con id Envio: " + idEnvio + ", se ha reactivado correctamente.");
							resultado.addNumOk();
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de reactivar la cola con idEnvio: " + idEnvio + ", error: ", e);
						resultado.addNumError();
						resultado.addListaErrores("Ha sucedido un error a la hora de reactivar la cola con idEnvio: " + idEnvio);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeLista("Debe de seleccionar alguna cola para poder reactivarlas.");
			}
			obtenerListasPantallas(resultado, consultaGestionCola);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de reactivar las colas seleccionadas, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeLista("Ha sucedido un error a la hora de reactivar las colas seleccionadas.");
		}
		return resultado;
	}

	@Override
	public ResultadoGestionColaBean maxPrioridadEnvios(String idEnvio, ConsultaGestionColaBean consultaGestionCola) {
		ResultadoGestionColaBean resultado = new ResultadoGestionColaBean(Boolean.FALSE);
		try {
			if (idEnvio != null && !idEnvio.isEmpty()) {
				String[] idsEnviosArray = idEnvio.split("-");
				if (idsEnviosArray.length == 1) {
					try {
						ResultBean resultMaxPrio = servicioProcesoSolicitud.establecerMaxPrioridad(idEnvio);
						if (resultMaxPrio.getError()) {
							resultado.addNumError();
							resultado.addListaErrores(resultMaxPrio.getMensaje());
						} else {
							resultado.addListaCorrectas("La cola con id Envio: " + idEnvio + ", se ha modificado su prioridad correctamente.");
							resultado.addNumOk();
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de establecer la máxima prioridad a la cola con idEnvio: " + idEnvio + ", error: ", e);
						resultado.addNumError();
						resultado.addListaErrores("Ha sucedido un error a la hora de establecer la máxima prioridad a la cola con idEnvio: " + idEnvio);
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.addMensajeLista("Solo se pueden establecer la máxima prioridad a una cola simultaneamente.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeLista("Debe de seleccionar alguna cola para poder establecer la prioridad.");
			}
			obtenerListasPantallas(resultado, consultaGestionCola);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de establecer la prioridad de la cola seleccionada, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeLista("Ha sucedido un error a la hora de establecer la prioridad de la cola seleccionada.");
		}
		return resultado;
	}

	public void obtenerListasPantallas(ResultadoGestionColaBean resultado, ConsultaGestionColaBean consultaGestionCola) {
		ResultadoGestionColaBean resultadoRecargListas = getListasGestion(consultaGestionCola);
		if (!resultadoRecargListas.getError()) {
			rellenarListasResultados(resultado, resultadoRecargListas);
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setListaMensajes(resultadoRecargListas.getListaMensajes());
		}
	}

	public void rellenarListasResultados(ResultadoGestionColaBean resultado, ResultadoGestionColaBean resultadoListas) {
		resultado.setListaEjecucionesProceso(resultadoListas.getListaEjecucionesProceso());
		resultado.setListaEjecucionesProcesoPorCola(resultadoListas.getListaEjecucionesProcesoPorCola());
		resultado.setListaSolicitudesCola(resultadoListas.getListaSolicitudesCola());
		resultado.setListaUltimaPeticionCorrecta(resultadoListas.getListaUltimaPeticionCorrecta());
		resultado.setTamListaSolicitudes(resultadoListas.getTamListaSolicitudes());
		resultado.setTamListaUltEjecuciones(resultadoListas.getTamListaUltEjecuciones());
	}
}
