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
 * Clase action para las solicitudes que se pasan de n�mero de intentos por errores en el servicio. 
 * Se podr�n buscar, reactivar y finalizar las SOLICITUDES erroneas.
 *
 */
public class SolicitudesAction extends ActionBase {

	private ConsultaTramiteTraficoBean consultaTramiteTrafico;
	
	private static final long serialVersionUID = 1L;
	//Par�metros de las peticiones del displayTag
	private PaginatedListImpl listaSolicitudes;//Lista que se va a mostrar en la vista es de tipo PaginatedList para el uso por displayTags
	private String page;								//P�gina a mostrar
	private String sort;								//Columna por la que se ordena
	private String dir;									//Orden de la ordenaci�n
	private String resultadosPorPagina; 				//La cantidad de elementos a mostrar por p�gina
	private String[] idEnvios; // Para los contratos en bloque.
	private ColaBean solicitud;
	
	
	//Par�metros generales de los listar
	HashMap<String, Object> parametrosBusqueda; 		//Se utiliza para asignar los par�metros de b�squeda al objeto DAO

	private ModeloSolicitud modeloSolicitud;
	

	
	///////////////////////////// METODOS ////////////////////////////////////////
	
	/**
	 * M�todo del action que se ejecutar� cuando entremos desde el men� �rbol y te seleccionar� todos los procesos
	 * que est�n en el estado 9, es decir los que est�n tratados como recuperables.
	 */
	public String inicio() throws Throwable {
	
		parametrosBusqueda = new HashMap<String, Object>();
		listaSolicitudes = new PaginatedListImpl();
		ModeloSolicitud modeloSolicitudes= new ModeloSolicitud();
		solicitud = new ColaBean(); 
		getSession().put(ConstantesSession.CONSULTA_SOLICITUD, solicitud);
		
		//mantenimientoCamposNavegar();
		// Colocar en la sessi�n las cosas b�sicas ya que es la primera vez
					
		getSession().put(ConstantesSession.RESULTADOS_PAGINA, UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO); 
		
		listaSolicitudes.setBaseDAO(modeloSolicitudes);
		listaSolicitudes.getBaseDAO().setParametrosBusqueda(parametrosBusqueda);
		getSession().put(ConstantesSession.LISTA_SOLICITUDES, listaSolicitudes);
		getSession().put(ConstantesSession.CONSULTA_SOLICITUD, solicitud);

		
		return "consultaSolicitudesErrorServicio"; 
	}
	
	
	/**
	 * Funci�n principal de b�squeda, buscar� por los par�metros que le pasemos.
	 * @return
	 * @throws Throwable
	 */
	
	public String buscar() throws Throwable {
		
		// mantenimientoCamposNavegar();
		mantenimientoListaSolicitudes();
		if(listaSolicitudes == null) {
			addActionError("La lista de peticiones pendientes se encuentra vac�a");
			return "consultaSolicitudesErrorServicio"; 
		}
		
		listaSolicitudes.establecerParametros(getSort(), getDir(), getPage() , getResultadosPorPagina());
		listaSolicitudes.getBaseDAO().setParametrosBusqueda(parametrosBusqueda);

		return "consultaSolicitudesErrorServicio_new"; 
	}
	

	/**
	 * Funci�n de b�squeda para la paginaci�n. Generar� la b�squeda limitada por los par�metros de
	 * paginaci�n.
	 * @return
	 */
	public String navegar() {

		mantenimientoCamposNavegar();
		
		//manteniemientoListaSolicitudes();
		if(listaSolicitudes == null){
			addActionError("La lista de solicitudes se encuentra vac�a");
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
					resultBean.setMensaje("El n�mero de la solicitud es incorrecto.");
				}
				
				if (resultBean.getError()==true)
				addActionError(resultBean.getMensaje());
				else
				addActionMessage(resultBean.getMensaje()); 
				
			}
		}
		else {
			addActionError("Los env�os vienen a nulo");
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
					resultBean.setMensaje("El n�mero de la solicitud es incorrecto.");
				}
				
				if (resultBean.getError()==true)
				addActionError(resultBean.getMensaje());
				else
				addActionMessage(resultBean.getMensaje()); 
				
			}
		}
		else {
			addActionError("Los env�os vienen a nulo");
		}


		return inicio();		
	}


	//M�TODOS AUXILIARES//
	
	/**
	 * M�todo para actualizar los par�metros de la b�squeda. Proceso:
	 * 
	 * -Actualizo los datos de la sesi�n.
	 * -Paso los par�metros de b�squeda.
	 */
	
	private void mantenimientoListaSolicitudes(){

    	setListaSolicitudes((PaginatedListImpl)getSession().get(ConstantesSession.LISTA_SOLICITUDES));
    	setResultadosPorPagina((String)getSession().get(ConstantesSession.RESULTADOS_PAGINA));
    	actualizaParametros(solicitud);
    	
    	getSession().put(ConstantesSession.RESULTADOS_PAGINA, getResultadosPorPagina());
    	getSession().put(ConstantesSession.CONSULTA_SOLICITUD, solicitud);
    	

	}
	
	
	/**
	 * M�todo para actualizar los par�metros de paginaci�n. Proceso:
	 * 
	 * -Recupero el bean de b�squeda de sesi�n.
	 * -Actualizo los par�metros de b�squeda.
	 * -Actualizo en sesi�n los de la paginaci�n.
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
	 * M�todo que actualiza los par�metros de b�squeda a partir de un bean de b�squeda que le pasamos
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
