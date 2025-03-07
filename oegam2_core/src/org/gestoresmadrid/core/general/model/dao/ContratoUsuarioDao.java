package org.gestoresmadrid.core.general.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.ContratoUsuarioVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ContratoUsuarioDao extends GenericDao<ContratoUsuarioVO>, Serializable {

	ContratoUsuarioVO getContratoUsuario(String idUsuario,Long idContrato);
	List<ContratoUsuarioVO> getContratosPorUsuario(BigDecimal idUsuario);
	List<ContratoUsuarioVO> getContratosAnterioresPorUsuario(BigDecimal idUsuario); 
	List<ContratoUsuarioVO> getUsuariosPorContrato(Long idContrato);
}