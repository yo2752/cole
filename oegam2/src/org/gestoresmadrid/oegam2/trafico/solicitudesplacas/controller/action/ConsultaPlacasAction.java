package org.gestoresmadrid.oegam2.trafico.solicitudesplacas.controller.action;

import java.text.ParseException;
import java.util.Collection;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.constantes.ConstantesPlacasMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.view.beans.SolicitudPlacasFilterBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamPlacasMatricula.service.ServicioPlacasMatricula;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ScriptFeaturesBean;
import general.acciones.TipoPosicionScript;
import general.acciones.TipoScript;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaPlacasAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 5202117990632242695L;

	private static final ILoggerOegam log = LoggerOegam.getLogger("SolicitudPlacas");
	private static final String LISTA_ERRORES = "listaErrores";
	private static final String LISTA_MENSAJES = "listaMensajes";

	// Atributos
	private SolicitudPlacasFilterBean solicitudPlacasFilterBean;

	@Autowired
	ServicioPlacasMatricula servicioPlacasMatricula;

	@Resource
	ModelPagination modeloSolicitudPlacasPaginated;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;

	/**
	 * Carga en la plantilla la hoja de estilos adicional para las solicitudes de placas
	 */
	private void cargarCSSBasico() {
		// Carga de CSS específico para placas de matriculación
		if (null == addScripts) {
			inicializarScripts();
		}
		ScriptFeaturesBean css = new ScriptFeaturesBean();
		css.setName(ConstantesPlacasMatriculacion.PLACAS_MATR_CSS);
		css.setPosicion(TipoPosicionScript.TOP);
		css.setTipo(TipoScript.CSS);
		addScripts.getScripts().add(css);
		// Fin Carga de CSS específico para placas de matriculación
	}

	@SuppressWarnings("unchecked")
	@Override
	public String inicio() {
		if (getSession().get(LISTA_ERRORES) != null) {
			super.setActionErrors((Collection<String>) getSession().get(LISTA_ERRORES));
			getSession().remove(LISTA_ERRORES);
		}

		if (getSession().get(LISTA_MENSAJES) != null) {
			super.setActionMessages((Collection<String>) getSession().get(LISTA_MENSAJES));
			getSession().remove(LISTA_MENSAJES);
		}

		String retorno = super.inicio();
		cargarCSSBasico();
		return retorno;
	}

	@Override
	public String buscar() {
		String retorno = super.buscar();
		cargarCSSBasico();
		return retorno;
	}

	@Override
	public String navegar() {
		String resultadosPorPagina = ServletActionContext.getRequest().getParameter("resultadosPorPagina");
		super.setResultadosPorPagina(resultadosPorPagina);

		String retorno = super.navegar();
		cargarCSSBasico();
		return retorno;
	}

	@Override
	protected void cargaRestricciones() {
		if (!utilesColegiado.tienePermisoAdmin()) {
			solicitudPlacasFilterBean.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal().intValue());
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if (solicitudPlacasFilterBean == null) {
			solicitudPlacasFilterBean = new SolicitudPlacasFilterBean();
		}
		solicitudPlacasFilterBean.setFechaSolicitud(utilesFecha.getFechaFracionadaActual());

		Fecha fechaAnteriorActual;
		try {
			fechaAnteriorActual = utilesFecha.getDiaMesAnterior();
			solicitudPlacasFilterBean.getFechaSolicitud().setDiaInicio(fechaAnteriorActual.getDia());
			solicitudPlacasFilterBean.getFechaSolicitud().setMesInicio(fechaAnteriorActual.getMes());
			solicitudPlacasFilterBean.getFechaSolicitud().setAnioInicio(fechaAnteriorActual.getAnio());
		} catch (ParseException e) {
			log.error("Fallo en parseo de fecha: ", e);
		}
	}

	@Override
	protected Object getBeanCriterios() {
		return solicitudPlacasFilterBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		solicitudPlacasFilterBean = (SolicitudPlacasFilterBean) object;

	}

	public SolicitudPlacasFilterBean getSolicitudPlacasFilterBean() {
		return solicitudPlacasFilterBean;
	}

	public void setSolicitudPlacasFilterBean(SolicitudPlacasFilterBean solicitudPlacasFilterBean) {
		this.solicitudPlacasFilterBean = solicitudPlacasFilterBean;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return ConstantesPlacasMatriculacion.LISTAR_SOLICITUDES;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloSolicitudPlacasPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

}