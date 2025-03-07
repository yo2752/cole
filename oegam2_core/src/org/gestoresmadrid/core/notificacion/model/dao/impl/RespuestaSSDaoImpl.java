package org.gestoresmadrid.core.notificacion.model.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.notificacion.model.dao.NotificacionSSDao;
import org.gestoresmadrid.core.notificacion.model.dao.RespuestaSSDao;
import org.gestoresmadrid.core.notificacion.model.vo.NotificacionSSVO;
import org.gestoresmadrid.core.notificacion.model.vo.RespuestaSSVO;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RespuestaSSDaoImpl extends GenericDaoImplHibernate<RespuestaSSVO> implements RespuestaSSDao {
	
	private static final long serialVersionUID = -8895692827870662711L;
	
	@Autowired
	private NotificacionSSDao notificacionSSDao;

	@Override
	@Transactional(readOnly=true)
	public List<RespuestaSSVO> getListadoRespuestas() {
		return buscarPorCriteria(new ArrayList<Criterion>(), null, null);
	}

	@Override
	@Transactional(readOnly=true)
	public List<RespuestaSSVO> getListadoRespuestaPorFecha(Date fechaInicio, Date fechaFin) {
		List<Criterion> listaCriterios = new ArrayList<Criterion>();
		
		if (fechaInicio != null && fechaFin == null) {
			listaCriterios.add(Restrictions.ge("fechaNotificacion", fechaInicio));
		} else if (fechaInicio == null && fechaFin != null) {
			listaCriterios.add(Restrictions.le("fechaNotificacion", fechaFin));
		} else if (fechaInicio != null && fechaFin != null) {
			listaCriterios.add(Restrictions.between("fechaNotificacion", fechaInicio, fechaFin));
		}
		
		List<RespuestaSSVO> lista = buscarPorCriteria(listaCriterios, null, null);
		
		return lista;
	}
	
	@Override
	@Transactional
	public void insertarRespuesta(Integer idCodigo, String estado, String numColegiado) {
		NotificacionSSVO notificacion = notificacionSSDao.getNotificacionByIdNumColegiado(idCodigo, numColegiado);
		if (notificacion != null) {
			RespuestaSSVO respuesta = new RespuestaSSVO();
			respuesta.setNotificacion(notificacion);
			respuesta.setEstado(estado);
			respuesta.setFechaNotificacion(Calendar.getInstance().getTime());
			guardar(respuesta);
		}
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<RespuestaSSVO> getResultadoByIdCodigoNumColegiado(Integer idCodigo, String numColegiado){
		List<Criterion> listaCriterios = new ArrayList<Criterion>();
		
		List<String> listOrden = new ArrayList<String>();
		listOrden.add("idResultado");
		
		listaCriterios.add(Restrictions.eq("notificacion.id.codigo", idCodigo));
		listaCriterios.add(Restrictions.eq("notificacion.id.numColegiado", numColegiado));
		
		List<RespuestaSSVO> lista = buscarPorCriteria(listaCriterios, "asc", listOrden, null, null);
		
		return lista;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<RespuestaSSVO> getPDFrespuesta(Integer idCodigo, String numColegiado){
		List<Criterion> listaCriterios = new ArrayList<Criterion>();
		
		listaCriterios.add(Restrictions.eq("notificacion.id.codigo", idCodigo));
		listaCriterios.add(Restrictions.eq("notificacion.id.numColegiado", numColegiado));
		listaCriterios.add(Restrictions.eq("estado", "PDF Custodiado"));
		
		List<RespuestaSSVO> lista = buscarPorCriteria(listaCriterios, null, null);
		
		return lista;
	}
	
	@Override
	@Transactional
	public void insertRespuestaNotificaciones(List<RespuestaSSVO> listaResultadoNotificaciones){
		
		for (RespuestaSSVO respuesta : listaResultadoNotificaciones) {
			guardar(respuesta);
		}
	}

}