package org.gestoresmadrid.oegam2comun.procesos.model.service.impl;

import java.io.IOException;
import java.math.BigDecimal;

import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioDistintivoDgt;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.mensajes.procesos.model.service.ServicioMensajesProcesos;
import org.gestoresmadrid.oegam2comun.procesos.model.service.ServicioWebServiceMatw;
import org.gestoresmadrid.oegam2comun.trafico.dgt.DGTMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoMatwBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.DatosCardMatwDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import matw.beans.RespuestaMatw;
import matw.beans.RespuestaMatwListadoErroresError;
import trafico.utiles.XMLMatwFactory;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioWebServiceMatwImpl implements ServicioWebServiceMatw {

	private static final long serialVersionUID = -345075721080488020L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServiceMatwImpl.class);

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;

	@Autowired
	ServicioMensajesProcesos servicioMensajesProcesos;

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioCredito servicioCredito;

	@Autowired
	DGTMatriculacion dgtMatriculacion;

	@Autowired
	ServicioDistintivoDgt servicioDistintivo;

	@Autowired
	Conversor conversor;

	@Autowired
	Utiles utiles;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Override
	public ResultadoMatwBean tramitarPeticion(ColaDto solicitud) {
		ResultadoMatwBean resultado = new ResultadoMatwBean(Boolean.FALSE);
		try {
			String nombre = solicitud.getXmlEnviar().split("\\.")[0];
			String xml = crearXml(solicitud.getIdTramite(), nombre);
			if (xml != null && !xml.isEmpty()) {
				resultado = llamadaWS(xml, solicitud.getIdTramite(), solicitud.getIdUsuario());
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("No existe el xml de envio para poder tramitar el tramite o no valida bien.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de realizar la tramitacion de MATW para el tramite: " + solicitud.getIdTramite() + ", error: ", e);
			resultado.setExcepcion(new Exception(e.getMessage()));
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	private ResultadoMatwBean llamadaWS(String xml, BigDecimal numExpediente, BigDecimal idUsuario) throws OegamExcepcion {
		ResultadoMatwBean resultado = new ResultadoMatwBean(Boolean.FALSE);
		try {
			DatosCardMatwDto datosCardMatwDto = servicioTramiteTraficoMatriculacion.datosCardMatw(numExpediente);
			if (datosCardMatwDto != null) {
				log.info("Proceso " + ConstantesProcesos.PROCESO_MATW + " -- Procesando petición de " + numExpediente);
				RespuestaMatw respuestaMatw = dgtMatriculacion.presentarDGTMatw(xml, datosCardMatwDto, numExpediente);
				log.info("Proceso " + ConstantesProcesos.PROCESO_MATW + " -- Peticion Procesada de " + numExpediente);

				resultado = gestionarRespuesta(respuestaMatw, numExpediente, idUsuario);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("Ha sucedido un error a la hora de completar la request de envio al WS de Matriculacion.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar la llamada al WS de MATW, error: ", e, numExpediente.toString());
			resultado.setExcepcion(e);
		}
		return resultado;
	}

	private ResultadoMatwBean gestionarRespuesta(RespuestaMatw respuesta, BigDecimal numExpediente, BigDecimal idUsuario) {
		ResultadoMatwBean resultadoWS = new ResultadoMatwBean(Boolean.FALSE);
		if (respuesta == null) {
			resultadoWS.setError(Boolean.TRUE);
			resultadoWS.setExcepcion(new Exception("Ha sucedido un error en la respuesta del WS de MATW y está se encuentra vacia"));
		} else if (respuesta.getListadoErrores() == null || respuesta.getListadoErrores().length == 0) {
			resultadoWS.setError(Boolean.TRUE);
			resultadoWS.setMensajeError(getMensajeError(respuesta.getListadoErrores()));
			resultadoWS.setEsRecuperable(compruebaErroresRecuperables(respuesta.getListadoErrores()));
		} else {
			resultadoWS = guardarMatriculacion(numExpediente, respuesta, idUsuario);
		}
		return resultadoWS;
	}

	private ResultadoMatwBean guardarMatriculacion(BigDecimal numExpediente, RespuestaMatw respuestaMatw, BigDecimal idUsuario) {
		ResultadoMatwBean resultadoWS = new ResultadoMatwBean(Boolean.FALSE);
		try {
			TramiteTrafMatrDto tramiteDto = servicioTramiteTraficoMatriculacion.getTramiteMatriculacion(numExpediente, false, true);
			if (tramiteDto != null) {
				ResultBean result = servicioTramiteTraficoMatriculacion.matriculado(tramiteDto, respuestaMatw.getInfoMatricula().getMatricula(), idUsuario, false);
				if (result != null && !result.getError()) {
					guardarPermisoTemporalCirculacion(numExpediente, respuestaMatw);
					guardarFichaTecnica(numExpediente, respuestaMatw);
					servicioTramiteTrafico.cambiarEstadoConEvolucion(numExpediente, EstadoTramiteTrafico.convertir(tramiteDto.getEstado()), EstadoTramiteTrafico.Finalizado_Telematicamente, true,
							NOTIFICACION, idUsuario);
					solicitarDistintivo(numExpediente, idUsuario);
					log.info("Proceso " + ConstantesProcesos.PROCESO_MATW + " -- Se va a descontar para: " + numExpediente);
					descontarCreditos(numExpediente, tramiteDto.getIdContrato(), idUsuario,tramiteDto.getNumColegiado());
				}
			} else {
				resultadoWS.setError(Boolean.TRUE);
				resultadoWS.setMensajeError("No existe datos para el expediente: " + numExpediente);
			}
			return resultadoWS;
		} catch (Throwable e) {
			log.error("Error al guardar la matricula del vehiculo", e);
			resultadoWS.setExcepcion(new Exception(e.getMessage()));
			resultadoWS.setError(Boolean.TRUE);
		}
		return null;
	}

	private boolean compruebaErroresRecuperables(RespuestaMatwListadoErroresError[] listadoErrores) {
		boolean recuperable = true;
		for (RespuestaMatwListadoErroresError error : listadoErrores) {
			recuperable = servicioMensajesProcesos.tratarMensaje(error.getCodigo(), error.getDescripcion());
			if (!recuperable) {
				break;
			}
		}
		return recuperable;
	}

	private String getMensajeError(RespuestaMatwListadoErroresError[] listadoErrores) {
		StringBuffer mensajeError = new StringBuffer();
		for (RespuestaMatwListadoErroresError error : listadoErrores) {
			mensajeError.append(error.getCodigo());
			mensajeError.append(" - ");
			mensajeError.append(error.getDescripcion());
			if (listadoErrores.length > 1) {
				mensajeError.append(" - ");
			}
			log.error(ConstantesProcesos.ERROR_DE_WEBSERVICE + ConstantesProcesos.CODIGO + error.getCodigo());
			log.error(ConstantesProcesos.ERROR_DE_WEBSERVICE + ConstantesProcesos.DESCRIPCION + error.getDescripcion());
		}
		return mensajeError.toString();
	}

	private String crearXml(BigDecimal numExpediente, String xmlEnviar) {
		String peticionXML = null;
		try {
			Fecha fechaBusqueda = Utilidades.transformExpedienteFecha(numExpediente);
			FileResultBean ficheroAenviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.ENVIO, fechaBusqueda, xmlEnviar,
					ConstantesGestorFicheros.EXTENSION_XML);
			if (ficheroAenviar != null && ficheroAenviar.getFile() != null) {
				XMLMatwFactory xMLMatwFactory = new XMLMatwFactory();
				String valido = xMLMatwFactory.validarXMLMATW(ficheroAenviar.getFile());
				if (valido != null && valido.equals(CORRECTO)) {
					peticionXML = xMLMatwFactory.xmlFileToString(ficheroAenviar.getFile());
				}
			}
		} catch (Throwable e) {
			log.error("Error al crear el xml, error: ", e, numExpediente.toString());
		}
		return peticionXML;
	}

	private void solicitarDistintivo(BigDecimal numExpediente, BigDecimal idUsuario) {
		try {
			ResultadoDistintivoDgtBean resultado = servicioDistintivo.solicitarDistintivo(numExpediente, idUsuario);
			if (resultado.isError()) {
				log.error("Ha sucedido un error a la hora de solicitar el distintivo mediambiental, error: ", resultado.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar el distintivo mediambiental, error: ", e);
		}
	}

	private boolean descontarCreditos(BigDecimal idTramite, BigDecimal idContrato, BigDecimal idUsuario, String numColegiado) {
		ResultBean res = new ResultBean();
		String descontarCreditosAM = gestorPropiedades.valorPropertie("descontar.creditos.Am");
		if ("2631".equals(numColegiado) && "SI".equals(descontarCreditosAM)) {
			res = servicioCredito.descontarCreditosAM(TipoTramiteTrafico.Matriculacion_AM.getValorEnum(), idContrato, new BigDecimal(1), idUsuario, ConceptoCreditoFacturado.TMAM,
					idTramite.toString());
		} else {
			res = servicioCredito.descontarCreditos(TipoTramiteTrafico.Matriculacion.getValorEnum(), idContrato,
					new BigDecimal(1), new BigDecimal(1), ConceptoCreditoFacturado.TMT, idTramite.toString());
		}
		if (res.getError()) {
			log.error("ERROR DESCONTAR CREDITOS");
			log.error("CONTRATO: " + idContrato.toString());
			log.error("ID_USUARIO: " + idUsuario);
		}
		return !res.getError();
	}

	private void guardarFichaTecnica(BigDecimal idTramite, RespuestaMatw respuestaMatw) throws Exception, IOException {
		String fichaTecnica = respuestaMatw.getInfoMatricula().getFicha_tecnica_PDF();
		if (null != fichaTecnica && !fichaTecnica.isEmpty()) {
			try {
				byte[] ptcBytes = utiles.doBase64Decode(fichaTecnica, UTF_8);
				FicheroBean fichero = new FicheroBean();

				fichero.setTipoDocumento(ConstantesGestorFicheros.MATE);
				fichero.setSubTipo(ConstantesGestorFicheros.FICHATECNICA);
				fichero.setSobreescribir(true);
				fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
				String nombreFichero = idTramite + ConstantesProcesos.FICHA_TECNICA;
				if (idTramite != null) {
					fichero.setFecha(Utilidades.transformExpedienteFecha(idTramite.toString()));
					fichero.setNombreDocumento(nombreFichero);
				} else {
					throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_NOMBRE_DOCUMENTO);
				}
				fichero.setFicheroByte(ptcBytes);
				gestorDocumentos.guardarByte(fichero);
			} catch (OegamExcepcion e) {
				log.error(e);
			}
		} else {
			log.error("La respuesta del tramite " + idTramite + " no trae ficha tecnica");
		}
	}

	private void guardarPermisoTemporalCirculacion(BigDecimal idTramite, RespuestaMatw respuestaMatw) throws Exception {
		String permisoTemporalCirculacion = respuestaMatw.getInfoMatricula().getPC_Temporal_PDF();
		if (permisoTemporalCirculacion != null && !permisoTemporalCirculacion.isEmpty()) {
			try {
				byte[] ptcBytes = utiles.doBase64Decode(permisoTemporalCirculacion, UTF_8);

				FicheroBean fichero = new FicheroBean();

				fichero.setTipoDocumento(ConstantesGestorFicheros.MATE);
				fichero.setSubTipo(ConstantesGestorFicheros.PTC);
				fichero.setSobreescribir(true);
				fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);

				if (idTramite != null) {
					fichero.setFecha(Utilidades.transformExpedienteFecha(idTramite));
					fichero.setNombreDocumento(idTramite.toString());
				} else {
					throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_NOMBRE_DOCUMENTO);
				}
				fichero.setFicheroByte(ptcBytes);
				gestorDocumentos.guardarByte(fichero);
			} catch (OegamExcepcion e) {
				log.error(e);
			}
		} else {
			log.error("La respuesta del tramite " + idTramite + " no trae permiso temporal de circulacion");
		}
	}

	@Override
	public void actualizarRespuestaMatriculacion(BigDecimal numExpediente, BigDecimal idUsuario, String respuesta) {
		try {
			if (respuesta != null && !respuesta.isEmpty()) {
				servicioTramiteTraficoMatriculacion.actualizarRespuestaMatriculacion(numExpediente, respuesta);
			}
			servicioTramiteTrafico.cambiarEstadoConEvolucion(numExpediente, EstadoTramiteTrafico.Pendiente_Respuesta_Jefatura, EstadoTramiteTrafico.Finalizado_Con_Error, true, NOTIFICACION,
					idUsuario);
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de actualizar la respuesta del tramite: " + numExpediente + ", error: ", e, numExpediente.toString());
		}
	}
}