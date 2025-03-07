package org.gestoresmadrid.core.accionTramite.model.dao;

import java.util.List;

import org.gestoresmadrid.core.accionTramite.model.vo.AccionTramiteVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface AccionTramiteDao extends GenericDao<AccionTramiteVO>{

	List<AccionTramiteVO> existeAccionTramiteSinFechaFin(AccionTramiteVO accionTramiteVO);

	AccionTramiteVO getAccionPorNumExpedienteYAccion(Long numExpediente, String accion);

}
