package org.gestoresmadrid.oegam2.consultaDev.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.evolucionConsultaDev.view.bean.EvolucionConsultaDevFilterBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class EvolucionConsultaDevAction extends AbstractPaginatedListAction{

	private static final long serialVersionUID = 5071456244910141954L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(EvolucionConsultaDevAction.class);
	
	private static final String[] fetchList = {"consultaDev","usuario","consultaDev"};
	
	private Long idConsultaDev;
	
	private EvolucionConsultaDevFilterBean evolucionConsultaDevFilterBean;
	
	@Resource
	ModelPagination modeloEvolucionConsultaDevPaginatedImpl;
	
	public String consultar(){
		cargarFiltroInicial();
		evolucionConsultaDevFilterBean.setIdConsultaDev(idConsultaDev);
		return actualizarPaginatedList();
	}
	
	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvolucionConsultaDevPaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		
	}

	@Override
	protected void cargarFiltroInicial() {
		if(evolucionConsultaDevFilterBean == null){
			evolucionConsultaDevFilterBean = new EvolucionConsultaDevFilterBean();
		}
		setSort("id.fechaCambio");
		setDir("desc");
	}

	@Override
	protected Object getBeanCriterios() {
		return evolucionConsultaDevFilterBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		evolucionConsultaDevFilterBean = (EvolucionConsultaDevFilterBean) object;
	}
	
	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	public EvolucionConsultaDevFilterBean getEvolucionConsultaDevFilterBean() {
		return evolucionConsultaDevFilterBean;
	}

	public void setEvolucionConsultaDevFilterBean(
			EvolucionConsultaDevFilterBean evolucionConsultaDevFilterBean) {
		this.evolucionConsultaDevFilterBean = evolucionConsultaDevFilterBean;
	}

	public Long getIdConsultaDev() {
		return idConsultaDev;
	}

	public void setIdConsultaDev(Long idConsultaDev) {
		this.idConsultaDev = idConsultaDev;
	}

}
