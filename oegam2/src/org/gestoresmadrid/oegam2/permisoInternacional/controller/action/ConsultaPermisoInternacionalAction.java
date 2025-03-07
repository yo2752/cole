package org.gestoresmadrid.oegam2.permisoInternacional.controller.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamPermisoInternacional.service.ServicioConsultaPermisoInternacional;
import org.gestoresmadrid.oegamPermisoInternacional.view.bean.PermisoInternacionalFilterBean;
import org.gestoresmadrid.oegamPermisoInternacional.view.bean.ResultadoPermInterBean;
import org.gestoresmadrid.oegamPermisoInternacional.view.bean.ResumenPermisoInternacionalBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaPermisoInternacionalAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -1286205593565295038L;
	private static final String[] fetchList = { "contrato", "contrato.colegiado" };
	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaPermisoInternacionalAction.class);
	private static final String POP_UP_CAMBIAR_ESTADO = "popUpCambioEstadoPI";
	private static final String IMPRIMIR_P_INTER = "imprimirPInternacional";
	private static final String DIAS_PARA_PODER_OBTENER_LOS_TRAMITES = " días para poder obtener los trámites.";

	PermisoInternacionalFilterBean permisoInternacional;
	String codSeleccionados;
	String estadoNuevo;
	ResumenPermisoInternacionalBean resumen;
	InputStream inputStream;
	String fileName;

	@Autowired
	UtilesFecha utilesFecha;

	@Resource
	ModelPagination modeloPermisoInternacionalPaginated;

	@Autowired
	ServicioConsultaPermisoInternacional servicioConsultaPermisoInternacional;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String validar() {
		ResultadoPermInterBean resultado = servicioConsultaPermisoInternacional.validarTramites(codSeleccionados, utilesColegiado.getIdUsuarioSession());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			resumen = resultado.getResumen();
		}
		return actualizarPaginatedList();
	}

	public String eliminar() {
		ResultadoPermInterBean resultado = servicioConsultaPermisoInternacional.eliminarTramites(codSeleccionados, utilesColegiado.getIdUsuarioSession());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			resumen = resultado.getResumen();
		}
		return actualizarPaginatedList();
	}

	public String tramitar() {
		ResultadoPermInterBean resultado = servicioConsultaPermisoInternacional.tramitarTramites(codSeleccionados, utilesColegiado.getIdUsuarioSession());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			resumen = resultado.getResumen();
		}
		return actualizarPaginatedList();
	}

	public String imprimir() {
		try {
			ResultadoPermInterBean resultado = servicioConsultaPermisoInternacional.imprimirTramites(codSeleccionados, utilesColegiado.getIdUsuarioSession(), utilesColegiado
					.tienePermisoAdmin());
			if (!resultado.getError()) {
				try {
					inputStream = new FileInputStream(resultado.getFichero());
					fileName = resultado.getNombreFichero();
					return IMPRIMIR_P_INTER;
				} catch (FileNotFoundException e) {
					log.error("No existe el fichero a imprimir, error:", e);
					addActionError("No existe el fichero a imprimir.");
				}
			} else {
				addActionError(resultado.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir el permiso internacional, error: ", e);
			addActionError("Ha sucedido un error a la hora de imprimir el permiso internacional.");
		}
		return actualizarPaginatedList();
	}

	public String cambiarEstado() {
		ResultadoPermInterBean resultado = servicioConsultaPermisoInternacional.cambiarEstadoTramites(codSeleccionados, estadoNuevo, utilesColegiado.getIdUsuarioSession());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			resumen = resultado.getResumen();
		}
		return actualizarPaginatedList();
	}

	public String cargarPopUpCambioEstado() {
		return POP_UP_CAMBIAR_ESTADO;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloPermisoInternacionalPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (permisoInternacional == null) {
			permisoInternacional = new PermisoInternacionalFilterBean();
		}

		if (!utilesColegiado.tienePermisoAdmin()) {
			permisoInternacional.setIdContrato(utilesColegiado.getIdContratoSession());
		}

		if (utilesColegiado.tienePermisosAlcala_INTERGA()) {
			permisoInternacional.setJefatura(JefaturasJPTEnum.ALCALADEHENARES.getJefatura());
		} else if (utilesColegiado.tienePermisosCiudadReal_INTERGA()) {
			permisoInternacional.setJefatura(JefaturasJPTEnum.CIUDADREAL.getJefatura());
		} else if (utilesColegiado.tienePermisosCuenca_INTERGA()) {
			permisoInternacional.setJefatura(JefaturasJPTEnum.CUENCA.getJefatura());
		} else if (utilesColegiado.tienePermisosGuadalajara_INTERGA()) {
			permisoInternacional.setJefatura(JefaturasJPTEnum.GUADALAJARA.getJefatura());
		} else if (utilesColegiado.tienePermisosSegovia_INTERGA()) {
			permisoInternacional.setJefatura(JefaturasJPTEnum.SEGOVIA.getJefatura());
		} else if (utilesColegiado.tienePermisosAvila_INTERGA()) {
			permisoInternacional.setJefatura(JefaturasJPTEnum.AVILA.getJefatura());
		} else if (utilesColegiado.tienePermisosAlcorcon_INTERGA()) {
			permisoInternacional.setJefatura(JefaturasJPTEnum.ALCORCON.getJefatura());
		} else if (utilesColegiado.tienePermisosMadrid_INTERGA()) {
			permisoInternacional.setJefatura(JefaturasJPTEnum.MADRID.getJefatura());
		}

		if (permisoInternacional.getNifTitular() != null && !permisoInternacional.getNifTitular().isEmpty()) {
			permisoInternacional.setNifTitular(permisoInternacional.getNifTitular().trim().toUpperCase());
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if (permisoInternacional == null) {
			permisoInternacional = new PermisoInternacionalFilterBean();
		}

		if (!utilesColegiado.tienePermisoAdmin()) {
			permisoInternacional.setIdContrato(utilesColegiado.getIdContratoSession());
		}

		if (utilesColegiado.tienePermisosAlcala_INTERGA()) {
			permisoInternacional.setJefatura(JefaturasJPTEnum.ALCALADEHENARES.getJefatura());
		} else if (utilesColegiado.tienePermisosCiudadReal_INTERGA()) {
			permisoInternacional.setJefatura(JefaturasJPTEnum.CIUDADREAL.getJefatura());
		} else if (utilesColegiado.tienePermisosCuenca_INTERGA()) {
			permisoInternacional.setJefatura(JefaturasJPTEnum.CUENCA.getJefatura());
		} else if (utilesColegiado.tienePermisosGuadalajara_INTERGA()) {
			permisoInternacional.setJefatura(JefaturasJPTEnum.GUADALAJARA.getJefatura());
		} else if (utilesColegiado.tienePermisosSegovia_INTERGA()) {
			permisoInternacional.setJefatura(JefaturasJPTEnum.SEGOVIA.getJefatura());
		} else if (utilesColegiado.tienePermisosAvila_INTERGA()) {
			permisoInternacional.setJefatura(JefaturasJPTEnum.AVILA.getJefatura());
		} else if (utilesColegiado.tienePermisosAlcorcon_INTERGA()) {
			permisoInternacional.setJefatura(JefaturasJPTEnum.ALCORCON.getJefatura());
		} else if (utilesColegiado.tienePermisosMadrid_INTERGA()) {
			permisoInternacional.setJefatura(JefaturasJPTEnum.MADRID.getJefatura());
		}

		permisoInternacional.setFechaAlta(utilesFecha.getFechaFracionadaActual());
	}

	@Override
	public Boolean validarFechas(Object object) {
		Boolean esRangoValido = Boolean.FALSE;
		String valorRangoFechas = gestorPropiedades.valorPropertie("valor.rango.busquedas");
		String mensaje = "";
		permisoInternacional = (PermisoInternacionalFilterBean) object;
		if (StringUtils.isBlank(permisoInternacional.getRefPropia()) && permisoInternacional.getNumExpediente() == null
				&& StringUtils.isBlank(permisoInternacional.getNifTitular())) {
			if (StringUtils.isNotBlank(valorRangoFechas)){
				if (permisoInternacional.getFechaAlta() != null && !permisoInternacional.getFechaAlta().isfechaNula()) {
					if (permisoInternacional.getFechaAlta().getFechaInicio() == null ||
							permisoInternacional.getFechaAlta().getFechaFin() == null) {
						mensaje = "Debe indicar un rango de fechas de alta no mayor a " + valorRangoFechas + DIAS_PARA_PODER_OBTENER_LOS_TRAMITES;
					} else {
						esRangoValido = utilesFecha.comprobarRangoFechas(permisoInternacional.getFechaAlta().getFechaInicio(), permisoInternacional.getFechaAlta().getFechaFin(), Integer.parseInt(valorRangoFechas));
					}
					if (!esRangoValido) {
						mensaje = "Debe indicar un rango de fechas de alta no mayor a " + valorRangoFechas + DIAS_PARA_PODER_OBTENER_LOS_TRAMITES;
					}
				}
				if (permisoInternacional.getFechaPresentacion() != null && !permisoInternacional.getFechaPresentacion().isfechaNula()) {
					if (permisoInternacional.getFechaPresentacion().getFechaInicio() == null || 
							permisoInternacional.getFechaPresentacion().getFechaFin() == null) {
						if (StringUtils.isBlank(mensaje)) {
							mensaje = "Debe indicar un rango de fechas de presentación no mayor a " + valorRangoFechas + DIAS_PARA_PODER_OBTENER_LOS_TRAMITES;
						} else {
							mensaje = ", debe indicar un rango de fechas de presentación no mayor a " + valorRangoFechas + DIAS_PARA_PODER_OBTENER_LOS_TRAMITES;
						}
					} else {
						esRangoValido = utilesFecha.comprobarRangoFechas(permisoInternacional.getFechaPresentacion().getFechaInicio(), permisoInternacional.getFechaPresentacion().getFechaFin(), Integer.parseInt(valorRangoFechas));
						if (!esRangoValido) {
							if (StringUtils.isBlank(mensaje)) {
								mensaje = "Debe indicar un rango de fechas de presentación no mayor a " + valorRangoFechas + DIAS_PARA_PODER_OBTENER_LOS_TRAMITES;
							} else {
								mensaje = ", debe indicar un rango de fechas de presentación no mayor a " + valorRangoFechas + DIAS_PARA_PODER_OBTENER_LOS_TRAMITES;
							}
						}
					}
				}
			}
			if (!esRangoValido) {
				addActionError(StringUtils.isNotBlank(mensaje) ? mensaje : "Para poder realizar una búsqueda de trámites debe de indicar alguna fecha.");
			}
		} else {
			esRangoValido = Boolean.TRUE;
		}
		return esRangoValido;
	}

	@Override
	protected Object getBeanCriterios() {
		return permisoInternacional;
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	@Override
	public String getDecorator() {
		return "org.gestoresmadrid.oegam2.view.decorator.DecoratorPermisoInternacional";
	}

	@Override
	protected void setBeanCriterios(Object object) {
		permisoInternacional = (PermisoInternacionalFilterBean) object;
	}

	public PermisoInternacionalFilterBean getPermisoInternacionalFilter() {
		return permisoInternacional;
	}

	public void setPermisoInternacionalFilter(PermisoInternacionalFilterBean permisoInternacionalFilter) {
		this.permisoInternacional = permisoInternacionalFilter;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}

	public ResumenPermisoInternacionalBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenPermisoInternacionalBean resumen) {
		this.resumen = resumen;
	}

	public String getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(String estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public PermisoInternacionalFilterBean getPermisoInternacional() {
		return permisoInternacional;
	}

	public void setPermisoInternacional(PermisoInternacionalFilterBean permisoInternacional) {
		this.permisoInternacional = permisoInternacional;
	}
}