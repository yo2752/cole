package org.gestoresmadrid.oegam2.semaforo.controller.action;

import java.util.List;

import org.gestoresmadrid.core.evolucionSemaforo.model.enumerados.OperacionEvolSemaforo;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.semaforo.service.ServicioComunSemaforos;
import org.gestoresmadrid.oegamComun.semaforo.view.bean.ResultadoSemaforosBean;
import org.gestoresmadrid.oegamComun.semaforo.view.dto.SemaforoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import oegam.constantes.ConstantesSession;

public class CreacionSemaforoAction extends ActionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -190149795360727299L;
	
	private SemaforoDto semaforo;
	
	@Autowired
	ServicioComunSemaforos servicioSemaforos;
	
	@Autowired
	GestorPropiedades gestorPropiedades;
	
	@Autowired
	private UtilesColegiado utilesColegiado;
	
	public String inicio() {
		return "altaSemaforo";
	}
	
	public String ejecutar() {
		try {
			semaforo.setNodo(gestorPropiedades.valorPropertie("nombreHostProceso"));
			SemaforoDto semaforoExist = servicioSemaforos.obtenerSemaforo(semaforo);
			ResultadoSemaforosBean resultado = servicioSemaforos.guardarSemaforo(semaforo, 
					semaforoExist, 
					OperacionEvolSemaforo.Alta, 
					utilesColegiado.getIdUsuarioSession());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				List<SemaforoDto> listaSemaforosSesion = servicioSemaforos.obtenerListaSemaforosSes(semaforo.getNodo());
				getSession().put(ConstantesSession.LISTA_SEMAFOROS_NODO, listaSemaforosSesion);
				addActionMessage("Semáforo creado correctamente");
			}
		} catch (Exception e) {
			addActionError(e.getMessage());
		}
		
		return "altaSemaforo";
	}

	public SemaforoDto getSemaforo() {
		return semaforo;
	}

	public void setSemaforo(SemaforoDto semaforo) {
		this.semaforo = semaforo;
	}

}
