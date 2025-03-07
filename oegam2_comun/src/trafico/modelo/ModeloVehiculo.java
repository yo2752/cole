package trafico.modelo;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.hibernate.util.HibernateBean;
import org.gestoresmadrid.core.hibernate.util.HibernateUtil;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioDirectivaCee;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import general.beans.RespuestaGenerica;
import general.modelo.ModeloGenerico;
import hibernate.entities.trafico.Vehiculo;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ValoresSchemas;
import trafico.beans.VehiculoBean;
import trafico.beans.daos.BeanPQGeneral;
import trafico.beans.daos.BeanPQVehiculoDetalle;
import trafico.beans.utiles.VehiculoBeanPQConversion;
import trafico.dao.implHibernate.VehiculoDAOImplHibernate;
import utilidades.constantes.ValoresCatalog;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
/**
 * Clase del modelo para el vehículo.
 * @author juan.gomez
 *
 */

public class ModeloVehiculo extends ModeloGenerico{
	private static ILoggerOegam log = LoggerOegam.getLogger(ModeloVehiculo.class);

	@Autowired
	private ServicioDirectivaCee servicioDirectivaCee;

	@Autowired
	VehiculoBeanPQConversion vehiculoBeanPQConversion;

	@Autowired
	ValoresSchemas valoresSchemas;

	public ModeloVehiculo() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/**
	 * Método estático del modelo de vehículo que permitirá obtener el detalle de un vehículo
	 * pasándole el id del mismo.
	 * @param idVehiculo
	 * @return VehiculoBean
	 */
	public VehiculoBean cargaVehiculoPorId(BigDecimal idVehiculo) {
		BeanPQVehiculoDetalle beanVehiculoConsulta = new BeanPQVehiculoDetalle();
		beanVehiculoConsulta.setP_ID_VEHICULO(idVehiculo);

		RespuestaGenerica resultado = ejecutarProc(beanVehiculoConsulta, valoresSchemas.getSchema(), ValoresCatalog.PQ_VEHICULOS,"DETALLE", BeanPQGeneral.class);

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = (BigDecimal)resultado.getParametro(ConstantesPQ.P_CODE);
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultado.getParametro(ConstantesPQ.P_SQLERRM));
		log.debug(ConstantesPQ.LOG_P_CUENTA + resultado.getParametro("CUENTA"));

		return vehiculoBeanPQConversion.convertirPQToBean(resultado);
	}

	public VehiculoBean cargaVehiculoPorIdMatw(BigDecimal idVehiculo) {
		BeanPQVehiculoDetalle beanVehiculoConsulta = new BeanPQVehiculoDetalle();
		beanVehiculoConsulta.setP_ID_VEHICULO(idVehiculo);

		RespuestaGenerica resultado = ejecutarProc(beanVehiculoConsulta, valoresSchemas.getSchema(), ValoresCatalog.PQ_VEHICULOS,"DETALLE", BeanPQGeneral.class);

		// Recuperamos información de respuesta
		BigDecimal pCodeTramite = (BigDecimal)resultado.getParametro(ConstantesPQ.P_CODE);
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultado.getParametro(ConstantesPQ.P_SQLERRM));
		log.debug(ConstantesPQ.LOG_P_CUENTA + resultado.getParametro("CUENTA"));

		VehiculoBean vehiculo = vehiculoBeanPQConversion.convertirPQToBean(resultado);

		if (vehiculo != null) {
			vehiculo.setHomologacionBean(servicioDirectivaCee.getHomologacionBean((String) resultado.getParametro("P_ID_DIRECTIVA_CEE")));
			if (vehiculo.getDireccionBean() != null && resultado.getParametro("P_ID_DIRECCION") != null) {
				vehiculo.getDireccionBean().setCodPostalCorreos((String) resultado.getParametro("P_COD_POSTAL_CORREOS"));
				vehiculo.getDireccionBean().setPuebloCorreos((String) resultado.getParametro("P_PUEBLO_CORREOS"));
			}
		}

		return vehiculo;
	}

	public Long getNextIdVehiculo(){
		Long next = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			List<?> list = session.createSQLQuery("select ID_VEHICULO_SEQ.nextval from dual").list();
			if (list!= null && !list.isEmpty()) {
				Object temp = list.get(0);
				if (Number.class.isAssignableFrom(temp.getClass())) {
					next = ((Number) temp).longValue();
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return next;
	}

	/**
	 * Método que actualiza el campo bastidor matriculado para el vehículo
	 * asociado a un determinado trámite de matriculación
	 * @param numExpediente
	 * @param valor
	 * @return actualizar
	 */
	public boolean updateBastidorMatriculado(Long numExpediente, Integer valor) {

		boolean actualizar = false;
		VehiculoDAOImplHibernate dao = new VehiculoDAOImplHibernate();
		Vehiculo vehiculo = null;

		// Obtenemos el vehículo
		HibernateBean hb = HibernateUtil.createCriteria(Vehiculo.class);

		hb.getCriteria().createCriteria("tramiteTrafico", "tramiteTrafico");
		hb.getCriteria().add(Restrictions.eq("tramiteTrafico.numExpediente", numExpediente));

		try {
			vehiculo = (Vehiculo) hb.getCriteria().uniqueResult();
		} catch (HibernateException e) {
			log.error(e.getMessage());
		}

		vehiculo.setBastidorMatriculado(valor);

		// Actualizamos el bastidor
		dao.actualizar(vehiculo);

		return actualizar;
	}

}