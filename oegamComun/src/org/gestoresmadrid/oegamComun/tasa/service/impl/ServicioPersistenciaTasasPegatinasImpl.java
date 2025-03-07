package org.gestoresmadrid.oegamComun.tasa.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.exceptions.TransactionalException;
import org.gestoresmadrid.core.tasas.model.dao.EvolucionTasaDao;
import org.gestoresmadrid.core.tasas.model.dao.TasaPegatinaDao;
import org.gestoresmadrid.core.tasas.model.vo.EvolucionTasaPK;
import org.gestoresmadrid.core.tasas.model.vo.EvolucionTasaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaPegatinaVO;
import org.gestoresmadrid.oegamComun.tasa.service.ServicioComunTasa;
import org.gestoresmadrid.oegamComun.tasa.service.ServicioPersistenciaTasas;
import org.gestoresmadrid.oegamComun.tasa.service.ServicioPersistenciaTasasPegatinas;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.hibernate.HibernateException;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class ServicioPersistenciaTasasPegatinasImpl implements ServicioPersistenciaTasasPegatinas{

	private static final long serialVersionUID = 6930716959867922578L;

	@Autowired 
	TasaPegatinaDao tasaPegatinaDao;
	
	@Autowired
	ServicioPersistenciaTasas servicioPersistenciaTasas;
	
	@Autowired
	EvolucionTasaDao evolucionTasaDao;
	
	@Override
	@Transactional
	public void borrarTasa(String codigoTasa) {
		tasaPegatinaDao.borrar(getTasaPegatina(codigoTasa));
	}
	
	@Override
	@Transactional(readOnly = true)
	public TasaPegatinaVO getTasaPegatina(String codigoTasa) {
		return tasaPegatinaDao.detalle(codigoTasa);
	}
	
	@Override
	@Transactional(readOnly = true)
	public TasaPegatinaVO getTasaVO(String codigoTasa) {
		try {
			return tasaPegatinaDao.detalle(codigoTasa);
		} catch (HibernateException e) {
			throw new TransactionalException(e);
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<TasaPegatinaVO> getListaTasasPorCodigos(List<String> listaTasas) {
		try {
			return tasaPegatinaDao.getTasasPorLista(listaTasas);
		} catch (HibernateException e) {
			throw new TransactionalException(e);
		}
	}
	
	@Override
	@Transactional
	public String eliminar(String codigoTasa) {
		try {
			TasaPegatinaVO tasa = tasaPegatinaDao.detalle(codigoTasa);
			if (tasa != null) {
				if (tasaPegatinaDao.borrar(tasa)) {
					return "OK";
				} else {
					return "No se pudo borrar";
				}
			} else {
				return "el codigo de tasa no corresponde con ninguna tasa de pegatina";
			}
		} catch (HibernateException e) {
			throw new TransactionalException(e);
		}
	}
	
	@Override
	@Transactional
	public ResultadoBean cambiarFormatoATasaPegatina(TasaPegatinaVO tasaPegatina, Long idUsuario) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			tasaPegatinaDao.guardar(tasaPegatina);
			servicioPersistenciaTasas.borrarTasa(tasaPegatina.getCodigoTasa());
			insertarEvolucionTasa(tasaPegatina.getCodigoTasa(), null, ServicioComunTasa.CAMBIOFORMATO, null, new Date(), new BigDecimal(idUsuario));
		} catch (Exception e) {
			Log.error("Ha sucedido un error a la hora de cambiar el formato de la tasa a pegatina con codigo: " + tasaPegatina.getCodigoTasa() +", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el formato de la tasa a pegatina con codigo: " + tasaPegatina.getCodigoTasa());
		}
		if(resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}
	
	private void insertarEvolucionTasa(String codigoTasa, BigDecimal numExpediente, String accion, String motivo, Date fecha, BigDecimal idUsuario) {
		EvolucionTasaVO evolucionTasa = new EvolucionTasaVO();
		evolucionTasa.setAccion(accion);
		evolucionTasa.setMotivoBloqueo(motivo);
		if (numExpediente != null) {
			evolucionTasa.setNumExpediente(numExpediente);
		} else {
			evolucionTasa.setNumExpediente(null);
		}
		EvolucionTasaPK id = new EvolucionTasaPK();
		id.setCodigoTasa(codigoTasa);
		id.setFechaHora(fecha);
		evolucionTasa.setId(id);
		UsuarioVO usuario = new UsuarioVO();
		usuario.setIdUsuario(idUsuario.longValue());
		evolucionTasa.setUsuario(usuario);
		evolucionTasaDao.guardar(evolucionTasa);
	}

}
