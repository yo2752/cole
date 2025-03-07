package org.gestoresmadrid.core.tipoInmueble.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.modelos.model.enumerados.TipoBien;
import org.gestoresmadrid.core.tipoInmueble.model.vo.TipoInmuebleVO;

public interface TipoInmuebleDao extends Serializable, GenericDao<TipoInmuebleVO>{

	List<TipoInmuebleVO> getListaTiposInmueblesPorTipo(TipoBien tipoBien);

}
