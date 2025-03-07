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
import org.gestoresmadrid.oegamComun.solicitudNRE06.beans.ResultadoSolicitudNRE06Bean;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;

public interface ServicioComunTramiteTrafico extends Serializable {

	TramiteTraficoVO getTramite(BigDecimal numExpediente, Boolean tramiteCompleto);

	TramiteTrafBajaVO getTramiteBajaVO(BigDecimal numExpediente, Boolean tramiteCompleto);

	TramiteTrafTranVO getTramiteTransmisionVO(BigDecimal numExpediente, Boolean tramiteCompleto);

	TramiteTrafDuplicadoVO getTramiteDuplicadoVO(BigDecimal numExpediente, Boolean tramiteCompleto);

	TramiteTrafMatrVO getTramiteMatriculacionVO(BigDecimal numExpediente, Boolean tramiteCompleto);

	TramiteTrafSolInfoVO getTramiteSolictudVO(BigDecimal numExpediente, Boolean tramiteCompleto);

	void cambiarEstado(BigDecimal numExpediente, BigDecimal estadoNuevo, Long idUsuario);

	void cambiarEstadoBajaDuplRelacionMatriculas(BigDecimal numExpediente, Long idUsuario);

	List<Long> getListaIdsContratosFinalizadosTelematicamenteDocBase(Date fechaPresentacion);

	List<TramiteTraficoVO> getListaTramitesDocBaseNocturno(Long idContrato, Date fechaPresentacion, String tipoTramite);

	void actualizarTramite(TramiteTraficoVO tramiteVO);

	List<TramiteTraficoVO> getListaTramitesTraficoVO(BigDecimal[] numExpedientes, Boolean tramiteCompleto);

	BigDecimal generarNumExpediente(String numColegiado) throws Exception;

	ResultadoBean crearTramite(Long idContrato, Date fechaAlta, String tipoTramite, Long idUsuario, String numColegiado, BigDecimal tipoCreacion);

	int getNumTramitePorVehiculo(BigDecimal numExpediente, Long idVehiculo);

	ResultadoBean custodiar(TramiteTraficoVO tramiteTrafico, String alias);

	ResultadoBean cambiarEstadoRevisado(BigDecimal numExpediente, Long idUsuario);

	ResultadoBean asignarTasaLibre(BigDecimal numExpediente, Long idUsuario);

	void actualizarTasa(BigDecimal numExpediente, String codigoTasa);

	ResultadoSolicitudNRE06Bean guardarNRE(TramiteTraficoVO tramiteTraficoBBDD, String nre, Date fechaRegistro);

	List<Object[]> getTramiteNRE();

	Integer getTotalPorColegiado(String numColegiado);

	ResultadoBean generarDocImprNocturno(List<BigDecimal> listaExpedientesImpr, Long idDocImpr, String sDocImpr, Long idUsuario, String tipoImpr, Boolean esEntornoAm, Long idContrato);

	void actualizarFechaDiaHoy(BigDecimal numExpediente, Long idUsuario);

	ResultadoBean desasignarTasaTramiteTrafico(BigDecimal numExpediente, Long idUsuario);


}