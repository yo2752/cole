package org.gestoresmadrid.core.imputaciones.model.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.imputaciones.model.dao.ImputacionHorasDao;
import org.gestoresmadrid.core.imputaciones.model.vo.ImputacionHorasVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ImputacionHorasDaoImpl extends GenericDaoImplHibernate<ImputacionHorasVO> implements ImputacionHorasDao, Serializable{
	private static final long serialVersionUID = 1L;

	@Override
	public ImputacionHorasVO guardarImputacion(ImputacionHorasVO imputacionHoras) {
		imputacionHoras.setIdImputacionHoras((Long) guardar(imputacionHoras));
		return imputacionHoras;
	}

	@Override
	public List<ImputacionHorasVO> getImputacionesPorFechaUsuario(ImputacionHorasVO imputacionHorasVO) {
		List <Criterion> listCriterion = new ArrayList<Criterion>();
		
			listCriterion.add(Restrictions.eq("usuario.idUsuario", imputacionHorasVO.getUsuario().getIdUsuario()));
		
		if(imputacionHorasVO.getFechaImputacion() != null){
			listCriterion.add(Restrictions.eq("fechaImputacion", imputacionHorasVO.getFechaImputacion()));
		}
		
		
		List<ImputacionHorasVO> lista = buscarPorCriteria(listCriterion, null, null);
		return lista;
	}
}
