package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.AsistenteVO;

public interface AsistenteDao extends GenericDao<AsistenteVO>, Serializable {

	AsistenteVO getAsistente(BigDecimal idTramiteRegistro, String cifSociedad, String nifCargo, String codigoCargo, Long idReunion);

	List<AsistenteVO> getAsistentes(BigDecimal idTramiteRegistro, String nifCargo);
}
