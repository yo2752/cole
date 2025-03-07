package org.gestoresmadrid.oegamComun.acceso.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.favoritos.model.vo.ContratoFavoritosVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;

import net.sf.navigator.menu.MenuRepository;
import utilidades.web.Menu;
import utilidades.web.UserAuthorizations;

public class ResultadoAccesoBean implements Serializable{

	private static final long serialVersionUID = -5294538677159903362L;
	
	boolean error;
	String mensaje;
	UsuarioAccesoBean usuarioAcceso;
	UsuarioVO usuario;
	private List<ContratoVO> listaContratos;
	private List<ContratoFavoritosVO> listaFavoritosContrato;
	private List<Menu> listaFavoritos;
	private List<MenuFuncionBean> listaMenu;
	private List<PermisoUsuarioBean> listaPermisos;
	MenuRepository menuArbol;
	private List<String> listaUrlsAcceso;
	UserAuthorizations userAuthorizations;
	
	public void addListaFavoritos(Menu menu){
		if(listaFavoritos == null || listaFavoritos.isEmpty()){
			listaFavoritos = new ArrayList<>();
		}
		listaFavoritos.add(menu);
	}
	
	public void addListaUrlsAcceso(String url){
		if(listaUrlsAcceso == null || listaUrlsAcceso.isEmpty()){
			listaUrlsAcceso = new ArrayList<>();
		}
		listaUrlsAcceso.add(url);
	}
	
	public void addListaMenu(MenuFuncionBean menu){
		if(listaMenu == null || listaMenu.isEmpty()){
			listaMenu = new ArrayList<>();
		}
		listaMenu.add(menu);
	}
	
	public void addListaPermisos(PermisoUsuarioBean permiso){
		if(listaPermisos == null || listaPermisos.isEmpty()){
			listaPermisos = new ArrayList<>();
		}
		listaPermisos.add(permiso);
	}
	
	public List<Menu> getListaFavoritos() {
		return listaFavoritos;
	}

	public void setListaFavoritos(List<Menu> listaFavoritos) {
		this.listaFavoritos = listaFavoritos;
	}

	public List<ContratoFavoritosVO> getListaFavoritosContrato() {
		return listaFavoritosContrato;
	}

	public void setListaFavoritosContrato(List<ContratoFavoritosVO> listaFavoritosContrato) {
		this.listaFavoritosContrato = listaFavoritosContrato;
	}

	public List<ContratoVO> getListaContratos() {
		return listaContratos;
	}

	public void setListaContratos(List<ContratoVO> listaContratos) {
		this.listaContratos = listaContratos;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public ResultadoAccesoBean(boolean error) {
		super();
		this.error = error;
	}
	
	public boolean getError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public UsuarioAccesoBean getUsuarioAcceso() {
		return usuarioAcceso;
	}

	public void setUsuarioAcceso(UsuarioAccesoBean usuarioAcceso) {
		this.usuarioAcceso = usuarioAcceso;
	}

	public List<PermisoUsuarioBean> getListaPermisos() {
		return listaPermisos;
	}

	public void setListaPermisos(List<PermisoUsuarioBean> listaPermisos) {
		this.listaPermisos = listaPermisos;
	}

	public List<MenuFuncionBean> getListaMenu() {
		return listaMenu;
	}

	public void setListaMenu(List<MenuFuncionBean> listaMenu) {
		this.listaMenu = listaMenu;
	}

	public MenuRepository getMenuArbol() {
		return menuArbol;
	}

	public void setMenuArbol(MenuRepository menuArbol) {
		this.menuArbol = menuArbol;
	}

	public List<String> getListaUrlsAcceso() {
		return listaUrlsAcceso;
	}

	public void setListaUrlsAcceso(List<String> listaUrlsAcceso) {
		this.listaUrlsAcceso = listaUrlsAcceso;
	}

	public UserAuthorizations getUserAuthorizations() {
		return userAuthorizations;
	}

	public void setUserAuthorizations(UserAuthorizations userAuthorizations) {
		this.userAuthorizations = userAuthorizations;
	}
	
}
