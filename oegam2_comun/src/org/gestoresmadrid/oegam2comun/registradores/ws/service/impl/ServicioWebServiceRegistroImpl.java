package org.gestoresmadrid.oegam2comun.registradores.ws.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;

import javax.xml.rpc.ServiceException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.util.encoders.Base64;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.enumerados.TextoNotificacion;
import org.gestoresmadrid.core.registradores.constantes.ConstantesRegistradores;
import org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoSubsanacion;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.registradores.corpme.CORPMEAcuseEntrada;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioRegistroFueraSecuencia;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.utiles.NumeroEntrada;
import org.gestoresmadrid.oegam2comun.registradores.utiles.RespuestaRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.RegistroFueraSecuenciaDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.ws.service.ServicioWebServiceRegistro;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.oegamComun.colegiado.services.ServicioColegiado;
import org.gestoresmadrid.oegamComun.colegiado.view.dto.ColegiadoDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import net.consejogestores.sercon.core.model.integracion.dto.RegistroError;
import net.consejogestores.sercon.core.model.integracion.dto.RegistroRequest;
import net.consejogestores.sercon.core.model.integracion.dto.RegistroResponse;
import net.consejogestores.sercon.ws.model.integracion.RegistroService;
import net.consejogestores.sercon.ws.model.integracion.RegistroServiceServiceLocator;
import procesos.enumerados.RetornoProceso;
import trafico.utiles.UtilesWSMatw;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioWebServiceRegistroImpl implements ServicioWebServiceRegistro {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 8723750037774513875L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServiceRegistroImpl.class);

	@Autowired
	private ServicioTramiteRegistro servicioTramiteRegistro;

	@Autowired
	private ServicioRegistroFueraSecuencia servicioRegistroFueraSecuencia;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private ServicioColegiado servicioColegiado;

	@Autowired
	private ServicioNotificacion servicioNotificacion;

	@Autowired
	private ServicioCredito servicioCredito;

	@Autowired
	private ServicioCorreo servicioCorreo;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public RegistroResponse procesarEnvioTramiteSercon(ColaDto cola) throws OegamExcepcion, Exception {
		RegistroFueraSecuenciaDto acusePendiente = null;
		BigDecimal idTramite = null;
		byte[] bytesRespuesta = null;
		RegistroResponse response;

		// Si es una petición fuera de secuencia
		if ("RFS".equals(cola.getTipoTramite())) {
			acusePendiente = servicioRegistroFueraSecuencia.getRegistroFueraSecuencia(cola.getIdTramite().longValue());
			idTramite = acusePendiente.getIdTramiteRegistro();
		} else {
			idTramite = cola.getIdTramite();
		}
		log.info(Claves.CLAVE_LOG_PROCESO_WREG + "Peticion recuperada: " + cola.getIdEnvio() + " IdTramite: " + idTramite);
		TramiteRegistroDto tramiteRegistro = servicioTramiteRegistro.getTramite(idTramite);

		if (tramiteRegistro != null) {
			String nombre = cola.getXmlEnviar().split("\\.")[0];
			String tipoDocumento = null;
			if (TipoTramiteRegistro.MODELO_6.getValorEnum().equalsIgnoreCase(tramiteRegistro.getTipoTramite())) {
				tipoDocumento = ConstantesGestorFicheros.ESCRITURAS;
			} else {
				tipoDocumento = ConstantesGestorFicheros.REGISTRADORES;
			}

			FileResultBean ficheroAenviar = gestorDocumentos.buscarFicheroPorNombreTipo(tipoDocumento, ConstantesGestorFicheros.REGISTRADORES_XML, Utilidades.transformExpedienteFecha(idTramite), nombre,
					ConstantesGestorFicheros.EXTENSION_XML);

			ContratoDto contrato = servicioContrato.getContratoDto(tramiteRegistro.getIdContrato());
			ColegiadoDto colegiado = servicioColegiado.getColegiadoDto(tramiteRegistro.getNumColegiado());
			String numColegiadoGestor = tramiteRegistro.getNumColegiado();
			String cifPlataforma = contrato.getColegioDto().getCif();
			String codColegio = gestorPropiedades.valorPropertie("corpmeEdoc.id.colegio");
			String nifGestor = colegiado.getUsuario().getNif();
			String idTramiteCorpme = "";
			if (tramiteRegistro.getIdTramiteCorpme() != null && !tramiteRegistro.getIdTramiteCorpme().isEmpty()) {
				idTramiteCorpme = tramiteRegistro.getIdTramiteCorpme();
			} else {
				idTramiteCorpme = tramiteRegistro.getIdTramiteRegistro().toString();
			}

			new UtilesWSMatw().cargarAlmacenesTrafico();

			response = enviarTramiteSercon(idTramiteCorpme, cifPlataforma, codColegio, nifGestor, numColegiadoGestor, ficheroAenviar.getFile());

			if (null != response) {
				if (response.getCodigosError() != null && response.getCodigosError().length > 0) {
					return response;
				} else {
					bytesRespuesta = response.getXml();
				}
			}

			if (bytesRespuesta == null) {
				RegistroError registro = new RegistroError("000", "No se ha obtenido respuesta del registro.", null);
				RegistroError[] registroError = { registro };
				return new RegistroResponse(registroError, idTramite.toString(), null);

			}

			// Se ha obtenido un array de bytes válido como respuesta:
			File ficheroRespuesta = new File(gestorPropiedades.valorPropertie("registradores.ruta.mensaje.recibido.temporal") + idTramite + ".xml");
			FileOutputStream fos = new FileOutputStream(ficheroRespuesta);
			fos.write(Base64.decode(bytesRespuesta));
			fos.flush();
			fos.close();

			RespuestaRegistro respuestaRegistro = new RespuestaRegistro(ficheroRespuesta);

			FicheroBean ficheroBean = new FicheroBean();
			ficheroBean.setTipoDocumento(tipoDocumento);
			ficheroBean.setSubTipo(ConstantesGestorFicheros.REGISTRADORES_RECIBIDOXML);
			ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
			ficheroBean.setFichero(ficheroRespuesta);
			ficheroBean.setSobreescribir(false);
			ficheroBean.setFecha(Utilidades.transformExpedienteFecha(idTramite));
			ficheroBean.setNombreDocumento(idTramite.toString() + "_" + respuestaRegistro.getTipoMensaje() + "_ACK");
			gestorDocumentos.guardarFichero(ficheroBean);

			if (ficheroRespuesta.delete()) {
				log.info(Claves.CLAVE_LOG_PROCESO_WREG + "Borrado correctamente el fichero temporal.");
			} else {
				log.error(Claves.CLAVE_LOG_PROCESO_WREG + "Error borrando el fichero temporal");
			}
			if (null != respuestaRegistro && null != respuestaRegistro.getCodigoRetorno() && 0 != BigInteger.ZERO.compareTo(respuestaRegistro.getCodigoRetorno())  && StringUtils.isNotBlank(respuestaRegistro.getDescripcionRetorno())) {

				RegistroError registro = new RegistroError(respuestaRegistro.getCodigoRetorno().toString(), respuestaRegistro.getDescripcionRetorno(), null);
				RegistroError[] registroError = { registro };
				response = new RegistroResponse(registroError, idTramite.toString(), null);

				return response;
			}

			ResultBean resultado = new ResultBean();

			// si no es un mensaje fuera de secuencia o un acuse de estados finales
			if (acusePendiente == null 
					|| ConstantesRegistradores.TIPOMENSAJE_RESOLUCION_DEFECTOS_ACUSE.equalsIgnoreCase(respuestaRegistro.getTipoMensaje()) 
					|| ConstantesRegistradores.TIPOMENSAJE_RESOLUCION_PARCIAL_ACUSE.equalsIgnoreCase(respuestaRegistro.getTipoMensaje()) 
					|| ConstantesRegistradores.TIPOMENSAJE_RESOLUCION_TOTAL_ACUSE.equalsIgnoreCase(respuestaRegistro.getTipoMensaje())) {

				EstadoTramiteRegistro nuevoEstado = EstadoTramiteRegistro.estadoTrasAcuse(tramiteRegistro.getEstado().toString(), tramiteRegistro.getTipoTramite());

				if (respuestaRegistro.getTipoMensaje().equalsIgnoreCase(ConstantesRegistradores.TIPOMENSAJE_NUMEROENTRADA)) {
					response = tratarNE(tramiteRegistro, respuestaRegistro, cola);

					if (null != response && response.getCodigosError() != null && response.getCodigosError().length > 0) {
						return response;
					}

				} else {
					// Porque si ya ha llegado la factura
					if (EstadoTramiteRegistro.Inscrito.equals(nuevoEstado)) {
						FileResultBean fichero;
						if (tramiteRegistro.getTipoTramite().equals(TipoTramiteRegistro.MODELO_6.getValorEnum())){
							fichero = gestorDocumentos.buscarFicheroPorNumExpTipo(tipoDocumento, ConstantesGestorFicheros.REGISTRADORES_DOCUMENTACION, Utilidades.transformExpedienteFecha(
									idTramite), idTramite + "_" + "RITDR");
							if (fichero != null && fichero.getFiles()!= null && !fichero.getFiles().isEmpty() && fichero.getFiles().size()>2) {
								nuevoEstado = EstadoTramiteRegistro.Finalizado;
							}
						}else {
							fichero = gestorDocumentos.buscarFicheroPorNumExpTipo(tipoDocumento, ConstantesGestorFicheros.REGISTRADORES_DOCUMENTACION, Utilidades.transformExpedienteFecha(
									idTramite), idTramite + "_" + "FDR");
							if (fichero != null && fichero.getFiles()!= null && !fichero.getFiles().isEmpty()) {
								nuevoEstado = EstadoTramiteRegistro.Finalizado;
							}
						}
					}

					resultado = servicioTramiteRegistro.cambiarEstado(true, idTramite, tramiteRegistro.getEstado(), new BigDecimal(nuevoEstado.getValorEnum()), true, TextoNotificacion.Cambio_Estado
							.getNombreEnum(), null);
					if (resultado != null && resultado.getError()) {
						log.error(Claves.CLAVE_LOG_PROCESO_WREG + "Ha ocurrido el siguiente error actualizando a " + nuevoEstado.getNombreEnum() + " el tramite con identificador: " + idTramite);
					}
					
					if(null != acusePendiente) {
						resultado = servicioRegistroFueraSecuencia.guardarRegistroFueraSecuencia(acusePendiente, cola.getIdUsuario());
					}
				}

			} else {
				resultado = servicioRegistroFueraSecuencia.guardarRegistroFueraSecuencia(acusePendiente, cola.getIdUsuario());
			}

			if (resultado != null && resultado.getError()) {
				RegistroError registro = new RegistroError("000", "Error al guardar registro fuera de secuencia", null);
				RegistroError[] registroError = { registro };
				response = new RegistroResponse(registroError, idTramite.toString(), null);
			}

		} else {
			RegistroError registro = new RegistroError("000", "No se ha recuperado el trámite", null);
			RegistroError[] registroError = { registro };
			response = new RegistroResponse(registroError, idTramite.toString(), null);
		}

		return response;

	}

	private RegistroResponse tratarNE(TramiteRegistroDto tramiteRegistro, RespuestaRegistro respuestaRegistro, ColaDto cola) throws Exception, OegamExcepcion {
		RegistroResponse response = null;

		File contenidoNE = respuestaRegistro.getContenido();
		// Construye un objeto jaxb a partir del fichero xml del elemento <Contenido> del xml de respuesta:
		NumeroEntrada objetoNE = new NumeroEntrada(contenidoNE);
		// Recupera los datos del número de entrada recibido:
		CORPMEAcuseEntrada entrada = objetoNE.getEntrada();
		if (entrada.getNumEntradaMercantil() != null) {
			tramiteRegistro.setHoraEntradaReg(entrada.getHora());
			tramiteRegistro.setAnioReg(new BigDecimal(entrada.getNumEntradaMercantil().getAnyo()));
			tramiteRegistro.setLibroReg(new BigDecimal(entrada.getNumEntradaMercantil().getLibro()));
			tramiteRegistro.setNumReg(new BigDecimal(entrada.getNumEntradaMercantil().getNumero()));
			log.info(Claves.CLAVE_LOG_PROCESO_WREG + "Numero de entrada recibido: " + entrada.getNumEntradaMercantil().getNumero() + " " + entrada.getNumEntradaMercantil().getLibro() + " " + entrada.getNumEntradaMercantil().getAnyo());
		} else if (entrada.getNumEntradaPropiedad() != null) {
			tramiteRegistro.setHoraEntradaReg(entrada.getHora());
			tramiteRegistro.setAnioReg(new BigDecimal(entrada.getNumEntradaPropiedad().getAnyo()));
			tramiteRegistro.setNumReg(new BigDecimal(entrada.getNumEntradaPropiedad().getNumero()));
			log.info(Claves.CLAVE_LOG_PROCESO_WREG + "Numero de entrada recibido: " + entrada.getNumEntradaPropiedad().getNumero() + " " + entrada.getNumEntradaPropiedad().getAnyo());
		} else if (entrada.getNumEntradaBienesMuebles() != null) {
			tramiteRegistro.setHoraEntradaReg(entrada.getHora());
			tramiteRegistro.setNumReg(new BigDecimal(entrada.getNumEntradaBienesMuebles().getNumero()));
			log.info(Claves.CLAVE_LOG_PROCESO_WREG + "Numero de entrada recibido: " + entrada.getNumEntradaBienesMuebles().getNumero());
		} else {
			log.error(Claves.CLAVE_LOG_PROCESOS_ERROR + "El objeto jaxb CORPMEAcuseEntrada tiene null sus propiedades getNumEntradaPropiedad, getNumEntradaMercantil y getNumEntradaBienesMuebles");
		}

		tramiteRegistro.setLocalizadorReg(entrada.getLocalizador());

		tramiteRegistro.setFechaEnvio(utilesFecha.getFechaHoraActualLEG());
		if (tramiteRegistro.getNEnvios() != null) {
			int nEnvios = tramiteRegistro.getNEnvios().intValue();
			nEnvios++;
			tramiteRegistro.setNEnvios(new BigDecimal(nEnvios));
		} else {
			tramiteRegistro.setNEnvios(BigDecimal.ONE);
		}

		BigDecimal estadoAnterior = tramiteRegistro.getEstado();

		if (TipoSubsanacion.DESDE_SUSPENDIDA_CALIFICACION.getValorEnum().equals(tramiteRegistro.getSubsanacion())) {
			tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Pendiente_Calificacion_Subsanacion.getValorEnum()));
		} else {
			tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Entrada_Registro.getValorEnum()));
		}

		tramiteRegistro.setRespuesta("Se ha realizado correctamente el envío a Registro");

		ResultBean result = servicioTramiteRegistro.guardarTramiteRegistro(tramiteRegistro, estadoAnterior, cola.getIdUsuario());
		if (result != null && result.getError()) {
			StringBuffer respuestaResistrar = new StringBuffer("");
			if (result.getListaMensajes() != null) {
				for (String mensaje : result.getListaMensajes()) {
					respuestaResistrar.append(mensaje).append(".");
				}
			}

			RegistroError registro = new RegistroError("000", respuestaResistrar.toString(), null);
			RegistroError[] registroError = { registro };
			response = new RegistroResponse(registroError, tramiteRegistro.getIdTramiteRegistro().toString(), null);
			return response;

		} else {
			enviarNotificacion(tramiteRegistro, estadoAnterior, cola.getIdUsuario());
			log.info(Claves.CLAVE_LOG_PROCESO_WREG + "Estado actualizado a Entrada_Registro el tramite con identificador: " + cola.getIdTramite());
		}
		log.info(Claves.CLAVE_LOG_PROCESO_WREG + "El return al GestorColas ha sido: " + RetornoProceso.CORRECTO);

		// Es correcto. Descuenta el crédito si es un trámite padre, no un envío de factura:
		if (null == tramiteRegistro.getIdTramiteOrigen()) {
			descontarCredito(cola, tramiteRegistro.getIdContrato());
		}

		return response;
	}

	private void descontarCredito(ColaDto cola, BigDecimal idContrato) throws OegamExcepcion {
		ResultBean result = servicioCredito.descontarCreditos(cola.getTipoTramite(), idContrato, BigDecimal.ONE, cola.getIdUsuario(), ConceptoCreditoFacturado.WREG, cola.getTipoTramite());
		if (result != null && result.getError()) {
			StringBuffer respuestaResistrar = new StringBuffer("");
			if (result.getListaMensajes() != null) {
				for (String mensaje : result.getListaMensajes()) {
					respuestaResistrar.append(mensaje).append(".");
				}
			}
			log.error(Claves.CLAVE_LOG_PROCESO_WREG + "Ha ocurrido el siguiente error: " + respuestaResistrar + ". Descontando el credito para la gestion del tramite con identificador: " + cola
					.getIdTramite());
		} else {
			log.info(Claves.CLAVE_LOG_PROCESO_WREG + "Se ha descontado el credito de forma correcta.");
		}
	}

	private void enviarNotificacion(TramiteRegistroDto tramiteRegistro, BigDecimal estadoAnterior, BigDecimal idUsuario) {
		NotificacionDto dto = new NotificacionDto();
		dto.setDescripcion(TextoNotificacion.Cambio_Estado.getNombreEnum());
		dto.setEstadoAnt(estadoAnterior);
		dto.setEstadoNue(tramiteRegistro.getEstado());
		dto.setIdTramite(tramiteRegistro.getIdTramiteRegistro());
		dto.setIdUsuario(idUsuario.longValue());
		dto.setTipoTramite(tramiteRegistro.getTipoTramite());
		servicioNotificacion.crearNotificacion(dto);
	}

	// Método que envía mediante el web service el fichero construido:
	public RegistroResponse enviarTramiteSercon(String idTramite, String cifPlataforma, String codColegio, String nifGestor, String numColegiadoGestor, File fichero) throws OegamExcepcion,
	IOException, ServiceException {
		log.info(Claves.CLAVE_LOG_PROCESO_WREG + "Inicio de envio al WS de sercon.");
		RegistroServiceServiceLocator service = new RegistroServiceServiceLocator();

		URL url = new URL(gestorPropiedades.valorPropertie("sercon.webService.url"));

		RegistroService registroService = service.getRegistroService(url);

		RegistroRequest request = establecerEnvioSercon(idTramite, cifPlataforma, codColegio, nifGestor, numColegiadoGestor, fichero);

		RegistroResponse response = registroService.envioMensaje(request);

		log.info(Claves.CLAVE_LOG_PROCESO_WREG + "Fin correcto del envio al WS de sercon.");
		return response;
	}

	public static RegistroRequest establecerEnvioSercon(String idTramite, String cifPlataforma, String codColegio, String nifGestor, String numColegiadoGestor, File fichero) throws IOException {
		RegistroRequest request = new RegistroRequest();
		request.setXml(establecerEnvio(fichero));
		request.setCifPlataforma(cifPlataforma);
		request.setCodColegio(codColegio);
		request.setIdTramite(idTramite);
		request.setNifGestor(nifGestor);
		request.setNumColegiadoGestor(numColegiadoGestor);
		return request;
	}

	// Establece el contenido del envio al servicio web a partir de un fichero xml:
	public static byte[] establecerEnvio(File fichero) throws IOException {
		log.info(Claves.CLAVE_LOG_PROCESO_WREG + "Estableciendo el envio...");
		// Convierte el xml en un array de bytes:
		FileInputStream fis = new FileInputStream(fichero);
		byte[] envio = IOUtils.toByteArray(fis);
		log.info(Claves.CLAVE_LOG_PROCESO_WREG + "Envio establecido correctamente.");
		return envio;
	}

	// Envío de correo a administrador cuando falle el envío de justificante de pago (trámite escritura)
	@Override
	public void enviarCorreoJustificantePago(BigDecimal idTramite) {
		String subject = null;
		String idTramiteOrigen;
		try {
			TramiteRegistroDto tramiteRegistroDto = servicioTramiteRegistro.getTramite(idTramite);

			// Enviamos el correo sólo si existe idTramiteOrigen
			if (null != tramiteRegistroDto && null != tramiteRegistroDto.getIdTramiteOrigen()) {

				idTramiteOrigen = tramiteRegistroDto.getIdTramiteOrigen().toString();

				subject = "Error envío justificante de pago: " + idTramiteOrigen;
				String emailCopia = gestorPropiedades.valorPropertie("email.compia.registradoresWS");
				log.info("Envio correo a:" + emailCopia);

				String entorno = gestorPropiedades.valorPropertie("Entorno");
				if (!"PRODUCCION".equals(entorno)) {
					subject = entorno + ": " + subject;
				}

				StringBuffer texto = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">El trámite de registro con identificador <b>" + idTramiteOrigen + "</b>");
				texto.append(" no ha podido enviar correctamente su justificante de pago.");

				texto.append("<br><br></span>");

				ResultBean resultado;

				resultado = servicioCorreo.enviarCorreo(texto.toString(), null, null, subject, emailCopia, null, null, null);

				if (resultado.getError()) {
					log.error("No se ha enviado el correo de error de envío de justificante de pago. Error: " + resultado.getMensaje());
				}
			}

		} catch (OegamExcepcion | IOException e) {
			log.error("No se ha enviado el correo de error de envío de justificante de pago", e);
		}
	}

}
