package org.gestoresmadrid.oegamComun.semaforo.service.impl;

import org.gestoresmadrid.oegamComun.semaforo.service.ServicioComunSemaforos;
import org.gestoresmadrid.oegamComun.semaforo.service.ServicioComunValidacionSemaforos;
import org.gestoresmadrid.oegamComun.semaforo.view.bean.ResultadoSemaforosBean;
import org.gestoresmadrid.oegamComun.semaforo.view.dto.SemaforoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioComunValidacionSemaforosImpl implements ServicioComunValidacionSemaforos {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6067947083890373778L;
	
	@Autowired
	ServicioComunSemaforos servicioSemaforos;

	@Override
	public ResultadoSemaforosBean validacionGuardarSemaforo(SemaforoDto semaforo, SemaforoDto semaforoExist) {
		ResultadoSemaforosBean resultado = new ResultadoSemaforosBean(false);
		
		if (semaforo.getProceso() == null || "".equals(semaforo.getProceso())) {
			// Proceso, obligatorio
			resultado.setError(true);
			resultado.setMensaje("Es obligatorio seleccionar un proceso");
		} else if (semaforo.getEstado() == null) {
			// Estado, obligatorio
			resultado.setError(true);
			resultado.setMensaje("Es obligatorio seleccionar un estado");
		} else {
			if (semaforoExist != null && semaforoExist.getIdSemaforo() != null) {
				if (semaforoExist.getEstado() == 0) {
					resultado.setError(true);
					resultado.setMensaje("El semáforo existe y está inhabilitado, vaya a la Gestión para habilitarlo");
				} else {
					resultado.setError(true);
					resultado.setMensaje("El semáforo existe");
				}
			}
			
		}
		
		return resultado;
	}

}
