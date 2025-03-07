package com.matriculasIvtmWS.integracion;

import java.io.Serializable;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

import com.matriculasIvtmWS.integracion.bean.MatriculasWSRequest;
import com.matriculasIvtmWS.integracion.bean.MatriculasWSResponse;
import com.matriculasIvtmWS.integracion.constantes.CodigoResultadoWS;
import com.matriculasIvtmWS.integracion.constantes.Constantes;
import com.matriculasIvtmWS.integracion.service.ServicioMatriculasIvtmWS;

@WebService
public class MatriculasWS implements Serializable{

	private static final long serialVersionUID = -8308937678531851299L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(MatriculasWS.class);
	
	public MatriculasWS() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Autowired
	ServicioMatriculasIvtmWS servicioMatriculasIvtmWS;
	
	@WebMethod
	@HandlerChain(file="src/resources/handler.xml")
	public MatriculasWSResponse obtenerMatricula(MatriculasWSRequest request){
		MatriculasWSResponse response = null;
		
		if(request.getUsuario().equals(Constantes.USUARIO) && 
				request.getClave().equals(Constantes.CLAVE)){
		
			try {
				log.info("Antes de comprobar Request");
				response = comprobarRequest(request);
				log.info("Después de comprobar Request");
				if(response == null){
					log.info("Antes de llamar al servicio");
					response = servicioMatriculasIvtmWS.getMatriculasIvtm(request);
					log.info("Después de llamar al servicio");
				}
			} catch (Exception e) {
				log.error("Ha sucedido un error a la hora de tratar la request, error: ",e);
				response = new MatriculasWSResponse();
				response.setCodigoResultado(CodigoResultadoWS.ERROR_INTERNO.getValorEnum());
				response.setMensaje(CodigoResultadoWS.ERROR_INTERNO.getNombreEnum());
			}
		
		
		}else{
			log.error("Los datos de validación [usuario/clave] son erróneos");
			response = new MatriculasWSResponse();
			response.setCodigoResultado(CodigoResultadoWS.ERROR_AUTENTICACION.getValorEnum());
			response.setMensaje(CodigoResultadoWS.ERROR_AUTENTICACION.getNombreEnum());
		}
		
		return response;
	}

	private MatriculasWSResponse comprobarRequest(MatriculasWSRequest request) {
		MatriculasWSResponse response = null;
		CodigoResultadoWS codigo = null;
		if(request.getListaAutoliquidaciones() == null || request.getListaAutoliquidaciones().length == 0){
			log.error("Datos de números de autoliquidación vacíos");
			codigo = CodigoResultadoWS.ERROR_FALTANPARAMETROS;
		}
		if(request.getFechaInicio() != null){
			if(request.getFechaInicio().getDia() == null || request.getFechaInicio().getMes() == null || request.getFechaInicio().getAnio() == null){
				log.error("Dato de fecha de inicio vacío");
				codigo = CodigoResultadoWS.ERROR_FALTANPARAMETROS;
			}
		}
		if(codigo != null){
			response = new MatriculasWSResponse();
			response.setCodigoResultado(codigo.getValorEnum());
			response.setMensaje(codigo.getNombreEnum());
		}
		return response;
	}

}
