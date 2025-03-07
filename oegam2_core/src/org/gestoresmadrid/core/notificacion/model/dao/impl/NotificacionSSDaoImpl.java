package org.gestoresmadrid.core.notificacion.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.notificacion.model.dao.NotificacionSSDao;
import org.gestoresmadrid.core.notificacion.model.vo.NotificacionSSVO;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class NotificacionSSDaoImpl extends GenericDaoImplHibernate<NotificacionSSVO> implements NotificacionSSDao{
	
	private static final long serialVersionUID = 8348314742235559198L;
	
	public NotificacionSSDaoImpl() {
		super();
	}

	@Override
	@Transactional(readOnly=true)
	public List<NotificacionSSVO> getAllNotificaciones(){
		
		return buscarPorCriteria(new ArrayList<Criterion>(), null, null);
	}
	
	@Override
	@Transactional
	public void insertNotificaciones(List<NotificacionSSVO> listaNotificaciones){
		
		for (NotificacionSSVO notificacionSS : listaNotificaciones) {
			guardarOActualizar(notificacionSS);
		}
	}
	
	@Override
	@Transactional(readOnly=true)
	public NotificacionSSVO getNotificacionByIdNumColegiado (int codigoNotificacion, String numColegiado){
		List<Criterion> listCriterion = new ArrayList<Criterion>();
		if (codigoNotificacion != -1) {
			listCriterion.add(Restrictions.eq("id.codigo", codigoNotificacion));
			listCriterion.add(Restrictions.eq("id.numColegiado", numColegiado));
			List<NotificacionSSVO> lista = buscarPorCriteria(listCriterion, null, null);
			if (lista != null && !lista.isEmpty()) {
				return lista.get(0);
			}
		}

		return null;
		
	}
	
	@Override
	@Transactional(readOnly=true)
	public int recuperaUltimoIdNotificacionByRolNumColegiado (int rol, BigDecimal numColegiado){
		/*
		 * ROL=0 - Todas
		 * ROL=1 - Propias
		 * ROL=2 - Red
		 * ROL=3 - Apoderdante
		 */
		List<Criterion> listCriterion = new ArrayList<Criterion>();
		listCriterion.add(Restrictions.eq("rol", rol));
		listCriterion.add(Restrictions.eq("id.numColegiado", numColegiado.toString()));
		List<String> listaOrden = new ArrayList<String>();
		listaOrden.add("id.codigo");
		List<NotificacionSSVO> lista = buscarPorCriteria(-1, -1, listCriterion, "desc", listaOrden);
		if (lista.isEmpty()){
			return -1;
		}else{
			NotificacionSSVO ultimoElemento = lista.get(0);
			return ultimoElemento.getId().getCodigo();
		}
	}

	@Override
	@Transactional
	public boolean cambiarEstadoNotificacion(int codigoNotificacion, int estado, String numColegiado) {
		boolean estadoCambiado = false;
		List<Criterion> listaCriterion = new ArrayList<Criterion>();
		listaCriterion.add(Restrictions.eq("id.codigo", codigoNotificacion));
		listaCriterion.add(Restrictions.eq("id.numColegiado", numColegiado));
		List<NotificacionSSVO> lista = buscarPorCriteria(-1, -1, listaCriterion, null, null);
		if (!lista.isEmpty()) {
			NotificacionSSVO notificacion = lista.get(0);
			if (estado == 2) {
				notificacion.setEstado(estado);
				notificacion.setDescripcionEstado("Notificada por aceptación");
			} else if (estado == 3){
				notificacion.setEstado(estado);
				notificacion.setDescripcionEstado("Notificada por rechazo");
			} else if (estado == 1){
				notificacion.setEstado(estado);
				notificacion.setDescripcionEstado("Acuse Solicitado");
			} else if (estado == 5){
				notificacion.setEstado(estado);
				notificacion.setDescripcionEstado("Finalizado con error");
			}
			estadoCambiado = actualizar(notificacion) != null;
		}
		return estadoCambiado;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<NotificacionSSVO> obtenerNotificacionesPendientesByNumColegiado(String numColegiado){
		List<Criterion> listaCriterion = new ArrayList<Criterion>();
		listaCriterion.add(Restrictions.eq("id.numColegiado", numColegiado));
		listaCriterion.add(Restrictions.eq("estado", new Integer(0)));
		listaCriterion.add(Restrictions.ge("fechaFinDisponibilidad", new Date()));
		List<String> listaOrden = new ArrayList<String>();
		listaOrden.add("fechaFinDisponibilidad");
		List<NotificacionSSVO> lista = buscarPorCriteria(-1, -1, listaCriterion, "asc", listaOrden);
		return lista;
	}
}
