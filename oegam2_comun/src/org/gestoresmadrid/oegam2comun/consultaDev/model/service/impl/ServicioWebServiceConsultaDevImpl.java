package org.gestoresmadrid.oegam2comun.consultaDev.model.service.impl;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;

import org.gestoresmadrid.core.consultaDev.model.enumerados.ConstantesConsultaDev;
import org.gestoresmadrid.core.consultaDev.model.enumerados.EstadoConsultaDev;
import org.gestoresmadrid.core.consultaDev.model.enumerados.ResultadoErrorConsultaDev;
import org.gestoresmadrid.core.consultaDev.model.vo.ConsultaDevVO;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.consultaDev.model.bean.ResultadoWSConsultaDev;
import org.gestoresmadrid.oegam2comun.consultaDev.model.service.ServicioDev;
import org.gestoresmadrid.oegam2comun.consultaDev.model.service.ServicioWebServiceConsultaDev;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.evolucionConsultaDev.model.service.ServicioEvolucionConsultaDev;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.dgt.www.nostra.esquemas.consultaDEV.ConsultaDEVPortType;
import es.dgt.www.nostra.esquemas.consultaDEV.ConsultaDEVServiceLocator;
import es.dgt.www.nostra.esquemas.consultaDEV.ConsultaDEVSoapBindingStub;
import es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.DatosEspecificos;
import es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.Usuario;
import es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.Usuarios;
import es.dgt.www.nostra.esquemas.consultaDEV.peticion.Peticion;
import es.dgt.www.nostra.esquemas.consultaDEV.peticion.SolicitudTransmision;
import es.dgt.www.nostra.esquemas.consultaDEV.peticion.Solicitudes;
import es.dgt.www.nostra.esquemas.consultaDEV.respuesta.Respuesta;
import es.dgt.www.nostra.esquemas.consultaDEV.ws.ConsultaDevSecurityClientHandler;
import es.dgt.www.nostra.esquemas.consultaDEV.ws.SoapConsultaDevWSHandler;
import es.dgt.www.scsp.esquemas.datos_comun.Atributos;
import es.dgt.www.scsp.esquemas.datos_comun.Consentimiento;
import es.dgt.www.scsp.esquemas.datos_comun.DatosGenericos;
import es.dgt.www.scsp.esquemas.datos_comun.Emisor;
import es.dgt.www.scsp.esquemas.datos_comun.Solicitante;
import es.dgt.www.scsp.esquemas.datos_comun.TipoDocumentacion;
import es.dgt.www.scsp.esquemas.datos_comun.Titular;
import es.dgt.www.scsp.esquemas.datos_comun.Transmision;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioWebServiceConsultaDevImpl implements ServicioWebServiceConsultaDev{

	private static final long serialVersionUID = -5710969742526532884L;
	
	private ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServiceConsultaDevImpl.class);
	
	@Autowired
	ServicioDev servicioDev;
	
	@Autowired
	ServicioContrato servicioContrato;
	
	@Autowired
	ServicioCredito servicioCredito;
	
	@Autowired
	ServicioEvolucionConsultaDev servicioEvolucionConsultaDev;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public ResultadoWSConsultaDev generarConsultaDev(ColaDto colaDto) {
		ResultadoWSConsultaDev resultado = new ResultadoWSConsultaDev();
		try {
			if(colaDto != null && colaDto.getIdTramite() != null){
				ConsultaDevVO consultaDevVO = servicioDev.getConsultaDevVO(colaDto.getIdTramite().longValue(),true); 
				if(consultaDevVO != null && consultaDevVO.getContrato() != null && consultaDevVO.getContrato().getColegiado() != null){
					resultado = llamadaWS(consultaDevVO,colaDto.getIdContrato());
				}else{
					resultado.setError(true);
					resultado.addMensajeALista("No existen datos de la consulta dev para poder generar la petición.");
				}
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("La cola o el id de la consulta estan vacios.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar la consulta dev, error: ",e);
			resultado.setExcepcion(e);
		}
		return resultado;
	}

	private ResultadoWSConsultaDev llamadaWS(ConsultaDevVO consultaDevVO, BigDecimal idContrato) {
		ResultadoWSConsultaDev resultado = new ResultadoWSConsultaDev();
		log.info("Entra en la llamadaWS de Consulta DEV");
		try {
			Peticion peticion = rellenarPeticion(consultaDevVO,idContrato);
			//Llamada WS
			Respuesta respuesta = getConsultaDevStub(consultaDevVO.getCif(),consultaDevVO.getContrato().getColegiado().getNumColegiado(),consultaDevVO.getContrato().getColegiado().getAlias()).consultaDEV(peticion);
			log.info("Recibida respuesta del WebService de Consultas DEV");
			resultado = tratarRespuestaWS(respuesta,consultaDevVO);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar la peticion al WS de consulta DEV, error: ",e);
			resultado.setExcepcion(e);
		}
		return resultado;
	}
	
	
	public ResultadoWSConsultaDev tratarRespuestaWS(Respuesta respuesta, ConsultaDevVO consultaDevVO) {
		ResultadoWSConsultaDev resultado = new ResultadoWSConsultaDev();
		resultado.setCifPeticion(consultaDevVO.getCif());
		if(ResultadoErrorConsultaDev.OK_WS.getValorEnum().equals(respuesta.getAtributos().getEstado().getCodigoEstado())){
			ResultBean resultBean = servicioDev.guardarDatosRespuesta(consultaDevVO,respuesta);
			if(resultBean.getError()){
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError(resultBean.getMensaje());
				consultaDevVO.setRespuesta(resultBean.getMensaje());
				consultaDevVO.setCodRespuesta(null);
				servicioDev.actualizarConsultaDevVO(consultaDevVO);
			}
		}else{
			resultado.setError(true);
			resultado.setMensajeError(respuesta.getAtributos().getEstado().getLiteralError());
			consultaDevVO.setRespuesta(respuesta.getAtributos().getEstado().getLiteralError());
			consultaDevVO.setCodRespuesta(respuesta.getAtributos().getEstado().getCodigoEstado());
			servicioDev.actualizarConsultaDevVO(consultaDevVO);
		}
		
		return resultado;
	}

	private ConsultaDEVSoapBindingStub getConsultaDevStub(String cif,String numColegiado, String alias) throws ServiceException, MalformedURLException {
		URL miURL = new URL(gestorPropiedades.valorPropertie(ConstantesConsultaDev.URL_WS));
		String timeOut = gestorPropiedades.valorPropertie(ConstantesConsultaDev.TIMEOUT_PROP_DEV);
		
		ConsultaDEVSoapBindingStub stub = null;
		ConsultaDEVServiceLocator consultaDEVServiceLocator = new ConsultaDEVServiceLocator();
		ConsultaDEVPortType consultaDEVPortType = null;
		
		javax.xml.namespace.QName portName = new javax.xml.namespace.QName(miURL.toString(),consultaDEVServiceLocator.getConsultaDEVSoapWSDDServiceName());
		
		@SuppressWarnings("unchecked")
		List<HandlerInfo> list = consultaDEVServiceLocator.getHandlerRegistry().getHandlerChain(portName);
		list.add(getSignerHandler(alias));
		list.add(getFilesHandler(cif,numColegiado));
		  
		consultaDEVPortType =  consultaDEVServiceLocator.getConsultaDEVSoap(miURL);
		stub = (ConsultaDEVSoapBindingStub) consultaDEVPortType;
		stub.setTimeout(Integer.parseInt(timeOut));
		
		return stub;
	}
	
	/**
	 * Instancia y configura un handler para guardar en fichero una copia de las peticiones y respuestas soap
	 * @param numColegiado 
	 * @return Nueva instancia de HandlerInfo
	 */
	private HandlerInfo getFilesHandler(String cif, String numColegiado) {
		// Configuración de la compra que desencadena la peticion
		Map<String, Object> filesConfig = new HashMap<String, Object>();
		filesConfig.put(SoapConsultaDevWSHandler.PROPERTY_KEY_ID, cif);
		filesConfig.put(SoapConsultaDevWSHandler.PROPERTY_KEY_ID_COLEGIADO, numColegiado);

		// Handler de logs
		HandlerInfo filesHandlerInfo = new HandlerInfo();
		filesHandlerInfo.setHandlerClass(SoapConsultaDevWSHandler.class);
		filesHandlerInfo.setHandlerConfig(filesConfig);

		return filesHandlerInfo;
	}
	
	/**
	 * Instancia y configura un handler para realizar la firma de las peticiones soap
	 * @return Nueva instancia de HandlerInfo
	 */
	private HandlerInfo getSignerHandler(String alias) {
		Map<String, Object> config = new HashMap<String, Object>();
		config.put(ConsultaDevSecurityClientHandler.ALIAS_KEY, alias);
		// Handler de firmado
		HandlerInfo signerHandlerInfo = new HandlerInfo();
		signerHandlerInfo.setHandlerClass(ConsultaDevSecurityClientHandler.class);
		signerHandlerInfo.setHandlerConfig(config);
		return signerHandlerInfo;
	}
	
	private Peticion rellenarPeticion(ConsultaDevVO consultaDevVO, BigDecimal idContrato) {
		ContratoVO contratoVO = servicioContrato.getContrato(idContrato);
		int numPeticiones = servicioEvolucionConsultaDev.getNumPeticionesWS(consultaDevVO.getIdConsultaDev());
		String idSolicitud = consultaDevVO.getCif() + consultaDevVO.getIdConsultaDev() + numPeticiones;
		Peticion peticion = new Peticion();
		peticion.setAtributos(rellenarAtributos(consultaDevVO,contratoVO, idSolicitud));
		peticion.setSolicitudes(rellenarSolicitud(consultaDevVO,contratoVO, idSolicitud));
		return peticion;
	}

	private Solicitudes rellenarSolicitud(ConsultaDevVO consultaDevVO, ContratoVO contratoVO, String idSolicitud) {
		DatosGenericos datosGenericos = rellenarDatosGenericos(consultaDevVO,contratoVO, idSolicitud);
		DatosEspecificos datosEspecificos = rellenarDatosEspecificos(consultaDevVO);
		SolicitudTransmision solicitudTransmision = new SolicitudTransmision(datosGenericos, datosEspecificos);
		Solicitudes solicitudes = new Solicitudes(solicitudTransmision);
		return solicitudes;
	}

	private DatosEspecificos rellenarDatosEspecificos(ConsultaDevVO consultaDevVO) {
		DatosEspecificos datosEspecificos = new DatosEspecificos();
		Usuario[] listaUsuarios = new Usuario[1];
		listaUsuarios[0] = new Usuario(consultaDevVO.getCif(),"","");
		Usuarios usuarios = new Usuarios(listaUsuarios);
		datosEspecificos.setUsuarios(usuarios);
		datosEspecificos.setCodProcedimiento(ConstantesConsultaDev.COD_PROCEDIMIENTO_WS);
		return datosEspecificos;
	}

	private DatosGenericos rellenarDatosGenericos(ConsultaDevVO consultaDevVO, ContratoVO contratoVO, String idSolicitud) {
		Emisor emisor = new Emisor();
		emisor.setNifEmisor(ConstantesConsultaDev.NIF_DGT_WS);
		emisor.setNombreEmisor(ConstantesConsultaDev.NOMBRE_DGT_WS);
		Solicitante solicitante = new Solicitante();
		solicitante.setIdentificadorSolicitante(ConstantesConsultaDev.NIF_DGT_WS);
		solicitante.setCodigoSolicitante(ConstantesConsultaDev.CODIGO_SOLICITANTE_WS);
		solicitante.setNombreSolicitante(ConstantesConsultaDev.NOMBRE_DGT_WS);
		solicitante.setCodigoAplicacion(ConstantesConsultaDev.CODIGO_APLICACION_WS);
		solicitante.setFinalidad(ConstantesConsultaDev.FINALIDAD_WS);
		solicitante.setConsentimiento(Consentimiento.Ley);
		Titular titular = new Titular(TipoDocumentacion.CIF,contratoVO.getColegiado().getUsuario().getNif());
		Transmision transmision = new Transmision();
		transmision.setCodigoCertificado(ConstantesConsultaDev.CODIGO_CERTIFICADO_WS);
		transmision.setIdSolicitud(idSolicitud);
		DatosGenericos datosGenericos = new DatosGenericos(emisor, solicitante, titular, transmision);
		return datosGenericos;
	}

	private Atributos rellenarAtributos(ConsultaDevVO consultaDevVO, ContratoVO contratoVO, String idSolicitud) {
		Atributos atributos = new Atributos();
		atributos.setIdPeticion(idSolicitud);
		atributos.setNumElementos(Integer.parseInt(ConstantesConsultaDev.NUM_ELEMENTOS_WS));
		atributos.setTimeStamp(utilesFecha.formatoFecha("yyyy-MM-dd HH:mm:ss", new Date()).replace(" ", "T"));
		atributos.setCodigoCertificado(ConstantesConsultaDev.CODIGO_CERTIFICADO_WS);
		return atributos;
	}
	
	@Override
	public void cambiarEstadoConsulta(BigDecimal idConsultaDev, BigDecimal idUsuario, EstadoConsultaDev estado) {
		try {
			servicioDev.cambiarEstado(idConsultaDev.longValue(),idUsuario , new BigDecimal(estado.getValorEnum()),false);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado de la consulta: " + idConsultaDev + " al estado: " + estado.getNombreEnum() + ", error: ",e);
		}
	}
	
	@Override
	public void devolverCreditos(BigDecimal idConsultaDev, BigDecimal idContrato, BigDecimal idUsuario) {
		try {
			ResultBean resultado = new ResultBean();
			if("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie(ServicioDev.cobrarCreditos))){
				resultado = servicioCredito.devolverCreditos(TipoTramiteTrafico.Consulta_Dev.getValorEnum(), idContrato, 
					1, idUsuario, ConceptoCreditoFacturado.DDEV, idConsultaDev.toString());
			}
			if(resultado.getError()){
				for(String mensaje : resultado.getListaMensajes()){
					log.error("Ha sucedido un error a la hora de devolver el credito en el proceso consulta Dev, error: " + mensaje);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de devolver los creditos de la consulta dev: " + idConsultaDev + ", error: ",e);
		}
	}
}
