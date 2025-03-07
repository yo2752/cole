package org.gestoresmadrid.oegam2comun.bien.model.service.impl;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.bien.model.dao.UnidadMetricaDao;
import org.gestoresmadrid.core.bien.model.vo.UnidadMetricaVO;
import org.gestoresmadrid.oegam2comun.bien.model.service.ServicioUnidadMetrica;
import org.gestoresmadrid.oegam2comun.bien.view.dto.UnidadMetricaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioUnidadMetricaImpl implements ServicioUnidadMetrica{

	private static final long serialVersionUID = -4565565232633622609L;

	@Autowired
	private Conversor conversor;
	
	@Autowired
	private UnidadMetricaDao unidadMetricaDao;
	
	@Override
	@Transactional
	public List<UnidadMetricaDto> getListaUnidadesMetricas() {
		List<UnidadMetricaVO> lista = unidadMetricaDao.getListaUnidadesMetricas();
		if(lista != null && !lista.isEmpty()){
			return conversor.transform(lista, UnidadMetricaDto.class);
		}
		return Collections.emptyList();
	}
}
