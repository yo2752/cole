package org.gestoresmadrid.oegam2comun.clonarTramites.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.clonarTramites.model.service.ServicioClonarTramites;
import org.gestoresmadrid.oegam2comun.clonarTramites.model.service.ServicioClonarTramitesMatriculacion;
import org.gestoresmadrid.oegam2comun.clonarTramites.view.dto.ClonarTramitesDto;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;
import escrituras.beans.ResultBean;

@Service
public class ServicioClonarTramitesImpl implements ServicioClonarTramites {

	private static final long serialVersionUID = 3129021230697261899L;

	public static final ILoggerOegam log = LoggerOegam.getLogger(ServicioClonarTramitesImpl.class);

	@Autowired
	ServicioClonarTramitesMatriculacion servicioClonarTramitesMatriculacion;

	@Autowired
	ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;

	@Override
	public ResultBean comprobarPrevioAClonarTramites(String[] codSeleccionados, String estadoTramiteSeleccionado, String tipoTramiteSeleccionado) {
		ResultBean resultado = null;

		if (estadoTramiteSeleccionado == null || tipoTramiteSeleccionado == null) {
			resultado = new ResultBean(true, "Existen datos inconsistentes del trámite para clonar.");
		} else if (codSeleccionados == null) {
			resultado = new ResultBean(true, "Debe seleccionar algún trámite para clonar.");
		} else if (codSeleccionados.length > 1) {
			resultado = new ResultBean(true, "Debe seleccionar solo un trámite para clonar.");
		} else if (!EstadoTramiteTrafico.Iniciado.toString().equals(estadoTramiteSeleccionado)) {
			resultado = new ResultBean(true, "El trámite debe de estar en estado Iniciado para poder realizar la clonación.");
		} else if (!TipoTramiteTrafico.Matriculacion.toString().equals(tipoTramiteSeleccionado)) {
			resultado = new ResultBean(true, "El tipo de trámite seleccionado debe ser de Matriculación para poder realizar la clonación.");
		}
		return resultado;
	}

	@Override
	public List<ClonarTramitesDto> crearListaExpedienteClonar(ClonarTramitesDto clonarTramitesDto) {
		List<ClonarTramitesDto> lista = new ArrayList<ClonarTramitesDto>();
		lista.add(clonarTramitesDto);
		return lista;
	}

	@Override
	public ResultBean comprobacionDatosClonacion(ClonarTramitesDto clonarTramitesDto) {
		ResultBean result = null;
		if (clonarTramitesDto.getNumExpediente() == null) {
			result = new ResultBean(true, "Debe existir un numero de expediente para poder realizar su clonación.");
		} else if (clonarTramitesDto.getEstado() == null && !EstadoTramiteTrafico.Iniciado.toString().equals(clonarTramitesDto.getEstado())) {
			result = new ResultBean(true, "El trámite tiene que estar en Estado Iniciado para poder realizar su clonación.");
		} else if (clonarTramitesDto.getTipoTramite() == null && !TipoTramiteTrafico.Matriculacion.toString().equals(clonarTramitesDto.getTipoTramite())) {
			result = new ResultBean(true, "El trámite tiene que ser del Tipo Matriculación para poder realizar su clonación.");
		} else if (clonarTramitesDto.getResult() == null) {
			result = new ResultBean(true, "Debe seleccionar al campo que clonar.");
		} else if (comprobarCheck(clonarTramitesDto)) {
			result = new ResultBean(true, "Debe seleccionar algún campo que clonar.");
		}
		return result;
	}

	private boolean comprobarCheck(ClonarTramitesDto clonarTramitesDto) {
		Boolean resultado = false;
		if (TipoTramiteTrafico.Matriculacion.toString().equals(clonarTramitesDto.getTipoTramite())) {
			resultado = servicioClonarTramitesMatriculacion.comprobarCheckClonacion(clonarTramitesDto);
		}
		return resultado;
	}

	@Override
	public ResultBean clonarTramites(ClonarTramitesDto clonarTramitesDto, BigDecimal idUsuario) {
		ResultBean result = null;
		try {
			if (TipoTramiteTrafico.Matriculacion.toString().equals(clonarTramitesDto.getTipoTramite())) {
				result = servicioClonarTramitesMatriculacion.clonar(clonarTramitesDto, idUsuario);
			}
		} catch (OegamExcepcion e) {
			if (EnumError.error_00002.equals(e.getCodigoError())) {
				result = new ResultBean(true, e.getMensajeError1());
				log.error("Ha sucedido un error a la hora de clonar el trámite, error: " + e.getMessage());
			} else {
				result = new ResultBean(true, "Ha sucedido un error a la hora de clonar el trámite.");
				log.error("Ha sucedido un error a la hora de clonar el trámite, error: " + e.getMensajeError1());
			}
		}
		return result;
	}

	@Override
	public ResultBean seleccionarPestaniasClonar(ClonarTramitesDto clonarTramitesDto) {
		ResultBean result = new ResultBean();
		try {
			if (TipoTramiteTrafico.Matriculacion.toString().equals(clonarTramitesDto.getTipoTramite())) {
				clonarTramitesDto = servicioClonarTramitesMatriculacion.getPestaniasClonar(clonarTramitesDto);
			}
			result.setError(false);
			result.addAttachment("clonarTramitesDTO", clonarTramitesDto);
		} catch (OegamExcepcion e) {
			result.setError(true);
			result.setMensaje("Ha sucedido un error a la hora de comprobar los datos del tramite para clonar");
			log.error("Ha sucedido un error a la hora de comprobar los datos del tramite para clonar, error: " + e.getMessage());
		}
		return result;
	}

	public ServicioClonarTramitesMatriculacion getServicioClonarTramitesMatriculacion() {
		return servicioClonarTramitesMatriculacion;
	}

	public void setServicioClonarTramitesMatriculacion(ServicioClonarTramitesMatriculacion servicioClonarTramitesMatriculacion) {
		this.servicioClonarTramitesMatriculacion = servicioClonarTramitesMatriculacion;
	}

	public ServicioTramiteTraficoMatriculacion getServicioTramiteTraficoMatriculacion() {
		return servicioTramiteTraficoMatriculacion;
	}

	public void setServicioTramiteTraficoMatriculacion(ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion) {
		this.servicioTramiteTraficoMatriculacion = servicioTramiteTraficoMatriculacion;
	}
}
