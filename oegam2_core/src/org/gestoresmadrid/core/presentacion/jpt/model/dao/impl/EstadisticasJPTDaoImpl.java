package org.gestoresmadrid.core.presentacion.jpt.model.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.presentacion.jpt.model.dao.EstadisticasJPTDao;
import org.gestoresmadrid.core.presentacion.jpt.model.vo.EstadisticasJPTVO;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EstadisticasJPTDaoImpl extends GenericDaoImplHibernate<EstadisticasJPTVO> implements EstadisticasJPTDao {

	private static final long serialVersionUID = -1698146467843273020L;

	@Override
	public List<EstadisticasJPTVO> getListaEstadisticaJPT(Date fechaEstadistica, Long idTipoEstadistica, String jefaturaJpt) {
		List<Criterion> listCriterion = new ArrayList<Criterion>();
		if (fechaEstadistica != null) {
			listCriterion.add(Restrictions.eq("fecha", fechaEstadistica));
		}
		if (idTipoEstadistica != null) {
			listCriterion.add(Restrictions.eq("tipoEstadisticasJPTVO.idTipoEstadistica", idTipoEstadistica));
		}
		
		listCriterion.add(Restrictions.eq("jefaturaJpt", jefaturaJpt));
		
		List<EstadisticasJPTVO> result = buscarPorCriteria(listCriterion, null, null);
		if (result != null && result.size() > 0) {
			return result;
		}
		return null;
	}

	@Override
	public Long getCantidadTipoEstadistica(Date fechaEstadistica, Long idTipoEstadistica, String jefaturaJpt) {
		Long cantidad = new Long(0);
		List<EstadisticasJPTVO> result = getListaEstadisticaJPT(fechaEstadistica, idTipoEstadistica, jefaturaJpt);
		if (result != null && result.size() > 0) {
			for (EstadisticasJPTVO estadisticasJPTVO : result) {
				cantidad = cantidad + estadisticasJPTVO.getCantidad();
			}
		}
		return cantidad;
	}

	@Override
	public int getNumColegiadosDistintos(Date fechaEstadistica, Long idTipoEstadistica, String jefaturaJpt) {
		List<Criterion> listCriterion = new ArrayList<Criterion>();
		if (fechaEstadistica != null) {
			listCriterion.add(Restrictions.eq("fecha", fechaEstadistica));
		}
		if (idTipoEstadistica != null) {
			listCriterion.add(Restrictions.eq("tipoEstadisticasJPTVO.idTipoEstadistica", idTipoEstadistica));
		}
		
		listCriterion.add(Restrictions.eq("jefaturaJpt", jefaturaJpt));

		ProjectionList listaProyecciones = Projections.projectionList();
		listaProyecciones.add(Projections.distinct(Projections.property("numColegiado")));

		List<?> result = buscarPorCriteria(listCriterion, null, null, null, listaProyecciones);

		if (result != null && result.size() > 0) {
			return result.size();
		}
		return 0;
	}
}
