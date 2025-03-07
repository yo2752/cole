package trafico.modelo;

import java.math.BigDecimal;

import oegam.constantes.ConstantesPQ;

import trafico.beans.AccionBean;
import trafico.beans.daos.pq_accion.BeanPQCERRAR;
import trafico.beans.daos.pq_accion.BeanPQCREAR;
import trafico.beans.utiles.AccionBeanPQConversion;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.AccionException;

public class ModeloAcciones {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloAcciones.class);

	/**
	 * Método para crear acciones con paso de parámetros.
	 * @param idUsuario
	 * @param numExpediente
	 * @param accion
	 * @return
	 * @throws AccionException
	 */
	public AccionBean crearAccion(BigDecimal idUsuario, BigDecimal numExpediente, String accion) throws AccionException{

		BeanPQCREAR beanPQcrearAccion = new BeanPQCREAR();
		AccionBean accionBean = new AccionBean();
		//TODO Tener en cuenta la posibilidad de comprobar antes de crear la acción que no esté ya creada.
		log.debug("INFO: Usuario con id: " + idUsuario + ", crea una acción: " + accion);
		beanPQcrearAccion.setP_ACCION(accion);
		beanPQcrearAccion.setP_ID_USUARIO(idUsuario);
		beanPQcrearAccion.setP_NUM_EXPEDIENTE(numExpediente);

		beanPQcrearAccion.execute();

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = beanPQcrearAccion.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQcrearAccion.getP_SQLERRM());

		if(!pCodeTramite.toString().equals("0")){
			throw new AccionException(pCodeTramite+ ": " + beanPQcrearAccion.getP_SQLERRM());
		}else{
			accionBean = AccionBeanPQConversion.convertirBeanPQToAccionBean(beanPQcrearAccion);
		}

		return accionBean;
	}

	/**
	 * Método que buscará según unos parámetros que le pasemos
	 * @return
	 * @throws AccionException 
	 */
	public AccionBean cerrarAccion(String accion, BigDecimal idUsuario, BigDecimal numExpediente, String respuesta) throws AccionException{

		BeanPQCERRAR beanPQ = new BeanPQCERRAR();
		AccionBean accionBeanSalida = new AccionBean();

		log.debug("INFO: Usuario con id: " + idUsuario + ", cierra una acción: " + accion);
		beanPQ.setP_ACCION(accion);
		beanPQ.setP_ID_USUARIO(idUsuario);
		beanPQ.setP_NUM_EXPEDIENTE(numExpediente);
		beanPQ.setP_RESPUESTA(respuesta);

		beanPQ.execute();

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = beanPQ.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQ.getP_SQLERRM());

		if(!pCodeTramite.toString().equals("0")){
			throw new AccionException(pCodeTramite+ ": " + beanPQ.getP_SQLERRM());
		}else{
			accionBeanSalida = AccionBeanPQConversion.convertirBeanPQToAccionBean(beanPQ);
		}

		return accionBeanSalida;
	}
}