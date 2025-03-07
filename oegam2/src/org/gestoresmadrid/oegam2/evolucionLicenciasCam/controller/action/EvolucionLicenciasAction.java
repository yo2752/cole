package org.gestoresmadrid.oegam2.evolucionLicenciasCam.controller.action;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ConsultaEvolucionLicenciaBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class EvolucionLicenciasAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 5584734488044960153L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(EvolucionLicenciasAction.class);

	@Resource
	private ModelPagination evolucionLicenciasPaginated;

	private BigDecimal numExpediente;

	private ConsultaEvolucionLicenciaBean consultaEvolucionLicenciaBean;

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {
		if (numExpediente != null) {
			consultaEvolucionLicenciaBean.setNumExpediente(numExpediente);
		}
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return evolucionLicenciasPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaEvolucionLicenciaBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaEvolucionLicenciaBean = (ConsultaEvolucionLicenciaBean) object;
	}

	@Override
	protected void cargarFiltroInicial() {
		consultaEvolucionLicenciaBean = new ConsultaEvolucionLicenciaBean();
		if (numExpediente != null) {
			consultaEvolucionLicenciaBean.setNumExpediente(numExpediente);
		}
		setSort("fechaCambio");
		setDir(GenericDaoImplHibernate.ordenAsc);
	}

	public ModelPagination getEvolucionLicenciasPaginated() {
		return evolucionLicenciasPaginated;
	}

	public void setEvolucionLicenciasPaginated(ModelPagination evolucionLicenciasPaginated) {
		this.evolucionLicenciasPaginated = evolucionLicenciasPaginated;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public ConsultaEvolucionLicenciaBean getConsultaEvolucionLicenciaBean() {
		return consultaEvolucionLicenciaBean;
	}

	public void setConsultaEvolucionLicenciaBean(ConsultaEvolucionLicenciaBean consultaEvolucionLicenciaBean) {
		this.consultaEvolucionLicenciaBean = consultaEvolucionLicenciaBean;
	}

}
