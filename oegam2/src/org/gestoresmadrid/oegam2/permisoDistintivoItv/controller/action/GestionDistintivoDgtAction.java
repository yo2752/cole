package org.gestoresmadrid.oegam2.permisoDistintivoItv.controller.action;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.consulta.tramite.model.service.ServicioConsultaTramiteTrafico;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioGestionDistintivoDgt;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ConsultaDistintivoDgtFilterBean;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResumenDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ActualizarVehiculoDstvBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class GestionDistintivoDgtAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -7110817721537757641L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(GestionDistintivoDgtAction.class);

	private static final String[] fetchList = { "contrato", "vehiculo" };
	private static final String POP_UP_ESTADOS = "popUpCambioEstado";
	private String codSeleccionados;

	private ConsultaDistintivoDgtFilterBean consultaDistintivo;
	private ActualizarVehiculoDstvBean actualizarVehiculoDstvBean;

	private ResumenDistintivoDgtBean resumen;
	private BigDecimal estadoNuevo;
	private InputStream inputStream;
	private String fileName;
	private String fileSize;
	private Boolean existeDocJstfPermiso = Boolean.FALSE;
	private Boolean existeDocJstfDistintivo = Boolean.FALSE;
	private Boolean existeDocJstfEitv = Boolean.FALSE;
	private Boolean resumenDocumentosGeneradosFlag = false;

	@Resource
	ModelPagination modeloConsultaDistintivoPaginated;

	@Autowired
	GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	public Boolean getExisteDocJstfPermiso() {
		return existeDocJstfPermiso;
	}

	public void setExisteDocJstfPermiso(Boolean existeDocJstfPermiso) {
		this.existeDocJstfPermiso = existeDocJstfPermiso;
	}

	public Boolean getExisteDocJstfDistintivo() {
		return existeDocJstfDistintivo;
	}

	public void setExisteDocJstfDistintivo(Boolean existeDocJstfDistintivo) {
		this.existeDocJstfDistintivo = existeDocJstfDistintivo;
	}

	public Boolean getExisteDocJstfEitv() {
		return existeDocJstfEitv;
	}

	public void setExisteDocJstfEitv(Boolean existeDocJstfEitv) {
		this.existeDocJstfEitv = existeDocJstfEitv;
	}

	@Autowired
	ServicioGestionDistintivoDgt servicioGestionDistintivoDgt;

	@Autowired
	ServicioConsultaTramiteTrafico servicioConsultaTramiteTrafico;

	public String solicitarDstv() {
		try {
			ResultadoDistintivoDgtBean resultado = servicioGestionDistintivoDgt.solicitarDstvBloque(codSeleccionados,
					utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				resumen = resultado.getResumen();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar los permisos o distintivos, error: ", e);
			addActionError("Ha sucedido un error a la hora de solicitar los perimsos o distintivos.");
		}
		return actualizarPaginatedList();
	}

	public String cambiarEstadoDstv() {
		try {
			if (!utilesColegiado.tienePermisoAdmin()) {
				addActionError("No tiene permisos para cambiar el estado de las consultas.");
			} else {
				ResultadoDistintivoDgtBean resultado = servicioGestionDistintivoDgt
						.cambiarEstadoDstvBloque(codSeleccionados, estadoNuevo, utilesColegiado.getIdUsuarioSessionBigDecimal());
				if (resultado.getError()) {
					addActionError(resultado.getMensaje());
				} else {
					resumen = resultado.getResumen();
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado de las consultas, error: ", e);
			addActionError("Ha sucedido un error a la hora de cambiar el estado de las consultas.");
		}
		return actualizarPaginatedList();
	}

	public String generarDistintivo() {
		try {
			String[] codSeleccionados = prepararExpedientesSeleccionados();
			if (codSeleccionados != null) {
				ResultadoDistintivoDgtBean resultado = servicioGestionDistintivoDgt.generarDistintivos(codSeleccionados,
						utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.tienePermisoAdmin());
				if (resultado.getError()) {
					addActionError(resultado.getMensaje());
				} else {
					resumen = resultado.getResumen();
				}
			} else {
				addActionError("Debe de seleccionar algún expediente para poder generar los distintivos.");
			}
		} catch (Exception e) {
			log.error("No se ha podido generar el distintivo solicitado,error:", e);
			addActionError("No se ha podido generar el distintivo solicitado.");
		}
		return actualizarPaginatedList();

	}

	public String generarDemandaDstv() {
		try {
			String[] codSeleccionados = prepararExpedientesSeleccionados();
			if (codSeleccionados != null) {
				if (utilesColegiado.tienePermisoAdmin() || utilesColegiado.tienePermisoImpresionDstvGestor()) {
					ResultadoDistintivoDgtBean resultado = servicioGestionDistintivoDgt.generarDemandaDistintivos(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado
							.tienePermisoAdmin());
					if (resultado.getError()) {
						addActionError(resultado.getMensaje());
					} else {
						addActionMessage("Solicitud de distintivo generado correctamente.");
						if (resultado != null && resultado.getResumen() != null && resultado.getResumen().getListaOk() != null && !resultado.getResumen().getListaOk().isEmpty()) {
							for (String mensajeOk : resultado.getResumen().getListaOk()) {
								addActionMessage(" - " + mensajeOk);
							}
						}
					}
				} else {
					addActionError("No tiene permisos para realizar dicha accion.");
				}
			} else {
				addActionError("Debe de seleccionar algún expediente para poder generar los distintivos.");
			}
		} catch (Exception e) {
			log.error("No se ha podido generar el distintivo solicitado,error:", e);
			addActionError("No se ha podido generar el distintivo solicitado.");
		}
		return actualizarPaginatedList();
	}

	public String revertir() {
		try {
			String[] codSeleccionados = prepararExpedientesSeleccionados();
			if (codSeleccionados != null) {
				ResultadoDistintivoDgtBean resultado = servicioGestionDistintivoDgt.revertir(codSeleccionados,
						utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.tienePermisoAdmin());
				if (resultado.getError()) {
					addActionError(resultado.getMensaje());
				} else {
					resumen = resultado.getResumen();
				}
			}
		} catch (Exception e) {
			log.error("No se ha podido generar el distintivo solicitado,error:", e);
			addActionError("No se ha podido generar el distintivo solicitado.");
		}
		return actualizarPaginatedList();
	}
	
	public String desasignar(){
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			ResultadoDistintivoDgtBean resultado = servicioGestionDistintivoDgt.desasignar(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (!resultado.getError()) {
				resumen = resultado.getResumen();
			} else {
				addActionError(resultado.getMensaje());
			}
		}else{
			addActionError("Debe seleccionar algun documento para desasignar.");
		}
		return actualizarPaginatedList();
	}

	@Override
	public Boolean validarFechas(Object object) {
		Boolean esRangoValido = Boolean.FALSE;
		String valorRangoFechas = gestorPropiedades.valorPropertie("valor.rango.busquedas");
		consultaDistintivo = (ConsultaDistintivoDgtFilterBean) object;
		if(StringUtils.isBlank(consultaDistintivo.getMatricula()) && StringUtils.isBlank(consultaDistintivo.getBastidor())
			&& StringUtils.isBlank(consultaDistintivo.getRefPropia()) && consultaDistintivo.getNumExpediente() == null 
			&& StringUtils.isBlank(consultaDistintivo.getNif())){
			if(consultaDistintivo.getFechaPresentacion() != null && !consultaDistintivo.getFechaPresentacion().isfechaNula() && 
					consultaDistintivo.getFechaPresentacion().getFechaInicio() != null &&
							consultaDistintivo.getFechaPresentacion().getFechaFin() != null){
				if(StringUtils.isNotBlank(valorRangoFechas)){
					esRangoValido = utilesFecha.comprobarRangoFechas(consultaDistintivo.getFechaPresentacion().getFechaInicio(), 
							consultaDistintivo.getFechaPresentacion().getFechaFin(), Integer.parseInt(valorRangoFechas));
					
				}
			}
			if(!esRangoValido){
				addActionError("Debe indicar un rango de fechas no mayor a " + valorRangoFechas + " dias para poder obtener los datos de los documentos.");
			}
		} else {
			esRangoValido = Boolean.TRUE;
		}
		return esRangoValido;
	}
	
	private String[] prepararExpedientesSeleccionados() {
		String[] codSelecc = null;
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			codSelecc = codSeleccionados.split(";");
		}
		return codSelecc;
	}

	public String cargarPopUpCambioEstado() {
		return POP_UP_ESTADOS;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloConsultaDistintivoPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		List<BigDecimal> listaEstadosTram = new ArrayList<BigDecimal>();
		if (utilesColegiado.tienePermisoAdmin() || utilesColegiado.tienePermisoColegio()) {
			listaEstadosTram.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum()));
		}
		listaEstadosTram.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()));
		listaEstadosTram.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()));
		listaEstadosTram.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()));
		consultaDistintivo.setEstadoTramite(listaEstadosTram);
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			consultaDistintivo.setIdContrato(utilesColegiado.getIdContratoSession());
		}
		List<String> listaEstados = null;
		if (EstadoPermisoDistintivoItv.Iniciado.getValorEnum().equals(consultaDistintivo.getEstadoSolicitud())) {
			listaEstados = new ArrayList<String>();
			listaEstados.add(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
			consultaDistintivo.setListaEstadoPeticionPermDstv(listaEstados);
			consultaDistintivo.setEstadoPeticionDstv(null);
		} else {
			consultaDistintivo.setEstadoPeticionDstv(consultaDistintivo.getEstadoSolicitud());
			consultaDistintivo.setListaEstadoPeticionPermDstv(null);
		}

		if (EstadoPermisoDistintivoItv.Iniciado.getValorEnum().equals(consultaDistintivo.getEstadoImpresion())) {
			listaEstados = new ArrayList<String>();
			listaEstados.add(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
			consultaDistintivo.setListaEstadoImpresionDstv(listaEstados);
			consultaDistintivo.setEstadoImpresionDstv(null);
		} else {
			consultaDistintivo.setEstadoImpresionDstv(consultaDistintivo.getEstadoImpresion());
			consultaDistintivo.setListaEstadoImpresionDstv(null);
		}
		
		if(consultaDistintivo.getNif() != null && consultaDistintivo.getNif().isEmpty()){
			consultaDistintivo.setNif(consultaDistintivo.getNif().trim().replace("-", "").toUpperCase());
		}
		
		if(consultaDistintivo.getMatricula() != null && !consultaDistintivo.getMatricula().isEmpty()){
			consultaDistintivo.setMatricula(consultaDistintivo.getMatricula().trim().replace("-", "").toUpperCase());
		}

	}

	@Override
	protected void cargarFiltroInicial() {
		if (consultaDistintivo == null) {
			consultaDistintivo = new ConsultaDistintivoDgtFilterBean();
		}
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			consultaDistintivo.setIdContrato(utilesColegiado.getIdContratoSession());
		}
		consultaDistintivo.setFechaPresentacion(utilesFecha.getFechaFracionadaActual());
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaDistintivo;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		consultaDistintivo = (ConsultaDistintivoDgtFilterBean) object;
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
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

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public BigDecimal getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(BigDecimal estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public Boolean getResumenDocumentosGeneradosFlag() {
		return resumenDocumentosGeneradosFlag;
	}

	public void setResumenDocumentosGeneradosFlag(Boolean resumenDocumentosGeneradosFlag) {
		this.resumenDocumentosGeneradosFlag = resumenDocumentosGeneradosFlag;
	}

	public ResumenDistintivoDgtBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenDistintivoDgtBean resumen) {
		this.resumen = resumen;
	}

	public ConsultaDistintivoDgtFilterBean getConsultaDistintivo() {
		return consultaDistintivo;
	}

	public void setConsultaDistintivo(ConsultaDistintivoDgtFilterBean consultaDistintivo) {
		this.consultaDistintivo = consultaDistintivo;
	}

	public ActualizarVehiculoDstvBean getActualizarVehiculoDstvBean() {
		return actualizarVehiculoDstvBean;
	}

	public void setActualizarVehiculoDstvBean(ActualizarVehiculoDstvBean actualizarVehiculoDstvBean) {
		this.actualizarVehiculoDstvBean = actualizarVehiculoDstvBean;
	}

	public ServicioConsultaTramiteTrafico getServicioConsultaTramiteTrafico() {
		return servicioConsultaTramiteTrafico;
	}

	public void setServicioConsultaTramiteTrafico(ServicioConsultaTramiteTrafico servicioConsultaTramiteTrafico) {
		this.servicioConsultaTramiteTrafico = servicioConsultaTramiteTrafico;
	}

	public static String[] getFetchlist() {
		return fetchList;
	}

	public ModelPagination getModeloConsultaDistintivoPaginated() {
		return modeloConsultaDistintivoPaginated;
	}

	public void setModeloConsultaDistintivoPaginated(ModelPagination modeloConsultaDistintivoPaginated) {
		this.modeloConsultaDistintivoPaginated = modeloConsultaDistintivoPaginated;
	}

	public ServicioGestionDistintivoDgt getServicioGestionDistintivoDgt() {
		return servicioGestionDistintivoDgt;
	}

	public void setServicioGestionDistintivoDgt(ServicioGestionDistintivoDgt servicioGestionDistintivoDgt) {
		this.servicioGestionDistintivoDgt = servicioGestionDistintivoDgt;
	}
}
