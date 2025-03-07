package org.gestoresmadrid.oegamComun.trafico.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafDuplicadoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafSolInfoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;

public interface ServicioPersistenciaTramiteTrafico extends Serializable {

	BigDecimal crearTramite(TramiteTraficoVO tramiteTrafico) throws Exception;

	int getNumTramitePorVehiculo(BigDecimal numExpediente, Long idVehiculo);

	TramiteTraficoVO getTramite(BigDecimal numExpediente, Boolean tramiteCompleto);

	void cambiarEstado(BigDecimal numExpediente, BigDecimal estadoNuevo, Long idUsuario);

	TramiteTrafTranVO getTramiteTransmision(BigDecimal numExpediente, Boolean tramiteCompleto);

	TramiteTrafBajaVO getTramiteBaja(BigDecimal numExpediente, Boolean tramiteCompleto);

	TramiteTrafDuplicadoVO getTramiteDuplicado(BigDecimal numExpediente, Boolean tramiteCompleto);

	TramiteTrafMatrVO getTramiteMatriculacion(BigDecimal numExpediente, Boolean tramiteCompleto);

	TramiteTrafSolInfoVO getTramiteSolictud(BigDecimal numExpediente, Boolean tramiteCompleto);

	void guardarOActualizar(TramiteTraficoVO tramite);

	List<Long> getListaIdsContratosFinalizadosTelematicamentePorFecha(Date fechaPresentacion, String[] tiposTramites);

	List<TramiteTraficoVO> getListaTramitesDocBaseNocturno(Long idContrato, Date fechaPresentacion, String tipoTramite);

	void actualizarTramite(TramiteTraficoVO tramiteVO);

	List<TramiteTraficoVO> getListaTramiteTraficoVO(BigDecimal[] numExpedientes, Boolean tramiteCompleto);

	BigDecimal generarNumExpediente(String numColegiado) throws Exception;

	void guardarNRE(BigDecimal numExpediente, String nre, Date fechaRegistro);

	List<Object[]> getTramitePorNRE() throws Exception;

	Integer getNumTramitePorColegiado(String numColegiado);

	ResultadoBean generarSolDocImprNocturno(List<BigDecimal> listaExpedientesImpr, Long docId, String sDocImpr, Long idUsuario, String tipoImpr, Long idContrato, Boolean esEntornoAm);

	void actualizarTramiteSolImpr(BigDecimal numExpediente, Long idDocImpr, String docId, String tipoImpr, Long idUsuario, String operacion, String estadoNuevo);

	void actualizarFechaDiaHoy(BigDecimal numExpediente, Long idUsuario);

	ResultadoBean desasignarTasaTramiteTrafico(BigDecimal numExpediente, Long idUsuario);

}
