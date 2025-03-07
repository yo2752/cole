package org.gestoresmadrid.oegam2comun.personas.model.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.general.model.vo.ColegiadoVO;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.core.personas.model.dao.PersonaDao;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.personas.model.vo.PersonaDireccionVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioEvolucionPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersonaDireccion;
import org.gestoresmadrid.oegam2comun.personas.view.dto.EvolucionPersonaDto;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioIntervinienteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegistroDto;
import org.gestoresmadrid.oegamComun.colegiado.services.ServicioColegiado;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import escrituras.beans.ResultBean;
import escrituras.utiles.enumerados.SexoPersona;
import escrituras.utiles.enumerados.SubtipoPersona;
import general.utiles.Anagrama;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.validaciones.NIFValidator;

@Service
public class ServicioPersonaImpl implements ServicioPersona {

	private static final long serialVersionUID = 4886095992948605707L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPersonaImpl.class);

	@Autowired
	private PersonaDao personaDao;

	@Autowired
	private ServicioColegiado servicioColegiado;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private ServicioDireccion servicioDireccion;

	@Autowired
	private ServicioIntervinienteTrafico servicioIntervinienteTrafico;

	@Autowired
	private ServicioIntervinienteRegistro servicioIntervinienteRegistro;

	@Autowired
	private ServicioEvolucionPersona servicioEvolucionPersona;

	@Autowired
	private ServicioPersonaDireccion servicioPersonaDireccion;

	@Autowired
	private ServicioUsuario servicioUsuario;

	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Override
	@Transactional
	public PersonaDto getPersona(String nif, String numColegiado) {
		try {
			PersonaVO persona = personaDao.getPersona(nif, numColegiado);
			if (persona != null) {
				return conversor.transform(persona, PersonaDto.class);
			}
		} catch (Exception e) {
			log.error("Error el obtener la persona");
		}
		return null;
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
	public DireccionDto getDireccionActiva(String nif, String numColegiado) {
		ResultBean resultado = new ResultBean();
		DireccionDto direccionDto = null;
		try {
			resultado = servicioPersonaDireccion.buscarPersonaDireccionVO(numColegiado, nif);
			if (!resultado.getError()) {
				PersonaDireccionVO personaDireccion = (PersonaDireccionVO) resultado.getAttachment(ServicioPersonaDireccion.PERSONADIRECCION);
				direccionDto = conversor.transform(personaDireccion.getDireccion(), DireccionDto.class);
			}
		} catch (Exception e) {
			log.error("Error al obtener la direccióna activa de la persona", e);
		}
		return direccionDto;
	}

	@Override
	@Transactional
	public PersonaDto getPersonaConDireccion(String nif, String numColegiado) {
		try {
			PersonaDto persona = getPersona(nif, numColegiado);
			if (persona != null) {
				DireccionDto direccion = getDireccionActiva(nif, numColegiado);
				persona.setDireccionDto(direccion);
				return persona;
			}
		} catch (Exception e) {
			log.error("Error el obtener la persona y la dirección", e);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultBean guardarActualizar(PersonaDto persona) {
		ResultBean resultado = new ResultBean();
		try {
			PersonaVO personaVO = conversor.transform(persona, PersonaVO.class);
			personaVO = personaDao.guardarOActualizar(personaVO);
			/*List<PersonaVO> lPersona = personaDao.buscar(personaVO);

			if (lPersona != null && lPersona.size() > 0) {
				persona = conversor.transform(persona, PersonaDto.class);
				resultado.addAttachment(PERSONA, persona);
			}*/
			persona = conversor.transform(personaVO, PersonaDto.class);
			resultado.addAttachment(PERSONA, persona);
			resultado.setError(false);
		} catch (Exception e) {
			resultado.setError(true);
			resultado.addMensajeALista("Error al guardarActualizar la persona");
			log.error("Error al guardarActualizar la persona", e);
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean guardarMantenimientoPersona(PersonaDto persona, BigDecimal numExpediente, String tipoTramite, BigDecimal idUsuario, String conversion) {
		ResultBean resultado = new ResultBean();
		boolean direccionNueva = false;
		try {
			PersonaVO personaVO = conversor.transform(persona, PersonaVO.class);
			UsuarioDto usuario = servicioUsuario.getUsuarioDto(idUsuario);
			resultado = guardarActualizar(personaVO, numExpediente, tipoTramite, usuario, conversion);
			if (!resultado.getError()) {
				// Guardar dirección
				if (persona.getDireccionDto() != null && utiles.convertirCombo(persona.getDireccionDto().getIdProvincia()) != null) {
					DireccionVO direccionVO = conversor.transform(persona.getDireccionDto(), DireccionVO.class);
					ResultBean resultDireccion = servicioDireccion.guardarActualizarPersona(direccionVO, persona.getNif(), persona.getNumColegiado(), tipoTramite, null);
					if (resultDireccion.getError()) {
						resultado.setMensaje(resultDireccion.getMensaje());
					} else {
						direccionNueva = (Boolean) resultDireccion.getAttachment(ServicioDireccion.NUEVA_DIRECCION);
						servicioEvolucionPersona.guardarEvolucionPersonaDireccion(persona.getNif(), null, tipoTramite, persona.getNumColegiado(), usuario, direccionNueva);
					}
				}
			}
		} catch (Exception e) {
			log.error("Error al guardarActualizar la persona", e);
		}
		return resultado;
	}

	@Override
	public ResultBean guardarActualizar(PersonaVO personaPantalla, BigDecimal numExpediente, String tipoTramite, UsuarioDto usuario, String conversion) {
		ResultBean resultado = new ResultBean();
		boolean actualiza = true;

		EvolucionPersonaDto evolucionDto = new EvolucionPersonaDto();
		try {
			int tipo = NIFValidator.isValidDniNieCif(personaPantalla.getId().getNif().toUpperCase());
			if (personaPantalla.getApellido1RazonSocial() != null && !"".equals(personaPantalla.getApellido1RazonSocial())) {
				if (tipo >= 0) {
					if (tipo == NIFValidator.FISICA) {
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
						if ((personaPantalla.getSubtipo() == null || personaPantalla.getSubtipo().isEmpty()) && tipo == NIFValidator.JURIDICA_PUBLICA) {
							personaPantalla.setSubtipo(SubtipoPersona.Publica.getNombreEnum());
						} else if ((personaPantalla.getSubtipo() == null || personaPantalla.getSubtipo().isEmpty()) && tipo == NIFValidator.JURIDICA_PRIVADA) {
							personaPantalla.setSubtipo(SubtipoPersona.Privada.getNombreEnum());
						}
					}
					evolucionDto.setNumColegiado(personaPantalla.getId().getNumColegiado());
					evolucionDto.setNumExpediente(numExpediente);
					evolucionDto.setTipoTramite(tipoTramite);
					evolucionDto.setNif(personaPantalla.getId().getNif().toUpperCase());
					evolucionDto.setOtros("");
					evolucionDto.setUsuario(usuario);

					PersonaVO personaBBDD = personaDao.getPersona(personaPantalla.getId().getNif().toUpperCase(), personaPantalla.getId().getNumColegiado());

					// Modificación de persona
					if (personaBBDD != null) {
						PersonaVO personaCompleta = (PersonaVO) personaBBDD.clone();
						evolucionDto.setTipoActuacion(TipoActualizacion.MOD.getNombreEnum());
						if (conversion != null) {
							conversor.transform(personaPantalla, personaCompleta, conversion);
						} else {
							conversor.transform(personaPantalla, personaCompleta);
						}
						actualiza = esModificada(personaCompleta, personaBBDD, evolucionDto);
						if (actualiza) {
							modificarDatos(personaCompleta);
							personaDao.evict(personaBBDD);
							personaDao.guardarOActualizar(personaCompleta);
							servicioEvolucionPersona.guardarEvolucion(evolucionDto);
						} else {
							personaDao.evict(personaCompleta);
						}
						resultado.addAttachment(PERSONA, personaCompleta);
						// Nueva persona
					} else {
						modificarDatos(personaPantalla);
						personaDao.guardar(personaPantalla);

						// Evolución
						evolucionDto.setTipoActuacion(TipoActualizacion.CRE.getNombreEnum());
						servicioEvolucionPersona.guardarEvolucion(evolucionDto);
						resultado.addAttachment(PERSONA, personaPantalla);
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

	@Override
	@Transactional
	public ResultBean buscarPersona(String nif, String numColegiado) {
		ResultBean resultado = new ResultBean();
		try {
			PersonaDto personaDto = getPersona(nif, numColegiado);
			resultado.addAttachment(PERSONA, personaDto);
		} catch (Exception e) {
			log.error("Error al buscar una persona", e);
			resultado.setError(true);
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
	public boolean esModificada(PersonaVO personaVO, PersonaVO personaBBDD, EvolucionPersonaDto evolucionPersonaDto) {
		boolean actualiza = false;
		try {

			if (!utiles.sonIgualesString(personaVO.getApellido1RazonSocial() != null ? personaVO.getApellido1RazonSocial().toUpperCase() : null, personaBBDD.getApellido1RazonSocial() != null
					? personaBBDD.getApellido1RazonSocial().toUpperCase() : null)) {
				evolucionPersonaDto.setApellido1Ant(personaBBDD.getApellido1RazonSocial() != null ? personaBBDD.getApellido1RazonSocial().toUpperCase() : null);
				actualiza = true;
				evolucionPersonaDto.setApellido1Nue(personaVO.getApellido1RazonSocial() != null ? personaVO.getApellido1RazonSocial().toUpperCase() : null);
			}
			if (!utiles.sonIgualesString(personaVO.getApellido2() != null ? personaVO.getApellido2().toUpperCase() : null, personaBBDD.getApellido2() != null ? personaBBDD.getApellido2().toUpperCase()
					: null)) {
				evolucionPersonaDto.setApellido2Ant(personaBBDD.getApellido2() != null ? personaBBDD.getApellido2().toUpperCase() : null);
				actualiza = true;
				evolucionPersonaDto.setApellido2Nue(personaVO.getApellido2().toUpperCase());
			}
			if (!utiles.sonIgualesString(personaVO.getNombre() != null ? personaVO.getNombre().toUpperCase() : null, personaBBDD.getNombre() != null ? personaBBDD.getNombre().toUpperCase() : null)) {
				evolucionPersonaDto.setNombreAnt(personaBBDD.getNombre() != null ? personaBBDD.getNombre().toUpperCase() : null);
				actualiza = true;
				evolucionPersonaDto.setNombreNue(personaVO.getNombre() != null ? personaVO.getNombre().toUpperCase() : null);
			}
			if (utilesFecha.compararFechaDate(personaVO.getFechaNacimiento(), personaBBDD.getFechaNacimiento()) != 0) {
				evolucionPersonaDto.setFechaNacimientoAnt(utilesFecha.getFechaTimeStampConDate(personaBBDD.getFechaNacimiento()));
				actualiza = true;
				evolucionPersonaDto.setFechaNacimientoNue(utilesFecha.getFechaTimeStampConDate(personaVO.getFechaNacimiento()));
			}
			if (!utiles.sonIgualesString(personaVO.getAnagrama() != null ? personaVO.getAnagrama().toUpperCase() : null, personaBBDD.getAnagrama() != null ? personaBBDD.getAnagrama().toUpperCase()
					: null)) {
				actualiza = true;
				evolucionPersonaDto.setOtros(evolucionPersonaDto.getOtros() + "Anagrama,");
			}
			if (!utiles.sonIgualesObjetos(personaVO.getEstado(), personaBBDD.getEstado())) {
				actualiza = true;
				evolucionPersonaDto.setOtros(evolucionPersonaDto.getOtros() + "Estado,");
			}
			if (!utiles.sonIgualesCombo(personaVO.getTipoPersona(), personaBBDD.getTipoPersona())) {
				actualiza = true;
				evolucionPersonaDto.setOtros(evolucionPersonaDto.getOtros() + "Tipo Persona,");
			}
			if (!utiles.sonIgualesString(personaVO.getTelefonos(), personaBBDD.getTelefonos())) {
				actualiza = true;
				evolucionPersonaDto.setOtros(evolucionPersonaDto.getOtros() + "Telefonos,");
			}
			if (!utiles.sonIgualesCombo(personaVO.getEstadoCivil(), personaBBDD.getEstadoCivil())) {
				actualiza = true;
				evolucionPersonaDto.setOtros(evolucionPersonaDto.getOtros() + "EstadoCivil,");
			}
			if (!utiles.sonIgualesCombo(personaVO.getSexo(), personaBBDD.getSexo())) {
				actualiza = true;
				evolucionPersonaDto.setOtros(evolucionPersonaDto.getOtros() + "Sexo,");
			}
			if (!utiles.sonIgualesObjetos(personaVO.getSeccion(), personaBBDD.getSeccion())) {
				actualiza = true;
				evolucionPersonaDto.setOtros(evolucionPersonaDto.getOtros() + "Seccion,");
			}
			if (!utiles.sonIgualesObjetos(personaVO.getHoja(), personaBBDD.getHoja())) {
				actualiza = true;
				evolucionPersonaDto.setOtros(evolucionPersonaDto.getOtros() + "Hoja,");
			}
			if (!utiles.sonIgualesString(personaVO.getHojaBis(), personaBBDD.getHojaBis())) {
				actualiza = true;
				evolucionPersonaDto.setOtros(evolucionPersonaDto.getOtros() + "HojaBis,");
			}
			if (!utiles.sonIgualesObjetos(personaVO.getIus(), personaBBDD.getIus())) {
				actualiza = true;
				evolucionPersonaDto.setOtros(evolucionPersonaDto.getOtros() + "Ius,");
			}
			if (!utiles.sonIgualesString(personaVO.getCorreoElectronico() != null ? personaVO.getCorreoElectronico().toUpperCase() : null, personaBBDD.getCorreoElectronico() != null ? personaBBDD
					.getCorreoElectronico().toUpperCase() : null)) {
				actualiza = true;
				evolucionPersonaDto.setOtros(evolucionPersonaDto.getOtros() + "Correo Electronico,");
			}
			if (!utiles.sonIgualesString(personaVO.getIban(), personaBBDD.getIban())) {
				actualiza = true;
				evolucionPersonaDto.setOtros(evolucionPersonaDto.getOtros() + "Iban,");
			}
			if (!utiles.sonIgualesString(personaVO.getNcorpme(), personaBBDD.getNcorpme())) {
				actualiza = true;
				evolucionPersonaDto.setOtros(evolucionPersonaDto.getOtros() + "Ncorpme,");
			}
			if (!utiles.sonIgualesObjetos(personaVO.getPirpf(), personaBBDD.getPirpf())) {
				actualiza = true;
				evolucionPersonaDto.setOtros(evolucionPersonaDto.getOtros() + "Pirpf,");
			}
			if (!utiles.sonIgualesString(personaVO.getFa(), personaBBDD.getFa())) {
				actualiza = true;
				evolucionPersonaDto.setOtros(evolucionPersonaDto.getOtros() + "Fa,");
			}
			if (utilesFecha.compararFechaDate(personaVO.getFechaCaducidadNIF(), personaBBDD.getFechaCaducidadNIF()) != 0) {
				actualiza = true;
				evolucionPersonaDto.setOtros(evolucionPersonaDto.getOtros() + "Fecha Caducidad Nif,");
			}
			if (utilesFecha.compararFechaDate(personaVO.getFechaCaducidadAlternativo(), personaBBDD.getFechaCaducidadAlternativo()) != 0) {
				actualiza = true;
				evolucionPersonaDto.setOtros(evolucionPersonaDto.getOtros() + "Fecha Caducidad Alternativo,");
			}
			if (!utiles.sonIgualesCombo(personaVO.getTipoDocumentoAlternativo(), personaBBDD.getTipoDocumentoAlternativo())) {
				actualiza = true;
				evolucionPersonaDto.setOtros(evolucionPersonaDto.getOtros() + "Tipo Documento Alternativo,");
			}
			if (!utiles.sonIgualesCheckBox(personaVO.getIndefinido(), personaBBDD.getIndefinido())) {
				actualiza = true;
				evolucionPersonaDto.setOtros(evolucionPersonaDto.getOtros() + "Indefinido,");
			}
			if (!utiles.sonIgualesString(personaVO.getCodigoMandato(), personaBBDD.getCodigoMandato())) {
				actualiza = true;
				evolucionPersonaDto.setOtros(evolucionPersonaDto.getOtros() + "Código Mandato,");
			}
			if (!utiles.sonIgualesString(personaVO.getSubtipo(), personaBBDD.getSubtipo())) {
				actualiza = true;
				evolucionPersonaDto.setOtros(evolucionPersonaDto.getOtros() + "SubTipo,");
			}
			if (!utiles.sonIgualesString(personaVO.getUsuarioCorpme(), personaBBDD.getUsuarioCorpme())) {
				actualiza = true;
				evolucionPersonaDto.setOtros(evolucionPersonaDto.getOtros() + "Usuario Corpme,");
			}
			if (!utiles.sonIgualesString(personaVO.getPasswordCorpme(), personaBBDD.getPasswordCorpme())) {
				actualiza = true;
				evolucionPersonaDto.setOtros(evolucionPersonaDto.getOtros() + "Password Corpme,");
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

		// Combos
		personaVO.setSexo(utiles.convertirCombo(personaVO.getSexo()));
		personaVO.setTipoPersona(utiles.convertirCombo(personaVO.getTipoPersona()));
		personaVO.setTipoDocumentoAlternativo(utiles.convertirCombo(personaVO.getTipoDocumentoAlternativo()));
		personaVO.setEstadoCivil(utiles.convertirCombo(personaVO.getEstadoCivil()));

		// Ver el campo estado
		if (personaVO.getEstado() == null) {
			personaVO.setEstado(1L);
		}
	}

	@Override
	@Transactional
	public PersonaDto obtenerColegiadoCompleto(String numColegiado, BigDecimal idContrato) {
		PersonaDto personaDto = null;
		try {
			ColegiadoVO colegiado = servicioColegiado.getColegiado(numColegiado);
			if (colegiado != null && colegiado.getUsuario() != null) {
				ResultBean resultado = buscarPersona(colegiado.getUsuario().getNif(), numColegiado);
				if (!resultado.getError()) {
					personaDto = (PersonaDto) resultado.getAttachment(PERSONA);
					ContratoVO contrato = servicioContrato.getContrato(idContrato);
					if (contrato != null) {
						personaDto.setCorreoElectronico(contrato.getCorreoElectronico());
						DireccionDto direccion = getDireccionContrato(contrato);
						personaDto.setDireccionDto(direccion);
					}
				}
			}
		} catch (Exception e) {
			log.error("Error al obtenerColegiadoCompleto", e);
		}
		return personaDto;
	}

	private DireccionDto getDireccionContrato(ContratoVO contrato) {
		DireccionDto direccion = new DireccionDto();
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
	@Transactional
	public ResultBean eliminarFusionarDireccion(String numColegiado, String nif, Long idDireccionPrincipal, Long idDireccionBorrar, Short estadoPersonaDireccion) {
		ResultBean result = new ResultBean();
		boolean existenIntervinientes = false;
		try {
			existenIntervinientes = servicioIntervinienteTrafico.existenIntervinientesPorIdDireccion(nif, numColegiado, idDireccionBorrar);

			if (!existenIntervinientes) {
				existenIntervinientes = servicioIntervinienteRegistro.existenIntervinientesPorIdDireccion(nif, numColegiado, idDireccionBorrar);
			}

			result = servicioPersonaDireccion.eliminarFusionarDireccion(numColegiado, nif, idDireccionPrincipal, idDireccionBorrar, existenIntervinientes, estadoPersonaDireccion);
			if ((result.getMensaje() == null || "".equals(result.getMensaje())) && !existenIntervinientes) {
				servicioDireccion.eliminar(idDireccionBorrar);
			}
		} catch (Exception e) {
			result.setError(true);
			result.setMensaje("Error al fusionar la dirección del interviniente: " + e.getMessage());
			log.error("Error al fusionar la dirección del interviniente", e);
		}
		return result;
	}

	@Override
	@Transactional
	public ResultBean asignarPrincipal(String numColegiado, String nif, Long idDireccionPrincipal) {
		ResultBean result = new ResultBean();
		try {
			result = servicioPersonaDireccion.asignarPrincipal(numColegiado, nif, idDireccionPrincipal);
			if (result.getError()) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
		} catch (Exception e) {
			result.setError(true);
			result.setMensaje("Error al asignar como principal la dirección del interviniente: " + e.getMessage());
			log.error("Error al asignar como principal la dirección del interviniente", e);
		}
		return result;
	}

	@Override
	@Transactional
	public List<PersonaDto> getListaNuevasPersonasPorFecha(Date fechaUltimoEnvioData) {
		List<PersonaVO> lista = personaDao.getListaNuevasPersonasPorFecha(fechaUltimoEnvioData);
		if (lista != null && !lista.isEmpty()) {
			return conversor.transform(lista, PersonaDto.class);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultBean guardarSociedad(TramiteRegistroDto tramiteRegistroDto, BigDecimal idUsuario) {
		ResultBean result = new ResultBean();

		PersonaDto sociedadDto = tramiteRegistroDto.getSociedad();

		if ((sociedadDto.getSeccion() == null && sociedadDto.getHoja() != null) || (sociedadDto.getSeccion() != null && sociedadDto.getHoja() == null)) {
			result.setError(true);
			result.addMensajeALista("Si se indica sección se debe indicar hoja y si se indica hoja se debe indicar sección");
		} else if (sociedadDto.getIus() != null && sociedadDto.getIus().toString().length() != 15) {
			result.setError(true);
			result.addMensajeALista("El IUS debe tener 15 caracteres");
		} else if (StringUtils.isNotBlank(sociedadDto.getTipoPersona()) && !TipoPersona.Juridica.getValorEnum().equals(sociedadDto.getTipoPersona())) {
			result.setError(true);
			result.addMensajeALista("Se debe tratar de una organización JURIDÍCA no puede ser una persona FÍSICA.");
		} else {
			UsuarioDto usuario = servicioUsuario.getUsuarioDto(idUsuario);
			sociedadDto.setNumColegiado(tramiteRegistroDto.getNumColegiado());

			PersonaVO sociedadVO = conversor.transform(sociedadDto, PersonaVO.class);

			result = guardarActualizar(sociedadVO, tramiteRegistroDto.getIdTramiteRegistro(), tramiteRegistroDto.getTipoTramite(), usuario, CONVERSION_PERSONA_SOCIEDAD);

			if (null == result || result.getError()) {
				return result;
			}
		}
		return result;
	}

	@Override
	@Transactional
	public ResultBean guardarCargo(PersonaDto cargo, BigDecimal numExpediente, String tipoTramite, BigDecimal idUsuario) {
		ResultBean result;
		UsuarioDto usuario = servicioUsuario.getUsuarioDto(idUsuario);
		PersonaVO cargoVO = conversor.transform(cargo, PersonaVO.class);
		result = guardarActualizar(cargoVO, numExpediente, tipoTramite, usuario, CONVERSION_PERSONA_CARGO);
		if (result != null && !result.getError()) {
			cargoVO = (PersonaVO) result.getAttachment(ServicioPersona.PERSONA);
			if (!TipoPersona.Fisica.getValorEnum().equals(cargoVO.getTipoPersona())) {
				result.setError(true);
				result.addMensajeALista("Un cargo debe ser una persona FÍSICA");
			}
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public String obtenerCodigoMandato(String numColegiado) {
		return personaDao.obtenerCodigoMandato(numColegiado);
	}

	public PersonaDao getPersonaDao() {
		return personaDao;
	}

	public void setPersonaDao(PersonaDao personaDao) {
		this.personaDao = personaDao;
	}

	public ServicioContrato getServicioContrato() {
		return servicioContrato;
	}

	public void setServicioContrato(ServicioContrato servicioContrato) {
		this.servicioContrato = servicioContrato;
	}

	public ServicioEvolucionPersona getServicioEvolucionPersona() {
		return servicioEvolucionPersona;
	}

	public void setServicioEvolucionPersona(ServicioEvolucionPersona servicioEvolucionPersona) {
		this.servicioEvolucionPersona = servicioEvolucionPersona;
	}

	public ServicioColegiado getServicioColegiado() {
		return servicioColegiado;
	}

	public void setServicioColegiado(ServicioColegiado servicioColegiado) {
		this.servicioColegiado = servicioColegiado;
	}

	public ServicioDireccion getServicioDireccion() {
		return servicioDireccion;
	}

	public void setServicioDireccion(ServicioDireccion servicioDireccion) {
		this.servicioDireccion = servicioDireccion;
	}

	public ServicioPersonaDireccion getServicioPersonaDireccion() {
		return servicioPersonaDireccion;
	}

	public void setServicioPersonaDireccion(ServicioPersonaDireccion servicioPersonaDireccion) {
		this.servicioPersonaDireccion = servicioPersonaDireccion;
	}

	public ServicioIntervinienteTrafico getServicioIntervinienteTrafico() {
		return servicioIntervinienteTrafico;
	}

	public void setServicioIntervinienteTrafico(ServicioIntervinienteTrafico servicioIntervinienteTrafico) {
		this.servicioIntervinienteTrafico = servicioIntervinienteTrafico;
	}

	public ServicioUsuario getServicioUsuario() {
		return servicioUsuario;
	}

	public void setServicioUsuario(ServicioUsuario servicioUsuario) {
		this.servicioUsuario = servicioUsuario;
	}
}
