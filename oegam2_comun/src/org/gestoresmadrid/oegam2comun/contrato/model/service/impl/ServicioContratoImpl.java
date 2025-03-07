package org.gestoresmadrid.oegam2comun.contrato.model.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.contrato.model.dao.ContratoDao;
import org.gestoresmadrid.core.contrato.model.enumerados.EstadoContrato;
import org.gestoresmadrid.core.contrato.model.enumerados.EstadoUsuario;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.dao.ColegiadoDao;
import org.gestoresmadrid.core.general.model.dao.ContratoColegiadoDao;
import org.gestoresmadrid.core.general.model.dao.ContratoUsuarioDao;
import org.gestoresmadrid.core.general.model.dao.UsuarioDao;
import org.gestoresmadrid.core.general.model.vo.AplicacionVO;
import org.gestoresmadrid.core.general.model.vo.ColegiadoVO;
import org.gestoresmadrid.core.general.model.vo.ContratoColegiadoPK;
import org.gestoresmadrid.core.general.model.vo.ContratoColegiadoVO;
import org.gestoresmadrid.core.general.model.vo.ContratoUsuarioPK;
import org.gestoresmadrid.core.general.model.vo.ContratoUsuarioVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.core.personas.model.vo.PersonaPK;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioCorreoContratoTramite;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioProvincia;
import org.gestoresmadrid.oegam2comun.model.service.ServicioAplicacion;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegamComun.accesos.view.dto.AplicacionDto;
import org.gestoresmadrid.oegamComun.colegiado.services.ServicioColegiado;
import org.gestoresmadrid.oegamComun.contrato.service.ServicioEvolucionContrato;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.direccion.view.dto.MunicipioDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.ProvinciaDto;
import org.gestoresmadrid.oegamComun.evolucionUsuario.service.ServicioEvolucionUsuario;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import escrituras.beans.contratos.ContratoBean;
import general.utiles.Anagrama;
import trafico.beans.utiles.CampoPdfBean;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.PdfMaker;
import utilidades.estructuras.Fecha;
import utilidades.estructuras.FechaFraccionada;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.Contrato;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Service
public class ServicioContratoImpl implements ServicioContrato {

	private static final long serialVersionUID = -8366257641662706305L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioContratoImpl.class);

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioAplicacion servicioAplicacion;

	@Autowired
	private ServicioUsuario servicioUsuario;

	@Autowired
	private ServicioColegiado servicioColegiado;

	@Autowired
	private ContratoDao contratoDao;

	@Autowired
	private ColegiadoDao colegiadoDao;

	@Autowired
	private ContratoColegiadoDao contratoColegiadoDao;

	@Autowired
	private ContratoUsuarioDao contratoUsuarioDao;

	@Autowired
	private UsuarioDao usuarioDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	private ServicioEvolucionContrato servicioEvolucionContrato;

	@Autowired
	private ServicioEvolucionUsuario servicioEvolucionUsuario;

	@Autowired
	ServicioTramiteTraficoMatriculacion servicioTramiteTrafMatriculacion;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioProvincia servicioProvincia;

	@Autowired
	ServicioMunicipio servicioMunicipio;
	
	@Autowired
	ServicioCorreoContratoTramite servicioCorreoContratoTramite;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public ContratoVO getContrato(BigDecimal idContrato) {
		try {
			return contratoDao.getContrato(idContrato.longValue(), false);
		} catch (Exception e) {
			log.error("Error al obtener el contrato: " + idContrato, e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public ContratoDto getContratoDto(BigDecimal idContrato) {
		try {
			ContratoVO contrato = getContrato(idContrato);
			if (contrato != null) {
				return conversor.transform(contrato, ContratoDto.class);
			}
		} catch (Exception e) {
			log.error("Error al obtener el contrato: " + idContrato, e);
		}
		return null;
	}

	@Override
	@Transactional
	public ContratoDto detalleContrato(BigDecimal idContrato) {
		try {
			String GestionarUsuariosPorContrato = gestorPropiedades.valorPropertie("gestionar.usuarios.contrato");

			ContratoVO contrato = contratoDao.getContrato(idContrato.longValue(), true);
			if (contrato != null) {
				ContratoDto contratoDto = conversor.transform(contrato, ContratoDto.class);
				// Obtenemos la persona asociada al contrato
				if (contratoDto.getColegiadoDto() != null && contratoDto.getColegiadoDto().getUsuario() != null) {
					PersonaDto personaDto = servicioPersona.getPersona(contratoDto.getColegiadoDto().getUsuario().getNif(), contratoDto.getColegiadoDto().getNumColegiado());
					contratoDto.getColegiadoDto().getUsuario().setApellido1(personaDto.getApellido1RazonSocial());
					contratoDto.getColegiadoDto().getUsuario().setApellido2(personaDto.getApellido2());
					contratoDto.getColegiadoDto().getUsuario().setNombre(personaDto.getNombre());
					contratoDto.setNcorpme(personaDto.getNcorpme());
					contratoDto.setUsuarioCorpme(personaDto.getUsuarioCorpme());
					contratoDto.setPasswordCorpme(personaDto.getPasswordCorpme());
				}
				obtenerAplicaciones(contratoDto);

				obtenerUsuariosColegiado(contratoDto);

				if (GestionarUsuariosPorContrato != null && "SI".equals(GestionarUsuariosPorContrato)) {
					obtenerUsuarios(contratoDto);
					obtenerUsuariosHabilitadosColegiado(contratoDto);
				}

				return contratoDto;
			}
		} catch (Exception e) {
			log.error("Error al obtener el contrato: " + idContrato, e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResultBean guardarContrato(ContratoDto contrato, BigDecimal idUsuario) {
		ResultBean respuesta = new ResultBean();
		try {
			ContratoVO contratoVO = convertirDTOVO(contrato);
			respuesta = guardar(contratoVO, idUsuario);
			Boolean nuevoContrato = (Boolean) respuesta.getAttachment(NUEVO_CONTRATO);
			Long idContrato = (Long) respuesta.getAttachment(ID_CONTRATO);
			
			if (respuesta.getError()) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return respuesta;
			}
			
			UsuarioDto usuarioSesion = servicioUsuario.getUsuarioDto(idUsuario);
			respuesta = guardarColegiado(contratoVO.getColegiado(), contrato, usuarioSesion);
			if (respuesta != null && respuesta.getError()) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return respuesta;
			}
			
			if (nuevoContrato) {
				ContratoColegiadoVO contratoColegiadoVO = new ContratoColegiadoVO();
				ContratoColegiadoPK id = new ContratoColegiadoPK();
				id.setNumColegiado(contratoVO.getColegiado().getNumColegiado());
				id.setIdContrato(idContrato);
				contratoColegiadoVO.setId(id);
				contrato.setIdContrato(new BigDecimal(idContrato));
			}
			
			
			if (contrato.getCorreoContratoTramite() != null && StringUtils.isNotBlank(contrato.getCorreoContratoTramite().getCorreoElectronico())) {
				contrato.getCorreoContratoTramite().setIdContrato(contrato.getIdContrato().longValue());
				respuesta = servicioCorreoContratoTramite.guardarOActualizarCorreoContratoTramite(contrato.getCorreoContratoTramite());
				if (respuesta != null && respuesta.getError()) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return respuesta;
				} 
			}
			
			respuesta.addAttachment(NUEVO_CONTRATO, nuevoContrato);
			
		} catch (Exception e) {
			log.error("Error al guardar el contrato. Mensaje: " + e.getMessage(), e);
			respuesta.setError(true);
			respuesta.addMensajeALista(e.getMessage());
		}
		
		return respuesta;
	}

	@Override
	@Transactional
	public ResultBean guardarCorpme(String numColegiado, String nif, String apellido1RazonSocial, String usuarioCorpme, String passwordCorpme, String nCorpme, BigDecimal idUsuario) {
		ResultBean respuesta = new ResultBean();
		try {
			respuesta = guardarPersonaCorpme(nif, numColegiado, apellido1RazonSocial, usuarioCorpme, passwordCorpme, nCorpme, idUsuario);
		} catch (Exception e) {
			log.error("Error al guardar el contrato. Mensaje: " + e.getMessage(), e);
			respuesta.setError(true);
			respuesta.addMensajeALista(e.getMessage());
		}
		return respuesta;
	}

	private ResultBean guardar(ContratoVO contratoVO, BigDecimal idUsuario) {
		ResultBean result = new ResultBean();
		try {
			if (contratoVO.getAnagramaContrato() == null || contratoVO.getAnagramaContrato().isEmpty()) {
				String anagramaContrato = Anagrama.obtenerAnagramaFiscal(contratoVO.getRazonSocial().toUpperCase(), contratoVO.getCif().toUpperCase());
				contratoVO.setAnagramaContrato(anagramaContrato);
			}
			modificarDatosMayusculas(contratoVO);
			if (contratoVO.getIdContrato() == null) {
				result = comprobarMismaVia(contratoVO);
				if (result != null && result.getError()) {
					return result;
				}
				contratoVO.setFechaInicio(new Date());
				contratoVO.setEstadoContrato(new BigDecimal(EstadoUsuario.Habilitado.getValorEnum()));
				contratoVO.setIdTipoContrato(new BigDecimal(1));

				Long idContrato = (Long) contratoDao.guardar(contratoVO);
				result.addAttachment(ID_CONTRATO, idContrato);
				result.addAttachment(NUEVO_CONTRATO, Boolean.TRUE);

				servicioEvolucionContrato.guardarEvolucionContrato(idContrato, idUsuario, null, null, new Date(), TipoActualizacion.CRE, new BigDecimal(EstadoContrato.Habilitado.getValorEnum()),
						BigDecimal.ZERO);

			} else {
				contratoVO = contratoDao.actualizar(contratoVO);
				result.addAttachment(NUEVO_CONTRATO, Boolean.FALSE);
			}
		} catch (Exception e) {
			log.error("Error al guardar el contrato. Mensaje: " + e.getMessage(), e);
			result.setMensaje(e.getMessage());
			result.setError(true);
		}
		return result;
	}

	public void guardarEvolucionUsuario(BigDecimal idUsuario, Timestamp fecha, Long idContrato, TipoActualizacion tipoActualizacion, BigDecimal estadoNuevo, BigDecimal estadoAnt, Long contratoAnt,
			String nif) {
		// TODO Auto-generated method stub

		servicioEvolucionUsuario.guardarEvolucionUsuario(idUsuario, fecha, idContrato.longValue(), tipoActualizacion, estadoNuevo, estadoAnt, contratoAnt, nif);

	}

	private ResultBean guardarColegiado(ColegiadoVO colegiadoVO, ContratoDto contrato, UsuarioDto usuario) {
		ResultBean result = new ResultBean();
		UsuarioDto persona = contrato.getColegiadoDto().getUsuario();
		try {
			if (colegiadoVO.getUsuario().getIdUsuario() == null) {
				ColegiadoVO colegiadoBBDD = servicioColegiado.getColegiado(colegiadoVO.getNumColegiado());
				if (colegiadoBBDD != null && colegiadoBBDD.getUsuario() != null) {
					try {
						colegiadoVO.getUsuario().setIdUsuario(colegiadoBBDD.getUsuario().getIdUsuario());
						colegiadoVO.getUsuario().setEstadoUsuario(colegiadoBBDD.getUsuario().getEstadoUsuario());
						colegiadoVO.getUsuario().setNif(colegiadoBBDD.getUsuario().getNif());
						colegiadoVO.getUsuario().setAnagrama(colegiadoBBDD.getUsuario().getAnagrama());
						colegiadoVO.getUsuario().setFechaRenovacion(colegiadoBBDD.getUsuario().getFechaRenovacion());
						colegiadoVO.getUsuario().setCorreoElectronico(colegiadoBBDD.getUsuario().getCorreoElectronico());
						if (colegiadoVO.getAlias() == null || colegiadoVO.getAlias().isEmpty()) {
							colegiadoVO.setAlias(colegiadoBBDD.getAlias());
						}
					} catch (Exception e) {
						log.error("Error al obtener colegidado. Mensaje: " + e.getMessage(), e);
					} finally {
						usuarioDao.evict(colegiadoBBDD.getUsuario());
					}
				}
			}
			colegiadoDao.guardarOActualizar(colegiadoVO);
			result = guardarUsuario(colegiadoVO.getUsuario());
			if (result != null && !result.getError()) {
				if (colegiadoVO.getIdUsuario() == null) {
					Long idUsuarioNuevo = (Long) result.getAttachment(ServicioUsuario.ID_USUARIO);
					colegiadoVO.setIdUsuario(idUsuarioNuevo);
					colegiadoDao.guardarOActualizar(colegiadoVO);
				}
				if (result != null && !result.getError()) {
					result = guardarPersona(colegiadoVO.getUsuario(), persona, colegiadoVO.getNumColegiado(), usuario, contrato.getNcorpme(), contrato.getUsuarioCorpme(), contrato
							.getPasswordCorpme());
				}
			}
		} catch (Exception e) {
			log.error("Error al guardar el colegidado. Mensaje: " + e.getMessage(), e);
			result.setMensaje(e.getMessage());
			result.setError(true);
		}
		return result;
	}
	
	private ResultBean guardarUsuario(UsuarioVO usuario) {
		ResultBean result = new ResultBean();
		try {
			result = servicioUsuario.guardar(usuario);
		} catch (Exception e) {
			log.error("Error al guardar el usuario. Mensaje: " + e.getMessage(), e);
			result.setMensaje(e.getMessage());
			result.setError(true);
		}
		return result;
	}

	private ResultBean guardarPersona(UsuarioVO usuario, UsuarioDto persona, String numColegiado, UsuarioDto usuarioSesion, String nCorpme, String usuarioCorpme, String passwordCorpme) {
		ResultBean result = new ResultBean();
		try {

			PersonaVO personaVO = new PersonaVO();
			PersonaPK id = new PersonaPK();
			id.setNif(usuario.getNif());
			id.setNumColegiado(numColegiado);
			personaVO.setId(id);
			personaVO.setApellido1RazonSocial(persona.getApellido1());
			personaVO.setApellido2(persona.getApellido2());
			personaVO.setNombre(persona.getNombre());
			personaVO.setNcorpme(nCorpme);
			personaVO.setUsuarioCorpme(usuarioCorpme);
			personaVO.setPasswordCorpme(passwordCorpme);

			result = servicioPersona.guardarActualizar(personaVO, null, ServicioPersona.TIPO_TRAMITE_CONTRATO, usuarioSesion, ServicioPersona.CONVERSION_PERSONA_CONTRATO);
		} catch (Exception e) {
			log.error("Error al guardar la persona. Mensaje: " + e.getMessage(), e);
			result.setMensaje(e.getMessage());
			result.setError(true);
		}
		return result;
	}

	private ResultBean guardarPersonaCorpme(String nif, String numColegiado, String apellido1RazonSocial, String usuarioCorpme, String passwordCorpme, String nCorpme, BigDecimal idUsuario) {
		ResultBean result = new ResultBean();
		try {
			PersonaVO personaVO = new PersonaVO();
			PersonaPK id = new PersonaPK();
			id.setNif(nif);
			id.setNumColegiado(numColegiado);
			personaVO.setId(id);
			personaVO.setUsuarioCorpme(usuarioCorpme);
			personaVO.setPasswordCorpme(passwordCorpme);
			personaVO.setNcorpme(nCorpme);
			personaVO.setApellido1RazonSocial(apellido1RazonSocial);
			UsuarioDto usuario = new UsuarioDto();
			usuario.setIdUsuario(idUsuario);
			result = servicioPersona.guardarActualizar(personaVO, null, ServicioPersona.TIPO_TRAMITE_CONTRATO, usuario, ServicioPersona.CONVERSION_PERSONA_CORPME);
		} catch (Exception e) {
			log.error("Error al guardar la persona. Mensaje: " + e.getMessage(), e);
			result.setMensaje(e.getMessage());
			result.setError(true);
		}
		return result;
	}

	private ContratoVO convertirDTOVO(ContratoDto contratoDto) {
		if (contratoDto.getColegiadoDto() != null && contratoDto.getColegiadoDto().getUsuario() != null) {
			String apellidosNombre = "";
			if (contratoDto.getColegiadoDto().getUsuario().getApellido1() != null) {
				apellidosNombre += contratoDto.getColegiadoDto().getUsuario().getApellido1() + " ";
			}

			if (contratoDto.getColegiadoDto().getUsuario().getApellido2() != null) {
				apellidosNombre += contratoDto.getColegiadoDto().getUsuario().getApellido2();
			}
			if (contratoDto.getColegiadoDto().getUsuario().getNombre() != null) {
				apellidosNombre += ", " + contratoDto.getColegiadoDto().getUsuario().getNombre();
			}
			contratoDto.getColegiadoDto().getUsuario().setApellidosNombre(apellidosNombre);
			contratoDto.getColegiadoDto().getUsuario().setNumColegiado(contratoDto.getColegiadoDto().getNumColegiado());
			contratoDto.getColegiadoDto().getUsuario().setNumColegiadoNacional(contratoDto.getColegiadoDto().getNumColegiadoNacional());
		}

		return conversor.transform(contratoDto, ContratoVO.class);
	}

	private void modificarDatosMayusculas(ContratoVO contratoVO) {
		if (contratoVO.getAnagramaContrato() != null) {
			contratoVO.setAnagramaContrato(contratoVO.getAnagramaContrato().toUpperCase());
		}
		if (contratoVO.getCif() != null) {
			contratoVO.setCif(contratoVO.getCif().toUpperCase());
		}
		if (contratoVO.getRazonSocial() != null) {
			contratoVO.setRazonSocial(contratoVO.getRazonSocial().toUpperCase());
		}
		if (contratoVO.getVia() != null) {
			contratoVO.setVia(contratoVO.getVia().toUpperCase());
		}
		if (contratoVO.getLetra() != null) {
			contratoVO.setLetra(contratoVO.getLetra().toUpperCase());
		}
		if (contratoVO.getEscalera() != null) {
			contratoVO.setEscalera(contratoVO.getEscalera().toUpperCase());
		}
		if (contratoVO.getPuerta() != null) {
			contratoVO.setPuerta(contratoVO.getPuerta().toUpperCase());
		}
	}

	private void obtenerAplicaciones(ContratoDto contratoDto) {
		List<AplicacionDto> aplicaciones = servicioAplicacion.getAplicaciones();
		List<AplicacionDto> aplicacionesFinales = new ArrayList<AplicacionDto>();

		if (aplicaciones != null && !aplicaciones.isEmpty()) {
			if (contratoDto.getAplicacionesDto() != null && !contratoDto.getAplicacionesDto().isEmpty()) {
				for (AplicacionDto aplicacion : aplicaciones) {
					for (AplicacionDto aplicacionAsociada : contratoDto.getAplicacionesDto()) {
						if (aplicacion.getCodigoAplicacion().equals(aplicacionAsociada.getCodigoAplicacion())) {
							aplicacion.setAsignada(true);
						}
					}
					aplicacionesFinales.add(aplicacion);
				}
			} else {
				aplicacionesFinales = aplicaciones;
			}
		}
		contratoDto.setAplicacionesDto(aplicacionesFinales);
	}

	private void obtenerUsuarios(ContratoDto contratoDto) {
		List<UsuarioDto> usuariosFinales = new ArrayList<UsuarioDto>();

		List<UsuarioDto> usuariosContrato = servicioUsuario.getUsuariosPorContrato(contratoDto.getIdContrato().longValue());
		for (UsuarioDto usuario : usuariosContrato) {
			// Eliminamos el del propio colegiado
			if (contratoDto.getColegiadoDto().getUsuario() != null && !usuario.getIdUsuario().equals(contratoDto.getColegiadoDto().getUsuario().getIdUsuario())) {
				usuariosFinales.add(usuario);
			}
		}
		contratoDto.setUsuariosContrato(usuariosFinales);
	}

	private void obtenerUsuariosColegiado(ContratoDto contratoDto) {
		List<UsuarioDto> usuariosFinales = new ArrayList<UsuarioDto>();
		List<UsuarioDto> usuarios = servicioUsuario.getUsuarioPorNumColegiado(contratoDto.getColegiadoDto().getNumColegiado());

		for (UsuarioDto usuario : usuarios) {
			// Eliminamos el del propio colegiado
			if (contratoDto.getColegiadoDto().getUsuario() != null && !usuario.getIdUsuario().equals(contratoDto.getColegiadoDto().getUsuario().getIdUsuario())) {
				usuariosFinales.add(usuario);
			}
		}
		contratoDto.setUsuarios(usuariosFinales);
	}

	private void obtenerUsuariosHabilitadosColegiado(ContratoDto contratoDto) {
		List<UsuarioDto> usuariosFinales = new ArrayList<UsuarioDto>();
		List<UsuarioDto> usuarios = servicioUsuario.getUsuariosHabilitadosPorNumColegiado(contratoDto.getColegiadoDto().getNumColegiado());

		for (UsuarioDto usuario : usuarios) {
			// Eliminamos el del propio colegiado
			if (contratoDto.getColegiadoDto().getUsuario() != null && !usuario.getIdUsuario().equals(contratoDto.getColegiadoDto().getUsuario().getIdUsuario())) {
				usuariosFinales.add(usuario);
			}
		}
		contratoDto.setUsuariosHabilitados(usuariosFinales);
	}

	@Override
	@Transactional
	public ResultBean comprobarMismaViaContratos(ContratoBean contratoBean) {
		ResultBean resultado = null;
		try {
			List<ContratoVO> listaConstratos = contratoDao.getContratosPorColegiado(contratoBean.getDatosColegiado().getNumColegiado());
			if (listaConstratos != null && !listaConstratos.isEmpty()) {
				for (ContratoVO contrato : listaConstratos) {
					if (contrato.getIdMunicipio() != null && contrato.getIdMunicipio().equals(contratoBean.getDatosContrato().getDireccion().getMunicipio().getIdMunicipio())) {
						if (contrato.getVia() != null && contrato.getVia().toUpperCase().equals(contratoBean.getDatosContrato().getDireccion().getNombreVia().toUpperCase())) {
							resultado = new ResultBean(true, "No es posible dar de alta más de un contrato con el mismo nombre de vía para un mismo colegiado");
							return resultado;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar si existe algun contrato con el mismo nombre de via, error: ", e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de comprobar si existe un contrato con el mismo nombre de via");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean comprobarMismaVia(ContratoVO contratoVO) {
		ResultBean resultado = new ResultBean();
		try {
			List<ContratoVO> listaConstratos = contratoDao.getContratosPorColegiado(contratoVO.getColegiado().getNumColegiado());
			if (listaConstratos != null && !listaConstratos.isEmpty()) {
				for (ContratoVO contrato : listaConstratos) {
					if (contrato.getIdMunicipio() != null && contrato.getIdMunicipio().equals(contratoVO.getIdMunicipio())) {
						if (contrato.getVia() != null && contrato.getVia().toUpperCase().equals(contratoVO.getVia().toUpperCase())) {
							resultado = new ResultBean(true, "No es posible dar de alta más de un contrato con el mismo nombre de vía para un mismo colegiado");
							return resultado;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar si existe algun contrato con el mismo nombre de via, error: ", e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de comprobar si existe un contrato con el mismo nombre de via");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public List<DatoMaestroBean> getComboContratosHabilitados() {
		return contratoDao.getComboContratosHabilitados();
	}

	@Override
	@Transactional(readOnly = true)
	public List<DatoMaestroBean> getComboContratosHabilitadosColegiado(String numColegiado) {
		return contratoDao.getComboContratosHabilitadosColegiado(numColegiado);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DatoMaestroBean> getComboContratosHabilitadosColegio(BigDecimal idContrato) {
		return contratoDao.getComboContratosHabilitadosColegio(idContrato);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Contrato> getContratosPorUsuario(BigDecimal idUsuario) {
		try {
			if (idUsuario != null) {
				List<ContratoUsuarioVO> listaContratoVOs = contratoUsuarioDao.getContratosPorUsuario(idUsuario);
				List<ContratoUsuarioVO> ListaContUsuario = new ArrayList<ContratoUsuarioVO>();

				if (listaContratoVOs != null && !listaContratoVOs.isEmpty()) {
					List<ContratoColegiadoVO> contratoColegiadoVO = contratoColegiadoDao.getContratoColegiado(listaContratoVOs.get(0).getUsuario().getNumColegiado());
					List<String> codigos = new ArrayList<String>();
					for (ContratoColegiadoVO contrato : contratoColegiadoVO) {
						codigos.add(contrato.getContrato().getIdContrato().toString());
					}
					for (ContratoUsuarioVO elem : listaContratoVOs) {
						if (codigos.contains(String.valueOf(elem.getContrato().getIdContrato()))) {
							ListaContUsuario.add(elem);
						}
					}
					return conversor.transform(ListaContUsuario, Contrato.class);
				}
			} else {
				log.error("No se puede consultar un contrato por el colegiado si este es nulo.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el contrato por el numero de colegiado, error: ");
		}
		return null;

	}

	@Override
	@Transactional(readOnly = true)
	public List<UsuarioDto> getUsuariosPorContrato(Long idContrato) {
		try {
			if (idContrato != null) {
				List<ContratoUsuarioVO> listaUsuariosVO = contratoUsuarioDao.getUsuariosPorContrato(idContrato);
				List<UsuarioDto> ListaUsuarioCont = new ArrayList<UsuarioDto>();

				if (listaUsuariosVO != null && !listaUsuariosVO.isEmpty()) {

					for (ContratoUsuarioVO elem : listaUsuariosVO) {
						ListaUsuarioCont.add(conversor.transform(elem.getUsuario(), UsuarioDto.class));
					}
					return ListaUsuarioCont;
				}
			} else {
				log.error("No se puede consultar un contrato por el colegiado si este es nulo.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el contrato por el numero de colegiado, error: ");
		}
		return null;

	}

	@Override
	@Transactional(readOnly = true)
	public ContratoDto getContratoPorColegiado(String numColegiado) {
		try {
			if (numColegiado != null && !numColegiado.isEmpty()) {
				List<ContratoVO> listaContratoVOs = contratoDao.getContratosPorColegiado(numColegiado);
				if (listaContratoVOs != null && !listaContratoVOs.isEmpty()) {
					return conversor.transform(listaContratoVOs.get(0), ContratoDto.class);
				}
			} else {
				log.error("No se puede consultar un contrato por el colegiado si este es nulo.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el contrato por el numero de colegiado, error: ");
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ContratoDto> getContratosPorColegiado(String numColegiado) {
		try {
			if (numColegiado != null && !numColegiado.isEmpty()) {
				List<ContratoVO> listaContratoVOs = contratoDao.getContratosPorColegiado(numColegiado);
				if (listaContratoVOs != null && !listaContratoVOs.isEmpty()) {
					return conversor.transform(listaContratoVOs, ContratoDto.class);
				}
			} else {
				log.error("No se puede consultar un contrato por el colegiado si este es nulo.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el contrato por el numero de colegiado, error: ");
		}
		return null;
	}

	@Override
	@Transactional
	public List<ContratoDto> getContratosPorAplicacion(String codigoAplicacion) {
		try {
			List<ContratoVO> contratos = contratoDao.getContratosPorAplicacion(codigoAplicacion);
			if (contratos != null && contratos.size() > 0) {
				return conversor.transform(contratos, ContratoDto.class);
			}
		} catch (Exception e) {
			log.error("Error al obtener el contrato con codigo aplicacion: " + codigoAplicacion, e);
		}
		return null;
	}
	
	@Override
	@Transactional
	public List<AplicacionDto> getAplicacionesPorContrato(BigDecimal idContrato) {
		try {
			List<AplicacionVO> aplicaciones = contratoDao.getAplicacionesPorContrato(idContrato);
			if (aplicaciones != null && !aplicaciones.isEmpty()) {
				return conversor.transform(aplicaciones, AplicacionDto.class);
			}
		} catch (Exception e) {
			log.error("Error al obtener las aplicaciones del contrato con codigo: " + idContrato, e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional
	public ResultBean habilitarContrato(BigDecimal idContrato, BigDecimal idUsuario, String motivo, String solicitante) {
		ResultBean resultado = new ResultBean();
		try {
			ContratoVO contratoVO = getContrato(idContrato);
			if (contratoVO != null) {
				if (EstadoContrato.Habilitado.getValorEnum().equals(contratoVO.getEstadoContrato().toString())) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El contrato: " + contratoVO.getRazonSocial() + ", ya se encuentra habilitado.");
				} else if (EstadoContrato.Eliminado.getValorEnum().equals(contratoVO.getEstadoContrato().toString())) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El contrato: " + contratoVO.getRazonSocial() + ", está eliminado, por lo que no se puede habilitar.");
				} else {
					servicioUsuario.habilitarUsuario(new BigDecimal(contratoVO.getColegiado().getUsuario().getIdUsuario()), false);
					Date fecha = Calendar.getInstance().getTime();
					BigDecimal estadoAnt = contratoVO.getEstadoContrato();
					contratoVO.setEstadoContrato(new BigDecimal(EstadoContrato.Habilitado.getValorEnum()));
					contratoVO.setFechaFin(null);
					contratoDao.actualizar(contratoVO);
					servicioEvolucionContrato.guardarEvolucionContrato(contratoVO.getIdContrato(), idUsuario, motivo, solicitante, fecha, TipoActualizacion.MOD, new BigDecimal(
							EstadoContrato.Habilitado.getValorEnum()), estadoAnt);
					resultado.setMensaje("El Contrato: " + contratoVO.getRazonSocial() + ", se ha habilitado correctamente.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos para el contrato: " + idContrato);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de habilitar el contrato con id: " + idContrato + ",error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de habilitar el contrato: " + idContrato);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean deshabilitarContrato(BigDecimal idContrato, BigDecimal idUsuario, String motivo, String solicitante) {
		ResultBean resultado = new ResultBean();
		try {
			ContratoVO contratoVO = getContrato(idContrato);
			if (contratoVO != null) {
				if (EstadoContrato.Deshabilitado.getValorEnum().equals(contratoVO.getEstadoContrato().toString())) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El contrato: " + contratoVO.getRazonSocial() + ", ya se encuentra deshabilitado.");
				} else if (EstadoContrato.Eliminado.getValorEnum().equals(contratoVO.getEstadoContrato().toString())) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El contrato: " + contratoVO.getRazonSocial() + ", está eliminado, por lo que no se puede deshabilitar.");
				} else {
					Date fecha = Calendar.getInstance().getTime();
					BigDecimal estadoAnt = contratoVO.getEstadoContrato();
					contratoVO.setEstadoContrato(new BigDecimal(EstadoContrato.Deshabilitado.getValorEnum()));
					contratoVO.setFechaFin(fecha);
					contratoDao.actualizar(contratoVO);
					servicioUsuario.deshabilitarUsuario(new BigDecimal(contratoVO.getColegiado().getUsuario().getIdUsuario()), fecha, false);
					servicioEvolucionContrato.guardarEvolucionContrato(contratoVO.getIdContrato(), idUsuario, motivo, solicitante, fecha, TipoActualizacion.MOD, new BigDecimal(
							EstadoContrato.Deshabilitado.getValorEnum()), estadoAnt);
					resultado.setMensaje("El Contrato: " + contratoVO.getRazonSocial() + ", se ha deshabilitado correctamente.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos para el contrato: " + idContrato);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de deshabilitar el contrato con id: " + idContrato + ",error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de deshabilitar el contrato: " + idContrato);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean eliminarContrato(BigDecimal idContrato, BigDecimal idUsuario, String motivo, String solicitante) {
		ResultBean resultado = new ResultBean();
		try {
			ContratoVO contratoVO = getContrato(idContrato);
			if (contratoVO != null) {
				if (EstadoContrato.Eliminado.getValorEnum().equals(contratoVO.getEstadoContrato().toString())) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El contrato: " + contratoVO.getRazonSocial() + ", ya se encuentra eliminado.");
				} else {
					Date fecha = Calendar.getInstance().getTime();
					BigDecimal estadoAnt = contratoVO.getEstadoContrato();
					contratoVO.setEstadoContrato(new BigDecimal(EstadoContrato.Eliminado.getValorEnum()));
					contratoVO.setFechaFin(fecha);
					contratoDao.actualizar(contratoVO);
					servicioUsuario.deshabilitarUsuario(new BigDecimal(contratoVO.getColegiado().getUsuario().getIdUsuario()), fecha, false);
					servicioEvolucionContrato.guardarEvolucionContrato(contratoVO.getIdContrato(), idUsuario, motivo, solicitante, fecha, TipoActualizacion.MOD, new BigDecimal(EstadoContrato.Eliminado
							.getValorEnum()), estadoAnt);
					resultado.setMensaje("El Contrato: " + contratoVO.getRazonSocial() + ", se ha deshabilitado correctamente.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos para el contrato: " + idContrato);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar el contrato con id: " + idContrato + ",error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de eliminar el contrato: " + idContrato);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean actualizarContrato(BigDecimal idContrato, BigDecimal idUsuario, String alias, FechaFraccionada fecha) {
		ResultBean resultado = new ResultBean();
		try {
			ContratoVO contratoVO = getContrato(idContrato);
			if (contratoVO != null) {
				contratoVO.getColegiado().setAlias(alias);
				contratoVO.getColegiado().setFechaCaducidad(utilesFecha.convertirFechaFraccionadaEnDate(fecha));
				contratoDao.actualizar(contratoVO);
				servicioEvolucionContrato.guardarEvolucionContratoAlias(contratoVO.getIdContrato(), idUsuario, fecha, TipoActualizacion.AAC, contratoVO.getEstadoContrato(), contratoVO
						.getEstadoContrato());
				resultado.setMensaje("El Alias: " + alias + " del colegiado se ha actualizado correctamente.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar el alias con id: " + idContrato + ",error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de actualizar el alias el contrato: " + idContrato);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return resultado;
	}

	@Transactional
	public ResultBean eliminarUsuarioContrato(String[] idUsuarios, Long idContrato) {
		// TODO Auto-generated method stub
		ResultBean resultado = new ResultBean();
		Calendar fechaCambio = Calendar.getInstance();
		try {
			ContratoVO contratoVO = getContrato(new BigDecimal(idContrato));
			if (contratoVO != null) {

				for (String elemento : idUsuarios) {
					ContratoUsuarioVO contratoUsuario = new ContratoUsuarioVO();
					ContratoUsuarioPK contratopk = new ContratoUsuarioPK();
					contratopk.setIdContrato(idContrato);
					contratopk.setIdUsuario(elemento);
					contratoUsuario.setId(contratopk);
					// cambiar estado a borrado y actualizar tabla contrato_usuario para cada usuario
					// añadir la evolucion

					UsuarioDto usuarioSesion = servicioUsuario.getUsuarioDto(new BigDecimal(elemento));

					// Cambiamos a eliminado en el contrato pero no en la gestoria.
					contratoUsuario.setEstadoUsuarioContrato(BigDecimal.ZERO);
					contratoUsuarioDao.actualizar(contratoUsuario);

					servicioEvolucionUsuario.guardarEvolucionUsuario(new BigDecimal(elemento), new Timestamp(fechaCambio.getTimeInMillis()), idContrato, TipoActualizacion.MOD, new BigDecimal(
							EstadoUsuario.SinAsociar.getValorEnum()), new BigDecimal(EstadoUsuario.Habilitado.getValorEnum()), idContrato, usuarioSesion.getNif());
					fechaCambio.add(Calendar.SECOND, 1);

				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos para el contrato: " + idContrato);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar  el usuario del contrato con id: " + idContrato + ",error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de eliminar el usuario del contrato: " + idContrato);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return resultado;
	}

	// Insertar uno o varios usuarios en la tabla de contrato usuario
	@Transactional
	public ResultBean asociarUsuarioContrato(String[] idUsuarios, Long idContrato) {
		ResultBean resultado = new ResultBean();
		ContratoUsuarioVO cont;
		try {
			ContratoVO contratoVO = getContrato(new BigDecimal(idContrato));
			if (contratoVO != null) {

				for (String elemento : idUsuarios) {

					ContratoUsuarioPK contratopk = new ContratoUsuarioPK();
					contratopk.setIdContrato(idContrato);
					contratopk.setIdUsuario(elemento);
					// Comprobar si ya existe el usuario en el contrato , en ese caso no se guardará.
					UsuarioDto usuarioSesion = servicioUsuario.getUsuarioDto(new BigDecimal(elemento));
					// añadir la evolucion
					// Timestamp fecha = new Timestamp(Calendar.getInstance().getTimeInMillis());
					Calendar fechaCambio = Calendar.getInstance();

					cont = buscaUsuarioContrato(contratopk.getIdUsuario(), contratopk.getIdContrato());
					if (cont == null) {
						ContratoUsuarioVO contratoUsuario = new ContratoUsuarioVO();
						contratoUsuario.setId(contratopk);
						contratoUsuario.setEstadoUsuarioContrato(BigDecimal.ONE);
						contratoUsuarioDao.guardar(contratoUsuario);
						servicioEvolucionUsuario.guardarEvolucionUsuario(new BigDecimal(elemento), new Timestamp(fechaCambio.getTimeInMillis()), idContrato, TipoActualizacion.CRE, new BigDecimal(
								EstadoUsuario.Asociado.getValorEnum()), new BigDecimal(EstadoUsuario.SinAsociar.getValorEnum()), null, usuarioSesion.getNif());
						fechaCambio.add(Calendar.SECOND, 1);

					} else { // Actualizar el estado a 1
								// Comprobar si el usuario ya esta agregado al contrato
						if (cont.getEstadoUsuarioContrato().equals(BigDecimal.ZERO) || cont.getEstadoUsuarioContrato().equals(new BigDecimal("6"))) {
							cont.setEstadoUsuarioContrato(BigDecimal.ONE);
							contratoUsuarioDao.actualizar(cont);
							servicioEvolucionUsuario.guardarEvolucionUsuario(new BigDecimal(elemento), new Timestamp(fechaCambio.getTimeInMillis()), idContrato, TipoActualizacion.MOD, new BigDecimal(
									EstadoUsuario.Asociado.getValorEnum()), new BigDecimal(EstadoUsuario.SinAsociar.getValorEnum()), idContrato, usuarioSesion.getNif());
							fechaCambio.add(Calendar.SECOND, 1);
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("El usuario ya se ha asociado al contrato ");

						}

					}

				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos para el contrato: " + idContrato);
			}

		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de agregar  el usuario del contrato con id: " + idContrato + ",error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de agregar el usuario del contrato: " + idContrato);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return resultado;
	}

	@Transactional
	public ResultBean cargarUsuarioGestoria(String[] idUsuarios, Long idContrato) {
		ResultBean resultado = new ResultBean();
		try {
			ContratoVO contratoVO = getContrato(new BigDecimal(idContrato));
			if (contratoVO != null) {

				for (String elemento : idUsuarios) {
					ContratoUsuarioVO contratoUsuario = new ContratoUsuarioVO();
					ContratoUsuarioPK contratopk = new ContratoUsuarioPK();
					contratopk.setIdContrato(idContrato);
					contratopk.setIdUsuario(elemento);
					contratoUsuario.setId(contratopk);
					contratoUsuarioDao.guardar(contratoUsuario);

				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos para el contrato: " + idContrato);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de recuperar los usuarios del contrato con id: " + idContrato + ",error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de agregar el usuario del contrato: " + idContrato);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return resultado;
	}

	@Override
	public ContratoUsuarioVO buscaUsuarioContrato(String idUsuario, Long idContrato) {
		try {
			ContratoUsuarioVO contratoUsuarioVO = contratoUsuarioDao.getContratoUsuario(idUsuario, idContrato);
			if (contratoUsuarioVO != null) {
				return contratoUsuarioVO;
			} else {
				return null;
			}
		} catch (Exception e) {
			log.error("Error al obtener el contrato colegiado", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ContratoDto> getListaContratosPorId(List<Long> listaIdsContratos) {
		try {
			if (listaIdsContratos != null && !listaIdsContratos.isEmpty()) {
				List<ContratoVO> listaContratos = contratoDao.getListaContratosPorId(listaIdsContratos);
				if (listaContratos != null && !listaContratos.isEmpty()) {
					return conversor.transform(listaContratos, ContratoDto.class);
				}
			}
		} catch (Exception e) {
			log.error("Error al obtener el contrato colegiado", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean tieneFirmadoLopd(Long idContrato) {
		try {
			ContratoVO contrato = contratoDao.getContrato(idContrato, Boolean.FALSE);
			if (contrato != null) {
				FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.DOCUMENTOS_LOPD, null, null, contrato.getColegiado().getNumColegiado() + "_Firma_OK",
						ConstantesGestorFicheros.EXTENSION_PDF);
				if (fichero == null || fichero.getFile() == null) {
					FileResultBean ficheroColegiado = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.DOCUMENTOS_LOPD, null, null, contrato.getColegiado().getNumColegiado(),
							ConstantesGestorFicheros.EXTENSION_PDF);
					if (ficheroColegiado == null || ficheroColegiado.getFile() == null) {
						crearFicheroLopd(contrato);
					}
					return Boolean.FALSE;
				}
			} else {
				log.error("No existen datos en BBDD para el idContrato: " + idContrato + ", por lo que no se podra confirmar si tiene firmada la LOPD.");
			}

		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de comprobar si tiene firmada la LOPD, error: ", e);
		}
		return Boolean.TRUE;
	}

	private File crearFicheroLopd(ContratoVO contrato) {
		File pdf = null;
		try {
			ProvinciaDto provincia = servicioProvincia.getProvinciaDto(contrato.getIdProvincia());
			MunicipioDto municipio = servicioMunicipio.getMunicipioDto(contrato.getIdProvincia(), contrato.getIdMunicipio());
			if (contrato != null) {
				List<CampoPdfBean> listaCampos = new ArrayList<CampoPdfBean>();
				PdfMaker pdfColegiado = new PdfMaker();
				String ruta = "/org/gestoresmadrid/oegam2/plantillasPDF/PlantillaContratoLopdColegiado.pdf";

				byte[] bytePdfColegiado = pdfColegiado.abrirPdf(ruta);
				String nombreApellidos = "";
				if (contrato.getColegiado().getUsuario().getApellidosNombre().contains(",")) {
					String apeNombre[] = contrato.getColegiado().getUsuario().getApellidosNombre().split(",");
					nombreApellidos = apeNombre[1] + " " + apeNombre[0];
				} else {
					nombreApellidos = contrato.getColegiado().getUsuario().getApellidosNombre();
				}
				Fecha fecha = utilesFecha.getFechaActual();
				listaCampos.add(new CampoPdfBean("nombre_representado", nombreApellidos, false, false, ConstantesPDF._7));
				listaCampos.add(new CampoPdfBean("ciudad_representado", provincia.getNombre(), false, false, ConstantesPDF._7));
				listaCampos.add(new CampoPdfBean("provincia_representado", municipio.getNombre(), false, false, ConstantesPDF._7));
				listaCampos.add(new CampoPdfBean("DNI_representado", contrato.getColegiado().getUsuario().getNif(), false, false, ConstantesPDF._7));
				listaCampos.add(new CampoPdfBean("nombre_representado_firma", "D. " + nombreApellidos, false, false, ConstantesPDF._7));
				listaCampos.add(new CampoPdfBean("mes", utilesFecha.convertirMesNumberToDesc(Integer.parseInt(fecha.getMes())), false, false, ConstantesPDF._7));
				listaCampos.add(new CampoPdfBean("dia", String.valueOf((fecha.getDia())), false, false, ConstantesPDF._7));

				bytePdfColegiado = pdfColegiado.setCampos(bytePdfColegiado, listaCampos);

				pdf = gestorDocumentos.guardarFichero(ConstantesGestorFicheros.DOCUMENTOS_LOPD, null, null, contrato.getColegiado().getNumColegiado(), ConstantesGestorFicheros.EXTENSION_PDF,
						bytePdfColegiado);
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de comprobar si tiene firmada la LOPD, error: ", e);
		}
		return pdf;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean firmarLopd(BigDecimal idContrato) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			ContratoVO contrato = contratoDao.getContrato(idContrato.longValue(), Boolean.FALSE);
			if (contrato != null) {
				FileResultBean ficheroFirmado = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.DOCUMENTOS_LOPD, null, null, contrato.getColegiado().getNumColegiado()
						+ "_Firma_OK", ConstantesGestorFicheros.EXTENSION_PDF);
				if (ficheroFirmado == null || ficheroFirmado.getFile() == null) {
					FileResultBean ficheroColegiado = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.DOCUMENTOS_LOPD, null, null, contrato.getColegiado().getNumColegiado(),
							ConstantesGestorFicheros.EXTENSION_PDF);
					File pdf = null;
					if (ficheroColegiado == null || ficheroColegiado.getFile() == null) {
						pdf = crearFicheroLopd(contrato);
					} else {
						pdf = ficheroColegiado.getFile();
					}
					UtilesViafirma utilesViafirma = new UtilesViafirma();
					byte[] docFirmado = utilesViafirma.firmarLopd(gestorDocumentos.transformFiletoByte(pdf), contrato.getColegiado().getAlias());
					if (docFirmado != null) {
						pdf = gestorDocumentos.guardarFichero(ConstantesGestorFicheros.DOCUMENTOS_LOPD, null, null, contrato.getColegiado().getNumColegiado() + "_Firma_OK",
								ConstantesGestorFicheros.EXTENSION_PDF, docFirmado);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos del colegiado para poder realizar la firma.");
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de firmar la LOPD, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de firmar la LOPD.");
		}
		return resultado;

	}
	
	@Override
	@Transactional(readOnly=true)
	public List<ContratoDto> getListGrupo(String idGrupo) {
		try {
			List<ContratoVO> contratosVO = contratoDao.getListGrupo(idGrupo);
			if (contratosVO != null && !contratosVO.isEmpty()) {
				return conversor.transform(contratosVO, ContratoDto.class);
			}
		} catch (Exception e) {
			log.error("Error al obtener la lista de contratos del grupo: " + idGrupo + ", error:" , e);
		}
		return Collections.emptyList();
	}
	
	@Override
	@Transactional(readOnly=true)
	public Boolean isContratoDeGrupo(Long idContrato, String idGrupo){
		return contratoDao.isContratoDeGrupo(idContrato, idGrupo);
	}

}
