package org.gestoresmadrid.oegamComun.stock.service.impl;

import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialStockVO;
import org.gestoresmadrid.oegamComun.stock.service.ServicioMaterialStock;
import org.gestoresmadrid.oegamComun.stock.service.ServicioPersistenciaMaterialStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioMaterialStockImpl implements ServicioMaterialStock {

	private static final long serialVersionUID = 2708325305949985014L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioMaterialStockImpl.class);

	@Autowired
	ServicioPersistenciaMaterialStock servicioPersistenciaMaterialStock;

	@Override
	public Boolean existeStockMaterial(String tipoDocumento, String jefatura) {
		try {
			MaterialStockVO materialStockVO = servicioPersistenciaMaterialStock.getMaterialPorTipoYJefatura(tipoDocumento, jefatura);
			if (materialStockVO != null) {
				if (materialStockVO.getStockInvId() > 0) {
					return Boolean.TRUE;
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar el stock del permiso internacional, error: ", e);
		}
		return Boolean.FALSE;
	}

	@Override
	@Transactional
	public void descontarStock(String jefatura, String tipo, Long cantidadExpediente) {
		try {
			servicioPersistenciaMaterialStock.descontarStock(jefatura, tipo, cantidadExpediente);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de descontar el stock del permiso internacional, error: ", e);
		}
	}
}
