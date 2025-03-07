package org.gestoresmadrid.oegam2.creditos.controller.action;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamCreditos.service.ServicioHistoricoCreditos;
import org.gestoresmadrid.oegamCreditos.view.bean.HistoricoCreditosBean;
import org.gestoresmadrid.oegamCreditos.view.bean.ResultCreditosBean;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class HistoricoCreditosAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 6523986228012683141L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(HistoricoCreditosAction.class);

	private static final String[] fetchList = { "usuario", "contrato", "credito", "credito.tipoCreditoVO" };

	private HistoricoCreditosBean historicoCreditosBean;

	private BigDecimal cantidadSumadaTotal;

	private BigDecimal cantidadRestadaTotal;

	private boolean volverResumenCargaCredito;
	private boolean volverCargaCredito;
	private String idContratoYProvincia;

	@Resource
	private ModelPagination modeloConsultaHistCreditosPaginated;

	@Autowired
	private ServicioHistoricoCreditos servicioHistoricoCreditos;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	@Override
	public String inicio() {
		if (historicoCreditosBean == null) {
			historicoCreditosBean = new HistoricoCreditosBean();
		}
		historicoCreditosBean.setFechaAlta(utilesFecha.getFechaFracionadaActual());
		setSort("fecha");
		setDir(GenericDaoImplHibernate.ordenDes);
		return buscar();
	}

	private void obtenerCantidadesTotales() {
		Date fechaDesde = null;
		Date fechaHasta = null;
		if (historicoCreditosBean != null) {

			if (historicoCreditosBean.getFechaAlta() != null) {
				fechaDesde = historicoCreditosBean.getFechaAlta().getFechaInicio();
				fechaHasta = historicoCreditosBean.getFechaAlta().getFechaFin();
			}
			try {
				ResultCreditosBean result = servicioHistoricoCreditos.cantidadesTotalesHistorico(
						historicoCreditosBean.getIdContrato(), historicoCreditosBean.getTipoCredito(), fechaDesde,
						fechaHasta);
				if (result != null && !result.getError()) {
					cantidadSumadaTotal = (BigDecimal) result.getAttachment(ServicioHistoricoCreditos.CANTIDAD_SUMADA_TOTAL);
					cantidadRestadaTotal = (BigDecimal) result.getAttachment(ServicioHistoricoCreditos.CANTIDAD_RESTADA_TOTAL);
				}

			} catch (Exception e) {
				log.error("Error al obtener las cantidades totales de los créditos", e);
			}
		}
	}

	@Override
	public String buscar() {
		obtenerCantidadesTotales();
		return super.buscar();
	}

	@Override
	public String navegar() {
		super.navegar();
		obtenerCantidadesTotales();
		return SUCCESS;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {
		if (historicoCreditosBean == null) {
			historicoCreditosBean = new HistoricoCreditosBean();
		}

		if (!utilesColegiado.tienePermisoAdmin()) {
			historicoCreditosBean.setIdContrato(utilesColegiado.getIdContratoSession());
		}
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloConsultaHistCreditosPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return historicoCreditosBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.historicoCreditosBean = (HistoricoCreditosBean) object;
	}

	@Override
	protected void cargarFiltroInicial() {}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	public HistoricoCreditosBean getConsultaHistoricoCreditosBean() {
		return historicoCreditosBean;
	}

	public void setConsultaHistoricoCreditosBean(HistoricoCreditosBean consultaHistoricoCreditosBean) {
		this.historicoCreditosBean = consultaHistoricoCreditosBean;
	}

	public BigDecimal getCantidadSumadaTotal() {
		return cantidadSumadaTotal;
	}

	public void setCantidadSumadaTotal(BigDecimal cantidadSumadaTotal) {
		this.cantidadSumadaTotal = cantidadSumadaTotal;
	}

	public BigDecimal getCantidadRestadaTotal() {
		return cantidadRestadaTotal;
	}

	public void setCantidadRestadaTotal(BigDecimal cantidadRestadaTotal) {
		this.cantidadRestadaTotal = cantidadRestadaTotal;
	}

	public ModelPagination getModeloConsultaHistCreditosPaginated() {
		return modeloConsultaHistCreditosPaginated;
	}

	public void setModeloConsultaHistCreditosPaginated(ModelPagination modeloConsultaHistCreditosPaginated) {
		this.modeloConsultaHistCreditosPaginated = modeloConsultaHistCreditosPaginated;
	}

	public ServicioHistoricoCreditos getServicioHistoricoCreditos() {
		return servicioHistoricoCreditos;
	}

	public void setServicioHistoricoCreditos(ServicioHistoricoCreditos servicioHistoricoCreditos) {
		this.servicioHistoricoCreditos = servicioHistoricoCreditos;
	}

	public static String[] getFetchlist() {
		return fetchList;
	}

	public HistoricoCreditosBean getHistoricoCreditosBean() {
		return historicoCreditosBean;
	}

	public void setHistoricoCreditosBean(HistoricoCreditosBean historicoCreditosBean) {
		this.historicoCreditosBean = historicoCreditosBean;
	}

	public boolean isVolverResumenCargaCredito() {
		return volverResumenCargaCredito;
	}

	public void setVolverResumenCargaCredito(boolean volverResumenCargaCredito) {
		this.volverResumenCargaCredito = volverResumenCargaCredito;
	}

	public boolean isVolverCargaCredito() {
		return volverCargaCredito;
	}

	public void setVolverCargaCredito(boolean volverCargaCredito) {
		this.volverCargaCredito = volverCargaCredito;
	}

	public String getIdContratoYProvincia() {
		return idContratoYProvincia;
	}

	public void setIdContratoYProvincia(String idContratoYProvincia) {
		this.idContratoYProvincia = idContratoYProvincia;
	}

}