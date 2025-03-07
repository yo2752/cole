package com.registradoresWS.buildXml;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.log4j.Logger;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.registradoresWS.utilidades.Propiedades;
import com.registradoresWS.utilidades.Utiles;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import utilidades.web.OegamExcepcion;

/*
 * Para crear acuses o ack técnicos para cualquier respuesta que se obtenga
 * del intercambio de mensajes con el CORPME
 */
public class BuildACK {

	public BuildACK() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	// log de errores
	private static final Logger log = Logger.getLogger(BuildACK.class);

	// Debe recibir desde el objeto jaxb de la respuesta 4 parámetros de la informacion
	// contenida y la llamada se puede meter directamente en el return del web service
	// puesto que retorna el array de bytes de la cadena xml construida:
	public static byte[] obtenerACK(String tipoMensaje, String idTramite, BigInteger codigoRetorno, String descripcionRetorno, BigInteger tipoRegistro, String codigoRegistro, BigDecimal numExpediente)
			throws Exception {

		if (codigoRetorno != BigInteger.ZERO && !descripcionRetorno.equals("")) {
			log.info("SERVICIO WEB REGISTRADORES : Construyendo el ack de respuesta al mensaje : " + tipoMensaje + " para el tramite : " + idTramite + " con codigoRetorno = " + codigoRetorno
					+ " y descripcionRetorno = " + descripcionRetorno);
		} else {
			log.info("SERVICIO WEB REGISTRADORES : Construyendo el ack de respuesta al mensaje : " + tipoMensaje + " para el tramite : " + idTramite
					+ " con codigoRetorno = 0 y descripcionRetorno = \" \"");
		}

		StringBuilder cad = new StringBuilder();
		String encoding = "ISO-8859-1";
		cad.append("<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>\n" + "<CORPME-eDoc Version=\"" + Propiedades.leer(Propiedades.ACK_VERSION) + "\"" + " Tipo_Mensaje=\"" + tipoMensaje + "\""
				+ " Id_Tramite=\"" + idTramite + "\"" + " Tipo_Entidad=\"" + Propiedades.leer(Propiedades.TIPO_ENTIDAD) + "\"" + " Codigo_Entidad=\"" + Propiedades.leer(Propiedades.CODIGO_ENTIDAD)
				+ "\"" + " Tipo_Registro=\"" + tipoRegistro + "\"" + " Codigo_Registro=\"" + codigoRegistro + "\"" + " Codigo_Retorno=\"" + codigoRetorno.toString() + "\"" + " Descripcion_Retorno=\""
				+ descripcionRetorno + "\"" + "/>");

		// Añadiendo los atributos de esquemas:
		String hecha = cad.toString();
		hecha = Utiles.incorporarAtributos(hecha);

		if (numExpediente != null) { 
			GestorDocumentos gestorDocumentos = ContextoSpring.getInstance().getBean(GestorDocumentos.class);
			try {
				gestorDocumentos.guardarFichero(ConstantesGestorFicheros.REGISTRADORES, ConstantesGestorFicheros.REGISTRADORES_ACUSES_ENVIADOS, Utilidades.transformExpedienteFecha(numExpediente.toString()),
						idTramite + "_" + tipoMensaje.toUpperCase(), ConstantesGestorFicheros.EXTENSION_XML, hecha.getBytes());
			} catch (OegamExcepcion e) {
				log.error("SERVICIO WEB REGISTRADORES.BuildACK : Error. " + e.getMessage());
			}
		}

		log.info("SERVICIO WEB REGISTRADORES : Fin correcto de la construccion del ack");
		return hecha.getBytes();

	}

}
