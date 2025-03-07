package org.gestoresmadrid.oegam2.duplicadoPermiso.controller.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.service.ServicioConsultaDuplPermCond;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.bean.DuplicadoPermCondFilterBean;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.bean.ResultadoDuplPermCondBean;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.bean.ResumenDuplicadoPermCondBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaDuplicadoPermisoConducirAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -6173680753310598783L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaDuplicadoPermisoConducirAction.class);

	private static final String[] fetchList = { "contrato", "contrato.colegiado" };

	private static final String POP_UP_CAMBIAR_ESTADO = "popUpCambioEstadoDPCond";
	private static final String IMPRIMIR_DPC = "imprimirDPC";
	private static final String DIAS_PARA_PODER_OBETENER_LOS_TRAMITES = " días para poder obtener los trámites.";

	DuplicadoPermCondFilterBean duplicadoPermConducir;
	String codSeleccionados;
	String estadoNuevo;
	ResumenDuplicadoPermCondBean resumen;

	InputStream inputStream;
	String fileName;

	@Autowired
	UtilesFecha utilesFecha;

	@Resource
	ModelPagination modeloDuplicPermCondPaginated;

	@Autowired
	ServicioConsultaDuplPermCond servicioConsultaDuplPermCond;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String validar() {
		ResultadoDuplPermCondBean resultado = servicioConsultaDuplPermCond.validarTramites(codSeleccionados, utilesColegiado.getIdUsuarioSession());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			resumen = resultado.getResumen();
		}
		return actualizarPaginatedList();
	}

	public String eliminar() {
		ResultadoDuplPermCondBean resultado = servicioConsultaDuplPermCond.eliminarTramites(codSeleccionados, utilesColegiado.getIdUsuarioSession());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			resumen = resultado.getResumen();
		}
		return actualizarPaginatedList();
	}

	public String tramitar() {
		ResultadoDuplPermCondBean resultado = servicioConsultaDuplPermCond.tramitarTramites(codSeleccionados, utilesColegiado.getIdUsuarioSession());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			resumen = resultado.getResumen();
		}
		return actualizarPaginatedList();
	}

	public String cambiarEstado() {
		ResultadoDuplPermCondBean resultado = servicioConsultaDuplPermCond.cambiarEstadoTramites(codSeleccionados, estadoNuevo, utilesColegiado.getIdUsuarioSession());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			resumen = resultado.getResumen();
		}
		return actualizarPaginatedList();
	}

	public String imprimir() {
		try {
			ResultadoDuplPermCondBean resultado = servicioConsultaDuplPermCond.imprimirTramites(codSeleccionados, utilesColegiado.getIdUsuarioSession());
			if (!resultado.getError()) {
				try {
					inputStream = new FileInputStream(resultado.getFichero());
					fileName = resultado.getNombreFichero();
					return IMPRIMIR_DPC;
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

	public String cargarPopUpCambioEstado() {
		return POP_UP_CAMBIAR_ESTADO;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloDuplicPermCondPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (duplicadoPermConducir == null) {
			duplicadoPermConducir = new DuplicadoPermCondFilterBean();
		}

		if (!utilesColegiado.tienePermisoAdmin()) {
			duplicadoPermConducir.setIdContrato(utilesColegiado.getIdContratoSession());
		}

		if (utilesColegiado.tienePermisosAlcala_INTERGA()) {
			duplicadoPermConducir.setJefatura(JefaturasJPTEnum.ALCALADEHENARES.getJefatura());
		} else if (utilesColegiado.tienePermisosCiudadReal_INTERGA()) {
			duplicadoPermConducir.setJefatura(JefaturasJPTEnum.CIUDADREAL.getJefatura());
		} else if (utilesColegiado.tienePermisosCuenca_INTERGA()) {
			duplicadoPermConducir.setJefatura(JefaturasJPTEnum.CUENCA.getJefatura());
		} else if (utilesColegiado.tienePermisosGuadalajara_INTERGA()) {
			duplicadoPermConducir.setJefatura(JefaturasJPTEnum.GUADALAJARA.getJefatura());
		} else if (utilesColegiado.tienePermisosSegovia_INTERGA()) {
			duplicadoPermConducir.setJefatura(JefaturasJPTEnum.SEGOVIA.getJefatura());
		} else if (utilesColegiado.tienePermisosAvila_INTERGA()) {
			duplicadoPermConducir.setJefatura(JefaturasJPTEnum.AVILA.getJefatura());
		} else if (utilesColegiado.tienePermisosAlcorcon_INTERGA()) {
			duplicadoPermConducir.setJefatura(JefaturasJPTEnum.ALCORCON.getJefatura());
		} else if (utilesColegiado.tienePermisosMadrid_INTERGA()) {
			duplicadoPermConducir.setJefatura(JefaturasJPTEnum.MADRID.getJefatura());
		}

		if (duplicadoPermConducir.getNifTitular() != null && !duplicadoPermConducir.getNifTitular().isEmpty()) {
			duplicadoPermConducir.setNifTitular(duplicadoPermConducir.getNifTitular().trim().toUpperCase());
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if (duplicadoPermConducir == null) {
			duplicadoPermConducir = new DuplicadoPermCondFilterBean();
		}

		if (!utilesColegiado.tienePermisoAdmin()) {
			duplicadoPermConducir.setIdContrato(utilesColegiado.getIdContratoSession());
		}

		if (utilesColegiado.tienePermisosAlcala_INTERGA()) {
			duplicadoPermConducir.setJefatura(JefaturasJPTEnum.ALCALADEHENARES.getJefatura());
		} else if (utilesColegiado.tienePermisosCiudadReal_INTERGA()) {
			duplicadoPermConducir.setJefatura(JefaturasJPTEnum.CIUDADREAL.getJefatura());
		} else if (utilesColegiado.tienePermisosCuenca_INTERGA()) {
			duplicadoPermConducir.setJefatura(JefaturasJPTEnum.CUENCA.getJefatura());
		} else if (utilesColegiado.tienePermisosGuadalajara_INTERGA()) {
			duplicadoPermConducir.setJefatura(JefaturasJPTEnum.GUADALAJARA.getJefatura());
		} else if (utilesColegiado.tienePermisosSegovia_INTERGA()) {
			duplicadoPermConducir.setJefatura(JefaturasJPTEnum.SEGOVIA.getJefatura());
		} else if (utilesColegiado.tienePermisosAvila_INTERGA()) {
			duplicadoPermConducir.setJefatura(JefaturasJPTEnum.AVILA.getJefatura());
		} else if (utilesColegiado.tienePermisosAlcorcon_INTERGA()) {
			duplicadoPermConducir.setJefatura(JefaturasJPTEnum.ALCORCON.getJefatura());
		} else if (utilesColegiado.tienePermisosMadrid_INTERGA()) {
			duplicadoPermConducir.setJefatura(JefaturasJPTEnum.MADRID.getJefatura());
		}

		duplicadoPermConducir.setFechaAlta(utilesFecha.getFechaFracionadaActual());
	}

	@Override
	public Boolean validarFechas(Object object) {
		Boolean esRangoValido = Boolean.FALSE;
		String valorRangoFechas = gestorPropiedades.valorPropertie("valor.rango.busquedas");
		String mensaje = "";
		duplicadoPermConducir = (DuplicadoPermCondFilterBean) object;
		if (duplicadoPermConducir.getNumExpediente() == null && StringUtils.isBlank(duplicadoPermConducir.getNifTitular()) && StringUtils.isBlank(duplicadoPermConducir.getRefPropia())) {
			if (StringUtils.isNotBlank(valorRangoFechas)) {
				if (duplicadoPermConducir.getFechaAlta() != null && !duplicadoPermConducir.getFechaAlta().isfechaNula()) {
					if (duplicadoPermConducir.getFechaAlta().getFechaInicio() == null || duplicadoPermConducir.getFechaAlta().getFechaFin() == null) {
						mensaje = "Debe indicar un rango de fechas de alta no mayor a " + valorRangoFechas + DIAS_PARA_PODER_OBETENER_LOS_TRAMITES;
					} else {
						esRangoValido = utilesFecha.comprobarRangoFechas(duplicadoPermConducir.getFechaAlta().getFechaInicio(), duplicadoPermConducir.getFechaAlta().getFechaFin(), Integer.parseInt(
								valorRangoFechas));
						if (!esRangoValido) {
							mensaje = "Debe indicar un rango de fechas de alta no mayor a " + valorRangoFechas + " días para poder obtener los tramites.";
						}
					}
				}
				if (duplicadoPermConducir.getFechaPresentacion() != null && !duplicadoPermConducir.getFechaPresentacion().isfechaNula()) {
					if (duplicadoPermConducir.getFechaPresentacion().getFechaInicio() == null || duplicadoPermConducir.getFechaPresentacion().getFechaFin() == null) {
						if (StringUtils.isBlank(mensaje)) {
							mensaje = "Debe indicar un rango de fechas de presentación no mayor a " + valorRangoFechas + DIAS_PARA_PODER_OBETENER_LOS_TRAMITES;
						} else {
							mensaje = ", debe indicar un rango de fechas de presentación no mayor a " + valorRangoFechas + DIAS_PARA_PODER_OBETENER_LOS_TRAMITES;
						}
					} else {
						esRangoValido = utilesFecha.comprobarRangoFechas(duplicadoPermConducir.getFechaPresentacion().getFechaInicio(), duplicadoPermConducir.getFechaPresentacion().getFechaFin(),
								Integer.parseInt(valorRangoFechas));
						if (!esRangoValido) {
							if (StringUtils.isBlank(mensaje)) {
								mensaje = "Debe indicar un rango de fechas de presentación no mayor a " + valorRangoFechas + DIAS_PARA_PODER_OBETENER_LOS_TRAMITES;
							} else {
								mensaje = ", debe indicar un rango de fechas de presentación no mayor a " + valorRangoFechas + DIAS_PARA_PODER_OBETENER_LOS_TRAMITES;
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
		return duplicadoPermConducir;
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	@Override
	public String getDecorator() {
		return "org.gestoresmadrid.oegam2.view.decorator.DecoratorDuplicadoPermConducir";
	}

	@Override
	protected void setBeanCriterios(Object object) {
		duplicadoPermConducir = (DuplicadoPermCondFilterBean) object;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}

	public String getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(String estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public DuplicadoPermCondFilterBean getDuplicadoPermConducir() {
		return duplicadoPermConducir;
	}

	public void setDuplicadoPermConducir(DuplicadoPermCondFilterBean duplicadoPermConducir) {
		this.duplicadoPermConducir = duplicadoPermConducir;
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

	public ResumenDuplicadoPermCondBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenDuplicadoPermCondBean resumen) {
		this.resumen = resumen;
	}
}