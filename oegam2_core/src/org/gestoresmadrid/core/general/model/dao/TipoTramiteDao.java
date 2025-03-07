package org.gestoresmadrid.core.general.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.TipoTramiteVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface TipoTramiteDao extends GenericDao<TipoTramiteVO>, Serializable {

	TipoTramiteVO getTipoTramite(String tipoTramite);

	List<String> busquedaTiposCreditosPorCodigoAplicacion(List<String> codigosAplicacion);

	List<TipoTramiteVO> getTipoTramitePorAplicacion(String aplicacion);

	List<TipoTramiteVO> getTiposTramitesCreditos();
}
