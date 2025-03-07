package org.gestoresmadrid.core.modelo600_601.model.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.modelo600_601.model.dao.ResultadoModelo600601Dao;
import org.gestoresmadrid.core.modelo600_601.model.vo.ResultadoModelo600601VO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ResultadoModelo600601DaoImp extends GenericDaoImplHibernate<ResultadoModelo600601VO> implements ResultadoModelo600601Dao{
	
	private static final long serialVersionUID = 8348314742235559198L;
	
	public ResultadoModelo600601DaoImp() { 
		super();
	}
	
	@Override
	@Transactional
	public void insertResultado(ResultadoModelo600601VO resultadoModelo){
		guardar(resultadoModelo);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<ResultadoModelo600601VO> getResultadoModeloByIdModelo600601 (Long idModelo){
		List<Criterion> listCriterion = new ArrayList<Criterion>();
		List<String> listOrden = new ArrayList<String>();
		listOrden.add("idResultado");
		listCriterion.add(Restrictions.eq("modelo600601.idModelo", idModelo));
		List<ResultadoModelo600601VO> lista = buscarPorCriteria(listCriterion, "desc", listOrden, null, null);
		
		return lista;
	}
	
	@Override
	@Transactional(readOnly=true)
	public Date getFechaPresentacion (Long idModelo, String justificante){
		List<Criterion> listCriterion = new ArrayList<Criterion>();
		listCriterion.add(Restrictions.eq("modelo600601.idModelo", idModelo));
		listCriterion.add(Restrictions.eq("justificante", justificante));
		List<ResultadoModelo600601VO> lista = buscarPorCriteria(listCriterion, null, null);
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0).getFechaPresentacion();
		}
		return null;
	}
	
	@Override
	public ResultadoModelo600601VO getResultadoModeloPorId(Integer idResultadoModelo600601) {
		Criteria criteria = getCurrentSession().createCriteria(ResultadoModelo600601VO.class);
		criteria.add(Restrictions.eq("idResultado", idResultadoModelo600601));
		criteria.createAlias("modelo600601", "modelo600601",CriteriaSpecification.LEFT_JOIN);
		return (ResultadoModelo600601VO) criteria.uniqueResult();
	}
	
}
