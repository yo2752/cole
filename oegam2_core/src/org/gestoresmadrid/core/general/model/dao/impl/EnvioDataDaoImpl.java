package org.gestoresmadrid.core.general.model.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.general.model.dao.EnvioDataDao;
import org.gestoresmadrid.core.general.model.vo.EnvioDataPK;
import org.gestoresmadrid.core.general.model.vo.EnvioDataVO;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EnvioDataDaoImpl extends GenericDaoImplHibernate<EnvioDataVO> implements EnvioDataDao {

	private static final long serialVersionUID = 340562564354920425L;

	public static final String EJECUCION_EXCEPCION = "LANZÓ EXCEPCIÓN";

	@Override
	public EnvioDataVO getEnvioDataProcesoResultadoEjecucion(String proceso, String resultadoEjecucion, String numCola) {
		Criteria criteria = getCurrentSession().createCriteria(EnvioDataVO.class);
		criteria.add(Restrictions.eq("envioData.id.proceso", proceso));
		criteria.add(Restrictions.eq("envioData.id.correcta", resultadoEjecucion));
		if (numCola != null && !numCola.isEmpty()) {
			criteria.add(Restrictions.eq("envioData.id.cola", numCola));
		} else {
			criteria.add(Restrictions.eq("envioData.id.cola", "0"));
		}
		return (EnvioDataVO) criteria.uniqueResult();
	}

	@Override
	public void actualizarUltimaEjecucion(ColaBean colaBean, String resultadoEjecucion, String excepcion) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(EnvioDataVO.class, "envioData");
		criteria.add(Restrictions.eq("envioData.id.proceso", colaBean.getProceso()));
		criteria.add(Restrictions.eq("envioData.id.correcta", resultadoEjecucion));
		String cola = null;
		if (colaBean.getCola() != null && !colaBean.getCola().isEmpty()) {
			cola = colaBean.getCola();
		} else {
			cola = "0";
		}
		criteria.add(Restrictions.eq("envioData.id.cola", cola));
		@SuppressWarnings("unchecked")
		List<EnvioDataVO> listaEnvioData = criteria.list();
		if (listaEnvioData.isEmpty()) {
			EnvioDataVO envioDataNuevo = new EnvioDataVO();
			EnvioDataPK envioDataPk = new EnvioDataPK();
			envioDataPk.setCorrecta(resultadoEjecucion);
			envioDataNuevo.setFechaEnvio(new Date());
			envioDataPk.setProceso(colaBean.getProceso());
			envioDataPk.setCola(cola);
			envioDataNuevo.setNumExpediente(colaBean.getIdTramite());
			envioDataNuevo.setId(envioDataPk);
			if (EJECUCION_EXCEPCION.equals(resultadoEjecucion)) {
				envioDataNuevo.setRespuesta(acortarRespuesta(excepcion));
			} else {
				envioDataNuevo.setRespuesta(acortarRespuesta(colaBean.getRespuesta()));
			}
			session.persist(envioDataNuevo);
		} else {
			EnvioDataVO envioData = listaEnvioData.get(0);
			envioData.setFechaEnvio(new Date());
			EnvioDataPK envioDataPk = new EnvioDataPK();
			envioDataPk.setProceso(colaBean.getProceso());
			envioDataPk.setCola(cola);
			envioData.setNumExpediente(colaBean.getIdTramite());
			envioDataPk.setCorrecta(resultadoEjecucion);
			envioData.setId(envioDataPk);
			if (EJECUCION_EXCEPCION.equals(resultadoEjecucion)) {
				envioData.setRespuesta(acortarRespuesta(excepcion));
			} else {
				envioData.setRespuesta(acortarRespuesta(colaBean.getRespuesta()));
			}
			session.update(envioData);
		}
	}

	private String acortarRespuesta(String respuesta) {
		if (StringUtils.isNotBlank(respuesta) && respuesta.length() > 500) {
			respuesta = respuesta.substring(0, 500);
		}
		return respuesta;
	}

	@Override
	public void actualizarUltimaEjecucionNuevo(String proceso, String cola, BigDecimal idTramite, String respuesta, String resultadoEjecucion, String excepcion) {
		Criteria criteria = getCurrentSession().createCriteria(EnvioDataVO.class, "envioData");
		criteria.add(Restrictions.eq("envioData.id.proceso", proceso));
		criteria.add(Restrictions.eq("envioData.id.correcta", resultadoEjecucion));
		String numCola = null;
		if (cola != null && !cola.isEmpty()) {
			numCola = cola;
		} else {
			numCola = "0";
		}
		criteria.add(Restrictions.eq("envioData.id.cola", numCola));
		@SuppressWarnings("unchecked")
		List<EnvioDataVO> listaEnvioData = criteria.list();
		if (listaEnvioData.isEmpty()) {
			EnvioDataVO envioDataNuevo = new EnvioDataVO();
			EnvioDataPK envioDataPk = new EnvioDataPK();
			envioDataPk.setCorrecta(resultadoEjecucion);
			envioDataNuevo.setFechaEnvio(new Date());
			envioDataPk.setProceso(proceso);
			envioDataPk.setCola(numCola);
			envioDataNuevo.setNumExpediente(idTramite);
			envioDataNuevo.setId(envioDataPk);
			if (EJECUCION_EXCEPCION.equals(resultadoEjecucion)) {
				envioDataNuevo.setRespuesta(excepcion);
			} else {
				envioDataNuevo.setRespuesta(respuesta);
			}
			getCurrentSession().persist(envioDataNuevo);
		} else {
			EnvioDataVO envioData = listaEnvioData.get(0);
			envioData.setFechaEnvio(new Date());
			EnvioDataPK envioDataPk = new EnvioDataPK();
			envioDataPk.setProceso(proceso);
			envioDataPk.setCola(numCola);
			envioData.setNumExpediente(idTramite);
			envioDataPk.setCorrecta(resultadoEjecucion);
			envioData.setId(envioDataPk);
			if (EJECUCION_EXCEPCION.equals(resultadoEjecucion)) {
				envioData.setRespuesta(excepcion);
			} else {
				envioData.setRespuesta(respuesta);
			}
			getCurrentSession().update(envioData);
		}
	}

	/**
	 * Inserta un registro del proceso de la última ejecución del tipo especificado en el parámetro
	 * @param proceso
	 * @param resultadoEjecucion
	 */
	@Override
	public void actualizarEjecucion(String proceso, String respuesta, String resultadoEjecucion, String numCola) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(EnvioDataVO.class, "envioData");

		criteria.add(Restrictions.eq("envioData.id.proceso", proceso));
		criteria.add(Restrictions.eq("envioData.id.correcta", resultadoEjecucion));

		String cola = null;
		if (numCola != null && !numCola.isEmpty()) {
			cola = numCola;
		} else {
			cola = "0";
		}
		criteria.add(Restrictions.eq("envioData.id.cola", cola));

		@SuppressWarnings("unchecked")
		List<EnvioDataVO> listaEnvioData = criteria.list();
		if (listaEnvioData.isEmpty()) {
			EnvioDataVO envioDataNuevo = new EnvioDataVO();
			EnvioDataPK envioDataPk = new EnvioDataPK();
			envioDataPk.setCorrecta(resultadoEjecucion);
			envioDataNuevo.setFechaEnvio(new Date());
			envioDataPk.setProceso(proceso);
			envioDataPk.setCola(cola);
			envioDataNuevo.setRespuesta(respuesta);
			envioDataNuevo.setId(envioDataPk);
			session.persist(envioDataNuevo);

		} else {
			EnvioDataVO envioData = listaEnvioData.get(0);
			envioData.setFechaEnvio(new Date());
			EnvioDataPK envioDataPk = new EnvioDataPK();
			envioDataPk.setProceso(proceso);
			envioDataPk.setCola(cola);
			envioDataPk.setCorrecta(resultadoEjecucion);
			if (respuesta != null && respuesta.equals("LANZÓ EXCEPCIÓN")) {
				envioData.setRespuesta(respuesta);
			} else {
				envioData.setRespuesta(respuesta);
			}
			// envioData.setRespuesta(respuesta);
			envioData.setId(envioDataPk);
			session.update(envioData);

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EnvioDataVO> getExisteEnvioData(String proceso, String tipoEjecucion, Date fechaInicial, Date fechaFinal) {
		Criteria criteria = getCurrentSession().createCriteria(EnvioDataVO.class);
		criteria.add(Restrictions.eq("id.proceso", proceso));
		criteria.add(Restrictions.eq("id.correcta", tipoEjecucion));
		criteria.add(Restrictions.between("fechaEnvio", fechaInicial, fechaFinal));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EnvioDataVO> getExisteEnvioDataEnvioStock(String proceso, String tipoEjecucion, Date fechaInicial, Date fechaFinal) {
		Criteria criteria = getCurrentSession().createCriteria(EnvioDataVO.class);
		criteria.add(Restrictions.eq("id.proceso", proceso));
		criteria.add(Restrictions.like("id.correcta", tipoEjecucion + "%"));
		criteria.add(Restrictions.between("fechaEnvio", fechaInicial, fechaFinal));

		return criteria.list();
	}

	@Override
	public EnvioDataVO getEnvioDataPorHoras(String proceso, String tipoEjecucion, String hiloCola, Date fechaInicial, Date fechaFinal) {
		Criteria criteria = getCurrentSession().createCriteria(EnvioDataVO.class);
		criteria.add(Restrictions.eq("id.proceso", proceso));
		criteria.add(Restrictions.eq("id.correcta", tipoEjecucion));
		criteria.add(Restrictions.eq("id.cola", hiloCola));
		criteria.add(Restrictions.between("fechaEnvio", fechaInicial, fechaFinal));
		return (EnvioDataVO) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EnvioDataVO> getUltimoEnvioData(String proceso, String tipoEjecucion) {
		Criteria criteria = getCurrentSession().createCriteria(EnvioDataVO.class);
		criteria.add(Restrictions.eq("id.proceso", proceso));
		if(tipoEjecucion!=null) {
			criteria.add(Restrictions.eq("id.correcta", tipoEjecucion));
		}
		criteria.addOrder(Order.asc("id.correcta"));
		return criteria.list();
	}

	@Override
	public Date getUltimoEnvioDataProcesoEjecucion(String proceso, String tipoEjecucion, String hiloCola) {
		Criteria criteria = getCurrentSession().createCriteria(EnvioDataVO.class);
		criteria.add(Restrictions.eq("id.proceso", proceso));
		criteria.add(Restrictions.eq("id.correcta", tipoEjecucion));
		criteria.add(Restrictions.eq("id.cola", hiloCola));
		criteria.setProjection(Projections.max("fechaEnvio"));
		return (Date) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EnvioDataVO> getListaEnvioDataPorProcesoYTipoEjecucionPorCola(String proceso, String ejecucionExcepcion) {
		Criteria criteria = getCurrentSession().createCriteria(EnvioDataVO.class);
		criteria.add(Restrictions.eq("id.proceso", proceso));
		criteria.add(Restrictions.eq("id.correcta", ejecucionExcepcion));
		criteria.addOrder(Order.desc("fechaEnvio"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public EnvioDataVO getListaEnvioDataPorProcesoYTipoEjecucion(String proceso, String ejecucionExcepcion) {
		Criteria criteria = getCurrentSession().createCriteria(EnvioDataVO.class);
		criteria.add(Restrictions.eq("id.proceso", proceso));
		criteria.add(Restrictions.eq("id.correcta", ejecucionExcepcion));
		criteria.addOrder(Order.desc("fechaEnvio"));
		List<EnvioDataVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EnvioDataVO> getListaUltimoEnvioPorEjecucion(String proceso, String cola, String ejecucionCorrecta, String orden, String dir) {
		Criteria criteria = getCurrentSession().createCriteria(EnvioDataVO.class);
		criteria.add(Restrictions.eq("id.proceso", proceso));
		if (StringUtils.isNotBlank(ejecucionCorrecta)) {
			criteria.add(Restrictions.eq("id.correcta", ejecucionCorrecta));
		}
		if (StringUtils.isNotBlank(cola)) {
			criteria.add(Restrictions.eq("id.cola", cola));
		}
		criteria.addOrder(Order.desc("fechaEnvio"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EnvioDataVO> recuperarUltimasEjecucionesProceso(String proceso) {
		Criteria criteria = getCurrentSession().createCriteria(EnvioDataVO.class);
		criteria.add(Restrictions.eq("id.proceso", proceso));
		criteria.addOrder(Order.asc("id.correcta"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public EnvioDataVO getEnvioDataPorProcesoYColaYTipoEjecucion(String proceso, String cola, String ejecucionCorrecta) {
		Criteria criteria = getCurrentSession().createCriteria(EnvioDataVO.class);
		criteria.add(Restrictions.eq("id.proceso", proceso));
		criteria.add(Restrictions.eq("id.correcta", ejecucionCorrecta));
		criteria.add(Restrictions.eq("id.cola", cola));
		List<EnvioDataVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EnvioDataVO> recuperarUltimasOk(String proceso, String cola) {
		Criteria criteria = getCurrentSession().createCriteria(EnvioDataVO.class);
		criteria.add(Restrictions.eq("id.proceso", proceso));
		criteria.add(Restrictions.eq("id.correcta", "CORRECTA"));
		criteria.add(Restrictions.eq("id.cola", cola));
		criteria.addOrder(Order.desc("id.proceso"));
		criteria.addOrder(Order.desc("id.cola"));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EnvioDataVO> listar(String tipoEjecucion) {
		Criteria criteria = getCurrentSession().createCriteria(EnvioDataVO.class);
		criteria.add(Restrictions.eq("id.correcta", tipoEjecucion));
		return criteria.list();
	}
}
