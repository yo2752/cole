package org.gestoresmadrid.core.trafico.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.modelos.model.vo.FundamentoExencion620VO;

public interface FundamentoExencion620Dao extends GenericDao<FundamentoExencion620VO>, Serializable {

	public List<FundamentoExencion620VO> getlistaFundamentoExencion620();

	FundamentoExencion620VO getFundamentoExencion620VO(String fundamentoExencion);

	public List<FundamentoExencion620VO> getlistaFundamentoNoSujeto620();

	public FundamentoExencion620VO getFundamentoNoSujeto620VO(String fundamentoNoSujeccion620);

}
