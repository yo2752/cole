package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.AcuerdoDao;
import org.gestoresmadrid.core.registradores.model.vo.AcuerdoVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class AcuerdoDaoImpl extends GenericDaoImplHibernate<AcuerdoVO> implements AcuerdoDao {

	private static final long serialVersionUID = 2998645411600106982L;

	@Override
	public AcuerdoVO getAcuerdo(Long idAcuerdo) {
		Criteria criteria = getCurrentSession().createCriteria(AcuerdoVO.class);
		if (idAcuerdo != null) {
			criteria.add(Restrictions.eq("idAcuerdo", idAcuerdo));
		}
		criteria.createAlias("sociedadCargo", "sociedadCargo");
		criteria.createAlias("sociedadCargo.personaCargo", "sociedadCargo.personaCargo");
		@SuppressWarnings("unchecked")
		List<AcuerdoVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public AcuerdoVO getAcuerdoPorSociedadCargo(String numColegiado, String cifSociedad, String nifCargo, String codigoCargo, BigDecimal idTramiteRegistro, String tipoAcuerdo) {
		Criteria criteria = getCurrentSession().createCriteria(AcuerdoVO.class);
		if (numColegiado != null && !numColegiado.isEmpty()) {
			criteria.add(Restrictions.eq("sociedadCargo.id.numColegiado", numColegiado));
		}
		if (cifSociedad != null && !cifSociedad.isEmpty()) {
			criteria.add(Restrictions.eq("sociedadCargo.id.cifSociedad", cifSociedad));
		}
		if (nifCargo != null && !nifCargo.isEmpty()) {
			criteria.add(Restrictions.eq("sociedadCargo.id.nifCargo", nifCargo));
		}
		if (codigoCargo != null && !codigoCargo.isEmpty()) {
			criteria.add(Restrictions.eq("sociedadCargo.id.codigoCargo", codigoCargo));
		}
		if (idTramiteRegistro != null) {
			criteria.add(Restrictions.eq("idTramiteRegistro", idTramiteRegistro));
		}
		if (tipoAcuerdo != null && !tipoAcuerdo.isEmpty()) {
			criteria.add(Restrictions.eq("tipoAcuerdo", tipoAcuerdo));
		}
		@SuppressWarnings("unchecked")
		List<AcuerdoVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public List<AcuerdoVO> getAcuerdos(BigDecimal idTramiteRegistro) {
		Criteria criteria = getCurrentSession().createCriteria(AcuerdoVO.class);
		if (idTramiteRegistro != null) {
			criteria.add(Restrictions.eq("idTramiteRegistro", idTramiteRegistro));
		}
		criteria.createAlias("sociedadCargo", "sociedadCargo");
		criteria.createAlias("sociedadCargo.personaCargo", "sociedadCargo.personaCargo");
		@SuppressWarnings("unchecked")
		List<AcuerdoVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}
}
