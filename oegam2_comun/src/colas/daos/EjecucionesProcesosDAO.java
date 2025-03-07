package colas.daos;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.EnvioDataPK;
import org.gestoresmadrid.core.general.model.vo.EnvioDataVO;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.envioData.model.service.ServicioEnvioData;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import colas.constantes.ConstantesProcesos;
import trafico.beans.EnvioDataPantalla;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Repository
public class EjecucionesProcesosDAO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9174011042350098400L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(EjecucionesProcesosDAO.class);

	@Autowired
	UtilesFecha utilesFecha;
	
	@Autowired
	ServicioProcesos servicioProcesos;

	@Autowired
	ServicioEnvioData servicioEnvioData;
	
	/**
	 * Recupera un registro con la ultima ejecución del tipo que se indica en el parámetro
	 * del proceso que se indica en el otro parametro
	 * @param el tipo de ejecución que se desea recuperar de los procesos
	 * @param el proceso cuya ejecución se quiere recuperar
	 * @return Lista de EnvioData
	 */
	public List<EnvioDataVO> recuperarUltimasEjecuciones(String proceso, String tipoEjecucion) throws Exception{

		return servicioProcesos.recuperarUltimasEjecuciones(proceso, tipoEjecucion);

	}
	
	public ArrayList<EnvioDataPantalla> recuperarUltimasOk(String proceso,String cola) throws Exception{
				
		List<EnvioDataVO> listaEjecuciones = servicioProcesos.recuperarUltimasOk(proceso, cola);
		ArrayList<EnvioDataPantalla> listaEnvioDataPantalla = EnvioDataPantalla.convertir(listaEjecuciones);
		return listaEnvioDataPantalla;

	}
	
	/**
	 * Recupera las ultimas ejecuciones correcta con error y excepción de un proceso que se recibe 
	 * como parametro
	 * @param proceso
	 * @return la lista de registros de envio data de un proceso
	 * @throws Exception
	 */
	public ArrayList<EnvioDataPantalla> recuperarUltimasEjecucionesProceso(String proceso) throws Exception{
		
		List<EnvioDataVO> listaEjecuciones = servicioProcesos.recuperarUltimasEjecuciones(proceso, null);
		ArrayList<EnvioDataPantalla> listaEnvioDataPantalla = EnvioDataPantalla.convertir(listaEjecuciones);
		
		return listaEnvioDataPantalla;
		
	}
	public EnvioDataPantalla ejecucionesUltimasProceso(String proceso, String correcta){
		
		List<EnvioDataVO> listaBBDD =  servicioProcesos.ejecucionesUltimasProceso(proceso, correcta);
		if(listaBBDD != null && !listaBBDD.isEmpty()){
			return new EnvioDataPantalla(listaBBDD.get(0));
		}
		return null;
		
	}
	/**
	 * Inserta un registro del proceso de la última ejecución del tipo especificado en el parámetro
	 * @param colaBean
	 * @param resultadoEjecucion
	 */
	public void actualizarUltimaEjecucion(ColaBean colaBean, String correcta, String respuesta, String proceso){

		if(colaBean.getProceso() !=null){
			proceso = colaBean.getProceso();
		}
		
		String cola = null;
		if(colaBean != null){
			if(colaBean.getCola() != null && !colaBean.getCola().isEmpty()){
				cola = colaBean.getCola();
			}else{
				cola = "0";
			}
		}
		List<EnvioDataVO> listaEnvioData = servicioEnvioData.getListaUltimoEnvioPorEjecucion(proceso, cola, correcta, null, null);
		if(listaEnvioData.isEmpty()){
			EnvioDataVO envioDataNuevo = new EnvioDataVO();
			EnvioDataPK envioDataPk = new EnvioDataPK();
			envioDataPk.setCorrecta(correcta);
			envioDataNuevo.setFechaEnvio(new Date());
			String proceso2=null;
			if(colaBean.getProceso() != null && !colaBean.getProceso().isEmpty()){
				proceso2 = colaBean.getProceso();
			}else{
				proceso2 = proceso;
			}
			envioDataPk.setProceso(proceso2);
			envioDataPk.setCola(cola);
			envioDataNuevo.setNumExpediente(colaBean.getIdTramite());
			envioDataNuevo.setId(envioDataPk);
			if(respuesta !=null && respuesta.equals(ConstantesProcesos.EJECUCION_EXCEPCION)){
				envioDataNuevo.setRespuesta(respuesta);
			}else{
				envioDataNuevo.setRespuesta(respuesta);
			}
			envioDataNuevo  = servicioEnvioData.actualizarEnvioData(envioDataNuevo);
			if(envioDataNuevo==null){
				System.err.println("Error al actualizar la ultima ejecución con Excepcion en la tabla ENVIO_DATA: ");	
				log.error("Error al actualizar la ultima ejecución con Excepcion en la tabla ENVIO_DATA: ");
			}
		}else{
			EnvioDataVO envioData = listaEnvioData.get(0);
			envioData.setFechaEnvio(new Date());
			EnvioDataPK envioDataPk = new EnvioDataPK();
			String proceso2=null;
			if(colaBean.getProceso() != null && !colaBean.getProceso().isEmpty()){
				proceso2 = colaBean.getProceso();
			}else{
				proceso2 = proceso;
			}
	
			envioDataPk.setProceso(proceso2);
			envioDataPk.setCola(cola);
			envioData.setNumExpediente(colaBean.getIdTramite());
			envioDataPk.setCorrecta(correcta);
			envioData.setId(envioDataPk);
			if(respuesta !=null &&  respuesta.equals(ConstantesProcesos.EJECUCION_EXCEPCION)){
				envioData.setRespuesta(respuesta);
			}else{
				envioData.setRespuesta(respuesta);
			}
			envioData = servicioEnvioData.actualizarEnvioData(envioData);
			if(envioData == null) {
				System.err.println("Error al actualizar la ultima ejecución con Excepcion en la tabla ENVIO_DATA: ");	
				log.error("Error al actualizar la ultima ejecución con Excepcion en la tabla ENVIO_DATA: ");
			}
		}

	}
	
	public void ejecutarTransacciones (Transaction tx) throws Exception {
		try {
			tx.commit();
		} catch(RuntimeException re){
			try {
				tx.rollback();
				log.error("Se ha ejecutado el rollback: " + re);
				System.err.println("Se ha ejecutado el rollback: " + re);
			} catch (RuntimeException rbe) {
				log.error("No se ha podido ejecutar el rollback: " + rbe);
			}
		} 
	}
	
	/**
	 * Inserta un registro del proceso de la última ejecución del tipo especificado en el parámetro
	 * @param colaBean
	 * @param resultadoEjecucion
	 */
	public void actualizarUltimaEjecucion(String proceso,String respuesta, String resultadoEjecucion){
		
		List<EnvioDataVO> listaEnvioData = servicioProcesos.ejecucionesUltimasProceso(proceso, resultadoEjecucion);
		
		if(listaEnvioData.isEmpty()){
			EnvioDataVO envioDataNuevo = new EnvioDataVO();
			EnvioDataPK envioDataPk = new EnvioDataPK();
			envioDataPk.setCorrecta(resultadoEjecucion);
			envioDataNuevo.setFechaEnvio(new Date());
			envioDataPk.setProceso(proceso);
			envioDataNuevo.setRespuesta(respuesta);
			envioDataNuevo.setId(envioDataPk);
			envioDataNuevo = servicioEnvioData.actualizarEnvioData(envioDataNuevo);
			if(envioDataNuevo == null) {
				System.err.println("Error: Error al actualizar la ultima ejecución con Excepcion en la tabla ENVIO_DATA");	
				log.error("Error al actualizar la ultima ejecución con Excepcion en la tabla ENVIO_DATA");
			}
		}else{
			EnvioDataVO envioData = listaEnvioData.get(0);
			envioData.setFechaEnvio(new Date());
			EnvioDataPK envioDataPk = new EnvioDataPK();
			envioDataPk.setProceso(proceso);
			envioDataPk.setCorrecta(resultadoEjecucion);
			envioData.setRespuesta(respuesta);
			envioData.setId(envioDataPk);
			envioData = servicioEnvioData.actualizarEnvioData(envioData);
			if(envioData == null) {
				System.err.println("Error: Error al actualizar la ultima ejecución con Excepcion en la tabla ENVIO_DATA ");	
				log.error("Error al actualizar la ultima ejecución con Excepcion en la tabla ENVIO_DATA");
			}
		}

	}
	
	/**
	 * Inserta un registro del proceso de la última ejecución del tipo especificado en el parámetro
	 * @param colaBean
	 * @param resultadoEjecucion
	 */
	public void cambiarFechaEnvioData(String proceso,String respuesta, String resultadoEjecucion,String dia){

		List<EnvioDataVO> listaEnvioData = servicioProcesos.ejecucionesUltimasProceso(proceso, resultadoEjecucion);
		
		if(listaEnvioData.isEmpty()){
			EnvioDataVO envioDataNuevo = new EnvioDataVO();
			EnvioDataPK envioDataPk = new EnvioDataPK();
			envioDataPk.setCorrecta(resultadoEjecucion);
			if(dia.equals("Hoy"))
				envioDataNuevo.setFechaEnvio(new Date());
			else if(dia.equals("Anterior"))
				try {
					envioDataNuevo.setFechaEnvio(utilesFecha.getPrimerLaborableAnterior(utilesFecha.getFechaFracionada(new Date())).getFecha());
				} catch (ParseException e1) {
					log.error("Error al cambiar fecha de envio de data. EjecucionesProcesosDao ", e1);
				} catch (Throwable e1) {
					log.error("Error al cambiar fecha de envio de data. EjecucionesProcesosDao ", e1);
				}
			envioDataPk.setProceso(proceso);
			envioDataNuevo.setRespuesta(respuesta);
			envioDataNuevo.setId(envioDataPk);
			envioDataNuevo = servicioEnvioData.actualizarEnvioData(envioDataNuevo);
			if(envioDataNuevo == null) {
				System.err.println("Error: Error al actualizar la ultima ejecución con Excepcion en la tabla ENVIO_DATA ");	
				log.error("Error al actualizar la ultima ejecución con Excepcion en la tabla ENVIO_DATA ");
			}
		}else{
			EnvioDataVO envioData = listaEnvioData.get(0);
			if(dia.equals("Hoy"))
				envioData.setFechaEnvio(new Date());
			else if(dia.equals("Anterior"))
				try {
					envioData.setFechaEnvio(utilesFecha.getPrimerLaborableAnterior(utilesFecha.getFechaFracionada(new Date())).getFecha());
				} catch (ParseException e1) {
					log.error("Error al cambiar fecha de envio de data. EjecucionesProcesosDao ", e1);
				} catch (Throwable e1) {
					log.error("Error al cambiar fecha de envio de data. EjecucionesProcesosDao ", e1);
				}
			envioData = servicioEnvioData.actualizarEnvioData(envioData);
			if(envioData == null) {
				System.err.println("Error: Error al actualizar la ultima ejecución con Excepcion en la tabla ENVIO_DATA ");	
				log.error("Error al actualizar la ultima ejecución con Excepcion en la tabla ENVIO_DATA ");
			}
		}

	}

	/**
	 * Metodo que lista todas las filas Envio Data segun el tipo de ejecucion que se le pase
	 * @param tipoEjecucion
	 * @return
	 * @throws Exception
	 */
	public List<EnvioDataVO> listaEnvioData(String tipoEjecucion) throws Exception{

		return  servicioEnvioData.getlistaEnvioData(tipoEjecucion);
		
	}
	
}