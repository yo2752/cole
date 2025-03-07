package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.LibroRegistroVO;




public interface LibroRegistroDao extends GenericDao<LibroRegistroVO>, Serializable {

	public LibroRegistroVO getLibroRegistro(String id);

	public List<LibroRegistroVO> getLibrosRegistro(BigDecimal idTramiteRegistro);

}
