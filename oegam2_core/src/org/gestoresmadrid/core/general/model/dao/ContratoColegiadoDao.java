package org.gestoresmadrid.core.general.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.ContratoColegiadoVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ContratoColegiadoDao extends GenericDao<ContratoColegiadoVO>, Serializable {

	ContratoColegiadoVO getContratoColegiado(Long idContrato, String numColegiado);

	List<ContratoColegiadoVO> getContratoColegiado(String numColegiado);

	ContratoColegiadoVO getContratoColegiado(Long idContrato, Long idUsuario);

	ContratoColegiadoVO getColegiadoPorContrato(Long idContrato);
}
