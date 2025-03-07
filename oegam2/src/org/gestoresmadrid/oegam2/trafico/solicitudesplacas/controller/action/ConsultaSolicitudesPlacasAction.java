package org.gestoresmadrid.oegam2.trafico.solicitudesplacas.controller.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

// import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.trafico.solicitudesplacas.model.vo.SolicitudPlacaVO;
// import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.constantes.ConstantesPlacasMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.enumerados.TipoSolicitudPlacasEnum;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.model.service.PlacasMatriculacionService;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.view.beans.ConsultaPlacasBean;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.view.beans.EstadisticasPlacasBean;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.view.beans.SolicitudPlacaBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.utiles.UtilesVista;
import general.acciones.ActionBase;
import general.acciones.ScriptFeaturesBean;
import general.acciones.TipoPosicionScript;
import general.acciones.TipoScript;
import hibernate.utiles.constantes.ConstantesHibernate;
import oegam.constantes.ConstantesCreditos;
import oegam.constantes.ConstantesSession;
import trafico.modelo.ModeloCreditosTrafico;
import utilidades.estructuras.Fecha;
import utilidades.estructuras.FechaFraccionada;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

/**
 * Action para la solicitud de placas de matriculación
 * @author Alvaro  Fernández
 * @date 26/12/2018
 *
 */
public class ConsultaSolicitudesPlacasAction extends ActionBase {

	/* Constantes */
	private static final long serialVersionUID = 1L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaSolicitudesPlacasAction.class);

	private HashMap<String, Object> parametrosBusqueda;

	///////////////////////////////////////////Paginación
	private String resultadosPorPagina; // La cantidad de elementos a mostrar
										// por página

	@Resource
	private ModelPagination modeloSolicitudPlacaPaginated;

	private InputStream inputStream;	// Flujo de bytes del fichero a imprimir en PDF del action
	private String fileName;			// Nombre del fichero a imprimir
	private String fileSize;			// Tamaño del fichero a imprimir

	private EstadisticasPlacasBean estadisticasPlacasBean;
	/////////////////////////////////////////////Fin de Paginación

	private ConsultaPlacasBean consultaPlacasBean;

	@Autowired
	PlacasMatriculacionService placasMatriculacionService;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	private ModeloCreditosTrafico modeloCreditosTrafico;

	// Atributos

	private ArrayList<SolicitudPlacaBean> listaSolicitudes;

	private BigDecimal numCreditosDisponiblesCTP1;
	private BigDecimal numCreditosDisponiblesCTP2;
	private BigDecimal numCreditosDisponiblesCTP3;
	private BigDecimal numCreditosDisponiblesCTP4;
	private BigDecimal numCreditosDisponiblesCTP5;
	private BigDecimal numCreditosDisponiblesCTP6;
	private BigDecimal numCreditosDisponiblesCTP7;
	private BigDecimal numCreditosDisponiblesCTP8;

	private String idSolicitud;
	private String estadoSolicitudId;

	/**
	 * Prepara la pantalla de búsqueda inicial
	 * @return buscarSolicitud
	 * @throws Throwable
	 */

	public String load_buscarPlacas() throws Throwable {
		cargarCSSBasico();
		cargarJSBasico();

		// Hashmap con los parámetros de búsqueda
		parametrosBusqueda = new HashMap<>();

		// Genero un objeto consulta de placas con los parámetros por defecto
		consultaPlacasBean = new ConsultaPlacasBean();

		Fecha fechaFin = utilesFecha.getFechaActual();
		Fecha fechaInicio = utilesFecha.getPrimerLaborableAnterior(fechaFin);
		FechaFraccionada fechaConsulta = new FechaFraccionada();
		fechaConsulta.setDiaInicio(fechaInicio.getDia());
		fechaConsulta.setMesInicio(fechaInicio.getMes());
		fechaConsulta.setAnioInicio(fechaInicio.getAnio());
		fechaConsulta.setDiaFin(fechaFin.getDia());
		fechaConsulta.setMesFin(fechaFin.getMes());
		fechaConsulta.setAnioFin(fechaFin.getAnio());
		consultaPlacasBean.setFecha(fechaConsulta);

		// Pongo en sesión los parámetros iniciales por defecto
		if (null != resultadosPorPagina) {
			getSession().put(ConstantesSession.RESULTADOS_PAGINA, resultadosPorPagina);
		} else {
			getSession().put(ConstantesSession.RESULTADOS_PAGINA, UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO);
			setResultadosPorPagina(UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO);
		}

		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			try {
				consultaPlacasBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			} catch(Exception e) {
				throw new OegamExcepcion("Error retrieving colegiado");
			}
		}
		getSession().put(ConstantesSession.CONSULTA_SOLICITUDES_PLACAS, consultaPlacasBean);

		String numColegiado = null;
		String matricula = null;
		String tipoMatricula = null;
		Long numExpediente = null;

		numColegiado = consultaPlacasBean.getNumColegiado() != null && !"".equals(consultaPlacasBean.getNumColegiado()) ? consultaPlacasBean.getNumColegiado() : null;
		numExpediente = consultaPlacasBean.getNumExpediente() != null ? consultaPlacasBean.getNumExpediente() : null;
		matricula = consultaPlacasBean.getMatricula() != null && !"".equals(consultaPlacasBean.getMatricula()) ? consultaPlacasBean.getMatricula() : null;
		tipoMatricula = consultaPlacasBean.getTipoMatricula() != null && !"".equals(consultaPlacasBean.getTipoMatricula()) ? consultaPlacasBean.getTipoMatricula() : null;

		listaSolicitudes = (ArrayList<SolicitudPlacaBean>) placasMatriculacionService.buscar(fechaInicio.getDate(), fechaFin.getDate(), numColegiado, numExpediente, matricula, tipoMatricula);

		mantenimientoCamposBuscar();

		return ConstantesPlacasMatriculacion.LISTAR_SOLICITUDES;
	}

	/**
	 * Prepara la pantalla de búsqueda una vez que se han introducido filtros
	 * @return buscarSolicitud
	 * @throws OegamExcepcion
	 */
	public String boton_busqueda() throws OegamExcepcion {
		cargarCSSBasico();
		cargarJSBasico();

		setResultadosPorPagina((String) getSession().get(ConstantesSession.RESULTADOS_PAGINA));
		if (null == getResultadosPorPagina()) {
			setResultadosPorPagina(UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO);
		}
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			try {
				consultaPlacasBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			} catch (Exception e) {
				throw new OegamExcepcion("Error retrieving colegiado");
			}
		}
		getSession().put(ConstantesSession.CONSULTA_SOLICITUDES_PLACAS, consultaPlacasBean);

		Date fechaInicio = null;
		Date fechaFin = null;
		String numColegiado = null;
		String matricula = null;
		String tipoMatricula = null;
		Long numExpediente = null;

		fechaInicio = consultaPlacasBean.getFecha().getFechaInicio();
		fechaFin = consultaPlacasBean.getFecha().getFechaFin();
		numColegiado = consultaPlacasBean.getNumColegiado() != null && !"".equals(consultaPlacasBean.getNumColegiado()) ? consultaPlacasBean.getNumColegiado() : null;
		numExpediente = consultaPlacasBean.getNumExpediente() != null ? consultaPlacasBean.getNumExpediente() : null;
		matricula = consultaPlacasBean.getMatricula() != null && !"".equals(consultaPlacasBean.getMatricula()) ? consultaPlacasBean.getMatricula() : null;
		tipoMatricula = consultaPlacasBean.getTipoMatricula() != null && !"".equals(consultaPlacasBean.getTipoMatricula()) ? consultaPlacasBean.getTipoMatricula() : null;

		listaSolicitudes = (ArrayList<SolicitudPlacaBean>)placasMatriculacionService.buscar(fechaInicio, fechaFin, numColegiado, numExpediente, matricula, tipoMatricula);

		mantenimientoCamposBuscar();

		return ConstantesPlacasMatriculacion.LISTAR_SOLICITUDES;
	}

	/**
	 * Prepara la pantalla de edición de solicitudes
	 * @return inicioSolicitud
	 */
	public String boton_modificar(){
		cargarCSSBasico();
		cargarJSBasico();
		mantenimientoCamposNavegar();
		mantenimientoCamposBuscar();

		String[] ids = idSolicitud.split("-");
		String[] estados = estadoSolicitudId.split("-");
		for (int x = 0; x < estados.length; x++) {
			for (int y = x+1; y < estados.length; y++) {
				if (!estados[x].equals(estados[y])){
					addActionError("No se pueden editar solicitudes con distinto estado");
					return ConstantesPlacasMatriculacion.LISTAR_SOLICITUDES;
				}
			}
		}

		listaSolicitudes = new ArrayList<>();

		for (String id : ids) {
			SolicitudPlacaBean solicitud = placasMatriculacionService.getSolicitudPantalla(Integer.valueOf(id));

			if (solicitud != null) {
				listaSolicitudes.add(solicitud);
			} else {
				addActionError("No se encontró una de la solicitudes indicadas o no es editable.");
			}
		}

		validarCreditos();

		return ConstantesPlacasMatriculacion.INICIO_SOLICITUD;
	}

	/**
	 * Pantalla de borrado de solicitudes
	 * @return inicioSolicitud
	 */
	public String boton_borrar() {
		cargarCSSBasico();
		cargarJSBasico();
		mantenimientoCamposNavegar();
		mantenimientoCamposBuscar();

		String[] ids = idSolicitud.split("-");
		String[] estados = estadoSolicitudId.split("-");
		for (int x = 0; x < estados.length; x++) {
			if (!estados[x].equals("1")){
				addActionError("Solo pueden borrarse solicitudes que se encuentren en estado Iniciado.");
				return ConstantesPlacasMatriculacion.LISTAR_SOLICITUDES;
			}
		}

		Integer numEntidades = Integer.valueOf(1);
		String[] entidades = (String[]) Array.newInstance(String.class, numEntidades);
		entidades[0] = ConstantesHibernate.USUARIO_PROPERTY;

		for (String id : ids) {
			SolicitudPlacaVO solicitud = placasMatriculacionService.getSolicitud(Integer.valueOf(id), entidades);

			listaSolicitudes = new ArrayList<>();

			if (solicitud == null) {
				addActionError("No se encontró una de la solicitudes indicadas o no es editable.");
			}

			placasMatriculacionService.borrarSolicitud(solicitud);
		}

		try {
			return boton_busqueda();
		} catch (OegamExcepcion e) {
			return ConstantesPlacasMatriculacion.LISTAR_SOLICITUDES;
		}

	}

	public String load_inicioEstadisticas()throws Throwable {
		estadisticasPlacasBean = new EstadisticasPlacasBean();

		cargarCSSBasico();
		cargarJSBasico();

		Fecha fechaFin = utilesFecha.getFechaActual();
		Fecha fechaInicio = utilesFecha.getPrimerLaborableAnterior(fechaFin);
		FechaFraccionada fechaConsulta = new FechaFraccionada();
		fechaConsulta.setDiaInicio(fechaInicio.getDia());
		fechaConsulta.setMesInicio(fechaInicio.getMes());
		fechaConsulta.setAnioInicio(fechaInicio.getAnio());
		fechaConsulta.setDiaFin(fechaFin.getDia());
		fechaConsulta.setMesFin(fechaFin.getMes());
		fechaConsulta.setAnioFin(fechaFin.getAnio());
		estadisticasPlacasBean.setFecha(fechaConsulta);

		return "estadisticasSolicitud";
	}

	public String boton_generarEstadisticas() throws Throwable {
		cargarCSSBasico();
		cargarJSBasico();

		byte[] pdfBytes= placasMatriculacionService.generarEstadisticas(estadisticasPlacasBean);
		setFileName("estadisticas_placas.pdf");
		inputStream = new ByteArrayInputStream(pdfBytes);
		return ConstantesPlacasMatriculacion.DESCARGAR_PDF;
	}

	/**
	 * Función de búsqueda para la paginación. Generará la búsqueda limitada por los parámetros de paginación.
	 * @return
	 */
	public String navegar() {
		cargarCSSBasico();
		cargarJSBasico();

		if (getResultadosPorPagina() != null) {
			getSession().put(ConstantesSession.RESULTADOS_PAGINA, getResultadosPorPagina());
		}
		mantenimientoCamposNavegar();

		if (listaSolicitudes == null) {
			log.error("SolicitudPlacasAction navegar al obtener lista de tramites.");
			return ERROR;
		}

		return ConstantesPlacasMatriculacion.LISTAR_SOLICITUDES;
	}

	/*****************************************************************************Funciones adicionales*/

	private void validarCreditos() {
		String contratoSesion = null != utilesColegiado.getIdContratoSessionBigDecimal() ? utilesColegiado.getIdContratoSessionBigDecimal().toString() : null;

		TipoSolicitudPlacasEnum[] tiposSolicitudes = TipoSolicitudPlacasEnum.values();

		for (TipoSolicitudPlacasEnum tipoSolicitud : tiposSolicitudes) {
			Map<String, Object> resultadoModelo = getModeloCreditosTrafico().validarCreditosPorNumColegiado(
				contratoSesion,
				new BigDecimal(1),
				tipoSolicitud.getValorEnum());

			switch (tipoSolicitud) {
				case Solicitud_Placa_Ordinaria_Larga:
					setNumCreditosDisponiblesCTP1((BigDecimal) resultadoModelo.get(ConstantesCreditos.CREDITOS_DISPONIBLES));
					break;
				case Solicitud_Placa_Ordinaria_Corta_Alfa_Romeo:
					setNumCreditosDisponiblesCTP2((BigDecimal) resultadoModelo.get(ConstantesCreditos.CREDITOS_DISPONIBLES));
					break;
				case Solicitud_Placa_Motocicleta:
					setNumCreditosDisponiblesCTP3((BigDecimal) resultadoModelo.get(ConstantesCreditos.CREDITOS_DISPONIBLES));
					break;
				case Solicitud_Placa_Motocicleta_Trial:
					setNumCreditosDisponiblesCTP4((BigDecimal) resultadoModelo.get(ConstantesCreditos.CREDITOS_DISPONIBLES));
					break;
				case Solicitud_Placa_Vehiculo_Especial:
					setNumCreditosDisponiblesCTP5((BigDecimal) resultadoModelo.get(ConstantesCreditos.CREDITOS_DISPONIBLES));
					break;
				case Solicitud_Placa_Ciclomotor:
					setNumCreditosDisponiblesCTP6((BigDecimal) resultadoModelo.get(ConstantesCreditos.CREDITOS_DISPONIBLES));
					break;
				case Solicitud_Placa_Ordinaria_Alta:
					setNumCreditosDisponiblesCTP7((BigDecimal) resultadoModelo.get(ConstantesCreditos.CREDITOS_DISPONIBLES));
					break;
				case Solicitud_Placa_TaxiVTC:
					setNumCreditosDisponiblesCTP8((BigDecimal) resultadoModelo.get(ConstantesCreditos.CREDITOS_DISPONIBLES));
					break;
			}
		}
	}

	/**
	 * Carga en la plantilla la hoja de estilos adicional para las solicitudes de placas
	 */
	private void cargarCSSBasico(){ // Se queda
		// Carga de CSS específico para placas de matriculación
		if (null == addScripts) {
			inicializarScripts();
		}
		ScriptFeaturesBean css = new ScriptFeaturesBean();
		css.setName(ConstantesPlacasMatriculacion.PLACAS_MATR_CSS);
		css.setPosicion(TipoPosicionScript.TOP);
		css.setTipo(TipoScript.CSS);
		addScripts.getScripts().add(css);
		// Fin Carga de JavaScript específico para placas de matriculación
	}

	/**
	 * Carga en la plantilla las funciones de JavaScript adicionales para las solicitudes de placas
	 */
	private void cargarJSBasico() { // Se queda
		// Carga de javaScript específico para placas de matriculación
		if (null == addScripts) {
			inicializarScripts();
		}
		ScriptFeaturesBean js = new ScriptFeaturesBean();
		js.setName(ConstantesPlacasMatriculacion.PLACAS1_MATR_JS_BOTTOM);
		js.setPosicion(TipoPosicionScript.BOTTOM);
		js.setTipo(TipoScript.JS);
		addScripts.getScripts().add(js);
		// Fin Carga de javaScript específico para placas de matriculación
	}

	/**
	 * Método para actualizar los parámetros de la búsqueda. Proceso:
	 * 
	 * -Actualizo los datos de la sesión. -Paso los parámetros de búsqueda.
	 */
	private void mantenimientoCamposBuscar() {
		if (resultadosPorPagina != null) getSession().put(ConstantesSession.RESULTADOS_PAGINA, getResultadosPorPagina());
		if (consultaPlacasBean != null) getSession().put(ConstantesSession.CONSULTA_SOLICITUDES_PLACAS, getConsultaPlacasBean());
		getSession().put(ConstantesSession.LISTA_SOLICITUDES_PLACAS, listaSolicitudes);
	}

	/**
	 * Método para actualizar los parámetros de paginación. Proceso:
	 * 
	 * -Recupero el bean de búsqueda de sesión.
	 * -Actualizo los parámetros de búsqueda.
	 * -Actualizo en sesión los de la paginación.
	 * 
	 */
	private void mantenimientoCamposNavegar() {
		ConsultaPlacasBean consultaSolicitudPlacaBean = (ConsultaPlacasBean) getSession().get(ConstantesSession.CONSULTA_SOLICITUDES_PLACAS);
		if (consultaSolicitudPlacaBean != null) {
			setConsultaPlacasBean(consultaSolicitudPlacaBean);
		}

		@SuppressWarnings("unchecked")
		ArrayList<SolicitudPlacaBean> listaSolicitudes = (ArrayList<SolicitudPlacaBean>) getSession().get(ConstantesSession.LISTA_SOLICITUDES_PLACAS);
		if (listaSolicitudes != null) {
			setListaSolicitudes(listaSolicitudes);
		}
	}

	/*********************************************************************getters y setters*/

	public ArrayList<SolicitudPlacaBean> getListaSolicitudes() {
		return listaSolicitudes;
	}

	public void setListaSolicitudes(ArrayList<SolicitudPlacaBean> listaSolicitudes) {
		this.listaSolicitudes = listaSolicitudes;
	}

	public ModeloCreditosTrafico getModeloCreditosTrafico() {
		if (modeloCreditosTrafico == null) {
			modeloCreditosTrafico = new ModeloCreditosTrafico();
		}
		return modeloCreditosTrafico;
	}

	public void setModeloCreditosTrafico(ModeloCreditosTrafico modeloCreditosTrafico) {
		this.modeloCreditosTrafico = modeloCreditosTrafico;
	}

	public String getResultadosPorPagina() {
		return resultadosPorPagina;
	}

	public void setResultadosPorPagina(String resultadosPorPagina) {
		this.resultadosPorPagina = resultadosPorPagina;
	}

	public ConsultaPlacasBean getConsultaPlacasBean() {
		return consultaPlacasBean;
	}

	public void setConsultaPlacasBean(ConsultaPlacasBean consultaPlacasBean) {
		this.consultaPlacasBean = consultaPlacasBean;
	}

	public String getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public String getEstadoSolicitudId() {
		return estadoSolicitudId;
	}

	public void setEstadoSolicitudId(String estadoSolicitudId) {
		this.estadoSolicitudId = estadoSolicitudId;
	}

	public BigDecimal getNumCreditosDisponiblesCTP1() {
		return numCreditosDisponiblesCTP1;
	}

	public void setNumCreditosDisponiblesCTP1(BigDecimal numCreditosDisponiblesCTP1) {
		this.numCreditosDisponiblesCTP1 = numCreditosDisponiblesCTP1;
	}

	public BigDecimal getNumCreditosDisponiblesCTP2() {
		return numCreditosDisponiblesCTP2;
	}

	public void setNumCreditosDisponiblesCTP2(BigDecimal numCreditosDisponiblesCTP2) {
		this.numCreditosDisponiblesCTP2 = numCreditosDisponiblesCTP2;
	}

	public BigDecimal getNumCreditosDisponiblesCTP3() {
		return numCreditosDisponiblesCTP3;
	}

	public void setNumCreditosDisponiblesCTP3(BigDecimal numCreditosDisponiblesCTP3) {
		this.numCreditosDisponiblesCTP3 = numCreditosDisponiblesCTP3;
	}

	public BigDecimal getNumCreditosDisponiblesCTP4() {
		return numCreditosDisponiblesCTP4;
	}

	public void setNumCreditosDisponiblesCTP4(BigDecimal numCreditosDisponiblesCTP4) {
		this.numCreditosDisponiblesCTP4 = numCreditosDisponiblesCTP4;
	}

	public BigDecimal getNumCreditosDisponiblesCTP5() {
		return numCreditosDisponiblesCTP5;
	}

	public void setNumCreditosDisponiblesCTP5(BigDecimal numCreditosDisponiblesCTP5) {
		this.numCreditosDisponiblesCTP5 = numCreditosDisponiblesCTP5;
	}

	public BigDecimal getNumCreditosDisponiblesCTP6() {
		return numCreditosDisponiblesCTP6;
	}

	public void setNumCreditosDisponiblesCTP6(BigDecimal numCreditosDisponiblesCTP6) {
		this.numCreditosDisponiblesCTP6 = numCreditosDisponiblesCTP6;
	}

	public BigDecimal getNumCreditosDisponiblesCTP7() {
		return numCreditosDisponiblesCTP7;
	}

	public void setNumCreditosDisponiblesCTP7(BigDecimal numCreditosDisponiblesCTP7) {
		this.numCreditosDisponiblesCTP7 = numCreditosDisponiblesCTP7;
	}

	public BigDecimal getNumCreditosDisponiblesCTP8() {
		return numCreditosDisponiblesCTP8;
	}

	public void setNumCreditosDisponiblesCTP8(BigDecimal numCreditosDisponiblesCTP8) {
		this.numCreditosDisponiblesCTP8 = numCreditosDisponiblesCTP8;
	}

	public HashMap<String, Object> getParametrosBusqueda() {
		return parametrosBusqueda;
	}

	public void setParametrosBusqueda(HashMap<String, Object> parametrosBusqueda) {
		this.parametrosBusqueda = parametrosBusqueda;
	}

	public EstadisticasPlacasBean getEstadisticasPlacasBean() {
		return estadisticasPlacasBean;
	}

	public void setEstadisticasPlacasBean(
			EstadisticasPlacasBean estadisticasPlacasBean) {
		this.estadisticasPlacasBean = estadisticasPlacasBean;
	}

	/*******************************************paginación (copia de RecuperarTramiteAction.java)*/
	/*@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {
		if (consultaPlacasBean == null) { //consultaRecuperarTramiteBean
			consultaPlacasBean = new ConsultaPlacasBean();
		}

		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio() && !utilesColegiado.tienePermisoEspecial()) {
			consultaPlacasBean.setIdContrato(utilesColegiado.getIdContrato());
		}
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloSolicitudPlacaPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaPlacasBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaPlacasBean = (ConsultaPlacasBean) object;
	}*/

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	/*@Override
	protected void cargarFiltroInicial() {
		if (consultaPlacasBean == null) { 
			consultaPlacasBean = new ConsultaPlacasBean();
		}
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio() && !utilesColegiado.tienePermisoEspecial()) {
			consultaPlacasBean.setIdContrato(utilesColegiado.getIdContrato());
		}
		consultaPlacasBean.setFecha(utilesFecha.getFechaFracionadaActual());

		setSort("fechaCreacion");
		setDir(GenericDaoImplHibernate.ordenDes);
	}*/

}