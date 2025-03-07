package org.gestoresmadrid.oegam2.utiles;

import java.util.List;

import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.trafico.eeff.model.enumerados.EstadoConsultaEEFF;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.ConsultaEEFFDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UtilesVistaEEFF {

	//private static ILoggerOegam log = LoggerOegam.getLogger(UtilesVistaEEFF.class);

	private static UtilesVistaEEFF utilesVistaEEFF;

	@Autowired
	ServicioContrato servicioContrato;

	public static UtilesVistaEEFF getInstance() {
		if (utilesVistaEEFF == null) {
			utilesVistaEEFF = new UtilesVistaEEFF();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaEEFF);
		}
		return utilesVistaEEFF;
	}

	public Boolean esRealizadaConsulta(ConsultaEEFFDto consultaEEFF){
		if (consultaEEFF != null && consultaEEFF.getNumExpediente() != null
				&& EstadoConsultaEEFF.Finalizado.getValorEnum().equals(consultaEEFF.getEstado())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean esEstadoGuardable(ConsultaEEFFDto consultaEEFF){
		if (consultaEEFF != null) {
			if (consultaEEFF.getNumExpediente() == null) {
				return Boolean.TRUE;
			} else{
				if(EstadoConsultaEEFF.Pdte_Respuesta.getValorEnum().equals(consultaEEFF.getEstado())){
					return Boolean.FALSE;
				} else {
					if (EstadoConsultaEEFF.Finalizado.getValorEnum().equals(consultaEEFF.getEstado())) {
						if ("ERROR".equals(consultaEEFF.getRespuesta())) {
							return Boolean.TRUE;
						}
					} else {
						return Boolean.TRUE;
					}
				}
			}
		}
		return Boolean.FALSE;
	}

	public Boolean esEstadoConsultable(ConsultaEEFFDto consultaEEFF) {
		if (consultaEEFF != null && consultaEEFF.getNumExpediente() != null 
				&& EstadoConsultaEEFF.Iniciado.getValorEnum().equals(consultaEEFF.getEstado())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public List<DatoMaestroBean> getComboContratosHabilitados() {
		return servicioContrato.getComboContratosHabilitados();
	}

	public EstadoConsultaEEFF[] getComboEstados(){
		return EstadoConsultaEEFF.values();
	}
}