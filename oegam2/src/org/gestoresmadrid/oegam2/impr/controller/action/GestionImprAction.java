package org.gestoresmadrid.oegam2.impr.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.impr.service.ServicioGestionarImpr;
import org.gestoresmadrid.oegamComun.impr.view.bean.GestionImprFilterBean;
import org.gestoresmadrid.oegamComun.impr.view.bean.ResultadoImprBean;
import org.gestoresmadrid.oegamComun.impr.view.bean.ResumenImprBean;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class GestionImprAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 1109011847189423918L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(GestionImprAction.class);

	private static final String POP_UP_ESTADOS = "popUpCambioEstadoImpr";
	private static final String POP_UP_CARPETA = "popUpModificarCarpetaImpr";

	private String codSeleccionados;
	private GestionImprFilterBean consultaImpr;
	private ResumenImprBean resumen;
	private String estadoNuevo;
	private String carpetaNueva;

	@Resource
	ModelPagination modeloImprPaginated;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	@Autowired
	ServicioGestionarImpr servicioGestionImpr;

	public String cambiarEstado() {
		try {
			ResultadoImprBean resultado = servicioGestionImpr.cambiarEstadoImpr(codSeleccionados, estadoNuevo, utilesColegiado.getIdUsuarioSession());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				resumen = resultado.getResumen();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de  cambiar el estado IMPR, error: ", e);
			addActionError("Ha sucedido un error a la hora de cambiar el estado IMPR");
		}
		return actualizarPaginatedList();
	}

	public String modificarCarpeta() {
		try {
			ResultadoImprBean resultado = servicioGestionImpr.modificarCarpetaImpr(codSeleccionados, carpetaNueva, utilesColegiado.getIdUsuarioSession());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				resumen = resultado.getResumen();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de modificar la carpeta IMPR, error: ", e);
			addActionError("Ha sucedido un error a la hora de modificar la carpeta IMPR");
		}
		return actualizarPaginatedList();
	}
	public String solicitarImpr() {
		try {
			ResultadoImprBean resultado = servicioGestionImpr.solicitarImpr(codSeleccionados, utilesColegiado.getIdUsuarioSession());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				resumen = resultado.getResumen();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar el IMPR, error: ", e);
			addActionError("Ha sucedido un error a la hora de solicitar el IMPR.");
		}
		return actualizarPaginatedList();
	}

	public String generarKo() {
		try {
			ResultadoImprBean resultado = servicioGestionImpr.generarKo(codSeleccionados, utilesColegiado.getIdUsuarioSession());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				resumen = resultado.getResumen();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar el IMPR, error: ", e);
			addActionError("Ha sucedido un error a la hora de solicitar el IMPR.");
		}
		return actualizarPaginatedList();
	}

	public String desasignarDocumento() {
		try {
			ResultadoImprBean resultado = servicioGestionImpr.desasignarDocumento(codSeleccionados, utilesColegiado.getIdUsuarioSession());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				resumen = resultado.getResumen();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de desasignar el documento, error: ", e);
			addActionError("Ha sucedido un error a la hora de desasignar el documento.");
		}
		return actualizarPaginatedList();
	}

	@Override
	public String inicio() {
		cargarFiltroInicial();
		return SUCCESS;
	}

	public String cargarPopUpCambioEstado() {
		return POP_UP_ESTADOS;
	}

	public String cargarPopUpModificaCarpeta() {
		return POP_UP_CARPETA;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloImprPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (consultaImpr == null) {
			consultaImpr = new GestionImprFilterBean();
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if (consultaImpr == null) {
			consultaImpr = new GestionImprFilterBean();
		}
		consultaImpr.setFechaAlta(utilesFecha.getFechaFracionadaActual());
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaImpr;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaImpr = (GestionImprFilterBean) object;
	}

	public ModelPagination getModeloImprPaginated() {
		return modeloImprPaginated;
	}

	public void setModeloImprPaginated(ModelPagination modeloImprPaginated) {
		this.modeloImprPaginated = modeloImprPaginated;
	}

	public GestionImprFilterBean getConsultaImpr() {
		return consultaImpr;
	}

	public void setConsultaImpr(GestionImprFilterBean consultaImpr) {
		this.consultaImpr = consultaImpr;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}

	public ResumenImprBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenImprBean resumen) {
		this.resumen = resumen;
	}

	public String getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(String estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public String getCarpetaNueva() {
		return carpetaNueva;
	}

	public void setCarpetaNueva(String carpetaNueva) {
		this.carpetaNueva = carpetaNueva;
	}

}