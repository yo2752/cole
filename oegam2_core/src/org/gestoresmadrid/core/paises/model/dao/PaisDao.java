package org.gestoresmadrid.core.paises.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.paises.model.vo.PaisVO;

public interface PaisDao extends GenericDao<PaisVO>, Serializable {

	PaisVO getPais(String siglaPais);

	PaisVO getPaisPorSigla(String sigla);

	// TipoPais: 0-PAISES EXPORTACION 1-PAISES TRANSITO COMUNITARIO CEE
	List<PaisVO> listaPaises(BigDecimal tipoPais);

	List<PaisVO> paises();

	List<PaisVO> paisesPorNombre(String pais);
}
