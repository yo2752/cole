package com.matriculaWS.signature;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.axis.AxisFault;
import org.apache.axis.MessageContext;
import org.apache.axis.handlers.BasicHandler;
import org.apache.log4j.Logger;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.matriculasWS.utiles.ConstantesWS;

import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

public class ServerRequestSigningHandler extends BasicHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(ServerRequestSigningHandler.class);
	
	@Autowired
	protected GestorDocumentos gestorDocumentos;
	
	@Autowired
	UtilesFecha utilesFecha;

	public ServerRequestSigningHandler() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public void invoke(MessageContext msgContext) throws AxisFault {
		try {
			log.info("Validando petición firmada");

			UtilesViafirma utilesViafirma = new UtilesViafirma();
			boolean coreValidity = utilesViafirma.validarFirmaXMLDSIG(msgContext.getRequestMessage().getSOAPPartAsBytes());

			if (!coreValidity) {
				log.info("La petición no pasa la validación de firma");
				throw new AxisFault("[" + ConstantesWS.Codigo_007 + "] - " + ConstantesWS.La_firma_no_es_valida);
			}
			String numeroNombreFichero  = "9999" + utilesFecha.formatoFecha("ddMMyyHHmmssSSS",new Date());
			try {
				gestorDocumentos.guardarFichero(ProcesosEnum.IVTM.toString(), 
						"MATRICULASWS",Utilidades.transformExpedienteFecha(new BigDecimal(numeroNombreFichero)), "IVTMMATRICULASWS_PET_"+numeroNombreFichero, ".soap", msgContext.getRequestMessage().getSOAPPartAsBytes());
			} catch (OegamExcepcion e) {
				log.error("No se ha podido guardar el fichero con la petición");
			}
		} catch (Exception e) {
			log.error("Ocurrió un error validando la firma de la peticion de matriculaWS", e);
			throw AxisFault.makeFault(e);
		}
	}
}