package org.gestoresmadrid.oegam2.arrendatarios.controller.action;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.conductor.controller.action.ConsultaConductorHabitualAction;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.arrendatarios.model.service.ServicioConsultaArrendatario;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.beans.ConsultaArrendatarioBean;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.beans.ConsultaArrendatarioFilter;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.beans.ResultConsultaArrendatarioBean;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.beans.ResumenArrendatariosBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaArrendatariosAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -1765275726632124300L;
	private static final String CONSULTA_ARRENDARARIOS = "inicioConsultaArrendatarios";
	private static final String POP_UP_ESTADOS = "popPupCambioEstado";
	private static final String POP_UP_FINALIZAR = "popPupFinalizar";
	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaConductorHabitualAction.class);

	// Necesario para poder utilizar la seleccion multiple
	private String codSeleccionados;
	private String estadoNuevo;
	private ConsultaArrendatarioFilter consultaArrendatarioFilter;
	private ConsultaArrendatarioBean consultaArrendatarioBean;
	private ResumenArrendatariosBean resumen;
	private String Ini_Dia;
	private String Ini_Mes;
	private String Ini_Anio;
	private String Fin_Dia;
	private String Fin_Mes;
	private String Fin_Anio;
	@Autowired
	private ServicioConsultaArrendatario servicioConsultaArrendatario;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	@Resource
	private ModelPagination modeloArrendatarioPaginationImpl;

	public String consultar() {
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			ResultConsultaArrendatarioBean resultado = servicioConsultaArrendatario.consultar(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal());
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

	public String modificar() {
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
			String stringFechaSinHora = Fin_Dia + "-" + Fin_Mes + "-" + Fin_Anio;
			String stringFechaSinHora2 = Ini_Dia + "-" + Ini_Mes + "-" + Ini_Anio;

			try {
				Date fechaSinHora = sdf.parse(stringFechaSinHora);
				Date fechaSinHora2 = sdf2.parse(stringFechaSinHora2);
				ResultConsultaArrendatarioBean resultado = servicioConsultaArrendatario.modificar(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal(), fechaSinHora, fechaSinHora2);
				if (resultado.getError()) {
					addActionError(resultado.getMensaje());
				} else {
					rellenarResumen(resultado);
				}
			} catch (ParseException e) {
				addActionError("Error en la fecha.");
				e.printStackTrace();
			}

		} else {
			addActionError("Debe seleccionar algun tramite para poder realizar su operacion.");
		}
		return actualizarPaginatedList();
	}

	public String validar() {
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			ResultConsultaArrendatarioBean resultado = servicioConsultaArrendatario.validar(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal());
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
			ResultConsultaArrendatarioBean resultado = servicioConsultaArrendatario.eliminar(codSeleccionados, usuario);
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
		if (consultaArrendatarioFilter == null) {
			consultaArrendatarioFilter = new ConsultaArrendatarioFilter();
		}
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			consultaArrendatarioFilter.setIdContrato(utilesColegiado.getIdContratoSession());
		}
	}

	public String cambiarEstado() {
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			ResultConsultaArrendatarioBean resultado = servicioConsultaArrendatario.cambiarEstado(codSeleccionados, estadoNuevo, utilesColegiado.getIdUsuarioSessionBigDecimal());
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
	protected void cargarFiltroInicial() {
		if (consultaArrendatarioFilter == null) {
			consultaArrendatarioFilter = new ConsultaArrendatarioFilter();
		}

		consultaArrendatarioFilter.setFechaAlta(utilesFecha.getFechaFracionadaActual());

		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio() && !utilesColegiado.tienePermisoEspecial()) {
			consultaArrendatarioFilter.setIdContrato(utilesColegiado.getIdContratoSession());
		}
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloArrendatarioPaginationImpl;
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaArrendatarioFilter;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaArrendatarioFilter = (ConsultaArrendatarioFilter) object;
	}

	public String listado() {
		return CONSULTA_ARRENDARARIOS;
	}

	public ConsultaArrendatarioBean getConsultaArrendatarioBean() {
		return consultaArrendatarioBean;
	}

	public ConsultaArrendatarioFilter getConsultaArrendatarioFilter() {
		return consultaArrendatarioFilter;
	}

	public void setConsultaArrendatarioFilter(ConsultaArrendatarioFilter consultaArrendatarioFilter) {
		this.consultaArrendatarioFilter = consultaArrendatarioFilter;
	}

	public void setConsultaArrendatarioBean(ConsultaArrendatarioBean consultaArrendatarioBean) {
		this.consultaArrendatarioBean = consultaArrendatarioBean;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}

	public ServicioConsultaArrendatario getServicioConsultaArrendatario() {
		return servicioConsultaArrendatario;
	}

	public void setServicioConsultaArrendatario(ServicioConsultaArrendatario servicioConsultaArrendatario) {
		this.servicioConsultaArrendatario = servicioConsultaArrendatario;
	}

	private void rellenarResumen(ResultConsultaArrendatarioBean resultado) {
		resumen = new ResumenArrendatariosBean();
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

	public String cargarPopUpFinalizar() {
		return POP_UP_FINALIZAR;
	}

	public String getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(String estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public static String getConsultaArrendararios() {
		return CONSULTA_ARRENDARARIOS;
	}

	public ResumenArrendatariosBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenArrendatariosBean resumen) {
		this.resumen = resumen;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return CONSULTA_ARRENDARARIOS;
	}

	@Override
	public String getDecorator() {
		return "org.gestoresmadrid.oegam2.view.decorator.DecoratorConsultaArrendatarios";

	}

	public String getIni_Dia() {
		return Ini_Dia;
	}

	public void setIni_Dia(String ini_Dia) {
		Ini_Dia = ini_Dia;
	}

	public String getIni_Mes() {
		return Ini_Mes;
	}

	public void setIni_Mes(String ini_Mes) {
		Ini_Mes = ini_Mes;
	}

	public String getIni_Anio() {
		return Ini_Anio;
	}

	public void setIni_Anio(String ini_Anio) {
		Ini_Anio = ini_Anio;
	}

	public String getFin_Dia() {
		return Fin_Dia;
	}

	public void setFin_Dia(String fin_Dia) {
		Fin_Dia = fin_Dia;
	}

	public String getFin_Mes() {
		return Fin_Mes;
	}

	public void setFin_Mes(String fin_Mes) {
		Fin_Mes = fin_Mes;
	}

	public String getFin_Anio() {
		return Fin_Anio;
	}

	public void setFin_Anio(String fin_Anio) {
		Fin_Anio = fin_Anio;
	}

}
