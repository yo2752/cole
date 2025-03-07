package org.gestoresmadrid.core.trafico.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafSolInfoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafSolInfoVehiculoVO;

public interface TramiteTraficoSolInfoDao extends GenericDao<TramiteTrafSolInfoVO> {

	TramiteTrafSolInfoVO getTramiteTrafSolInfo(BigDecimal numExpediente, boolean tramiteCompleto);

	String getUltimoSolicitante(String matricula, String bastidor, String nive);

	List<TramiteTrafSolInfoVehiculoVO> getSolicitudes(BigDecimal numExpediente);

	TramiteTrafSolInfoVehiculoVO getSolicitudInformacion(BigDecimal numExpediente, long idVehiculo, boolean tramiteCompleto);

	boolean borrarSolInfoVehiculo(TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVO);

	TramiteTrafSolInfoVehiculoVO actualizarSolInfoVehiculo(TramiteTrafSolInfoVehiculoVO trafSolInfoVO);

	Serializable guardarSolInfoVehiculo(TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVehiculoVO);

	TramiteTrafSolInfoVehiculoVO getTramiteTrafSolInfoPorCodTasa(String codigoTasa, Boolean tramiteCompleto);

	TramiteTrafSolInfoVO getTramiteTrafSolInfoNuevo(BigDecimal numExpediente, Boolean tramiteCompleto);

	TramiteTrafSolInfoVehiculoVO getTramiteTrafSolInfoVehiculoPorId(Long idTramiteSolInfo);

	TramiteTrafSolInfoVehiculoVO guardarOActualizarTramSolInfoVehiculo(TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVehiculoVO);

	TramiteTrafSolInfoVehiculoVO getTramiteTrafSolInfoVehiculo(Long idTramiteSolInfo, BigDecimal numExpediente);

}
