package org.gestoresmadrid.presentacion.jpt.controller.action;

import org.gestoresmadrid.oegam2comun.presentacion.jpt.model.service.ServicioEstadisticasJPT;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.view.beans.EstadisticasJPTBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class EstadisticasJPTAction extends ActionBase {

	private static final long serialVersionUID = 4103957618165302922L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(EstadisticasJPTAction.class);

	@Autowired
	private ServicioEstadisticasJPT servicioEstadisticasPresentacionJPTImpl;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;

	private EstadisticasJPTBean estadisticasJPTBean;

	public String cargaInicial() {
		cargarValoresIniciales();
		estadisticasJPTBean.setListadoTipoEstadisticasJPTBean(servicioEstadisticasPresentacionJPTImpl.getlistaTipoEstadisticasJPT());
		return SUCCESS;
	}

	public String guardar() {
		try {
			ResultBean resultado = servicioEstadisticasPresentacionJPTImpl.comprobarBean(estadisticasJPTBean);
			if (resultado != null) {
				addActionError(resultado.getListaMensajes().get(0));
			} else {
				resultado = servicioEstadisticasPresentacionJPTImpl.guardarEstadisticas(estadisticasJPTBean);
				if (resultado != null) {
					addActionError(resultado.getListaMensajes().get(0));
				} else {
					addActionMessage("El alta se ha realizado correctamente.");
				}
			}
		} catch (Exception e) {
			log.error("Se ha producido un error a la hora de guardar las estadisticas jpt: " + e);
			addActionError("Se ha producido un error a la hora de guardar las estadisticas JPT, verifique los datos introducidos.");
		}
		return cargaInicial();
	}

	public String mostrarIncidencias(){
		servicioEstadisticasPresentacionJPTImpl.getEstadisticasConIncidencias(estadisticasJPTBean);
		return SUCCESS;
	}

	private void cargarValoresIniciales() {
		estadisticasJPTBean = new EstadisticasJPTBean();
		estadisticasJPTBean.setFecha(utilesFecha.getFechaFracionadaActual());
		if (utilesColegiado.tienePermisoAdmin()) {
			estadisticasJPTBean.setIncidencias(false);
			if (!utilesColegiado.getTipoUsuarioAdminJefaturaJpt()) {
				estadisticasJPTBean.setJefaturaJPT(servicioEstadisticasPresentacionJPTImpl.getJefaturaProvincialPorUsuario(utilesColegiado.getIdUsuarioSession()));
			}
		}
	}

	public EstadisticasJPTBean getEstadisticasJPTBean() {
		return estadisticasJPTBean;
	}

	public void setEstadisticasJPTBean(EstadisticasJPTBean estadisticasJPTBean) {
		this.estadisticasJPTBean = estadisticasJPTBean;
	}

}