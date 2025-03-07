package utilidades.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.opensymphony.xwork2.ActionContext;

import net.sf.navigator.menu.MenuRepository;
import utilidades.basedatos.GestorBD;
import utilidades.basedatos.GestorBDFactory;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion.EnumError;


public class GestorArbol {
	
	private List<Contrato>			_contratos=null;
	private List<String>            _funcionesAutorizadas=null;
	private GestorBD				_gestorBD=null;
	private BigDecimal				_id_contrato=null;
	private List<Menu>			 _listaMenus=null;
	private List<String>			_menusAutorizados=null;
	private Boolean					_tienePermisoMantenimiento=false;
	private Usuario					_usuario=null; 
	private List<Aplicacion> 		listaAplicaciones	=null;
	private Boolean                 tienePermisoAdministracion=false;
	private Boolean                 tienePermisoColegio=false;
	private Boolean                 tienePermisoEspecial=false;
	private static final ILoggerOegam log = LoggerOegam.getLogger(GestorArbol.class);
	private UserAuthorizations		userAuthorizations = null;
	
	public GestorArbol(List<Contrato> contratos, BigDecimal idContrato) throws OegamExcepcion{
		
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		_gestorBD= GestorBDFactory.getInstance().getGestorBDGenerico(); 
		_contratos = contratos;
		_id_contrato = idContrato;
	}
	
	
	public GestorArbol(String nif, BigDecimal idContratoSeleccionado) throws OegamExcepcion{
		
		log.info("creamos arbol para el nif " + nif); 
		
		if(null==nif || nif.trim().length()==0){
			throw new OegamExcepcion(EnumError.error_00057);
		}
		_id_contrato = idContratoSeleccionado;
	}
	
	public void getArbol(Usuario user, List<Menu> listaMenus, List<Aplicacion> aplicaciones)  throws Throwable {
		
		if (_usuario == null){
			_usuario = user;
		}
		
		if (_listaMenus == null){
			_listaMenus = listaMenus;
		}
		
		MenuRepository menuArbol=new MenuRepository();
		Map<?,?> application=ActionContext.getContext().getApplication();
		
		_menusAutorizados=new ArrayList<String>();
		_funcionesAutorizadas=new ArrayList<String>();
		listaAplicaciones = aplicaciones;
		
		MenuRepository defaultRepository = (MenuRepository) application.get(MenuRepository.MENU_REPOSITORY_KEY);
		menuArbol.setDisplayers(defaultRepository.getDisplayers());
		
		HashMap<String,String> listaRaices=new HashMap<String,String>();

		MiMenuComponent mc=null;
		MiMenuComponent parentMenu=null;
			
		for(Menu menu:_listaMenus) {
			if(menu.getTipo().equalsIgnoreCase("M"))
				{
				 addUrlMenusAutorizados(menu);
		    
				if(!listaRaices.containsKey(menu.getCodigo_aplicacion()))
					{
					for(Aplicacion apli:listaAplicaciones)
						{
						if(menu.getCodigo_aplicacion().equals(apli.getCodigo_aplicacion()))
							{
						    mc = new MiMenuComponent();
						    mc.setName(menu.getCodigo_aplicacion());
						    mc.setTitle(apli.getDesc_aplicacion());
							listaRaices.put(menu.getCodigo_aplicacion(), apli.getDesc_aplicacion());
							menuArbol.addMenu(mc);
							break;
							}
						}
					}
			    mc = new MiMenuComponent();
			    mc.setName(menu.getCodigo_funcion());
			    mc.setTitle(menu.getDesc_funcion());
			    
			    mc.setLocation(menu.getUrl());
			   
			    if (menu.getCodigo_funcion_padre() == null) 
			    	{
			    	parentMenu = (MiMenuComponent)menuArbol.getMenu(menu.getCodigo_aplicacion());
			    	}
			    else{
			        parentMenu = (MiMenuComponent)menuArbol.getMenu(menu.getCodigo_funcion_padre());
			    	}
		    
		        if (parentMenu == null) 
		        	{
		            parentMenu = new MiMenuComponent();
		            parentMenu.setName(menu.getCodigo_funcion_padre());
		            /* INICIO Mantis 0011687 */
		            // Se pone el title a un valor diferente a nulo, ya que si no en el método
		            // "java.util.PropertyResourceBundle.handleGetObject(String key)" se lanza un NullPointerException si key es nula 
		            parentMenu.setTitle("");
		            /* FIN Mantis 0011687 */
		            menuArbol.addMenu(parentMenu);
		        	}

		        mc.setParent(parentMenu);
		        menuArbol.addMenu(mc);
			}//fin de if(menu.getTipo().equalsIgnoreCase("M"))
			
			else if(menu.getTipo().equalsIgnoreCase("E"))
				{
					if(menu.getCodigo_funcion().equalsIgnoreCase("OA110"))
						{
						setTienePermisoAdministracion(true);
						}
					if(menu.getCodigo_funcion().equalsIgnoreCase("OA001"))
						{
						setTienePermisoColegio(true);
						}
					if(menu.getCodigo_funcion().equalsIgnoreCase("OP001"))
					{
					setTienePermisoEspecial(true);
					}
//					_listaFunciones.add(menu);
				}
			}//for de lista de menu por contratos
		
			Claves.setObjetoDeContextoSesion(utilidades.mensajes.Claves.CLAVE_ARBOL, menuArbol);
	}
	
	
	public void getArbolProcesos(BigDecimal id_contrato, String tipoPermiso, List<Menu> listaMenus,Aplicacion aplicacionProcesos)  throws Throwable {
		MenuRepository menuArbol=new MenuRepository();
		Map<?,?> application=ActionContext.getContext().getApplication();
		try {
			_menusAutorizados=new ArrayList<String>();
			_funcionesAutorizadas=new ArrayList<String>();
			_listaMenus = new ArrayList<Menu>();
			listaAplicaciones = new ArrayList<Aplicacion>();
			
			listaAplicaciones.add(aplicacionProcesos);
			MenuRepository defaultRepository = (MenuRepository) application.get(MenuRepository.MENU_REPOSITORY_KEY);
			menuArbol.setDisplayers(defaultRepository.getDisplayers());
			
			MiMenuComponent mc=null;
			MiMenuComponent parentMenu=null;
			
			mc = new MiMenuComponent();
		    mc.setName(aplicacionProcesos.getCodigo_aplicacion());
		    mc.setTitle(aplicacionProcesos.getDesc_aplicacion());
			menuArbol.addMenu(mc);
			
			Menu menuApli = new Menu();
			menuApli.setCodigo_aplicacion(aplicacionProcesos.getCodigo_aplicacion());
			menuApli.setDesc_funcion(aplicacionProcesos.getDesc_aplicacion());
			menuApli.setNivel(BigDecimal.ONE);
			menuApli.setOrden(BigDecimal.ONE);
			menuApli.setTipo("M");
			_listaMenus.add(menuApli);
			for(Menu menuBBDD : listaMenus){
				addUrlMenusAutorizados(menuBBDD);
			    mc = new MiMenuComponent();
			    mc.setName(menuBBDD.getCodigo_funcion());
			    mc.setTitle(menuBBDD.getDesc_funcion());
			    
			    mc.setLocation(menuBBDD.getUrl());
			    
			    if (menuBBDD.getCodigo_funcion_padre() == null) {
			    	parentMenu = (MiMenuComponent)menuArbol.getMenu(menuBBDD.getCodigo_aplicacion());
			    } else{
			        parentMenu = (MiMenuComponent)menuArbol.getMenu(menuBBDD.getCodigo_funcion_padre());
			    }
			    
			    if (parentMenu == null) {
		            parentMenu = new MiMenuComponent();
		            parentMenu.setName(menuBBDD.getCodigo_funcion_padre());
		            parentMenu.setTitle("");
		            menuArbol.addMenu(parentMenu);
		        }

		        mc.setParent(parentMenu);
		        menuArbol.addMenu(mc);
		        _listaMenus.add(menuBBDD);
			}
			
		} catch (Exception e) {
			throw new OegamExcepcion(e, EnumError.error_00001);
		}
		
		Claves.setObjetoDeContextoSesion(utilidades.mensajes.Claves.CLAVE_ARBOL, menuArbol);
	}
	

	private void addUrlMenusAutorizados(Menu menu) {
		if(null!=menu.getUrl())	{
			_menusAutorizados.add(menu.getUrl());
		}
	}
	
		
	public void suprimirContratoSinPermisos(List<Object> contratos,BigDecimal idContrato) {
		
		Iterator iter = contratos.iterator(); 
		
		while (iter.hasNext())
			{
			
			Contrato contrato = (Contrato) iter.next(); 
			if (contrato.getId_contrato().equals(idContrato))
				{
				iter.remove(); 
				}
			}
			
		}
		
	

	public List<String> getFuncionesAutorizadas() {
		return _funcionesAutorizadas;
	}
	
	public List<Contrato> getContratos() {
		return _contratos;
	}
	
	public GestorBD getGestorBD() {
		return _gestorBD;
	}

	public BigDecimal getIdContrato() {
		return _id_contrato; 
	}

	
	public List<String> getMenusAutorizados() {
		return _menusAutorizados;
	}

	public Boolean getTienePermisoAdministracion() {
		return tienePermisoAdministracion;
	}
	
	public Boolean getTienePermisoColegio() {
		return tienePermisoColegio;
	}
	
	
	public Usuario getUsuario() {
		return _usuario;
	}
	
	
	public void setFuncionesAutorizadas(List<String> funcionesAutorizadas) {
		_funcionesAutorizadas = funcionesAutorizadas;
	}
	
	public void setGestorBD(GestorBD gestorBD) {
		_gestorBD = gestorBD;
	}

	public void setIdContrato(BigDecimal contrato) {
		_id_contrato = contrato;
	}

	public void setTienePermisoAdministracion(Boolean tienePermisoAdministracion) {
		this.tienePermisoAdministracion = tienePermisoAdministracion;
	}
	public void setTienePermisoColegio(Boolean tienePermisoColegio) {
		this.tienePermisoColegio = tienePermisoColegio;
	}

	public Boolean getTienePermisoEspecial() {
		return tienePermisoEspecial;
	}

	public void setTienePermisoEspecial(Boolean tienePermisoEspecial) {
		this.tienePermisoEspecial = tienePermisoEspecial;
	}

	public UserAuthorizations getUserAuthorizations() {
		return userAuthorizations;
	}

	public void setUserAuthorizations(UserAuthorizations userAuthorizations) {
		this.userAuthorizations = userAuthorizations;
	}
	
	public List<Menu> get_listaMenus() {
		return _listaMenus;
	}	

	public void getArbolIntegracionSiga(Usuario user, List<Menu> listaMenus, List<Aplicacion> aplicaciones)  throws Throwable {
		
		if (_usuario == null){
			_usuario = user;
		}
		
		if (_listaMenus == null){
			_listaMenus = listaMenus;
		}
		
		MenuRepository menuArbol=new MenuRepository();
		
		_menusAutorizados=new ArrayList<String>();
		_funcionesAutorizadas=new ArrayList<String>();
		listaAplicaciones = aplicaciones;
		
		HashMap<String,String> listaRaices=new HashMap<String,String>();

		MiMenuComponent mc=null;
		MiMenuComponent parentMenu=null;
			
		for(Object funcion:_listaMenus)
			{
			Menu menu=(Menu)funcion;
			if(menu.getTipo().equalsIgnoreCase("M"))
				{
				 addUrlMenusAutorizados(menu);
		    
				if(!listaRaices.containsKey(menu.getCodigo_aplicacion()))
					{
					for(Aplicacion apli:listaAplicaciones)
						{
						if(menu.getCodigo_aplicacion().equals(apli.getCodigo_aplicacion()))
							{
						    mc = new MiMenuComponent();
						    mc.setName(menu.getCodigo_aplicacion());
						    mc.setTitle(apli.getDesc_aplicacion());
							listaRaices.put(menu.getCodigo_aplicacion(), apli.getDesc_aplicacion());
							menuArbol.addMenu(mc);
							break;
							}
						}
					}
			    mc = new MiMenuComponent();
			    mc.setName(menu.getCodigo_funcion());
			    mc.setTitle(menu.getDesc_funcion());
			    
			    mc.setLocation(menu.getUrl());
			   
			    if (menu.getCodigo_funcion_padre() == null) 
			    	{
			    	parentMenu = (MiMenuComponent)menuArbol.getMenu(menu.getCodigo_aplicacion());
			    	}
			    else{
			        parentMenu = (MiMenuComponent)menuArbol.getMenu(menu.getCodigo_funcion_padre());
			    	}
		    
		        if (parentMenu == null) 
		        	{
		            parentMenu = new MiMenuComponent();
		            parentMenu.setName(menu.getCodigo_funcion_padre());
		            menuArbol.addMenu(parentMenu);
		        	}

		        mc.setParent(parentMenu);
		        menuArbol.addMenu(mc);
			}//fin de if(menu.getTipo().equalsIgnoreCase("M"))
			
			else if(menu.getTipo().equalsIgnoreCase("E"))
				{
					if(menu.getCodigo_funcion().equalsIgnoreCase("OA110"))
						{
						setTienePermisoAdministracion(true);
						}
					if(menu.getCodigo_funcion().equalsIgnoreCase("OA001"))
						{
						setTienePermisoColegio(true);
						}
					if(menu.getCodigo_funcion().equalsIgnoreCase("OP001"))
					{
					setTienePermisoEspecial(true);
					}
//					_listaFunciones.add(menu);
				}
			}//for de lista de menu por contratos
		
			Claves.setObjetoDeContextoSesion(utilidades.mensajes.Claves.CLAVE_ARBOL, menuArbol);
	}

	public void set_listaMenus(List<Menu> _listaMenus) {
		this._listaMenus = _listaMenus;
	}
}
