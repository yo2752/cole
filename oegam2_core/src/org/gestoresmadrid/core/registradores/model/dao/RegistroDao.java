package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.RegistroVO;

public interface RegistroDao extends GenericDao<RegistroVO>, Serializable {

	RegistroVO getRegistroPorId(long idRegistro);

	List<RegistroVO> getRegistro(String idProvincia, String tipo);
	
	Long getIdRegistro(String idRegistro, String idProvincia, String tipo);

	RegistroVO getRegistroPorNombre(String nombre, String tipo);
}
