package org.gestoresmadrid.oegam2comun.vehiculo.component.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.vehiculo.model.dao.VehiculoTramiteTraficoDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloVehiculoTramiteTraficoPaginated")
@Transactional(readOnly = true)
public class ModeloVehiculoTramiteTraficoPaginatedImpl extends AbstractModelPagination {

	@Resource
	private VehiculoTramiteTraficoDao vehiculoTramiteTraficoDao;

	@Override
	protected GenericDao<?> getDao() {
		return vehiculoTramiteTraficoDao;
	}

	public VehiculoTramiteTraficoDao getVehiculoTramiteTraficoDao() {
		return vehiculoTramiteTraficoDao;
	}

	public void setVehiculoTramiteTraficoDao(VehiculoTramiteTraficoDao vehiculoTramiteTraficoDao) {
		this.vehiculoTramiteTraficoDao = vehiculoTramiteTraficoDao;
	}
}
