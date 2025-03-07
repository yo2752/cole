package org.gestoresmadrid.core.trafico.solicitudesplacas.model.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.model.enumerados.TipoVehiculoEnum;
import org.gestoresmadrid.core.trafico.solicitudesplacas.model.dao.PlacasMatriculacionDAO;
import org.gestoresmadrid.core.trafico.solicitudesplacas.model.vo.SolicitudPlacaVO;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hibernate.entities.general.JefaturaTrafico;
import hibernate.entities.general.Usuario;
import hibernate.entities.personas.Persona;
import hibernate.entities.trafico.SolicitudPlaca;

@Repository
public class PlacasMatriculacionDAOImpl extends GenericDaoImplHibernate<SolicitudPlacaVO> implements PlacasMatriculacionDAO {

	private static final long serialVersionUID = 6805101404380424077L;
	/* Inicio nombres entidades como propiedad */
	private static final String TRAMITE_TRAFICO_PROPERTY = "tramiteTrafico";
	private static final String USUARIO_PROPERTY = "usuario";

	@Override
	public SolicitudPlacaVO getSolicitud(Integer idSolicitud, String... initialized) {

		SolicitudPlacaVO solicitudPlacaVO = null;

		Criteria criteria = getCurrentSession().createCriteria(SolicitudPlacaVO.class);

		criteria.createAlias(TRAMITE_TRAFICO_PROPERTY, TRAMITE_TRAFICO_PROPERTY, CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("idSolicitud", idSolicitud));

		solicitudPlacaVO = (SolicitudPlacaVO) criteria.uniqueResult();

		if(initialized!=null) {
			initLazys(solicitudPlacaVO, initialized);
		}

		return solicitudPlacaVO;

	}

	@Override
	public void initLazys(SolicitudPlacaVO solicitudPlacaVO, String... initialized){

		for (String initialize : initialized) {
			try {
				if (initialized != null){
					Hibernate.initialize(BeanUtils.getProperty(solicitudPlacaVO, initialize));
				}
			} catch (HibernateException e) {
				log.error("Error de hibernate inicializando lazzys", e);
			} catch (IllegalAccessException e) {
				log.error("Error accediendo a clases, inicializando lazzys", e);
			} catch (InvocationTargetException e) {
				log.error("Error invocando metodos, inicializando lazzys", e);
			} catch (NoSuchMethodException e) {
				log.error("Error inicializando lazzys, metodo no existe", e);
			}
		}

	}

	@Override
	public boolean ejecutarTransacciones (Transaction tx) throws Exception {

		boolean resultado;

		try {
			tx.commit();
			resultado = true;
		} catch(RuntimeException re){
			try {
				tx.rollback();
				log.error("Se ha ejecutado el rollback: ", re);
			} catch (RuntimeException rbe) {
				log.error("No se ha podido ejecutar el rollback: ", rbe);
			}
			resultado = false;
		}
		return resultado;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<SolicitudPlacaVO> buscar(Date fechaInicio, Date fechaFin, String numColegiado, Long numExpediente, String matricula, String tipoMatricula, String... initialized){

		ArrayList<SolicitudPlacaVO> lsolicitudesPlacaVO = new ArrayList<SolicitudPlacaVO>();
		SolicitudPlacaVO solicitudPlaca = new SolicitudPlacaVO();

		Criteria criteria = getCurrentSession().createCriteria(SolicitudPlacaVO.class);

		criteria.createAlias(TRAMITE_TRAFICO_PROPERTY, TRAMITE_TRAFICO_PROPERTY, CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias(USUARIO_PROPERTY, USUARIO_PROPERTY, CriteriaSpecification.INNER_JOIN);

		if(fechaInicio!=null){
			criteria.add(Restrictions.ge("fechaSolicitud", fechaInicio));
		}

		if(fechaFin!=null){
			criteria.add(Restrictions.le("fechaSolicitud", fechaFin));
		}

		if(numColegiado!=null){
			criteria.add(Restrictions.eq(TRAMITE_TRAFICO_PROPERTY + ".numColegiado", numColegiado));
		}

		if(numExpediente!=null){
			criteria.add(Restrictions.eq(TRAMITE_TRAFICO_PROPERTY + ".numExpediente", BigDecimal.valueOf(numExpediente)));
		}

		if(matricula!=null){
			criteria.add(Restrictions.eq("matricula", matricula));
		}

		if(tipoMatricula!=null){
			criteria.add(Restrictions.eq("tipoMatricula", tipoMatricula));
		}

		criteria.add(Restrictions.ne("estado", 0));

		initLazys(solicitudPlaca,initialized);

		lsolicitudesPlacaVO = (ArrayList<SolicitudPlacaVO>) criteria.list();

		return lsolicitudesPlacaVO;

	}

	@Override
	public Integer getIdSolicitudPorClaveUnica(SolicitudPlacaVO solicitudPlacaVO) {

		Integer idSolicitud = null;

		Date fechaSolicitud = solicitudPlacaVO!=null ? solicitudPlacaVO.getFechaSolicitud() : null;

		Criteria criteria = getCurrentSession().createCriteria(SolicitudPlacaVO.class);

		criteria.add(Restrictions.eq("matricula", solicitudPlacaVO.getMatricula()));
		criteria.add(Restrictions.eq("fechaSolicitud", fechaSolicitud));

		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("idSolicitud"));

		criteria.setProjection(pList);

		idSolicitud = (Integer) criteria.uniqueResult();

		return idSolicitud;

	}

	@Override
	public SolicitudPlacaVO getSolicitudPorClaveUnica(SolicitudPlacaVO solicitudPlacaVO) {

		SolicitudPlacaVO solicitudGuardada = null;

		String matricula = solicitudPlacaVO.getMatricula() ;

		String numColegiado = solicitudPlacaVO.getNumColegiado();

		Date fechaSolicitud = solicitudPlacaVO!=null ? solicitudPlacaVO.getFechaSolicitud() : null;

		Criteria criteria = getCurrentSession().createCriteria(SolicitudPlacaVO.class);

		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		criteria.add(Restrictions.eq("matricula", matricula));
		criteria.add(Restrictions.eq("fechaSolicitud", fechaSolicitud));

		solicitudGuardada = (SolicitudPlacaVO) criteria.uniqueResult();

		return solicitudGuardada;

	}

	@SuppressWarnings("unchecked")
	public List<SolicitudPlacaVO> generarEstadisticasSinAgrupacion(Date fechaInicio,Date fechaFin){

		Criteria criteria = getCurrentSession().createCriteria(SolicitudPlacaVO.class);

		criteria.add(Restrictions.ge("fechaSolicitud", fechaInicio));
		criteria.add(Restrictions.le("fechaSolicitud", fechaFin));
		criteria.add(Restrictions.ne("estado", 1));
		criteria.addOrder(Order.desc("fechaSolicitud"));

		List<SolicitudPlacaVO> lSolicitudPlacaVO = criteria.list();

		return lSolicitudPlacaVO;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap<Integer, Integer> generarEstadisticasAgrupadoPorCreditos(Date fechaInicio,Date fechaFin){
		HashMap<Integer, Integer> mapaAuxiliar = new HashMap<Integer, Integer>();

		Criteria criteria = getCurrentSession().createCriteria(SolicitudPlacaVO.class);

		criteria.add(Restrictions.ge("fechaSolicitud", fechaInicio));
		criteria.add(Restrictions.le("fechaSolicitud", fechaFin));
		criteria.add(Restrictions.ne("estado", 1));

		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.rowCount());
		pList.add(Projections.groupProperty("tipoDelantera"));

		criteria.setProjection(pList);

		List lista = criteria.list();

		Criteria criteria2 = getCurrentSession().createCriteria(SolicitudPlacaVO.class);

		criteria2.add(Restrictions.ge("fechaSolicitud", fechaInicio));
		criteria2.add(Restrictions.le("fechaSolicitud", fechaFin));
		criteria2.add(Restrictions.ne("estado", 1));

		ProjectionList pList2 = Projections.projectionList();
		pList2.add(Projections.rowCount());
		pList2.add(Projections.groupProperty("tipoTrasera"));

		criteria2.setProjection(pList2);

		List lista2 = criteria.list();

		Criteria criteria3 = getCurrentSession().createCriteria(SolicitudPlaca.class);

		criteria3.add(Restrictions.ge("fechaSolicitud", fechaInicio));
		criteria3.add(Restrictions.le("fechaSolicitud", fechaFin));
		criteria3.add(Restrictions.ne("estado", 1));

		ProjectionList pList3 = Projections.projectionList();
		pList3.add(Projections.rowCount());
		pList3.add(Projections.groupProperty("tipoAdicional"));

		criteria3.setProjection(pList3);

		List lista3 = criteria3.list();

		lista.addAll(lista2);
		lista.addAll(lista3);

		for (Object objeto : lista){
			if (objeto instanceof Object[]){
				Object[] arrayDeLista = (Object[])objeto;
				Integer tipoPlaca = (Integer)arrayDeLista[1];
				Integer totalTipoPlaca = (Integer)arrayDeLista[0];
				if (mapaAuxiliar.containsKey(tipoPlaca)){
					totalTipoPlaca+= mapaAuxiliar.get(tipoPlaca);
					mapaAuxiliar.remove(tipoPlaca);
					mapaAuxiliar.put(tipoPlaca, totalTipoPlaca);
				}else{
					mapaAuxiliar.put(tipoPlaca, totalTipoPlaca);
				}
			}
		}

		return mapaAuxiliar;
	}

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public HashMap<String, Integer> generarEstadisticasAgrupadoPorNumColegiado(Date fechaInicio,Date fechaFin){
		HashMap<String, Integer> mapaAuxiliar = new HashMap<String, Integer>();
		List listaAuxiliar = new ArrayList();

		Criteria criteria = getCurrentSession().createCriteria(SolicitudPlacaVO.class);

		criteria.add(Restrictions.ge("fechaSolicitud", fechaInicio));
		criteria.add(Restrictions.le("fechaSolicitud", fechaFin));
		criteria.addOrder(Order.asc("numColegiado"));
		criteria.add(Restrictions.ne("estado", 1));

		List<SolicitudPlacaVO> listaSolicitudes = (List<SolicitudPlacaVO>)criteria.list();

		for (SolicitudPlacaVO solicitudPlaca : listaSolicitudes) {
			if (mapaAuxiliar.containsKey(solicitudPlaca.getVehiculo().getNumColegiado())){
				Integer totalPlacas = mapaAuxiliar.get(solicitudPlaca.getVehiculo().getNumColegiado());
				if(solicitudPlaca.getTipoAdicional()!=null){
					totalPlacas+=1;
				}
				if(solicitudPlaca.getTipoDelantera()!=null){
					totalPlacas+=1;
				}
				if(solicitudPlaca.getTipoTrasera()!=null){
					totalPlacas+=1;
				}
				mapaAuxiliar.remove(solicitudPlaca.getVehiculo().getNumColegiado());
				mapaAuxiliar.put(solicitudPlaca.getVehiculo().getNumColegiado(), totalPlacas);
			}else{
				Integer totalPlacas = 0;
				if(solicitudPlaca.getTipoAdicional()!=null){
					totalPlacas+=1;
				}
				if(solicitudPlaca.getTipoDelantera()!=null){
					totalPlacas+=1;
				}
				if(solicitudPlaca.getTipoTrasera()!=null){
					totalPlacas+=1;
				}
				mapaAuxiliar.put(solicitudPlaca.getVehiculo().getNumColegiado(), totalPlacas);
			}
		}

		return mapaAuxiliar;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<List> generarEstadisticasAgrupadoPorVehiculo(Date fechaInicio,Date fechaFin){
		ArrayList<HashMap<Integer, String>> mapa = new ArrayList<HashMap<Integer, String>>();
		ArrayList<List> mapa2 = new ArrayList<>();
		HashMap<Integer, String> mapaAuxiliar;

		Criteria criteria = getCurrentSession().createCriteria(SolicitudPlacaVO.class);

		criteria.add(Restrictions.ge("fechaSolicitud", fechaInicio));
		criteria.add(Restrictions.le("fechaSolicitud", fechaFin));
		criteria.add(Restrictions.ne("estado", 1));

		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.rowCount());
		pList.add(Projections.groupProperty("tipoVehiculo"));

		criteria.setProjection(pList);

		List<Object> lista = criteria.list();

		for (Object objeto : lista) {
			mapaAuxiliar = new HashMap<Integer, String>();
			List listaResultados = new ArrayList();
			if (objeto instanceof Object[]) {
				// Es un array
				Object[] arrayObjeto = (Object[]) objeto;
				if (arrayObjeto.length == 2) {
					Integer total = (Integer)arrayObjeto[0];
					String vehiculo = (String)arrayObjeto[1];
					String nombreV = TipoVehiculoEnum.convertirTexto(vehiculo);
					mapaAuxiliar.put(total, nombreV);
					mapa.add(mapaAuxiliar);
					listaResultados.add(0, nombreV);
					listaResultados.add(1, total);
					mapa2.add(listaResultados);
				}
			}
		}

		return mapa2;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HashMap<String, HashMap<Integer, Integer>> generarEstadisticasAgrupadoPorCreditosColegiado(Date fechaInicio,Date fechaFin){
		HashMap<Integer, Integer> mapaAuxiliar = null;
		HashMap<String, HashMap<Integer, Integer>> mapaPorColegiado = null;

		Criteria criteria = getCurrentSession().createCriteria(SolicitudPlacaVO.class);

		criteria.add(Restrictions.ge("fechaSolicitud", fechaInicio));
		criteria.add(Restrictions.le("fechaSolicitud", fechaFin));
		criteria.add(Restrictions.ne("estado", 1));

		criteria.setProjection(Projections.distinct(Projections.property("numColegiado")));

		List lista4 = criteria.list();

		mapaPorColegiado = new HashMap<String, HashMap<Integer, Integer>>();
		for (Object objetoColegiados : lista4){
			mapaAuxiliar = new HashMap<Integer, Integer>();
			if (objetoColegiados instanceof String){
				String numColegiado = (String)objetoColegiados;

				Criteria criteria0 = getCurrentSession().createCriteria(SolicitudPlaca.class);

				criteria0.add(Restrictions.ge("fechaSolicitud", fechaInicio));
				criteria0.add(Restrictions.le("fechaSolicitud", fechaFin));
				criteria0.add(Restrictions.eq("numColegiado", numColegiado));
				criteria0.add(Restrictions.ne("estado", 1));

				ProjectionList pList = Projections.projectionList();
				pList.add(Projections.rowCount());
				pList.add(Projections.groupProperty("tipoDelantera"));
				criteria0.setProjection(pList);

				List lista = criteria0.list();

				Criteria criteria2 = getCurrentSession().createCriteria(SolicitudPlacaVO.class);

				criteria2.add(Restrictions.ge("fechaSolicitud", fechaInicio));
				criteria2.add(Restrictions.le("fechaSolicitud", fechaFin));
				criteria2.add(Restrictions.eq("numColegiado", numColegiado));
				criteria2.add(Restrictions.ne("estado", 1));

				ProjectionList pList2 = Projections.projectionList();
				pList2.add(Projections.rowCount());
				pList2.add(Projections.groupProperty("tipoTrasera"));

				criteria2.setProjection(pList2);

				List lista2 = criteria2.list();

				Criteria criteria3 = getCurrentSession().createCriteria(SolicitudPlacaVO.class);

				criteria3.add(Restrictions.ge("fechaSolicitud", fechaInicio));
				criteria3.add(Restrictions.le("fechaSolicitud", fechaFin));
				criteria3.add(Restrictions.eq("numColegiado", numColegiado));
				criteria3.add(Restrictions.ne("estado", 1));

				ProjectionList pList3 = Projections.projectionList();
				pList3.add(Projections.rowCount());
				pList3.add(Projections.groupProperty("tipoAdicional"));

				criteria3.setProjection(pList3);

				List lista3 = criteria3.list();

				lista.addAll(lista2);
				lista.addAll(lista3);

				for (Object objeto : lista){
					if (objeto instanceof Object[]){
						Object[] arrayDeLista = (Object[])objeto;
						Integer tipoPlaca = (Integer)arrayDeLista[1];
						Integer totalTipoPlaca = (Integer)arrayDeLista[0];
						if (mapaAuxiliar.containsKey(tipoPlaca)){
							totalTipoPlaca+= mapaAuxiliar.get(tipoPlaca);
							mapaAuxiliar.remove(tipoPlaca);
							mapaAuxiliar.put(tipoPlaca, totalTipoPlaca);
						}else{
							mapaAuxiliar.put(tipoPlaca, totalTipoPlaca);
						}
					}
				}

				mapaPorColegiado.put(numColegiado, mapaAuxiliar);
			}
		}
		return mapaPorColegiado;
	}

	@Override
	public Persona getPersonaFromNif(String nif, String numColegiado) {

		Persona persona = null;

		Criteria criteria = getCurrentSession().createCriteria(Persona.class);

		criteria.add(Restrictions.eq("id.nif", nif));
		criteria.add(Restrictions.eq("id.numColegiado", numColegiado));

		try{
			persona = (Persona) criteria.uniqueResult();

		} catch (HibernateException e){
			log.error("No se pudo recuperar la persona especificada como titular de la solicitud de placas.");
		}

		return persona;

	}

	public Usuario getUsuario(Integer idUsuario){
		Usuario usuario = null;

		Criteria criteria = getCurrentSession().createCriteria(Usuario.class);

		criteria.add(Restrictions.eq("idUsuario", idUsuario.longValue()));

		usuario = (Usuario) criteria.uniqueResult();

		return usuario;
	}

	public JefaturaTrafico getJefaturaFromId(String jefaturaProv){
		JefaturaTrafico jefaturaTrafico = null;
		Criteria criteria = getCurrentSession().createCriteria(JefaturaTrafico.class);

		criteria.add(Restrictions.eq("jefaturaProvincial", jefaturaProv));

		jefaturaTrafico = (JefaturaTrafico) criteria.uniqueResult();

		return jefaturaTrafico;
	}

	public boolean borrarSolicitud (SolicitudPlacaVO solicitudPlacaVO){
		boolean resultado = false;

		try {
			borrar(solicitudPlacaVO);
		} catch(Exception e){
			log.error("Error borrando la solicitud de placas para el trámite " + String.valueOf(solicitudPlacaVO.getTramiteTrafico().getNumExpediente()) + ": " + e.getMessage());
			resultado = false;
		}

		return resultado;
	}

	public List<SolicitudPlacaVO> obtenerListaSolicitudes(){
		List<SolicitudPlacaVO> solicitudPlacaVO = null;

		Criteria criteria = getCurrentSession().createCriteria(SolicitudPlacaVO.class);

		criteria.createAlias(TRAMITE_TRAFICO_PROPERTY, TRAMITE_TRAFICO_PROPERTY, CriteriaSpecification.LEFT_JOIN);

		solicitudPlacaVO = (List<SolicitudPlacaVO>) criteria.list();

		return solicitudPlacaVO;
	}
}