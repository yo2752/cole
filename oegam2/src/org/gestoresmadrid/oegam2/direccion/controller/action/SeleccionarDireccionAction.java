package org.gestoresmadrid.oegam2.direccion.controller.action;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.personas.model.enumerados.EstadoPersonaDireccion;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.direccion.view.beans.SeleccionarDireccionBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import escrituras.utiles.UtilesVista;

public class SeleccionarDireccionAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -3611740071298626477L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(SeleccionarDireccionAction.class);

	@Resource
	private ModelPagination modeloPersonaDireccionPaginated;

	private SeleccionarDireccionBean seleccionarDireccionBean;

	private String nif;
	private String numColegiado;
	private String pestania;

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {
		if (numColegiado != null) {
			seleccionarDireccionBean.setNumColegiado(numColegiado);
		}
		if (nif != null) {
			seleccionarDireccionBean.setNif(nif);
		}
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloPersonaDireccionPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return seleccionarDireccionBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.seleccionarDireccionBean = (SeleccionarDireccionBean) object;
	}

	@Override
	protected void cargarFiltroInicial() {
		seleccionarDireccionBean = new SeleccionarDireccionBean();
		seleccionarDireccionBean.setNumColegiado(numColegiado);
		seleccionarDireccionBean.setNif(nif);

		ArrayList<Short> estadosDirecciones = new ArrayList<Short>();
		estadosDirecciones.add(EstadoPersonaDireccion.Activo.getValorEnum());
		seleccionarDireccionBean.setEstadosDirecciones(estadosDirecciones);

		setResultadosPorPagina(UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO_DIRECCIONES);
		setSort("id.idDireccion");
		setDir(GenericDaoImplHibernate.ordenDes);
	}

	public SeleccionarDireccionBean getSeleccionarDireccionBean() {
		return seleccionarDireccionBean;
	}

	public void setSeleccionarDireccionBean(SeleccionarDireccionBean seleccionarDireccionBean) {
		this.seleccionarDireccionBean = seleccionarDireccionBean;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getPestania() {
		return pestania;
	}

	public void setPestania(String pestania) {
		this.pestania = pestania;
	}
}