package org.gestoresmadrid.oegam2comun.atex5.model.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import org.gestoresmadrid.core.atex5.model.enumerados.EstadoAtex5;
import org.gestoresmadrid.core.atex5.model.vo.ConsultaVehiculoAtex5VO;
import org.gestoresmadrid.core.consultasTGate.model.vo.ConsultasTGateVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2comun.atex5.model.service.ServicioVehiculoAtex5;
import org.gestoresmadrid.oegam2comun.atex5.model.service.ServicioWebServiceVehiculosAtex5;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResultadoAtex5Bean;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResultadoWSAtex5Bean;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.ConsultaVehiculoAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.Vehiculo;
import org.gestoresmadrid.oegam2comun.consultasTGate.model.service.ServicioConsultasTGate;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.trafico.servicios.vehiculos.consulta.atex.webservices.ATEX;
import es.trafico.servicios.vehiculos.consulta.atex.webservices.ConsultaATEX;
import es.trafico.servicios.vehiculos.consulta.atex.webservices.CriteriosConsultaVehiculo;
import es.trafico.servicios.vehiculos.consulta.atex.webservices.RespuestaAtex;
import es.trafico.servicios.vehiculos.consulta.atex.webservices.SolicitudConsultaVehiculoAtex;
import es.trafico.servicios.vehiculos.consulta.atex.webservices.ws.SoapAtex5SignerHandler;
import es.trafico.servicios.vehiculos.consulta.atex.webservices.ws.SoapAtex5VehiculoWSHandler;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioWebServiceVehiculosAtex5Impl implements ServicioWebServiceVehiculosAtex5 {

	private static final long serialVersionUID = 1102263192437878721L;

	private ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServiceVehiculosAtex5Impl.class);

	@Autowired
	private ServicioVehiculoAtex5 servicioVehiculoAtex5;

	@Autowired
	private ServicioConsultasTGate servicioConsultasTGate;

	@Autowired
	private ServicioVehiculo servicioVehiculo;
	
	@Autowired
	ServicioCorreo servicioCorreo;
	
	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public ResultadoWSAtex5Bean generarConsultaVehiculoAtex5(ColaBean colaBean) {
		ResultadoWSAtex5Bean resultadoWSAtex5Bean = new ResultadoWSAtex5Bean();
		try {
			if (colaBean != null && colaBean.getIdTramite() != null) {
				ConsultaVehiculoAtex5Dto consultaVehiculoAtex5 = servicioVehiculoAtex5.getConsultaVehiculoAtex5PorIdConsultaDto(colaBean.getIdTramite(), Boolean.TRUE);
				if (consultaVehiculoAtex5 != null) {
					resultadoWSAtex5Bean = llamadaWSVehiculoAtex5(consultaVehiculoAtex5);
					resultadoWSAtex5Bean.setMatricula(consultaVehiculoAtex5.getMatricula());
					resultadoWSAtex5Bean.setBastidor(consultaVehiculoAtex5.getBastidor());
				} else {
					resultadoWSAtex5Bean.setError(Boolean.TRUE);
					resultadoWSAtex5Bean.setMensajeError("No existen datos de la consulta para poder realizar la petición.");
				}
			} else {
				resultadoWSAtex5Bean.setError(Boolean.TRUE);
				resultadoWSAtex5Bean.setMensajeError("La cola o el numero del expediente de la consulta estan vacios.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar la peticion de consulta de vehiculos atex5, error: ", e);
			resultadoWSAtex5Bean.setExcepcion(e);
		}
		return resultadoWSAtex5Bean;
	}

	private ResultadoWSAtex5Bean llamadaWSVehiculoAtex5(ConsultaVehiculoAtex5Dto consultaVehiculoAtex5) {
		ResultadoWSAtex5Bean resultado = null;
		log.info("Entra en la llamadaWS de Consulta Vehiculo Atex5");
		try {
			SolicitudConsultaVehiculoAtex solicitud = rellenarDatosSolicitudVehiculoAtex5(consultaVehiculoAtex5);
			RespuestaAtex respuestaAtex = getStubConsultVehiculoAtex5(consultaVehiculoAtex5.getNumExpediente()).consultaATEXVehiculo(solicitud);
			log.info("Recibida respuesta del WebService de Consulta Vehiculo Atex5");
			resultado = tratarRespuestaVehiculoAtex5WS(respuestaAtex, consultaVehiculoAtex5);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar la peticion al WS de consulta de vehiculo atex5, error: ", e);
			resultado = new ResultadoWSAtex5Bean();
			resultado.setExcepcion(e);
		}
		return resultado;
	}

	private ConsultaATEX getStubConsultVehiculoAtex5(BigDecimal numExpediente) throws MalformedURLException {
		URL miURL = new URL(gestorPropiedades.valorPropertie(URL_WS));
		String timeOut = gestorPropiedades.valorPropertie(TIMEOUT);

		ATEX atexLocator = new ATEX(miURL);
		ConsultaATEX consultaATEX = atexLocator.getATEX();

		Binding binding = ((BindingProvider) consultaATEX).getBinding();
		List<Handler> handlerList = binding.getHandlerChain(); 
		
        ((BindingProvider) consultaATEX).getRequestContext().put("com.sun.xml.internal.ws.connect.timeout", Integer.parseInt(timeOut));
        ((BindingProvider) consultaATEX).getRequestContext().put("com.sun.xml.internal.ws.request.timeout", Integer.parseInt(timeOut));

        handlerList.add(new SoapAtex5SignerHandler()); 
        
        String alias = gestorPropiedades.valorPropertie(ALIAS_CONTRATO_INFORMATICA);
        
        ((BindingProvider) consultaATEX).getRequestContext().put(SoapAtex5SignerHandler.ALIAS_KEY, alias);

        handlerList.add(new es.trafico.servicios.vehiculos.consulta.atex.webservices.ws.SoapAtex5VehiculoWSHandler());
        
        ((BindingProvider) consultaATEX).getRequestContext().put(SoapAtex5VehiculoWSHandler.PROPERTY_KEY_ID, numExpediente);
        
        binding.setHandlerChain(handlerList); 
        
        return consultaATEX;             

	}

	private SolicitudConsultaVehiculoAtex rellenarDatosSolicitudVehiculoAtex5(ConsultaVehiculoAtex5Dto consultaVehiculoAtex5) {
		SolicitudConsultaVehiculoAtex solicitud = new SolicitudConsultaVehiculoAtex();
		solicitud.setIdOrganismoResponsable(consultaVehiculoAtex5.getContrato().getColegioDto().getCif());
		solicitud.setIdResponsable(consultaVehiculoAtex5.getContrato().getColegiadoDto().getUsuario().getNif());
		//String idContratoPryInfo = gestorPropiedades.valorPropertie(ID_CONTRATO_INFORMATICA);
		//ContratoDto contratoProyInformatico = servicioContrato.getContratoDto(new BigDecimal(idContratoPryInfo));
		solicitud.setIdUsuario("B87016630");
		solicitud.setVersion(VERSION_CONSULTA);
		CriteriosConsultaVehiculo criteriosConsultaVehiculo = new CriteriosConsultaVehiculo();
		criteriosConsultaVehiculo.setBastidor(consultaVehiculoAtex5.getBastidor());
		criteriosConsultaVehiculo.setMatricula(consultaVehiculoAtex5.getMatricula());
		solicitud.setCriteriosConsultaVehiculo(criteriosConsultaVehiculo);
		return solicitud;
	}

	@Override
	public ResultadoWSAtex5Bean generarConsultaVehiculoAvpoAtex5(ColaBean colaBean, String matricula, String bastidor) {
		ResultadoWSAtex5Bean resultadoWSAtex5Bean = new ResultadoWSAtex5Bean();
		try {
			if (colaBean != null && colaBean.getIdTramite() != null) {
				ConsultaVehiculoAtex5Dto consultaVehiculoAtex5 = servicioVehiculoAtex5.getConsultaVehiculoAtex5PorIdConsultaDto(colaBean.getIdTramite(), Boolean.TRUE);
				if (consultaVehiculoAtex5 != null) {
					resultadoWSAtex5Bean = llamadaWSVehiculoAtex5(consultaVehiculoAtex5);
					resultadoWSAtex5Bean.setMatricula(matricula);
					resultadoWSAtex5Bean.setBastidor(bastidor);
				} else {
					resultadoWSAtex5Bean.setError(Boolean.TRUE);
					resultadoWSAtex5Bean.setMensajeError("No existen datos de la consulta para poder realizar la petición.");
				}
			} else {
				resultadoWSAtex5Bean.setError(Boolean.TRUE);
				resultadoWSAtex5Bean.setMensajeError("La cola o el numero del expediente de la consulta estan vacios.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar la peticion de consulta de vehiculos atex5, error: ", e);
			resultadoWSAtex5Bean.setExcepcion(e);
		}
		return resultadoWSAtex5Bean;
	}

	private ResultadoWSAtex5Bean tratarRespuestaVehiculoAtex5WS(RespuestaAtex respuestaAtex, ConsultaVehiculoAtex5Dto consultaVehiculoAtex5) {
		ResultadoWSAtex5Bean resultadoWSAtex5Bean = new ResultadoWSAtex5Bean();
		if (respuestaAtex != null) {
			if ("OK".equalsIgnoreCase(respuestaAtex.getResultado())) {
				ResultadoAtex5Bean resultadoImpresion = servicioVehiculoAtex5.imprimirPdf(consultaVehiculoAtex5, respuestaAtex.getRespuesta());
				if(!resultadoImpresion.getError()){
					resultadoWSAtex5Bean.setRespuestaWS("Finalización Correcta.");
					resultadoWSAtex5Bean.setEstado(EstadoAtex5.Finalizado_PDF);
				} else {
					resultadoWSAtex5Bean.setError(Boolean.TRUE);
					resultadoWSAtex5Bean.setMensajeError(resultadoImpresion.getMensaje());
				}
			} else {
				if(respuestaAtex.getRespuesta().contains(ServicioVehiculoAtex5.ID_ATEX5_SIN_ANTECEDENTES)){
					resultadoWSAtex5Bean.setRespuestaWS(respuestaAtex.getRespuesta());
					resultadoWSAtex5Bean.setEstado(EstadoAtex5.Finalizado_Sin_Antecedentes);
				} else {
					resultadoWSAtex5Bean.setError(Boolean.TRUE);
					resultadoWSAtex5Bean.setMensajeError(respuestaAtex.getRespuesta());
				}
			}
		} else {
			resultadoWSAtex5Bean.setExcepcion(new Exception("No se ha encontrado respuesta para la consulta de vehiculo atex5."));
		}
		return resultadoWSAtex5Bean;
	}

	@Override
	public void cambiarEstadoConsulta(BigDecimal idConsultaVehiculoAtex5, BigDecimal idUsuario, EstadoAtex5 estado, String respuesta) {
		try {
			servicioVehiculoAtex5.cambiarEstadoProceso(idConsultaVehiculoAtex5, idUsuario, new BigDecimal(estado.getValorEnum()),respuesta);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado de la consulta de vehiculo atex5: " + idConsultaVehiculoAtex5 + " al estado: " + estado.getNombreEnum() + ", error: ", e);
		}
	}

	@Override
	public void devolverCreditos(BigDecimal idConsultaVehiculo, BigDecimal idContrato, BigDecimal idUsuario, String tipoTramite, ConceptoCreditoFacturado concepto, int numeroSolicitudes) {
		try {
			ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
			if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie(ServicioVehiculoAtex5.COBRAR_CREDITOS))) {
				resultado = servicioVehiculoAtex5.devolverCreditosWS(idConsultaVehiculo, idUsuario, idContrato, tipoTramite, concepto, numeroSolicitudes);
			}
			if (resultado.getError()) {
				log.error("Ha sucedido un error a la hora de devolver el credito en el proceso consulta de Vehiculo Atex5, error: " + resultado.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de devolver los creditos de la consulta vehiculo atex5 con id: " + idConsultaVehiculo + ", error: ", e);
		}
	}
	

	@Override
	public void asignarTasa() {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		try {
			String solicitudesMaximas = gestorPropiedades.valorPropertie("numero.maximo.tasas.atex5");
			if(solicitudesMaximas != null && !solicitudesMaximas.isEmpty()){
				List<ConsultaVehiculoAtex5VO> listaVehiculoAtex5BBDD = servicioVehiculoAtex5.getListaConsultasFinalizadas();
				if(listaVehiculoAtex5BBDD != null && !listaVehiculoAtex5BBDD.isEmpty() && listaVehiculoAtex5BBDD.size() >= Integer.valueOf(solicitudesMaximas)){
					resultado = servicioVehiculoAtex5.asignarTasasProceso(listaVehiculoAtex5BBDD);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se puede asignar la tasa de atex5 porque no se puede obtener el minimo de consultas en estado finalizadas.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de asignar la tasa a las consultas Atex5, error:  ",  e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de asignar la tasa a las consultas Atex5.");
		}
		if(resultado.getError()){
			enviarMailConsultaAtex5(resultado);
		}
	}
	

	private void enviarMailConsultaAtex5(ResultadoAtex5Bean resultado) {
		try {
			if(resultado.getError()){
				String to = gestorPropiedades.valorPropertie("direccion.tasa.vehiculo.atex5");
				String subject = gestorPropiedades.valorPropertie("direccion.tasa.vehiculo.atex5.subject");
	
				StringBuffer sb = getResumenEnvio(resultado); 
				
				ResultBean resultEnvio;
				resultEnvio = servicioCorreo.enviarCorreo(sb.toString(),Boolean.FALSE, null, subject, to, null, null, null);
				
				if(resultEnvio.getError()){
					for(String mensaje : resultEnvio.getListaMensajes()){
						log.error(mensaje);
					}
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de asignar tasa en la consulta de vehiculos atex5 : ",e);
		}
		
	}

	private StringBuffer getResumenEnvio(ResultadoAtex5Bean resultado) {
		StringBuffer resultadoHtml = new StringBuffer();
		final String sFormatoFecha = "dd/MM/yyyy";
		resultadoHtml.append("<br>Ocurrieron problemas en la asignación de tasa a la consulta de vehiculos Atex5 : ")
		.append(utilesFecha.formatoFecha(sFormatoFecha,new Date()))
		.append(".<br></br>");
		resultadoHtml.append(cadenaTextoPlanoResultado(resultado));
		resultadoHtml.append("<br>");
		
		return resultadoHtml;
	}

	private String cadenaTextoPlanoResultado(ResultadoAtex5Bean resultado) {
		StringBuffer cadenaResultado = new StringBuffer("<br><u><b>");
		
		cadenaResultado.append("Existen errores al asignar tasa a la consulta de vehiculos Atex5:");
		cadenaResultado.append("</u></b><br><br>");
		return cadenaResultado.toString();
	}

	@Override
	public Vehiculo prepararVehiculoByte(byte[] xml) {
		Vehiculo vehiculo = null;
		InputStream inputStream = null;
		try {
			JAXBContext context = JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo");
			Unmarshaller unmarshaller = context.createUnmarshaller();
			inputStream = new ByteArrayInputStream(xml);
			vehiculo = (Vehiculo) unmarshaller.unmarshal(inputStream);
		} catch (Exception e) {
			log.error("Error al generar el vehiculo con el xml", e);
		}
		return vehiculo;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Vehiculo prepararVehiculoFile(File fichero) {
		Vehiculo vehiculo = null;
		try {
			JAXBContext context = JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo");
			Unmarshaller unmarshaller = context.createUnmarshaller();
			JAXBElement<Vehiculo> unmarshal = (JAXBElement<Vehiculo>) unmarshaller.unmarshal(fichero);
			vehiculo = (Vehiculo) unmarshal.getValue();
		} catch (Exception e) {
			log.error("Error al generar el vehiculo con el xml", e);
		}
		return vehiculo;
	}

	@Override
	@Transactional
	public void guardarConsultasTGate(BigDecimal numExpediente, BigDecimal idUsuario, String numColegiado, String matricula, String bastidor, String origen, String tipoServicio, String respuesta) {
		VehiculoVO vehiculo = null;
		if (matricula != null && !matricula.isEmpty()) {
			vehiculo = servicioVehiculo.getVehiculoVO(null, numColegiado, matricula, null, null, null);
		} else if (bastidor != null && !bastidor.isEmpty()) {
			vehiculo = servicioVehiculo.getVehiculoVO(null, numColegiado, null, bastidor, null, null);
		}

		if (vehiculo != null) {
			ConsultasTGateVO consultasTGateVO = new ConsultasTGateVO();
			consultasTGateVO.setFechaHora(Calendar.getInstance().getTime());
			consultasTGateVO.setIdVehiculo(vehiculo.getIdVehiculo());
			consultasTGateVO.setNumColegiado(numColegiado);
			consultasTGateVO.setNumExpediente(numExpediente);
			consultasTGateVO.setOrigen(origen);
			consultasTGateVO.setRespuesta(respuesta);
			consultasTGateVO.setTipoServicio(tipoServicio);
			consultasTGateVO.setIdUsuario(idUsuario);
			servicioConsultasTGate.guardar(consultasTGateVO);
		}
	}

}
