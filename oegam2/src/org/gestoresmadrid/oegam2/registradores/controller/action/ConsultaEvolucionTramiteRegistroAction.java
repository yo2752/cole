package org.gestoresmadrid.oegam2.registradores.controller.action;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.registradores.view.beans.ConsultaEvolucionTramiteRegistroBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaEvolucionTramiteRegistroAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -689631337505258455L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaEvolucionTramiteRegistroAction.class);

	@Resource
	private ModelPagination modeloEvolucionTramiteRegistroPaginated;

	private BigDecimal idTramiteRegistro;

	private ConsultaEvolucionTramiteRegistroBean consultaEvolucionTramiteRegistroBean;

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {
		if (idTramiteRegistro != null) {
			consultaEvolucionTramiteRegistroBean.setIdTramiteRegistro(idTramiteRegistro);
		}
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvolucionTramiteRegistroPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaEvolucionTramiteRegistroBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaEvolucionTramiteRegistroBean = (ConsultaEvolucionTramiteRegistroBean) object;
	}

	@Override
	protected void cargarFiltroInicial() {
		consultaEvolucionTramiteRegistroBean = new ConsultaEvolucionTramiteRegistroBean();
		if (idTramiteRegistro != null) {
			consultaEvolucionTramiteRegistroBean.setIdTramiteRegistro(idTramiteRegistro);
		}
		setSort("id.fechaCambio");
		setDir(GenericDaoImplHibernate.ordenAsc);
	}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}

	public ConsultaEvolucionTramiteRegistroBean getConsultaEvolucionTramiteRegistroBean() {
		return consultaEvolucionTramiteRegistroBean;
	}

	public void setConsultaEvolucionTramiteRegistroBean(ConsultaEvolucionTramiteRegistroBean consultaEvolucionTramiteRegistroBean) {
		this.consultaEvolucionTramiteRegistroBean = consultaEvolucionTramiteRegistroBean;
	}
}
