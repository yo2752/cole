package org.gestoresmadrid.oegam2comun.personas.model.service;

import java.io.Serializable;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaDireccionVO;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDireccionDto;

import escrituras.beans.ResultBean;

public interface ServicioPersonaDireccion extends Serializable {

	public static final String PERSONADIRECCION = "PERSONADIRECCION";

	ResultBean guardarActualizar(PersonaDireccionDto persona);

	ResultBean guardarActualizar(PersonaDireccionVO persona);

	ResultBean buscarPersonaDireccionDto(String numColegiado, String nif);

	ResultBean buscarPersonaDireccionVO(String numColegiado, String nif);

	PersonaDireccionVO buscarDireccionExistente(DireccionVO direccionVO, String numColegiado, String nif, String tipoTramite);

	ResultBean eliminarFusionarDireccion(String numColegiado, String nif, Long idDireccionPrincipal, Long idDireccionBorrar, boolean existenIntervinientes, Short estadoPersonaDireccion);

	ResultBean asignarPrincipal(String numColegiado, String nif, Long idDireccionPrincipal);
}
