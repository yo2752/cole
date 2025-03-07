package org.gestoresmadrid.oegam2.trafico.inteve.controller.action;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamInteve.service.ServicioConsultaTramiteInteve;
import org.gestoresmadrid.oegamInteve.view.bean.ConsultaTramiteInteveFilterBean;
import org.gestoresmadrid.oegamInteve.view.bean.ResultadoTramiteInteveBean;
import org.gestoresmadrid.oegamInteve.view.bean.ResumenTramiteInteveBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaTramiteInteveAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -5740817991216040863L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaTramiteInteveAction.class);

	private static final String POP_UP_SELECCION_ESTADO = "popUpSeleccionEstado";

	private static final String[] fetchList = { "contrato", "contrato.colegiado", "tramitesInteves" };

	private static final String DESCARGAR_SOL_INTEVE = "descargarSolicitudes";

	ConsultaTramiteInteveFilterBean consultaTramiteInteve;

	@Resource
	ModelPagination modeloConsultaTramiteIntevePaginated;

	@Autowired
	ServicioConsultaTramiteInteve servicioConsultaTramiteInteve;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;

	private String ids;
	private String estadoNuevo;

	private InputStream inputStream;
	private String fileName;

	ResumenTramiteInteveBean resumen;

	public String cambiarEstado() {
		try {
			ResultadoTramiteInteveBean resultado = servicioConsultaTramiteInteve.cambiarEstado(ids, estadoNuevo, utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.tienePermisoAdmin());
			if(!resultado.getError()){
				resumen = resultado.getResumen();
			} else {
				addActionError(resultado.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado a los tramites seleccionados, error: ",e);
		}
		return actualizarPaginatedList();
	}

	public String validar() {
		try {
			ResultadoTramiteInteveBean resultado = servicioConsultaTramiteInteve.validarTramites(ids, utilesColegiado.getIdUsuarioSession());
			if(!resultado.getError()){
				resumen = resultado.getResumen();
			} else {
				addActionError(resultado.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar los tramites seleccionados, error: ",e);
		}
		return actualizarPaginatedList();
	}

	public String solicitar() {
		try {
			ResultadoTramiteInteveBean resultado = servicioConsultaTramiteInteve.solicitarTramites(ids, utilesColegiado.getIdUsuarioSession());
			if(!resultado.getError()){
				resumen = resultado.getResumen();
			} else {
				addActionError(resultado.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar los inteve tramites seleccionados, error: ",e);
		}
		return actualizarPaginatedList();
	}

	public String reiniciarEstd() {
		try {
			ResultadoTramiteInteveBean resultado = servicioConsultaTramiteInteve.reiniciarEstados(ids, utilesColegiado.getIdUsuarioSession());
			if(!resultado.getError()){
				resumen = resultado.getResumen();
			} else {
				addActionError(resultado.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de reiniciar los estados de los inteve tramites seleccionados, error: ",e);
		}
		return actualizarPaginatedList();
	}

	public String eliminar() {
		try {
			ResultadoTramiteInteveBean resultado = servicioConsultaTramiteInteve.eliminarTramites(ids, utilesColegiado.getIdUsuarioSession());
			if(!resultado.getError()){
				resumen = resultado.getResumen();
			} else {
				addActionError(resultado.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de los tramites seleccionados, error: ",e);
		}
		return actualizarPaginatedList();
	}

	public String descargar(){
		try {
			ResultadoTramiteInteveBean resultado = servicioConsultaTramiteInteve.descargarSolicitudes(ids);
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				inputStream = new FileInputStream(resultado.getFichero()); // TODO: 
				fileName = resultado.getNombreFichero();
				if(resultado.getEsZip()){
					servicioConsultaTramiteInteve.borrarZip(resultado.getFichero());
				}
				return DESCARGAR_SOL_INTEVE;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de descargar los informes de los tramites seleccionados, error: ",e);
		}
		return actualizarPaginatedList();
	}

	public String abrirSeleccionEstado() {
		return POP_UP_SELECCION_ESTADO;
	}

	@Override
	public Boolean validarFechas(Object object) {
		Boolean esRangoValido = Boolean.FALSE;
		String valorRangoFechas = gestorPropiedades.valorPropertie("valor.rango.busquedas");
		String mensaje = "";
		consultaTramiteInteve = (ConsultaTramiteInteveFilterBean) object;
		if(consultaTramiteInteve.getNumExpediente() == null && StringUtils.isBlank(consultaTramiteInteve.getBastidor())
			&& StringUtils.isBlank(consultaTramiteInteve.getCodigoTasa()) && StringUtils.isBlank(consultaTramiteInteve.getMatricula())
			&& StringUtils.isBlank(consultaTramiteInteve.getNive())){
			if(StringUtils.isNotBlank(valorRangoFechas)){
				if(consultaTramiteInteve.getFechaAlta() != null && !consultaTramiteInteve.getFechaAlta().isfechaNula()){
					if(consultaTramiteInteve.getFechaAlta().getFechaInicio() == null ||
							consultaTramiteInteve.getFechaAlta().getFechaFin() == null){
						mensaje = "Debe indicar un rango de fechas de alta no mayor a " + valorRangoFechas + " días para poder obtener los trámites.";
					} else {
						esRangoValido = utilesFecha.comprobarRangoFechas(consultaTramiteInteve.getFechaAlta().getFechaInicio(), consultaTramiteInteve.getFechaAlta().getFechaFin(), Integer.parseInt(valorRangoFechas));
						if(!esRangoValido){
							mensaje = "Debe indicar un rango de fechas de alta no mayor a " + valorRangoFechas + " días para poder obtener los trámites.";
						}
					}
				}
				if(consultaTramiteInteve.getFechaPresentacion() != null && !consultaTramiteInteve.getFechaPresentacion().isfechaNula()){
					if(consultaTramiteInteve.getFechaPresentacion().getFechaInicio() == null ||
							consultaTramiteInteve.getFechaPresentacion().getFechaFin() == null){
						if (StringUtils.isBlank(mensaje)) {
							mensaje = "Debe indicar un rango de fechas de presentación no mayor a " + valorRangoFechas + " días para poder obtener los trámites.";
						} else {
							mensaje = ", debe indicar un rango de fechas de presentación no mayor a " + valorRangoFechas + " días para poder obtener los trámites.";
						}
					} else {
						esRangoValido = utilesFecha.comprobarRangoFechas(consultaTramiteInteve.getFechaPresentacion().getFechaInicio(), consultaTramiteInteve.getFechaPresentacion().getFechaFin(), Integer.parseInt(valorRangoFechas));
						if (StringUtils.isBlank(mensaje)) {
							mensaje = "Debe indicar un rango de fechas de presentación no mayor a " + valorRangoFechas + " días para poder obtener los trámites.";
						} else {
							mensaje = ", debe indicar un rango de fechas de presentación no mayor a " + valorRangoFechas + " días para poder obtener los trámites.";
						}
					}
				}
			}
			if (!esRangoValido) {
				if(StringUtils.isNotBlank(mensaje)){
					addActionError(mensaje);
				} else{
					addActionError("Para poder realizar una búsqueda de trámites debe de indicar alguna fecha.");
				}
			}
		} else {
			esRangoValido = Boolean.TRUE;
		}
		return esRangoValido;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloConsultaTramiteIntevePaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if(consultaTramiteInteve == null){
			consultaTramiteInteve = new ConsultaTramiteInteveFilterBean();
		}
		if (consultaTramiteInteve.getMatricula() != null && !consultaTramiteInteve.getMatricula().isEmpty()) {
			consultaTramiteInteve.setMatricula(consultaTramiteInteve.getMatricula().trim().toUpperCase());
		}
		if (consultaTramiteInteve.getBastidor() != null && !consultaTramiteInteve.getBastidor().isEmpty()) {
			consultaTramiteInteve.setBastidor(consultaTramiteInteve.getBastidor().trim().toUpperCase());
		}
		if (consultaTramiteInteve.getNive() != null && !consultaTramiteInteve.getNive().isEmpty()) {
			consultaTramiteInteve.setNive(consultaTramiteInteve.getNive().trim().toUpperCase());
		}
		consultaTramiteInteve.setTipoTramite(TipoTramiteTrafico.Inteve.getValorEnum());
		if (!utilesColegiado.tienePermisoAdmin()) {
			consultaTramiteInteve.setIdContrato(utilesColegiado.getIdContratoSession());
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if (consultaTramiteInteve == null) {
			consultaTramiteInteve = new ConsultaTramiteInteveFilterBean();
		}
		consultaTramiteInteve.setFechaAlta(utilesFecha.getFechaFracionadaActual());
		if (!utilesColegiado.tienePermisoAdmin()) {
			consultaTramiteInteve.setIdContrato(utilesColegiado.getIdContratoSession());
		}
	}

	@Override
	public String getDecorator() {
		return "org.gestoresmadrid.oegam2.view.decorator.DecoratorTramiteInteve";
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaTramiteInteve;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		consultaTramiteInteve = (ConsultaTramiteInteveFilterBean) object;
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	public ConsultaTramiteInteveFilterBean getConsultaTramiteInteve() {
		return consultaTramiteInteve;
	}

	public void setConsultaTramiteInteve(ConsultaTramiteInteveFilterBean consultaTramiteInteve) {
		this.consultaTramiteInteve = consultaTramiteInteve;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(String estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public ResumenTramiteInteveBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenTramiteInteveBean resumen) {
		this.resumen = resumen;
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

}