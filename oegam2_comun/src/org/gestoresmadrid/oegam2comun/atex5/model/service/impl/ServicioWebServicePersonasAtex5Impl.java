package org.gestoresmadrid.oegam2comun.atex5.model.service.impl;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import org.gestoresmadrid.core.atex5.model.enumerados.EstadoAtex5;
import org.gestoresmadrid.core.atex5.model.vo.ConsultaPersonaAtex5VO;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.atex5.model.service.ServicioPersonaAtex5;
import org.gestoresmadrid.oegam2comun.atex5.model.service.ServicioWebServicePersonasAtex5;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResultadoAtex5Bean;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResultadoWSAtex5Bean;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.trafico.servicios.vehiculos.consulta.atex.webservices.ATEX;
import es.trafico.servicios.vehiculos.consulta.atex.webservices.ConsultaATEX;
import es.trafico.servicios.vehiculos.consulta.atex.webservices.CriteriosConsultaPersona;
import es.trafico.servicios.vehiculos.consulta.atex.webservices.RespuestaAtex;
import es.trafico.servicios.vehiculos.consulta.atex.webservices.SolicitudConsultaPersonaAtex;
import es.trafico.servicios.vehiculos.consulta.atex.webservices.ws.SoapAtex5PersonaWSHandler;
import es.trafico.servicios.vehiculos.consulta.atex.webservices.ws.SoapAtex5SignerHandler;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioWebServicePersonasAtex5Impl implements ServicioWebServicePersonasAtex5 {

	private static final long serialVersionUID = 1102263192437878721L;

	private ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServicePersonasAtex5Impl.class);

	@Autowired
	ServicioPersonaAtex5 servicioPersonaAtex5;

	@Autowired
	Conversor conversor;
	
	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public ResultadoWSAtex5Bean generarConsultaPersonaAtex5(ColaBean colaBean) {
		ResultadoWSAtex5Bean resultadoWSPersonasAtex5Bean = new ResultadoWSAtex5Bean();
		try {
			if (colaBean != null && colaBean.getIdTramite() != null) {
				ConsultaPersonaAtex5VO consultaPersonaAtex5VO = servicioPersonaAtex5.getConsultaPersonaAtex5PorExpVO(colaBean.getIdTramite(), Boolean.TRUE);
				if (consultaPersonaAtex5VO != null) {
					resultadoWSPersonasAtex5Bean = llamadaWSPersonaAtex5(consultaPersonaAtex5VO);
				} else {
					resultadoWSPersonasAtex5Bean.setError(Boolean.TRUE);
					resultadoWSPersonasAtex5Bean.setMensajeError("No existen datos de la consulta para poder realizar la petición.");
				}
			} else {
				resultadoWSPersonasAtex5Bean.setError(Boolean.TRUE);
				resultadoWSPersonasAtex5Bean.setMensajeError("La cola o el numero del expediente de la consulta estan vacios.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar la peticion de consulta de personas atex5, error: ", e);
			resultadoWSPersonasAtex5Bean.setExcepcion(e);
		}
		return resultadoWSPersonasAtex5Bean;
	}

	private ResultadoWSAtex5Bean llamadaWSPersonaAtex5(ConsultaPersonaAtex5VO consultaPersonaAtex5VO) {
		ResultadoWSAtex5Bean resultado = null;
		log.info("Entra en la llamadaWS de Consulta Persona Atex5");
		try {
			SolicitudConsultaPersonaAtex solicitud = rellenarDatosSolicitudPersonaAtex5(consultaPersonaAtex5VO);
			RespuestaAtex respuestaAtex = getStubConsultPersonaAtex5(consultaPersonaAtex5VO.getNumExpediente()).consultaATEXPersona(
					solicitud);
			log.info("Recibida respuesta del WebService de Consulta Persona Atex5");
			resultado = tratarRespuestaPersonaAtex5WS(respuestaAtex, consultaPersonaAtex5VO);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar la peticion al WS de consulta de persona atex5, error: ", e);
			resultado = new ResultadoWSAtex5Bean();
			resultado.setExcepcion(e);
		}
		return resultado;
	}

	private ResultadoWSAtex5Bean tratarRespuestaPersonaAtex5WS(RespuestaAtex respuestaAtex, ConsultaPersonaAtex5VO consultaPersonaAtex5VO) {
		ResultadoWSAtex5Bean resultadoWSMasivaPersAtex5Bean = new ResultadoWSAtex5Bean();
		if (respuestaAtex != null) {
			if ("OK".equalsIgnoreCase(respuestaAtex.getResultado())) {
				ResultadoAtex5Bean resultGuardarXml = servicioPersonaAtex5.guardarXmlConsultaPersonaAtex5(consultaPersonaAtex5VO.getNumExpediente(), respuestaAtex);
				if (resultGuardarXml.getError()) {
					resultadoWSMasivaPersAtex5Bean.setError(Boolean.TRUE);
					resultadoWSMasivaPersAtex5Bean.setMensajeError(resultGuardarXml.getMensaje());
				}
			} else {
				resultadoWSMasivaPersAtex5Bean.setError(Boolean.TRUE);
				resultadoWSMasivaPersAtex5Bean.setMensajeError(respuestaAtex.getResultado());
			}
		} else {
			resultadoWSMasivaPersAtex5Bean.setExcepcion(new Exception("No se ha encontrado respuesta para la consulta de persona atex5."));
		}
		return resultadoWSMasivaPersAtex5Bean;
	}

	private ConsultaATEX getStubConsultPersonaAtex5(BigDecimal numExpediente) throws MalformedURLException {
		URL miURL = new URL(gestorPropiedades.valorPropertie(URL_WS));
		String timeOut = gestorPropiedades.valorPropertie(TIMEOUT);

		ATEX atexLocator = new ATEX(miURL);
		ConsultaATEX consultaATEX = atexLocator.getATEX0();

		Binding binding = ((BindingProvider) consultaATEX).getBinding();
		List<Handler> handlerList = binding.getHandlerChain(); 
		
        ((BindingProvider) consultaATEX).getRequestContext().put("com.sun.xml.internal.ws.connect.timeout", Integer.parseInt(timeOut));
        ((BindingProvider) consultaATEX).getRequestContext().put("com.sun.xml.internal.ws.request.timeout", Integer.parseInt(timeOut));

        handlerList.add(new SoapAtex5SignerHandler()); 
        
        String alias = gestorPropiedades.valorPropertie(ALIAS_CONTRATO_INFORMATICA);
        
        ((BindingProvider) consultaATEX).getRequestContext().put(SoapAtex5SignerHandler.ALIAS_KEY, alias);

        handlerList.add(new es.trafico.servicios.vehiculos.consulta.atex.webservices.ws.SoapAtex5VehiculoWSHandler());
        
        ((BindingProvider) consultaATEX).getRequestContext().put(SoapAtex5PersonaWSHandler.PROPERTY_KEY_ID, numExpediente);
        
        binding.setHandlerChain(handlerList); 
        
        return consultaATEX;             

	}

	private SolicitudConsultaPersonaAtex rellenarDatosSolicitudPersonaAtex5(ConsultaPersonaAtex5VO consultaPersonaAtex5VO) {
		SolicitudConsultaPersonaAtex solicitud = conversor.transform(consultaPersonaAtex5VO, SolicitudConsultaPersonaAtex.class);
		solicitud.setIdOrganismoResponsable(consultaPersonaAtex5VO.getContrato().getColegio().getCif());
		solicitud.setIdResponsable(consultaPersonaAtex5VO.getContrato().getColegiado().getUsuario().getNif());
		String idContratoPryInfo = gestorPropiedades.valorPropertie(ID_CONTRATO_INFORMATICA);
		ContratoDto contratoProyInformatico = servicioContrato.getContratoDto(new BigDecimal(idContratoPryInfo));
		solicitud.setIdUsuario(contratoProyInformatico.getCif());
		solicitud.setVersion(VERSION_CONSULTA);
		
		if(consultaPersonaAtex5VO.getFechaNacimiento() != null){
			if(solicitud.getCriteriosConsultaPersona() != null){
				solicitud.getCriteriosConsultaPersona().setFechaNacimiento(utilesFecha.formatoFecha("yyyy-MM-dd", consultaPersonaAtex5VO.getFechaNacimiento()));
			}else{
				CriteriosConsultaPersona criteriosConsultaPersona = new CriteriosConsultaPersona();
				criteriosConsultaPersona.setFechaNacimiento(utilesFecha.formatoFecha("yyyy-MM-dd", consultaPersonaAtex5VO.getFechaNacimiento()));
				solicitud.setCriteriosConsultaPersona(criteriosConsultaPersona);
			}
		}
		return solicitud;
	}

	@Override
	public void cambiarEstadoConsulta(BigDecimal numExpediente, BigDecimal idUsuario, EstadoAtex5 estado) {
		try {
			servicioPersonaAtex5.cambiarEstado(numExpediente, idUsuario, new BigDecimal(estado.getValorEnum()), false);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado de la consulta de persona atex5: " + numExpediente + " al estado: " + estado.getNombreEnum() + ", error: ", e);
		}
	}

	@Override
	public void devolverCreditos(BigDecimal numExpediente, BigDecimal idContrato, BigDecimal idUsuario) {
		try {
			ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
			if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie(ServicioPersonaAtex5.cobrarCreditos))) {
				resultado = servicioPersonaAtex5.devolverCreditosWS(numExpediente, idUsuario, idContrato);
			}
			if (resultado.getError()) {
				log.error("Ha sucedido un error a la hora de devolver el credito en el proceso consulta de Persona Atex5, error: " + resultado.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de devolver los creditos de la consulta persona atex5 con num.Expediente: " + numExpediente + ", error: ", e);
		}
	}

}
