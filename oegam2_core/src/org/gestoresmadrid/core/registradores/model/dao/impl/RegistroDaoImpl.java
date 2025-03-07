package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.RegistroDao;
import org.gestoresmadrid.core.registradores.model.vo.RegistroVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class RegistroDaoImpl extends GenericDaoImplHibernate<RegistroVO> implements RegistroDao, Serializable {

	private static final long serialVersionUID = 6069879321891698494L;

	@Override
	public RegistroVO getRegistroPorId(long idRegistro) {
		Criteria criteria = getCurrentSession().createCriteria(RegistroVO.class);
		criteria.add(Restrictions.eq("id", idRegistro));

		return (RegistroVO) criteria.uniqueResult();
	}

	@Override
	public List<RegistroVO> getRegistro(String idProvincia, String tipo) {
		Criteria criteria = getCurrentSession().createCriteria(RegistroVO.class);
		if(idProvincia!=null && !idProvincia.isEmpty()){
			criteria.add(Restrictions.eq("idProvincia", idProvincia));
		}
		criteria.add(Restrictions.eq("tipo", tipo));
		criteria.add(Restrictions.eq("estado",new BigDecimal ("0")));
		criteria.addOrder(Order.asc("nombre"));

		@SuppressWarnings("unchecked")
		List<RegistroVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		} else {
			return Collections.emptyList();
		}
	}
	
	@Override
	public RegistroVO getRegistroPorNombre(String nombre, String tipo) {
		Criteria criteria = getCurrentSession().createCriteria(RegistroVO.class);
		if(StringUtils.isNotBlank(nombre)){
			criteria.add(Restrictions.eq("nombre", nombre));
		}
		
		if(StringUtils.isNotBlank(tipo)){
			criteria.add(Restrictions.eq("tipo", tipo));
		}
		
		return (RegistroVO) criteria.uniqueResult();
	}
	
	@Override
	public Long getIdRegistro(String idRegistro, String idProvincia, String tipo) {
		Criteria criteria = getCurrentSession().createCriteria(RegistroVO.class);
		criteria.add(Restrictions.eq("idRegistro", idRegistro));
		if(StringUtils.isNotBlank(idProvincia)){
			criteria.add(Restrictions.eq("idProvincia", idProvincia));
		}
		criteria.add(Restrictions.eq("tipo", tipo));
		RegistroVO result = ((RegistroVO)criteria.uniqueResult());
		if(null!=result){
			return result.getId();
		}
		return null;
	}

}
