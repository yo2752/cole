package org.gestoresmadrid.oegam2.consultaDev.controller.action;

import org.gestoresmadrid.oegam2comun.consultaDev.model.dto.ConsultaDevDto;
import org.gestoresmadrid.oegam2comun.consultaDev.model.service.ServicioDev;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;

public class AltaConsultaDevAction extends ActionBase{

	private static final long serialVersionUID = -6383732931872020938L;
	
	private static final String ALTA_DEV = "altaDev";
	
	private Long idConsultaDev;
	private ConsultaDevDto consultaDev;
	
	@Autowired
	ServicioDev servicioDev;
	
	@Autowired
	UtilesColegiado utilesColegiado;
	@Autowired
	private Utiles utiles;
	
	public String alta(){
		if(utiles.esUsuarioOegam3(utilesColegiado.getIdUsuarioSessionBigDecimal().toString())) {
			return "redireccionOegam3";
		}else {
			if(idConsultaDev != null){
				ResultBean resultado = servicioDev.getConsultaDev(idConsultaDev);
				if(!resultado.getError()){
					consultaDev = (ConsultaDevDto) resultado.getAttachment("consultaDevDto");
				}else{
					aniadirMensajeError(resultado);
				}
			}else{
				cargarValoresIniciales();
			}
			return ALTA_DEV;
		}
	}
	
	public String consultar(){
		ResultBean resultado = servicioDev.consultar(consultaDev,utilesColegiado.getIdUsuarioSessionBigDecimal(),false);
		if(resultado.getError()){
			aniadirMensajeError(resultado);
		} else {
			getConsultaDevDto(resultado, "Consulta Pendiente de DGT.");
		}
		return ALTA_DEV;
	}
	
	public String guardar(){
		ResultBean resultado = servicioDev.guardar(consultaDev,utilesColegiado.getIdUsuarioSessionBigDecimal());
		if(resultado.getError()){
			aniadirMensajeError(resultado);
		} else {
			getConsultaDevDto(resultado, "Consulta Dev guardada.");
		}
		return ALTA_DEV;
	}

	private void getConsultaDevDto(ResultBean resultado, String mensajeOk) {
		idConsultaDev = (Long) resultado.getAttachment(ServicioDev.idConsultaDevDto);
		resultado = servicioDev.getConsultaDev(idConsultaDev);
		if(!resultado.getError()){
			consultaDev = (ConsultaDevDto) resultado.getAttachment(ServicioDev.descConsultaDevDto);
			if(mensajeOk != null && !mensajeOk.isEmpty()){
				addActionMessage(mensajeOk);
			}
		}else{
			aniadirMensajeError(resultado);
		}
		
	}

	private void cargarValoresIniciales() {
		if(consultaDev == null){
			consultaDev = new ConsultaDevDto();
		}
		if(!utilesColegiado.tienePermisoAdmin()){
			ContratoDto contratoDto = new ContratoDto();
			contratoDto.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
			consultaDev.setContrato(contratoDto);
		}
	}

	public Long getIdConsultaDev() {
		return idConsultaDev;
	}

	public void setIdConsultaDev(Long idConsultaDev) {
		this.idConsultaDev = idConsultaDev;
	}

	public ConsultaDevDto getConsultaDev() {
		return consultaDev;
	}

	public void setConsultaDev(ConsultaDevDto consultaDev) {
		this.consultaDev = consultaDev;
	}
	
}
