package org.gestoresmadrid.oegamComun.persona.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.core.personas.model.dao.PersonaDao;
import org.gestoresmadrid.core.personas.model.enumerados.SexoPersona;
import org.gestoresmadrid.core.personas.model.enumerados.SubtipoPersona;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaPK;
import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.oegamComun.contrato.service.ServicioComunContrato;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.persona.service.ServicioComunEvolucionPersona;
import org.gestoresmadrid.oegamComun.persona.service.ServicioComunPersona;
import org.gestoresmadrid.oegamComun.persona.view.bean.ResultadoPersonaBean;
import org.gestoresmadrid.oegamComun.persona.view.bean.ResultadoValPersonaBean;
import org.gestoresmadrid.oegamComun.utiles.Anagrama;
import org.gestoresmadrid.oegamComun.utiles.UtilidadesNIFValidator;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioComunPersonaImpl implements ServicioComunPersona {

	private static final long serialVersionUID = -3759877607608188593L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioComunPersonaImpl.class);

	@Autowired
	PersonaDao personaDao;

	@Autowired
	Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	ServicioComunEvolucionPersona servicioEvolucionPersona;

	@Autowired
	ServicioComunContrato servicioComunContrato;

	@Autowired
	UtilidadesNIFValidator utilidadesNIFValidator;

	@Autowired
	Utiles utiles;

	@Autowired
	ServicioValidacionPersona servicioValidacionPersona;

	@Override
	@Transactional
	public void guardarPersonaConEvolucion(PersonaVO persona, EvolucionPersonaVO evolucion) {
		personaDao.guardarOActualizar(persona);
		servicioEvolucionPersona.guardarEvolucionPersona(evolucion);
	}

	@Override
	@Transactional
	public void guardarPersonaSinEvo(PersonaVO persona) {
		personaDao.guardarOActualizar(persona);
	}

	@Override
	@Transactional(readOnly = true)
	public PersonaVO getPersona(String nif, String numColegiado) {
		return personaDao.getPersona(nif, numColegiado);
	}

	@Override
	@Transactional
	public PersonaVO obtenerColegiadoCompleto(String numColegiado, String nif) {
		PersonaVO persona = null;
		try {
			persona = personaDao.getPersona(nif, numColegiado);
		} catch (Exception e) {
			log.error("Error al obtenerColegiadoCompleto", e);
		}
		return persona;
	}

	@Override
	@Transactional
	public PersonaVO obtenerColegiadoCompletoConDireccion(String numColegiado, String nif, Long idContrato) {
		PersonaVO persona = null;
		try {
			persona = personaDao.getPersona(nif, numColegiado);
			if (persona != null) {
				ContratoVO contrato = servicioComunContrato.getContrato(idContrato);
				if (contrato != null) {
					persona.setCorreoElectronico(contrato.getCorreoElectronico());
					persona.setDireccion(getDireccionContrato(contrato));
				}
			}
		} catch (Exception e) {
			log.error("Error al obtenerColegiadoCompleto", e);
		}
		return persona;
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

	@Override
	@Transactional(readOnly = true)
	public ResultadoPersonaBean tratarGuardadoPersona(PersonaVO persona, BigDecimal numExpediente, String tipoTramite, Long idUsuario, String conversion) {
		ResultadoPersonaBean resultado = new ResultadoPersonaBean(Boolean.FALSE);
		try {
			int tipo = utilidadesNIFValidator.isValidDniNieCif(persona.getId().getNif().toUpperCase());
			if (StringUtils.isNotBlank(persona.getApellido1RazonSocial())) {
				if (tipo >= 0) {
					if (tipo == utilidadesNIFValidator.FISICA) {
						String anagrama = Anagrama.obtenerAnagramaFiscal(persona.getApellido1RazonSocial(), persona.getId().getNif());
						persona.setAnagrama(anagrama);
						persona.setTipoPersona(TipoPersona.Fisica.getValorEnum());
						persona.setSubtipo(null);
						persona.setSexo(utiles.convertirCombo(persona.getSexo()));
					} else {
						persona.setSexo(SexoPersona.Juridica.getNombreEnum());
						persona.setApellido2(null);
						persona.setAnagrama(null);
						persona.setFechaNacimiento(null);
						persona.setTipoPersona(TipoPersona.Juridica.getValorEnum());
						if ((persona.getSubtipo() == null || persona.getSubtipo().isEmpty()) && tipo == utilidadesNIFValidator.JURIDICA_PUBLICA) {
							persona.setSubtipo(SubtipoPersona.Publica.getNombreEnum());
						} else if ((persona.getSubtipo() == null || persona.getSubtipo().isEmpty()) && tipo == utilidadesNIFValidator.JURIDICA_PRIVADA) {
							persona.setSubtipo(SubtipoPersona.Privada.getNombreEnum());
						}
					}
					EvolucionPersonaVO evolucion = crearEvolucion(persona.getId().getNif(), persona.getId().getNumColegiado(), numExpediente, tipoTramite, idUsuario);
					PersonaVO personaBBDD = personaDao.getPersona(persona.getId().getNif().toUpperCase(), persona.getId().getNumColegiado());
					// Modificación de persona
					if (personaBBDD != null) {
						PersonaVO personaCompleta = (PersonaVO) personaBBDD.clone();
						evolucion.setTipoActuacion(TipoActualizacion.MOD.getNombreEnum());
						if (conversion != null) {
							conversor.transform(persona, personaCompleta, conversion);
						} else {
							conversor.transform(persona, personaCompleta);
						}
						modificarDatos(personaCompleta);
						ResultadoValPersonaBean resultValPersona = servicioValidacionPersona.esModificadaPersona(personaCompleta, personaBBDD);
						if (!resultValPersona.getError()) {
							if (resultValPersona.getEsModificada()) {
								resultado.setPersona(personaCompleta);
								resultado.setEvolucionPersona(evolucion);
							} else {
								resultado.setPersona(personaBBDD);
							}
						} else {
							resultado.setMensaje(resultValPersona.getMensaje());
							resultado.setError(Boolean.TRUE);
						}
					} else {
						modificarDatos(persona);
						evolucion.setTipoActuacion(TipoActualizacion.CRE.getNombreEnum());
						resultado.setPersona(persona);
						resultado.setEvolucionPersona(evolucion);
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El DNI / NIE / NIF del interviniente no es válido");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Nombre o razón social del interviniente debe ir relleno");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de tratar el guardado de la persona, error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de tratar el guardado de la persona.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoPersonaBean guardarActualizar(PersonaVO personaPantalla, BigDecimal numExpediente, String tipoTramite, Long idUsuario, String conversion) {
		ResultadoPersonaBean resultado = new ResultadoPersonaBean(Boolean.FALSE);
		boolean actualiza = true;
		try {
			int tipo = utilidadesNIFValidator.isValidDniNieCif(personaPantalla.getId().getNif().toUpperCase());
			if (StringUtils.isNotBlank(personaPantalla.getApellido1RazonSocial())) {
				if (tipo >= 0) {
					if (tipo == utilidadesNIFValidator.FISICA) {
						String anagrama = Anagrama.obtenerAnagramaFiscal(personaPantalla.getApellido1RazonSocial(), personaPantalla.getId().getNif());
						personaPantalla.setAnagrama(anagrama);
						personaPantalla.setTipoPersona(TipoPersona.Fisica.getValorEnum());
						personaPantalla.setSubtipo(null);
						personaPantalla.setSexo(utiles.convertirCombo(personaPantalla.getSexo()));
					} else {
						personaPantalla.setSexo(SexoPersona.Juridica.getNombreEnum());
						personaPantalla.setApellido2(null);
						personaPantalla.setAnagrama(null);
						personaPantalla.setFechaNacimiento(null);
						personaPantalla.setTipoPersona(TipoPersona.Juridica.getValorEnum());
						if ((personaPantalla.getSubtipo() == null || personaPantalla.getSubtipo().isEmpty()) && tipo == utilidadesNIFValidator.JURIDICA_PUBLICA) {
							personaPantalla.setSubtipo(SubtipoPersona.Publica.getNombreEnum());
						} else if ((personaPantalla.getSubtipo() == null || personaPantalla.getSubtipo().isEmpty()) && tipo == utilidadesNIFValidator.JURIDICA_PRIVADA) {
							personaPantalla.setSubtipo(SubtipoPersona.Privada.getNombreEnum());
						}
					}

					EvolucionPersonaVO evolucion = crearEvolucion(personaPantalla.getId().getNif(), personaPantalla.getId().getNumColegiado(), numExpediente, tipoTramite, idUsuario);

					PersonaVO personaBBDD = personaDao.getPersona(personaPantalla.getId().getNif().toUpperCase(), personaPantalla.getId().getNumColegiado());

					// Modificación de persona
					if (personaBBDD != null) {
						PersonaVO personaCompleta = (PersonaVO) personaBBDD.clone();
						evolucion.setTipoActuacion(TipoActualizacion.MOD.getNombreEnum());
						if (conversion != null) {
							conversor.transform(personaPantalla, personaCompleta, conversion);
						} else {
							conversor.transform(personaPantalla, personaCompleta);
						}
						actualiza = esModificada(personaCompleta, personaBBDD, evolucion);
						if (actualiza) {
							modificarDatos(personaCompleta);
							personaDao.evict(personaBBDD);
							personaDao.guardarOActualizar(personaCompleta);
							servicioEvolucionPersona.guardarEvolucionPersona(evolucion);
						} else {
							personaDao.evict(personaCompleta);
						}
						resultado.setPersona(personaCompleta);
						// Nueva persona
					} else {
						modificarDatos(personaPantalla);
						personaDao.guardar(personaPantalla);

						evolucion.setTipoActuacion(TipoActualizacion.CRE.getNombreEnum());
						servicioEvolucionPersona.guardarEvolucionPersona(evolucion);
						resultado.setPersona(personaPantalla);
					}
				} else {
					resultado.setError(true);
					resultado.setMensaje("El DNI / NIE / NIF del interviniente no es válido");
				}
			} else {
				resultado.setError(true);
				resultado.setMensaje("Nombre o razón social del interviniente debe ir relleno");
			}
		} catch (Exception e) {
			log.error("Error al guardarActualizar la persona", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar la persona.");
		}
		return resultado;
	}

	private EvolucionPersonaVO crearEvolucion(String nif, String numColegiado, BigDecimal numExpediente, String tipoTramite, Long idUsuario) {
		EvolucionPersonaVO evolucion = new EvolucionPersonaVO();
		EvolucionPersonaPK id = new EvolucionPersonaPK();

		id.setNif(nif);
		id.setNumColegiado(numColegiado);
		id.setFechaHora(new Date());
		evolucion.setId(id);

		evolucion.setNumExpediente(numExpediente);
		evolucion.setTipoTramite(tipoTramite);
		evolucion.setOtros("");
		UsuarioVO usuarioVO = new UsuarioVO();
		usuarioVO.setIdUsuario(idUsuario);
		evolucion.setUsuario(usuarioVO);

		return evolucion;
	}

	private boolean esModificada(PersonaVO personaVO, PersonaVO personaBBDD, EvolucionPersonaVO evolucionPersona) {
		boolean actualiza = false;
		try {

			if (!utiles.sonIgualesString(personaVO.getApellido1RazonSocial() != null ? personaVO.getApellido1RazonSocial().toUpperCase() : null, personaBBDD.getApellido1RazonSocial() != null
					? personaBBDD.getApellido1RazonSocial().toUpperCase() : null)) {
				evolucionPersona.setApellido1Ant(personaBBDD.getApellido1RazonSocial() != null ? personaBBDD.getApellido1RazonSocial().toUpperCase() : null);
				actualiza = true;
				evolucionPersona.setApellido1Nue(personaVO.getApellido1RazonSocial() != null ? personaVO.getApellido1RazonSocial().toUpperCase() : null);
			}
			if (!utiles.sonIgualesString(personaVO.getApellido2() != null ? personaVO.getApellido2().toUpperCase() : null, personaBBDD.getApellido2() != null ? personaBBDD.getApellido2().toUpperCase()
					: null)) {
				evolucionPersona.setApellido2Ant(personaBBDD.getApellido2() != null ? personaBBDD.getApellido2().toUpperCase() : null);
				actualiza = true;
				evolucionPersona.setApellido2Nue(personaVO.getApellido2().toUpperCase());
			}
			if (!utiles.sonIgualesString(personaVO.getNombre() != null ? personaVO.getNombre().toUpperCase() : null, personaBBDD.getNombre() != null ? personaBBDD.getNombre().toUpperCase() : null)) {
				evolucionPersona.setNombreAnt(personaBBDD.getNombre() != null ? personaBBDD.getNombre().toUpperCase() : null);
				actualiza = true;
				evolucionPersona.setNombreNue(personaVO.getNombre() != null ? personaVO.getNombre().toUpperCase() : null);
			}
			if (utilesFecha.compararFechaDate(personaVO.getFechaNacimiento(), personaBBDD.getFechaNacimiento()) != 0) {
				evolucionPersona.setFechaNacimientoAnt(personaBBDD.getFechaNacimiento());
				actualiza = true;
				evolucionPersona.setFechaNacimientoNue(personaVO.getFechaNacimiento());
			}
			if (!utiles.sonIgualesString(personaVO.getAnagrama() != null ? personaVO.getAnagrama().toUpperCase() : null, personaBBDD.getAnagrama() != null ? personaBBDD.getAnagrama().toUpperCase()
					: null)) {
				actualiza = true;
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Anagrama,");
			}
			if (!utiles.sonIgualesObjetos(personaVO.getEstado(), personaBBDD.getEstado())) {
				actualiza = true;
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Estado,");
			}
			if (!utiles.sonIgualesCombo(personaVO.getTipoPersona(), personaBBDD.getTipoPersona())) {
				actualiza = true;
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Tipo Persona,");
			}
			if (!utiles.sonIgualesString(personaVO.getTelefonos(), personaBBDD.getTelefonos())) {
				actualiza = true;
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Telefonos,");
			}
			if (!utiles.sonIgualesCombo(personaVO.getEstadoCivil(), personaBBDD.getEstadoCivil())) {
				actualiza = true;
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "EstadoCivil,");
			}
			if (!utiles.sonIgualesCombo(personaVO.getSexo(), personaBBDD.getSexo())) {
				actualiza = true;
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Sexo,");
			}
			if (!utiles.sonIgualesObjetos(personaVO.getSeccion(), personaBBDD.getSeccion())) {
				actualiza = true;
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Seccion,");
			}
			if (!utiles.sonIgualesObjetos(personaVO.getHoja(), personaBBDD.getHoja())) {
				actualiza = true;
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Hoja,");
			}
			if (!utiles.sonIgualesString(personaVO.getHojaBis(), personaBBDD.getHojaBis())) {
				actualiza = true;
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "HojaBis,");
			}
			if (!utiles.sonIgualesObjetos(personaVO.getIus(), personaBBDD.getIus())) {
				actualiza = true;
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Ius,");
			}
			if (!utiles.sonIgualesString(personaVO.getCorreoElectronico() != null ? personaVO.getCorreoElectronico().toUpperCase() : null, personaBBDD.getCorreoElectronico() != null ? personaBBDD
					.getCorreoElectronico().toUpperCase() : null)) {
				actualiza = true;
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Correo Electronico,");
			}
			if (!utiles.sonIgualesString(personaVO.getIban(), personaBBDD.getIban())) {
				actualiza = true;
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Iban,");
			}
			if (!utiles.sonIgualesString(personaVO.getNcorpme(), personaBBDD.getNcorpme())) {
				actualiza = true;
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Ncorpme,");
			}
			if (!utiles.sonIgualesObjetos(personaVO.getPirpf(), personaBBDD.getPirpf())) {
				actualiza = true;
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Pirpf,");
			}
			if (!utiles.sonIgualesString(personaVO.getFa(), personaBBDD.getFa())) {
				actualiza = true;
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Fa,");
			}
			if (utilesFecha.compararFechaDate(personaVO.getFechaCaducidadNIF(), personaBBDD.getFechaCaducidadNIF()) != 0) {
				actualiza = true;
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Fecha Caducidad Nif,");
			}
			if (utilesFecha.compararFechaDate(personaVO.getFechaCaducidadAlternativo(), personaBBDD.getFechaCaducidadAlternativo()) != 0) {
				actualiza = true;
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Fecha Caducidad Alternativo,");
			}
			if (!utiles.sonIgualesCombo(personaVO.getTipoDocumentoAlternativo(), personaBBDD.getTipoDocumentoAlternativo())) {
				actualiza = true;
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Tipo Documento Alternativo,");
			}
			if (!utiles.sonIgualesCheckBox(personaVO.getIndefinido(), personaBBDD.getIndefinido())) {
				actualiza = true;
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Indefinido,");
			}
			if (!utiles.sonIgualesString(personaVO.getCodigoMandato(), personaBBDD.getCodigoMandato())) {
				actualiza = true;
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Código Mandato,");
			}
			if (!utiles.sonIgualesString(personaVO.getSubtipo(), personaBBDD.getSubtipo())) {
				actualiza = true;
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "SubTipo,");
			}
			if (!utiles.sonIgualesString(personaVO.getUsuarioCorpme(), personaBBDD.getUsuarioCorpme())) {
				actualiza = true;
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Usuario Corpme,");
			}
			if (!utiles.sonIgualesString(personaVO.getPasswordCorpme(), personaBBDD.getPasswordCorpme())) {
				actualiza = true;
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Password Corpme,");
			}
			if (!utiles.sonIgualesString(personaVO.getPaisNacimiento(), personaBBDD.getPaisNacimiento())) {
				actualiza = true;
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "País Nacimiento,");
			}
			if (!utiles.sonIgualesString(personaVO.getNacionalidad(), personaBBDD.getNacionalidad())) {
				actualiza = true;
				evolucionPersona.setOtros(evolucionPersona.getOtros() + "Nacionalidad,");
			}
		} catch (Exception e) {
			log.error("Error al comparar la persona de pantalla con la persona de la bbdd", e);
		}
		return actualiza;
	}

	private void modificarDatos(PersonaVO personaVO) {
		// Mayusculas
		personaVO.getId().setNif(personaVO.getId().getNif().toUpperCase());
		if (personaVO.getApellido1RazonSocial() != null) {
			personaVO.setApellido1RazonSocial(personaVO.getApellido1RazonSocial().toUpperCase());
		}
		if (personaVO.getApellido2() != null) {
			personaVO.setApellido2(personaVO.getApellido2().toUpperCase());
		}
		if (personaVO.getNombre() != null) {
			personaVO.setNombre(personaVO.getNombre().toUpperCase());
		}
		if (personaVO.getCorreoElectronico() != null) {
			personaVO.setCorreoElectronico(personaVO.getCorreoElectronico().toUpperCase());
		}
		if (personaVO.getAnagrama() != null) {
			personaVO.setAnagrama(personaVO.getAnagrama().toUpperCase());
		}
		if (personaVO.getNacionalidad() != null) {
			personaVO.setNacionalidad(personaVO.getNacionalidad().toUpperCase());
		}

		// Combos
		personaVO.setSexo(utiles.convertirCombo(personaVO.getSexo()));
		personaVO.setTipoPersona(utiles.convertirCombo(personaVO.getTipoPersona()));
		personaVO.setTipoDocumentoAlternativo(utiles.convertirCombo(personaVO.getTipoDocumentoAlternativo()));
		personaVO.setEstadoCivil(utiles.convertirCombo(personaVO.getEstadoCivil()));

		// Ver el campo estado
		if (personaVO.getEstado() == null) {
			personaVO.setEstado(new Long(1));
		}
	}

}
