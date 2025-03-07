package org.gestoresmadrid.oegamImportacion.persona.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.core.personas.model.dao.PersonaDao;
import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaPK;
import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.oegamConversiones.conversion.Conversion;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoBean;
import org.gestoresmadrid.oegamImportacion.persona.service.ServicioEvolucionPersonaImportacion;
import org.gestoresmadrid.oegamImportacion.persona.service.ServicioPersonaImportacion;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioPersonaImportacionImpl implements ServicioPersonaImportacion {

	private static final long serialVersionUID = 3217780953204618111L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPersonaImportacionImpl.class);

	@Autowired
	PersonaDao personaDao;

	@Autowired
	ServicioEvolucionPersonaImportacion servicioEvolucionPersona;

	@Autowired
	Conversion conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Override
	@Transactional
	public ResultadoBean guardarActualizarImportacion(PersonaVO persona, BigDecimal numExpediente, String tipoTramite, Long idUsuario, String conversion) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			if (persona.getApellido1RazonSocial() == null || persona.getApellido1RazonSocial().isEmpty()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El interviniente no tiene Apellido o Razon Social.");
			} else {
				PersonaVO personaBBDD = personaDao.getPersona(persona.getId().getNif(), persona.getId().getNumColegiado());
				// Modificación de persona
				if (personaBBDD != null) {
					PersonaVO personaCompleta = (PersonaVO) personaBBDD.clone();
					if (conversion != null) {
						conversor.transform(persona, personaCompleta, conversion);
					} else {
						conversor.transform(persona, personaCompleta);
					}
					ResultadoBean resulValPersona = esModificadaPersona(personaCompleta, personaBBDD, numExpediente, tipoTramite);
					if (!resulValPersona.getError()) {
						if (resulValPersona.getActualizar()) {
							personaDao.evict(personaBBDD);
							personaDao.guardarOActualizar(personaCompleta);
							servicioEvolucionPersona.guardarEvolucionVO(resulValPersona.getEvolucionPersona(), idUsuario);
						} else {
							personaDao.evict(personaCompleta);
						}
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(resulValPersona.getMensaje());
					}
					resultado.setPersona(personaCompleta);
				} else {
					personaDao.guardar(persona);
					EvolucionPersonaVO evolucionPersona = new EvolucionPersonaVO();
					EvolucionPersonaPK id = new EvolucionPersonaPK();
					id.setNif(persona.getId().getNif());
					id.setNumColegiado(persona.getId().getNumColegiado());
					id.setFechaHora(new Date());
					evolucionPersona.setId(id);
					evolucionPersona.setNumExpediente(numExpediente);
					evolucionPersona.setTipoTramite(tipoTramite);
					evolucionPersona.setTipoActuacion(TipoActualizacion.CRE.getValorEnum());
					servicioEvolucionPersona.guardarEvolucionVO(evolucionPersona, idUsuario);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la persona, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar la persona.");
		}
		return resultado;
	}

	private ResultadoBean esModificadaPersona(PersonaVO persona, PersonaVO personaBBDD, BigDecimal numExpediente, String tipoTramite) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			EvolucionPersonaVO evolucionPersona = new EvolucionPersonaVO();
			evolucionPersona.setOtros("");
			resultado.setActualizar(Boolean.FALSE);
			if (!utiles.sonIgualesString(persona.getApellido1RazonSocial() != null ? persona.getApellido1RazonSocial().toUpperCase() : null, personaBBDD.getApellido1RazonSocial() != null ? personaBBDD
					.getApellido1RazonSocial().toUpperCase() : null)) {
				evolucionPersona.setApellido1Ant(personaBBDD.getApellido1RazonSocial() != null ? personaBBDD.getApellido1RazonSocial().toUpperCase() : null);
				evolucionPersona.setApellido1Nue(persona.getApellido1RazonSocial() != null ? persona.getApellido1RazonSocial().toUpperCase() : null);
				resultado.setActualizar(Boolean.TRUE);
			}
			if (!utiles.sonIgualesString(persona.getApellido2() != null ? persona.getApellido2().toUpperCase() : null, personaBBDD.getApellido2() != null ? personaBBDD.getApellido2().toUpperCase()
					: null)) {
				evolucionPersona.setApellido2Ant(personaBBDD.getApellido2() != null ? personaBBDD.getApellido2().toUpperCase() : null);
				resultado.setActualizar(Boolean.TRUE);
				evolucionPersona.setApellido2Nue(persona.getApellido2().toUpperCase());
			}
			if (!utiles.sonIgualesString(persona.getNombre() != null ? persona.getNombre().toUpperCase() : null, personaBBDD.getNombre() != null ? personaBBDD.getNombre().toUpperCase() : null)) {
				evolucionPersona.setNombreAnt(personaBBDD.getNombre() != null ? personaBBDD.getNombre().toUpperCase() : null);
				resultado.setActualizar(Boolean.TRUE);
				evolucionPersona.setNombreNue(persona.getNombre() != null ? persona.getNombre().toUpperCase() : null);
			}
			if (utilesFecha.compararFechaDate(persona.getFechaNacimiento(), personaBBDD.getFechaNacimiento()) != 0) {
				evolucionPersona.setFechaNacimientoAnt(personaBBDD.getFechaNacimiento());
				resultado.setActualizar(Boolean.TRUE);
				evolucionPersona.setFechaNacimientoNue(persona.getFechaNacimiento());
			}
			if (!utiles.sonIgualesString(persona.getAnagrama() != null ? persona.getAnagrama().toUpperCase() : null, personaBBDD.getAnagrama() != null ? personaBBDD.getAnagrama().toUpperCase()
					: null)) {
				resultado.setActualizar(Boolean.TRUE);
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Anagrama,");
			}
			if (!utiles.sonIgualesObjetos(persona.getEstado(), personaBBDD.getEstado())) {
				resultado.setActualizar(Boolean.TRUE);
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Estado,");
			}
			if (!utiles.sonIgualesCombo(persona.getTipoPersona(), personaBBDD.getTipoPersona())) {
				resultado.setActualizar(Boolean.TRUE);
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Tipo Persona,");
			}
			if (!utiles.sonIgualesString(persona.getTelefonos(), personaBBDD.getTelefonos())) {
				resultado.setActualizar(Boolean.TRUE);
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Telefonos,");
			}
			if (!utiles.sonIgualesCombo(persona.getEstadoCivil(), personaBBDD.getEstadoCivil())) {
				resultado.setActualizar(Boolean.TRUE);
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "EstadoCivil,");
			}
			if (!utiles.sonIgualesCombo(persona.getSexo(), personaBBDD.getSexo())) {
				resultado.setActualizar(Boolean.TRUE);
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Sexo,");
			}
			if (!utiles.sonIgualesObjetos(persona.getSeccion(), personaBBDD.getSeccion())) {
				resultado.setActualizar(Boolean.TRUE);
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Seccion,");
			}
			if (!utiles.sonIgualesObjetos(persona.getHoja(), personaBBDD.getHoja())) {
				resultado.setActualizar(Boolean.TRUE);
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Hoja,");
			}
			if (!utiles.sonIgualesString(persona.getHojaBis(), personaBBDD.getHojaBis())) {
				resultado.setActualizar(Boolean.TRUE);
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "HojaBis,");
			}
			if (!utiles.sonIgualesObjetos(persona.getIus(), personaBBDD.getIus())) {
				resultado.setActualizar(Boolean.TRUE);
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Ius,");
			}
			if (!utiles.sonIgualesString(persona.getCorreoElectronico() != null ? persona.getCorreoElectronico().toUpperCase() : null, personaBBDD.getCorreoElectronico() != null ? personaBBDD
					.getCorreoElectronico().toUpperCase() : null)) {
				resultado.setActualizar(Boolean.TRUE);
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Correo Electronico,");
			}
			if (!utiles.sonIgualesString(persona.getIban(), personaBBDD.getIban())) {
				resultado.setActualizar(Boolean.TRUE);
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Iban,");
			}
			if (!utiles.sonIgualesString(persona.getNcorpme(), personaBBDD.getNcorpme())) {
				resultado.setActualizar(Boolean.TRUE);
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Ncorpme,");
			}
			if (!utiles.sonIgualesObjetos(persona.getPirpf(), personaBBDD.getPirpf())) {
				resultado.setActualizar(Boolean.TRUE);
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Pirpf,");
			}
			if (!utiles.sonIgualesString(persona.getFa(), personaBBDD.getFa())) {
				resultado.setActualizar(Boolean.TRUE);
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Fa,");
			}
			if (utilesFecha.compararFechaDate(persona.getFechaCaducidadNIF(), personaBBDD.getFechaCaducidadNIF()) != 0) {
				resultado.setActualizar(Boolean.TRUE);
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Fecha Caducidad Nif,");
			}
			if (utilesFecha.compararFechaDate(persona.getFechaCaducidadAlternativo(), personaBBDD.getFechaCaducidadAlternativo()) != 0) {
				resultado.setActualizar(Boolean.TRUE);
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Fecha Caducidad Alternativo,");
			}
			if (!utiles.sonIgualesCombo(persona.getTipoDocumentoAlternativo(), personaBBDD.getTipoDocumentoAlternativo())) {
				resultado.setActualizar(Boolean.TRUE);
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Tipo Documento Alternativo,");
			}
			if (!utiles.sonIgualesCheckBox(persona.getIndefinido(), personaBBDD.getIndefinido())) {
				resultado.setActualizar(Boolean.TRUE);
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Indefinido,");
			}
			if (!utiles.sonIgualesString(persona.getCodigoMandato(), personaBBDD.getCodigoMandato())) {
				resultado.setActualizar(Boolean.TRUE);
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Código Mandato,");
			}
			if (!utiles.sonIgualesString(persona.getSubtipo(), personaBBDD.getSubtipo())) {
				resultado.setActualizar(Boolean.TRUE);
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "SubTipo,");
			}
			if (!utiles.sonIgualesString(persona.getUsuarioCorpme(), personaBBDD.getUsuarioCorpme())) {
				resultado.setActualizar(Boolean.TRUE);
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Usuario Corpme,");
			}
			if (!utiles.sonIgualesString(persona.getPasswordCorpme(), personaBBDD.getPasswordCorpme())) {
				resultado.setActualizar(Boolean.TRUE);
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Password Corpme,");
			}
			if (resultado.getActualizar()) {
				EvolucionPersonaPK id = new EvolucionPersonaPK();
				id.setNif(persona.getId().getNif());
				id.setNumColegiado(persona.getId().getNumColegiado());
				id.setFechaHora(new Date());
				evolucionPersona.setId(id);
				evolucionPersona.setNumExpediente(numExpediente);
				evolucionPersona.setTipoTramite(tipoTramite);
				evolucionPersona.setTipoActuacion(TipoActualizacion.MOD.getNombreEnum());
				resultado.setEvolucionPersona(evolucionPersona);
			}
		} catch (Exception e) {
			log.error("Error al comparar la persona con la persona de la bbdd, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al comprobar los datos de la persona.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public PersonaVO getPersonaVO(String nif, String numColegiado) {
		try {
			return personaDao.getPersona(nif, numColegiado);
		} catch (Exception e) {
			log.error("Error el obtener la persona");
		}
		return null;
	}

	@Override
	@Transactional
	public IntervinienteTraficoVO obtenerColegiadoCompleto(IntervinienteTraficoVO presentador, ContratoVO contrato) {
		try {
			PersonaVO persona = getPersonaVO(contrato.getColegiado().getUsuario().getNif(), presentador.getId().getNumColegiado());
			if (persona != null) {
				presentador.setPersona(persona);
				persona.setCorreoElectronico(contrato.getCorreoElectronico());
				presentador.setDireccion(getDireccionContrato(contrato));
			}
		} catch (Exception e) {
			log.error("Error al obtenerColegiadoCompleto", e);
		}
		return presentador;
	}

	private DireccionVO getDireccionContrato(ContratoVO contrato) {
		DireccionVO direccion = new DireccionVO();
		direccion.setIdMunicipio(contrato.getIdMunicipio());
		direccion.setIdProvincia(contrato.getIdProvincia());
		direccion.setIdTipoVia(contrato.getIdTipoVia());
		direccion.setNombreVia(contrato.getVia());
		direccion.setNumero(contrato.getNumero());
		direccion.setCodPostal(contrato.getCodPostal());
		direccion.setEscalera(contrato.getEscalera());
		direccion.setPlanta(contrato.getPiso());
		direccion.setPuerta(contrato.getPuerta());

		return direccion;
	}
}
