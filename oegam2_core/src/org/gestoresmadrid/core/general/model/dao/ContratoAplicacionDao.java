package org.gestoresmadrid.core.general.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.ContratoAplicacionVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ContratoAplicacionDao extends GenericDao<ContratoAplicacionVO>, Serializable {

	List<ContratoAplicacionVO> getAplicacionesPorContrato(Long idContrato);

	List<String> getCodigosAplicacionPorContrato(Long idContrato);
}
