package org.gestoresmadrid.oegam2comun.procesos.model.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.NPasos;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.mensajes.procesos.model.service.ServicioMensajesProcesos;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.procesos.model.service.ServicioWebServiceFullCtit;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.TipoConsentimiento;
import org.gestoresmadrid.oegam2comun.trafico.dgt.DGTTransmision;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoTransmision;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoCtitBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.DatosCTITDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafTranDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gescogroup.blackbox.CtitFullAdvise;
import com.gescogroup.blackbox.CtitFullError;
import com.gescogroup.blackbox.CtitFullImpediment;
import com.gescogroup.blackbox.CtitsoapFullResponse;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import trafico.utiles.XmlTransTelematicaFactory;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioWebServiceFullCtitImpl implements ServicioWebServiceFullCtit {

	private static final long serialVersionUID = -2760622172791605255L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServiceFullCtitImpl.class);

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioTramiteTraficoTransmision servicioTramiteTraficoTransmision;

	@Autowired
	ServicioMensajesProcesos servicioMensajesProcesos;

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioCredito servicioCredito;

	@Autowired
	DGTTransmision dgtTransmision;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	ServicioContrato servicioContrato;

	@Override
	public ResultadoCtitBean tramitarPeticion(ColaDto solicitud) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		try {
			String xml = crearXml(solicitud.getIdTramite(), solicitud.getXmlEnviar());
			if (xml != null && !xml.isEmpty()) {
				resultado = llamadaWS(xml, solicitud.getIdTramite(), solicitud.getIdUsuario());
				if (resultado.getAhiSegundoEnvio()) {
					TramiteTrafTranDto tramiteDto = servicioTramiteTraficoTransmision.getTramiteTransmision(solicitud.getIdTramite(), false);
					resultado = dgtTransmision.generarXmlSegundoEnvioFullCtit(tramiteDto, solicitud.getXmlEnviar());
					if (!resultado.getError()) {
						tramiteDto.setNpasos(NPasos.Uno.getValorEnum());
						String xmlSegundoEnvio = crearXml(solicitud.getIdTramite(), resultado.getNombreXml());
						if (xmlSegundoEnvio != null && !xmlSegundoEnvio.isEmpty()) {
							resultado = llamadaWS(xmlSegundoEnvio, solicitud.getIdTramite(), solicitud.getIdUsuario());
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensajeError("No existe el xml de envio para la segunda llamada al WS de fullCtit del tramite.");
						}
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("No existe el xml de envio para poder realizar el fullCtit del tramite.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de realizar el FullCtit, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(new Exception(e.getMessage()));
		}
		return resultado;
	}

	private ResultadoCtitBean llamadaWS(String xml, BigDecimal numExpediente, BigDecimal idUsuario) throws OegamExcepcion {
		ResultadoCtitBean resultadoWS = new ResultadoCtitBean(Boolean.FALSE);
		try {
			DatosCTITDto datosCTITDto = servicioTramiteTraficoTransmision.datosCTIT(numExpediente);
			if (datosCTITDto != null) {
				log.info("Proceso " + ConstantesProcesos.PROCESO_FULLCTIT + " -- Procesando petición");
				CtitsoapFullResponse respuestaWS = dgtTransmision.fullCTITTransmision(xml, datosCTITDto);
				log.info("Proceso " + ConstantesProcesos.PROCESO_FULLCTIT + " -- Peticion Procesada");

				resultadoWS = gestionarRespuesta(respuestaWS, numExpediente, datosCTITDto);
			} else {
				resultadoWS.setError(Boolean.TRUE);
				resultadoWS.setMensajeError("Ha sucedido un error a la hora de completar la request de envio al WS de FULL_CTTIT.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar la llamada al WS de FULL_CTTIT, error: ", e, numExpediente.toString());
			resultadoWS.setError(Boolean.TRUE);
			resultadoWS.setExcepcion(e);
		}
		return resultadoWS;
	}

	private ResultadoCtitBean gestionarRespuesta(CtitsoapFullResponse respuesta, BigDecimal numExpediente, DatosCTITDto datosCTITDto) {
		ResultadoCtitBean resultadoWS = new ResultadoCtitBean(Boolean.FALSE);
		if (respuesta == null) {
			resultadoWS.setError(Boolean.TRUE);
			resultadoWS.setExcepcion(new Exception("Ha sucedido un error en la respuesta del WS de FULL_CTTIT y está se encuentra vacia"));
		} else if (ConstantesProcesos.ERROR.equals(respuesta.getResult())) {
			resultadoWS.setMensajeError(gestionarMensajes(respuesta));
			if (resultadoWS.getMensajeError().contains("generico") || resultadoWS.getMensajeError().contains("timeout") || resultadoWS.getMensajeError().contains("CTITE")
					|| compruebaErroresRecuperables(respuesta.getErrorCodes())) {
				resultadoWS.setEsRecuperable(Boolean.TRUE);
			} else {
				if (respuesta.getLicense() != null && !respuesta.getLicense().isEmpty()) {
					guardarPTC(numExpediente, respuesta.getLicense());
				}
			}
			resultadoWS.setError(Boolean.TRUE);
		} else {
			guardarPTC(numExpediente, respuesta.getLicense());
			guardarInforme(numExpediente, respuesta.getReport());
			if (ConstantesProcesos.OK.equals(respuesta.getResult())) {
				if (NPasos.Uno.getValorEnum().equals(datosCTITDto.getPasos())) {
					resultadoWS.setEstadoNuevoProceso(EstadoTramiteTrafico.Finalizado_Telematicamente);
					resultadoWS.setMensajeError("TRÁMITE OK");
				} else if (NPasos.Dos.getValorEnum().equals(datosCTITDto.getPasos())) {
					if (TipoConsentimiento.NO.equals(datosCTITDto.getConsentimiento())) {
						resultadoWS.setEstadoNuevoProceso(EstadoTramiteTrafico.Finalizado_Con_Error);
						resultadoWS.setMensajeError("Es necesario el consentimiento.");
						resultadoWS.setError(Boolean.TRUE);
					} else {
						resultadoWS.setAhiSegundoEnvio(Boolean.TRUE);
					}
				}
			} else if (ConstantesProcesos.TRAMITABLE_CON_INCIDENCIAS.equals(respuesta.getResult())) {
				log.info("Proceso " + ProcesosEnum.FULL_CTIT_SEGA.getNombreEnum() + " -- " + "Respuesta recibida con resultado TRAMITABLE CON INCIDENCIAS");
				resultadoWS.setMensajeError(gestionarMensajes(respuesta));
				resultadoWS.setEstadoNuevoProceso(EstadoTramiteTrafico.Finalizado_Con_Error);
				resultadoWS.setError(Boolean.TRUE);
			} else if (ConstantesProcesos.NO_TRAMITABLE.equals(respuesta.getResult())) {
				resultadoWS.setMensajeError(gestionarMensajes(respuesta));
				if (respuesta.getReport() != null && !respuesta.getReport().isEmpty()) {
					resultadoWS.setEstadoNuevoProceso(EstadoTramiteTrafico.Informe_Telematico);
				} else {
					resultadoWS.setEstadoNuevoProceso(EstadoTramiteTrafico.Finalizado_Con_Error);
					resultadoWS.setError(Boolean.TRUE);
				}
			} else {
				resultadoWS.setError(Boolean.TRUE);
				resultadoWS.setExcepcion(new Exception(ConstantesProcesos.EL_WEBSERVICE_NO_HA_DADO_UNA_RESPUESTA_CONOCIDA));
			}
		}
		return resultadoWS;
	}

	private void guardarInforme(BigDecimal numExpediente, String informe) {
		if (informe != null && !informe.isEmpty()) {
			try {
				byte[] ptcBytes = utiles.doBase64Decode(informe, UTF_8);
				FicheroBean fichero = new FicheroBean();
				fichero.setTipoDocumento(ConstantesGestorFicheros.CTIT);
				fichero.setSubTipo(ConstantesGestorFicheros.INFORMES);
				fichero.setSobreescribir(true);
				fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
				fichero.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
				fichero.setNombreDocumento(numExpediente.toString());
				fichero.setFicheroByte(ptcBytes);
				gestorDocumentos.guardarByte(fichero);
			} catch (Throwable e) {
				log.error("Ha sucedido un error a la hora de guardar el informe para el tramite: " + numExpediente + ", error: ", e, numExpediente.toString());
			}
		} else {
			log.error("La respuesta del tramite " + numExpediente + " no trae informe.");
		}
	}

	private String gestionarMensajes(CtitsoapFullResponse respuestaWS) {
		String respuestaError = "";
		CtitFullError[] listadoErrores = respuestaWS.getErrorCodes();
		if (listadoErrores != null) {
			listarErroresCTIT(listadoErrores);
			respuestaError += getMensajeError(listadoErrores);
		}

		CtitFullImpediment[] listadoImpedimentos = respuestaWS.getImpedimentCodes();
		if (listadoImpedimentos != null) {
			if (!"".equals(respuestaError)) {
				respuestaError += Claves.BR;
			}
			respuestaError += getImpedimentos(listadoImpedimentos);
		}

		CtitFullAdvise[] listadoAvisos = respuestaWS.getAdviseCodes();
		if (listadoAvisos != null) {
			if (!"".equals(respuestaError)) {
				respuestaError += Claves.BR;
			}
			respuestaError += getAvisos(listadoAvisos);
		}
		return respuestaError;
	}

	private String getAvisos(CtitFullAdvise[] listadoAvisos) {
		StringBuffer aviso = new StringBuffer();
		for (int i = 0; i < listadoAvisos.length; i++) {
			if (i > 0)
				aviso.append(Claves.BR);
			aviso.append(listadoAvisos[i].getKey());
			aviso.append(" - ");
			String error = utiles.quitarCaracteresExtranios(listadoAvisos[i].getMessage());
			if (error.length() > 80) {
				String resAux = "";
				for (int tam = 0; tam <= Math.floor(error.length() / 80); tam++) {
					if (tam != Math.floor(error.length() / 80)) {
						resAux += error.substring(80 * tam, 80 * tam + 80) + Claves.BR;
					} else {
						resAux += error.substring(80 * tam) + Claves.BR;
					}
				}
				error = resAux;
			}
			aviso.append(error);
		}
		return aviso.toString();
	}

	private String getImpedimentos(CtitFullImpediment[] listadoImpedimentos) {
		StringBuffer impedimento = new StringBuffer();
		for (int i = 0; i < listadoImpedimentos.length; i++) {
			if (i > 0)
				impedimento.append(Claves.BR);
			impedimento.append(listadoImpedimentos[i].getKey());
			impedimento.append(" - ");
			String error = utiles.quitarCaracteresExtranios(listadoImpedimentos[i].getMessage());
			if (error.length() > 80) {
				String resAux = "";
				for (int tam = 0; tam <= Math.floor(error.length() / 80); tam++) {
					if (tam != Math.floor(error.length() / 80)) {
						resAux += error.substring(80 * tam, 80 * tam + 80) + Claves.BR;
					} else {
						resAux += error.substring(80 * tam) + Claves.BR;
					}
				}
				error = resAux;
			}
			impedimento.append(error);
		}
		return impedimento.toString();
	}

	private String getMensajeError(CtitFullError[] listadoErrores) {
		StringBuffer mensajeError = new StringBuffer();
		for (int i = 0; i < listadoErrores.length; i++) {
			if (i > 0)
				mensajeError.append(Claves.BR);
			mensajeError.append(listadoErrores[i].getKey());
			mensajeError.append(" - ");
			String error = utiles.quitarCaracteresExtranios(listadoErrores[i].getMessage());
			if (error.length() > 80) {
				String resAux = "";
				for (int tam = 0; tam <= Math.floor(error.length() / 80); tam++) {
					if (tam != Math.floor(error.length() / 80)) {
						resAux += error.substring(80 * tam, 80 * tam + 80) + Claves.BR;
					} else {
						resAux += error.substring(80 * tam) + Claves.BR;
					}
				}
				error = resAux;
			}
			mensajeError.append(error);
		}
		return mensajeError.toString();
	}

	private void listarErroresCTIT(CtitFullError[] listadoErrores) {
		for (int i = 0; i < listadoErrores.length; i++) {
			log.error(" Error de webservice codigo: " + listadoErrores[i].getKey());
			log.error(" Error de webservice descripcion: " + listadoErrores[i].getMessage());
		}
	}

	private void guardarPTC(BigDecimal numExpediente, String ptc) {
		if (ptc != null && !ptc.isEmpty()) {
			try {
				byte[] ptcBytes = utiles.doBase64Decode(ptc, UTF_8);
				FicheroBean fichero = new FicheroBean();
				fichero.setTipoDocumento(ConstantesGestorFicheros.CTIT);
				fichero.setSubTipo(ConstantesGestorFicheros.PTC);
				fichero.setSobreescribir(true);
				fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
				fichero.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
				fichero.setNombreDocumento(numExpediente.toString());
				fichero.setFicheroByte(ptcBytes);
				gestorDocumentos.guardarByte(fichero);
			} catch (Throwable e) {
				log.error("Ha sucedido un error a la hora de guardar el PTC para el tramite: " + numExpediente + ", error: ", e, numExpediente.toString());
			}
		} else {
			log.error("La respuesta del tramite " + numExpediente + " no trae permiso temporal de circulacion");
		}
	}

	private boolean compruebaErroresRecuperables(CtitFullError[] listadoErrores) {
		boolean recuperable = true;
		if (listadoErrores != null && listadoErrores.length > 0) {
			for (CtitFullError error : listadoErrores) {
				recuperable = servicioMensajesProcesos.tratarMensaje(error.getKey(), error.getMessage());
				if (!recuperable) {
					break;
				}
			}
		}
		return recuperable;
	}

	private String crearXml(BigDecimal numExpediente, String xmlEnviar) {
		String peticionXML = null;
		try {
			Fecha fechaBusqueda = Utilidades.transformExpedienteFecha(numExpediente);
			FileResultBean ficheroAenviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.CTIT, ConstantesGestorFicheros.CTITENVIO, fechaBusqueda, xmlEnviar, null);
			if (ficheroAenviar != null && ficheroAenviar.getFile() != null) {
				XmlTransTelematicaFactory xmlTransTelematicaFactory = new XmlTransTelematicaFactory();
				peticionXML = xmlTransTelematicaFactory.xmlFileToString(ficheroAenviar.getFile());
			}
		} catch (Throwable e) {
			log.error("Error al recuperar el xml, error: ", e, numExpediente.toString());
		}
		return peticionXML;
	}

	@Override
	public ResultadoCtitBean actualizarTramiteProceso(BigDecimal numExpediente, EstadoTramiteTrafico estadoNuevo, BigDecimal idUsuario, String respuesta) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		try {
			TramiteTrafTranDto tramiteTrafTranDto = servicioTramiteTraficoTransmision.getTramiteTransmision(numExpediente, Boolean.TRUE);
			if (tramiteTrafTranDto != null) {
				Date fechaActual = utilesFecha.getFechaActualDesfaseBBDD();
				tramiteTrafTranDto.setRespuesta(respuesta);
				tramiteTrafTranDto.setFechaUltModif(new Fecha(fechaActual));
				servicioTramiteTraficoTransmision.actualizarTramiteTransmision(tramiteTrafTranDto);
				servicioTramiteTrafico.cambiarEstadoConEvolucion(numExpediente, EstadoTramiteTrafico.convertir(tramiteTrafTranDto.getEstado()), estadoNuevo, Boolean.TRUE, NOTIFICACION, idUsuario);
			} else {
				log.error("No existen datos en la BBDD para el tramite de transmision: " + numExpediente);
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("No existen datos en la BBDD para el tramite de transmision: " + numExpediente);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar los datos del tramite:" + numExpediente + ", error: ", e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de actualizar los datos del tramite:" + numExpediente + ", error: " + e.getMessage());
		}
		return resultado;
	}

	@Override
	public ResultadoCtitBean descontarCreditos(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal idContrato) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		try {
			ContratoDto contrato = servicioContrato.getContratoDto(idContrato);
			String descontarCreditosAM = gestorPropiedades.valorPropertie("descontar.creditos.Am");
			ResultBean resultBean = new ResultBean();
			if ("2631".equals(contrato.getColegiadoDto().getNumColegiado()) && "SI".equals(descontarCreditosAM)) {
				resultBean = servicioCredito.descontarCreditos(TipoTramiteTrafico.CTIT_AM.getValorEnum(), idContrato, new BigDecimal(1), new BigDecimal(1),
						ConceptoCreditoFacturado.TTAM, numExpediente.toString());
			} else {
				resultBean = servicioCredito.descontarCreditos(TipoTramiteTrafico.TransmisionElectronica.getValorEnum(), idContrato, new BigDecimal(1), new BigDecimal(1),
						ConceptoCreditoFacturado.TTT, numExpediente.toString());
			}
	
			if (resultBean.getError()) {
				log.error("ERROR DESCONTAR CREDITOS");
				log.error("CONTRATO: " + idContrato.toString());
				log.error("ID_USUARIO: " + idUsuario);
				String mensajes = "";
				for (String mensajeError : resultBean.getListaMensajes()) {
					log.error(mensajeError);
					mensajes += mensajeError;
				}
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError(mensajes);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de descontar los creditos para el tramite: " + numExpediente + ",error: ", e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de descontar los creditos para el tramite: " + numExpediente);
		}
		return resultado;
	}
}