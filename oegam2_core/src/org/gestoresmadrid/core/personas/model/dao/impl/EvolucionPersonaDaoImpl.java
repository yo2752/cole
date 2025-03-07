package org.gestoresmadrid.core.personas.model.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.personas.model.dao.EvolucionPersonaDao;
import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaVO;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionPersonaDaoImpl extends GenericDaoImplHibernate<EvolucionPersonaVO> implements EvolucionPersonaDao, Serializable {
	
	private static final long serialVersionUID = 3870805109073110935L;
	
	@Override
	public List<EvolucionPersonaVO> getEvolucionPersona(String nif, String numColegiado, ArrayList<String> tipoActualizacion) {
		List<Criterion> listCriterion = new ArrayList<Criterion>();
		if (nif != null && !nif.isEmpty()) {
			listCriterion.add(Restrictions.eq("id.nif", nif));
		}
		if (numColegiado != null && !numColegiado.isEmpty()) {
			listCriterion.add(Restrictions.eq("id.numColegiado", numColegiado));
		}
		if (tipoActualizacion != null && tipoActualizacion.size() > 0) {
			listCriterion.add(Restrictions.in("tipoActualizacion", tipoActualizacion));
		}

		List<AliasQueryBean> listaAlias = new ArrayList<AliasQueryBean>();
		listaAlias.add(new AliasQueryBean(UsuarioVO.class, "usuario", "usuario", CriteriaSpecification.LEFT_JOIN));

		ArrayList<String> orden = new ArrayList<String>();
		orden.add("id.fechaHora");

		List<EvolucionPersonaVO> lista = buscarPorCriteria(listCriterion, GenericDaoImplHibernate.ordenDes, orden, listaAlias, null);

		if (lista != null && lista.size() > 0) {
			return lista;
		}
		return null;
	}
}
