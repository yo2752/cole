/**
 * 
 */
package org.gestoresmadrid.oegam2.trafico.eeff.controller.action;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioConsultaEEFF;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.beans.ConsultaEEFFFilterBean;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.beans.ResultadoConsultaEEFF;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.beans.ResumenConsultaEEFFBean;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.ConsultaEEFFDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaEEFFAction extends AbstractPaginatedListAction{

	private static final long serialVersionUID = 1987619245859732339L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaEEFFAction.class);
	
	private static final String ALTA_CONSULTA_EEFF = "aAltaConsultaEEFFF";
	private static final String POP_UP_ESTADOS = "popPupCambioEstado";

	@Autowired
	ServicioConsultaEEFF servicioConsultaEEFF;
	
	@Resource
	private ModelPagination modeloConsultaEEFFPaginated;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	private ConsultaEEFFFilterBean consultaEEFFFilterBean;
	
	private static final String COLUMDEFECT = "numExpediente";
	private String codSeleccionados;
	private String estadoNuevo;
	private String numExpediente;
	private ConsultaEEFFDto consultaEEFF;
	private ResumenConsultaEEFFBean resumen;
	
	public String consultar(){
		if (!utilesColegiado.tienePermisoConsultaEEFF()){
			addActionError("No tiene permiso para usar la consulta de EEFF.");
			log.error("El colegiado "+ utilesColegiado.getNumColegiadoSession() + " ha intentado consultar EEFF y no tiene permisos");
			return ERROR;
		}
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			ResultadoConsultaEEFF resultado = servicioConsultaEEFF.consultarBloque(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			}else{
				rellenarResumen(resultado, Boolean.TRUE, Boolean.FALSE);
			}
		}else{
			addActionError("Debe seleccionar alguna consulta EEFF.");
		}
	
		return actualizarPaginatedList();
	}
	
	public String cambiarEstado(){
		if (!utilesColegiado.tienePermisoAdmin()){
			addActionError("No tiene permiso para realizar el cambio de estado de las consultas de EEFF.");
			log.error("El colegiado "+ utilesColegiado.getNumColegiadoSession() + " ha intentado cambiar el estado a las consultas EEFF.");
			return ERROR;
		}
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			ResultadoConsultaEEFF resultado = servicioConsultaEEFF.cambiarEstadoBloque(codSeleccionados, estadoNuevo, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			}else{
				rellenarResumen(resultado, Boolean.FALSE, Boolean.TRUE);
			}
		}else{
			addActionError("Debe seleccionar alguna consulta EEFF para cambiar su estado.");
		}
		return actualizarPaginatedList();
	}
	
	public String eliminar(){
		if (!utilesColegiado.tienePermisoConsultaEEFF()){
			addActionError("No tiene permiso para anular las consultas de EEFF.");
			log.error("El colegiado "+ utilesColegiado.getNumColegiadoCabecera() + " ha intentado anular las consultas EEFF.");
			return ERROR;
		}
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			ResultadoConsultaEEFF resultado = servicioConsultaEEFF.eliminar(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			}else{
				rellenarResumen(resultado,Boolean.FALSE, Boolean.TRUE);
			}
		}else{
			addActionError("Debe seleccionar alguna consulta EEFF para anular.");
		}
		return actualizarPaginatedList();
	}
	
	public String alta() {
		if(numExpediente != null && !numExpediente.isEmpty()){
			consultaEEFF = new ConsultaEEFFDto();
			consultaEEFF.setNumExpediente(new BigDecimal(numExpediente));
			return ALTA_CONSULTA_EEFF;
		} else{
			addActionError("Debe de indicar la consulta EEFF que desea conocer su detalle");
		}
		return actualizarPaginatedList();
	}
	
	public String cargarPopUpCambioEstado(){
		return POP_UP_ESTADOS;
	}
	
	private void rellenarResumen(ResultadoConsultaEEFF resultado, Boolean esConsultar, Boolean esCambioEstado) {
		resumen = new ResumenConsultaEEFFBean();
		resumen.setNumError(resultado.getNumError());
		resumen.setListaErrores(resultado.getListaErrores());
		resumen.setNumOk(resultado.getNumOk());
		resumen.setListaOk(resultado.getListaOK());
		resumen.setResumenCambioEstado(esCambioEstado);
		resumen.setResumenConsultar(esConsultar);
	}

	@Override
	public String getColumnaPorDefecto() {
		return COLUMDEFECT;
	}

	@Override
	public String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloConsultaEEFFPaginated;
	}

	@Override
	protected void cargaRestricciones() {
		if(!utilesColegiado.tienePermisoAdmin()){
			consultaEEFFFilterBean.setIdContrato(utilesColegiado.getIdContratoSession());
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if(consultaEEFFFilterBean == null){
			consultaEEFFFilterBean = new ConsultaEEFFFilterBean();
		}
		if(!utilesColegiado.tienePermisoAdmin()){
			consultaEEFFFilterBean.setIdContrato(utilesColegiado.getIdContratoSession());
		}
		consultaEEFFFilterBean.setFechaAlta(utilesFecha.getFechaFracionadaActual());
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaEEFFFilterBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		consultaEEFFFilterBean = (ConsultaEEFFFilterBean) object;	
	}

	public ModelPagination getModeloConsultaEEFFPaginated() {
		return modeloConsultaEEFFPaginated;
	}

	public void setModeloConsultaEEFFPaginated(
			ModelPagination modeloConsultaEEFFPaginated) {
		this.modeloConsultaEEFFPaginated = modeloConsultaEEFFPaginated;
	}

	public ConsultaEEFFFilterBean getConsultaEEFFFilterBean() {
		return consultaEEFFFilterBean;
	}

	public void setConsultaEEFFFilterBean(
			ConsultaEEFFFilterBean consultaEEFFFilterBean) {
		this.consultaEEFFFilterBean = consultaEEFFFilterBean;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}
	
	public ConsultaEEFFDto getConsultaEEFF() {
		return consultaEEFF;
	}

	public void setConsultaEEFF(ConsultaEEFFDto consultaEEFF) {
		this.consultaEEFF = consultaEEFF;
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

	public ResumenConsultaEEFFBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenConsultaEEFFBean resumen) {
		this.resumen = resumen;
	}
	
}
