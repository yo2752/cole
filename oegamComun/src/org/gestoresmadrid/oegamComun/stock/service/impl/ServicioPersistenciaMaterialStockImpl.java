package org.gestoresmadrid.oegamComun.stock.service.impl;

import java.util.Date;

import org.gestoresmadrid.core.trafico.materiales.model.dao.MaterialStockDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialStockVO;
import org.gestoresmadrid.oegamComun.stock.service.ServicioPersistenciaMaterialStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioPersistenciaMaterialStockImpl implements ServicioPersistenciaMaterialStock {

	private static final long serialVersionUID = 1891428845976726869L;

	@Autowired
	MaterialStockDao materialStockDao;

	@Override
	@Transactional(readOnly = true)
	public MaterialStockVO getMaterialPorTipoYJefatura(String tipoDocumento, String jefatura) {
		return materialStockDao.findStockPorJefaturaTipo(jefatura, tipoDocumento);
	}

	@Override
	@Transactional
	public void descontarStock(String jefatura, String tipo, Long cantidadExpediente) {
		MaterialStockVO materialStockVO = materialStockDao.findStockPorJefaturaTipo(jefatura, tipo);
		materialStockVO.setUnidades(materialStockVO.getUnidades() - cantidadExpediente);
		materialStockVO.setFecUltConsumo(new Date());
		materialStockDao.actualizar(materialStockVO);
	}
}
