package org.gestoresmadrid.oegam2comun.estacionITV.model.service;

import java.util.List;

import org.gestoresmadrid.core.estacionITV.model.vo.EstacionITVVO;
import org.gestoresmadrid.oegam2comun.estacionITV.view.bean.EstacionITV;

import escrituras.beans.ResultBean;
import trafico.beans.EstacionITVBean;

public interface ServicioEstacionITV {

	public ResultBean altaEstacionITV(EstacionITV estacion);

	List<EstacionITVBean> getEstacionesItv(String idProvincia);

	List<EstacionITVVO> getEstacion(String id);
}
