package org.gestoresmadrid.oegam2.estadisticas.listados.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.direccion.model.vo.ProvinciaVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.TipoIntervinienteVO;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoVehiculoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.estadisticas.listados.view.beans.ListadoGeneralYPersonalizadoBean;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import estadisticas.utiles.enumerados.Agrupacion;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.constantes.ConstantesEstadisticas;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ListadoGeneralYPersonalizadoAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -7075987876360062905L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ListadoGeneralYPersonalizadoAction.class);

	@Resource
	private ModelPagination modeloListadoGeneralYPersonalizadoPaginated;

	private ListadoGeneralYPersonalizadoBean listadoGeneralYPersonalizadoBean;

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
		log.debug("inicio buscar listado general y personalizado");

		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);

		if (listadoGeneralYPersonalizadoBean.getFechaPresentacion().isfechaNula()) {
			addActionError("Debe indicar un rango válido de Fecha de Presentación.");
			return SUCCESS;
		}

		List<AliasQueryBean> listaAlias = crearListaAlias(listadoGeneralYPersonalizadoBean.getAgrupacion());

		numElementosSinAgrupar = servicioTramiteTrafico.numeroElementosListadosEstadisticas(getBeanCriterios(), null, listaAlias, listadoGeneralYPersonalizadoBean.getAgrupacion());

		if (StringUtils.isNotBlank(listadoGeneralYPersonalizadoBean.getAgrupacion())) {

			ResultBean resultado = servicioTramiteTrafico.validarAgrupacionListadoGeneralYPersonalizado(listadoGeneralYPersonalizadoBean);
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
				return SUCCESS;
			}

			return super.buscar();
		} else {
			addActionMessage(" El Número Total de Trámites es de : " + numElementosSinAgrupar + ".");
			return SUCCESS;
		}
	}

	@Override
	public String navegar() {
		log.debug("inicio navegar listado general y personalizado");
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
		super.navegar();

		List<AliasQueryBean> listaAlias = crearListaAlias(listadoGeneralYPersonalizadoBean.getAgrupacion());

		numElementosSinAgrupar = servicioTramiteTrafico.numeroElementosListadosEstadisticas(getBeanCriterios(), null, listaAlias, listadoGeneralYPersonalizadoBean.getAgrupacion());

		return SUCCESS;
	}

	private List<AliasQueryBean> crearListaAlias(String agrupacion) {
		List<AliasQueryBean> listaAlias = new ArrayList<AliasQueryBean>();
		listaAlias.add(new AliasQueryBean(ContratoVO.class, "contrato", "contrato", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(ProvinciaVO.class, "contrato.provincia", "contratoProvincia", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(JefaturaTraficoVO.class, "contrato.jefaturaTrafico", "contratoJefaturaTrafico", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(VehiculoVO.class, "vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(TipoVehiculoVO.class, "vehiculo.tipoVehiculoVO", "vehiculoTipoVehiculoVO", CriteriaSpecification.LEFT_JOIN));
		if (Agrupacion.Municipio_Titular.getValorEnum().equals(agrupacion)) {
			listaAlias.add(new AliasQueryBean(IntervinienteTraficoVO.class, "intervinienteTraficos", "intervinienteTraficos", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(DireccionVO.class, "intervinienteTraficos.direccion", "intervinienteTraficosDireccion", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(TipoIntervinienteVO.class, "intervinienteTraficos.tipoIntervinienteVO", "intervinienteTraficosTipoIntervinienteVO", CriteriaSpecification.LEFT_JOIN));
			listadoGeneralYPersonalizadoBean.setTipoTramite(TipoTramiteTrafico.Matriculacion.getValorEnum());
			listadoGeneralYPersonalizadoBean.setTipoInterviniente(TipoInterviniente.Titular.getValorEnum());
		} else {
			listadoGeneralYPersonalizadoBean.setTipoInterviniente(null);
		}
		return listaAlias;

	}

	public String generarExcel() {

		log.debug("Inicio Exportando Excel general y personalizado");
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);

		// Comprobamos que tenga algún tipo de permiso de manteniemiento
		if (!utilesColegiado.tienePermisoAdmin()) {
			addActionError("No tienes permiso para realizar esta acción.");
			return SUCCESS;
		}

		ResultBean result = servicioTramiteTrafico.generarExcelListadoGeneralYPersonalizadoEstadisticas(getBeanCriterios());

		if (!result.getError()) {

			File file = (File) result.getAttachment(ResultBean.TIPO_FILE);
			String nombreFichero = (String) result.getAttachment(ResultBean.NOMBRE_FICHERO);

			if (file != null) {

				try {
					setFicheroResultado(new FileInputStream(file.getAbsoluteFile()));
					setFileName(nombreFichero + ConstantesPDF.EXTENSION_XLS);
				} catch (FileNotFoundException e) {
					log.error("Listado general y personalizado ha lanzado la siguiente excepción: ", e);
					addActionError("Fichero de Listado general y específico no encontrado.");
					return SUCCESS;
				}
			} else { // La lista está vacía
				addActionError("No hay resultados para el listado general y específico.");
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
		return modeloListadoGeneralYPersonalizadoPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (listadoGeneralYPersonalizadoBean == null) {
			listadoGeneralYPersonalizadoBean = new ListadoGeneralYPersonalizadoBean();
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if (listadoGeneralYPersonalizadoBean == null) {
			listadoGeneralYPersonalizadoBean = new ListadoGeneralYPersonalizadoBean();
		}
		listadoGeneralYPersonalizadoBean.setFechaPresentacion(utilesFecha.getFechaFracionadaActual());
	}

	@Override
	protected Object getBeanCriterios() {
		return listadoGeneralYPersonalizadoBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.listadoGeneralYPersonalizadoBean = (ListadoGeneralYPersonalizadoBean) object;
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

	public ListadoGeneralYPersonalizadoBean getListadoGeneralYPersonalizadoBean() {
		return listadoGeneralYPersonalizadoBean;
	}

	public void setListadoGeneralYPersonalizadoBean(ListadoGeneralYPersonalizadoBean listadoGeneralYPersonalizadoBean) {
		this.listadoGeneralYPersonalizadoBean = listadoGeneralYPersonalizadoBean;
	}

}