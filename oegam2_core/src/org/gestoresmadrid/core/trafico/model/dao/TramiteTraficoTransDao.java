package org.gestoresmadrid.core.trafico.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.hibernate.criterion.ProjectionList;

public interface TramiteTraficoTransDao extends GenericDao<TramiteTrafTranVO>, Serializable {

	TramiteTrafTranVO getTramiteTransmision(BigDecimal numExpediente, boolean tramiteCompleto);

	TramiteTrafTranVO datosCTIT(BigDecimal numExpediente);

	boolean sitexComprobarCTITPrevio(String matricula);

	String recuperarMismoTipoCreditoTramiteTransmision(BigDecimal[] numExpedientes, TipoTramiteTrafico tipo);

	TramiteTrafTranVO getTramitePorTasaCamSer(String codigoTasa, Boolean tramiteCompleto);

	List<TramiteTraficoVO> getListaExpedientesPorTasaCambServOInforme(String codigoTasa);

	Integer getTramitesFinalizadosTelematicamentePorContrato(Long idContrato, Date fechaPresentacion);

	List<TramiteTraficoVO> getListaTramitesImprimirImprPorContratoYFecha(Long idContrato, Date fechaPresentacion);

	TramiteTrafTranVO getTramiteAutoliquidarCet(String matricula, Long idContrato);

	Integer getPosibleDuplicado(String bastidor, String matricula, String nif, String tipoTramite, String numColegiado, String tipoTransferencia);

	// Estadísticas CTIT

	List<Object[]> obtenerTotalesTramitesCTIT(boolean esTelematico, Date fechaDesde, Date fechaHasta, String idProvincia);

	List<Object[]> obtenerDetalleTramitesCTIT(boolean esTelematico, Date fechaDesde, Date fechaHasta, String idProvincia);

	List<BigDecimal> obtenerListadoNumerosExpedienteCTIT(boolean esTelematico, Date fechaDesde, Date fechaHasta);

	List<BigDecimal> obtenerListadoNumerosExpedienteCTITAnotacionesGest(boolean esTelematico, Date fechaDesde, Date fechaHasta, List<BigDecimal> listadoPosibles);

	List<Object[]> obtenerDetalleTramitesCTITCarpetaFusion(boolean esTelematico, Date fechaDesde, Date fechaHasta, List<BigDecimal> listadoPosibles);

	List<BigDecimal> obtenerListaExpedientesCTITCarpetaFusion(boolean esTelematico, Date fechaDesde, Date fechaHasta, List<BigDecimal> listadoPosibles);

	List<Object[]> obtenerDetalleTramitesCTITCarpetaI(boolean esTelematico, Date fechaDesde, Date fechaHasta, List<BigDecimal> listadoPosibles);

	List<BigDecimal> obtenerListaExpedientesCTITCarpetaI(boolean esTelematico, Date fechaDesde, Date fechaHasta, List<BigDecimal> listadoPosibles);

	List<BigDecimal> obtenerListaExpedientesCTITCarpetaB(boolean esTelematico, Date fechaDesde, Date fechaHasta, List<BigDecimal> listadoPosibles);

	List<Object[]> obtenerDetalleTramitesCTITVehiculosAgricolas(boolean esTelematico, Date fechaDesde, Date fechaHasta, List<BigDecimal> listadoPosibles);

	List<BigDecimal> obtenerListaExpedientesCTITVehiculosAgricolas(boolean esTelematico, Date fechaDesde, Date fechaHasta, List<BigDecimal> listadoPosibles);

	List<BigDecimal> obtenerDetalleTramitesCTITJudicialSubasta(boolean esTelematico, Date fechaDesde, Date fechaHasta, List<BigDecimal> listadoPosibles);

	List<BigDecimal> obtenerListaExpedientesCTITSinCETNiFactura(boolean esTelematico, Date fechaDesde, Date fechaHasta, List<BigDecimal> listadoPosibles);

	List<BigDecimal> obtenerListaExpedientesCTITEmbargoPrecinto(boolean esTelematico, Date fechaDesde, Date fechaHasta, List<BigDecimal> listadoPosibles);

	List<Object[]> obtenerDetalleTramitesCTITErrorJefatura(Date fechaDesde, Date fechaHasta, List<BigDecimal> listadoPosibles);

	List<BigDecimal> obtenerListaExpedientesCTITErrorJefatura(Date fechaInicio, Date fechaFin, List<BigDecimal> listadoPosibles);

	List<BigDecimal> obtenerListaExpedientesCTITEvolucion(boolean esTelematico, Date fechaDesde, Date fechaHasta, List<BigDecimal> listadoPosibles, List<EstadoTramiteTrafico> transicionEstados);

	List<Object[]> obtenerListaTramitesGestorSobreExp(List<BigDecimal> listadoPosibles);

	List<TramiteTrafTranVO> getListaTramitesFinalizadosTelematicamentePorContrato(Long idContrato, Date fecha);

	int getNumElementosMasivos(Long idContrato, Date fechaInicio);

	List<TramiteTrafTranVO> getTramitesMasivos(Long idContrato, Date fecha);

	List<TramiteTrafTranVO> listadoPantallasEstadisticas(Object filter, String[] fetchModeJoinList, List<AliasQueryBean> entitiesJoin, ProjectionList listaProyecciones);

}