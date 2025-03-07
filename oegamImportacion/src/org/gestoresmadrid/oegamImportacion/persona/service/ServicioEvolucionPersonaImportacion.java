package org.gestoresmadrid.oegamImportacion.persona.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaVO;

public interface ServicioEvolucionPersonaImportacion extends Serializable {

	public static String EVOLUCION_PERSONA = "EVOLUCIONPERSONA";

	List<EvolucionPersonaVO> getEvolucionPersona(String nif, String numColegiado, ArrayList<String> tipoActualizacion);

	void guardarEvolucionVO(EvolucionPersonaVO evolucionPersona, Long idUsuario);

	void guardarEvolucionPersonaDireccion(String nif, BigDecimal numExpediente, String tipoTramite, String numColegiado, Long idUsuario);
}
