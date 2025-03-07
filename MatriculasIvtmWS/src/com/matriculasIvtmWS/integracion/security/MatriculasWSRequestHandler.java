	package com.matriculasIvtmWS.integracion.security;

import java.util.Date;

import org.apache.axis.AxisFault;
import org.apache.axis.MessageContext;
import org.apache.axis.handlers.BasicHandler;
import org.apache.log4j.Logger;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.matriculasIvtmWS.integracion.constantes.CodigoResultadoWS;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.estructuras.Fecha;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirmaProceso;

public class MatriculasWSRequestHandler extends BasicHandler{
	
	private static final long serialVersionUID = 2199158855111059902L;

	private static final Logger log = Logger.getLogger(MatriculasWSRequestHandler.class);

	@Autowired
	UtilesViafirmaProceso utilesViafirma;
	
	@Autowired
	GestorDocumentos gestorDocumentos;
	
	@Autowired
	UtilesFecha utilesFecha;
	
	public MatriculasWSRequestHandler() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}


	@Override
	public void invoke(MessageContext msgContext) throws AxisFault {
		try {
			log.info("Validando petición firmada");
			boolean coreValidity = utilesViafirma.validarFirmaXMLDSIG(msgContext.getRequestMessage().getSOAPPartAsBytes());
//			boolean coreValidity = true;
			if (!coreValidity) {
				log.info("La petición no pasa la validación de firma");
				//throw new AxisFault("[" + ConstantesWS.Codigo_007 + "] - " + ConstantesWS.La_firma_no_es_valida);
				throw new AxisFault(CodigoResultadoWS.ERROR_FIRMA.getValorEnum() + ":" + CodigoResultadoWS.ERROR_FIRMA.getNombreEnum());
			}
			Date fecha = new Date();
			String nombreFichero  = "IVTMMATRICULASWS_PET_" + utilesFecha.formatoFecha("ddMMyyHHmmssSSS",fecha);
			try {
				gestorDocumentos.guardarFichero(ConstantesGestorFicheros.IVTM, ConstantesGestorFicheros.IVTM_MATRICULAS_WS,new Fecha(fecha), nombreFichero, ".soap", msgContext.getRequestMessage().getSOAPPartAsBytes());
			} catch (OegamExcepcion e) {
				log.error("No se ha podido guardar el fichero con la petición");
			}
		} catch (Exception e) {
			log.error("Ocurrió un error validando la firma de la peticion de matriculaWS", e);
			throw AxisFault.makeFault(e);
		}
	}
}
