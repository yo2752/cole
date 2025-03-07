package org.gestoresmadrid.core.vehiculo.model.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.vehiculo.model.dao.FabricanteDao;
import org.gestoresmadrid.core.vehiculo.model.vo.FabricanteVO;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaDgtVO;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class FabricanteDaoImpl extends GenericDaoImplHibernate<FabricanteVO> implements FabricanteDao, Serializable {

	private static final long serialVersionUID = -8814935185682568808L;

	@Override
	public FabricanteVO getFabricante(String fabricante) {
		List<Criterion> listCriterion = new ArrayList<>();
		listCriterion.add(Restrictions.like("fabricante", fabricante));

		List<FabricanteVO> lista = buscarPorCriteria(listCriterion, null, null);

		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public List<FabricanteVO> getFabricantesInactivos(ArrayList<Long> arrayInactivos) {
		List<Criterion> listCriterion = new ArrayList<>();

		if (arrayInactivos != null && !arrayInactivos.isEmpty()) {
			listCriterion.add(Restrictions.in("codFabricante", arrayInactivos));
		}

		List<FabricanteVO> lista = buscarPorCriteria(listCriterion, null, null);

		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

	@Override
	public List<FabricanteVO> getFabricantePorCodMarca(String codMarca) {
		List<Criterion> listCriterion = new ArrayList<>();

		// listCriterion.add(Restrictions.like("marcas.codigoMarca", new BigDecimal(codMarca)));
		List<AliasQueryBean> entitiesJoin = new ArrayList<>();
		entitiesJoin.add(new AliasQueryBean(FabricanteVO.class, "marcas", "marcas", CriteriaSpecification.LEFT_JOIN));

		List<FabricanteVO> lista = buscarPorCriteria(listCriterion, entitiesJoin, null);
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

	@Override
	@Transactional
	public MarcaDgtVO recuperarMarcaConFabricantes(String codMarca) {
		Criteria criteria = getCurrentSession().createCriteria(MarcaDgtVO.class);

		criteria.add(Restrictions.eq("codigoMarca", new Long(codMarca)));
		criteria.setFetchMode("fabricantes", FetchMode.JOIN);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		return (MarcaDgtVO) criteria.uniqueResult();
	}
}