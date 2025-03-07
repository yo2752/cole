package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.AsistenteDao;
import org.gestoresmadrid.core.registradores.model.vo.AsistenteVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class AsistenteDaoImpl extends GenericDaoImplHibernate<AsistenteVO> implements AsistenteDao {

	private static final long serialVersionUID = -6946214478875501972L;

	@Override
	public AsistenteVO getAsistente(BigDecimal idTramiteRegistro, String cifSociedad, String nifCargo, String codigoCargo, Long idReunion) {
		Criteria criteria = getCurrentSession().createCriteria(AsistenteVO.class);
		if (idTramiteRegistro != null) {
			criteria.add(Restrictions.eq("id.idTramiteRegistro", idTramiteRegistro));
		}
		if (cifSociedad != null && !cifSociedad.isEmpty()) {
			criteria.add(Restrictions.eq("id.cifSociedad", cifSociedad));
		}
		if (nifCargo != null && !nifCargo.isEmpty()) {
			criteria.add(Restrictions.eq("id.nifCargo", nifCargo));
		}
		if (codigoCargo != null && !codigoCargo.isEmpty()) {
			criteria.add(Restrictions.eq("id.codigoCargo", codigoCargo));
		}
		if (idReunion != null) {
			criteria.add(Restrictions.eq("id.idReunion", idReunion));
		}
		criteria.createAlias("sociedadCargo", "sociedadCargo");
		criteria.createAlias("sociedadCargo.personaCargo", "sociedadCargo.personaCargo");
		@SuppressWarnings("unchecked")
		List<AsistenteVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public List<AsistenteVO> getAsistentes(BigDecimal idTramiteRegistro, String nifCargo) {
		Criteria criteria = getCurrentSession().createCriteria(AsistenteVO.class);
		if (idTramiteRegistro != null) {
			criteria.add(Restrictions.eq("id.idTramiteRegistro", idTramiteRegistro));
		}
		if (nifCargo != null && !nifCargo.isEmpty()) {
			criteria.add(Restrictions.eq("id.nifCargo", nifCargo));
		}
		criteria.createAlias("sociedadCargo", "sociedadCargo");
		criteria.createAlias("sociedadCargo.personaCargo", "sociedadCargo.personaCargo");
		@SuppressWarnings("unchecked")
		List<AsistenteVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}
}
