package org.gestoresmadrid.oegam2.registradores.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoAcuerdo;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.registradores.view.beans.ConsultaSociedadCargoBean;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegistroDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.estructuras.Fecha;
import utilidades.estructuras.FechaFraccionada;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class CargoAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -1070266051275019050L;

	private static final ILoggerOegam log = LoggerOegam.getLogger("Registradores");

	@Resource
	private ModelPagination modeloSociedadCargoPaginated;

	private ConsultaSociedadCargoBean consultaSociedadCargoBean;

	private String numColegiado;
	private String tipoAcuerdo;

	private String rowidCargo;

	private String nifSeleccion;
	private String codigoCargoSeleccion;

	private TramiteRegistroDto tramiteRegistro;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargarFiltroInicial() {}

	public String inicio() {
		consultaSociedadCargoBean = new ConsultaSociedadCargoBean();
		consultaSociedadCargoBean.setCifSociedad(getTramiteRegistro().getSociedad().getNif());
		if (TipoAcuerdo.Nombramiento.getValorEnum().equalsIgnoreCase(tipoAcuerdo)) {
			consultaSociedadCargoBean.setNifCargo(getTramiteRegistro().getAcuerdoNombramiento().getSociedadCargo().getPersonaCargo().getNif());
			consultaSociedadCargoBean.setCodigoCargo(getTramiteRegistro().getAcuerdoNombramiento().getSociedadCargo().getCodigoCargo());
			consultaSociedadCargoBean.setNombre(getTramiteRegistro().getAcuerdoNombramiento().getSociedadCargo().getPersonaCargo().getNombre());
			consultaSociedadCargoBean.setApellido1(getTramiteRegistro().getAcuerdoNombramiento().getSociedadCargo().getPersonaCargo().getApellido1RazonSocial());
			consultaSociedadCargoBean.setApellido2(getTramiteRegistro().getAcuerdoNombramiento().getSociedadCargo().getPersonaCargo().getApellido2());
			consultaSociedadCargoBean.setCorreoElectronico(getTramiteRegistro().getAcuerdoNombramiento().getSociedadCargo().getPersonaCargo().getCorreoElectronico());

		} else if (TipoAcuerdo.Cese.getValorEnum().equalsIgnoreCase(tipoAcuerdo)) {
			consultaSociedadCargoBean.setNifCargo(getTramiteRegistro().getAcuerdoCese().getSociedadCargo().getPersonaCargo().getNif());
			consultaSociedadCargoBean.setCodigoCargo(getTramiteRegistro().getAcuerdoCese().getSociedadCargo().getCodigoCargo());
			consultaSociedadCargoBean.setNombre(getTramiteRegistro().getAcuerdoCese().getSociedadCargo().getPersonaCargo().getNombre());
			consultaSociedadCargoBean.setApellido1(getTramiteRegistro().getAcuerdoCese().getSociedadCargo().getPersonaCargo().getApellido1RazonSocial());
			consultaSociedadCargoBean.setApellido2(getTramiteRegistro().getAcuerdoCese().getSociedadCargo().getPersonaCargo().getApellido2());
			consultaSociedadCargoBean.setCorreoElectronico(getTramiteRegistro().getAcuerdoCese().getSociedadCargo().getPersonaCargo().getCorreoElectronico());
		}

		FechaFraccionada fechaInicio = new FechaFraccionada();
		Fecha fechaActual = utilesFecha.getFechaActual();
		fechaInicio.setDiaInicio(fechaActual.getDia());
		fechaInicio.setMesInicio(fechaActual.getMes());
		fechaInicio.setAnioInicio(fechaActual.getAnio());

		FechaFraccionada fechaFin = new FechaFraccionada();
		fechaFin.setDiaFin(fechaActual.getDia());
		fechaFin.setMesFin(fechaActual.getMes());
		fechaFin.setAnioFin(fechaActual.getAnio());

		consultaSociedadCargoBean.setFechaInicio(fechaInicio);
		consultaSociedadCargoBean.setFechaFin(fechaFin);
		return actualizarPaginatedList();
	}

	@Override
	protected void cargaRestricciones() {
		if (consultaSociedadCargoBean == null) {
			consultaSociedadCargoBean = new ConsultaSociedadCargoBean();
		}
		consultaSociedadCargoBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		if (null != getTramiteRegistro() && null != getTramiteRegistro().getSociedad()) {
			consultaSociedadCargoBean.setCifSociedad(getTramiteRegistro().getSociedad().getNif());
		}

		FechaFraccionada fechaInicio = new FechaFraccionada();
		Fecha fechaActual = utilesFecha.getFechaActual();
		fechaInicio.setDiaInicio(fechaActual.getDia());
		fechaInicio.setMesInicio(fechaActual.getMes());
		fechaInicio.setAnioInicio(fechaActual.getAnio());

		FechaFraccionada fechaFin = new FechaFraccionada();
		fechaFin.setDiaFin(fechaActual.getDia());
		fechaFin.setMesFin(fechaActual.getMes());
		fechaFin.setAnioFin(fechaActual.getAnio());

		consultaSociedadCargoBean.setFechaInicio(fechaInicio);
		consultaSociedadCargoBean.setFechaFin(fechaFin);
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloSociedadCargoPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaSociedadCargoBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaSociedadCargoBean = (ConsultaSociedadCargoBean) object;
	}

	public String getRowidCargo() {
		return rowidCargo;
	}

	public void setRowidCargo(String rowidCargo) {
		this.rowidCargo = rowidCargo;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getTipoAcuerdo() {
		return tipoAcuerdo;
	}

	public void setTipoAcuerdo(String tipoAcuerdo) {
		this.tipoAcuerdo = tipoAcuerdo;
	}

	public String getNifSeleccion() {
		return nifSeleccion;
	}

	public void setNifSeleccion(String nifSeleccion) {
		this.nifSeleccion = nifSeleccion;
	}

	public String getCodigoCargoSeleccion() {
		return codigoCargoSeleccion;
	}

	public void setCodigoCargoSeleccion(String codigoCargoSeleccion) {
		this.codigoCargoSeleccion = codigoCargoSeleccion;
	}

	public TramiteRegistroDto getTramiteRegistro() {
		return tramiteRegistro;
	}

	public void setTramiteRegistro(TramiteRegistroDto tramiteRegistro) {
		this.tramiteRegistro = tramiteRegistro;
	}

	public ConsultaSociedadCargoBean getConsultaSociedadCargoBean() {
		return consultaSociedadCargoBean;
	}

	public void setConsultaSociedadCargoBean(ConsultaSociedadCargoBean consultaSociedadCargoBean) {
		this.consultaSociedadCargoBean = consultaSociedadCargoBean;
	}
}