package org.gestoresmadrid.oegam2.facturacion.tasa.controller.action;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ConsultaTramiteTraficoFactBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaFacturacionTasaAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 791319033858823245L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaFacturacionTasaAction.class);

	private static final String ALTA_FACTURACION_TASA = "altaFacturacionTasa";

	private BigDecimal numExpediente;

	@Resource
	private ModelPagination modeloTramiteTraficoFactPaginated;

	private ConsultaTramiteTraficoFactBean consultaTramiteTraficoFactBean;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {
		if (!utilesColegiado.tienePermisoAdmin()) {
			consultaTramiteTraficoFactBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
	}

	public String detalle() {
		return ALTA_FACTURACION_TASA;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloTramiteTraficoFactPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaTramiteTraficoFactBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaTramiteTraficoFactBean = (ConsultaTramiteTraficoFactBean) object;
	}

	@Override
	protected void cargarFiltroInicial() {
		consultaTramiteTraficoFactBean = new ConsultaTramiteTraficoFactBean();
		if (!utilesColegiado.tienePermisoAdmin()) {
			consultaTramiteTraficoFactBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
		consultaTramiteTraficoFactBean.setFechaFiltradoAplicacion(utilesFecha.getFechaFracionadaActual());
		setSort("id.numExpediente");
		setDir(GenericDaoImplHibernate.ordenDes);
	}

	public ConsultaTramiteTraficoFactBean getConsultaTramiteTraficoFactBean() {
		return consultaTramiteTraficoFactBean;
	}

	public void setConsultaTramiteTraficoFactBean(ConsultaTramiteTraficoFactBean consultaTramiteTraficoFactBean) {
		this.consultaTramiteTraficoFactBean = consultaTramiteTraficoFactBean;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}
}