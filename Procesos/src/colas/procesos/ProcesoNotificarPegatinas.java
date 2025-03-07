package colas.procesos;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasNotificaVO;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.pegatinas.model.dto.RespuestaNotificacionImpresas;
import org.gestoresmadrid.oegam2comun.pegatinas.model.dto.RespuestaNotificacionInvalidos;
import org.gestoresmadrid.oegam2comun.pegatinas.model.service.ServicioPegatinasTransactional;
import org.gestoresmadrid.oegam2comun.pegatinas.model.service.ServicioRestPegatinas;
import org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados.UtilesPegatinas;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.procesos.model.jobs.AbstractProcesoBase;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import escrituras.beans.ResultBean;
import trafico.utiles.UtilesWSMatw;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ProcesoNotificarPegatinas extends AbstractProcesoBase {

	
	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoNotificarPegatinas.class);
	
	
	private static final String RECIPIENT = "pegatinas.notificar.correo.recipient";
	private static final String SUBJECT = "pegatinas.notificar.correo.subject";
	private static final String PRODUCCION = "PRODUCCION";
	private static final String NOTIFICAR_IMPRESION = "notificarImpresion";
	private static final String NOTIFICAR_INVALIDOS = "notificarInvalidos";
	private static final String SPAN = "<span>";
	private static final String BR = "</br>";
	private static final String CERO = "0";
	
	
	@Autowired
	private ServicioPegatinasTransactional servicioPegatinasTransactional;
	
	@Autowired
	private ServicioRestPegatinas servicioRestPegatinas;
		
	@Autowired
	private ServicioCorreo servicioCorreo;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	private SimpleDateFormat format;
	
	public ProcesoNotificarPegatinas(){
		format = new SimpleDateFormat("dd-MM-yyyy");
	}
	
	@Override
	protected void doExecute() throws JobExecutionException {
		
		log.info("Proceso " + ProcesosEnum.PEGATINAS_NOTIFICAR + " -- Procesando petición");

		try{
			
			/**
			 * 1.- Recuperamos todas las notificaciones del dia de hoy y en funcion del tipo lo metemos en el array correspondiente
			 * 2.- Llamamos al WS que corresponda para notificar las impresiones 
			 * 3.- Enviamos correo de notificacion al colegio con el numero de impresiones y el resultado del WS
			 */
			
			if (utilesFecha.esFechaLaborable(true)){
				if (!hayEnvioData(getProceso())) {
			
					JefaturasJPTEnum[] jefaturas = UtilesPegatinas.getJefaturas();
					for(JefaturasJPTEnum jefatura : jefaturas) {
					
						new UtilesWSMatw().cargarAlmacenesTrafico();
						List<PegatinasNotificaVO> listaNotificaciones = servicioPegatinasTransactional.getAllPegatinasHoyByJefatura(jefatura.getJefatura());		
						List<PegatinasNotificaVO> listaOK = new ArrayList<PegatinasNotificaVO>();
						List<PegatinasNotificaVO> listaKO = new ArrayList<PegatinasNotificaVO>();
			
						for (int i = 0; i < listaNotificaciones.size(); i++) {
							
							String accion = listaNotificaciones.get(i).getAccion();
							
							if(("IMPRESION CORRECTA").equals(accion)){
								listaOK.add(listaNotificaciones.get(i));				
							}else if (("NO VALIDO").equals(accion) || accion ==null ) {
								listaKO.add(listaNotificaciones.get(i));
							}
						}
							
						if(0!=listaOK.size() && null !=listaOK){
							
							RespuestaNotificacionImpresas respuesta = servicioRestPegatinas.notificarImpresion(NOTIFICAR_IMPRESION, listaOK, jefatura.getJefatura());
							
							if(CERO.equals(respuesta.getResultado())){
								enviarCorreo(listaOK, "LAS NOTIFICACIONES CORRECTAS ENVIADAS, HAN RECIBIDO UNA RESPUESTA OK", respuesta.getResultado(),respuesta.getMensaje());
							}else{
								enviarCorreo(listaOK, "LAS NOTIFICACIONES CORRECTAS ENVIADAS, HAN RECIBIDO UNA RESPUESTA KO", respuesta.getResultado(),respuesta.getMensaje());
							}
		
						}
						
						if(0!=listaKO.size() && null !=listaKO){
							
							RespuestaNotificacionInvalidos respuesta = servicioRestPegatinas.notificarInvalidos(NOTIFICAR_INVALIDOS, listaKO, jefatura.getJefatura());
							
							if(CERO.equals(respuesta.getResultado())){
								enviarCorreo(listaKO, "IMPRESIONES INVÁLIDAS", respuesta.getResultado(), respuesta.getMensaje());
							}else{
								enviarCorreo(listaKO, "IMPRESIONES INVÁLIDAS NO HAN SIDO ENVIADAS", respuesta.getResultado(),respuesta.getMensaje());
							}
							
						}
					}
					
					actualizarEjecucion(getProceso(), ConstantesProcesos.EJECUCION_CORRECTA, ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO, "0");
					
				}else{
					log.info("Proceso " + ProcesosEnum.PEGATINAS_NOTIFICAR + " -- El proceso ya se ha ejecutado para el día de hoy");
				}
						
				}else{
					log.info("Proceso " + ProcesosEnum.PEGATINAS_NOTIFICAR + " -- Día NO LABORABLE");
				}
		
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
			errorNoRecuperable();
			actualizarEjecucion(getProceso(), ConstantesProcesos.EJECUCION_EXCEPCION,ex.getMessage(), "0");
		} catch (OegamExcepcion e) {
			log.error(e.getMessage(),e);
			errorNoRecuperable();
			actualizarEjecucion(getProceso(), ConstantesProcesos.EJECUCION_EXCEPCION,e.getMessage(),"0");
		}
		
		sinPeticiones();

	}

	@Override
	protected String getProceso() {

		return ProcesosEnum.PEGATINAS_NOTIFICAR.getNombreEnum();
	}
	
	private void enviarCorreo(List<PegatinasNotificaVO> pegatinas, String tipo, int respuestaWS, String mensaje ){
		
		
		String subject = null;
		String recipient = null;
		String direccionesOcultas = null;
		Date hoy= new Date();

		
		StringBuilder texto = null;
		
		try{
			subject = gestorPropiedades.valorPropertie(SUBJECT);
			
			texto = new StringBuilder("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">");
			texto.append("Las siguientes matriculaciones han sido notificadas hoy "+ format.format(hoy) + " como " + tipo );
			texto.append(" <br>");
			
			for(PegatinasNotificaVO pegatina : pegatinas){
				texto.append(pegatina.getMatricula());
				texto.append(BR);
			}

			texto.append(SPAN);
			texto.append("La respuesta del WS ha sido : " + BR + respuestaWS + BR + mensaje + BR);
			recipient = gestorPropiedades.valorPropertie(RECIPIENT);
			String entorno = gestorPropiedades.valorPropertie("Entorno");
			if(!PRODUCCION.equals(entorno)){
				subject = entorno + ": " + subject;
			}
			
			ResultBean resultado;
			resultado = servicioCorreo.enviarCorreo(texto.toString(), null, null, subject, recipient, null, direccionesOcultas, null, null);
			
			if (resultado.getError()) {
				log.error("No se ha enviado el correo de la notificacion de impresiones de pegatinas. Error: " + resultado.getMensaje());
			}
			
		}catch (OegamExcepcion | IOException e){
			log.error("No se ha enviado el correo de la notificacion de impresiones de pegatinas", e);
		}
	}

}
