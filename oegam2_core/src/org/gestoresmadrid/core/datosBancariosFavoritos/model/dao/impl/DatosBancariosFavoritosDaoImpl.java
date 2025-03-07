package org.gestoresmadrid.core.datosBancariosFavoritos.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.datosBancariosFavoritos.model.dao.DatosBancariosFavoritosDao;
import org.gestoresmadrid.core.datosBancariosFavoritos.model.vo.DatosBancariosFavoritosVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class DatosBancariosFavoritosDaoImpl extends GenericDaoImplHibernate<DatosBancariosFavoritosVO> implements DatosBancariosFavoritosDao{

	private static final long serialVersionUID = -5121672002140858754L;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DatosBancariosFavoritosVO> getListaDatosBancariosPorIdContrato(Long idContrato, BigDecimal formaPago, BigDecimal estado) {
		Criteria criteria = getCurrentSession().createCriteria(DatosBancariosFavoritosVO.class);
		criteria.add(Restrictions.eq("idContrato", idContrato));
		if(formaPago != null){
			criteria.add(Restrictions.eq("formaPago", formaPago));
		}
		if(estado != null){
			criteria.add(Restrictions.eq("estado", estado));
		}
		criteria.add(Restrictions.isNull("fechaBaja"));
		criteria.addOrder(Order.asc("descripcion"));
		return criteria.list();
	}

	@Override
	public DatosBancariosFavoritosVO getDatoBancarioFavoritoPorId(Long idDatosBancarios) {
		Criteria criteria = getCurrentSession().createCriteria(DatosBancariosFavoritosVO.class);
		criteria.add(Restrictions.eq("idDatosBancarios", idDatosBancarios));
		return (DatosBancariosFavoritosVO) criteria.uniqueResult();
	}
	
	@Override
	public String getDatoBancario(Long idDatoBancario){
		Criteria criteria = getCurrentSession().createCriteria(DatosBancariosFavoritosVO.class);
		criteria.add(Restrictions.eq("idDatosBancarios", idDatoBancario));
		criteria.setProjection(Projections.property("datosBancarios"));
		return (String) criteria.uniqueResult();
	}
}
