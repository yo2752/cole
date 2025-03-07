package org.gestoresmadrid.oegamComun.interviniente.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaDireccionVO;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.personas.model.vo.PersonaPK;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunDireccion;
import org.gestoresmadrid.oegamComun.interviniente.service.ServicioComunIntervinienteTrafico;
import org.gestoresmadrid.oegamComun.interviniente.service.ServicioPersistenciaIntervTrafico;
import org.gestoresmadrid.oegamComun.interviniente.view.bean.ResultadoIntevTraficoBean;
import org.gestoresmadrid.oegamComun.persona.service.ServicioComunPersona;
import org.gestoresmadrid.oegamComun.persona.view.bean.ResultadoPersonaBean;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.validaciones.NIFValidator;

@Service
public class ServicioComunIntervinienteTraficoImpl implements ServicioComunIntervinienteTrafico {

	private static final long serialVersionUID = 5795331765101048406L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioComunIntervinienteTraficoImpl.class);

	@Autowired
	ServicioPersistenciaIntervTrafico servicioPersistenciaIntervTrafico; 
	
	@Autowired
	ServicioValIntervinieteTrafico servicioValIntervinieteTrafico; 
	
	@Autowired
	ServicioComunDireccion servicioComunDireccion;
	
	@Autowired
	Conversor conversor;

	@Autowired
	ServicioComunPersona servicioComunPersona;
	
	@Override
	public List<IntervinienteTraficoVO> getListaIntervinientesExpediente(BigDecimal numExpediente) {
		return servicioPersistenciaIntervTrafico.getListaIntervinientesExpediente(numExpediente);
	}

	@Override
	public void eliminarInterviniente(BigDecimal numExpediente, String tipoInterviniente) {
		IntervinienteTraficoVO intervinienteTraficoVO = getIntervinienteTramite(numExpediente, tipoInterviniente);
		if (intervinienteTraficoVO != null) {
			servicioPersistenciaIntervTrafico.borrar(intervinienteTraficoVO);
		}
	}

	@Override
	public IntervinienteTraficoVO getIntervinienteTramite(BigDecimal numExpediente, String tipoInterviniente) {
		return servicioPersistenciaIntervTrafico.getIntervinienteTrafico(numExpediente, tipoInterviniente, null, null);
	}

	@Override
	@Transactional
	public void actualizarIntervinienteInteve(IntervinienteTraficoVO intervinienteTraficoVO) {
		PersonaVO personaBBDD = servicioComunPersona.getPersona(intervinienteTraficoVO.getId().getNif(), intervinienteTraficoVO.getId().getNumColegiado());
		if (personaBBDD != null) {
			personaBBDD.setApellido1RazonSocial(intervinienteTraficoVO.getPersona().getApellido1RazonSocial());
			personaBBDD.setApellido2(intervinienteTraficoVO.getPersona().getApellido2());
			personaBBDD.setNombre(intervinienteTraficoVO.getPersona().getNombre());
			personaBBDD = rellenarTipoPersona(personaBBDD);
		} else {
			personaBBDD = new PersonaVO();
			PersonaPK id = new PersonaPK();
			id.setNif(intervinienteTraficoVO.getId().getNif());
			id.setNumColegiado(intervinienteTraficoVO.getId().getNumColegiado());
			personaBBDD.setId(id);
			personaBBDD.setApellido1RazonSocial(intervinienteTraficoVO.getPersona().getApellido1RazonSocial());
			personaBBDD.setApellido2(intervinienteTraficoVO.getPersona().getApellido2());
			personaBBDD.setNombre(intervinienteTraficoVO.getPersona().getNombre());
			personaBBDD.setEstado(BigDecimal.ONE.longValue());
			personaBBDD = rellenarTipoPersona(personaBBDD);
		}
		
		servicioPersistenciaIntervTrafico.actualizarIntervinienteConPersona(personaBBDD, intervinienteTraficoVO);
	}
	
	@Override
	public void eliminarIntervinienteVO(IntervinienteTraficoVO intervinienteTraficoBBDD) {
		servicioPersistenciaIntervTrafico.eliminarInterviniente(intervinienteTraficoBBDD);
	}
	
	@Override
	public ResultadoBean guardarIntervinienteTrafico(IntervinienteTraficoVO interviniente, Long idUsuario, String tipoTramite, String conversion, String conversionDireccion) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		EvolucionPersonaVO evolucionPersonaVO = null;
		EvolucionPersonaVO evolucionPerDireccion = null;
		PersonaDireccionVO personaDirAnt = null;
		PersonaDireccionVO personaDirNue = null;
		PersonaVO personaInterv = null;
		DireccionVO dirInterviniente = null;
		Boolean guardarDir = Boolean.FALSE;
		try {
			ResultadoPersonaBean resultPersona = servicioComunPersona.tratarGuardadoPersona(interviniente.getPersona(), 
					interviniente.getId().getNumExpediente(), tipoTramite, idUsuario, conversion);
			if(!resultPersona.getError()){
				interviniente.setPersona(resultPersona.getPersona());
				if(resultPersona.getEvolucionPersona() != null){
					personaInterv = resultPersona.getPersona();
					evolucionPersonaVO = resultPersona.getEvolucionPersona();
				}	
				if(interviniente.getDireccion() != null){
					ResultadoPersonaBean resultPersDir = servicioComunDireccion.tratarPersonaDireccion(interviniente.getDireccion(), 
							interviniente.getPersona().getId().getNif(), interviniente.getPersona().getId().getNumColegiado(),
							tipoTramite, interviniente.getId().getNumExpediente(), idUsuario, conversionDireccion);
					if(!resultPersDir.getError()){
						interviniente.setDireccion(resultPersDir.getDireccion());
						interviniente.setIdDireccion(resultPersDir.getDireccion().getIdDireccion());
						dirInterviniente = resultPersDir.getDireccion();
						guardarDir = resultPersDir.getGuardarDir();
						if(resultPersDir.getPersonaDirPrincipalAnt() != null){
							personaDirAnt = resultPersDir.getPersonaDirPrincipalAnt();
						}
						if(resultPersDir.getPersonaDirPrincipalNue() != null){
							personaDirNue = resultPersDir.getPersonaDirPrincipalNue();
						}
						if(resultPersDir.getEvolucionPersona() != null){
							evolucionPerDireccion = resultPersDir.getEvolucionPersona();
						}
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(resultPersDir.getMensaje());
					}
				} else {
					interviniente.setIdDireccion(null);
				}
				ResultadoIntevTraficoBean resultIntr = tratarGuardadoIntervTrafico(interviniente);
				if(!resultIntr.getError()){
					servicioPersistenciaIntervTrafico.guardarIntervinienteTrafico(resultIntr.getIntervinienteTrafico(), 
							resultIntr.getIntervinienteTrafBorrar(),personaInterv, evolucionPersonaVO, personaDirAnt, personaDirNue, evolucionPerDireccion, dirInterviniente, guardarDir);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje(resultIntr.getMensaje());
				}
			} else {
				resultado.setMensaje(resultPersona.getMensaje());
				resultado.setError(Boolean.TRUE);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el " + TipoInterviniente.convertirTexto(interviniente.getId().getTipoInterviniente())
				+" y del expediente: " + interviniente.getId().getNumExpediente() + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar el " + TipoInterviniente.convertirTexto(interviniente.getId().getTipoInterviniente()));
		}
		return resultado;
	}

	private ResultadoIntevTraficoBean tratarGuardadoIntervTrafico(IntervinienteTraficoVO interviniente) {
		ResultadoIntevTraficoBean resultado = new ResultadoIntevTraficoBean(Boolean.FALSE);
		try {
			if (interviniente.getId().getNumExpediente() != null) {
				IntervinienteTraficoVO intervinienteTraficoBBDD = 
						servicioPersistenciaIntervTrafico.getIntervinienteTrafico(interviniente.getId().getNumExpediente(), interviniente.getId().getTipoInterviniente(), 
								null, interviniente.getId().getNumColegiado());
				if(intervinienteTraficoBBDD != null){
					if (interviniente.getId().getNif().equals(intervinienteTraficoBBDD.getId().getNif())) {
						conversor.transform(interviniente, intervinienteTraficoBBDD);
						servicioValIntervinieteTrafico.modificarDatosCombos(intervinienteTraficoBBDD);
						/*ResultadoIntevTraficoBean resultadoValInterv = servicioValIntervinieteTrafico.esModificada(interviniente, intervinienteTraficoBBDD);
						if(!resultadoValInterv.getError()){
							if(resultadoValInterv.getEsModificada()){
								servicioValIntervinieteTrafico.modificarDatosCombos(intervinienteTraficoBBDD);
								resultado.setIntervinienteTrafico(intervinienteTraficoBBDD);
							}
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje(resultadoValInterv.getMensaje());
						}*/
						resultado.setIntervinienteTrafico(intervinienteTraficoBBDD);
					} else {
						resultado.setIntervinienteTrafBorrar(intervinienteTraficoBBDD);
						resultado.setIntervinienteTrafico(interviniente);
					}
				} else {
					servicioValIntervinieteTrafico.modificarDatosCombos(interviniente);
					resultado.setIntervinienteTrafico(interviniente);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error guardado el interviniente. No tiene expediente asociado");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de tratar el guardado del interviniente con NIF: " +interviniente.getId().getNif() 
					+ " para el expediente: " + interviniente.getId().getNumExpediente() + ", error: ");
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de tratar el guardado del interviniente con NIF: " +interviniente.getId().getNif());
		}
		return resultado;
	}

	private PersonaVO rellenarTipoPersona(PersonaVO personaBBDD) {
		if (StringUtils.isBlank(personaBBDD.getTipoPersona())) {
			int tipo = NIFValidator.isValidDniNieCif(personaBBDD.getId().getNif().toUpperCase());
			if (tipo == NIFValidator.FISICA) {
				personaBBDD.setTipoPersona(TipoPersona.Fisica.getValorEnum());
			} else {
				personaBBDD.setTipoPersona(TipoPersona.Juridica.getValorEnum());
			}
		}
		return personaBBDD;
	}
}
