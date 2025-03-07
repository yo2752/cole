package org.gestoresmadrid.oegam2comun.vehiculo.model.service.impl;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.vehiculo.model.dao.TipoReduccionDao;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoReduccionVO;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioTipoReduccion;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioTipoReduccionImpl implements ServicioTipoReduccion {

	private static final long serialVersionUID = -5105168613594884149L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioTipoReduccionImpl.class);

	@Autowired
	private TipoReduccionDao tipoReduccionDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public TipoReduccionVO getTipoReduccion(String tipoReduccion) {
		try {
			TipoReduccionVO tipoReduccionVO = tipoReduccionDao.getTipoReduccion(tipoReduccion);
			if (tipoReduccionVO != null) {
				return tipoReduccionVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el tipo de reducción", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TipoReduccionVO> getListaTiposReducciones() {
		List<TipoReduccionVO> listaTiposReducciones = tipoReduccionDao.getListaTipoReducciones();
		if (listaTiposReducciones != null && !listaTiposReducciones.isEmpty()) {
			return conversor.transform(listaTiposReducciones, TipoReduccionVO.class);
		}
		return Collections.emptyList();
	}

}