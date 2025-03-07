package org.gestoresmadrid.oegam2comun.consultaDev.utiles;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.consultaDev.model.enumerados.EstadoCif;
import org.gestoresmadrid.core.consultaDev.model.enumerados.EstadoConsultaDev;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.oegam2comun.consultaDev.model.dto.ConsultaDevDto;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UtilesConsultaDev implements Serializable{
	
	private static final long serialVersionUID = -7752315487626900558L;
	
	private static UtilesConsultaDev utilesConsultaDev;
	
	@Autowired
	ServicioContrato servicioContrato;
	
	public static UtilesConsultaDev getInstance(){
		if(utilesConsultaDev == null){
			utilesConsultaDev = new UtilesConsultaDev();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesConsultaDev);
		}
		return utilesConsultaDev;
	}
	
	public EstadoConsultaDev[] getEstadoConsulta(){
		return EstadoConsultaDev.values();
	}
	
	public EstadoCif[] getEstadoCif(){
		return EstadoCif.values();
	}

	public Boolean mostrarResultadoConsulta(ConsultaDevDto consultaDevDto){
		if(consultaDevDto != null && consultaDevDto.getEstado() != null && !consultaDevDto.getEstado().isEmpty()){
			if(EstadoConsultaDev.Finalizado.getValorEnum().equals(consultaDevDto.getEstado())){
				return true;
			}
		}
		return false;
	}
	
	public Boolean esEstadoConsultable(ConsultaDevDto consultaDevDto){
		if(consultaDevDto != null && consultaDevDto.getEstado() != null && !consultaDevDto.getEstado().isEmpty()){
			if(EstadoConsultaDev.Iniciado.getValorEnum().equals(consultaDevDto.getEstado())){
				return true;
			}
		}
		return false;
	}
	
	public Boolean esEstadoGuardable(ConsultaDevDto consultaDevDto){
		if(consultaDevDto != null){
			if(consultaDevDto.getEstado() == null || consultaDevDto.getEstado().isEmpty()
				|| EstadoConsultaDev.Iniciado.getValorEnum().equals(consultaDevDto.getEstado())
				|| EstadoConsultaDev.Finalizado_Con_Error.getValorEnum().equals(consultaDevDto.getEstado())){
				return true;
			}
		}
		return false;
	}
	
	
	public List<DatoMaestroBean> getComboContratosHabilitados() {
		return servicioContrato.getComboContratosHabilitados();
	}
}
