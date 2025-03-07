package trafico.ajax.daos;

import java.math.BigDecimal;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import general.beans.RespuestaGenerica;
import general.modelo.ModeloGenerico;
import oegam.constantes.ValoresSchemas;
import trafico.beans.daos.BeanPQGeneral;
import trafico.beans.daos.BeanPQVehiculosGuardar;
import utilidades.constantes.ValoresCatalog;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class DaoAjax {

	// log de errores
	private static final ILoggerOegam log = LoggerOegam.getLogger(DaoAjax.class);

	@Autowired
	ValoresSchemas valoresSchemas;

	public DaoAjax() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/**
	 * Método que recibe los objetos query y transaction, intenta ejecutar un update en BBDD y devuelve una respuesta verdadera o falsa
	 * @param query
	 * @param transaction
	 * @return
	 */
	public static boolean ejecutarUpdate(Query query, Transaction transaction, String mensajeFallo) {
		boolean resultadoGuardar = false;

		try {
			query.executeUpdate();
			transaction.commit();
			resultadoGuardar = true;
		} catch (HibernateException e) {
			log.error(mensajeFallo);
			transaction.rollback();
			resultadoGuardar = false;
		}

		return resultadoGuardar;
	}

	public static boolean ejecutarTransacciones(Transaction tx) throws Exception {
		boolean resultado;

		try {
			tx.commit();
			resultado = true;
		} catch(RuntimeException re) {
			try {
				tx.rollback();
				log.error("Se ha ejecutado el rollback: " + re);
				System.err.println("Se ha ejecutado el rollback: " + re);
			} catch (RuntimeException rbe) {
				log.error("No se ha podido ejecutar el rollback: " + rbe);
			}
			resultado = false;
		}
		return resultado;
	}

	public RespuestaGenerica calcularFechaCaducidadItv(String numExpediente) {
		BeanPQVehiculosGuardar beanPQVehiculosGuardar = new BeanPQVehiculosGuardar();
		beanPQVehiculosGuardar.setP_NUM_EXPEDIENTE(new BigDecimal(numExpediente));
		return new ModeloGenerico().ejecutarProc(beanPQVehiculosGuardar, valoresSchemas.getSchema(), ValoresCatalog.PQ_VEHICULOS, "CALCULAR_CADUCIDAD_ITV_MATW", BeanPQGeneral.class);
	}
}