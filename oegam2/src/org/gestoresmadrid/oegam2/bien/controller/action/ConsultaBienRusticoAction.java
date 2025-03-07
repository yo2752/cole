package org.gestoresmadrid.oegam2.bien.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.bien.view.bean.BienRusticoFilterBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaBienRusticoAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -1680517688253806366L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaBienRusticoAction.class);

	private static final String[] fetchList = {"direccion", "usoRustico", "tipoInmueble", "unidadMetrica"};

	private BienRusticoFilterBean bien;
	private String codigo;
	private boolean esSeleccionadoBien = false;

	@Resource
	private ModelPagination modeloBienRusticoPaginatedImpl;

	public String inicio() {
		cargarFiltroInicial();
		return actualizarPaginatedList();
	}

	public String seleccionar() {
		if (codigo != null && !codigo.isEmpty()) {
			esSeleccionadoBien = true;
		} else {
			buscar();
		}
		return SUCCESS;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloBienRusticoPaginatedImpl;
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
		bien = new BienRusticoFilterBean();
	}

	@Override
	protected Object getBeanCriterios() {
		return bien;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		bien = (BienRusticoFilterBean) object;
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	public BienRusticoFilterBean getBien() {
		return bien;
	}

	public void setBien(BienRusticoFilterBean bien) {
		this.bien = bien;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public boolean isEsSeleccionadoBien() {
		return esSeleccionadoBien;
	}

	public void setEsSeleccionadoBien(boolean esSeleccionadoBien) {
		this.esSeleccionadoBien = esSeleccionadoBien;
	}

}