package org.gestoresmadrid.oegamCreditos.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.CreditoVO;
import org.gestoresmadrid.core.general.model.vo.HistoricoCreditoVO;
import org.gestoresmadrid.core.general.model.vo.TipoCreditoTramiteVO;
import org.gestoresmadrid.core.general.model.vo.TipoCreditoVO;
import org.gestoresmadrid.core.general.model.vo.TipoTramiteVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.historicocreditos.model.vo.CreditoFacturadoVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.transform.ResultTransformer;

import utilidades.estructuras.FechaFraccionada;

public interface ServicioPersistenciaCreditos extends Serializable {

	// Métodos ServicioCreditosFacturados

	public CreditoFacturadoVO getCreditoFacturadoVO(Long id, String... initialized);

	public List<CreditoFacturadoVO> getListaExportarHistroricoFacturado(ConceptoCreditoFacturado conceptoCreditoFacturado, Long idContrato, String tipoCredito, String tramite,
			FechaFraccionada fechaCreditos);

	// Métodos ServicioHistoricoCreditos

	public List<BigDecimal[]> cantidadesTotalesHistorico(Long idContrato, String tipoCredito, Date fechaDesde, Date fechaHasta) throws Exception;

	public List<HistoricoCreditoVO> consultaCreditosAcumuladosMesPorColegiado(Long idContrato);

	// Métodos ServicioResumenCargaCreditos
	public List<BigDecimal[]> cantidadesTotalesResumen(Long idContrato, String tipoCredito, Date fechaDesde, Date fechaHasta) throws Exception;

	public int numeroElementosResumenCargaCreditos(Long idContrato, String tipoCredito, Date fechaDesde, Date fechaHasta) throws Exception;

	@SuppressWarnings("rawtypes")
	public List buscarPorCriteria(List<Criterion> criterion, List<AliasQueryBean> alias, ProjectionList listaProyecciones, ResultTransformer transformadorResultados);

	public TipoCreditoVO getTipoCredito(String tipoCredito);

	public List<Object[]> getListaExportarTablaResumenCargaCreditos(Long idContrato, String tipoCredito, Date fechaDesde, Date fechaHasta) throws Exception;

	public List<CreditoVO> consultaCreditosDisponiblesPorColegiado(Long idContrato);

	public List<Object[]> consultaCreditosBloqueadosPorContrato(BigDecimal idContrato);

	// Métodos ServicioTipoCreditos

	public List<TipoCreditoVO> getTipoCreditos();

	public List<TipoCreditoVO> getTipoCreditosIncrementales();

	public TipoCreditoVO guardarTipoCredito(TipoCreditoVO tipoCreditoVO);

	public List<TipoTramiteVO> getTiposTramitesCreditos();

	public TipoTramiteVO getTipoTramite(String tipoTramite);

	public TipoCreditoTramiteVO guardarTipoCreditoTramite(TipoCreditoTramiteVO tipoCreditoTramiteVO);

	public List<TipoCreditoTramiteVO> getListaTiposCreditosTramite(String tipoCredito);

	public void borrarListaTipoCreditoTramite(List<TipoCreditoTramiteVO> listTipoCreditoTramite);

	public TipoCreditoTramiteVO getTipoCreditoTramite(String tipoTramite);

	public List<CreditoFacturadoVO> getListaCreditoFacturadoPorTramite(String tipoTramite);

	public void borrarTipoCreditoTramite(TipoCreditoTramiteVO tipoCreditoTramite);

}
