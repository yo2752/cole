package org.gestoresmadrid.oegam2.permisoDistintivoItv.controller.action;

import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioDistintivoVehNoMat;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.distintivo.view.dto.VehiculoNoMatriculadoOegamDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class AltaVehNoMatOegamAction extends ActionBase {

	private static final long serialVersionUID = 8774471527311563586L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(AltaVehNoMatOegamAction.class);
	
	private static final String ALTA_VEH_NO_MATRICULADO = "altaVehNoMatOegam";
	
	private VehiculoNoMatriculadoOegamDto vehiculoNoMatriculadoDto;
	private Long idVehNotMatOegam;
	
	
	@Autowired
	ServicioDistintivoVehNoMat servicioDistintivoVehNoMat;
	
	@Autowired
	UtilesColegiado utilesColegiado;

	public String inicio(){
		try {
			if(idVehNotMatOegam != null){
				ResultadoDistintivoDgtBean resultado = servicioDistintivoVehNoMat.getVehNoMatrPantalla(idVehNotMatOegam);
				if(resultado.getError()){
					addActionError(resultado.getMensaje());
				} else {
					vehiculoNoMatriculadoDto = resultado.getVehiculoNoMatrOegam();
				}
			} else {
				vehiculoNoMatriculadoDto = new VehiculoNoMatriculadoOegamDto();
				if(!utilesColegiado.tienePermisoAdmin()){
					vehiculoNoMatriculadoDto.setContrato(new ContratoDto());
					vehiculoNoMatriculadoDto.getContrato().setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
					vehiculoNoMatriculadoDto.setNumColegiado(utilesColegiado.getNumColegiadoSession());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el detalle del vehiculo no matriculado en oegam, error: ",e);
			addActionError("Ha sucedido un error a la hora de obtener el detalle.");
		}
		return ALTA_VEH_NO_MATRICULADO;
	}
	
	public String guardar() {
		try {
			ResultadoDistintivoDgtBean resultado = servicioDistintivoVehNoMat.guardarVehNoMatOegam(vehiculoNoMatriculadoDto, 
					utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultado.getError()){
					addActionError(resultado.getMensaje());
			}else{
				vehiculoNoMatriculadoDto = resultado.getVehiculoNoMatrOegam();
				addActionMessage( "El vehículo con matricula " +vehiculoNoMatriculadoDto.getMatricula() + " se ha guardado correctamente.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el vehículo, error: ",e);
			addActionError("Ha sucedido un error a la hora de guardar el vehículo.");
		}
		return ALTA_VEH_NO_MATRICULADO;
	}

	public String solicitar(){
		try {
			ResultadoDistintivoDgtBean resultado = servicioDistintivoVehNoMat.solicitarDstv(vehiculoNoMatriculadoDto, 
					utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultado.getError()){
					addActionError(resultado.getMensaje());
			}else{
				addActionMessage(resultado.getMensaje());
				resultado = servicioDistintivoVehNoMat.getVehNoMatrPantalla(vehiculoNoMatriculadoDto.getId());
				if(!resultado.getError()){
					vehiculoNoMatriculadoDto = resultado.getVehiculoNoMatrOegam();
				} else {
					addActionError(resultado.getMensaje());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar el distintivo del vehículo, error: ",e);
			addActionError("Ha sucedido un error a la hora de solicitar el distintivo del vehículo.");
		}
		return ALTA_VEH_NO_MATRICULADO;
	}
	
	public String generar(){
		try {
			ResultadoDistintivoDgtBean resultado = servicioDistintivoVehNoMat.solicitarImpresionDstv(vehiculoNoMatriculadoDto, 
					utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultado.getError()){
					addActionError(resultado.getMensaje());
			}else{
				addActionMessage(resultado.getMensaje());
				resultado = servicioDistintivoVehNoMat.getVehNoMatrPantalla(vehiculoNoMatriculadoDto.getId());
				if(!resultado.getError()){
					vehiculoNoMatriculadoDto = resultado.getVehiculoNoMatrOegam();
				} else {
					addActionError(resultado.getMensaje());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar la impresión del distintivo del vehículo, error: ",e);
			addActionError("Ha sucedido un error a la hora de solicitar la impresión del distintivo del vehículo.");
		}
		return ALTA_VEH_NO_MATRICULADO;
	}
	
	public String autorizar(){
		try {
			ResultadoDistintivoDgtBean resultado = servicioDistintivoVehNoMat.autorizarImpresionDstv(vehiculoNoMatriculadoDto, 
					utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultado.getError()){
					addActionError(resultado.getMensaje());
			}else{
				addActionMessage(resultado.getMensaje());
				resultado = servicioDistintivoVehNoMat.getVehNoMatrPantalla(vehiculoNoMatriculadoDto.getId());
				if(!resultado.getError()){
					vehiculoNoMatriculadoDto = resultado.getVehiculoNoMatrOegam();
				} else {
					addActionError(resultado.getMensaje());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de autorizar la impresión del distintivo del vehículo, error: ",e);
			addActionError("Ha sucedido un error a la hora de autorizar la impresión del distintivo del vehículo.");
		}
		return ALTA_VEH_NO_MATRICULADO;
	}

	public VehiculoNoMatriculadoOegamDto getVehiculoNoMatriculado() {
		return vehiculoNoMatriculadoDto;
	}


	public void setVehiculoNoMatriculado(VehiculoNoMatriculadoOegamDto vehiculoNoMatriculado) {
		this.vehiculoNoMatriculadoDto = vehiculoNoMatriculado;
	}

	public VehiculoNoMatriculadoOegamDto getVehiculoNoMatriculadoDto() {
		return vehiculoNoMatriculadoDto;
	}

	public void setVehiculoNoMatriculadoDto(VehiculoNoMatriculadoOegamDto vehiculoNoMatriculadoDto) {
		this.vehiculoNoMatriculadoDto = vehiculoNoMatriculadoDto;
	}

	public Long getIdVehNotMatOegam() {
		return idVehNotMatOegam;
	}

	public void setIdVehNotMatOegam(Long idVehNotMatOegam) {
		this.idVehNotMatOegam = idVehNotMatOegam;
	}
	
	
}
