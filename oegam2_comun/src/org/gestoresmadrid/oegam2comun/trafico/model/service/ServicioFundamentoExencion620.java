package org.gestoresmadrid.oegam2comun.trafico.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.modelos.model.vo.FundamentoExencion620VO;

public interface ServicioFundamentoExencion620 extends Serializable {

	public List<FundamentoExencion620VO> getlistaFundamentoExencion620();

	public FundamentoExencion620VO getlistaFundamentoExencion620(String fundamentoExencion);

	public List<FundamentoExencion620VO> getlistaFundamentoNoSujeto620();

	public FundamentoExencion620VO getlistaFundamentoNoSujeto620(String fundamentoNoSujeccion620);

}
