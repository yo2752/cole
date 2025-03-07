package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.CertifCargoDao;
import org.gestoresmadrid.core.registradores.model.vo.CertifCargoVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class CertifCargoDaoImpl extends GenericDaoImplHibernate<CertifCargoVO> implements CertifCargoDao {

	private static final long serialVersionUID = -7949493055189408919L;

	@Override
	public CertifCargoVO getCertificante(BigDecimal idTramiteRegistro, String cifSociedad, String nifCargo, String codigoCargo, String idFirma) {
		Criteria criteria = getCurrentSession().createCriteria(CertifCargoVO.class);
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
		if (idFirma != null && !idFirma.isEmpty()) {
			criteria.add(Restrictions.eq("idFirma", idFirma));
		}
		criteria.createAlias("sociedadCargo", "sociedadCargo");
		criteria.createAlias("sociedadCargo.personaCargo", "sociedadCargo.personaCargo");
		@SuppressWarnings("unchecked")
		List<CertifCargoVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public List<CertifCargoVO> getCertificantes(BigDecimal idTramiteRegistro, String nifCargo) {
		Criteria criteria = getCurrentSession().createCriteria(CertifCargoVO.class);
		if (idTramiteRegistro != null) {
			criteria.add(Restrictions.eq("id.idTramiteRegistro", idTramiteRegistro));
		}
		if (nifCargo != null && !nifCargo.isEmpty()) {
			criteria.add(Restrictions.eq("id.nifCargo", nifCargo));
		}
		criteria.createAlias("sociedadCargo", "sociedadCargo");
		criteria.createAlias("sociedadCargo.personaCargo", "sociedadCargo.personaCargo");
		@SuppressWarnings("unchecked")
		List<CertifCargoVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

	@Override
	public List<CertifCargoVO> getCertificantesIdFirma(BigDecimal idTramiteRegistro, String nifCargo, boolean idFirmaNull) {
		Criteria criteria = getCurrentSession().createCriteria(CertifCargoVO.class);
		if (idTramiteRegistro != null) {
			criteria.add(Restrictions.eq("id.idTramiteRegistro", idTramiteRegistro));
		}
		if (nifCargo != null && !nifCargo.isEmpty()) {
			criteria.add(Restrictions.eq("id.nifCargo", nifCargo));
		}
		if (idFirmaNull) {
			criteria.add(Restrictions.isNull("idFirma"));
		} else {
			criteria.add(Restrictions.isNotNull("idFirma"));
		}

		@SuppressWarnings("unchecked")
		List<CertifCargoVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}
}
