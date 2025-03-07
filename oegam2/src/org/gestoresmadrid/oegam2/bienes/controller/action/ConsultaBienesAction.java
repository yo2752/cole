package org.gestoresmadrid.oegam2.bienes.controller.action;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.bien.model.service.ServicioConsultaBienes;
import org.gestoresmadrid.oegam2comun.bien.view.bean.BienFilterBean;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaBienesAction extends AbstractPaginatedListAction{

	private static final long serialVersionUID = 8702679395639205760L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaBienesAction.class);
	
	
	private static final String[] fetchList = {"direccion", "tipoInmueble"};
	
	
	
	private BienFilterBean bienFilter;
	private String codSeleccionados;
	private Boolean esEliminado;
	private String esPopup;
	@Autowired
	private ServicioConsultaBienes servicioConsultaBienes;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Resource
	private ModelPagination modeloBienPaginatedImpl;
	
	public String inicio() {
		// Si se realizo una busqueda previamente, se recupera el filtro empleado
 		Object beanCriterios = getSession().get(getKeyCriteriosSession());
		if (beanCriterios != null) {
			try {
				setBeanCriterios(beanCriterios);
			} catch (ClassCastException e) {
				getLog().error("Error recuperando el filtro del action. La clave \""
								+ getKeyCriteriosSession()
								+ "\" parece que está siendo reutilizada", e);
			}
		} else { //Si no hay filtro cargado, lo inicio.
			cargarFiltroInicial();
		}
		if(esEliminado!= null && esEliminado){
			addActionMessage("El bien se ha eliminado correctamente");
		}
		
		return actualizarPaginatedList();
	}
	
	public String getEsPopup() {
		return esPopup;
	}

	public void setEsPopup(String esPopup) {
		this.esPopup = esPopup;
	}

	
	
	public String eliminar(){
		try {
			ResultBean resultado = servicioConsultaBienes.eliminarBloque(codSeleccionados);
			if(!resultado.getError()){
				addActionMessage("Los bienes se han eliminado correctamente.");
			}else{
				aniadirMensajeError(resultado);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar los bienes, error: ", e);
			addActionError("Ha sucedido un error a la hora de eliminar los bienes.");
		}
		return actualizarPaginatedList();
	}
	
	@Override
	protected String getResultadoPorDefecto() {
		//Utilizamos esta variable para devolver el destino de busqueda
		setEsPopup(ServletActionContext.getRequest().getParameter("esPopup"));
		if(esPopup != null && esPopup.equalsIgnoreCase("S")) {
			esPopup = "popup";
		} else {
			esPopup = SUCCESS;
			
		}
		return esPopup;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloBienPaginatedImpl;
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
		if(bienFilter == null){
			bienFilter = new BienFilterBean();
			bienFilter.setFechaAlta(utilesFecha.getFechaFracionadaActual());
		}
	}


	@Override
	protected Object getBeanCriterios() {
		return bienFilter;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		bienFilter = (BienFilterBean) object;
		setEsPopup(ServletActionContext.getRequest().getParameter("esPopup"));
		if(esPopup != null && esPopup.equalsIgnoreCase("S")) {
			bienFilter.setIdTipoBien("OTRO");
		}
	}
	
	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	public BienFilterBean getBienFilter() {
		return bienFilter;
	}

	public void setBienFilter(BienFilterBean bienFilter) {
		this.bienFilter = bienFilter;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}

	public Boolean getEsEliminado() {
		return esEliminado;
	}

	public void setEsEliminado(Boolean esEliminado) {
		this.esEliminado = esEliminado;
	}

}
