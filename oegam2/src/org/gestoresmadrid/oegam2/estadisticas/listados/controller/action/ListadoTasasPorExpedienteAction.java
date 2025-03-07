package org.gestoresmadrid.oegam2.estadisticas.listados.controller.action;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.estadisticas.listados.view.beans.ListadoTasasPorExpedienteBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;

import trafico.utiles.constantes.ConstantesEstadisticas;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ListadoTasasPorExpedienteAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -7075987876360062905L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ListadoTasasPorExpedienteAction.class);

	@Resource
	private ModelPagination modeloListadoTasasPorExpedientePaginated;

	private ListadoTasasPorExpedienteBean listadoTasasPorExpedienteBean;

	private String password; // Password para ver estadísticas
	private String passValidado; // Password Validado para ver estadísticas. Por defecto No hay permiso.

	@Autowired
	UtilesColegiado utilesColegiado;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	public String login() {
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_FALSE);
		return SUCCESS;
	}

	public String comprobarPassword() {
		String passwordPropiedades = gestorPropiedades.valorPropertie(ConstantesEstadisticas.PASSWORD_CAMPO);

		if (StringUtils.isNotBlank(passwordPropiedades) && passwordPropiedades.equals(getPassword()) && utilesColegiado.tienePermisoAdmin()) {
			setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
			cargarFiltroInicial();
		} else {
			setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_ERROR);
			addActionError(ConstantesEstadisticas.PASSWORD_INCORRECTO);
		}

		return SUCCESS;
	}

	@Override
	public String buscar() {
		log.debug("inicio buscar listado tasas por expediente");
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
		setSort("id.fechaHora");
		setDir(GenericDaoImplHibernate.ordenDes);
		return super.buscar();
	}

	@Override
	public String navegar() {
		log.debug("inicio navegar listado tasas por expediente");
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
		return super.navegar();
	}
	// ----------------- GET & SET -------------------------

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassValidado() {
		return passValidado;
	}

	public void setPassValidado(String passValidado) {
		this.passValidado = passValidado;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloListadoTasasPorExpedientePaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (listadoTasasPorExpedienteBean == null) {
			listadoTasasPorExpedienteBean = new ListadoTasasPorExpedienteBean();
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if (listadoTasasPorExpedienteBean == null) {
			listadoTasasPorExpedienteBean = new ListadoTasasPorExpedienteBean();
		}
	}

	@Override
	protected Object getBeanCriterios() {
		return listadoTasasPorExpedienteBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.listadoTasasPorExpedienteBean = (ListadoTasasPorExpedienteBean) object;
	}

	public ListadoTasasPorExpedienteBean getListadoTasasPorExpedienteBean() {
		return listadoTasasPorExpedienteBean;
	}

	public void setListadoTasasPorExpedienteBean(ListadoTasasPorExpedienteBean listadoTasasPorExpedienteBean) {
		this.listadoTasasPorExpedienteBean = listadoTasasPorExpedienteBean;
	}
}