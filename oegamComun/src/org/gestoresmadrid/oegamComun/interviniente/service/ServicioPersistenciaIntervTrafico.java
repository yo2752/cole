package org.gestoresmadrid.oegamComun.interviniente.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaDireccionVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;

public interface ServicioPersistenciaIntervTrafico extends Serializable{

	void borrar(IntervinienteTraficoVO intervinienteTraficoVO);

	IntervinienteTraficoVO getIntervinienteTrafico(BigDecimal numExpediente, String tipoInterviniente, String nif, String numColegiado);

	void actualizarIntervinienteConPersona(PersonaVO personaBBDD, IntervinienteTraficoVO intervinienteTraficoVO);

	List<IntervinienteTraficoVO> getListaIntervinientesExpediente(BigDecimal numExpediente);

	void guardarIntervinienteTrafico(IntervinienteTraficoVO intervinienteTrafico, IntervinienteTraficoVO intervinienteTrafBorrar, 
			PersonaVO personaInterv, EvolucionPersonaVO evolucionPersonaVO, PersonaDireccionVO personaDirAnt, PersonaDireccionVO personaDirNue,
			EvolucionPersonaVO evolucionPerDireccion, DireccionVO dirInterviniente, Boolean guardarDir);

	void eliminarInterviniente(IntervinienteTraficoVO intervinienteTrafico);

}
