package org.gestoresmadrid.core.licencias.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.licencias.model.dao.LcDatosPlantaBajaDao;
import org.gestoresmadrid.core.licencias.model.vo.LcDatosPlantaBajaVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LcDatosPlantaBajaDaoImpl extends GenericDaoImplHibernate<LcDatosPlantaBajaVO> implements LcDatosPlantaBajaDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6531629929974359646L;

	@Override
	public LcDatosPlantaBajaVO getDatosPlantaBaja(long id) {
		Criteria criteria = getCurrentSession().createCriteria(LcDatosPlantaBajaVO.class);
		criteria.add(Restrictions.eq("idDatosPlantaBaja", id));
		return (LcDatosPlantaBajaVO) criteria.uniqueResult();
	}

	@Override
	public List<LcDatosPlantaBajaVO> getPlantasBaja(long id) {
		Criteria criteria = getCurrentSession().createCriteria(LcDatosPlantaBajaVO.class);
		if (id != 0) {
			criteria.add(Restrictions.eq("idInfoEdificioBaja", id));
		}
		@SuppressWarnings("unchecked")
		List<LcDatosPlantaBajaVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}
}
