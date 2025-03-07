package org.gestoresmadrid.oegam2.datosBancariosFavoritos.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.bean.DatosBancariosFilterBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaDatosBancariosAction extends AbstractPaginatedListAction{

	private static final long serialVersionUID = 1754388563255985795L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaDatosBancariosAction.class);
	
	private DatosBancariosFilterBean datosBancarios;

	private static final String[] fetchList = {"contrato","contrato.colegiado"};
	
	@Autowired
	UtilesFecha utilesFecha;

	@Resource
	private ModelPagination modeloDatosBancariosPaginatedImpl;
	
	@Autowired
	UtilesColegiado utilesColegiado;

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloDatosBancariosPaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void cargarFiltroInicial() {
		if(datosBancarios == null){
			datosBancarios = new DatosBancariosFilterBean();
		}
		datosBancarios.setFechaAlta(utilesFecha.getFechaFracionadaActual());
		if(!utilesColegiado.tienePermisoAdmin()){
			datosBancarios.setIdContrato(utilesColegiado.getIdContratoSession());
		}
	}

	@Override
	protected Object getBeanCriterios() {
		return datosBancarios;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		datosBancarios = (DatosBancariosFilterBean) object;
	}
	
	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	public DatosBancariosFilterBean getDatosBancarios() {
		return datosBancarios;
	}

	public void setDatosBancarios(DatosBancariosFilterBean datosBancarios) {
		this.datosBancarios = datosBancarios;
	}

	public ModelPagination getModeloDatosBancariosPaginatedImpl() {
		return modeloDatosBancariosPaginatedImpl;
	}

	public void setModeloDatosBancariosPaginatedImpl(
			ModelPagination modeloDatosBancariosPaginatedImpl) {
		this.modeloDatosBancariosPaginatedImpl = modeloDatosBancariosPaginatedImpl;
	}

}
