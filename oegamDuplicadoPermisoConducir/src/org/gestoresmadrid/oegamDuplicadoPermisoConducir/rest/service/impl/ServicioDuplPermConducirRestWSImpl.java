package org.gestoresmadrid.oegamDuplicadoPermisoConducir.rest.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.CorreoContratoTramiteVO;
import org.gestoresmadrid.core.impresion.model.enumerados.EstadosDocumentos;
import org.gestoresmadrid.core.impresion.model.enumerados.TipoDocumentoImpresiones;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.enumerados.TiposDuplicadosPermisos;
import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.vo.DuplicadoPermisoConducirVO;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoTramitesInterga;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.TipoTramiteInterga;
import org.gestoresmadrid.core.trafico.interga.model.vo.IntervinienteIntergaVO;
import org.gestoresmadrid.oegamComun.correo.service.ServicioComunCorreo;
import org.gestoresmadrid.oegamComun.credito.service.ServicioComunCredito;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunDireccion;
import org.gestoresmadrid.oegamComun.utiles.UtilidadesNIFValidator;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.rest.service.ServicioDuplPermConducirRestWS;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.service.ServicioDuplicadoPermCond;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.service.ServicioEvoDuplicadoPermisoConducir;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.service.ServicioPersistenciaDuplPermCond;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.bean.ResultadoDuplPermCondBean;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionDocumentos;
import org.gestoresmadrid.oegamInterga.rest.service.ServicioIntergaRestWS;
import org.gestoresmadrid.oegamInterga.restWS.request.FileRequest;
import org.gestoresmadrid.oegamInterga.restWS.request.Files;
import org.gestoresmadrid.oegamInterga.restWS.request.SendFilesRequest;
import org.gestoresmadrid.oegamInterga.restWS.request.UpdateRequest;
import org.gestoresmadrid.oegamInterga.service.ServicioIntervinienteInterga;
import org.gestoresmadrid.oegamInterga.view.bean.ResultadoIntergaBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioDuplPermConducirRestWSImpl implements ServicioDuplPermConducirRestWS {

	private static final long serialVersionUID = -1434847309722685638L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioDuplPermConducirRestWSImpl.class);

	@Autowired
	ServicioIntergaRestWS servicioIntergaRestWS;

	@Autowired
	ServicioPersistenciaDuplPermCond servicioPersistenciaDuplPermCond;

	@Autowired
	ServicioEvoDuplicadoPermisoConducir servicioEvoDuplicadoPermisoConducir;

	@Autowired
	ServicioDuplicadoPermCond servicioDuplicadoPermCond;

	@Autowired
	ServicioComunCredito servicioComunCredito;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilidadesNIFValidator utilidadesNIFValidator;

	@Autowired
	ServicioImpresionDocumentos servicioImpresionDocumentos;

	@Autowired
	ServicioIntervinienteInterga servicioIntervinienteInterga;

	@Autowired
	ServicioComunDireccion servicioComunDireccion;

	@Autowired
	ServicioComunCorreo servicioCorreo;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	Utiles utiles;

	@Override
	public ResultadoIntergaBean enviarRest(Long idDuplicadoPermCond, Long idUsuario) {
		ResultadoIntergaBean resultado = new ResultadoIntergaBean(Boolean.FALSE);
		boolean evolucion = false;
		try {
			DuplicadoPermisoConducirVO duplicado = servicioPersistenciaDuplPermCond.getDuplicadoPermisoConducir(idDuplicadoPermCond);
			if (duplicado != null) {
				String estadoAnterior = duplicado.getEstado();
				String estadoImpAnterior = duplicado.getEstadoImpresion();
				SendFilesRequest request = crearRequestEnvio(duplicado);
				resultado = servicioIntergaRestWS.enviar(request);
				if (resultado != null && !resultado.getError()) {
					evolucion = true;
					duplicado.setFechaPresentacion(new Date());
					ResultadoDuplPermCondBean resultadoDoc = enviarDocumentos(duplicado, idUsuario);
					if (resultadoDoc != null && !resultadoDoc.getError()) {
						duplicado.setEstado(EstadoTramitesInterga.Pendiente_Repuesta_DGT.getValorEnum());
						duplicado.setEstadoImpresion(EstadoTramitesInterga.Doc_Subida.getValorEnum());
					} else {
						duplicado.setEstado(EstadoTramitesInterga.Pendiente_Envio_Documentos.getValorEnum());
						duplicado.setEstadoImpresion(EstadoTramitesInterga.Doc_Parcialmente_Subida.getValorEnum());
						if (resultadoDoc.getListaMensajes() != null && !resultadoDoc.getListaMensajes().isEmpty()) {
							String respuesta = "";
							for (String mensaje : resultadoDoc.getListaMensajes()) {
								respuesta += mensaje;
							}
							duplicado.setRespuesta(respuesta);
						}
					}
					duplicado.setRespuesta(resultado.getMensaje());
					servicioPersistenciaDuplPermCond.actualizar(duplicado);
					if (evolucion) {
						servicioEvoDuplicadoPermisoConducir.guardar(idDuplicadoPermCond, estadoAnterior, duplicado.getEstado(), idUsuario, TipoActualizacion.MOD.getValorEnum());
						servicioEvoDuplicadoPermisoConducir.guardar(idDuplicadoPermCond, estadoImpAnterior, duplicado.getEstadoImpresion(), idUsuario, TipoActualizacion.MOD.getValorEnum());
					}
				} else {
					resultado.setError(Boolean.TRUE);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se encuentra el duplicado permiso conducir en base de datos");
			}
		} catch (Exception e) {
			log.error("Error al enviar la solicitud de duplicado permiso conducir, error:", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al enviar el duplicado permiso conducir");
		}
		return resultado;
	}

	@Override
	public ResultadoDuplPermCondBean enviarDocumentos(DuplicadoPermisoConducirVO duplicadoPermisoConducirVO, Long idUsuario) {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);

		if (EstadosDocumentos.Subido.getValorEnum().equals(duplicadoPermisoConducirVO.getEstadoMandato())
				|| EstadosDocumentos.Error_Envio.getValorEnum().equals(duplicadoPermisoConducirVO.getEstadoMandato())) {
			String nombreDocumento = TipoDocumentoImpresiones.MandatoEspecifico.getNombreDocumento() + "_" + duplicadoPermisoConducirVO.getNumExpediente().toString();
			ResultadoDuplPermCondBean resultadoDes = descargarDocAportada(nombreDocumento, duplicadoPermisoConducirVO.getNumExpediente());
			if (resultadoDes != null && !resultadoDes.getError() && resultadoDes.getFichero() != null) {
				ResultadoIntergaBean resultadoSub = enviarMandato(duplicadoPermisoConducirVO, resultadoDes.getFichero(), idUsuario);
				if (resultadoSub != null && resultadoSub.getError()) {
					duplicadoPermisoConducirVO.setEstadoMandato(EstadosDocumentos.Error_Envio.getValorEnum());
					resultado.setError(Boolean.TRUE);
					resultado.addListaMensaje("No se ha podido enviar el Mandato. ");
				} else {
					duplicadoPermisoConducirVO.setEstadoMandato(EstadosDocumentos.Enviado.getValorEnum());
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.addListaMensaje("No se ha encontrato el Mandato para enviar. ");
			}
		}

		if (EstadosDocumentos.Subido.getValorEnum().equals(duplicadoPermisoConducirVO.getEstadoDeclaracionTitular())
				|| EstadosDocumentos.Error_Envio.getValorEnum()
						.equals(duplicadoPermisoConducirVO.getEstadoDeclaracionTitular())) {
			String nombreDocumento = TipoDocumentoImpresiones.DeclaracionResponsabilidadTitularConducir.getNombreDocumento() + "_" + duplicadoPermisoConducirVO.getNumExpediente().toString();
			ResultadoDuplPermCondBean resultadoDes = descargarDocAportada(nombreDocumento, duplicadoPermisoConducirVO.getNumExpediente());
			if (resultadoDes != null && !resultadoDes.getError() && resultadoDes.getFichero() != null) {
				ResultadoIntergaBean resultadoSub = enviarDeclaracionTitular(duplicadoPermisoConducirVO, resultadoDes.getFichero(), idUsuario);
				if (resultadoSub != null && resultadoSub.getError()) {
					duplicadoPermisoConducirVO.setEstadoDeclaracionTitular(EstadosDocumentos.Error_Envio.getValorEnum());
					resultado.setError(Boolean.TRUE);
					resultado.addListaMensaje("No se ha podido enviar la Declaración de Responsabilidad del Titular. ");
				} else {
					duplicadoPermisoConducirVO.setEstadoDeclaracionTitular(EstadosDocumentos.Enviado.getValorEnum());
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.addListaMensaje("No se ha encontrato la Declaración de Responsabilidad del Titular para enviar. ");
			}
		}

		if (EstadosDocumentos.Subido.getValorEnum().equals(duplicadoPermisoConducirVO.getEstadoOtroDocumento())
				|| EstadosDocumentos.Error_Envio.getValorEnum()
						.equals(duplicadoPermisoConducirVO.getEstadoOtroDocumento())) {
			String nombreDocumento = TipoDocumentoImpresiones.OtroDocumentoDPC.getNombreDocumento() + "_" + duplicadoPermisoConducirVO.getNumExpediente().toString();
			ResultadoDuplPermCondBean resultadoDes = descargarDocAportada(nombreDocumento, duplicadoPermisoConducirVO.getNumExpediente());
			if (resultadoDes != null && !resultadoDes.getError() && resultadoDes.getFichero() != null) {
				ResultadoIntergaBean resultadoSub = enviarOtroDocumento(duplicadoPermisoConducirVO, resultadoDes.getFichero(), idUsuario);
				if (resultadoSub != null && resultadoSub.getError()) {
					duplicadoPermisoConducirVO.setEstadoOtroDocumento(EstadosDocumentos.Error_Envio.getValorEnum());
					resultado.setError(Boolean.TRUE);
					resultado.addListaMensaje("No se ha podido enviar el Otro Documento. ");
				} else {
					duplicadoPermisoConducirVO.setEstadoOtroDocumento(EstadosDocumentos.Enviado.getValorEnum());
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.addListaMensaje("No se ha encontrato el Otro Documento para enviar. ");
			}
		}

		if (EstadosDocumentos.Firmado.getValorEnum().equals(duplicadoPermisoConducirVO.getEstadoSolicitud())
				|| EstadosDocumentos.Error_Envio.getValorEnum()
						.equals(duplicadoPermisoConducirVO.getEstadoSolicitud())) {
			ResultadoIntergaBean resultadoDes = enviarSolicitud(duplicadoPermisoConducirVO, idUsuario);
			if (resultadoDes != null && resultadoDes.getError()) {
				duplicadoPermisoConducirVO.setEstadoSolicitud(EstadosDocumentos.Error_Envio.getValorEnum());
				resultado.setError(Boolean.TRUE);
				resultado.addListaMensaje("No se ha podido enviar la Solicitud. ");
			} else {
				duplicadoPermisoConducirVO.setEstadoSolicitud(EstadosDocumentos.Enviado.getValorEnum());
			}
		}

		if (EstadosDocumentos.Firmado.getValorEnum().equals(duplicadoPermisoConducirVO.getEstadoDeclaracion()) || EstadosDocumentos.Error_Envio.getValorEnum().equals(duplicadoPermisoConducirVO
				.getEstadoDeclaracion())) {
			ResultadoIntergaBean resultadoDes = enviarDeclaracion(duplicadoPermisoConducirVO, idUsuario);
			if (resultadoDes != null && resultadoDes.getError()) {
				duplicadoPermisoConducirVO.setEstadoDeclaracion(EstadosDocumentos.Error_Envio.getValorEnum());
				resultado.setError(Boolean.TRUE);
				resultado.addListaMensaje("No se ha podido enviar la Declaración de Responsabilidad del Colegiado. ");
			} else {
				duplicadoPermisoConducirVO.setEstadoDeclaracion(EstadosDocumentos.Enviado.getValorEnum());
			}
		}

		resultado.setEstadoDeclaracion(duplicadoPermisoConducirVO.getEstadoDeclaracion());
		resultado.setEstadoDeclaracionTitular(duplicadoPermisoConducirVO.getEstadoDeclaracionTitular());
		resultado.setEstadoOtroDocumento(duplicadoPermisoConducirVO.getEstadoOtroDocumento());
		resultado.setEstadoMandato(duplicadoPermisoConducirVO.getEstadoMandato());
		resultado.setEstadoSolicitud(duplicadoPermisoConducirVO.getEstadoSolicitud());

		// Si no hay errores comprobamos que se los estados se han modificado y se han enviado bien los documentos.
		if (resultado != null && !resultado.getError()) {
			boolean enviados = documentacionEnviada(duplicadoPermisoConducirVO);
			if (!enviados) {
				resultado.setError(Boolean.TRUE);
				resultado.addListaMensaje("No se han enviado todos los documentos. ");
			}
		}

		return resultado;
	}

	@Override
	public ResultadoDuplPermCondBean descargarDocAportada(String nombreDocumento, BigDecimal numExpediente) {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			FileResultBean fileResult = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.DUPLICADO_PERMISO_CONDUCIR, ConstantesGestorFicheros.APORTADO, utiles
					.transformExpedienteInterga(numExpediente.toString()), nombreDocumento, ConstantesGestorFicheros.EXTENSION_PDF);
			if (fileResult != null && fileResult.getFile() != null) {
				resultado.setFichero(fileResult.getFile());
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha encontrado el PDF para la descargar de la documentación aportada del expediente: " + numExpediente);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de descargar la documentación aportada para el tramite de duplicado permiso conducir con expediente: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de descargar documentación aportada para tramitar el tramite con expediente: " + numExpediente);
		}
		return resultado;
	}

	@Override
	public ResultadoIntergaBean enviarDeclaracion(DuplicadoPermisoConducirVO duplicado, Long idUsuario) {
		ResultadoIntergaBean result = new ResultadoIntergaBean(Boolean.FALSE);
		try {
			File fichero = servicioImpresionDocumentos.descargarFichero(TipoDocumentoImpresiones.DeclaracionResponsabilidadColegiadoConducir.getNombreDocumento() + "_" + duplicado.getNumExpediente()
					.toString(), idUsuario);
			if (fichero != null) {
				result = subidaDocumentacion(duplicado, fichero, ServicioIntergaRestWS.PDF_ESCANEADO);
			} else {
				result.setMensaje("No se ha encontrado la Declaración.");
				result.setError(Boolean.TRUE);
			}
		} catch (Exception e) {
			result.setMensaje("Error al enviar la documentación.");
			result.setError(Boolean.TRUE);
			log.error("Error al enviar la documentación", e);
		}
		return result;
	}

	@Override
	public ResultadoIntergaBean enviarSolicitud(DuplicadoPermisoConducirVO duplicado, Long idUsuario) {
		ResultadoIntergaBean result = new ResultadoIntergaBean(Boolean.FALSE);
		try {
			File fichero = servicioImpresionDocumentos.descargarFichero(TipoDocumentoImpresiones.SolicitudDuplicadoPermisoConducir.getNombreDocumento() + "_" + duplicado.getNumExpediente().toString(),
					idUsuario);
			if (fichero != null) {
				result = subidaDocumentacion(duplicado, fichero, ServicioIntergaRestWS.PDF_SOLICITUD);
			} else {
				result.setMensaje("No se ha encontrado la Solicitud.");
				result.setError(Boolean.TRUE);
			}
		} catch (Exception e) {
			result.setMensaje("Error al enviar la documentación.");
			result.setError(Boolean.TRUE);
			log.error("Error al enviar la documentación", e);
		}
		return result;
	}

	@Override
	public ResultadoIntergaBean enviarMandato(DuplicadoPermisoConducirVO duplicado, File fichero, Long idUsuario) {
		ResultadoIntergaBean result = new ResultadoIntergaBean(Boolean.FALSE);
		try {
			result = subidaDocumentacion(duplicado, fichero, ServicioIntergaRestWS.PDF_MANDATO);
		} catch (Exception e) {
			result.setMensaje("Error al enviar el mandato.");
			result.setError(Boolean.TRUE);
			log.error("Error al enviar el mandato", e);
		}
		return result;
	}

	@Override
	public ResultadoIntergaBean enviarDeclaracionTitular(DuplicadoPermisoConducirVO duplicado, File fichero, Long idUsuario) {
		ResultadoIntergaBean result = new ResultadoIntergaBean(Boolean.FALSE);
		try {
			result = subidaDocumentacion(duplicado, fichero, ServicioIntergaRestWS.PDF_DECLARACION_GESTOR);
		} catch (Exception e) {
			result.setMensaje("Error al enviar la declaración de responsabilidad del titular.");
			result.setError(Boolean.TRUE);
			log.error("Error al enviar la declaración de responsabilidad del titular", e);
		}
		return result;
	}

	@Override
	public ResultadoIntergaBean enviarOtroDocumento(DuplicadoPermisoConducirVO duplicado, File fichero, Long idUsuario) {
		ResultadoIntergaBean result = new ResultadoIntergaBean(Boolean.FALSE);
		try {
			result = subidaDocumentacion(duplicado, fichero, ServicioIntergaRestWS.PDF_LIBRE_DISPOSICION);
		} catch (Exception e) {
			result.setMensaje("Error al enviar la otra documentación.");
			result.setError(Boolean.TRUE);
			log.error("Error al enviar la otra documentación", e);
		}
		return result;
	}

	@Override
	public ResultadoIntergaBean consultarRest() {
		ResultadoIntergaBean resultadoProceso = new ResultadoIntergaBean(Boolean.FALSE);
		try {
			List<DuplicadoPermisoConducirVO> lista = servicioPersistenciaDuplPermCond.consultarDuplicadosPermConducir();
			if (lista != null && !lista.isEmpty()) {
				int numeroEjecuciones = 0;
				for (DuplicadoPermisoConducirVO duplicado : lista) {
					ResultadoIntergaBean resultado = new ResultadoIntergaBean(Boolean.FALSE);

					String estadoAnterior = duplicado.getEstado();
					String estadoNuevo = duplicado.getEstado();
					String estadoImpAnterior = duplicado.getEstadoImpresion();
					String estadoImpNuevo = duplicado.getEstadoImpresion();
					String tipoTramite = duplicado.getTipoTramite();
					String respuesta = duplicado.getRespuesta();

					try {
						FileRequest request = crearRequestConsultar(duplicado);
						resultado = servicioIntergaRestWS.consultar(request, duplicado.getNumExpediente(), ServicioIntergaRestWS.PDF_ORIGINAL);
						if (resultado != null && !resultado.getError()) {
							if (StringUtils.isNotBlank(resultado.getEstado())) {
								estadoNuevo = resultado.getEstado();
							}
							if (EstadoTramitesInterga.Finalizado.getValorEnum().equals(estadoNuevo)) {
								ResultadoBean resultCredito = descontarCreditos(duplicado);
								if (resultCredito != null && !resultCredito.getError()) {
									numeroEjecuciones++;
									estadoImpNuevo = EstadoTramitesInterga.Doc_Recibida.getValorEnum();
								}
							}
						} else {
							if (StringUtils.isBlank(resultado.getEstado())) {
								estadoNuevo = EstadoTramitesInterga.Finalizado_Error.getValorEnum();
								duplicado = inicializarEstadoDocumentos(duplicado);
							} else {
								estadoNuevo = resultado.getEstado();
							}
						}
						respuesta = resultado.getMensaje();
					} catch (Exception e) {
						log.error("Error en la consulta del duplicado permiso conducir con numExpediente: " + duplicado.getNumExpediente(), e);
						respuesta = "Error en la consulta del duplicado permiso conducir";
						estadoNuevo = EstadoTramitesInterga.Finalizado_Error.getValorEnum();
						inicializarEstadoDocumentos(duplicado);
					}

					servicioPersistenciaDuplPermCond.actualizarEstadosYRespuesta(duplicado.getIdDuplicadoPermCond(), estadoNuevo, estadoImpNuevo, respuesta);

					if (StringUtils.isNotBlank(estadoNuevo) && !estadoNuevo.equals(estadoAnterior)) {
						servicioEvoDuplicadoPermisoConducir.guardar(duplicado.getIdDuplicadoPermCond(), estadoAnterior, estadoNuevo, duplicado.getContrato().getColegiado().getIdUsuario(),
								TipoActualizacion.MOD.getValorEnum());
					}

					if (StringUtils.isNotBlank(estadoImpNuevo) && !estadoImpNuevo.equals(estadoImpAnterior)) {
						servicioEvoDuplicadoPermisoConducir.guardar(duplicado.getIdDuplicadoPermCond(), estadoImpAnterior, estadoImpNuevo, duplicado.getContrato().getColegiado().getIdUsuario(),
								TipoActualizacion.MOD.getValorEnum());
					}

					if (EstadoTramitesInterga.Finalizado.getValorEnum().equals(estadoNuevo)) {
						String direccionCorreo = duplicado.getContrato().getCorreoElectronico();
						if (duplicado.getContrato().getCorreosTramites() != null && !duplicado.getContrato().getCorreosTramites().isEmpty()) {
							for (CorreoContratoTramiteVO correoContratoTramite : duplicado.getContrato().getCorreosTramites()) {
								if (tipoTramite.equalsIgnoreCase(correoContratoTramite.getTipoTramite().getTipoTramite())){
									direccionCorreo = correoContratoTramite.getCorreoElectronico();
									break;
								}
							}
						}
						enviarCorreo(direccionCorreo, duplicado.getNumExpediente().toString());
					}
				}
				resultadoProceso.setNumeroEjecuciones(numeroEjecuciones);
			} else {
				resultadoProceso.setError(Boolean.TRUE);
				resultadoProceso.setMensaje("No se encuentra ningún duplicado permiso conducir para ser consultado");
			}
		} catch (Exception e) {
			log.error("Error al consultar el duplicado permiso conducir error:", e);
			resultadoProceso.setError(Boolean.TRUE);
			resultadoProceso.setMensaje("Error el duplicado permiso conducir");
		}
		return resultadoProceso;
	}

	private boolean documentacionEnviada(DuplicadoPermisoConducirVO duplicado) {
		return (EstadosDocumentos.Enviado.getValorEnum().equals(duplicado.getEstadoMandato())
				&& EstadosDocumentos.Enviado.getValorEnum().equals(duplicado.getEstadoDeclaracion())
				&& EstadosDocumentos.Enviado.getValorEnum().equals(duplicado.getEstadoDeclaracionTitular())
				&& EstadosDocumentos.Enviado.getValorEnum().equals(duplicado.getEstadoSolicitud()));
	}

	private DuplicadoPermisoConducirVO inicializarEstadoDocumentos(DuplicadoPermisoConducirVO duplicado) {
		duplicado.setEstadoDeclaracion(EstadosDocumentos.Firmado.getValorEnum());
		duplicado.setEstadoDeclaracionTitular(EstadosDocumentos.Subido.getValorEnum());
		duplicado.setEstadoSolicitud(EstadosDocumentos.Firmado.getValorEnum());
		duplicado.setEstadoMandato(EstadosDocumentos.Subido.getValorEnum());
		if (StringUtils.isNotBlank(duplicado.getEstadoOtroDocumento())) {
			duplicado.setEstadoOtroDocumento(EstadosDocumentos.Subido.getValorEnum());
		}
		return duplicado;
	}

	private void enviarCorreo(String correoElectronico, String numExpediente) {
		Boolean enviarCorreoError = Boolean.FALSE;
		String mensajeError = "";
		try {
			String dirOculta = gestorPropiedades.valorPropertie("direcciones.ocultas.duplicado.permiso.conducir");
			StringBuffer sb = new StringBuffer();
			sb.append("El trámite de Duplicado de Permiso de Conducir con número de expediente ");
			sb.append("<b>");
			sb.append(numExpediente);
			sb.append("</b>");
			sb.append(" ha finalizado correctamente.");
			sb.append("<br>");
			sb.append("<br>");
			ResultadoBean resultEnvio = servicioCorreo.enviarCorreo(sb.toString(), Boolean.FALSE, null, "Duplicado Permiso Conducir 'Finalizado'", correoElectronico, null, dirOculta, null, null);
			if (resultEnvio.getError()) {
				for (String mensaje : resultEnvio.getListaMensajes()) {
					log.error(mensaje);
					mensaje += " " + mensaje;
				}
				enviarCorreoError = Boolean.TRUE;
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de enviar el correo al colegiado con el aviso de finalización del duplicado permiso conducir, error: ", e);
			mensajeError = "Ha sucedido un error a la hora de enviar el correo al colegiado con el aviso de finalización del duplicado permiso conducir, error: " + e.getMessage();
			enviarCorreoError = Boolean.TRUE;
		}
		if (enviarCorreoError) {
			enviarMailError(mensajeError, "Error envio Aviso Finalización Duplicado Permiso Conducir", numExpediente);
		}
	}

	private void enviarMailError(String mensajeError, String asunto, String numExpediente) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(mensajeError);
			sb.append("<br>");
			sb.append("El error ha sucedido para el expediente: " + numExpediente);
			ResultadoBean resultEnvio = servicioCorreo.enviarCorreo(sb.toString(), Boolean.FALSE, null, asunto,
					gestorPropiedades.valorPropertie("direcciones.permisoInternacional.mail.error"), null, null, null);
			if (resultEnvio.getError()) {
				for (String mensaje : resultEnvio.getListaMensajes()) {
					log.error(mensaje);
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de enviar el correo de aviso de error, error: ", e);
		}
	}

	private ResultadoBean descontarCreditos(DuplicadoPermisoConducirVO duplicado) {
		ResultadoBean resultado = new ResultadoBean(Boolean.TRUE);
		try {
			resultado = servicioComunCredito.descontarCreditos(
					TipoTramiteTrafico.DuplicadoPermisoConducir.getValorEnum(), duplicado.getIdContrato(),
					duplicado.getContrato().getColegiado().getIdUsuario(),
					Collections.singletonList(duplicado.getNumExpediente()));
		} catch (Exception e) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de descontar el crédito para el Duplicado Permiso Conducir");
		}
		return resultado;
	}

	@Override
	public ResultadoIntergaBean subidaDocumentacion(DuplicadoPermisoConducirVO duplicado, File fichero, String tipo) {
		ResultadoIntergaBean resultado = new ResultadoIntergaBean(Boolean.FALSE);
		UpdateRequest request = crearRequestSubirDocu(duplicado, fichero);
		if (request != null) {
			resultado = servicioIntergaRestWS.subidaDocumentacion(request, tipo);
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se ha generado bien la petición para enviar la documentación.");
		}
		return resultado;
	}

	private SendFilesRequest crearRequestEnvio(DuplicadoPermisoConducirVO duplicado) {
		SendFilesRequest request = new SendFilesRequest();

		request.setSocietyCif(duplicado.getContrato().getColegio().getCif());
		request.setPlatformCif(duplicado.getContrato().getColegio().getCif());

		List<Files> files = new ArrayList<Files>();
		Files file = new Files();
		file.setTipo(TiposDuplicadosPermisos.convertirTexto(duplicado.getTipoDuplicado()));
		file.setNumReferencia("");
		file.setTipoTramite(duplicado.getTipoTramite());
		file.setGestoria(84);
		file.setDoiTitular(duplicado.getDoiTitular());
		file.setMatricula("");
		file.setTasa(duplicado.getCodigoTasa());

		int tipo = utilidadesNIFValidator.getType(duplicado.getDoiTitular());
		file.setDoiTipo(tipo == utilidadesNIFValidator.ES_NIE ? "3" : "1");

		file.setDoiCotitular("");

		IntervinienteIntergaVO titular = servicioIntervinienteInterga.getIntervinienteTrafico(
				duplicado.getNumExpediente(), duplicado.getDoiTitular(), duplicado.getNumColegiado(),
				TipoTramiteInterga.Duplicado_Permiso_Conducir.getValorEnum());
		if (titular.getDireccion() != null) {
			String nombreVia = servicioComunDireccion.obtenerNombreTipoVia(titular.getDireccion().getIdTipoVia());
			file.setDirTipoVia(nombreVia);
			file.setDirNombreVia(titular.getDireccion().getNombreVia());
			file.setDirNumero(titular.getDireccion().getNumero());
			file.setDirBloque(titular.getDireccion().getBloque());
			file.setDirPortal(titular.getDireccion().getPortal());
			file.setDirEscalera(titular.getDireccion().getEscalera());
			file.setDirPlanta(titular.getDireccion().getPlanta());
			file.setDirPuerta(titular.getDireccion().getPuerta());
			if (titular.getDireccion().getKm() != null) {
				file.setDirKilometro(titular.getDireccion().getKm().intValue());
			}
			if (titular.getDireccion().getHm() != null) {
				file.setDirHectometro(titular.getDireccion().getHm().intValue());
			}
			file.setDirCodigoPostal(titular.getDireccion().getCodPostal());
			file.setDirLocalidad(titular.getDireccion().getPueblo());
			String nombreMunicipio = servicioComunDireccion.obtenerNombreMunicipioSitex(titular.getDireccion().getIdMunicipio(), titular.getDireccion().getIdProvincia());
			if (StringUtils.isNotBlank(nombreMunicipio)) {
				file.setDirMunicipio(nombreMunicipio.toUpperCase());
			} else {
				file.setDirMunicipio("");
			}
			String nombreProvincia = servicioComunDireccion.obtenerNombreProvinciaSitex(titular.getDireccion().getIdProvincia());
			file.setDirProvincia(nombreProvincia);
		}

		file.setImpresion("NO");
		file.setVehiculoImportacion("NO");
		file.setEmailAute(duplicado.getEmailAute());
		file.setObservaciones(duplicado.getObservaciones());
		files.add(file);

		request.setFiles(files);

		return request;
	}

	private FileRequest crearRequestConsultar(DuplicadoPermisoConducirVO duplicado) {
		FileRequest request = new FileRequest();

		request.setSocietyCif(duplicado.getContrato().getColegio().getCif());
		request.setPlatformCif(duplicado.getContrato().getColegio().getCif());

		request.setFileType(TiposDuplicadosPermisos.convertirTexto(duplicado.getTipoDuplicado()));
		request.setFileDate(utilesFecha.formatoFecha(duplicado.getFechaPresentacion()));
		request.setFileDoi(duplicado.getDoiTitular());
		request.setFilePlate("");

		return request;
	}

	private UpdateRequest crearRequestSubirDocu(DuplicadoPermisoConducirVO duplicado, File fichero) {
		UpdateRequest request = new UpdateRequest();
		try {
			request.setSocietyCif(duplicado.getContrato().getColegio().getCif());
			request.setPlatformCif(duplicado.getContrato().getColegio().getCif());

			request.setFileType(TiposDuplicadosPermisos.convertirTexto(duplicado.getTipoDuplicado()));
			request.setFileDate(utilesFecha.formatoFecha(duplicado.getFechaPresentacion()));
			request.setFileDoi(duplicado.getDoiTitular());
			request.setFilePlate("");

			byte[] bArray = readFileToByteArray(fichero);
			request.setBase64(new String(Base64.encodeBase64(bArray)));
			return request;
		} catch (Throwable e) {
			log.error("Error al crear la request para subir los documentos", e);
		}
		return null;
	}

	private byte[] readFileToByteArray(File file) {
		FileInputStream fis = null;
		byte[] bArray = new byte[(int) file.length()];
		try {
			fis = new FileInputStream(file);
			fis.read(bArray);
			fis.close();
		} catch (IOException ioExp) {
			ioExp.printStackTrace();
		}
		return bArray;
	}
}