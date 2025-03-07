package org.gestoresmadrid.oegamCreditos.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.cola.model.dao.ColaDao;
import org.gestoresmadrid.core.general.model.dao.CreditoDao;
import org.gestoresmadrid.core.general.model.dao.HistoricoCreditoDao;
import org.gestoresmadrid.core.general.model.dao.ResumenCargaCreditosDao;
import org.gestoresmadrid.core.general.model.dao.TipoCreditoDao;
import org.gestoresmadrid.core.general.model.dao.TipoCreditoTramiteDao;
import org.gestoresmadrid.core.general.model.dao.TipoTramiteDao;
import org.gestoresmadrid.core.general.model.vo.CreditoVO;
import org.gestoresmadrid.core.general.model.vo.HistoricoCreditoVO;
import org.gestoresmadrid.core.general.model.vo.TipoCreditoTramiteVO;
import org.gestoresmadrid.core.general.model.vo.TipoCreditoVO;
import org.gestoresmadrid.core.general.model.vo.TipoTramiteVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.CreditoFacturadoDao;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.historicocreditos.model.vo.CreditoFacturadoVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.oegamCreditos.service.ServicioPersistenciaCreditos;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.estructuras.FechaFraccionada;

@Service
public class ServicioPersistenciaCreditosImpl implements ServicioPersistenciaCreditos {

	private static final long serialVersionUID = 7158449642288689089L;

	@Autowired
	CreditoFacturadoDao creditoFacturadoDao;

	@Autowired
	private HistoricoCreditoDao historicoCreditoDao;

	@Autowired
	private ResumenCargaCreditosDao resumenCargaCreditosDao;

	@Autowired
	private TipoCreditoDao tipoCreditoDao;

	@Autowired
	private TipoTramiteDao tipoTramiteDao;

	@Autowired
	private TipoCreditoTramiteDao tipoCreditoTramiteDao;
	
	@Autowired
	private CreditoDao creditoDao;
	
	@Autowired
	private ColaDao colaDao;

	// Métodos ServicioHistoricoCreditos

	@Override
	@Transactional(readOnly = true)
	public CreditoFacturadoVO getCreditoFacturadoVO(Long id, String... initialized) {
		return creditoFacturadoDao.getCreditoFacturadoVO(id, initialized);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CreditoFacturadoVO> getListaExportarHistroricoFacturado(ConceptoCreditoFacturado conceptoCreditoFacturado, Long idContrato, String tipoCredito, String tramite,
			FechaFraccionada fechaCreditos) {

		return creditoFacturadoDao.getListaExportarHistroricoFacturado(conceptoCreditoFacturado, idContrato, tipoCredito, tramite, fechaCreditos);
	}

	// Métodos ServicioHistoricoCreditos

	@Override
	@Transactional(readOnly = true)
	public List<BigDecimal[]> cantidadesTotalesHistorico(Long idContrato, String tipoCredito, Date fechaDesde, Date fechaHasta) throws Exception {
		return historicoCreditoDao.cantidadesTotales(idContrato, tipoCredito, fechaDesde, fechaHasta);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<HistoricoCreditoVO> consultaCreditosAcumuladosMesPorColegiado(Long idContrato) {
		return historicoCreditoDao.consultaCreditosAcumuladosMesPorColegiado(idContrato);
	}

	// Métodos ServicioResumenCargaCreditos
	@Override
	@Transactional(readOnly = true)
	public List<BigDecimal[]> cantidadesTotalesResumen(Long idContrato, String tipoCredito, Date fechaDesde, Date fechaHasta) throws Exception {
		return resumenCargaCreditosDao.cantidadesTotales(idContrato, tipoCredito, fechaDesde, fechaHasta);
	}

	@Override
	@Transactional(readOnly = true)
	public int numeroElementosResumenCargaCreditos(Long idContrato, String tipoCredito, Date fechaDesde, Date fechaHasta) throws Exception {
		return resumenCargaCreditosDao.numeroElementos(idContrato, tipoCredito, fechaDesde, fechaHasta);
	}

	@Override
	@Transactional(readOnly = true)
	public TipoCreditoVO getTipoCredito(String tipoCredito) {
		return tipoCreditoDao.getTipoCredito(tipoCredito);
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(readOnly = true)
	public List buscarPorCriteria(List<Criterion> criterion, List<AliasQueryBean> alias, ProjectionList listaProyecciones, ResultTransformer transformadorResultados) {
		return resumenCargaCreditosDao.buscarPorCriteria(-1, -1, criterion, null, null, alias, listaProyecciones, transformadorResultados);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getListaExportarTablaResumenCargaCreditos(Long idContrato, String tipoCredito, Date fechaDesde, Date fechaHasta) throws Exception {
		return resumenCargaCreditosDao.getListaExportarTablaResumenCargaCreditos(idContrato, tipoCredito, fechaDesde, fechaHasta);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<CreditoVO> consultaCreditosDisponiblesPorColegiado(Long idContrato) {
		return creditoDao.consultaCreditosDisponiblesPorColegiado(idContrato);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Object[]> consultaCreditosBloqueadosPorContrato(BigDecimal idContrato) {
		return colaDao.consultaCreditosBloqueadosPorContrato(idContrato);
	}

	// Métodos ServicioTipoCreditos

	@Override
	@Transactional(readOnly = true)
	public List<TipoCreditoVO> getTipoCreditos() {
		return tipoCreditoDao.getTiposCreditos();
	}

	@Override
	@Transactional(readOnly = true)
	public List<TipoCreditoVO> getTipoCreditosIncrementales() {
		return tipoCreditoDao.getTiposCreditosIncrementales();
	}

	@Override
	@Transactional
	public TipoCreditoVO guardarTipoCredito(TipoCreditoVO tipoCreditoVO) {
		return tipoCreditoDao.guardarOActualizar(tipoCreditoVO);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TipoTramiteVO> getTiposTramitesCreditos() {
		return tipoTramiteDao.getTiposTramitesCreditos();
	}

	@Override
	@Transactional(readOnly = true)
	public TipoTramiteVO getTipoTramite(String tipoTramite) {
		return tipoTramiteDao.getTipoTramite(tipoTramite);
	}

	@Override
	@Transactional
	public TipoCreditoTramiteVO guardarTipoCreditoTramite(TipoCreditoTramiteVO tipoCreditoTramiteVO) {
		return tipoCreditoTramiteDao.guardarOActualizar(tipoCreditoTramiteVO);
	}

	@Override
	@Transactional
	public void borrarListaTipoCreditoTramite(List<TipoCreditoTramiteVO> listTipoCreditoTramite) {
		if (listTipoCreditoTramite != null && !listTipoCreditoTramite.isEmpty()) {
			for (TipoCreditoTramiteVO tipo : listTipoCreditoTramite) {
				tipoCreditoTramiteDao.borrar(tipo);
			}
		}
	}
	
	@Override
	@Transactional
	public void borrarTipoCreditoTramite(TipoCreditoTramiteVO tipoCreditoTramite) {
		if (tipoCreditoTramite != null) {
			tipoCreditoTramiteDao.borrar(tipoCreditoTramite);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<TipoCreditoTramiteVO> getListaTiposCreditosTramite(String tipoCredito) {
		return tipoCreditoTramiteDao.getListaTiposCreditosTramite(tipoCredito);
	}
	
	@Override
	@Transactional(readOnly = true)
	public TipoCreditoTramiteVO getTipoCreditoTramite(String tipoTramite) {
		return tipoCreditoTramiteDao.getTipoCreditoTramite(tipoTramite);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<CreditoFacturadoVO> getListaCreditoFacturadoPorTramite(String tipoTramite) {
		return creditoFacturadoDao.getListaCreditoFacturadoPorTramite(tipoTramite);
	}

}
