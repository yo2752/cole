package org.gestoresmadrid.core.pegatinas.model.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pegatinas.model.dao.PegatinasNotificaDao;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasNotificaVO;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import utilidades.estructuras.Fecha;

@Repository
public class PegatinasNotificaDaoImpl extends GenericDaoImplHibernate<PegatinasNotificaVO> implements PegatinasNotificaDao{
	
	private static final long serialVersionUID = 5446003534181996255L;

	
	private static final String horaFInDia = "23:59";
	private static final int N_SEGUNDOS = 59;
	
	@Autowired
	UtilesFecha utilesFecha;

	
	public PegatinasNotificaDaoImpl() {
		super();
	}

	
	@Override
	public List<PegatinasNotificaVO> getAllPegatinasHoyByJefatura(String jefatura) {
		Fecha fecha = utilesFecha.getFechaActual();
		Date fin = new Date();
		List<Criterion> listCriterion = new ArrayList<Criterion>();
		try {
			utilesFecha.setHoraEnDate(fin, horaFInDia);
			utilesFecha.setSegundosEnDate(fin, N_SEGUNDOS);
			listCriterion.add(Restrictions.eq("jefatura", jefatura));
			listCriterion.add(Restrictions.between("fecha", fecha.getFecha(), fin));
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buscarPorCriteria(listCriterion, null, null);
	}
}
