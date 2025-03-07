package org.gestoresmadrid.oegam2comun.trafico.model.service.impl;

import java.util.List;

import org.gestoresmadrid.core.modelos.model.vo.FundamentoExencion620VO;
import org.gestoresmadrid.core.trafico.model.dao.FundamentoExencion620Dao;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioFundamentoExencion620;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioFundamentoExencion620Impl implements ServicioFundamentoExencion620 {

	private static final long serialVersionUID = 7920646144736949182L;

	@Autowired
	FundamentoExencion620Dao fundamentoExencion620Dao;

	@Override
	@Transactional(readOnly = true)
	public FundamentoExencion620VO getlistaFundamentoExencion620(String fundamentoExencion) {
		return fundamentoExencion620Dao.getFundamentoExencion620VO(fundamentoExencion);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FundamentoExencion620VO> getlistaFundamentoExencion620() {
		return fundamentoExencion620Dao.getlistaFundamentoExencion620();
	}

	@Override
	@Transactional(readOnly = true)
	public List<FundamentoExencion620VO> getlistaFundamentoNoSujeto620() {
		return fundamentoExencion620Dao.getlistaFundamentoNoSujeto620();
	}

	@Override
	@Transactional(readOnly = true)
	public FundamentoExencion620VO getlistaFundamentoNoSujeto620(String fundamentoNoSujeccion620) {
		return fundamentoExencion620Dao.getFundamentoNoSujeto620VO(fundamentoNoSujeccion620);
	}

}