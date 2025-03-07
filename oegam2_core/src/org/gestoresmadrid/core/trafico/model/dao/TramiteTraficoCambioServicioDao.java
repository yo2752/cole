package org.gestoresmadrid.core.trafico.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafCambioServicioVO;

public interface TramiteTraficoCambioServicioDao extends GenericDao<TramiteTrafCambioServicioVO>, Serializable {

	TramiteTrafCambioServicioVO getTramiteCambServ(BigDecimal numExpediente, boolean tramiteCompleto);

	BigDecimal generarNumExpediente(String numColegiado) throws Exception;

	List<TramiteTrafCambioServicioVO> cambioServicioExcel(String jefatura) throws Exception;
}
