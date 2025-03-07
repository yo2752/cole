package org.gestoresmadrid.core.direccion.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.direccion.model.vo.DgtMunicipioVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface DgtMunicipioDao extends GenericDao<DgtMunicipioVO>, Serializable {

	DgtMunicipioVO getDgtMunicipio(String idProvincia, String municipio);

	DgtMunicipioVO getDgtMunicipioPorCodigoIne(String idProvincia, String codigoIne);

	DgtMunicipioVO getDgtMunicipioPorIdDgt(String idProvincia, BigDecimal idDgtMunicipio);
}
