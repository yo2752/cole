package org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.impl;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.rpc.ServiceException;

import org.gestoresmadrid.core.general.model.vo.ContratoUsuarioVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.trafico.eeff.model.dao.ConsultaDao;
import org.gestoresmadrid.core.trafico.eeff.model.enumerados.ConstantesEEFF;
import org.gestoresmadrid.core.trafico.eeff.model.vo.EeffConsultaVO;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioEEFFConsulta;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioEEFFLiberacion;
import org.gestoresmadrid.oegam2comun.trafico.eeff.utils.TransformToXML;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.beans.DetalleConsultaEEFFBean;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.EeffConsultaDTO;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.colegiado.services.ServicioColegiado;
import org.gestoresmadrid.oegamComun.colegiado.view.dto.ColegiadoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.viafirma.cliente.exception.InternalException;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;

import colas.modelo.ModeloSolicitud;
import colas.utiles.UtilesEEFF;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import es.trafico.cliente.vehiculos.custodiaitv.webservice.SolicitudOperacionesITVWS;
import es.trafico.cliente.vehiculos.custodiaitv.webservice.SolicitudOperacionesITVWSService;
import es.trafico.cliente.vehiculos.custodiaitv.webservice.SolicitudOperacionesITVWSServiceLocator;
import es.trafico.servicios.vehiculos.custodiaitv.beans.ConsultaEITVRespuestaDTO;
import es.trafico.servicios.vehiculos.custodiaitv.beans.InfoErrorDTO;
import escrituras.beans.ResultBean;
import trafico.beans.jaxb.consultaEEFF.RespuestaEEFF;
import trafico.modelo.ModeloCreditosTrafico;
import trafico.modelo.ModeloTrafico;
import trafico.utiles.XMLGAFactory;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

/**
 * 
 * @author GLobaltms
 *
 */
@Service
public class ServicioEEFFConsultaImpl implements ServicioEEFFConsulta {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEEFFConsultaImpl.class);
	private ModeloSolicitud modeloSolicitud;
	
	@Autowired
	ServicioCola servicioCola;
	
	@Autowired
	private ConsultaDao consultaDao;
	
	@Autowired
    private GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioCredito servicioCredito;

	@Autowired
	private Conversor conversor;
	
	@Autowired
	private ServicioColegiado servicioColegiado;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	ModeloCreditosTrafico modeloCreditosTrafico = null;
	ModeloTrafico modeloTrafico = null;
	SolicitudOperacionesITVWSService soOperacionesITVWSService = null;
	
	/**
	 * @throws OegamExcepcion 
	 * @throws InternalException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws JAXBException 
	 * @throws ParseException 
	 * @throws Exception 
	 * 
	 */
	
	@Override
	public boolean solicitarConsulta(EeffConsultaDTO eeffConsultaDTO){
		log.debug("Ha entrado método  solicitar servicio consulta");
		BigDecimal usuario = utilesColegiado.getIdUsuarioSessionBigDecimal();
		String nombreFicheroXML =generarXml(eeffConsultaDTO); //guardarXml(generarXml((Eeff)eeffConsulta), ConstantesEEFF.TIPO_GUARDAR_XML_PETICION_CONSULTAR);
		try {
			ResultBean resultBean = servicioCola.crearSolicitud(ProcesosEnum.PROCESO_EEFF.getNombreEnum(), nombreFicheroXML, gestorPropiedades.valorPropertie(ServicioEEFFLiberacion.NOMBRE_HOST),
					TipoTramiteTrafico.consultaEEFF.getValorEnum(), eeffConsultaDTO.getNumExpediente().toString(), usuario, null,null);
			if(resultBean == null || !resultBean.getError()){
				return true;
			}
			return false;
		} catch (Throwable e) {
			 log.error("Un error ha ocurrido al crear una Solicitud para encolar la peteción");
	         log.error(e.getMessage());
			return false;
		}
	}
	
	
	@Override
	public List<String> validarDatos(EeffConsultaDTO eeff) {
		log.debug("Ha entrado método validar Datos consulta");
		List<String> errores = new ArrayList<String>();
		if (eeff== null){
			errores.add(ConstantesEEFF.EEFF_TEXTO_ERROR_VALIDACION_CONSULTA);		
		}else{
			try {
				if (eeff.getTarjetaNive()!=null && !eeff.getTarjetaNive().isEmpty()){
					if (eeff.getTarjetaNive().length()!=ConstantesEEFF.EEFF_LONGITUD_NIVE){ 
						errores.add(ConstantesEEFF.EEFF_TEXTO_ERROR_VALI_LONG_NIVE);		
					}
				} else {
					errores.add(ConstantesEEFF.EEFF_TEXTO_ERROR_VALI_NIVE);	
				}
				if (eeff.getTarjetaBastidor()==null || eeff.getTarjetaBastidor().isEmpty()){
					errores.add(ConstantesEEFF.EEFF_TEXTO_ERROR_VALI_BASTIDOR);	
				}
				if (eeff.getFirCif()==null || eeff.getFirCif().isEmpty()){
						errores.add(ConstantesEEFF.EEFF_TEXTO_ERROR_VALI_FIRCIF);				
				}
				if (eeff.getFirMarca()==null || eeff.getFirMarca().isEmpty()){
						errores.add(ConstantesEEFF.EEFF_TEXTO_ERROR_VALI_FIRMARCA);
				}
				if(eeff.getNifRepresentado() == null || eeff.getNifRepresentado().isEmpty()){
					errores.add(ConstantesEEFF.EEFF_TEXTO_ERROR_CIF_CONCESIONARIO);
				}
			} catch (ClassCastException e) {
				errores.add(ConstantesEEFF.EEFF_TEXTO_ERROR_VALIDACION_CONSULTA);
			}
		}
		
		return errores;
	}


	/**
	 * 
	 * @param eeffConsultaDTO
	 * @return
	 */
	private EeffConsultaVO convertirEEFFConsultaDTOToEEFFConsulta(EeffConsultaDTO eeffConsultaDTO){
		return conversor.transform(eeffConsultaDTO, EeffConsultaVO.class);
	}
	
	/**
	 * 
	 * @param eeffConsulta
	 * @return
	 */
	private EeffConsultaDTO convertirEEFFConsultaToEEFFConsultaDTO(EeffConsultaVO eeffConsulta){
		return conversor.transform(eeffConsulta, EeffConsultaDTO.class);
	}
	
	/**
	 * 
	 */
	private String generarXml(EeffConsultaDTO eeffDto){
		String xml;
		File file =null;
		try {
			xml = TransformToXML.transformarDtoLibXml(eeffDto);
			if(xml !=null && !xml.isEmpty()){
				file = gestorDocumentos.guardarFichero(ConstantesEEFF.EEFF, ConstantesEEFF.EEFF_SUBTIPO_CONSULTA, 
						Utilidades.transformExpedienteFecha(eeffDto.getNumExpediente()), "EEFFCONSULTA_"+eeffDto.getNumExpediente(), 
						".xml", xml.getBytes("UTF-8"));
				if (file!=null){
					return file.getName();
				}
			}
			
		} catch (ParseException e) {
			log.error("Se ha producido un error al intentar parsear el documento xml\n" + e.getMessage());
		} catch (JAXBException je) {
			log.error("Se ha producido un error al generar  el documento xml\n"+ je.getMessage());
		} catch (SAXException se) {
			log.error("Se ha producido un error al generar el documento xml\n" + se.getMessage());
		} catch (IOException ie) {
			log.error("Se ha producido un error al tratar el archivo xml\n" + ie.getMessage());
		} catch (InternalException ine) {
			log.error("Ha ocurrido un error interno al generar el archivo xml\n" + ine.getMessage());
		} catch (OegamExcepcion oe) {
			log.error("Ha ocurrido un error  al generar el archivo xml\n" + oe.getMessage());
		}
		return null;
	}
	
	/**
	 * 
	 */
	private BigDecimal generarNumExpediente(String numColegiado){
		
		BigDecimal expediente = consultaDao.generarNumExpediente(numColegiado);
		Formatter fmt = new Formatter();
		//Forma el número 00001
		String resultSumarPet =fmt.format("%05d",(new BigDecimal(1)).intValue()).toString();
		fmt.close();	
		if (expediente==null)
		{			
			String exp = numColegiado;
			Fecha fecha = new Fecha(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
			exp += fecha.getDia()+fecha.getMes()+fecha.getAnio().substring(2);
			exp += ConstantesEEFF.TIPO_ID_EEFF;
			exp += resultSumarPet;
			expediente = new BigDecimal(exp);
		}else{			
			expediente= expediente.add(new BigDecimal(resultSumarPet));
		}

		return expediente; 
	}

	
	@Override
	@Transactional
	public BigDecimal guardarDatos(EeffConsultaVO eeffConsulta) {
		try {
			log.debug("Ha entrado guardar Datos consulta");
			eeffConsulta.setNumExpediente(generarNumExpediente(eeffConsulta.getNumColegiado().toString()));
			return consultaDao.guardarConsulta(eeffConsulta);
		} catch (ClassCastException e) {
			log.error("Error guardado los datos.");
		}
		return null;
	}

	@Override
	@Transactional
	public boolean actualizarDatos(EeffConsultaDTO eeffDTO) {
		return actualizarDatosVO(convertirEEFFConsultaDTOToEEFFConsulta(eeffDTO));
	}
	
	@Override
	@Transactional
	public boolean actualizarDatosVO (EeffConsultaVO eeffConsultaVO){
		return consultaDao.actualizar(eeffConsultaVO) !=null;
	}

	@Override
	@Transactional
	public EeffConsultaDTO recuperarDatos(BigDecimal numExpediente) {
		return convertirEEFFConsultaToEEFFConsultaDTO(recuperarDatosVO(numExpediente));
	}
	
	@Override
	public EeffConsultaVO recuperarDatosVO(BigDecimal numExpediente){
		return consultaDao.buscarPorExpediente(numExpediente);
	}
	
	private ModeloSolicitud getModeloSolicitud() {
		if (modeloSolicitud ==null){
			modeloSolicitud = new ModeloSolicitud();
		}
		return modeloSolicitud;
	}

	public void setModeloSolicitud(ModeloSolicitud modeloSolicitud) {
		this.modeloSolicitud = modeloSolicitud;
	}

	public ConsultaDao getConsultaDao() {
		return consultaDao;
	}

	public void setConsultaDao(ConsultaDao consultaDao) {
		this.consultaDao = consultaDao;
	}


	@Override
	@Transactional
	public ResultBean consultar(EeffConsultaDTO eeffConsulta) {
		BigDecimal idUsuario = new BigDecimal(utilesColegiado.getIdUsuarioSession());
		BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
		ResultBean resultBean;
		
		boolean cobrarCreditosOk = descontarCreditos(idUsuario, idContrato, TipoTramiteTrafico.consultaEEFF.getValorEnum());
		if(cobrarCreditosOk){
			//Se inicializa el bean con el número de colegiado y la fecha actual
			eeffConsulta.setNumColegiado(Integer.valueOf(utilesColegiado.getNumColegiadoSession()).intValue());			
			eeffConsulta.setFechaRealizacion(utilesFecha.getFechaHoraActualLEG());
			//Se validan los datos de entrada
			List<String> erroresValidacion = validarDatos(eeffConsulta);
			if (erroresValidacion !=null && erroresValidacion.size()>0) {
				resultBean = new ResultBean(true, ConstantesEEFF.EEFF_TEXTO_ERROR_VALIDACION_CONSULTA);
				for (int i=0; i<erroresValidacion.size(); i++){
					resultBean.addMensajeALista(erroresValidacion.get(i));
				}
				return resultBean;
			}
			
			//Se guarda la petición al servicio consulta, con un numero de expediente generado
			EeffConsultaVO eeffConsultaVO = convertirEEFFConsultaDTOToEEFFConsulta(eeffConsulta); 
			eeffConsulta.setNumExpediente(guardarDatos(eeffConsultaVO));
			eeffConsultaVO.setNumExpediente(eeffConsulta.getNumExpediente());
			
			//Se realiza el proceso de encolar la petición
			boolean resultadoSolicitud = solicitarConsulta(eeffConsulta);
			if (resultadoSolicitud) {
				log.error("Se ha realizado la petición del servicio de consulta de tarjeta Eitv en Entidades Financieras del expediente " + eeffConsultaVO.getNumExpediente());
				resultBean = new ResultBean (false, ConstantesEEFF.EEFF_TEXTO_SOLICITUD_CONSULTA_CORRECTA);
			} else {
				log.error("No se ha realizado la petición del servicio de consulta de tarjeta Eitv en Entidades Financieras del expediente " + eeffConsultaVO.getNumExpediente());
				resultBean = new ResultBean(true, ConstantesEEFF.EEFF_TEXTO_SOLICITUD_CONSULTA_ERROR);
				borrarConsulta(eeffConsultaVO);
				devolverCreditos(idUsuario, idContrato, TipoTramiteTrafico.consultaEEFF.getValorEnum());
				
			}		
		}else{
			log.error("No tiene creditos suficientes para realizar la consulta.");
			resultBean = new ResultBean(true, "No tiene creditos suficientes para realizar la consulta.");
		}		
		return resultBean;
	}

	
	private void borrarConsulta(EeffConsultaVO eeffConsultaVO) {
		try {
			log.debug("Ha entrado borrar Solicitud consulta");
			consultaDao.borrar(eeffConsultaVO);
		} catch (ClassCastException e) {
			log.error("Error borrado de los datos.");
		}
	}


	@Override
	public StringBuffer getFicheroXml(ColaBean solicitud) {
		BigDecimal idEnvio = solicitud.getIdTramite();
		File file = null;
		StringBuffer xml = null;
		
		try {
			file = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesEEFF.EEFF, ConstantesEEFF.EEFF_SUBTIPO_CONSULTA, Utilidades.transformExpedienteFecha(idEnvio),solicitud.getXmlEnviar(),ConstantesGestorFicheros.EXTENSION_XML).getFile();
			if(file!=null){
				xml = new StringBuffer("<![CDATA[");
				//Solo debe de haber un documento para un expediente.
				log.info("Obtenido fichero XML " + file.getName());
				
				BufferedReader br = new BufferedReader( new InputStreamReader(new FileInputStream(file), "UTF-8"));
				int value=0;
		        // reads to the end of the stream 
		        while((value = br.read()) != -1){
		        	//converts int to character
		            xml.append((char) value);
		        }
				br.close();
				xml.append("]]>");
				if (log.isInfoEnabled()) {
					log.info("XML generado: " + xml);
				}
			}
		} catch (OegamExcepcion e) {
			log.error("Error: Fichero no encontrado." + e);
		} catch (IOException e) {
			log.error("Error al leeer el fichero."+e);
		}
		
		return xml;
	}

	protected EeffConsultaVO consultaDatosProceso(EeffConsultaVO eeffConsulta, String estadoBastidor) {
		if(estadoBastidor.equals(ConstantesEEFF.ESTADO_BASTIDOR_LIBERADO)){
			log.info("Bastidor en estado liberado");
			eeffConsulta.setRealizado(true);
		}else{
			log.info("Bastidor en estado desconocido");
			eeffConsulta.setRealizado(false);
		}
		
		eeffConsulta.setRespuesta(estadoBastidor);
		
		actualizarDatosVO(eeffConsulta);
		log.info("Consulta actualizada, se guarda la respuesta");
		
		return eeffConsulta;
	}


	@Override
	public ResultBean comprobarCreditos(int numCreditos) {
		//Hay creditos para poder liberar
		if(!utilesColegiado.tienePermisoAdmin()){
			ContratoUsuarioVO contrato = utilesColegiado.getContratoUsuario(utilesColegiado.getIdUsuarioSessionBigDecimal().toString());
			ResultBean result =  servicioCredito.validarCreditos(TipoTramiteTrafico.consultaEEFF.getValorEnum(), BigDecimal.valueOf(contrato.getId().getIdContrato()), new BigDecimal(numCreditos));
			if(result.getError()){
				return new ResultBean(true, "No tiene suficientes creditos para hacer la liberación");
			}
		}
				
		return new ResultBean();
	}

	@Override
	public ResultBean comprobarCreditosProceso(String idUsuario, int numCreditos) {
		ContratoUsuarioVO contrato = utilesColegiado.getContratoUsuario(idUsuario);
		ResultBean result =  servicioCredito.validarCreditos(TipoTramiteTrafico.consultaEEFF.getValorEnum(), BigDecimal.valueOf(contrato.getId().getIdContrato()), new BigDecimal(numCreditos));
		if(result.getError()){
			return new ResultBean(true, "No tiene suficientes creditos para hacer la liberación");
		}
				
		return new ResultBean();
	}
	
	private boolean devolverCreditos(BigDecimal idUsuario, BigDecimal idcontrato, String tipoTramite) {
		String cobraCreditos = gestorPropiedades.valorPropertie(ConstantesEEFF.EEFF_COBRAR_CREDITOS_CONSULTA);
		if (cobraCreditos==null || !cobraCreditos.equals("true")){
			return true;
		}

		ResultBean resultBean = servicioCredito.devolverCreditos(tipoTramite, idcontrato, -1, idUsuario, ConceptoCreditoFacturado.DEFC);
		if(resultBean.getError()){
			log.error("Error al devolver los creditos.");
		}
		return !resultBean.getError();
	}

	private boolean descontarCreditos(BigDecimal idUsuario, BigDecimal idcontrato, String tipoTramite) {
		String cobraCreditos = gestorPropiedades.valorPropertie(ConstantesEEFF.EEFF_COBRAR_CREDITOS_CONSULTA);
		if (cobraCreditos==null || !cobraCreditos.equals("true")){
			return true;
		}
		// El tipo de credito es decremental y se descuenta credito en negativo... es una manera distinta de poner credito incremental
		ResultBean res = servicioCredito.descontarCreditos(tipoTramite, idcontrato, new BigDecimal(-1), idUsuario, ConceptoCreditoFacturado.EFC);
		if(res.getError()){
			log.error("ERROR DESCONTAR CREDITOS");
			log.error("CONTRATO: " + idcontrato.toString());
			log.error("ID_USUARIO: " + idUsuario);
		}
		return !res.getError();		
	}

	@Override
	@Transactional
	public ResultBean consultaProceso(ColaBean solicitud) {
		ConsultaEITVRespuestaDTO respuestaConsultaDTO = null;
		EeffConsultaVO eeffConsultaVO = null;
		ResultBean resultado = null;
		ContratoUsuarioVO contrato =  utilesColegiado.getContratoUsuario(solicitud.getIdUsuario().toString());
		try {
			boolean creditosOk = descontarCreditos(solicitud.getIdUsuario(), new BigDecimal(contrato.getId().getIdContrato()), TipoTramiteTrafico.consultaEEFF.getValorEnum());
			if(creditosOk){
				StringBuffer xml = getFicheroXml(solicitud);
				if(xml != null){
					ColegiadoDto colegiadoDto = servicioColegiado.getColegiadoDto("2717");
					UtilesEEFF utilesEEFF = new UtilesEEFF();
					utilesEEFF.getHandlerFirmado(colegiadoDto.getAlias(), getSoOperacionesITVWSService());
					SolicitudOperacionesITVWS solicitudOperacionesITVWS = soOperacionesITVWSService.getSolicitudOperacionesITVWS(new URL(gestorPropiedades.valorPropertie(ConstantesEEFF.EEFF_URL)));
					log.info("Enviando consultarEITV...");
					respuestaConsultaDTO = solicitudOperacionesITVWS.consultarEITV("ES_es", xml.toString());
					log.info("Respuesta obtenida de consultarEITV");
					eeffConsultaVO = recuperarDatosVO(solicitud.getIdTramite());
					if(respuestaConsultaDTO ==  null){
						devolverCreditos(solicitud.getIdUsuario(),  new BigDecimal(contrato.getId().getIdContrato()),TipoTramiteTrafico.consultaEEFF.getValorEnum());
						resultado = new ResultBean(true, "La respuesta del webService ha sido nula");
					}else {
						if(respuestaConsultaDTO.getInfoErrores() != null && respuestaConsultaDTO.getInfoErrores().length > 0){
							String sRespuesta = "";
							for(InfoErrorDTO info : respuestaConsultaDTO.getInfoErrores()){
								sRespuesta = info.getCodigoError()+":"+info.getDescripcionError() + " ";
							}
							eeffConsultaVO = consultaDatosProceso(eeffConsultaVO, sRespuesta);
							resultado = new ResultBean(false, sRespuesta);
						}else{
							eeffConsultaVO = consultaDatosProceso(eeffConsultaVO, respuestaConsultaDTO.getDatossimpleeitv().getEstadoBastidor());
						}
						guardarRespuesta(respuestaConsultaDTO,eeffConsultaVO.getNumExpediente().toString());
					}
					
				}else{
					eeffConsultaVO = recuperarDatosVO(solicitud.getIdTramite());
					eeffConsultaVO = consultaDatosProceso(eeffConsultaVO, "Error: No se puede realizar la consulta porque no existe un fichero xml con los datos.");
					guardarRespuesta(devolverRespuestaErrorXml(),solicitud.getIdTramite().toString());
				}
			}
		} catch (MalformedURLException e) {
			devolverCreditos(solicitud.getIdUsuario(),  new BigDecimal(contrato.getId().getIdContrato()),TipoTramiteTrafico.consultaEEFF.getValorEnum());
			resultado = new ResultBean(true, "Error consulta: " + e);
			log.error("Error consulta: " + e);
		} catch (ServiceException e) {
			devolverCreditos(solicitud.getIdUsuario(),  new BigDecimal(contrato.getId().getIdContrato()),TipoTramiteTrafico.consultaEEFF.getValorEnum());
			resultado = new ResultBean(true, "Error consulta: " + e);
			log.error("Error consulta: " + e);
		} catch (RemoteException e) {
			devolverCreditos(solicitud.getIdUsuario(),  new BigDecimal(contrato.getId().getIdContrato()),TipoTramiteTrafico.consultaEEFF.getValorEnum());
			resultado = new ResultBean(true, "Error consulta: " + e);
			log.error("Error consulta: " + e);
		}
		return resultado; 
	}

	protected ConsultaEITVRespuestaDTO devolverRespuestaErrorXml() {
		ConsultaEITVRespuestaDTO respuestaConsultaDTO = new ConsultaEITVRespuestaDTO();
		
		InfoErrorDTO infoErrorDTO = new InfoErrorDTO();
		infoErrorDTO.setCodigoError("0");
		infoErrorDTO.setDescripcionError("Error: No se puede realizar la consulta porque no existe un fichero xml con los datos.");
		InfoErrorDTO[] listaInfoErrorDTOs = new InfoErrorDTO[1];
		listaInfoErrorDTOs[0] = infoErrorDTO;
		respuestaConsultaDTO.setInfoErrores(listaInfoErrorDTOs);
		
		return respuestaConsultaDTO;
	}


	protected void guardarRespuesta(Object XmlRespuesta,String numExpediente){
		String xmlXStream = null;
		//Pasando un objeto a xml.
		XStream xstream = new XStream();
		xstream.processAnnotations(XmlRespuesta.getClass());
		xmlXStream = xstream.toXML(XmlRespuesta);

		FicheroBean fichero = new FicheroBean();
		fichero.setNombreDocumento(ConstantesGestorFicheros.RESPUESTA_EEFF + numExpediente);
		fichero.setTipoDocumento(ConstantesGestorFicheros.EEFF);
		fichero.setSubTipo(ConstantesGestorFicheros.EEFFCONSULTA);
		fichero.setFicheroByte(xmlXStream.getBytes());
		fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
		fichero.setSobreescribir(false);
		if(numExpediente != null && numExpediente.isEmpty()){
			fichero.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
		}else{
			fichero.setFecha(utilesFecha.getFechaHoraActualLEG());
		}
		
		try {
			gestorDocumentos.guardarFichero(fichero);
		} catch (OegamExcepcion e) {
			log.error("Error al guardar la respeusta",e, numExpediente);
		}
	}
	
	@Override
	@Transactional
	public DetalleConsultaEEFFBean getDetalleConsulta(String numExpediente) throws FileNotFoundException, JAXBException, UnsupportedEncodingException {
		DetalleConsultaEEFFBean detalleConsultaEEFFBean = null;
		File file = getFicheroRespuestaXml(numExpediente);
			
		if(file == null){
			return null;
		}
		Unmarshaller unmarshaller = new XMLGAFactory().getConsultaEEFFContext().createUnmarshaller();
		InputStream inputStream = new FileInputStream(file);
		Reader reader = new InputStreamReader(inputStream, "ISO-8859-15");
		
		detalleConsultaEEFFBean = convertirRespuestaEEFFToDetalleConsultaBean((RespuestaEEFF) unmarshaller.unmarshal(reader));

		return detalleConsultaEEFFBean;
	}
	
	private DetalleConsultaEEFFBean convertirRespuestaEEFFToDetalleConsultaBean(RespuestaEEFF respuestaEEFF) {
		
		if(respuestaEEFF != null){
			DetalleConsultaEEFFBean detalleConsultaEEFFBean = new DetalleConsultaEEFFBean();
			if(respuestaEEFF.getInfoErrores() != null){
				if(respuestaEEFF.getInfoErrores().getInfoErrorDTO() != null){
					detalleConsultaEEFFBean.setCodigoError(respuestaEEFF.getInfoErrores().getInfoErrorDTO().getCodigoError());
					detalleConsultaEEFFBean.setDescripcionError(respuestaEEFF.getInfoErrores().getInfoErrorDTO().getDescripcionError());
				}
				return detalleConsultaEEFFBean;
			}else{
				if(respuestaEEFF.getDatossimpleeitv() != null){
					if(respuestaEEFF.getDatossimpleeitv().getBaseeitvdto() != null){
						detalleConsultaEEFFBean.setTarjetaBastidor(respuestaEEFF.getDatossimpleeitv().getBaseeitvdto().getBastidor());
						detalleConsultaEEFFBean.setTarjetaNive(respuestaEEFF.getDatossimpleeitv().getBaseeitvdto().getNive());
					}
					
					detalleConsultaEEFFBean.setConcesionarioComercial(respuestaEEFF.getDatossimpleeitv().getConcesionarioClienteComercial());
					detalleConsultaEEFFBean.setCustodiaActual(respuestaEEFF.getDatossimpleeitv().getCustodioActual());
					detalleConsultaEEFFBean.setCustodioSiguiente(respuestaEEFF.getDatossimpleeitv().getCustodioSiguiente());
					detalleConsultaEEFFBean.setEstadoFinanciero(respuestaEEFF.getDatossimpleeitv().getDenominacionEstadoFinanciero());
					detalleConsultaEEFFBean.setNifCliente(respuestaEEFF.getDatossimpleeitv().getDninifNieClienteFinal());
					detalleConsultaEEFFBean.setEstadoBastidor(respuestaEEFF.getDatossimpleeitv().getEstadoBastidor());
					detalleConsultaEEFFBean.setFechaFacturaFinal(respuestaEEFF.getDatossimpleeitv().getFechaFacturaFinal());
					detalleConsultaEEFFBean.setFirCif(respuestaEEFF.getDatossimpleeitv().getFirCif());
					detalleConsultaEEFFBean.setFirMarca(respuestaEEFF.getDatossimpleeitv().getFIRMarca());
					detalleConsultaEEFFBean.setImporteFacturaFinal(respuestaEEFF.getDatossimpleeitv().getImporteFacturaFinal());
					detalleConsultaEEFFBean.setNumeroFacturaFinal(respuestaEEFF.getDatossimpleeitv().getNumeroFacturaFinal());
					detalleConsultaEEFFBean.setCustodioAnterior(respuestaEEFF.getDatossimpleeitv().getCustodioAnterior());
					detalleConsultaEEFFBean.setCustodioFinal(respuestaEEFF.getDatossimpleeitv().getCustodioFinal());
					detalleConsultaEEFFBean.setEntidadCredito(respuestaEEFF.getDatossimpleeitv().getEntidadCredito());
					
					if(respuestaEEFF.getDatossimpleeitv().getDatosHistoricosITV() != null){
						detalleConsultaEEFFBean.setCustodioActualAnterior(respuestaEEFF.getDatossimpleeitv().getDatosHistoricosITV().getCustodioActualAnterior());
						detalleConsultaEEFFBean.setCustodioAnteriorAnterior(respuestaEEFF.getDatossimpleeitv().getDatosHistoricosITV().getCustodioAnteriorAnterior());
						detalleConsultaEEFFBean.setCustodioFinalAnterior(respuestaEEFF.getDatossimpleeitv().getDatosHistoricosITV().getCustodioFinalAnterior());
						detalleConsultaEEFFBean.setCustodioSiguienteAnterior(respuestaEEFF.getDatossimpleeitv().getDatosHistoricosITV().getCustodioSiguienteAnterior());
						detalleConsultaEEFFBean.setDenominacioNEstadoFinancieroAnterior(respuestaEEFF.getDatossimpleeitv().getDatosHistoricosITV().getDenominacionEstadoFinancieroAnterior());
					}
					
					if(respuestaEEFF.getDatossimpleeitv().getNombreApellidosClienteFinal().getString() != null){
						String nombre = "";
						String apellidos = "";
						for(int i = 0;i< respuestaEEFF.getDatossimpleeitv().getNombreApellidosClienteFinal().getString().size();i++){
							if(i==0){
								nombre = ", " + respuestaEEFF.getDatossimpleeitv().getNombreApellidosClienteFinal().getString().get(i);
							}else{
								apellidos +=  respuestaEEFF.getDatossimpleeitv().getNombreApellidosClienteFinal().getString().get(i) + " ";
							}
						}
						detalleConsultaEEFFBean.setNombreApellidosCliente(apellidos + nombre);
					}
					
					if(respuestaEEFF.getDatossimpleeitv().getDireccionCliente().getString() != null){
						String[] sDireccion = new String[respuestaEEFF.getDatossimpleeitv().getDireccionCliente().getString().size()];
						for(int e=0;e<respuestaEEFF.getDatossimpleeitv().getDireccionCliente().getString().size();e++){
							sDireccion[e] = respuestaEEFF.getDatossimpleeitv().getDireccionCliente().getString().get(e);
						}
						
						detalleConsultaEEFFBean.setDireccionCliente(sDireccion);
					}
					return detalleConsultaEEFFBean;
				}
			}
		}
		
		return null;
	}


	public File getFicheroRespuestaXml(String numExpediente) {
		File file = null;
		String nombreFicheroResp = "RESPUESTA_WS_" + numExpediente;
		try {
			file = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesEEFF.EEFF, ConstantesEEFF.EEFF_SUBTIPO_CONSULTA, 
					Utilidades.transformExpedienteFecha(numExpediente),nombreFicheroResp,".xml").getFile();
		} catch (OegamExcepcion e) {
			log.error("Error al leeer el fichero."+e);
		}
		return file;
	}

	private DetalleConsultaEEFFBean getDetalleEEFFConsulta(String numExpediente) {
		EeffConsultaVO eeffConsultaVO = recuperarDatosVO(new BigDecimal(numExpediente));
		if(!utilesColegiado.tienePermisoAdmin()){
			if(!eeffConsultaVO.getNumColegiado().toString().equals(utilesColegiado.getNumColegiadoSession())){
				return null;
			}
		}
		return convertirEEFFConsultaVOToDetalleConsultaBean(eeffConsultaVO);
	}


	private DetalleConsultaEEFFBean convertirEEFFConsultaVOToDetalleConsultaBean(EeffConsultaVO eeffConsultaVO) {
		return conversor.transform(eeffConsultaVO, DetalleConsultaEEFFBean.class);
	}
	
	@Override
	@Transactional
	public EeffConsultaVO obtenerRespuestaDesdeExpedienteLiberacion(BigDecimal numExpediente) {
		EeffConsultaVO consulta = consultaDao.consultarDesdeExpedienteLiberacion(numExpediente);
		if (consulta==null){
			return null;
		}
		return consulta;
	}

	public SolicitudOperacionesITVWSService getSoOperacionesITVWSService() {
		if(soOperacionesITVWSService == null){
			soOperacionesITVWSService = new SolicitudOperacionesITVWSServiceLocator();
			return soOperacionesITVWSService;
		}
		return soOperacionesITVWSService;
	}


	public ModeloCreditosTrafico getModeloCreditosTrafico() {
		if(modeloCreditosTrafico == null){
			modeloCreditosTrafico = new ModeloCreditosTrafico();
			return modeloCreditosTrafico;
		}
		return modeloCreditosTrafico;
	}

	public ModeloTrafico getModeloTrafico() {
		if(modeloTrafico == null){
			modeloTrafico = new ModeloTrafico();
			return modeloTrafico;
		}
		return modeloTrafico;
	}
	
}
