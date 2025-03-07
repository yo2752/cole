package org.gestoresmadrid.oegamCreditos.component.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.dao.ResumenCargaCreditosDao;
import org.gestoresmadrid.core.general.model.vo.ColegiadoVO;
import org.gestoresmadrid.core.general.model.vo.CreditoVO;
import org.gestoresmadrid.core.general.model.vo.TipoCreditoVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.oegamComun.colegiado.view.dto.ColegiadoDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamCreditos.service.ServicioResumenCargaCreditos;
import org.gestoresmadrid.oegamCreditos.view.bean.ResumenCargaCreditosBean;
import org.gestoresmadrid.oegamCreditos.view.dto.CreditoDto;
import org.gestoresmadrid.oegamCreditos.view.dto.HistoricoCreditoDto;
import org.gestoresmadrid.oegamCreditos.view.dto.TipoCreditoDto;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service(value = "modeloResumenCargaCreditosPaginated")
@Transactional(readOnly = true)
public class ModeloResumenCargaCreditosPaginatedImpl extends AbstractModelPagination {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloResumenCargaCreditosPaginatedImpl.class);

	@Resource
	private ResumenCargaCreditosDao resumenCargaCreditosDao;

	@Autowired
	private ServicioResumenCargaCreditos servicioResumenCargaCreditos;

	@Override
	protected List<AliasQueryBean> crearListaAlias() {
		List<AliasQueryBean> listaAlias = new ArrayList<AliasQueryBean>();
		listaAlias.add(new AliasQueryBean(CreditoVO.class, "credito", "credito", CriteriaSpecification.INNER_JOIN));
		listaAlias.add(new AliasQueryBean(TipoCreditoVO.class, "credito.tipoCreditoVO", "creditoTipoCreditoVO", CriteriaSpecification.INNER_JOIN));
		listaAlias.add(new AliasQueryBean(ContratoVO.class, "contrato", "contrato", CriteriaSpecification.INNER_JOIN));
		listaAlias.add(new AliasQueryBean(ColegiadoVO.class, "contrato.colegiado", "contratoColegiado", CriteriaSpecification.INNER_JOIN));
		return listaAlias;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort, String[] listInitializedOnePath, String[] listInitializedAnyPath) {
		List<?> list;
		List<HistoricoCreditoDto> listaFinal = new ArrayList<HistoricoCreditoDto>();
		List<AliasQueryBean> alias = crearListaAlias();
		ResumenCargaCreditosBean resumenCargaCreditosBean = (ResumenCargaCreditosBean) beanCriterios;
		Date fechaDesde = null;
		Date fechaHasta = null;
		int numElementosTotales = 0;
		if (resumenCargaCreditosBean != null) {

			if (resumenCargaCreditosBean.getFechaAlta() != null) {
				fechaDesde = resumenCargaCreditosBean.getFechaAlta().getFechaInicio();
				fechaHasta = resumenCargaCreditosBean.getFechaAlta().getFechaFin();
			}

			try {
				numElementosTotales = servicioResumenCargaCreditos.numeroElementos(resumenCargaCreditosBean.getIdContrato(), resumenCargaCreditosBean.getTipoCredito(), fechaDesde, fechaHasta);
			} catch (Exception e) {
				log.error("Error al obtener las cantidades totales de los créditos", e);
			}
		}

		if (numElementosTotales > 0) {
			int firstResult = 0;
			int maxResults = resPag;

			firstResult = resPag * (page - 1);
			if (firstResult >= numElementosTotales) {
				if (numElementosTotales % resPag == 0) {
					page = (numElementosTotales / resPag);
				} else {
					page = (numElementosTotales / resPag) + 1;
				}
				firstResult = resPag * (page - 1);
			}

			ProjectionList listaProyecciones = Projections.projectionList();
			listaProyecciones.add(Projections.groupProperty("tipoCredito"));
			listaProyecciones.add(Projections.groupProperty("idContrato"));
			listaProyecciones.add(Projections.alias(Projections.groupProperty("contratoColegiado.numColegiado"), "numColegiado"));
			listaProyecciones.add(Projections.groupProperty("contrato.razonSocial"));
			listaProyecciones.add(Projections.alias(Projections.groupProperty("creditoTipoCreditoVO.descripcion"), "descripcion"));
			listaProyecciones.add(Projections.groupProperty("credito.creditos"));
			listaProyecciones.add(Projections.alias(Projections.sum("cantidadSumada"), "sumada"));
			listaProyecciones.add(Projections.alias(Projections.sum("cantidadRestada"), "restada"));

			BeanResultTransformPaginatedList transformadorResultados = crearTransformer();

			list = getDao().buscarPorCriteria(firstResult, maxResults, dir, sort, beanCriterios, listaProyecciones, transformadorResultados, listInitializedOnePath, listInitializedAnyPath, alias);

			if (list != null && !list.isEmpty()) {
				for (Object[] tramite : (List<Object[]>) list) {

					HistoricoCreditoDto historicoCredito = new HistoricoCreditoDto();
					historicoCredito.setContrato(new ContratoDto());
					historicoCredito.getContrato().setColegiadoDto(new ColegiadoDto());
					historicoCredito.setCredito(new CreditoDto());
					historicoCredito.getCredito().setTipoCreditoDto(new TipoCreditoDto());
					historicoCredito.getCredito().getTipoCreditoDto().setTipoCredito((String) tramite[0]);
					historicoCredito.getCredito().setTipoCredito((String) tramite[0]);
					historicoCredito.setIdContrato((Long) tramite[1]);
					historicoCredito.getContrato().getColegiadoDto().setNumColegiado((String) tramite[2]);
					historicoCredito.getContrato().setRazonSocial((String) tramite[3]);
					historicoCredito.getCredito().getTipoCreditoDto().setDescripcion((String) tramite[4]);
					historicoCredito.getCredito().setCreditos((BigDecimal) tramite[5]);
					historicoCredito.setCantidadSumada((BigDecimal) tramite[6]);
					historicoCredito.setCantidadRestada((BigDecimal) tramite[7]);
					listaFinal.add(historicoCredito);
				}
			}
		} else {
			// Si no hay resultados, lista vacia
			listaFinal = Collections.emptyList();
		}

		return new PaginatedListImpl(resPag, page, dir, sort, numElementosTotales, listaFinal);
	}

	@Override
	protected GenericDao<?> getDao() {
		return resumenCargaCreditosDao;
	}
}
