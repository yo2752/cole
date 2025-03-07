package org.gestoresmadrid.core.mandato.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.mandato.model.dao.MandatoDao;
import org.gestoresmadrid.core.mandato.model.vo.MandatoVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MandatoDaoImpl extends GenericDaoImplHibernate<MandatoVO> implements MandatoDao {

	private static final long serialVersionUID = -6988709583120444948L;
	
	@Override
	public MandatoVO getMandatoPorCodigoYColegiado(String codigoMandato, String numColegiado) {
		Criteria criteria = getCurrentSession().createCriteria(MandatoVO.class);
		criteria.add(Restrictions.eq("codigoMandato", codigoMandato));
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		return (MandatoVO) criteria.uniqueResult();
	}
	
	@Override
	public MandatoVO getMandatoPorCodigo(String codigoMandato) {
		Criteria criteria = getCurrentSession().createCriteria(MandatoVO.class);
		criteria.add(Restrictions.eq("codigoMandato", codigoMandato));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		return (MandatoVO) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MandatoVO> getListaMandatosPorColegiados(String numColegiado) {
		Criteria criteria = getCurrentSession().createCriteria(MandatoVO.class);
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getListaCodMandatosPorColegiados(String numColegiado) {
		Criteria criteria = getCurrentSession().createCriteria(MandatoVO.class);
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		criteria.setProjection(Projections.distinct(Projections.property("codigoMandato")));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getListaColegiadosConMandatos() {
		Criteria criteria = getCurrentSession().createCriteria(MandatoVO.class);
		criteria.setProjection(Projections.distinct(Projections.property("numColegiado")));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}
	
	@Override
	public MandatoVO getMandato(Long idMandato) {
		Criteria criteria = getCurrentSession().createCriteria(MandatoVO.class);
		if (idMandato != null) {
			criteria.add(Restrictions.eq("idMandato", idMandato));
		}
		@SuppressWarnings("unchecked")
		List<MandatoVO> lista = criteria.list();
		if (lista != null && lista.size() > 0) {
			return lista.get(0);
		}
		return null;
	}
	
	@Override
	public String obtenerCodigoMandato(String numColegiado) {
		String codigoMandato = null;
		Criteria criteria = getCurrentSession().createCriteria(MandatoVO.class);
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		//criteria.setProjection(Projections.max("codigoMandato"));
		criteria.setProjection(Projections.rowCount());
		Integer codigo = (Integer) criteria.uniqueResult();
		if (codigo != null) {
			codigo = codigo + 1;
			codigoMandato = codigo.toString();
			
		} else {
			codigoMandato = "1" ;
		}
		/*String maxCodigoMandato = (String) criteria.uniqueResult();
		if (maxCodigoMandato != null && !maxCodigoMandato.isEmpty()) {
			try {
				Long codigo = Long.valueOf(maxCodigoMandato);
				codigo = codigo+1;
				codigoMandato = codigo.toString();
			} catch (Exception e) {
				log.error("Error al obtener el codigo mandato. Codigo mandato en BBDD probablemente no numérico", e);
			}
			
		} else {
			codigoMandato = "1";
		}*/
		return codigoMandato;
	}
}
