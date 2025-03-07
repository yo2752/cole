package org.gestoresmadrid.oegam2.material.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.materiales.model.service.ServicioStockMateriales;
import org.gestoresmadrid.oegam2comun.materiales.view.bean.EvolucionStockMaterialBean;
import org.gestoresmadrid.oegam2comun.materiales.view.bean.MaterialStockFilterBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.StockMaterialBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaMaterialStockAction extends AbstractPaginatedListAction{
	
	private static final long serialVersionUID = 1760433257835020624L;
	
	private static final String RECARGAR = "recargar";
	
	private static final String AGREGAR = "agregar";
	
	private static final String VER_EVOLUCION = "verEvolucion";
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaMaterialStockAction.class);
	
	@Resource
	ModelPagination modeloMaterialStockPaginated;
	
	private MaterialStockFilterBean stockMateriales;
	
	private StockMaterialBean stockMaterialBean;
	
	private EvolucionStockMaterialBean evolucionStockMaterialBean;
	
	private String idSeleccionado;
	
	
	@Autowired
	private ServicioStockMateriales servicioStockMateriales;

	@Autowired
	UtilesColegiado utilesColegiado;

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloMaterialStockPaginated;
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
		if(stockMateriales == null){
			stockMateriales = new MaterialStockFilterBean();
		}
	}
	
	public String recargarMaterial() {
		if(idSeleccionado != null && !idSeleccionado.isEmpty()) {
			stockMaterialBean.setId(Long.parseLong(idSeleccionado));
			stockMaterialBean.setIdUsuario(utilesColegiado.getIdUsuarioSession());
			if(servicioStockMateriales.actualizarUnidades(getStockMaterialBean())) {
				return buscar();
			}
		}
		return buscar();
	}
	
	public String agregarMaterial() {
			stockMaterialBean.setIdUsuario(utilesColegiado.getIdUsuarioSession());
			boolean validaMaterial = validarMaterial(stockMaterialBean);
			if(validaMaterial) {
				if(!servicioStockMateriales.crearElemento(stockMaterialBean)) {
					addActionError("El material seleccionado ya existe en esa jefatura, por favor, seleccione uno que no exista.");
					return SUCCESS;
				}
			}
			
		return buscar();
	}
	
	public String eliminar() {
		if(idSeleccionado != null && !idSeleccionado.isEmpty()) {
			servicioStockMateriales.eliminar(Long.parseLong(idSeleccionado));	
		}
		return buscar();
	}
	
	private boolean validarMaterial(StockMaterialBean stockMaterialBean) {
		if(stockMaterialBean.getUnidades().isEmpty() || stockMaterialBean.getUnidades() == null) {
			addActionError("Introduzca número de unidades.");
			return false;
		}
		if(stockMaterialBean.getJefatura().isEmpty() || stockMaterialBean.getJefatura() == null) {
			addActionError("Seleccione Jefatura.");
			return false;
		}
		if(stockMaterialBean.getTipoMaterial().isEmpty() || stockMaterialBean.getTipoMaterial() == null) {
			addActionError("Seleccione Tipo de Material.");
			return false;
		}
		return true;
	}
	


	public String retornarEvolucion() {
		return SUCCESS;
	}
	
	public String cargarPopUpRecargar() {
		return RECARGAR;
	}
	
	public String cargarPopUpAgregar() {
		return AGREGAR;
	}
	
	public String cargarPopUpEvolucion() {
		return VER_EVOLUCION;
	}
	@Override
	protected Object getBeanCriterios() {
		return stockMateriales;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		stockMateriales = (MaterialStockFilterBean) object;
	}

	public MaterialStockFilterBean getStockMateriales() {
		return stockMateriales;
	}

	public void setStockMateriales(MaterialStockFilterBean stockMateriales) {
		this.stockMateriales = stockMateriales;
	}

	public StockMaterialBean getStockMaterialBean() {
		return stockMaterialBean;
	}

	public void setStockMaterialBean(StockMaterialBean stockMaterialBean) {
		this.stockMaterialBean = stockMaterialBean;
	}

	public String getIdSeleccionado() {
		return idSeleccionado;
	}

	public void setIdSeleccionado(String idSeleccionado) {
		this.idSeleccionado = idSeleccionado;
	}

	public EvolucionStockMaterialBean getEvolucionStockMaterialBean() {
		return evolucionStockMaterialBean;
	}

	public void setEvolucionStockMaterialBean(EvolucionStockMaterialBean evolucionStockMaterialBean) {
		this.evolucionStockMaterialBean = evolucionStockMaterialBean;
	}

	@Override
	public String inicio() {
		return SUCCESS;
	}
	

	public ServicioStockMateriales getServicioStockMateriales() {
		return servicioStockMateriales;
	}

	public void setServicioStockMateriales(ServicioStockMateriales servicioStockMateriales) {
		this.servicioStockMateriales = servicioStockMateriales;
	}

}
