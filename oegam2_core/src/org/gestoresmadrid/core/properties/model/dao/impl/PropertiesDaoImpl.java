package org.gestoresmadrid.core.properties.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.properties.model.dao.PropertiesDao;
import org.gestoresmadrid.core.properties.model.vo.PropertiesVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class PropertiesDaoImpl extends GenericDaoImplHibernate<PropertiesVO> implements PropertiesDao{

	private static final long serialVersionUID = -1265633110927606488L;

	
	@Override
	public PropertiesVO getPropertiePorIdYEntorno(Long id, String entorno) {
		Criteria criteria = getCurrentSession().createCriteria(PropertiesVO.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.add(Restrictions.eq("idContext", entorno));
		return (PropertiesVO) criteria.uniqueResult();
	}
	@Override
	public PropertiesVO getPropertiePorNombreYEntorno(String nombre, String entorno) {
		Criteria criteria = getCurrentSession().createCriteria(PropertiesVO.class);
		criteria.add(Restrictions.eq("nombre", nombre));
		criteria.add(Restrictions.eq("idContext", entorno));
		return (PropertiesVO) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PropertiesVO> getListaPropertiesPorContextoListeners(String contexto) {
		Criteria criteria = getCurrentSession().createCriteria(PropertiesVO.class);
		criteria.add(Restrictions.eq("idContext", contexto));
		criteria.createAlias("propertiesContext", "propertiesContext",CriteriaSpecification.LEFT_JOIN);
		List<PropertiesVO> listaBBDD = criteria.list();
		return listaBBDD;
	}
	
	@Override
	public PropertiesVO getPropertiePorId(Long id) {
		Criteria criteria = getCurrentSession().createCriteria(PropertiesVO.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.createAlias("propertiesContext", "propertiesContext",CriteriaSpecification.LEFT_JOIN);
		return (PropertiesVO)criteria.uniqueResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<PropertiesVO> getListaPropertiesPorContexto(String contexto) {
		Criteria criteria = getCurrentSession().createCriteria(PropertiesVO.class);
		criteria.add(Restrictions.eq("idContext", contexto));
		criteria.createAlias("propertiesContext", "propertiesContext",CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PropertiesVO> getListaConsultaPropertie(String contextoProperties, String nombre, String valor) {
		Criteria criteria = getCurrentSession().createCriteria(PropertiesVO.class);
		criteria.add(Restrictions.eq("idContext", contextoProperties));
		if(nombre != null && !nombre.isEmpty()){
			criteria.add(Restrictions.like("nombre", "%"+nombre+"%"));
		}
		if(valor != null && !valor.isEmpty()){
			criteria.add(Restrictions.like("valor", "%"+valor+"%"));
		}
		criteria.createAlias("propertiesContext", "propertiesContext",CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PropertiesVO> getListaRecargaPropertie(String contextoProperties, String nombre, String valor, String sort, String dir) {
		Criteria criteria = getCurrentSession().createCriteria(PropertiesVO.class);
		criteria.add(Restrictions.eq("idContext", contextoProperties));
		if(nombre != null && !nombre.isEmpty()){
			criteria.add(Restrictions.like("nombre", "%"+nombre+"%"));
		}
		if(valor != null && !valor.isEmpty()){
			criteria.add(Restrictions.like("valor", "%"+valor+"%"));
		}
		if(sort != null && !sort.isEmpty()){
			if(dir != null && !dir.isEmpty()){
				if("asc".equals(dir)){
					criteria.addOrder(Order.asc(sort));
				} else {
					criteria.addOrder(Order.desc(sort));
				}
			} 
		}
		criteria.createAlias("propertiesContext", "propertiesContext",CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}

	@Override
	public PropertiesVO findByContextAndName(String context, String name) {
		// TODO Auto-generated method stub
		Criteria criteria = getCurrentSession().createCriteria(PropertiesVO.class);
		criteria.add(Restrictions.eq("idContext", context));
		criteria.add(Restrictions.eq("nombre", name));
		
		return (PropertiesVO) criteria.uniqueResult();
	}
}
