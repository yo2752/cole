package org.gestoresmadrid.core.favoritos.model.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.favoritos.model.dao.FavoritosContratoDao;
import org.gestoresmadrid.core.favoritos.model.vo.ContratoFavoritosVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class FavoritosContratoDaoImpl extends GenericDaoImplHibernate<ContratoFavoritosVO> implements FavoritosContratoDao, Serializable {

	private static final long serialVersionUID = 7044013135828017425L;

	@Override
	public List<ContratoFavoritosVO> recuperarFavoritos(BigDecimal idContrato) {
		List<Criterion> listCriterion = new ArrayList<Criterion>();
		listCriterion.add(Restrictions.eq("contrato", idContrato));	
		List<ContratoFavoritosVO> lista = buscarPorCriteria(listCriterion, null, null);
		if (lista != null && lista.size() > 0) {
			return lista;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContratoFavoritosVO> getListaFavoritosPorContrato(Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(ContratoFavoritosVO.class);
		criteria.add(Restrictions.eq("contrato", new BigDecimal(idContrato)));
		criteria.createAlias("funcion", "funcion", CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}
}
