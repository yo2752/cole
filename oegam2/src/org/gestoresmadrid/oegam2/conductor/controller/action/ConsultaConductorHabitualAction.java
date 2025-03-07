package org.gestoresmadrid.oegam2.conductor.controller.action;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.conductor.model.service.ServicioConsultaConductor;
import org.gestoresmadrid.oegam2comun.conductor.view.beans.ConsultaConductorBean;
import org.gestoresmadrid.oegam2comun.conductor.view.beans.ConsultaConductorFilter;
import org.gestoresmadrid.oegam2comun.conductor.view.beans.ResultConsultaConductorBean;
import org.gestoresmadrid.oegam2comun.conductor.view.beans.ResumenConductoresBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaConductorHabitualAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 2433401131317961110L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaConductorHabitualAction.class);

	private static final String CONSULTA_CONDUCTOR = "inicioConsultaConductorHabitual";

	private static final String LISTADO_CONDUCTOR = "inicioConsultaConductorHabitual";

	private static final String POP_UP_ESTADOS = "popPupCambioEstado";

	private static final String POP_UP_MODIFICAR_FECHAS = "popPupModificarFechas";

	private String estadoNuevo;

	private Fecha fechaIniModificacion;
	private Fecha fechaFinModificacion;

	@Resource
	private ModelPagination modeloConductorPaginationImpl;

	private ConsultaConductorFilter consultaConductorFilter;

	private ConsultaConductorBean consultaConductorBean;

	private ResumenConductoresBean resumen;

	private String codSeleccionados;

	@Autowired
	private ServicioConsultaConductor servicioConsultaConductor;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String consultar() {
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			ResultConsultaConductorBean resultado = servicioConsultaConductor.consultar(codSeleccionados,
					utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				rellenarResumen(resultado);
			}
		} else {
			addActionError("Debe seleccionar algun tramite para poder realizar su operacion.");
		}
		return actualizarPaginatedList();
	}

	public String eliminar() {
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {

			BigDecimal usuario = utilesColegiado.getIdUsuarioSessionBigDecimal();
			ResultConsultaConductorBean resultado = servicioConsultaConductor.eliminar(codSeleccionados, usuario);
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				rellenarResumen(resultado);
			}
		} else {
			addActionError("Debe seleccionar algun tramite para poder realizar su operacion.");
		}
		return actualizarPaginatedList();
	}

	public String cambiarEstado() {
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			ResultConsultaConductorBean resultado = servicioConsultaConductor.cambiarEstado(codSeleccionados,
					estadoNuevo, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				rellenarResumen(resultado);
			}
		} else {
			addActionError("Debe seleccionar algun tramite para poder realizar su operacion.");
		}
		return actualizarPaginatedList();
	}

	public String validar() {
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			ResultConsultaConductorBean resultado = servicioConsultaConductor.validar(codSeleccionados,
					utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				rellenarResumen(resultado);
			}
		} else {
			addActionError("Debe seleccionar algun tramite para poder realizar su operacion.");
		}
		return actualizarPaginatedList();
	}

	@Override
	protected void cargaRestricciones() {
		if (consultaConductorFilter == null) {
			consultaConductorFilter = new ConsultaConductorFilter();
		}
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			consultaConductorFilter.setIdContrato(utilesColegiado.getIdContratoSession());
		}
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloConductorPaginationImpl;

	}

	@Override
	protected Object getBeanCriterios() {
		return consultaConductorFilter;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaConductorFilter = (ConsultaConductorFilter) object;
	}

	@Override
	protected void cargarFiltroInicial() {
		if (consultaConductorFilter == null) {
			consultaConductorFilter = new ConsultaConductorFilter();
		}
		consultaConductorFilter.setFechaAlta(utilesFecha.getFechaFracionadaActual());

		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			consultaConductorFilter.setIdContrato(utilesColegiado.getIdContratoSession());
		}
	}

	public String listado() {
		return LISTADO_CONDUCTOR;
	}

	public ConsultaConductorBean getConsultaConductorBean() {
		return consultaConductorBean;
	}

	public void setConsultaConductorBean(ConsultaConductorBean consultaConductorBean) {
		this.consultaConductorBean = consultaConductorBean;
	}

	public ConsultaConductorFilter getConsultaConductorFilter() {
		return consultaConductorFilter;
	}

	public void setConsultaConductorFilter(ConsultaConductorFilter consultaConductorFilter) {
		this.consultaConductorFilter = consultaConductorFilter;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}

	private void rellenarResumen(ResultConsultaConductorBean resultado) {
		resumen = new ResumenConductoresBean();
		resumen.setNumError(resultado.getNumError());
		resumen.setListaErrores(resultado.getListaErrores());
		resumen.setNumOk(resultado.getNumOk());
		resumen.setListaOk(resultado.getListaOK());
	}

	public String cargarPopUpCambioEstado() {
		return POP_UP_ESTADOS;
	}

	public String popUpCambioEstado() {

		return POP_UP_ESTADOS;
	}

	public static String getPopUpEstados() {
		return POP_UP_ESTADOS;
	}

	public String popPupCambioEstado() {
		return POP_UP_ESTADOS;
	}

	public String cargarPopUpModificarFechas() {
		return POP_UP_MODIFICAR_FECHAS;
	}

	public String modificarFechas() {
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			if ((fechaIniModificacion != null) && (fechaFinModificacion != null)) {
				ResultConsultaConductorBean resultado = servicioConsultaConductor.modificarFechas(codSeleccionados,
						utilesColegiado.getIdUsuarioSessionBigDecimal(), fechaIniModificacion, fechaFinModificacion);
				if (resultado.getError()) {
					addActionError(resultado.getMensaje());
				} else {
					rellenarResumen(resultado);
				}
			} else {
				addActionError("Las fechas no contienen datos.");
			}

		} else {
			addActionError("Debe seleccionar algun expediente para poder realizar su consulta.");
		}

		return actualizarPaginatedList();
	}

	public String getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(String estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public ResumenConductoresBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenConductoresBean resumen) {
		this.resumen = resumen;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return CONSULTA_CONDUCTOR;
	}

	@Override
	public String getDecorator() {
		return "org.gestoresmadrid.oegam2.view.decorator.DecoratorConsultaConductor";
	}

	public Fecha getFechaFinModificacion() {
		return fechaFinModificacion;
	}

	public void setFechaFinModificacion(Fecha fechaFinModificacion) {
		this.fechaFinModificacion = fechaFinModificacion;
	}

	public Fecha getFechaIniModificacion() {
		return fechaIniModificacion;
	}

	public void setFechaIniModificacion(Fecha fechaIniModificacion) {
		this.fechaIniModificacion = fechaIniModificacion;
	}

}