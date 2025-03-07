package org.gestoresmadrid.oegam2.personas.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.personas.view.beans.ConsultaPersonaBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaPersonaAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 2433401131317961110L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaPersonaAction.class);

	@Resource
	private ModelPagination modeloPersonaPaginated;

	@Autowired
	UtilesColegiado utilesColegiado;

	private ConsultaPersonaBean consultaPersonaBean;

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {
		if (consultaPersonaBean != null) {
			if (new Long(-1).equals(consultaPersonaBean.getEstado())) {
				consultaPersonaBean.setEstado(null);
			}
			if (consultaPersonaBean.getNif() != null) {
				consultaPersonaBean.setNif(consultaPersonaBean.getNif().toUpperCase());
			}
			if (consultaPersonaBean.getApellido1RazonSocial() != null) {
				consultaPersonaBean.setApellido1RazonSocial(consultaPersonaBean.getApellido1RazonSocial().toUpperCase());
			}
			if (consultaPersonaBean.getApellido2() != null) {
				consultaPersonaBean.setApellido2(consultaPersonaBean.getApellido2().toUpperCase());
			}
			if (consultaPersonaBean.getNombre() != null) {
				consultaPersonaBean.setNombre(consultaPersonaBean.getNombre().toUpperCase());
			}
			if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()  && !utilesColegiado.tienePermisoEspecial()) {
				consultaPersonaBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			}
		}
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloPersonaPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaPersonaBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaPersonaBean = (ConsultaPersonaBean) object;
	}

	@Override
	protected void cargarFiltroInicial() {
		if(consultaPersonaBean == null){
			consultaPersonaBean = new ConsultaPersonaBean();
		}
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()  && !utilesColegiado.tienePermisoEspecial()) {
			consultaPersonaBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
	}
	
	@Override
	public boolean isBuscarInicio() {
		return false;
	}

	public ConsultaPersonaBean getConsultaPersonaBean() {
		return consultaPersonaBean;
	}

	public void setConsultaPersonaBean(ConsultaPersonaBean consultaPersonaBean) {
		this.consultaPersonaBean = consultaPersonaBean;
	}
}