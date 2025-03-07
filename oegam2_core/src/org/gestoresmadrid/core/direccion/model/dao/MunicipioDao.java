package org.gestoresmadrid.core.direccion.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.MunicipioVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface MunicipioDao extends GenericDao<MunicipioVO>, Serializable {

	MunicipioVO getMunicipio(String idMunicipio, String idProvincia);

	List<MunicipioVO> listaMunicipios(String idProvincia);

	List<MunicipioVO> listaOficinasLiquidadoras(String idMunicipio);

	MunicipioVO getMunicipioPorNombre(String nombreMunicipio, String idProvincia);
}
