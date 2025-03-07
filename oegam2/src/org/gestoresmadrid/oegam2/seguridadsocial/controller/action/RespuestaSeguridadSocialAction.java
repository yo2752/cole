package org.gestoresmadrid.oegam2.seguridadsocial.controller.action;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.notificacion.view.beans.RespuestaSeguridadSocialBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

import com.opensymphony.xwork2.Action;

public class RespuestaSeguridadSocialAction extends AbstractPaginatedListAction implements SessionAware {

	private static final long serialVersionUID = -67420610028315341L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(RespuestaSeguridadSocialAction.class);
	
	@Resource
	private ModelPagination modeloRespuestaSeguridadSocialPaginatedImpl;
	
	private RespuestaSeguridadSocialBean respuestaSeguridadSocialBean;
	
	@Override
	protected String getResultadoPorDefecto() {
		return Action.SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloRespuestaSeguridadSocialPaginatedImpl;
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
		if (respuestaSeguridadSocialBean == null){
			respuestaSeguridadSocialBean = new RespuestaSeguridadSocialBean();
		}
		
	}

	@Override
	protected Object getBeanCriterios() {
		return respuestaSeguridadSocialBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		respuestaSeguridadSocialBean = (RespuestaSeguridadSocialBean) object;
	}
	
	@Override
	public String getDecorator() {
		return "org.gestoresmadrid.oegam2.view.decorator.DecoratorRespuestaSeguridadSocial";
	}

	public ModelPagination getModeloRespuestaSeguridadSocialPaginatedImpl() {
		return modeloRespuestaSeguridadSocialPaginatedImpl;
	}

	public void setModeloRespuestaSeguridadSocialPaginatedImpl(
			ModelPagination modeloRespuestaSeguridadSocialPaginatedImpl) {
		this.modeloRespuestaSeguridadSocialPaginatedImpl = modeloRespuestaSeguridadSocialPaginatedImpl;
	}

	public RespuestaSeguridadSocialBean getRespuestaSeguridadSocialBean() {
		return respuestaSeguridadSocialBean;
	}

	public void setRespuestaSeguridadSocialBean(
			RespuestaSeguridadSocialBean respuestaSeguridadSocialBean) {
		this.respuestaSeguridadSocialBean = respuestaSeguridadSocialBean;
	}

}