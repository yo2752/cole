package org.gestoresmadrid.oegamPermisoInternacional.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.contrato.model.vo.CorreoContratoTramiteVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.impresion.model.vo.ImpresionVO;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoTasa;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaPK;
import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoDeclaracion;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoTramitesInterga;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.TipoTramiteInterga;
import org.gestoresmadrid.core.trafico.interga.model.vo.IntervinienteIntergaVO;
import org.gestoresmadrid.core.trafico.permiso.internacional.model.vo.PermisoInternacionalVO;
import org.gestoresmadrid.oegamComun.cola.service.ServicioComunCola;
import org.gestoresmadrid.oegamComun.correo.service.ServicioComunCorreo;
import org.gestoresmadrid.oegamComun.credito.service.ServicioComunCredito;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunDireccion;
import org.gestoresmadrid.oegamComun.persona.service.ServicioComunEvolucionPersona;
import org.gestoresmadrid.oegamComun.persona.service.ServicioComunPersona;
import org.gestoresmadrid.oegamComun.persona.view.bean.ResultadoPersonaBean;
import org.gestoresmadrid.oegamComun.stock.service.ServicioMaterialStock;
import org.gestoresmadrid.oegamComun.tasa.service.ServicioComunTasa;
import org.gestoresmadrid.oegamComun.utiles.UtilidadesNIFValidator;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionDocumentos;
import org.gestoresmadrid.oegamImpresion.view.dto.ImpresionDto;
import org.gestoresmadrid.oegamInterga.service.ServicioInterga;
import org.gestoresmadrid.oegamInterga.service.ServicioIntervinienteInterga;
import org.gestoresmadrid.oegamInterga.view.bean.ResultadoIntergaBean;
import org.gestoresmadrid.oegamInterga.view.dto.IntervinienteIntergaDto;
import org.gestoresmadrid.oegamPermisoInternacional.conversor.service.ConversorPermInter;
import org.gestoresmadrid.oegamPermisoInternacional.rest.service.ServicioPermisoInternacionalRestWS;
import org.gestoresmadrid.oegamPermisoInternacional.service.ServicioEvolucionPermisoInternacional;
import org.gestoresmadrid.oegamPermisoInternacional.service.ServicioPermisoInternacional;
import org.gestoresmadrid.oegamPermisoInternacional.service.ServicioPersistenciaPermInter;
import org.gestoresmadrid.oegamPermisoInternacional.view.bean.DocumentoTramitesPermisoInterBean;
import org.gestoresmadrid.oegamPermisoInternacional.view.bean.ResultadoPermInterBean;
import org.gestoresmadrid.oegamPermisoInternacional.view.bean.TramitePermisoInternDocBean;
import org.gestoresmadrid.oegamPermisoInternacional.view.dto.PermisoInternacionalDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.informes.ReportExporter;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioPermisoInternacionalImpl implements ServicioPermisoInternacional {

	private static final long serialVersionUID = 2684077310469978434L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPermisoInternacionalImpl.class);

	@Autowired
	ServicioComunTasa servicioTasa;

	@Autowired
	ServicioComunPersona servicioPersona;

	@Autowired
	ServicioComunDireccion servicioComunDireccion;

	@Autowired
	ServicioComunEvolucionPersona servicioComunEvolucionPersona;

	@Autowired
	ServicioInterga servicioInterga;

	@Autowired
	ServicioIntervinienteInterga servicioIntervinientePermInter;

	@Autowired
	ServicioEvolucionPermisoInternacional servicioEvolucionPermisoInternacional;

	@Autowired
	ConversorPermInter conversorPermInter;

	@Autowired
	ServicioPersistenciaPermInter servicioPersistenciaPermInter;

	@Autowired
	ServicioComunCola servicioComunCola;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	ServicioComunCredito servicioComunCredito;

	@Autowired
	ServicioMaterialStock servicioMaterialStock;

	@Autowired
	ServicioImpresionDocumentos servicioImpresionDocumentos;

	@Autowired
	UtilidadesNIFValidator utilesNifValidator;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	Utiles utiles;

	@Autowired
	ServicioComunCorreo servicioComunCorreo;

	@Autowired
	ServicioPermisoInternacionalRestWS servicioPermInterRestWS;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public ResultadoPermInterBean imprimir(Long idPermisoIntern, Long idUsuario) {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			PermisoInternacionalVO permisoInternacionalVO = servicioPersistenciaPermInter.getPermisoInternacional(idPermisoIntern);
			resultado = validarTramiteImprimir(permisoInternacionalVO, idPermisoIntern);
			if (!resultado.getError()) {
				FileResultBean fileResult = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.PERMISO_INTERNACIONAL, ConstantesGestorFicheros.RECIBIDO, utiles
						.transformExpedienteInterga(permisoInternacionalVO.getNumExpediente().toString()), permisoInternacionalVO.getNumExpediente().toString(),
						ConstantesGestorFicheros.EXTENSION_PDF);
				if (fileResult != null && fileResult.getFile() != null) {
					servicioPersistenciaPermInter.actualizarEstadoImpresion(idPermisoIntern, EstadoTramitesInterga.Impresion_OK.getValorEnum());
					servicioEvolucionPermisoInternacional.guardar(idPermisoIntern, permisoInternacionalVO.getEstadoImpresion(), EstadoTramitesInterga.Impresion_OK.getValorEnum(), idUsuario,
							TipoActualizacion.MOD.getValorEnum());
					servicioMaterialStock.descontarStock("CO", TipoDocumentoImprimirEnum.PERMISO_INTERNACIONAL.getValorEnum(), 1L);
					resultado.setFichero(fileResult.getFile());
					resultado.setNombreFichero(permisoInternacionalVO.getNumExpediente().toString() + ConstantesGestorFicheros.EXTENSION_PDF);
					enviarCorreoImpresion(permisoInternacionalVO.getNumExpediente(), permisoInternacionalVO.getContrato());
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se ha encontrado el PDF para la impresion del expediente: " + permisoInternacionalVO.getNumExpediente());
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de imprimir el tramite de permiso internacional con ID: " + idPermisoIntern + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir el tramite con ID: " + idPermisoIntern);
		}
		return resultado;
	}

	private void enviarCorreoImpresion(BigDecimal numExpediente, ContratoVO contrato) {
		try {
			StringBuffer resultadoHtml = new StringBuffer();
			resultadoHtml.append("<br>Ya se ha procedido a imprimir el tramite: " + numExpediente + " de solicitud de permiso internacional. Puede pasar a recogerlo cuando crea oportuno.<br>");
			String subject = "Impresión Permiso Internacional";

			String direccionCorreo = contrato.getCorreoElectronico();
			if (contrato.getCorreosTramites() != null && !contrato.getCorreosTramites().isEmpty()) {
				for (CorreoContratoTramiteVO correoContratoTramite : contrato.getCorreosTramites()) {
					if (TipoTramiteTrafico.PermisonInternacional.getValorEnum().equalsIgnoreCase(correoContratoTramite.getTipoTramite().getTipoTramite())) {
						direccionCorreo = correoContratoTramite.getCorreoElectronico();
						break;
					}
				}
			}
			
			servicioComunCorreo.enviarCorreo(resultadoHtml.toString(), Boolean.FALSE, null, subject, direccionCorreo, null, null, null, null);
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de enviar el correo con el aviso de impresion, error: ", e);
		}
	}

	@Override
	public ResultadoPermInterBean firmarDeclaracion(PermisoInternacionalDto permiso, Long idUsuario) {
		ResultadoPermInterBean result = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			String[] numExpedientes = new String[1];
			numExpedientes[0] = permiso.getNumExpediente().toString();
			ResultadoImpresionBean resultado = servicioImpresionDocumentos.crearImpresion(numExpedientes, permiso.getIdContrato(), idUsuario, TipoImpreso.DeclaracionResponsabilidadColegiado
					.toString(), TipoTramiteTrafico.PermisonInternacional.getValorEnum(), null);
			if (resultado.getError()) {
				result.setError(Boolean.TRUE);
				result.setMensaje(resultado.getMensaje());
			} else {
				servicioPersistenciaPermInter.actualizarEstado(permiso.getIdPermiso(), EstadoTramitesInterga.Pendiente_Firma_Declaracion.getValorEnum());
				servicioEvolucionPermisoInternacional.guardar(permiso.getIdPermiso(), permiso.getEstado(), EstadoTramitesInterga.Pendiente_Firma_Declaracion.getValorEnum(), idUsuario,
						TipoActualizacion.MOD.getValorEnum());
				result.setMensaje("Su solicitud de envío de declaración se está procesando.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de enviar la declaración de permiso internacional con ID: " + permiso.getIdPermiso() + ", error: ", e);
			result.setError(Boolean.TRUE);
			result.setMensaje("Ha sucedido un error a la hora de declaración de permiso internacional con ID: " + permiso.getIdPermiso());
		}
		return result;
	}

	@Override
	public ResultadoPermInterBean enviarDeclaracion(Long idPermisoIntern, Long idUsuario) {
		ResultadoPermInterBean result = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			PermisoInternacionalVO permisoInternacionalVO = servicioPersistenciaPermInter.getPermisoInternacional(idPermisoIntern);
			String estadoDeclaracionAnterior = permisoInternacionalVO.getEstadoDeclaracion();
			String estadoDeclaracion = servicioPermInterRestWS.enviarDeclaracionResponsabilidad(permisoInternacionalVO, estadoDeclaracionAnterior, idUsuario);
			if (!estadoDeclaracionAnterior.equals(estadoDeclaracion)) {
				permisoInternacionalVO.setEstadoDeclaracion(estadoDeclaracion);
				if (EstadoDeclaracion.No_Enviado_Colegiado.getValorEnum().equals(estadoDeclaracionAnterior)) {
					permisoInternacionalVO.setEstado(EstadoTramitesInterga.Finalizado.getValorEnum());
					servicioEvolucionPermisoInternacional.guardar(permisoInternacionalVO.getIdPermiso(), permisoInternacionalVO.getEstado(), EstadoTramitesInterga.Finalizado.getValorEnum(), idUsuario,
							TipoActualizacion.MOD.getValorEnum());
					enviarJustificante(permisoInternacionalVO);
				}
				servicioPersistenciaPermInter.actualizar(permisoInternacionalVO);
				result.setMensaje("Declaración enviada correctamente.");
			} else {
				result.setError(Boolean.TRUE);
				result.setMensaje("No se ha podido enviar la declaración");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de enviar la declaración de permiso internacional con ID: " + idPermisoIntern + ", error: ", e);
			result.setError(Boolean.TRUE);
			result.setMensaje("Ha sucedido un error a la hora de declaración de permiso internacional con ID: " + idPermisoIntern);
		}
		return result;
	}

	private void enviarJustificante(PermisoInternacionalVO permisoInternacional) throws OegamExcepcion {
		String nombreFichero = "JustificanteDoc_" + permisoInternacional.getNumExpediente().toString();
		FileResultBean result = gestorDocumentos.buscarFicheroPorNumExpTipo(ConstantesGestorFicheros.PERMISO_INTERNACIONAL, ConstantesGestorFicheros.JUSTIFICANTE_DOC, utiles
				.transformExpedienteInterga(permisoInternacional.getNumExpediente().toString()), nombreFichero);
		if (result.getFiles() != null && !result.getFiles().isEmpty() && result.getFiles().size() == 1) {
			FicheroBean ficheroBean = new FicheroBean();
			ficheroBean.setFichero(result.getFiles().get(0));
			ficheroBean.setNombreDocumento(nombreFichero);
			ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);

			enviarMailJustificanteImpresionPermisoInter(permisoInternacional.getContrato(), ficheroBean, permisoInternacional.getJefatura(), nombreFichero, permisoInternacional
					.getNumExpediente().toString());
		}
	}

	@Override
	public ResultadoPermInterBean firmada(BigDecimal numExpediente, Long idUsuario) {
		ResultadoPermInterBean result = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			PermisoInternacionalVO permisoInternacional = servicioPersistenciaPermInter.getPermisoInternacionPorExpediente(numExpediente);
			servicioPersistenciaPermInter.actualizarEstado(permisoInternacional.getIdPermiso(), EstadoTramitesInterga.Declaracion_Firmada.getValorEnum());
			servicioEvolucionPermisoInternacional.guardar(permisoInternacional.getIdPermiso(), permisoInternacional.getEstado(), EstadoTramitesInterga.Declaracion_Firmada.getValorEnum(), idUsuario,
					TipoActualizacion.MOD.getValorEnum());
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado a declaración firmada, error: ", e);
			result.setError(Boolean.TRUE);
			result.setMensaje("Ha sucedido un error a la hora de cambiar el estado a declaración firmada");
		}
		return result;
	}

	@Override
	public ResultadoPermInterBean errorFirma(BigDecimal numExpediente, Long idUsuario) {
		ResultadoPermInterBean result = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			PermisoInternacionalVO permisoInternacional = servicioPersistenciaPermInter.getPermisoInternacionPorExpediente(numExpediente);
			servicioPersistenciaPermInter.actualizarEstado(permisoInternacional.getIdPermiso(), EstadoTramitesInterga.Error_Firma_Declaracion.getValorEnum());
			servicioEvolucionPermisoInternacional.guardar(permisoInternacional.getIdPermiso(), permisoInternacional.getEstado(), EstadoTramitesInterga.Error_Firma_Declaracion.getValorEnum(),
					idUsuario, TipoActualizacion.MOD.getValorEnum());
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado a error firma declaración, error: ", e);
			result.setError(Boolean.TRUE);
			result.setMensaje("Ha sucedido un error a la hora de cambiar el estado a error firma declaración");
		}
		return result;
	}

	@Override
	public ResultadoPermInterBean tramitar(Long idPermisoIntern, Long idUsuario) {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		String estadoNuevo = "";
		String estadorAnterior = "";
		try {
			PermisoInternacionalVO permisoInternacionalVO = servicioPersistenciaPermInter.getPermisoInternacional(idPermisoIntern);
			estadorAnterior = permisoInternacionalVO.getEstado();
			resultado = validarTramiteTramitar(permisoInternacionalVO, idPermisoIntern);
			if (!resultado.getError()) {
				estadoNuevo = EstadoTramitesInterga.Pendiente_Envio_DGT.getValorEnum();
				servicioPersistenciaPermInter.actualizarEstado(idPermisoIntern, estadoNuevo);
				ResultadoBean resultCola = servicioComunCola.crearSolicitud(idPermisoIntern, ProcesosEnum.ENVIO_PERMISO_INTER.getNombreEnum(), gestorPropiedades.valorPropertie("nombreHostProceso"),
						TipoTramiteTrafico.PermisonInternacional.getValorEnum(), new BigDecimal(idUsuario), new BigDecimal(permisoInternacionalVO.getIdContrato()), null);
				if (resultCola.getError()) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje(resultCola.getMensaje());
				} else {
					try {
						servicioEvolucionPermisoInternacional.guardar(idPermisoIntern, permisoInternacionalVO.getEstado(), EstadoTramitesInterga.Pendiente_Envio_DGT.getValorEnum(), idUsuario,
								TipoActualizacion.MOD.getValorEnum());
					} catch (Exception e) {
						log.error("Error al guardar la evolución del permiso internacional a la hora de validar", e);
					}
					resultado.setMensaje("Tramite: " + permisoInternacionalVO.getNumExpediente() + " tramitado correctamente.");
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de tramitar el tramite de permiso internacional con ID: " + idPermisoIntern + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de tramitar el tramite con ID: " + idPermisoIntern);
		}
		if (resultado != null && resultado.getError() && !StringUtils.isNotBlank(estadoNuevo)) {
			try {
				servicioPersistenciaPermInter.actualizarEstado(idPermisoIntern, estadorAnterior);
			} catch (Exception e) {
				log.error("Ha sucedido un error a la hora de actualizar el estado del expediente con id: " + idPermisoIntern + ", error: ", e);
			}
		}
		return resultado;
	}

	@Override
	public ResultadoPermInterBean generarMandato(BigDecimal numExpediente, Long idContrato, Long idUsuario) {
		ResultadoPermInterBean result = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			ResultadoIntergaBean resultadoInterga = servicioInterga.generarMandato(numExpediente, idContrato, idUsuario, TipoTramiteTrafico.PermisonInternacional.getValorEnum());
			if (resultadoInterga != null) {
				if (resultadoInterga.getError()) {
					result.setError(Boolean.TRUE);
				}
				result.setMensaje(resultadoInterga.getMensaje());
			}
		} catch (Exception e) {
			log.error("Existen errores en la solicitud de impresión para el mandato de permiso internacional.", e);
			result.setMensaje("Existen errores en la solicitud de impresión.");
			result.setError(Boolean.TRUE);
		}
		return result;
	}

	@Override
	public ResultadoPermInterBean eliminarMandato(BigDecimal numExpediente) {
		ResultadoPermInterBean result = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			ResultadoIntergaBean resultadoInterga = servicioInterga.eliminarMandato(numExpediente, TipoTramiteTrafico.PermisonInternacional.getValorEnum());
			if (resultadoInterga != null && resultadoInterga.getError()) {
				result.setError(Boolean.TRUE);
				result.setMensaje(resultadoInterga.getMensaje());
			}
		} catch (Exception e) {
			log.error("Existen errores a la hora de eliminar el mandato del permiso internacional.", e);
			result.setMensaje("Existen errores a la hora de eliminar el mandato.");
			result.setError(Boolean.TRUE);
		}
		return result;
	}

	@Override
	public ResultadoPermInterBean descargarMandato(BigDecimal numExpediente, Long idUsuario) {
		ResultadoPermInterBean result = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			ResultadoIntergaBean resultadoInterga = servicioInterga.descargarMandato(numExpediente, idUsuario, TipoTramiteTrafico.PermisonInternacional.getValorEnum());
			if (resultadoInterga != null && !resultadoInterga.getError()) {
				result.setFichero(resultadoInterga.getFichero());
			} else {
				result.setError(Boolean.TRUE);
			}
		} catch (Exception e) {
			log.error("Existen errores a la hora de descargar el mandato del permiso internacional.", e);
			result.setMensaje("Existen errores a la hora de descargar el mandato.");
			result.setError(Boolean.TRUE);
		}
		return result;
	}

	@Override
	public ResultadoPermInterBean cambiarEstado(Long idPermisoIntern, String estadoNuevo, Long idUsuario, Boolean accionesAsociadas) {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			PermisoInternacionalVO permisoInternacionalVO = servicioPersistenciaPermInter.getPermisoInternacional(idPermisoIntern);
			if (!EstadoTramitesInterga.Anulado.getValorEnum().equals(estadoNuevo)) {
				servicioPersistenciaPermInter.actualizarEstado(idPermisoIntern, estadoNuevo);
				servicioEvolucionPermisoInternacional.guardar(idPermisoIntern, permisoInternacionalVO.getEstado(), estadoNuevo, idUsuario, TipoActualizacion.MOD.getValorEnum());
				String mensaje = "";
				if (accionesAsociadas) {
					if (EstadoTramitesInterga.Finalizado.getValorEnum().equals(estadoNuevo)) {
						ResultadoBean resultCredito = servicioComunCredito.descontarCreditos(TipoTramiteTrafico.PermisonInternacional.getValorEnum(), permisoInternacionalVO.getIdContrato(), idUsuario,
								Collections.singletonList(permisoInternacionalVO.getNumExpediente()));
						if (resultCredito.getError()) {
							mensaje += resultCredito.getMensaje();
						}
					} else if (EstadoTramitesInterga.Pendiente_Envio_DGT.getValorEnum().equals(permisoInternacionalVO.getEstado())) {
						ResultadoBean resultCola = servicioComunCola.eliminarCola(new BigDecimal(permisoInternacionalVO.getIdPermiso()), ProcesosEnum.ENVIO_PERMISO_INTER.getNombreEnum(),
								gestorPropiedades.valorPropertie("nombreHostProceso"));
						if (resultCola.getError()) {
							mensaje += resultCola.getMensaje();
						}
					}
				}
				if (!mensaje.isEmpty()) {
					resultado.setMensaje("Tramite: " + permisoInternacionalVO.getNumExpediente() + " actualizado correctamente, con las siguientes incidencias: " + mensaje);
				} else {
					resultado.setMensaje("Tramite: " + permisoInternacionalVO.getNumExpediente() + " actualizado correctamente.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se puede actualizar el estado del expediente: " + permisoInternacionalVO.getNumExpediente()
						+ " al estado Anulado, para eso debera pulsar el boton de eliminar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado del tramite de permiso internacional con ID: " + idPermisoIntern + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado del tramite con ID: " + idPermisoIntern);
		}
		return resultado;
	}

	@Override
	public ResultadoPermInterBean eliminar(Long idPermisoIntern, Long idUsuario) {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			PermisoInternacionalVO permisoInternacionalVO = servicioPersistenciaPermInter.getPermisoInternacional(idPermisoIntern);
			resultado = validarTramiteEliminar(permisoInternacionalVO, idPermisoIntern);
			if (!resultado.getError()) {
				if (permisoInternacionalVO.getCodigoTasa() != null && !permisoInternacionalVO.getCodigoTasa().isEmpty()) {
					ResultadoBean resultTasa = servicioTasa.desasignarTasaExpediente(permisoInternacionalVO.getCodigoTasa(), permisoInternacionalVO.getNumExpediente(), permisoInternacionalVO
							.getIdContrato(), TipoTasa.CuatroCinco.getValorEnum(), idUsuario);
					if (resultTasa.getError()) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(resultTasa.getMensaje());
						return resultado;
					}
				}
				servicioPersistenciaPermInter.eliminarPermisoIntern(permisoInternacionalVO.getIdPermiso());
				servicioEvolucionPermisoInternacional.guardar(idPermisoIntern, permisoInternacionalVO.getEstado(), EstadoTramitesInterga.Anulado.getValorEnum(), idUsuario, TipoActualizacion.MOD
						.getValorEnum());
				resultado.setMensaje("Tramite: " + permisoInternacionalVO.getNumExpediente() + " eliminado correctamente.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el tramite de permiso internacional con ID: " + idPermisoIntern + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el tramite con ID: " + idPermisoIntern);
		}
		return resultado;
	}

	private ResultadoPermInterBean validarTramiteEliminar(PermisoInternacionalVO permisoInternacionalVO, Long idPermisoIntern) {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		if (permisoInternacionalVO == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos en BBDD para el id: " + idPermisoIntern);
		} else if (!EstadoTramitesInterga.Iniciado.getValorEnum().equals(permisoInternacionalVO.getEstado())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + permisoInternacionalVO.getNumExpediente() + " no se puede eliminar porque no se encuentra en estado Iniciado.");
		}
		return resultado;
	}

	private ResultadoPermInterBean validarTramiteImprimir(PermisoInternacionalVO permisoInternacionalVO, Long idPermisoIntern) {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		if (permisoInternacionalVO == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos en BBDD para el id: " + idPermisoIntern);
		} else if (!EstadoTramitesInterga.Finalizado.getValorEnum().equals(permisoInternacionalVO.getEstado())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + permisoInternacionalVO.getNumExpediente() + " no se puede imprimir porque no se encuentra en estado Finalizado.");
		} else if (!EstadoTramitesInterga.Doc_Recibida.getValorEnum().equals(permisoInternacionalVO.getEstadoImpresion())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + permisoInternacionalVO.getNumExpediente() + " no se puede imprimir porque no se encuentra en estado Documentacion Recibida la documentación.");
		} else if (!servicioMaterialStock.existeStockMaterial(TipoDocumentoImprimirEnum.PERMISO_INTERNACIONAL.getValorEnum(), "CO")) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + permisoInternacionalVO.getNumExpediente() + " no se puede imprimir porque no existe stock disponible para ese tipo de documento.");
		}
		return resultado;
	}

	private ResultadoPermInterBean validarTramiteTramitar(PermisoInternacionalVO permisoInternacionalVO, Long idPermisoIntern) {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		if (permisoInternacionalVO == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos en BBDD para el id: " + idPermisoIntern);
		} else if (!EstadoTramitesInterga.Validado.getValorEnum().equals(permisoInternacionalVO.getEstado())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + permisoInternacionalVO.getNumExpediente() + " no se puede tramitar porque no se encuentra en estado 'Validado'.");
		}
		return resultado;
	}

	@Override
	public PermisoInternacionalDto getPermisoInternacional(Long idPermiso) {
		try {
			PermisoInternacionalVO permisoInternacional = servicioPersistenciaPermInter.getPermisoInternacional(idPermiso);
			if (permisoInternacional != null) {
				PermisoInternacionalDto permisoInternacionalDto = conversorPermInter.transform(permisoInternacional, PermisoInternacionalDto.class);
				IntervinienteIntergaVO titular = servicioIntervinientePermInter.getIntervinienteTramite(permisoInternacionalDto.getNumExpediente(), TipoTramiteInterga.Permiso_Internacional
						.getValorEnum());
				if (titular != null) {
					permisoInternacionalDto.setTitular(conversorPermInter.transform(titular, IntervinienteIntergaDto.class));
					ImpresionVO impresion = servicioImpresionDocumentos.getDatosPorNombreDocumentoGeneradoYDescargado(servicioInterga.obtenerNombreDocumentoMandato(permisoInternacionalDto
							.getNumExpediente(), TipoTramiteTrafico.PermisonInternacional.getValorEnum()));
					if (impresion != null) {
						permisoInternacionalDto.addMandato(conversorPermInter.transform(impresion, ImpresionDto.class));
					}
				}
				if (EstadoTramitesInterga.Finalizado.getValorEnum().equals(permisoInternacional.getEstado()) && EstadoTramitesInterga.Doc_Subida.getValorEnum().equals(permisoInternacional
						.getEstadoImpresion()) && "S".equals(permisoInternacional.getDocumentacionAportada())) {
					permisoInternacionalDto.addListaFicheroInfo(permisoInternacional.getNumExpediente().toString() + ".pdf", permisoInternacional.getIdPermiso(), permisoInternacional
							.getNumReferencia());
				}
				return permisoInternacionalDto;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el permiso internacional, error: ", e);
		}
		return null;
	}

	@Override
	public ResultadoPermInterBean getPermisoInternPorNumExpediente(BigDecimal numExpediente) {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			PermisoInternacionalVO permisoInternacional = servicioPersistenciaPermInter.getPermisoInternacionPorExpediente(numExpediente);
			if (permisoInternacional != null) {
				PermisoInternacionalDto permisoInternacionalDto = conversorPermInter.transform(permisoInternacional, PermisoInternacionalDto.class);
				IntervinienteIntergaVO titular = servicioIntervinientePermInter.getIntervinienteTramite(permisoInternacionalDto.getNumExpediente(), TipoTramiteInterga.Permiso_Internacional
						.getValorEnum());
				if (titular != null) {
					permisoInternacionalDto.setTitular(conversorPermInter.transform(titular, IntervinienteIntergaDto.class));
					ImpresionVO impresion = servicioImpresionDocumentos.getDatosPorNombreDocumentoGeneradoYDescargado(servicioInterga.obtenerNombreDocumentoMandato(permisoInternacionalDto
							.getNumExpediente(), TipoTramiteTrafico.PermisonInternacional.getValorEnum()));
					if (impresion != null) {
						permisoInternacionalDto.addMandato(conversorPermInter.transform(impresion, ImpresionDto.class));
					}
				}
				if (EstadoTramitesInterga.Finalizado.getValorEnum().equals(permisoInternacional.getEstado())
						&& EstadoTramitesInterga.Doc_Subida.getValorEnum()
								.equals(permisoInternacional.getEstadoImpresion())
						&& "S".equals(permisoInternacional.getDocumentacionAportada())) {
					permisoInternacionalDto.addListaFicheroInfo(permisoInternacional.getNumExpediente().toString() + ".pdf", permisoInternacional.getIdPermiso(), permisoInternacional
							.getNumReferencia());
				}
				resultado.setPermisoInternacional(permisoInternacionalDto);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el permiso internacional, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al recuperar el permiso internacional.");
		}
		return resultado;
	}

	@Override
	public ResultadoPermInterBean validar(Long idPermisoIntern, Long idUsuario) {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			PermisoInternacionalVO permisoInternacionalVO = servicioPersistenciaPermInter.getPermisoInternacional(idPermisoIntern);
			resultado = validarTramite(permisoInternacionalVO, idPermisoIntern);
			if (!resultado.getError()) {
				String estadoAnterior = permisoInternacionalVO.getEstado();
				servicioPersistenciaPermInter.actualizarEstado(idPermisoIntern, EstadoTramitesInterga.Validado.getValorEnum());
				servicioEvolucionPermisoInternacional.guardar(idPermisoIntern, estadoAnterior, EstadoTramitesInterga.Validado.getValorEnum(), idUsuario, TipoActualizacion.MOD.getValorEnum());
				resultado.setMensaje("Tramite: " + permisoInternacionalVO.getNumExpediente() + " validado correctamente.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el tramite de permiso internacional con ID: " + idPermisoIntern + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el tramite con ID: " + idPermisoIntern);
		}
		return resultado;
	}

	private ResultadoPermInterBean validarTramite(PermisoInternacionalVO permisoInternacionalVO, Long idPermisoIntern) {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		if (permisoInternacionalVO == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos en BBDD para el id: " + idPermisoIntern);
		} else if (!EstadoTramitesInterga.Declaracion_Firmada.getValorEnum().equals(permisoInternacionalVO.getEstado())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + permisoInternacionalVO.getNumExpediente() + " no se puede validar porque no se encuentra en estado 'Declaración Firmada'.");
		} else if (permisoInternacionalVO.getDoiTitular() == null || permisoInternacionalVO.getDoiTitular().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de rellenar el NIF del titular para el expediente: " + permisoInternacionalVO.getNumExpediente());
		} else if (permisoInternacionalVO.getCodigoTasa() == null || permisoInternacionalVO.getCodigoTasa().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de seleccionar una tasa para el expediente: " + permisoInternacionalVO.getNumExpediente());
		} else if (servicioPersistenciaPermInter.existeTramiteDoiPendienteDgt(permisoInternacionalVO.getDoiTitular(), EstadoTramitesInterga.Pendiente_Repuesta_DGT.getValorEnum())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Existe ya un tramite Pendiente de Respuesta de DGT por lo que hasta que no finalize dicho tramite no se podra realizar ningún otro envio para el NIF: "
					+ permisoInternacionalVO.getDoiTitular());
		} else if (servicioPersistenciaPermInter.existeTramiteDoiPendienteDgt(permisoInternacionalVO.getDoiTitular(), EstadoTramitesInterga.Finalizado_Pdt_PDF.getValorEnum())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Existe ya un tramite Finalizado pero Pendiente del PDF por lo que hasta que no finalize dicho tramite no se podra realizar ningún otro envio para el NIF: "
					+ permisoInternacionalVO.getDoiTitular());
		} else {
			IntervinienteIntergaVO titular = servicioIntervinientePermInter.getIntervinienteTrafico(permisoInternacionalVO.getNumExpediente(), permisoInternacionalVO.getDoiTitular(),
					permisoInternacionalVO.getNumColegiado(), TipoTramiteInterga.Permiso_Internacional.getValorEnum());
			if (titular != null) {
				if (utilesNifValidator.isValidDniNieCif(titular.getNif()) == -1) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El NIF/CIF del titular del expediente: " + permisoInternacionalVO.getNumExpediente() + " no es valido.");
				} else if (titular.getPersona() == null) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Debe de rellenar los datos de la persona para el expediente: " + permisoInternacionalVO.getNumExpediente());
				} else if (titular.getPersona().getApellido1RazonSocial() == null || titular.getPersona().getApellido1RazonSocial().isEmpty()) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El Apellido1 o razon social del titular del expediente: " + permisoInternacionalVO.getNumExpediente() + " debe de ir relleno.");
				} else if (utilesNifValidator.FISICA == utilesNifValidator.isValidDniNieCif(titular.getNif())) {
					if (titular.getPersona().getNombre() == null || titular.getPersona().getNombre().isEmpty()) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("El nombre del titular del expediente: " + permisoInternacionalVO.getNumExpediente() + " debe de ir relleno.");
					}
				}
				if (titular.getDireccion() != null) {
					DireccionVO dirTitular = titular.getDireccion();
					if ((dirTitular.getIdProvincia() != null && !dirTitular.getIdProvincia().isEmpty())
							|| (dirTitular.getIdMunicipio() != null && !dirTitular.getIdMunicipio().isEmpty())
							|| (dirTitular.getNombreVia() != null && !dirTitular.getNombreVia().isEmpty())
							|| (dirTitular.getNumero() != null && !dirTitular.getNumero().isEmpty())
							|| (dirTitular.getCodPostal() != null && !dirTitular.getCodPostal().isEmpty())) {
						if (dirTitular.getIdProvincia() == null || dirTitular.getIdProvincia().isEmpty()) {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("Debe de seleccionar la provincia de la direccion del titular para el expediente: " + permisoInternacionalVO.getNumExpediente());
						} else if (dirTitular.getIdMunicipio() == null || dirTitular.getIdMunicipio().isEmpty()) {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("Debe de seleccionar el municipio de la direccion del titular para el expediente: " + permisoInternacionalVO.getNumExpediente());
						} else if (dirTitular.getNombreVia() == null || dirTitular.getNombreVia().isEmpty()) {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("Debe de seleccionar el nombre de la via de la direccion del titular para el expediente: " + permisoInternacionalVO.getNumExpediente());
						} else if (dirTitular.getNumero() == null || dirTitular.getNumero().isEmpty()) {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("Debe de seleccionar el numero de la via de la direccion del titular para el expediente: " + permisoInternacionalVO.getNumExpediente());
						} else if (dirTitular.getCodPostal() == null || dirTitular.getCodPostal().isEmpty()) {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("Debe de seleccionar el codigo postal de la direccion del titular para el expediente: " + permisoInternacionalVO.getNumExpediente());
						}
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se encuentran datos en la BBDD para el titular del expediente: " + permisoInternacionalVO.getNumExpediente());
			}
		}
		return resultado;
	}

	@Override
	public ResultadoPermInterBean guardar(PermisoInternacionalDto permisoInternacional, BigDecimal idUsuario, boolean cambioDomicilio) {
		ResultadoPermInterBean respuesta = new ResultadoPermInterBean(Boolean.FALSE);
		try {

			PermisoInternacionalVO permisoInternacionalVO = conversorPermInter.transform(permisoInternacional, PermisoInternacionalVO.class);
			ResultadoPermInterBean respuestaPermiso = servicioPersistenciaPermInter.guardarPermiso(permisoInternacionalVO, idUsuario);
			if (respuestaPermiso != null && !respuestaPermiso.getError()) {
				respuesta.setIdPermiso(respuestaPermiso.getIdPermiso());
				permisoInternacionalVO.setNumExpediente(respuestaPermiso.getNumExpediente());

				ResultadoPermInterBean respuestaTasa = gestionarTasa(permisoInternacional.getCodigoTasa(),
						permisoInternacionalVO.getNumExpediente(), permisoInternacional.getIdContrato(),
						idUsuario.longValue());
				if (respuestaTasa != null && respuestaTasa.getError() && StringUtils.isNotBlank(respuestaTasa.getMensaje())) {
					respuesta.addListaMensaje(respuestaTasa.getMensaje());
				}

				ResultadoPermInterBean respuestaInterviniente = guardarIntervinientes(permisoInternacional.getTitular(), permisoInternacionalVO.getNumExpediente(), permisoInternacional
						.getNumColegiado(), idUsuario.longValue(), cambioDomicilio);
				if (respuestaInterviniente.getListaMensajes() != null) {
					for (String mensaje : respuestaInterviniente.getListaMensajes()) {
						respuesta.addListaMensaje(mensaje);
					}
				}
			} else {
				log.error("Error al guardar el permiso internacional: " + permisoInternacional.getNumExpediente() + ". Mensaje: " + respuestaPermiso.getMensaje());
				respuesta.setError(true);
				respuesta.addListaMensaje(respuestaPermiso.getMensaje());
			}
		} catch (Exception e) {
			log.error("Error al guardar el permiso internacional: " + permisoInternacional.getNumExpediente() + ". Mensaje: " + e.getMessage(), e, permisoInternacional.getNumExpediente().toString());
			respuesta.setError(true);
			respuesta.addListaMensaje(e.getMessage());
		}
		return respuesta;
	}

	private ResultadoPermInterBean gestionarTasa(String codigoTasa, BigDecimal numExpediente, Long idContrato, Long idUsuario) {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			TasaVO tasaBBDD = servicioTasa.getTasa(null, numExpediente, TipoTasa.CuatroCinco.getValorEnum());
			ResultadoBean resultadoTasa = new ResultadoBean(Boolean.FALSE);
			if (tasaBBDD == null && StringUtils.isNotBlank(codigoTasa)) {
				resultadoTasa = servicioTasa.asignarTasaExpediente(codigoTasa, numExpediente, idContrato, TipoTasa.CuatroCinco.getValorEnum(), idUsuario);
			} else {
				if (tasaBBDD != null && StringUtils.isNotBlank(tasaBBDD.getCodigoTasa()) && StringUtils.isBlank(codigoTasa)) {
					resultadoTasa = servicioTasa.desasignarTasaExpediente(tasaBBDD.getCodigoTasa(), numExpediente, idContrato, TipoTasa.CuatroCinco.getValorEnum(), idUsuario);
				} else if (tasaBBDD != null && !tasaBBDD.getCodigoTasa().equals(codigoTasa)) {
					resultadoTasa = servicioTasa.desasignarTasaExpediente(tasaBBDD.getCodigoTasa(), numExpediente, idContrato, TipoTasa.CuatroCinco.getValorEnum(), idUsuario);
					if (!resultadoTasa.getError()) {
						resultadoTasa = servicioTasa.asignarTasaExpediente(codigoTasa, numExpediente, idContrato, TipoTasa.CuatroCinco.getValorEnum(), idUsuario);
					}
				}
			}
			if (resultadoTasa != null && resultadoTasa.getError()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultadoTasa.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de gestionar la tasa del permiso internacional para el expediente: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de gestionar la tasa del permiso internacional para el expediente: " + numExpediente);
		}
		return resultado;
	}

	private ResultadoPermInterBean guardarIntervinientes(IntervinienteIntergaDto titular, BigDecimal numExpediente,
			String numColegiado, Long idUsuario, boolean cambioDomicilio) {
		ResultadoPermInterBean result = new ResultadoPermInterBean(Boolean.FALSE);
		boolean direccionNueva = false;
		if (titular != null && titular.getPersona() != null && StringUtils.isNotBlank(titular.getPersona().getNif())) {
			IntervinienteIntergaVO titularVO = conversorPermInter.transform(titular, IntervinienteIntergaVO.class);
			if (StringUtils.isNotBlank(titularVO.getPersona().getId().getNif())) {
				titularVO.setNif(titularVO.getPersona().getId().getNif().toUpperCase());
				titularVO.setNumExpediente(numExpediente);
				titularVO.setNumColegiado(numColegiado);
				titularVO.setTipoTramiteInterga(TipoTramiteInterga.Permiso_Internacional.getValorEnum());
				titularVO.getPersona().getId().setNumColegiado(numColegiado);
				titularVO.getPersona().setEstado(1L);

				String conversion = ServicioComunPersona.CONVERSION_PERSONA_TITULAR;

				// Guardar persona
				ResultadoPersonaBean resultPersona = servicioPersona.guardarActualizar(titularVO.getPersona(),
						numExpediente, TipoTramiteTrafico.PermisonInternacional.getValorEnum(), idUsuario, conversion);

				if (!resultPersona.getError()) {
					// Guardar direccion
					if (titularVO.getDireccion() != null && utiles.convertirCombo(titularVO.getDireccion().getIdProvincia()) != null && cambioDomicilio) {
						ResultadoPersonaBean resultDireccion = servicioComunDireccion.guardarActualizarPersona(
								titularVO.getDireccion(), titularVO.getPersona().getId().getNif(),
								titularVO.getPersona().getId().getNumColegiado(),
								TipoTramiteTrafico.PermisonInternacional.getValorEnum(),
								ServicioComunDireccion.CONVERSION_DIRECCION_INE);
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
								guardarEvolucionPersonaNuevaDireccion(titularVO.getPersona().getId().getNif(),
										numColegiado, numExpediente,
										TipoTramiteTrafico.PermisonInternacional.getValorEnum(), idUsuario);
							}
						}
					} else {
						titularVO.setIdDireccion(null);
					}
					// Guardar interviniente
					ResultadoBean resultInterviente = servicioIntervinientePermInter.guardarActualizar(titularVO, TipoTramiteInterga.Permiso_Internacional.getValorEnum());
					if (resultInterviente.getListaMensajes() != null) {
						for (String mensaje : resultInterviente.getListaMensajes()) {
							result.addListaMensaje(mensaje);
						}
					}
				} else {
					result.addListaMensaje(resultPersona.getMensaje());
				}
			} else {
				servicioIntervinientePermInter.eliminar(titularVO, TipoTramiteInterga.Permiso_Internacional.getValorEnum());
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

		servicioComunEvolucionPersona.guardarEvolucionPersona(evolucion);
	}

	@Override
	public ResultadoPermInterBean getTitular(String nif, String numColegiado) {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			IntervinienteIntergaVO interviniente = servicioIntervinientePermInter.crearIntervinienteNif(nif, numColegiado);
			if (interviniente != null) {
				IntervinienteIntergaDto intervinienteDto = conversorPermInter.transform(interviniente, IntervinienteIntergaDto.class);
				resultado.setTitular(intervinienteDto);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el titular con NIF: " + nif + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener el titular con nif: " + nif);
		}
		return resultado;
	}

	@Override
	public ResultadoPermInterBean subirDocAportada(Long idPermiso, String numReferencia, File fichero, String ficheroFileName, Long idUsuario) {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);

		if (!utiles.esNombreFicheroValido(ficheroFileName)) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El fichero añadido debe tener un nombre correcto");
			log.error("El fichero añadido debe tener un nombre correcto");
			return resultado;
		}

		try {
			PermisoInternacionalVO permisoInternacionalVO = servicioPersistenciaPermInter.getPermisoInternacional(idPermiso);
			resultado = validarSubirDocTramite(permisoInternacionalVO, numReferencia, idPermiso, fichero, ficheroFileName);
			if (!resultado.getError()) {
				ResultadoIntergaBean resultadoInterga = servicioPermInterRestWS.subidaDocumentacionEscaneada(permisoInternacionalVO, fichero, numReferencia);
				if (resultadoInterga != null) {
					if (!resultadoInterga.getError()) {
						gestorDocumentos.guardarFichero(ConstantesGestorFicheros.PERMISO_INTERNACIONAL, ConstantesGestorFicheros.APORTADO, utiles.transformExpedienteInterga(permisoInternacionalVO
								.getNumExpediente().toString()), permisoInternacionalVO.getNumExpediente().toString(), ConstantesGestorFicheros.EXTENSION_PDF, fichero);
						servicioPersistenciaPermInter.actualizarSubidaDoc(permisoInternacionalVO.getIdPermiso(), numReferencia);
						servicioEvolucionPermisoInternacional.guardar(idPermiso, permisoInternacionalVO.getEstadoImpresion(), EstadoTramitesInterga.Doc_Subida.getValorEnum(), idUsuario,
								TipoActualizacion.MOD.getValorEnum());
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(resultadoInterga.getMensaje());
					}
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de subir la documentacion para el tramite con id: " + idPermiso + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de subir la documentacion para el tramite con id: " + idPermiso);
		}
		return resultado;
	}

	private ResultadoPermInterBean validarSubirDocTramite(PermisoInternacionalVO permisoInternacionalVO, String numReferencia, Long idPermisoIntern, File fichero, String ficheroFileName) {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		if (permisoInternacionalVO == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos en BBDD para el id: " + idPermisoIntern);
		} else if (!EstadoTramitesInterga.Finalizado.getValorEnum().equals(permisoInternacionalVO.getEstado())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + permisoInternacionalVO.getNumExpediente() + " no se puede subir documentación porque no se encuentra en estado Finalizado.");
		} else if (!EstadoTramitesInterga.Impresion_OK.getValorEnum().equals(permisoInternacionalVO.getEstadoImpresion())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + permisoInternacionalVO.getNumExpediente() + " no se puede subir documentación porque no se encuentra en estado Impreso.");
		} else if (numReferencia == null || numReferencia.isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + permisoInternacionalVO.getNumExpediente() + " no se puede subir documentación porque no se ha indicado el numero de referencia.");
		} else if (fichero == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe seleccionar un documento para el tramite: " + permisoInternacionalVO.getNumExpediente());
		} else {
			String extension = utiles.dameExtension(ficheroFileName, Boolean.FALSE);
			if (extension == null || extension.isEmpty() || !extension.equals("pdf")) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El documento para el tramite: " + permisoInternacionalVO.getNumExpediente() + " no se puede subir porque solo se permiten documento con extension PDF.");
			}
		}
		return resultado;
	}

	@Override
	public ResultadoPermInterBean descargarDocAportada(Long idPermisoIntern) {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			PermisoInternacionalVO permisoInternacionalVO = servicioPersistenciaPermInter.getPermisoInternacional(idPermisoIntern);
			resultado = validarDescargaDocTramite(permisoInternacionalVO, idPermisoIntern);
			if (!resultado.getError()) {
				FileResultBean fileResult = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.PERMISO_INTERNACIONAL, ConstantesGestorFicheros.APORTADO, utiles
						.transformExpedienteInterga(permisoInternacionalVO.getNumExpediente().toString()), permisoInternacionalVO.getNumExpediente().toString(),
						ConstantesGestorFicheros.EXTENSION_PDF);
				if (fileResult != null && fileResult.getFile() != null) {
					resultado.setFichero(fileResult.getFile());
					resultado.setNombreFichero(permisoInternacionalVO.getNumExpediente().toString() + ConstantesGestorFicheros.EXTENSION_PDF);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se ha encontrado el PDF para la descargar de la documentación aportada del expediente: " + permisoInternacionalVO.getNumExpediente());
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de descargar la documentación aportada para el tramite de permiso internacional con ID: " + idPermisoIntern + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de descargar documentación aportada para tramitar el tramite con ID: " + idPermisoIntern);
		}
		return resultado;
	}

	private ResultadoPermInterBean validarDescargaDocTramite(PermisoInternacionalVO permisoInternacionalVO, Long idPermisoIntern) {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		if (permisoInternacionalVO == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos en BBDD para el id: " + idPermisoIntern);
		} else if (!EstadoTramitesInterga.Finalizado.getValorEnum().equals(permisoInternacionalVO.getEstado())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + permisoInternacionalVO.getNumExpediente() + " no se puede descargar su documentación porque no se encuentra en estado Finalizado.");
		} else if (!EstadoTramitesInterga.Doc_Subida.getValorEnum().equals(permisoInternacionalVO.getEstadoImpresion())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + permisoInternacionalVO.getNumExpediente() + " no se puede descargar su documentación porque no se encuentra en estado Documentacion Subida.");
		}
		return resultado;
	}

	@Override
	public void finalizarConError(Long idPermiso, Long idUsuario, String respuesta) {
		try {
			PermisoInternacionalVO permiso = servicioPersistenciaPermInter.getPermisoInternacional(idPermiso);
			if (permiso != null) {
				String estadoAnterior = permiso.getEstado();
				permiso.setEstado(EstadoTramitesInterga.Finalizado_Error.getValorEnum());
				permiso.setRespuesta(respuesta);
				servicioPersistenciaPermInter.actualizar(permiso);
				servicioEvolucionPermisoInternacional.guardar(idPermiso, estadoAnterior, EstadoTramitesInterga.Finalizado_Error.getValorEnum(), idUsuario, TipoActualizacion.MOD.getValorEnum());
			}
		} catch (Exception e) {
			log.error("Error al Finalizar con Error el permiso internacional", e);
		}
	}

	@Override
	public void generarDocDocumentacion(Long idPermiso, String estadoNuevo) {
		try {
			PermisoInternacionalVO permisoInternacional = servicioPersistenciaPermInter.getPermisoInternacional(idPermiso);
			Map<String, Object> params = new HashMap<>();
			params.put("numeroTramites", 1);
			params.put("numTramitesPagina", 30);
			params.put("numeroTramites", 1);
			String xml = generarXmlDocumentacion(permisoInternacional);
			String nombreFichero = "JustificanteDoc_" + permisoInternacional.getNumExpediente().toString();
			ReportExporter re = new ReportExporter();
			byte[] fichero = re.generarInforme(gestorPropiedades.valorPropertie("rutaDirectorioDatos") + gestorPropiedades.valorPropertie("documentoPermisoInternacional.plantillas.rutaPlantillas"),
					"justificantePermisoInternacional", "pdf", xml, null, params, null);
			if (fichero != null) {
				gestorDocumentos.guardarFichero(ConstantesGestorFicheros.PERMISO_INTERNACIONAL, ConstantesGestorFicheros.JUSTIFICANTE_DOC, utiles.transformExpedienteInterga(permisoInternacional
						.getNumExpediente().toString()), nombreFichero, ConstantesGestorFicheros.EXTENSION_PDF, fichero);
				if (EstadoTramitesInterga.Finalizado.getValorEnum().equals(estadoNuevo)) {
					FicheroBean ficheroBean = new FicheroBean();
					ficheroBean.setFicheroByte(fichero);
					ficheroBean.setNombreDocumento(nombreFichero);
					ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
					enviarMailJustificanteImpresionPermisoInter(permisoInternacional.getContrato(), ficheroBean, permisoInternacional.getJefatura(), nombreFichero,
							permisoInternacional.getNumExpediente().toString());
				} else {
					enviarMailPdtDeclaracion(permisoInternacional.getContrato(), permisoInternacional.getNumExpediente().toString());
				}
			} else {
				enviarMailError("No se ha podido guardar el justificante de la impresion del permiso internacional", "Error guardado justificante permiso internacional", permisoInternacional
						.getNumExpediente().toString());
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de generar el justificante con el tramite del idPermiso: " + idPermiso + ", error: ", e);
			enviarMailError("Ha sucedido un error a la hora de generar el justificante con el tramite del idPermiso: " + idPermiso + ", error: " + e.getMessage(),
					"Error guardado justificante permiso internacional", idPermiso.toString());
		}
	}

	@Override
	public void enviarMailJustificanteImpresionPermisoInter(ContratoVO contrato, FicheroBean ficheroBean, String jefatura, String nombreFichero, String numExpediente) {
		Boolean enviarCorreoError = Boolean.FALSE;
		String mensajeError = "";
		try {
			String dirOculta = gestorPropiedades.valorPropertie("direcciones.ocultas.impresiones.permisoInternacional");
			String direccionCC = obtenerDireccion(jefatura);
			StringBuffer sb = new StringBuffer();
			sb.append("Este es el justificante del permiso internacional solicitado al Ilustre Colegio de Gestores Administrativos de Madrid y el cual se va a proceder a imprimir.");
			sb.append("<br>");
			sb.append("Recuerde que para poder retirar el permiso internacional debera de presentar dicho justificante firmado, así como el mandato y solicitud del permiso relleno y ");
			sb.append("foto tamaño carnet (32x26) y fotocopia del DNI y carnet de conducir del titular compulsada por el colegiado.");

			String direccionCorreo = contrato.getCorreoElectronico();
			if (contrato.getCorreosTramites() != null && !contrato.getCorreosTramites().isEmpty()) {
				for (CorreoContratoTramiteVO correoContratoTramite : contrato.getCorreosTramites()) {
					if (TipoTramiteTrafico.PermisonInternacional.getValorEnum().equalsIgnoreCase(correoContratoTramite.getTipoTramite().getTipoTramite())) {
						direccionCorreo = correoContratoTramite.getCorreoElectronico();
						break;
					}
				}
			}

			ResultadoBean resultEnvio = servicioComunCorreo.enviarCorreo(sb.toString(), Boolean.FALSE, null,
					"Justificante Solicitud Impresion Permiso Internacional", direccionCorreo, direccionCC, dirOculta,
					null, ficheroBean);
			if (resultEnvio.getError()) {
				for (String mensaje : resultEnvio.getListaMensajes()) {
					log.error(mensaje);
					mensaje += " " + mensaje;
				}
				enviarCorreoError = Boolean.TRUE;
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de enviar el correo al colegiado con el justificante de la solicitud de impresion del permiso internacional, error: ", e);
			mensajeError = "Ha sucedido un error a la hora de enviar el correo al colegiado con el justificante de la solicitud de impresion del permiso internacional, error: " + e.getMessage();
			enviarCorreoError = Boolean.TRUE;
		}
		if (enviarCorreoError) {
			enviarMailError(mensajeError, "Error envio Justificante Solicitud Impresion Permiso Internacional", numExpediente);
		}
	}

	private void enviarMailError(String mensajeError, String asunto, String numExpediente) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(mensajeError);
			sb.append("<br>");
			sb.append("El error ha sucedido para el expediente: " + numExpediente);
			ResultadoBean resultEnvio = servicioComunCorreo.enviarCorreo(sb.toString(), Boolean.FALSE, null, asunto, gestorPropiedades.valorPropertie("direcciones.permisoInternacional.mail.error"),
					null, null, null);
			if (resultEnvio.getError()) {
				for (String mensaje : resultEnvio.getListaMensajes()) {
					log.error(mensaje);
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de enviar el correo de aviso de error, error: ", e);
		}
	}

	private void enviarMailPdtDeclaracion(ContratoVO contrato, String numExpediente) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("Debe enviar la declaración de responsabilidad para finalizar el permiso internacional. Para enviarla accede al trámite desde OEGAM.");
			sb.append("<br>");
			sb.append("El trámite a pendiente de finalizar es para el expediente: " + numExpediente);
			String asunto = "Permiso Internacional pendiente de enviar la declaración de responsabilidad";

			String direccionCorreo = contrato.getCorreoElectronico();
			if (contrato.getCorreosTramites() != null && !contrato.getCorreosTramites().isEmpty()) {
				for (CorreoContratoTramiteVO correoContratoTramite : contrato.getCorreosTramites()) {
					if (TipoTramiteTrafico.PermisonInternacional.getValorEnum().equalsIgnoreCase(correoContratoTramite.getTipoTramite().getTipoTramite())) {
						direccionCorreo = correoContratoTramite.getCorreoElectronico();
						break;
					}
				}
			}

			ResultadoBean resultEnvio = servicioComunCorreo.enviarCorreo(sb.toString(), Boolean.FALSE, null, asunto, direccionCorreo, null, null, null);
			if (resultEnvio.getError()) {
				for (String mensaje : resultEnvio.getListaMensajes()) {
					log.error(mensaje);
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de enviar el correo de aviso de pendiente de declaración, error: ", e);
		}
	}

	private String obtenerDireccion(String jefatura) {
		String direcciones = null;
		if (JefaturasJPTEnum.MADRID.getJefatura().equals(jefatura)) {
			direcciones = gestorPropiedades.valorPropertie("direcciones.permisoInternacional.mail.arturosoria.bcc");
		} else if (JefaturasJPTEnum.ALCORCON.getJefatura().equals(jefatura)) {
			direcciones = gestorPropiedades.valorPropertie("direcciones.permisoInternacional.mail.alcorcon.bcc");
		} else if (JefaturasJPTEnum.ALCALADEHENARES.getJefatura().equals(jefatura)) {
			direcciones = gestorPropiedades.valorPropertie("direcciones.permisoInternacional.mail.alcala.bcc");
		} else if (JefaturasJPTEnum.SEGOVIA.getJefatura().equals(jefatura)) {
			direcciones = gestorPropiedades.valorPropertie("direcciones.permisoInternacional.mail.segovia.bcc");
		} else if (JefaturasJPTEnum.AVILA.getJefatura().equals(jefatura)) {
			direcciones = gestorPropiedades.valorPropertie("direcciones.permisoInternacional.mail.avila.bcc");
		} else if (JefaturasJPTEnum.CUENCA.getJefatura().equals(jefatura)) {
			direcciones = gestorPropiedades.valorPropertie("direcciones.permisoInternacional.mail.cuenca.bcc");
		} else if (JefaturasJPTEnum.CIUDADREAL.getJefatura().equals(jefatura)) {
			direcciones = gestorPropiedades.valorPropertie("direcciones.permisoInternacional.mail.ciudadreal.bcc");
		} else if (JefaturasJPTEnum.GUADALAJARA.getJefatura().equals(jefatura)) {
			direcciones = gestorPropiedades.valorPropertie("direcciones.permisoInternacional.mail.guadalajara.bcc");
		}
		return direcciones;
	}

	private String generarXmlDocumentacion(PermisoInternacionalVO permisoInternacional) {
		DocumentoTramitesPermisoInterBean docBean = new DocumentoTramitesPermisoInterBean();
		docBean.setDocumento("Permiso Internacional");
		docBean.setFechaPresentacion(utilesFecha.formatoFecha("dd/MM/yyyy", permisoInternacional.getFechaPresentacion()));
		docBean.setGestoria(permisoInternacional.getContrato().getColegiado().getNumColegiado() + " - " + permisoInternacional.getContrato().getVia());
		docBean.setJefatura(JefaturasJPTEnum.convertirJefatura(permisoInternacional.getJefatura()));
		docBean.setNifGestor(permisoInternacional.getContrato().getColegiado().getUsuario().getNif());
		TramitePermisoInternDocBean tramite = new TramitePermisoInternDocBean();
		tramite.setFechaPresentacion(utilesFecha.formatoFecha("dd/MM/yyyy", permisoInternacional.getFechaPresentacion()));
		tramite.setNifTitular(permisoInternacional.getDoiTitular());
		tramite.setNumero(1);
		tramite.setNumExpediente(permisoInternacional.getNumExpediente().toString());
		docBean.addListaTramites(tramite);

		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>";
		XStream xStream = new XStream();
		xStream.processAnnotations(DocumentoTramitesPermisoInterBean.class);
		xml += xStream.toXML(docBean);
		xml = xml.replaceAll("__", "_");
		xml = xml.replaceAll("\n *<", "<");
		return xml;
	}
}