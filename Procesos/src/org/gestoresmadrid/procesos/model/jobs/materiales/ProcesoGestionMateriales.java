package org.gestoresmadrid.procesos.model.jobs.materiales;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.ComandosMaterial;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioWebServicePedidos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProcesoBase;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;

public class ProcesoGestionMateriales extends AbstractProcesoBase {
	
	private ILoggerOegam log = LoggerOegam.getLogger(ProcesoGestionMateriales.class);
	
	public static final String MSG_NO_TYPE_CONSULTE    = "No se ha recuperado el tipo de consulta materiales correspondiente a la solicitud con identificador: ";
	public static final String MSG_ERROR_DELETE_SOL    = "Error al borrar la solicitud: %d , Error: %s";
	public static final String MSG_ERROR_GENERICO      = "Se ha producido un error y no se ha podido recibir la solicitud";
	public static final String MSG_ERROR_NO_CONTROLADO = "Ocurrio un error no controlado en el proceso de materiales %s";
	
	public static final int success = 0;
	public static final int error   = 1;
	
	@Autowired ServicioWebServicePedidos servicioWebServicePedidos;

	@Override
	protected void doExecute() throws JobExecutionException {
		
		ColaBean colaBean = null;
		try {
			colaBean = tomarSolicitud();
			if (colaBean == null) {
				sinPeticiones();
			} else {
				String resultadoEjecucion = null;
				String excepcion = null;

				if (colaBean.getXmlEnviar() == null || colaBean.getXmlEnviar().isEmpty()) {
					resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
					excepcion =  MSG_NO_TYPE_CONSULTE + colaBean.getIdEnvio();
					log.error(excepcion);
					try {
						finalizarTransaccionConErrorNoRecuperable(colaBean, excepcion);
					} catch (BorrarSolicitudExcepcion e) {
						log.error(String.format(MSG_ERROR_DELETE_SOL, colaBean.getIdEnvio(), e.toString()));
						resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
						excepcion = e.toString();
					}
				} else {
					ResultBean resultBean = null;
					
					String xml = colaBean.getXmlEnviar();
					if (xml.indexOf(ComandosMaterial.PEDIDOCREAR.getNombreEnum()) > -1) {
						resultBean = servicioWebServicePedidos.generarCrearPedido(colaBean);
					} else if (xml.indexOf(ComandosMaterial.INCIDENCIACREAR.getNombreEnum()) > -1) {
						resultBean = servicioWebServicePedidos.generarCrearIncidencia(colaBean);
					} else if (xml.indexOf(ComandosMaterial.PEDIDODETALLE.getNombreEnum()) > -1) {
						resultBean = servicioWebServicePedidos.generarModificarPedido(colaBean);
					} else if (xml.indexOf(ComandosMaterial.INCIDENCIAUPDATE.getNombreEnum()) > -1) {
						resultBean = servicioWebServicePedidos.generarUpdateIncidencia(colaBean);
					} else if (xml.indexOf(ComandosMaterial.INFOPEDIDO.getNombreEnum()) > -1) {
						resultBean = servicioWebServicePedidos.generarInfoPedido(colaBean);
					} else if (xml.indexOf(ComandosMaterial.INFOSTOCK.getNombreEnum()) > -1) {
						resultBean = servicioWebServicePedidos.generarInfoStock(colaBean);
					} else if (xml.indexOf(ComandosMaterial.UPDATESTOCK.getNombreEnum()) > -1) {
						resultBean = servicioWebServicePedidos.generarActualizarStock(colaBean);
					}
					
					String mensaje = "";
					if (resultBean.getError()) {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						mensaje = (String) resultBean.getMensaje();
						colaBean.setRespuesta(mensaje);
						try {
							finalizarTransaccionConErrorNoRecuperable(colaBean,mensaje);
						} catch (BorrarSolicitudExcepcion e) {
							log.error(String.format(MSG_ERROR_DELETE_SOL, colaBean.getIdEnvio(), e.toString()));
							resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
							excepcion = e.toString();
						}
						
					} else {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						colaBean.setRespuesta(ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO);
						finalizarTransaccionCorrecta(colaBean, resultadoEjecucion);
					}
					
					actualizarUltimaEjecucion(colaBean, resultadoEjecucion, excepcion);
					
 				}
			}
			
		} catch(Exception e) {
			log.error(String.format(MSG_ERROR_DELETE_SOL, e.toString()));
			String messageException = e.getMessage() != null ? e.getMessage() : e.toString();
			if (colaBean != null && colaBean.getProceso() != null) {
				try {
					if (messageException.length() > 500) {
						messageException = messageException.substring(0, 500);
					}
					finalizarTransaccionErrorRecuperableConErrorServico(colaBean, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + messageException);
				} catch (BorrarSolicitudExcepcion e1) {
					log.error(String.format(MSG_ERROR_DELETE_SOL, colaBean.getIdEnvio(), e.toString()));
				}
				actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, messageException);
			} else {
				log.error(MSG_ERROR_GENERICO);
			}
		} 
		

	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.MATERIALES.getNombreEnum();
	}

}
