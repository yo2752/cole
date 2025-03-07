package org.gestoresmadrid.oegam2.controller.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.favoritos.model.vo.ContratoFavoritosVO;
import org.gestoresmadrid.oegam2comun.favoritos.model.service.ServicioFavoritos;
import org.gestoresmadrid.oegam2comun.favoritos.views.beans.FavoritosBean;
import org.gestoresmadrid.oegamComun.acceso.model.bean.MenuFuncionBean;
import org.gestoresmadrid.oegamComun.acceso.model.bean.UsuarioAccesoBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import general.acciones.ScriptFeaturesBean;
import general.acciones.TipoPosicionScript;
import general.acciones.TipoScript;
import net.sf.navigator.menu.MenuRepository;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.GestorArbol;
import utilidades.web.Menu;

public class FavoritosAction extends ActionBase {

	private static final long serialVersionUID = 1L;
	private static final String GDOC_CSS = "css/gdoc.css";
	private static final ILoggerOegam log = LoggerOegam.getLogger(FavoritosAction.class);

	private FavoritosBean favoritosBean;
	private String idFavorito;
	private String codigoFuncion;

	private List<ContratoFavoritosVO> listaFavoritos;

	@Autowired
	private  ServicioFavoritos servicioFavoritos;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	Conversor conversor;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String inicio() {
		cargarScriptsYCSS();
		BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
		// Se carga la tabla con la lista de favoritos
		setListaFavoritos(servicioFavoritos.recuperarFavoritos(idContrato));
		return SUCCESS;
	}

	public String guardar() {
		cargarScriptsYCSS();
		BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
		// Control errores
		if (favoritosBean.getCodigoAplicacion() == null || favoritosBean.getCodigoAplicacion().isEmpty()) {
			addActionError("Debe seleccionar algún elemento en el combo Módulo.");
			setListaFavoritos(servicioFavoritos.recuperarFavoritos(idContrato));
			return SUCCESS;
		}
		if (favoritosBean.getCodigoFuncion() == null || favoritosBean.getCodigoFuncion().isEmpty()) {
			addActionError("Debe seleccionar algún elemento en el combo Lista de páginas.");
			setListaFavoritos(servicioFavoritos.recuperarFavoritos(idContrato));
			return SUCCESS;
		}
		// Se recupera los favoritos en BBDD para comprobar que no se agregan favoritos duplicados y ni más de 6 favoritos
		setListaFavoritos(servicioFavoritos.recuperarFavoritos(idContrato));
		// Mas de 6 favoritos
		if (listaFavoritos != null && listaFavoritos.size() >= 6) {
			addActionError("No se pueden agregar más de 6 favoritos por usuario.");
			return SUCCESS;
		} // Posibles favoritos duplicados
		else if (listaFavoritos != null && !listaFavoritos.isEmpty()) {
			for (ContratoFavoritosVO lista : listaFavoritos) {
				if (favoritosBean.getCodigoFuncion().equals(lista.getFuncion().getId().getCodigoFuncion())) {
					addActionError("No se pueden guardar favoritos duplicados.");
					return SUCCESS;
				}
			}
		}
		// Se guardan los favoritos
		servicioFavoritos.guardarFavoritos(favoritosBean, idContrato);
		addActionMessage("El favorito se ha guardado correctamente.");
		List<Menu> favoritos = (List<Menu>) Claves.getObjetoDeContextoSesion("favoritos");
		if (favoritos == null || favoritos.isEmpty()) {
			favoritos =  new ArrayList<>();
		}
		if ("SI".equals(gestorPropiedades.valorPropertie("nuevo.gestorAccesos"))) {
			UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
			for (MenuFuncionBean menu : usuarioAccesoBean.getListaMenu()) {
				if (menu.getCodigoFuncion().equals(favoritosBean.getCodigoFuncion()) 
						&& menu.getCodigoAplicacion().equals(favoritosBean.getCodigoAplicacion())){
					favoritos.add(conversor.transform(menu, Menu.class));
				}
			}
		} else { // Se actualiza el menú de favoritos
			GestorArbol ga = (GestorArbol)Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
			List<Menu> menus = ga.get_listaMenus();
			for (Menu menu : menus) {
				if (favoritosBean.getCodigoFuncion().equals(menu.getCodigo_funcion()) && favoritosBean.getCodigoAplicacion().equals(menu.getCodigo_aplicacion())) {
					favoritos.add(menu);
				}
			}
		}
		Claves.setObjetoDeContextoSesion("favoritos", favoritos);
		// Se actualiza la tabla de la pantalla
		setListaFavoritos(servicioFavoritos.recuperarFavoritos(idContrato));
		return SUCCESS;
	}

	public String eliminar() {
		cargarScriptsYCSS();
		// Eliminacion del favorito seleccionado en la tabla
		String indices = getCodigoFuncion();
		String[] codSeleccionados = indices.split("-");
		ResultBean resultadoUnitarioBorrar = new ResultBean();
		try {
			for (String valorCheck : codSeleccionados) {
				String [] arrayString = valorCheck.split(",");
				// Se recupera para actualizar los favoritos del menu
				String codigoFuncion = arrayString[0];
				String favorito = arrayString[1];
				Long idfavorito = Long.parseLong(favorito);

				resultadoUnitarioBorrar = servicioFavoritos.eliminarFavorito(idfavorito);

				if (resultadoUnitarioBorrar.getError() != null && resultadoUnitarioBorrar.getError() == true) {
					addActionError(resultadoUnitarioBorrar.getMensaje());
				} else {
					addActionMessage("El favorito fue borrado correctamente.");
					List<Menu> favoritos = (List<Menu>) Claves.getObjetoDeContextoSesion("favoritos");
					if(favoritos == null || favoritos.isEmpty()){
						favoritos =  new ArrayList<>();
					}
					if("SI".equals(gestorPropiedades.valorPropertie("nuevo.gestorAccesos"))){
						UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
						for(MenuFuncionBean menu : usuarioAccesoBean.getListaMenu()){
							if(codigoFuncion.equals(menu.getCodigoFuncion())){
								favoritos.add(conversor.transform(menu, Menu.class));
								break;
							}
						}
					} else {
						// Se actualiza el menú de favoritos
						GestorArbol ga = (GestorArbol)Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
						List<Menu> menus = ga.get_listaMenus();
						for (Menu menu : menus) {
							if (codigoFuncion.equals(menu.getCodigo_funcion())) {
								favoritos.remove(menu);
								break;
							}
						}
					}
					Claves.setObjetoDeContextoSesion("favoritos", favoritos);
				}
			}
		}catch(Exception e){
			log.error("Se ha producido un error no experado al eliminar un favorito: " + utilesColegiado.getNumColegiadoSession(), e);
			addActionError("Se ha producido un error no experado al borrar los favoritos");
		}
		BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
		// Se actualiza la tabla
		setListaFavoritos(servicioFavoritos.recuperarFavoritos(idContrato));
		return SUCCESS;
	}

	// Se recupera la listaModulos para cargarlos en el combo Modulos
	@SuppressWarnings("rawtypes")
	public List getListaModulos() {
		MenuRepository menuRepository=(MenuRepository) Claves.getObjetoDeContextoSesion(Claves.CLAVE_ARBOL);
		if (menuRepository != null) {
			return menuRepository.getTopMenus();
		} else {
			return Collections.EMPTY_LIST;
		}
	}

	protected void cargarScriptsYCSS() {
		inicializarScripts();
		ScriptFeaturesBean css = new ScriptFeaturesBean();
		css.setName(GDOC_CSS);
		css.setPosicion(TipoPosicionScript.TOP);
		css.setTipo(TipoScript.CSS);
		addScripts.getScripts().add(css);
	}

	public FavoritosBean getFavoritosBean() {
		return favoritosBean;
	}

	public void setFavoritosBean(FavoritosBean favoritosBean) {
		this.favoritosBean = favoritosBean;
	}

	public List<ContratoFavoritosVO> getListaFavoritos() {
		return listaFavoritos;
	}

	public void setListaFavoritos(List<ContratoFavoritosVO> listaFavoritos) {
		this.listaFavoritos = listaFavoritos;
	}

	public String getIdFavorito() {
		return codigoFuncion;
	}

	public void setIdFavorito(String idFavorito) {
		this.codigoFuncion = idFavorito;
	}
	
	public String getCodigoFuncion() {
		return codigoFuncion;
	}

	public void setCodigoFuncion(String codigoFuncion) {
		this.codigoFuncion = codigoFuncion;
	}

}