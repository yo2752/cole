package org.gestoresmadrid.oegam2.permisoDistintivoItv.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioConsultaDistintivoVehNoMat;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ConsultaVehNoMatrOegamFilterBean;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResumenDistintivoDgtBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaVehNoMatOegamAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -2580163146126946824L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaVehNoMatOegamAction.class);
	
	private static final String POP_UP_ESTADOS = "popUpCambioEstado";
	private static final String ALTA_VEH_NO_MATRICULADO = "irAltaVehNoMaO";
	
	private String codSeleccionados;
	private String estadoNuevo;
	private String fileName;
	private Boolean existeDocJstfDistintivo = Boolean.FALSE;
	private Boolean resumenDocumentosGeneradosFlag = false;
	private Long idVehNotMatOegam;

	@Autowired
	ServicioConsultaDistintivoVehNoMat servicioConsultaDistintivoVehNoMat;

	private ResumenDistintivoDgtBean resumen;

	private ConsultaVehNoMatrOegamFilterBean consultaVehiculo;

	@Resource
	ModelPagination modeloConsultaVehNoMatrOegamPaginated;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String detalle(){
		if(idVehNotMatOegam == null){
			addActionError("No se ha indicado id para poder acceder a su detalle.");
			return actualizarPaginatedList();
		}
		return ALTA_VEH_NO_MATRICULADO;
	}
	
	public String solicitarDstv() {
		try {
			ResultadoDistintivoDgtBean resultado = servicioConsultaDistintivoVehNoMat.solicitarDstv(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				resumen = resultado.getResumen();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar los  distintivos, error: ", e);
			addActionError("Ha sucedido un error a la hora de solicitar los distintivos.");
		}
		return actualizarPaginatedList();
	}
	
	public String autorizar(){
		try {
			if (codSeleccionados != null) {
				ResultadoDistintivoDgtBean resultado = servicioConsultaDistintivoVehNoMat.autorizarDstv(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal());
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

	public String generarDistintivo() {
		try {
		if (codSeleccionados != null) {
			ResultadoDistintivoDgtBean resultado = servicioConsultaDistintivoVehNoMat.solictarImpresionDstv(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal());
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

	public String generarDemandaDstv() {
		try {
			if (codSeleccionados != null) {
				if(utilesColegiado.tienePermisoAdmin() || utilesColegiado.tienePermisoImpresionDstvGestor()){
					ResultadoDistintivoDgtBean resultado = servicioConsultaDistintivoVehNoMat.generarDemandaDistintivos(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal());
					if (resultado.getError()) {
						addActionError(resultado.getMensaje());
					} else {
						resumen = resultado.getResumen();
					}
				} else {
					addActionError("No tiene permisos para poder generar el documento con los distintivos.");
				}
			} else {
				addActionError("Debe de seleccionar alguna matricula para poder generar su documento.");
			}
		} catch (Exception e) {
			log.error("No se ha podido generar el distintivo solicitado,error:", e);
			addActionError("No se ha podido generar el distintivo solicitado.");
		}
		return actualizarPaginatedList();
	}

	public String revertir() {
		try {
			if (codSeleccionados != null) {
				if(utilesColegiado.tienePermisoAdmin()){
					ResultadoDistintivoDgtBean resultado = servicioConsultaDistintivoVehNoMat.revertir(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal());
					if (resultado.getError()) {
						addActionError(resultado.getMensaje());
					} else {
						resumen = resultado.getResumen();
					}
				} else {
					addActionError("No tiene permisos para poder revertir las matriculas seleccionadas.");
				}
			} else {
				addActionError("Debe de seleccionar alguna matricula para poder generar su documento.");
			}
		} catch (Exception e) {
			log.error("No se ha podido generar el distintivo solicitado,error:", e);
			addActionError("No se ha podido generar el distintivo solicitado.");
		}
		return actualizarPaginatedList();
	}

	
	public String cambiarEstadoDstv(){
		try {
			if(!utilesColegiado.tienePermisoAdmin()){
				addActionError("No tiene permisos para cambiar el estado de las matriculas.");
			} else {
				ResultadoDistintivoDgtBean resultado = servicioConsultaDistintivoVehNoMat.cambiarEstadoDstv(codSeleccionados,estadoNuevo, utilesColegiado.getIdUsuarioSessionBigDecimal());
				if(resultado.getError()){
					addActionError(resultado.getMensaje());
				} else {
					resumen = resultado.getResumen();
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado de las consultas, error: ",e);
			addActionError("Ha sucedido un error a la hora de cambiar el estado de las consultas.");
		}
		return actualizarPaginatedList();
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
		return modeloConsultaVehNoMatrOegamPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if(consultaVehiculo != null){
			if(consultaVehiculo.getBastidor() != null && !consultaVehiculo.getBastidor().isEmpty()){
				consultaVehiculo.setBastidor("%" + consultaVehiculo.getBastidor().trim().toUpperCase() + "%");
			}
			if(consultaVehiculo.getMatricula() != null && !consultaVehiculo.getMatricula().isEmpty()){
				consultaVehiculo.setMatricula(consultaVehiculo.getMatricula().trim().toUpperCase());
			}
			if(consultaVehiculo.getNive() != null && !consultaVehiculo.getNive().isEmpty()){
				consultaVehiculo.setNive("%" + consultaVehiculo.getNive().trim().toUpperCase() + "%");
			}
			if(!utilesColegiado.tienePermisoAdmin()){
				consultaVehiculo.setIdContrato(utilesColegiado.getIdContratoSession());
			}
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if(consultaVehiculo == null){
			consultaVehiculo = new ConsultaVehNoMatrOegamFilterBean();
		}
		consultaVehiculo.setFechaAlta(utilesFecha.getFechaFracionadaActual());
		if(!utilesColegiado.tienePermisoAdmin()){
			consultaVehiculo.setIdContrato(utilesColegiado.getIdContratoSession());
		}
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaVehiculo;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		consultaVehiculo = (ConsultaVehNoMatrOegamFilterBean) object;

	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}

	public ResumenDistintivoDgtBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenDistintivoDgtBean resumen) {
		this.resumen = resumen;
	}

	public ConsultaVehNoMatrOegamFilterBean getConsultaVehiculo() {
		return consultaVehiculo;
	}

	public void setConsultaVehiculo(ConsultaVehNoMatrOegamFilterBean consultaVehiculo) {
		this.consultaVehiculo = consultaVehiculo;
	}

	public ModelPagination getModeloConsultaVehNoMatrOegamPaginated() {
		return modeloConsultaVehNoMatrOegamPaginated;
	}

	public void setModeloConsultaVehNoMatrOegamPaginated(ModelPagination modeloConsultaVehNoMatrOegamPaginated) {
		this.modeloConsultaVehNoMatrOegamPaginated = modeloConsultaVehNoMatrOegamPaginated;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Boolean getExisteDocJstfDistintivo() {
		return existeDocJstfDistintivo;
	}

	public void setExisteDocJstfDistintivo(Boolean existeDocJstfDistintivo) {
		this.existeDocJstfDistintivo = existeDocJstfDistintivo;
	}

	public Boolean getResumenDocumentosGeneradosFlag() {
		return resumenDocumentosGeneradosFlag;
	}

	public void setResumenDocumentosGeneradosFlag(Boolean resumenDocumentosGeneradosFlag) {
		this.resumenDocumentosGeneradosFlag = resumenDocumentosGeneradosFlag;
	}

	public String getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(String estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public Long getIdVehNotMatOegam() {
		return idVehNotMatOegam;
	}

	public void setIdVehNotMatOegam(Long idVehNotMatOegam) {
		this.idVehNotMatOegam = idVehNotMatOegam;
	}
	

}
