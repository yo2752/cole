package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.registradores.model.dao.IntervinienteRegistroDao;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.vo.IntervinienteRegistroVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioEvolucionPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioDatRegMercantil;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioFinanciador;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioIntervinienteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioNotarioRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.IntervinienteRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegRbmDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import escrituras.beans.ResultBean;
import utilidades.validaciones.NIFValidator;

@Service
public class ServicioFinanciadorImpl implements ServicioFinanciador {

	private static final long serialVersionUID = 3495913289619060926L;

	public static final String ID_DIRECCION_DESTINATARIO = "IDDIRECCIONDESTINATARIO";

	public static final String INTERVINIENTE_GUARDADO = "INTERVINIENTEGUARDADO";

	@Autowired
	private IntervinienteRegistroDao intervinienteRegistroDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	private ServicioIntervinienteRegistro servicioIntervinienteRegistro;

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

	public ServicioFinanciadorImpl() {}

	@Override
	@Transactional
	public ResultRegistro guardarFinanciador(IntervinienteRegistroDto financiador, BigDecimal idUsuario) {

		ResultBean result = new ResultBean();
		ResultRegistro resultReg;
		long idDatRegMercantil = 0;
		long codigoNotario = 0;

		// Nuevo Interviniente
		resultReg = servicioIntervinienteRegistro.validarInterviniente(financiador, "");
		if (resultReg.isError()) {
			return resultReg;
		}

		UsuarioDto usuario = servicioUsuario.getUsuarioDto(idUsuario);
		ResultBean resultPersona = servicioPersona.guardarActualizar(conversor.transform(financiador.getPersona(), PersonaVO.class), null, null, usuario, null);
		boolean direccionNueva = false;
		// Si tiene notario se guarda
		if (null != financiador.getNotario() && StringUtils.isNotBlank(financiador.getNotario().getNif())) {
			resultReg = servicioNotarioRegistro.guardarOActualizarNotario(financiador);
			if (resultReg.isError())
				return resultReg;

			codigoNotario = (long) resultReg.getObj();
			if (0 != codigoNotario) {
				financiador.getNotario().setCodigo(codigoNotario);
			} else {
				financiador.setNotario(null);
			}
		} else if (null != financiador.getNotario() && 0 != financiador.getNotario().getCodigo()) {
			servicioNotarioRegistro.borrarNotario(String.valueOf(financiador.getNotario().getCodigo()));
		} else {
			financiador.setNotario(null);
		}
		if (null != financiador.getDatRegMercantil() && null != financiador.getDatRegMercantil().getCodRegistroMercantil()) {
			resultReg = servicioDatRegMercantil.guardarOActualizarDatRegMercantil(financiador.getDatRegMercantil());
			if (resultReg.isError())
				return resultReg;
			idDatRegMercantil = (long) resultReg.getObj();
		}

		if (0 != idDatRegMercantil) {
			financiador.getDatRegMercantil().setIdDatRegMercantil(idDatRegMercantil);
		} else {
			financiador.setDatRegMercantil(null);
		}

		if (!resultPersona.getError() && !result.getError()) {
			// Guardar direccion
			if (financiador.getPersona().getDireccionDto() != null && utiles.convertirCombo(financiador.getPersona().getDireccionDto().getIdProvincia()) != null) {

				DireccionVO direccion = conversor.transform(financiador.getPersona().getDireccionDto(), DireccionVO.class);

				ResultBean resultDireccion = servicioDireccion.guardarActualizarPersona(direccion, financiador.getPersona().getNif(), financiador.getPersona().getNumColegiado(),
						TipoTramiteRegistro.MODELO_7.getValorEnum(), ServicioDireccion.CONVERSION_DIRECCION_REGISTRO);
				if (resultDireccion != null && !resultDireccion.getError()) {
					direccion = (DireccionVO) resultDireccion.getAttachment(ServicioDireccion.DIRECCION);
					direccionNueva = (Boolean) resultDireccion.getAttachment(ServicioDireccion.NUEVA_DIRECCION);
					servicioEvolucionPersona.guardarEvolucionPersonaDireccion(financiador.getPersona().getNif(), null, null, financiador.getPersona().getNumColegiado(), usuario, direccionNueva);
					financiador.setIdDireccion(direccion.getIdDireccion());
					IntervinienteRegistroVO intervinienteGuardado = intervinienteRegistroDao.guardarOActualizar(conversor.transform(financiador, IntervinienteRegistroVO.class));
					resultReg.setObj(conversor.transform(intervinienteGuardado, IntervinienteRegistroDto.class));
					if (intervinienteGuardado != null) {
						resultReg.setMensaje("Financiador guardado correctamente");
					} else {
						resultReg.setError(Boolean.TRUE);
						resultReg.setMensaje("Error guardando financiador");
					}
				} else {
					if (null != resultDireccion) {
						resultReg.setError(Boolean.TRUE);
						resultReg.setMensaje(resultDireccion.getListaMensajes().get(0));
					}
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				}
			} else {
				resultReg.setError(Boolean.TRUE);
				resultReg.setMensaje("Debe seleccionar una provincia");
			}
		} else {
			resultReg.setError(Boolean.TRUE);
			resultReg.setMensaje(resultPersona.getListaMensajes().get(0));
		}

		if (resultReg.isError())
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		return resultReg;
	}

	@Override
	@Transactional
	public ResultBean borrarFinanciador(String id) {
		ResultBean result = new ResultBean();
		// buscar si el financiador tiene algun tramite, si lo tiene no se borra
		if (intervinienteRegistroDao.hasTramites(id)) {
			result.addError("Error borrando financiador: El financiador tiene trámites asociados");
		} else {
			if (intervinienteRegistroDao.borrar(intervinienteRegistroDao.getInterviniente(id)))
				result.addMensajeALista("Financiador borrado correctamente");
			else
				result.addError("Error borrando financiador");
		}
		return result;
	}

	@Override
	@Transactional
	public ResultBean borrarRepresentante(String id) {
		ResultBean result = new ResultBean();
		if (intervinienteRegistroDao.borrar(intervinienteRegistroDao.getInterviniente(id)))
			result.addMensajeALista("Representante borrado correctamente");
		else
			result.addError("Error borrando representante");

		return result;
	}

	@Override
	@Transactional
	public List<IntervinienteRegistroDto> getRepresentantes(long id) {
		List<IntervinienteRegistroDto> listaRepresentantes = new ArrayList<>();
		if (id != 0)
			listaRepresentantes = conversor.transform(intervinienteRegistroDao.getRepresentantes(id), IntervinienteRegistroDto.class);
		return listaRepresentantes;
	}

	@Override
	@Transactional
	public IntervinienteRegistroDto getRepresentante(String identificador) {
		return conversor.transform(intervinienteRegistroDao.getInterviniente(identificador), IntervinienteRegistroDto.class);
	}

	@Override
	@Transactional
	public List<IntervinienteRegistroDto> getFinanciadores() {
		return conversor.transform(intervinienteRegistroDao.getFinanciadores(), IntervinienteRegistroDto.class);
	}

	@Override
	@Transactional
	public IntervinienteRegistroDto getFinanciador(String identificador) {
		return conversor.transform(intervinienteRegistroDao.getInterviniente(identificador), IntervinienteRegistroDto.class);
	}

	@Override
	@Transactional
	public IntervinienteRegistroDto getFinanciadorPorNifColegiado(IntervinienteRegistroDto financiadorNif) {
		IntervinienteRegistroDto financiador = conversor.transform(intervinienteRegistroDao.getIntervinientePorNifColegiadoTipo(conversor.transform(financiadorNif, IntervinienteRegistroVO.class)),
				IntervinienteRegistroDto.class);
		if (financiador != null)
			financiador.setDireccion(servicioPersona.getDireccionActiva(financiador.getNif(), financiador.getNumColegiado()));
		return financiador;
	}

	public ResultBean validarInterviniente(IntervinienteRegistroDto intervinienteRegistro) {
		ResultBean resultRegistro = new ResultBean();

		// Validaciones de persona
		if ((intervinienteRegistro.getTipoPersona() == null) || (intervinienteRegistro.getTipoPersona().isEmpty())) {
			resultRegistro.addError("Debe seleccionar un tipo de persona");
			return resultRegistro;
		}

		int tipo = NIFValidator.isValidDniNieCif(intervinienteRegistro.getNif().toUpperCase());
		if (tipo < 0) {
			resultRegistro.addError("El DNI / NIE / NIF no es válido");
			return resultRegistro;
		}

		if (null == intervinienteRegistro.getPersona().getApellido1RazonSocial() || "".equals(intervinienteRegistro.getPersona().getApellido1RazonSocial())) {
			resultRegistro.addError("Primer apellido o razón social debe ir relleno");
			return resultRegistro;
		}

		// Validaciones de dirección
		comprobarDireccionCorrecta(intervinienteRegistro.getDireccion(), resultRegistro);

		return resultRegistro;
	}

	private boolean comprobarDireccionCorrecta(DireccionDto direccion, ResultBean resultRegistro) {
		if (utiles.convertirCombo(direccion.getIdProvincia()) == null) {
			resultRegistro.addError("Provincia debe ir relleno");
			return false;
		} else if (utiles.convertirCombo(direccion.getIdMunicipio()) == null) {
			resultRegistro.addError("Municipio debe ir relleno");
			return false;
		} else if (utiles.convertirCombo(direccion.getIdTipoVia()) == null) {
			resultRegistro.addError("Tipo vía debe ir relleno");
			return false;
		} else if (utiles.convertirNulltoString(direccion.getNombreVia()) == null) {
			resultRegistro.addError("Nombre vía debe ir relleno");
			return false;
		} else if (utiles.convertirNulltoString(direccion.getNumero()) == null) {
			resultRegistro.addError("Núm. calle debe ir relleno");
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public List<IntervinienteRegistroDto> getFinanciadoresColegiado(String numColegiado) {
		return conversor.transform(intervinienteRegistroDao.getFinanciadoresColegiado(numColegiado), IntervinienteRegistroDto.class);
	}

	@Override
	@Transactional
	public List<TramiteRegRbmDto> getTramites(long idInterviniente) {
		return conversor.transform(intervinienteRegistroDao.getTramitesInterviniente(idInterviniente), TramiteRegRbmDto.class);
	}
}
