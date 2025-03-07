package org.gestoresmadrid.oegam2.trafico.eeff.controller.action;

import java.math.BigDecimal;

import org.gestoresmadrid.core.trafico.eeff.model.enumerados.EstadoConsultaEEFF;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioEEFFNuevo;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.ConsultaEEFFDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;

public class AltaConsultaEEFFAction extends ActionBase{

	private static final long serialVersionUID = 8028127153940263722L;
	
	private static final String POP_PUP_RESULTADO_WS = "popPupResultadoWs";
	
	private ConsultaEEFFDto consultaEEFF; 
	
	@Autowired
	ServicioEEFFNuevo servicioEEFF;
	
	@Autowired
	private UtilesColegiado utilesColegiado;
	
	public String alta(){
		if(consultaEEFF != null && consultaEEFF.getNumExpediente() != null){
			ResultBean resultado = servicioEEFF.obtenerDetalleConsultaEEFF(consultaEEFF.getNumExpediente());
			if(!resultado.getError()){
				consultaEEFF = (ConsultaEEFFDto) resultado.getAttachment(ServicioEEFFNuevo.CONSULTA_EEFF_DTO);
				aniadirMensajeAviso(resultado, null);
			} else {
				aniadirMensajeError(resultado);
				cargarDatosIniciales();
			}
		} else {
			cargarDatosIniciales();
		}
		return SUCCESS;
	}
	
	public String guardar(){
		ResultBean resultado = servicioEEFF.guardarConsultaEEFF(consultaEEFF);
		if(!resultado.getError()){
			getConsultaEEFFDto(resultado,"La consulta se ha guardado correctamente.");
		} else {
			aniadirMensajeError(resultado);
		}
		return SUCCESS;
	}

	public String consultar(){
		ResultBean resultado = servicioEEFF.guardarConsultaEEFF(consultaEEFF);
		if(!resultado.getError()){
			resultado = servicioEEFF.consultarEEFF(consultaEEFF.getNumExpediente(),utilesColegiado.getIdUsuarioSessionBigDecimal(),Boolean.FALSE);
			if(!resultado.getError()){
				getConsultaEEFFDto(resultado,"La petición de consulta EEFF se ha generado correctamente.");
			} else {
				aniadirMensajeError(resultado);
			}
		} else {
			aniadirMensajeError(resultado);
		}
		return SUCCESS;
	}
	
	public String cargarPopUpResultadoWS(){
		if(consultaEEFF != null && consultaEEFF.getNumExpediente() != null){
			ResultBean resultado = servicioEEFF.obtenerDetalleConsultaEEFF(consultaEEFF.getNumExpediente());
			if(!resultado.getError()){
				consultaEEFF = (ConsultaEEFFDto) resultado.getAttachment(ServicioEEFFNuevo.CONSULTA_EEFF_DTO);
			} else {
				addActionError(resultado.getMensaje());
			}
		} else {
			addActionError("Debe de seleccionar un expediente de consulta EEFF para poder obtener su resultado");
		}
		return POP_PUP_RESULTADO_WS;
	}
	
	private void getConsultaEEFFDto(ResultBean resultado, String mensajeOk) {
		consultaEEFF.setNumExpediente((BigDecimal) resultado.getAttachment(ServicioEEFFNuevo.NUM_EXPEDIENTE_CONSULTA_EEFF_DTO));
		if(consultaEEFF.getNumExpediente() != null){
			ResultBean resultBean = servicioEEFF.obtenerDetalleConsultaEEFF(consultaEEFF.getNumExpediente());
			if(!resultBean.getError()){
				consultaEEFF = (ConsultaEEFFDto) resultBean.getAttachment(ServicioEEFFNuevo.CONSULTA_EEFF_DTO);
				addActionMessage(mensajeOk);
			} else {
				aniadirMensajeError(resultBean);
			}
		}
	}

	private void cargarDatosIniciales() {
		consultaEEFF = new ConsultaEEFFDto();
		if(!utilesColegiado.tienePermisoAdmin()){
			consultaEEFF.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			ContratoDto contratoDto = new ContratoDto();
			contratoDto.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
			consultaEEFF.setContrato(contratoDto);
		}
		consultaEEFF.setEstado(EstadoConsultaEEFF.Iniciado.getValorEnum());
	}

	public ConsultaEEFFDto getConsultaEEFF() {
		return consultaEEFF;
	}

	public void setConsultaEEFF(ConsultaEEFFDto consultaEEFF) {
		this.consultaEEFF = consultaEEFF;
	}
	
	
}
