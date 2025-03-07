package org.gestoresmadrid.core.direccion.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.LocalidadDgtVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface LocalidadDgtDao extends GenericDao<LocalidadDgtVO>, Serializable {

	List<LocalidadDgtVO> getLocalidades(String localidad, String municipio);

	List<LocalidadDgtVO> getLocalidadesPorCodigoPostal(String codigoPostal, String localidad);

	List<String> listaLocalidades(String codigoIne);
}
