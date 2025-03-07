package org.gestoresmadrid.oegam2.colas.controller.action;

import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioGestionCola;
import org.gestoresmadrid.oegam2comun.cola.view.bean.ConsultaGestionColaBean;
import org.gestoresmadrid.oegam2comun.cola.view.bean.GestionColaBean;
import org.gestoresmadrid.oegam2comun.cola.view.bean.ResultadoGestionColaBean;
import org.gestoresmadrid.oegam2comun.cola.view.bean.ResumenGestionColaBean;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;

public class GestionColasAction extends ActionBase {

	private static final long serialVersionUID = -1198686020876998147L;

	private static final String PAG_CONSULTA_GESTION_COLA = "pagGestionCola";
	private static final String LOGIN = "login";

	private ConsultaGestionColaBean consultaGestionCola;

	private String idsEnvios;

	private ResumenGestionColaBean resumen;

	@Autowired
	ServicioGestionCola servicioGestionCola;

	@Autowired
	UtilesFecha utilesFecha;

	private String rol;
	private boolean logado;

	public String inicio() {
		if (!logado) {
			return LOGIN;
		}
		iniciarGestionCola();
		return consultar();
	}

	public String consultar() {
		ResultadoGestionColaBean resultado = servicioGestionCola.getListasGestion(consultaGestionCola);
		if (!resultado.getError()) {
			rellenarPaginatedList(resultado);
		} else {
			aniadirListaErrores(resultado.getListaMensajes());
		}
		return PAG_CONSULTA_GESTION_COLA;
	}

	public String eliminarPorCola() {
		ResultadoGestionColaBean resultado = servicioGestionCola.eliminarColas(idsEnvios, consultaGestionCola);
		if (!resultado.getError()) {
			rellenarResumen(resultado, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
		} else {
			aniadirListaErrores(resultado.getListaMensajes());
		}
		rellenarPaginatedList(resultado);
		return PAG_CONSULTA_GESTION_COLA;
	}

	public String finalizarConErrorServicio() {
		ResultadoGestionColaBean resultado = servicioGestionCola.finalizarConErrorServicioEnvios(idsEnvios, consultaGestionCola);
		if (!resultado.getError()) {
			rellenarResumen(resultado, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
		} else {
			aniadirListaErrores(resultado.getListaMensajes());
		}
		rellenarPaginatedList(resultado);
		return PAG_CONSULTA_GESTION_COLA;
	}

	public String finalizarConError() {
		ResultadoGestionColaBean resultado = servicioGestionCola.finalizarConErrorEnvios(idsEnvios, consultaGestionCola);
		if (!resultado.getError()) {
			rellenarResumen(resultado, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE);
		} else {
			aniadirListaErrores(resultado.getListaMensajes());
		}
		rellenarPaginatedList(resultado);
		return PAG_CONSULTA_GESTION_COLA;
	}

	public String reactivar() {
		ResultadoGestionColaBean resultado = servicioGestionCola.reactivarEnvios(idsEnvios, consultaGestionCola);
		if (!resultado.getError()) {
			rellenarResumen(resultado, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE);
		} else {
			aniadirListaErrores(resultado.getListaMensajes());
		}
		rellenarPaginatedList(resultado);
		return PAG_CONSULTA_GESTION_COLA;
	}

	public String establecerMaxPrioridad() {
		ResultadoGestionColaBean resultado = servicioGestionCola.maxPrioridadEnvios(idsEnvios, consultaGestionCola);
		if (!resultado.getError()) {
			rellenarResumen(resultado, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE);
		} else {
			aniadirListaErrores(resultado.getListaMensajes());
		}
		rellenarPaginatedList(resultado);
		return PAG_CONSULTA_GESTION_COLA;
	}

	private void iniciarGestionCola() {
		if (consultaGestionCola == null) {
			consultaGestionCola = new ConsultaGestionColaBean();
			consultaGestionCola.setFecha(utilesFecha.getFechaFracionadaActual());
			consultaGestionCola.setGestionCola(new GestionColaBean());
		}
	}

	private void rellenarResumen(ResultadoGestionColaBean resultado, Boolean esEliminar, Boolean esFinalizarErrorServ, Boolean esFinalizarError, Boolean esReactivar, Boolean esPrioridad) {
		resumen = new ResumenGestionColaBean();
		resumen.setListaErrores(resultado.getListaError());
		resumen.setNumError(resultado.getNumError());
		resumen.setListaOk(resultado.getListaCorrectas());
		resumen.setNumOk(resultado.getNumOk());
		if (esEliminar) {
			resumen.setEsEliminarCola(esEliminar);
		} else if (esFinalizarErrorServ) {
			resumen.setEsFinalizarErrorServ(esFinalizarErrorServ);
		} else if (esFinalizarError) {
			resumen.setEsFinalizarConError(esFinalizarError);
		} else if (esReactivar) {
			resumen.setEsReactivar(esReactivar);
		} else if (esPrioridad) {
			resumen.setEsEstablecerMaxPrio(esPrioridad);
		}
	}

	private void rellenarPaginatedList(ResultadoGestionColaBean resultado) {
		if (consultaGestionCola.getGestionCola() == null) {
			consultaGestionCola.setGestionCola(new GestionColaBean());
		}
		if (resultado.getListaSolicitudesCola() != null && !resultado.getListaSolicitudesCola().isEmpty()) {
			// consultaGestionCola.getGestionCola().setResultadosPorPaginaSolicitudesCola(5);
			consultaGestionCola.getGestionCola().setListaSolicitudesCola(new PaginatedListImpl(consultaGestionCola.getGestionCola().getResultadosPorPaginaSolicitudesCola(), consultaGestionCola
					.getGestionCola().getPageSolicitudesCola(), consultaGestionCola.getGestionCola().getDirSolicitudesCola(), consultaGestionCola.getGestionCola().getSortSolicitudesCola(), resultado
							.getTamListaSolicitudes(), resultado.getListaSolicitudesCola()));
			consultaGestionCola.getGestionCola().setExisteBusqueda(Boolean.TRUE);
		}
		if (resultado.getListaUltimaPeticionCorrecta() != null && !resultado.getListaUltimaPeticionCorrecta().isEmpty()) {
			// consultaGestionCola.getGestionCola().setResultadosPorPaginaUltimaEjecucion(5);
			consultaGestionCola.getGestionCola().setListaUltimaEjecucion(new PaginatedListImpl(consultaGestionCola.getGestionCola().getResultadosPorPaginaUltimaEjecucion(), consultaGestionCola
					.getGestionCola().getPageUltimaEjecuion(), consultaGestionCola.getGestionCola().getDirUltimaEjecuion(), consultaGestionCola.getGestionCola().getSortUltimaEjecuion(), resultado
							.getTamListaUltEjecuciones(), resultado.getListaUltimaPeticionCorrecta()));
			consultaGestionCola.getGestionCola().setExisteBusqueda(Boolean.TRUE);
		}
		if (resultado.getListaEjecucionesProceso() != null && !resultado.getListaEjecucionesProceso().isEmpty()) {
			consultaGestionCola.getGestionCola().setListaEjecucionesProceso(resultado.getListaEjecucionesProceso());
			consultaGestionCola.getGestionCola().setExisteBusqueda(Boolean.TRUE);
		}
		if (resultado.getListaEjecucionesProcesoPorCola() != null && !resultado.getListaEjecucionesProcesoPorCola().isEmpty()) {
			consultaGestionCola.getGestionCola().setListaEjecucionesProcesoPorCola(resultado.getListaEjecucionesProcesoPorCola());
			consultaGestionCola.getGestionCola().setExisteBusqueda(Boolean.TRUE);
		}
		getSession().put("consultaGestionColaBean", consultaGestionCola);
	}

	public String getIdsEnvios() {
		return idsEnvios;
	}

	public void setIdsEnvios(String idsEnvios) {
		this.idsEnvios = idsEnvios;
	}

	public ResumenGestionColaBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenGestionColaBean resumen) {
		this.resumen = resumen;
	}

	public ConsultaGestionColaBean getConsultaGestionCola() {
		return consultaGestionCola;
	}

	public void setConsultaGestionCola(ConsultaGestionColaBean consultaGestionCola) {
		this.consultaGestionCola = consultaGestionCola;
	}

	public boolean isLogado() {
		return logado;
	}

	public void setLogado(boolean logado) {
		this.logado = logado;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
}
