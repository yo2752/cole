package escrituras.acciones;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.core.model.enumerados.Estado;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;

import escrituras.beans.ColegioBean;
import escrituras.beans.JefaturaBean;
import escrituras.beans.MunicipioBean;
import escrituras.beans.Provincia;
import escrituras.beans.ResultBean;
import escrituras.beans.TipoVia;
import escrituras.beans.contratos.AplicacionContratoBean;
import escrituras.beans.contratos.ConsultaContratoBean;
import escrituras.beans.contratos.ContratoBean;
import escrituras.beans.contratos.FuncionAplicacionContratoBean;
import escrituras.beans.contratos.PermisoUsuarioContratoBean;
import escrituras.beans.contratos.UsuarioContratoBean;
import escrituras.modelo.ModeloContratoNuevo;
import escrituras.utiles.PaginatedListImpl;
import escrituras.utiles.UtilesVista;
import general.acciones.ActionBase;
import general.utiles.Anagrama;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ConstantesSession;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;

public class ContratoNuevoAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 1L;
	private static final String CONSULTA_CONTRATO = "consultaContrato";
	private static final String MODIFICAR_CONTRATO = "modificarContrato";
	private static final String NO_SE_PUDO_RECUPERAR_LISTA = "No se pudo recuperar la lista.";

	// log de errores
	private static final ILoggerOegam log = LoggerOegam.getLogger(ContratoNuevoAction.class);

	/*
	 * Elementos comunes a todos los action de listas, tales como la página actual (para la paginación), si está ordenado por una columna, si el ordenamiento es ascendente o descendente, la cantidad de elementos a mostrar por página, entre otros
	 */
	// Parámetros de las peticiones del displayTag
	// Lista que se va a mostrar en la vista es de tipo PaginatedList para el uso por displayTags
	private PaginatedListImpl listaContratos;
	private String page; // Página a mostrar
	private String sort; // Columna por la que se ordena
	private String dir; // Orden de la ordenación
	private String resultadosPorPagina; // La cantidad de elementos a mostrar por página

	// Parámetros generales de los listar
	// Se utiliza para asignar los parámetros de búsqueda al objeto DAO
	HashMap<String, Object> parametrosBusqueda;
	// Para las selecciones de una vista, si no queremos hacer una nueva llamada a BD.
	ArrayList<Object> listaBeanVista = new ArrayList<>();

	private ConsultaContratoBean consultaContrato;

	private String[] idContratos; // Para los contratos en bloque.
	private String[] idAplicaciones; // Para las aplicaciones en bloque.
	private String[] idUsuarios; // Para los usuarios en bloque.
	private String[] idUsuariosContrato;
	private String[] permisosUsuarios; // Para los permisos de usuario en bloque.
	private String[] permisosAplicaciones; // Para los permisos de aplicaciones en bloque.
	private String idContrato;
	private String idUsuarioActivo;
	private String idUsuarioSeleccionado;

	private String codAplicacionActiva;
	private String codAplicacionSeleccionada;

	private ContratoBean contratoBean; // Contrato de pantalla
	private UsuarioContratoBean usuarioBean; // Usuario de pantalla de un alta
	private UsuarioContratoBean usuarioSeleccionado; // Usuario de pantalla seleccionado

	private List<Provincia> provincias; // Provincias que se mostrarán en el
	// combo (al insertar o modificar un contrato y en el listado)
	private List<MunicipioBean> municipios;
	private List<ColegioBean> colegios;
	private List<JefaturaBean> jefaturas;
	private List<TipoVia> tiposVia;

	private Boolean altaNuevo = false;
	private Boolean usuarioSel = false;
	private Boolean aplicacionSel = false;

	private String ASIGNAR = "1";
	private String DESASIGNAR = "0";
	private List<FuncionAplicacionContratoBean> listaPermisosAplicacion;

	private static final String POP_UP_MOTIVO = "popPupMotivo";
	private static final String POP_UP_EVOLUCION = "irEvolucionContrato";
	private String motivo;
	private String solicitante;
	private String codSeleccionados;
	private Long idContratoAnt;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	ModeloContratoNuevo modeloContratoNuevo;

	@Autowired
	UtilesColegiado utilesColegiado;

	// MÉTODOS

	public String cargarPopUpMotivo() {
		return POP_UP_MOTIVO;
	}

	public String abrirEvolucion() {
		if (idContrato != null && !idContrato.isEmpty()) {
			idContratoAnt = Long.parseLong(idContrato);
			return POP_UP_EVOLUCION;
		}
		addActionError("No ha seleccionado ningún contrato para obtener su evolucion.");
		return CONSULTA_CONTRATO;
	}

	/**
	 * Método con el que comenzará las funcionalidades de contratos mostrando los contratos por defecto.
	 */
	public String inicio() throws Throwable {
		limpiarCamposSession();

		// Colocar en la sessión las cosas básicas ya que es la primera vez
		getSession().put(ConstantesSession.RESULTADOS_PAGINA, UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO);

		// INICIO Mantis 0013076: Tratamiento de objetos de sesión
		obtenerDeSesionOInicializarConsultaContrato();
		obtenerDeSesionOInicializarListaContratos();
		// FIN Mantis 0013076

		return CONSULTA_CONTRATO;
	}

	/* INICIO Mantis 0013076: tratamiento de objetos de sesión */
	/**
	 * Método que inicializa el Bean 'consultaContrato'
	 */
	private void obtenerDeSesionOInicializarConsultaContrato() {
		if (consultaContrato == null) {
			ConsultaContratoBean consultaContratoBeanSession = (ConsultaContratoBean) getSession().get(getCriteriosSession());
			setConsultaContrato(consultaContratoBeanSession == null ? new ConsultaContratoBean() : consultaContratoBeanSession);
		}
		getSession().put(getCriteriosSession(), consultaContrato);
	}

	/**
	 * Método que inicializa el Bean 'listaContratos'
	 * @throws Throwable
	 */
	private void obtenerDeSesionOInicializarListaContratos() {
		try {
			PaginatedListImpl listaContratosSession = (PaginatedListImpl) getSession().get(getCriterioPaginatedList());

			if (listaContratosSession == null) {
				parametrosBusqueda = new HashMap<>();
				listaContratos = new PaginatedListImpl();

				listaContratos.setBaseDAO(modeloContratoNuevo);
				listaContratos.getBaseDAO().setParametrosBusqueda(parametrosBusqueda);
				getSession().put(getCriterioPaginatedList(), listaContratos);
			} else {
				setParametrosBusqueda((HashMap<String, Object>) listaContratosSession.getBaseDAO().getParametrosBusqueda());
				setListaContratos(listaContratosSession);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private String getCriterioPaginatedList() {
		return "criterioPaginatedListContratoNuevoAction";
	}

	public String getCriteriosSession() {
		return "contratoNuevoActionBeanSesion";
	}
	/* FIN Mantis 0013076 */

	public String lista() throws Throwable {
		return buscar();
	}

	/**
	 * Función principal de búsqueda, que dependiendo de si es la primera vez u otra vez que se ejecuta accederá al modelo de tráfico para realizar la búsqueda con los parámetros que hayamos pasado.
	 * @return
	 * @throws Throwable
	 */
	public String buscar() throws Throwable {
		log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "buscar.");

		mantenimientoCamposBuscar();

		if (listaContratos == null) {
			log.error(Claves.CLAVE_LOG_ESCRITURA + " al obtener lista de Contratos.");
			log.info(Claves.CLAVE_LOG_ESCRITURA + " buscar.");
			addActionError(NO_SE_PUDO_RECUPERAR_LISTA);
			return CONSULTA_CONTRATO;
		}

		listaContratos.establecerParametros(getSort(), getDir(), "1", getResultadosPorPagina());
		listaContratos.getBaseDAO().setParametrosBusqueda(parametrosBusqueda);
		getSession().put(getCriterioPaginatedList(), listaContratos);

		return CONSULTA_CONTRATO;
	}

	/**
	 * Función de búsqueda para la paginación. Generará la búsqueda limitada por los parámetros de paginación.
	 * @return
	 */
	public String navegar() {
		mantenimientoCamposNavegar();
		if (listaContratos == null) {
			log.error(Claves.CLAVE_LOG_ESCRITURA + " al obtener lista de Contratos.");
			log.info(Claves.CLAVE_LOG_ESCRITURA + " buscar.");
			addActionError(NO_SE_PUDO_RECUPERAR_LISTA);
			return CONSULTA_CONTRATO;
		}

		listaContratos.establecerParametros(getSort(), getDir(), getPage(), getResultadosPorPagina());
		listaContratos.getBaseDAO().setParametrosBusqueda(parametrosBusqueda);
		getSession().put(getCriterioPaginatedList(), listaContratos);

		return CONSULTA_CONTRATO;
	}

	/**
	 * Función que habilitará en bloque los contratos que hayamos seleccionado en la pantalla.
	 * @return
	 */
	public String habilitar() {
		mantenimientoCamposNavegar();
		if (listaContratos == null) {
			log.error(Claves.CLAVE_LOG_ESCRITURA + " al obtener lista de Contratos.");
			log.info(Claves.CLAVE_LOG_ESCRITURA + " habilitar.");
			addActionError(NO_SE_PUDO_RECUPERAR_LISTA);
			return CONSULTA_CONTRATO;
		}
		if (codSeleccionados == null || codSeleccionados.isEmpty()) {
			addActionError("Debe seleccionar algún contrato para habilitar.");
			return CONSULTA_CONTRATO;
		} else {
			idContratos = codSeleccionados.split("_");
		}
		for (String idContr : getIdContratos()) {
			ContratoBean contrato = obtenerBeanConIdContrato(idContr);

			if (contrato == null) {
				addActionError("Contrado con número " + idContr + " no se ha encontrado.");
			} else {
				/* INICIO Mantis 0011494: (ihgl) se mostrará la razón social del contrato */
				String razonSocial = (contrato.getDatosContrato() != null && contrato.getDatosContrato().getRazonSocial() != null) ? contrato.getDatosContrato().getRazonSocial() : "";

				if (contrato.getDatosContrato().getEstadoContrato().getNombreEnum().equals(Estado.Habilitado.getNombreEnum())) {
					addActionMessage("El contrato '" + razonSocial + "' ya está habilitado.");
				}
				if (contrato.getDatosContrato().getEstadoContrato().getNombreEnum().equals(Estado.Eliminado.getNombreEnum())) {
					addActionError("El contrato '" + razonSocial + "' está eliminado, no se puede habilitar.");
				}
				if (motivo == null || motivo.isEmpty()) {
					addActionError("Debe seleccioanr el motivo para poder habilitar el contrato.");
					return CONSULTA_CONTRATO;
				}
				if (solicitante == null || solicitante.isEmpty()) {
					addActionError("Debe seleccioanr el solicitante para poder habilitar el contrato.");
					return CONSULTA_CONTRATO;
				}
				// En este caso es cuando el contrato se pasará al estado habilitado.
				if (contrato.getDatosContrato().getEstadoContrato().getNombreEnum().equals(Estado.Deshabilitado.getNombreEnum())) {
					ResultBean resultadoModelo = modeloContratoNuevo.habilitarContrato(new BigDecimal(idContr), utilesColegiado.getIdUsuarioSessionBigDecimal(), motivo, solicitante);

					if (resultadoModelo.getError()) {
						addActionError("Error al habilitar el contrato '" + razonSocial + "': " + resultadoModelo.getMensaje() + ".");
					} else {
						addActionMessage("El contrato '" + razonSocial + "' se ha habilitado.");
					}
				}
				/* FIN Mantis 0011494 (ihgl) */
			}
		}
		listaContratos.establecerParametros(getSort(), getDir(), getPage(), getResultadosPorPagina());
		listaContratos.getBaseDAO().setParametrosBusqueda(parametrosBusqueda);
		getSession().put(getCriterioPaginatedList(), listaContratos);

		return CONSULTA_CONTRATO;
	}

	/**
	 * Función que deshabilitará en bloque los contratos que hayamos seleccionado en la pantalla.
	 * @return
	 */
	public String deshabilitar() {
		mantenimientoCamposNavegar();
		if (listaContratos == null) {
			log.error(Claves.CLAVE_LOG_ESCRITURA + " al obtener lista de Contratos.");
			log.info(Claves.CLAVE_LOG_ESCRITURA + " deshabilitar.");
			addActionError(NO_SE_PUDO_RECUPERAR_LISTA);
			return CONSULTA_CONTRATO;
		}
		if (codSeleccionados == null || codSeleccionados.isEmpty()) {
			addActionError("Debe seleccionar algún contrato para deshabilitar.");
			return CONSULTA_CONTRATO;
		} else {
			idContratos = codSeleccionados.split("_");
		}
		for (String idContr : getIdContratos()) {
			ContratoBean contrato = obtenerBeanConIdContrato(idContr);

			if (contrato == null) {
				addActionError("El contrato con número '" + idContr + "' no se ha encontrado.");
			} else {
				/* INICIO Mantis 0011494: (ihgl) se mostrará la razón social del contrato */
				String razonSocial = (contrato.getDatosContrato() != null && contrato.getDatosContrato().getRazonSocial() != null) ? contrato.getDatosContrato().getRazonSocial() : "";

				if (contrato.getDatosContrato().getEstadoContrato().getNombreEnum().equals(Estado.Deshabilitado.getNombreEnum())) {
					addActionMessage("El contrato '" + razonSocial + "' ya está deshabilitado.");
				}
				if (contrato.getDatosContrato().getEstadoContrato().getNombreEnum().equals(Estado.Eliminado.getNombreEnum())) {
					addActionError("El contrato '" + razonSocial + "' está eliminado, no se puede deshabilitar.");
				}
				if (motivo == null || motivo.isEmpty()) {
					addActionError("Debe seleccionar el motivo para poder deshabilitar el contrato.");
					return CONSULTA_CONTRATO;
				}
				if (solicitante == null || solicitante.isEmpty()) {
					addActionError("Debe seleccionar el solicitante para poder deshabilitar el contrato.");
					return CONSULTA_CONTRATO;
				}
				// En este caso es cuando el contrato se pasará al estado habilitado.
				if (contrato.getDatosContrato().getEstadoContrato().getNombreEnum().equals(Estado.Habilitado.getNombreEnum())) {
					ResultBean resultadoModelo = modeloContratoNuevo.deshabilitarContrato(new BigDecimal(idContr), utilesColegiado.getIdUsuarioSessionBigDecimal(), motivo, solicitante);

					if (resultadoModelo.getError()) {
						addActionError("Error al deshabilitar el contrato '" + razonSocial + "': " + resultadoModelo.getMensaje() + ".");
					} else {
						addActionMessage("El contrato '" + razonSocial + "' se ha deshabilitado.");
					}
				}
				/* FIN Mantis 0011494 (ihgl) */
			}
		}

		listaContratos.establecerParametros(getSort(), getDir(), getPage(), getResultadosPorPagina());
		listaContratos.getBaseDAO().setParametrosBusqueda(parametrosBusqueda);
		getSession().put(getCriterioPaginatedList(), listaContratos);

		return CONSULTA_CONTRATO;
	}

	/**
	 * Función que eliminará en bloque los contratos que hayamos seleccionado en la pantalla.
	 * @return
	 */
	public String eliminar() {
		mantenimientoCamposNavegar();
		if (listaContratos == null) {
			log.error(Claves.CLAVE_LOG_ESCRITURA + " al obtener lista de Contratos.");
			log.info(Claves.CLAVE_LOG_ESCRITURA + " eliminar.");
			addActionError(NO_SE_PUDO_RECUPERAR_LISTA);
			return CONSULTA_CONTRATO;
		}
		if (codSeleccionados == null || codSeleccionados.isEmpty()) {
			addActionError("Debe seleccionar algún contrato para eliminar.");
			return CONSULTA_CONTRATO;
		} else {
			idContratos = codSeleccionados.split("_");
		}
		for (String idContr : getIdContratos()) {
			ContratoBean contrato = obtenerBeanConIdContrato(idContr);

			if (contrato == null) {
				addActionError("El contrato con número '" + idContr + "' no se ha encontrado.");
			} else {
				/* INICIO Mantis 0011494: (ihgl) se mostrará la razón social del contrato */
				String razonSocial = (contrato.getDatosContrato() != null
						&& contrato.getDatosContrato().getRazonSocial() != null)
								? contrato.getDatosContrato().getRazonSocial()
								: "";

				if (contrato.getDatosContrato().getEstadoContrato().getNombreEnum().equals(Estado.Eliminado.getNombreEnum())) {
					addActionMessage("El contrato '" + razonSocial + "' ya está eliminado.");
				}
				if (motivo == null || motivo.isEmpty()) {
					addActionError("Debe seleccionar el motivo para poder eliminar el contrato.");
					return CONSULTA_CONTRATO;
				}
				if (solicitante == null || solicitante.isEmpty()) {
					addActionError("Debe seleccionar el solicitante para poder eliminar el contrato.");
					return CONSULTA_CONTRATO;
				}
				// En este caso es cuando el contrato se pasará al estado
				// eliminado
				if (contrato.getDatosContrato().getEstadoContrato().getNombreEnum().equals(Estado.Habilitado.getNombreEnum()) || contrato.getDatosContrato().getEstadoContrato().getNombreEnum().equals(
						Estado.Deshabilitado.getNombreEnum())) {

					ResultBean resultadoModelo = modeloContratoNuevo.eliminarContrato(new BigDecimal(idContr), utilesColegiado.getIdUsuarioSessionBigDecimal(), motivo, solicitante);

					if (resultadoModelo.getError()) {
						addActionError("Error al eliminar el contrato '" + razonSocial + "': " + resultadoModelo.getMensaje() + ".");
					} else {
						addActionMessage("El contrato '" + razonSocial + "' se ha eliminado.");
					}
				}
				/* FIN Mantis 0011494 (ihgl) */
			}
		}

		listaContratos.establecerParametros(getSort(), getDir(), getPage(), getResultadosPorPagina());
		listaContratos.getBaseDAO().setParametrosBusqueda(parametrosBusqueda);
		getSession().put(getCriterioPaginatedList(), listaContratos);

		return CONSULTA_CONTRATO;
	}

	/**
	 * Irá al formulario para introducir un nuevo contrato.
	 * @return
	 */
	public String nuevo() {
		setAltaNuevo(true);
		return MODIFICAR_CONTRATO;
	}

	/**
	 * Función que modificará un contrato que hayamos seleccionado en la pantalla.
	 * @return
	 */
	public String modificar() {
		contratoBean = new ContratoBean(true);
		ResultBean resultadoModelo = modeloContratoNuevo.obtenerDetalleContrato(new BigDecimal(idContrato));
		ContratoBean contrato = new ContratoBean();
		limpiarCamposSession();

		log.info(Claves.CLAVE_LOG_ESCRITURA + " modificar.");

		contrato = (ContratoBean) resultadoModelo.getAttachment(ConstantesPQ.BEANPANTALLA);
		if (resultadoModelo.getError() || null == contrato) {
			log.error(Claves.CLAVE_LOG_ESCRITURA + " al obtener lista de Contratos.");
			addActionError("Error al obtener datos del contrato: " + resultadoModelo.getMensaje());
			return CONSULTA_CONTRATO;
		}

		contratoBean = contrato;

		getSession().put(ConstantesSession.LISTA_APLICACIONES_CONTRATO, contratoBean.getListaAplicaciones());
		getSession().put(ConstantesSession.LISTA_USUARIOS_CONTRATO, contratoBean.getListaUsuarios());

		return MODIFICAR_CONTRATO;
	}

	/**
	 * Función que guardará un contrato que hayamos seleccionado en la pantalla.
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	public String guardar() throws ParseException {
		ContratoBean contrato = new ContratoBean();
		ArrayList<AplicacionContratoBean> listaAplicaciones = (ArrayList<AplicacionContratoBean>) getSession().get(ConstantesSession.LISTA_APLICACIONES_CONTRATO);
		ArrayList<UsuarioContratoBean> listaUsuarios = (ArrayList<UsuarioContratoBean>) getSession().get(ConstantesSession.LISTA_USUARIOS_CONTRATO);
		ArrayList<AplicacionContratoBean> listaAplicacionesAuxiliar = new ArrayList<>();

		if (altaNuevo) {
			contratoBean.getDatosContrato().setFechaInicio(utilesFecha.getFechaActual());
			contratoBean.getDatosContrato().setEstadoContrato(Estado.Habilitado.getValorEnum().toString());
			contratoBean.getDatosContrato().setIdTipoContrato(new BigDecimal(1));
			if (null != contratoBean.getDatosColegiado().getNif() && !contratoBean.getDatosColegiado().getNif().equals("")) {
				contratoBean.getDatosColegiado().setEstadoUsuario(Estado.Habilitado.getValorEnum().toString());
				if ((null == contratoBean.getDatosColegiado().getAnagrama()
						|| contratoBean.getDatosColegiado().getAnagrama().equals(""))
						&& (null != contratoBean.getDatosColegiado().getApellido1()
								&& !contratoBean.getDatosColegiado().getApellido1().equals(""))) {

					String anagramaColegiado = Anagrama.obtenerAnagramaFiscal(contratoBean.getDatosColegiado().getApellido1().toUpperCase(), contratoBean.getDatosColegiado().getNif());

					contratoBean.getDatosColegiado().setAnagrama(anagramaColegiado);
				}
			}

			ResultBean result = servicioContrato.comprobarMismaViaContratos(contratoBean);
			if (result != null && result.getError()) {
				addActionError(result.getListaMensajes().get(0));
				return MODIFICAR_CONTRATO;
			}
		}

		if (null == contratoBean.getDatosContrato().getAnagramaContrato() || contratoBean.getDatosContrato().getAnagramaContrato().equals("")) {
			String anagramaContrato = Anagrama.obtenerAnagramaFiscal(contratoBean.getDatosContrato().getRazonSocial().toUpperCase(), contratoBean.getDatosContrato().getCif());
			contratoBean.getDatosContrato().setAnagramaContrato(anagramaContrato);
		}

		ResultBean resultadoModelo = new ResultBean();
		log.info(Claves.CLAVE_LOG_ESCRITURA + " guardar.");

		resultadoModelo = modeloContratoNuevo.guardarContrato(contratoBean);

		contrato = (ContratoBean) resultadoModelo.getAttachment(ConstantesPQ.BEANPANTALLA);

		/* INICIO Mantis 0011494: (ihgl) se mostrará la razón social del contrato */
		String razonSocial = (contratoBean.getDatosContrato() != null && contratoBean.getDatosContrato().getRazonSocial() != null) ? contratoBean.getDatosContrato().getRazonSocial() : "";
		/* FIN Mantis 0011494 (ihgl) */

		if (resultadoModelo.getError() || null == contrato) {
			log.error(Claves.CLAVE_LOG_ESCRITURA + " al guardar un contrato.");
			/* INICIO Mantis 0011494: (ihgl) se mostrará la razón social del contrato */
			addActionError("Error al guardar el contrato '" + razonSocial + "': " + resultadoModelo.getMensaje() + ".");
			/* FIN Mantis 0011494 (ihgl) */
		} else {
			if (altaNuevo) {
				altaNuevo = false;
				idContrato = String.valueOf(contrato.getIdContrato());
				/* INICIO Mantis 0011494: (ihgl) se mostrará la razón social del contrato */
				addActionMessage("El contrato '" + razonSocial + "' se ha creado correctamente.");
				/* FIN Mantis 0011494 (ihgl) */
				// Si es un alta nueva vamos a coger el gestor del detalle de contrato y pondremos el boolean a false.
				return modificar();
			} else {
				for (AplicacionContratoBean aplicacionContratoBean : listaAplicaciones) {
					Boolean asignar = false;
					Boolean desasignar = true;
					ResultBean resultadoMod = new ResultBean();
					resultadoMod.setError(false);
					Integer longitud = null != idAplicaciones ? idAplicaciones.length : 0;

					if (!aplicacionContratoBean.getAsignada()) {
						desasignar = false;
						for (int i = 0; i < longitud; i++) {
							if (aplicacionContratoBean.getCodigo_Aplicacion().equals(idAplicaciones[i])) {
								asignar = true;
								break;
							}
						}
					} else if (aplicacionContratoBean.getAsignada()) {
						for (int i = 0; i < longitud; i++) {
							if (aplicacionContratoBean.getCodigo_Aplicacion().equals(idAplicaciones[i])) {
								desasignar = false;
								break;
							}
						}
					}

					if (asignar) {
						aplicacionContratoBean.setAsignada(true);
						resultadoMod = modeloContratoNuevo.asociarAplicacionContrato(aplicacionContratoBean.getCodigo_Aplicacion(), null != contratoBean.getIdContrato() ? contratoBean.getIdContrato()
								.toString() : "", 1);

						if ("OEGAM_TRAF".equals(aplicacionContratoBean.getCodigo_Aplicacion())) {
							String permisosPorDefecto = gestorPropiedades.valorPropertie("permisos.noHabilitados.defecto.trafico");
							if (permisosPorDefecto != null) {
								String[] permisos = permisosPorDefecto.split(",");
								for (String permiso : permisos) {
									modeloContratoNuevo.cambiarPermisoAplicacion(contratoBean.getIdContrato(), permiso, "OEGAM_TRAF", DESASIGNAR);
								}
							}
						}
						if ("OEGAM_SEGSOC".equals(aplicacionContratoBean.getCodigo_Aplicacion())) {
							String permisosPorDefecto = gestorPropiedades.valorPropertie("permisos.noHabilitados.defecto.seguridadsocial");
							if (permisosPorDefecto != null) {
								String[] permisos = permisosPorDefecto.split(",");
								for (String permiso : permisos) {
									modeloContratoNuevo.cambiarPermisoAplicacion(contratoBean.getIdContrato(), permiso, "OEGAM_SEGSOC", DESASIGNAR);
								}
							}
						}
						if ("OEGAM_GENE".equals(aplicacionContratoBean.getCodigo_Aplicacion())) {
							String permisosPorDefecto = gestorPropiedades.valorPropertie("permisos.noHabilitados.defecto.general");
							if (permisosPorDefecto != null) {
								String[] permisos = permisosPorDefecto.split(",");
								for (String permiso : permisos) {
									modeloContratoNuevo.cambiarPermisoAplicacion(contratoBean.getIdContrato(), permiso, "OEGAM_GENE", DESASIGNAR);
								}
							}
						}
						if ("OEGAM_DIG".equals(aplicacionContratoBean.getCodigo_Aplicacion())) {
							String permisosPorDefecto = gestorPropiedades.valorPropertie("permisos.noHabilitados.defecto.digitalizacion");
							if (permisosPorDefecto != null) {
								String[] permisos = permisosPorDefecto.split(",");
								for (String permiso : permisos) {
									modeloContratoNuevo.cambiarPermisoAplicacion(contratoBean.getIdContrato(), permiso, "OEGAM_DIG", DESASIGNAR);
								}
							}
						}
					} else if (desasignar) {
						aplicacionContratoBean.setAsignada(false);
						resultadoMod = modeloContratoNuevo.asociarAplicacionContrato(
								aplicacionContratoBean.getCodigo_Aplicacion(),
								null != contratoBean.getIdContrato() ? contratoBean.getIdContrato().toString() : "", 0);
					}

					listaAplicacionesAuxiliar.add(aplicacionContratoBean);
					if (resultadoMod.getError()) {
						resultadoModelo.setError(true);
						addActionError("Ha habido un error asignando/desasignando aplicaciones." + aplicacionContratoBean.getCodigo_Aplicacion());
					}
				}
			}

			if (!resultadoModelo.getError()) {
				/* INICIO Mantis 0011494: (ihgl) se mostrará la razón social del contrato */
				addActionMessage("Contrato '" + razonSocial + "' guardado.");
				/* FIN Mantis 0011494 (ihgl) */
			}

		}

		contratoBean.setListaAplicaciones(listaAplicacionesAuxiliar);
		contratoBean.setListaUsuarios(listaUsuarios);
		return MODIFICAR_CONTRATO;
	}

	/**
	 * Modifica el email del contrato
	 * @return cadena identificativa para la vista struts
	 */
	public String modificarEmail() {
		try {
			BigDecimal idContrato = contratoBean.getIdContrato();
			String emailModificar = contratoBean.getDatosContrato().getCorreoElectronico();
			if (idContrato == null || emailModificar == null || emailModificar.equals("")) {
				addActionError("Señale el nuevo correo electrónico");
				return MODIFICAR_CONTRATO;
			}
			ResultBean resultadoModelo = new ResultBean();
			resultadoModelo = modeloContratoNuevo.modificarEmailContrato(idContrato, emailModificar);
			if (!resultadoModelo.getError()) {
				addActionMessage("Correo electrónico modificado.");
			} else {
				addActionError(resultadoModelo.getMensaje());
			}
			return MODIFICAR_CONTRATO;
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			return Action.ERROR;
		}
	}

	/**
	 * Función que habilitará en bloque los usuarios que hayamos seleccionado en la pantalla.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String habilitarUsuarios() {
		ArrayList<AplicacionContratoBean> listaAplicaciones = (ArrayList<AplicacionContratoBean>) getSession().get(ConstantesSession.LISTA_APLICACIONES_CONTRATO);
		ArrayList<UsuarioContratoBean> listaUsuarios = (ArrayList<UsuarioContratoBean>) getSession().get(ConstantesSession.LISTA_USUARIOS_CONTRATO);
		ArrayList<UsuarioContratoBean> listaUsuariosAux = new ArrayList<>();
		String numColegiado = contratoBean.getDatosColegiado().getNumColegiado();

		for (String idUsuario : getIdUsuarios()) {
			UsuarioContratoBean usuario = obtenerUsuarioLista(idUsuario, listaUsuarios);

			if (usuario == null) {
				addActionError("El usuario con número '" + idUsuario + "' no se ha encontrado.");
			} else {
				/* INICIO Mantis 0011494: (ihgl) se mostrará el nombre y apellidos del usuario en lugar de su ID */
				String apellidosNombre = (usuario.getApellidosNombre() != null) ? usuario.getApellidosNombre() : "";

				if (usuario.getEstadoUsuario().getNombreEnum().equals(Estado.Habilitado.getNombreEnum())) {
					addActionMessage("El usuario '" + apellidosNombre + "' ya está habilitado.");
				}
				if (usuario.getEstadoUsuario().getNombreEnum().equals(Estado.Eliminado.getNombreEnum())) {
					addActionError("El usuario '" + apellidosNombre + "' está eliminado, no se puede habilitar.");
				}
				// En este caso es cuando el usuario se pasará al estado habilitado.
				if (usuario.getEstadoUsuario().getNombreEnum().equals(Estado.Deshabilitado.getNombreEnum())) {
					ResultBean resultadoModelo = modeloContratoNuevo.habilitarUsuario(usuario.getIdUsuario());

					if (resultadoModelo.getError()) {
						addActionError("Error al habilitar el usuario '" + apellidosNombre + "': " + resultadoModelo.getMensaje() + ".");
					} else {
						addActionMessage("El usuario '" + apellidosNombre + "' se ha habilitado.");
					}
				}
				/* FIN Mantis 0011494 (ihgl) */
			}
		}

		listaUsuariosAux = (ArrayList<UsuarioContratoBean>) modeloContratoNuevo.obtenerUsuariosContrato(numColegiado);

		contratoBean.setListaAplicaciones(listaAplicaciones);
		contratoBean.setListaUsuarios(listaUsuariosAux);
		getSession().put(ConstantesSession.LISTA_USUARIOS_CONTRATO, contratoBean.getListaUsuarios());
		return MODIFICAR_CONTRATO;
	}

	/**
	 * Función que deshabilitará en bloque los usuarios que hayamos seleccionado en la pantalla.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String deshabilitarUsuarios() {
		ArrayList<AplicacionContratoBean> listaAplicaciones = (ArrayList<AplicacionContratoBean>) getSession().get(ConstantesSession.LISTA_APLICACIONES_CONTRATO);
		ArrayList<UsuarioContratoBean> listaUsuarios = (ArrayList<UsuarioContratoBean>) getSession().get(ConstantesSession.LISTA_USUARIOS_CONTRATO);
		ArrayList<UsuarioContratoBean> listaUsuariosAux = new ArrayList<>();
		String numColegiado = contratoBean.getDatosColegiado().getNumColegiado();

		for (String idUsuario : getIdUsuarios()) {
			UsuarioContratoBean usuario = obtenerUsuarioLista(idUsuario, listaUsuarios);

			if (usuario == null) {
				addActionError("El usuario con número '" + idUsuario + "' no se ha encontrado.");
			} else {
				/* INICIO Mantis 0011494: (ihgl) se mostrará el nombre y apellidos del usuario en lugar de su ID */
				String apellidosNombre = (usuario.getApellidosNombre() != null) ? usuario.getApellidosNombre() : "";

				if (usuario.getEstadoUsuario().getNombreEnum().equals(Estado.Deshabilitado.getNombreEnum())) {
					addActionMessage("El usuario '" + apellidosNombre + "' ya está deshabilitado.");
				}
				if (usuario.getEstadoUsuario().getNombreEnum().equals(Estado.Eliminado.getNombreEnum())) {
					addActionError("El usuario '" + apellidosNombre + "' está eliminado, no se puede deshabilitar.");
				}
				// En este caso es cuando el contrato se pasará al estado habilitado.
				if (usuario.getEstadoUsuario().getNombreEnum().equals(Estado.Habilitado.getNombreEnum())) {

					ResultBean resultadoModelo = modeloContratoNuevo.deshabilitarUsuario(usuario.getIdUsuario());

					if (resultadoModelo.getError()) {
						addActionError("Error al deshabilitar el usuario '" + apellidosNombre + "': " + resultadoModelo.getMensaje() + ".");
					} else {
						addActionMessage("El usuario '" + apellidosNombre + "' se ha deshabilitado.");
					}
				}
				/* FIN Mantis 0011494 (ihgl) */
			}
		}

		listaUsuariosAux = (ArrayList<UsuarioContratoBean>) modeloContratoNuevo.obtenerUsuariosContrato(numColegiado);

		contratoBean.setListaAplicaciones(listaAplicaciones);
		contratoBean.setListaUsuarios(listaUsuariosAux);
		getSession().put(ConstantesSession.LISTA_USUARIOS_CONTRATO, contratoBean.getListaUsuarios());
		return MODIFICAR_CONTRATO;
	}

	/**
	 * Función que eliminará en bloque los contratos que hayamos seleccionado en la pantalla.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String eliminarUsuarios() {
		ArrayList<AplicacionContratoBean> listaAplicaciones = (ArrayList<AplicacionContratoBean>) getSession().get(ConstantesSession.LISTA_APLICACIONES_CONTRATO);
		ArrayList<UsuarioContratoBean> listaUsuarios = (ArrayList<UsuarioContratoBean>) getSession().get(ConstantesSession.LISTA_USUARIOS_CONTRATO);
		ArrayList<UsuarioContratoBean> listaUsuariosAux = new ArrayList<>();
		String numColegiado = contratoBean.getDatosColegiado().getNumColegiado();

		for (String idUsuario : getIdUsuarios()) {
			UsuarioContratoBean usuario = obtenerUsuarioLista(idUsuario, listaUsuarios);

			if (usuario == null) {
				addActionError("El usuario con número '" + idUsuario + "' no se ha encontrado.");
			} else {
				/* INICIO Mantis 0011494: (ihgl) se mostrará el nombre y apellidos del usuario en lugar de su ID */
				String apellidosNombre = (usuario.getApellidosNombre() != null) ? usuario.getApellidosNombre() : "";

				if (usuario.getEstadoUsuario().getNombreEnum().equals(Estado.Eliminado.getNombreEnum())) {
					addActionMessage("El usuario '" + apellidosNombre + "' ya está eliminado.");
				}
				// En este caso es cuando el usuario se pasará al estado
				// eliminado
				if (usuario.getEstadoUsuario().getNombreEnum().equals(Estado.Habilitado.getNombreEnum())
						|| usuario.getEstadoUsuario().getNombreEnum().equals(Estado.Deshabilitado.getNombreEnum())) {
					ResultBean resultadoModelo = modeloContratoNuevo.eliminarUsuario(usuario.getIdUsuario());

					if (resultadoModelo.getError()) {
						addActionError("Error al eliminar el usuario '" + apellidosNombre + "': " + resultadoModelo.getMensaje() + ".");
					} else {
						addActionMessage("El usuario '" + apellidosNombre + "' se ha eliminado.");
					}
				}
				/* FIN Mantis 0011494 (ihgl) */
			}
		}

		listaUsuariosAux = (ArrayList<UsuarioContratoBean>) modeloContratoNuevo.obtenerUsuariosContrato(numColegiado);
		contratoBean.setListaAplicaciones(listaAplicaciones);
		contratoBean.setListaUsuarios(listaUsuariosAux);
		getSession().put(ConstantesSession.LISTA_USUARIOS_CONTRATO, contratoBean.getListaUsuarios());
		return MODIFICAR_CONTRATO;
	}

	/**
	 * Recogerá del formulario los datos del nuevo usuario y lo dará de alta, manteniendo todos los datos del contrato.
	 * @return
	 */
	public String nuevoUsuario() {
		if (null == usuarioBean.getAnagrama() || usuarioBean.getAnagrama().equals("")) {
			String anagramaUsuario = Anagrama.obtenerAnagramaFiscal(usuarioBean.getApellidosNombre().toUpperCase(), usuarioBean.getNif());
			usuarioBean.setAnagrama(anagramaUsuario);
		}
		if (null == usuarioBean.getNumColegiado() || usuarioBean.getNumColegiado().equals("")) {
			usuarioBean.setNumColegiado(contratoBean.getDatosColegiado().getNumColegiado());
		}

		// Mantis 11562. David Sierra.
		// Se asigna al UsuarioContratoBean la fecha de alta en la plataforma
		if (null == usuarioBean.getFechaAlta()) {
			usuarioBean.setFechaAlta(utilesFecha.getFechaActual());
		}

		// Si el Estado Usuario es desahabilitado o eliminado se asigna a la fecha de fin el día actual
		if (usuarioBean.getEstadoUsuario().getNombreEnum() != null
				&& !usuarioBean.getEstadoUsuario().getNombreEnum().equals(Estado.Habilitado.getNombreEnum())) {
			usuarioBean.setFechaFin(utilesFecha.getFechaActual());
		}
		// Fin mantis

		ResultBean resultadoModelo = modeloContratoNuevo.guardarUsuario(usuarioBean);

		/* INICIO Mantis 0011494: (ihgl) se mostrará el nombre y apellidos del usuario en lugar de su ID */
		String apellidosNombre = (usuarioBean.getApellidosNombre() != null) ? usuarioBean.getApellidosNombre() : "";

		if (resultadoModelo.getError()) {
			addActionError("Error al dar de alta el usuario '" + apellidosNombre + "': " + resultadoModelo.getMensaje() + ".");
		} else {
			addActionMessage("El usuario '" + apellidosNombre + "' se ha dado de alta correctamente. Ahora le puede asignar permisos modificándolo.");
			// Mantis 6990. Ricardo Rodriguez. Se quitan los permisos solo cuando el alta de usuario no da error.

			// SE QUITAN LOS PERMISOS DEL CTIT SIEMPRE QUE SE DA DE ALTA UN NUEVO USUARIO
			resultadoModelo = modeloContratoNuevo.cambiarPermisoUsuario(contratoBean.getIdContrato(), usuarioBean.getIdUsuario(), "OT02", "OEGAM_TRAF", DESASIGNAR);
			if (resultadoModelo.getError()) {
				addActionError("Error estableciendo permisos al dar de alta el usuario '" + apellidosNombre + "': " + resultadoModelo.getMensaje() + ".");
			}
			resultadoModelo = modeloContratoNuevo.cambiarPermisoUsuario(contratoBean.getIdContrato(), usuarioBean.getIdUsuario(), "OT2T", "OEGAM_TRAF", DESASIGNAR);
			if (resultadoModelo.getError()) {
				addActionError("Error estableciendo permisos al dar de alta el usuario '" + apellidosNombre + "': " + resultadoModelo.getMensaje() + ".");
			}
			resultadoModelo = modeloContratoNuevo.cambiarPermisoUsuario(contratoBean.getIdContrato(), usuarioBean.getIdUsuario(), "OT7M", "OEGAM_TRAF", DESASIGNAR);
			if (resultadoModelo.getError()) {
				addActionError("Error estableciendo permisos al dar de alta el usuario '" + apellidosNombre + "': " + resultadoModelo.getMensaje() + ".");
			}
			// Fin de la incidencia 6990
		}
		/* FIN Mantis 0011494 (ihgl) */

		idContrato = String.valueOf(contratoBean.getIdContrato());

		return modificar();
	}

	public String agregarUsuariosGestoria() {
		// Buscar el usuario y contrato en la lista de usuariosContrato para comprobar si se quiere volver a
		// agregar el mismo

		ResultBean resultado = servicioContrato.asociarUsuarioContrato(getIdUsuarios(), Long.parseLong(idContrato));
		if (resultado.getError()) {
			addActionError("Error al agregar usuarios : " + resultado.getMensaje() + ".");
		} else {
			addActionMessage("El usuario se ha asociado al contrato: " + idContrato);
		}

		return MODIFICAR_CONTRATO;
	}

	/**
	 * Recogerá del formulario los datos del nuevo usuario y lo dará de alta, manteniendo todos los datos del contrato.
	 * @return
	 */
	public String guardarUsuario() {
		if (null == usuarioSeleccionado.getAnagrama() || usuarioSeleccionado.getAnagrama().equals("")) {
			String anagramaUsuario = Anagrama.obtenerAnagramaFiscal(usuarioSeleccionado.getApellidosNombre().toUpperCase(), usuarioSeleccionado.getNif());
			usuarioSeleccionado.setAnagrama(anagramaUsuario);
		}

		if (null == usuarioSeleccionado.getNumColegiado() || usuarioSeleccionado.getNumColegiado().equals("")) {
			usuarioSeleccionado.setNumColegiado(contratoBean.getDatosColegiado().getNumColegiado());
		}

		ResultBean resultadoModelo = modeloContratoNuevo.guardarUsuario(usuarioSeleccionado);

		/* INICIO Mantis 0011494: (ihgl) se mostrará el nombre y apellidos del usuario en lugar de su ID */
		String apellidosNombre = (usuarioSeleccionado.getApellidosNombre() != null) ? usuarioSeleccionado.getApellidosNombre() : "";

		if (resultadoModelo.getError()) {
			addActionError("Error al guardar el usuario '" + apellidosNombre + "': " + resultadoModelo.getMensaje() + ".");
		} else {
			addActionMessage("El usuario '" + apellidosNombre + "' se ha guardado correctamente.");
		}
		/* FIN Mantis 0011494 (ihgl) */

		idContrato = String.valueOf(contratoBean.getIdContrato());
		ArrayList<UsuarioContratoBean> listaUsuarios = (ArrayList<UsuarioContratoBean>) modeloContratoNuevo.obtenerUsuariosContrato(contratoBean.getDatosColegiado().getNumColegiado());

		contratoBean.setListaUsuarios(listaUsuarios);

		// Como aquí se va a guardar el usuario, debemos avisar a la jsp de alguna manera para que muestre el usuario
		// que se ha modificado.
		ResultBean resultadoPermisos = mantenimientoPermisosUsuario();

		if (resultadoPermisos.getError()) {
			addActionError("Error en el mantenimiento de permisos: " + resultadoPermisos.getMensaje());
		} else {
			addActionMessage("Permisos Actualizados correctamente.");
		}
		setUsuarioSel(true);

		return detalleUsuario();
	}

	/**
	 * Obtendrá el detalle del usuario que se haya seleccionado y lo mostrará con sus permisos.
	 * @return
	 */
	public String detalleUsuario() {
		String usuarioDetalle = "";

		if (idUsuarioSeleccionado != null) {
			usuarioDetalle = idUsuarioSeleccionado;
			idUsuarioActivo = idUsuarioSeleccionado;
		} else
			usuarioDetalle = idUsuarioActivo;
		ResultBean resultadoModelo = modeloContratoNuevo.obtenerDetalleUsuario(usuarioDetalle, contratoBean.getIdContrato());

		if (resultadoModelo.getError()) {
			addActionError("Error al obtener el detalle del usuario: " + resultadoModelo.getMensaje());
		} else {
			// Si no se ha producido error se ha cargado el bean de pantalla para el usuario,
			// con la lista de los permisos del mismo.

			usuarioSeleccionado = (UsuarioContratoBean) resultadoModelo.getAttachment(ConstantesPQ.BEANPANTALLA);
			usuarioSeleccionado.setListaPermisos(marcarPadres(usuarioSeleccionado.getListaPermisos()));
		}

		getSession().put(ConstantesSession.LISTA_PERMISOS_USUARIO, usuarioSeleccionado.getListaPermisos());
		setUsuarioSel(true);
		idContrato = String.valueOf(contratoBean.getIdContrato());
		return modificar();
	}

	/**
	 * Mostrará los permisos de la aplicación que seleccionemos.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String detalleAplicacion() {
		String aplicacionDetalle = "";

		if (codAplicacionSeleccionada != null) {
			aplicacionDetalle = codAplicacionSeleccionada;
			codAplicacionActiva = codAplicacionSeleccionada;
		} else
			aplicacionDetalle = codAplicacionActiva;

		ResultBean resultadoModelo = modeloContratoNuevo.obtenerPermisosAplicacion(aplicacionDetalle, contratoBean.getIdContrato());

		if (resultadoModelo.getError()) {
			addActionError("Error al obtener los permisos de una aplicación: " + resultadoModelo.getMensaje());
		} else {
			// Si no se ha producido error se ha cargado la lista de los permisos de una aplicación.
			ArrayList<FuncionAplicacionContratoBean> listaFunciones = new ArrayList<>();
			listaFunciones = (ArrayList<FuncionAplicacionContratoBean>) resultadoModelo.getAttachment(ConstantesPQ.BEANPANTALLA);

			setListaPermisosAplicacion(marcarPadresAplicacion(listaFunciones));
		}

		getSession().put(ConstantesSession.LISTA_PERMISOS_APLICACION, listaPermisosAplicacion);
		setAplicacionSel(true);
		idContrato = String.valueOf(contratoBean.getIdContrato());
		return modificar();
	}

	/**
	 * Modificará los permisos según lo teníamos en la pantalla.
	 * @return
	 */
	public String guardarPermisos() {
		idContrato = String.valueOf(contratoBean.getIdContrato());
		ArrayList<UsuarioContratoBean> listaUsuarios = (ArrayList<UsuarioContratoBean>) modeloContratoNuevo.obtenerUsuariosContrato(contratoBean.getDatosColegiado().getNumColegiado());

		contratoBean.setListaUsuarios(listaUsuarios);

		// Como aquí se va a guardar el usuario, debemos avisar a la jsp de alguna manera para que muestre el usuario
		// que se ha modificado.
		ResultBean resultadoPermisos = mantenimientoPermisosAplicacion();

		if (resultadoPermisos.getError()) {
			addActionError("Error en el mantenimiento de permisos: " + resultadoPermisos.getMensaje());
		} else {
			addActionMessage("Permisos Actualizados correctamente.");
		}

		return detalleAplicacion();
	}
	// Métodos AUXILIARES

	@SuppressWarnings("unchecked")
	private ResultBean mantenimientoPermisosAplicacion() {

		ResultBean resultado = new ResultBean();
		ArrayList<FuncionAplicacionContratoBean> listaPermisos = new ArrayList<>();

		listaPermisos = (ArrayList<FuncionAplicacionContratoBean>) getSession().get(ConstantesSession.LISTA_PERMISOS_APLICACION);
		if (null == listaPermisos) {
			resultado.setError(true);
			resultado.setMensaje("No se pudo recuperar la lista de permisos.");
		}

		// Comprobamos que de los checks seleccionados
		for (FuncionAplicacionContratoBean permisoAplicacion : listaPermisos) {
			Boolean asignar = false;
			Boolean desasignar = false;
			Boolean seleccionado = false;
			ResultBean resultadoModelo = new ResultBean();

			// Recorro los checks selecciondos
			for (String codPermiso : getPermisosAplicaciones()) {
				// Si este permiso está seleccionado, comprobamos que estuviese antes ya asignado, y si no lo
				// asignamos ahora.

				if (codPermiso.equals(permisoAplicacion.getCodigo_Funcion())) {
					asignar = permisoAplicacion.getAsignada() == 1 ? false : true;
					seleccionado = true;
					break;
				}
			}

			if (!seleccionado) {
				desasignar = permisoAplicacion.getAsignada() == 1;
			}

			// Una vez que ya sabemos si tenemos que asignar o desasignar el permiso del usuario lo hacemos.

			if (asignar) {
				resultadoModelo = modeloContratoNuevo.cambiarPermisoAplicacion(contratoBean.getIdContrato(), permisoAplicacion.getCodigo_Funcion(), permisoAplicacion.getCodigo_Aplicacion(), ASIGNAR);

			} else if (desasignar) {
				resultadoModelo = modeloContratoNuevo.cambiarPermisoAplicacion(contratoBean.getIdContrato(), permisoAplicacion.getCodigo_Funcion(), permisoAplicacion.getCodigo_Aplicacion(),
						DESASIGNAR);
			}

			if (resultadoModelo.getError()) {
				resultado.setError(true);
				resultado.setMensaje("Error cambiando el permiso: " + resultadoModelo.getMensaje());
			}
		} // Fin del for de la lista

		return resultado;
	}

	@SuppressWarnings("unchecked")
	private ResultBean mantenimientoPermisosUsuario() {

		ResultBean resultado = new ResultBean();
		ArrayList<PermisoUsuarioContratoBean> listaPermisos = new ArrayList<>();

		listaPermisos = (ArrayList<PermisoUsuarioContratoBean>) getSession().get(ConstantesSession.LISTA_PERMISOS_USUARIO);
		if (null == listaPermisos) {
			resultado.setError(true);
			resultado.setMensaje("No se pudo recuperar la lista de permisos.");
		}

		// Comprobamos que de los checks seleccionados
		for (PermisoUsuarioContratoBean permisoUsuario : listaPermisos) {
			Boolean asignar = false;
			Boolean desasignar = false;
			Boolean seleccionado = false;
			ResultBean resultadoModelo = new ResultBean();

			// Recorro los checks selecciondos
			for (String codPermiso : getPermisosUsuarios()) {
				// Si este permiso está seleccionado, comprobamos que estuviese antes ya asignado, y si no lo
				// asignamos ahora.

				if (codPermiso.equals(permisoUsuario.getCodigo_Funcion())) {
					asignar = permisoUsuario.getAsignada() == 1 ? false : true;
					seleccionado = true;
					break;
				}
			}

			if (!seleccionado) {
				desasignar = permisoUsuario.getAsignada() == 1;
			}

			// Una vez que ya sabemos si tenemos que asignar o desasignar el permiso del usuario lo hacemos.

			if (asignar) {
				resultadoModelo = modeloContratoNuevo.cambiarPermisoUsuario(usuarioSeleccionado.getIdContrato(), usuarioSeleccionado.getIdUsuario(), permisoUsuario.getCodigo_Funcion(), permisoUsuario
						.getCodigo_Aplicacion(), ASIGNAR);
			} else if (desasignar) {
				resultadoModelo = modeloContratoNuevo.cambiarPermisoUsuario(usuarioSeleccionado.getIdContrato(), usuarioSeleccionado.getIdUsuario(), permisoUsuario.getCodigo_Funcion(), permisoUsuario
						.getCodigo_Aplicacion(), DESASIGNAR);
			}

			if (resultadoModelo.getError()) {
				resultado.setError(true);
				resultado.setMensaje("Error cambiando el permiso: " + resultadoModelo.getMensaje());
			}
		} // Fin del for de la lista

		return resultado;
	}

	/**
	 * Método que de la lista marca a verdadero las funciones que tengan hijos.
	 * @param listaFunciones
	 * @return
	 */
	private List<PermisoUsuarioContratoBean> marcarPadres(List<PermisoUsuarioContratoBean> listaFunciones) {
		ArrayList<PermisoUsuarioContratoBean> funcionesModificadas = new ArrayList<>();

		for (int i = 0; i < listaFunciones.size(); i++) {
			PermisoUsuarioContratoBean funcion = listaFunciones.get(i);
			Boolean esPadre = false;
			String codFuncion = funcion.getCodigo_Funcion();

			List<PermisoUsuarioContratoBean> listaAuxiliar = listaFunciones;

			for (int j = 0; j < listaAuxiliar.size(); j++) {
				PermisoUsuarioContratoBean funcionPadre = (PermisoUsuarioContratoBean) listaAuxiliar.get(j);

				if (codFuncion.equals(funcionPadre.getCodigo_Funcion_Padre())) {
					esPadre = true;
					break;
				}
			}

			funcion.setEsPadre(esPadre);

			funcionesModificadas.add(funcion);
		}
		return funcionesModificadas;
	}

	/**
	 * Método que de la lista marca a verdadero las funciones que tengan hijos.
	 * @param listaFunciones
	 * @return
	 */
	private List<FuncionAplicacionContratoBean> marcarPadresAplicacion(List<FuncionAplicacionContratoBean> listaFunciones) {
		ArrayList<FuncionAplicacionContratoBean> funcionesModificadas = new ArrayList<>();

		for (int i = 0; i < listaFunciones.size(); i++) {
			FuncionAplicacionContratoBean funcion = (FuncionAplicacionContratoBean) listaFunciones.get(i);
			Boolean esPadre = false;
			String codFuncion = funcion.getCodigo_Funcion();

			List<FuncionAplicacionContratoBean> listaAuxiliar = listaFunciones;

			for (int j = 0; j < listaAuxiliar.size(); j++) {
				FuncionAplicacionContratoBean funcionPadre = listaAuxiliar.get(j);

				if (codFuncion.equals(funcionPadre.getCodigo_Funcion_Padre())) {
					esPadre = true;
					break;
				}
			}
			funcion.setEsPadre(esPadre);

			funcionesModificadas.add(funcion);
		}
		return funcionesModificadas;
	}

	/**
	 * Método auxiliar que obtendrá de la lista en sesión una lista por su número de expediente.
	 * @param expediente
	 * @return
	 */
	private ContratoBean obtenerBeanConIdContrato(String idContrato) {
		ContratoBean linea = new ContratoBean(true);
		List<Object> listaBeansResult = listaContratos.getList();
		for (Object objeto : listaBeansResult) {
			linea = (ContratoBean) objeto;
			if (idContrato.equals(null != linea.getIdContrato() ? linea.getIdContrato().toString() : "")) {
				return linea;
			}
		}
		return null;
	}

	/**
	 * Método auxiliar que obtendrá de la lista de usuarios un usuario por su id.
	 * @param
	 * @return
	 */
	private UsuarioContratoBean obtenerUsuarioLista(String idUsuario, ArrayList<UsuarioContratoBean> listaUsuarios) {
		for (UsuarioContratoBean user : listaUsuarios) {
			if (idUsuario.equals(null != user.getIdUsuario() ? user.getIdUsuario().toString() : "")) {
				return user;
			}
		}
		return null;
	}

	/**
	 * Método para actualizar los parámetros de la búsqueda. Proceso: -Actualizo los datos de la sesión. -Paso los parámetros de búsqueda.
	 * @throws Throwable
	 */
	private void mantenimientoCamposBuscar() {
		/* INICIO Mantis 0013076: tratamiento de objetos de sesión */
		obtenerDeSesionOInicializarConsultaContrato();

		obtenerDeSesionOInicializarListaContratos();
		/* FIN Mantis 0013076 */

		setResultadosPorPagina((String) getSession().get(ConstantesSession.RESULTADOS_PAGINA));

		limpiarCamposSession();

		getSession().put(ConstantesSession.RESULTADOS_PAGINA, getResultadosPorPagina());

		actualizaParametros(consultaContrato);
	}

	/**
	 * Método para actualizar los parámetros de paginación. Proceso: -Recupero el bean de búsqueda de sesión. -Actualizo los parámetros de búsqueda. -Actualizo en sesión los de la paginación.
	 */
	private void mantenimientoCamposNavegar() {
		if (resultadosPorPagina != null) {
			getSession().put("resultadosPorPagina", resultadosPorPagina);
		}

		/* INICIO Mantis 0013076: tratamiento de objetos de sesión */
		obtenerDeSesionOInicializarConsultaContrato();

		obtenerDeSesionOInicializarListaContratos();
		/* FIN Mantis 0013076 */

		setResultadosPorPagina((String) getSession().get(ConstantesSession.RESULTADOS_PAGINA));

		actualizaParametros(consultaContrato);
	}

	/**
	 * Método que actualizará los parámetros de búsqueda
	 * @param beanActualiza
	 */
	private void actualizaParametros(ConsultaContratoBean beanActualiza) {
		parametrosBusqueda = new HashMap<>();
		if (null != beanActualiza) {
			parametrosBusqueda.put(ConstantesSession.CIF_CONTRATO, beanActualiza.getCif());

			Integer estado = beanActualiza.getEstado();
			if (estado != null && estado.equals(Integer.valueOf(String.valueOf("-1")))) {
				estado = null;
			}

			parametrosBusqueda.put(ConstantesSession.ESTADO_CONTRATO, estado);
			parametrosBusqueda.put(ConstantesSession.RAZON_SOCIAL_CONTRATO, beanActualiza.getRazonSocial());
			parametrosBusqueda.put(ConstantesSession.NUM_COLEGIADO_CONTRATO, beanActualiza.getNumColegiado());
		}
	}

	// Métodos GETTER y SETTER

	@Override
	public void setServletRequest(HttpServletRequest arg0) {}

	public PaginatedListImpl getListaContratos() {
		return listaContratos;
	}

	public void setListaContratos(PaginatedListImpl listaContratos) {
		this.listaContratos = listaContratos;
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

	public ContratoBean getContratoBean() {
		return contratoBean;
	}

	public void setContratoBean(ContratoBean contratoBean) {
		this.contratoBean = contratoBean;
	}

	public HashMap<String, Object> getParametrosBusqueda() {
		return parametrosBusqueda;
	}

	public void setParametrosBusqueda(HashMap<String, Object> parametrosBusqueda) {
		this.parametrosBusqueda = parametrosBusqueda;
	}

	public ArrayList<Object> getListaBeanVista() {
		return listaBeanVista;
	}

	public String[] getIdContratos() {
		return idContratos;
	}

	public void setIdContratos(String[] idContratos) {
		this.idContratos = idContratos;
	}

	public void setListaBeanVista(ArrayList<Object> listaBeanVista) {
		this.listaBeanVista = listaBeanVista;
	}

	public String[] getIdUsuarios() {
		return idUsuarios;
	}

	public Boolean getAltaNuevo() {
		return altaNuevo;
	}

	public void setAltaNuevo(Boolean altaNuevo) {
		this.altaNuevo = altaNuevo;
	}

	public void setIdUsuarios(String[] idUsuarios) {
		this.idUsuarios = idUsuarios;
	}

	public String getIdContrato() {
		return idContrato;
	}

	public List<Provincia> getProvincias() {
		return provincias;
	}

	public void setProvincias(List<Provincia> provincias) {
		this.provincias = provincias;
	}

	public List<MunicipioBean> getMunicipios() {
		return municipios;
	}

	public void setMunicipios(List<MunicipioBean> municipios) {
		this.municipios = municipios;
	}

	public List<ColegioBean> getColegios() {
		return colegios;
	}

	public void setColegios(List<ColegioBean> colegios) {
		this.colegios = colegios;
	}

	public List<JefaturaBean> getJefaturas() {
		return jefaturas;
	}

	public UsuarioContratoBean getUsuarioBean() {
		return usuarioBean;
	}

	public void setUsuarioBean(UsuarioContratoBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}

	public void setJefaturas(List<JefaturaBean> jefaturas) {
		this.jefaturas = jefaturas;
	}

	public List<TipoVia> getTiposVia() {
		return tiposVia;
	}

	public void setTiposVia(List<TipoVia> tiposVia) {
		this.tiposVia = tiposVia;
	}

	public Boolean getUsuarioSel() {
		return usuarioSel;
	}

	public void setUsuarioSel(Boolean usuarioSel) {
		this.usuarioSel = usuarioSel;
	}

	public UsuarioContratoBean getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(UsuarioContratoBean usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}

	public String[] getIdAplicaciones() {
		return idAplicaciones;
	}

	public void setIdAplicaciones(String[] idAplicaciones) {
		this.idAplicaciones = idAplicaciones;
	}

	public String[] getPermisosUsuarios() {
		return permisosUsuarios;
	}

	public void setPermisosUsuarios(String[] permisosUsuarios) {
		this.permisosUsuarios = permisosUsuarios;
	}

	public String[] getPermisosAplicaciones() {
		return permisosAplicaciones;
	}

	public void setPermisosAplicaciones(String[] permisosAplicaciones) {
		this.permisosAplicaciones = permisosAplicaciones;
	}

	public String getCodAplicacionActiva() {
		return codAplicacionActiva;
	}

	public void setCodAplicacionActiva(String codAplicacionActiva) {
		this.codAplicacionActiva = codAplicacionActiva;
	}

	public String getCodAplicacionSeleccionada() {
		return codAplicacionSeleccionada;
	}

	public void setCodAplicacionSeleccionada(String codAplicacionSeleccionada) {
		this.codAplicacionSeleccionada = codAplicacionSeleccionada;
	}

	public void setIdContrato(String idContrato) {
		this.idContrato = idContrato;
	}

	public List<FuncionAplicacionContratoBean> getListaPermisosAplicacion() {
		return listaPermisosAplicacion;
	}

	public void setListaPermisosAplicacion(List<FuncionAplicacionContratoBean> listaPermisosAplicacion) {
		this.listaPermisosAplicacion = listaPermisosAplicacion;
	}

	public Boolean getAplicacionSel() {
		return aplicacionSel;
	}

	public void setAplicacionSel(Boolean aplicacionSel) {
		this.aplicacionSel = aplicacionSel;
	}

	public ConsultaContratoBean getConsultaContrato() {
		return consultaContrato;
	}

	public void setConsultaContrato(ConsultaContratoBean consultaContrato) {
		this.consultaContrato = consultaContrato;
	}

	@Override
	public void setServletResponse(HttpServletResponse arg0) {

	}

	public String getIdUsuarioActivo() {
		return idUsuarioActivo;
	}

	public void setIdUsuarioActivo(String idUsuarioActivo) {
		this.idUsuarioActivo = idUsuarioActivo;
	}

	public String getIdUsuarioSeleccionado() {
		return idUsuarioSeleccionado;
	}

	public void setIdUsuarioSeleccionado(String idUsuarioSeleccionado) {
		this.idUsuarioSeleccionado = idUsuarioSeleccionado;
	}

	public ServicioContrato getServicioContrato() {
		return servicioContrato;
	}

	public void setServicioContrato(ServicioContrato servicioContrato) {
		this.servicioContrato = servicioContrato;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}

	public Long getIdContratoAnt() {
		return idContratoAnt;
	}

	public void setIdContratoAnt(Long idContratoAnt) {
		this.idContratoAnt = idContratoAnt;
	}

	public String[] getIdUsuariosContrato() {
		return idUsuariosContrato;
	}

	public void setIdUsuariosContrato(String[] idUsuariosContrato) {
		this.idUsuariosContrato = idUsuariosContrato;
	}

}