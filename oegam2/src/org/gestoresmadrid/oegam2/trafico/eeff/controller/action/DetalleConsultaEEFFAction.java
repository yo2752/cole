package org.gestoresmadrid.oegam2.trafico.eeff.controller.action;

import org.apache.struts2.interceptor.SessionAware;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioEEFF;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.beans.DetalleConsultaEEFFBean;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class DetalleConsultaEEFFAction extends AbstractPaginatedListAction implements SessionAware {
	private static final long serialVersionUID = 1L;

	private DetalleConsultaEEFFBean detalleConsultaEEFFBean;
	
	private String numExpediente;
	
	@Autowired
	private ServicioEEFF servicioEEFF;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaEEFFAction.class);
	
	public String obtener(){
		log.error("Obteniendo el detalle de la consulta");
		try {
			detalleConsultaEEFFBean = servicioEEFF.getDetalleConsultaEEFF(numExpediente);
			if(detalleConsultaEEFFBean == null){
				addActionError("Error al obtener el Detalle de la Respuesta.");
			}
		}catch (Exception e) {
			log.error("Error al obtener el detalle de la respuesta de la consulta EEFF: " + e);
			addActionError("Error al obtener el detalle de la respuesta de la consulta EEFF.");
		}
		return SUCCESS;
	}
	
	@Override
	protected String getResultadoPorDefecto() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ModelPagination getModelo() {
		// TODO Auto-generated method stub
		return null;
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
		detalleConsultaEEFFBean = new DetalleConsultaEEFFBean();
	}

	@Override
	protected Object getBeanCriterios() {
		return detalleConsultaEEFFBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.detalleConsultaEEFFBean = (DetalleConsultaEEFFBean) object;		
	}

	public ServicioEEFF getServicioEEFF() {
		return servicioEEFF;
	}

	public void setServicioEEFF(ServicioEEFF servicioEEFF) {
		this.servicioEEFF = servicioEEFF;
	}
	
	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public DetalleConsultaEEFFBean getDetalleConsultaEEFFBean() {
		return detalleConsultaEEFFBean;
	}

	public void setDetalleConsultaEEFFBean(DetalleConsultaEEFFBean detalleConsultaEEFFBean) {
		this.detalleConsultaEEFFBean = detalleConsultaEEFFBean;
	}
}
