package org.gestoresmadrid.core.direccion.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.MunicipioCamVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface MunicipioCamDao extends GenericDao<MunicipioCamVO>, Serializable {

	List<MunicipioCamVO> getListaMunicipiosPorProvincia(String idProvincia);

	MunicipioCamVO getMunicipio(String idMunicipio, String idProvincia);

	String getMunicipioNombre(String idMunicipio, String idProvincia);

}
