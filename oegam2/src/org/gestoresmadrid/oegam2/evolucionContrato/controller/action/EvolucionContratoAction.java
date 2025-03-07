package org.gestoresmadrid.oegam2.evolucionContrato.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegamComun.evolucionContrato.view.bean.EvolucionContratoFilterBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class EvolucionContratoAction extends AbstractPaginatedListAction{

	private static final long serialVersionUID = -4228408102204760841L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(EvolucionContratoAction.class);
	
	private static final String[] fetchList = {"contrato","usuario"};
	
	private EvolucionContratoFilterBean evolContratoFilterBean;
	
	private Long idContrato;
	private Long idContratoAnt;
	
	@Resource
	ModelPagination modeloEvolucionContratoPaginatedImpl;
	
	public String consultar(){
		cargarFiltroInicial();
		evolContratoFilterBean.setIdContrato(idContrato);
		return buscar();
	}
	
	public String consultarAnt(){
		idContrato = idContratoAnt;
		cargarFiltroInicial();
		evolContratoFilterBean.setIdContrato(idContrato);
		return buscar();
	}
	
	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvolucionContratoPaginatedImpl;
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
		if(evolContratoFilterBean == null){
			evolContratoFilterBean = new EvolucionContratoFilterBean();
		}
		setSort("id.fechaCambio");
		setDir("desc");
	}

	@Override
	protected Object getBeanCriterios() {
		return evolContratoFilterBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		evolContratoFilterBean = (EvolucionContratoFilterBean) object;		
	}
	
	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public Long getIdContratoAnt() {
		return idContratoAnt;
	}

	public void setIdContratoAnt(Long idContratoAnt) {
		this.idContratoAnt = idContratoAnt;
	}

}
