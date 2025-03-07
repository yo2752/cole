package org.gestoresmadrid.oegam2comun.conductor.model.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;

import org.gestoresmadrid.core.arrendatarios.model.enumerados.EstadoCaycEnum;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.beans.ResultadoWSCaycBean;
import org.gestoresmadrid.oegam2comun.conductor.model.service.ServicioConductor;
import org.gestoresmadrid.oegam2comun.conductor.model.service.ServicioWebServiceConductorHabitual;
import org.gestoresmadrid.oegam2comun.conductor.view.beans.ResultConsultaConductorBean;
import org.gestoresmadrid.oegam2comun.conductor.view.dto.ConductorDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.CaycConductorHabitualSoapBindingStub;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.ConductorHabitualServicioWeb;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.ServiciosConductorHabitualLocator;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosConductorHabitual;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosDomicilio;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosVehiculo;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.respuesta.ResultadoComunicacion;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.ws.AltaArrendatarioHandler;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.ws.AltaConductorHandler;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.ws.CaycWSSecurityClientHandler;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.ws.ModiConductorHandler;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioWebServiceConductorHabitualImpl implements ServicioWebServiceConductorHabitual {

	@Autowired
	ServicioConductor servicioConductor;
	@Autowired
	GestorDocumentos gestorDocumentos;
	private ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServiceConductorHabitualImpl.class);

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	public ResultadoWSCaycBean procesarAltaConductor(ColaBean colabean) {
		ResultadoWSCaycBean resultadoWSCaycBean = new ResultadoWSCaycBean();
		try {
			if (colabean != null && colabean.getIdTramite() != null) {
				ResultConsultaConductorBean resultado = servicioConductor.getConductorDto(colabean.getIdTramite());
				ConductorDto conductorDto = resultado.getConductorDto();
				if (conductorDto != null) {
					String xml = recogerXml(colabean.getXmlEnviar(), conductorDto.getNumExpediente(),
							ConstantesGestorFicheros.XML_ALTA_CONDUCTOR_HABITUAL);
					resultadoWSCaycBean = llamadaWSAltaConductor(conductorDto, xml, colabean);
					//resultadoWSCaycBean = llamadaWSAltaConductor1(conductorDto, xml, colabean);
				} else {
					resultadoWSCaycBean.setError(Boolean.TRUE);
					resultadoWSCaycBean
							.setMensajeError("No existen datos en BBDD para el arrendatario de la cola con id: "
									+ colabean.getIdTramite());
				}
			} else {
				resultadoWSCaycBean.setError(Boolean.TRUE);
				resultadoWSCaycBean.setMensajeError("La cola o el id del alta del arrendatario estan vacios.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar la peticion de consulta de conductor habitual, error: ", e);
			resultadoWSCaycBean.setExcepcion(e);
		}
		return resultadoWSCaycBean;
		
	}

	@Override
	public ResultadoWSCaycBean procesarModificacionConductor(ColaBean colabean) {
		ResultadoWSCaycBean resultadoWSCaycBean = new ResultadoWSCaycBean();
		try {
			if (colabean != null && colabean.getIdTramite() != null) {
				ResultConsultaConductorBean resultado = servicioConductor.getConductorDto(colabean.getIdTramite());
				ConductorDto conductorDto = resultado.getConductorDto();
				if (conductorDto != null) {
					String xml = recogerXml(colabean.getXmlEnviar(), conductorDto.getNumExpediente(),
							ConstantesGestorFicheros.XML_MOD_CONDUCTOR_HABITUAL);
					resultadoWSCaycBean = llamadaWSModConductor(conductorDto, xml, colabean);
					//resultadoWSCaycBean = llamadaWSModConductor1(conductorDto, xml, colabean);
				} else {
					resultadoWSCaycBean.setError(Boolean.TRUE);
					resultadoWSCaycBean
							.setMensajeError("No existen datos en BBDD para el arrendatario de la cola con id: "
									+ colabean.getIdTramite());
				}
			} else {
				resultadoWSCaycBean.setError(Boolean.TRUE);
				resultadoWSCaycBean.setMensajeError("La cola o el id del alta del arrendatario estan vacios.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar la peticion de consulta de arrendatarios, error: ", e);
			resultadoWSCaycBean.setExcepcion(e);
		}
		return resultadoWSCaycBean;
	}

	private String recogerXml(String xmlEnviar, BigDecimal numExpediente, String subTipo) {

		FileResultBean ficheroAEnviar = null;
		StringBuffer xml = null;
		try {
			Fecha fecha = Utilidades.transformExpedienteFecha(numExpediente);
			ficheroAEnviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.CAYC, subTipo, fecha,
					xmlEnviar, ConstantesGestorFicheros.EXTENSION_XML);
			if (ficheroAEnviar != null && ficheroAEnviar.getFile() != null) {
				xml = new StringBuffer("<![CDATA[");
				BufferedReader br = new BufferedReader(
						new InputStreamReader(new FileInputStream(ficheroAEnviar.getFile()), "UTF-8"));
				int value = 0;
				// reads to the end of the stream
				while ((value = br.read()) != -1) {
					// converts int to character
					xml.append((char) value);
				}
				br.close();
				xml.append("]]>");
			}
		} catch (Exception e) {
			log.error("Error al recuperar el documento, error: ", e);
		} catch (OegamExcepcion e) {
			log.error("Error al recuperar el documento, error: ", e);
		}
		// return out.toString();
		return xml.toString().replaceAll("\n", "");
	}

	private ResultadoWSCaycBean llamadaWSAltaConductor(ConductorDto conductorDto, String xml, ColaBean colabean)
			throws ParseException {
		ResultadoWSCaycBean resultado = new ResultadoWSCaycBean();
		log.info("Entra en la llamadaWS de Alta Conductor");
		try {
			DatosConductorHabitual conductorhabitual = rellenarDatosconductorHabitual(conductorDto);
			DatosDomicilio domicilio = rellenarDatosDomicilio(conductorDto);
			DatosVehiculo vehiculo = rellenarDatosVehiculo(conductorDto);
			ResultadoComunicacion salida = getStubAltaConductor(conductorDto)
					.altaConductorHabitual(conductorhabitual, domicilio, vehiculo, xml);

			resultado = gestionarResultadoAlta(salida, conductorDto, colabean);
			log.info("Recibida respuesta del WebService de Alta conductor");
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de realizar la peticion al WS de conductor, error: ", e);
			resultado = new ResultadoWSCaycBean(Boolean.TRUE);
			resultado.setExcepcion(new Exception(e.getMessage()));
			resultado.setRespuestaWS(e.getMessage());
		}
		log.info("Recibida respuesta del WebService de Alta Arrendatario");
		return resultado;
	}

	
//	private ResultadoWSCaycBean llamadaWSAltaConductor1(ConductorDto conductorDto, String xml, ColaBean colabean)
//			throws ParseException {
//		ResultadoWSCaycBean resultado = new ResultadoWSCaycBean();
//		log.info("Entra en la llamadaWS de Alta Conductor");
//		try {
//			
//			
//			org.gestoresmadrid.conductorhabitual.genfromwsdl.DatosConductorHabitual conductorhabitual = rellenarDatosconductorHabitual1(conductorDto);
//			org.gestoresmadrid.conductorhabitual.genfromwsdl.DatosDomicilio domicilio = rellenarDatosDomicilio1(conductorDto);
//			org.gestoresmadrid.conductorhabitual.genfromwsdl.DatosVehiculo vehiculo = rellenarDatosVehiculo1(conductorDto);
//			
//			
//			
//			org.gestoresmadrid.conductorhabitual.ConductorHabitualClient conductorHabitualClient = 
//					  new org.gestoresmadrid.conductorhabitual.ConductorHabitualClient(conductorDto.getNumExpediente(),
//							                                                   conductorDto.getContrato().getColegiadoDto().getAlias(),"ALTA");
//			conductorHabitualClient.setDefaultUri("http://pr-apls-prep.dgt.es:8080/WS_CAYC/ServiciosConductorHabitual");
//			                                  
//			
//			org.gestoresmadrid.conductorhabitual.genfromwsdl.AltaConductorHabitual altaConductorHabitual=new org.gestoresmadrid.conductorhabitual.genfromwsdl.AltaConductorHabitual();
//			altaConductorHabitual.setSolicitud(xml);
//			altaConductorHabitual.setDatosConductorHabitual(conductorhabitual);
//			altaConductorHabitual.setDatosDomicilio(domicilio);
//			altaConductorHabitual.setDatosVehiculo(vehiculo);
//			
//			
//			
//			
//			org.gestoresmadrid.conductorhabitual.genfromwsdl.AltaConductorHabitualResponse altaConductorHabitualResponse
//            =conductorHabitualClient.alta(altaConductorHabitual);
//
//			org.gestoresmadrid.conductorhabitual.genfromwsdl.ResultadoComunicacion salida=altaConductorHabitualResponse.getResultadoComunicacion();
//			
//			resultado = gestionarResultadoAlta1(salida, conductorDto, colabean);
//			log.info("Recibida respuesta del WebService de Alta conductor");
//		} catch (Throwable e) {
//			log.error("Ha sucedido un error a la hora de realizar la peticion al WS de conductor, error: ", e);
//			resultado = new ResultadoWSCaycBean(Boolean.TRUE);
//			resultado.setExcepcion(new Exception(e.getMessage()));
//			resultado.setRespuestaWS(e.getMessage());
//		}
//		log.info("Recibida respuesta del WebService de Alta Arrendatario");
//		return resultado;
//	}
	private ResultadoWSCaycBean llamadaWSModConductor(ConductorDto conductorDto, String xml, ColaBean colabean)
			throws ParseException {
		ResultadoWSCaycBean resultado = new ResultadoWSCaycBean();
		log.info("Entra en la llamadaWS de Modi Conductor");
		try {
			DatosConductorHabitual conductorhabitual = rellenarDatosconductorHabitual(conductorDto);

			DatosVehiculo vehiculo = rellenarDatosVehiculo(conductorDto);
			ResultadoComunicacion salida = getStubModConductor(conductorDto)
					.modificacionConductorHabitual(conductorhabitual, vehiculo, xml);

			resultado = gestionarResultadoModi(salida, conductorDto, colabean);
			log.info("Recibida respuesta del WebService de Modi conductor");
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de realizar la peticion al WS de conductor, error: ", e);
			resultado = new ResultadoWSCaycBean(Boolean.TRUE);
			resultado.setExcepcion(new Exception(e.getMessage()));
			resultado.setRespuestaWS(e.getMessage());
		}
		log.info("Recibida respuesta del WebService de Modi Arrendatario");
		return resultado;
	}
	
//	private ResultadoWSCaycBean llamadaWSModConductor1(ConductorDto conductorDto, String xml, ColaBean colabean)
//			throws ParseException {
//		ResultadoWSCaycBean resultado = new ResultadoWSCaycBean();
//		log.info("Entra en la llamadaWS de Modi Conductor");
//		try {
//			org.gestoresmadrid.conductorhabitual.genfromwsdl.DatosConductorHabitual 
//			conductorhabitual = rellenarDatosconductorHabitual1(conductorDto);
//			
//			org.gestoresmadrid.conductorhabitual.genfromwsdl.DatosDomicilio
//			datosdomicilio=rellenarDatosDomicilio1(conductorDto);
//			
//			org.gestoresmadrid.conductorhabitual.genfromwsdl.DatosVehiculo 
//			vehiculo = rellenarDatosVehiculo1(conductorDto);
//			
//		
//            org.gestoresmadrid.conductorhabitual.genfromwsdl.ModificacionConductorHabitual modificacionConductorHabitual=
//               new org.gestoresmadrid.conductorhabitual.genfromwsdl.ModificacionConductorHabitual();
//			
//            modificacionConductorHabitual.setSolicitud(xml);
//            modificacionConductorHabitual.setDatosConductorHabitual(conductorhabitual);
//            //111modificacionConductorHabitual.setDatosDomicilio(datosdomicilio);
//            modificacionConductorHabitual.setDatosVehiculo(vehiculo);
//            
//           
//            org.gestoresmadrid.conductorhabitual.ConductorHabitualClient conductorHabitualClient=
//            		new org.gestoresmadrid.conductorhabitual.ConductorHabitualClient(conductorDto.getNumExpediente(),
//            				conductorDto.getContrato().getColegiadoDto().getAlias(),"MODIFICACION");
//            conductorHabitualClient.setDefaultUri("http://pr-apls-prep.dgt.es:8080/WS_CAYC/ServiciosConductorHabitual");
//			
//            org.gestoresmadrid.conductorhabitual.genfromwsdl.ModificacionConductorHabitualResponse modificacionConductorHabitualResponse
//            =conductorHabitualClient.modificacion(modificacionConductorHabitual);
//			
//			org.gestoresmadrid.conductorhabitual.genfromwsdl.ResultadoComunicacion salida=modificacionConductorHabitualResponse.getResultadoComunicacion();
//			
//			resultado = gestionarResultadoModi1(salida, conductorDto, colabean);
//			log.info("Recibida respuesta del WebService de Modi conductor");
//		} catch (Throwable e) {
//			log.error("Ha sucedido un error a la hora de realizar la peticion al WS de conductor, error: ", e);
//			resultado = new ResultadoWSCaycBean(Boolean.TRUE);
//			resultado.setExcepcion(new Exception(e.getMessage()));
//			resultado.setRespuestaWS(e.getMessage());
//		}
//		log.info("Recibida respuesta del WebService de Modi Arrendatario");
//		return resultado;
//	}

	private ResultadoWSCaycBean gestionarResultadoModi(ResultadoComunicacion resultadoComunicacion,
			ConductorDto conductorDto, ColaBean colabean) {
		ResultadoWSCaycBean resultado = new ResultadoWSCaycBean(Boolean.FALSE);
		try {
			if (resultadoComunicacion== null) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("No se ha obtenido resultado en la invocación al WS de alta.");
			} else if (resultadoComunicacion.getCodigoError() != null) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("El WS de alta ha devuelto el siguiente error: "
						+ resultadoComunicacion.getCodigoError() + ": " + resultadoComunicacion.getDescripcionError());
				resultado.setRespuestaWS("El WS de alta ha devuelto el siguiente error: "
						+ resultadoComunicacion.getCodigoError() + ": " + resultadoComunicacion.getDescripcionError());
			}
		} catch (Exception e) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("No se ha obtenido resultado en la invocación al WS de alta.");
		}
		return resultado;
	}

//	private ResultadoWSCaycBean gestionarResultadoModi1(org.gestoresmadrid.conductorhabitual.genfromwsdl.ResultadoComunicacion resultadoComunicacion,
//			ConductorDto conductorDto, ColaBean colabean) {
//		ResultadoWSCaycBean resultado = new ResultadoWSCaycBean(Boolean.FALSE);
//		try {
//			if (resultadoComunicacion== null) {
//				resultado.setError(Boolean.TRUE);
//				resultado.setMensajeError("No se ha obtenido resultado en la invocación al WS de alta.");
//			} else if (resultadoComunicacion.getCodigoError() != null) {
//				resultado.setError(Boolean.TRUE);
//				resultado.setMensajeError("El WS de alta ha devuelto el siguiente error: "
//						+ resultadoComunicacion.getCodigoError() + ": " + resultadoComunicacion.getDescripcionError());
//				resultado.setRespuestaWS("El WS de alta ha devuelto el siguiente error: "
//						+ resultadoComunicacion.getCodigoError() + ": " + resultadoComunicacion.getDescripcionError());
//			}
//		} catch (Exception e) {
//			resultado.setError(Boolean.TRUE);
//			resultado.setMensajeError("No se ha obtenido resultado en la invocación al WS de alta.");
//		}
//		return resultado;
//	}
	private DatosConductorHabitual rellenarDatosconductorHabitual(ConductorDto conductorDto) throws ParseException {
		DatosConductorHabitual datosPersonaCompleta = new DatosConductorHabitual();
		if (conductorDto.getPersona() != null) {
			// Datos opcionales
			datosPersonaCompleta.setDoi(conductorDto.getDoiVehiculo());

			if (conductorDto.getFechaIni() != null && !conductorDto.getFechaIni().isfechaNula()) {
				Calendar gc = toCalendar(conductorDto.getFechaIni().getDate());
				gc.set(new Integer(conductorDto.getFechaIni().getAnio()), new Integer(conductorDto.getFechaIni().getMes()) -1 ,1 + new Integer( conductorDto.getFechaIni().getDia()));
				datosPersonaCompleta.setFechaIni(gc);

			}
			if (conductorDto.getFechaFin() != null && !conductorDto.getFechaFin().isfechaNula()) {
				Calendar gc = toCalendar(conductorDto.getFechaFin().getDate());
				gc.set(new Integer(conductorDto.getFechaFin().getAnio()), new Integer(conductorDto.getFechaFin().getMes())-1,1 + new Integer( conductorDto.getFechaFin().getDia()));
				datosPersonaCompleta.setFechaFin(gc);

			}
			if (conductorDto.getPersona().getFechaNacimiento() != null
					&& !conductorDto.getPersona().getFechaNacimiento().isfechaNula()) {
				datosPersonaCompleta.setFechaNacimiento(conductorDto.getPersona().getFechaNacimiento().getDate());

			}
			
			datosPersonaCompleta.setExisteConsentimiento(conductorDto.getConsentimiento());
		}
		return datosPersonaCompleta;
	}

//	private org.gestoresmadrid.conductorhabitual.genfromwsdl.DatosConductorHabitual rellenarDatosconductorHabitual1(ConductorDto conductorDto) throws ParseException, DatatypeConfigurationException {
//		org.gestoresmadrid.conductorhabitual.genfromwsdl.DatosConductorHabitual datosPersonaCompleta = new org.gestoresmadrid.conductorhabitual.genfromwsdl.DatosConductorHabitual();
//		if (conductorDto.getPersona() != null) {
//			// Datos opcionales
//			datosPersonaCompleta.setDoi(conductorDto.getDoiVehiculo());
//
//			if (conductorDto.getFechaIni() != null && !conductorDto.getFechaIni().isfechaNula()) {
//				//Calendar gc = toCalendar(conductorDto.getFechaIni().getDate());
//				//gc.set(new Integer(conductorDto.getFechaIni().getAnio()), new Integer(conductorDto.getFechaIni().getMes()) -1 ,1 + new Integer( conductorDto.getFechaIni().getDia()));
//				//datosPersonaCompleta.setFechaIni(gc);
//
//				
//				GregorianCalendar calendar = new GregorianCalendar();				 	
//				calendar.setTime(conductorDto.getFechaIni().getDate());
//				datosPersonaCompleta.setFechaIni(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
//			
//			}
//			if (conductorDto.getFechaFin() != null && !conductorDto.getFechaFin().isfechaNula()) {
//				//Calendar gc = toCalendar(conductorDto.getFechaFin().getDate());
//				//gc.set(new Integer(conductorDto.getFechaFin().getAnio()), new Integer(conductorDto.getFechaFin().getMes())-1,1 + new Integer( conductorDto.getFechaFin().getDia()));
//				//datosPersonaCompleta.setFechaFin(gc);
//				
//				GregorianCalendar calendar = new GregorianCalendar();				 	
//				calendar.setTime(conductorDto.getFechaFin().getDate());
//				datosPersonaCompleta.setFechaIni(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
//		
//
//			}
//			if (conductorDto.getPersona().getFechaNacimiento() != null
//					&& !conductorDto.getPersona().getFechaNacimiento().isfechaNula()) {
//				
//				//datosPersonaCompleta.setFechaNacimiento(conductorDto.getPersona().getFechaNacimiento().getDate());
//
//				GregorianCalendar calendar = new GregorianCalendar();				 	
//				calendar.setTime(conductorDto.getPersona().getFechaNacimiento().getDate());
//				datosPersonaCompleta.setFechaIni(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
//		
//				
//				
//			}
//			
//			datosPersonaCompleta.setExisteConsentimiento(conductorDto.getConsentimiento());
//		}
//		return datosPersonaCompleta;
//	}

	public static Calendar toCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	private DatosVehiculo rellenarDatosVehiculo(ConductorDto conductorDto) {
		DatosVehiculo datosVehiculo = new DatosVehiculo();
		// Datos opcionales
		datosVehiculo.setDoiTitular(conductorDto.getDoiVehiculo());
		// Datos Obligatorios
		int longitud = conductorDto.getBastidor().length();
		datosVehiculo.setMatricula(conductorDto.getMatricula());
		datosVehiculo.setBastidor(conductorDto.getBastidor().substring(longitud - 6, longitud));
		return datosVehiculo;
	}

//	private org.gestoresmadrid.conductorhabitual.genfromwsdl.DatosVehiculo rellenarDatosVehiculo1(ConductorDto conductorDto) {
//		org.gestoresmadrid.conductorhabitual.genfromwsdl.DatosVehiculo datosVehiculo = new org.gestoresmadrid.conductorhabitual.genfromwsdl.DatosVehiculo();
//		// Datos opcionales
//		datosVehiculo.setDoiTitular(conductorDto.getDoiVehiculo());
//		// Datos Obligatorios
//		int longitud = conductorDto.getBastidor().length();
//		datosVehiculo.setMatricula(conductorDto.getMatricula());
//		datosVehiculo.setBastidor(conductorDto.getBastidor().substring(longitud - 6, longitud));
//		return datosVehiculo;
//	}
	private DatosDomicilio rellenarDatosDomicilio(ConductorDto conductorDto) {
		DatosDomicilio datosDomicilio = new DatosDomicilio();
		if (conductorDto.getPersona() != null && conductorDto.getPersona().getDireccionDto() != null) {
			datosDomicilio.setBloque(conductorDto.getPersona().getDireccionDto().getBloque() == null ? ""
					: conductorDto.getPersona().getDireccionDto().getBloque());
			datosDomicilio.setCp(conductorDto.getPersona().getDireccionDto().getCodPostal() == null ? ""
					: conductorDto.getPersona().getDireccionDto().getCodPostal());
			datosDomicilio.setEscalera(conductorDto.getPersona().getDireccionDto().getEscalera() == null ? ""
					: conductorDto.getPersona().getDireccionDto().getEscalera());
			datosDomicilio.setHm(conductorDto.getPersona().getDireccionDto().getHm() == null ? ""
					: conductorDto.getPersona().getDireccionDto().getHm().toString());
			datosDomicilio.setKm(conductorDto.getPersona().getDireccionDto().getKm() == null ? ""
					: conductorDto.getPersona().getDireccionDto().getKm().toString());
			datosDomicilio.setLocalidad(conductorDto.getPersona().getDireccionDto().getPueblo() == null ? ""
					: conductorDto.getPersona().getDireccionDto().getPueblo());
			datosDomicilio.setNumero(conductorDto.getPersona().getDireccionDto().getNumero() == null ? ""
					: conductorDto.getPersona().getDireccionDto().getNumero());
			datosDomicilio.setPlanta(conductorDto.getPersona().getDireccionDto().getPlanta() == null ? ""
					: conductorDto.getPersona().getDireccionDto().getPlanta());
			datosDomicilio.setPortal(conductorDto.getPersona().getDireccionDto().getPortal() == null ? ""
					: conductorDto.getPersona().getDireccionDto().getPortal());
			datosDomicilio.setPuerta(conductorDto.getPersona().getDireccionDto().getPuerta() == null ? ""
					: conductorDto.getPersona().getDireccionDto().getPuerta());
			datosDomicilio.setTipoVia(conductorDto.getPersona().getDireccionDto().getIdTipoVia() == null ? ""
					: conductorDto.getPersona().getDireccionDto().getIdTipoVia());
			datosDomicilio.setVia(conductorDto.getPersona().getDireccionDto().getNombreVia() == null ? ""
					: conductorDto.getPersona().getDireccionDto().getNombreVia());
			datosDomicilio.setProvincia(conductorDto.getPersona().getDireccionDto().getIdProvincia() == null ? "" : 
				conductorDto.getPersona().getDireccionDto().getIdProvincia());
			datosDomicilio.setMunicipio(conductorDto.getPersona().getDireccionDto().getIdMunicipio() == null ? "" : 
				conductorDto.getPersona().getDireccionDto().getIdMunicipio());
		}
		return datosDomicilio;
	}

//	private org.gestoresmadrid.conductorhabitual.genfromwsdl.DatosDomicilio rellenarDatosDomicilio1(ConductorDto conductorDto) {
//		org.gestoresmadrid.conductorhabitual.genfromwsdl.DatosDomicilio datosDomicilio = new org.gestoresmadrid.conductorhabitual.genfromwsdl.DatosDomicilio();
//		if (conductorDto.getPersona() != null && conductorDto.getPersona().getDireccionDto() != null) {
//			datosDomicilio.setBloque(conductorDto.getPersona().getDireccionDto().getBloque() == null ? ""
//					: conductorDto.getPersona().getDireccionDto().getBloque());
//			datosDomicilio.setCp(conductorDto.getPersona().getDireccionDto().getCodPostal() == null ? ""
//					: conductorDto.getPersona().getDireccionDto().getCodPostal());
//			datosDomicilio.setEscalera(conductorDto.getPersona().getDireccionDto().getEscalera() == null ? ""
//					: conductorDto.getPersona().getDireccionDto().getEscalera());
//			datosDomicilio.setHm(conductorDto.getPersona().getDireccionDto().getHm() == null ? ""
//					: conductorDto.getPersona().getDireccionDto().getHm().toString());
//			datosDomicilio.setKm(conductorDto.getPersona().getDireccionDto().getKm() == null ? ""
//					: conductorDto.getPersona().getDireccionDto().getKm().toString());
//			datosDomicilio.setLocalidad(conductorDto.getPersona().getDireccionDto().getPueblo() == null ? ""
//					: conductorDto.getPersona().getDireccionDto().getPueblo());
//			datosDomicilio.setNumero(conductorDto.getPersona().getDireccionDto().getNumero() == null ? ""
//					: conductorDto.getPersona().getDireccionDto().getNumero());
//			datosDomicilio.setPlanta(conductorDto.getPersona().getDireccionDto().getPlanta() == null ? ""
//					: conductorDto.getPersona().getDireccionDto().getPlanta());
//			datosDomicilio.setPortal(conductorDto.getPersona().getDireccionDto().getPortal() == null ? ""
//					: conductorDto.getPersona().getDireccionDto().getPortal());
//			datosDomicilio.setPuerta(conductorDto.getPersona().getDireccionDto().getPuerta() == null ? ""
//					: conductorDto.getPersona().getDireccionDto().getPuerta());
//			datosDomicilio.setTipoVia(conductorDto.getPersona().getDireccionDto().getIdTipoVia() == null ? ""
//					: conductorDto.getPersona().getDireccionDto().getIdTipoVia());
//			datosDomicilio.setVia(conductorDto.getPersona().getDireccionDto().getNombreVia() == null ? ""
//					: conductorDto.getPersona().getDireccionDto().getNombreVia());
//			datosDomicilio.setProvincia(conductorDto.getPersona().getDireccionDto().getIdProvincia() == null ? "" : 
//				conductorDto.getPersona().getDireccionDto().getIdProvincia());
//			datosDomicilio.setMunicipio(conductorDto.getPersona().getDireccionDto().getIdMunicipio() == null ? "" : 
//				conductorDto.getPersona().getDireccionDto().getIdMunicipio());
//		}
//		return datosDomicilio;
//	}

	private ConductorHabitualServicioWeb getStubAltaConductor(ConductorDto conductorDto)
			throws MalformedURLException, ServiceException {
		URL miURL = new URL(gestorPropiedades.valorPropertie(URL_WS));
		String timeOut = gestorPropiedades.valorPropertie(TIMEOUT);
		CaycConductorHabitualSoapBindingStub stub = null;
		ServiciosConductorHabitualLocator arrLocator = new ServiciosConductorHabitualLocator();
		ConductorHabitualServicioWeb conductorServicioWeb = null;
		javax.xml.namespace.QName portName = new javax.xml.namespace.QName(miURL.toString(),
				arrLocator.getCaycConductorHabitualSoapWSDDServiceName());
		@SuppressWarnings("unchecked")
		List<HandlerInfo> list = arrLocator.getHandlerRegistry().getHandlerChain(portName);
		// Se configura el Handler de seguridad
		HandlerInfo signerHandlerInfoSegu = new HandlerInfo();
		Map<String, Object> configs = new HashMap<String, Object>();
		configs.put(CaycWSSecurityClientHandler.ALIAS_KEY, conductorDto.getContrato().getColegiadoDto().getAlias());
		signerHandlerInfoSegu.setHandlerConfig(configs);
		signerHandlerInfoSegu.setHandlerClass(CaycWSSecurityClientHandler.class);
		list.add(signerHandlerInfoSegu);

		// Se configura el Handler de fichero
		HandlerInfo signerHandlerInfo = new HandlerInfo();
		Map<String, Object> config = new HashMap<String, Object>();
		config.put(AltaArrendatarioHandler.PROPERTY_KEY_ID, conductorDto.getNumExpediente());
		signerHandlerInfo.setHandlerConfig(config);
		signerHandlerInfo.setHandlerClass(AltaConductorHandler.class);
		list.add(signerHandlerInfo);
		conductorServicioWeb = arrLocator.getCaycConductorHabitualSoap(miURL);
		stub = (CaycConductorHabitualSoapBindingStub) conductorServicioWeb;
		stub.setTimeout(Integer.parseInt(timeOut));
		return stub;
	}

	private ResultadoWSCaycBean gestionarResultadoAlta(ResultadoComunicacion resultadoComunicacion,
			ConductorDto conductorDto, ColaBean colabean) {
		ResultadoWSCaycBean resultado = new ResultadoWSCaycBean(Boolean.FALSE);
		try {
			if (resultadoComunicacion == null) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("No se ha obtenido resultado en la invocación al WS de alta.");
			} else if (resultadoComunicacion.getCodigoError() != null) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("El WS de alta ha devuelto el siguiente error: "
						+ resultadoComunicacion.getCodigoError() + ": " + resultadoComunicacion.getDescripcionError());
				resultado.setRespuestaWS("El WS de alta ha devuelto el siguiente error: "
						+ resultadoComunicacion.getCodigoError() + ": " + resultadoComunicacion.getDescripcionError());
			}
		} catch (Exception e) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("No se ha obtenido resultado en la invocación al WS de alta.");
		}
		return resultado;
	}

//	private ResultadoWSCaycBean gestionarResultadoAlta1(org.gestoresmadrid.conductorhabitual.genfromwsdl.ResultadoComunicacion resultadoComunicacion,
//			ConductorDto conductorDto, ColaBean colabean) {
//		ResultadoWSCaycBean resultado = new ResultadoWSCaycBean(Boolean.FALSE);
//		try {
//			if (resultadoComunicacion == null) {
//				resultado.setError(Boolean.TRUE);
//				resultado.setMensajeError("No se ha obtenido resultado en la invocación al WS de alta.");
//			} else if (resultadoComunicacion.getCodigoError() != null) {
//				resultado.setError(Boolean.TRUE);
//				resultado.setMensajeError("El WS de alta ha devuelto el siguiente error: "
//						+ resultadoComunicacion.getCodigoError() + ": " + resultadoComunicacion.getDescripcionError());
//				resultado.setRespuestaWS("El WS de alta ha devuelto el siguiente error: "
//						+ resultadoComunicacion.getCodigoError() + ": " + resultadoComunicacion.getDescripcionError());
//			}
//		} catch (Exception e) {
//			resultado.setError(Boolean.TRUE);
//			resultado.setMensajeError("No se ha obtenido resultado en la invocación al WS de alta.");
//		}
//		return resultado;
//	}

	
	@Override
	public void cambiarEstadoConsulta(BigDecimal idConsultaConductor, BigDecimal idUsuario, EstadoCaycEnum estado,
			String respuesta, String numRegistro) {
		try {
			servicioConductor.cambiarEstadoProceso(idConsultaConductor, idUsuario,
					new BigDecimal(estado.getValorEnum()), respuesta, numRegistro);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado de la consulta de vehiculo atex5: "
					+ idConsultaConductor + " al estado: " + estado.getNombreEnum() + ", error: ", e);
		}
	}

	@SuppressWarnings("unchecked")
	private ConductorHabitualServicioWeb getStubModConductor(ConductorDto conductorDto)
			throws MalformedURLException, ServiceException {
		URL miURL = new URL(gestorPropiedades.valorPropertie(URL_WS));
		String timeOut = gestorPropiedades.valorPropertie(TIMEOUT);
		CaycConductorHabitualSoapBindingStub stub = null;
		ServiciosConductorHabitualLocator arrLocator = new ServiciosConductorHabitualLocator();
		ConductorHabitualServicioWeb conductorServicioWeb = null;
		javax.xml.namespace.QName portName = new javax.xml.namespace.QName(miURL.toString(),
				arrLocator.getCaycConductorHabitualSoapWSDDServiceName());

		List<HandlerInfo> list = arrLocator.getHandlerRegistry().getHandlerChain(portName);

		// Se configura el Handler de seguridad
		HandlerInfo signerHandlerInfoSegu = new HandlerInfo();
		Map<String, Object> configs = new HashMap<String, Object>();
		configs.put(CaycWSSecurityClientHandler.ALIAS_KEY, conductorDto.getContrato().getColegiadoDto().getAlias());
		signerHandlerInfoSegu.setHandlerConfig(configs);
		signerHandlerInfoSegu.setHandlerClass(CaycWSSecurityClientHandler.class);
		list.add(signerHandlerInfoSegu);

		// Se configura el Handler de fichero
		HandlerInfo signerHandlerInfo = new HandlerInfo();
		Map<String, Object> config = new HashMap<String, Object>();
		config.put(AltaArrendatarioHandler.PROPERTY_KEY_ID, conductorDto.getNumExpediente());
		signerHandlerInfo.setHandlerConfig(config);
		signerHandlerInfo.setHandlerClass(ModiConductorHandler.class);
		list.add(signerHandlerInfo);

		conductorServicioWeb = arrLocator.getCaycConductorHabitualSoap(miURL);
		stub = (CaycConductorHabitualSoapBindingStub) conductorServicioWeb;
		stub.setTimeout(Integer.parseInt(timeOut));
		return stub;
	}
}
