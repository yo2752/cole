package org.gestoresmadrid.oegamDuplicadoPermisoConducir.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.impresion.model.enumerados.EstadosDocumentos;
import org.gestoresmadrid.core.impresion.model.enumerados.TipoDocumentoImpresiones;
import org.gestoresmadrid.core.impresion.model.vo.ImpresionVO;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoTasa;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaPK;
import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.enumerados.TiposDocDuplicadosPermisos;
import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.vo.DuplicadoPermisoConducirVO;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoTramitesInterga;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.TipoTramiteInterga;
import org.gestoresmadrid.core.trafico.interga.model.vo.IntervinienteIntergaVO;
import org.gestoresmadrid.oegamComun.cola.service.ServicioComunCola;
import org.gestoresmadrid.oegamComun.credito.service.ServicioComunCredito;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunDireccion;
import org.gestoresmadrid.oegamComun.persona.service.ServicioComunEvolucionPersona;
import org.gestoresmadrid.oegamComun.persona.service.ServicioComunPersona;
import org.gestoresmadrid.oegamComun.persona.view.bean.ResultadoPersonaBean;
import org.gestoresmadrid.oegamComun.tasa.service.ServicioComunTasa;
import org.gestoresmadrid.oegamComun.utiles.UtilidadesNIFValidator;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.conversor.service.ConversorDuplPermCond;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.rest.service.ServicioDuplPermConducirRestWS;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.service.ServicioDuplicadoPermCond;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.service.ServicioEvoDuplicadoPermisoConducir;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.service.ServicioPersistenciaDuplPermCond;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.bean.ResultadoDuplPermCondBean;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.dto.DuplicadoPermisoConducirDto;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionDocumentos;
import org.gestoresmadrid.oegamImpresion.view.dto.ImpresionDto;
import org.gestoresmadrid.oegamInterga.service.ServicioInterga;
import org.gestoresmadrid.oegamInterga.service.ServicioIntervinienteInterga;
import org.gestoresmadrid.oegamInterga.view.bean.ResultadoIntergaBean;
import org.gestoresmadrid.oegamInterga.view.dto.IntervinienteIntergaDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioDuplicadoPermCondImpl implements ServicioDuplicadoPermCond {

	private static final long serialVersionUID = -5520634810982261316L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioDuplicadoPermCondImpl.class);

	@Autowired
	ServicioPersistenciaDuplPermCond servicioPersistenciaDuplPermCond;

	@Autowired
	ServicioIntervinienteInterga servicioIntervinienteInterga;

	@Autowired
	ServicioDuplPermConducirRestWS servicioDuplPermConducirRestWS;

	@Autowired
	ServicioInterga servicioInterga;

	@Autowired
	ServicioEvoDuplicadoPermisoConducir servicioEvoDuplicadoPermisoConducir;

	@Autowired
	ServicioComunTasa servicioTasa;

	@Autowired
	ServicioComunPersona servicioPersona;

	@Autowired
	ServicioComunDireccion servicioDireccion;

	@Autowired
	ServicioComunEvolucionPersona servicioEvolucionPersona;

	@Autowired
	ServicioComunCola servicioCola;

	@Autowired
	ServicioComunCredito servicioCredito;

	@Autowired
	ServicioImpresionDocumentos servicioImpresionDocumentos;

	@Autowired
	ConversorDuplPermCond conversorDuplPermCond;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	Utiles utiles;

	@Autowired
	UtilidadesNIFValidator utilesNifValidator;

	@Override
	public DuplicadoPermisoConducirDto getDuplicadoPermisoConducir(Long idDuplicadoPermisoCond) {
		try {
			DuplicadoPermisoConducirVO duplicadoPermisoConducirVO = servicioPersistenciaDuplPermCond.getDuplicadoPermisoConducir(idDuplicadoPermisoCond);
			if (duplicadoPermisoConducirVO != null) {
				DuplicadoPermisoConducirDto duplicadoPermisoConducirDto = conversorDuplPermCond.transform(duplicadoPermisoConducirVO, DuplicadoPermisoConducirDto.class);
				IntervinienteIntergaVO titular = servicioIntervinienteInterga.getIntervinienteTramite(duplicadoPermisoConducirDto.getNumExpediente(), TipoTramiteInterga.Duplicado_Permiso_Conducir
						.getValorEnum());
				if (titular != null) {
					duplicadoPermisoConducirDto.setTitular(conversorDuplPermCond.transform(titular, IntervinienteIntergaDto.class));

					ImpresionVO impresion = servicioImpresionDocumentos.getDatosPorNombreDocumentoGeneradoYDescargado(servicioInterga.obtenerNombreDocumentoMandato(duplicadoPermisoConducirDto
							.getNumExpediente(), TipoTramiteTrafico.DuplicadoPermisoConducir.getValorEnum()));
					if (impresion != null) {
						duplicadoPermisoConducirDto.addMandato(conversorDuplPermCond.transform(impresion, ImpresionDto.class));
					}
				}
				recuperarDocumentos(duplicadoPermisoConducirDto);
				return duplicadoPermisoConducirDto;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el duplicado permiso conducir, error: ", e);
		}
		return null;
	}

	private void recuperarDocumentos(DuplicadoPermisoConducirDto duplicadoPermisoConducirDto) {
		if (duplicadoPermisoConducirDto.getEstadoDeclaracion() != null && (EstadosDocumentos.Firmado.getValorEnum().equals(duplicadoPermisoConducirDto.getEstadoDeclaracion())
				|| EstadosDocumentos.Enviado.getValorEnum().equals(duplicadoPermisoConducirDto.getEstadoDeclaracion()) || EstadosDocumentos.Error_Envio.getValorEnum().equals(
						duplicadoPermisoConducirDto.getEstadoDeclaracion()))) {
			String nombreDocumento = TipoDocumentoImpresiones.DeclaracionResponsabilidadColegiadoConducir.getNombreDocumento() + "_" + duplicadoPermisoConducirDto.getNumExpediente().toString();
			duplicadoPermisoConducirDto.addListaFicheroInfo(duplicadoPermisoConducirDto.getNumExpediente(), nombreDocumento, TiposDocDuplicadosPermisos.DECLARACION.getNombreEnum(), "NO",
					EstadosDocumentos.convertirTexto(duplicadoPermisoConducirDto.getEstadoDeclaracion()));
		}

		if (duplicadoPermisoConducirDto.getEstadoSolicitud() != null && (EstadosDocumentos.Firmado.getValorEnum().equals(duplicadoPermisoConducirDto.getEstadoSolicitud()) || EstadosDocumentos.Enviado
				.getValorEnum().equals(duplicadoPermisoConducirDto.getEstadoSolicitud()) || EstadosDocumentos.Error_Envio.getValorEnum().equals(duplicadoPermisoConducirDto.getEstadoSolicitud()))) {
			String nombreDocumento = TipoDocumentoImpresiones.SolicitudDuplicadoPermisoConducir.getNombreDocumento() + "_" + duplicadoPermisoConducirDto.getNumExpediente().toString();
			duplicadoPermisoConducirDto.addListaFicheroInfo(duplicadoPermisoConducirDto.getNumExpediente(), nombreDocumento, TiposDocDuplicadosPermisos.SOLICITUD.getNombreEnum(), "NO",
					EstadosDocumentos.convertirTexto(duplicadoPermisoConducirDto.getEstadoSolicitud()));
		}

		if (duplicadoPermisoConducirDto.getEstadoDeclaracionTitular() != null && (EstadosDocumentos.Subido.getValorEnum().equals(duplicadoPermisoConducirDto.getEstadoDeclaracionTitular())
				|| EstadosDocumentos.Enviado.getValorEnum().equals(duplicadoPermisoConducirDto.getEstadoDeclaracionTitular()) || EstadosDocumentos.Error_Envio.getValorEnum().equals(
						duplicadoPermisoConducirDto.getEstadoDeclaracionTitular()))) {
			String nombreDocumento = TipoDocumentoImpresiones.DeclaracionResponsabilidadTitularConducir.getNombreDocumento() + "_" + duplicadoPermisoConducirDto.getNumExpediente().toString();
			duplicadoPermisoConducirDto.addListaFicheroInfo(duplicadoPermisoConducirDto.getNumExpediente(), nombreDocumento, TiposDocDuplicadosPermisos.DECLARACION_TITULAR.getNombreEnum(), "SI",
					EstadosDocumentos.convertirTexto(duplicadoPermisoConducirDto.getEstadoDeclaracionTitular()));
		}

		if (duplicadoPermisoConducirDto.getEstadoMandato() != null && (EstadosDocumentos.Subido.getValorEnum().equals(duplicadoPermisoConducirDto.getEstadoMandato()) || EstadosDocumentos.Enviado
				.getValorEnum().equals(duplicadoPermisoConducirDto.getEstadoMandato()) || EstadosDocumentos.Error_Envio.getValorEnum().equals(duplicadoPermisoConducirDto.getEstadoMandato()))) {
			String nombreDocumento = TipoDocumentoImpresiones.MandatoEspecifico.getNombreDocumento() + "_" + duplicadoPermisoConducirDto.getNumExpediente().toString();
			duplicadoPermisoConducirDto.addListaFicheroInfo(duplicadoPermisoConducirDto.getNumExpediente(), nombreDocumento, TiposDocDuplicadosPermisos.MANDATO.getNombreEnum(), "SI", EstadosDocumentos
					.convertirTexto(duplicadoPermisoConducirDto.getEstadoMandato()));
		}

		if (duplicadoPermisoConducirDto.getEstadoOtroDocumento() != null && (EstadosDocumentos.Subido.getValorEnum().equals(duplicadoPermisoConducirDto.getEstadoOtroDocumento())
				|| EstadosDocumentos.Enviado.getValorEnum().equals(duplicadoPermisoConducirDto.getEstadoOtroDocumento()) || EstadosDocumentos.Error_Envio.getValorEnum().equals(
						duplicadoPermisoConducirDto.getEstadoOtroDocumento()))) {
			String nombreDocumento = TipoDocumentoImpresiones.OtroDocumentoDPC.getNombreDocumento() + "_" + duplicadoPermisoConducirDto.getNumExpediente().toString();
			duplicadoPermisoConducirDto.addListaFicheroInfo(duplicadoPermisoConducirDto.getNumExpediente(), nombreDocumento, TiposDocDuplicadosPermisos.OTRO.getNombreEnum(), "SI", EstadosDocumentos
					.convertirTexto(duplicadoPermisoConducirDto.getEstadoMandato()));
		}
	}

	@Override
	public ResultadoDuplPermCondBean getDuplPermCondPorNumExpediente(BigDecimal numExpediente) {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			DuplicadoPermisoConducirVO duplicadoPermisoConducirVO = servicioPersistenciaDuplPermCond.getDuplPermCondPorNumExpediente(numExpediente);
			if (duplicadoPermisoConducirVO != null) {
				DuplicadoPermisoConducirDto duplicadoPermisoConducirDto = conversorDuplPermCond.transform(duplicadoPermisoConducirVO, DuplicadoPermisoConducirDto.class);
				IntervinienteIntergaVO titular = servicioIntervinienteInterga.getIntervinienteTramite(duplicadoPermisoConducirDto.getNumExpediente(), TipoTramiteInterga.Duplicado_Permiso_Conducir
						.getValorEnum());
				if (titular != null) {
					duplicadoPermisoConducirDto.setTitular(conversorDuplPermCond.transform(titular, IntervinienteIntergaDto.class));
					ImpresionVO impresion = servicioImpresionDocumentos.getDatosPorNombreDocumentoGeneradoYDescargado(servicioInterga.obtenerNombreDocumentoMandato(duplicadoPermisoConducirDto
							.getNumExpediente(), TipoTramiteTrafico.DuplicadoPermisoConducir.getValorEnum()));
					if (impresion != null) {
						duplicadoPermisoConducirDto.addMandato(conversorDuplPermCond.transform(impresion, ImpresionDto.class));
					}
				}
				recuperarDocumentos(duplicadoPermisoConducirDto);
				resultado.setDuplicadoPermisoConducirDto(duplicadoPermisoConducirDto);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el duplicado permiso conducir, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al recuperar el duplicado permiso conducir.");
		}
		return resultado;
	}

	@Override
	public ResultadoDuplPermCondBean guardar(DuplicadoPermisoConducirDto duplicadoPermisoConducirDto, BigDecimal idUsuario) {
		ResultadoDuplPermCondBean respuesta = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			DuplicadoPermisoConducirVO duplicadoPermisoConducirVO = conversorDuplPermCond.transform(duplicadoPermisoConducirDto, DuplicadoPermisoConducirVO.class);
			ResultadoDuplPermCondBean respuestaPermisoCond = servicioPersistenciaDuplPermCond.guardarPermisoCond(duplicadoPermisoConducirVO, idUsuario);
			if (respuestaPermisoCond != null && !respuestaPermisoCond.getError()) {
				respuesta.setIdDuplicadoPermisoCond(respuestaPermisoCond.getIdDuplicadoPermisoCond());
				duplicadoPermisoConducirVO.setNumExpediente(respuestaPermisoCond.getNumExpediente());

				ResultadoDuplPermCondBean respuestaTasa = gestionarTasa(duplicadoPermisoConducirDto.getCodigoTasa(), duplicadoPermisoConducirVO.getNumExpediente(), duplicadoPermisoConducirDto
						.getIdContrato(), idUsuario.longValue());
				if (respuestaTasa != null && respuestaTasa.getError() && StringUtils.isNotBlank(respuestaTasa.getMensaje())) {
					respuesta.addListaMensaje(respuestaTasa.getMensaje());
				}

				ResultadoDuplPermCondBean respuestaInterviniente = guardarIntervinientes(duplicadoPermisoConducirDto.getTitular(), duplicadoPermisoConducirVO.getNumExpediente(),
						duplicadoPermisoConducirDto.getNumColegiado(), idUsuario.longValue());
				if (respuestaInterviniente.getListaMensajes() != null) {
					for (String mensaje : respuestaInterviniente.getListaMensajes()) {
						respuesta.addListaMensaje(mensaje);
					}
				}
				if (StringUtils.isNotBlank(duplicadoPermisoConducirDto.getCodigoTasa())) {
					ResultadoBean respuestaTasaExpediente = servicioTasa.asignarTasaExpediente(duplicadoPermisoConducirDto.getCodigoTasa(), duplicadoPermisoConducirVO.getNumExpediente(),
							duplicadoPermisoConducirDto.getIdContrato(), TipoTasa.CuatroCinco.getValorEnum(), idUsuario.longValue());
					if (respuestaTasaExpediente.getListaMensajes() != null) {
						for (String mensaje : respuestaTasaExpediente.getListaMensajes()) {
							respuesta.addListaMensaje(mensaje);
						}
					}
				}
			} else {
				log.error("Error al guardar el duplicado permiso conducir: " + duplicadoPermisoConducirDto.getNumExpediente() + ". Mensaje: " + respuestaPermisoCond.getMensaje());
				respuesta.setError(true);
				respuesta.addListaMensaje(respuestaPermisoCond.getMensaje());
			}
		} catch (Exception e) {
			log.error("Error al guardar el duplicado permiso conducir: " + duplicadoPermisoConducirDto.getNumExpediente() + ". Mensaje: " + e.getMessage(), e);
			respuesta.setError(true);
			respuesta.addListaMensaje(e.getMessage());
		}
		return respuesta;
	}

	@Override
	public ResultadoDuplPermCondBean validar(Long idDuplicadoPermisoCond, Long idUsuario) {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			DuplicadoPermisoConducirVO duplicadoPermisoConducirVO = servicioPersistenciaDuplPermCond.getDuplicadoPermisoConducir(idDuplicadoPermisoCond);
			resultado = validarTramite(duplicadoPermisoConducirVO, idDuplicadoPermisoCond);
			if (!resultado.getError()) {
				String estadoAnterior = duplicadoPermisoConducirVO.getEstado();
				servicioPersistenciaDuplPermCond.actualizarEstado(idDuplicadoPermisoCond, EstadoTramitesInterga.Validado.getValorEnum());
				servicioEvoDuplicadoPermisoConducir.guardar(idDuplicadoPermisoCond, estadoAnterior, EstadoTramitesInterga.Validado.getValorEnum(), idUsuario, TipoActualizacion.MOD.getValorEnum());
				resultado.setMensaje("Tramite: " + duplicadoPermisoConducirVO.getNumExpediente() + " validado correctamente.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el tramite de duplicado permiso conducir con ID: " + idDuplicadoPermisoCond + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el tramite con ID: " + idDuplicadoPermisoCond);
		}
		return resultado;
	}

	private ResultadoDuplPermCondBean validarTramite(DuplicadoPermisoConducirVO duplicadoPermisoConducirVO, Long idPermisoIntern) {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		if (duplicadoPermisoConducirVO == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos en BBDD para el id: " + idPermisoIntern);
		} else if (!EstadoTramitesInterga.Iniciado.getValorEnum().equals(duplicadoPermisoConducirVO.getEstado())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + duplicadoPermisoConducirVO.getNumExpediente() + " no se puede validar porque no se encuentra en estado 'Iniciado'.");
		} else if (StringUtils.isBlank(duplicadoPermisoConducirVO.getDoiTitular())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de rellenar el NIF del titular para el expediente: " + duplicadoPermisoConducirVO.getNumExpediente());
		} else if (StringUtils.isBlank(duplicadoPermisoConducirVO.getCodigoTasa())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de seleccionar una tasa para el expediente: " + duplicadoPermisoConducirVO.getNumExpediente());
		} else if (StringUtils.isBlank(duplicadoPermisoConducirVO.getTipoDuplicado())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de seleccionar un tipo de duplicado para el expediente: " + duplicadoPermisoConducirVO.getNumExpediente());
		} else if (servicioPersistenciaDuplPermCond.existeTramiteDoiPendienteDgt(duplicadoPermisoConducirVO.getDoiTitular(), EstadoTramitesInterga.Pendiente_Repuesta_DGT.getValorEnum())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Existe ya un tramite Pendiente de Respuesta de DGT por lo que hasta que no finalize dicho tramite no se podra realizar ningún otro envio para el NIF: "
					+ duplicadoPermisoConducirVO.getDoiTitular());
		} else {
			IntervinienteIntergaVO titular = servicioIntervinienteInterga.getIntervinienteTrafico(duplicadoPermisoConducirVO.getNumExpediente(), duplicadoPermisoConducirVO.getDoiTitular(),
					duplicadoPermisoConducirVO.getNumColegiado(), TipoTramiteInterga.Duplicado_Permiso_Conducir.getValorEnum());
			if (titular != null) {
				if (utilesNifValidator.isValidDniNieCif(titular.getNif()) == -1) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El NIF/CIF del titular del expediente: " + duplicadoPermisoConducirVO.getNumExpediente() + " no es valido.");
				} else if (titular.getPersona() == null) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Debe de rellenar los datos de la persona para el expediente: " + duplicadoPermisoConducirVO.getNumExpediente());
				} else if (titular.getPersona().getApellido1RazonSocial() == null || titular.getPersona().getApellido1RazonSocial().isEmpty()) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El Apellido1 o razon social del titular del expediente: " + duplicadoPermisoConducirVO.getNumExpediente() + " debe de ir relleno.");
				} else if (StringUtils.isBlank(titular.getPersona().getPaisNacimiento())) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Debe rellenar el País de Nacimiento del Titular.");
				} else if (StringUtils.isBlank(titular.getPersona().getNacionalidad())) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Debe rellenar la Nacionalidad del Titular.");
				} else if (utilesNifValidator.FISICA == utilesNifValidator.isValidDniNieCif(titular.getNif())) {
					if (titular.getPersona().getNombre() == null || titular.getPersona().getNombre().isEmpty()) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("El nombre del titular del expediente: " + duplicadoPermisoConducirVO.getNumExpediente() + " debe de ir relleno.");
					}
				}
				if (titular.getDireccion() != null) {
					DireccionVO dirTitular = titular.getDireccion();
					if ((dirTitular.getIdProvincia() != null && !dirTitular.getIdProvincia().isEmpty()) || (dirTitular.getIdMunicipio() != null && !dirTitular.getIdMunicipio().isEmpty())
							|| (dirTitular.getNombreVia() != null && !dirTitular.getNombreVia().isEmpty()) || (dirTitular.getNumero() != null && !dirTitular.getNumero().isEmpty()) || (dirTitular
									.getCodPostal() != null && !dirTitular.getCodPostal().isEmpty())) {
						if (dirTitular.getIdProvincia() == null || dirTitular.getIdProvincia().isEmpty()) {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("Debe de seleccionar la provincia de la direccion del titular para el expediente: " + duplicadoPermisoConducirVO.getNumExpediente());
						} else if (dirTitular.getIdMunicipio() == null || dirTitular.getIdMunicipio().isEmpty()) {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("Debe de seleccionar el municipio de la direccion del titular para el expediente: " + duplicadoPermisoConducirVO.getNumExpediente());
						} else if (dirTitular.getNombreVia() == null || dirTitular.getNombreVia().isEmpty()) {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("Debe de seleccionar el nombre de la via de la direccion del titular para el expediente: " + duplicadoPermisoConducirVO.getNumExpediente());
						} else if (dirTitular.getNumero() == null || dirTitular.getNumero().isEmpty()) {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("Debe de seleccionar el numero de la via de la direccion del titular para el expediente: " + duplicadoPermisoConducirVO.getNumExpediente());
						} else if (dirTitular.getCodPostal() == null || dirTitular.getCodPostal().isEmpty()) {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("Debe de seleccionar el codigo postal de la direccion del titular para el expediente: " + duplicadoPermisoConducirVO.getNumExpediente());
						}
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("La dirección del titular es obligatoria.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se encuentran datos en la BBDD para el titular del expediente: " + duplicadoPermisoConducirVO.getNumExpediente());
			}
		}
		return resultado;
	}

	@Override
	public ResultadoDuplPermCondBean tramitar(Long idDuplicadoPermisoCond, Long idUsuario) {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		String estadoNuevo = "";
		String estadorAnterior = "";
		try {
			DuplicadoPermisoConducirVO duplicadoPermisoConducirVO = servicioPersistenciaDuplPermCond.getDuplicadoPermisoConducir(idDuplicadoPermisoCond);
			estadorAnterior = duplicadoPermisoConducirVO.getEstado();
			resultado = validarTramiteTramitar(duplicadoPermisoConducirVO, idDuplicadoPermisoCond);
			if (!resultado.getError()) {
				resultado = validarDocumentacion(duplicadoPermisoConducirVO);
				if (!resultado.getError()) {
					estadoNuevo = EstadoTramitesInterga.Pendiente_Envio_DGT.getValorEnum();
					servicioPersistenciaDuplPermCond.actualizarEstado(idDuplicadoPermisoCond, estadoNuevo);
					ResultadoBean resultCola = servicioCola.crearSolicitud(idDuplicadoPermisoCond, ProcesosEnum.ENVIO_DUPL_PERM_COND.getNombreEnum(), gestorPropiedades.valorPropertie(
							"nombreHostProceso"), TipoTramiteTrafico.DuplicadoPermisoConducir.getValorEnum(), new BigDecimal(idUsuario), new BigDecimal(duplicadoPermisoConducirVO.getIdContrato()),
							null);
					if (resultCola.getError()) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(resultCola.getMensaje());
					} else {
						try {
							servicioEvoDuplicadoPermisoConducir.guardar(idDuplicadoPermisoCond, duplicadoPermisoConducirVO.getEstado(), EstadoTramitesInterga.Pendiente_Envio_DGT.getValorEnum(),
									idUsuario, TipoActualizacion.MOD.getValorEnum());
						} catch (Exception e) {
							log.error("Error al guardar la evolución del duplicado permiso conducir a la hora de validar", e);
						}
						resultado.setMensaje("Tramite: " + duplicadoPermisoConducirVO.getNumExpediente() + " tramitado correctamente.");
					}
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de tramitar el tramite de duplicado permiso conducir con ID: " + idDuplicadoPermisoCond + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de tramitar el tramite con ID: " + idDuplicadoPermisoCond);
		}
		if (resultado != null && resultado.getError() && !StringUtils.isNotBlank(estadoNuevo)) {
			try {
				servicioPersistenciaDuplPermCond.actualizarEstado(idDuplicadoPermisoCond, estadorAnterior);
			} catch (Exception e) {
				log.error("Ha sucedido un error a la hora de actualizar el estado del expediente con id: " + idDuplicadoPermisoCond + ", error: ", e);
			}
		}
		return resultado;
	}

	private ResultadoDuplPermCondBean validarTramiteTramitar(DuplicadoPermisoConducirVO duplicadoPermisoConducirVO, Long idDuplicadoPermisoCond) {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		if (duplicadoPermisoConducirVO == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos en BBDD para el id: " + idDuplicadoPermisoCond);
		} else if (!EstadoTramitesInterga.Documentacion_Firmada.getValorEnum().equals(duplicadoPermisoConducirVO.getEstado())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + duplicadoPermisoConducirVO.getNumExpediente() + " no se puede tramitar porque no se encuentra en estado 'Documetación Firmada'.");
		}
		return resultado;
	}

	private ResultadoDuplPermCondBean gestionarTasa(String codigoTasa, BigDecimal numExpediente, Long idContrato, Long idUsuario) {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			TasaVO tasaBBDD = servicioTasa.getTasa(null, numExpediente, TipoTasa.CuatroCuatro.getValorEnum());
			ResultadoBean resultadoTasa = new ResultadoBean(Boolean.FALSE);
			if (tasaBBDD == null && StringUtils.isNotBlank(codigoTasa)) {
				resultadoTasa = servicioTasa.asignarTasaExpediente(codigoTasa, numExpediente, idContrato, TipoTasa.CuatroCuatro.getValorEnum(), idUsuario);
			} else {
				if (tasaBBDD != null && StringUtils.isNotBlank(tasaBBDD.getCodigoTasa()) && StringUtils.isBlank(codigoTasa)) {
					resultadoTasa = servicioTasa.desasignarTasaExpediente(tasaBBDD.getCodigoTasa(), numExpediente, idContrato, TipoTasa.CuatroCuatro.getValorEnum(), idUsuario);
				} else if (tasaBBDD != null && !tasaBBDD.getCodigoTasa().equals(codigoTasa)) {
					resultadoTasa = servicioTasa.desasignarTasaExpediente(tasaBBDD.getCodigoTasa(), numExpediente, idContrato, TipoTasa.CuatroCuatro.getValorEnum(), idUsuario);
					if (!resultadoTasa.getError()) {
						resultadoTasa = servicioTasa.asignarTasaExpediente(codigoTasa, numExpediente, idContrato, TipoTasa.CuatroCuatro.getValorEnum(), idUsuario);
					}
				}
			}
			if (resultadoTasa != null && resultadoTasa.getError()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultadoTasa.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de gestionar la tasa del duplicado permiso conducir para el expediente: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de gestionar la tasa del duplicado permiso conducir para el expediente: " + numExpediente);
		}
		return resultado;
	}

	private ResultadoDuplPermCondBean guardarIntervinientes(IntervinienteIntergaDto titular, BigDecimal numExpediente, String numColegiado, Long idUsuario) {
		ResultadoDuplPermCondBean result = new ResultadoDuplPermCondBean(Boolean.FALSE);
		boolean direccionNueva = false;
		if (titular != null && titular.getPersona() != null && StringUtils.isNotBlank(titular.getPersona().getNif())) {
			IntervinienteIntergaVO titularVO = conversorDuplPermCond.transform(titular, IntervinienteIntergaVO.class);
			if (StringUtils.isNotBlank(titularVO.getPersona().getId().getNif())) {
				titularVO.setNif(titularVO.getPersona().getId().getNif().toUpperCase());
				titularVO.setNumExpediente(numExpediente);
				titularVO.setNumColegiado(numColegiado);
				titularVO.setTipoTramiteInterga(TipoTramiteInterga.Duplicado_Permiso_Conducir.getValorEnum());
				titularVO.getPersona().getId().setNumColegiado(numColegiado);
				titularVO.getPersona().setEstado(new Long(1));

				String conversion = ServicioComunPersona.CONVERSION_PERSONA_INTERGA;

				// Guardar persona
				ResultadoPersonaBean resultPersona = servicioPersona.guardarActualizar(titularVO.getPersona(), numExpediente, TipoTramiteTrafico.DuplicadoPermisoConducir.getValorEnum(), idUsuario,
						conversion);

				if (!resultPersona.getError()) {
					// Guardar direccion
					if (titularVO.getDireccion() != null && utiles.convertirCombo(titularVO.getDireccion().getIdProvincia()) != null) {
						ResultadoPersonaBean resultDireccion = servicioDireccion.guardarActualizarPersona(titularVO.getDireccion(), titularVO.getPersona().getId().getNif(), titularVO.getPersona()
								.getId().getNumColegiado(), TipoTramiteTrafico.DuplicadoPermisoConducir.getValorEnum(), ServicioComunDireccion.CONVERSION_DIRECCION_INE);
						if (resultDireccion.getError()) {
							result.addListaMensaje("- Titular: " + resultDireccion.getMensaje());
							titularVO.setIdDireccion(null);
						} else {
							DireccionVO direccion = resultDireccion.getDireccion();
							direccionNueva = resultDireccion.getNuevaDireccion();
							if (direccion != null) {
								titularVO.setIdDireccion(direccion.getIdDireccion());
							}
							if (direccionNueva) {
								guardarEvolucionPersonaNuevaDireccion(titularVO.getPersona().getId().getNif(), numColegiado, numExpediente, TipoTramiteTrafico.PermisonInternacional.getValorEnum(),
										idUsuario);
							}
						}
					} else {
						titularVO.setIdDireccion(null);
					}
					// Guardar interviniente
					ResultadoBean resultInterviente = servicioIntervinienteInterga.guardarActualizar(titularVO, TipoTramiteInterga.Duplicado_Permiso_Conducir.getValorEnum());
					if (resultInterviente.getListaMensajes() != null) {
						for (String mensaje : resultInterviente.getListaMensajes()) {
							result.addListaMensaje(mensaje);
						}
					}
				} else {
					result.addListaMensaje(resultPersona.getMensaje());
				}
			} else {
				servicioIntervinienteInterga.eliminar(titularVO, TipoTramiteInterga.Duplicado_Permiso_Conducir.getValorEnum());
			}
		}
		return result;
	}

	private void guardarEvolucionPersonaNuevaDireccion(String nif, String numColegiado, BigDecimal numExpediente, String tipoTramite, Long idUsuario) {
		EvolucionPersonaVO evolucion = new EvolucionPersonaVO();
		EvolucionPersonaPK id = new EvolucionPersonaPK();

		id.setNif(nif);
		id.setNumColegiado(numColegiado);
		id.setFechaHora(new Date());
		evolucion.setId(id);

		evolucion.setTipoActuacion(TipoActualizacion.MOD.getValorEnum());
		evolucion.setNumExpediente(numExpediente);
		evolucion.setTipoTramite(tipoTramite);
		evolucion.setOtros("Nueva Dirección");
		UsuarioVO usuarioVO = new UsuarioVO();
		usuarioVO.setIdUsuario(idUsuario);
		evolucion.setUsuario(usuarioVO);

		servicioEvolucionPersona.guardarEvolucionPersona(evolucion);
	}

	@Override
	public ResultadoDuplPermCondBean cambiarEstado(Long idDuplPermCond, String estadoNuevo, Long idUsuario, Boolean accionesAsociadas) {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			DuplicadoPermisoConducirVO duplicadoPermisoConducirVO = servicioPersistenciaDuplPermCond.getDuplicadoPermisoConducir(idDuplPermCond);
			if (!EstadoTramitesInterga.Anulado.getValorEnum().equals(estadoNuevo)) {
				servicioPersistenciaDuplPermCond.actualizarEstado(idDuplPermCond, estadoNuevo);
				servicioEvoDuplicadoPermisoConducir.guardar(idDuplPermCond, duplicadoPermisoConducirVO.getEstado(), estadoNuevo, idUsuario, TipoActualizacion.MOD.getValorEnum());
				String mensaje = "";
				if (accionesAsociadas) {
					if (EstadoTramitesInterga.Finalizado.getValorEnum().equals(estadoNuevo)) {
						ResultadoBean resultCredito = servicioCredito.descontarCreditos(TipoTramiteTrafico.DuplicadoPermisoConducir.getValorEnum(), duplicadoPermisoConducirVO.getIdContrato(),
								idUsuario, Collections.singletonList(duplicadoPermisoConducirVO.getNumExpediente()));
						if (resultCredito.getError()) {
							mensaje += resultCredito.getMensaje();
						}
					} else if (EstadoTramitesInterga.Pendiente_Envio_DGT.getValorEnum().equals(duplicadoPermisoConducirVO.getEstado())) {
						ResultadoBean resultCola = servicioCola.eliminarCola(new BigDecimal(duplicadoPermisoConducirVO.getIdDuplicadoPermCond()), ProcesosEnum.ENVIO_PERMISO_INTER.getNombreEnum(),
								gestorPropiedades.valorPropertie("nombreHostProceso"));
						if (resultCola.getError()) {
							mensaje += resultCola.getMensaje();
						}
					}
				}
				if (!mensaje.isEmpty()) {
					resultado.setMensaje("Tramite: " + duplicadoPermisoConducirVO.getNumExpediente() + " actualizado correctamente, con las siguientes incidencias: " + mensaje);
				} else {
					resultado.setMensaje("Tramite: " + duplicadoPermisoConducirVO.getNumExpediente() + " actualizado correctamente.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se puede actualizar el estado del expediente: " + duplicadoPermisoConducirVO.getNumExpediente()
						+ " al estado Anulado, para eso debera pulsar el boton de eliminar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado del tramite de duplicado permiso conducir con ID: " + idDuplPermCond + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado del tramite con ID: " + idDuplPermCond);
		}
		return resultado;
	}

	@Override
	public ResultadoDuplPermCondBean eliminar(Long idDuplPermCond, Long idUsuario) {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			DuplicadoPermisoConducirVO duplicadoPermisoConducirVO = servicioPersistenciaDuplPermCond.getDuplicadoPermisoConducir(idDuplPermCond);
			resultado = validarTramiteEliminar(duplicadoPermisoConducirVO, idDuplPermCond);
			if (!resultado.getError()) {
				if (duplicadoPermisoConducirVO.getCodigoTasa() != null && !duplicadoPermisoConducirVO.getCodigoTasa().isEmpty()) {
					ResultadoBean resultTasa = servicioTasa.desasignarTasaExpediente(duplicadoPermisoConducirVO.getCodigoTasa(), duplicadoPermisoConducirVO.getNumExpediente(),
							duplicadoPermisoConducirVO.getIdContrato(), TipoTasa.CuatroCuatro.getValorEnum(), idUsuario);
					if (resultTasa.getError()) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(resultTasa.getMensaje());
						return resultado;
					}
				}
				servicioPersistenciaDuplPermCond.eliminarDuplPermConducir(duplicadoPermisoConducirVO.getIdDuplicadoPermCond());
				servicioEvoDuplicadoPermisoConducir.guardar(idDuplPermCond, duplicadoPermisoConducirVO.getEstado(), EstadoTramitesInterga.Anulado.getValorEnum(), idUsuario, TipoActualizacion.MOD
						.getValorEnum());
				resultado.setMensaje("Tramite: " + duplicadoPermisoConducirVO.getNumExpediente() + " eliminado correctamente.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el tramite de duplicado permiso conducir con ID: " + idDuplPermCond + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el tramite con ID: " + idDuplPermCond);
		}
		return resultado;
	}

	private ResultadoDuplPermCondBean validarTramiteEliminar(DuplicadoPermisoConducirVO duplicadoPermisoConducirVO, Long idPermisoIntern) {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		if (duplicadoPermisoConducirVO == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos en BBDD para el id: " + idPermisoIntern);
		} else if (!EstadoTramitesInterga.Iniciado.getValorEnum().equals(duplicadoPermisoConducirVO.getEstado())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + duplicadoPermisoConducirVO.getNumExpediente() + " no se puede eliminar porque no se encuentra en estado Iniciado.");
		}
		return resultado;
	}

	@Override
	public ResultadoDuplPermCondBean getTitular(String nif, String numColegiado) {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			IntervinienteIntergaVO interviniente = servicioIntervinienteInterga.crearIntervinienteNif(nif, numColegiado);
			if (interviniente != null) {
				IntervinienteIntergaDto intervinienteDto = conversorDuplPermCond.transform(interviniente, IntervinienteIntergaDto.class);
				resultado.setTitular(intervinienteDto);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el titular con nif: " + nif + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener el titular con nif: " + nif);
		}
		return resultado;
	}

	@Override
	public void finalizarConError(Long idDuplPermCond, Long idUsuario, String respuesta) {
		try {
			DuplicadoPermisoConducirVO duplicado = servicioPersistenciaDuplPermCond.getDuplicadoPermisoConducir(idDuplPermCond);
			if (duplicado != null) {
				String estadoAnterior = duplicado.getEstado();
				duplicado.setEstado(EstadoTramitesInterga.Finalizado_Error.getValorEnum());
				duplicado.setRespuesta(respuesta);
				servicioPersistenciaDuplPermCond.actualizar(duplicado);
				servicioEvoDuplicadoPermisoConducir.guardar(idDuplPermCond, estadoAnterior, EstadoTramitesInterga.Finalizado_Error.getValorEnum(), idUsuario, TipoActualizacion.MOD.getValorEnum());
			}
		} catch (Exception e) {
			log.error("Error al Finalizar con Error el duplicado permiso conducir", e);
		}
	}

	@Override
	public ResultadoDuplPermCondBean generarMandato(BigDecimal numExpediente, Long idContrato, Long idUsuario) {
		ResultadoDuplPermCondBean result = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			ResultadoIntergaBean resultadoInterga = servicioInterga.generarMandato(numExpediente, idContrato, idUsuario, TipoTramiteTrafico.DuplicadoPermisoConducir.getValorEnum());
			if (resultadoInterga != null) {
				if (resultadoInterga.getError()) {
					result.setError(Boolean.TRUE);
				}
				result.setMensaje(resultadoInterga.getMensaje());
			}
		} catch (Exception e) {
			log.error("Existen errores en la solicitud de impresión para el mandato de duplicado permiso conducir.", e);
			result.setMensaje("Existen errores en la solicitud de impresión.");
			result.setError(Boolean.TRUE);
		}
		return result;
	}

	@Override
	public ResultadoDuplPermCondBean eliminarMandato(BigDecimal numExpediente) {
		ResultadoDuplPermCondBean result = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			ResultadoIntergaBean resultadoInterga = servicioInterga.eliminarMandato(numExpediente, TipoTramiteTrafico.DuplicadoPermisoConducir.getValorEnum());
			if (resultadoInterga != null && resultadoInterga.getError()) {
				result.setError(Boolean.TRUE);
				result.setMensaje(resultadoInterga.getMensaje());
			}
		} catch (Exception e) {
			log.error("Existen errores a la hora de eliminar el mandato del duplicado permiso conducir.", e);
			result.setMensaje("Existen errores a la hora de eliminar el mandato.");
			result.setError(Boolean.TRUE);
		}
		return result;
	}

	@Override
	public ResultadoDuplPermCondBean eliminarDocAportada(String nombreDocumento, String tipoDocumento, BigDecimal numExpediente) {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			gestorDocumentos.borraFicheroSiExisteConExtension(ConstantesGestorFicheros.DUPLICADO_PERMISO_CONDUCIR, ConstantesGestorFicheros.APORTADO, utiles.transformExpedienteInterga(numExpediente
					.toString()), nombreDocumento, ConstantesGestorFicheros.EXTENSION_PDF);
			DuplicadoPermisoConducirVO duplicado = servicioPersistenciaDuplPermCond.getDuplPermCondPorNumExpediente(numExpediente);
			if (TiposDocDuplicadosPermisos.DECLARACION_TITULAR.getNombreEnum().equals(tipoDocumento)) {
				duplicado.setEstadoDeclaracionTitular(EstadosDocumentos.Eliminado.getValorEnum());
			} else if (TiposDocDuplicadosPermisos.MANDATO.getNombreEnum().equals(tipoDocumento)) {
				duplicado.setEstadoMandato(EstadosDocumentos.Eliminado.getValorEnum());
			} else if (TiposDocDuplicadosPermisos.OTRO.getNombreEnum().equals(tipoDocumento)) {
				duplicado.setEstadoOtroDocumento(EstadosDocumentos.Eliminado.getValorEnum());
			}
			servicioPersistenciaDuplPermCond.actualizar(duplicado);
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de eliminar el documento para el tramite de duplicado permiso conducir con expediente: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de eliminar el documento para tramitar el tramite con expediente: " + numExpediente);
		}
		return resultado;
	}

	@Override
	public ResultadoDuplPermCondBean descargarFichero(String nombreDocumento, Long idUsuario) {
		ResultadoDuplPermCondBean result = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			ResultadoIntergaBean resultadoInterga = servicioInterga.descargarFichero(nombreDocumento, idUsuario);
			if (resultadoInterga != null && !resultadoInterga.getError()) {
				result.setFichero(resultadoInterga.getFichero());
			} else {
				result.setError(Boolean.TRUE);
				result.setMensaje("No se ha podido descargar el fichero.");
			}
		} catch (Exception e) {
			log.error("Existen errores a la hora de descargar el fichero del duplicado permiso conducir.", e);
			result.setMensaje("Existen errores a la hora de descargar el fichero.");
			result.setError(Boolean.TRUE);
		}
		return result;
	}

	@Override
	public ResultadoDuplPermCondBean descargarMandato(BigDecimal numExpediente, Long idUsuario) {
		ResultadoDuplPermCondBean result = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			ResultadoIntergaBean resultadoInterga = servicioInterga.descargarMandato(numExpediente, idUsuario, TipoTramiteTrafico.DuplicadoPermisoConducir.getValorEnum());
			if (resultadoInterga != null && !resultadoInterga.getError()) {
				result.setFichero(resultadoInterga.getFichero());
			} else {
				result.setError(Boolean.TRUE);
			}
		} catch (Exception e) {
			log.error("Existen errores a la hora de descargar el mandato del duplicado permiso conducir.", e);
			result.setMensaje("Existen errores a la hora de descargar el mandato.");
			result.setError(Boolean.TRUE);
		}
		return result;
	}

	@Override
	public ResultadoDuplPermCondBean subirDocAportada(Long idDuplicadoPermisoConducir, String tipoDocumento, File fichero, String ficheroFileName, Long idUsuario) {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			DuplicadoPermisoConducirVO duplicadoPermisoConducirVO = servicioPersistenciaDuplPermCond.getDuplicadoPermisoConducir(idDuplicadoPermisoConducir);
			resultado = validarSubirDocTramite(duplicadoPermisoConducirVO, idDuplicadoPermisoConducir, fichero, ficheroFileName, tipoDocumento);
			if (!resultado.getError()) {
				String nombreDocumento = "";
				if (TiposDocDuplicadosPermisos.MANDATO.toString().equals(tipoDocumento)) {
					if (EstadosDocumentos.Subido.getValorEnum().equals(duplicadoPermisoConducirVO.getEstadoMandato())) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("El Mandato Específico ya está subido, si quiere modificarlo, elimine el antiguo y suba el nuevo.");
					} else {
						nombreDocumento = TipoDocumentoImpresiones.MandatoEspecifico.getNombreDocumento() + "_" + duplicadoPermisoConducirVO.getNumExpediente();
						duplicadoPermisoConducirVO.setEstadoMandato(EstadosDocumentos.Subido.getValorEnum());
					}
				} else if (TiposDocDuplicadosPermisos.DECLARACION_TITULAR.toString().equals(tipoDocumento)) {
					if (EstadosDocumentos.Subido.getValorEnum().equals(duplicadoPermisoConducirVO.getEstadoDeclaracionTitular())) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("La Declaración de Responsabilidad del Titular ya está subido, si quiere modificarlo, elimine el antiguo y suba el nuevo.");
					} else {
						nombreDocumento = TipoDocumentoImpresiones.DeclaracionResponsabilidadTitularConducir.getNombreDocumento() + "_" + duplicadoPermisoConducirVO.getNumExpediente();
						duplicadoPermisoConducirVO.setEstadoDeclaracionTitular(EstadosDocumentos.Subido.getValorEnum());
					}
				} else if (TiposDocDuplicadosPermisos.OTRO.toString().equals(tipoDocumento)) {
					if (EstadosDocumentos.Subido.getValorEnum().equals(duplicadoPermisoConducirVO.getEstadoOtroDocumento())) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Ya existe un documento de tipo 'Otro' subido, si quiere modificarlo, elimine el antiguo y suba el nuevo.");
					} else {
						nombreDocumento = TipoDocumentoImpresiones.OtroDocumentoDPC.getNombreDocumento() + "_" + duplicadoPermisoConducirVO.getNumExpediente();
						duplicadoPermisoConducirVO.setEstadoDeclaracionTitular(EstadosDocumentos.Subido.getValorEnum());
					}
				}

				if (!resultado.getError()) {
					gestorDocumentos.guardarFichero(ConstantesGestorFicheros.DUPLICADO_PERMISO_CONDUCIR, ConstantesGestorFicheros.APORTADO, utiles.transformExpedienteInterga(duplicadoPermisoConducirVO
							.getNumExpediente().toString()), nombreDocumento, ConstantesGestorFicheros.EXTENSION_PDF, fichero);
					servicioPersistenciaDuplPermCond.actualizar(duplicadoPermisoConducirVO);
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de subir la documentacion para el tramite con id: " + idDuplicadoPermisoConducir + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de subir la documentacion para el tramite con id: " + idDuplicadoPermisoConducir);
		}
		return resultado;
	}

	private ResultadoDuplPermCondBean validarSubirDocTramite(DuplicadoPermisoConducirVO duplicadoPermisoConducirVO, Long idDuplicadoPermisoConducir, File fichero, String ficheroFileName,
			String tipoDocumento) {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		if (duplicadoPermisoConducirVO == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos en BBDD para el id: " + idDuplicadoPermisoConducir);
		} else if (fichero == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe seleccionar un documento para el tramite: " + duplicadoPermisoConducirVO.getNumExpediente());
		} else if (StringUtils.isBlank(tipoDocumento)) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe seleccionar un tipo de documento para el tramite: " + duplicadoPermisoConducirVO.getNumExpediente());
		} else {
			String extension = utiles.dameExtension(ficheroFileName, Boolean.FALSE);
			if (extension == null || extension.isEmpty() || !extension.equals("pdf")) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El documento para el tramite: " + duplicadoPermisoConducirVO.getNumExpediente() + " no se puede subir porque solo se permiten documento con extension PDF.");
			}
		}
		return resultado;
	}

	private ResultadoDuplPermCondBean validarDocumentacion(DuplicadoPermisoConducirVO duplicadoPermisoConducirVO) {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		boolean mandato = false;
		boolean declTitular = false;

		if (!EstadosDocumentos.Subido.getValorEnum().equals(duplicadoPermisoConducirVO.getEstadoMandato()) && !EstadosDocumentos.Enviado.getValorEnum().equals(duplicadoPermisoConducirVO
				.getEstadoMandato()) && !EstadosDocumentos.Error_Envio.getValorEnum().equals(duplicadoPermisoConducirVO.getEstadoMandato())) {
			mandato = true;
		}

		if (!EstadosDocumentos.Subido.getValorEnum().equals(duplicadoPermisoConducirVO.getEstadoDeclaracionTitular()) && !EstadosDocumentos.Enviado.getValorEnum().equals(duplicadoPermisoConducirVO
				.getEstadoDeclaracionTitular()) && !EstadosDocumentos.Error_Envio.getValorEnum().equals(duplicadoPermisoConducirVO.getEstadoDeclaracionTitular())) {
			declTitular = true;
		}

		if (mandato || declTitular) {
			String mensaje = "Debe subir todos los documentos obligatorios para el trámite: " + duplicadoPermisoConducirVO.getNumExpediente() + ". ";
			mensaje += "Los documentos obligatorios que faltan por subir son:";
			if (mandato) {
				mensaje += " - Mandato Específico";
			}
			if (declTitular) {
				mensaje += " - Declaración de Responsabilidad del Titular";
			}
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(mensaje);
		}
		return resultado;
	}

	@Override
	public ResultadoDuplPermCondBean imprimir(Long idDuplicadoPermisoConducir, Long idUsuario) {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			DuplicadoPermisoConducirVO duplicadoPermisoConducirVO = servicioPersistenciaDuplPermCond.getDuplicadoPermisoConducir(idDuplicadoPermisoConducir);
			resultado = validarTramiteImprimir(duplicadoPermisoConducirVO, idDuplicadoPermisoConducir);
			if (!resultado.getError()) {
				FileResultBean fileResult = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.DUPLICADO_PERMISO_CONDUCIR, ConstantesGestorFicheros.RECIBIDO, utiles
						.transformExpedienteInterga(duplicadoPermisoConducirVO.getNumExpediente().toString()), duplicadoPermisoConducirVO.getNumExpediente().toString(),
						ConstantesGestorFicheros.EXTENSION_PDF);
				if (fileResult != null && fileResult.getFile() != null) {
					servicioPersistenciaDuplPermCond.actualizarEstadoImpresion(idDuplicadoPermisoConducir, EstadoTramitesInterga.Impresion_OK.getValorEnum());
					servicioEvoDuplicadoPermisoConducir.guardar(idDuplicadoPermisoConducir, duplicadoPermisoConducirVO.getEstadoImpresion(), EstadoTramitesInterga.Impresion_OK.getValorEnum(),
							idUsuario, TipoActualizacion.MOD.getValorEnum());
					resultado.setFichero(fileResult.getFile());
					resultado.setNombreFichero(duplicadoPermisoConducirVO.getNumExpediente().toString() + ConstantesGestorFicheros.EXTENSION_PDF);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se ha encontrado el PDF para la impresion del expediente: " + duplicadoPermisoConducirVO.getNumExpediente());
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de imprimir el tramite de duplicado permiso conducir con ID: " + idDuplicadoPermisoConducir + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir el tramite con ID: " + idDuplicadoPermisoConducir);
		}
		return resultado;
	}

	private ResultadoDuplPermCondBean validarTramiteImprimir(DuplicadoPermisoConducirVO duplicadoPermisoConducirVO, Long idDuplicadoPermisoConducir) {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		if (duplicadoPermisoConducirVO == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos en BBDD para el id: " + idDuplicadoPermisoConducir);
		} else if (!EstadoTramitesInterga.Finalizado.getValorEnum().equals(duplicadoPermisoConducirVO.getEstado())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + duplicadoPermisoConducirVO.getNumExpediente() + " no se puede imprimir porque no se encuentra en estado Finalizado.");
		} else if (!EstadoTramitesInterga.Doc_Recibida.getValorEnum().equals(duplicadoPermisoConducirVO.getEstadoImpresion())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + duplicadoPermisoConducirVO.getNumExpediente() + " no se puede imprimir porque no se encuentra en estado Documentacion Subida.");
		}
		return resultado;
	}

	@Override
	public ResultadoDuplPermCondBean firmarDeclYSolicitud(DuplicadoPermisoConducirDto duplicadoPermisoConducirDto, Long idUsuario) {
		ResultadoDuplPermCondBean result = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			String[] numExpedientes = new String[1];
			numExpedientes[0] = duplicadoPermisoConducirDto.getNumExpediente().toString();

			ResultadoImpresionBean resultadoImpDecl = crearImpresionDeclaracion(numExpedientes, duplicadoPermisoConducirDto.getIdContrato(), idUsuario);
			if (resultadoImpDecl.getError()) {
				result.setError(Boolean.TRUE);
				result.setMensaje(resultadoImpDecl.getMensaje());
			} else {
				ResultadoImpresionBean resultadoImpSol = crearImpresionSolicitud(numExpedientes, duplicadoPermisoConducirDto.getIdContrato(), idUsuario);
				if (resultadoImpSol.getError()) {
					result.setError(Boolean.TRUE);
					result.setMensaje(resultadoImpSol.getMensaje());
					servicioCola.eliminarCola(new BigDecimal(resultadoImpDecl.getIdImpresion()), ProcesosEnum.IMP_DECLARACIONES.getNombreEnum(), gestorPropiedades.valorPropertie(NOMBRE_HOST));
				} else {
					servicioPersistenciaDuplPermCond.actualizarEstado(duplicadoPermisoConducirDto.getIdDuplicadoPermCond(), EstadoTramitesInterga.Pendiente_Firma_Documentos.getValorEnum());
					servicioEvoDuplicadoPermisoConducir.guardar(duplicadoPermisoConducirDto.getIdDuplicadoPermCond(), duplicadoPermisoConducirDto.getEstado(),
							EstadoTramitesInterga.Pendiente_Firma_Documentos.getValorEnum(), idUsuario, TipoActualizacion.MOD.getValorEnum());
					result.setMensaje("Su solicitud de envío de documentación se está procesando.");
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de enviar la documentación de duplicado permiso conducir con ID: " + duplicadoPermisoConducirDto.getIdDuplicadoPermCond() + ", error: ", e);
			result.setError(Boolean.TRUE);
			result.setMensaje("Ha sucedido un error a la hora de declaración de duplicado permiso conducir con ID: " + duplicadoPermisoConducirDto.getIdDuplicadoPermCond());
		}
		return result;
	}

	private ResultadoImpresionBean crearImpresionDeclaracion(String[] numExpedientes, Long idContrato, Long idUsuario) {
		ResultadoImpresionBean resultado = new ResultadoImpresionBean(Boolean.FALSE);
		try {
			resultado = servicioImpresionDocumentos.crearImpresion(numExpedientes, idContrato, idUsuario, TipoImpreso.DeclaracionResponsabilidadColegiadoConducir.toString(),
					TipoTramiteTrafico.DuplicadoPermisoConducir.getValorEnum(), null);
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de enviar la declaración del duplicado permiso conducir, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de declaración del duplicado permiso conducir");
		}
		return resultado;
	}

	private ResultadoImpresionBean crearImpresionSolicitud(String[] numExpedientes, Long idContrato, Long idUsuario) {
		ResultadoImpresionBean resultado = new ResultadoImpresionBean(Boolean.FALSE);
		try {
			resultado = servicioImpresionDocumentos.crearImpresion(numExpedientes, idContrato, idUsuario, TipoImpreso.SolicitudDuplicadoPermisoConducir.toString(),
					TipoTramiteTrafico.DuplicadoPermisoConducir.getValorEnum(), null);
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de enviar la solicitud del duplicado permiso conducir, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitud del duplicado permiso conducir");
		}
		return resultado;
	}

	@Override
	public ResultadoDuplPermCondBean firmadaDeclaracion(BigDecimal numExpediente, Long idUsuario) {
		ResultadoDuplPermCondBean result = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			DuplicadoPermisoConducirVO duplicadoPermisoConducirVO = servicioPersistenciaDuplPermCond.getDuplPermCondPorNumExpediente(numExpediente);
			duplicadoPermisoConducirVO.setEstadoDeclaracion(EstadosDocumentos.Firmado.getValorEnum());
			if (StringUtils.isNotBlank(duplicadoPermisoConducirVO.getEstadoSolicitud()) && EstadosDocumentos.Firmado.getValorEnum().equals(duplicadoPermisoConducirVO.getEstadoSolicitud())) {
				servicioEvoDuplicadoPermisoConducir.guardar(duplicadoPermisoConducirVO.getIdDuplicadoPermCond(), duplicadoPermisoConducirVO.getEstado(), EstadoTramitesInterga.Documentacion_Firmada
						.getValorEnum(), idUsuario, TipoActualizacion.MOD.getValorEnum());
				duplicadoPermisoConducirVO.setEstado(EstadoTramitesInterga.Documentacion_Firmada.getValorEnum());
			}
			servicioPersistenciaDuplPermCond.actualizar(duplicadoPermisoConducirVO);
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de actualizar la declaración firmada, error: ", e);
			result.setError(Boolean.TRUE);
			result.setMensaje("Ha sucedido un error a la hora de actualizar la declaración firmada");
		}
		return result;
	}

	@Override
	public ResultadoDuplPermCondBean firmadaSolicitud(BigDecimal numExpediente, Long idUsuario) {
		ResultadoDuplPermCondBean result = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			DuplicadoPermisoConducirVO duplicadoPermisoConducirVO = servicioPersistenciaDuplPermCond.getDuplPermCondPorNumExpediente(numExpediente);
			duplicadoPermisoConducirVO.setEstadoSolicitud(EstadosDocumentos.Firmado.getValorEnum());
			if (StringUtils.isNotBlank(duplicadoPermisoConducirVO.getEstadoDeclaracion()) && EstadosDocumentos.Firmado.getValorEnum().equals(duplicadoPermisoConducirVO.getEstadoDeclaracion())) {
				servicioEvoDuplicadoPermisoConducir.guardar(duplicadoPermisoConducirVO.getIdDuplicadoPermCond(), duplicadoPermisoConducirVO.getEstado(), EstadoTramitesInterga.Documentacion_Firmada
						.getValorEnum(), idUsuario, TipoActualizacion.MOD.getValorEnum());
				duplicadoPermisoConducirVO.setEstado(EstadoTramitesInterga.Documentacion_Firmada.getValorEnum());
			}
			servicioPersistenciaDuplPermCond.actualizar(duplicadoPermisoConducirVO);
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de actualizar la declaración firmada, error: ", e);
			result.setError(Boolean.TRUE);
			result.setMensaje("Ha sucedido un error a la hora de actualizar la declaración firmada");
		}
		return result;
	}

	@Override
	public ResultadoDuplPermCondBean errorFirmaDocumentos(BigDecimal numExpediente, Long idUsuario) {
		ResultadoDuplPermCondBean result = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			DuplicadoPermisoConducirVO duplicadoPermisoConducirVO = servicioPersistenciaDuplPermCond.getDuplPermCondPorNumExpediente(numExpediente);
			servicioPersistenciaDuplPermCond.actualizarEstado(duplicadoPermisoConducirVO.getIdDuplicadoPermCond(), EstadoTramitesInterga.Error_Firma_Documentos.getValorEnum());
			servicioEvoDuplicadoPermisoConducir.guardar(duplicadoPermisoConducirVO.getIdDuplicadoPermCond(), duplicadoPermisoConducirVO.getEstado(), EstadoTramitesInterga.Error_Firma_Documentos
					.getValorEnum(), idUsuario, TipoActualizacion.MOD.getValorEnum());
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado a error firma documentos, error: ", e);
			result.setError(Boolean.TRUE);
			result.setMensaje("Ha sucedido un error a la hora de cambiar el estado a error firma documentos");
		}
		return result;
	}

	@Override
	public ResultadoDuplPermCondBean enviarDocumentacion(Long idDuplicadoPermisoCond, Long idUsuario) {
		ResultadoDuplPermCondBean result = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			DuplicadoPermisoConducirVO duplicadoPermisoConducirVO = servicioPersistenciaDuplPermCond.getDuplicadoPermisoConducir(idDuplicadoPermisoCond);
			result = validarEnviarDocumentacion(duplicadoPermisoConducirVO, idDuplicadoPermisoCond);
			if (!result.getError()) {
				result = servicioDuplPermConducirRestWS.enviarDocumentos(duplicadoPermisoConducirVO, idUsuario);
				if (result != null && !result.getError()) {
					duplicadoPermisoConducirVO.setEstado(EstadoTramitesInterga.Pendiente_Repuesta_DGT.getValorEnum());
					duplicadoPermisoConducirVO.setEstadoImpresion(EstadoTramitesInterga.Doc_Subida.getValorEnum());

					duplicadoPermisoConducirVO.setEstadoDeclaracion(result.getEstadoDeclaracion());
					duplicadoPermisoConducirVO.setEstadoDeclaracionTitular(result.getEstadoDeclaracionTitular());
					duplicadoPermisoConducirVO.setEstadoSolicitud(result.getEstadoSolicitud());
					duplicadoPermisoConducirVO.setEstadoOtroDocumento(result.getEstadoOtroDocumento());
					duplicadoPermisoConducirVO.setEstadoMandato(result.getEstadoMandato());

					servicioPersistenciaDuplPermCond.actualizar(duplicadoPermisoConducirVO);
					servicioEvoDuplicadoPermisoConducir.guardar(idDuplicadoPermisoCond, duplicadoPermisoConducirVO.getEstado(), EstadoTramitesInterga.Pendiente_Repuesta_DGT.getValorEnum(), idUsuario,
							TipoActualizacion.MOD.getValorEnum());

					result.setMensaje("Documentación enviada correctamente.");
				} else {
					result.setError(Boolean.TRUE);
					result.setMensaje("No se han podido enviar los documentos pendientes.");
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de enviar la documentación de duplicado permiso conducir con ID: " + idDuplicadoPermisoCond + ", error: ", e);
			result.setError(Boolean.TRUE);
			result.setMensaje("Ha sucedido un error a la hora de documentación de duplicado permiso conducir con ID: " + idDuplicadoPermisoCond);
		}
		return result;
	}

	private ResultadoDuplPermCondBean validarEnviarDocumentacion(DuplicadoPermisoConducirVO duplicadoPermisoConducirVO, Long idDuplicadoPermisoConducir) {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		if (duplicadoPermisoConducirVO == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos en BBDD para el id: " + idDuplicadoPermisoConducir);
		} else if (!EstadoTramitesInterga.Pendiente_Envio_Documentos.getValorEnum().equals(duplicadoPermisoConducirVO.getEstado())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + duplicadoPermisoConducirVO.getNumExpediente() + " no se puede enviar documentación porque no se encuentra en estado Pendiente Envío Documentos.");
		} else if (!EstadoTramitesInterga.Doc_Parcialmente_Subida.getValorEnum().equals(duplicadoPermisoConducirVO.getEstadoImpresion())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + duplicadoPermisoConducirVO.getNumExpediente()
					+ " no se puede enviar documentación porque no se encuentra en estado Documentación Parcialmente Subida.");
		} else {
			resultado = validarDocumentacion(duplicadoPermisoConducirVO);
		}
		return resultado;
	}

	@Override
	public ResultadoDuplPermCondBean descargarDocAportada(String nombreDocumento, BigDecimal numExpediente) {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			resultado = servicioDuplPermConducirRestWS.descargarDocAportada(nombreDocumento, numExpediente);
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de descargar la documentación aportada para el tramite de duplicado permiso conducir con expediente: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de descargar documentación aportada para tramitar el tramite con expediente: " + numExpediente);
		}
		return resultado;
	}
}
