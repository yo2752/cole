package org.gestoresmadrid.core.trafico.liquidacion620.model.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.liquidacion620.beans.ResultadoLiquidacion620;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;

public interface Liquidacion620Dao extends GenericDao<VehiculoVO>, Serializable {

	List<ResultadoLiquidacion620> getListaLiquidaciones620(Date fechaIni, Date fechaFin, String numColegiado);
}