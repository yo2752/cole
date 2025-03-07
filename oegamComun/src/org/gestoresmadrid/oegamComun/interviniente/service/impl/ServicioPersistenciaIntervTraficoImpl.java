package org.gestoresmadrid.oegamComun.interviniente.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.dao.IntervinienteTraficoDao;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaDireccionVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunDireccion;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunPersonaDireccion;
import org.gestoresmadrid.oegamComun.interviniente.service.ServicioPersistenciaIntervTrafico;
import org.gestoresmadrid.oegamComun.persona.service.ServicioComunPersona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioPersistenciaIntervTraficoImpl implements ServicioPersistenciaIntervTrafico{

	private static final long serialVersionUID = 5726347935773949611L;

	@Autowired
	IntervinienteTraficoDao intervinienteTraficoDao;
	
	@Autowired
	ServicioComunPersona servicioComunPersona;
	
	@Autowired
	ServicioComunDireccion servicioComunDireccion;
	
	@Autowired
	ServicioComunPersonaDireccion servicioComunPersonaDireccion;
	
	@Override
	@Transactional(readOnly=true)
	public List<IntervinienteTraficoVO> getListaIntervinientesExpediente(BigDecimal numExpediente) {
		return intervinienteTraficoDao.getListaIntervinientesTramite(numExpediente);
	}
	
	@Override
	@Transactional
	public void borrar(IntervinienteTraficoVO intervinienteTraficoVO) {
		intervinienteTraficoDao.borrar(intervinienteTraficoVO);
	}
	
	@Override
	@Transactional(readOnly=true)
	public IntervinienteTraficoVO getIntervinienteTrafico(BigDecimal numExpediente, String tipoInterviniente, String nif, String numColegiado) {
		return intervinienteTraficoDao.getIntervinienteTrafico(numExpediente, tipoInterviniente, nif, numColegiado);
	}
	
	@Override
	@Transactional
	public void actualizarIntervinienteConPersona(PersonaVO personaBBDD, IntervinienteTraficoVO intervinienteTraficoVO) {
		servicioComunPersona.guardarPersonaSinEvo(personaBBDD);
		intervinienteTraficoDao.guardarOActualizar(intervinienteTraficoVO);
	}
	
	@Override
	@Transactional
	public void guardarIntervinienteTrafico(IntervinienteTraficoVO intervinienteTrafico,
			IntervinienteTraficoVO intervinienteTrafBorrar, PersonaVO personaInterv,
			EvolucionPersonaVO evolucionPersonaVO, PersonaDireccionVO personaDirAnt, PersonaDireccionVO personaDirNue,
			EvolucionPersonaVO evolucionPerDireccion, DireccionVO dirInteviniente, Boolean guardarDir) {
		if(intervinienteTrafBorrar != null){
			intervinienteTraficoDao.borrar(intervinienteTrafBorrar);
		}
		if(personaInterv != null){
			evolucionPersonaVO.getId().setFechaHora(new Date());
			servicioComunPersona.guardarPersonaConEvolucion(personaInterv, evolucionPersonaVO);
		}
		if(dirInteviniente != null && dirInteviniente.getIdDireccion() == null || guardarDir){
			intervinienteTrafico.setIdDireccion(servicioComunDireccion.guardarActualizarDireccion(dirInteviniente).getIdDireccion());
			if(personaDirNue != null){
				personaDirNue.getId().setIdDireccion(intervinienteTrafico.getIdDireccion());
			}
		}
		if(personaDirAnt != null){
			servicioComunPersonaDireccion.guardarActualizar(personaDirAnt);
		}
		if(personaDirNue != null){
			evolucionPerDireccion.getId().setFechaHora(new Date());
			servicioComunPersonaDireccion.guardarActualizarConEvo(personaDirNue, evolucionPerDireccion);
		}
		intervinienteTraficoDao.guardarOActualizar(intervinienteTrafico);
	}
	
	@Override
	@Transactional
	public void eliminarInterviniente(IntervinienteTraficoVO intervinienteTrafico) {
		intervinienteTraficoDao.borrar(intervinienteTrafico);
	}
}
