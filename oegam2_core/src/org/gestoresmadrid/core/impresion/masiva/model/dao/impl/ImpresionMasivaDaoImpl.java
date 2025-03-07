package org.gestoresmadrid.core.impresion.masiva.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.impresion.masiva.model.dao.ImpresionMasivaDao;
import org.gestoresmadrid.core.impresion.masiva.model.vo.ImpresionMasivaVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ImpresionMasivaDaoImpl extends GenericDaoImplHibernate<ImpresionMasivaVO> implements ImpresionMasivaDao {

	private static final long serialVersionUID = 4726942519371002288L;

	@Override
	public ImpresionMasivaVO getImpresionMasivaPorNombreFichero(String nombreFichero) {
		List<Criterion> listCriterion = new ArrayList<Criterion>();
		listCriterion.add(Restrictions.eq("nombreFichero", nombreFichero));

		List<ImpresionMasivaVO> lista = buscarPorCriteria(listCriterion, null, null);

		if (lista != null && lista.size() > 0) {
			return lista.get(0);
		}

		return null;
	}
}
