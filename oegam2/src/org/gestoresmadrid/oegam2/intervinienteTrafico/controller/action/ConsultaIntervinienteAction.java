package org.gestoresmadrid.oegam2.intervinienteTrafico.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioConsultaInterviniente;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.view.bean.ResultadoConsultaIntervinienteTrafBean;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.view.bean.ResumenIntervinienteTrafBean;
import org.gestoresmadrid.oegam2comun.personas.view.beans.ConsultaIntervinienteBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaIntervinienteAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -1582565860061788280L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaIntervinienteAction.class);

	private ConsultaIntervinienteBean consultaIntervinienteBean;
	private String codSeleccionados;
	private ResumenIntervinienteTrafBean resumen;

	@Resource
	ModelPagination modeloIntervinientePaginated;

	@Autowired
	ServicioConsultaInterviniente servicioConsultaInterviniente;

	@Autowired
	UtilesColegiado utilesColegiado;

	@Override
	public String inicio() {
		// Si se realizó una búsqueda previamente, se recupera el filtro empleado
		Object beanCriterios = getSession().get(getKeyCriteriosSession());
		if (beanCriterios != null) {
			if (!comprobarBeanRelleno((ConsultaIntervinienteBean) beanCriterios)) {
				setBusquedaInicial(false);
			}
			try {
				setBeanCriterios(beanCriterios);
			} catch (ClassCastException e) {
				getLog().error("Error recuperando el filtro del action. La clave \""
								+ getKeyCriteriosSession()
								+ "\" parece que está siendo reutilizada", e);
			}
		} else { //Si no hay filtro cargado, lo inicio.
			setBusquedaInicial(isBuscarInicio());
			cargarFiltroInicial();
		}
		return actualizarPaginatedList();
	}

	private boolean comprobarBeanRelleno(ConsultaIntervinienteBean beanCriterios) {
		return ((beanCriterios.getNif() != null && !beanCriterios.getNif().isEmpty())
				|| (beanCriterios.getNumColegiado() != null && !beanCriterios.getNumColegiado().isEmpty())
				|| beanCriterios.getNumExpediente() != null);
	}

	public String eliminar(){
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			ResultadoConsultaIntervinienteTrafBean resultado = servicioConsultaInterviniente.eliminarIntervinientes(codSeleccionados);
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				rellenarResumen(resultado);
			}
		} else {
			addActionError("Debe seleccionar algún interviniente para poder eliminarlo.");
		}
		return actualizarPaginatedList();
	}

	private void rellenarResumen(ResultadoConsultaIntervinienteTrafBean resultado) {
		resumen = new ResumenIntervinienteTrafBean();
		resumen.setResumenEliminar(Boolean.TRUE);
		resumen.setListaErrores(resultado.getListaErrores());
		resumen.setListaOk(resultado.getListaOK());
		resumen.setNumError(resultado.getNumError());
		resumen.setNumOk(resultado.getNumOk());
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloIntervinientePaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaIntervinienteBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaIntervinienteBean = (ConsultaIntervinienteBean) object;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (consultaIntervinienteBean == null) {
			consultaIntervinienteBean = new ConsultaIntervinienteBean();
		}
		if (!utilesColegiado.tienePermisoAdmin()) {
			consultaIntervinienteBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if (consultaIntervinienteBean == null) {
			consultaIntervinienteBean = new ConsultaIntervinienteBean();
		}
		if (!utilesColegiado.tienePermisoAdmin()) {
			consultaIntervinienteBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
	}

	@Override
	protected boolean isBuscarInicio() {
		return false;
	}

	public ConsultaIntervinienteBean getConsultaIntervinienteBean() {
		return consultaIntervinienteBean;
	}

	public void setConsultaIntervinienteBean(ConsultaIntervinienteBean consultaIntervinienteBean) {
		this.consultaIntervinienteBean = consultaIntervinienteBean;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}

	public ResumenIntervinienteTrafBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenIntervinienteTrafBean resumen) {
		this.resumen = resumen;
	}

}