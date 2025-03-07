package org.gestoresmadrid.oegamInterga.rest.service.impl;

import java.math.BigDecimal;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoRespuestaDGT;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoTramitesInterga;
import org.gestoresmadrid.oegamInterga.rest.service.ServicioIntergaRestWS;
import org.gestoresmadrid.oegamInterga.restWS.IntergaRest;
import org.gestoresmadrid.oegamInterga.restWS.request.FileRequest;
import org.gestoresmadrid.oegamInterga.restWS.request.SendFilesRequest;
import org.gestoresmadrid.oegamInterga.restWS.request.UpdateRequest;
import org.gestoresmadrid.oegamInterga.restWS.response.ErrorResponse;
import org.gestoresmadrid.oegamInterga.restWS.response.Errors;
import org.gestoresmadrid.oegamInterga.restWS.response.FilePdfResponse;
import org.gestoresmadrid.oegamInterga.restWS.response.FileResponse;
import org.gestoresmadrid.oegamInterga.restWS.response.SendFilesResponse;
import org.gestoresmadrid.oegamInterga.restWS.response.UpdateResponse;
import org.gestoresmadrid.oegamInterga.view.bean.ResultadoIntergaBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioIntergaRestWSImpl implements ServicioIntergaRestWS {

	private static final long serialVersionUID = -700508065321934785L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioIntergaRestWSImpl.class);

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	IntergaRest intergaRest;

	@Autowired
	Utiles utiles;

	@Override
	public ResultadoIntergaBean enviar(SendFilesRequest request) {
		ResultadoIntergaBean resultado = new ResultadoIntergaBean(Boolean.FALSE);
		try {
			resultado = intergaRest.enviar(request);
			if (resultado != null && !resultado.getError()) {
				if (resultado.getCode() == 200) {
					SendFilesResponse response = (SendFilesResponse) resultado.getResponse();
					if ("OK".equals(response.getResult())) {
						resultado.setMensaje(response.getResult());
					} else {
						resultado.setError(Boolean.TRUE);
						if (response.getErrors() != null && !response.getErrors().isEmpty()) {
							String mensaje = "";
							for (Errors error : response.getErrors()) {
								if (StringUtils.isNotBlank(mensaje)) {
									mensaje += ", ";
								}
								mensaje += error.getCode() + " => " + error.getMessage();
							}
							resultado.setMensaje(mensaje);
						}
					}
				} else {
					resultado.setError(Boolean.TRUE);
					Errors response = (Errors) resultado.getResponse();
					resultado.setMensaje(response.getMessage());
				}
			}
		} catch (Exception e) {
			log.error("Error al enviar la solicitud de interga, error:", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al enviar de interga");
		}
		return resultado;
	}

	@Override
	public ResultadoIntergaBean consultar(FileRequest request, BigDecimal numExpediente, String tipoPdf) {
		ResultadoIntergaBean resultado = new ResultadoIntergaBean(Boolean.FALSE);
		try {
			resultado = intergaRest.consultar(request);
			if (resultado != null && !resultado.getError()) {
				if (resultado.getCode() == 200) {
					FileResponse responseCon = (FileResponse) resultado.getResponse();
					if ("OK".equals(responseCon.getCode())) {
						if (responseCon.getFile() != null) {
							String estadoFinal = EstadoRespuestaDGT.Tramitado.getValorEnum();
							if (!TIPO_PERMISO_INTERNACIONAL.equals(request.getFileType())) {
								estadoFinal = EstadoRespuestaDGT.Finalizado.getValorEnum();
							}

							if (estadoFinal.equals(responseCon.getFile().getEstado())) {
								resultado = descargar(request, numExpediente, tipoPdf);
								if (resultado != null && !resultado.getError()) {
									resultado.setEstado(EstadoTramitesInterga.Finalizado.getValorEnum());
								} else if (StringUtils.isNotBlank(resultado.getCodigoError()) && "ERR008".equals(resultado.getCodigoError())) {
									resultado.setEstado(EstadoTramitesInterga.Finalizado_Pdt_PDF.getValorEnum());
								} else {
									resultado.setEstado(EstadoTramitesInterga.Finalizado_Error.getValorEnum());
								}
							} else if (EstadoRespuestaDGT.Con_Errores_Resolucion.getValorEnum().equals(responseCon.getFile().getEstado())) {
								String respuesta = "La respuesta de la consulta devuelve el estado 'Con errores en la resolución'. ";
								if (StringUtils.isNotBlank(responseCon.getFile().getDescripcion())) {
									respuesta += "Mensaje: " + responseCon.getFile().getDescripcion();
								}
								resultado.setMensaje(respuesta);
								resultado.setEstado(EstadoTramitesInterga.Finalizado_Error.getValorEnum());
							} else if (EstadoRespuestaDGT.Errores_DGT.getValorEnum().equals(responseCon.getFile().getEstado())) {
								resultado.setMensaje("La respuesta de la consulta devuelve el estado 'No fue procesado por DGT, debe enviarse un nuevo' ");
								resultado.setEstado(EstadoTramitesInterga.Finalizado_Error.getValorEnum());
							} else {
								resultado.setMensaje("La respuesta de la consulta devuelve el estado: " + EstadoRespuestaDGT.convertirTexto(responseCon.getFile().getEstado()));
								log.error("La respuesta de la consulta devuelve el estado: " + EstadoRespuestaDGT.convertirTexto(responseCon.getFile().getEstado()));
							}
						} else {
							resultado.setMensaje("La respuesta de la consulta no devuelve el estado");
							log.error("La respuesta de la consulta no devuelve el estado");
						}
					} else {
						resultado.setMensaje(responseCon.getCode() + " => " + responseCon.getMessage());
					}
				} else {
					ErrorResponse response = (ErrorResponse) resultado.getResponse();
					resultado.setMensaje(response.getMessage());
				}
			}
		} catch (

		Exception e) {
			log.error("Error al consultar el tramite de interga error:", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error el tramite de interga");
		}
		return resultado;
	}

	private ResultadoIntergaBean descargar(FileRequest request, BigDecimal numExpediente, String tipoPdf) {
		ResultadoIntergaBean resultado = new ResultadoIntergaBean(Boolean.FALSE);
		try {
			resultado = intergaRest.descargar(request, tipoPdf);
			if (resultado != null && !resultado.getError()) {
				if (resultado.getCode() == 200) {
					FilePdfResponse response = (FilePdfResponse) resultado.getResponse();
					if ("OK".equals(response.getCode())) {
						resultado = guardarPdf(response.getPdfB64(), numExpediente, tipoPdf);
						if (resultado != null && !resultado.getError()) {
							if (StringUtils.isBlank(response.getMessage())) {
								resultado.setMensaje("OK => PDF descargado");
							} else {
								resultado.setMensaje(response.getMessage());
							}
						}
					} else {
						resultado.setCodigoError(response.getCode());
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(response.getCode() + " => " + response.getMessage());
					}
				} else {
					resultado.setError(Boolean.TRUE);
					ErrorResponse response = (ErrorResponse) resultado.getResponse();
					resultado.setMensaje(response.getMessage());
				}
			}
		} catch (Exception e) {
			log.error("Error al consultar el permiso internacional error:", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error el permiso internacional");
		}
		return resultado;
	}

	@Override
	public ResultadoIntergaBean subidaDocumentacion(UpdateRequest request, String tipoPdf) {
		ResultadoIntergaBean resultado = new ResultadoIntergaBean(Boolean.FALSE);
		try {
			resultado = intergaRest.subirDocumentacion(request, tipoPdf);
			if (resultado != null && !resultado.getError()) {
				if (resultado.getCode() != 200) {
					ErrorResponse responseDes = (ErrorResponse) resultado.getResponse();
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Error al intentar subir la documentación a DGT: " + responseDes.getMessage());
				} else {
					UpdateResponse response = (UpdateResponse) resultado.getResponse();
					if (!"OK".equals(response.getCode())) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(response.getCode() + " => " + response.getMessage());
					}
				}
			}
		} catch (Exception e) {
			log.error("Error al enviar la solicitud de permiso internacional, error:", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al enviar el permiso internacional");
		}
		return resultado;
	}

	private ResultadoIntergaBean guardarPdf(String pdfB64, BigDecimal numExpediente, String tipoPdf) {
		ResultadoIntergaBean result = new ResultadoIntergaBean(Boolean.FALSE);
		try {
			byte[] pdf = Base64.decodeBase64(pdfB64.getBytes());
			FicheroBean ficheroBean = new FicheroBean();
			ficheroBean.setFicheroByte(pdf);
			if (PDF_ORIGINAL.equals(tipoPdf)) {
				ficheroBean.setTipoDocumento(ConstantesGestorFicheros.DUPLICADO_PERMISO_CONDUCIR);
			} else {
				ficheroBean.setTipoDocumento(ConstantesGestorFicheros.PERMISO_INTERNACIONAL);
			}
			ficheroBean.setSubTipo(ConstantesGestorFicheros.RECIBIDO);
			ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
			ficheroBean.setFecha(utiles.transformExpedienteInterga(numExpediente.toString()));
			ficheroBean.setSobreescribir(true);
			ficheroBean.setNombreDocumento(numExpediente.toString());
			gestorDocumentos.guardarFichero(ficheroBean);
		} catch (Throwable e) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error a la hora de guardar el pdf de interga recibido de la DGT");
			log.error("Error a la hora de guardar el pdf de interga recibido de la DGT", e);
		}
		return result;
	}
}
