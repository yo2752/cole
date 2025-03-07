package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import org.gestoresmadrid.core.trafico.materiales.model.dao.IncidenciaSerialDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.IncidenciaSerialVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.IncidenciaVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaIncidenciasSerial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioConsultaIncidenciasSerialImpl implements ServicioConsultaIncidenciasSerial {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3886828157684808085L;
	
	@Autowired IncidenciaSerialDao incidenciaSerialDao;

	@Override
	public IncidenciaSerialVO obtenerIncidenciaSerial(IncidenciaVO incidenciaVO, String serial) {
		// TODO Auto-generated method stub
		for(IncidenciaSerialVO item: incidenciaVO.getListaSeriales()) {
			if (item.getPk().getNumSerie().equals(serial)) {
				return item;
			}
		}
		return null;
	}

	@Override
	public IncidenciaSerialVO obtenerIncidenciaSerial(IncidenciaVO incidenciaVO, Long incidenciaInv) {
		// TODO Auto-generated method stub
		for(IncidenciaSerialVO item: incidenciaVO.getListaSeriales()) {
			if (item.getIncidenciaInvId().equals(incidenciaInv)) {
				return item;
			}
		}

		return null;
	}

	@Override
	public IncidenciaSerialVO obtenerIncidenciaByIncidenciaConsejo(Long incidenciaInvId) {
		// TODO Auto-generated method stub
		
		return incidenciaSerialDao.findByIncidenciaConsejo(incidenciaInvId);
	}

}
