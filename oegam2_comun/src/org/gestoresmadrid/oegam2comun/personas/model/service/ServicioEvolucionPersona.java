package org.gestoresmadrid.oegam2comun.personas.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaVO;
import org.gestoresmadrid.oegam2comun.personas.view.dto.EvolucionPersonaDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

import escrituras.beans.ResultBean;

public interface ServicioEvolucionPersona extends Serializable {

	public static String EVOLUCION_PERSONA = "EVOLUCIONPERSONA";

	ResultBean guardarEvolucion(EvolucionPersonaDto evolucion);

	List<EvolucionPersonaVO> getEvolucionPersona(String nif, String numColegiado, ArrayList<String> tipoActualizacion);

	void guardarEvolucionPersonaDireccion(String nif, BigDecimal numExpediente, String tipoTramite, String numColegiado, UsuarioDto usuario, boolean direccionNueva);
}
