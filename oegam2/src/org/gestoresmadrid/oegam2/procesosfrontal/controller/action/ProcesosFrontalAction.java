package org.gestoresmadrid.oegam2.procesosfrontal.controller.action;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesosFrontalAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 5086238948778688986L;
	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ProcesosFrontalAction.class);

	private static final String LISTADO = "listaProcesosFrontal";
	
	private String usuario;
	private String password;
	private boolean isLogged = false;
	
	@Autowired
	private ServicioProcesos servicioProcesos;
	
	public String inicio(){
		
		return LISTADO;
	}
	
	
	
	public String comprobarPassword(){
		
		setLogged(true);
		return LISTADO;
	}
	
	@Override
	protected String getResultadoPorDefecto() {
		return LISTADO;
	}

	@Override
	protected ModelPagination getModelo() {
		return null;
	}

	@Override
	protected ILoggerOegam getLog() {
		return LOG;
	}

	@Override
	protected void cargaRestricciones() {

		
	}

	@Override
	protected void cargarFiltroInicial() {
	
		
	}

	@Override
	protected Object getBeanCriterios() {
	
		return null;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		
		
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLogged() {
		return isLogged;
	}

	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}



	public ServicioProcesos getServicioProcesos() {
		return servicioProcesos;
	}



	public void setServicioProcesos(ServicioProcesos servicioProcesos) {
		this.servicioProcesos = servicioProcesos;
	}

}
