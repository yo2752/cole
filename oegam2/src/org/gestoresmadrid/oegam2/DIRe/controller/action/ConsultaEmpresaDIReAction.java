package org.gestoresmadrid.oegam2.DIRe.controller.action;

import java.math.BigDecimal;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.EmpresaDIRe.model.service.ServicioEmpresaDIRe;
import org.gestoresmadrid.oegam2comun.EmpresaDIRe.view.beans.ConsultaEmpresaDIReBean;
import org.gestoresmadrid.oegam2comun.EmpresaDIRe.view.beans.ConsultaEmpresaDIReFilter;
import org.gestoresmadrid.oegam2comun.EmpresaDIRe.view.beans.ResultConsultaEmpresaDIReBean;
import org.gestoresmadrid.oegam2comun.EmpresaDIRe.view.beans.ResumenEmpresaDIReBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaEmpresaDIReAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 2433401131317961110L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaEmpresaDIReAction.class);

	private static final String CONSULTA_DIRe = "consulta_ConsultaEmpresaDIRe";

	private static final String LISTADO_DIRe = "consulta_ConsultaEmpresaDIRe";

	private static final String POP_UP_ESTADOS = "popPupCambioEstado";

	private static final String POP_UP_MODIFICAR_FECHAS = "popPupModificarFechas";

	private String estadoNuevo;

	private Fecha fechaIniModificacion;
	private Fecha fechaFinModificacion;

	@Autowired
	private ModelPagination ModeloEmpresaDIRePaginationImpl;

	private ConsultaEmpresaDIReFilter consultaEmpresaDIReFilter;

	private ConsultaEmpresaDIReBean consultaEmpresaDIReBean;

	private ResumenEmpresaDIReBean resumen;

	private String codSeleccionados;

	@Autowired
	private ServicioEmpresaDIRe servicioEmpresaDIRe;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String consulta() {

		return actualizarPaginatedList();
	}

	public String eliminar() {
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {

			BigDecimal usuario = utilesColegiado.getIdUsuarioSessionBigDecimal();
			ResultConsultaEmpresaDIReBean resultado = servicioEmpresaDIRe.eliminar(codSeleccionados, usuario);
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
			ResultConsultaEmpresaDIReBean resultado = servicioEmpresaDIRe.cambiarEstado(codSeleccionados, estadoNuevo,
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

	public String validar() {
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			ResultConsultaEmpresaDIReBean resultado = servicioEmpresaDIRe.validar(codSeleccionados,
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
		if (consultaEmpresaDIReFilter == null) {
			consultaEmpresaDIReFilter = new ConsultaEmpresaDIReFilter();
			consultaEmpresaDIReFilter.setFechaIni(utilesFecha.getFechaFracionadaActual());
		}
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			consultaEmpresaDIReFilter.setIdContrato(utilesColegiado.getIdContratoSession());
		}
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return ModeloEmpresaDIRePaginationImpl;

	}

	@Override
	protected Object getBeanCriterios() {
		return consultaEmpresaDIReFilter;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaEmpresaDIReFilter = (ConsultaEmpresaDIReFilter) object;
	}

	@Override
	protected void cargarFiltroInicial() {
		if (consultaEmpresaDIReFilter == null) {
			consultaEmpresaDIReFilter = new ConsultaEmpresaDIReFilter();
		}
		consultaEmpresaDIReFilter.setFechaIni(utilesFecha.getFechaFracionadaActual());

		// if (!utilesColegiado.tienePermisoAdmin() &&
		// !utilesColegiado.tienePermisoColegio()){
		// consultaEmpresaDIReFilter.setIdContrato(utilesColegiado.getIdContrato().longValue());
		// }
	}

	public String listado() {
		return LISTADO_DIRe;
	}

	public ConsultaEmpresaDIReBean getConsultaEmpresaDIReBean() {
		return consultaEmpresaDIReBean;
	}

	public void setConsultaEmpresaDIReBean(ConsultaEmpresaDIReBean consultaEmpresaDIReBean) {
		this.consultaEmpresaDIReBean = consultaEmpresaDIReBean;
	}

	public ConsultaEmpresaDIReFilter getConsultaEmpresaDIReFilter() {
		return consultaEmpresaDIReFilter;
	}

	public void setConsultaEmpresaDIReFilter(ConsultaEmpresaDIReFilter consultaEmpresaDIReFilter) {
		this.consultaEmpresaDIReFilter = consultaEmpresaDIReFilter;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}

	private void rellenarResumen(ResultConsultaEmpresaDIReBean resultado) {
		resumen = new ResumenEmpresaDIReBean();
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
				ResultConsultaEmpresaDIReBean resultado = servicioEmpresaDIRe.modificarFechas(codSeleccionados,
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

	public ResumenEmpresaDIReBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenEmpresaDIReBean resumen) {
		this.resumen = resumen;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return CONSULTA_DIRe;
	}

	@Override
	public String getDecorator() {
		return null;

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