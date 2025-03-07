package org.gestoresmadrid.oegam2.personas.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.personas.view.beans.ConsultaEvolucionPersonaBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaEvolucionPersonaAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 4328835707970535426L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaEvolucionPersonaAction.class);

	@Resource
	private ModelPagination modeloEvolucionPersonaPaginated;

	private String nif;
	private String numColegiado;

	private ConsultaEvolucionPersonaBean consultaEvolucionPersonaBean;

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {
		if (nif != null) {
			consultaEvolucionPersonaBean.setNif(nif);
		}
		if (numColegiado != null) {
			consultaEvolucionPersonaBean.setNumColegiado(numColegiado);
		}
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvolucionPersonaPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaEvolucionPersonaBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaEvolucionPersonaBean = (ConsultaEvolucionPersonaBean) object;
	}

	@Override
	protected void cargarFiltroInicial() {
		consultaEvolucionPersonaBean = new ConsultaEvolucionPersonaBean();
		consultaEvolucionPersonaBean.setNif(nif);
		consultaEvolucionPersonaBean.setNumColegiado(numColegiado);
		setSort("id.fechaHora");
		setDir(GenericDaoImplHibernate.ordenDes);
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public ConsultaEvolucionPersonaBean getConsultaEvolucionPersonaBean() {
		return consultaEvolucionPersonaBean;
	}

	public void setConsultaEvolucionPersonaBean(ConsultaEvolucionPersonaBean consultaEvolucionPersonaBean) {
		this.consultaEvolucionPersonaBean = consultaEvolucionPersonaBean;
	}
}
