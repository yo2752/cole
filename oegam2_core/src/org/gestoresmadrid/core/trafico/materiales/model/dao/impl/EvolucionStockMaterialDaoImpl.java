package org.gestoresmadrid.core.trafico.materiales.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.materiales.model.dao.EvolucionStockMaterialDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.EvolucionStockMaterialesVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionStockMaterialDaoImpl extends GenericDaoImplHibernate<EvolucionStockMaterialesVO> implements EvolucionStockMaterialDao{

	private static final long serialVersionUID = 2170590707738864559L;

	@SuppressWarnings("unchecked")
	@Override
	public List<EvolucionStockMaterialesVO> getElementByIdStock(Long id) {
		Criteria criteria = getCurrentSession().createCriteria(EvolucionStockMaterialesVO.class);
		criteria.add(Restrictions.eq("idStock", id));
		return (List<EvolucionStockMaterialesVO>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void eliminarByIdStock(Long id) {
		Criteria criteria = getCurrentSession().createCriteria(EvolucionStockMaterialesVO.class);
		criteria.add(Restrictions.eq("idStock", id));
		List<EvolucionStockMaterialesVO> lista = (List<EvolucionStockMaterialesVO>) criteria.list();
		for(EvolucionStockMaterialesVO vo: lista) {
			borrar(vo);
		}

	}

}