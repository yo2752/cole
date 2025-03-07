package org.gestoresmadrid.core.vehiculo.model.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaDgtVO;

public interface MarcaDgtDao extends GenericDao<MarcaDgtVO>, Serializable {

	List<MarcaDgtVO> listaMarcas(String nombreMarca, Boolean versionMatw);

	MarcaDgtVO getMarcaDgt(String codigoMarca, String marca, Boolean versionMatw);

	MarcaDgtVO getMarcaDgt(String codigoMarca, String marca, ArrayList<Integer> version);

	List<MarcaDgtVO> getMarcaDgt(String marca);

	Long getCodigoFromMarca(String marca);

	MarcaDgtVO getMarcaDgtCodigo(Long codigoMarca);

}
