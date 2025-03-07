package org.gestoresmadrid.oegam2comun.consultaEitv.model.service.impl;

import java.io.FileNotFoundException;
import java.math.BigDecimal;

import javax.xml.bind.JAXBException;

import org.gestoresmadrid.core.general.model.vo.ColegiadoVO;
import org.gestoresmadrid.core.general.model.vo.ColegioVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TextoNotificacion;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.core.trafico.prematriculados.model.vo.VehiculoPrematriculadoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.consultaEitv.model.dto.RespuestaConsultaEitv;
import org.gestoresmadrid.oegam2comun.consultaEitv.model.service.ServicioConsultaEitv;
import org.gestoresmadrid.oegam2comun.consultaEitv.model.service.ServicioWebServiceConsultaEitv;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.sega.model.service.ServicioWebServiceConsultaEitvSega;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.prematriculados.model.service.ServicioVehiculosPrematriculados;
import org.gestoresmadrid.oegam2comun.trafico.prematriculados.view.dto.VehiculoPrematriculadoDto;
import org.gestoresmadrid.oegamComun.colegiado.services.ServicioColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import trafico.beans.schemas.generated.eitv.generated.ConsultaTarjeta;
import trafico.beans.schemas.generated.eitv.generated.DatosFirmados;
import trafico.beans.schemas.generated.eitv.generated.ObjectFactory;
import trafico.beans.schemas.generated.eitv.generated.TipoTextoLegal;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;

@Service
public class ServicioConsultaEitvImpl implements ServicioConsultaEitv{

	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ServicioConsultaEitvImpl.class);

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	private ServicioCola servicioCola;

	@Autowired
	private ServicioWebServiceConsultaEitv servicioWebServiceConsultaEitv;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioWebServiceConsultaEitvSega servicioWebServiceConsultaEitvSega;

	@Autowired
	private ServicioVehiculosPrematriculados servicioVehiculosPrematriculados;

	@Autowired
	private ServicioColegiado servicioColegiado;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	private ObjectFactory objectFactory;

	private org.gestoresmadrid.oegam2comun.sega.eitv.view.xml.ObjectFactory objectFactorySega;

	@Override
	@Transactional
	public ResultBean consultaEitv(BigDecimal numExp, BigDecimal idUsuario, BigDecimal idContrato) {
		ResultBean result = new ResultBean(false);
		try {
			TramiteTraficoVO tramiteTraficoVO = comprobarNumExpediente(numExp);
			if(tramiteTraficoVO != null){
				// Mantis 19525. David Sierra: Comprobación que sea un trámite de Matriculación y en estado Iniciado
				if(!TipoTramiteTrafico.Matriculacion.toString().equals(tramiteTraficoVO.getTipoTramite())) {
					result.setError(true);
					result.addMensajeALista("Para realizar la Consulta de Tarjeta eITV el tipo de trámite tiene que ser de Matriculación");
				}else if(!EstadoTramiteTrafico.Iniciado.toString().equals(tramiteTraficoVO.getEstado().toString()) && !EstadoTramiteTrafico.Error_Consulta_EITV.toString().equals(tramiteTraficoVO.getEstado().toString())) {
					result.setError(true);
					result.addMensajeALista("Para realizar la Consulta de Tarjeta eITV el trámite tiene que estar en estado Iniciado");
				}else {
					String consultaEitv = gestorPropiedades.valorPropertie("nuevas.url.sega.consultaEitv");
					if("SI".equals(consultaEitv)){
						String xmlEnviar = ServicioConsultaEitv.CONSULTA_TARJETA_XML_ENVIAR + "_" + numExp;
						ResultBean resultXmlConsulta = crearXmlConsultaEitv(tramiteTraficoVO, idUsuario, xmlEnviar);
						if(!resultXmlConsulta.getError()){
							ResultBean resultado = servicioTramiteTrafico.cambiarEstadoConEvolucion(tramiteTraficoVO.getNumExpediente(), EstadoTramiteTrafico.convertir(tramiteTraficoVO.getEstado()),
									EstadoTramiteTrafico.Pendiente_Consulta_EITV, true, TextoNotificacion.Cambio_Estado.getNombreEnum(), idUsuario);
							if (resultado == null || !resultado.getError()) {
								resultado = servicioCola.crearSolicitud(ProcesosEnum.CONSULTA_TARJETA_EITV_SEGA.getNombreEnum(), xmlEnviar, gestorPropiedades.valorPropertie(ServicioConsultaEitv.NOMBRE_HOST_SOLICITUD_NODO_2),
									TipoTramiteTrafico.ConsultaEITV.toString(), numExp.toString(), idUsuario, null, idContrato);
							}
							if(resultado != null && !resultado.getError()){
								result.setError(false);
								result.addMensajeALista("Solicitud de consulta de tarjeta eITV correcta para el expediente: " + tramiteTraficoVO.getNumExpediente());
							}else{
								result.addMensajeALista(resultado.getListaMensajes().get(0));
								result.setError(true);
							}
						}else{
							for(String mensaje  : resultXmlConsulta.getListaMensajes()){
								result.addMensajeALista(mensaje);
							}
							result.setError(true);
						}
					}else{
						String xmlEnviar = ServicioConsultaEitv.CONSULTA_TARJETA_XML_ENVIAR + "_" + numExp;
						ResultBean resultXmlConsulta = crearXmlConsultaEitv(tramiteTraficoVO, idUsuario, xmlEnviar);
						if(!resultXmlConsulta.getError()){
							ResultBean resultado = servicioTramiteTrafico.cambiarEstadoConEvolucion(tramiteTraficoVO.getNumExpediente(), EstadoTramiteTrafico.convertir(tramiteTraficoVO.getEstado()),
									EstadoTramiteTrafico.Pendiente_Consulta_EITV, true, TextoNotificacion.Cambio_Estado.getNombreEnum(), idUsuario);
							if (resultado == null || !resultado.getError()) {
								resultado = servicioCola.crearSolicitud(ProcesosEnum.CONSULTA_TARJETA_EITV.getNombreEnum(), xmlEnviar, gestorPropiedades.valorPropertie(ServicioConsultaEitv.NOMBRE_HOST_SOLICITUD_NODO_2),
									TipoTramiteTrafico.ConsultaEITV.toString(), numExp.toString(), idUsuario, null, idContrato);
							}
							if(resultado != null && !resultado.getError()){
								result.setError(false);
								result.addMensajeALista("Solicitud de consulta de tarjeta eITV correcta para el expediente: " + tramiteTraficoVO.getNumExpediente());
							}else{
								result.addMensajeALista(resultado.getListaMensajes().get(0));
								result.setError(true);
							}
						}else{
							for(String mensaje  : resultXmlConsulta.getListaMensajes()){
								result.addMensajeALista(mensaje);
							}
							result.setError(true);
						}
					}
				}
			}else{
				result = new ResultBean(true, "Para el trámite " + numExp.toString() + " no existen los datos necesarios para poder realizar la consulta de tarjeta eITV.");
			}
		} catch (OegamExcepcion e) {
			if(EnumError.error_00001.equals(e.getCodigoError())){
				result = new ResultBean(true, e.getMensajeError1());
			}else if(EnumError.error_00002.equals(e.getCodigoError())){
				result = new ResultBean(true, e.getMensajeError1());
			}else{
				LOG.error("Error a la hora de crear la consulta, error: ", e);
				result = new ResultBean(true, "Ha surgido un error a la hora de generar la consulta de la tarjeta eITV.");
			}
		}
		if(result.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	private ResultBean crearXmlConsultaEitv(TramiteTraficoVO tramiteTraficoVO, BigDecimal idUsuario, String nombreFichero) throws OegamExcepcion {
		ResultBean resultado = new ResultBean(false);
		try {
			resultado = generarXmlFirmado(tramiteTraficoVO,idUsuario);
			if(!resultado.getError()){
				gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.EITV,Utilidades.transformExpedienteFecha(tramiteTraficoVO.getNumExpediente()),
						nombreFichero, ConstantesGestorFicheros.EXTENSION_XML,((String)resultado.getAttachment("xmlFirmado")).getBytes());
			}
		} catch (Exception e) {
			Log.error("Ha sucedido un error a la hora de crear el XML de consulta eITV, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de crear el XML de consulta eITV.");
		}

		return resultado;
	}

	@Override
	public ResultBean consultaEitvPreMatriculados(VehiculoPrematriculadoDto vehiculoDto, BigDecimal idUsuario, BigDecimal idContrato) {
		ResultBean result = null;
		try {
			Fecha fecha = utilesFecha.getFechaActual();
			String sFecha = fecha.getDia() + fecha.getMes() + fecha.getAnio();
			String xmlEnviar = ServicioConsultaEitv.CONSULTA_TARJETA_PRE_MATRICULADOS_XML_ENVIAR + "_" + vehiculoDto.getBastidor() + "_" + sFecha;
			result = crearXmlConsultaEitvPreMatriculados(vehiculoDto, xmlEnviar);
			if(result == null){
				result = servicioCola.crearSolicitud(ProcesosEnum.CONSULTA_TARJETA_EITV.getNombreEnum(), xmlEnviar, gestorPropiedades.valorPropertie(ServicioConsultaEitv.NOMBRE_HOST_SOLICITUD_NODO_2),
					TipoTramiteTrafico.ConsultaEITV.toString(), vehiculoDto.getId().toString(), idUsuario, null, idContrato);
			}
		} catch (OegamExcepcion e) {
			LOG.error("Error a la hora de encolar la solicitud para la cosnulta de eITV, erorr: ", e);
			result = new ResultBean(true, "El trámite ya se encuentra en la cola para el proceso Consulta de Tarjeta eITV");
		}
		return result;
	}

	private ResultBean crearXmlConsultaEitvPreMatriculados(VehiculoPrematriculadoDto vehiculoDto, String nombreFichero) throws OegamExcepcion {
		ResultBean resultado = null;
		String xml = generarXmlFirmadoPreMatriculados(vehiculoDto);
		if(xml != null){
			gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.EITV_PREMATRICULADO, utilesFecha.getFechaHoraActualLEG(),
					nombreFichero, ConstantesGestorFicheros.EXTENSION_XML, xml.getBytes());
		}else{
			resultado = new ResultBean(true,"Ha sucedido un error a la hora de crear el XML de la consulta.");
		}
		return resultado;
	}

	private TramiteTraficoVO comprobarNumExpediente(BigDecimal numExp) {
		TramiteTraficoVO  tramiteTraficoVO = null;
		if(numExp != null){
			tramiteTraficoVO = servicioTramiteTrafico.getTramite(numExp, true);
			if(tramiteTraficoVO != null){
				if(TipoTramiteTrafico.Matriculacion.toString().equals(tramiteTraficoVO.getTipoTramite())){
					VehiculoVO vehiculoVO = tramiteTraficoVO.getVehiculo();
					if(vehiculoVO == null || vehiculoVO.getBastidor() == null){
						return null;
					}
				}else{
					return tramiteTraficoVO;
				}
			}else{
				return null;
			}
		}else{
			return null;
		}
		return tramiteTraficoVO;
	}

	@Override
	public RespuestaConsultaEitv generarConsultaEitv(ColaDto colaDto) {
		RespuestaConsultaEitv respuesta = null;
		try {
			if(colaDto.getXmlEnviar().contains(ServicioConsultaEitv.CONSULTA_TARJETA_PRE_MATRICULADOS_XML_ENVIAR)){
				respuesta = generarConsultaEitvPreMatriculados(colaDto);
			}else{
				String xml = recogerXmlEitv(colaDto.getXmlEnviar());
				if(xml != null){
					ConsultaTarjeta consultaTarjetaEitv;
					consultaTarjetaEitv = (ConsultaTarjeta)servicioWebServiceConsultaEitv.getBeanToXml(xml, ServicioWebServiceConsultaEitv.NAME_CONTEXT);
					if(consultaTarjetaEitv != null){
						TramiteTraficoVO tramiteTraficoVO = servicioTramiteTrafico.getTramite(colaDto.getIdTramite(), true);
						respuesta = servicioWebServiceConsultaEitv.llamadaWS(consultaTarjetaEitv, xml, tramiteTraficoVO, colaDto.getIdUsuario(), false);
					}else{
						respuesta = new RespuestaConsultaEitv();
						respuesta.setException(new Exception("Ha sucedido un error a la hora de crear el XML con los datos firmados"));
					}
				}else{
					respuesta = new RespuestaConsultaEitv(true,"No existe el XML de consulta de la tarjeta eITV, con nombre: " + colaDto.getXmlEnviar());
					respuesta.setException(new Exception("No existe el XML de consulta de la tarjeta eITV"));
				}
			}
		} catch (JAXBException e) {
			LOG.error(e);
			respuesta = new RespuestaConsultaEitv();
			respuesta.setException(new Exception(e));
		} catch (FileNotFoundException e) {
			LOG.error(e);
			respuesta = new RespuestaConsultaEitv();
			respuesta.setException(new Exception(e));
		}
		return respuesta;
	}

	private RespuestaConsultaEitv generarConsultaEitvPreMatriculados(ColaDto colaDto) throws FileNotFoundException, JAXBException {
		RespuestaConsultaEitv respuesta = null;
		String xml = recogerXmlEitvPreMatriculados(colaDto.getXmlEnviar());
		if(xml != null){
			ConsultaTarjeta consultaTarjetaEitv;
			consultaTarjetaEitv = (ConsultaTarjeta)servicioWebServiceConsultaEitv.getBeanToXml(xml, ServicioWebServiceConsultaEitv.NAME_CONTEXT);
			if(consultaTarjetaEitv != null){
				VehiculoPrematriculadoVO vehiculoPreVO = servicioVehiculosPrematriculados.buscarVehiculoPrematriculado(colaDto.getIdTramite().longValue());
				respuesta = servicioWebServiceConsultaEitv.llamadaWSPreMatriculados(consultaTarjetaEitv, xml, vehiculoPreVO, colaDto.getIdUsuario(), colaDto.getIdContrato());
			}
		}else{
			respuesta = new RespuestaConsultaEitv(true,"No existe el XML de consulta de la tarjeta eITV, con nombre: " + colaDto.getXmlEnviar());
			respuesta.setException(new Exception("No existe el XML de consulta de la tarjeta eITV"));
		}
		return respuesta;
	}

	private ResultBean generarXmlFirmado(TramiteTraficoVO tramiteTraficoVO, BigDecimal idContrato) {
		ResultBean resultado = new ResultBean(false);
		String consultaEitv = gestorPropiedades.valorPropertie("nuevas.url.sega.consultaEitv");
		if("SI".equals(consultaEitv)){
			Log.info("nuevas.url.sega.consultaEitv SI");
			org.gestoresmadrid.oegam2comun.sega.eitv.view.xml.ConsultaTarjeta consultaTarjetaSega = getConsultaTarjetaSega(tramiteTraficoVO);
			if(consultaTarjetaSega != null){
				resultado = servicioWebServiceConsultaEitvSega.firmarConsultaTarjetaEitvSega(consultaTarjetaSega, tramiteTraficoVO);
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("Ha sucedido un error a la hora de generar el XML para la consulta.");
			}
		}else{
			Log.info("nuevas.url.sega.consultaEitv NO");
			ConsultaTarjeta consultaTarjeta = getConsultaTarjeta(tramiteTraficoVO);
			if(consultaTarjeta != null){
				resultado = servicioWebServiceConsultaEitv.firmarConsultaTarjetaEitv(consultaTarjeta, tramiteTraficoVO);
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("Ha sucedido un error a la hora de generar el XML para la consulta.");
			}
		}
		return resultado;
	}

	private String generarXmlFirmadoPreMatriculados(VehiculoPrematriculadoDto vehiculoDto) {
		String xmlFirmado = null;
		if(vehiculoDto.getNumColegiado() != null){
			ColegiadoVO colegiadoVO = servicioColegiado.getColegiado(vehiculoDto.getNumColegiado());
			if(colegiadoVO == null) {
				return null;
			}
			ConsultaTarjeta consultaTarjeta = getConsultaTarjetaPreMatriculados(vehiculoDto, colegiadoVO);
			if(consultaTarjeta != null){
				xmlFirmado = servicioWebServiceConsultaEitv.firmarConsultaTarjetaEitvPreMatriculados(consultaTarjeta, vehiculoDto, colegiadoVO);
			}
		}
		return xmlFirmado;
	}

	private ConsultaTarjeta getConsultaTarjeta(TramiteTraficoVO tramiteTraficoVO){
		ConsultaTarjeta consultaTarjetaEitv = getObjectFactory().createConsultaTarjeta();
		if(tramiteTraficoVO.getContrato() != null){
			DatosFirmados datosFirmados = getObjectFactory().createDatosFirmados();
			ColegioVO colegioVO = tramiteTraficoVO.getContrato().getColegio();
			ColegiadoVO colegiadoVO = tramiteTraficoVO.getContrato().getColegiado();
			if(colegioVO == null || colegiadoVO == null){
				return null;
			}
			datosFirmados.setAGENCYDOI(colegiadoVO.getUsuario().getNif());
			datosFirmados.setAGENTDOI(colegiadoVO.getUsuario().getNif());
			datosFirmados.setVIN(tramiteTraficoVO.getVehiculo().getBastidor());
			datosFirmados.setNIVE(tramiteTraficoVO.getVehiculo().getNive() == null ? " " : tramiteTraficoVO.getVehiculo().getNive());
			datosFirmados.setTEXTOLEGAL(TipoTextoLegal.TEXTO_LEGAL);
			consultaTarjetaEitv.setDatosFirmados(datosFirmados);
			consultaTarjetaEitv.setFirmaGestor(null);
		}
		return consultaTarjetaEitv;
	}

	private org.gestoresmadrid.oegam2comun.sega.eitv.view.xml.ConsultaTarjeta getConsultaTarjetaSega(TramiteTraficoVO tramiteTraficoVO){
		org.gestoresmadrid.oegam2comun.sega.eitv.view.xml.ConsultaTarjeta consultaTarjetaEitvSega = getObjectFactorySega().createConsultaTarjeta();
		if(tramiteTraficoVO.getContrato() != null){
			org.gestoresmadrid.oegam2comun.sega.eitv.view.xml.ConsultaTarjeta.DatosFirmados datosFirmadosSega = getObjectFactorySega().createConsultaTarjetaDatosFirmados();
			ColegioVO colegioVO = tramiteTraficoVO.getContrato().getColegio();
			ColegiadoVO colegiadoVO = tramiteTraficoVO.getContrato().getColegiado();
			if(colegioVO == null || colegiadoVO == null){
				return null;
			}
			datosFirmadosSega.setAGENCYDOI(colegiadoVO.getUsuario().getNif());
			datosFirmadosSega.setAGENTDOI(colegiadoVO.getUsuario().getNif());
			datosFirmadosSega.setVIN(tramiteTraficoVO.getVehiculo().getBastidor());
			datosFirmadosSega.setNIVE(tramiteTraficoVO.getVehiculo().getNive() == null ? " " : tramiteTraficoVO.getVehiculo().getNive());
			datosFirmadosSega.setTEXTOLEGAL(TEXTO_LEGAL);
			consultaTarjetaEitvSega.setDatosFirmados(datosFirmadosSega);
			consultaTarjetaEitvSega.setFirmaGestor(null);
		}
		return consultaTarjetaEitvSega;
	}

	private ConsultaTarjeta getConsultaTarjetaPreMatriculados(VehiculoPrematriculadoDto vehiculoDto, ColegiadoVO colegiadoVO){
		ConsultaTarjeta consultaTarjetaEitv = getObjectFactory().createConsultaTarjeta();
		if(vehiculoDto.getNumColegiado() != null){
			DatosFirmados datosFirmados = null;
			datosFirmados = getObjectFactory().createDatosFirmados();
			datosFirmados.setAGENCYDOI(colegiadoVO.getUsuario().getNif());
			datosFirmados.setAGENTDOI(colegiadoVO.getUsuario().getNif());
			datosFirmados.setVIN(vehiculoDto.getBastidor());
			datosFirmados.setNIVE(vehiculoDto.getNive() == null ? "0" : vehiculoDto.getNive());
			datosFirmados.setTEXTOLEGAL(TipoTextoLegal.TEXTO_LEGAL);
			consultaTarjetaEitv.setDatosFirmados(datosFirmados);
			consultaTarjetaEitv.setFirmaGestor(null);
		}
		return consultaTarjetaEitv;
	}

	public String recogerXmlEitv(String xmlEnviar) throws FileNotFoundException {
		FileResultBean ficheroAenviar = null;
		String xml = null;
		try {
			String[] xmlEnv = xmlEnviar.split("_");
			Fecha fecha = Utilidades.transformExpedienteFecha(xmlEnv[1]);
			ficheroAenviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.EITV, fecha, xmlEnviar, ConstantesGestorFicheros.EXTENSION_XML);
			if(ficheroAenviar != null && ficheroAenviar.getFile() != null){
				xml =  servicioWebServiceConsultaEitv.xmlFiletoString(ficheroAenviar.getFile());
			}
		} catch (OegamExcepcion e) {
			LOG.error("Error al recuperar el documento, error: ", e);
		} catch (JAXBException e) {
			LOG.error("Error al convertir el documento, error: ", e);
		}
		return xml;
	}

	public String recogerXmlEitvPreMatriculados(String xmlEnviar) throws FileNotFoundException {
		FileResultBean ficheroAenviar = null;
		String xml = null;
		try {
			Fecha fecha = extraerFechaXmlEnviar(xmlEnviar);
			ficheroAenviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.EITV_PREMATRICULADO, fecha, xmlEnviar, ConstantesGestorFicheros.EXTENSION_XML);
			if(ficheroAenviar != null && ficheroAenviar.getFile() != null){
				xml =  servicioWebServiceConsultaEitv.xmlFiletoString(ficheroAenviar.getFile());
			}
		} catch (OegamExcepcion e) {
			LOG.error("Error al recuperar el documento, error: ", e);
		} catch (JAXBException e) {
			LOG.error("Error al convertir el documento, error: ", e);
		}
		return xml;
	}

	private Fecha extraerFechaXmlEnviar(String xmlEnviar) {
		String sFecha = xmlEnviar.substring(xmlEnviar.length() - 8, xmlEnviar.length());
		Fecha fecha = new Fecha();
		fecha.setDia(sFecha.substring(0,2));
		fecha.setMes(sFecha.substring(2,4));
		fecha.setAnio(sFecha.substring(4,8));
		return fecha;
	}

	@Override
	@Transactional
	public void cambiarEstadoTramiteProceso(BigDecimal numExpediente, BigDecimal estadoNuevo, BigDecimal idUsuario) {
		servicioTramiteTrafico.cambiarEstadoConEvolucion(numExpediente, EstadoTramiteTrafico.Pendiente_Consulta_EITV,
				EstadoTramiteTrafico.convertir(estadoNuevo), false, null, idUsuario);
	}

	public ServicioTramiteTrafico getServicioTramiteTrafico() {
		return servicioTramiteTrafico;
	}

	public void setServicioTramiteTrafico(
			ServicioTramiteTrafico servicioTramiteTrafico) {
		this.servicioTramiteTrafico = servicioTramiteTrafico;
	}

	public ServicioCola getServicioCola() {
		return servicioCola;
	}

	public void setServicioCola(ServicioCola servicioCola) {
		this.servicioCola = servicioCola;
	}

	public ServicioWebServiceConsultaEitv getServicioWebServiceConsultaEitv() {
		return servicioWebServiceConsultaEitv;
	}

	public void setServicioWebServiceConsultaEitv(
			ServicioWebServiceConsultaEitv servicioWebServiceConsultaEitv) {
		this.servicioWebServiceConsultaEitv = servicioWebServiceConsultaEitv;
	}

	public ServicioVehiculosPrematriculados getServicioVehiculosPrematriculados() {
		return servicioVehiculosPrematriculados;
	}

	public void setServicioVehiculosPrematriculados(
			ServicioVehiculosPrematriculados servicioVehiculosPrematriculados) {
		this.servicioVehiculosPrematriculados = servicioVehiculosPrematriculados;
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */

	public ObjectFactory getObjectFactory() {
		if(objectFactory == null){
			objectFactory = new ObjectFactory();
		}
		return objectFactory;
	}

	public org.gestoresmadrid.oegam2comun.sega.eitv.view.xml.ObjectFactory getObjectFactorySega() {
		if(objectFactorySega == null){
			objectFactorySega = new org.gestoresmadrid.oegam2comun.sega.eitv.view.xml.ObjectFactory();
		}
		return objectFactorySega;
	}

	public void setObjectFactorySega(org.gestoresmadrid.oegam2comun.sega.eitv.view.xml.ObjectFactory objectFactorySega) {
		this.objectFactorySega = objectFactorySega;
	}

	public void setObjectFactory(ObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}
}