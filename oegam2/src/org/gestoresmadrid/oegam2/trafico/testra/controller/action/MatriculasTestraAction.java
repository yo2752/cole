package org.gestoresmadrid.oegam2.trafico.testra.controller.action;

import org.gestoresmadrid.core.vehiculo.model.enumerados.EstadoVehiculo;
import org.gestoresmadrid.oegam2comun.trafico.testra.service.ServicioConsultasTestra;
import org.gestoresmadrid.oegam2comun.trafico.testra.service.impl.ServicioConsultasTestraImpl;
import org.gestoresmadrid.oegam2comun.trafico.testra.view.dto.ConsultaTestraDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class MatriculasTestraAction extends ActionBase {

	private static final ILoggerOegam log = LoggerOegam.getLogger(MatriculasTestraAction.class);
	private static final long serialVersionUID = 5060452115524184755L;

	private Long idConsultaTestra;
	
	private boolean vieneDeResumen = false;
	
	@Autowired
	private ServicioConsultasTestra servicioConsultasTestra;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	private ConsultaTestraDto consultaTestra;
	
	@Autowired
	private Utiles utiles;

	public String alta() {
		return SUCCESS;
		/*if(utiles.esUsuarioOegam3(utilesColegiado.getNumColegiadoSession())) {
			return "redireccionOegam3";
		}else {
			return SUCCESS;
		}*/
	}

	public String guardar() {

		if (!utilesColegiado.tienePermisoAdmin() || consultaTestra.getNumColegiado() == null || consultaTestra.getNumColegiado().isEmpty()) {
			consultaTestra.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}

		ResultBean result = null;
		try {
			result = servicioConsultasTestra.guardarConsulta(consultaTestra);
		} catch (RuntimeException r) {
			log.error("Error al guardar consulta testra", r);
		}
		if (result == null) {
			addActionError("Ocurrió un error al guardar los datos");

		} else if (result.getError()) {
			if (result.getListaMensajes() != null) {
				for (String mensaje : result.getListaMensajes()) {
					addActionError(mensaje);
				}
			}
		} else {
			consultaTestra = (ConsultaTestraDto) result.getAttachment(ServicioConsultasTestraImpl.KEY_RESULT);
			addActionMessage(result.getMensaje());
		}
		return SUCCESS;
	}
	
	public String detalle(){
		if(idConsultaTestra != null ){	
			try{
				ResultBean resultado =  servicioConsultasTestra.getConsultaTestraDto(idConsultaTestra);		
				if (resultado.getError()){
					addActionError(resultado.getMensaje());
				} else {
					consultaTestra = (ConsultaTestraDto) resultado.getAttachment(ServicioConsultasTestraImpl.KEY_RESULT);
				}				
			}catch (Throwable e){
				log.error("Error obteniendo consulta de testra con id: " + consultaTestra.getId().toString()  + e);	
				addActionError("Error obteniendo consulta de testra con id: " + consultaTestra.getId().toString());
			}						
		}else{
			addActionError("Error obteniendo consulta de testra con id: " + consultaTestra.getId().toString());
		}
		
		return SUCCESS;
	}

	public String activar(){		
		if (consultaTestra!= null && consultaTestra.getId()!=null){
			try{
				servicioConsultasTestra.actualizarActivacion(EstadoVehiculo.Activo.getValorEnum(), consultaTestra.getId());
				ResultBean resultado = servicioConsultasTestra.getConsultaTestraDto(consultaTestra.getId());	
				if (!resultado.getError()){
					//Recuperamos Dto actualizado
					consultaTestra = (ConsultaTestraDto) resultado.getAttachment(ServicioConsultasTestraImpl.KEY_RESULT);
				}else{
					addActionError("Error a la hora de recuperar la consulta de testra");
				}
			}catch(Throwable e){
				log.error("Error a la hora de activar la consulta de testra con id: " + consultaTestra.getId()  , e);
				addActionError("Error a la hora de activar la consulta de testra con id: " + consultaTestra.getId() );
			}
			
		}else{
			addActionError("Error a la hora de activar la consulta de testra con id: " + consultaTestra.getId());
		}		
		
		return SUCCESS;
	}
	
	public String desactivar(){
		if (consultaTestra!= null && consultaTestra.getId()!=null){
			try{
				servicioConsultasTestra.actualizarActivacion(EstadoVehiculo.Desactivo.getValorEnum(), consultaTestra.getId());
				ResultBean resultado = servicioConsultasTestra.getConsultaTestraDto(consultaTestra.getId());	
				if (!resultado.getError()){
					//Recuperamos Dto actualizado
					consultaTestra = (ConsultaTestraDto) resultado.getAttachment(ServicioConsultasTestraImpl.KEY_RESULT);
				}else{
					addActionError("Error a la hora de recuperar la consulta de testra");
				}
			}catch(Throwable e){
				log.error("Error a la hora de activar la consulta de testra con id: " + consultaTestra.getId()  , e);
				addActionError("Error a la hora de desactivar la consulta de testra con id: " + consultaTestra.getId() );
			}
			
		}else{
			addActionError("Error a la hora de desactivar la consulta de testra con id: " + consultaTestra.getId());
		}		
		
		return SUCCESS;
	}
	
	public ConsultaTestraDto getConsultaTestra() {
		return consultaTestra;
	}

	public void setConsultaTestra(ConsultaTestraDto consultaTestra) {
		this.consultaTestra = consultaTestra;
	}
	
	public Long getIdConsultaTestra() {
		return idConsultaTestra;
	}

	public void setIdConsultaTestra(Long idConsultaTestra) {
		this.idConsultaTestra = idConsultaTestra;
	}

	public boolean isVieneDeResumen() {
		return vieneDeResumen;
	}

	public void setVieneDeResumen(boolean vieneDeResumen) {
		this.vieneDeResumen = vieneDeResumen;
	}
	

}
