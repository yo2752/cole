package org.gestoresmadrid.core.general.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.contrato.model.vo.CorreoContratoTramiteVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface CorreoContratoTramiteDao extends GenericDao<CorreoContratoTramiteVO>, Serializable {

	List<CorreoContratoTramiteVO> getCorreosPorContrato(long idContrato);

	CorreoContratoTramiteVO getCorreoContratoTramite(Long idCorreo);

}
