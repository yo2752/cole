package org.gestoresmadrid.oegamComun.direccion.service;

import java.io.Serializable;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaDireccionVO;
import org.gestoresmadrid.oegamComun.persona.view.bean.ResultadoPersonaBean;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;

public interface ServicioComunPersonaDireccion extends Serializable {

	ResultadoPersonaBean guardarActualizar(PersonaDireccionVO persona);

	ResultadoPersonaBean buscarPersonaDireccionVO(String numColegiado, String nif);

	PersonaDireccionVO buscarDireccionExistente(DireccionVO direccionVO, String numColegiado, String nif, String tipoTramite);

	ResultadoBean asignarPrincipal(String numColegiado, String nif, Long idDireccionPrincipal);

	ResultadoPersonaBean tratarAsignarPrincipal(String numColegiado, String nif, Long idDireccion);

	void guardarActualizarConEvo(PersonaDireccionVO personaDirNue, EvolucionPersonaVO evolucionPerDireccion);

}
