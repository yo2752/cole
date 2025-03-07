package colas.acciones;

import java.math.BigDecimal;
import java.util.HashMap;

import org.gestoresmadrid.core.model.beans.ColaBean;

import colas.modelo.ModeloSolicitud;
import escrituras.beans.ResultBean;
import escrituras.utiles.PaginatedListImpl;
import escrituras.utiles.UtilesVista;
import general.acciones.ActionBase;
import oegam.constantes.ConstantesSession;
import trafico.beans.paginacion.ConsultaTramiteTraficoBean;

/**
 * 
 * Clase action para las solicitudes que se pasan de número de intentos por errores en el servicio. 
 * Se podrán buscar, reactivar y finalizar las SOLICITUDES erroneas.
 *
 */
public class SolicitudesAction extends ActionBase {

	private ConsultaTramiteTraficoBean consultaTramiteTrafico;
	
	private static final long serialVersionUID = 1L;
	//Parámetros de las peticiones del displayTag
	private PaginatedListImpl listaSolicitudes;//Lista que se va a mostrar en la vista es de tipo PaginatedList para el uso por displayTags
	private String page;								//Página a mostrar
	private String sort;								//Columna por la que se ordena
	private String dir;									//Orden de la ordenación
	private String resultadosPorPagina; 				//La cantidad de elementos a mostrar por página
	private String[] idEnvios; // Para los contratos en bloque.
	private ColaBean solicitud;
	
	
	//Parámetros generales de los listar
	HashMap<String, Object> parametrosBusqueda; 		//Se utiliza para asignar los parámetros de búsqueda al objeto DAO

	private ModeloSolicitud modeloSolicitud;
	

	
	///////////////////////////// METODOS ////////////////////////////////////////
	
	/**
	 * Método del action que se ejecutará cuando entremos desde el menú árbol y te seleccionará todos los procesos
	 * que estén en el estado 9, es decir los que estén tratados como recuperables.
	 */
	public String inicio() throws Throwable {
	
		parametrosBusqueda = new HashMap<String, Object>();
		listaSolicitudes = new PaginatedListImpl();
		ModeloSolicitud modeloSolicitudes= new ModeloSolicitud();
		solicitud = new ColaBean(); 
		getSession().put(ConstantesSession.CONSULTA_SOLICITUD, solicitud);
		
		//mantenimientoCamposNavegar();
		// Colocar en la sessión las cosas básicas ya que es la primera vez
					
		getSession().put(ConstantesSession.RESULTADOS_PAGINA, UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO); 
		
		listaSolicitudes.setBaseDAO(modeloSolicitudes);
		listaSolicitudes.getBaseDAO().setParametrosBusqueda(parametrosBusqueda);
		getSession().put(ConstantesSession.LISTA_SOLICITUDES, listaSolicitudes);
		getSession().put(ConstantesSession.CONSULTA_SOLICITUD, solicitud);

		
		return "consultaSolicitudesErrorServicio"; 
	}
	
	
	/**
	 * Función principal de búsqueda, buscará por los parámetros que le pasemos.
	 * @return
	 * @throws Throwable
	 */
	
	public String buscar() throws Throwable {
		
		// mantenimientoCamposNavegar();
		mantenimientoListaSolicitudes();
		if(listaSolicitudes == null) {
			addActionError("La lista de peticiones pendientes se encuentra vacía");
			return "consultaSolicitudesErrorServicio"; 
		}
		
		listaSolicitudes.establecerParametros(getSort(), getDir(), getPage() , getResultadosPorPagina());
		listaSolicitudes.getBaseDAO().setParametrosBusqueda(parametrosBusqueda);

		return "consultaSolicitudesErrorServicio_new"; 
	}
	

	/**
	 * Función de búsqueda para la paginación. Generará la búsqueda limitada por los parámetros de
	 * paginación.
	 * @return
	 */
	public String navegar() {

		mantenimientoCamposNavegar();
		
		//manteniemientoListaSolicitudes();
		if(listaSolicitudes == null){
			addActionError("La lista de solicitudes se encuentra vacía");
			return "consultaSolicitudesErrorServicio"; 
		}
	
		listaSolicitudes.establecerParametros(getSort(), getDir(), getPage(), getResultadosPorPagina());
		listaSolicitudes.getBaseDAO().setParametrosBusqueda(parametrosBusqueda);

		return "consultaSolicitudesErrorServicio"; 
	}


	public String reactivar() throws Throwable{
		
		if(idEnvios!=null && idEnvios.length > 0){
			for (String idSolicitud : idEnvios) {
				
				ResultBean resultBean = new ResultBean();
				
				if(null!=idSolicitud){
					BigDecimal numSolicitud = new BigDecimal(idSolicitud);
					resultBean = getModeloSolicitud().reactivarError(numSolicitud);
				}
				else {
					resultBean.setError(true);
					resultBean.setMensaje("El número de la solicitud es incorrecto.");
				}
				
				if (resultBean.getError()==true)
				addActionError(resultBean.getMensaje());
				else
				addActionMessage(resultBean.getMensaje()); 
				
			}
		}
		else {
			addActionError("Los envíos vienen a nulo");
		}


		return inicio();		
	}
	
	public String finalizarConError() throws Throwable{
		
		if(idEnvios!=null && idEnvios.length > 0){
			for (String idSolicitud : idEnvios) {
				
				ResultBean resultBean = new ResultBean();
				
				if(null!=idSolicitud){
					BigDecimal numSolicitud = new BigDecimal(idSolicitud);
					resultBean = getModeloSolicitud().finalizarError(numSolicitud);
				}
				else {
					resultBean.setError(true);
					resultBean.setMensaje("El número de la solicitud es incorrecto.");
				}
				
				if (resultBean.getError()==true)
				addActionError(resultBean.getMensaje());
				else
				addActionMessage(resultBean.getMensaje()); 
				
			}
		}
		else {
			addActionError("Los envíos vienen a nulo");
		}


		return inicio();		
	}


	//MÉTODOS AUXILIARES//
	
	/**
	 * Método para actualizar los parámetros de la búsqueda. Proceso:
	 * 
	 * -Actualizo los datos de la sesión.
	 * -Paso los parámetros de búsqueda.
	 */
	
	private void mantenimientoListaSolicitudes(){

    	setListaSolicitudes((PaginatedListImpl)getSession().get(ConstantesSession.LISTA_SOLICITUDES));
    	setResultadosPorPagina((String)getSession().get(ConstantesSession.RESULTADOS_PAGINA));
    	actualizaParametros(solicitud);
    	
    	getSession().put(ConstantesSession.RESULTADOS_PAGINA, getResultadosPorPagina());
    	getSession().put(ConstantesSession.CONSULTA_SOLICITUD, solicitud);
    	

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
			
			if(resultadosPorPagina!=null){
	    		getSession().put("resultadosPorPagina", resultadosPorPagina);
	    	}
			
			solicitud = (ColaBean) getSession().get(ConstantesSession.CONSULTA_SOLICITUD);
			if(solicitud!=null){
				setSolicitud(solicitud);	
			}
			PaginatedListImpl listaSolicitudes =(PaginatedListImpl)getSession().get(ConstantesSession.LISTA_SOLICITUDES);
			if(listaSolicitudes != null){
				setListaSolicitudes(listaSolicitudes);		
			}
	    	String resultadosPagina = (String)getSession().get(ConstantesSession.RESULTADOS_PAGINA);
			if(null != resultadosPagina){
				setResultadosPorPagina(resultadosPagina);	
			}
	    	
	    	getSession().put(ConstantesSession.CONSULTA_SOLICITUD, solicitud);
			actualizaParametros(solicitud);
			
		}
		
	/**
	 * Método que actualiza los parámetros de búsqueda a partir de un bean de búsqueda que le pasamos
	 */
	private void actualizaParametros(ColaBean beanActualiza){
		
    	parametrosBusqueda = new HashMap<String, Object>();
//    	if(null!=beanActualiza){
//    	parametrosBusqueda.put(ConstantesSession.NIF_TITULAR_CONSULTA, beanActualiza.getTitular().getNif());
    	
    	BigDecimal estado = beanActualiza.getEstado();
		if (estado != null && estado.equals(Integer.valueOf(String.valueOf("-1")))){
			estado=null;
		}
    	
		String tipoTramite = beanActualiza.getTipoTramite();
		if (tipoTramite != null && tipoTramite.equals("-1")){
			tipoTramite=null;
		}
		
		parametrosBusqueda.put(ConstantesSession.ESTADO_TRAMITE_CONSULTA, estado);
    	parametrosBusqueda.put(ConstantesSession.TIPO_TRAMITE_CONSULTA, tipoTramite);
    	parametrosBusqueda.put(ConstantesSession.ID_TRAMITE, beanActualiza.getIdTramite());
    	parametrosBusqueda.put(ConstantesSession.NUM_COLEGIADO, beanActualiza.getNumColegiado());
    	parametrosBusqueda.put(ConstantesSession.FECHA_ENTRADA_DESDE, beanActualiza.getFechaEntrada().getFechaInicio());
    	parametrosBusqueda.put(ConstantesSession.FECHA_ENTRADA_HASTA, beanActualiza.getFechaEntrada().getFechaFin());
    	parametrosBusqueda.put(ConstantesSession.HORA_ENTRADA, beanActualiza.getHoraEntrada());
    	
	}
	
	public String lista() throws Throwable {
		return buscar();		
	}
	public String getResultadosPorPagina() {
		return resultadosPorPagina;
	}
	public void setResultadosPorPagina(String resultadosPorPagina) {
		this.resultadosPorPagina = resultadosPorPagina;
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
	public HashMap<String, Object> getParametrosBusqueda() {
		return parametrosBusqueda;
	}
	public String[] getIdEnvios() {
		return idEnvios;
	}
	public void setIdEnvios(String[] idEnvios) {
		this.idEnvios = idEnvios;
	}
	public void setParametrosBusqueda(HashMap<String, Object> parametrosBusqueda) {
		this.parametrosBusqueda = parametrosBusqueda;
	}
	public PaginatedListImpl getListaSolicitudes() {
		return listaSolicitudes;
	}
	public void setListaSolicitudes(PaginatedListImpl listaSolicitudes) {
		this.listaSolicitudes = listaSolicitudes;
	}
	public ColaBean getSolicitud() {
		return solicitud;
	}
	public void setSolicitud(ColaBean solicitud) {
		this.solicitud = solicitud;
	}
	public ConsultaTramiteTraficoBean getConsultaTramiteTrafico() {
		return consultaTramiteTrafico;
	}
	public void setConsultaTramiteTrafico(
			ConsultaTramiteTraficoBean consultaTramiteTrafico) {
		this.consultaTramiteTrafico = consultaTramiteTrafico;
	}


	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */

	public ModeloSolicitud getModeloSolicitud() {
		if (modeloSolicitud == null) {
			modeloSolicitud = new ModeloSolicitud();
		}
		return modeloSolicitud;
	}

	public void setModeloSolicitud(ModeloSolicitud modeloSolicitud) {
		this.modeloSolicitud = modeloSolicitud;
	}
	
}
