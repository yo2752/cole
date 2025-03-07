package org.gestoresmadrid.core.trafico.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;

public interface TramiteTraficoBajaDao extends GenericDao<TramiteTrafBajaVO>, Serializable {

	TramiteTrafBajaVO getTramiteBaja(BigDecimal numExpediente, boolean tramiteCompleto);

	List<TramiteTrafBajaVO> bajasExcel(String jefatura) throws Exception;

	List<TramiteTrafBajaVO> getListaTramitesFinalizadosIncidenciasBtv(String jefatura, Date fecha);

	int getNumElementosMasivos(Long idContrato, Date fecha);

	List<TramiteTrafBajaVO> getTramitesMasivos(Long idContrato, Date fecha);
}
