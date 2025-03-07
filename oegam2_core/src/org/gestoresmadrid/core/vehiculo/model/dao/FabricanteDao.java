package org.gestoresmadrid.core.vehiculo.model.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.vehiculo.model.vo.FabricanteVO;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaDgtVO;

public interface FabricanteDao extends GenericDao<FabricanteVO>, Serializable {

	FabricanteVO getFabricante(String fabricante);

	List<FabricanteVO> getFabricantePorCodMarca(String codMarca);

	List<FabricanteVO> getFabricantesInactivos(ArrayList<Long> arrayInactivos);

	MarcaDgtVO recuperarMarcaConFabricantes(String codMarca);
}