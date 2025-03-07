package org.gestoresmadrid.oegamImportacion.trafico.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoInteveVO;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoSolInteveVO;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafDuplicadoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoBean;

public interface ServicioTramiteTraficoImportacion extends Serializable {

	Boolean comprobarTasaAsignada(String codigoTasa);

	ResultadoBean crearTramite(String numColegiado, String tipoTramite, Long idContrato, Long idUsuario, Date fechaAlta, JefaturaTraficoVO jefatura, BigDecimal tipoCreacion);

	int getNumTramitePorVehiculo(BigDecimal numExpediente, Long idVehiculo);

	ResultadoBean guardarTramiteBaja(TramiteTrafBajaVO tramiteBajaVO, Long idUsuario);

	ResultadoBean guardarTramiteCtit(TramiteTrafTranVO tramiteMatrVO, Long idUsuario);

	ResultadoBean guardarTramiteMatw(TramiteTrafMatrVO tramiteMatrVO, Long idUsuario);

	ResultadoBean guardarTramiteDuplicado(TramiteTrafDuplicadoVO tramiteDuplicadoVO, Long idUsuario);

	ResultadoBean guardarTramiteInteve(TramiteTraficoInteveVO tramiteSolicitudVO, Long idUsuario);

	boolean validarPosibleDuplicado(String bastidor, String nif, String tipoTramite, String numColegiado);

	boolean validarPosibleDuplicadoCtit(String bastidor, String matricula, String nif, String tipoTramite, String numColegiado, String tipoTransfenrencia);

	void guardarOactualizarTramiteSolInteve(TramiteTraficoSolInteveVO solInteveVo);

	void guardarEvolucionTramite(EvolucionTramiteTraficoVO evolucion);

	void guardarTramiteTraficoConEvolucion(TramiteTraficoVO tramiteTraficoVO, Long idUsuario);

	TramiteTraficoVO getTramite(BigDecimal numExpediente, boolean tramiteCompleto);

	boolean validarPosibleDuplicadoExcel(String matricula, String nif, String tipoTramite, String numColegiado);
}
