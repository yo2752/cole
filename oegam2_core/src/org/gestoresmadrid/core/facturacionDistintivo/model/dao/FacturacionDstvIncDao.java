package org.gestoresmadrid.core.facturacionDistintivo.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.facturacionDistintivo.model.vo.FacturacionDstvIncidenciaVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface FacturacionDstvIncDao extends Serializable, GenericDao<FacturacionDstvIncidenciaVO> {

	List<FacturacionDstvIncidenciaVO> getListaIncidenciasFact(Long idDistintivoFacturado);

}