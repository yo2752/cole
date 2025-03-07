package org.gestoresmadrid.oegam2.circulares.controller.action;

import org.gestoresmadrid.oegam2comun.circular.model.service.ServicioCircular;
import org.gestoresmadrid.oegam2comun.circular.view.bean.ResultadoCircularBean;
import org.gestoresmadrid.oegam2comun.circular.view.dto.CircularDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class AltaCircularAction extends ActionBase {

	private static final long serialVersionUID = 4714971291253917883L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(AltaCircularAction.class);

	private static final String ALTA_CIRCULAR = "altaCircularOegam";
	private CircularDto circularDto;

	@Autowired
	ServicioCircular servicioCircular;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String inicio() {

		return ALTA_CIRCULAR;
	}

	public String guardar() {
		try {
			ResultadoCircularBean resultado = servicioCircular.guardarCircular(circularDto,
					utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (resultado.getError()) {
				if(resultado.getListaMensajes()!=null){
					
						addActionError(resultado.getMensaje());
				}
				
			} else {
				addActionMessage(
						"El número de circular " + circularDto.getNumCircular() + " se ha guardado correctamente.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la circular, error: ", e);
			addActionError("Ha sucedido un error a la hora de guardar la circular.");
		}
		return ALTA_CIRCULAR;
	}

	public CircularDto getCircularDto() {
		return circularDto;
	}

	public void setCircularDto(CircularDto circularDto) {
		this.circularDto = circularDto;
	}

	public ServicioCircular getServicioCircular() {
		return servicioCircular;
	}

	public void setServicioCircular(ServicioCircular servicioCircular) {
		this.servicioCircular = servicioCircular;
	}

}
