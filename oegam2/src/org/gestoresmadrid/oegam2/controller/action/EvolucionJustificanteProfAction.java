package org.gestoresmadrid.oegam2.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.evolucionJustifProf.views.beans.EvolucionJustifProfBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import trafico.servicio.interfaz.ServicioJustificanteProInt;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class EvolucionJustificanteProfAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private ModelPagination modeloEvolucionJustifProfPaginated;

	@Autowired
	private ServicioJustificanteProInt servicioJustificanteProImpl;

	@Autowired
	UtilesColegiado utilesColegiado;

	private static final ILoggerOegam log = LoggerOegam.getLogger(EvolucionJustificanteProfAction.class);

	private EvolucionJustifProfBean evolucionJustifProfBean;

	// Parámetros de las peticiones del displayTag
	private static final String COLUMDEFECT = "id.fechaCambio";

	private String numExpediente;
	private String idJustificante;
	private String fechaInicio;

	public String cargarListaEvolucionJustificante() {
		cargarFiltroInicial();
		return buscar();
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvolucionJustifProfPaginated;
	}

	@Override
	public String getColumnaPorDefecto() {
		return COLUMDEFECT;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (!utilesColegiado.tienePermisoAdmin()) {
			evolucionJustifProfBean.setIdJustificanteInterno(!idJustificante.isEmpty()
					? servicioJustificanteProImpl.getObtenerIdJustifcanteInternoPorIdJustificante(idJustificante)
					: servicioJustificanteProImpl.getObtenerIdJustificanteInternoPorNumExp(numExpediente, fechaInicio));
			evolucionJustifProfBean.setNumExpediente(Long.parseLong(numExpediente));
		}
	}

	@Override
	protected Object getBeanCriterios() {
		return evolucionJustifProfBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.evolucionJustifProfBean = (EvolucionJustifProfBean) object;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getIdJustificante() {
		return idJustificante;
	}

	public void setIdJustificante(String idJustificante) {
		this.idJustificante = idJustificante;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	@Override
	protected void cargarFiltroInicial() {
		evolucionJustifProfBean = new EvolucionJustifProfBean();
		evolucionJustifProfBean.setIdJustificanteInterno(!idJustificante.isEmpty()
				? servicioJustificanteProImpl.getObtenerIdJustifcanteInternoPorIdJustificante(idJustificante)
				: servicioJustificanteProImpl.getObtenerIdJustificanteInternoPorNumExp(numExpediente, fechaInicio));
		evolucionJustifProfBean.setNumExpediente(Long.parseLong(numExpediente));
	}

	@Override
	public String getDecorator(){
		return  "org.gestoresmadrid.oegam2.view.decorator.DecoratorEvolucionJustifProf";
	}

}