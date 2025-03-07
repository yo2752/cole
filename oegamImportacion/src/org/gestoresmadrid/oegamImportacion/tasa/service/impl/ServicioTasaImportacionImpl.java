package org.gestoresmadrid.oegamImportacion.tasa.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.gestoresmadrid.core.tasas.model.dao.TasaDao;
import org.gestoresmadrid.core.tasas.model.dao.TasaPegatinaDao;
import org.gestoresmadrid.core.tasas.model.vo.TasaPegatinaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.oegamImportacion.tasa.service.ServicioEvolucionTasaImportacion;
import org.gestoresmadrid.oegamImportacion.tasa.service.ServicioTasaImportacion;
import org.gestoresmadrid.oegamImportacion.trafico.service.ServicioTramiteTraficoImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioTasaImportacionImpl implements ServicioTasaImportacion {

	private static final long serialVersionUID = 363268821739698613L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioTasaImportacionImpl.class);

	@Autowired
	ServicioTramiteTraficoImportacion servicioTramiteTrafico;

	@Autowired
	ServicioEvolucionTasaImportacion servicioEvolucionTasa;

	@Resource
	TasaDao tasaDao;
	
	@Resource
	TasaPegatinaDao tasaPegatinaDao;

	@Override
	@Transactional(readOnly = true)
	public TasaVO getTasa(String codigoTasa, String tipoTasa) {
		try {
			TasaVO tasa = tasaDao.getTasa(codigoTasa, null, tipoTasa);
			if (tasa != null) {
				return tasa;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de recuperar la tasa: " + codigoTasa + ", error: ", e);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public TasaPegatinaVO getTasaPegatina(String codigoTasa, String tipoTasa) {
		try {
			TasaPegatinaVO tasaPegatinaVO = tasaPegatinaDao.getTasaPegatina(codigoTasa, null, tipoTasa);
			if (tasaPegatinaVO != null) {
				return tasaPegatinaVO;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de recuperar la tasa: " + codigoTasa + ", error: ", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public TasaVO esTasaValida(String codigoTasa, String tipoTasa) {
		try {
			TasaVO tasa = tasaDao.getTasa(codigoTasa, null, tipoTasa);
			if (tasa != null) {
				if (tasa.getNumExpediente() == null) {
					Boolean tasaAsignada = servicioTramiteTrafico.comprobarTasaAsignada(codigoTasa);
					if (!tasaAsignada) {
						return tasa;
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar la tasa: " + codigoTasa + ", error: ", e);
		}
		return null;
	}

	@Transactional
	@Override
	public String asignarTasa(TasaVO tasa, BigDecimal numExpediente, Long idUsuario) {
		String mensaje = "";
		try {
			tasa.setFechaAsignacion(new Date());
			tasa.setNumExpediente(numExpediente);
			servicioEvolucionTasa.insertarEvolucionTasa(tasa.getCodigoTasa(), numExpediente, ServicioEvolucionTasaImportacion.ASIGNAR, idUsuario);
			tasaDao.actualizar(tasa);
		} catch (Exception e) {
			mensaje = "No se ha podido asignar la tasa";
			log.error("Error al asignar la tasa al trámite: " + numExpediente, e, numExpediente.toString());
		}
		return mensaje;
	}
}
