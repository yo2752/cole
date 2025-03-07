package trafico.tasas.acciones;

import java.util.HashMap;
import java.util.Map;

import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.utiles.PaginatedListImpl;
import general.acciones.ActionBase;
import oegam.constantes.ConstantesSession;
import trafico.modelo.ModeloResumenTasas;
import utilidades.estructuras.FechaFraccionada;

@SuppressWarnings("serial")
public class ResumenTasaAction extends ActionBase{

	private Map<String,Object>session;

	//Campos de búsqueda
	private String numColegiado;
	private String tipoTasa;
	private FechaFraccionada fechaAlta;

	//Parámetros de las peticiones del displayTag
	private PaginatedListImpl listaResumenTasa;		//Lista que se va a mostrar en la vista es de tipo PaginatedList para el uso por displayTags
	private String page;							//Página a mostrar
	private String sort;							//Columna por la que se ordena
	private String dir;								//Orden de la ordenación
	private String resultadosPorPagina;				//La cantidad de elementos a mostrar por página

	//Parámetros generales de los listar
	HashMap<String, Object> parametrosBusqueda;		//Se utiliza para asignar los parámetros de búsqueda al objeto DAO

	@Autowired
	UtilesFecha utilesFecha;

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getTipoTasa() {
		return tipoTasa;
	}

	public void setTipoTasa(String tipoTasa) {
		this.tipoTasa = tipoTasa;
	}

	public FechaFraccionada getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(FechaFraccionada fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public PaginatedListImpl getListaResumenTasa() {
		return listaResumenTasa;
	}

	public void setListaResumenTasa(PaginatedListImpl listaResumenTasa) {
		this.listaResumenTasa = listaResumenTasa;
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

	public HashMap<String, Object> getParametrosBusqueda() {
		return parametrosBusqueda;
	}

	public void setParametrosBusqueda(HashMap<String, Object> parametrosBusqueda) {
		this.parametrosBusqueda = parametrosBusqueda;
	}

	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	public String inicio() throws Throwable {
		return buscar(true);
	}

	public String navegar() {
		asignarCamposDesdeSession();
		if(listaResumenTasa == null){
			return ERROR;
		}

		listaResumenTasa.establecerParametros(getSort(), getDir(), getPage(), getResultadosPorPagina());
		listaResumenTasa.getBaseDAO().setParametrosBusqueda(parametrosBusqueda);
		listaResumenTasa.setHayPaginacion(false);
		return "resumenTasa";
	}

	public String buscar() throws Throwable {
		return buscar(false);
	}

	public String buscar(boolean busquedaInicial) throws Throwable {
		if(busquedaInicial){
			//Inicializaciones
			limpiarCamposSession();
			limpiarCamposDeSession();

			listaResumenTasa = new PaginatedListImpl();

			ModeloResumenTasas modeloTasa = new ModeloResumenTasas();

			listaResumenTasa.setBaseDAO(modeloTasa);
			listaResumenTasa.setHayPaginacion(false);

			// Colocar en la sessión las cosas básicas ya que es la primera vez
			getSession().put("listaResumenTasa", listaResumenTasa);
			getSession().put(ConstantesSession.RESULTADOS_PAGINA, 30);

			parametrosBusqueda = new HashMap<String, Object>();
		} else {
			asignarCamposDesdeSession();

			if(listaResumenTasa == null){
				return ERROR;
			}
		}

		listaResumenTasa.getBaseDAO().setParametrosBusqueda(parametrosBusqueda);
		return "resumenTasa";
	}

	private void asignarCamposDesdeSession(){
		if(resultadosPorPagina!=null){
			getSession().put("resultadosPorPagina", resultadosPorPagina);
		}

		/*
		 * PARÁMETROS DE BÚSQUEDA
		 */
		if(numColegiado!=null){
			getSession().put("numColegiado", numColegiado);
		}
		if(tipoTasa!=null){
			getSession().put("tipoTasa", tipoTasa);
		}
		if(fechaAlta!=null){
			utilesFecha.actualizarFechaFracionada(fechaAlta);
			getSession().put("fechaAlta", fechaAlta);
		}

		//Se inserta directo porque nunca debería ser nulo
		listaResumenTasa = (PaginatedListImpl)getSession().get("listaResumenTasa");
		resultadosPorPagina = (String)getSession().get(ConstantesSession.RESULTADOS_PAGINA);
		numColegiado = (String)getSession().get("numColegiado");
		tipoTasa = (String)getSession().get("tipoTasa");
		fechaAlta = (FechaFraccionada)getSession().get("fechaAlta");

		parametrosBusqueda = new HashMap<>();

		parametrosBusqueda.put("numColegiado", numColegiado);
		parametrosBusqueda.put("tipoTasa", tipoTasa);
		parametrosBusqueda.put("fechaAlta", fechaAlta);
	}

	public void limpiarCamposDeSession(){
		getSession().remove("resultadosPorPagina");
		getSession().remove("numColegiado");
		getSession().remove("tipoTasa");
		getSession().remove("fechaAlta");
	}
}