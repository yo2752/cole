package org.gestoresmadrid.oegam2comun.sega.model.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;

import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.mensajes.procesos.model.service.ServicioMensajesProcesos;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.sega.model.service.ServicioWebServiceMatwSega;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoMatwBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import net.gestores.sega.matw.MATWServiceLocator;
import net.gestores.sega.matw.MATWWS;
import net.gestores.sega.matw.MATWWSBindingStub;
import net.gestores.sega.matw.MatwArgument;
import net.gestores.sega.matw.MatwError;
import net.gestores.sega.matw.MatwReturn;
import net.gestores.sega.matw.ws.SoapMatwSegaWSHandler;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioWebServiceMatwSegaImpl implements ServicioWebServiceMatwSega{

	private static final long serialVersionUID = -3066996793820857261L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServiceMatwSegaImpl.class);
	
	@Autowired
	GestorDocumentos gestorDocumentos;
	
	@Autowired
	ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;
	
	@Autowired
	ServicioUsuario servicioUsuario;
	
	@Autowired
	ServicioVehiculo servicioVehiculo;
	
	@Autowired
	ServicioMensajesProcesos servicioMensajesProcesos;
	
	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;
	
	@Autowired
	ServicioCredito servicioCredito;
	
	@Autowired
	Conversor conversor;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Override
	public ResultadoMatwBean tramitarPeticion(ColaBean solicitud) {
		ResultadoMatwBean resultado = new ResultadoMatwBean(Boolean.FALSE);
		try {
			String xml = recogerXml(solicitud.getXmlEnviar(), solicitud.getIdTramite());
			if(xml != null && !xml.isEmpty()){
				TramiteTrafMatrDto tramiteTrafMatrDto = servicioTramiteTraficoMatriculacion.getTramiteMatriculacion(solicitud.getIdTramite(), Boolean.FALSE, Boolean.TRUE);
				if(tramiteTrafMatrDto != null){
					resultado = llamadaWS(xml, tramiteTrafMatrDto, solicitud.getIdUsuario());
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensajeError("No existe datos para el expediente: " + solicitud.getIdTramite());
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("No existe el xml de envio para poder tramitar el tramite.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de realizar la tramitacion de MATW_SEGA para el tramite: " + solicitud.getIdTramite() + ", error: ",e);
			resultado.setExcepcion(new Exception(e.getMessage()));
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	private ResultadoMatwBean llamadaWS(String xml, TramiteTrafMatrDto tramiteTrafMatrDto, BigDecimal idUsuario){
		ResultadoMatwBean resultado = new ResultadoMatwBean(Boolean.FALSE);
		try {
			MatwArgument request = rellenarParamRequest(xml, tramiteTrafMatrDto);
			if(request != null){
				log.info(TEXTO_MATW_LOG + " ENTRA EN BLACK BOX: " + tramiteTrafMatrDto.getNumExpediente());
				MatwReturn respuesta = getStubMatwSega(tramiteTrafMatrDto.getNumExpediente()).sendMATW(request);
				log.info("------ " + TEXTO_MATW_LOG + " RECIBIDA RESPUESTA DEL WEBSERVICE-----");
				log.info(TEXTO_MATW_LOG + " idTramite: " + tramiteTrafMatrDto.getNumExpediente());
				log.info(TEXTO_MATW_LOG + " dossierNumber Respuesta: " + respuesta.getDossierNumber());
				resultado = gestionarRespuesta(respuesta,tramiteTrafMatrDto,idUsuario);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("Ha sucedido un error a la hora de completar la request de envio al WS de Matriculacion.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar la llamada al WS de MATWSEGA, error: ",e, tramiteTrafMatrDto.getNumExpediente().toString());
			resultado.setExcepcion(e);
		}
		return resultado;
	}
	
	private ResultadoMatwBean gestionarRespuesta(MatwReturn respuesta, TramiteTrafMatrDto tramiteTrafMatrDto, BigDecimal idUsuario) {
		ResultadoMatwBean resultadoWS = new ResultadoMatwBean(Boolean.FALSE);
		if(respuesta == null){
			resultadoWS.setError(Boolean.TRUE);
			resultadoWS.setExcepcion(new Exception("Ha sucedido un error en la respuesta del WS de MATW_SEGA y está se encuentra vacia"));
		} else if(respuesta.getErrorCodes() == null || respuesta.getErrorCodes().length == 0){
			resultadoWS.setError(Boolean.TRUE);
			resultadoWS.setMensajeError(getMensajeError(respuesta.getErrorCodes()));
			resultadoWS.setEsRecuperable(compruebaErroresRecuperables(respuesta.getErrorCodes()));
		} else {
			resultadoWS = guardarTramiteMatriculado(respuesta,tramiteTrafMatrDto,idUsuario); 
			if(!resultadoWS.getError()){
				guardarPermisoTemporalCirculacion(respuesta,tramiteTrafMatrDto.getNumExpediente());
				guardarFichaTecnica(respuesta,tramiteTrafMatrDto.getNumExpediente());
			}
		}
		return resultadoWS;
	}
	
	private Boolean compruebaErroresRecuperables(MatwError[] errorCodes) {
		Boolean recuperable = true;
		for (MatwError error : errorCodes) {
			recuperable = servicioMensajesProcesos.tratarMensaje(error.getKey(), error.getMessage());
			if (!recuperable) {
				break;
			}
		}
		return recuperable;
	}

	private String getMensajeError(MatwError[] listadoErrores) {
		StringBuffer mensajeError = new StringBuffer();
		for (MatwError error : listadoErrores) {
			mensajeError.append(error.getKey());
			mensajeError.append(" - ");
			mensajeError.append(error.getMessage());
			if (listadoErrores.length > 1) {
				mensajeError.append(" ");
			}
			log.error(ConstantesProcesos.ERROR_DE_WEBSERVICE + ConstantesProcesos.CODIGO + error.getKey());
			log.error(ConstantesProcesos.ERROR_DE_WEBSERVICE + ConstantesProcesos.DESCRIPCION + error.getMessage());
		}
		return mensajeError.toString();
	}

	private void guardarFichaTecnica(MatwReturn respuesta, BigDecimal numExpediente) {
		if (respuesta.getItvCard() != null && !respuesta.getItvCard().isEmpty()) {
			try {
				byte[] ptcBytes = utiles.doBase64Decode(respuesta.getItvCard(), UTF_8);
				FicheroBean fichero = new FicheroBean();
				fichero.setTipoDocumento(ConstantesGestorFicheros.MATE);
				fichero.setSubTipo(ConstantesGestorFicheros.FICHATECNICA);
				fichero.setSobreescribir(true);
				fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
				fichero.setFecha(Utilidades.transformExpedienteFecha(numExpediente.toString()));
				fichero.setNombreDocumento(numExpediente + ConstantesProcesos.FICHA_TECNICA);
				fichero.setFicheroByte(ptcBytes);
				gestorDocumentos.guardarByte(fichero);
			} catch (Throwable e) {
				log.error("Ha sucedido un error a la hora de guardar la ficha Técnica del tramite: " + numExpediente + ",error: ",e, numExpediente.toString());
			}
		} else {
			log.error("La respuesta del tramite " + numExpediente + " no trae ficha tecnica");
		}
	}

	private void guardarPermisoTemporalCirculacion(MatwReturn respuesta, BigDecimal numExpediente) {
		if(respuesta.getLicense() != null && !respuesta.getLicense().isEmpty()){
			try {
				byte[] ptcBytes = utiles.doBase64Decode(respuesta.getLicense(), UTF_8);
				FicheroBean fichero = new FicheroBean();
				fichero.setTipoDocumento(ConstantesGestorFicheros.MATE);
				fichero.setSubTipo(ConstantesGestorFicheros.PTC);
				fichero.setSobreescribir(true);
				fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
				fichero.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
				fichero.setNombreDocumento(numExpediente.toString());
				fichero.setFicheroByte(ptcBytes);
				gestorDocumentos.guardarByte(fichero);
			} catch (Throwable e) {
				log.error("Ha sucedido un error a la hora de guardar el PTC para el tramite: " + numExpediente + ", error: ",e, numExpediente.toString());
			}
		} else {
			log.error("La respuesta del tramite " + numExpediente + " no trae permiso temporal de circulacion");
		}
	}

	private ResultadoMatwBean guardarTramiteMatriculado(MatwReturn respuesta, TramiteTrafMatrDto tramiteTrafMatrDto, BigDecimal idUsuario) {
		ResultadoMatwBean resultadoMatw = new ResultadoMatwBean(Boolean.FALSE);
		try {
			tramiteTrafMatrDto.getVehiculoDto().setMatricula(respuesta.getPlateNumber());
			Fecha fechaMatri = utilesFecha.getFechaHoraActual();
			tramiteTrafMatrDto.getVehiculoDto().setFechaMatriculacion(fechaMatri);
			tramiteTrafMatrDto.getVehiculoDto().setFechaPrimMatri(fechaMatri);
			tramiteTrafMatrDto.getVehiculoDto().setLugarAdquisicion("1");
			UsuarioDto usuario = servicioUsuario.getUsuarioDto(idUsuario);
			ResultBean resultBean = servicioVehiculo.guardarVehiculoConPrever(tramiteTrafMatrDto.getVehiculoDto(), tramiteTrafMatrDto.getNumExpediente(), ServicioVehiculo.MATRICULAR, usuario, tramiteTrafMatrDto.getFechaPresentacion()
					.getDate(), ServicioVehiculo.CONVERSION_VEHICULO_MATRICULACION, tramiteTrafMatrDto.getVehiculoPrever(), Boolean.FALSE);
			if(resultBean.getError()){
				resultadoMatw.setError(Boolean.TRUE);
				resultadoMatw.setMensajeError(resultBean.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar los datos de la matriculacion del vehiculo para el tramite: " + tramiteTrafMatrDto.getNumExpediente() + ", error: ",e, tramiteTrafMatrDto.getNumExpediente().toString());
			resultadoMatw.setError(Boolean.TRUE);
			resultadoMatw.setMensajeError("Ha sucedido un error a la hora de guardar los datos de la matriculacion del vehiculo.");
		}
		return resultadoMatw;
	}

	private MatwArgument rellenarParamRequest(String xml, TramiteTrafMatrDto tramiteTrafMatrDto) throws UnsupportedEncodingException {
		MatwArgument request = conversor.transform(tramiteTrafMatrDto, MatwArgument.class);
		Boolean hasForm05 = Boolean.FALSE;
		if (tramiteTrafMatrDto.getIdReduccion05() != null && "SI".equals(tramiteTrafMatrDto.getIdReduccion05())) {
			hasForm05 = Boolean.TRUE;
		}
		request.setHasForm05(hasForm05);
		Boolean hasForm06 = Boolean.FALSE;
		if (tramiteTrafMatrDto.getIdNoSujeccion06() != null && "SI".equals(tramiteTrafMatrDto.getIdNoSujeccion06())) {
			hasForm06 = Boolean.TRUE;
		}
		String form06ExcemptionType = null;
		if (hasForm06) {
			if (tramiteTrafMatrDto.getIdNoSujeccion06() == null || tramiteTrafMatrDto.getIdNoSujeccion06().isEmpty() 
					|| tramiteTrafMatrDto.getIdNoSujeccion06().equalsIgnoreCase("NO HAY")) {
				form06ExcemptionType = "NOSUBJECT";
			} else {
				form06ExcemptionType = "EXCEMPTION";
			}
		}
		request.setHasForm06(hasForm06);
		request.setForm06ExcemptionType(form06ExcemptionType);
		request.setForm06ExcemptionValue(tramiteTrafMatrDto.getIdNoSujeccion06());
		Boolean hasForm576 = Boolean.TRUE;
		if (tramiteTrafMatrDto.getExento576() != null) {
			hasForm576 = tramiteTrafMatrDto.getExento576();
		}
		request.setHasForm576(hasForm576);
		Boolean itvCardNew = Boolean.FALSE;
		if (tramiteTrafMatrDto.getVehiculoDto().getTipoVehiculo() != null && (tramiteTrafMatrDto.getVehiculoDto().getTipoVehiculo().substring(0, 1) == "8")) {
			if (tramiteTrafMatrDto.getVehiculoDto().getFichaTecnicaRD750() != null && tramiteTrafMatrDto.getVehiculoDto().getFichaTecnicaRD750()) {
				itvCardNew = Boolean.TRUE;
			}
		}	
		request.setItvCardNew(itvCardNew);
		request.setXmlB64(utiles.doBase64Encode(xml.getBytes(UTF_8)));
		return request;
	}

	private MATWWS getStubMatwSega(BigDecimal numExpediente) throws ServiceException, MalformedURLException{
		URL miURL = new URL(gestorPropiedades.valorPropertie(URL_WS));
		String timeOut = gestorPropiedades.valorPropertie(TIMEOUT);
		
		MATWWSBindingStub stub = null;
		MATWServiceLocator matwServiceLocator = new MATWServiceLocator(); 
		javax.xml.namespace.QName portName = new javax.xml.namespace.QName(miURL.toString(),matwServiceLocator.getMATWServiceWSDDServiceName());
		
		@SuppressWarnings("unchecked")
		List<HandlerInfo> list = matwServiceLocator.getHandlerRegistry().getHandlerChain(portName);
		list.add(getFilesHandler(numExpediente));
		stub = (MATWWSBindingStub) matwServiceLocator.getMATWService(miURL);
		stub.setTimeout(Integer.parseInt(timeOut));
		return stub;
	}
	
	private HandlerInfo getFilesHandler(BigDecimal numExpediente) {
		// Configuración de la compra que desencadena la peticion
		Map<String, Object> filesConfig = new HashMap<String, Object>();
		filesConfig.put(SoapMatwSegaWSHandler.PROPERTY_KEY_ID, numExpediente);

		// Handler de logs
		HandlerInfo filesHandlerInfo = new HandlerInfo();
		filesHandlerInfo.setHandlerClass(SoapMatwSegaWSHandler.class);
		filesHandlerInfo.setHandlerConfig(filesConfig);

		return filesHandlerInfo;
	}

	private String recogerXml(String xmlEnviar, BigDecimal numExpediente) {
		FileResultBean ficheroAenviar = null;
		StringBuffer xml = null;
		try {
			Fecha fecha = Utilidades.transformExpedienteFecha(numExpediente);
			ficheroAenviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, 
					ConstantesGestorFicheros.ENVIO, fecha, xmlEnviar, ConstantesGestorFicheros.EXTENSION_XML);
			if(ficheroAenviar != null && ficheroAenviar.getFile() != null){
				xml  = new StringBuffer("<![CDATA[");
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(ficheroAenviar.getFile()), "UTF-8"));
				int value = 0;
				// reads to the end of the stream
				while ((value = br.read()) != -1) {
					// converts int to character
					xml.append((char) value);
				}
				br.close();
				xml.append("]]>");			
			}
		} catch (Throwable e) {
			log.error("Error al recuperar el xml, error: ", e, numExpediente.toString());
		}
		return xml.toString();
	}
	
	@Override
	public ResultadoMatwBean cambiarEstadoTramite(BigDecimal numExpediente,  EstadoTramiteTrafico estadoNuevo, BigDecimal idUsuario) {
		ResultadoMatwBean resultado = new ResultadoMatwBean(Boolean.FALSE);
		try {
			TramiteTrafMatrDto tramiteTrafMatrDto = servicioTramiteTraficoMatriculacion.getTramiteMatriculacion(numExpediente,Boolean.FALSE,Boolean.TRUE);
			if(tramiteTrafMatrDto != null){
				ResultBean resulBean = servicioTramiteTrafico.cambiarEstadoConEvolucion(numExpediente, EstadoTramiteTrafico.convertir(tramiteTrafMatrDto.getEstado()), estadoNuevo, Boolean.TRUE,
						NOTIFICACION, idUsuario);
				if(resulBean.getError()){
					resultado.setError(Boolean.TRUE);
					resultado.setMensajeError(resulBean.getMensaje());
				}
			} else {
				log.error("No se puede cambiar el estado del tramite: " + numExpediente + ", porque no existe en la BBDD.");
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("No se puede cambiar el estado del tramite: " + numExpediente + ", porque no existe en la BBDD.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado del tramite: " + numExpediente + ", error: ",e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de cambiar el estado del tramite: " + numExpediente);
		}
		return resultado;
	}
	
	@Override
	public void actualizarRespuestaMatriculacion(BigDecimal numExpediente, String respuesta) {
		try {
			TramiteTrafMatrDto tramiteTrafMatrDto = servicioTramiteTraficoMatriculacion.getTramiteMatriculacion(numExpediente,Boolean.FALSE,Boolean.TRUE);
			if(tramiteTrafMatrDto != null){
				servicioTramiteTraficoMatriculacion.actualizarRespuestaMatriculacion(numExpediente, respuesta);
			} else {
				log.error("No se puede actualizar la respuesta del tramite: " + numExpediente + ", porque no existe en la BBDD.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de actualizar la respuesta del tramite: " + numExpediente + ", error: ",e, numExpediente.toString());
		}
	}
	
	@Override
	public ResultadoMatwBean descontarCreditos(BigDecimal numExpediente, BigDecimal idContrato, BigDecimal idUsuario){
		ResultadoMatwBean resultado = new ResultadoMatwBean(Boolean.FALSE);
		try {
			ResultBean resultBean = servicioCredito.descontarCreditos(TipoTramiteTrafico.Matriculacion.getValorEnum(), idContrato, 
					new BigDecimal(1), new BigDecimal(1), ConceptoCreditoFacturado.TMT, numExpediente.toString());
			if(resultBean.getError()){
				log.error("ERROR DESCONTAR CREDITOS");
				log.error("CONTRATO: " + idContrato.toString());
				log.error("ID_USUARIO: " + idUsuario);
				String mensaje = "";
				for(String mensajeError : resultBean.getListaMensajes()){
					log.error(mensajeError);
					mensaje += mensajeError;
				}
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError(mensaje);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de descontar los creditos para el tramite: " + numExpediente + ",error: ",e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de descontar los creditos para el tramite: " + numExpediente);
		}
		return resultado;
	}
}
