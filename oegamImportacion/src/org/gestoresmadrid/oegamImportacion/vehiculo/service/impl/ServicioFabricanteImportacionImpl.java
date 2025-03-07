package org.gestoresmadrid.oegamImportacion.vehiculo.service.impl;

import org.gestoresmadrid.core.vehiculo.model.dao.FabricanteDao;
import org.gestoresmadrid.core.vehiculo.model.vo.FabricanteVO;
import org.gestoresmadrid.oegamImportacion.vehiculo.service.ServicioFabricanteImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioFabricanteImportacionImpl implements ServicioFabricanteImportacion {

	private static final long serialVersionUID = 7668807588568671475L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioFabricanteImportacionImpl.class);

	@Autowired
	FabricanteDao fabricanteDao;

	@Override
	@Transactional
	public FabricanteVO getFabricante(String fabricante) {
		try {
			FabricanteVO fabricanteVO = fabricanteDao.getFabricante(fabricante);
			if (fabricanteVO != null) {
				return fabricanteVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el fabricante", e);
		}
		return null;
	}
}
