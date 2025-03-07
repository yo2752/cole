package org.gestoresmadrid.procesos.model.jobs;

import java.math.BigDecimal;

import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesBastidorVO;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.TipoBastidor;
import org.gestoresmadrid.oegam2comun.datosSensibles.model.service.ServicioDatosSensibles;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.dto.RespuestaDatosSensibles;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import trafico.utiles.UtilesWSMatw;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;
import colas.constantes.ConstantesProcesos;

public class ProcesoDatosSensibles extends AbstractProcesoBase {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoDatosSensibles.class);

	@Autowired
	private ServicioDatosSensibles servicioDatosSensiblesImpl;

	@Override
	protected void doExecute() throws JobExecutionException {
		ColaBean solicitud = null;

		try{
			//Cargamos los almacenes de certificados que están en la máquina, en la carpeta datos/oegam/keystore.
			new UtilesWSMatw().cargarAlmacenesTrafico();
			log.info(" --INICIO PROCESOSEA_ENVIO_DS--");
			log.info(" --Buscando Solicitud--");
			solicitud = tomarSolicitud();
			RespuestaDatosSensibles respuesta = null;
			if (solicitud == null) {
				sinPeticiones();
			} else {
				String resultadoEjecucion = null;
				String excepcion = null;
				if (solicitud.getXmlEnviar() == null) {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					excepcion = "No existen el xml de envio.";
					log.info("La Solicitud " + solicitud.getIdTramite() + " no contiene xml de envio.");
					try {
						finalizarTransaccionConErrorNoRecuperable(solicitud, excepcion);
					} catch (BorrarSolicitudExcepcion e) {
						log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e.toString());
						resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
						excepcion = e.toString();
					}
				} else {
					DatosSensiblesBastidorVO datosSensiblesBastidorVO = servicioDatosSensiblesImpl.getDatosSensibles(solicitud.getXmlEnviar());
					if (datosSensiblesBastidorVO != null) {
						respuesta = servicioDatosSensiblesImpl.getllamadaWSDatosSensibles(datosSensiblesBastidorVO, solicitud);
						if (respuesta == null) {
							resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
							solicitud.setRespuesta(ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO);
							finalizarTransaccionCorrecta(solicitud, resultadoEjecucion);
						} else if (respuesta.getException() != null) {
							throw respuesta.getException();
						} else if (respuesta.isError()) {
							// Ocurrió un error en el servicio
							resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
							solicitud.setRespuesta(respuesta.getMensajeError());
							try {
								finalizarTransaccionConErrorNoRecuperable(solicitud, respuesta.getMensajeError());
							} catch (BorrarSolicitudExcepcion e) {
								log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e.toString());
								resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
								excepcion = e.toString();
							}
						}
					} else {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						String[] xml = solicitud.getXmlEnviar().split("_"); 
						solicitud.setRespuesta("No existen datos en la base de datos para el bastidor: " + xml[0] + " y el grupo " + xml[1]);
						try {
							finalizarTransaccionConErrorNoRecuperable(solicitud, solicitud.getRespuesta());
						} catch (BorrarSolicitudExcepcion e) {
							log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e.toString());
							resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
							excepcion = e.toString();
						}
					}
				}
				actualizarUltimaEjecucion(solicitud, resultadoEjecucion, excepcion);
			}
		} catch (Exception e) {
			log.error("Ocurrio un error no controlado en el proceso de datos sensibles de SEA_ENVIO_DS", e);
			String messageException = ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + (e.getMessage() != null ? e.getMessage() : e.toString());
			if (solicitud != null && solicitud.getProceso() != null) {
				DatosSensiblesBastidorVO datosSensiblesBastidorVO = servicioDatosSensiblesImpl.getDatosSensibles(solicitud.getXmlEnviar());
				String accion = "";
				accion = datosSensiblesBastidorVO.getFechaBaja() != null ? "Baja" : "Alta";

				String tipoBastidor = "";
				if (TipoBastidor.VO.getValorEnum().equals(datosSensiblesBastidorVO.getTipoBastidor())) {
					tipoBastidor = TipoBastidor.VO.getNombreEnum();
				} else if (TipoBastidor.DM.getValorEnum().equals(datosSensiblesBastidorVO.getTipoBastidor())) {
					tipoBastidor = TipoBastidor.DM.getNombreEnum();
				} else if (TipoBastidor.VN.getValorEnum().equals(datosSensiblesBastidorVO.getTipoBastidor())) {
					tipoBastidor = TipoBastidor.VN.getNombreEnum();
				} else if (TipoBastidor.FI.getValorEnum().equals(datosSensiblesBastidorVO.getTipoBastidor())) {
					tipoBastidor = TipoBastidor.FI.getNombreEnum();
				} else if (TipoBastidor.LE.getValorEnum().equals(datosSensiblesBastidorVO.getTipoBastidor())) {
					tipoBastidor = TipoBastidor.LE.getNombreEnum();
				} else if (TipoBastidor.RE.getValorEnum().equals(datosSensiblesBastidorVO.getTipoBastidor())) {
					tipoBastidor = TipoBastidor.RE.getNombreEnum();
				}
				try {
					finalizarTransaccionErrorRecuperable(solicitud, messageException +
					" al dar de " + accion + " el bastidor " + datosSensiblesBastidorVO.getId().getBastidor() + " de tipo " + tipoBastidor);
				} catch (BorrarSolicitudExcepcion e1) {
					log.error("Error al borrar la solicitud: " + solicitud.getIdEnvio() + ", Error: " + e1.toString());
				}

				actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, messageException +
						" al dar de " + accion + " el bastidor " + datosSensiblesBastidorVO.getId().getBastidor() + " de tipo " + tipoBastidor);
			} else {
				log.error("Se ha producido un error y no se ha podido recibir la solicitud");
			}
		} catch (OegamExcepcion e) {
			log.error("Error al cargar los almacenes de Trafico: ", e);
		}
	}
	
	protected void finalizarTransaccionErrorRecuperable(ColaBean cola, String respuestaError) throws BorrarSolicitudExcepcion {
		BigDecimal numIntentos = getNumeroIntentos(cola.getProceso());
		if (cola.getNumeroIntento().intValue() < numIntentos.intValue()) {
			getModeloSolicitud().errorSolicitud(cola.getIdEnvio(), respuestaError);
			peticionRecuperable();
		} else {
			marcarSolicitudConErrorServicio(cola, respuestaError);
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.SEA_ENVIO_DS.getNombreEnum();
	}

	/**
	 * @return the servicioDatosSensiblesImpl
	 */
	public ServicioDatosSensibles getServicioDatosSensiblesImpl() {
		return servicioDatosSensiblesImpl;
	}

	/**
	 * @param servicioDatosSensiblesImpl the servicioDatosSensiblesImpl to set
	 */
	public void setServicioDatosSensiblesImpl(ServicioDatosSensibles servicioDatosSensiblesImpl) {
		this.servicioDatosSensiblesImpl = servicioDatosSensiblesImpl;
	}

}