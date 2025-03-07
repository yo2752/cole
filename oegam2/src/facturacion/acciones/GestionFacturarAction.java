package facturacion.acciones;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoBaja;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;

import escrituras.beans.ResultBean;
import escrituras.utiles.PaginatedListImpl;
import escrituras.utiles.UtilesVista;
import facturacion.beans.ConceptoIReport;
import facturacion.beans.ConsultaFacturaBean;
import facturacion.beans.ExpedientesBean;
import facturacion.comun.ConstantesFacturacion;
import facturacion.comun.DatosClienteBean;
import facturacion.dao.DatosDAO;
import facturacion.gestion.ModeloConceptoFactura;
import facturacion.gestion.ModeloNuevaFactura;
import facturacion.utiles.enumerados.EmisorFactura;
import facturacion.utiles.enumerados.EstadoFacturacion;
import general.acciones.ActionBase;
import general.utiles.Anagrama;
import general.utiles.UtilidadIreport;
import hibernate.entities.facturacion.Factura;
import hibernate.entities.facturacion.FacturaColegiadoConcepto;
import hibernate.entities.facturacion.FacturaConcepto;
import hibernate.entities.facturacion.FacturaGasto;
import hibernate.entities.facturacion.FacturaHonorario;
import hibernate.entities.facturacion.FacturaOtro;
import hibernate.entities.facturacion.FacturaPK;
import hibernate.entities.facturacion.FacturaProvFondo;
import hibernate.entities.facturacion.FacturaSuplido;
import hibernate.entities.general.Colegiado;
import hibernate.entities.personas.Direccion;
import hibernate.entities.personas.EvolucionPersona;
import hibernate.entities.personas.EvolucionPersonaPK;
import hibernate.entities.personas.Persona;
import hibernate.entities.personas.PersonaDireccion;
import hibernate.entities.personas.PersonaDireccionPK;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ConstantesSession;
import trafico.beans.ConsultaTramiteTraficoFacturacionBean;
import trafico.beans.TramiteTraficoBean;
import trafico.beans.TramiteTraficoDuplicadoBean;
import trafico.beans.TramiteTraficoMatriculacionBean;
import trafico.beans.TramiteTraficoTransmisionBean;
import trafico.beans.daos.BeanPQTasasDetalle;
import trafico.modelo.ModeloDuplicado;
import trafico.modelo.ModeloMatriculacion;
import trafico.modelo.ModeloTasas;
import trafico.modelo.ModeloTrafico;
import trafico.modelo.ModeloTransmision;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.validaciones.NIFValidator;
import utilidades.web.OegamExcepcion;

public class GestionFacturarAction extends ActionBase implements ServletRequestAware {
	private static final long serialVersionUID = 1L;

	// log
	private static ILoggerOegam log = LoggerOegam.getLogger(GestionFacturarAction.class);
	private HttpServletRequest request;

	// Sirve para recuperar datos de facturacion
	private DatosClienteBean datosCliente;

	// private ConsultaFacturaBean consultaFactura;
	private ConsultaFacturaBean beanNormal;
	private ConsultaFacturaBean beanRect;
	private ConsultaFacturaBean beanAbon;

	// Recupera los datos del Cliente
	private Factura factura;
	private DatosDAO dao = new DatosDAO();
	private InputStream inputStream; // Flujo de bytes del fichero a imprimir en 

	// PDF del action
	private String ficheroDescarga; // Nombre del fichero a imprimir

	private String numFactura;
	private String numColegiado;

	private String numsFactura; // Lista de numeros de factura
	private String descripcionConcepto;

	private List<FacturaColegiadoConcepto> lFacturaConcepto;

	private String claveColegiado;
	private long idConcepto;
	private String idConceptoAltaModificacion;
	private String conceptoIdBorrar; // conceptoIdBorrar que se va a borrar
	private String honorarioIdBorrar; // honorarioIdBorrar que se va a borrar

	private String[] honorarioDescripcion;

	private final String GUARDAR_NUEVA_FACTURA="guardarNuevaFactura";
	private final String GUARDAR_MODIFICAR_FACTURA="guardarModificarFactura";

	HashMap<String, Object> parametrosBusqueda; 		//Se utiliza para asignar los parámetros de búsqueda al objeto DAO
	private PaginatedListImpl listaConsultaTramiteTraficoFacturacion; //Lista que se va a mostrar en la vista es de tipo PaginatedList para el uso por displayTag
	private String sort;								//Columna por la que se ordena
	private String dir;									//Orden de la ordenación
	private String page;								//Página a mostrar

	private String numsExpediente;
	private String tipoTramite = null;
	private BeanPQTasasDetalle resultadoCodigoTasa;

	private ExpedientesBean expedientesBean;
	private List<ExpedientesBean> listaExpedientesBean;

	//Es necesario hacer las variables globales para cuando se añadan/eliminen números de expediente poder saber el total para el nombre del concepto. 
	private int totalTramiteMatriculacion = 0;
	private int totalTramiteTransmision = 0;
	private int totalTramiteTransmisionTelematica = 0;
	private int totalTramiteBaja = 0;
	private int totalTramiteDuplicados = 0;
	private int totalTramiteSolicitudes = 0;
	private List<ExpedientesBean> listaNumsExpediente = null;
	private boolean vieneDeEliminarExpediente = false;

	private int totalTramiteMatriculacionNoEstatica = 0;
	private int totalTramiteTransmisionNoEstatica = 0;
	private int totalTramiteTransmisionTelematicaNoEstatica = 0;
	private int totalTramiteBajaNoEstatica = 0;
	private int totalTramiteDuplicadosNoEstatica = 0;
	private int totalTramiteSolicitudesNoEstatica = 0;

	//Trámite Tráfico
	private TramiteTraficoBean tramiteTraficoBean; 
	//T1-Trámite Tráfico Matriculación
	private TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean;
	//T2-Trámite Tráfico Transmisión
	private TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean;
	//T3-Trámite Tráfico Bajas
	private TramiteTrafBajaDto tramiteTraficoBaja;
	//T8-Trámite Duplicado
	private TramiteTraficoDuplicadoBean tramiteTraficoDuplicado;
	/*
	 * Elementos comunes a todos los action de listas, tales como la página
	 * actual (para la paginación), si esta ordenado por una columna, si el
	 * ordenamiento es ascendente o descendente, la cantidad de elemntos a
	 * mostrar por página, entre otros
	 */
	// Lista que se va a mostrar en la vista es de tipo PaginatedList para el
	// uso por displayTag
	private List<Factura> listaConsultaFacturaNormal;
	private List<Factura> listaConsultaFacturaRect;
	private List<Factura> listaConsultaFacturaAbon;
	private String resultadosPorPagina; 				//La cantidad de elementos a mostrar por página

	//Variables del formulario, pertenecen al bean.
	private ConsultaTramiteTraficoFacturacionBean consultaTramiteTraficoNuevaFacturacion;

	// log de errores	
	public static final  int T1 = 1;
	public static final  int T2 = 2;
	public static final  int T3 = 3;
	public static final  int T4 = 4;
	public static final  int T7 = 7;
	public static final  int T8 = 8;
	// HashMap<String, Object> parametrosBusqueda; // Se utiliza para asignar
	// los

	private String tipoFactura;

	// Servicios
	private UtilidadIreport utilidadIreport;
	private ModeloDuplicado modeloDuplicado;
	private ModeloMatriculacion modeloMatriculacion;
	private ModeloTasas modeloTasas;
	private ModeloTrafico modeloTrafico;
	private ModeloTransmision modeloTransmision;
	
	private boolean gestorComoEmisor;
	private boolean gestoriaComoEmisor;
	
	private String numColegiadoFactura;
	
	@Autowired
	private ServicioTramiteTraficoBaja servicioTramiteTraficoBaja;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	/**
	 * Metodo que se carga por defecto, al pulsar la opcion Consulta Factura, y
	 * se encarga de recuperar las facturas por defecto del dia de hoy.
	 */
	public String inicio() throws Throwable {
		log.info(Claves.CLAVE_LOG_FACTURACION_CONSULTA + "consulta.");
		beanNormal = new ConsultaFacturaBean();
		beanNormal.setFecha(utilesFecha.getFechaFracionadaActual());
		beanNormal.setMensajeBorrador(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
		beanNormal.setMensajePDF(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
		beanNormal.setMensajeXML(ConstantesFacturacion.ALERT_MENSAJE_XML);
		beanNormal.setMensajeImprimir(ConstantesFacturacion.ALERT_MENSAJE_IMPRIMIR);
		beanNormal.setNumSerie(ConstantesFacturacion.FACTURA_SERIE_DEFECTO);
		//Si tiene permiso de administrador no tengo que dar numColegiado, porque no tiene que filtrarlo.
		if (!utilesColegiado.tienePermisoAdmin()){
			beanNormal.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
		mantenimientoCamposNavegar();
		if(null!=resultadosPorPagina){
			getSession().put(ConstantesSession.RESULTADOS_PAGINA,resultadosPorPagina); 
		}else{
			getSession().put(ConstantesSession.RESULTADOS_PAGINA, UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO); 
		}

		if (listaConsultaFacturaNormal == null) {

			listaConsultaFacturaNormal = dao.listaFacturas(beanNormal,
					EstadoFacturacion.EstadoFacturaRectificativa.getValorEnum(),
					EstadoFacturacion.EstadoFacturaGenerada.getNombreEnum());
		}

		listaConsultaFacturaNormal = getIncluirColumnaCheckPDFCambioEstado(listaConsultaFacturaNormal);

		beanNormal.setNumSerie(ConstantesFacturacion.FACTURA_SERIE_DEFECTO);

		setListaConsultaFacturaNormal(listaConsultaFacturaNormal);
		getSession().put("listaConsultaFacturaNormal", listaConsultaFacturaNormal);
		return ConstantesFacturacion.RESULT_CONSULTA_FACTURACION;
	}

	/**
	 * Metodo que se carga por defecto, al pulsar la opcion Consulta Factura
	 * Rectificatica, y se encarga de recuperar las facturas rectificativas por
	 * defecto del dia de hoy.
	 */
	public String inicioRectificativa() throws Throwable {

		log.info(Claves.CLAVE_LOG_FACTURACION_CONSULTA_RECTIFICATIVA	+ "consultaRectificativa.");
		beanRect = new ConsultaFacturaBean();
		beanRect.setFecha(utilesFecha.getFechaFracionadaActual());
		beanRect.setMensajeBorrador(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
		beanRect.setMensajePDF(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
		beanRect.setMensajeImprimir(ConstantesFacturacion.ALERT_MENSAJE_IMPRIMIR);
		beanRect.setNumSerie(ConstantesFacturacion.FACTURA_RECTIFICATIVA_DEFECTO);
		mantenimientoCamposNavegar();

		if(null!=resultadosPorPagina){
			getSession().put(ConstantesSession.RESULTADOS_PAGINA,resultadosPorPagina); 
		}else{
			getSession().put(ConstantesSession.RESULTADOS_PAGINA, UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO); 
		}

		if (listaConsultaFacturaRect == null) {

			listaConsultaFacturaRect = dao.listaFacturas(beanRect,
					EstadoFacturacion.EstadoFacturaRectificativa.getValorEnum(),
					EstadoFacturacion.EstadoFacturaRectificativa.getNombreEnum());
		}

		listaConsultaFacturaRect = getIncluirColumnaCheckPDFCambioEstado(listaConsultaFacturaRect);

		beanRect.setNumSerie(ConstantesFacturacion.FACTURA_RECTIFICATIVA_DEFECTO);

		setListaConsultaFacturaRect(listaConsultaFacturaRect);
		getSession().put("listaConsultaFacturaRect", listaConsultaFacturaRect);
		return ConstantesFacturacion.RESULT_CONSULTA_FACTURACION_RECTIFICATIVA;
	}

	/**
	 * Metodo que se carga por defecto, al pulsar la opcion Consulta Concepto, y
	 * se encarga de recuperar los conceptos por defecto del colegiado.
	 */
	public String inicioConcepto() throws Throwable {
		log.info(Claves.CLAVE_LOG_FACTURACION_CONSULTA_CONCEPTO
				+ "consultaConcepto.");
		lFacturaConcepto = dao
				.listaConceptos(utilesColegiado.getNumColegiadoSession());

		// datosConcepto.setConceptoTodoArray(modelo.convertirConceptoListToString(llFacturaConcepto));
		// datosConcepto.setClaveColegiado(dao.claveColegiado(utilesColegiado.getNumColegiado()));

		// getSession().put("datosConcepto", datosConcepto);
		getSession().put("lFacturaConcepto", lFacturaConcepto);
		return ConstantesFacturacion.RESULT_CONSULTA_CONCEPTO;
	}

	/**
	 * Metodo que se encarga de la paginacion y de no perder la informacion,
	 * recuperada anteriormente de la consulta de facturas
	 */
	public String navegarNormal() {
		mantenimientoCamposNavegar();
		return ConstantesFacturacion.RESULT_CONSULTA_FACTURACION;
	}

	/**
	 * Metodo que se encarga de la paginacion y de no perder la informacion,
	 * recuperada anteriormente de la consulta de facturas
	 */
	public String navegarRect() {
		mantenimientoCamposNavegar();
		return ConstantesFacturacion.RESULT_CONSULTA_FACTURACION_RECTIFICATIVA;
	}

	/**
	 * Metodo que se encarga de no perder la informacion, recuperada
	 * anteriormente de la consulta de facturas
	 */
	@SuppressWarnings("unchecked")
	private void mantenimientoCamposNavegar() {

		if (resultadosPorPagina!=null){
			getSession().put("resultadosPorPagina", resultadosPorPagina);
		}


		ConsultaFacturaBean consultaTramitesNormal = (ConsultaFacturaBean) getSession().get("beanNormal");
		if (consultaTramitesNormal != null) {
			setBeanNormal(consultaTramitesNormal);
		}

		ConsultaFacturaBean consultaTramitesRect = (ConsultaFacturaBean) getSession().get("beanRect");
		if (consultaTramitesRect != null) {
			setBeanRect(consultaTramitesRect);
		}

		ConsultaFacturaBean consultaTramitesAbono = (ConsultaFacturaBean) getSession().get("beanAbon");
		if (consultaTramitesAbono != null) {
			setBeanAbon(consultaTramitesAbono);
		}

		List<Factura> listaConsultaNormal = (List<Factura>) getSession().get("listaConsultaFacturaNormal");
		if (listaConsultaNormal != null) {
			setListaConsultaFacturaNormal(listaConsultaNormal);
		}

		List<Factura> listaConsultaRect = (List<Factura>) getSession().get("listaConsultaFacturaRect");
		if (listaConsultaRect != null) {
			setListaConsultaFacturaRect(listaConsultaRect);
		}

		List<Factura> listaConsultaAbono = (List<Factura>) getSession().get("listaConsultaFacturaAbon");
		if (listaConsultaAbono != null) {
			setListaConsultaFacturaAbon(listaConsultaAbono);
		}

		String resultadosPagina = (String)getSession().get(ConstantesSession.RESULTADOS_PAGINA);
		if(null != resultadosPagina){
			setResultadosPorPagina(resultadosPagina);	
		}

		getSession().put("beanNormal", beanNormal);
		getSession().put("beanRect", beanRect);
		getSession().put("beanAbon", beanAbon);
	}

	/**
	 * Metodo que se encarga de no perder la informacion, recuperada
	 * anteriormente del anterior Bean de Sesion.
	 */
	private void mantenimientoCamposSesion() {

		DatosClienteBean datosClienteSession = (DatosClienteBean)  getSession().get("datosCliente");
		datosCliente.getFactura().setFacturaConceptos(datosClienteSession.getFactura().getFacturaConceptos());
		datosCliente.setIsError(datosClienteSession.getIsError());
		datosCliente.setIsPantallaAlta(datosClienteSession.getIsPantallaAlta());
		datosCliente.setIsRectificativa(datosClienteSession.getIsRectificativa());
		datosCliente.setNumClave(datosClienteSession.getNumClave());
		datosCliente.setNumDecimales(datosClienteSession.getNumDecimales());
		datosCliente.setListaExpedientes(datosClienteSession.getListaExpedientes());
		datosCliente.setFacturaConceptoCargado(datosCliente.getFactura().getFacturaConceptos().get(0));

	}

	/**
	 * Metodo que se encarga de realizar la busqueda en BB.DD. de las facturas
	 * que coincidan con los filtros seleccionado en pantalla.
	 * 
	 * @return
	 * @throws OegamExcepcion
	 */
	public String buscar() throws OegamExcepcion {
		// recuperamos los tramites de busqueda
		getSession().put("beanNormal", beanNormal);
		//		mantenimientoCamposNavegar();

		try {
			listaConsultaTramiteTraficoFacturacion = new PaginatedListImpl();
		} catch (Throwable e1) {
			log.error("Error al buscar una factura. ", e1);
		}
		ModeloTrafico modeloTrafico = new ModeloTrafico();
		listaConsultaTramiteTraficoFacturacion.setBaseDAO(modeloTrafico);
		listaConsultaTramiteTraficoFacturacion.getBaseDAO().setParametrosBusqueda(parametrosBusqueda);
		getSession().put(ConstantesSession.LISTA_CONSULTA_TRAMITE_TRAFICO_FACTURACION_CONSULTA, listaConsultaTramiteTraficoFacturacion);

		if (beanNormal == null) {
			beanNormal = new ConsultaFacturaBean();
			beanNormal.setFecha(utilesFecha.getFechaFracionadaActual());

		}

		beanNormal.setMensajeBorrador(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
		beanNormal.setMensajePDF(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
		beanNormal.setMensajeXML(ConstantesFacturacion.ALERT_MENSAJE_XML);
		beanNormal.setMensajeImprimir(ConstantesFacturacion.ALERT_MENSAJE_IMPRIMIR);
		mantenimientoCamposBuscar();

		try {

			listaConsultaFacturaNormal = dao.buscaFactura(beanNormal,
					EstadoFacturacion.EstadoFacturaRectificativa.getValorEnum());
		} catch (Exception e) {
		}
		listaConsultaFacturaNormal = getIncluirColumnaCheckPDFCambioEstado(listaConsultaFacturaNormal);
		setListaConsultaFacturaNormal(listaConsultaFacturaNormal);
		getSession().put("listaConsultaFacturaNormal", listaConsultaFacturaNormal);

		// return "consultaFactura";
		return ConstantesFacturacion.RESULT_CONSULTA_FACTURACION;
	}

	/**
	 * Metodo que se encarga de realizar la busqueda en BB.DD. de las facturas
	 * que coincidan con los filtros seleccionado en pantalla.
	 * 
	 * @return
	 * @throws OegamExcepcion
	 */
	public String buscarRectificativa() throws OegamExcepcion {
		// recuperamos los tramites de busqueda
		getSession().put("beanRect", beanRect);

		if (beanRect == null) {
			beanRect = new ConsultaFacturaBean();
			beanRect.setFecha(utilesFecha.getFechaFracionadaActual());

		}
		//		if ((bean != null && bean.getNumSerie() != null && !bean.getNumSerie().equalsIgnoreCase(""))
		//				|| !bean.getNumSerie().equalsIgnoreCase("R")) {
		//			bean.setNumSerie(ficheroPropiedades.getMensaje("factura.rectificativa.defecto"));
		//		}

		beanRect.setMensajeBorrador(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
		beanRect.setMensajePDF(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
		beanNormal.setMensajeXML(ConstantesFacturacion.ALERT_MENSAJE_XML);
		beanRect.setMensajeImprimir(ConstantesFacturacion.ALERT_MENSAJE_IMPRIMIR);
		mantenimientoCamposBuscar();

		listaConsultaFacturaRect = dao.buscaFactura(beanRect,
				EstadoFacturacion.EstadoFacturaRectificativa.getValorEnum());
		listaConsultaFacturaRect = getIncluirColumnaCheckPDFCambioEstado(listaConsultaFacturaRect);
		setListaConsultaFacturaRect(listaConsultaFacturaRect);
		getSession().put("listaConsultaFacturaRect", listaConsultaFacturaRect);

		// return "consultaFactura";
		return ConstantesFacturacion.RESULT_CONSULTA_FACTURACION_RECTIFICATIVA;
	}

	/**
	 * Metodo que se encarga de no perder la informacion, recuperada
	 * anteriormente de la consulta de facturas
	 */
	@SuppressWarnings("unchecked")
	private void mantenimientoCamposBuscar() {

		setListaConsultaFacturaNormal((List<Factura>) getSession().get("listaConsultaFacturaNormal"));		
		setListaConsultaFacturaRect((List<Factura>) getSession().get("listaConsultaFacturaRect"));

		setResultadosPorPagina((String)getSession().get(ConstantesSession.RESULTADOS_PAGINA));
		getSession().put(ConstantesSession.RESULTADOS_PAGINA, getResultadosPorPagina());

	}

	/**
	 * Metodo que se encarga de eliminar la factura Normales de las consultas. Se realiza
	 * un borrado logico.
	 * 
	 * @return
	 * @throws Exception
	 * @throws OegamExcepcion
	 * @throws OegamExcepcion
	 */
	public String eliminarNormal() throws Exception, OegamExcepcion {
		String indices = getNumsFactura();
		String[] codSeleccionados = indices.split("-");
		Factura linea;
		mantenimientoCamposNavegar();
		for (int i = 0; i < codSeleccionados.length; i++) {
			linea = dao.detalleFacturaCompleto(codSeleccionados[i], utilesColegiado.getNumColegiadoSession());

			if (linea != null) {
				dao.borrarFactura(linea.getId());
				// addActionMessage("Factura " + codSeleccionados[i] + " eliminada");
				addActionMessage(ConstantesFacturacion.MENSAJE_ERROR_ELIMINAR_UNO
						+ codSeleccionados[i] + " "
						+ ConstantesFacturacion.MENSAJE_ERROR_ELIMINAR_DOS);
			}
		}
		//beanNormal = new ConsultaFacturaBean();
		//beanNormal.setFecha(utilesFecha.getFechaFracionadaActual());
		listaConsultaFacturaNormal = dao.listaFacturas(beanNormal,EstadoFacturacion.EstadoFacturaRectificativa.getValorEnum(),
				EstadoFacturacion.EstadoFacturaGenerada.getNombreEnum());
		listaConsultaFacturaNormal = getIncluirColumnaCheckPDFCambioEstado(listaConsultaFacturaNormal);

		// Actualizamos la lista de facturas
		getSession().put("listaConsultaFacturaNormal", listaConsultaFacturaNormal);
		// return "consultaFactura";
		return ConstantesFacturacion.RESULT_CONSULTA_FACTURACION;
	}

	/**
	 * Metodo que se encarga de eliminar la factura Rectificativas de las consultas. Se realiza
	 * un borrado logico.
	 * 
	 * @return
	 * @throws Exception
	 * @throws OegamExcepcion
	 * @throws OegamExcepcion
	 */
	public String eliminarRect() throws Exception, OegamExcepcion {
		String indices = getNumsFactura();
		String[] codSeleccionados = indices.split("-");
		Factura linea;
		mantenimientoCamposNavegar();
		for (int i = 0; i < codSeleccionados.length; i++) {
			linea = dao.detalleFacturaCompleto(codSeleccionados[i], utilesColegiado.getNumColegiadoSession());

			if (linea != null) {
				dao.borrarFactura(linea.getId());
				// addActionMessage("Factura " + codSeleccionados[i] +
				// " eliminada");
				addActionMessage(ConstantesFacturacion.MENSAJE_ERROR_ELIMINAR_UNO
						+ codSeleccionados[i] + " "
						+ ConstantesFacturacion.MENSAJE_ERROR_ELIMINAR_DOS);
			}
		}
		//beanRect = new ConsultaFacturaBean();
		//beanRect.setFecha(utilesFecha.getFechaFracionadaActual());
		listaConsultaFacturaRect = dao.listaFacturas(beanRect,EstadoFacturacion.EstadoFacturaRectificativa.getValorEnum(),
				EstadoFacturacion.EstadoFacturaRectificativa.getNombreEnum());
		listaConsultaFacturaRect = getIncluirColumnaCheckPDFCambioEstado(listaConsultaFacturaRect);

		// Actualizamos la lista de facturas
		getSession().put("listaConsultaFacturaRect", listaConsultaFacturaRect);
		return ConstantesFacturacion.RESULT_CONSULTA_FACTURACION_RECTIFICATIVA;
	}

	/**
	 * Metodo que se encarga de eliminar la factura de las consultas. Se realiza
	 * un borrado logico.
	 * 
	 * @return
	 * @throws Exception
	 * @throws OegamExcepcion
	 * @throws OegamExcepcion
	 */
	public String crearRectificativa() throws Exception, OegamExcepcion {
		ResultBean resultado = new ResultBean();
		String mensaje = "";
		String indices = getNumsFactura();
		String[] codSeleccionados = indices.split("-");
		Factura linea;
		mantenimientoCamposNavegar();
		for (int i = 0; i < codSeleccionados.length; i++) {
			linea = dao.detalleFacturaCompleto(codSeleccionados[i], utilesColegiado.getNumColegiadoSession());

			if (linea != null && linea.getCheckPdf().equalsIgnoreCase(EstadoFacturacion.EstadoFacturaGenerada.getValorEnum())) {

				try {
					// DRC@07-08-2012 Rectifica el numero de Factura
					Fecha fechaFrac = utilesFecha.getFechaActual();
					String tFecha = fechaFrac.getMes() + fechaFrac.getAnio().substring(2);					
					Factura detalleFactura = dao.detalleFacturaCompleto(linea.getId().getNumFactura(), utilesColegiado.getNumColegiadoSession());

					try {
						resultado = dao.anularFactura(linea.getId(),EstadoFacturacion.EstadoFacturaAnulada.getValorEnum());

					} catch (Exception e) {
						addActionError(ConstantesFacturacion.PDF_RECTIFICATIVA_MENSAJE_CERO);
						return ConstantesFacturacion.RESULT_CONSULTA_FACTURACION;
					}

					Factura nFactura = new Factura();
					nFactura = (Factura) detalleFactura.clone();

					List<FacturaConcepto> listaFacturaConcepto = new ArrayList<FacturaConcepto>();


					// Inicializamos los Ids para que se guarden como nueva factura en la BBDD
					for (FacturaConcepto fc: nFactura.getFacturaConceptos()){
						FacturaConcepto nConcepto = new FacturaConcepto();
						nConcepto = (FacturaConcepto) fc.clone();						
						nConcepto.setIdConcepto(0);
						nConcepto.setFactura(nFactura);
						listaFacturaConcepto.add(nConcepto);
						nFactura.setFacturaConceptos(listaFacturaConcepto);
						List<FacturaHonorario> factHonorario = new ArrayList<FacturaHonorario>();
						List<FacturaSuplido> factSuplidoList = new ArrayList<FacturaSuplido>();
						List<FacturaGasto> factGastosList = new ArrayList<FacturaGasto>();

						for (FacturaHonorario fh: fc.getFacturaHonorarios()){
							FacturaHonorario nHonorario = new FacturaHonorario();
							nHonorario = (FacturaHonorario) fh.clone();						
							nHonorario.setIdHonorario(0);
							nHonorario.setFacturaConcepto(nConcepto);
							factHonorario.add(nHonorario);
						}
						nConcepto.setFacturaHonorarios(factHonorario);
						for (FacturaSuplido fs: fc.getFacturaSuplidos()){							
							FacturaSuplido nSuplido = new FacturaSuplido();
							nSuplido = (FacturaSuplido) fs.clone();						
							nSuplido.setIdSuplido(0);
							nSuplido.setFacturaConcepto(nConcepto);
							factSuplidoList.add(nSuplido);
						}
						nConcepto.setFacturaSuplidos(factSuplidoList);
						for (FacturaGasto fg: fc.getFacturaGastos()){
							FacturaGasto nGasto = new FacturaGasto();
							nGasto = (FacturaGasto) fg.clone();						
							nGasto.setIdGasto(0);
							nGasto.setFacturaConcepto(nConcepto);
							factGastosList.add(nGasto);
						}
						nConcepto.setFacturaGastos(factGastosList);
					}

					FacturaOtro facturaOtro = new FacturaOtro();
					facturaOtro = (FacturaOtro) nFactura.getFacturaOtro().clone();
					facturaOtro.setIdOtro(0);		
					facturaOtro.setFactura(nFactura);
					nFactura.setFacturaOtro(facturaOtro);

					FacturaProvFondo facturaProvFondo = new FacturaProvFondo();
					facturaProvFondo = (FacturaProvFondo) nFactura.getFacturaProvFondo().clone();
					facturaProvFondo.setIdFondo(0);
					facturaProvFondo.setFactura(nFactura);
					nFactura.setFacturaProvFondo(facturaProvFondo);

					String numFacturaAnulada = detalleFactura.getId().getNumFactura();

					nFactura.setNumSerie(ConstantesFacturacion.FACTURA_RECTIFICATIVA_DEFECTO);
					String campoBusqueda = '%' + utilesColegiado.getNumColegiadoSession() + tFecha + '%';					
					//					int numFacturasBBDD = dao.buscaNumFacturaRectificativa(campoBusqueda,EstadoFacturacion.EstadoFacturaRectificativa.getValorEnum());
					int numFacturasBBDD = dao.buscaNumFacturaRectificativa(campoBusqueda,"RECT");
					int numFacturasN = numFacturasBBDD + 1;
					String valorFactura = String.valueOf(numFacturasN);
					String idFactura = utiles.rellenarCeros(valorFactura, 5);
					numColegiado = utilesColegiado.getNumColegiadoSession();
					numFactura = nFactura.getNumSerie() + numColegiado + tFecha + idFactura;

					campoBusqueda = '%'	+ numColegiado	+ tFecha + numFactura.substring(numFactura.length() - 5, numFactura.length());

					nFactura.getId().setNumFactura(nFactura.getNumSerie() + utilesColegiado.getNumColegiadoSession() + tFecha + idFactura);

					for (int x = 0; x < nFactura.getFacturaConceptos().size(); x++) {
						nFactura.getFacturaConceptos().get(x).setNumColegiado(utilesColegiado.getNumColegiadoSession());
						nFactura.getFacturaConceptos().get(x).setNumFactura(nFactura.getId().getNumFactura());
					}

					nFactura.setNumeracion(new BigDecimal(idFactura));
					nFactura.setFechaAlta(new Date());
					nFactura.setFechaFactura(new Date());
					//					nFactura.setCheckPdf(EstadoFacturacion.EstadoFacturaRectificativa.getValorEnum());
					nFactura.setCheckPdf(EstadoFacturacion.EstadoNueva.getValorEnum());

					nFactura.setFacAnulada(numFacturaAnulada);

					resultado = dao.saveOrUpdate(nFactura);

					if (datosCliente == null)
						datosCliente = new DatosClienteBean();

					datosCliente.setFactura(nFactura);
					datosCliente.setIsRectificativa(Boolean.TRUE);

					if (resultado.getError()) {
						datosCliente.setIsError(Boolean.FALSE);
						datosCliente.setIsPantallaAlta(Boolean.TRUE);
						for (int j = 0; j < resultado.getListaMensajes().size(); j++) {
							if (resultado.getListaMensajes().get(j) != null) {
								mensaje = resultado.getListaMensajes().get(j);
								mensaje = mensaje.replace("--", " - ");
								addActionError(mensaje);
							}
						}
						return ConstantesFacturacion.RESULT_CONSULTA_FACTURACION;
					} else {

						datosCliente.setIsRectificativa(Boolean.FALSE);

						datosCliente.setIsError(Boolean.TRUE);
						datosCliente.setIsPantallaAlta(Boolean.FALSE);

						setDatosCliente(datosCliente);
						getSession().put("datosCliente", datosCliente);

						for (int j = 0; j < resultado.getListaMensajes().size(); j++) {
							if (resultado.getListaMensajes().get(j) != null) {
								mensaje = resultado.getListaMensajes().get(j);
								mensaje = mensaje.replace("--", " - ");
								addActionMessage(mensaje);
							}
						}
					}
					addActionMessage(ConstantesFacturacion.MENSAJE_ERROR_CREAR_RECTIFICATIVA_UNO + " " + codSeleccionados[i] + " " + ConstantesFacturacion.MENSAJE_ERROR_CREAR_RECTIFICATIVA_DOS);
					addActionMessage(ConstantesFacturacion.MENSAJE_ERROR_CREAR_RECTIFICATIVA_UNO + " " + numFactura + " " + ConstantesFacturacion.MENSAJE_ERROR_CREAR_RECTIFICATIVA_TRES);
				} catch (Throwable e) {
					log.error("Error al crear una factura rectificativa. ", e);
					addActionError(ConstantesFacturacion.PDF_RECTIFICATIVA_MENSAJE_TRES + " " + codSeleccionados[i] + " " + ConstantesFacturacion.PDF_RECTIFICATIVA_MENSAJE_CUATRO);
					return ConstantesFacturacion.RESULT_CONSULTA_FACTURACION;
				}				
			} else {
				addActionError(ConstantesFacturacion.PDF_RECTIFICATIVA_MENSAJE_TRES + " " + codSeleccionados[i] + " " + ConstantesFacturacion.PDF_RECTIFICATIVA_MENSAJE_CUATRO);
				buscar();
				return ConstantesFacturacion.RESULT_CONSULTA_FACTURACION;
			}
		}
		// DRC@14-08-2012 Actualiza los campos Numero Colegiado y Numero
		// Factura, a blanco para que el usuario si lo desee utilice los filtros
		//		beanRect.setNumColegiado("");
		//		beanRect.setNumFactura("");
		buscarRectificativa();
		return ConstantesFacturacion.RESULT_CONSULTA_FACTURACION;
	}
	/**
	 * Metodo que se encarga de recuperar las facturadas rectificativas seleccionadas por el
	 * usuario, para su posterior eleminacion logica de la BB.DD.
	 * 
	 * @param numFactura
	 * @param numColegiado
	 * @return
	 */

	//	private Factura obtenerFacturaRect(String numFactura, String numColegiado) {
	//		beanRect = new ConsultaFacturaBean();
	//		// bean.setFecha(utilesFecha.getFechaFracionadaActual());
	//		listaConsultaFacturaRect = dao.buscaFactura(beanRect, null, null);
	//		listaConsultaFacturaRect = getIncluirColumnaCheckPDFCambioEstado(listaConsultaFacturaRect);
	//		for (Factura f : listaConsultaFacturaRect) {
	//			if (f.getId().getNumFactura().equalsIgnoreCase(numFactura)
	//					&& f.getId().getNumColegiado().equalsIgnoreCase(
	//							numColegiado)) {
	//				return f;
	//			}
	//		}
	//		return null;
	//	}

	//	public String detalleSesion() throws Throwable {
	//
	//		setDatosCliente((DatosClienteBean) getSession().get("datosCliente"));
	//
	//		getSession().put("datosCliente", datosCliente);
	//
	//		return ConstantesFacturacion.RESULT_ALTA_FACTURACION;
	//
	//	}


	public void actualizaOtros(Factura detalle){

		if (detalle.getFacturaOtro() != null && detalle.getFacturaOtro().getOtroCheckIva() != null){
			if ("1".equals(String.valueOf(detalle.getFacturaOtro().getOtroCheckIva()))){
				detalle.getFacturaOtro().setOtroCheckIvaHidden("1");
			}else{
				detalle.getFacturaOtro().setOtroCheckIvaHidden("0");
			}
		} else{
			detalle.getFacturaOtro().setOtroCheckIvaHidden("0");
		}


		if (detalle.getFacturaOtro().getOtroCheckDescuento() != null){
			if ("1".equals(String.valueOf(detalle.getFacturaOtro().getOtroCheckDescuento()))){
				detalle.getFacturaOtro().setOtroCheckDescuentoHidden("1");
			}else{
				detalle.getFacturaOtro().setOtroCheckDescuentoHidden("0");
			}
		} else{
			detalle.getFacturaOtro().setOtroCheckDescuentoHidden("0");
		}

		if(detalle.getEmisor() != null){
			if(detalle.getEmisor().equals(EmisorFactura.GESTOR.getNombre())){
				gestorComoEmisor = true;
			}else if(detalle.getEmisor().equals(EmisorFactura.GESTORIA.getNombre())){
				gestoriaComoEmisor = true;
			}
		}
		
	}


	/**
	 * Metodo que se encarga de realizar la busqueda en BB.DD. de los datos a
	 * mostrar para la modificacion de la factura. Una vez el usuario pulsa
	 * sobre el numero de factura, en la pantalla de consulta de facturas.
	 * 
	 * @return
	 * @throws Throwable
	 */
	public String detalle() throws Throwable {
		// String donde = "consultaFactura";		
		String donde = ConstantesFacturacion.RESULT_CONSULTA_FACTURACION;
		mantenimientoCamposNavegar();
		
		// Mantis 13737. David Sierra: En el caso de que en la busqueda previa se utilice el filtrado por Num Colegiado 
		// (solo lo puede hacer el Administrador) se controla que el numColegiado se corresponda con el contenido 
		// en el numFactura del registro seleccionado
		if(ConstantesFacturacion.NUM_COLEGIADO_ADMINISTRADOR.equals(utilesColegiado.getNumColegiadoSession())) {
			numColegiadoFactura = numFactura.substring(4, 8);
		}
		// Si el usuario no tiene permisos de administrador el numColegiado se recupera de sesion
		else {
			numColegiadoFactura = utilesColegiado.getNumColegiadoSession();
		}
		
		Factura detalle = dao.detalleFacturaCompleto(numFactura, numColegiadoFactura);

		actualizaOtros(detalle);

		hibernate.entities.personas.Direccion dir = dao.buscarDireccionActual(detalle.getNif(),detalle.getId().getNumColegiado());

		if (detalle.getPersona() != null)
			detalle.getPersona().setDireccionActual(dir);

		DatosClienteBean datosClienteBean = new DatosClienteBean();
		datosClienteBean.setFactura(detalle);

		// Factura detalleDeFactura = dao.detalleFactura(numFactura,
		// utilesColegiado.getNumColegiado());
		// datosClienteBean.setFactura(detalleDeFactura);
		// ModeloNuevaFactura modelo = new ModeloNuevaFactura();
		// datosCliente = modelo.cargarDatosModificacionFactura(detalle);
		//setDatosCliente((DatosClienteBean) getSession().get("datosCliente"));
		setDatosCliente(datosClienteBean);
		ArrayList<String> listaExpedientes = (ArrayList<String>) dao.listaExpedientes(numFactura);
		datosClienteBean.setListaExpedientes(listaExpedientes);
		// datosCliente.setListaExpedientes(listaExpedientes);

		// datosClienteBean.getFactura().getId().setNumFactura(numFactura);
		datosClienteBean.setNumClave(recuperarClaveColegiado());
		datosClienteBean.setIsPantallaAlta(Boolean.FALSE);
		datosClienteBean.setIsError(Boolean.FALSE);
		datosClienteBean.setNumDecimales(ConstantesFacturacion.DECIMALES_FACT);
		// datosCliente.setIdColegiado(recuperarClaveColegiado());
		
		// Mantis 13737. David Sierra: El numColegiado se corresponde con el contenido en el numFactura del registro seleccionado  		
		String resp = dao.checkPDF(numFactura, numColegiadoFactura);

		if (resp.equalsIgnoreCase(EstadoFacturacion.EstadoNueva.getValorEnum())) {
			datosClienteBean.setIsRectificativa(Boolean.FALSE);
			datosClienteBean.getFactura().setCheckPdf(
					EstadoFacturacion.EstadoNueva.getValorEnum());
		} else if (resp
				.equalsIgnoreCase(EstadoFacturacion.EstadoFacturaGenerada.getValorEnum())) {
			datosClienteBean.setIsRectificativa(Boolean.TRUE);
			datosClienteBean.getFactura().setCheckPdf(
					EstadoFacturacion.EstadoFacturaRectificativa.getValorEnum());
		} else if (resp
				.equalsIgnoreCase(EstadoFacturacion.EstadoFacturaRectificativa.getValorEnum())) {
			datosClienteBean.setIsRectificativa(Boolean.TRUE);
			datosClienteBean.getFactura().setCheckPdf(EstadoFacturacion.EstadoFacturaRectificativa.getValorEnum());
		} else if (resp.equalsIgnoreCase(EstadoFacturacion.EstadoFacturaAnulada
				.getValorEnum())) {
			datosClienteBean.setIsRectificativa(Boolean.TRUE);
			datosClienteBean.getFactura().setCheckPdf(EstadoFacturacion.EstadoFacturaAnulada.getValorEnum());
		}


		datosClienteBean.setFacturaConceptoCargado(datosClienteBean.getFactura().getFacturaConceptos().get(0));
		datosCliente = datosClienteBean;

		datosCliente.setNumDecimales(gestorPropiedades.valorPropertie("facturacion.num.decimales"));
		datosCliente.setMensajeBorrador(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
		datosCliente.setMensajePDF(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
		datosCliente.setMensajeImprimir(ConstantesFacturacion.ALERT_MENSAJE_IMPRIMIR);
		datosCliente.setMensajeClave(ConstantesFacturacion.ALERT_MENSAJE_MODFICAR);

		getSession().put("datosCliente", datosCliente);
		donde = ConstantesFacturacion.RESULT_ALTA_FACTURACION;
		return donde;
	}

	public String eliminarDescripcionDeConceptos() {
		ModeloConceptoFactura modelo = new ModeloConceptoFactura();
		List<String> listaIdConceptos = new ArrayList<String>();
		String indices = getNumsFactura();
		// String[] codSeleccionados = indices.split("-");
		StringTokenizer str = new StringTokenizer(indices, "-");
		while (str.hasMoreElements()) {
			listaIdConceptos.add((String) str.nextElement());
		}
		modelo.eliminarConceptosFacturas(dao, listaIdConceptos, utilesColegiado
				.getNumColegiadoSession());

		lFacturaConcepto = dao
				.listaConceptos(utilesColegiado.getNumColegiadoSession());
		getSession().put("lFacturaConcepto", lFacturaConcepto);

		return ConstantesFacturacion.RESULT_CONSULTA_CONCEPTO;
	}

	public String guardarDescripcionDeConceptos() {

		if (getIdConceptoAltaModificacion() != null
				&& !getIdConceptoAltaModificacion().equals(""))
			dao.updateColegiadoConcepto(utilesColegiado.getNumColegiadoSession(),
					getDescripcionConcepto(), getIdConceptoAltaModificacion());
		else
			dao.saveColegiadoConcepto(utilesColegiado.getNumColegiadoSession(),
					getDescripcionConcepto());

		lFacturaConcepto = dao
				.listaConceptos(utilesColegiado.getNumColegiadoSession());
		getSession().put("lFacturaConcepto", lFacturaConcepto);

		return ConstantesFacturacion.RESULT_CONSULTA_CONCEPTO;
	}

	/**
	 * Metodo que se encarga de recuperar los valores introducidos por el
	 * usuario BB.DD.
	 * 
	 * @return
	 * @throws Throwable
	 */
	public String altaConcepto() throws Throwable {
		ModeloConceptoFactura modelo = new ModeloConceptoFactura();
		ResultBean resultado = modelo.validarDatosAltaConcepto(datosCliente
				.getFactura().getFacturaConceptos().get(0));
		String mensaje = "";

		if (resultado.getError()) {
			for (int i = 0; i < resultado.getListaMensajes().size(); i++) {
				if (resultado.getListaMensajes().get(i) != null) {
					mensaje = resultado.getListaMensajes().get(i);
					mensaje = mensaje.replace("--", " - ");
					addActionError(mensaje);
				}
			}
		} else {
			try {
				List<String> mensajeSQL = new ArrayList<String>();
				mensajeSQL = modelo.alta(datosCliente.getFactura()
						.getFacturaConceptos().get(0), dao);
				resultado.setListaMensajes(mensajeSQL);

				for (int i = 0; i < resultado.getListaMensajes().size(); i++) {
					if (resultado.getListaMensajes().get(i) != null) {
						mensaje = resultado.getListaMensajes().get(i);
						mensaje = mensaje.replace("--", " - ");
						addActionMessage(mensaje);
					}
				}
			} catch (Exception e) {
				for (int i = 0; i < resultado.getListaMensajes().size(); i++) {
					if (resultado.getListaMensajes().get(i) != null) {
						mensaje = resultado.getListaMensajes().get(i);
						mensaje = mensaje.replace("--", " - ");
						addActionError(mensaje);
					}
				}
				addActionError(e.getCause().toString());
			}
		}
		return ConstantesFacturacion.RESULT_CONSULTA_CONCEPTO;
	}

	/**
	 * Metodo que se encarga de recuperar los valores introducidos por el
	 * usuario BB.DD.
	 * 
	 * @return
	 * @throws Throwable
	 */
	public String altaClave() throws Throwable {

		try {
			List<String> mensajeSQL = new ArrayList<String>();

			Colegiado colegiado = new Colegiado();
			colegiado = utilesColegiado.getColegiado();
			
			if (colegiado != null) {
				colegiado.setClaveFacturacion(utilesColegiado.encriptaEnSHA(getClaveColegiado()));
				mensajeSQL = dao.guardarClaveColegiado(colegiado, mensajeSQL);
				lFacturaConcepto = dao.listaConceptos(utilesColegiado.getNumColegiadoSession());
				getSession().put("lFacturaConcepto", lFacturaConcepto);	
			}
		} catch (Exception e) {
			addActionError(e.getCause().toString());
		}

		return ConstantesFacturacion.RESULT_CONSULTA_CONCEPTO;
	}

	public String guardarNuevaFactura() throws Throwable {
		try {		

			ModeloNuevaFactura modelo = new ModeloNuevaFactura();

			mantenimientoCamposSesion();
			ResultBean resultado = modelo.validarDatosAlta(datosCliente);
			//ArrayList<String> listaExpedientes = new ArrayList<String>();
			String mensaje = "";

			if (resultado.getError()) {

				datosCliente.setIsError(Boolean.FALSE); // Hay Errores, por lo tanto los botones														// Borrador y PDF deshabilitados
				datosCliente.setIsPantallaAlta(Boolean.TRUE);

				for (int i = 0; i < resultado.getListaMensajes().size(); i++) {
					if (resultado.getListaMensajes().get(i) != null) {
						mensaje = resultado.getListaMensajes().get(i);
						mensaje = mensaje.replace("--", " - ");
						addActionError(mensaje);
					}
				}
			} else {
				persistirSessionPantalla(resultado, GUARDAR_NUEVA_FACTURA);
			}
			setDatosCliente(datosCliente);
			getSession().put("datosCliente", datosCliente);
			return ConstantesFacturacion.RESULT_ALTA_FACTURACION;

		} catch (Exception e) {
			log.error("Error al realizar las validaciones de facturacion: "	+ e);
			datosCliente.setIsPantallaAlta(Boolean.TRUE);
			setDatosCliente(datosCliente);
			getSession().put("datosCliente", datosCliente);
			addActionError("Se ha producido un error al guardar la factura");
			return ConstantesFacturacion.RESULT_ALTA_FACTURACION;
		}
	}


	public void persistirSessionPantalla(ResultBean resultado, String tipoAGuardar) throws OegamExcepcion, Exception{

		boolean existePersona = false;
		boolean cambioPersona = false;
		boolean cambioDireccion = false;


		datosCliente.setIsError(Boolean.TRUE);  // No hay Errores, por lo tanto los botones
		// 	Borrador y PDF habilitados

		//Actualizamos la fecha de Nacimiento
		if ((datosCliente.getFactura().getPersona().getFnacimiento() !=null) && (datosCliente.getFactura().getPersona().getFnacimiento().getFecha()!=null) &&
				(datosCliente.getFactura().getPersona().getFechaNacimiento()==null)){
			try {
				this.datosCliente.getFactura().getPersona().setFechaNacimiento(this.datosCliente.getFactura().getPersona().getFnacimiento().getFecha());
			} catch (ParseException e) {
				log.error("ERROR PARSENADO FECHA");	
			}
		}

		String numeroFactura = getDatosCliente().getFactura().getId().getNumFactura(); 

		FacturaOtro facturaOtro = getDatosCliente().getFactura().getFacturaOtro();
		FacturaProvFondo facturaProvFondo = getDatosCliente().getFactura().getFacturaProvFondo();

		//		String importeFactura = String.valueOf(getDatosCliente().getFactura().getImporteTotal());
		float importeFacturaFloat = getDatosCliente().getFactura().getImporteTotal();

		//Cogemos el Nif de la pantalla para ver si existe la Persona en BBDD
		String nif = datosCliente.getFactura().getPersona().getId().getNif();
		hibernate.entities.personas.Persona personaExistente = dao.buscarPersona(nif,utilesColegiado.getNumColegiadoSession());				
		if (personaExistente != null) {
			existePersona = true;
			hibernate.entities.personas.Direccion dirExistente = dao.buscarDireccionActual(nif,utilesColegiado.getNumColegiadoSession());
			personaExistente.setDireccionActual(dirExistente);
			personaExistente.actualizaNulos();
		}

		//Cogemos la Pantalla y la Direccion de Pantalla para compararlas a ver si se ha modificado
		hibernate.entities.personas.Persona personaPant = datosCliente.getFactura().getPersona();
		hibernate.entities.personas.Direccion dirPantalla = datosCliente.getFactura().getPersona().getDireccionActual();
		personaPant.setDireccionActual(dirPantalla);

		//hibernate.entities.personas.Persona personaSesion = datosCliente.getFactura().getPersona();				
		if (existePersona){
			cambioPersona = personaExistente.equals(personaPant);
			cambioDireccion = personaExistente.getDireccionActual().equals(personaPant.getDireccionActual());
		}


		//Si la persona ya existe y no se le ha cambiado ni la Persona ni la Direccion
		if (existePersona && cambioPersona && cambioDireccion){ 
			//El nif ya existia, por lo que ponemos la que hay guardada
			datosCliente.getFactura().setPersona(personaExistente);
			datosCliente.getFactura().getPersona().setDireccionActual(personaExistente.getDireccionActual());

			//Si la persona ya existe y no se ha modificado la Persona pero si la Direccion
		} else if (existePersona && cambioPersona && !(cambioDireccion)){					
			//Guardamos sobre la Persona de anterior. Le seteamos la fecha de Fin a la de Hoy
			try {
				dao.anularPersonaDireccion(personaExistente.getId().getNif(), personaExistente.getId().getNumColegiado(), personaExistente.getDireccionActual().getIdDireccion());
			} catch (Exception e) {
				throw e;
			}

			//Creo Direccion
			Direccion dir = new Direccion();
			dir = personaPant.getDireccionActual();
			dir.getMunicipio().getId().setIdProvincia(dir.getMunicipio().getProvincia().getIdProvincia());					
			dir.setIdDireccion(0);					

			dao.addDireccion(dir);

			//Creo PersonaDireccion					
			PersonaDireccion persDir = new PersonaDireccion();					
			PersonaDireccionPK pPk = new PersonaDireccionPK();

			pPk.setNif(nif);
			pPk.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			pPk.setIdDireccion(dir.getIdDireccion());

			persDir.setId(pPk);
			persDir.setFechaInicio(new Date());
			persDir.setDireccion(dir);

			dao.addPersonaDireccion(persDir);

			personaPant.getId().setNumColegiado(utilesColegiado.getNumColegiadoSession());
			personaPant.setEstado(1);

			List<PersonaDireccion> personaDirs = new ArrayList<PersonaDireccion>();
			personaDirs.add(persDir);

			personaPant.setPersonaDireccions(personaDirs);

			datosCliente.getFactura().setPersona(personaPant);
			List<Factura> facturas = new ArrayList<Factura>();
			facturas.add(datosCliente.getFactura());
			datosCliente.getFactura().getPersona().setFacturas(facturas);					

			//Si la persona ya existe y se ha modificado la Persona pero no la Direccion
		} else if (existePersona && !cambioPersona && cambioDireccion){										

			//Creo Persona
			personaPant.getId().setNumColegiado(utilesColegiado.getNumColegiadoSession());
			personaPant.getId().setNif(nif);
			Persona pers = new Persona();
			pers = personaPant;
			pers.setEstado(1);

			BigDecimal tipoPersona = NIFValidator.validarNif(pers.getId().getNif());
			if ("1".equals(String.valueOf(tipoPersona))){
				pers.setTipoPersona("FISICA");
			} else if ("2".equals(String.valueOf(tipoPersona))){
				pers.setTipoPersona("JURIDICA");
			} else if ("3".equals(String.valueOf(tipoPersona))){
				pers.setTipoPersona("FISICA");
			}

			String anagrama = Anagrama.obtenerAnagramaFiscal(pers.getApellido1RazonSocial(), pers.getId().getNif());
			pers.setAnagrama(anagrama);

			if ("-".equals(pers.getEstadoCivil())) pers.setEstadoCivil(null);
			if ("-".equals(pers.getSexo())) pers.setSexo(null);
			if ("-".equals(pers.getTipoPersona())) pers.setTipoPersona(null);

			dao.saveOrUpdatePersona(pers);

			EvolucionPersona evolPersona = new EvolucionPersona();
			EvolucionPersonaPK ePk = new EvolucionPersonaPK();
			ePk.setNif(personaExistente.getId().getNif());
			ePk.setNumColegiado(personaExistente.getId().getNumColegiado());
			ePk.setFechaHora(utilesFecha.getTimestampActual());
			evolPersona.setId(ePk);

			evolPersona.setApellido1Ant(personaExistente.getApellido1RazonSocial());
			evolPersona.setApellido1Nue(personaPant.getApellido1RazonSocial());

			evolPersona.setApellido2Ant(personaExistente.getApellido2());
			evolPersona.setApellido2Nue(personaPant.getApellido2());

			evolPersona.setNombreAnt(personaExistente.getNombre());
			evolPersona.setNombreNue(personaPant.getNombre());

			evolPersona.setFechaNacimientoAnt(personaExistente.getFechaNacimiento());
			evolPersona.setFechaNacimientoNue(personaPant.getFechaNacimiento());

			evolPersona.setTipoTramite("FACT");
			evolPersona.setTipoActuacion("MODIFICACIÓN");
			evolPersona.setOtros("Facturacion");

			evolPersona.setIdUsuario(utilesColegiado.getIdUsuarioSessionBigDecimal());
			//Creo PersonaDireccion

			dao.addEvolucionPersona(evolPersona);


		} else if (existePersona && !cambioPersona && !cambioDireccion){					
			//Guardamos sobre la Persona de anterior. Le seteamos la fecha de Fin a la de Hoy

			//Creo Persona
			personaPant.getId().setNumColegiado(utilesColegiado.getNumColegiadoSession());
			personaPant.getId().setNif(nif);
			Persona pers = new Persona();
			pers = personaPant;
			pers.setEstado(1);

			BigDecimal tipoPersona = NIFValidator.validarNif(pers.getId().getNif());
			if ("1".equals(String.valueOf(tipoPersona))){
				pers.setTipoPersona("FISICA");
			} else if ("2".equals(String.valueOf(tipoPersona))){
				pers.setTipoPersona("JURIDICA");
			} else if ("3".equals(String.valueOf(tipoPersona))){
				pers.setTipoPersona("FISICA");
			}

			String anagrama = Anagrama.obtenerAnagramaFiscal(pers.getApellido1RazonSocial(), pers.getId().getNif());
			pers.setAnagrama(anagrama);

			if ("-".equals(pers.getEstadoCivil())) pers.setEstadoCivil(null);
			if ("-".equals(pers.getSexo())) pers.setSexo(null);
			if ("-".equals(pers.getTipoPersona())) pers.setTipoPersona(null);

			dao.saveOrUpdatePersona(pers);

			EvolucionPersona evolPersona = new EvolucionPersona();
			EvolucionPersonaPK ePk = new EvolucionPersonaPK();
			ePk.setNif(personaExistente.getId().getNif());
			ePk.setNumColegiado(personaExistente.getId().getNumColegiado());
			ePk.setFechaHora(utilesFecha.getTimestampActual());
			evolPersona.setId(ePk);

			evolPersona.setApellido1Ant(personaExistente.getApellido1RazonSocial());
			evolPersona.setApellido1Nue(personaPant.getApellido1RazonSocial());

			evolPersona.setApellido2Ant(personaExistente.getApellido2());
			evolPersona.setApellido2Nue(personaPant.getApellido2());

			evolPersona.setNombreAnt(personaExistente.getNombre());
			evolPersona.setNombreNue(personaPant.getNombre());

			evolPersona.setFechaNacimientoAnt(personaExistente.getFechaNacimiento());
			evolPersona.setFechaNacimientoNue(personaPant.getFechaNacimiento());

			evolPersona.setTipoTramite("FACT");
			evolPersona.setTipoActuacion("MODIFICACIÓN");
			evolPersona.setOtros("Facturacion");

			evolPersona.setIdUsuario(utilesColegiado.getIdUsuarioSessionBigDecimal());

			dao.addEvolucionPersona(evolPersona);

			dao.anularPersonaDireccion(personaExistente.getId().getNif(), personaExistente.getId().getNumColegiado(), personaExistente.getDireccionActual().getIdDireccion());

			//Creo Direccion
			Direccion dir = new Direccion();
			dir = personaPant.getDireccionActual();
			dir.getMunicipio().getId().setIdProvincia(dir.getMunicipio().getProvincia().getIdProvincia());					
			dir.setIdDireccion(0);					

			dao.addDireccion(dir);

			//Creo PersonaDireccion

			PersonaDireccion persDir = new PersonaDireccion();					
			PersonaDireccionPK pPk = new PersonaDireccionPK();

			pPk.setNif(nif);
			pPk.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			pPk.setIdDireccion(dir.getIdDireccion());

			persDir.setId(pPk);
			persDir.setFechaInicio(new Date());
			persDir.setDireccion(dir);

			dao.addPersonaDireccion(persDir);

			personaPant.getId().setNumColegiado(utilesColegiado.getNumColegiadoSession());
			personaPant.setEstado(1);

			List<PersonaDireccion> personaDirs = new ArrayList<PersonaDireccion>();
			personaDirs.add(persDir);

			personaPant.setPersonaDireccions(personaDirs);

			datosCliente.getFactura().setPersona(personaPant);
			List<Factura> facturas = new ArrayList<Factura>();
			facturas.add(datosCliente.getFactura());
			datosCliente.getFactura().getPersona().setFacturas(facturas);

		} else if (!existePersona){

			//Creo Persona
			personaPant.getId().setNumColegiado(utilesColegiado.getNumColegiadoSession());
			personaPant.getId().setNif(nif);
			Persona pers = new Persona();
			pers = personaPant;
			pers.setEstado(1);

			BigDecimal tipoPersona = NIFValidator.validarNif(pers.getId().getNif());
			if ("1".equals(String.valueOf(tipoPersona))){
				pers.setTipoPersona("FISICA");
			} else if ("2".equals(String.valueOf(tipoPersona))){
				pers.setTipoPersona("JURIDICA");
			} else if ("3".equals(String.valueOf(tipoPersona))){
				pers.setTipoPersona("FISICA");
			}

			String anagrama = Anagrama.obtenerAnagramaFiscal(pers.getApellido1RazonSocial(), pers.getId().getNif());
			pers.setAnagrama(anagrama);

			if ("-".equals(pers.getEstadoCivil())) pers.setEstadoCivil(null);
			if ("-".equals(pers.getSexo())) pers.setSexo(null);
			if ("-".equals(pers.getTipoPersona())) pers.setTipoPersona(null);
			dao.saveOrUpdatePersona(pers);

			EvolucionPersona evolPersona = new EvolucionPersona();
			EvolucionPersonaPK ePk = new EvolucionPersonaPK();
			ePk.setNif(pers.getId().getNif());
			ePk.setNumColegiado(pers.getId().getNumColegiado());
			ePk.setFechaHora(utilesFecha.getTimestampActual());
			evolPersona.setId(ePk);

			//Solo seteo de la persona Nueva ya que la vieja no existe y es todo nulo
			evolPersona.setApellido1Nue(personaPant.getApellido1RazonSocial());

			evolPersona.setApellido2Nue(personaPant.getApellido2());

			evolPersona.setNombreNue(personaPant.getNombre());

			evolPersona.setFechaNacimientoNue(personaPant.getFechaNacimiento());

			evolPersona.setTipoTramite("FACT");
			evolPersona.setTipoActuacion("CREACION");
			evolPersona.setOtros("Facturacion");

			evolPersona.setIdUsuario(utilesColegiado.getIdUsuarioSessionBigDecimal());
			//Creo PersonaDireccion

			dao.addEvolucionPersona(evolPersona);

			//Creo Direccion
			Direccion dir = new Direccion();
			dir = personaPant.getDireccionActual();
			dir.getMunicipio().getId().setIdProvincia(dir.getMunicipio().getProvincia().getIdProvincia());					
			dir.setIdDireccion(0);					

			dao.addDireccion(dir);

			//Creo PersonaDireccion

			PersonaDireccion persDir = new PersonaDireccion();					
			PersonaDireccionPK pPk = new PersonaDireccionPK();

			pPk.setNif(nif);
			pPk.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			pPk.setIdDireccion(dir.getIdDireccion());

			persDir.setId(pPk);
			persDir.setFechaInicio(new Date());
			persDir.setDireccion(dir);
			persDir.setPersona(pers);

			dao.addPersonaDireccion(persDir);

			personaPant.getId().setNumColegiado(utilesColegiado.getNumColegiadoSession());
			personaPant.setEstado(1);

			List<PersonaDireccion> personaDirs = new ArrayList<PersonaDireccion>();
			personaDirs.add(persDir);

			personaPant.setPersonaDireccions(personaDirs);

			datosCliente.getFactura().setPersona(personaPant);
			List<Factura> facturas = new ArrayList<Factura>();
			facturas.add(datosCliente.getFactura());
			datosCliente.getFactura().getPersona().setFacturas(facturas);

		}

		for (int x = 0; x < getDatosCliente().getFactura().getFacturaConceptos().size(); x++) {
			getDatosCliente().getFactura().getFacturaConceptos().get(x).setNumColegiado(utilesColegiado.getNumColegiadoSession());
			getDatosCliente().getFactura().getFacturaConceptos().get(x).setNumFactura(datosCliente.getFactura().getId().getNumFactura());

		}

		getDatosCliente().getFactura().setNif(getDatosCliente().getFactura().getPersona().getId().getNif());
		getDatosCliente().getFactura().setVisible(new BigDecimal(1));
		getDatosCliente().getFactura().setImporteTotal(importeFacturaFloat);
		getDatosCliente().getFactura().getId().setNumFactura(numeroFactura);

		// getDatosCliente().getFactura().setN
		String numFacturasBBDD = "";

		//		numFacturasBBDD = modelo.compruebaNumFactura(datosCliente, dao,	resultado);
		numFacturasBBDD = dao.existeFactura(datosCliente.getFactura().getId().getNumFactura());

		if ((tipoAGuardar.equals(GUARDAR_MODIFICAR_FACTURA)) || "0".equals(numFacturasBBDD) ) {

			datosCliente.getFactura().setCheckPdf(EstadoFacturacion.EstadoNueva.getValorEnum());
			datosCliente.setIsRectificativa(Boolean.FALSE);

			// JRG Los datos de concepto y prov. fondos no estan en
			// session. Estan en el objeto datosCliente que se recibe.

			facturaOtro.setFactura(getDatosCliente().getFactura());

			facturaProvFondo.setFactura(getDatosCliente().getFactura());

			if (facturaOtro != null) {
				getDatosCliente().getFactura().setFacturaOtro(facturaOtro);
			}
			if (facturaProvFondo != null) {
				getDatosCliente().getFactura().setFacturaProvFondo(facturaProvFondo);
			}

			try {
				resultado =dao.saveOrUpdate(getDatosCliente().getFactura());
			} catch (Exception e) {
				log.error("Error al realizar las validaciones de facturacion: "	+ e);
				datosCliente.setIsPantallaAlta(Boolean.TRUE);
				setDatosCliente(datosCliente);
				getSession().put("datosCliente", datosCliente);
				addActionError("Se ha producido un error al guardar la factura");
				throw e;
				//En caso de error lanza la excepcion para que sea recogida por el metodo de guardarNueva o Modificar.
				//ConstantesFacturacion.RESULT_ALTA_FACTURACION;
			}

			datosCliente.setIsError(Boolean.TRUE);
			datosCliente.setIsPantallaAlta(Boolean.FALSE);
			datosCliente.setNumClave(recuperarClaveColegiado());
			datosCliente.setMensajeClave(ConstantesFacturacion.ALERT_MENSAJE_MODFICAR);
			datosCliente.setMensajeBorrador(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
			datosCliente.setMensajePDF(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
			datosCliente.setMensajeXML(ConstantesFacturacion.ALERT_MENSAJE_XML);			
			datosCliente.setMensajeImprimir(ConstantesFacturacion.ALERT_MENSAJE_IMPRIMIR);
			datosCliente.setEstadoPDF("");
			datosCliente.setEstadoXML("");
			datosCliente.setTipoPDF("");
			addActionMessage("La factura se ha guardado correctamente");
			log.debug("Fin guardarNuevaFactura");
		} else {
			log.info("El numero de Factura ya existía o bien otro usuario lo esta utilizando");
			int numMaxBBDD = dao.buscaMaxFactura(datosCliente.getFactura().getNumCodigo());
			int numFacturasN = numMaxBBDD + 1;
			Fecha fechaFrac = utilesFecha.getFechaActual();
			String fecha = fechaFrac.getMes() + fechaFrac.getAnio().substring(2);
			String valorFactura = String.valueOf(numFacturasN);
			String idFactura = utiles.rellenarCeros(valorFactura, 5);
			String numFactura = datosCliente.getFactura().getNumSerie()	+ datosCliente.getFactura().getNumCodigo() + fecha + idFactura;
			datosCliente.getFactura().setNumeracion(new BigDecimal(idFactura));
			datosCliente.getFactura().getId().setNumFactura(numFactura);

			datosCliente.setIsError(Boolean.FALSE);
			datosCliente.setIsPantallaAlta(Boolean.TRUE);
			addActionError("El Nº de Factura ya existe o bien otro usuario lo esta usando");
			addActionError("El nuevo Nº de Factura propuesto es "	+ numFactura);

		}

	}

	/**
	 * Metodo que se encarga de recuperar los valores introducidos por el
	 * usuario, y primero validar que todos los campos obligatorios, han sido
	 * rellenados correctamente y posteriormente modificar la factura en BB.DD.
	 * 
	 * @return
	 * @throws Throwable
	 */
	public String modificarFactura() throws Throwable {

		try {

			ModeloNuevaFactura modelo = new ModeloNuevaFactura();
			mantenimientoCamposSesion();
			ResultBean resultado = modelo.validarDatosAlta(datosCliente);
			datosCliente.setIsPantallaAlta(Boolean.FALSE);

			if (resultado.getError())
				datosCliente.setIsError(Boolean.FALSE); // Hay Errores, por lo tanto los botones
			// Borrador y PDF deshabilitados
			else
				datosCliente.setIsError(Boolean.TRUE); // No hay Errores, por lo tanto los botones

			if (resultado.getError()) {
				for (int i = 0; i < resultado.getListaMensajes().size(); i++) {
					if (resultado.getListaMensajes().get(i) != null) {
						String mensaje = resultado.getListaMensajes().get(i);
						mensaje = mensaje.replace("--", " - ");
						addActionError(mensaje);
					}
				}
			} else {
				persistirSessionPantalla(resultado, GUARDAR_MODIFICAR_FACTURA);

				//				dao.saveOrUpdate(datosCliente.getFactura());
				ArrayList<String> listaExpedientes = (ArrayList<String>) dao.listaExpedientes(datosCliente.getFactura().getId().getNumFactura());
				datosCliente.setListaExpedientes(listaExpedientes);
				datosCliente.setIsRectificativa(Boolean.FALSE);
				datosCliente.setNumClave(recuperarClaveColegiado());

				// siendo 0 --> NUEVA
				//datosCliente.getFactura().setCheckPdf(EstadoFacturacion.EstadoNueva.getValorEnum());
				if (datosCliente.getFactura().getCheckPdf().equals(EstadoFacturacion.EstadoNueva.getValorEnum())){
					datosCliente.setIsRectificativa(Boolean.FALSE);
				}else{
					datosCliente.setIsRectificativa(Boolean.TRUE);
				}
				if (resultado.getError()) {
					datosCliente.setIsError(Boolean.FALSE);
					addActionError("Se ha producido un error al Modificar la factura");
				} else {
					datosCliente.setIsError(Boolean.TRUE);
					addActionMessage("Factura Modificada Correctamente");
				}
			}
			setDatosCliente(datosCliente);
			getSession().put("datosCliente", datosCliente);
			return ConstantesFacturacion.RESULT_ALTA_FACTURACION;
		} catch (OegamExcepcion e) {
			log.error("Error al realizar las validaciones de facturacion: "	+ e);
		}
		return ConstantesFacturacion.RESULT_ALTA_FACTURACION;
	}

	/**
	 * Función que buscará el tipo de detalle que es, accederá a base de datos y
	 * obtendrá los datos correspondientes dependiendo del tipo de trámite que
	 * sea.
	 * 
	 * @return
	 * @throws ParseException
	 */
	public String detalleConcepto() {

		setDatosCliente((DatosClienteBean) getSession().get("datosCliente"));
		// String idConcept = (String) getSession().get("idConcepto");
		getSession().put("idConcepto", idConcepto);

		String idConcept = String.valueOf(idConcepto);
		// FacturaConcepto factConcepto = dao.recuperarConcepto(idConcepto);

		for (int i = 0; i < datosCliente.getFactura().getFacturaConceptos()
				.size(); i++) {
			FacturaConcepto fConc = datosCliente.getFactura()
					.getFacturaConceptos().get(i);
			String identConcepto = String.valueOf(fConc.getIdConcepto());
			if (idConcept.equals(identConcepto)) {
				datosCliente.setFacturaConceptoCargado(fConc);
				return ConstantesFacturacion.RESULT_ALTA_FACTURACION;
			}

		}
		addActionError("Ha habido un error al cambiar de Concepto");
		return ConstantesFacturacion.RESULT_ALTA_FACTURACION;
	}


	public String generarPDF() throws Throwable {
		try {
			modificarFactura();
			imprimirPDF();
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			return ConstantesFacturacion.RESULT_CONSULTA_FACTURACION;
		}

		if (datosCliente.getEstadoPDF().endsWith(
				ConstantesFacturacion.ESTADO_PDF_FIN))
			return ConstantesFacturacion.RESULT_DESCARGAR_PDF_FACTURACION;
		else
			return ConstantesFacturacion.RESULT_ALTA_FACTURACION;
	}

	/**
	 * Metodo que se encarga de recuperar los valores de BB.DD. para el numero
	 * de factura seleccionado y crear el pdf con esos datos.
	 * 
	 * @return
	 * @throws Throwable
	 */
	public String imprimirPDF() throws Throwable {
		String donde = ConstantesFacturacion.RESULT_ALTA_FACTURACION;
		String tipo = EstadoFacturacion.EstadoFacturaGenerada.getNombreEnum();
		String estado = EstadoFacturacion.EstadoFacturaGenerada.getValorEnum();

		try {
			// DRC@14-08-2012 Con este parametro "donde", valido de que pantalla
			// vengo y a que pantalla le tengo que devolver el contro.

			if (datosCliente == null)
				datosCliente = new DatosClienteBean();

			if (request.getParameter("donde") != null) {
				String sDonde = request.getParameter("donde");
				if (sDonde.equalsIgnoreCase("crectificativa")) {
					donde = ConstantesFacturacion.RESULT_CONSULTA_FACTURACION_RECTIFICATIVA;
					tipo = EstadoFacturacion.EstadoFacturaRectificativa.getNombreEnum();
					estado = EstadoFacturacion.EstadoFacturaGenerada.getValorEnum();
				} else if (sDonde.equalsIgnoreCase("cfactura")) {
					donde = ConstantesFacturacion.RESULT_CONSULTA_FACTURACION;
					tipo = EstadoFacturacion.EstadoFacturaGenerada.getNombreEnum();
					estado = EstadoFacturacion.EstadoFacturaGenerada.getValorEnum();
				} else if (sDonde.equalsIgnoreCase("canulada")) {
					donde = ConstantesFacturacion.RESULT_CONSULTA_ANULADA;
					tipo = EstadoFacturacion.EstadoFacturaAnulada.getNombreEnum();
					estado = EstadoFacturacion.EstadoFacturaAnulada.getValorEnum();
				} else if (sDonde.equalsIgnoreCase("cabono")) {
					donde = ConstantesFacturacion.RESULT_CONSULTA_FACTURACION_ABONO;
					tipo = EstadoFacturacion.EstadoFacturaAbono.getNombreEnum();
					estado = EstadoFacturacion.EstadoFacturaGenerada.getValorEnum();	
				} else if (sDonde.equalsIgnoreCase("altafactura")) {
					donde = ConstantesFacturacion.RESULT_ALTA_FACTURACION;
					tipo = EstadoFacturacion.EstadoFacturaGenerada.getNombreEnum();
					estado = EstadoFacturacion.EstadoFacturaGenerada.getValorEnum();
					//Recupero de la sesion los Conceptos ya que vengo del Alta
					mantenimientoCamposSesion();
				}
			} else {
				donde = ConstantesFacturacion.RESULT_ALTA_FACTURACION;
			}

			String indices = getNumsFactura();
			String[] codSeleccionados = indices.split("-");
			Factura factura = null;
			mantenimientoCamposNavegar();
			String numFactura = codSeleccionados[0];

			ArrayList<ConceptoIReport> listaConceptoIReport = null;
			for (int i = 0; i < codSeleccionados.length; i++) {
				// 1º. Carga un array de beans específicos para la plantilla del
				// ireport
				
				// Mantis 13737. David Sierra: En el caso de que en la busqueda previa se utilice el filtrado por Num Colegiado 
				// (solo lo puede hacer el Administrador) se controla que el numColegiado se corresponda con el contenido 
				// en el numFactura del registro seleccionado 
				if(ConstantesFacturacion.NUM_COLEGIADO_ADMINISTRADOR.equals(utilesColegiado.getNumColegiadoSession())) {
					numColegiadoFactura = numFactura.substring(4, 8);
				}
				// Si el usuario no tiene permisos de administrador el numColegiado se recupera de sesion
				else {
					numColegiadoFactura = utilesColegiado.getNumColegiadoSession();
				}
				
				factura = dao.detalleFacturaCompleto(codSeleccionados[i], numColegiadoFactura);

				Float totalSuplidos = new Float(0);

				if (factura != null) {
					List<FacturaConcepto> conceptos = factura.getFacturaConceptos();
					FacturaOtro otroFactura = factura.getFacturaOtro();
					FacturaProvFondo provisionFondos = factura.getFacturaProvFondo();

					listaConceptoIReport = new ArrayList<ConceptoIReport>();

					// Añadimos los conceptos
					int numConcepto = 1;
					boolean printed = false;
					for (FacturaConcepto conceptoAux : conceptos) {
						if (conceptoAux.getConcepto() != null) {
							listaConceptoIReport.add(getUtilidadIreport()
									.convertirBeanFacturaConcepto(conceptoAux,
											printed, numConcepto));
							numConcepto++;
							// Imprimimos Honorarios;
							if (!conceptoAux.getFacturaHonorarios().isEmpty()) {
								boolean printHono = false;
								for (FacturaHonorario honorario : conceptoAux.getFacturaHonorarios()) {
									if(!(honorario.getHonorarioDescripcion().equals("Introduzca la descripcion") && honorario.getHonorario().equals("0") && honorario.getHonorarioTotal().equals("0"))){
										listaConceptoIReport.add(getUtilidadIreport().convertirBeanFacturaHonorario(honorario, printHono));
										printHono = true;
									}
								}
							}
							// Imprimimos Suplidos
							if (!conceptoAux.getFacturaSuplidos().isEmpty()) {
								boolean printSupli = false;
								for (FacturaSuplido suplido : conceptoAux
										.getFacturaSuplidos()) {
									if(!(suplido.getSuplidoDescripcion().equals("Introduzca la descripcion") && suplido.getSuplido().equals("0") && suplido.getSuplidoTotal().equals("0"))){
										listaConceptoIReport.add(getUtilidadIreport()
												.convertirBeanFacturaSuplido(
														suplido, printSupli));
										printSupli = true;
									}
								}
							}
							// Imprimimos Gastos
							if (!conceptoAux.getFacturaGastos().isEmpty()) {
								boolean printGasto = false;
								for (FacturaGasto gasto : conceptoAux
										.getFacturaGastos()) {
									if(!(gasto.getGastoDescripcion().equals("Introduzca la descripcion") && gasto.getGasto().equals("0") && gasto.getGastoTotal().equals("0"))){
										listaConceptoIReport.add(getUtilidadIreport()
												.convertirBeanFacturaGasto(gasto,
														printGasto));
										printGasto = true;
									}
								}
							}
						}
					}
					printed = false;
					// Añadimos Otros
					printed = false;
					if (otroFactura != null) {
						listaConceptoIReport
						.add(getUtilidadIreport().convertirBeanFacturaOtros(
								otroFactura, printed));
						printed = true;
					}
					// Añadimos provision de fondos
					printed = false;
					if (provisionFondos != null) {
						listaConceptoIReport.add(getUtilidadIreport()
								.convertirBeanFacturaProvision(provisionFondos,
										printed));
						printed = true;
					}
					if (!listaConceptoIReport.isEmpty()) {
						listaConceptoIReport.get(
								listaConceptoIReport.size() - 1).setIsLastLine(
										Boolean.TRUE);
					}
				}

				EmisorFactura emisor = null;
				if(factura.getEmisor() != null && factura.getEmisor().equals(EmisorFactura.GESTOR.getNombre())){
					emisor = EmisorFactura.GESTOR;
				}else if(factura.getEmisor() != null && factura.getEmisor().equals(EmisorFactura.GESTORIA.getNombre())){
					emisor = EmisorFactura.GESTORIA;
				}else{
					addActionError("Seleccione gestor/gestoría como emisor de la factura");
					return donde;
				}

				// 2º. Carga el mapa de parámetros de la plantilla del ireport
				Map<String, Object> parametros = getUtilidadIreport().cargarParametrosFactura(emisor, factura, totalSuplidos, listaConceptoIReport);

				// Comprueba el estado de la peticion, para saber si mostrar el
				// pdf o simplemente devolver el control al cliente para
				// actualziar campos
				String estadoPDF = "";
				try {
					if (request.getParameter("estado") != null)
						estadoPDF = request.getParameter("estado");

					// Recupera el parámetro modelo que indica si generar pdf o
					// borrador de la factura:
					// //////////////////// Se recupera el detalle de la factura
					// para ver su estado. Si es anulada lo inserta a mano, si
					// no lo recoge del parametro modelo.
					if (dao.isFacturaRectificativa(numFactura))
						datosCliente.setTipoPDF("rectificativa");
					else if (dao.isFacturaAnulada(numFactura))
						datosCliente.setTipoPDF("anulada");
					else if(dao.isFacturaAbono(numFactura))
						datosCliente.setTipoPDF("abono");
					else 
						datosCliente.setTipoPDF(request.getParameter("modelo"));
				} catch (Exception e) {
					log.error("Error al recuperar los parametros: " + e);
				}

				// 3º. Invocar la lógica de construcción del pdf
				File pdfFactura = getUtilidadIreport().generarPdfFactura(
						listaConceptoIReport, parametros, datosCliente
						.getTipoPDF());

				// DRC@09-08-2012 Si el estado es INICIAL y no ha dado ningun
				// error, actualizo el valor en BBDD, para que al refrescar la
				// pantalla
				// ya apareza el cambio registrado y actualizo el campo a OK
				//JRG Se incluye tipoPDF.equals rectificativa porque si es nueva y rectificativa hay que cambiarlo a "generada" 
				//(previamente el estado sea inicial). Igual para rectificativa
				if (estadoPDF.endsWith(ConstantesFacturacion.ESTADO_PDF_INICIAL)) {
					if (datosCliente.getTipoPDF().equalsIgnoreCase("pdf")
							|| datosCliente.getTipoPDF().equalsIgnoreCase("rectificativa")
							|| datosCliente.getTipoPDF().equalsIgnoreCase("abono")) {
						// linea = obtenerFactura(codSeleccionados[i],
						// utilesColegiado.getNumColegiado());
						// linea = dao.detalleFactura(codSeleccionados[i],
						// utilesColegiado.getNumColegiado());
						FacturaPK fId = new FacturaPK();
						fId.setNumColegiado(utilesColegiado.getNumColegiadoSession());
						fId.setNumFactura(codSeleccionados[i]);
						dao.checkPDF(fId, estado);
					}
					datosCliente.setEstadoPDF(numFactura+ ConstantesFacturacion.ESTADO_PDF_OK);
				}

				// DRC@09-08-2012 Si el estado es PDF, muestro el PDF al usuario
				// y actualizo el campo a FIN
				if (estadoPDF.endsWith(ConstantesFacturacion.ESTADO_PDF_PDF)) {
					inputStream = new FileInputStream(pdfFactura);
					setFicheroDescarga(pdfFactura.getName());
					getSession().put("factura", pdfFactura);

					datosCliente.setEstadoPDF(numFactura + ConstantesFacturacion.ESTADO_PDF_FIN);
				}
			}

			if (donde.equalsIgnoreCase(ConstantesFacturacion.RESULT_CONSULTA_FACTURACION)) {

				beanNormal.setMensajeBorrador(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
				beanNormal.setMensajePDF(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
				beanNormal.setMensajeXML(ConstantesFacturacion.ALERT_MENSAJE_XML);
				beanNormal.setMensajeImprimir(ConstantesFacturacion.ALERT_MENSAJE_IMPRIMIR);
				// Mantis 13737. David Sierra: Se mantiene la informacion, recuperada anteriormente de la consulta de facturas
				mantenimientoCamposBuscar();

			} else if (donde.equalsIgnoreCase(ConstantesFacturacion.RESULT_CONSULTA_FACTURACION_RECTIFICATIVA)) {

				beanRect.setMensajeBorrador(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
				beanRect.setMensajePDF(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
				beanRect.setMensajeImprimir(ConstantesFacturacion.ALERT_MENSAJE_IMPRIMIR);

				listaConsultaFacturaRect = dao.listaFacturas(beanRect,EstadoFacturacion.EstadoFacturaRectificativa.getValorEnum(), tipo);
				listaConsultaFacturaRect = getIncluirColumnaCheckPDFCambioEstado(listaConsultaFacturaRect);

				// Actualizamos la lista de facturas
				getSession().put("listaConsultaFacturaRect", listaConsultaFacturaRect);

			} else if (donde.equalsIgnoreCase(ConstantesFacturacion.RESULT_CONSULTA_FACTURACION_ABONO)) {

				beanAbon.setMensajeBorrador(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
				beanAbon.setMensajePDF(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
				beanAbon.setMensajeImprimir(ConstantesFacturacion.ALERT_MENSAJE_IMPRIMIR);

				listaConsultaFacturaAbon = dao.listaFacturas(beanAbon,EstadoFacturacion.EstadoFacturaAbono.getValorEnum(), tipo);
				listaConsultaFacturaAbon = getIncluirColumnaCheckPDFCambioEstado(listaConsultaFacturaAbon);

				// Actualizamos la lista de facturas
				getSession().put("listaConsultaFacturaAbon", listaConsultaFacturaAbon);

			}
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			datosCliente.setEstadoPDF(numFactura + ConstantesFacturacion.ESTADO_PDF_KO);
			return donde;
		}

		if (datosCliente.getEstadoPDF().endsWith(
				ConstantesFacturacion.ESTADO_PDF_FIN))
			return ConstantesFacturacion.RESULT_DESCARGAR_PDF_FACTURACION;
		else
			return donde;
	}

	/**
	 * Método que busca un Cliente de Factura en bbdd por el NIF/CIF y devuelve
	 * todos los datos del Cliente.
	 * 
	 * @return
	 */
	public String buscarInterviniente() {

		mantenimientoCamposSesion();		
		try {
			String nif = datosCliente.getFactura().getPersona().getId().getNif();
			hibernate.entities.personas.Persona pers = dao.buscarPersona(nif,utilesColegiado.getNumColegiadoSession());

			if (pers != null){
				datosCliente.getFactura().setPersona(pers);

			}

		} catch (Throwable e) {
			addActionError(e.toString());
		}

		setDatosCliente(datosCliente);
		getSession().put("datosCliente", datosCliente);
		return ConstantesFacturacion.RESULT_ALTA_FACTURACION;
	}

	public String recuperarClaveColegiado() throws OegamExcepcion {
		String numClave = dao.getClaveColegiado(utilesColegiado.getNumColegiadoSession());

		if (numClave==null || numClave.equals(""))
			numClave=ConstantesFacturacion.CLAVE_DEFECTO;

		return numClave;
	}

	/**
	 * Metodo que se encarga de transformar los valores de BBDD, a textos
	 * legibles por el usuario. Tipo de Estado y su correspondiente significado:
	 * Estado 1 --> FACTURA GENERADO --> Indica que si quieres modificar se
	 * genera Factura Rectificada Estado 0 --> NUEVA --> Indica que todavia no
	 * has generado el PDF y por lo tanto puedes modificar las veces que
	 * quieras.
	 * 
	 * @param listaConsultaFactura
	 * @return
	 */
	public List<Factura> getIncluirColumnaCheckPDFCambioEstado(List<Factura> listaConsultaFactura) {
		try {
			List<Factura> listaConsultaFacturaMod = new ArrayList<Factura>();
			for (int i = 0; i < listaConsultaFactura.size(); i++) {
				Factura FacturaMod = new Factura();
				FacturaMod = (Factura) listaConsultaFactura.get(i);
				// Se realiza la conversion con el modo de las clases de tipo
				// Enum > 1.5
				if (FacturaMod.getCheckPdf().equalsIgnoreCase(
						EstadoFacturacion.EstadoNueva.getValorEnum()))
					FacturaMod.setCheckPdf(EstadoFacturacion.EstadoNueva
							.getNombreEnum());
				else if (FacturaMod.getCheckPdf().equalsIgnoreCase(
						EstadoFacturacion.EstadoFacturaGenerada.getValorEnum()))
					FacturaMod
					.setCheckPdf(EstadoFacturacion.EstadoFacturaGenerada
							.getNombreEnum());
				else if (FacturaMod.getCheckPdf().equalsIgnoreCase(
						EstadoFacturacion.EstadoFacturaRectificativa
						.getValorEnum()))
					FacturaMod
					.setCheckPdf(EstadoFacturacion.EstadoFacturaRectificativa
							.getNombreEnum());
				else if (FacturaMod.getCheckPdf().equalsIgnoreCase(
						EstadoFacturacion.EstadoFacturaAnulada.getValorEnum()))
					FacturaMod
					.setCheckPdf(EstadoFacturacion.EstadoFacturaAnulada
							.getNombreEnum());
				else if (FacturaMod.getCheckPdf().equalsIgnoreCase(
						EstadoFacturacion.EstadoFacturaAbono.getValorEnum()))
					FacturaMod
					.setCheckPdf(EstadoFacturacion.EstadoFacturaAbono
							.getNombreEnum());

				listaConsultaFacturaMod.add(FacturaMod);
			}
			return listaConsultaFacturaMod;
		} catch (Exception e) {
			log.error("Error al convertir los valores de Check PDF: " + e);
			return listaConsultaFactura;
		}
	}

	public String generarPDFAlta() throws Throwable {
		try {
			String resp = modificarFacturaPDF();
			if (resp.equalsIgnoreCase("")) {
				imprimirPDF();
			} else {
				datosCliente.setEstadoPDF(numFactura + ConstantesFacturacion.ESTADO_PDF_KO);
				return ConstantesFacturacion.RESULT_ALTA_FACTURACION;
			}
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			return ConstantesFacturacion.RESULT_ALTA_FACTURACION;
		}

		if (datosCliente.getEstadoPDF().endsWith(
				ConstantesFacturacion.ESTADO_PDF_FIN))
			return ConstantesFacturacion.RESULT_DESCARGAR_PDF_FACTURACION;
		else
			return ConstantesFacturacion.RESULT_ALTA_FACTURACION;
	}



	public String modificarFacturaPDF() throws Throwable {

		ResultBean resultado = null;
		String msgError = "";
		try {
			mantenimientoCamposSesion();
			ModeloNuevaFactura modelo = new ModeloNuevaFactura();
			resultado = modelo.validarDatosAlta(datosCliente);
			datosCliente.setIsPantallaAlta(Boolean.FALSE);

			if (resultado.getError())
				datosCliente.setIsError(Boolean.FALSE); // Hay Errores, por lo tanto los botones
			// Borrador y PDF deshabilitados
			else
				datosCliente.setIsError(Boolean.TRUE); // No hay Errores, por lo tanto los botones
			// Borrador y PDF habilitados

			if (resultado.getError()) {
				for (int i = 0; i < resultado.getListaMensajes().size(); i++) {
					if (resultado.getListaMensajes().get(i) != null) {
						String mensaje = resultado.getListaMensajes().get(i);
						mensaje = mensaje.replace("--", " - ");
						addActionError(mensaje);
						msgError = mensaje;
					}
				}
			} else {
				resultado = dao.saveOrUpdate(datosCliente.getFactura());
				datosCliente.setIsRectificativa(Boolean.FALSE);

				// DRC@07-08-2012 Al modificar una factura su estado sigue
				// siendo 0 --> NUEVA
				datosCliente.getFactura().setCheckPdf(
						EstadoFacturacion.EstadoNueva.getValorEnum());
				if (resultado.getError()) {
					datosCliente.setIsError(Boolean.FALSE);
					addActionError(resultado.getMensaje());
					msgError = resultado.getMensaje();
				} else {
					datosCliente.setIsError(Boolean.TRUE);
					addActionMessage(resultado.getMensaje());
				}
			}

		} catch (OegamExcepcion e) {
			log.error("Error al realizar las validaciones de facturacion: "
					+ e);
		}
		setDatosCliente(datosCliente);
		getSession().put("datosCliente", datosCliente);
		if (resultado == null || resultado.getError()) {
			return msgError;
		} else {
			return msgError;
		}
	}



	// public void actualizaDatosCliente(String idConceptoCargado){
	//	
	// for (int i=0;i<
	// datosCliente.getFactura().getFacturaConceptos().size();i++){
	// if
	// (idConceptoCargado.equals(String.valueOf(datosCliente.getFactura().getFacturaConceptos().get(i).getIdConcepto()))){
	// datosCliente.getFactura().getFacturaConceptos().get(i).getFacturaHonorarios().remove(getHonorarioIdBorrar());
	// }
	// }
	//		
	//		
	// }

	public String nuevoHonorario() {

		setDatosCliente((DatosClienteBean) getSession().get("datosCliente"));

		int MaxHonorarioConcepto = 0;

		for (int i = 0; i < datosCliente.getFacturaConceptoCargado()
				.getFacturaHonorarios().size(); i++) {
			if (datosCliente.getFacturaConceptoCargado().getFacturaHonorarios()
					.get(i).getIdHonorario() > MaxHonorarioConcepto) {
				MaxHonorarioConcepto = (int) datosCliente
						.getFacturaConceptoCargado().getFacturaHonorarios()
						.get(i).getIdHonorario();
			}
		}

		FacturaHonorario fHonorario = new FacturaHonorario();
		fHonorario.setIdHonorario(MaxHonorarioConcepto + 1);

		// Actualizo el Concepto cargado
		datosCliente.getFacturaConceptoCargado().getFacturaHonorarios().add(
				fHonorario);

		// String idConceptoCargado =
		// String.valueOf(datosCliente.getFacturaConceptoCargado().getIdConcepto());

		// Actualizo el objeto datosCliente que lo tiene todo

		// for (int i=0;i<
		// datosCliente.getFactura().getFacturaConceptos().size();i++){
		// if
		// (idConceptoCargado.equals(String.valueOf(datosCliente.getFactura().getFacturaConceptos().get(i).getIdConcepto()))){
		// datosCliente.getFactura().getFacturaConceptos().get(i).getFacturaHonorarios().add(fHonorario);
		// }
		// }

		return ConstantesFacturacion.RESULT_ALTA_FACTURACION;

	}

	public String inicioNuevaFactura() throws Throwable {

		parametrosBusqueda = new HashMap<String, Object>();
		listaConsultaTramiteTraficoFacturacion = new PaginatedListImpl();
		ModeloTrafico modeloTrafico = new ModeloTrafico();
		consultaTramiteTraficoNuevaFacturacion = new ConsultaTramiteTraficoFacturacionBean(true);
		consultaTramiteTraficoNuevaFacturacion.setFechaAlta(utilesFecha.getFechaFracionadaActual());
		mantenimientoCamposNavegarDeFactura();

		if(null!=resultadosPorPagina){
			getSession().put(ConstantesSession.RESULTADOS_PAGINA,resultadosPorPagina); 
		}else{
			getSession().put(ConstantesSession.RESULTADOS_PAGINA, UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO); 
		}

		getSession().put(ConstantesSession.CONSULTA_TT_NUEVA_FACTURACION, consultaTramiteTraficoNuevaFacturacion);

		listaConsultaTramiteTraficoFacturacion.setBaseDAO(modeloTrafico);
		listaConsultaTramiteTraficoFacturacion.getBaseDAO().setParametrosBusqueda(parametrosBusqueda);
		getSession().put(ConstantesSession.LISTA_CONSULTA_TRAMITE_TRAFICO_FACTURACION_CONSULTA, listaConsultaTramiteTraficoFacturacion);

		//Definido en facturacion_struts.xml que a su vez busca en tiles.xml y llama a consultaTramiteTraficoFacturacion.jsp
		return "nuevaFactura"; 

	}



	// ************************************************************************************************
	// ************************************************************************************************
	// * Getter & Setter *
	// ************************************************************************************************
	// ************************************************************************************************
	public DatosClienteBean getDatosCliente() {
		return datosCliente;
	}

	public String buscarTramitesFacturacion() throws Throwable {


		getSession().put(ConstantesSession.CONSULTA_TT_NUEVA_FACTURACION, consultaTramiteTraficoNuevaFacturacion);
		log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA+"buscar.");

		mantenimientoCamposBuscarDeFactura();

		if(listaConsultaTramiteTraficoFacturacion == null) {
			log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA+"al obtener lista de tramites.");
			log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA+" buscar.");
			return ERROR;
		}

		listaConsultaTramiteTraficoFacturacion.establecerParametros(getSort(), getDir(), "1" , getResultadosPorPagina());
		listaConsultaTramiteTraficoFacturacion.getBaseDAO().setParametrosBusqueda(parametrosBusqueda);

		return "nuevaFactura";		
	}

	/**
	 * Método para actualizar los parámetros de paginación. Proceso:
	 * 
	 * -Recupero el bean de búsqueda de sesión.
	 * -Actualizo los parámetros de búsqueda.
	 * -Actualizo en sesión los de la paginación.
	 * 
	 */
	private void mantenimientoCamposNavegarDeFactura(){

		if (resultadosPorPagina!=null){
			getSession().put("resultadosPorPagina", resultadosPorPagina);
		}

		ConsultaTramiteTraficoFacturacionBean consultaTramitesFacturacion = (ConsultaTramiteTraficoFacturacionBean) 
				getSession().get(ConstantesSession.CONSULTA_TT_NUEVA_FACTURACION);
		if(consultaTramitesFacturacion!=null){
			setConsultaTramiteTraficoFactura(consultaTramitesFacturacion);	
		}
		PaginatedListImpl listaConsulta =(PaginatedListImpl)getSession().get(ConstantesSession.LISTA_CONSULTA_TRAMITE_TRAFICO_FACTURACION_CONSULTA);
		if(listaConsulta != null){
			setListaConsultaTramiteTraficoFacturacion(listaConsulta);		
		}
		String resultadosPagina = (String)getSession().get(ConstantesSession.RESULTADOS_PAGINA);
		if(null != resultadosPagina){
			setResultadosPorPagina(resultadosPagina);	
		}

		getSession().put(ConstantesSession.CONSULTA_TT_NUEVA_FACTURACION, consultaTramiteTraficoNuevaFacturacion);
		actualizaParametros(consultaTramiteTraficoNuevaFacturacion);

	}

	public String crearFacturaAbono() throws Exception, OegamExcepcion {
		ResultBean resultado = new ResultBean();
		String mensaje = "";
		String indices = getNumsFactura();
		String[] codSeleccionados = indices.split("-");
		Factura linea;
		mantenimientoCamposNavegar();
		for (int i = 0; i < codSeleccionados.length; i++) {
			linea = dao.detalleFacturaCompleto(codSeleccionados[i], utilesColegiado.getNumColegiadoSession());

			if (linea != null && linea.getCheckPdf().equalsIgnoreCase(EstadoFacturacion.EstadoFacturaGenerada.getValorEnum())) {

				try {
					// DRC@07-08-2012 Rectifica el numero de Factura
					Fecha fechaFrac = utilesFecha.getFechaActual();
					String tFecha = fechaFrac.getMes() + fechaFrac.getAnio().substring(2);					
					Factura detalleFactura = dao.detalleFacturaCompleto(linea.getId().getNumFactura(), utilesColegiado.getNumColegiadoSession());
					//De momento No hay que cambiar el estado de la factura
					//A la espera de que confirmen si hay que cambiarlo
					//					try {
					//						resultado = dao.anularFactura(linea.getId(),EstadoFacturacion.EstadoFacturaAnulada.getValorEnum());
					//
					//					} catch (Exception e) {
					//						addActionError(ficheroPropiedades.getMensaje("facturacion.pdf.rectificativa.mensaje.cero"));
					//						return ConstantesFacturacion.RESULT_CONSULTA_FACTURACION;
					//					}

					Factura nFactura = new Factura();
					nFactura = (Factura) detalleFactura.clone();
					List<FacturaConcepto> listFC = new ArrayList<FacturaConcepto>();

					for (FacturaConcepto fc : nFactura.getFacturaConceptos()){
						FacturaConcepto facturaConceptoClone = new FacturaConcepto(); 
						facturaConceptoClone=		(FacturaConcepto)fc.clone();
						facturaConceptoClone.setIdConcepto(0);
						List<FacturaHonorario> listaFh = new ArrayList<FacturaHonorario>();
						List<FacturaSuplido> listaFs = new ArrayList<FacturaSuplido>();
						List<FacturaGasto> listaFg = new ArrayList<FacturaGasto>();

						for(FacturaHonorario fh : facturaConceptoClone.getFacturaHonorarios()){
							FacturaHonorario facturaHonorarioClone = new FacturaHonorario(); 
							facturaHonorarioClone=		(FacturaHonorario)fh.clone();
							//Multiplicar por -1 para poner los valores en negativo para un abono.
							facturaHonorarioClone.setHonorario(facturaHonorarioClone.getHonorario() * -1);
							facturaHonorarioClone.setHonorarioTotal(facturaHonorarioClone.getHonorarioTotal() * -1);
							facturaHonorarioClone.setHonorarioTotalIva(facturaHonorarioClone.getHonorarioTotalIva() * -1);
							facturaHonorarioClone.setHonorarioTotalIrpf(facturaHonorarioClone.getHonorarioTotalIrpf() * -1);
							facturaHonorarioClone.setHonorarioDescuento(facturaHonorarioClone.getHonorarioDescuento() * -1);

							facturaHonorarioClone.setFacturaConcepto(facturaConceptoClone);
							facturaHonorarioClone.setIdHonorario(0);
							listaFh.add(facturaHonorarioClone);
						}
						for(FacturaSuplido fs : facturaConceptoClone.getFacturaSuplidos()){
							FacturaSuplido facturaSuplidoClone = new FacturaSuplido(); 
							facturaSuplidoClone=		(FacturaSuplido)fs.clone();
							//Multiplicar por -1 para poner los valores en negativo para un abono.
							facturaSuplidoClone.setSuplido(facturaSuplidoClone.getSuplido() * -1);
							facturaSuplidoClone.setSuplidoTotal(facturaSuplidoClone.getSuplidoTotal() * -1);
							facturaSuplidoClone.setSuplidoDescuento(facturaSuplidoClone.getSuplidoDescuento() * -1);
							facturaSuplidoClone.setFacturaConcepto(facturaConceptoClone);
							facturaSuplidoClone.setIdSuplido(0);
							listaFs.add(facturaSuplidoClone);
						}
						for(FacturaGasto fg : facturaConceptoClone.getFacturaGastos()){
							FacturaGasto facturaGastoClone = new FacturaGasto();
							facturaGastoClone = (FacturaGasto)fg.clone();
							//Multiplicar por -1 para poner los valores en negativo para un abono.
							facturaGastoClone.setGasto(facturaGastoClone.getGasto() * -1);
							facturaGastoClone.setGastoTotal(facturaGastoClone.getGastoTotal() * -1);
							facturaGastoClone.setGastoTotalIva(facturaGastoClone.getGastoTotalIva() * -1);
							facturaGastoClone.setFacturaConcepto(facturaConceptoClone);
							facturaGastoClone.setIdGasto(0);
							listaFg.add(facturaGastoClone);
						}
						facturaConceptoClone.setFacturaHonorarios(listaFh);
						facturaConceptoClone.setFacturaSuplidos(listaFs);
						facturaConceptoClone.setFacturaGastos(listaFg);
						facturaConceptoClone.setFactura(nFactura);
						listFC.add(facturaConceptoClone);
					}
					nFactura.setFacturaConceptos(listFC);

					FacturaOtro fo = new FacturaOtro(); 
					fo = (FacturaOtro)nFactura.getFacturaOtro().clone();
					//Multiplicar por -1 para poner los valores en negativo para un abono.
					fo.setOtro(fo.getOtro() * -1);
					fo.setOtroDescuento(fo.getOtroDescuento() * -1);
					fo.setOtroTotalIva(fo.getOtroTotalIva());
					fo.setOtroTotal(fo.getOtroTotal() * -1);
					fo.setIdOtro(0);
					fo.setFactura(nFactura);
					nFactura.setFacturaOtro(fo);

					FacturaProvFondo fpf = new FacturaProvFondo(); 
					fpf = (FacturaProvFondo)nFactura.getFacturaProvFondo().clone();
					//Multiplicar por -1 para poner los valores en negativo para un abono.
					fpf.setFondo(fpf.getFondo() * -1);
					fpf.setFondoTotal(fpf.getFondoTotal() * -1);
					fpf.setIdFondo(0);
					fpf.setFactura(nFactura);
					nFactura.setFacturaProvFondo(fpf);

					nFactura.setImporteTotal(nFactura.getImporteTotal() * -1);

					String numFacturaAnulada = detalleFactura.getId().getNumFactura();

					nFactura.setNumSerie(ConstantesFacturacion.FACTURA_ABONO_DEFECTO);
					String campoBusqueda = '%' + utilesColegiado.getNumColegiadoSession() + tFecha + '%';					
					int numFacturasBBDD = dao.buscaNumFacturaAbono(campoBusqueda,"ABON");
					int numFacturasN = numFacturasBBDD + 1;
					String valorFactura = String.valueOf(numFacturasN);
					String idFactura = utiles.rellenarCeros(valorFactura, 5);
					numColegiado = utilesColegiado.getNumColegiadoSession();
					numFactura = nFactura.getNumSerie() + numColegiado + tFecha + idFactura;

					campoBusqueda = '%'	+ numColegiado	+ tFecha + numFactura.substring(numFactura.length() - 5, numFactura.length());

					nFactura.getId().setNumFactura(nFactura.getNumSerie() + utilesColegiado.getNumColegiadoSession() + tFecha + idFactura);

					for (int x = 0; x < nFactura.getFacturaConceptos().size(); x++) {
						nFactura.getFacturaConceptos().get(x).setNumColegiado(utilesColegiado.getNumColegiadoSession());
						nFactura.getFacturaConceptos().get(x).setNumFactura(nFactura.getId().getNumFactura());
					}

					nFactura.setNumeracion(new BigDecimal(idFactura));
					nFactura.setFechaAlta(new Date());
					nFactura.setFechaFactura(new Date());
					//					nFactura.setCheckPdf(EstadoFacturacion.EstadoFacturaAbono.getValorEnum());
					nFactura.setCheckPdf(EstadoFacturacion.EstadoNueva.getValorEnum());

					//TODO: Modificar esto para que inserte en el campo de factura sobre la que se saca el abono. 
					//Esta mal expresado porque no es una fac anulada, si no la factura del abono.
					nFactura.setFacAnulada(numFacturaAnulada);

					resultado = dao.saveOrUpdate(nFactura);

					if (datosCliente == null)
						datosCliente = new DatosClienteBean();

					datosCliente.setFactura(nFactura);
					datosCliente.setIsRectificativa(Boolean.FALSE);

					if (resultado.getError()) {
						datosCliente.setIsError(Boolean.FALSE);
						datosCliente.setIsPantallaAlta(Boolean.TRUE);
						for (int j = 0; j < resultado.getListaMensajes().size(); j++) {
							if (resultado.getListaMensajes().get(j) != null) {
								mensaje = resultado.getListaMensajes().get(j);
								mensaje = mensaje.replace("--", " - ");
								addActionError(mensaje);
							}
						}
						return ConstantesFacturacion.RESULT_CONSULTA_FACTURACION;
					} else {

						datosCliente.setIsRectificativa(Boolean.FALSE);

						datosCliente.setIsError(Boolean.TRUE);
						datosCliente.setIsPantallaAlta(Boolean.FALSE);

						setDatosCliente(datosCliente);
						getSession().put("datosCliente", datosCliente);

						for (int j = 0; j < resultado.getListaMensajes().size(); j++) {
							if (resultado.getListaMensajes().get(j) != null) {
								mensaje = resultado.getListaMensajes().get(j);
								mensaje = mensaje.replace("--", " - ");
								addActionMessage(mensaje);
							}
						}
					}
					addActionMessage(ConstantesFacturacion.MENSAJE_ERROR_CREAR_ABONO_UNO + " " + codSeleccionados[i] + " " + ConstantesFacturacion.MENSAJE_ERROR_CREAR_ABONO_DOS);
					addActionMessage(ConstantesFacturacion.MENSAJE_ERROR_CREAR_ABONO_UNO + " " + numFactura);
				} catch (Throwable e) {
					log.error("Error al crear una factura de abono. ", e);
					addActionError(ConstantesFacturacion.PDF_ABONO_MENSAJE_TRES + " " + codSeleccionados[i] + " " + ConstantesFacturacion.PDF_ABONO_MENSAJE_CUATRO);
					return ConstantesFacturacion.RESULT_CONSULTA_FACTURACION;
				}				
			} else {
				addActionError(ConstantesFacturacion.PDF_ABONO_MENSAJE_TRES + " " + codSeleccionados[i] + " " + ConstantesFacturacion.PDF_ABONO_MENSAJE_CUATRO);
				buscar();
				return ConstantesFacturacion.RESULT_CONSULTA_FACTURACION;
			}
		}
		// DRC@14-08-2012 Actualiza los campos Numero Colegiado y Numero
		// Factura, a blanco para que el usuario si lo desee utilice los filtros
		//		beanRect.setNumColegiado("");
		//		beanRect.setNumFactura("");
		buscarRectificativa();
		return ConstantesFacturacion.RESULT_CONSULTA_FACTURACION;
	}

	public String eliminarExpedientes(){
		String numsExpediente =request.getParameter("numsExpediente");
		String[] expedientesSeleccionados = numsExpediente.split("-");
		datosCliente = (DatosClienteBean)getSession().get("datosCliente");
		List<String> listaStringExpedientes = new ArrayList<String>();

		for (int i=0; i<expedientesSeleccionados.length;i++){
			TramiteTraficoBean linea = new TramiteTraficoBean(true);
			BigDecimal numExpediente = new BigDecimal(expedientesSeleccionados[i].trim());
			linea = obtenerBeanConNumExpediente(numExpediente);
			tipoTramite      = linea.getTipoTramite()!=null?linea.getTipoTramite().getValorEnum():null;
			//Quitar el expediente eliminado de la lista de expedientes.
			for (int j=0; j<listaNumsExpediente.size(); j++){
				if (listaNumsExpediente.get(j).getNumExpediente().trim().equals(expedientesSeleccionados[i].trim()))
					listaNumsExpediente.remove(j);
				if (!(listaNumsExpediente.get(j).getNumExpediente().trim().equals(expedientesSeleccionados[i].trim())))
					listaStringExpedientes.add(listaNumsExpediente.get(j).getNumExpediente().trim());
			}

			switch (Integer.parseInt(tipoTramite.substring(1))) {
			//Matriculación
			case T1:{
				totalTramiteMatriculacion --;
				if (totalTramiteMatriculacion == 0){
					for (int x=0;x<datosCliente.getFactura().getFacturaConceptos().size();x++){
						if (datosCliente.getFactura().getFacturaConceptos().get(x).getConcepto().contains("MATRICULACION")){
							datosCliente.getFactura().getFacturaConceptos().remove(x);
						}
					}
				}else if (totalTramiteMatriculacion == 1){
					for (int x=0;x<datosCliente.getFactura().getFacturaConceptos().size();x++){
						if (datosCliente.getFactura().getFacturaConceptos().get(x).getConcepto().contains("MATRICULACION")){
							datosCliente.getFactura().getFacturaConceptos().get(x).setConcepto(totalTramiteMatriculacion + " TRÁMITE DE MATRICULACION");
						}
					}
				}else{
					for (int x=0;x<datosCliente.getFactura().getFacturaConceptos().size();x++){
						if (datosCliente.getFactura().getFacturaConceptos().get(x).getConcepto().contains("MATRICULACION")){
							datosCliente.getFactura().getFacturaConceptos().get(x).setConcepto(totalTramiteMatriculacion + " TRÁMITES DE MATRICULACION");
						}
					}
				}
				break;
			}
			//Transmisión
			case T2:{
				totalTramiteTransmision --;
				if (totalTramiteTransmision == 0){
					for (int x=0;x<datosCliente.getFactura().getFacturaConceptos().size();x++){
						if (datosCliente.getFactura().getFacturaConceptos().get(x).getConcepto().contains("TRANSMISIÓN")){
							datosCliente.getFactura().getFacturaConceptos().remove(x);
						}
					}
				}else if (totalTramiteTransmision == 1){	
					for (int x=0;x<datosCliente.getFactura().getFacturaConceptos().size();x++){
						if (datosCliente.getFactura().getFacturaConceptos().get(x).getConcepto().contains("TRANSMISIÓN")){
							datosCliente.getFactura().getFacturaConceptos().get(x).setConcepto(totalTramiteTransmision + " TRÁMITE DE TRANSMISIÓN");
						}
					}
				}else{
					for (int x=0;x<datosCliente.getFactura().getFacturaConceptos().size();x++){
						if (datosCliente.getFactura().getFacturaConceptos().get(x).getConcepto().contains("TRANSMISIÓN")){
							datosCliente.getFactura().getFacturaConceptos().get(x).setConcepto(totalTramiteTransmision + " TRÁMITES DE TRANSMISIÓN");
						}
					}
				}
				break;
			}
			//Transmisión Electrónica
			case T7:{
				totalTramiteTransmisionTelematica --;
				if(totalTramiteTransmisionTelematica == 0){
					for (int x=0;x<datosCliente.getFactura().getFacturaConceptos().size();x++){
						if (datosCliente.getFactura().getFacturaConceptos().get(x).getConcepto().contains("TRANSMISIÓN TELEMÁTICA")){
							datosCliente.getFactura().getFacturaConceptos().remove(x);
						}
					}
				}else if (totalTramiteTransmisionTelematica == 1){
					for (int x=0;x<datosCliente.getFactura().getFacturaConceptos().size();x++){
						if (datosCliente.getFactura().getFacturaConceptos().get(x).getConcepto().contains("TRANSMISIÓN TELEMÁTICA")){
							datosCliente.getFactura().getFacturaConceptos().get(x).setConcepto(totalTramiteTransmisionTelematica + " TRÁMITE DE TRANSMISIÓN TELEMÁTICA");
						}
					}
				}else{
					for (int x=0;x<datosCliente.getFactura().getFacturaConceptos().size();x++){
						if (datosCliente.getFactura().getFacturaConceptos().get(x).getConcepto().contains("TRANSMISIÓN TELEMÁTICA")){
							datosCliente.getFactura().getFacturaConceptos().get(x).setConcepto(totalTramiteTransmisionTelematica + " TRÁMITES DE TRANSMISIÓN TELEMÁTICA");
						}
					}
				}
				break;
			}
			//Bajas
			case T3:{
				totalTramiteBaja --;
				if (totalTramiteBaja == 0){
					for (int x=0;x<datosCliente.getFactura().getFacturaConceptos().size();x++){
						if (datosCliente.getFactura().getFacturaConceptos().get(x).getConcepto().contains("BAJA")){
							datosCliente.getFactura().getFacturaConceptos().remove(x);
						}
					}
				}else if(totalTramiteBaja == 1){
					for (int x=0;x<datosCliente.getFactura().getFacturaConceptos().size();x++){
						if (datosCliente.getFactura().getFacturaConceptos().get(x).getConcepto().contains("BAJA")){
							datosCliente.getFactura().getFacturaConceptos().get(x).setConcepto(totalTramiteBaja + " TRÁMITE DE BAJA");
						}
					}
				}else{
					for (int x=0;x<datosCliente.getFactura().getFacturaConceptos().size();x++){
						if (datosCliente.getFactura().getFacturaConceptos().get(x).getConcepto().contains("BAJA")){
							datosCliente.getFactura().getFacturaConceptos().get(x).setConcepto(totalTramiteBaja + " TRÁMITES DE BAJA");
						}
					}
				}
				break;
			}
			// Duplicados
			case T8:{
				totalTramiteDuplicados --;
				if (totalTramiteDuplicados == 0){
					for (int x=0;x<datosCliente.getFactura().getFacturaConceptos().size();x++){
						if (datosCliente.getFactura().getFacturaConceptos().get(x).getConcepto().contains("DUPLICADOS")){
							datosCliente.getFactura().getFacturaConceptos().remove(x);
						}
					}
				}else if(totalTramiteDuplicados == 1){	
					for (int x=0;x<datosCliente.getFactura().getFacturaConceptos().size();x++){
						if (datosCliente.getFactura().getFacturaConceptos().get(x).getConcepto().contains("DUPLICADOS")){
							datosCliente.getFactura().getFacturaConceptos().get(x).setConcepto(totalTramiteDuplicados + " TRÁMITE DUPLICADOS");
						}
					}
				}else{
					for (int x=0;x<datosCliente.getFactura().getFacturaConceptos().size();x++){
						if (datosCliente.getFactura().getFacturaConceptos().get(x).getConcepto().contains("DUPLICADOS")){
							datosCliente.getFactura().getFacturaConceptos().get(x).setConcepto(totalTramiteDuplicados + " TRÁMITES DUPLICADOS");
						}
					}
				}
				break;
			}
			//Solicitudes
			case T4:{
				totalTramiteSolicitudes --;
				if (totalTramiteSolicitudes == 0){
					for (int x=0;x<datosCliente.getFactura().getFacturaConceptos().size();x++){
						if (datosCliente.getFactura().getFacturaConceptos().get(x).getConcepto().contains("SOLICITADOS")){
							datosCliente.getFactura().getFacturaConceptos().remove(x);
						}
					}
				}else if (totalTramiteSolicitudes == 1){
					for (int x=0;x<datosCliente.getFactura().getFacturaConceptos().size();x++){
						if (datosCliente.getFactura().getFacturaConceptos().get(x).getConcepto().contains("SOLICITADOS")){
							datosCliente.getFactura().getFacturaConceptos().get(x).setConcepto(totalTramiteSolicitudes + " TRÁMITE SOLICITADOS");
						}
					}
				}else{
					for (int x=0;x<datosCliente.getFactura().getFacturaConceptos().size();x++){
						if (datosCliente.getFactura().getFacturaConceptos().get(x).getConcepto().contains("SOLICITADOS")){
							datosCliente.getFactura().getFacturaConceptos().get(x).setConcepto(totalTramiteSolicitudes + " TRÁMITES SOLICITADOS");
						}
					}
				}
				break;
			}
			}
		}

		datosCliente.setListaExpedientes(listaStringExpedientes);
		getSession().put("datosCliente", datosCliente);
		vieneDeEliminarExpediente = true;
		return mostrarExpedientes();
	}

	/**
	 * Método que actualiza los parámetros de búsqueda a partir de un bean de búsqueda que le pasamos
	 */
	private void actualizaParametros(ConsultaTramiteTraficoFacturacionBean beanActualiza){

		parametrosBusqueda = new HashMap<String, Object>();
		if(null!=beanActualiza){
			parametrosBusqueda.put(ConstantesSession.NIF_TITULAR_CONSULTA, beanActualiza.getTitular().getNif());

			Integer estado = beanActualiza.getEstado();
			if (estado != null && estado.equals(Integer.valueOf(String.valueOf("-1")))){
				estado=null;
			}

			String tipoTramite = beanActualiza.getTipoTramite();
			if (tipoTramite != null && tipoTramite.equals("-1")){
				tipoTramite=null;
			}
			parametrosBusqueda.put(ConstantesSession.ESTADO_TRAMITE_CONSULTA, estado);
			parametrosBusqueda.put(ConstantesSession.REFERENCIA_PROPIA_CONSULTA, beanActualiza.getReferenciaPropia());
			parametrosBusqueda.put(ConstantesSession.NUM_EXPEDIENTE_CONSULTA, beanActualiza.getNumExpediente());
			parametrosBusqueda.put(ConstantesSession.TIPO_TRAMITE_CONSULTA, tipoTramite);
			parametrosBusqueda.put(ConstantesSession.NUM_COLEGIADO_CONCULTA, beanActualiza.getNumColegiado());
			parametrosBusqueda.put(ConstantesSession.MATRICULA_CONSULTA, beanActualiza.getMatricula());
			parametrosBusqueda.put(ConstantesSession.NUM_BASTIDOR_CONSULTA, beanActualiza.getNumBastidor());
			parametrosBusqueda.put(ConstantesSession.NIVE_CONSULTA, beanActualiza.getNive());
			parametrosBusqueda.put(ConstantesSession.FECHA_ALTA_TRAMITE_CONSULTA, beanActualiza.getFechaAlta());
			parametrosBusqueda.put(ConstantesSession.FECHA_PRESENTACION_TRAMITE_CONSULTA, beanActualiza.getFechaPresentacion());
			parametrosBusqueda.put(ConstantesSession.NIF_FACTURACION_CONSULTA , beanActualiza.getNifFacturacion());
		}
	}

	public String nuevaFactura() throws Throwable{
		log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA+" nuevaFactura.");
		ArrayList<String> listaExpedientes = new ArrayList<String>();
		String tipoPersonaFacturacion = "Titular";
		String tipoTramiteNombre = "";
		String nifAnterior=null;
		String nif=null;
		String numColegiado = utilesColegiado.getNumColegiadoSession();
		//	if (request.getParameter("numsExpediente") != null) {
		//		numExpediente      = request.getParameter("numsExpediente");			
		//		//tipoTramite = linea.getTipoTramite()!=null?linea.getTipoTramite().getValorEnum():null;
		//		//String valor[] = request.getParameter("numsExpediente").split("-");
		//		//numExpediente      = valor[0].trim();
		//		//String tipoTramite = valor[1].trim();
		//		tipoPersonaFacturacion = request.getParameter("combo");
		//	}
		/*Aquí recuperamos los expedientes*/
		String expedientes = getNumsExpediente();			
		String valor[] = expedientes.split("-");
		tipoPersonaFacturacion = request.getParameter("combo");
		try {		
			mantenimientoCamposNavegar();
			//Incorporamos la obtención de acciones por trámite
			parametrosBusqueda = new HashMap<String, Object>();
			//getSession().put(ConstantesSession.NUM_EXPEDIENTE, numExpediente);

			// Recupera si tiene titular facturación los detalles:
			factura = new Factura();
			DatosDAO dao = new DatosDAO();				

			Fecha fechaFrac = utilesFecha.getFechaActual();
			String fecha = fechaFrac.getMes() + fechaFrac.getAnio().substring(2);
			//String tFecha = "% " + fechaFrac.getMes().toString() +"/"+ fechaFrac.getAnio().substring(2).toString();
			//tFecha.trim();
			String numSerie = ConstantesFacturacion.FACTURA_SERIE_DEFECTO;

			if(datosCliente==null) datosCliente = new DatosClienteBean();
			datosCliente.setFactura(factura);
			datosCliente.getFactura().setNumSerie(numSerie);

			//int numFacturasBBDD = dao.buscaMaxFactura(numSerie,numColegiado);					
			int numFacturasBBDD = dao.buscaMaxFactura(numColegiado);
			int numFacturasN = numFacturasBBDD +1;				

			String valorFactura = String.valueOf(numFacturasN);
			String idFactura = utiles.rellenarCeros(valorFactura,5);
			String numFactura =  numSerie + numColegiado + fecha + idFactura;				
			long idConcepto = 0;
			for(int i=0; i<valor.length; i++){
				listaExpedientes.add(valor[i]);
				//Consultamos el nif del cliente. Si son diferentes clientes, no se puede hacer la factura
				if(nif!=null && nifAnterior!=null){
					if(!nif.equalsIgnoreCase(nifAnterior)){
						factura=null;
						addActionError("No se puede hacer factura para diferentes NIF/CIF");
						return "nuevaFactura";
					}
				}

				String numColegiadoExpediente = valor[i].substring(0, 4);
				BigDecimal numExp = new BigDecimal(valor[i]);
				//Almacenamos los expedientes de la factura
				datosCliente.getFactura().setNumExpediente(valor[i]);						

				if(datosCliente.getFactura().getId() == null){
					FacturaPK facturapk = new FacturaPK();
					facturapk.setNumColegiado(numColegiadoExpediente);						
					facturapk.setNumFactura(numFactura);
					datosCliente.getFactura().setId(facturapk);
					//factura.getId().setNumColegiado(numColegiadoExpediente);
				}
				Map<String, Object> resultadoMetodo = new HashMap<String, Object>();
				TramiteTraficoBean linea = new TramiteTraficoBean(true);
				linea = obtenerBeanConNumExpediente(numExp);

				if(linea == null){
					addActionError("No se ha encontrado el número de expediente");
					return "nuevaFactura";
				}
				tipoTramite      = linea.getTipoTramite()!=null?linea.getTipoTramite().getValorEnum():null;
				String bastidor  = linea.getVehiculo().getBastidor()!=null?linea.getVehiculo().getBastidor():null;
				String matricula = linea.getVehiculo().getMatricula()!=null?linea.getVehiculo().getMatricula():null;
				String codigoTasa = linea.getTasa().getCodigoTasa()!=null?linea.getTasa().getCodigoTasa():null;
				if (codigoTasa != null && !codigoTasa.equalsIgnoreCase("")) {
					BeanPQTasasDetalle beanDetalle = new BeanPQTasasDetalle();
					beanDetalle.setP_CODIGO_TASA(codigoTasa);
					try {
						resultadoCodigoTasa = getModeloTasas().detalleTasa(beanDetalle);
						//precioTasa = resultadoCodigoTasa.getP_PRECIO().toString();
					} catch (Exception e){
						log.error("Excepcion al recoger el precio del Codigo de la Tasa: " + resultadoCodigoTasa);
					}					
				}


				switch (Integer.parseInt(tipoTramite.substring(1))) {
				//Matriculación
				case T1:{
					traficoTramiteMatriculacionBean = new TramiteTraficoMatriculacionBean();
					resultadoMetodo =  getModeloMatriculacion().obtenerDetalle(linea.getNumExpediente(),utilesColegiado.getNumColegiadoSession(),utilesColegiado.getIdContratoSessionBigDecimal());
					traficoTramiteMatriculacionBean = (TramiteTraficoMatriculacionBean) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);	
					if (ConstantesFacturacion.TITULAR.equals(tipoPersonaFacturacion.toUpperCase())){
						//							nif = traficoTramiteMatriculacionBean.getTitularBean().getPersona().getNif();
						//							if (nif == null) { addActionError("El Tramite Seleccionado no tiene asignado un TITULAR"); return "consultaTramiteTraficoFacturacion";}
						//factura.setPersona(traficoTramiteMatriculacionBean.getTitularBean().getPersona());
					}else if (ConstantesFacturacion.COMPRADOR.equals(tipoPersonaFacturacion.toUpperCase())){
						nif = traficoTramiteMatriculacionBean.getTitularBean().getPersona().getNif();
						if (nif == null) { addActionError("El Tramite Seleccionado no tiene asignado un TITULAR"); 
						return "nuevaFactura";}
						//addActionError("Un trámite de Matriculacion no tiene asignado Adquiriente");
						//return "consultaTramiteTraficoFacturacion";
					}else if (ConstantesFacturacion.TRANSMITENTE.equals(tipoPersonaFacturacion.toUpperCase())){
						addActionError("Un trámite de Matriculacion no tiene asignado Transmitente");
						return "nuevaFactura";
					}else if (ConstantesFacturacion.COMPRAVENTA.equals(tipoPersonaFacturacion.toUpperCase())){
						addActionError("Un Tramite de matriculacion no puede asociarse a COMPRAVENTA"); 
						return "nuevaFactura";
					}else {
						datosCliente.getFactura().setPersona(null);
					}
					break;
				}	
				//Transmisión
				case T2:{
					tramiteTraficoTransmisionBean = new TramiteTraficoTransmisionBean();
					resultadoMetodo =  getModeloTransmision().obtenerDetalle(linea.getNumExpediente());
					tramiteTraficoTransmisionBean =(TramiteTraficoTransmisionBean) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
					if (ConstantesFacturacion.TITULAR.equals(tipoPersonaFacturacion.toUpperCase())){
						addActionError("Un trámite de Transmision no tiene asignado Titular");
						return "nuevaFactura";
					}else if (ConstantesFacturacion.COMPRADOR.equals(tipoPersonaFacturacion.toUpperCase())){
						nif = tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona().getNif();
						if (nif == null) { addActionError("El Tramite Seleccionado no tiene asignado un COMPRADOR"); 
						return "nuevaFactura";}
						//factura.setPersonaFac(tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona());
					}else if (ConstantesFacturacion.TRANSMITENTE.equals(tipoPersonaFacturacion.toUpperCase())){
						nif = tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona().getNif();
						if (nif == null) { addActionError("El Tramite Seleccionado no tiene asignado un TRANSMITENTE"); 
						return "nuevaFactura";}
						//factura.setPersonaFac(tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona());
					}else if (ConstantesFacturacion.COMPRAVENTA.equals(tipoPersonaFacturacion.toUpperCase())){
						//El poseedor es el CompraVenta.
						nif = tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getNif();
						if (nif == null) { addActionError("El Tramite Seleccionado no tiene asignado un COMPRAVENTA"); 
						return "nuevaFactura";}
					}else {
						//factura.setPersonaFac(null);
					}
					break;
				}
				//Transmisión Electrónica
				case T7:{
					tramiteTraficoTransmisionBean = new TramiteTraficoTransmisionBean();
					resultadoMetodo =  getModeloTransmision().obtenerDetalle(linea.getNumExpediente());
					tramiteTraficoTransmisionBean =(TramiteTraficoTransmisionBean) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
					if (ConstantesFacturacion.TITULAR.equals(tipoPersonaFacturacion.toUpperCase())){
						addActionError("Un trámite de Transmision Electronica no tiene asignado Titular");
						return "nuevaFactura";
					}else if (ConstantesFacturacion.COMPRADOR.equals(tipoPersonaFacturacion.toUpperCase())){
						nif = tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona().getNif();
						if (nif == null) { addActionError("El Tramite Seleccionado no tiene asignado un COMPRADOR"); 
						return "nuevaFactura";}
						//factura.setPersonaFac(tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona());
					}else if (ConstantesFacturacion.TRANSMITENTE.equals(tipoPersonaFacturacion.toUpperCase())){
						nif = tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona().getNif();
						if (nif == null) { addActionError("El Tramite Seleccionado no tiene  asignado un TRANSMITENTE"); 
						return "nuevaFactura";}
						//factura.setPersonaFac(tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona());
					}else if (ConstantesFacturacion.COMPRAVENTA.equals(tipoPersonaFacturacion.toUpperCase())){
						//El poseedor es el CompraVenta.
						nif = tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getNif();
						if (nif == null) { addActionError("El Tramite Seleccionado no tiene asignado un COMPRAVENTA"); 
						return "nuevaFactura";}
					}else {
						//factura.setPersonaFac(null);
					}
					break;
				}
				//Bajas
				case T3:{
					tramiteTraficoBaja = new TramiteTrafBajaDto();
					tramiteTraficoBaja = servicioTramiteTraficoBaja.getTramiteBaja(linea.getNumExpediente(), true);
					if (ConstantesFacturacion.TITULAR.equals(tipoPersonaFacturacion.toUpperCase())){
						nif = tramiteTraficoBaja.getTitular().getPersona().getNif();
						if (nif == null){ addActionError("El Tramite Seleccionado no tiene asignado un TITULAR");return "nuevaFactura";}
						//factura.setPersonaFac(tramiteTraficoBaja.getTitular().getPersona());
					}else if (ConstantesFacturacion.COMPRADOR.equals(tipoPersonaFacturacion.toUpperCase())){
						nif = tramiteTraficoBaja.getAdquiriente().getPersona().getNif();
						if (nif == null) { addActionError("El Tramite Seleccionado no tiene asignado un COMPRADOR"); return "nuevaFactura";}
						//factura.setPersonaFac(tramiteTraficoBaja.getAdquiriente().getPersona());
					}else if (ConstantesFacturacion.TRANSMITENTE.equals(tipoPersonaFacturacion.toUpperCase())){
						addActionError("Un trámite de Baja no tiene asignado Transmitente");
						return "consultaTramiteTraficoFacturacion";
					}else if (ConstantesFacturacion.COMPRAVENTA.equals(tipoPersonaFacturacion.toUpperCase())){
						addActionError("Un Tramite de baja no puede asociarse a COMPRAVENTA"); return "nuevaFactura";
					}else {
						//factura.setPersonaFac(null);
					}
					break;
				}
				// Duplicados
				case T8:{
					tramiteTraficoDuplicado = new TramiteTraficoDuplicadoBean(true);
					resultadoMetodo = getModeloDuplicado().obtenerDetalle(linea.getNumExpediente(),utilesColegiado.getNumColegiadoSession(),utilesColegiado.getIdContratoSessionBigDecimal());
					tramiteTraficoDuplicado =(TramiteTraficoDuplicadoBean) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
					if (ConstantesFacturacion.TITULAR.equals(tipoPersonaFacturacion.toUpperCase())){
						nif = tramiteTraficoDuplicado.getTitular().getPersona().getNif();
						if (nif == null){ addActionError("El Tramite Seleccionado no tiene asignado un TITULAR"); return "nuevaFactura";}
						//factura.setPersonaFac(tramiteTraficoDuplicado.getTitular().getPersona());
					}else if (ConstantesFacturacion.COMPRADOR.equals(tipoPersonaFacturacion.toUpperCase())){
						addActionError("Un trámite de Duplicado no tiene asignado Adquiriente");
						return "nuevaFactura";
					}else if (ConstantesFacturacion.TRANSMITENTE.equals(tipoPersonaFacturacion.toUpperCase())){
						addActionError("Un trámite de Duplicado no tiene asignado Transmitente");
						return "nuevaFactura";
					}else if (ConstantesFacturacion.COMPRAVENTA.equals(tipoPersonaFacturacion.toUpperCase())){
						addActionError("Un Tramite Duplicado no puede asociarse a COMPRAVENTA"); 
						return "nuevaFactura";
					}else {
						//	factura.setPersonaFac(null);
					}
					break;
				}
				//Solicitudes
				case T4:{
					if (ConstantesFacturacion.TITULAR.equals(tipoPersonaFacturacion.toUpperCase())){
						addActionError("Un trámite de Solicitud no tiene asignado Titular");
						return "nuevaFactura";
					}else if (ConstantesFacturacion.COMPRADOR.equals(tipoPersonaFacturacion.toUpperCase())){
						addActionError("Un trámite de Solicitud no tiene asignado Adquiriente");
						return "nuevaFactura";
					}else if (ConstantesFacturacion.TRANSMITENTE.equals(tipoPersonaFacturacion.toUpperCase())){
						addActionError("Un trámite de Solicitud no tiene asignado Transmitente");
						return "nuevaFactura";
					}else if (ConstantesFacturacion.COMPRAVENTA.equals(tipoPersonaFacturacion.toUpperCase())){
						addActionError("Un Tramite de Solicitud no puede asociarse a COMPRAVENTA"); 
						return "nuevaFactura";
					}else {
						//factura.setPersonaFac(null);
					}
					break;
				}
				//No tiene tipo, se define el error y se redirecciona a la pagina de consulta
				default:{
					addActionError("Este trámite no tiene asignado ningún tipo");
					log.debug("Este trámite no tiene asignado ningún tipo");
					if(null != listaConsultaTramiteTraficoFacturacion) {
						listaConsultaTramiteTraficoFacturacion.establecerParametros(getSort(), getDir(), getPage(), getResultadosPorPagina());
						listaConsultaTramiteTraficoFacturacion.getBaseDAO().setParametrosBusqueda(parametrosBusqueda);
					}
					return "nuevaFactura";
				}  	
				}					

				if (datosCliente.getFactura().getPersona() == null){
					hibernate.entities.personas.Persona pers = dao.buscarPersona(nif,numColegiado);												
					if (pers != null){
						hibernate.entities.personas.Direccion dir = dao.buscarDireccionActual(nif,numColegiado);
						datosCliente.getFactura().setPersona(pers);
						datosCliente.getFactura().getPersona().setDireccionActual(dir);							

						if (pers.getFechaNacimiento() != null){
							datosCliente.getFactura().getPersona().setFnacimiento(utilesFecha.getFechaConDate(pers.getFechaNacimiento()));
						}else{
							datosCliente.getFactura().getPersona().setFnacimiento(null);
						}
					}else if ((ConstantesFacturacion.TITULAR.equals(tipoPersonaFacturacion.toUpperCase()))
							|| (ConstantesFacturacion.COMPRADOR.equals(tipoPersonaFacturacion.toUpperCase()))
							|| (ConstantesFacturacion.TRANSMITENTE.equals(tipoPersonaFacturacion.toUpperCase()))){
						addActionError("No existe una persona con DNI "+ nif + " para el Colegiado " + numColegiado );
						return "nuevaFactura";
					}
				} 
				//				else{
				//					datosCliente.getFactura().getPersona().getId().setNif(nif);
				//				}

				tipoTramiteNombre = TipoTramiteTrafico.convertirTexto(tipoTramite);


				//String concepto = valor[i] + " " + tipoTramiteNombre;
				String concepto = tipoTramiteNombre;
				if (bastidor != null)
					concepto += " " + bastidor;
				if (matricula != null)
					concepto += " " + matricula;


				FacturaConcepto facturaConcepto = new FacturaConcepto();
				facturaConcepto.setIdConcepto(idConcepto);

				//Creamos un Honorario para que se muestre por pantalla
				List<FacturaHonorario> factHonorario = new ArrayList<FacturaHonorario>();
				FacturaHonorario fHonorario = new FacturaHonorario();					
				fHonorario.setIdHonorario(0);
				//				fHonorario.setHonorario("");
				fHonorario.setHonorarioCheckDescuento("0");
				fHonorario.setHonorarioCheckIva(new BigDecimal("1"));
				fHonorario.setHonorarioCheckIrpf(new BigDecimal("0"));					
				fHonorario.setHonorario(0F);
				fHonorario.setHonorarioDescripcion("Introduzca la descripcion");
				fHonorario.setHonorarioDescuento(0F);
				fHonorario.setHonorarioIva(ConstantesFacturacion.IVA_DEFECTO);
				fHonorario.setHonorarioIrpf(ConstantesFacturacion.IRPF_DEFECTO);
				fHonorario.setHonorarioTotalIrpf(0F);
				fHonorario.setHonorarioTotalIva(0F);		
				fHonorario.setHonorarioTotal(0F);
				fHonorario.setFacturaConcepto(facturaConcepto);
				factHonorario.add(fHonorario);
				facturaConcepto.setFacturaHonorarios(factHonorario);

				//Creamos un Suplido para que se muestre por pantalla.
				List<FacturaSuplido> factSuplidoList = new ArrayList<FacturaSuplido>();
				FacturaSuplido facturaSuplido = new FacturaSuplido();
				facturaSuplido.setIdSuplido(0);
				facturaSuplido.setSuplidoCheckDescuento("0");
				facturaSuplido.setSuplido(0F);
				facturaSuplido.setSuplidoDescripcion("Introduzca la descripcion");
				facturaSuplido.setSuplidoDescuento(0F);	
				facturaSuplido.setSuplidoTotal(0F);
				facturaSuplido.setFacturaConcepto(facturaConcepto);
				factSuplidoList.add(facturaSuplido);
				facturaConcepto.setFacturaSuplidos(factSuplidoList);

				//Creamos un Gasto para que se muestre por pantalla.
				List<FacturaGasto> factGastosList = new ArrayList<FacturaGasto>();
				FacturaGasto facturagasto = new FacturaGasto();
				facturagasto.setIdGasto(0);
				facturagasto.setGastoDescripcion("Introduzca la descripcion");
				facturagasto.setGasto(0F);
				facturagasto.setGastoCheck("1");
				facturagasto.setGastoIva(ConstantesFacturacion.IVA_DEFECTO);
				facturagasto.setGastoTotal(0F);
				facturagasto.setFacturaConcepto(facturaConcepto);
				factGastosList.add(facturagasto);
				facturaConcepto.setFacturaGastos(factGastosList);
				facturaConcepto.setFactura(datosCliente.getFactura());

				facturaConcepto.setConcepto(concepto);
				facturaConcepto.setNumExpediente(String.valueOf(numExp));

				if (datosCliente.getFactura().getFacturaConceptos() == null){

					//Si no existe ningun concepto pongo el primero como el que se muestra automaticamente
					datosCliente.setFacturaConceptoCargado(facturaConcepto);

					//Creo la lista de todos los conceptos
					List<FacturaConcepto> listaFacturaConcepto = new ArrayList<FacturaConcepto>();
					listaFacturaConcepto.add(facturaConcepto);

					datosCliente.getFactura().setFacturaConceptos(listaFacturaConcepto);					
				} else{
					datosCliente.getFactura().getFacturaConceptos().add(facturaConcepto);
				}


				//datosCliente.setFactura(factura);
				datosCliente.getFactura().getId().setNumColegiado(utilesColegiado.getNumColegiadoSession());
				datosCliente.getFactura().setNumCodigo(utilesColegiado.getNumColegiadoSession());
				datosCliente.getFactura().setNumExpediente(datosCliente.getFactura().getNumExpediente());
				datosCliente.setIsError(Boolean.FALSE);
				datosCliente.setIsPantallaAlta(Boolean.TRUE);	
				//datosCliente.setHonorariosTotalIva("0");
				//datosCliente.setHonorariosTotalIRPF("0");
				//datosCliente.setOtrosTotalIva("0");
				parametrosBusqueda.put(ConstantesSession.NUM_EXPEDIENTE, new BigDecimal (valor[i]));
			} //Fin de for factura

			//List<FacturaOtro> factOtro = new ArrayList<FacturaOtro>();
			//Cambiado para que reciba un objeto en lugar de una lista porque se cambia la relacion a ontToOne. 
			//			datosCliente.getFactura().setFacturaOtro(factOtro);
			datosCliente.getFactura().setFacturaOtro(new FacturaOtro());
			datosCliente.getFactura().getFacturaOtro().setOtroCheckIvaHidden("1");
			datosCliente.getFactura().getFacturaOtro().setOtroCheckDescuentoHidden("0");
			//			datosCliente.getFactura().setFacturaOtro(factOtro.get(0));
			//List<FacturaProvFondo> factProvFondos = new ArrayList<FacturaProvFondo>();
			//			datosCliente.getFactura().setFacturaProvFondo(factProvFondos);
			datosCliente.getFactura().setFacturaProvFondo(new FacturaProvFondo());

			datosCliente.setNumDecimales(gestorPropiedades.valorPropertie("facturacion.num.decimales"));
			datosCliente.setMensajeBorrador(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
			datosCliente.setMensajePDF(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
			datosCliente.setMensajeImprimir(ConstantesFacturacion.ALERT_MENSAJE_IMPRIMIR);
			datosCliente.setMensajeClave(ConstantesFacturacion.ALERT_MENSAJE_MODFICAR);
			datosCliente.setIsRectificativa(Boolean.FALSE);
			datosCliente.getFactura().setCheckPdf(EstadoFacturacion.EstadoNueva.getValorEnum());
			datosCliente.getFactura().setFechaFactura(new Date());
			datosCliente.getFactura().setFechaAlta(new Date());
			//datosCliente.getFactura().setFondosId("0");
			//datosCliente.getFactura().getFacturaOtro().setIdOtro("0");

			// DRC@24-08-2012 Valida si el numero de factura, generado no existe en la base de datos
			// y si existe, esta buscando el siguiente numero libre 		
			//			String validaNumFactura = dao.checkNumFactura(campoBusqueda);					
			//			if (validaNumFactura == null) {
			//				do { 
			//					numFacturasN++;
			//					valorFactura = String.valueOf(numFacturasN);
			//					idFactura = utiles.rellenarCeros(valorFactura,5);
			//					numFactura =  datosCliente.getNumSerie() + numColegiado + tFecha + idFactura;
			//					campoBusqueda =  '%' + numColegiado + tFecha + numFactura.substring(numFactura.length()-5, numFactura.length());
			//					validaNumFactura = dao.checkNumFactura(campoBusqueda);						
			//				} while (validaNumFactura == null);
			//			}

			////////////////////////////////////////////////////////////////				
			datosCliente.getFactura().getId().setNumFactura(numFactura);
			datosCliente.getFactura().setNumeracion(new BigDecimal(idFactura));
			datosCliente.getFactura().setNifEmisor(utilesColegiado.getNifUsuario());
			datosCliente.getFactura().setImporteTotal(0f);
			datosCliente.setListaExpedientes(listaExpedientes);

			String numClave = dao.getClaveColegiado(utilesColegiado.getNumColegiadoSession());
			if (numClave!=null)
				datosCliente.setNumClave(numClave);
			else
				datosCliente.setNumClave(ConstantesFacturacion.CLAVE_DEFECTO);
			////////////////////////////////////////////////////////////////				
			getSession().put("datosCliente", datosCliente);
			getSession().put("idConcepto", "0");

			parametrosBusqueda.put(ConstantesSession.ID_USUARIO, utilesColegiado.getIdUsuarioSessionBigDecimal());


			//			tramiteTraficoBean = new TramiteTraficoBean(true);
			//			tramiteTraficoBean.setNumExpediente(new BigDecimal (numExpediente));

			log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA+"detalle.");

			return ConstantesFacturacion.RESULT_ALTA_FACTURACION;
		} catch(Throwable ex) {
			addActionError(ex.toString());
			log.error(ex);
			return Action.ERROR;
		}		
	}

	public String nuevaFacturaSinExpediente() throws Throwable{
		log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA+" nuevaFactura.");
		ArrayList<String> listaExpedientes = new ArrayList<String>();
		String nif=null;
		String numColegiado = utilesColegiado.getNumColegiadoSession();
		//	if (request.getParameter("numsExpediente") != null) {
		//		numExpediente      = request.getParameter("numsExpediente");			
		//		//tipoTramite = linea.getTipoTramite()!=null?linea.getTipoTramite().getValorEnum():null;
		//		//String valor[] = request.getParameter("numsExpediente").split("-");
		//		//numExpediente      = valor[0].trim();
		//		//String tipoTramite = valor[1].trim();
		//		tipoPersonaFacturacion = request.getParameter("combo");
		//	}
		/*Aquí recuperamos los expedientes*/
		try {		
			mantenimientoCamposNavegar();
			//Incorporamos la obtención de acciones por trámite
			parametrosBusqueda = new HashMap<String, Object>();
			//getSession().put(ConstantesSession.NUM_EXPEDIENTE, numExpediente);

			// Recupera si tiene titular facturación los detalles:
			factura = new Factura();
			DatosDAO dao = new DatosDAO();				

			Fecha fechaFrac = utilesFecha.getFechaActual();
			String fecha = fechaFrac.getMes() + fechaFrac.getAnio().substring(2);
			//String tFecha = "% " + fechaFrac.getMes().toString() +"/"+ fechaFrac.getAnio().substring(2).toString();
			//tFecha.trim();
			String numSerie = ConstantesFacturacion.FACTURA_SERIE_DEFECTO;

			if(datosCliente==null) datosCliente = new DatosClienteBean();
			datosCliente.setFactura(factura);
			datosCliente.getFactura().setNumSerie(numSerie);

			//int numFacturasBBDD = dao.buscaMaxFactura(numSerie,numColegiado);					
			int numFacturasBBDD = dao.buscaMaxFactura(numColegiado);
			int numFacturasN = numFacturasBBDD +1;				

			String valorFactura = String.valueOf(numFacturasN);
			String idFactura = utiles.rellenarCeros(valorFactura,5);
			String numFactura =  numSerie + numColegiado + fecha + idFactura;
			long idConcepto = 0;

			if(datosCliente.getFactura().getId() == null){
				FacturaPK facturapk = new FacturaPK();
				facturapk.setNumColegiado(utilesColegiado.getNumColegiadoSession());						
				facturapk.setNumFactura(numFactura);
				datosCliente.getFactura().setId(facturapk);
				//factura.getId().setNumColegiado(numColegiadoExpediente);
			}

			if (datosCliente.getFactura().getPersona() == null){
				hibernate.entities.personas.Persona pers = dao.buscarPersona(nif,numColegiado);												
				if (pers != null){
					hibernate.entities.personas.Direccion dir = dao.buscarDireccionActual(nif,numColegiado);
					datosCliente.getFactura().setPersona(pers);
					datosCliente.getFactura().getPersona().setDireccionActual(dir);							

					if (pers.getFechaNacimiento() != null){
						datosCliente.getFactura().getPersona().setFnacimiento(utilesFecha.getFechaConDate(pers.getFechaNacimiento()));
					}else{
						datosCliente.getFactura().getPersona().setFnacimiento(null);
					}
				}
			} 
			//				else{
			//					datosCliente.getFactura().getPersona().getId().setNif(nif);
			//				}

			FacturaConcepto facturaConcepto = new FacturaConcepto();
			facturaConcepto.setIdConcepto(idConcepto);

			//Creamos un Honorario para que se muestre por pantalla
			List<FacturaHonorario> factHonorario = new ArrayList<FacturaHonorario>();
			FacturaHonorario fHonorario = new FacturaHonorario();					
			fHonorario.setIdHonorario(0);
			//				fHonorario.setHonorario("");
			fHonorario.setHonorarioCheckDescuento("0");
			fHonorario.setHonorarioCheckIva(new BigDecimal("1"));
			fHonorario.setHonorarioCheckIrpf(new BigDecimal("0"));					
			fHonorario.setHonorario(0F);
			fHonorario.setHonorarioDescripcion("Introduzca la descripcion");
			fHonorario.setHonorarioDescuento(0F);
			fHonorario.setHonorarioIva(ConstantesFacturacion.IVA_DEFECTO);
			fHonorario.setHonorarioIrpf(ConstantesFacturacion.IRPF_DEFECTO);
			fHonorario.setHonorarioTotalIrpf(0F);
			fHonorario.setHonorarioTotalIva(0F);		
			fHonorario.setHonorarioTotal(0F);
			fHonorario.setFacturaConcepto(facturaConcepto);
			factHonorario.add(fHonorario);
			facturaConcepto.setFacturaHonorarios(factHonorario);

			//Creamos un Suplido para que se muestre por pantalla.
			List<FacturaSuplido> factSuplidoList = new ArrayList<FacturaSuplido>();
			FacturaSuplido facturaSuplido = new FacturaSuplido();
			facturaSuplido.setIdSuplido(0);
			facturaSuplido.setSuplidoCheckDescuento("0");
			facturaSuplido.setSuplido(0F);
			facturaSuplido.setSuplidoDescripcion("Introduzca la descripcion");
			facturaSuplido.setSuplidoDescuento(0F);	
			facturaSuplido.setSuplidoTotal(0F);
			facturaSuplido.setFacturaConcepto(facturaConcepto);
			factSuplidoList.add(facturaSuplido);
			facturaConcepto.setFacturaSuplidos(factSuplidoList);

			//Creamos un Gasto para que se muestre por pantalla.
			List<FacturaGasto> factGastosList = new ArrayList<FacturaGasto>();
			FacturaGasto facturagasto = new FacturaGasto();
			facturagasto.setIdGasto(0);
			facturagasto.setGastoDescripcion("Introduzca la descripcion");
			facturagasto.setGasto(0F);
			facturagasto.setGastoCheck("1");
			facturagasto.setGastoIva(ConstantesFacturacion.IVA_DEFECTO);
			facturagasto.setGastoTotal(0F);
			facturagasto.setFacturaConcepto(facturaConcepto);
			factGastosList.add(facturagasto);
			facturaConcepto.setFacturaGastos(factGastosList);
			facturaConcepto.setFactura(datosCliente.getFactura());

			facturaConcepto.setConcepto("");
			facturaConcepto.setNumExpediente("");

			if (datosCliente.getFactura().getFacturaConceptos() == null){

				//Si no existe ningun concepto pongo el primero como el que se muestra automaticamente
				datosCliente.setFacturaConceptoCargado(facturaConcepto);

				//Creo la lista de todos los conceptos
				List<FacturaConcepto> listaFacturaConcepto = new ArrayList<FacturaConcepto>();
				listaFacturaConcepto.add(facturaConcepto);

				datosCliente.getFactura().setFacturaConceptos(listaFacturaConcepto);					
			} else{
				datosCliente.getFactura().getFacturaConceptos().add(facturaConcepto);
			}


			//datosCliente.setFactura(factura);
			datosCliente.getFactura().getId().setNumColegiado(utilesColegiado.getNumColegiadoSession());
			datosCliente.getFactura().setNumCodigo(utilesColegiado.getNumColegiadoSession());
			datosCliente.getFactura().setNumExpediente(datosCliente.getFactura().getNumExpediente());
			datosCliente.setIsError(Boolean.FALSE);
			datosCliente.setIsPantallaAlta(Boolean.TRUE);	
			//datosCliente.setHonorariosTotalIva("0");
			//datosCliente.setHonorariosTotalIRPF("0");
			//datosCliente.setOtrosTotalIva("0");

			//List<FacturaOtro> factOtro = new ArrayList<FacturaOtro>();
			//Cambiado para que reciba un objeto en lugar de una lista porque se cambia la relacion a ontToOne. 
			//			datosCliente.getFactura().setFacturaOtro(factOtro);
			datosCliente.getFactura().setFacturaOtro(new FacturaOtro());
			datosCliente.getFactura().getFacturaOtro().setOtroCheckIvaHidden("1");
			datosCliente.getFactura().getFacturaOtro().setOtroCheckDescuentoHidden("0");
			//			datosCliente.getFactura().setFacturaOtro(factOtro.get(0));
			//List<FacturaProvFondo> factProvFondos = new ArrayList<FacturaProvFondo>();
			//			datosCliente.getFactura().setFacturaProvFondo(factProvFondos);
			datosCliente.getFactura().setFacturaProvFondo(new FacturaProvFondo());

			datosCliente.setNumDecimales(gestorPropiedades.valorPropertie("facturacion.num.decimales"));
			datosCliente.setMensajeBorrador(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
			datosCliente.setMensajePDF(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
			datosCliente.setMensajeImprimir(ConstantesFacturacion.ALERT_MENSAJE_IMPRIMIR);
			datosCliente.setMensajeClave(ConstantesFacturacion.ALERT_MENSAJE_MODFICAR);
			datosCliente.setIsRectificativa(Boolean.FALSE);
			datosCliente.getFactura().setCheckPdf(EstadoFacturacion.EstadoNueva.getValorEnum());
			datosCliente.getFactura().setFechaFactura(new Date());
			datosCliente.getFactura().setFechaAlta(new Date());
			//datosCliente.getFactura().setFondosId("0");
			//datosCliente.getFactura().getFacturaOtro().setIdOtro("0");

			// DRC@24-08-2012 Valida si el numero de factura, generado no existe en la base de datos
			// y si existe, esta buscando el siguiente numero libre 		
			//			String validaNumFactura = dao.checkNumFactura(campoBusqueda);					
			//			if (validaNumFactura == null) {
			//				do { 
			//					numFacturasN++;
			//					valorFactura = String.valueOf(numFacturasN);
			//					idFactura = utiles.rellenarCeros(valorFactura,5);
			//					numFactura =  datosCliente.getNumSerie() + numColegiado + tFecha + idFactura;
			//					campoBusqueda =  '%' + numColegiado + tFecha + numFactura.substring(numFactura.length()-5, numFactura.length());
			//					validaNumFactura = dao.checkNumFactura(campoBusqueda);						
			//				} while (validaNumFactura == null);
			//			}

			////////////////////////////////////////////////////////////////				
			datosCliente.getFactura().getId().setNumFactura(numFactura);
			datosCliente.getFactura().setNumeracion(new BigDecimal(idFactura));
			datosCliente.getFactura().setNifEmisor(utilesColegiado.getNifUsuario());
			datosCliente.getFactura().setImporteTotal(0f);
			datosCliente.setListaExpedientes(listaExpedientes);

			String numClave = dao.getClaveColegiado(utilesColegiado.getNumColegiadoSession());
			if (numClave!=null)
				datosCliente.setNumClave(numClave);
			else
				datosCliente.setNumClave(ConstantesFacturacion.CLAVE_DEFECTO);
			////////////////////////////////////////////////////////////////				
			getSession().put("datosCliente", datosCliente);
			getSession().put("idConcepto", "0");

			parametrosBusqueda.put(ConstantesSession.ID_USUARIO, utilesColegiado.getIdUsuarioSessionBigDecimal());

			//			tramiteTraficoBean = new TramiteTraficoBean(true);
			//			tramiteTraficoBean.setNumExpediente(new BigDecimal (numExpediente));

			log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA+"detalle.");

			return ConstantesFacturacion.RESULT_ALTA_FACTURACION;
		} catch(Throwable ex) {
			addActionError(ex.toString());
			log.error(ex);
			return Action.ERROR;
		}		
	}

	/**
	 * Método auxiliar que obtendrá de la lista en sesión una lista por su número de expediente.
	 * @param expediente
	 * @return
	 */
	private TramiteTraficoBean obtenerBeanConNumExpediente(
			BigDecimal expediente) {

		TramiteTraficoBean linea = new TramiteTraficoBean(true);
		//List<Object> listaBeansResult = listaConsultaTramiteTraficoFacturacion.getList();
		List<Object> listaBeansResult = ((PaginatedListImpl)getSession().get(ConstantesSession.LISTA_CONSULTA_TRAMITE_TRAFICO_FACTURACION_CONSULTA)).getList();
		for (Object objeto : listaBeansResult) {
			linea = (TramiteTraficoBean)objeto;
			if(expediente.equals(linea.getNumExpediente())){
				return linea;
			}
		}
		return null;
	}

	private void mantenimientoCamposBuscarDeFactura(){

		setListaConsultaTramiteTraficoFacturacion((PaginatedListImpl)getSession().get(ConstantesSession.LISTA_CONSULTA_TRAMITE_TRAFICO_FACTURACION_CONSULTA));
		setResultadosPorPagina((String)getSession().get(ConstantesSession.RESULTADOS_PAGINA));
		actualizaParametros(consultaTramiteTraficoNuevaFacturacion);

		getSession().put(ConstantesSession.RESULTADOS_PAGINA, getResultadosPorPagina());
		getSession().put(ConstantesSession.CONSULTA_TRAMITE_TRAFICO, consultaTramiteTraficoNuevaFacturacion);
	}

	/**
	 * Función de búsqueda para la paginación. Generará la búsqueda limitada por los parámetros de
	 * paginación.
	 * @return
	 */
	public String navegar() {

		mantenimientoCamposNavegarDeFactura();
		//asignarCamposDesdeSession();
		if(listaConsultaTramiteTraficoFacturacion == null){
			log.error(Claves.CLAVE_LOG_FACTURACION_CONSULTA+"al obtener lista de tramites para la facturacion.");
			log.info(Claves.CLAVE_LOG_FACTURACION_CONSULTA+" navegar.");
			return ERROR;
		}

		listaConsultaTramiteTraficoFacturacion.establecerParametros(getSort(), getDir(), getPage(), getResultadosPorPagina());
		listaConsultaTramiteTraficoFacturacion.getBaseDAO().setParametrosBusqueda(parametrosBusqueda);

		return "nuevaFactura";		
	}

	public String inicioFacturaAbono() throws Throwable {

		log.info(Claves.CLAVE_LOG_FACTURACION_CONSULTA_RECTIFICATIVA	+ "consultaAbono.");
		beanAbon = new ConsultaFacturaBean();
		beanAbon.setFecha(utilesFecha.getFechaFracionadaActual());
		beanAbon.setMensajeBorrador(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
		beanAbon.setMensajePDF(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
		beanAbon.setMensajeImprimir(ConstantesFacturacion.ALERT_MENSAJE_IMPRIMIR);
		beanAbon.setNumSerie(ConstantesFacturacion.FACTURA_ABONO_DEFECTO);
		mantenimientoCamposNavegar();

		if(null!=resultadosPorPagina){
			getSession().put(ConstantesSession.RESULTADOS_PAGINA,resultadosPorPagina); 
		}else{
			getSession().put(ConstantesSession.RESULTADOS_PAGINA, UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO); 
		}

		if (listaConsultaFacturaAbon == null) {

			listaConsultaFacturaAbon = dao.listaFacturas(beanAbon,
					EstadoFacturacion.EstadoFacturaAbono.getValorEnum(),
					EstadoFacturacion.EstadoFacturaAbono.getNombreEnum());
		}

		listaConsultaFacturaAbon = getIncluirColumnaCheckPDFCambioEstado(listaConsultaFacturaAbon);

		beanAbon.setNumSerie(ConstantesFacturacion.FACTURA_ABONO_DEFECTO);

		setListaConsultaFacturaRect(listaConsultaFacturaAbon);
		getSession().put("listaConsultaFacturaAbon", listaConsultaFacturaAbon);
		return ConstantesFacturacion.RESULT_CONSULTA_FACTURACION_ABONO;
	}

	public String buscarFacturaAbono() throws Exception, OegamExcepcion {
		getSession().put("beanAbon", beanAbon);

		if (beanAbon == null) {
			beanAbon = new ConsultaFacturaBean();
			beanAbon.setFecha(utilesFecha.getFechaFracionadaActual());

		}
		//		if ((bean != null && bean.getNumSerie() != null && !bean.getNumSerie().equalsIgnoreCase(""))
		//				|| !bean.getNumSerie().equalsIgnoreCase("R")) {
		//			bean.setNumSerie(ficheroPropiedades.getMensaje("factura.rectificativa.defecto"));
		//		}

		beanAbon.setMensajeBorrador(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
		beanAbon.setMensajePDF(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
		beanAbon.setMensajeImprimir(ConstantesFacturacion.ALERT_MENSAJE_IMPRIMIR);
		mantenimientoCamposBuscar();

		listaConsultaFacturaAbon = dao.buscaFactura(beanAbon,
				EstadoFacturacion.EstadoFacturaAbono.getValorEnum());
		listaConsultaFacturaAbon = getIncluirColumnaCheckPDFCambioEstado(listaConsultaFacturaAbon);
		setListaConsultaFacturaRect(listaConsultaFacturaAbon);
		getSession().put("listaConsultaFacturaAbon", listaConsultaFacturaAbon);

		return ConstantesFacturacion.RESULT_CONSULTA_FACTURACION_ABONO;
	}

	public String eliminaFacturaAbono()throws Exception, OegamExcepcion {
		String indices = getNumsFactura();
		String[] codSeleccionados = indices.split("-");
		Factura linea;
		mantenimientoCamposNavegar();
		for (int i = 0; i < codSeleccionados.length; i++) {
			linea = dao.detalleFacturaCompleto(codSeleccionados[i], utilesColegiado.getNumColegiadoSession());

			if (linea != null) {
				dao.borrarFactura(linea.getId());
				// addActionMessage("Factura " + codSeleccionados[i] +
				// " eliminada");
				addActionMessage(ConstantesFacturacion.MENSAJE_ERROR_ELIMINAR_UNO
						+ codSeleccionados[i] + " "
						+ ConstantesFacturacion.MENSAJE_ERROR_ELIMINAR_DOS);
			}
		}
		//beanRect = new ConsultaFacturaBean();
		//beanRect.setFecha(utilesFecha.getFechaFracionadaActual());
		listaConsultaFacturaAbon = dao.listaFacturas(
				beanAbon,EstadoFacturacion.EstadoFacturaAbono.getValorEnum(),
				EstadoFacturacion.EstadoFacturaAbono.getNombreEnum());
		listaConsultaFacturaAbon = getIncluirColumnaCheckPDFCambioEstado(listaConsultaFacturaAbon);

		// Actualizamos la lista de facturas
		getSession().put("listaConsultaFacturaAbon", listaConsultaFacturaAbon);
		return ConstantesFacturacion.RESULT_CONSULTA_FACTURACION_ABONO;		
	}

	public String nuevaFacturaResumen()throws OegamExcepcion{

		log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA+" nuevaFactura.");
		ArrayList<String> listaExpedientes = new ArrayList<String>();
		String tipoPersonaFacturacion = "Titular";
		String nifAnterior=null;
		String nif=null;
		String numColegiado = utilesColegiado.getNumColegiadoSession();

		/*Aquí recuperamos los expedientes*/
		String expedientes = getNumsExpediente();			
		String valor[] = expedientes.split("-");
		tipoPersonaFacturacion = request.getParameter("combo");
		BigDecimal numExp = new BigDecimal(0);


		try {		
			mantenimientoCamposNavegar();
			//Incorporamos la obtención de acciones por trámite
			parametrosBusqueda = new HashMap<String, Object>();
			//getSession().put(ConstantesSession.NUM_EXPEDIENTE, numExpediente);

			// Recupera si tiene titular facturación los detalles:
			factura = new Factura();
			DatosDAO dao = new DatosDAO();				

			Fecha fechaFrac = utilesFecha.getFechaActual();
			String fecha = fechaFrac.getMes() + fechaFrac.getAnio().substring(2);
			//String tFecha = "% " + fechaFrac.getMes().toString() +"/"+ fechaFrac.getAnio().substring(2).toString();
			//tFecha.trim();
			String numSerie = ConstantesFacturacion.FACTURA_SERIE_DEFECTO;

			if(datosCliente==null) datosCliente = new DatosClienteBean();
			datosCliente.setFactura(factura);
			datosCliente.getFactura().setNumSerie(numSerie);

			//int numFacturasBBDD = dao.buscaMaxFactura(numSerie,numColegiado);					
			int numFacturasBBDD = dao.buscaMaxFactura(numColegiado);
			int numFacturasN = numFacturasBBDD +1;				

			String valorFactura = String.valueOf(numFacturasN);
			String idFactura = utiles.rellenarCeros(valorFactura,5);
			String numFactura =  numSerie + numColegiado + fecha + idFactura;				
			for(int i=0; i<valor.length; i++){
				listaExpedientes.add(valor[i]);
				//Consultamos el nif del cliente. Si son diferentes clientes, no se puede hacer la factura
				if(nif!=null && nifAnterior!=null){
					if(!nif.equalsIgnoreCase(nifAnterior)){
						factura=null;
						addActionError("No se puede hacer factura para diferentes NIF/CIF");
						return "nuevaFactura";
					}
				}

				String numColegiadoExpediente = valor[i].substring(0, 4);
				numExp = new BigDecimal(valor[i]);
				//Almacenamos los expedientes de la factura
				datosCliente.getFactura().setNumExpediente(valor[i]);						

				if(datosCliente.getFactura().getId() == null){
					FacturaPK facturapk = new FacturaPK();
					facturapk.setNumColegiado(numColegiadoExpediente);						
					facturapk.setNumFactura(numFactura);
					datosCliente.getFactura().setId(facturapk);
					//factura.getId().setNumColegiado(numColegiadoExpediente);
				}
				TramiteTraficoBean linea = new TramiteTraficoBean(true);
				linea = obtenerBeanConNumExpediente(numExp);

				if(linea == null){
					addActionError("No se ha encontrado el número de expediente");
					return "nuevaFactura";
				}
				tipoTramite      = linea.getTipoTramite()!=null?linea.getTipoTramite().getValorEnum():null;
				String codigoTasa = linea.getTasa().getCodigoTasa()!=null?linea.getTasa().getCodigoTasa():null;
				if (codigoTasa != null && !codigoTasa.equalsIgnoreCase("")) {
					BeanPQTasasDetalle beanDetalle = new BeanPQTasasDetalle();
					beanDetalle.setP_CODIGO_TASA(codigoTasa);
					try {
						resultadoCodigoTasa = getModeloTasas().detalleTasa(beanDetalle);
						//precioTasa = resultadoCodigoTasa.getP_PRECIO().toString();
					} catch (Exception e){
						log.error("Excepcion al recoger el precio del Codigo de la Tasa: " + resultadoCodigoTasa);
					}					
				}


				switch (Integer.parseInt(tipoTramite.substring(1))) {
				//Matriculación
				case T1:{
					totalTramiteMatriculacionNoEstatica ++;
					traficoTramiteMatriculacionBean = new TramiteTraficoMatriculacionBean();
					Map<String, Object> resultadoMetodo =  getModeloMatriculacion().obtenerDetalle(linea.getNumExpediente(),utilesColegiado.getNumColegiadoSession(),utilesColegiado.getIdContratoSessionBigDecimal());
					traficoTramiteMatriculacionBean = (TramiteTraficoMatriculacionBean) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);	
					if (ConstantesFacturacion.TITULAR.equals(tipoPersonaFacturacion.toUpperCase())){
						//							nif = traficoTramiteMatriculacionBean.getTitularBean().getPersona().getNif();
						//							if (nif == null) { addActionError("El Tramite Seleccionado no tiene asignado un TITULAR"); return "consultaTramiteTraficoFacturacion";}
						//factura.setPersona(traficoTramiteMatriculacionBean.getTitularBean().getPersona());
					}else if (ConstantesFacturacion.COMPRADOR.equals(tipoPersonaFacturacion.toUpperCase())){
						nif = traficoTramiteMatriculacionBean.getTitularBean().getPersona().getNif();
						if (nif == null) { addActionError("El Tramite Seleccionado no tiene asignado un TITULAR"); 
						return "nuevaFactura";}
						//addActionError("Un trámite de Matriculacion no tiene asignado Adquiriente");
						//return "consultaTramiteTraficoFacturacion";
					}else if (ConstantesFacturacion.TRANSMITENTE.equals(tipoPersonaFacturacion.toUpperCase())){
						addActionError("Un trámite de Matriculacion no tiene asignado Transmitente");
						return "nuevaFactura";
					}else if (ConstantesFacturacion.COMPRAVENTA.equals(tipoPersonaFacturacion.toUpperCase())){
						addActionError("Un Tramite de matriculacion no puede asociarse a COMPRAVENTA"); 
						return "nuevaFactura";
					}else {
						datosCliente.getFactura().setPersona(null);
					}
					break;
				}	
				//Transmisión
				case T2:{
					totalTramiteTransmisionNoEstatica ++;
					tramiteTraficoTransmisionBean = new TramiteTraficoTransmisionBean();
					Map<String, Object> resultadoMetodo =  getModeloTransmision().obtenerDetalle(linea.getNumExpediente());
					tramiteTraficoTransmisionBean =(TramiteTraficoTransmisionBean) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
					if (ConstantesFacturacion.TITULAR.equals(tipoPersonaFacturacion.toUpperCase())){
						addActionError("Un trámite de Transmision no tiene asignado Titular");
						return "nuevaFactura";
					}else if (ConstantesFacturacion.COMPRADOR.equals(tipoPersonaFacturacion.toUpperCase())){
						nif = tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona().getNif();
						if (nif == null) { addActionError("El Tramite Seleccionado no tiene asignado un COMPRADOR"); 
						return "nuevaFactura";}
						//factura.setPersonaFac(tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona());
					}else if (ConstantesFacturacion.TRANSMITENTE.equals(tipoPersonaFacturacion.toUpperCase())){
						nif = tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona().getNif();
						if (nif == null) { addActionError("El Tramite Seleccionado no tiene asignado un TRANSMITENTE"); 
						return "nuevaFactura";}
						//factura.setPersonaFac(tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona());
					}else if (ConstantesFacturacion.COMPRAVENTA.equals(tipoPersonaFacturacion.toUpperCase())){
						//El poseedor es el CompraVenta.
						nif = tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getNif();
						if (nif == null) { addActionError("El Tramite Seleccionado no tiene asignado un COMPRAVENTA"); 
						return "nuevaFactura";}
					}else {
						//factura.setPersonaFac(null);
					}
					break;
				}
				//Transmisión Electrónica
				case T7:{
					totalTramiteTransmisionTelematicaNoEstatica ++;
					tramiteTraficoTransmisionBean = new TramiteTraficoTransmisionBean();
					Map<String, Object> resultadoMetodo =  getModeloTransmision().obtenerDetalle(linea.getNumExpediente());
					tramiteTraficoTransmisionBean =(TramiteTraficoTransmisionBean) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
					if (ConstantesFacturacion.TITULAR.equals(tipoPersonaFacturacion.toUpperCase())){
						addActionError("Un trámite de Transmision Electronica no tiene asignado Titular");
						return "nuevaFactura";
					}else if (ConstantesFacturacion.COMPRADOR.equals(tipoPersonaFacturacion.toUpperCase())){
						nif = tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona().getNif();
						if (nif == null) { addActionError("El Tramite Seleccionado no tiene asignado un COMPRADOR"); 
						return "nuevaFactura";}
						//factura.setPersonaFac(tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona());
					}else if (ConstantesFacturacion.TRANSMITENTE.equals(tipoPersonaFacturacion.toUpperCase())){
						nif = tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona().getNif();
						if (nif == null) { addActionError("El Tramite Seleccionado no tiene  asignado un TRANSMITENTE"); 
						return "nuevaFactura";}
						//factura.setPersonaFac(tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona());
					}else if (ConstantesFacturacion.COMPRAVENTA.equals(tipoPersonaFacturacion.toUpperCase())){
						//El poseedor es el CompraVenta.
						nif = tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getNif();
						if (nif == null) { addActionError("El Tramite Seleccionado no tiene asignado un COMPRAVENTA"); 
						return "nuevaFactura";}
					}else {
						//factura.setPersonaFac(null);
					}
					break;
				}
				//Bajas
				case T3:{
					tramiteTraficoBaja = new TramiteTrafBajaDto();
					tramiteTraficoBaja = servicioTramiteTraficoBaja.getTramiteBaja(linea.getNumExpediente(), true);
					if (ConstantesFacturacion.TITULAR.equals(tipoPersonaFacturacion.toUpperCase())){
						nif = tramiteTraficoBaja.getTitular().getPersona().getNif();
						if (nif == null){ addActionError("El Tramite Seleccionado no tiene asignado un TITULAR");return "nuevaFactura";}
						//factura.setPersonaFac(tramiteTraficoBaja.getTitular().getPersona());
					}else if (ConstantesFacturacion.COMPRADOR.equals(tipoPersonaFacturacion.toUpperCase())){
						nif = tramiteTraficoBaja.getAdquiriente().getPersona().getNif();
						if (nif == null) { addActionError("El Tramite Seleccionado no tiene asignado un COMPRADOR"); return "nuevaFactura";}
						//factura.setPersonaFac(tramiteTraficoBaja.getAdquiriente().getPersona());
					}else if (ConstantesFacturacion.TRANSMITENTE.equals(tipoPersonaFacturacion.toUpperCase())){
						addActionError("Un trámite de Baja no tiene asignado Transmitente");
						return "nuevaFactura";
					}else if (ConstantesFacturacion.COMPRAVENTA.equals(tipoPersonaFacturacion.toUpperCase())){
						addActionError("Un Tramite de baja no puede asociarse a COMPRAVENTA"); return "nuevaFactura";
					}else {
						//factura.setPersonaFac(null);
					}
					break;
				}
				// Duplicados
				case T8:{
					totalTramiteDuplicadosNoEstatica ++;
					tramiteTraficoDuplicado = new TramiteTraficoDuplicadoBean(true);
					Map<String, Object> resultadoMetodo = getModeloDuplicado().obtenerDetalle(linea.getNumExpediente(),utilesColegiado.getNumColegiadoSession(),utilesColegiado.getIdContratoSessionBigDecimal());
					tramiteTraficoDuplicado =(TramiteTraficoDuplicadoBean) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
					if (ConstantesFacturacion.TITULAR.equals(tipoPersonaFacturacion.toUpperCase())){
						nif = tramiteTraficoDuplicado.getTitular().getPersona().getNif();
						if (nif == null){ addActionError("El Tramite Seleccionado no tiene asignado un TITULAR"); return "nuevaFactura";}
						//factura.setPersonaFac(tramiteTraficoDuplicado.getTitular().getPersona());
					}else if (ConstantesFacturacion.COMPRADOR.equals(tipoPersonaFacturacion.toUpperCase())){
						addActionError("Un trámite de Duplicado no tiene asignado Adquiriente");
						return "nuevaFactura";
					}else if (ConstantesFacturacion.TRANSMITENTE.equals(tipoPersonaFacturacion.toUpperCase())){
						addActionError("Un trámite de Duplicado no tiene asignado Transmitente");
						return "nuevaFactura";
					}else if (ConstantesFacturacion.COMPRAVENTA.equals(tipoPersonaFacturacion.toUpperCase())){
						addActionError("Un Tramite Duplicado no puede asociarse a COMPRAVENTA"); 
						return "nuevaFactura";
					}else {
						//	factura.setPersonaFac(null);
					}
					break;
				}
				//Solicitudes
				case T4:{
					totalTramiteSolicitudesNoEstatica ++;
					if (ConstantesFacturacion.TITULAR.equals(tipoPersonaFacturacion.toUpperCase())){
						addActionError("Un trámite de Solicitud no tiene asignado Titular");
						return "nuevaFactura";
					}else if (ConstantesFacturacion.COMPRADOR.equals(tipoPersonaFacturacion.toUpperCase())){
						addActionError("Un trámite de Solicitud no tiene asignado Adquiriente");
						return "nuevaFactura";
					}else if (ConstantesFacturacion.TRANSMITENTE.equals(tipoPersonaFacturacion.toUpperCase())){
						addActionError("Un trámite de Solicitud no tiene asignado Transmitente");
						return "nuevaFactura";
					}else if (ConstantesFacturacion.COMPRAVENTA.equals(tipoPersonaFacturacion.toUpperCase())){
						addActionError("Un Tramite de Solicitud no puede asociarse a COMPRAVENTA"); 
						return "nuevaFactura";
					}else {
						//factura.setPersonaFac(null);
					}
					break;
				}
				//No tiene tipo, se define el error y se redirecciona a la pagina de consulta
				default:{
					addActionError("Este trámite no tiene asignado ningún tipo");
					log.debug("Este trámite no tiene asignado ningún tipo");
					listaConsultaTramiteTraficoFacturacion.establecerParametros(getSort(), getDir(), getPage(), getResultadosPorPagina());
					listaConsultaTramiteTraficoFacturacion.getBaseDAO().setParametrosBusqueda(parametrosBusqueda);
					return "nuevaFactura";
				}  	
				}					

				if (datosCliente.getFactura().getPersona() == null){
					hibernate.entities.personas.Persona pers = dao.buscarPersona(nif,numColegiado);												
					if (pers != null){
						hibernate.entities.personas.Direccion dir = dao.buscarDireccionActual(nif,numColegiado);
						datosCliente.getFactura().setPersona(pers);
						datosCliente.getFactura().getPersona().setDireccionActual(dir);							

						if (pers.getFechaNacimiento() != null){
							datosCliente.getFactura().getPersona().setFnacimiento(utilesFecha.getFechaConDate(pers.getFechaNacimiento()));
						}else{
							datosCliente.getFactura().getPersona().setFnacimiento(null);
						}
					}else if ((ConstantesFacturacion.TITULAR.equals(tipoPersonaFacturacion.toUpperCase()))
							|| (ConstantesFacturacion.COMPRADOR.equals(tipoPersonaFacturacion.toUpperCase()))
							|| (ConstantesFacturacion.TRANSMITENTE.equals(tipoPersonaFacturacion.toUpperCase()))){
						addActionError("No existe una persona con DNI "+ nif + " para el Colegiado " + numColegiado );
						return "nuevaFactura";
					}
				} 

			} //Fin de for factura

			//No puede ser 0 porque cuando se pinche en crear factura resumencomprobará que al menos hay 1 TT seleccionado.
			if (totalTramiteMatriculacionNoEstatica > 0){
				CreaConceptoFacturaResumen(totalTramiteMatriculacionNoEstatica, "Matriculacion");
				totalTramiteMatriculacion = totalTramiteMatriculacionNoEstatica;}
			if (totalTramiteTransmisionNoEstatica > 0){
				CreaConceptoFacturaResumen(totalTramiteTransmisionNoEstatica, "Transmisión");
				totalTramiteTransmision = totalTramiteTransmisionNoEstatica;}
			if (totalTramiteTransmisionTelematicaNoEstatica > 0){
				CreaConceptoFacturaResumen(totalTramiteTransmisionTelematicaNoEstatica, "Transmisión telemática");
				totalTramiteTransmisionTelematica = totalTramiteTransmisionTelematicaNoEstatica;}
			if (totalTramiteBajaNoEstatica > 0){
				CreaConceptoFacturaResumen(totalTramiteBajaNoEstatica, "Baja");
				totalTramiteBaja = totalTramiteBajaNoEstatica;}
			if (totalTramiteDuplicadosNoEstatica > 0){
				CreaConceptoFacturaResumen(totalTramiteDuplicadosNoEstatica, "Duplicados");
				totalTramiteDuplicados = totalTramiteDuplicadosNoEstatica;}
			if (totalTramiteSolicitudesNoEstatica > 0){
				CreaConceptoFacturaResumen(totalTramiteSolicitudesNoEstatica, "Solicitudes");
				totalTramiteSolicitudes = totalTramiteSolicitudesNoEstatica;}

			//datosCliente.setFactura(factura);
			datosCliente.getFactura().getId().setNumColegiado(utilesColegiado.getNumColegiadoSession());
			datosCliente.getFactura().setNumCodigo(utilesColegiado.getNumColegiadoSession());
			datosCliente.getFactura().setNumExpediente(datosCliente.getFactura().getNumExpediente());
			datosCliente.setIsError(Boolean.FALSE);
			datosCliente.setIsPantallaAlta(Boolean.TRUE);	

			//Cambiado para que reciba un objeto en lugar de una lista porque se cambia la relacion a ontToOne. 
			datosCliente.getFactura().setFacturaOtro(new FacturaOtro());
			datosCliente.getFactura().getFacturaOtro().setOtroCheckIvaHidden("1");
			datosCliente.getFactura().getFacturaOtro().setOtroCheckDescuentoHidden("0");
			datosCliente.getFactura().setFacturaProvFondo(new FacturaProvFondo());

			datosCliente.setNumDecimales(gestorPropiedades.valorPropertie("facturacion.num.decimales"));
			datosCliente.setMensajeBorrador(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
			datosCliente.setMensajePDF(ConstantesFacturacion.ALERT_MENSAJE_BORRADOR);
			datosCliente.setMensajeImprimir(ConstantesFacturacion.ALERT_MENSAJE_IMPRIMIR);
			datosCliente.setMensajeClave(ConstantesFacturacion.ALERT_MENSAJE_MODFICAR);
			datosCliente.setIsRectificativa(Boolean.FALSE);
			datosCliente.getFactura().setCheckPdf(EstadoFacturacion.EstadoNueva.getValorEnum());
			datosCliente.getFactura().setFechaFactura(new Date());
			datosCliente.getFactura().setFechaAlta(new Date());

			////////////////////////////////////////////////////////////////				
			datosCliente.getFactura().getId().setNumFactura(numFactura);
			datosCliente.getFactura().setNumeracion(new BigDecimal(idFactura));
			datosCliente.getFactura().setNifEmisor(utilesColegiado.getNifUsuario());
			datosCliente.getFactura().setImporteTotal(0f);
			datosCliente.setListaExpedientes(listaExpedientes);

			String numClave = dao.getClaveColegiado(utilesColegiado.getNumColegiadoSession());
			if (numClave!=null)
				datosCliente.setNumClave(numClave);
			else
				datosCliente.setNumClave(ConstantesFacturacion.CLAVE_DEFECTO);
			////////////////////////////////////////////////////////////////				
			getSession().put("datosCliente", datosCliente);
			getSession().put("idConcepto", "0");

			parametrosBusqueda.put(ConstantesSession.ID_USUARIO, utilesColegiado.getIdUsuarioSessionBigDecimal());

			log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA+"detalle.");
			vieneDeEliminarExpediente = false;

			return "altaFacturaResumen";
		} catch(Throwable ex) {
			log.error(ex);
			addActionError(ex.toString());
			return Action.ERROR;
		}		
	}
	/*
	 * Método para recorrer 
	 */
	public void CreaConceptoFacturaResumen(int totalTipoTramite, String tipoTramiteNombre){
		String concepto = "";
		//No puede ser 0 porque cuando se pinche en crear factura resumencomprobará que al menos hay 1 TT seleccionado.
		if (totalTipoTramite==1)
			concepto = totalTipoTramite + " TRÁMITE DE "+tipoTramiteNombre.toUpperCase();
		else
			concepto = totalTipoTramite + " TRÁMITES DE "+tipoTramiteNombre.toUpperCase();

		FacturaConcepto facturaConcepto = new FacturaConcepto();
		facturaConcepto.setIdConcepto(idConcepto);

		//Creamos un Honorario para que se muestre por pantalla
		List<FacturaHonorario> factHonorario = new ArrayList<FacturaHonorario>();
		FacturaHonorario fHonorario = new FacturaHonorario();					
		fHonorario.setIdHonorario(0);
		//				fHonorario.setHonorario("");
		fHonorario.setHonorarioCheckDescuento("0");
		fHonorario.setHonorarioCheckIva(new BigDecimal("1"));
		fHonorario.setHonorarioCheckIrpf(new BigDecimal("0"));					
		fHonorario.setHonorario(0F);
		fHonorario.setHonorarioDescripcion("Introduzca la descripcion");
		fHonorario.setHonorarioDescuento(0F);
		fHonorario.setHonorarioIva(ConstantesFacturacion.IVA_DEFECTO);
		fHonorario.setHonorarioIrpf(ConstantesFacturacion.IRPF_DEFECTO);
		fHonorario.setHonorarioTotalIrpf(0F);
		fHonorario.setHonorarioTotalIva(0F);		
		fHonorario.setHonorarioTotal(0F);
		fHonorario.setFacturaConcepto(facturaConcepto);
		factHonorario.add(fHonorario);
		facturaConcepto.setFacturaHonorarios(factHonorario);

		//Creamos un Suplido para que se muestre por pantalla.
		List<FacturaSuplido> factSuplidoList = new ArrayList<FacturaSuplido>();
		FacturaSuplido facturaSuplido = new FacturaSuplido();
		facturaSuplido.setIdSuplido(0);
		facturaSuplido.setSuplidoCheckDescuento("0");
		facturaSuplido.setSuplido(0F);
		facturaSuplido.setSuplidoDescripcion("Introduzca la descripcion");
		facturaSuplido.setSuplidoDescuento(0F);	
		facturaSuplido.setSuplidoTotal(0F);
		facturaSuplido.setFacturaConcepto(facturaConcepto);
		factSuplidoList.add(facturaSuplido);
		facturaConcepto.setFacturaSuplidos(factSuplidoList);

		//Creamos un Gasto para que se muestre por pantalla.
		List<FacturaGasto> factGastosList = new ArrayList<FacturaGasto>();
		FacturaGasto facturagasto = new FacturaGasto();
		facturagasto.setIdGasto(0);
		facturagasto.setGastoDescripcion("Introduzca la descripcion");
		facturagasto.setGasto(0F);
		facturagasto.setGastoCheck("1");
		facturagasto.setGastoIva(ConstantesFacturacion.IVA_DEFECTO);
		facturagasto.setGastoTotal(0F);
		facturagasto.setFacturaConcepto(facturaConcepto);
		factGastosList.add(facturagasto);
		facturaConcepto.setFacturaGastos(factGastosList);
		facturaConcepto.setFactura(datosCliente.getFactura());

		facturaConcepto.setConcepto(concepto);
		//De momento no se inserta numExp para el concepto.
		//		facturaConcepto.setNumExpediente(String.valueOf(numExp));

		if (datosCliente.getFactura().getFacturaConceptos() == null){

			//Si no existe ningun concepto pongo el primero como el que se muestra automaticamente
			datosCliente.setFacturaConceptoCargado(facturaConcepto);

			//Creo la lista de todos los conceptos
			List<FacturaConcepto> listaFacturaConcepto = new ArrayList<FacturaConcepto>();
			listaFacturaConcepto.add(facturaConcepto);

			datosCliente.getFactura().setFacturaConceptos(listaFacturaConcepto);					
		} else{
			datosCliente.getFactura().getFacturaConceptos().add(facturaConcepto);
		}
	}

	public String mostrarExpedientes(){
		String[] arrayNumExpedientes = null;
		String lNumExpedientes = null;
		if (vieneDeEliminarExpediente==false){
			lNumExpedientes=request.getParameter("listaExpedientes");
			arrayNumExpedientes = lNumExpedientes.split(",");
			listaExpedientesBean = new ArrayList<ExpedientesBean>();

			for (String numExpediente : arrayNumExpedientes){
				numExpediente = numExpediente.replace("]", "");
				numExpediente = numExpediente.replace("[", "");
				ExpedientesBean expedientesBean = new ExpedientesBean();
				expedientesBean.setNumExpediente(numExpediente);
				listaExpedientesBean.add(expedientesBean);
				listaNumsExpediente = listaExpedientesBean;
			}
		}else{
			listaExpedientesBean = listaNumsExpediente;
		}
		vieneDeEliminarExpediente = false;
		return "numExpedientes";
	}




	//	public List<Factura> getListaConsultaFactura() {
	//		return listaConsultaFactura;
	//	}
	//
	//	public void setListaConsultaFactura(List<Factura> listaConsultaFactura) {
	//		this.listaConsultaFactura = listaConsultaFactura;
	//	}

	public String getResultadosPorPagina() {
		return resultadosPorPagina;
	}

	public void setResultadosPorPagina(String resultadosPorPagina) {
		this.resultadosPorPagina = resultadosPorPagina;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFicheroDescarga() {
		return ficheroDescarga;
	}

	public void setFicheroDescarga(String ficheroDescarga) {
		this.ficheroDescarga = ficheroDescarga;
	}

	public String getNumFactura() {
		return numFactura;
	}

	public void setNumFactura(String numFactura) {
		this.numFactura = numFactura;
	}

	public DatosDAO getDao() {
		return dao;
	}

	public void setDao(DatosDAO dao) {
		this.dao = dao;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	//	public ConsultaFacturaBean getBean() {
	//		return bean;
	//	}
	//
	//	public void setBean(ConsultaFacturaBean bean) {
	//		this.bean = bean;
	//	}

	public List<FacturaColegiadoConcepto> getLFacturaConcepto() {
		return lFacturaConcepto;
	}

	public void setLFacturaConcepto(
			List<FacturaColegiadoConcepto> lFacturaConcepto) {
		this.lFacturaConcepto = lFacturaConcepto;
	}

	public String getDescripcionConcepto() {
		return descripcionConcepto;
	}

	public void setDescripcionConcepto(String descripcionConcepto) {
		this.descripcionConcepto = descripcionConcepto;
	}

	public long getIdConcepto() {
		return idConcepto;
	}

	public void setIdConcepto(long idConcepto) {
		this.idConcepto = idConcepto;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getConceptoIdBorrar() {
		return conceptoIdBorrar;
	}

	public void setConceptoIdBorrar(String conceptoIdBorrar) {
		this.conceptoIdBorrar = conceptoIdBorrar;
	}

	public String getHonorarioIdBorrar() {
		return honorarioIdBorrar;
	}

	public void setHonorarioIdBorrar(String honorarioIdBorrar) {
		this.honorarioIdBorrar = honorarioIdBorrar;
	}

	public String[] getHonorarioDescripcion() {
		return honorarioDescripcion;
	}

	public void setHonorarioDescripcion(String[] honorarioDescripcion) {
		this.honorarioDescripcion = honorarioDescripcion;
	}

	public String getIdConceptoAltaModificacion() {
		return idConceptoAltaModificacion;
	}

	public void setIdConceptoAltaModificacion(String idConceptoAltaModificacion) {
		this.idConceptoAltaModificacion = idConceptoAltaModificacion;
	}

	public String getClaveColegiado() {
		return claveColegiado;
	}

	public void setClaveColegiado(String claveColegiado) {
		this.claveColegiado = claveColegiado;
	}

	public List<Factura> getListaConsultaFacturaNormal() {
		return listaConsultaFacturaNormal;
	}

	public void setListaConsultaFacturaNormal(
			List<Factura> listaConsultaFacturaNormal) {
		this.listaConsultaFacturaNormal = listaConsultaFacturaNormal;
	}

	public List<Factura> getListaConsultaFacturaRect() {
		return listaConsultaFacturaRect;
	}

	public void setListaConsultaFacturaRect(List<Factura> listaConsultaFacturaRect) {
		this.listaConsultaFacturaRect = listaConsultaFacturaRect;
	}

	public ConsultaFacturaBean getBeanNormal() {
		return beanNormal;
	}

	public void setBeanNormal(ConsultaFacturaBean beanNormal) {
		this.beanNormal = beanNormal;
	}

	public ConsultaFacturaBean getBeanRect() {
		return beanRect;
	}

	public void setBeanRect(ConsultaFacturaBean beanRect) {
		this.beanRect = beanRect;
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

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public void setConsultaTramiteTraficoFactura(
			ConsultaTramiteTraficoFacturacionBean consultaTramiteTraficoFactura) {
		this.consultaTramiteTraficoNuevaFacturacion = consultaTramiteTraficoFactura;
	}

	public void setListaConsultaTramiteTraficoFacturacion(
			PaginatedListImpl listaConsultaTramiteTraficoFacturacion) {
		this.listaConsultaTramiteTraficoFacturacion = listaConsultaTramiteTraficoFacturacion;
	}

	public ConsultaTramiteTraficoFacturacionBean getConsultaTramiteTraficoNuevaFacturacion() {
		return consultaTramiteTraficoNuevaFacturacion;
	}

	public void setConsultaTramiteTraficoNuevaFacturacion(
			ConsultaTramiteTraficoFacturacionBean consultaTramiteTraficoNuevaFacturacion) {
		this.consultaTramiteTraficoNuevaFacturacion = consultaTramiteTraficoNuevaFacturacion;
	}

	public PaginatedListImpl getListaConsultaTramiteTraficoFacturacion() {
		return listaConsultaTramiteTraficoFacturacion;
	}

	public String getNumsExpediente() {
		return numsExpediente;
	}

	public void setNumsExpediente(String numsExpediente) {
		this.numsExpediente = numsExpediente;
	}
	
	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public BeanPQTasasDetalle getResultadoCodigoTasa() {
		return resultadoCodigoTasa;
	}

	public void setResultadoCodigoTasa(BeanPQTasasDetalle resultadoCodigoTasa) {
		this.resultadoCodigoTasa = resultadoCodigoTasa;
	}

	public TramiteTraficoBean getTramiteTraficoBean() {
		return tramiteTraficoBean;
	}

	public void setTramiteTraficoBean(TramiteTraficoBean tramiteTraficoBean) {
		this.tramiteTraficoBean = tramiteTraficoBean;
	}

	public TramiteTraficoMatriculacionBean getTraficoTramiteMatriculacionBean() {
		return traficoTramiteMatriculacionBean;
	}

	public void setTraficoTramiteMatriculacionBean(
			TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean) {
		this.traficoTramiteMatriculacionBean = traficoTramiteMatriculacionBean;
	}

	public TramiteTraficoTransmisionBean getTramiteTraficoTransmisionBean() {
		return tramiteTraficoTransmisionBean;
	}

	public void setTramiteTraficoTransmisionBean(
			TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean) {
		this.tramiteTraficoTransmisionBean = tramiteTraficoTransmisionBean;
	}
	
	public TramiteTrafBajaDto getTramiteTraficoBaja() {
		return tramiteTraficoBaja;
	}

	public void setTramiteTraficoBaja(TramiteTrafBajaDto tramiteTraficoBaja) {
		this.tramiteTraficoBaja = tramiteTraficoBaja;
	}

	public TramiteTraficoDuplicadoBean getTramiteTraficoDuplicado() {
		return tramiteTraficoDuplicado;
	}

	public void setTramiteTraficoDuplicado(
			TramiteTraficoDuplicadoBean tramiteTraficoDuplicado) {
		this.tramiteTraficoDuplicado = tramiteTraficoDuplicado;
	}

	public ConsultaFacturaBean getBeanAbon() {
		return beanAbon;
	}

	public void setBeanAbon(ConsultaFacturaBean beanAbon) {
		this.beanAbon = beanAbon;
	}

	public List<Factura> getListaConsultaFacturaAbon() {
		return listaConsultaFacturaAbon;
	}

	public void setListaConsultaFacturaAbon(List<Factura> listaConsultaFacturaAbon) {
		this.listaConsultaFacturaAbon = listaConsultaFacturaAbon;
	}

	public ExpedientesBean getExpedientesBean() {
		return expedientesBean;
	}

	public void setExpedientesBean(ExpedientesBean expedientesBean) {
		this.expedientesBean = expedientesBean;
	}

	public List<ExpedientesBean> getListaExpedientesBean() {
		return listaExpedientesBean;
	}

	public void setListaExpedientesBean(List<ExpedientesBean> listaExpedientesBean) {
		this.listaExpedientesBean = listaExpedientesBean;
	}

	public int getTotalTramiteMatriculacion() {
		return totalTramiteMatriculacion;
	}

	public void setTotalTramiteMatriculacion(int totalTramiteMatriculacion) {
		this.totalTramiteMatriculacion = totalTramiteMatriculacion;
	}

	public int getTotalTramiteTransmision() {
		return totalTramiteTransmision;
	}

	public void setTotalTramiteTransmision(int totalTramiteTransmision) {
		this.totalTramiteTransmision = totalTramiteTransmision;
	}

	public int getTotalTramiteTransmisionTelematica() {
		return totalTramiteTransmisionTelematica;
	}

	public void setTotalTramiteTransmisionTelematica(
			int totalTramiteTransmisionTelematica) {
		this.totalTramiteTransmisionTelematica = totalTramiteTransmisionTelematica;
	}

	public int getTotalTramiteBaja() {
		return totalTramiteBaja;
	}

	public void setTotalTramiteBaja(int totalTramiteBaja) {
		this.totalTramiteBaja = totalTramiteBaja;
	}

	public int getTotalTramiteDuplicados() {
		return totalTramiteDuplicados;
	}

	public void setTotalTramiteDuplicados(int totalTramiteDuplicados) {
		this.totalTramiteDuplicados = totalTramiteDuplicados;
	}

	public int getTotalTramiteSolicitudes() {
		return totalTramiteSolicitudes;
	}

	public void setTotalTramiteSolicitudes(int totalTramiteSolicitudes) {
		this.totalTramiteSolicitudes = totalTramiteSolicitudes;
	}

	public int getTotalTramiteMatriculacionNoEstatica() {
		return totalTramiteMatriculacionNoEstatica;
	}

	public void setTotalTramiteMatriculacionNoEstatica(
			int totalTramiteMatriculacionNoEstatica) {
		this.totalTramiteMatriculacionNoEstatica = totalTramiteMatriculacionNoEstatica;
	}

	public int getTotalTramiteTransmisionNoEstatica() {
		return totalTramiteTransmisionNoEstatica;
	}

	public void setTotalTramiteTransmisionNoEstatica(
			int totalTramiteTransmisionNoEstatica) {
		this.totalTramiteTransmisionNoEstatica = totalTramiteTransmisionNoEstatica;
	}

	public int getTotalTramiteTransmisionTelematicaNoEstatica() {
		return totalTramiteTransmisionTelematicaNoEstatica;
	}

	public void setTotalTramiteTransmisionTelematicaNoEstatica(
			int totalTramiteTransmisionTelematicaNoEstatica) {
		this.totalTramiteTransmisionTelematicaNoEstatica = totalTramiteTransmisionTelematicaNoEstatica;
	}

	public int getTotalTramiteBajaNoEstatica() {
		return totalTramiteBajaNoEstatica;
	}

	public void setTotalTramiteBajaNoEstatica(int totalTramiteBajaNoEstatica) {
		this.totalTramiteBajaNoEstatica = totalTramiteBajaNoEstatica;
	}

	public int getTotalTramiteDuplicadosNoEstatica() {
		return totalTramiteDuplicadosNoEstatica;
	}

	public void setTotalTramiteDuplicadosNoEstatica(
			int totalTramiteDuplicadosNoEstatica) {
		this.totalTramiteDuplicadosNoEstatica = totalTramiteDuplicadosNoEstatica;
	}

	public int getTotalTramiteSolicitudesNoEstatica() {
		return totalTramiteSolicitudesNoEstatica;
	}

	public void setTotalTramiteSolicitudesNoEstatica(
			int totalTramiteSolicitudesNoEstatica) {
		this.totalTramiteSolicitudesNoEstatica = totalTramiteSolicitudesNoEstatica;
	}

	public String getTipoFactura() {
		return tipoFactura;
	}

	public void setTipoFactura(String tipoFactura) {
		this.tipoFactura = tipoFactura;
	}

	public void setDatosCliente(DatosClienteBean datosCliente) {
		this.datosCliente = datosCliente;
	}
	// ************************************************************************************************
	// ************************************************************************************************
	// * Getter & Setter *
	// ************************************************************************************************
	// ************************************************************************************************

	public String navegarAbono() {
		mantenimientoCamposNavegar();
		return ConstantesFacturacion.RESULT_CONSULTA_FACTURACION_ABONO;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getNumsFactura() {
		return numsFactura;
	}

	public void setNumsFactura(String numsFactura) {
		this.numsFactura = numsFactura;
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */

	public UtilidadIreport getUtilidadIreport() {
		if (utilidadIreport == null) {
			utilidadIreport = new UtilidadIreport();
		}
		return utilidadIreport;
	}

	public void setUtilidadIreport(UtilidadIreport utilidadIreport) {
		this.utilidadIreport = utilidadIreport;
	}

	public ModeloDuplicado getModeloDuplicado() {
		if (modeloDuplicado == null) {
			modeloDuplicado = new ModeloDuplicado();
		}
		return modeloDuplicado;
	}

	public void setModeloDuplicado(ModeloDuplicado modeloDuplicado) {
		this.modeloDuplicado = modeloDuplicado;
	}

	public ModeloMatriculacion getModeloMatriculacion() {
		if (modeloMatriculacion == null) {
			modeloMatriculacion = new ModeloMatriculacion();
		}
		return modeloMatriculacion;
	}

	public void setModeloMatriculacion(ModeloMatriculacion modeloMatriculacion) {
		this.modeloMatriculacion = modeloMatriculacion;
	}

	public ModeloTasas getModeloTasas() {
		if (modeloTasas == null) {
			modeloTasas = new ModeloTasas();
		}
		return modeloTasas;
	}

	public void setModeloTasas(ModeloTasas modeloTasas) {
		this.modeloTasas = modeloTasas;
	}

	public ModeloTrafico getModeloTrafico() {
		if (modeloTrafico == null) {
			modeloTrafico = new ModeloTrafico();
		}
		return modeloTrafico;
	}

	public void setModeloTrafico(ModeloTrafico modeloTrafico) {
		this.modeloTrafico = modeloTrafico;
	}

	public ModeloTransmision getModeloTransmision() {
		if (modeloTransmision == null) {
			modeloTransmision = new ModeloTransmision();
		}
		return modeloTransmision;
	}

	public void setModeloTransmision(ModeloTransmision modeloTransmision) {
		this.modeloTransmision = modeloTransmision;
	}

	public boolean isGestorComoEmisor() {
		return gestorComoEmisor;
	}

	public void setGestorComoEmisor(boolean gestorComoEmisor) {
		this.gestorComoEmisor = gestorComoEmisor;
	}

	public boolean isGestoriaComoEmisor() {
		return gestoriaComoEmisor;
	}

	public void setGestoriaComoEmisor(boolean gestoriaComoEmisor) {
		this.gestoriaComoEmisor = gestoriaComoEmisor;
	}
	

}
