package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.registradores.model.dao.IntervinienteRegistroDao;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoPersonaRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.vo.IntervinienteRegistroVO;
import org.gestoresmadrid.core.registradores.model.vo.TramiteRegRbmVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioEvolucionPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioDatRegMercantil;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioIntervinienteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioNotarioRegistro;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.IntervinienteRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegRbmDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import escrituras.beans.ResultBean;
import general.utiles.Anagrama;
import utilidades.validaciones.NIFValidator;

@Service
public class ServicioIntervinienteRegistroImpl implements ServicioIntervinienteRegistro {

	private static final long serialVersionUID = -1337945142565276615L;

	public static final String ID_DIRECCION_DESTINATARIO = "IDDIRECCIONDESTINATARIO";

	@Autowired
	private IntervinienteRegistroDao intervinienteRegistroDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioDireccion servicioDireccion;

	@Autowired
	private ServicioEvolucionPersona servicioEvolucionPersona;

	@Autowired
	private ServicioUsuario servicioUsuario;

	@Autowired
	private ServicioNotarioRegistro servicioNotarioRegistro;

	@Autowired
	private ServicioDatRegMercantil servicioDatRegMercantil;

	@Autowired
	Utiles utiles;

	public ServicioIntervinienteRegistroImpl() {}

	@Override
	@Transactional
	public ResultRegistro guardarInterviniente(IntervinienteRegistroDto interviniente, TramiteRegRbmDto tramiteRegRbmDto) {

		ResultRegistro result;
		long idDatRegMercantil = 0;
		long codigoNotario = 0;

		// Nuevo Interviniente
		result = validarInterviniente(interviniente, tramiteRegRbmDto.getTipoTramite());
		if (result.isError()) {
			return result;
		}
		List<TramiteRegRbmDto> listTramites = new ArrayList<>();
		if (null != tramiteRegRbmDto) {
			listTramites.add(tramiteRegRbmDto);
		}
		interviniente.setTramites(listTramites);
		interviniente.setNumColegiado(tramiteRegRbmDto.getNumColegiado());
		interviniente.getPersona().setNif(interviniente.getNif());
		interviniente.getPersona().setNumColegiado(interviniente.getNumColegiado());
		interviniente.getPersona().setEstado("1");
		interviniente.getPersona().setDireccionDto(interviniente.getDireccion());

		BigDecimal idUsuario = tramiteRegRbmDto.getIdUsuario();
		UsuarioDto usuario = servicioUsuario.getUsuarioDto(idUsuario);
		ResultBean resultPersona = servicioPersona.guardarActualizar(conversor.transform(interviniente.getPersona(), PersonaVO.class), null, null, usuario, null);
		boolean direccionNueva = false;
		// Si tiene notario se guarda
		if (null != interviniente.getNotario() && StringUtils.isNotBlank(interviniente.getNotario().getNif())) {
			result = servicioNotarioRegistro.guardarOActualizarNotario(interviniente);
			if (result.isError())
				return result;

			codigoNotario = (long) result.getObj();
			if (0 != codigoNotario) {
				interviniente.getNotario().setCodigo(codigoNotario);
			} else {
				interviniente.setNotario(null);
			}
		} else if (null != interviniente.getNotario() && 0 != interviniente.getNotario().getCodigo()) {
			servicioNotarioRegistro.borrarNotario(String.valueOf(interviniente.getNotario().getCodigo()));
		} else {
			interviniente.setNotario(null);
		}

		// Si tiene datos registro mercantil se guarda
		if (null != interviniente.getDatRegMercantil() && StringUtils.isNotBlank(interviniente.getDatRegMercantil().getCodRegistroMercantil())) {
			if (TipoTramiteRegistro.MODELO_10.getValorEnum().equals(tramiteRegRbmDto.getTipoTramite()) && TipoInterviniente.RepresentanteSolicitante.getValorEnum().equals(interviniente
					.getTipoInterviniente())) {
				result = servicioDatRegMercantil.guardarOActualizarDatRegMercantilCancelacion(interviniente.getDatRegMercantil());
			} else {
				result = servicioDatRegMercantil.guardarOActualizarDatRegMercantil(interviniente.getDatRegMercantil());
			}
			if (result.isError())
				return result;
			idDatRegMercantil = (long) result.getObj();
		}
		if (0 != idDatRegMercantil) {
			interviniente.getDatRegMercantil().setIdDatRegMercantil(idDatRegMercantil);
		} else {
			interviniente.setDatRegMercantil(null);
		}
		if (!resultPersona.getError() && !result.isError()) {
			// Guardar direccion
			if (interviniente.getPersona().getDireccionDto() != null && utiles.convertirCombo(interviniente.getPersona().getDireccionDto().getIdProvincia()) != null) {

				DireccionVO direccion = conversor.transform(interviniente.getPersona().getDireccionDto(), DireccionVO.class);

				ResultBean resultDireccion = servicioDireccion.guardarActualizarPersona(direccion, interviniente.getPersona().getNif(), interviniente.getPersona().getNumColegiado(),
						TipoTramiteRegistro.MODELO_7.getValorEnum(), ServicioDireccion.CONVERSION_DIRECCION_REGISTRO);
				if (resultDireccion != null && !resultDireccion.getError()) {
					direccion = (DireccionVO) resultDireccion.getAttachment(ServicioDireccion.DIRECCION);
					direccionNueva = (Boolean) resultDireccion.getAttachment(ServicioDireccion.NUEVA_DIRECCION);
					servicioEvolucionPersona.guardarEvolucionPersonaDireccion(interviniente.getPersona().getNif(), null, null, interviniente.getPersona().getNumColegiado(), usuario, direccionNueva);
					interviniente.setIdDireccion(direccion.getIdDireccion());
					IntervinienteRegistroVO intervinienteGuardado = intervinienteRegistroDao.guardarOActualizar(conversor.transform(interviniente, IntervinienteRegistroVO.class));
					result.setObj(conversor.transform(intervinienteGuardado, IntervinienteRegistroDto.class));
					if (result.getObj() != null) {
						result.setMensaje("Interviniente guardado correctamente");
					} else {
						result.setError(Boolean.TRUE);
						result.setMensaje("Error guardando interviniente");
					}
				} else {
					if (null != resultDireccion && null != resultDireccion.getListaMensajes() && !resultDireccion.getListaMensajes().isEmpty()) {
						result.setError(Boolean.TRUE);
						result.setMensaje(resultDireccion.getMensaje());
					}
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				}
			} else {
				result.setError(Boolean.TRUE);
				result.setMensaje("Debe seleccionar una provincia");
			}
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje(resultPersona.getMensaje());
		}

		if (result.isError())
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro guardarIntervinienteCancelacion(IntervinienteRegistroDto interviniente, TramiteRegRbmDto tramiteRegRbmDto) {

		ResultRegistro result;
		long idDatRegMercantil = 0;
		long codigoNotario = 0;

		// Nuevo Interviniente
		result = validarInterviniente(interviniente, tramiteRegRbmDto.getTipoTramite());
		if (result.isError()) {
			return result;
		}
		List<TramiteRegRbmDto> listTramites = new ArrayList<>();
		listTramites.add(tramiteRegRbmDto);

		interviniente.setTramites(listTramites);
		interviniente.setNumColegiado(tramiteRegRbmDto.getNumColegiado());
		interviniente.getPersona().setNif(interviniente.getNif());
		interviniente.getPersona().setNumColegiado(interviniente.getNumColegiado());
		interviniente.getPersona().setEstado("1");
		interviniente.getPersona().setDireccionDto(interviniente.getDireccion());

		BigDecimal idUsuario = tramiteRegRbmDto.getIdUsuario();
		UsuarioDto usuario = servicioUsuario.getUsuarioDto(idUsuario);
		ResultBean resultPersona = servicioPersona.guardarActualizar(conversor.transform(interviniente.getPersona(), PersonaVO.class), null, null, usuario, null);

		// Si tiene notario se guarda
		if (null != interviniente.getNotario() && StringUtils.isNotBlank(interviniente.getNotario().getNif())) {
			result = servicioNotarioRegistro.guardarOActualizarNotario(interviniente);
			if (result.isError())
				return result;

			codigoNotario = (long) result.getObj();
			if (0 != codigoNotario) {
				interviniente.getNotario().setCodigo(codigoNotario);
			} else {
				interviniente.setNotario(null);
			}
		} else if (null != interviniente.getNotario() && 0 != interviniente.getNotario().getCodigo()) {
			servicioNotarioRegistro.borrarNotario(String.valueOf(interviniente.getNotario().getCodigo()));
		} else {
			interviniente.setNotario(null);
		}

		// Si tiene datos registro mercantil se guarda
		if (null != interviniente.getDatRegMercantil() && StringUtils.isNotBlank(interviniente.getDatRegMercantil().getCodRegistroMercantil())) {
			if (TipoTramiteRegistro.MODELO_10.getValorEnum().equals(tramiteRegRbmDto.getTipoTramite()) && TipoInterviniente.RepresentanteSolicitante.getValorEnum().equals(interviniente
					.getTipoInterviniente())) {
				result = servicioDatRegMercantil.guardarOActualizarDatRegMercantilCancelacion(interviniente.getDatRegMercantil());
			} else {
				result = servicioDatRegMercantil.guardarOActualizarDatRegMercantil(interviniente.getDatRegMercantil());
			}
			if (result.isError())
				return result;
			idDatRegMercantil = (long) result.getObj();
		}
		if (0 != idDatRegMercantil) {
			interviniente.getDatRegMercantil().setIdDatRegMercantil(idDatRegMercantil);
		} else {
			interviniente.setDatRegMercantil(null);
		}

		if (!resultPersona.getError() && !result.isError()) {
			IntervinienteRegistroVO intervinienteGuardado = intervinienteRegistroDao.guardarOActualizar(conversor.transform(interviniente, IntervinienteRegistroVO.class));
			result.setObj(conversor.transform(intervinienteGuardado, IntervinienteRegistroDto.class));
			if (result.getObj() != null) {
				result.setMensaje("Interviniente guardado correctamente");
			} else {
				result.setError(Boolean.TRUE);
				result.setMensaje("Error guardando interviniente");
			}

		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje(resultPersona.getMensaje());
		}

		if (result.isError())
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		return result;
	}

	@Override
	@Transactional
	public ResultBean guardarIntervinienteSociedad(PersonaDto destinatario, BigDecimal idTramiteRegistro, String numColegiado, String tipoTramite, BigDecimal idUsuario) {
		ResultBean respuesta;
		boolean direccionNueva = false;

		respuesta = validarIntervinienteSociedad(destinatario);
		if (respuesta.getError()) {
			return respuesta;
		}

		if (null != destinatario) {
			destinatario.setNumColegiado(numColegiado);
		}

		PersonaVO destinatarioVO = conversor.transform(destinatario, PersonaVO.class);

		if (destinatarioVO != null && destinatarioVO.getId() != null && StringUtils.isNotBlank(destinatarioVO.getId().getNif())) {
			destinatarioVO.getId().setNif(destinatarioVO.getId().getNif().toUpperCase());
			destinatarioVO.getId().setNumColegiado(numColegiado);
			destinatarioVO.setEstado(new Long("1"));
			// Se crea el anagrama
			String anagrama = Anagrama.obtenerAnagramaFiscal(destinatarioVO.getApellido1RazonSocial(), destinatarioVO.getId().getNif());
			destinatarioVO.setAnagrama(anagrama);

			// Guardar persona
			UsuarioDto usuario = servicioUsuario.getUsuarioDto(idUsuario);
			ResultBean resultPersona = servicioPersona.guardarActualizar(destinatarioVO, idTramiteRegistro, tipoTramite, usuario, ServicioPersona.CONVERSION_PERSONA_REGISTRO);

			if (!resultPersona.getError()) {
				// Guardar direccion
				if (destinatario.getDireccionDto() != null && utiles.convertirCombo(destinatario.getDireccionDto().getIdProvincia()) != null) {

					DireccionVO direccion = conversor.transform(destinatario.getDireccionDto(), DireccionVO.class);

					ResultBean resultDireccion = servicioDireccion.guardarActualizarPersona(direccion, destinatario.getNif(), destinatario.getNumColegiado(), tipoTramite,
							ServicioDireccion.CONVERSION_DIRECCION_REGISTRO);
					if (resultDireccion != null && !resultDireccion.getError()) {
						direccion = (DireccionVO) resultDireccion.getAttachment(ServicioDireccion.DIRECCION);
						direccionNueva = (Boolean) resultDireccion.getAttachment(ServicioDireccion.NUEVA_DIRECCION);
						servicioEvolucionPersona.guardarEvolucionPersonaDireccion(destinatario.getNif(), idTramiteRegistro, tipoTramite, destinatario.getNumColegiado(), usuario, direccionNueva);
					}

					respuesta.addAttachment(ID_DIRECCION_DESTINATARIO, direccion.getIdDireccion());
				}
			} else {
				respuesta.addMensajeALista(resultPersona.getMensaje());
				respuesta.setError(Boolean.TRUE);
			}
		} else {
			respuesta.addMensajeALista("No se ha guardado ningún interviniente");
			respuesta.setError(Boolean.TRUE);
		}
		return respuesta;
	}

	@Override
	@Transactional
	public ResultRegistro borrarInterviniente(String id) {
		ResultRegistro result = new ResultRegistro();

		// Obtenermos el interviniente
		IntervinienteRegistroVO interviniente = intervinienteRegistroDao.getInterviniente(id);
		if (null == interviniente) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al recuperar el interviniente");
			return result;
		}

		// Borramos el notario si lo tuviera
		if (null != interviniente.getNotario() && 0 != interviniente.getNotario().getCodigo()) {
			servicioNotarioRegistro.borrarNotario(String.valueOf(interviniente.getNotario().getCodigo()));
		}

		List<IntervinienteRegistroDto> representantes = getRepresentantes(interviniente.getIdInterviniente());
		if (null != representantes && !representantes.isEmpty()) {
			int numRepresentantes = representantes.size();

			for (int i = 0; i < numRepresentantes; i++) {
				borrarRepresentante(String.valueOf(representantes.get(i).getIdInterviniente()));
			}
		}

		// Borramos los datos del registro mercantil si los tuviera
		if (null != interviniente.getDatRegMercantil() && 0 != interviniente.getDatRegMercantil().getIdDatRegMercantil()) {
			result = servicioDatRegMercantil.borrarDatRegMercantil(String.valueOf(interviniente.getDatRegMercantil().getIdDatRegMercantil()));
			if (result.isError()) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return result;
			}
		}

		if (intervinienteRegistroDao.borrar(interviniente))
			result.setMensaje("Interviniente borrado correctamente");
		else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error borrando interviniente");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	@Override
	@Transactional
	public ResultRegistro borrarRepresentante(String id) {
		ResultRegistro result = new ResultRegistro();

		// Obtenermos el representante
		IntervinienteRegistroVO interviniente = intervinienteRegistroDao.getInterviniente(id);
		if (null == interviniente) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al recuperar el representante");
			return result;
		}

		// Borramos el notario si lo tuviera
		if (null != interviniente.getNotario() && 0 != interviniente.getNotario().getCodigo()) {
			servicioNotarioRegistro.borrarNotario(String.valueOf(interviniente.getNotario().getCodigo()));
		}

		if (intervinienteRegistroDao.borrar(interviniente)) {
			result.setMensaje("Representante borrado correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error borrando representante");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	@Override
	@Transactional
	public List<IntervinienteRegistroDto> getRepresentantes(long id) {
		List<IntervinienteRegistroDto> resultado = conversor.transform(intervinienteRegistroDao.getRepresentantes(id), IntervinienteRegistroDto.class);
		if (null != resultado && !resultado.isEmpty()) {
			Collections.sort(resultado, new ComparadorInterviniente());
		}
		return resultado;
	}

	@Override
	@Transactional
	public List<IntervinienteRegistroDto> getRepresentantesPorId(String nif) {
		List<IntervinienteRegistroDto> resultado = conversor.transform(intervinienteRegistroDao.getRepresentantes(Long.parseLong(nif)), IntervinienteRegistroDto.class);
		if (null != resultado && !resultado.isEmpty()) {
			Collections.sort(resultado, new ComparadorInterviniente());
		}
		return resultado;
	}

	@Override
	@Transactional
	public IntervinienteRegistroDto getRepresentante(String identificador) {
		return conversor.transform(intervinienteRegistroDao.getInterviniente(identificador), IntervinienteRegistroDto.class);
	}

	@Override
	@Transactional
	public IntervinienteRegistroDto getInterviniente(String identificador) {
		return conversor.transform(intervinienteRegistroDao.getInterviniente(identificador), IntervinienteRegistroDto.class);
	}

	@Override
	@Transactional
	public IntervinienteRegistroDto getIntervinientePorNifColegiado(IntervinienteRegistroDto intervinienteNif) {
		IntervinienteRegistroDto interviniente = conversor.transform(intervinienteRegistroDao.getIntervinientePorNifColegiadoTipo(conversor.transform(intervinienteNif, IntervinienteRegistroVO.class)),
				IntervinienteRegistroDto.class);
		if (interviniente != null)
			interviniente.setDireccion(servicioPersona.getDireccionActiva(interviniente.getNif(), interviniente.getNumColegiado()));
		return interviniente;
	}

	@Override
	@Transactional
	public IntervinienteRegistroDto getIntervinienteTramiteRegRbmNif(TramiteRegRbmDto tramiteRegRbmDto, String nif) {
		return conversor.transform(intervinienteRegistroDao.getIntervinienteTramiteRegRbmNif(conversor.transform(tramiteRegRbmDto, TramiteRegRbmVO.class), nif), IntervinienteRegistroDto.class);
	}

	@Override
	@Transactional
	public List<IntervinienteRegistroDto> getIntervinientesTramiteRegRbmTipo(BigDecimal idTramiteRegRbm, String tipo) {
		return conversor.transform(intervinienteRegistroDao.getIntervinientesTramiteRegRbmTipo(idTramiteRegRbm, tipo), IntervinienteRegistroDto.class);
	}

	public ResultRegistro validarInterviniente(IntervinienteRegistroDto intervinienteRegistro, String tipoTramite) {
		ResultRegistro result = new ResultRegistro();

		if (StringUtils.isBlank(intervinienteRegistro.getTipoPersona())) {
			result.setError(Boolean.TRUE);
			result.addValidacion(" El tipo de persona es obligatorio.");
		}

		if (StringUtils.isBlank(intervinienteRegistro.getNif())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El DNI / NIE / NIF es obligatorio.");
		} else {
			int tipo = NIFValidator.isValidDniNieCif(intervinienteRegistro.getNif().toUpperCase());
			if (tipo < 0) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El DNI / NIE / NIF no tiene el formato correcto.");
				return result;
			}
		}

		if (StringUtils.isNotBlank(intervinienteRegistro.getTipoPersona()) && (TipoPersonaRegistro.Juridica.getValorXML().equals(intervinienteRegistro.getTipoPersona())
				|| TipoPersonaRegistro.Compania_Publica.getValorXML().equals(intervinienteRegistro.getTipoPersona())) && (StringUtils.isBlank(intervinienteRegistro.getPersona()
						.getApellido1RazonSocial()))) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Si selecciona persona Jurídica o Compañía pública debe rellenar el primer apellido (Razón Social).");
		}

		if (StringUtils.isNotBlank(intervinienteRegistro.getTipoPersona()) && (TipoPersonaRegistro.Fisica.getValorXML().equals(intervinienteRegistro.getTipoPersona()) || TipoPersonaRegistro.Extranjero
				.getValorXML().equals(intervinienteRegistro.getTipoPersona())) && (StringUtils.isBlank(intervinienteRegistro.getPersona().getNombre()) || StringUtils.isBlank(intervinienteRegistro
						.getPersona().getApellido1RazonSocial()))) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Si selecciona persona Física o Extranjero debe rellenar nombre y dos apellidos.");
		}

		// Si el interviniente es de tipo representante el cargo es obligatorio
		if (0 != intervinienteRegistro.getIdRepresentado() && StringUtils.isBlank(intervinienteRegistro.getCargo()) && (!TipoTramiteRegistro.MODELO_10.getValorEnum().equalsIgnoreCase(tipoTramite)
				|| !TipoInterviniente.RepresentanteSolicitante.getValorEnum().equals(intervinienteRegistro.getTipoInterviniente()))) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El cargo del representante es obligatorio.");
		}

		if (!TipoInterviniente.CompradorArrendatario.getValorEnum().equals(intervinienteRegistro.getTipoInterviniente()) && (!TipoTramiteRegistro.MODELO_10.getValorEnum().equalsIgnoreCase(tipoTramite)
				|| !TipoInterviniente.RepresentanteSolicitante.getValorEnum().equals(intervinienteRegistro.getTipoInterviniente()))) {
			// Validaciones de dirección
			comprobarDireccionCorrecta(intervinienteRegistro, result, tipoTramite);
		}

		if (!TipoTramiteRegistro.MODELO_10.getValorEnum().equalsIgnoreCase(tipoTramite)) {
			// Validaciones de los datos registro mercantil
			ResultRegistro resultDatRegMercantil;
			resultDatRegMercantil = servicioDatRegMercantil.validarDatRegMercantil(intervinienteRegistro.getDatRegMercantil());
			if (resultDatRegMercantil.isError()) {
				result.setError(Boolean.TRUE);
				for (String validacion : resultDatRegMercantil.getValidaciones()) {
					result.addValidacion(validacion);
				}
			}
		}

		if (TipoTramiteRegistro.MODELO_10.getValorEnum().equalsIgnoreCase(tipoTramite) && TipoInterviniente.RepresentanteSolicitante.getValorEnum().equals(intervinienteRegistro
				.getTipoInterviniente())) {
			// Validaciones de los datos registro mercantil
			ResultRegistro resultDatRegMercantil;
			resultDatRegMercantil = servicioDatRegMercantil.validarDatRegMercantilCancelacion(intervinienteRegistro.getDatRegMercantil());
			if (resultDatRegMercantil.isError()) {
				result.setError(Boolean.TRUE);
				for (String validacion : resultDatRegMercantil.getValidaciones()) {
					result.addValidacion(validacion);
				}
			}
		}

		return result;
	}

	private boolean comprobarDireccionCorrecta(IntervinienteRegistroDto interviniente, ResultRegistro result, String tipoTramite) {

		DireccionDto direccion = interviniente.getDireccion();

		if (StringUtils.isBlank(direccion.getPais())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El país de la dirección es obligatorio.");
		}

		if ((!TipoTramiteRegistro.MODELO_10.getValorEnum().equalsIgnoreCase(tipoTramite) || !TipoInterviniente.Solicitante.getValorEnum().equals(interviniente.getTipoInterviniente())) && StringUtils
				.isNotBlank(direccion.getPais()) && !direccion.getPais().equals("ES") && StringUtils.isBlank(direccion.getRegionExtranjera())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Si el país de la dirección es distinto de España la región es obligatoria.");
		}

		if (StringUtils.isBlank(direccion.getIdProvincia())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Provincia de la dirección es obligatorio.");
		}

		if (StringUtils.isBlank(direccion.getIdMunicipio())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Municipio de la dirección es obligatorio.");
		}

		if (StringUtils.isBlank(direccion.getCodPostal())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El código postal de la dirección es obligatorio.");
		} else if (!UtilesValidaciones.validarCodigoPostal(direccion.getCodPostal())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El código postal de la dirección no tiene el formato correcto.");
		}

		if (StringUtils.isBlank(direccion.getIdTipoVia())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Tipo de vía de la dirección es obligatorio.");
		}

		if (StringUtils.isBlank(direccion.getNombreVia())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Nombre calle de la dirección es obligatorio.");
		}

		if (StringUtils.isBlank(direccion.getNumero())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Número calle de la dirección es obligatorio.");
		} else if (!UtilesValidaciones.validarNumero(direccion.getNumero())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Número calle de la dirección no tiene el formato correcto.");
		}

		return true;
	}

	private ResultBean validarIntervinienteSociedad(PersonaDto destinatario) {
		ResultBean result = new ResultBean();

		if (StringUtils.isBlank(destinatario.getNif())) {
			result.addMensajeALista("El NIF/CIF es obligatorio.");
			result.setError(Boolean.TRUE);
			return result;
		} else {
			int tipo = NIFValidator.isValidDniNieCif(destinatario.getNif().toUpperCase());
			if (tipo < 0) {
				result.addMensajeALista("El NIF/CIF no tiene el formato correcto.");
				result.setError(Boolean.TRUE);
				return result;
			} else if (tipo == 0 && (StringUtils.isBlank(destinatario.getNombre()) || (StringUtils.isBlank(destinatario.getApellido1RazonSocial())) || (StringUtils.isBlank(destinatario
					.getApellido2())))) {
				result.addMensajeALista("Si el cliente o destinatario es una persona física deberá indicar nombre y dos apellidos.");
				result.setError(Boolean.TRUE);
			}
		}

		if (null != destinatario.getDireccionDto()) {
			if (StringUtils.isBlank(destinatario.getDireccionDto().getIdProvincia()) || "-1".equals(destinatario.getDireccionDto().getIdProvincia())) {
				result.addMensajeALista("La provincia del cliente o destinatario es obligatoria.");
				result.setError(Boolean.TRUE);
			}

			if (StringUtils.isBlank(destinatario.getDireccionDto().getIdMunicipio()) || "-1".equals(destinatario.getDireccionDto().getIdMunicipio())) {
				result.addMensajeALista("El municipio del cliente o destinatario es obligatorio.");
				result.setError(Boolean.TRUE);
			}

			if (StringUtils.isBlank(destinatario.getDireccionDto().getIdTipoVia())) {
				result.addMensajeALista("El tipo de vía del cliente o destinatario es obligatorio.");
				result.setError(Boolean.TRUE);
			}

			if (StringUtils.isBlank(destinatario.getDireccionDto().getNombreVia())) {
				result.addMensajeALista("El nombre de la vía pública del cliente o destinatario es obligatorio.");
				result.setError(Boolean.TRUE);
			}

			// if (StringUtils.isBlank(destinatario.getDireccionDto().getNumero())) {
			// result.addMensajeALista("El número de la vía pública del cliente o destinatario es obligatorio.");
			// result.setError(Boolean.TRUE);
			// }

			if (!UtilesValidaciones.validarCodigoPostal(destinatario.getDireccionDto().getCodPostal())) {
				result.addMensajeALista("El código postal del cliente o destinatario no tiene el formato correcto.");
				result.setError(Boolean.TRUE);
			}
		}

		return result;
	}

	private class ComparadorInterviniente implements Comparator<IntervinienteRegistroDto> {

		@Override
		public int compare(IntervinienteRegistroDto o1, IntervinienteRegistroDto o2) {
			return ((Long) o1.getIdInterviniente()).compareTo(o2.getIdInterviniente());
		}

	}

	@Override
	@Transactional
	public boolean existenIntervinientesPorIdDireccion(String nif, String numColegiado, Long idDireccion) {

		List<IntervinienteRegistroVO> lista = intervinienteRegistroDao.getIntervinientesPorDireccion(nif, numColegiado, idDireccion);
		return (lista != null && !lista.isEmpty());

	}

}
