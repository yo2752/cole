package org.gestoresmadrid.oegam2.estadisticas.listados.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.estadisticas.listados.view.beans.ListadoJustificantesNoUltimadosBean;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesional;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.constantes.ConstantesEstadisticas;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ListadoJustificantesNoUltimadosAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -7075987876360062905L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ListadoJustificantesNoUltimadosAction.class);

	@Resource
	private ModelPagination modeloListadoJustificantesNoUltimadosPaginated;

	private ListadoJustificantesNoUltimadosBean listadoJustificantesNoUltimadosBean;

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
	private ServicioJustificanteProfesional servicioJustificanteProfesional;

	public String login() {
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_FALSE);
		return SUCCESS;
	}

	public String comprobarPassword() {
		String passwordPropiedades = gestorPropiedades.valorPropertie(ConstantesEstadisticas.PASSWORD_CAMPO);

		if (listadoJustificantesNoUltimadosBean != null && StringUtils.isNotBlank(passwordPropiedades) && passwordPropiedades.equals(listadoJustificantesNoUltimadosBean.getPassword())
				&& utilesColegiado.tienePermisoAdmin()) {
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
		log.debug("inicio buscar listado justificantes no ultimados");

		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);

		if (listadoJustificantesNoUltimadosBean.getFechaInicio().isfechaNula()) {
			addActionError("Debe indicar un rango válido de Fecha Inicio.");
			return SUCCESS;
		}

		if (StringUtils.isBlank(listadoJustificantesNoUltimadosBean.getNumColegiado())) {
			addActionError("El Número de Colegiado es obligatorio.");
			return SUCCESS;
		}

		numElementosSinAgrupar = servicioJustificanteProfesional.numeroElementosListadoJustificantesNoUltimados(getBeanCriterios(), null, crearListaAlias(), null);
		addActionMessage(" El número total de Justificantes para el Colegiado " + listadoJustificantesNoUltimadosBean.getNumColegiado() + " en el periodo seleccionado es de " + numElementosSinAgrupar
				+ ".");
		return super.buscar();
	}

	@Override
	public String navegar() {
		log.debug("inicio navegar listado justificantes no ultimados");
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
		super.navegar();
		numElementosSinAgrupar = servicioJustificanteProfesional.numeroElementosListadoJustificantesNoUltimados(getBeanCriterios(), null, crearListaAlias(), null);

		return SUCCESS;
	}

	public String generarExcel() {

		log.debug("Inicio Exportando Excel de Justificantes");
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);

		// Comprobamos que tenga algún tipo de permiso de manteniemiento
		if (!utilesColegiado.tienePermisoAdmin()) {
			addActionError("No tienes permiso para realizar esta acción.");
			return SUCCESS;
		}

		ResultBean result = servicioJustificanteProfesional.generarExcelListadoJustificantesNoUltimados(getBeanCriterios());

		if (!result.getError()) {

			File file = (File) result.getAttachment(ResultBean.TIPO_FILE);
			String nombreFichero = (String) result.getAttachment(ResultBean.NOMBRE_FICHERO);

			if (file != null) {

				try {
					setFicheroResultado(new FileInputStream(file.getAbsoluteFile()));
					setFileName(nombreFichero + ConstantesPDF.EXTENSION_XLS);
				} catch (FileNotFoundException e) {
					log.error("Listado de justificantes profesionales no ultimados ha lanzado la siguiente excepción: ", e);
					addActionError("Fichero de justificantes profesionales no ultimados no encontrado.");
					return SUCCESS;
				}
			} else { // La lista está vacía
				addActionError("No hay resultados para el listado de justificantes profesionales no ultimados.");
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

	private List<AliasQueryBean> crearListaAlias() {
		List<AliasQueryBean> listaAlias = new ArrayList<AliasQueryBean>();
		listaAlias.add(new AliasQueryBean(TramiteTraficoVO.class, "tramiteTrafico", "tramiteTrafico", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(VehiculoVO.class, "tramiteTrafico.vehiculo", "tramiteTraficoVehiculo", CriteriaSpecification.LEFT_JOIN));
		return listaAlias;
	}

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
		return modeloListadoJustificantesNoUltimadosPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (listadoJustificantesNoUltimadosBean == null) {
			listadoJustificantesNoUltimadosBean = new ListadoJustificantesNoUltimadosBean();
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if (listadoJustificantesNoUltimadosBean == null) {
			listadoJustificantesNoUltimadosBean = new ListadoJustificantesNoUltimadosBean();
		}
		listadoJustificantesNoUltimadosBean.setFechaInicio(utilesFecha.getFechaFracionadaActual());
	}

	@Override
	protected Object getBeanCriterios() {
		return listadoJustificantesNoUltimadosBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.listadoJustificantesNoUltimadosBean = (ListadoJustificantesNoUltimadosBean) object;
	}

	public ListadoJustificantesNoUltimadosBean getListadoJustificantesNoUltimadosBean() {
		return listadoJustificantesNoUltimadosBean;
	}

	public void setListadoJustificantesNoUltimadosBean(ListadoJustificantesNoUltimadosBean listadoJustificantesNoUltimadosBean) {
		this.listadoJustificantesNoUltimadosBean = listadoJustificantesNoUltimadosBean;
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

}