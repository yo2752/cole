package org.gestoresmadrid.oegam2.estadisticas.listados.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.estadisticas.listados.view.beans.ListadoVehiculosBean;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.constantes.ConstantesEstadisticas;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ListadoVehiculosAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -7075987876360062905L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ListadoVehiculosAction.class);

	@Resource
	private ModelPagination modeloListadoVehiculosPaginated;

	private ListadoVehiculosBean listadoVehiculosBean;

	private int numElementosSinAgrupar;

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
	private ServicioTramiteTrafico servicioTramiteTrafico;

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
		log.debug("inicio buscar listado de vehículos");

		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);

		if (listadoVehiculosBean.getFechaPresentacion().isfechaNula()) {
			addActionError("Debe indicar un rango válido de Fecha de Presentación.");
			return SUCCESS;
		}

		numElementosSinAgrupar = servicioTramiteTrafico.numeroElementosListadosEstadisticas(getBeanCriterios(), null, null, listadoVehiculosBean.getAgrupacionVehiculos());

		if (StringUtils.isNotBlank(listadoVehiculosBean.getAgrupacionVehiculos())) {
			return super.buscar();
		} else {
			listadoVehiculosBean.setFechaPrimMatri(Boolean.TRUE);

			int numElementosConFechaPrimeraMatriculacion = servicioTramiteTrafico.numeroElementosListadosEstadisticas(getBeanCriterios(), null, null, listadoVehiculosBean.getAgrupacionVehiculos());
			addActionMessage("Nº de registros sin fecha de primera matriculación: " + (numElementosSinAgrupar - numElementosConFechaPrimeraMatriculacion));
			addActionMessage("Nº de registros con fecha de primera matriculación: " + numElementosConFechaPrimeraMatriculacion);
			addActionMessage("Total de registros: " + numElementosSinAgrupar);

			return SUCCESS;
		}
	}

	@Override
	public String navegar() {
		log.debug("inicio navegar listado de vehículos");
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
		super.navegar();
		numElementosSinAgrupar = servicioTramiteTrafico.numeroElementosListadosEstadisticas(getBeanCriterios(), null, null, listadoVehiculosBean.getAgrupacionVehiculos());

		return SUCCESS;
	}

	public String generarExcel() {

		log.debug("Inicio Exportando Excel de Vehiculos");
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);

		// Comprobamos que tenga algún tipo de permiso de manteniemiento
		if (!utilesColegiado.tienePermisoAdmin()) {
			addActionError("No tienes permiso para realizar esta acción.");
			return SUCCESS;
		}

		ResultBean result = servicioTramiteTrafico.generarExcelListadoVehiculosEstadisticas(getBeanCriterios());

		if (!result.getError()) {

			File file = (File) result.getAttachment(ResultBean.TIPO_FILE);
			String nombreFichero = (String) result.getAttachment(ResultBean.NOMBRE_FICHERO);

			if (file != null) {

				try {
					setFicheroResultado(new FileInputStream(file.getAbsoluteFile()));
					setFileName(nombreFichero + ConstantesPDF.EXTENSION_XLS);
				} catch (FileNotFoundException e) {
					log.error("Listado de vehículos ha lanzado la siguiente excepción: ", e);
					addActionError("Fichero de Listado de Vehículos Estadísticas no encontrado.");
					return SUCCESS;
				}
			} else { // La lista está vacía
				addActionError("No hay resultados para el listado de vehículos.");
				return SUCCESS;
			}

		} else { // Hay errores en el ResultBean devuelto.
			if (result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
				for (String mensaje : result.getListaMensajes()) {
					addActionError(mensaje);
				}
			} else {
				addActionError(result.getMensaje());
			}
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
		return modeloListadoVehiculosPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (listadoVehiculosBean == null) {
			listadoVehiculosBean = new ListadoVehiculosBean();
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if (listadoVehiculosBean == null) {
			listadoVehiculosBean = new ListadoVehiculosBean();
		}
		listadoVehiculosBean.setFechaPresentacion(utilesFecha.getFechaFracionadaActual());
	}

	@Override
	protected Object getBeanCriterios() {
		return listadoVehiculosBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.listadoVehiculosBean = (ListadoVehiculosBean) object;
	}

	public int getNumElementosSinAgrupar() {
		return numElementosSinAgrupar;
	}

	public void setNumElementosSinAgrupar(int numElementosSinAgrupar) {
		this.numElementosSinAgrupar = numElementosSinAgrupar;
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

	public ListadoVehiculosBean getListadoVehiculosBean() {
		return listadoVehiculosBean;
	}

	public void setListadoVehiculosBean(ListadoVehiculosBean listadoVehiculosBean) {
		this.listadoVehiculosBean = listadoVehiculosBean;
	}

}