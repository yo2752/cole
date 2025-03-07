package org.gestoresmadrid.core.tipoInmueble.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.modelos.model.enumerados.TipoBien;
import org.gestoresmadrid.core.tipoInmueble.model.dao.TipoInmuebleDao;
import org.gestoresmadrid.core.tipoInmueble.model.vo.TipoInmuebleVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


@Repository
public class TipoInmuebleDaoImpl extends GenericDaoImplHibernate<TipoInmuebleVO> implements TipoInmuebleDao {

	private static final long serialVersionUID = 1553581748448736129L;

	
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoInmuebleVO> getListaTiposInmueblesPorTipo(TipoBien tipoBien) {
		Criteria criteria = getCurrentSession().createCriteria(TipoInmuebleVO.class);
		criteria.add(Restrictions.eq("id.idTipoBien", tipoBien.getValorEnum()));
		return criteria.list();
	}
}
