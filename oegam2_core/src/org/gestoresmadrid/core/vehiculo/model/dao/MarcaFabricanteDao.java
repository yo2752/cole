package org.gestoresmadrid.core.vehiculo.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaFabricanteVO;

public interface MarcaFabricanteDao extends GenericDao<MarcaFabricanteVO>, Serializable {

	MarcaFabricanteVO getMarcaFabricante(String codigoMarca, Long codFabricante);

}
