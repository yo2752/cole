package org.gestoresmadrid.oegam2.estadisticas.listados.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.estadisticas.listados.view.beans.ListadoMensajesErrorServicioBean;
import org.gestoresmadrid.oegam2comun.mensajeErrorServicio.model.service.ServicioMensajeErrorServicio;
import org.gestoresmadrid.oegam2comun.mensajeErrorServicio.view.bean.ResultadoMensajeErrorServicio;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import trafico.utiles.ConstantesPDF;
import trafico.utiles.constantes.ConstantesEstadisticas;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ListadoMensajesErrorServicioAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -7075987876360062905L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ListadoMensajesErrorServicioAction.class);

	@Resource
	private ModelPagination modeloListadoMensajesErrorServicioPaginated;

	private ListadoMensajesErrorServicioBean listadoMensajesErrorServicioBean;

	// Fichero de resultados a descargar
	private InputStream ficheroResultado;
	private String fileName;

	private String password; // Password para ver estadísticas
	private String passValidado; // Password Validado para ver estadísticas. Por defecto No hay permiso.

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private ServicioMensajeErrorServicio servicioMensajeErrorServicio;

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
		log.debug("inicio buscar listado mensajes error servicio");

		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);

		if (listadoMensajesErrorServicioBean.getFecha().isfechaNula()) {
			addActionError("Debe indicar un rango válido de Fecha de Mensaje.");
			return SUCCESS;
		}

		return super.buscar();
	}

	@Override
	public String navegar() {
		log.debug("inicio navegar listado mensajes error servicio");
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
		super.navegar();

		return SUCCESS;
	}

	public String generarExcel() {

		log.debug("Inicio Exportando Excel de Listado Mensajes Error Servicio");
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);

		// Comprobamos que tenga algún tipo de permiso de manteniemiento
		if (!utilesColegiado.tienePermisoAdmin()) {
			addActionError("No tienes permiso para realizar esta acción.");
			return SUCCESS;
		}

		ResultadoMensajeErrorServicio result = servicioMensajeErrorServicio.generarExcelListadoMensajesErrorServicio(listadoMensajesErrorServicioBean.getFecha());

		if (!result.isError()) {

			File file = result.getFile();
			String nombreFichero = result.getNombreFichero();

			if (file != null) {

				try {
					setFicheroResultado(new FileInputStream(file.getAbsoluteFile()));
					setFileName(nombreFichero + ConstantesPDF.EXTENSION_XLS);
				} catch (FileNotFoundException e) {
					log.error("Listado de Mensajes Error Servicio ha lanzado la siguiente excepción: ", e);
					addActionError("Fichero de Mensajes Error Servicio no encontrado.");
					return SUCCESS;
				}
			} else { // La lista está vacía
				addActionError("No hay resultados para el listado de Mensajes Error Servicio.");
				return SUCCESS;
			}

		} else { // Hay errores en el ResultBean devuelto.
			addActionError(result.getMensaje());
			return SUCCESS;
		}

		return "ficheroDownload";
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
		return modeloListadoMensajesErrorServicioPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (listadoMensajesErrorServicioBean == null) {
			listadoMensajesErrorServicioBean = new ListadoMensajesErrorServicioBean();
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if (listadoMensajesErrorServicioBean == null) {
			listadoMensajesErrorServicioBean = new ListadoMensajesErrorServicioBean();
		}
		listadoMensajesErrorServicioBean.setFecha(utilesFecha.getFechaFracionadaActual());
		setSort("fecha");
		setDir(GenericDaoImplHibernate.ordenAsc);
	}

	@Override
	protected Object getBeanCriterios() {
		return listadoMensajesErrorServicioBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.listadoMensajesErrorServicioBean = (ListadoMensajesErrorServicioBean) object;
	}

	public InputStream getFicheroResultado() {
		return ficheroResultado;
	}

	public void setFicheroResultado(InputStream ficheroResultado) {
		this.ficheroResultado = ficheroResultado;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ListadoMensajesErrorServicioBean getListadoMensajesErrorServicioBean() {
		return listadoMensajesErrorServicioBean;
	}

	public void setListadoMensajesErrorServicioBean(ListadoMensajesErrorServicioBean listadoMensajesErrorServicioBean) {
		this.listadoMensajesErrorServicioBean = listadoMensajesErrorServicioBean;
	}

}