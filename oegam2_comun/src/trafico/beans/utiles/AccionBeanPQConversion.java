package trafico.beans.utiles;

import java.sql.Timestamp;

import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import trafico.beans.AccionBean;
import trafico.beans.daos.pq_accion.BeanPQBUSCAR;
import trafico.beans.daos.pq_accion.BeanPQCERRAR;
import trafico.beans.daos.pq_accion.BeanPQCREAR;

public class AccionBeanPQConversion {

	@Autowired
	UtilesFecha utilesFecha;

	public AccionBeanPQConversion() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/**
	 * Método estático para convertir del beanPQCrearAccion hacia AccionBean
	 * @param beanPQcrearAccion
	 * @return
	 */
	public static AccionBean convertirBeanPQToAccionBean(BeanPQCREAR beanPQ) {

		AccionBean accionBean = new AccionBean();

		accionBean.setAccion(beanPQ.getP_ACCION());
		accionBean.setRespuesta(beanPQ.getP_INFORMACION());

		if (beanPQ.getP_ID_USUARIO()!= null) {
			accionBean.setIdUsuario(beanPQ.getP_ID_USUARIO());
		}

		if (beanPQ.getP_NUM_EXPEDIENTE()!= null) {
			accionBean.setNumExpediente(beanPQ.getP_NUM_EXPEDIENTE());
		}

		return accionBean;
	}

	/**
	 * Método estático para convertir del beanPQCerrar hacia AccionBean
	 * @param beanPQcrearAccion
	 * @return
	 */
	public static AccionBean convertirBeanPQToAccionBean(BeanPQCERRAR beanPQ) {

		AccionBean accionBean = new AccionBean();

		accionBean.setAccion(beanPQ.getP_ACCION());
		accionBean.setRespuesta(beanPQ.getP_INFORMACION());

		if ( beanPQ.getP_ID_USUARIO()!= null) {
			accionBean.setIdUsuario(beanPQ.getP_ID_USUARIO());
		}

		if ( beanPQ.getP_NUM_EXPEDIENTE()!= null) {
			accionBean.setNumExpediente(beanPQ.getP_NUM_EXPEDIENTE());
		}

		return accionBean;
	}

	/**
	 * Método estático para convertir del beanPQBuscar hacia AccionBean
	 * @param beanPQcrearAccion
	 * @return
	 */
	public static AccionBean convertirBeanPQToAccionBean(BeanPQBUSCAR beanPQ) {
		AccionBean accionBean = new AccionBean();

		accionBean.setRespuesta(beanPQ.getP_INFORMACION());

		if (beanPQ.getP_ID_USUARIO()!= null) {
			accionBean.setIdUsuario(beanPQ.getP_ID_USUARIO());
		}

		if (beanPQ.getP_NUM_EXPEDIENTE()!= null) {
			accionBean.setNumExpediente(beanPQ.getP_NUM_EXPEDIENTE());
		}

		return accionBean;
	}

	public AccionBean convertirCursorAccionToBean(
			beanAccionCursor cursorAccion) {

		AccionBean accionBean = new AccionBean();

		accionBean.setAccion(cursorAccion.getACCION());
		accionBean.setNombreApellidos(cursorAccion.getApellidos_nombre());

		if (cursorAccion.getFECHA_INICIO() != null) {
			Timestamp fecha = cursorAccion.getFECHA_INICIO();
			accionBean.setFechaInicioT(fecha);
			accionBean.setFechaInicio((utilesFecha.getFechaFracionada(fecha)));
		}

		if (cursorAccion.getFECHA_FIN() != null) {
			Timestamp fecha = cursorAccion.getFECHA_FIN();
			accionBean.setFechaFinT(fecha);
			accionBean.setFechaFin((utilesFecha.getFechaFracionada(fecha)));
		}

		accionBean.setRespuesta(cursorAccion.getRESPUESTA());

		return accionBean;
	}

}