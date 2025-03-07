package estadisticas.acciones;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.gestoresmadrid.core.administracion.model.vo.UsuarioLoginVO;
import org.gestoresmadrid.oegamComun.session.service.ServicioSesion;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.utiles.UtilesVista;
import estadisticas.beans.consulta.ConsultaEstadisticasBean;
import estadisticas.beans.daos.EstadisticasDAOImpl;
import estadisticas.utiles.EstadisticasAgrupadas;
import estadisticas.utiles.EstadisticasAgrupadasExcel;
import estadisticas.utiles.EstadisticasAgrupadasJustificantesExcel;
import estadisticas.utiles.enumerados.Agrupacion;
import estadisticas.utiles.enumerados.AgrupacionVehiculos;
import estadisticas.utiles.enumerados.ConvierteCodigoALiteral;
import general.acciones.ActionBase;
import hibernate.entities.tasas.EvolucionTasa;
import hibernate.entities.trafico.JustificanteProf;
import hibernate.entities.trafico.TramiteTrafico;
import hibernate.entities.trafico.Vehiculo;
import oegam.constantes.ConstantesSession;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.constantes.ConstantesEstadisticas;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@SuppressWarnings("serial")
public class EstadisticasAction extends ActionBase {
	private List<UsuarioLoginVO> listaConsultaEstadisticasUsuariosIP;
	private List<TramiteTrafico> listaConsultaEstadisticasTramiteTraf;
	private List<JustificanteProf> listaConsultaEstadisticasJustificantes;
	private List<EstadisticasAgrupadas> listaConsultaEstadisticasAgrupadas;
	private List<Vehiculo> listaConsultaEstadisticasVehiculos;
	private List<Vehiculo> listaConsultaEstadisticasVehiculosConFechaMatriculacion;
	private List<Vehiculo> listaConsultaEstadisticasVehiculosSinFechaMatriculacion;
	private List<EvolucionTasa> listaConsultaEstadisticasEvolucionTasa;
	private List<Integer> listaFrontales;
	private List<Object[]> listaFrontalesUsuarios;
	private List<Object[]> listasUsuariosRepetidos;
	private List<Object[]> listaUsuariosTotalesFrontales;

	private HashMap mapFechaCalculada;
	private String resultadosPorPagina; // La cantidad de elementos a mostrar
										// por página

	private static final ILoggerOegam log = LoggerOegam.getLogger(EstadisticasAction.class);

	private InputStream inputStream; // Flujo de bytes del fichero a imprimir en
										// PDF del action
	private String fileName; // Nombre del fichero a imprimir
	private String fileSize; // Tamaño del fichero a imprimir

	private String password; // Password para ver estadísticas
	private String passValidado; // Password Validado para ver estadísticas. Por
									// defecto No hay permiso

	@Resource
	private ServicioSesion servicioSesion;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	UtilesFecha utilesFecha;

	// Variables del formulario, pertenecen al bean.
	private ConsultaEstadisticasBean consultaEstadisticas;

	// Parámetros generales de los listar
	HashMap<String, Object> parametrosBusqueda;

	// Constantes bigDecimal para campo erroneo en agrupacion con filtro.
	final BigDecimal NUM_COLEGIADO = BigDecimal.ONE;
	final BigDecimal PROVINCIA = BigDecimal.valueOf(2);
	final BigDecimal JEFATURA = BigDecimal.valueOf(3);
	final BigDecimal ESTADO_TRAMITE = BigDecimal.valueOf(11);
	final BigDecimal TIPO_TRAMITE = BigDecimal.TEN;
	final BigDecimal TIPO_VEHICULO = BigDecimal.valueOf(13);
	final BigDecimal MARCA = BigDecimal.valueOf(12);

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	private EstadisticasDAOImpl estadisticasDAO;
	
	public String listadoGeneral() throws Throwable {
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_FALSE);
		return ConstantesEstadisticas.LISTADO_GENERAL;
	}

	public String listadoJustificantesNoUltimados() throws Throwable {
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_FALSE);
		return ConstantesEstadisticas.LISTADO_JUSTIFICANTES_NO_ULTIMADOS;
	}

	public String listadoUsuariosIP() throws Throwable {
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_FALSE);
		return ConstantesEstadisticas.LISTADO_USUARIOSIP;
	}

	public String listadoEvolucionTasa() throws Throwable {
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_FALSE);
		return ConstantesEstadisticas.LISTADO_EVOLUCIONTASAEXP;
	}

	public String listadoVehiculos() throws Throwable {
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_FALSE);
		return ConstantesEstadisticas.LISTADO_VEHICULOS;
	}

	public String listadoMatriculas() throws Throwable {
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_FALSE);
		return ConstantesEstadisticas.LISTADO_MATRICULAS;
	}

	public String comprobarPasswordGeneral() {
		String passwordPropiedades = gestorPropiedades.valorPropertie(ConstantesEstadisticas.PASSWORD_CAMPO);

		if (consultaEstadisticas != null && passwordPropiedades != null && passwordPropiedades.equals(consultaEstadisticas.getPassword())) {
			setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);

			parametrosBusqueda = new HashMap<String, Object>();
			consultaEstadisticas = new ConsultaEstadisticasBean(true);
			consultaEstadisticas.setFechaMatriculacion(utilesFecha.getFechaFracionadaActual());

			if (null != resultadosPorPagina) {
				getSession().put(ConstantesSession.RESULTADOS_PAGINA, resultadosPorPagina);
			} else {
				getSession().put(ConstantesSession.RESULTADOS_PAGINA, UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO_ESTADISTICAS);
			}

			getSession().put(ConstantesSession.CONSULTA_ESTADISTICAS, null);
			setListaConsultaEstadisticasAgrupadas(listaConsultaEstadisticasAgrupadas);
			addActionMessage(ConstantesEstadisticas.PASSWORD_CORRECTO);
			return ConstantesEstadisticas.LISTADO_GENERAL;
		} else {
			setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_ERROR);
			addActionError(ConstantesEstadisticas.PASSWORD_INCORRECTO);
			return ConstantesEstadisticas.LISTADO_GENERAL;
		}
	}

	public String comprobarPasswordUsuarios() {
		String passwordPropiedades = gestorPropiedades.valorPropertie(ConstantesEstadisticas.PASSWORD_CAMPO);

		if (consultaEstadisticas != null && passwordPropiedades != null && passwordPropiedades.equals(consultaEstadisticas.getPassword())) {
			setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
			parametrosBusqueda = new HashMap<String, Object>();
			consultaEstadisticas = new ConsultaEstadisticasBean(true);
			consultaEstadisticas.setFechaMatriculacion(utilesFecha.getFechaFracionadaActual());
			// mantenimientoCamposNavegar();

			if (null != resultadosPorPagina) {
				getSession().put(ConstantesSession.RESULTADOS_PAGINA, resultadosPorPagina);
			} else {
				getSession().put(ConstantesSession.RESULTADOS_PAGINA, UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO_ESTADISTICAS);
			}

			getSession().put(ConstantesSession.CONSULTA_ESTADISTICAS, null);
			setListaConsultaEstadisticasUsuariosIP(listaConsultaEstadisticasUsuariosIP);
			addActionMessage(ConstantesEstadisticas.PASSWORD_CORRECTO);
			return ConstantesEstadisticas.LISTADO_USUARIOSIP;
		} else {
			setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_ERROR);
			addActionError(ConstantesEstadisticas.PASSWORD_INCORRECTO);
			return ConstantesEstadisticas.LISTADO_USUARIOSIP;
		}
	}

	public String comprobarPasswordEvolucionTasaExpediente() {
		String passwordPropiedades = gestorPropiedades.valorPropertie(ConstantesEstadisticas.PASSWORD_CAMPO);

		if (consultaEstadisticas != null && passwordPropiedades != null && passwordPropiedades.equals(consultaEstadisticas.getPassword())) {
			setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
			parametrosBusqueda = new HashMap<String, Object>();
			consultaEstadisticas = new ConsultaEstadisticasBean(true);
			// consultaEstadisticas.setFechaMatriculacion(utilesFecha.getFechaFracionadaActual());
			// mantenimientoCamposNavegar();

			if (null != resultadosPorPagina) {
				getSession().put(ConstantesSession.RESULTADOS_PAGINA, resultadosPorPagina);
			} else {
				getSession().put(ConstantesSession.RESULTADOS_PAGINA, UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO_ESTADISTICAS);
			}

			getSession().put(ConstantesSession.CONSULTA_ESTADISTICAS, null);
			setListaConsultaEstadisticasEvolucionTasa(listaConsultaEstadisticasEvolucionTasa);
			addActionMessage(ConstantesEstadisticas.PASSWORD_CORRECTO);
			return ConstantesEstadisticas.LISTADO_EVOLUCIONTASAEXP;
		} else {
			setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_ERROR);
			addActionError(ConstantesEstadisticas.PASSWORD_INCORRECTO);
			return ConstantesEstadisticas.LISTADO_EVOLUCIONTASAEXP;
		}
	}

	public String comprobarPasswordJustificantes() {
		String passwordPropiedades = gestorPropiedades.valorPropertie(ConstantesEstadisticas.PASSWORD_CAMPO);

		if (consultaEstadisticas != null && passwordPropiedades != null && passwordPropiedades.equals(consultaEstadisticas.getPassword())) {
			setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
			parametrosBusqueda = new HashMap<String, Object>();
			consultaEstadisticas = new ConsultaEstadisticasBean(true);
			consultaEstadisticas.setFechaMatriculacion(utilesFecha.getFechaFracionadaActual());

			if (null != resultadosPorPagina) {
				getSession().put(ConstantesSession.RESULTADOS_PAGINA, resultadosPorPagina);
			} else {
				getSession().put(ConstantesSession.RESULTADOS_PAGINA, UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO_ESTADISTICAS);
			}

			getSession().put(ConstantesSession.CONSULTA_ESTADISTICAS, null);
			setListaConsultaEstadisticasAgrupadas(listaConsultaEstadisticasAgrupadas);
			addActionMessage(ConstantesEstadisticas.PASSWORD_CORRECTO);
			return ConstantesEstadisticas.LISTADO_JUSTIFICANTES_NO_ULTIMADOS;
		} else {
			setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_ERROR);
			addActionError(ConstantesEstadisticas.PASSWORD_INCORRECTO);
			return ConstantesEstadisticas.LISTADO_JUSTIFICANTES_NO_ULTIMADOS;
		}
	}

	public String comprobarPasswordVehiculos() {
		String passwordPropiedades = gestorPropiedades.valorPropertie(ConstantesEstadisticas.PASSWORD_CAMPO);

		if (consultaEstadisticas != null && passwordPropiedades != null && passwordPropiedades.equals(consultaEstadisticas.getPassword())) {
			setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
			parametrosBusqueda = new HashMap<String, Object>();

			consultaEstadisticas = new ConsultaEstadisticasBean(true);
			// consultaEstadisticas.setFechaPrimeraMatriculacion(utilesFecha.getFechaFracionadaActual());
			consultaEstadisticas.setFechaPresentacion(utilesFecha.getFechaFracionadaActual());

			if (null != resultadosPorPagina) {
				getSession().put(ConstantesSession.RESULTADOS_PAGINA, resultadosPorPagina);
			} else {
				getSession().put(ConstantesSession.RESULTADOS_PAGINA, UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO_ESTADISTICAS);
			}

			getSession().put(ConstantesSession.CONSULTA_ESTADISTICAS, null);
			setListaConsultaEstadisticasVehiculos(listaConsultaEstadisticasVehiculos);
			addActionMessage(ConstantesEstadisticas.PASSWORD_CORRECTO);
			return ConstantesEstadisticas.LISTADO_VEHICULOS;
		} else {
			setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_ERROR);
			addActionError(ConstantesEstadisticas.PASSWORD_INCORRECTO);
			return ConstantesEstadisticas.LISTADO_VEHICULOS;
		}
	}

	public String comprobarPasswordMatriculas() {
		String passwordPropiedades = gestorPropiedades.valorPropertie(ConstantesEstadisticas.PASSWORD_CAMPO);

		if (consultaEstadisticas != null && passwordPropiedades != null && passwordPropiedades.equals(consultaEstadisticas.getPassword())) {
			setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
			parametrosBusqueda = new HashMap<String, Object>();
			consultaEstadisticas = new ConsultaEstadisticasBean(true);

			if (null != resultadosPorPagina) {
				getSession().put(ConstantesSession.RESULTADOS_PAGINA, resultadosPorPagina);
			} else {
				getSession().put(ConstantesSession.RESULTADOS_PAGINA, UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO_ESTADISTICAS);
			}

			getSession().put(ConstantesSession.CONSULTA_ESTADISTICAS, null);
			addActionMessage(ConstantesEstadisticas.PASSWORD_CORRECTO);
			return ConstantesEstadisticas.LISTADO_MATRICULAS;
		} else {
			setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_ERROR);
			addActionError(ConstantesEstadisticas.PASSWORD_INCORRECTO);
			return ConstantesEstadisticas.LISTADO_MATRICULAS;
		}
	}

	/**
	 * Método para actualizar los parámetros de la búsqueda. Proceso: -Actualizo los datos de la sesión. -Paso los parámetros de búsqueda.
	 */
	private void mantenimientoCamposNavegar() {
		if (resultadosPorPagina != null) {
			getSession().put("resultadosPorPagina", resultadosPorPagina);
		}

		ConsultaEstadisticasBean consultaEstadistica = (ConsultaEstadisticasBean) getSession().get(ConstantesSession.CONSULTA_ESTADISTICAS);
		if (consultaEstadistica != null) {
			setConsultaEstadistica(consultaEstadistica);
		}

		List<UsuarioLoginVO> listaConsultaEstadisticasUsuariosIP = (List<UsuarioLoginVO>) getSession().get("listaConsultaEstadisticasUsuariosIP");
		if (listaConsultaEstadisticasUsuariosIP != null) {
			setListaConsultaEstadisticasUsuariosIP(listaConsultaEstadisticasUsuariosIP);
		}

		List<EstadisticasAgrupadas> listaConsultaEstadisticasAgrupadas = (List<EstadisticasAgrupadas>) getSession().get("listaConsultaEstadisticasAgrupadas");
		if (listaConsultaEstadisticasAgrupadas != null) {
			setListaConsultaEstadisticasAgrupadas(listaConsultaEstadisticasAgrupadas);
		}

		List<EvolucionTasa> listaConsultaEstadisticasEvolucionTasa = (List<EvolucionTasa>) getSession().get("listaConsultaEstadisticasEvolucionTasa");
		if (listaConsultaEstadisticasEvolucionTasa != null) {
			setListaConsultaEstadisticasEvolucionTasa(listaConsultaEstadisticasEvolucionTasa);
		}

		String resultadosPagina = (String) getSession().get(ConstantesSession.RESULTADOS_PAGINA);
		if (null != resultadosPagina) {
			setResultadosPorPagina(resultadosPagina);
		}
	}

	private void mantenimientoCamposBuscar() {
		setResultadosPorPagina((String) getSession().get(ConstantesSession.RESULTADOS_PAGINA));
		actualizaParametros(consultaEstadisticas);

		getSession().put(ConstantesSession.RESULTADOS_PAGINA, getResultadosPorPagina());
		getSession().put(ConstantesSession.CONSULTA_ESTADISTICAS, consultaEstadisticas);
	}

	public void setConsultaEstadistica(ConsultaEstadisticasBean consultaEstadistica) {
		this.consultaEstadisticas = consultaEstadistica;
	}

	/**
	 * Método que actualiza los parámetros de búsqueda a partir de un bean de búsqueda que le pasamos
	 */
	private void actualizaParametros(ConsultaEstadisticasBean beanActualiza) {
		parametrosBusqueda = new HashMap<>();

		if (null != beanActualiza) {

			String tipoTramite = beanActualiza.getTipoTramite();
			if (tipoTramite != null && tipoTramite.equals(ConstantesEstadisticas.SIN_AGRUPACION)) {
				tipoTramite = null;
			}

			String numColegiado = beanActualiza.getNumColegiado();
			if (numColegiado != null && (numColegiado.equals(ConstantesEstadisticas.SIN_AGRUPACION) || numColegiado.equals(""))) {
				numColegiado = null;
			}

			String provincia = null;
			if (beanActualiza.getProvincia() != null) {
				provincia = beanActualiza.getProvincia().getIdProvincia();
				if (provincia != null && provincia.equals(ConstantesEstadisticas.SIN_AGRUPACION)) {
					provincia = null;
				}
			}

			String jefatura = null;
			if (beanActualiza.getJefatura() != null) {
				jefatura = beanActualiza.getJefatura().getJefaturaProvincial();
				if (jefatura != null && jefatura.equals(ConstantesEstadisticas.SIN_AGRUPACION)) {
					jefatura = null;
				}
			}

			List<String> estadoMultiple = null;
			if (beanActualiza.getEstadoMultiple() != null) {
				estadoMultiple = new ArrayList<>();
				for (String seleccionado : beanActualiza.getEstadoMultiple()) {
					estadoMultiple.add(seleccionado);
				}
			}

			String tipoVehiculo = null;
			if (beanActualiza.getTipoVehiculoBean() != null) {
				tipoVehiculo = beanActualiza.getTipoVehiculoBean().getTipoVehiculo();
				if (tipoVehiculo != null && tipoVehiculo.equals(ConstantesEstadisticas.SIN_AGRUPACION)) {
					tipoVehiculo = null;
				}
			}

			BigDecimal codigoMarca = null;
			if (beanActualiza.getMarcaBean() != null) {
				codigoMarca = beanActualiza.getMarcaBean().getCodigoMarca();
				if (codigoMarca != null && codigoMarca.equals(ConstantesEstadisticas.SIN_AGRUPACION)) {
					codigoMarca = null;
				}
			}

			BigDecimal agrupacion = null;
			if (beanActualiza.getAgrupacion() != null) {
				agrupacion = beanActualiza.getAgrupacion();
				if (agrupacion != null && agrupacion.equals(ConstantesEstadisticas.SIN_AGRUPACION)) {
					agrupacion = null;
				}
			}

			parametrosBusqueda.put(ConstantesSession.TIPO_TRAMITE_ESTADISTICAS, tipoTramite);
			parametrosBusqueda.put(ConstantesSession.NUM_COLEGIADO_ESTADISTICAS, numColegiado);
			parametrosBusqueda.put(ConstantesSession.PROVINCIA_ESTADISTICAS, provincia);
			parametrosBusqueda.put(ConstantesSession.JEFATURA_ESTADISTICAS, jefatura);
			parametrosBusqueda.put(ConstantesSession.TIPO_VEHICULO_ESTADISTICAS, tipoVehiculo);
			parametrosBusqueda.put(ConstantesSession.MARCA_ESTADISTICAS, codigoMarca);
			parametrosBusqueda.put(ConstantesSession.FECHA_MATR_ESTADISTICAS, beanActualiza.getFechaMatriculacion());

			parametrosBusqueda.put(ConstantesSession.AGRUPACION_ESTADISTICAS, agrupacion);

			parametrosBusqueda.put(ConstantesSession.ESTADO_MULTIPLE, estadoMultiple);
		}
	}

	// @Override
	// public String execute() throws Exception {
	// log.debug("execute");
	// try {
	// // mantenerAcciones();
	// } catch (Throwable e) {
	//
	// addActionError(e.toString());
	// }
	// try {
	// // mantenerAcciones();
	// } catch (Throwable e) {
	//
	// addActionError(e.toString());
	// }
	// return ConstantesEstadisticas.LISTADO_GENERAL;
	// }

	public String generarListadoGeneral() {
		log.debug("inicio generarListadoGeneral");
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);

		if (("".equals(consultaEstadisticas.getFechaMatriculacion().getDiaInicio())) || ("".equals(consultaEstadisticas.getFechaMatriculacion().getDiaFin()))
				|| ("".equals(consultaEstadisticas.getFechaMatriculacion().getMesInicio())) || ("".equals(consultaEstadisticas.getFechaMatriculacion().getMesFin()))
				|| ("".equals(consultaEstadisticas.getFechaMatriculacion().getAnioInicio())) || ("".equals(consultaEstadisticas.getFechaMatriculacion().getAnioFin()))) {
			addActionError("Falta por establecer algún Parametro de la Fecha");
			return ConstantesEstadisticas.LISTADO_GENERAL;
		}

		String campoErroneo = "";
		if ((!"".equals(consultaEstadisticas.getNumColegiado()) && (consultaEstadisticas.getAgrupacion().intValue()) == 1)) {
			campoErroneo = "Número de colegiado";
			addActionError(campoErroneo + ConstantesEstadisticas.MENSAJE_ERROR_AGRUPAR_FILTRO);
			return ConstantesEstadisticas.LISTADO_GENERAL;
		} else if ((!ConstantesEstadisticas.SIN_AGRUPACION.equals(consultaEstadisticas.getProvincia().getIdProvincia()) && (consultaEstadisticas.getAgrupacion().intValue()) == 2)) {
			campoErroneo = "Provincia";
			addActionError(campoErroneo + ConstantesEstadisticas.MENSAJE_ERROR_AGRUPAR_FILTRO);
			return ConstantesEstadisticas.LISTADO_GENERAL;
		} else if ((!"".equals(consultaEstadisticas.getJefatura().getJefaturaProvincial()) && !ConstantesEstadisticas.SIN_AGRUPACION.equals(consultaEstadisticas.getJefatura().getJefaturaProvincial()) && (consultaEstadisticas
				.getAgrupacion().intValue()) == 3)) {
			campoErroneo = "Jefatura";
			addActionError(campoErroneo + ConstantesEstadisticas.MENSAJE_ERROR_AGRUPAR_FILTRO);
			return ConstantesEstadisticas.LISTADO_GENERAL;

			// Se elimina esta validación ya que ahora es posible generar un informe mediante la selección
			// múltiple en el combo Estado del trámite cuando en Agrupación se selecciona el valor Estado

			/*
			 * } else if ((consultaEstadisticas.getEstadoMultiple().length > 0) && (consultaEstadisticas .getAgrupacion().intValue()) == 11) { campoErroneo = "Estado"; addActionError(campoErroneo + ConstantesEstadisticas.MENSAJE_ERROR_AGRUPAR_FILTRO); return ConstantesEstadisticas.LISTADO_GENERAL;
			 */
		} else if ((!ConstantesEstadisticas.SIN_AGRUPACION.equals(consultaEstadisticas.getTipoTramite()) && (consultaEstadisticas.getAgrupacion().intValue()) == 10)) {
			campoErroneo = "Tipo de trámite";
			addActionError(campoErroneo + ConstantesEstadisticas.MENSAJE_ERROR_AGRUPAR_FILTRO);
			return ConstantesEstadisticas.LISTADO_GENERAL;
		} else if ((!ConstantesEstadisticas.SIN_AGRUPACION.equals(consultaEstadisticas.getTipoVehiculoBean().getTipoVehiculo()) && (consultaEstadisticas.getAgrupacion().intValue()) == 13)) {
			campoErroneo = "Tipo de vehículo";
			addActionError(campoErroneo + ConstantesEstadisticas.MENSAJE_ERROR_AGRUPAR_FILTRO);
			return ConstantesEstadisticas.LISTADO_GENERAL;
		} else if ((consultaEstadisticas.getMarcaBean().getCodigoMarca() != null && (consultaEstadisticas.getAgrupacion().intValue() == 12))) {
			campoErroneo = "Marca";
			addActionError(campoErroneo + ConstantesEstadisticas.MENSAJE_ERROR_AGRUPAR_FILTRO);
			return ConstantesEstadisticas.LISTADO_GENERAL;
		} else if ((!ConstantesEstadisticas.SIN_AGRUPACION.equals(consultaEstadisticas.getIdTipoCreacion().toString()) && (consultaEstadisticas.getAgrupacion().intValue()) == 14)) {
			campoErroneo = "Tipo de Creacion";
			addActionError(campoErroneo + ConstantesEstadisticas.MENSAJE_ERROR_AGRUPAR_FILTRO);
			return ConstantesEstadisticas.LISTADO_GENERAL;
		}

		listaConsultaEstadisticasAgrupadas = new ArrayList<EstadisticasAgrupadas>();
		consultaEstadisticas.setTextoAgrupacion(Agrupacion.convertirTexto(consultaEstadisticas.getAgrupacion().toString()));

		mantenimientoCamposBuscar();

		if ((BigDecimal.ONE.negate().compareTo((consultaEstadisticas.getAgrupacion()))) == 0) {

			listaConsultaEstadisticasTramiteTraf = estadisticasDAO.buscaTramites(consultaEstadisticas);

			Iterator iter = listaConsultaEstadisticasTramiteTraf.iterator();
			Object temp = (Object) iter.next();
			addActionMessage(" El Numero Total de Tramites es de : " + Integer.parseInt(temp.toString()));
		} else {
			listaConsultaEstadisticasTramiteTraf = estadisticasDAO.buscaTramitesPorAgrupacion(consultaEstadisticas);
			Iterator iter = listaConsultaEstadisticasTramiteTraf.iterator();

			while (iter.hasNext()) {
				EstadisticasAgrupadas estadisticasAgrupadas = new EstadisticasAgrupadas();

				Object[] temp = (Object[]) iter.next();
				ConvierteCodigoALiteral convierteCodigoALiteral = new ConvierteCodigoALiteral();
				if (temp[0] != null) {
					estadisticasAgrupadas.setCampo(convierteCodigoALiteral.getLiteral(temp[0].toString(), consultaEstadisticas.getAgrupacion()));
				} else {
					estadisticasAgrupadas.setCampo("Vacio");
				}

				if (temp[1] != null) {
					estadisticasAgrupadas.setNumRegistros(Integer.parseInt(temp[1].toString()));
				}

				listaConsultaEstadisticasAgrupadas.add(estadisticasAgrupadas);
				getSession().put(ConstantesSession.CONSULTA_ESTADISTICAS, consultaEstadisticas);
				setListaConsultaEstadisticasAgrupadas(listaConsultaEstadisticasAgrupadas);
			}
		}

		setListaConsultaEstadisticasAgrupadas(listaConsultaEstadisticasAgrupadas);
		setListaConsultaEstadisticasTramiteTrafico(listaConsultaEstadisticasTramiteTraf);
		setConsultaEstadisticas(consultaEstadisticas);

		try {
			getSession().put("totalRows", estadisticasDAO.getNumberOfFiles(consultaEstadisticas));
		} catch (Exception e) {
			log.error("Error al obtener el numero de filas en estadisticas", e);
		}

		getSession().put("listaConsultaEstadisticasAgrupadas", listaConsultaEstadisticasAgrupadas);
		getSession().put("totalList", listaConsultaEstadisticasAgrupadas.size());
		getSession().put("listaConsultaEstadisticasTramiteTraf", listaConsultaEstadisticasTramiteTraf);
		getSession().put(ConstantesSession.CONSULTA_ESTADISTICAS, consultaEstadisticas);

		log.debug("fin generarListadoGeneral");
		return ConstantesEstadisticas.LISTADO_GENERAL;
	}

	public String generarListadoJustificantes() {
		log.debug("inicio generarListadoJustificantes");
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);

		if (("".equals(consultaEstadisticas.getFechaMatriculacion().getDiaInicio())) || ("".equals(consultaEstadisticas.getFechaMatriculacion().getDiaFin()))
				|| ("".equals(consultaEstadisticas.getFechaMatriculacion().getMesInicio())) || ("".equals(consultaEstadisticas.getFechaMatriculacion().getMesFin()))
				|| ("".equals(consultaEstadisticas.getFechaMatriculacion().getAnioInicio())) || ("".equals(consultaEstadisticas.getFechaMatriculacion().getAnioFin()))) {
			addActionError("Falta por establecer algún Parametro de la Fecha");
			return ConstantesEstadisticas.LISTADO_JUSTIFICANTES_NO_ULTIMADOS;
		}

		if ("".equals(consultaEstadisticas.getNumColegiado())) {
			addActionError("El Numero de Colegiado es Obligatorio");
			return ConstantesEstadisticas.LISTADO_JUSTIFICANTES_NO_ULTIMADOS;
		}

		listaConsultaEstadisticasAgrupadas = new ArrayList<EstadisticasAgrupadas>();
		mantenimientoCamposBuscar();
		String totalJustificantes = estadisticasDAO.buscaTotalJustificantes(consultaEstadisticas);
		int numJustificantes = Integer.valueOf(totalJustificantes);
		addActionMessage(" El numero total de Justificantes para el Colegiado " + consultaEstadisticas.getNumColegiado() + " en el periodo seleccionado es de " + totalJustificantes);

		listaConsultaEstadisticasJustificantes = estadisticasDAO.buscaJustificantesPorAgrupacion(consultaEstadisticas);
		int numJustificantesMatr = 0;

		Iterator iter = listaConsultaEstadisticasJustificantes.iterator();
		while (iter.hasNext()) {
			EstadisticasAgrupadas estadisticasAgrupadas = new EstadisticasAgrupadas();

			Object[] temp = (Object[]) iter.next();
			if (temp[0] != null) {
				estadisticasAgrupadas.setCampo(temp[0].toString());
			}

			if (temp[1] != null) {
				estadisticasAgrupadas.setNumRegistros(Integer.parseInt(temp[1].toString()));
				numJustificantesMatr = numJustificantesMatr + Integer.valueOf(temp[1].toString());
			}
			listaConsultaEstadisticasAgrupadas.add(estadisticasAgrupadas);
		}

		if (numJustificantes != numJustificantesMatr) {
			EstadisticasAgrupadas estadisticasAgrupadas = new EstadisticasAgrupadas();
			estadisticasAgrupadas.setCampo("Sin Matricula");
			estadisticasAgrupadas.setNumRegistros(numJustificantes - numJustificantesMatr);
			listaConsultaEstadisticasAgrupadas.add(estadisticasAgrupadas);
		}

		getSession().put(ConstantesSession.CONSULTA_ESTADISTICAS, consultaEstadisticas);
		setListaConsultaEstadisticasAgrupadas(listaConsultaEstadisticasAgrupadas);
		setListaConsultaEstadisticasJustificantes(listaConsultaEstadisticasJustificantes);
		setConsultaEstadisticas(consultaEstadisticas);

		getSession().put("totalRows", totalJustificantes);

		getSession().put("listaConsultaEstadisticasAgrupadas", listaConsultaEstadisticasAgrupadas);
		getSession().put("totalList", listaConsultaEstadisticasAgrupadas.size());
		getSession().put("listaConsultaEstadisticasJustificantes", listaConsultaEstadisticasJustificantes);
		getSession().put(ConstantesSession.CONSULTA_ESTADISTICAS, consultaEstadisticas);

		log.debug("fin generarListadoJustificantes");
		return ConstantesEstadisticas.LISTADO_JUSTIFICANTES_NO_ULTIMADOS;
	}

	public String generarListadoUsuariosIP() {
		log.debug("inicio generarListadoUsuariosIP");
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);

		if (("".equals(consultaEstadisticas.getFechaMatriculacion().getDiaInicio())) || ("".equals(consultaEstadisticas.getFechaMatriculacion().getDiaFin()))
				|| ("".equals(consultaEstadisticas.getFechaMatriculacion().getMesInicio())) || ("".equals(consultaEstadisticas.getFechaMatriculacion().getMesFin()))
				|| ("".equals(consultaEstadisticas.getFechaMatriculacion().getAnioInicio())) || ("".equals(consultaEstadisticas.getFechaMatriculacion().getAnioFin()))) {
			addActionError("Falta por establecer algún Parametro de la Fecha");
			return ConstantesEstadisticas.LISTADO_USUARIOSIP;
		}

		mantenimientoCamposBuscar();
		List<Object[]> ipMasRepetida = servicioSesion.buscaIPMasRepetida(consultaEstadisticas.getNumColegiado(), consultaEstadisticas.getFechaMatriculacion());

		Iterator<Object[]> iter = ipMasRepetida.iterator();
		while (iter.hasNext()) {
			Object[] temp = iter.next();
			addActionMessage(" La Ip Mas repetida es la " + (String) temp[1] + " con un Total de " + (Integer) temp[0] + " veces");
		}

		listaConsultaEstadisticasUsuariosIP = servicioSesion.buscaUsuarioLogin(consultaEstadisticas.getNumColegiado(), consultaEstadisticas.getFrontal(), consultaEstadisticas.getFechaMatriculacion());
		setListaConsultaEstadisticasUsuariosIP(listaConsultaEstadisticasUsuariosIP);
		getSession().put("listaConsultaEstadisticasUsuariosIP", listaConsultaEstadisticasUsuariosIP);

		getSession().put(ConstantesSession.CONSULTA_ESTADISTICAS, consultaEstadisticas);

		log.debug("fin generarListadoUsuariosIP");
		return ConstantesEstadisticas.LISTADO_USUARIOSIP;
	}

	public String generarListadoEvolucioTasaExp() {
		log.debug("inicio generarListadoEvolucioTasaExp");
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
		mantenimientoCamposBuscar();

		listaConsultaEstadisticasEvolucionTasa = estadisticasDAO.buscaEvolucionTasa(consultaEstadisticas.getNumExpediente(), consultaEstadisticas.getCodigoTasa());
		setListaConsultaEstadisticasEvolucionTasa(listaConsultaEstadisticasEvolucionTasa);
		getSession().put("listaConsultaEstadisticasEvolucionTasa", listaConsultaEstadisticasEvolucionTasa);

		getSession().put(ConstantesSession.CONSULTA_ESTADISTICAS, consultaEstadisticas);
		log.debug("fin generarListadoEvolucioTasaExp");

		return ConstantesEstadisticas.LISTADO_EVOLUCIONTASAEXP;
	}

	public String generarListadoUsuariosActivosIP() {
		log.debug("inicio generarListadoUsuariosActivosIP");
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
		mantenimientoCamposBuscar();

		listaConsultaEstadisticasUsuariosIP = servicioSesion.buscaUsuariosActivosLogin(consultaEstadisticas.getNumColegiado(), consultaEstadisticas.getFrontal(), consultaEstadisticas.getListIdUsuarios(), consultaEstadisticas.getOrdenBusqueda());
		setListaConsultaEstadisticasUsuariosIP(listaConsultaEstadisticasUsuariosIP);
		getSession().put("listaConsultaEstadisticasUsuariosIP", listaConsultaEstadisticasUsuariosIP);

		getSession().put(ConstantesSession.CONSULTA_ESTADISTICAS, consultaEstadisticas);
		log.debug("fin generarListadoUsuariosActivosIP");

		return ConstantesEstadisticas.LISTADO_USUARIOSIP;
	}

	// David Sierra: Metodo que muestra una informacion tipo ActionMessage indicando cuantos
	// usuarios hay en cada frontal y el total de usuarios que hay en todos los frontales .
	public String generarListadoUsuariosActivosAgrupadosIP() {

		log.debug("inicio generarListadoUsuariosActivosIPAgrupados");

		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);

		getSession().put(ConstantesSession.CONSULTA_ESTADISTICAS, consultaEstadisticas);
		// Muestra el mensaje del ActionMessage con el frontal activo y sus usuarios.
		for (int x = 0; x < getListaFrontalesUsuarios().size(); x++) {
			String message = "En el frontal " + getListaFrontalesUsuarios().get(x)[0].toString() + " hay " + getListaFrontalesUsuarios().get(x)[1].toString() + " usuarios";
			addActionMessage(message);
		}
		// Se muestra el total de usuarios que hay en todos los frontales
		for (int i = 0; i < getListaUsuariosTotalesFrontales().size(); i++) {
			String message = "En total, en todos los frontales hay " + getListaUsuariosTotalesFrontales().get(i) + " usuarios";
			addActionMessage(message);
		}

		log.debug("fin generarListadoUsuariosActivosIPAgrupados");

		return ConstantesEstadisticas.LISTADO_USUARIOSIP;
	}

	// Metodo que muestra una informacion tipo ActionMessage indicando cuantos usuarios hay conectados mas de una vez.
	public String generarListadoUsuariosRepetidos() {

		log.debug("inicio generarListadoUsuariosRepetidos");

		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);

		getSession().put(ConstantesSession.CONSULTA_ESTADISTICAS, consultaEstadisticas);

		// Muestra el mensaje del ActionMessage con los usuarios
		// conectados más de una vez.
		for (int x = 0; x < getListasUsuariosRepetidos().size(); x++) {

			if (((Integer) getListasUsuariosRepetidos().get(x)[3]).intValue() > 1) {
				String message = "El usuario con numColegiado " + getListasUsuariosRepetidos().get(x)[0].toString() + " e idUsuario " 
				+ getListasUsuariosRepetidos().get(x)[1].toString() + " ("+ getListasUsuariosRepetidos().get(x)[2].toString() + ")" + " está conectado " 
				+ getListasUsuariosRepetidos().get(x)[3].toString() + " veces";
				addActionMessage(message);
			}
		}

		log.debug("fin generarListadoUsuariosRepetidos");

		return ConstantesEstadisticas.LISTADO_USUARIOSIP;
	}

	// DRC@26-04-2013 Incidencia Matis: 4250
	public String generarListadoVehiculos() {
		log.debug("inicio generarListadoVehiculos");
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
		String limitacionNumRegistrosFechaCalculada = "";

		if (("".equals(consultaEstadisticas.getFechaPresentacion().getDiaInicio())) || ("".equals(consultaEstadisticas.getFechaPresentacion().getDiaFin()))
				|| ("".equals(consultaEstadisticas.getFechaPresentacion().getMesInicio())) || ("".equals(consultaEstadisticas.getFechaPresentacion().getMesFin()))
				|| ("".equals(consultaEstadisticas.getFechaPresentacion().getAnioInicio())) || ("".equals(consultaEstadisticas.getFechaPresentacion().getAnioFin()))) {
			addActionError("Falta por establecer algún Parametro de la Fecha de Presentacion");
			return ConstantesEstadisticas.LISTADO_VEHICULOS;
		}

		listaConsultaEstadisticasAgrupadas = new ArrayList<EstadisticasAgrupadas>();
		listaConsultaEstadisticasVehiculos = new ArrayList<Vehiculo>();
		consultaEstadisticas.setTextoAgrupacion(AgrupacionVehiculos.convertirTexto(consultaEstadisticas.getAgrupacionVehiculos().toString()));
		mantenimientoCamposBuscar();

		int totalRows = 0;
		if ((BigDecimal.ONE.negate().compareTo((consultaEstadisticas.getAgrupacionVehiculos()))) == 0) {
			// DRC@25-04-2013 Se crea un hashmap donde se guardan los registros
			// donde la fecha de primera matriculacion es distina de nula.
			HashMap mapConFecha = new HashMap();
			int fechaConMatricula = 0;
			Object[] temp = null;

			// DRC@26-04-2013 Recupera todos los registros
			listaConsultaEstadisticasVehiculos = estadisticasDAO.buscaVehiculosTipoTramite(consultaEstadisticas, Boolean.FALSE, ConstantesEstadisticas.SIN_FILTRO);
			// DRC@26-04-2013 Recupera todos los registros con Fecha de Primera
			// Matriculacion
			listaConsultaEstadisticasVehiculosConFechaMatriculacion = estadisticasDAO.buscaVehiculosTipoTramite(consultaEstadisticas, Boolean.FALSE, ConstantesEstadisticas.FILTRO_IS_NOT_NULL);
			Iterator iterVehiculosConFecha = listaConsultaEstadisticasVehiculosConFechaMatriculacion.iterator();
			Iterator iterVehiculos = listaConsultaEstadisticasVehiculos.iterator();

			// DRC@26-04-2013 Recupera todos los registros
			while (iterVehiculos.hasNext()) {
				EstadisticasAgrupadas estadisticasAgrupadas = new EstadisticasAgrupadas();
				temp = (Object[]) iterVehiculos.next();

				if (temp[1] != null) {
					estadisticasAgrupadas.setNumRegistros(Integer.parseInt(temp[1].toString()));
				} else {
					estadisticasAgrupadas.setNumRegistros(0);
				}
				totalRows += estadisticasAgrupadas.getNumRegistros();
			}

			// DRC@26-04-2013 Recupera todos los registros con Fecha de Primera
			// Matriculacion
			while (iterVehiculosConFecha.hasNext()) {
				EstadisticasAgrupadas estadisticasAgrupadas = new EstadisticasAgrupadas();
				Object[] tempConFecha = (Object[]) iterVehiculosConFecha.next();

				if (tempConFecha[1] != null) {
					estadisticasAgrupadas.setNumRegistros(Integer.parseInt(tempConFecha[1].toString()));
				} else {
					estadisticasAgrupadas.setNumRegistros(0);
				}
				fechaConMatricula += estadisticasAgrupadas.getNumRegistros();
			}

			// DRC@26-04-2013 Mensajes a mostrar en la JSP
			consultaEstadisticas.setTextoAgrupacion("");
			addActionMessage("Nº de registros sin fecha de primera matriculacion: " + (totalRows - fechaConMatricula));
			addActionMessage("Nº de registros con fecha de primera matriculacion: " + fechaConMatricula);
			addActionMessage("Total de registros: " + totalRows);
		} else {
			// DRC@26-04-2013 Recupera todos los registros
			listaConsultaEstadisticasVehiculos = estadisticasDAO.buscaVehiculosTipoTramite(consultaEstadisticas, Boolean.TRUE, ConstantesEstadisticas.SIN_FILTRO);
			// DRC@26-04-2013 Recupera todos los registros con Fecha de Primera
			// Matriculacion
			listaConsultaEstadisticasVehiculosConFechaMatriculacion = estadisticasDAO.buscaVehiculosTipoTramite(consultaEstadisticas, Boolean.TRUE, ConstantesEstadisticas.FILTRO_IS_NOT_NULL);
			mapFechaCalculada = new HashMap();
			// DRC@26-04-2013 Si el agrupamiento es igual a Año o Mes/Año
			if (ConstantesEstadisticas.AGRUPACION_ANYO.equalsIgnoreCase(consultaEstadisticas.getAgrupacionVehiculos().toString())
					|| ConstantesEstadisticas.AGRUPACION_MES_ANYO.equalsIgnoreCase(consultaEstadisticas.getAgrupacionVehiculos().toString())) {
				String filtro = ConstantesEstadisticas.INFO_MES_ANYO;
				String sMatricula = "";
				String fechaCalculada = "";
				int contFechaCalculada = 0;

				// DRC@26-04-2013 Recupera todos los registros sin Fecha de
				// Primera Matriculacion
				listaConsultaEstadisticasVehiculosSinFechaMatriculacion = estadisticasDAO.buscaVehiculosTipoTramite(consultaEstadisticas, Boolean.TRUE, ConstantesEstadisticas.FILTRO_IS_NULL);
				Iterator iterSinFecha = listaConsultaEstadisticasVehiculosSinFechaMatriculacion.iterator();

				// DRC@26-04-2013 Comprueba la limitacion de numero de registros
				// a calcular su fecha de primera matriculacion a traves del
				// rango de matriculas
				try {
					limitacionNumRegistrosFechaCalculada = gestorPropiedades.valorPropertie(ConstantesEstadisticas.LIMITACION_NUM_REGISTROS_CAMPO);
					if (limitacionNumRegistrosFechaCalculada == null) {
						log.debug("Error al recuperar properties de estadísticas.");
					} else if (Integer.parseInt(limitacionNumRegistrosFechaCalculada) < listaConsultaEstadisticasVehiculosSinFechaMatriculacion.size()) {
						addActionError(ConstantesEstadisticas.MENSAJE_SUPERAR_LIMITE_NUM_REGISTROS + " " + limitacionNumRegistrosFechaCalculada + ".");
						addActionError(ConstantesEstadisticas.MENSAJE_SUPERAR_LIMITE_NUM_REGISTROS2);
						return ConstantesEstadisticas.LISTADO_VEHICULOS;
					}
				} catch (NumberFormatException e) {
					log.debug("Error al recuperar properties de estadísticas. Error: " + e);
				}

				if (ConstantesEstadisticas.AGRUPACION_ANYO.equalsIgnoreCase(consultaEstadisticas.getAgrupacionVehiculos().toString()))
					filtro = ConstantesEstadisticas.INFO_ANYO;

				// listaConsultaEstadisticasVehiculosSinFechaMatriculacion =
				// null;
				while (iterSinFecha.hasNext()) {
					sMatricula = (String) iterSinFecha.next();
					if (sMatricula.length() == 7) {
						consultaEstadisticas.setNumMatricula(sMatricula.substring(0, 4));
						consultaEstadisticas.setLetraMatricula(sMatricula.substring(4, sMatricula.length()));
						fechaCalculada = estadisticasDAO.buscaRangoFechaMatriculacion(consultaEstadisticas, filtro);
						if (fechaCalculada == null)
							fechaCalculada = ConstantesEstadisticas.RANGO_FECHA_MATRICULA_SIN_DETERMINAR_0000;
					} else {
						fechaCalculada = ConstantesEstadisticas.RANGO_FECHA_MATRICULA_SIN_DETERMINAR_0000;
					}
					Object fecha = mapFechaCalculada.get(fechaCalculada);
					if (fecha != null)
						contFechaCalculada = Integer.parseInt(fecha.toString()) + 1;
					else
						contFechaCalculada = 1;
					mapFechaCalculada.put(fechaCalculada, contFechaCalculada);
				}
			}
			// DRC@26-04-2013 Ordena los registros calculados, de mayor a menor
			Map mapOrdenadoFechaCalculada = ordenDescendente(mapFechaCalculada);

			if (ConstantesEstadisticas.AGRUPACION_MES_ANYO.equals(consultaEstadisticas.getAgrupacionVehiculos().toString())) {
				Collections.sort(listaConsultaEstadisticasVehiculos, new Comparator<Object>() {
					@Override
					// MES AÑO
					public int compare(Object arg0, Object arg1) {
						try {
							String a = ((Object[]) arg0)[0].toString().substring(3, ((Object[]) arg0)[0].toString().length());
							String b = ((Object[]) arg1)[0].toString().substring(3, ((Object[]) arg1)[0].toString().length());
							return a.compareTo(b);
						} catch (Exception e) {
							log.error("Excepcion al realizar el orden de la lista: " + e);
						}
						return 0;
					}
				});

				Collections.sort(listaConsultaEstadisticasVehiculosConFechaMatriculacion, new Comparator<Object>() {
					@Override
					// MES AÑO
					public int compare(Object arg0, Object arg1) {
						try {
							String a = ((Object[]) arg0)[0].toString().substring(3, ((Object[]) arg0)[0].toString().length());
							String b = ((Object[]) arg1)[0].toString().substring(3, ((Object[]) arg1)[0].toString().length());
							return a.compareTo(b);
						} catch (Exception e) {
							log.error("Excepcion al realizar el orden de la lista: " + e);
						}
						return 0;
					}
				});

			} else {
				Collections.sort(listaConsultaEstadisticasVehiculos, new Comparator<Object>() {

					@Override
					// AÑO ESTADO TIPO TRAMITE
					public int compare(Object arg0, Object arg1) {
						try {
							String a = ((Object[]) arg0)[0].toString();
							String b = (((Object[]) arg1)[0].toString());
							if (Integer.valueOf(a) > Integer.valueOf(b))
								return -1;
							else if (Integer.valueOf(a) < Integer.valueOf(b))
								return 1;
							else
								return 0;
						} catch (Exception e) {
							log.error("Excepcion al realizar el orden de la lista: " + e);
						}
						return 0;
					}
				});
			}

			// DRC@25/04/2013 Se crea un hashmap donde se guardan los registros
			// donde la fecha de primera matriculacion es distina de nula.
			HashMap mapConFecha = new HashMap();
			int matriculaFecha = 0;
			int matriculaFechaCalculada = 0;
			String fecha = null;
			Iterator iterConFecha = listaConsultaEstadisticasVehiculosConFechaMatriculacion.iterator();
			Iterator iter = listaConsultaEstadisticasVehiculos.iterator();

			// DRC@25-04-2013 Para el listado de Vehiculos Con fecha de primera
			// matriculacion al hashmap
			while (iterConFecha.hasNext()) {
				Object[] tempConFecha = (Object[]) iterConFecha.next();
				mapConFecha.put(tempConFecha[0].toString(), tempConFecha[1].toString());
			}

			while (iter.hasNext()) {
				EstadisticasAgrupadas estadisticasAgrupadas = new EstadisticasAgrupadas();
				Object[] temp = (Object[]) iter.next();

				if (ConstantesEstadisticas.AGRUPACION_ESTADO.equals(consultaEstadisticas.getAgrupacionVehiculos().toString()))
					fecha = String.valueOf(mapConFecha.get(temp[0].toString()));
				else
					fecha = String.valueOf(mapConFecha.get(temp[0]));

				if (fecha != null && !fecha.equalsIgnoreCase("null"))
					matriculaFecha = Integer.parseInt(fecha);
				else
					matriculaFecha = 0;
				if (mapOrdenadoFechaCalculada.size() > 0) {
					fecha = String.valueOf(mapOrdenadoFechaCalculada.get(temp[0]));
					if (fecha != null && !fecha.equalsIgnoreCase("null")) {
						matriculaFechaCalculada = Integer.parseInt(fecha);
						mapOrdenadoFechaCalculada.remove(temp[0].toString());
					} else {
						matriculaFechaCalculada = 0;
					}
				}

				// DRC@26-04-2013 Si matriculaFecha y matriculaÇFechaCalculada
				// es igual a 0, quiere decir que esos registros no hay que
				// mostrarlos en este momento
				// porque se mostraran posteriormene con el
				// mapOrdenadoFechaCalculada
				if ((matriculaFecha != 0 || matriculaFechaCalculada != 0)) {
					if (temp[0] != null) {
						estadisticasAgrupadas.setCampo(getConvertirResultados(temp[0].toString(), consultaEstadisticas.getAgrupacionVehiculos().toString()));
					} else {
						estadisticasAgrupadas.setCampo("Sin Fecha de Primera Matriculacion");
					}

					if (temp[1] != null) {
						if (ConstantesEstadisticas.AGRUPACION_ANYO.equals(consultaEstadisticas.getAgrupacionVehiculos().toString())
								|| ConstantesEstadisticas.AGRUPACION_MES_ANYO.equals(consultaEstadisticas.getAgrupacionVehiculos().toString()))
							estadisticasAgrupadas.setNumRegistros(matriculaFecha + matriculaFechaCalculada);
						else
							estadisticasAgrupadas.setNumRegistros(Integer.parseInt(temp[1].toString()));
					} else {
						estadisticasAgrupadas.setNumRegistros(0);
					}

					if (temp[0] != null && temp[1] != null)
						if (ConstantesEstadisticas.AGRUPACION_ANYO.equals(consultaEstadisticas.getAgrupacionVehiculos().toString())
								|| ConstantesEstadisticas.AGRUPACION_MES_ANYO.equals(consultaEstadisticas.getAgrupacionVehiculos().toString()))
							estadisticasAgrupadas.setFechaMatriculacion(matriculaFecha + " / " + matriculaFechaCalculada);
						else
							estadisticasAgrupadas.setFechaMatriculacion(matriculaFecha + " / " + (Integer.parseInt(temp[1].toString()) - matriculaFecha));

					if (ConstantesEstadisticas.AGRUPACION_ANYO.equals(consultaEstadisticas.getAgrupacionVehiculos().toString())
							|| ConstantesEstadisticas.AGRUPACION_MES_ANYO.equals(consultaEstadisticas.getAgrupacionVehiculos().toString()))
						totalRows += matriculaFecha + matriculaFechaCalculada;
					else
						totalRows += estadisticasAgrupadas.getNumRegistros();
					listaConsultaEstadisticasAgrupadas.add(estadisticasAgrupadas);
					getSession().put(ConstantesSession.CONSULTA_ESTADISTICAS, consultaEstadisticas);
					setListaConsultaEstadisticasAgrupadas(listaConsultaEstadisticasAgrupadas);
				}
			}

			// DRC@26-04-2013 Calcula las fechas que no se han podido recoger
			// con el anterior iteratos. Todas estas fechas son calculadas,
			// por lo que la columna de fecha de 1 matricula siempre sera 0.
			// Iterator iterFechaCalculada =
			// mapFechaCalculada.keySet().iterator();
			Iterator iterFechaCalculada = mapOrdenadoFechaCalculada.keySet().iterator();

			matriculaFecha = 0;
			while (iterFechaCalculada.hasNext()) {
				EstadisticasAgrupadas estadisticasAgrupadas = new EstadisticasAgrupadas();
				String anyoFechaCalculada = (String) iterFechaCalculada.next();
				int numRegistrosFechaCalculada = Integer.parseInt((String) mapOrdenadoFechaCalculada.get(anyoFechaCalculada).toString());

				if (anyoFechaCalculada != null) {
					if (anyoFechaCalculada.equalsIgnoreCase(ConstantesEstadisticas.RANGO_FECHA_MATRICULA_SIN_DETERMINAR_0000))
						anyoFechaCalculada = ConstantesEstadisticas.RANGO_FECHA_MATRICULA_SIN_DETERMINAR;
					estadisticasAgrupadas.setCampo(getConvertirResultados(anyoFechaCalculada, consultaEstadisticas.getAgrupacionVehiculos().toString()));
				} else {
					estadisticasAgrupadas.setCampo("Sin Fecha de Primera Matriculacion");
				}

				try {
					estadisticasAgrupadas.setNumRegistros(numRegistrosFechaCalculada);
				} catch (NumberFormatException nfe) {
					estadisticasAgrupadas.setNumRegistros(0);
				} catch (Exception e) {
					estadisticasAgrupadas.setNumRegistros(0);
				}

				if (anyoFechaCalculada != null && numRegistrosFechaCalculada > 0)
					estadisticasAgrupadas.setFechaMatriculacion(matriculaFecha + " / " + estadisticasAgrupadas.getNumRegistros());

				totalRows += matriculaFecha + estadisticasAgrupadas.getNumRegistros();
				listaConsultaEstadisticasAgrupadas.add(estadisticasAgrupadas);
				getSession().put(ConstantesSession.CONSULTA_ESTADISTICAS, consultaEstadisticas);
				setListaConsultaEstadisticasAgrupadas(listaConsultaEstadisticasAgrupadas);
			}

			getSession().put("totalList", listaConsultaEstadisticasAgrupadas.size());
			try {
				getSession().put("totalRows", totalRows);
			} catch (Exception e) {
				log.error("Error al obtener las estadisticas agrupadas. ", e);
			}
		}

		setListaConsultaEstadisticasAgrupadas(listaConsultaEstadisticasAgrupadas);
		setListaConsultaEstadisticasVehiculos(listaConsultaEstadisticasVehiculos);
		setConsultaEstadisticas(consultaEstadisticas);

		getSession().put("listaConsultaEstadisticasAgrupadas", listaConsultaEstadisticasAgrupadas);
		getSession().put("listaConsultaEstadisticasTramiteTraf", listaConsultaEstadisticasTramiteTraf);
		getSession().put(ConstantesSession.CONSULTA_ESTADISTICAS, consultaEstadisticas);
		getSession().put(ConstantesSession.CONSULTA_ESTADISTICAS, consultaEstadisticas);

		log.debug("fin generarListadoVehiculos");
		return ConstantesEstadisticas.LISTADO_VEHICULOS;
	}

	public String generarCalcularMatricula() {
		log.debug("inicio generarCalcularMatricula");
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);

		if (consultaEstadisticas.getNumMatricula() == null || consultaEstadisticas.getNumMatricula().equals("") || consultaEstadisticas.getNumMatricula().length() != 4) {
			addActionError(ConstantesEstadisticas.MENSAJE_ERROR_NUM_MATRICULA);
			return ConstantesEstadisticas.LISTADO_MATRICULAS;
		} else if (consultaEstadisticas.getLetraMatricula() == null || consultaEstadisticas.getLetraMatricula().equals("") || consultaEstadisticas.getLetraMatricula().length() != 3) {
			addActionError(ConstantesEstadisticas.MENSAJE_ERROR_LETRA_MATRICULA);
			return ConstantesEstadisticas.LISTADO_MATRICULAS;
		}

		String buscaFechaMatriculacion = estadisticasDAO.buscaFechaMatriculacion(consultaEstadisticas);
		if (buscaFechaMatriculacion != null && !buscaFechaMatriculacion.equalsIgnoreCase(""))
			addActionMessage("La matricula " + consultaEstadisticas.getNumMatricula() + " - " + consultaEstadisticas.getLetraMatricula().toUpperCase() + " corresponde a " + buscaFechaMatriculacion
					+ " en la plataforma OEGAM.");
		else {
			buscaFechaMatriculacion = estadisticasDAO.buscaRangoFechaMatriculacion(consultaEstadisticas, ConstantesEstadisticas.INFO_MES_ANYO);
			if (buscaFechaMatriculacion != null && !buscaFechaMatriculacion.equalsIgnoreCase(""))
				addActionMessage("La matricula " + consultaEstadisticas.getNumMatricula() + " - " + consultaEstadisticas.getLetraMatricula().toUpperCase() + " corresponde a "
						+ buscaFechaMatriculacion + " dentro del rango de matriculas.");
			else
				addActionError("No se ha encontrado la matricula " + consultaEstadisticas.getNumMatricula() + " - " + consultaEstadisticas.getLetraMatricula().toUpperCase() + ".");
		}

		log.debug("fin generarCalcularMatricula");
		return ConstantesEstadisticas.LISTADO_MATRICULAS;
	}

	public String imprimirExcel() {
		log.debug("Inicio imprimirExcel TramiteTrafico");
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
		EstadisticasAgrupadasExcel estadisticasAgrupadasExcel = new EstadisticasAgrupadasExcel();
		List<HashMap> listaHashConsultaEstadisticasTramiteTraf = null;

		try {
			listaHashConsultaEstadisticasTramiteTraf = estadisticasDAO.buscaTramitesPorAgrupacionParaExcel(consultaEstadisticas);
		} catch (Exception e3) {
			addActionError("Se ha producido un conflicto de datos. No existe el vehiculo referenciado desde TramiteTrafico.");
			return ConstantesEstadisticas.LISTADO_GENERAL;
		}

		getSession().put(ConstantesSession.CONSULTA_ESTADISTICAS, consultaEstadisticas);

		String nombreFichero = "";

		nombreFichero = "ESTADISTICAS__ICOGAM__" + "_" + utilesFecha.getTimestampActual().toString();
		nombreFichero = nombreFichero.replace(':', '_');
		nombreFichero = nombreFichero.replace('-', '_');
		nombreFichero = nombreFichero.replace(' ', '_');
		nombreFichero = nombreFichero.replace('.', '_');

		Fecha fecha = utilesFecha.getFechaActual();

		File archivo = null;
		try {
			try {
				archivo = gestorDocumentos.guardarFichero(ConstantesGestorFicheros.ESTADISTICAS, ConstantesGestorFicheros.EXCELS, fecha, nombreFichero, ConstantesGestorFicheros.EXTENSION_XLS,
						new byte[1]);
			} catch (Exception e) {
				log.error("Error al guardar el fichero", e);
			}
		} catch (OegamExcepcion e2) {
			log.error("Error al guardar el excel", e2);
		}

		try {
			estadisticasAgrupadasExcel.createAgrupacionExcel(listaHashConsultaEstadisticasTramiteTraf, archivo.getAbsolutePath());
		} catch (IOException e1) {
			addActionError(ConstantesEstadisticas.MENSAJE_ERROR_EXCEL);
			return ConstantesEstadisticas.LISTADO_GENERAL;
		} catch (Exception e) {
			addActionError(ConstantesEstadisticas.MENSAJE_ERROR_EXCEL);
			return ConstantesEstadisticas.LISTADO_GENERAL;
		}

		try {
			inputStream = new FileInputStream(archivo);
		} catch (FileNotFoundException e) {
			addActionError(ConstantesEstadisticas.MENSAJE_ERROR_EXCEL);
			return ConstantesEstadisticas.LISTADO_GENERAL;
		}
		nombreFichero = nombreFichero + ConstantesPDF.EXTENSION_XLS;
		setFileName(nombreFichero);

		log.debug("Fin imprimirExcel TramiteTrafico");

		return ConstantesEstadisticas.DESCARGA_EXCEL;
	}

	public String imprimirExcelJustificantes() {
		log.debug("Inicio Exportando Excel de Justificantes");
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
		EstadisticasAgrupadasJustificantesExcel estadisticasAgrupadasJustificantesExcel = new EstadisticasAgrupadasJustificantesExcel();
		List<HashMap> listaHashConsultaEstadisticasJustificantes = null;

		try {
			listaHashConsultaEstadisticasJustificantes = estadisticasDAO.buscaJustificantesPorAgrupacionParaExcel(consultaEstadisticas);
		} catch (Exception e3) {
			addActionError("Se ha producido un conflicto de datos. No existe el vehiculo referenciado desde TramiteTrafico.");
			return ConstantesEstadisticas.LISTADO_JUSTIFICANTES_NO_ULTIMADOS;
		}

		getSession().put(ConstantesSession.CONSULTA_ESTADISTICAS, consultaEstadisticas);

		String nombreFichero = "";

		nombreFichero = "ESTADISTICAS__JUSTIFICANTES__" + "_" + utilesFecha.getTimestampActual().toString();
		nombreFichero = nombreFichero.replace(':', '_');
		nombreFichero = nombreFichero.replace('-', '_');
		nombreFichero = nombreFichero.replace(' ', '_');
		nombreFichero = nombreFichero.replace('.', '_');

		Fecha fecha = utilesFecha.getFechaActual();
		File archivo = null;
		try {
			try {
				archivo = gestorDocumentos.guardarFichero(ConstantesGestorFicheros.ESTADISTICAS, ConstantesGestorFicheros.EXCELS, fecha, nombreFichero, ConstantesGestorFicheros.EXTENSION_XLS,
						new byte[1]);
			} catch (Exception e) {
				log.error("Error al guardar el fichero", e);
			}
		} catch (OegamExcepcion e2) {
			log.error("Error al guardar el excel", e2);
		}

		try {
			estadisticasAgrupadasJustificantesExcel.createAgrupacionExcel(listaHashConsultaEstadisticasJustificantes, archivo.getAbsolutePath());
		} catch (IOException e1) {
			addActionError(ConstantesEstadisticas.MENSAJE_ERROR_EXCEL);
			return ConstantesEstadisticas.LISTADO_JUSTIFICANTES_NO_ULTIMADOS;
		} catch (Exception e) {
			addActionError(ConstantesEstadisticas.MENSAJE_ERROR_EXCEL);
			return ConstantesEstadisticas.LISTADO_JUSTIFICANTES_NO_ULTIMADOS;
		}

		try {
			inputStream = new FileInputStream(archivo);
		} catch (FileNotFoundException e) {
			addActionError(ConstantesEstadisticas.MENSAJE_ERROR_EXCEL);
			return ConstantesEstadisticas.LISTADO_JUSTIFICANTES_NO_ULTIMADOS;
		}
		nombreFichero = nombreFichero + ConstantesPDF.EXTENSION_XLS;
		setFileName(nombreFichero);

		log.debug("Fin Exportando Excel de Justificantes");
		return ConstantesEstadisticas.DESCARGA_EXCEL;
	}

	public String imprimirExcelVehiculos() {
		log.debug("Inicio imprimirExcelVehiculos TramiteTrafico");
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
		EstadisticasAgrupadasExcel estadisticasAgrupadasExcel = new EstadisticasAgrupadasExcel();
		List<HashMap> listaHashConsultaEstadisticasTramiteTraf = null;

		try {
			listaHashConsultaEstadisticasTramiteTraf = estadisticasDAO.buscaVehiculosPorAgrupacionParaExcel(consultaEstadisticas, Boolean.TRUE);
		} catch (Exception e3) {
			addActionError("Se ha producido un conflicto de datos. No existe el vehiculo referenciado desde TramiteTrafico.");
			return ConstantesEstadisticas.LISTADO_VEHICULOS;
		}

		getSession().put(ConstantesSession.CONSULTA_ESTADISTICAS, consultaEstadisticas);

		String nombreFichero = "";

		nombreFichero = "ESTADISTICAS__ICOGAM__" + "_" + utilesFecha.getTimestampActual().toString();
		nombreFichero = nombreFichero.replace(':', '_');
		nombreFichero = nombreFichero.replace('-', '_');
		nombreFichero = nombreFichero.replace(' ', '_');
		nombreFichero = nombreFichero.replace('.', '_');

		Fecha fecha = utilesFecha.getFechaActual();
		File archivo = null;
		try {
			archivo = gestorDocumentos
					.guardarFichero(ConstantesGestorFicheros.ESTADISTICAS, ConstantesGestorFicheros.EXCELS, fecha, nombreFichero, ConstantesGestorFicheros.EXTENSION_XLS, new byte[1]);
		} catch (OegamExcepcion e2) {
			log.error("Error al guardar el excel", e2);
		}

		try {
			estadisticasAgrupadasExcel.createAgrupacionVehiculosExcel(listaHashConsultaEstadisticasTramiteTraf, archivo.getAbsolutePath());
		} catch (IOException e1) {
			addActionError(ConstantesEstadisticas.MENSAJE_ERROR_EXCEL);
			return ConstantesEstadisticas.LISTADO_VEHICULOS;
		} catch (Exception e) {
			addActionError(ConstantesEstadisticas.MENSAJE_ERROR_EXCEL);
			return ConstantesEstadisticas.LISTADO_VEHICULOS;
		}

		try {
			inputStream = new FileInputStream(archivo);
		} catch (FileNotFoundException e) {
			addActionError(ConstantesEstadisticas.MENSAJE_ERROR_EXCEL);
			return ConstantesEstadisticas.LISTADO_VEHICULOS;
		}
		nombreFichero = nombreFichero + ConstantesPDF.EXTENSION_XLS;
		setFileName(nombreFichero);

		log.debug("Fin imprimirExcel TramiteTrafico");

		return ConstantesEstadisticas.DESCARGA_EXCEL;
	}

	// DRC@22-04-2013 Convierte los resultados obtenidos en las agrupacion por
	// informacion legible por el usuario
	public String getConvertirResultados(String codigo, String agrupacion) {
		// Mes
		if (agrupacion.equalsIgnoreCase(ConstantesEstadisticas.AGRUPACION_MES)) {
			if ("01".equals(codigo)) {
				return "Enero";
			} else if ("02".equals(codigo)) {
				return "Febrero";
			} else if ("03".equals(codigo)) {
				return "Marzo";
			} else if ("04".equals(codigo)) {
				return "Abril";
			} else if ("05".equals(codigo)) {
				return "Mayo";
			} else if ("06".equals(codigo)) {
				return "Junio";
			} else if ("07".equals(codigo)) {
				return "Julio";
			} else if ("08".equals(codigo)) {
				return "Agosto";
			} else if ("09".equals(codigo)) {
				return "Septiembre";
			} else if ("10".equals(codigo)) {
				return "Octubre";
			} else if ("11".equals(codigo)) {
				return "Noviembre";
			} else if ("12".equals(codigo)) {
				return "Diciembre";
			}
		}

		// Estado
		if (agrupacion.equalsIgnoreCase(ConstantesEstadisticas.AGRUPACION_ESTADO)) {
			if ("1".equals(codigo)) {
				return "Iniciado";
			} else if ("2".equals(codigo)) {
				return "Duplicado";
			} else if ("3".equals(codigo)) {
				return "Anulado";
			} else if ("4".equals(codigo)) {
				return "Pendiente CheckCTIT";
			} else if ("5".equals(codigo)) {
				return "No Tramitable";
			} else if ("6".equals(codigo)) {
				return "Tramitable Incidnecias";
			} else if ("7".equals(codigo)) {
				return "Tramitable";
			} else if ("8".equals(codigo)) {
				return "Validado Telematicamente";
			} else if ("9".equals(codigo)) {
				return "Validado PDF";
			} else if ("10".equals(codigo)) {
				return "Pendiente de respuesta de la DGT";
			} else if ("11".equals(codigo)) {
				return "Finalizado con Error";
			} else if ("12".equals(codigo)) {
				return "Finalziado Telematicamente";
			} else if ("13".equals(codigo)) {
				return "Finalizado PDF";
			} else if ("14".equals(codigo)) {
				return "Finalizado Telematicamente Impreso";
			} else if ("15".equals(codigo)) {
				return "Tramitable Jefatura";
			} else if ("16".equals(codigo)) {
				return "Informe Telematico";
			} else if ("17".equals(codigo)) {
				return "Pendiente Envio Excel";
			} else if ("18".equals(codigo)) {
				return "Pendiente Respuesta Jefatura";
			} else if ("19".equals(codigo)) {
				return "Finalizado Excel";
			} else if ("20".equals(codigo)) {
				return "Finalizado Excel con incidencia";
			} else if ("21".equals(codigo)) {
				return "Finalizado Excel Impreso";
			}
		}

		// Tipo Tramite
		if (agrupacion.equalsIgnoreCase(ConstantesEstadisticas.AGRUPACION_TIPO_TRAMITE)) {
			if ("T1".equals(codigo)) {
				return codigo + " - Matriculacion";
			} else if ("T2".equals(codigo)) {
				return codigo + " - Transmision";
			} else if ("T3".equals(codigo)) {
				return codigo + " - Baja";
			} else if ("T4".equals(codigo)) {
				return codigo + " - Solicitud";
			} else if ("T7".equals(codigo)) {
				return codigo + " - Transmision Electronica";
			} else if ("T8".equals(codigo)) {
				return codigo + " - Duplicado";
			} else if ("T27".equals(codigo)) {
				return codigo + " - Cambio Servicio";
			}
		}
		return codigo;
	}

	// DRC@26-04-2013 Ordena el map de forma Descendente
	public static Map ordenDescendente(Map ordenarMap) {
		Map descentente = new TreeMap(Collections.reverseOrder());
		descentente.putAll(ordenarMap);
		return descentente;
	}

	public String navegar() {
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
		mantenimientoCamposNavegar();
		return ConstantesEstadisticas.LISTADO_USUARIOSIP;
	}

	public String navegarTasa() {
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
		mantenimientoCamposNavegar();
		return ConstantesEstadisticas.LISTADO_EVOLUCIONTASAEXP;
	}

	public String navegarGeneral() {
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
		mantenimientoCamposNavegar();
		return ConstantesEstadisticas.LISTADO_GENERAL;
	}

	public String navegarJustificantes() {
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
		mantenimientoCamposNavegar();
		return ConstantesEstadisticas.LISTADO_JUSTIFICANTES_NO_ULTIMADOS;
	}

	public String navegarVehiculos() {
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
		mantenimientoCamposNavegar();
		return ConstantesEstadisticas.LISTADO_VEHICULOS;
	}

	// ----------------- GET & SET -------------------------
	public ConsultaEstadisticasBean getConsultaEstadisticas() {
		return consultaEstadisticas;
	}

	public void setConsultaEstadisticas(ConsultaEstadisticasBean consultaEstadisticas) {
		this.consultaEstadisticas = consultaEstadisticas;
	}

	public HashMap<String, Object> getParametrosBusqueda() {
		return parametrosBusqueda;
	}

	public void setParametrosBusqueda(HashMap<String, Object> parametrosBusqueda) {
		this.parametrosBusqueda = parametrosBusqueda;
	}

	public void setListaConsultaEstadisticasUsuariosIP(List<UsuarioLoginVO> listaConsultaEstadisticasUsuarios) {
		this.listaConsultaEstadisticasUsuariosIP = listaConsultaEstadisticasUsuarios;
	}

	public void setListaConsultaEstadisticasTramiteTrafico(List<TramiteTrafico> listaConsultaEstadisticasTramiteTraf) {
		this.listaConsultaEstadisticasTramiteTraf = listaConsultaEstadisticasTramiteTraf;
	}

	public List<UsuarioLoginVO> getListaConsultaEstadisticasUsuariosIP() {
		return listaConsultaEstadisticasUsuariosIP;
	}

	public List<TramiteTrafico> getListaConsultaEstadisticasTramiteTrafico() {
		return listaConsultaEstadisticasTramiteTraf;
	}

	public List<EstadisticasAgrupadas> getListaConsultaEstadisticasAgrupadas() {
		return listaConsultaEstadisticasAgrupadas;
	}

	public void setListaConsultaEstadisticasAgrupadas(List<EstadisticasAgrupadas> listaConsultaEstadisticasAgrupadas) {
		this.listaConsultaEstadisticasAgrupadas = listaConsultaEstadisticasAgrupadas;
	}

	public String getResultadosPorPagina() {
		return resultadosPorPagina;
	}

	public void setResultadosPorPagina(String resultadosPorPagina) {
		this.resultadosPorPagina = resultadosPorPagina;
	}

	public List<TramiteTrafico> getListaConsultaEstadisticasTramiteTraf() {
		return listaConsultaEstadisticasTramiteTraf;
	}

	public void setListaConsultaEstadisticasTramiteTraf(List<TramiteTrafico> listaConsultaEstadisticasTramiteTraf) {
		this.listaConsultaEstadisticasTramiteTraf = listaConsultaEstadisticasTramiteTraf;
	}

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

	public List<JustificanteProf> getListaConsultaEstadisticasJustificantes() {
		return listaConsultaEstadisticasJustificantes;
	}

	public void setListaConsultaEstadisticasJustificantes(List<JustificanteProf> listaConsultaEstadisticasJustificantes) {
		this.listaConsultaEstadisticasJustificantes = listaConsultaEstadisticasJustificantes;
	}

	public List<Vehiculo> getListaConsultaEstadisticasVehiculos() {
		return listaConsultaEstadisticasVehiculos;
	}

	public void setListaConsultaEstadisticasVehiculos(List<Vehiculo> listaConsultaEstadisticasVehiculos) {
		this.listaConsultaEstadisticasVehiculos = listaConsultaEstadisticasVehiculos;
	}

	public List<EvolucionTasa> getListaConsultaEstadisticasEvolucionTasa() {
		return listaConsultaEstadisticasEvolucionTasa;
	}

	public void setListaConsultaEstadisticasEvolucionTasa(List<EvolucionTasa> listaConsultaEstadisticasEvolucionTasa) {
		this.listaConsultaEstadisticasEvolucionTasa = listaConsultaEstadisticasEvolucionTasa;
	}

	// Devuelve una lista con los frontales activos
	public List<Integer> getListaFrontales() {
		if (listaFrontales == null) {
			listaFrontales = servicioSesion.getFrontalesActivos();
		}
		return listaFrontales;

	}

	public void setListaFrontales(List<Integer> listaFrontales) {
		this.listaFrontales = listaFrontales;
	}

	// Devuelve una lista con los usuarios que hay en cada frontal activo
	public List<Object[]> getListaFrontalesUsuarios() {
		if (listaFrontalesUsuarios == null) {
			listaFrontalesUsuarios = servicioSesion.getUsuariosFrontal();
		}
		return listaFrontalesUsuarios;
	}

	public void setListaFrontalesUsuarios(List<Object[]> listaFrontalesUsuarios) {
		this.listaFrontalesUsuarios = listaFrontalesUsuarios;
	}

	public List<Object[]> getListasUsuariosRepetidos() {
		if (listasUsuariosRepetidos == null) {
			listasUsuariosRepetidos = servicioSesion.getUsuariosRepetidos();
		}
		return listasUsuariosRepetidos;
	}

	public void setListasUsuariosRepetidos(List<Object[]> listasUsuariosRepetidos) {
		this.listasUsuariosRepetidos = listasUsuariosRepetidos;
	}

	// Devuelve una lista con los usuarios totales en los frontales
	public List<Object[]> getListaUsuariosTotalesFrontales() {
		if (listaUsuariosTotalesFrontales == null) {
			listaUsuariosTotalesFrontales = servicioSesion.getUsuariosTotalesFrontales();
		}
		return listaUsuariosTotalesFrontales;
	}

	public void setListaUsuariosTotalesFrontales(List<Object[]> listaUsuariosTotalesFrontales) {
		this.listaUsuariosTotalesFrontales = listaUsuariosTotalesFrontales;
	}

	public ServicioSesion getServicioSesion() {
		return servicioSesion;
	}

	public void setServicioSesion(ServicioSesion servicioSesion) {
		this.servicioSesion = servicioSesion;
	}

}