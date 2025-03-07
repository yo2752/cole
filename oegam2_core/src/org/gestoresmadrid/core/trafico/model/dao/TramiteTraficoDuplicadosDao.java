package org.gestoresmadrid.core.trafico.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafDuplicadoVO;

public interface TramiteTraficoDuplicadosDao extends GenericDao<TramiteTrafDuplicadoVO>, Serializable {

	TramiteTrafDuplicadoVO getTramiteDuplicado(BigDecimal numExpediente, boolean tramiteCompleto);
	TramiteTrafDuplicadoVO getTramiteDuplicadoSive(BigDecimal numExpediente, boolean tramiteCompleto);

	List<TramiteTrafDuplicadoVO> duplicadosExcel(String jefatura) throws Exception;

	int getNumElementosMasivos(Long idContrato, Date fecha);

	List<TramiteTrafDuplicadoVO> getTramitesMasivos(Long idContrato, Date fecha);

	List<TramiteTrafDuplicadoVO> getListaTramitesFinalizadosTelematicamentePorContrato(Long idContrato, Date fecha);
	
	List<TramiteTrafDuplicadoVO> listaTramitesFinalizadosTelemPorContrato(Long idContrato, Date fecha, String tipoSolicitud, Boolean esMotos);
}
