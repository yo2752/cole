package org.gestoresmadrid.oegam2comun.arrendatarios.model.service.impl;

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
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.oegam2comun.arrendatarios.model.service.ServicioArrendatario;
import org.gestoresmadrid.oegam2comun.arrendatarios.model.service.ServicioWebServiceArrendatario;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.beans.ResultConsultaArrendatarioBean;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.beans.ResultadoWSCaycBean;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.dto.ArrendatarioDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
//import org.gestoresmadrid.oegam2comun.modeloCAYC.AA.XML.FORMATOAltaArrendamiento.AltaArrendamiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.ArrendamientoServicioWeb;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.CaycArrendamientoSoapBindingStub;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.ServiciosArrendamientoLocator;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosArrendamiento;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosDomicilio;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosPersonaCompleta;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion.DatosVehiculo;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.respuesta.ResultadoComunicacion;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.ws.AltaArrendatarioHandler;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.ws.CaycWSSecurityClientHandler;
import es.trafico.www.servicios.vehiculos.comunicaciones.webservices.ws.ModiArrendatarioHandler;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioWebServiceArrendatarioImpl implements ServicioWebServiceArrendatario {

	private static final long serialVersionUID = 2337289872143186734L;
	private ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServiceArrendatarioImpl.class);
	@Autowired
	ServicioArrendatario servicioArrendatario;
	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	public ResultadoWSCaycBean procesarAltaArrendatario(ColaBean colabean) {
		ResultadoWSCaycBean resultadoWSCaycBean = new ResultadoWSCaycBean();
		try {
			if (colabean != null && colabean.getIdTramite() != null) {
				ResultConsultaArrendatarioBean resultado = servicioArrendatario.getArrendatarioDto(colabean.getIdTramite());
				if(!resultado.getError()){
					ArrendatarioDto arrendatarioDto = resultado.getArrendatarioDto();
					if (arrendatarioDto != null) {
						String xml = recogerXml(colabean.getXmlEnviar(), arrendatarioDto.getNumExpediente(),
								ConstantesGestorFicheros.XML_ALTA_ARRENDATARIO);
						//resultadoWSCaycBean = llamadaWSAltaArrendatario1(arrendatarioDto, xml, colabean);
						resultadoWSCaycBean = llamadaWSAltaArrendatario(arrendatarioDto, xml, colabean);
					} else {
						resultadoWSCaycBean.setError(Boolean.TRUE);
						resultadoWSCaycBean
						.setMensajeError("No existen datos en BBDD para el arrendatario de la cola con id: "
								+ colabean.getIdTramite());
					}
					
				} else {
					resultadoWSCaycBean.setError(Boolean.TRUE);
					resultadoWSCaycBean.setMensajeError(resultado.getMensaje());
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

	public void cambiarEstadoConsulta(BigDecimal idConsultaConductor, BigDecimal idUsuario, EstadoCaycEnum estado,
			String respuesta, String numRegistro) {
		try {
			servicioArrendatario.cambiarEstadoProceso(idConsultaConductor, idUsuario,
					new BigDecimal(estado.getValorEnum()), respuesta, numRegistro);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado de la consulta de arrendatario: "
					+ idConsultaConductor + " al estado: " + estado.getNombreEnum() + ", error: ", e);
		}
	}

	private String recogerXml(String xmlEnviar, BigDecimal numExpediente, String subtipo) {
		FileResultBean ficheroAEnviar = null;
		// String xml = null;
		StringBuffer xml = null;
		try {
			Fecha fecha = Utilidades.transformExpedienteFecha(numExpediente);
			ficheroAEnviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.CAYC, subtipo, fecha,
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
		} catch (Exception | OegamExcepcion e) {
			System.out.println("Error al recuperar el documento, error: " + e.getMessage());
		}
		return xml.toString();
	}

	private ResultadoWSCaycBean llamadaWSAltaArrendatario(ArrendatarioDto arrendatarioDto, String xml,
			ColaBean colabean) {
		ResultadoWSCaycBean resultado = new ResultadoWSCaycBean();
		log.info("Entra en la llamadaWS de Alta Arrendatario");
		try {
			DatosArrendamiento datosArrendamiento = rellenarDatosArrendamiento(arrendatarioDto);
			DatosVehiculo datosVehiculo = rellenarDatosVehiculo(arrendatarioDto);
			DatosDomicilio domicilio = rellenarDatosDomicilio(arrendatarioDto);
			DatosPersonaCompleta persona = rellenarDatosPersonaCompleta(arrendatarioDto);
			ResultadoComunicacion salida = getStubAltaArrendamiento(arrendatarioDto).altaArrendamiento(datosArrendamiento, persona, domicilio, datosVehiculo, xml);
			resultado = gestionarResultadoAlta(salida, arrendatarioDto, colabean);
			log.info("Recibida respuesta del WebService de modificacion Arrendatario");
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de realizar la peticion al WS de arrendatario, error: ", e);
			resultado.setExcepcion(new Exception(e.getMessage()));
			resultado.setRespuestaWS(e.getMessage());
		}
		return resultado;
	}

	private ResultadoWSCaycBean llamadaWSAltaArrendatario1(ArrendatarioDto arrendatarioDto, String xml,
			ColaBean colabean) {
		ResultadoWSCaycBean resultado = new ResultadoWSCaycBean();
		log.info("Entra en la llamadaWS de Alta Arrendatario");
//		try {
//			
//			org.gestoresmadrid.arrendamiento.genfromwsdl.DatosArrendamiento datosArrendamiento = rellenarDatosArrendamiento1(arrendatarioDto);
//			org.gestoresmadrid.arrendamiento.genfromwsdl.DatosVehiculo datosVehiculo = rellenarDatosVehiculo1(arrendatarioDto);
//			org.gestoresmadrid.arrendamiento.genfromwsdl.DatosDomicilio domicilio = rellenarDatosDomicilio1(arrendatarioDto);
//			org.gestoresmadrid.arrendamiento.genfromwsdl.DatosPersonaCompleta persona = rellenarDatosPersonaCompleta1(arrendatarioDto);
//			
//			
//			org.gestoresmadrid.arrendamiento.ArrendamientoClient arrendamientoClient = 
//					  new org.gestoresmadrid.arrendamiento.ArrendamientoClient(arrendatarioDto.getNumExpediente(),
//							                                                   arrendatarioDto.getContrato().getColegiadoDto().getAlias(),"ALTA");
//			arrendamientoClient.setDefaultUri("http://pr-apls-prep.dgt.es:8080/WS_CAYC/ServiciosArrendamiento");
//			                                  
//			
//			org.gestoresmadrid.arrendamiento.genfromwsdl.AltaArrendamiento altaArrendamiento=new org.gestoresmadrid.arrendamiento.genfromwsdl.AltaArrendamiento();
//			
//			altaArrendamiento.setSolicitud(xml);
//			altaArrendamiento.setDatosArrendamiento(datosArrendamiento);
//			altaArrendamiento.setDatosDomicilio(domicilio);
//			altaArrendamiento.setDatosPersona(persona);
//			altaArrendamiento.setDatosVehiculo(datosVehiculo);
//			
//			org.gestoresmadrid.arrendamiento.genfromwsdl.AltaArrendamientoResponse altaArrendamientoResponse
//            =arrendamientoClient.alta(altaArrendamiento);
//			
//			org.gestoresmadrid.arrendamiento.genfromwsdl.ResultadoComunicacion salida=altaArrendamientoResponse.getResultadoComunicacion();
//			
//			
//			resultado = gestionarResultadoAlta1(salida, arrendatarioDto, colabean);
//			log.info("Recibida respuesta del WebService de Alta de Arrendatario");
//		} catch (Throwable e) {
//			log.error("Ha sucedido un error a la hora de realizar la peticion al WS de arrendatario, error: ", e);
//			resultado.setExcepcion(new Exception(e.getMessage()));
//			resultado.setRespuestaWS(e.getMessage());
//		}
		return resultado;
	}
	
//	private ResultadoWSCaycBean gestionarResultadoAlta1(org.gestoresmadrid.arrendamiento.genfromwsdl.ResultadoComunicacion resultadoComunicacion,
//			ArrendatarioDto arrendatarioDto, ColaBean colabean) {
//		ResultadoWSCaycBean resultado = new ResultadoWSCaycBean(Boolean.FALSE);
//		try {
//			if (resultadoComunicacion==null) {
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

	private ResultadoWSCaycBean gestionarResultadoAlta(ResultadoComunicacion resultadoComunicacion,
			ArrendatarioDto arrendatarioDto, ColaBean colabean) {
		ResultadoWSCaycBean resultado = new ResultadoWSCaycBean(Boolean.FALSE);
		try {
			if (resultadoComunicacion==null) {
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

	private DatosVehiculo rellenarDatosVehiculo(ArrendatarioDto arrendatarioDto) {
		DatosVehiculo datosVehiculo = new DatosVehiculo();
		// Datos opcionales
		datosVehiculo.setDoiTitular(arrendatarioDto.getDoiVehiculo());
		// Datos Obligatorios
		int longitud = arrendatarioDto.getBastidor().length();
		datosVehiculo.setMatricula(arrendatarioDto.getMatricula());
		datosVehiculo.setBastidor(arrendatarioDto.getBastidor().substring(longitud - 6, longitud));
		return datosVehiculo;
	}

	private DatosDomicilio rellenarDatosDomicilio(ArrendatarioDto arrendatarioDto) {
		DatosDomicilio datosDomicilio = new DatosDomicilio();
		if (arrendatarioDto.getPersona() != null && arrendatarioDto.getPersona().getDireccionDto() != null) {
			datosDomicilio.setBloque(arrendatarioDto.getPersona().getDireccionDto().getBloque() == null ? ""
					: arrendatarioDto.getPersona().getDireccionDto().getBloque());
			datosDomicilio.setCp(arrendatarioDto.getPersona().getDireccionDto().getCodPostal() == null ? ""
					: arrendatarioDto.getPersona().getDireccionDto().getCodPostal());
			datosDomicilio.setEscalera(arrendatarioDto.getPersona().getDireccionDto().getEscalera() == null ? ""
					: arrendatarioDto.getPersona().getDireccionDto().getEscalera());
			datosDomicilio.setHm(arrendatarioDto.getPersona().getDireccionDto().getHm() == null ? ""
					: arrendatarioDto.getPersona().getDireccionDto().getHm().toString());
			datosDomicilio.setKm(arrendatarioDto.getPersona().getDireccionDto().getKm() == null ? ""
					: arrendatarioDto.getPersona().getDireccionDto().getKm().toString());
			datosDomicilio.setLocalidad(arrendatarioDto.getPersona().getDireccionDto().getPueblo() == null ? ""
					: arrendatarioDto.getPersona().getDireccionDto().getPueblo());
			datosDomicilio.setNumero(arrendatarioDto.getPersona().getDireccionDto().getNumero() == null ? ""
					: arrendatarioDto.getPersona().getDireccionDto().getNumero());
			datosDomicilio.setPlanta(arrendatarioDto.getPersona().getDireccionDto().getPlanta() == null ? ""
					: arrendatarioDto.getPersona().getDireccionDto().getPlanta());
			datosDomicilio.setPortal(arrendatarioDto.getPersona().getDireccionDto().getPortal() == null ? ""
					: arrendatarioDto.getPersona().getDireccionDto().getPortal());
			datosDomicilio.setPuerta(arrendatarioDto.getPersona().getDireccionDto().getPuerta() == null ? ""
					: arrendatarioDto.getPersona().getDireccionDto().getPuerta());
			datosDomicilio.setTipoVia(arrendatarioDto.getPersona().getDireccionDto().getIdTipoVia() == null ? ""
					: arrendatarioDto.getPersona().getDireccionDto().getIdTipoVia());
			datosDomicilio.setVia(arrendatarioDto.getPersona().getDireccionDto().getNombreVia() == null ? ""
					: arrendatarioDto.getPersona().getDireccionDto().getNombreVia());
			datosDomicilio.setProvincia(arrendatarioDto.getPersona().getDireccionDto().getIdProvincia() == null ? "" : 
				arrendatarioDto.getPersona().getDireccionDto().getIdProvincia());
			datosDomicilio.setMunicipio(arrendatarioDto.getPersona().getDireccionDto().getIdMunicipio() == null ? "" : 
				arrendatarioDto.getPersona().getDireccionDto().getIdMunicipio());
		}
		return datosDomicilio;
	}

	private DatosPersonaCompleta rellenarDatosPersonaCompleta(ArrendatarioDto arrendatarioDto) throws ParseException {
		DatosPersonaCompleta datosPersonaCompleta = new DatosPersonaCompleta();
		if (arrendatarioDto.getPersona() != null) {
			// Datos opcionales
			datosPersonaCompleta.setDoi(arrendatarioDto.getPersona().getNif());
			if(arrendatarioDto.getPersona().getFechaNacimiento() != null && !arrendatarioDto.getPersona().getFechaNacimiento().isfechaNula()){
				datosPersonaCompleta.setFechaNacimiento(arrendatarioDto.getPersona().getFechaNacimiento().getFecha());
			}
			datosPersonaCompleta.setNombre(arrendatarioDto.getPersona().getNombre());
			if(TipoPersona.Fisica.getValorEnum().equals(arrendatarioDto.getPersona().getTipoPersona())){
				datosPersonaCompleta.setPrimerApellido(arrendatarioDto.getPersona().getApellido1RazonSocial());
				if(arrendatarioDto.getPersona().getApellido2() != null && !arrendatarioDto.getPersona().getApellido2().isEmpty()){
					datosPersonaCompleta.setSegundoApellido(arrendatarioDto.getPersona().getApellido2());
				}
			} else {
				datosPersonaCompleta.setRazonSocial(arrendatarioDto.getPersona().getApellido1RazonSocial());
			}
			datosPersonaCompleta.setSexo(arrendatarioDto.getPersona().getSexo());
		}
		return datosPersonaCompleta;
	}

	private DatosArrendamiento rellenarDatosArrendamiento(ArrendatarioDto arrendatarioDto) throws ParseException {
		DatosArrendamiento datosArrendamiento = new DatosArrendamiento();
		try {
			if (arrendatarioDto.getPersona() != null) {
				datosArrendamiento.setDoi(arrendatarioDto.getPersona().getNif());
				if(arrendatarioDto.getPersona().getFechaNacimiento() != null && arrendatarioDto.getPersona().getFechaNacimiento().isfechaNula()){
					datosArrendamiento.setFechaNacimiento(arrendatarioDto.getPersona().getFechaNacimiento().getFecha());
				}
			}
			if (arrendatarioDto.getFechaIni() != null && !arrendatarioDto.getFechaIni().isfechaNula()) {
				Calendar gc = toCalendar(arrendatarioDto.getFechaIni().getDate());
				
				gc.set(new Integer(arrendatarioDto.getFechaIni().getAnio()), new Integer(arrendatarioDto.getFechaIni().getMes()) -1 ,1 + new Integer( arrendatarioDto.getFechaIni().getDia()));
				datosArrendamiento.setFechaIni(gc);
			}
			if (arrendatarioDto.getFechaFin() != null && !arrendatarioDto.getFechaFin().isfechaNula()) {
				Calendar gc = toCalendar(arrendatarioDto.getFechaFin().getDate());
				gc.set(new Integer(arrendatarioDto.getFechaFin().getAnio()), new Integer(arrendatarioDto.getFechaFin().getMes())-1,1 + new Integer( arrendatarioDto.getFechaFin().getDia()));
				datosArrendamiento.setFechaFin(gc);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de montar la request de arrendatarios: " + e.getMessage());
		}
		return datosArrendamiento;
	}
/********************************************************/
//	private org.gestoresmadrid.arrendamiento.genfromwsdl.DatosVehiculo rellenarDatosVehiculo1(ArrendatarioDto arrendatarioDto) {
//		org.gestoresmadrid.arrendamiento.genfromwsdl.DatosVehiculo datosVehiculo = new org.gestoresmadrid.arrendamiento.genfromwsdl.DatosVehiculo();
//		// Datos opcionales
//		datosVehiculo.setDoiTitular(arrendatarioDto.getDoiVehiculo());
//		// Datos Obligatorios
//		int longitud = arrendatarioDto.getBastidor().length();
//		datosVehiculo.setMatricula(arrendatarioDto.getMatricula());
//		datosVehiculo.setBastidor(arrendatarioDto.getBastidor().substring(longitud - 6, longitud));
//		return datosVehiculo;
//	}

//	private org.gestoresmadrid.arrendamiento.genfromwsdl.DatosDomicilio rellenarDatosDomicilio1(ArrendatarioDto arrendatarioDto) {
//		org.gestoresmadrid.arrendamiento.genfromwsdl.DatosDomicilio datosDomicilio = new org.gestoresmadrid.arrendamiento.genfromwsdl.DatosDomicilio();
//		if (arrendatarioDto.getPersona() != null && arrendatarioDto.getPersona().getDireccionDto() != null) {
//			datosDomicilio.setBloque(arrendatarioDto.getPersona().getDireccionDto().getBloque() == null ? ""
//					: arrendatarioDto.getPersona().getDireccionDto().getBloque());
//			datosDomicilio.setCp(arrendatarioDto.getPersona().getDireccionDto().getCodPostal() == null ? ""
//					: arrendatarioDto.getPersona().getDireccionDto().getCodPostal());
//			datosDomicilio.setEscalera(arrendatarioDto.getPersona().getDireccionDto().getEscalera() == null ? ""
//					: arrendatarioDto.getPersona().getDireccionDto().getEscalera());
//			datosDomicilio.setHm(arrendatarioDto.getPersona().getDireccionDto().getHm() == null ? ""
//					: arrendatarioDto.getPersona().getDireccionDto().getHm().toString());
//			datosDomicilio.setKm(arrendatarioDto.getPersona().getDireccionDto().getKm() == null ? ""
//					: arrendatarioDto.getPersona().getDireccionDto().getKm().toString());
//			datosDomicilio.setLocalidad(arrendatarioDto.getPersona().getDireccionDto().getPueblo() == null ? ""
//					: arrendatarioDto.getPersona().getDireccionDto().getPueblo());
//			datosDomicilio.setNumero(arrendatarioDto.getPersona().getDireccionDto().getNumero() == null ? ""
//					: arrendatarioDto.getPersona().getDireccionDto().getNumero());
//			datosDomicilio.setPlanta(arrendatarioDto.getPersona().getDireccionDto().getPlanta() == null ? ""
//					: arrendatarioDto.getPersona().getDireccionDto().getPlanta());
//			datosDomicilio.setPortal(arrendatarioDto.getPersona().getDireccionDto().getPortal() == null ? ""
//					: arrendatarioDto.getPersona().getDireccionDto().getPortal());
//			datosDomicilio.setPuerta(arrendatarioDto.getPersona().getDireccionDto().getPuerta() == null ? ""
//					: arrendatarioDto.getPersona().getDireccionDto().getPuerta());
//			datosDomicilio.setTipoVia(arrendatarioDto.getPersona().getDireccionDto().getIdTipoVia() == null ? ""
//					: arrendatarioDto.getPersona().getDireccionDto().getIdTipoVia());
//			datosDomicilio.setVia(arrendatarioDto.getPersona().getDireccionDto().getNombreVia() == null ? ""
//					: arrendatarioDto.getPersona().getDireccionDto().getNombreVia());
//			datosDomicilio.setProvincia(arrendatarioDto.getPersona().getDireccionDto().getIdProvincia() == null ? "" : 
//				arrendatarioDto.getPersona().getDireccionDto().getIdProvincia());
//			datosDomicilio.setMunicipio(arrendatarioDto.getPersona().getDireccionDto().getIdMunicipio() == null ? "" : 
//				arrendatarioDto.getPersona().getDireccionDto().getIdMunicipio());
//		}
//		return datosDomicilio;
//	}

//	private org.gestoresmadrid.arrendamiento.genfromwsdl.DatosPersonaCompleta rellenarDatosPersonaCompleta1(ArrendatarioDto arrendatarioDto) throws ParseException, DatatypeConfigurationException {
//		org.gestoresmadrid.arrendamiento.genfromwsdl.DatosPersonaCompleta datosPersonaCompleta = new org.gestoresmadrid.arrendamiento.genfromwsdl.DatosPersonaCompleta();
//		if (arrendatarioDto.getPersona() != null) {
//			// Datos opcionales
//			datosPersonaCompleta.setDoi(arrendatarioDto.getPersona().getNif());
//			if(arrendatarioDto.getPersona().getFechaNacimiento() != null && !arrendatarioDto.getPersona().getFechaNacimiento().isfechaNula()){
//		
//				GregorianCalendar calendar = new GregorianCalendar();				 	
//				calendar.setTime(arrendatarioDto.getPersona().getFechaNacimiento().getFecha());
//				datosPersonaCompleta.setFechaNacimiento(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
//			
//			}
//			datosPersonaCompleta.setNombre(arrendatarioDto.getPersona().getNombre());
//			if(TipoPersona.Fisica.getValorEnum().equals(arrendatarioDto.getPersona().getTipoPersona())){
//				datosPersonaCompleta.setPrimerApellido(arrendatarioDto.getPersona().getApellido1RazonSocial());
//				if(arrendatarioDto.getPersona().getApellido2() != null && !arrendatarioDto.getPersona().getApellido2().isEmpty()){
//					datosPersonaCompleta.setSegundoApellido(arrendatarioDto.getPersona().getApellido2());
//				}
//			} else {
//				datosPersonaCompleta.setRazonSocial(arrendatarioDto.getPersona().getApellido1RazonSocial());
//			}
//			datosPersonaCompleta.setSexo(arrendatarioDto.getPersona().getSexo());
//		}
//		return datosPersonaCompleta;
//	}

//	private org.gestoresmadrid.arrendamiento.genfromwsdl.DatosArrendamiento rellenarDatosArrendamiento1(ArrendatarioDto arrendatarioDto) throws ParseException {
//		org.gestoresmadrid.arrendamiento.genfromwsdl.DatosArrendamiento datosArrendamiento = new org.gestoresmadrid.arrendamiento.genfromwsdl.DatosArrendamiento();
//		try {
//			if (arrendatarioDto.getPersona() != null) {
//				datosArrendamiento.setDoi(arrendatarioDto.getPersona().getNif());
//				if(arrendatarioDto.getPersona().getFechaNacimiento() != null && !arrendatarioDto.getPersona().getFechaNacimiento().isfechaNula()){					
//					GregorianCalendar calendar = new GregorianCalendar();				 	
//					calendar.setTime(arrendatarioDto.getPersona().getFechaNacimiento().getFecha());
//					datosArrendamiento.setFechaNacimiento(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
//				}
//			}
//			if (arrendatarioDto.getFechaIni() != null && !arrendatarioDto.getFechaIni().isfechaNula()) {
//				//Calendar gc = toCalendar(arrendatarioDto.getFechaIni().getDate());
//				//gc.set(new Integer(arrendatarioDto.getFechaIni().getAnio()), new Integer(arrendatarioDto.getFechaIni().getMes()) -1 ,1 + new Integer( arrendatarioDto.getFechaIni().getDia()));
//				
//			    GregorianCalendar cal = new GregorianCalendar();
//		        cal.setTime(arrendatarioDto.getFechaIni().getDate());
//		        
//		        
//				datosArrendamiento.setFechaIni(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
//			}
//			if (arrendatarioDto.getFechaFin() != null && !arrendatarioDto.getFechaFin().isfechaNula()) {
//				//Calendar gc = toCalendar(arrendatarioDto.getFechaFin().getDate());
//				//gc.set(new Integer(arrendatarioDto.getFechaFin().getAnio()), new Integer(arrendatarioDto.getFechaFin().getMes())-1,1 + new Integer( arrendatarioDto.getFechaFin().getDia()));
//
//				GregorianCalendar cal = new GregorianCalendar();
//		        cal.setTime(arrendatarioDto.getFechaFin().getDate());
//		        
//		        datosArrendamiento.setFechaFin(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
//			}
//		} catch (Exception e) {
//			log.error("Ha sucedido un error a la hora de montar la request de arrendatarios: " + e.getMessage());
//		}
//		return datosArrendamiento;
//	}
	
	
/********************************************************/	
	
	public static Calendar toCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public ArrendamientoServicioWeb getStubAltaArrendamiento(ArrendatarioDto arrendatarioDto)
			throws MalformedURLException, ServiceException {
		URL miURL = new URL(gestorPropiedades.valorPropertie(URL_WS));
		String timeOut = gestorPropiedades.valorPropertie(TIMEOUT);
		CaycArrendamientoSoapBindingStub stub = null;
		ServiciosArrendamientoLocator arrLocator = new ServiciosArrendamientoLocator();
		ArrendamientoServicioWeb arrendamientoServicioWeb = null;
		javax.xml.namespace.QName portName = new javax.xml.namespace.QName(miURL.toString(),
				arrLocator.getCaycArrendamientoSoapWSDDServiceName());
		@SuppressWarnings("unchecked")
		List<HandlerInfo> list = arrLocator.getHandlerRegistry().getHandlerChain(portName);

		// Se configura el Handler de seguridad
		HandlerInfo signerHandlerInfoSegu = new HandlerInfo();
		Map<String, Object> configs = new HashMap<String, Object>();
		configs.put(CaycWSSecurityClientHandler.ALIAS_KEY, arrendatarioDto.getContrato().getColegiadoDto().getAlias());
		signerHandlerInfoSegu.setHandlerConfig(configs);
		signerHandlerInfoSegu.setHandlerClass(CaycWSSecurityClientHandler.class);
		list.add(signerHandlerInfoSegu);
		// Se configura el Handler de fichero
		HandlerInfo signerHandlerInfo = new HandlerInfo();
		Map<String, Object> config = new HashMap<String, Object>();
		config.put(AltaArrendatarioHandler.PROPERTY_KEY_ID, arrendatarioDto.getNumExpediente());
		signerHandlerInfo.setHandlerConfig(config);
		signerHandlerInfo.setHandlerClass(AltaArrendatarioHandler.class);
		list.add(signerHandlerInfo);

		arrendamientoServicioWeb = arrLocator.getCaycArrendamientoSoap(miURL);
		stub = (CaycArrendamientoSoapBindingStub) arrendamientoServicioWeb;
		stub.setTimeout(Integer.parseInt(timeOut));
		return stub;

	}

	// -------------------------------- OPERACION DE MODIFICACION
	// --------------------------------------- //
	@Override
	public ResultadoWSCaycBean procesarModificacionArrendatario(ColaBean colabean) {
		ResultadoWSCaycBean resultadoWSCaycBean = new ResultadoWSCaycBean();
		try {
			if (colabean != null && colabean.getIdTramite() != null) {
				ResultConsultaArrendatarioBean resultado = servicioArrendatario
						.getArrendatarioDto(colabean.getIdTramite());
				ArrendatarioDto arrendatarioDto = resultado.getArrendatarioDto();
				if (arrendatarioDto != null) {
					String xml = recogerXml(colabean.getXmlEnviar(), arrendatarioDto.getNumExpediente(),
							ConstantesGestorFicheros.XML_MOD_ARRENDATARIO);
					resultadoWSCaycBean = llamadaWSModArrendatario(arrendatarioDto, xml, colabean);
					//resultadoWSCaycBean = llamadaWSModArrendatario1(arrendatarioDto, xml, colabean);
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

	private ResultadoWSCaycBean llamadaWSModArrendatario1(ArrendatarioDto arrendatarioDto, String xml,
			ColaBean colabean) {

		ResultadoWSCaycBean resultado = new ResultadoWSCaycBean();
		log.info("Entra en la llamadaWS de MOD Arrendatario");
//		try {
//
//			org.gestoresmadrid.arrendamiento.genfromwsdl.DatosArrendamiento datosArrendamiento = rellenarDatosArrendamiento1(arrendatarioDto);
//			org.gestoresmadrid.arrendamiento.genfromwsdl.DatosVehiculo datosVehiculo = rellenarDatosVehiculo1(arrendatarioDto);
//			//ResultadoComunicacion salida = getStubModArrendatario(arrendatarioDto)
//				//	.modificacionArrendamiento(datosArrendamiento, datosVehiculo, xml);
//
//			org.gestoresmadrid.arrendamiento.ArrendamientoClient arrendamientoClient = 
//					    new org.gestoresmadrid.arrendamiento.ArrendamientoClient(arrendatarioDto.getNumExpediente(),
//					                                        		arrendatarioDto.getContrato().getColegiadoDto().getAlias(),"MODIFICACION"
//					                                        		);
//			arrendamientoClient.setDefaultUri("http://pr-apls-prep.dgt.es:8080/WS_CAYC/ServiciosArrendamiento");
//			//arrendamientoClient.setNumExpediente(arrendatarioDto.getNumExpediente());
//			int i=0;
//			org.gestoresmadrid.arrendamiento.genfromwsdl.ModificacionArrendamiento modificacionArrendamiento=new org.gestoresmadrid.arrendamiento.genfromwsdl.ModificacionArrendamiento();
//			modificacionArrendamiento.setSolicitud(xml);
//			modificacionArrendamiento.setDatosVehiculo(datosVehiculo);
//			modificacionArrendamiento.setDatosArrendamiento(datosArrendamiento);
//			 
//			org.gestoresmadrid.arrendamiento.genfromwsdl.ModificacionArrendamientoResponse modificacionArrendamientoResponse
//            =arrendamientoClient.modificacion(modificacionArrendamiento);
//			
//			org.gestoresmadrid.arrendamiento.genfromwsdl.ResultadoComunicacion salida=modificacionArrendamientoResponse.getResultadoComunicacion();
//			
//
//			resultado = gestionarResultadoAlta1(salida, arrendatarioDto, colabean);
//			log.info("Recibida respuesta del WebService de modificacion Arrendatario");
//		} catch (Throwable e) {
//			log.error("Ha sucedido un error a la hora de realizar la peticion al WS de arrendatario, error: ", e);
//			resultado = new ResultadoWSCaycBean(Boolean.TRUE);
//			resultado.setExcepcion(new Exception(e.getMessage()));
//			resultado.setRespuestaWS(e.getMessage());
//		}
		return resultado;
	}

	private ResultadoWSCaycBean llamadaWSModArrendatario(ArrendatarioDto arrendatarioDto, String xml,
			ColaBean colabean) {

		ResultadoWSCaycBean resultado = new ResultadoWSCaycBean();
		log.info("Entra en la llamadaWS de MOD Arrendatario");
		try {

			DatosArrendamiento datosArrendamiento = rellenarDatosArrendamiento(arrendatarioDto);
			DatosVehiculo datosVehiculo = rellenarDatosVehiculo(arrendatarioDto);
			ResultadoComunicacion salida = getStubModArrendatario(arrendatarioDto)
					.modificacionArrendamiento(datosArrendamiento, datosVehiculo, xml);

			resultado = gestionarResultadoAlta(salida, arrendatarioDto, colabean);
			log.info("Recibida respuesta del WebService de modificacion Arrendatario");
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de realizar la peticion al WS de arrendatario, error: ", e);
			resultado = new ResultadoWSCaycBean(Boolean.TRUE);
			resultado.setExcepcion(new Exception(e.getMessage()));
			resultado.setRespuestaWS(e.getMessage());
		}
		return resultado;
	}
	
	private ArrendamientoServicioWeb getStubModArrendatario(ArrendatarioDto arrendatarioDto)
			throws MalformedURLException, ServiceException {
		URL miURL = new URL(gestorPropiedades.valorPropertie(URL_WS));
		String timeOut = gestorPropiedades.valorPropertie(TIMEOUT);
		CaycArrendamientoSoapBindingStub stub = null;
		ServiciosArrendamientoLocator arrLocator = new ServiciosArrendamientoLocator();
		ArrendamientoServicioWeb arrendamientoServicioWeb = null;
		javax.xml.namespace.QName portName = new javax.xml.namespace.QName(miURL.toString(),
				arrLocator.getCaycArrendamientoSoapWSDDServiceName());
		@SuppressWarnings("unchecked")
		List<HandlerInfo> list = arrLocator.getHandlerRegistry().getHandlerChain(portName);

		// Se configura el Handler de seguridad
		HandlerInfo signerHandlerInfoSegu = new HandlerInfo();
		Map<String, Object> configs = new HashMap<String, Object>();
		configs.put(CaycWSSecurityClientHandler.ALIAS_KEY, arrendatarioDto.getContrato().getColegiadoDto().getAlias());
		signerHandlerInfoSegu.setHandlerConfig(configs);
		signerHandlerInfoSegu.setHandlerClass(CaycWSSecurityClientHandler.class);
		list.add(signerHandlerInfoSegu);

		// Se configura el Handler de fichero
		HandlerInfo signerHandlerInfo = new HandlerInfo();
		Map<String, Object> config = new HashMap<String, Object>();
		config.put(ModiArrendatarioHandler.PROPERTY_KEY_ID, arrendatarioDto.getNumExpediente());
		signerHandlerInfo.setHandlerConfig(config);
		signerHandlerInfo.setHandlerClass(ModiArrendatarioHandler.class);
		list.add(signerHandlerInfo);

		arrendamientoServicioWeb = arrLocator.getCaycArrendamientoSoap(miURL);
		stub = (CaycArrendamientoSoapBindingStub) arrendamientoServicioWeb;
		stub.setTimeout(Integer.parseInt(timeOut));
		return stub;
	}

}
