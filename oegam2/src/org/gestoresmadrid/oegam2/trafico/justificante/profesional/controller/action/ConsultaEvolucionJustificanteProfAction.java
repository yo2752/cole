package org.gestoresmadrid.oegam2.trafico.justificante.profesional.controller.action;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.beans.ConsultaEvolucionJustifProfesionalesBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaEvolucionJustificanteProfAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -3919216178502494420L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaEvolucionJustificanteProfAction.class);

	@Resource
	private ModelPagination modeloEvolucionJustifProfesionalesPaginated;

	private BigDecimal numExpediente;
	private Long idJustificanteInterno;

	private ConsultaEvolucionJustifProfesionalesBean consultaEvolucionJustifProfesionalesBean;

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {
		if (consultaEvolucionJustifProfesionalesBean == null) {
			consultaEvolucionJustifProfesionalesBean = new ConsultaEvolucionJustifProfesionalesBean();
		}
		
		if (numExpediente != null) {
			consultaEvolucionJustifProfesionalesBean.setNumExpediente(numExpediente);
		}
		if (idJustificanteInterno != null) {
			consultaEvolucionJustifProfesionalesBean.setIdJustificanteInterno(idJustificanteInterno);
		}
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvolucionJustifProfesionalesPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaEvolucionJustifProfesionalesBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaEvolucionJustifProfesionalesBean = (ConsultaEvolucionJustifProfesionalesBean) object;
	}

	@Override
	protected void cargarFiltroInicial() {
		consultaEvolucionJustifProfesionalesBean = new ConsultaEvolucionJustifProfesionalesBean();
		if (numExpediente != null) {
			consultaEvolucionJustifProfesionalesBean.setNumExpediente(numExpediente);
		}
		if (idJustificanteInterno != null) {
			consultaEvolucionJustifProfesionalesBean.setIdJustificanteInterno(idJustificanteInterno);
		}
		setSort("id.fechaCambio");
		setDir(GenericDaoImplHibernate.ordenDes);
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public Long getIdJustificanteInterno() {
		return idJustificanteInterno;
	}

	public void setIdJustificanteInterno(Long idJustificanteInterno) {
		this.idJustificanteInterno = idJustificanteInterno;
	}

	public ConsultaEvolucionJustifProfesionalesBean getConsultaEvolucionJustifProfesionalesBean() {
		return consultaEvolucionJustifProfesionalesBean;
	}

	public void setConsultaEvolucionJustifProfesionalesBean(ConsultaEvolucionJustifProfesionalesBean consultaEvolucionJustifProfesionalesBean) {
		this.consultaEvolucionJustifProfesionalesBean = consultaEvolucionJustifProfesionalesBean;
	}
}
