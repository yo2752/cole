package general.acciones;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.displaytag.properties.SortOrderEnum;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import escrituras.modelo.ModeloReconocimiento;
import escrituras.utiles.UtilesVista;
import exportarXML.ReconocimientosMedicosExcel;
import facturacion.dao.DatosDAO;
import general.utiles.Anagrama;
import general.utiles.enumerados.EstadoReconocimientoMedico;
import hibernate.entities.general.Contrato;
import hibernate.entities.general.ReconocimientoMedico;
import hibernate.entities.personas.Direccion;
import hibernate.entities.personas.EvolucionPersona;
import hibernate.entities.personas.EvolucionPersonaPK;
import hibernate.entities.personas.Persona;
import hibernate.entities.personas.PersonaDireccion;
import hibernate.entities.personas.PersonaDireccionPK;
import hibernate.entities.trafico.filters.ReconocimientoMedicoFilter;
import oegam.constantes.ConstantesSession;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.imprimir.ImprimirGeneral;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.validaciones.NIFValidator;
import utilidades.web.OegamExcepcion;

public class ReconocimientoMedicoAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 1L;
	private static final String RESULT_CONSULTA = "consulta";
	private static final String RESULT_LISTADO = "list";
	private static final String RESULT_PDF = "peticionPDF";
	private static final String RESULT_XLS = "resumenXLS";
	private static final String RESULT_DOWNLOAD_ZIP = "descargarZip";
	private static final String RESULT_POPUP = "popUp";
	private static final String RESULT_POPUP_FECHA_VISITA = "popUpFechaVisita";
	private static final String ID_CLINICA = "CLI";
	private static final String RECONOCIMIENTO_MEDICO = "Reconocimiento médico";
	private static final String SIN_PERMISOS_PARA_EJECUTAR_ACCION = "No tiene permisos para ejecutar esta acción";
	private static final String ERROR_GUARDADO_FICHERO_EXCEL = "Se produjo un error al guardar el fichero excel";

	// logger
	private static final ILoggerOegam log = LoggerOegam.getLogger(ReconocimientoMedicoAction.class);

	// Modelo de los reconocimientos médicos.
	private ModeloReconocimiento modeloReconocimiento = new ModeloReconocimiento();

	private HttpServletRequest servletRequest; // Para AJAX
	private HttpServletResponse servletResponse; // Para AJAX

	// Variables del formulario, pertenecen al bean.
	private ReconocimientoMedicoFilter filtro;
	private Long idReconocimiento;

	private Fecha fechaReconocimiento;
	private Fecha fcaducidad;
	private Fecha fnacimiento;
	private Fecha fechaVisita;
	private String importe;

	private ReconocimientoMedico reconocimiento;
	private List<ReconocimientoMedico> listaReconocimiento;

	private String idsReconocimientos;	// Reconocimientos/s seleccionados/s
	private BigDecimal valorEstadoCambio;

	private List<ContratoDto> clinicas;

	private String identificador; // Identificador del reconocimiento Medico (ediciones)

	/*
	 * Elementos comunes a todos los action de listas, tales como la página
	 * actual (para la paginación), si está ordenado por una columna, si el
	 * ordenamiento es ascendente o descendente, la cantidad de elemntos a
	 * mostrar por página, entre otros
	 */
	// Parámetros de las peticiones del displayTag
	private String page; // Página a mostrar
	private String sort; // Columna por la que se ordena
	private String dir; // Orden de la ordenación
	private String resultadosPorPagina; // La cantidad de elementos a mostrar por página

	// Flag para controlar si el usuario tiene permisos de Clinica
	private Boolean permisoClinica;

	private InputStream inputStream;	// Flujo de bytes del fichero a imprimir en PDF del action
	private String fileName;			// Nombre del fichero a imprimir
	private String fileSize;			// Tamaño del fichero a imprimir
	private Boolean impresoEspera = false; //Booleano que nos permite controlar si hay una impresión que realizar

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	Conversor conversor;

	/////////////// MÉTODOS

	/**
	 * Método que se ejecuta al principio de la consulta, que inicializará los
	 * parámetros de búsqueda.
	 */
	public String inicio() throws Throwable {
		mantenimientoCamposNavegar();
		if (getResultadosPorPagina() == null) {
			setResultadosPorPagina(UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO);
		}
		if (getFiltro() == null) {
			setFiltro(new ReconocimientoMedicoFilter());
			setSort("fechaAlta");
			setDir(SortOrderEnum.DESCENDING.getName());
			getFiltro().setSort(getSort());
			getFiltro().setDir(getDir());
		}
		if (getClinicas() == null) {
			setClinicas(servicioContrato.getListGrupo(ID_CLINICA));
		}
		if (getPermisoClinica() == null) {
			setPermisoClinica(servicioContrato.isContratoDeGrupo(utilesColegiado.getIdContratoSession(), ID_CLINICA));
		}
		updateFilter();
		try {
			setListaReconocimiento(modeloReconocimiento.list(getFiltro(), "clinica", "persona", "tipoTramiteRenovacion"));
		} catch (Exception e) {
		}
		persistenciaListados();

		return RESULT_LISTADO;
	}

	/**
	 * Función principal de búsqueda, que dependiendo de si es la primera vez u
	 * otra vez que se ejecuta accederá al modelo de tráfico para realizar la
	 * búsqueda con los parámetros que hayamos pasado.
	 * 
	 * @param busquedaInicial
	 * @return
	 * @throws Throwable
	 */
	public String buscar() throws Throwable {
		log.info("Consulta de peticiones de reconocimientos médicos, buscar.");
		mantenimientoDatosAuxiliares();
		String resultadosPorPagina = (String) getSession().get(ConstantesSession.RESULTADOS_PAGINA);
		setResultadosPorPagina(resultadosPorPagina != null ? resultadosPorPagina : UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO);
		updateFilter();

		setListaReconocimiento(modeloReconocimiento.list(getFiltro(), "clinica", "persona", "tipoTramiteRenovacion"));
		if (getListaReconocimiento() == null) {
			log.error("Error buscar al obtener lista de reconocimientos.");
			return ERROR;
		}

		persistenciaListados();
		return RESULT_LISTADO;
	}

	/**
	 * Función de búsqueda para la paginación. Generará la búsqueda limitada por los parámetros de
	 * paginación.
	 * @return
	 */
	public String navegar() {
		if (getResultadosPorPagina() != null) {
			getSession().put(ConstantesSession.RESULTADOS_PAGINA, getResultadosPorPagina());
		}

		mantenimientoCamposNavegar();

		if (getListaReconocimiento() == null) {
			log.error("Error navegar al obtener lista de reconocimientos.");
			return ERROR;
		}
		return RESULT_LISTADO;
	}

	public String editar() throws Throwable {
		mantenimientoDatosAuxiliares();
		if (getClinicas() == null) {
			setClinicas(servicioContrato.getListGrupo(ID_CLINICA));
		}
		if (getPermisoClinica() == null) {
			setPermisoClinica(servicioContrato.isContratoDeGrupo(utilesColegiado.getIdContratoSession(), ID_CLINICA));
		}
		persistenciaDatosAuxiliares();
		if (getIdReconocimiento() != null) {
			setReconocimiento(modeloReconocimiento.get(getIdReconocimiento(), "persona", "clinica", "colegiado", "contrato", "tipoTramiteRenovacion"));
			getReconocimiento().getPersona().setDireccionActual(
					new DatosDAO().buscarDireccionActual(getReconocimiento().getPersona().getId().getNif(),
							getReconocimiento().getPersona().getId().getNumColegiado()));

			if (getReconocimiento() != null && getReconocimiento().getFechaReconocimiento() != null) {
				setFechaReconocimiento(utilesFecha.getFechaTimeStampConDate(getReconocimiento().getFechaReconocimiento()));
			}
			if (getReconocimiento() != null && getReconocimiento().getPersona() != null && getReconocimiento().getPersona().getFechaNacimiento() != null) {
				setFnacimiento(utilesFecha.getFechaTimeStampConDate(getReconocimiento().getPersona().getFechaNacimiento()));
			}
			if (getReconocimiento() != null && getReconocimiento().getPersona() != null && getReconocimiento().getPersona().getFechaCaducidadCarnet() != null) {
				setFcaducidad(utilesFecha.getFechaTimeStampConDate(getReconocimiento().getPersona().getFechaCaducidadCarnet()));
			}
			if (getReconocimiento() != null && getReconocimiento().getFechaRealVisita() != null) {
				setFechaVisita(utilesFecha.getFechaTimeStampConDate(getReconocimiento().getFechaRealVisita()));
			}

			String importe = null;
			if (getReconocimiento() != null && getReconocimiento().getTipoTramiteRenovacion() != null) {
				importe = calcularImporte(getReconocimiento().getTipoTramiteRenovacion().getIdTipoTramiteRenovacion(), getFechaReconocimiento());
			}
			setImporte(importe!=null?importe:"");
		} else {
			setReconocimiento(new ReconocimientoMedico());
			Contrato contrato = new Contrato();
			ContratoVO contratoVO = servicioContrato.getContrato(utilesColegiado.getIdContratoSessionBigDecimal());
			if (contratoVO != null) {
				contrato = conversor.transform(contratoVO, Contrato.class);
			}
			getReconocimiento().setContrato(contrato);
			getReconocimiento().setColegiado(getReconocimiento().getContrato().getColegiado());
			getReconocimiento().setEstado(new BigDecimal(EstadoReconocimientoMedico.Iniciado.getValorEnum()));
		}
		return RESULT_CONSULTA;
	}

	/**
	 * Guarda la petición de reconocimiento médico
	 * @return
	 * @throws Throwable
	 */
	public String guardar() throws Throwable{
		mantenimientoDatosAuxiliares();
		try{
			ResultBean resultado;
			if (getReconocimiento().getIdReconocimiento() != null && getReconocimiento().getEstado() != null
					&& getReconocimiento().getEstado().longValue() != Long.parseLong(EstadoReconocimientoMedico.Iniciado.getValorEnum())) {
				resultado = new ResultBean();
				resultado.setError(true);
				resultado.setMensaje("Sólo se puede modificar en estado iniciado");
			} else if (getPermisoClinica()) {
				resultado = new ResultBean();
				resultado.setError(false);
			} else {
				// Actualizamos la fecha de caducidad del carnet de conducir
				if ((getFcaducidad() !=null) && (getFcaducidad().getFecha() != null)
						&& (getReconocimiento().getPersona().getFechaCaducidadCarnet() == null)) {
					try {
						this.getReconocimiento().getPersona().setFechaCaducidadCarnet(getFcaducidad().getFecha());
					} catch (ParseException e) {
						log.error("ERROR PARSENADO FECHA DE CADUCIDAD DEL PERMISO");
					}
				}
				//Actualizamos la fecha de la cita
				if (getFechaReconocimiento() != null && getFechaReconocimiento().getFecha() != null
						&& getReconocimiento().getFechaReconocimiento() == null) {
					getFechaReconocimiento().setSegundos("00");
					try {
						this.getReconocimiento().setFechaReconocimiento(getFechaReconocimiento().getFechaHora());
					} catch (ParseException e) {
						log.error("ERROR PARSENADO FECHA DE LA CITA DEL RECONOCIMIENTO");
					}
				}
				// Actualizamos la fecha de la visita
				if (getFechaVisita() != null && getFechaVisita().getFecha() != null
						&& getReconocimiento().getFechaRealVisita() == null) {
					getFechaVisita().setSegundos("00");
					try {
						this.getReconocimiento().setFechaRealVisita(getFechaVisita().getFechaHora());
					} catch (ParseException e) {
						log.error("ERROR PARSENADO FECHA DE LA CITA DEL RECONOCIMIENTO");
					}
				}
				// Actualizamos el tipo de reconocimiento
				if (getReconocimiento().getTipoTramiteRenovacion() != null  && getReconocimiento().getTipoTramiteRenovacion().getIdTipoTramiteRenovacion() != null) {
					getReconocimiento().setTipoTramiteRenovacion(modeloReconocimiento.getTipoTramiteRenovacion(getReconocimiento().getTipoTramiteRenovacion().getIdTipoTramiteRenovacion()));
				}
				resultado = modeloReconocimiento.validarDatosAlta(getReconocimiento());
			}

			if (resultado.getError()) {
				for (int i = 0; i < resultado.getListaMensajes().size(); i++) {
					if (resultado.getListaMensajes().get(i) != null) {
						addActionError(resultado.getListaMensajes().get(i).replace("--", " -"));
					}
				}
			} else {
				persistirSessionPantalla(resultado);
			}
		} catch (Exception e) {
			log.error("Error al realizar las validaciones de reconocimientos medicos: " + e);
			addActionError("Se ha producido un error al guardar la petición de reconocimiento médico");
		}
		return RESULT_CONSULTA;
	}

	public void persistirSessionPantalla(ResultBean resultado) throws OegamExcepcion, Exception{
		boolean existePersona = false;
		boolean dirtyPersona = false;
		boolean dirtyDireccion = false;

		//Actualizamos la fecha de Nacimiento
		if (getFnacimiento() != null && getFnacimiento().getFecha() != null
				&& getReconocimiento().getPersona().getFechaNacimiento() == null) {
			try {
				this.getReconocimiento().getPersona().setFechaNacimiento(getFnacimiento().getFecha());
			} catch (ParseException e) {
				log.error("ERROR PARSENADO FECHA DE NACIMIENTO");
			}
		}

		// Actualizamos el tipo de persona
		BigDecimal tipoPersona = NIFValidator.validarNif(getReconocimiento().getPersona().getId().getNif());
		if ("1".equals(String.valueOf(tipoPersona))) {
			getReconocimiento().getPersona().setTipoPersona("FISICA");
		} else if ("2".equals(String.valueOf(tipoPersona))) {
			getReconocimiento().getPersona().setTipoPersona("JURIDICA");
		} else if ("3".equals(String.valueOf(tipoPersona))) {
			getReconocimiento().getPersona().setTipoPersona("FISICA");
		}

		// Actualizamos el municipio
		if (getReconocimiento().getPersona().getDireccionActual().getMunicipio().getId() != null
				&& getReconocimiento().getPersona().getDireccionActual() != null
				&& getReconocimiento().getPersona().getDireccionActual().getMunicipio() != null
				&& getReconocimiento().getPersona().getDireccionActual().getMunicipio().getProvincia() != null
				&& getReconocimiento().getPersona().getDireccionActual().getMunicipio().getProvincia().getIdProvincia() != null)
		{
			getReconocimiento().getPersona().getDireccionActual().getMunicipio().getId()
					.setIdProvincia(getReconocimiento().getPersona().getDireccionActual().getMunicipio().getProvincia()
							.getIdProvincia());
		}

		// Actualizamos la fecha del reconocimiento
		if (getFechaReconocimiento() != null) {
			getFechaReconocimiento().setSegundos("00");
			if (!getFechaReconocimiento().isfechaHoraMinutosSegundosNula()) {
				getReconocimiento().setFechaReconocimiento(getFechaReconocimiento().getFechaHora());
			}
		}

		// Actualizamos la fecha real de la visita
		if (getFechaVisita() != null) {
			getFechaVisita().setSegundos("00");
			if (!getFechaVisita().isfechaHoraNula()) {
				getReconocimiento().setFechaRealVisita(getFechaVisita().getFechaHora());
			}
		}

		DatosDAO dao = new DatosDAO();

		// Cogemos el NIF de la pantalla para ver si existe la Persona en BBDD
		String nif = getReconocimiento().getPersona().getId().getNif();
		String numColegiado = getReconocimiento().getPersona().getId().getNumColegiado()!=null ? getReconocimiento().getPersona().getId().getNumColegiado() : utilesColegiado.getNumColegiadoSession();
		Persona personaExistente = dao.buscarPersona(nif, numColegiado);
		if (personaExistente != null) {
			existePersona = true;
			Direccion dirExistente = dao.buscarDireccionActual(nif, numColegiado);
			personaExistente.setDireccionActual(dirExistente);
			personaExistente.actualizaNulos();
		}
		if (getPermisoClinica()) {
			dirtyDireccion = false;
			dirtyPersona = false;
		} else if (existePersona) {
			// Cogemos la Pantalla y la Dirección de Pantalla para compararlas a ver si se ha modificado
			dirtyPersona = !personaExistente.equals(getReconocimiento().getPersona())
					|| (personaExistente.getFechaCaducidadCarnet() == null || !personaExistente.getFechaCaducidadCarnet().equals(getReconocimiento().getPersona().getFechaCaducidadCarnet()));
			dirtyDireccion = !personaExistente.getDireccionActual().equals(getReconocimiento().getPersona().getDireccionActual());
		}

		//Si la persona ya existe y no se le ha cambiado ni la Persona ni la Dirección
		if (existePersona && !dirtyPersona && !dirtyDireccion) {
			// El NIF ya existía, por lo que ponemos la que hay guardada
			getReconocimiento().setPersona(personaExistente);

			//Si la persona ya existe y no se ha modificado la Persona pero si la Direccion
		} else if (existePersona && !dirtyPersona && dirtyDireccion) {
			//Guardamos sobre la Persona de anterior. Le seteamos la fecha de Fin a la de Hoy
			try {
				dao.anularPersonaDireccion(personaExistente.getId().getNif(), personaExistente.getId().getNumColegiado(), personaExistente.getDireccionActual().getIdDireccion());
			} catch (Exception e) {
				throw e;
			}

			//Creo Direccion
			getReconocimiento().getPersona().getDireccionActual().getMunicipio().getId().setIdProvincia(getReconocimiento().getPersona().getDireccionActual().getMunicipio().getProvincia().getIdProvincia());					
			getReconocimiento().getPersona().getDireccionActual().setIdDireccion(0);

			dao.addDireccion(getReconocimiento().getPersona().getDireccionActual());

			//Creo PersonaDireccion
			PersonaDireccion persDir = new PersonaDireccion();
			PersonaDireccionPK pPk = new PersonaDireccionPK();

			pPk.setNif(nif);
			pPk.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			pPk.setIdDireccion(getReconocimiento().getPersona().getDireccionActual().getIdDireccion());

			persDir.setId(pPk);
			persDir.setFechaInicio(new Date());
			persDir.setDireccion(getReconocimiento().getPersona().getDireccionActual());

			dao.addPersonaDireccion(persDir);

			getReconocimiento().getPersona().getId().setNumColegiado(utilesColegiado.getNumColegiadoSession());
			getReconocimiento().getPersona().setEstado(1);

			List<PersonaDireccion> personaDirs = new ArrayList<>();
			personaDirs.add(persDir);

			getReconocimiento().getPersona().setPersonaDireccions(personaDirs);

			//Si la persona ya existe y se ha modificado la Persona pero no la Direccion
		} else if (existePersona && dirtyPersona && !dirtyDireccion) {
			//Creo Persona
			getReconocimiento().getPersona().getId().setNumColegiado(utilesColegiado.getNumColegiadoSession());
			getReconocimiento().getPersona().getId().setNif(nif);
			getReconocimiento().getPersona().setEstado(1);

			String anagrama = Anagrama.obtenerAnagramaFiscal(getReconocimiento().getPersona().getApellido1RazonSocial(), getReconocimiento().getPersona().getId().getNif());
			getReconocimiento().getPersona().setAnagrama(anagrama);

			if ("-".equals(getReconocimiento().getPersona().getEstadoCivil())) getReconocimiento().getPersona().setEstadoCivil(null);
			if ("-".equals(getReconocimiento().getPersona().getSexo())) getReconocimiento().getPersona().setSexo(null);
			if ("-".equals(getReconocimiento().getPersona().getTipoPersona())) getReconocimiento().getPersona().setTipoPersona(null);

			dao.saveOrUpdatePersona(getReconocimiento().getPersona());

			EvolucionPersona evolPersona = new EvolucionPersona();
			EvolucionPersonaPK ePk = new EvolucionPersonaPK();
			ePk.setNif(personaExistente.getId().getNif());
			ePk.setNumColegiado(personaExistente.getId().getNumColegiado());
			ePk.setFechaHora(utilesFecha.getTimestampActual());
			evolPersona.setId(ePk);

			evolPersona.setApellido1Ant(personaExistente.getApellido1RazonSocial());
			evolPersona.setApellido1Nue(getReconocimiento().getPersona().getApellido1RazonSocial());

			evolPersona.setApellido2Ant(personaExistente.getApellido2());
			evolPersona.setApellido2Nue(getReconocimiento().getPersona().getApellido2());

			evolPersona.setNombreAnt(personaExistente.getNombre());
			evolPersona.setNombreNue(getReconocimiento().getPersona().getNombre());

			evolPersona.setFechaNacimientoAnt(personaExistente.getFechaNacimiento());
			evolPersona.setFechaNacimientoNue(getReconocimiento().getPersona().getFechaNacimiento());

			evolPersona.setTipoTramite("RECO");
			evolPersona.setTipoActuacion("MODIFICACIÓN");
			evolPersona.setOtros(RECONOCIMIENTO_MEDICO);

			evolPersona.setIdUsuario(utilesColegiado.getIdUsuarioSessionBigDecimal());

			dao.addEvolucionPersona(evolPersona);

		} else if (existePersona && dirtyPersona && dirtyDireccion) {
			//Guardamos sobre la Persona de anterior. Le seteamos la fecha de Fin a la de Hoy

			//Creo Persona
			getReconocimiento().getPersona().getId().setNumColegiado(utilesColegiado.getNumColegiadoSession());
			getReconocimiento().getPersona().getId().setNif(nif);
			getReconocimiento().getPersona().setEstado(1);

			String anagrama = Anagrama.obtenerAnagramaFiscal(getReconocimiento().getPersona().getApellido1RazonSocial(), getReconocimiento().getPersona().getId().getNif());
			getReconocimiento().getPersona().setAnagrama(anagrama);

			if ("-".equals(getReconocimiento().getPersona().getEstadoCivil())) getReconocimiento().getPersona().setEstadoCivil(null);
			if ("-".equals(getReconocimiento().getPersona().getSexo())) getReconocimiento().getPersona().setSexo(null);
			if ("-".equals(getReconocimiento().getPersona())) getReconocimiento().getPersona().setTipoPersona(null);

			dao.saveOrUpdatePersona(getReconocimiento().getPersona());

			EvolucionPersona evolPersona = new EvolucionPersona();
			EvolucionPersonaPK ePk = new EvolucionPersonaPK();
			ePk.setNif(personaExistente.getId().getNif());
			ePk.setNumColegiado(personaExistente.getId().getNumColegiado());
			ePk.setFechaHora(utilesFecha.getTimestampActual());
			evolPersona.setId(ePk);

			evolPersona.setApellido1Ant(personaExistente.getApellido1RazonSocial());
			evolPersona.setApellido1Nue(getReconocimiento().getPersona().getApellido1RazonSocial());

			evolPersona.setApellido2Ant(personaExistente.getApellido2());
			evolPersona.setApellido2Nue(getReconocimiento().getPersona().getApellido2());

			evolPersona.setNombreAnt(personaExistente.getNombre());
			evolPersona.setNombreNue(getReconocimiento().getPersona().getNombre());

			evolPersona.setFechaNacimientoAnt(personaExistente.getFechaNacimiento());
			evolPersona.setFechaNacimientoNue(getReconocimiento().getPersona().getFechaNacimiento());

			evolPersona.setTipoTramite("RECO");
			evolPersona.setTipoActuacion("MODIFICACIÓN");
			evolPersona.setOtros(RECONOCIMIENTO_MEDICO);

			evolPersona.setIdUsuario(utilesColegiado.getIdUsuarioSessionBigDecimal());

			dao.addEvolucionPersona(evolPersona);

			dao.anularPersonaDireccion(personaExistente.getId().getNif(), personaExistente.getId().getNumColegiado(), personaExistente.getDireccionActual().getIdDireccion());

			// Creo Dirección
			getReconocimiento().getPersona().getDireccionActual().getMunicipio().getId().setIdProvincia(getReconocimiento().getPersona().getDireccionActual().getMunicipio().getProvincia().getIdProvincia());					
			getReconocimiento().getPersona().getDireccionActual().setIdDireccion(0);

			dao.addDireccion(getReconocimiento().getPersona().getDireccionActual());

			// Creo PersonaDireccion

			PersonaDireccion persDir = new PersonaDireccion();
			PersonaDireccionPK pPk = new PersonaDireccionPK();

			pPk.setNif(nif);
			pPk.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			pPk.setIdDireccion(getReconocimiento().getPersona().getDireccionActual().getIdDireccion());

			persDir.setId(pPk);
			persDir.setFechaInicio(new Date());
			persDir.setDireccion(getReconocimiento().getPersona().getDireccionActual());

			dao.addPersonaDireccion(persDir);

			getReconocimiento().getPersona().getId().setNumColegiado(utilesColegiado.getNumColegiadoSession());
			getReconocimiento().getPersona().setEstado(1);

			List<PersonaDireccion> personaDirs = new ArrayList<>();
			personaDirs.add(persDir);

			getReconocimiento().getPersona().setPersonaDireccions(personaDirs);

		} else if (!existePersona) {
			// Creo Persona
			getReconocimiento().getPersona().getId().setNumColegiado(utilesColegiado.getNumColegiadoSession());
			getReconocimiento().getPersona().getId().setNif(nif);
			getReconocimiento().getPersona().setEstado(1);

			String anagrama = Anagrama.obtenerAnagramaFiscal(getReconocimiento().getPersona().getApellido1RazonSocial(), getReconocimiento().getPersona().getId().getNif());
			getReconocimiento().getPersona().setAnagrama(anagrama);

			if ("-".equals(getReconocimiento().getPersona().getEstadoCivil())) getReconocimiento().getPersona().setEstadoCivil(null);
			if ("-".equals(getReconocimiento().getPersona().getSexo())) getReconocimiento().getPersona().setSexo(null);
			if ("-".equals(getReconocimiento().getPersona().getTipoPersona())) getReconocimiento().getPersona().setTipoPersona(null);
			dao.saveOrUpdatePersona(getReconocimiento().getPersona());

			EvolucionPersona evolPersona = new EvolucionPersona();
			EvolucionPersonaPK ePk = new EvolucionPersonaPK();
			ePk.setNif(getReconocimiento().getPersona().getId().getNif());
			ePk.setNumColegiado(getReconocimiento().getPersona().getId().getNumColegiado());
			ePk.setFechaHora(utilesFecha.getTimestampActual());
			evolPersona.setId(ePk);

			// Solo seteo de la persona Nueva ya que la vieja no existe y es todo nulo
			evolPersona.setApellido1Nue(getReconocimiento().getPersona().getApellido1RazonSocial());

			evolPersona.setApellido2Nue(getReconocimiento().getPersona().getApellido2());

			evolPersona.setNombreNue(getReconocimiento().getPersona().getNombre());

			evolPersona.setFechaNacimientoNue(getReconocimiento().getPersona().getFechaNacimiento());

			evolPersona.setTipoTramite("RECO");
			evolPersona.setTipoActuacion("CREACION");
			evolPersona.setOtros(RECONOCIMIENTO_MEDICO);

			evolPersona.setIdUsuario(utilesColegiado.getIdUsuarioSessionBigDecimal());
			//Creo PersonaDireccion

			dao.addEvolucionPersona(evolPersona);

			// Creo Dirección
			getReconocimiento().getPersona().getDireccionActual().getMunicipio().getId().setIdProvincia(getReconocimiento().getPersona().getDireccionActual().getMunicipio().getProvincia().getIdProvincia());
			getReconocimiento().getPersona().getDireccionActual().setIdDireccion(0);

			dao.addDireccion(getReconocimiento().getPersona().getDireccionActual());

			// Creo PersonaDireccion

			PersonaDireccion persDir = new PersonaDireccion();
			PersonaDireccionPK pPk = new PersonaDireccionPK();

			pPk.setNif(nif);
			pPk.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			pPk.setIdDireccion(getReconocimiento().getPersona().getDireccionActual().getIdDireccion());

			persDir.setId(pPk);
			persDir.setFechaInicio(new Date());
			persDir.setDireccion(getReconocimiento().getPersona().getDireccionActual());
			persDir.setPersona(getReconocimiento().getPersona());

			dao.addPersonaDireccion(persDir);

			getReconocimiento().getPersona().getId().setNumColegiado(utilesColegiado.getNumColegiadoSession());
			getReconocimiento().getPersona().setEstado(1);

			List<PersonaDireccion> personaDirs = new ArrayList<>();
			personaDirs.add(persDir);

			getReconocimiento().getPersona().setPersonaDireccions(personaDirs);
		}

		try {
			resultado = modeloReconocimiento.saveOrUpdate(getReconocimiento());

			if (resultado.getError()) {
				for (int i = 0; i < resultado.getListaMensajes().size(); i++) {
					if (resultado.getListaMensajes().get(i) != null) {
						addActionError(resultado.getListaMensajes().get(i).replace("--", " -"));
					}
				}
			} else {
				addActionMessage("Petición de reconocimiento médico guardada correctamente.");
			}
		} catch (Exception e) {
			log.error("Error al realizar las validaciones de facturacion: "	+ e);
			addActionError("Se ha producido un error al guardar la factura");
			throw e;
			//En caso de error lanza la excepción para que sea recogida por el método de guardarNueva o Modificar.
		}
		log.debug("Fin");
	}

	/**
	 * Método que busca un Cliente de Factura en bbdd por el NIF/CIF y devuelve
	 * todos los datos del Cliente.
	 * 
	 * @return
	 */
	public String buscarPersona() {
		mantenimientoDatosAuxiliares();
		try {
			String nif = getReconocimiento().getPersona().getId().getNif();
			String numColegiado;
			if (getReconocimiento() != null
					&& getReconocimiento().getPersona() != null
					&& getReconocimiento().getPersona().getId() != null
					&& getReconocimiento().getPersona().getId().getNumColegiado() != null
					&& !getReconocimiento().getPersona().getId().getNumColegiado().isEmpty()) {
				numColegiado = getReconocimiento().getPersona().getId().getNumColegiado();
			} else {
				numColegiado = utilesColegiado.getNumColegiadoSession();
			}
			Persona pers = new DatosDAO().buscarPersona(nif, numColegiado);

			if (pers != null){
				getReconocimiento().setPersona(pers);
				getReconocimiento().getPersona().setDireccionActual(
						new DatosDAO().buscarDireccionActual(getReconocimiento().getPersona().getId().getNif(),
								getReconocimiento().getPersona().getId().getNumColegiado()));

				if (getReconocimiento() != null && getReconocimiento().getPersona() != null && getReconocimiento().getPersona().getFechaNacimiento() != null) {
					setFnacimiento(utilesFecha.getFechaTimeStampConDate(getReconocimiento().getPersona().getFechaNacimiento()));
				}

				if (getReconocimiento() != null && getReconocimiento().getPersona() != null && getReconocimiento().getPersona().getFechaCaducidadCarnet() != null) {
					setFcaducidad(utilesFecha.getFechaTimeStampConDate(getReconocimiento().getPersona().getFechaCaducidadCarnet()));
				}
				if (getReconocimiento() != null && getReconocimiento().getFechaRealVisita() != null) {
					setFechaVisita(utilesFecha.getFechaTimeStampConDate(getReconocimiento().getFechaRealVisita()));
				}

				String importe = null;
				if (getReconocimiento() != null && getReconocimiento().getTipoTramiteRenovacion() != null) {
					importe = calcularImporte(getReconocimiento().getTipoTramiteRenovacion().getIdTipoTramiteRenovacion(), getFechaReconocimiento());
				}
				setImporte(importe != null ? importe : "");
			}
		} catch (Throwable e) {
			addActionError(e.toString());
		}
		return RESULT_CONSULTA;
	}

	/**
	 * imprime el Excel con el resumen de los reconocimientos médicos obtenidos
	 * @return
	 */
	public String resumenXLS() {
		mantenimientoCamposNavegar();

		log.debug("Inicio resumenXLS de los reconocimientos médicos");

		String ruta = gestorPropiedades.valorPropertie("reconocimientos.ficheros.NODO1")+ConstantesPDF.RUTA_EXCEL;

		setFileName("RECONOCIMIENTOS_MEDICOS_"+ new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()) + ConstantesPDF.EXTENSION_XLS);

		ruta += getFileName();

		try {
			ReconocimientosMedicosExcel.createResumenExcel(getListaReconocimiento(), ruta);
		} catch (IOException e1) {
			addActionError(ERROR_GUARDADO_FICHERO_EXCEL);
			return RESULT_LISTADO;
		} catch (Exception e) {
			addActionError(ERROR_GUARDADO_FICHERO_EXCEL);
			return RESULT_LISTADO;
		}

		try {
			setInputStream(new FileInputStream(new File(ruta)));
		} catch (FileNotFoundException e) {
			addActionError(ERROR_GUARDADO_FICHERO_EXCEL);
			return RESULT_LISTADO;
		}

		log.debug("Fin resumenXLS de los reconocimientos médicos");
		return RESULT_XLS;
	}

	/**
	 * imprime el PDF con la petición de reconocimiento médico seleccionada
	 * @return
	 */
	public String imprimirPDF() {
		log.debug("Inicio resumenXLS de los reconocimientos médicos");

		List<String> filesName = new ArrayList<>();
		List<ByteArrayInputStream> files = new ArrayList<>();

		if (getIdsReconocimientos()!=null) {
			ImprimirGeneral imprimirGeneral = new ImprimirGeneral();
			for (String codSeleccionado: getIdsReconocimientos().split(",")) {
				ReconocimientoMedico recMedico = modeloReconocimiento.get(Long.valueOf(codSeleccionado), "persona", "clinica", "colegiado", "colegiado.usuario", "contrato");
				if (recMedico == null) {
					addActionError("Se produjo un error al recuperar los datos de la petición con id " + codSeleccionado);
					continue;
				} else if (recMedico.getEstado() != null && recMedico.getEstado().longValue() != Long.parseLong(EstadoReconocimientoMedico.Iniciado.getValorEnum())) {
					addActionError("Solo se puede generar el PDF de los trámites en estado iniciado - " + codSeleccionado);
					continue;
				}

				recMedico.getPersona().setDireccionActual(
						new DatosDAO().buscarDireccionActual(recMedico.getPersona().getId().getNif(),
								recMedico.getPersona().getId().getNumColegiado()));
				try {
					ResultBean resultado = imprimirGeneral.imprimirPDFReconocimientoMedico(recMedico);
					if (resultado.getError()) {
						addActionError(resultado.getMensaje());
						log.debug("Imprimir petición: fallo");
					} else {
						String name = "Reco_"+recMedico.getPersona().getId().getNif()+ConstantesPDF.EXTENSION_PDF;
						int i = 0;
						while (filesName.contains(name)) {
							i++;
							name = "Reco_"+recMedico.getPersona().getId().getNif() + " ("+ i +")" + ConstantesPDF.EXTENSION_PDF;
						}
						filesName.add(name);
						files.add(new ByteArrayInputStream((byte[]) resultado.getAttachment("pdf")));
						log.debug("Correcto");
						log.debug("Fin imprimirPDF de los reconocimientos médicos");
					}
				} catch (OegamExcepcion e) {
					addActionError("Se produjo un error al generar el fichero PDF");
				}
			}
		}

		if (files.isEmpty()) {
			addActionError("No se ha podido obtener ninguno de las peticiones seleccionadas.");
			setImpresoEspera(false);
		} else {
			getSession().put(ConstantesSession.CONSULTA_DOCUMENTOS_RECONOCIMIENTO_MED, files);
			getSession().put(ConstantesSession.CONSULTA_NOMBRES_DOCUMENTOS_RECONOCIMIENTO_MED, filesName);
			setImpresoEspera(true);
		}

		mantenimientoCamposNavegar();
		return RESULT_LISTADO;
	}

	public String mostrarDocumento() throws Throwable {
		@SuppressWarnings("unchecked")
		List<ByteArrayInputStream> byteStreams = (List<ByteArrayInputStream>) getSession().get(ConstantesSession.CONSULTA_DOCUMENTOS_RECONOCIMIENTO_MED);
		@SuppressWarnings("unchecked")
		List<String> filesName = (List<String>) getSession().get(ConstantesSession.CONSULTA_NOMBRES_DOCUMENTOS_RECONOCIMIENTO_MED);

		if (byteStreams == null || byteStreams.isEmpty()) {
			addActionError("No hay nada que imprimir");
			return RESULT_LISTADO;
		}

		if (byteStreams.size() == 1) {
			setInputStream(byteStreams.get(0));
			setFileName(filesName.get(0));
			return RESULT_PDF;
		} else {
			// Los metemos en un zip

			// Aquí obtendremos el resto de ficheros para meterlos en el último
			// que será el que imprimimamos.
			ByteArrayOutputStream baosdeZips = new ByteArrayOutputStream();
			ZipOutputStream salidaFinaldeZips = new ZipOutputStream(baosdeZips);

			byte[] buffer = new byte[128];
			for (int i=0; i < byteStreams.size(); i++) {
				ByteArrayInputStream fis = byteStreams.get(i);
				ZipEntry entry = new ZipEntry(filesName.get(i));
				salidaFinaldeZips.putNextEntry(entry);
				int read = 0;
				while ((read = fis.read(buffer)) != -1) {
					salidaFinaldeZips.write(buffer, 0, read);
				}
				salidaFinaldeZips.closeEntry();
				fis.close();
			}
			salidaFinaldeZips.close();
			baosdeZips.close();

			salidaFinaldeZips.close();
			setInputStream(new ByteArrayInputStream(baosdeZips.toByteArray()));
			setFileName("peticionesReconocimiento.zip");
			return RESULT_DOWNLOAD_ZIP;
		}
	}

	public String cargarPopUp() {
		return RESULT_POPUP;
	}

	public String cargarPopUpFechaVisita() {
		return RESULT_POPUP_FECHA_VISITA;
	}

	public String eliminar() throws Throwable {
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			addActionError(SIN_PERMISOS_PARA_EJECUTAR_ACCION);
			return RESULT_LISTADO;
		}

		for (String codSeleccionado: getIdsReconocimientos().split(",")) {
			ResultBean resultado = modeloReconocimiento.remove(Long.valueOf(codSeleccionado));
			if (resultado.getError()) {
				for (int i = 0; i < resultado.getListaMensajes().size(); i++) {
					if (resultado.getListaMensajes().get(i) != null) {
						addActionError(resultado.getListaMensajes().get(i).replace("--", " -"));
					}
				}
			} else {
				addActionMessage("Petición de reconocimiento médico eliminada correctamente.");
			}
		}

		mantenimientoCamposNavegar();
		buscar();
		persistenciaListados();

		return RESULT_LISTADO;
	}

	public String cambiarEstados() throws Throwable {
		mantenimientoCamposNavegar();
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			addActionError(SIN_PERMISOS_PARA_EJECUTAR_ACCION);
			return RESULT_LISTADO;
		}
		updateEstados();

		buscar();
		persistenciaListados();
		return RESULT_LISTADO;
	}

	public String cambiarEstadosAsistio() throws Throwable {
		mantenimientoCamposNavegar();
		if (!getPermisoClinica()) {
			addActionError(SIN_PERMISOS_PARA_EJECUTAR_ACCION);
			return RESULT_LISTADO;
		}
		setValorEstadoCambio(new BigDecimal(EstadoReconocimientoMedico.Asistio.getValorEnum()));
		updateEstados(new BigDecimal(EstadoReconocimientoMedico.Iniciado.getValorEnum()));

		buscar();
		persistenciaListados();
		return RESULT_LISTADO;
	}

	public String cambiarEstadosNoAsistio() throws Throwable {
		mantenimientoCamposNavegar();
		if (!getPermisoClinica()) {
			addActionError(SIN_PERMISOS_PARA_EJECUTAR_ACCION);
			return RESULT_LISTADO;
		}
		setValorEstadoCambio(new BigDecimal(EstadoReconocimientoMedico.NoAsistio.getValorEnum()));
		updateEstados(new BigDecimal(EstadoReconocimientoMedico.Iniciado.getValorEnum()));

		buscar();
		persistenciaListados();
		return RESULT_LISTADO;
	}

	public String cambiarEstadosAsistioOtraFecha() throws Throwable {
		mantenimientoCamposNavegar();
		if (!getPermisoClinica()) {
			addActionError(SIN_PERMISOS_PARA_EJECUTAR_ACCION);
			return RESULT_LISTADO;
		}
		getFechaVisita().setSegundos("00");
		setValorEstadoCambio(new BigDecimal(EstadoReconocimientoMedico.AsistioDistinaFecha.getValorEnum()));
		updateEstados(new BigDecimal(EstadoReconocimientoMedico.Iniciado.getValorEnum()));

		buscar();
		persistenciaListados();
		return RESULT_LISTADO;
	}

	/**
	 * Método ajax para calcular el importe del reconocimiento
	 * @throws Throwable
	 */
	public void recuperarImporteReconocimiento() throws Throwable {
		log.debug("recuperarImporteReconocimiento");
		String tipo = getReconocimiento().getTipoTramiteRenovacion().getIdTipoTramiteRenovacion();

		if (log.isDebugEnabled()) {
			log.debug("tipo=" + tipo);
			log.debug("fecha="+getFechaReconocimiento());
		}
		String resultado = calcularImporte(tipo, getFechaReconocimiento());
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}

	private String calcularImporte(String tipo, Fecha fecha){
		try {
			BigDecimal importe = modeloReconocimiento.calcularImporte(tipo, fecha);

			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			df.setMinimumFractionDigits(2);

			return df.format(importe.setScale(2, BigDecimal.ROUND_DOWN));
		} catch (Exception e) {
			return "";
		}
	}

	private void updateEstados(BigDecimal... estadosPrevios) {
		List<BigDecimal> permitidos = Arrays.asList(estadosPrevios);
		for (String codSeleccionado: getIdsReconocimientos().split(",")) {
			ReconocimientoMedico recoMedico = modeloReconocimiento.get(Long.valueOf(codSeleccionado), "persona");
			if (!permitidos.isEmpty() && !permitidos.contains(recoMedico.getEstado())) {
				addActionError("Reconocimiento médico del usuario " + recoMedico.getPersona().getId().getNif() + ", no se encuentra en un estado apropiado.");
				continue;
			}

			recoMedico.setEstado(getValorEstadoCambio());
			if (new BigDecimal(EstadoReconocimientoMedico.AsistioDistinaFecha.getValorEnum()).equals(getValorEstadoCambio()) && getFechaVisita() != null) {
				try {
					recoMedico.setFechaRealVisita(getFechaVisita().getFechaHora());
				} catch (Throwable e) {}
			}
			ResultBean resultado = modeloReconocimiento.saveOrUpdate(recoMedico);
			if (resultado.getError()) {
				for (int i = 0; i < resultado.getListaMensajes().size(); i++) {
					if (resultado.getListaMensajes().get(i) != null) {
						addActionError(resultado.getListaMensajes().get(i).replace("--", " -"));
					}
				}
			} else {
				addActionMessage("Reconocimiento médico del usuario " + recoMedico.getPersona().getId().getNif() + ", actualizada correctamente.");
			}
		}
	}

	/**
	 * Método para actualizar los parámetros de la búsqueda. Proceso:
	 * 
	 * -Actualizo los datos de la sesión. -Paso los parámetros de búsqueda.
	 */
	private void persistenciaListados() {
		getSession().put(ConstantesSession.RESULTADOS_PAGINA, getResultadosPorPagina());
		getSession().put(ConstantesSession.CONSULTA_RECONOCIMIENTO_MED, getFiltro());
		getSession().put( ConstantesSession.CONSULTA_LISTA_RECONOCIMIENTO_MED, getListaReconocimiento());
		persistenciaDatosAuxiliares();
	}

	/**
	 * Método para actualizar los parámetros de la Auxiliares. Proceso:
	 * 
	 * -Actualizo los datos de la sesión. -Paso los parámetros de búsqueda.
	 */
	private void persistenciaDatosAuxiliares() {
		getSession().put(ConstantesSession.CONSULTA_CLINICAS_RECONOCIMIENTO_MED, getClinicas());
		getSession().put(ConstantesSession.PERMISO_CLINICAS_RECONOCIMIENTO_MED, getPermisoClinica());
	}

	/**
	 * Método para actualizar los parámetros de paginación. Proceso:
	 * 
	 * -Recupero el bean de búsqueda de sesión. -Actualizo los parámetros de
	 * búsqueda. -Actualizo en sesión los de la paginación.
	 * 
	 */
	private void mantenimientoDatosAuxiliares() {
		@SuppressWarnings("unchecked")
		List<ContratoDto> listaClinicas = (List<ContratoDto>) getSession().get(ConstantesSession.CONSULTA_CLINICAS_RECONOCIMIENTO_MED);
		if (listaClinicas != null) {
			setClinicas(listaClinicas);
		}

		Boolean permisos = (Boolean) getSession().get(ConstantesSession.PERMISO_CLINICAS_RECONOCIMIENTO_MED);
		if (permisos != null) {
			setPermisoClinica(permisos);
		}
	}

	/**
	 * Método para actualizar los parámetros de paginación. Proceso:
	 * 
	 * -Recupero el bean de búsqueda de sesión. -Actualizo los parámetros de
	 * búsqueda. -Actualizo en sesión los de la paginación.
	 * 
	 */
	private void mantenimientoCamposNavegar() {
		ReconocimientoMedicoFilter reconocimientoMedicoFilter = (ReconocimientoMedicoFilter) getSession().get(ConstantesSession.CONSULTA_RECONOCIMIENTO_MED);
		if (reconocimientoMedicoFilter != null) {
			setFiltro(reconocimientoMedicoFilter);
		}

		@SuppressWarnings("unchecked")
		List<ReconocimientoMedico> listaConsulta = (List<ReconocimientoMedico>) getSession().get(ConstantesSession.CONSULTA_LISTA_RECONOCIMIENTO_MED);
		if (listaConsulta != null) {
			setListaReconocimiento(listaConsulta);
		}

		@SuppressWarnings("unchecked")
		List<ContratoDto> listaClinicas = (List<ContratoDto>) getSession().get(ConstantesSession.CONSULTA_CLINICAS_RECONOCIMIENTO_MED);
		if (listaClinicas != null) {
			setClinicas(listaClinicas);
		}

		String resultadosPagina = (String) getSession().get(ConstantesSession.RESULTADOS_PAGINA);
		if (resultadosPagina != null) {
			setResultadosPorPagina(resultadosPagina);
		}

		Boolean permisos = (Boolean) getSession().get(ConstantesSession.PERMISO_CLINICAS_RECONOCIMIENTO_MED);
		if (permisos != null) {
			setPermisoClinica(permisos);
		}
	}

	/**
	 * Evita consultar expedientes que no correspondan con el colegiado
	 * @throws OegamExcepcion 
	 * 
	 */
	private void updateFilter() throws OegamExcepcion {
		if (getFiltro() != null) {
			updateFilterDates();
			if (!getPermisoClinica()) {
				if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
					try {
						getFiltro().setIdContrato(utilesColegiado.getIdContratoSession());
					} catch(Exception e) {
						throw new OegamExcepcion("Error retrieving colegiado");
					}
				}
			} else {
				try {
					getFiltro().setIdClinica(utilesColegiado.getIdContratoSession());
				} catch(Exception e) {
					throw new OegamExcepcion("Error retrieving colegiado");
				}
			}
		}
	}

	/**
	 * Actualiza las fechas de inicio y fin con lo que venga informado en la fecha fraccionada
	 */
	private void updateFilterDates(){
		if (filtro != null) {
			if (filtro.getPeriodoAlta() != null) {
				filtro.setFechaAltaIni(getDate(filtro.getPeriodoAlta().getAnioInicio(),
						filtro.getPeriodoAlta().getMesInicio(),
						filtro.getPeriodoAlta().getDiaInicio(), false));
				filtro.setFechaAltaFin(getDate(filtro.getPeriodoAlta().getAnioFin(),
						filtro.getPeriodoAlta().getMesFin(),
						filtro.getPeriodoAlta().getDiaFin(), true));
			}

			if (filtro.getPeriodoPresentacion() != null) {
				filtro.setFechaReconocimientoIni(getDate(filtro.getPeriodoPresentacion().getAnioInicio(),
						filtro.getPeriodoPresentacion().getMesInicio(),
						filtro.getPeriodoPresentacion().getDiaInicio(), false));
				filtro.setFechaReconocimientoFin(getDate(filtro.getPeriodoPresentacion().getAnioFin(),
						filtro.getPeriodoPresentacion().getMesFin(),
						filtro.getPeriodoPresentacion().getDiaFin(), true));
			}
		}
	}

	private Date getDate(String anio, String mes, String dia, boolean end){
		if (anio != null && !anio.isEmpty()
				&& mes!=null && !mes.isEmpty()
				&& dia!=null && !dia.isEmpty()) {
			try {
				Calendar calendar = Calendar.getInstance();
				if (end) {
					calendar.set(Integer.parseInt(anio), Integer.parseInt(mes)-1, Integer.parseInt(dia), 23, 59);
				} else {
					calendar.set(Integer.parseInt(anio), Integer.parseInt(mes)-1, Integer.parseInt(dia), 0, 0);
				}
				return calendar.getTime();
			} catch(Throwable t) {}
		}
		return null;
	}

	// Getters y Setters

	public EstadoReconocimientoMedico[] getComboEstados() {
		return EstadoReconocimientoMedico.values();
	}

	public ReconocimientoMedicoFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(ReconocimientoMedicoFilter filtro) {
		this.filtro = filtro;
	}

	public Long getIdReconocimiento() {
		return idReconocimiento;
	}

	public void setIdReconocimiento(Long idReconocimiento) {
		this.idReconocimiento = idReconocimiento;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getResultadosPorPagina() {
		return resultadosPorPagina;
	}

	public void setResultadosPorPagina(String resultadosPorPagina) {
		this.resultadosPorPagina = resultadosPorPagina;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public ReconocimientoMedico getReconocimiento() {
		return reconocimiento;
	}

	public void setReconocimiento(ReconocimientoMedico reconocimiento) {
		this.reconocimiento = reconocimiento;
	}

	public List<ReconocimientoMedico> getListaReconocimiento() {
		return listaReconocimiento;
	}

	public void setListaReconocimiento(List<ReconocimientoMedico> listaReconocimiento) {
		this.listaReconocimiento = listaReconocimiento;
	}

	public List<ContratoDto> getClinicas() {
		return clinicas;
	}

	public void setClinicas(List<ContratoDto> clinicas) {
		this.clinicas = clinicas;
	}

	public Fecha getFechaReconocimiento() {
		return fechaReconocimiento;
	}

	public void setFechaReconocimiento(Fecha fechaReconocimiento) {
		this.fechaReconocimiento = fechaReconocimiento;
	}

	public Fecha getFcaducidad() {
		return fcaducidad;
	}

	public void setFcaducidad(Fecha fcaducidad) {
		this.fcaducidad = fcaducidad;
	}

	public Fecha getFnacimiento() {
		return fnacimiento;
	}

	public void setFnacimiento(Fecha fnacimiento) {
		this.fnacimiento = fnacimiento;
	}

	public String getIdsReconocimientos() {
		return idsReconocimientos;
	}

	public void setIdsReconocimientos(String idsReconocimientos) {
		this.idsReconocimientos = idsReconocimientos;
	}

	public BigDecimal getValorEstadoCambio() {
		return valorEstadoCambio;
	}

	public void setValorEstadoCambio(BigDecimal valorEstadoCambio) {
		this.valorEstadoCambio = valorEstadoCambio;
	}

	public Boolean getPermisoClinica() {
		return permisoClinica;
	}

	public void setPermisoClinica(Boolean permisoClinica) {
		this.permisoClinica = permisoClinica;
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

	public Boolean getImpresoEspera() {
		return impresoEspera;
	}

	public void setImpresoEspera(Boolean impresoEspera) {
		this.impresoEspera = impresoEspera;
	}

	public Fecha getFechaVisita() {
		return fechaVisita;
	}

	public void setFechaVisita(Fecha fechaVisita) {
		this.fechaVisita = fechaVisita;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}

	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}

}