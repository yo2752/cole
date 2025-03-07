package org.gestoresmadrid.core.general.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.TipoCreditoTramiteVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface TipoCreditoTramiteDao extends GenericDao<TipoCreditoTramiteVO>, Serializable {

	public TipoCreditoTramiteVO getTipoCreditoTramite(String tipoTramite);

	public List<TipoCreditoTramiteVO> getListaTiposCreditosTramite(String tipoCredito);

	TipoCreditoTramiteVO getTipoCreditoTramitePorTipoCredito(String tipoCredito);
}
