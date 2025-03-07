package org.gestoresmadrid.oegamComun.acceso.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.beans.DatoMaestroBean;

import utilidades.web.Menu;
import utilidades.web.UserAuthorizations;

public class UsuarioAccesoBean implements Serializable{

	private static final long serialVersionUID = 1L;

	String nif;
	Long idUsuario;
	List<Long> listaIdsContratos;
	Long idContrato;
	String nombreProvinciaContrato;
	String idProvinciaContrato;
	String colegio;
	String idJefatura;
	String numColegiado;
	String aliasColegiado;
	String numColegiadoNacional;
	String razonSocialContrato;
	String cifContrato;
	DetalleUsuarioBean detalleUsuario;
	List<MenuFuncionBean> listaMenu;
	List<PermisoUsuarioBean> listaPermisos;
	List<Menu> listaFavoritos;
	Boolean tienePermisoAdministracion;
	Boolean tienePermisoColegio;
	Boolean tienePermisoEspecial;
	List<DatoMaestroBean> listaComboContratos;
	UserAuthorizations userAuthorizations;

	public UsuarioAccesoBean() {
		super();
		this.tienePermisoAdministracion = Boolean.FALSE;
		this.tienePermisoColegio = Boolean.FALSE;
		this.tienePermisoEspecial = Boolean.FALSE;
	}

	
	public List<MenuFuncionBean> getListaMenu() {
		return listaMenu;
	}

	public void setListaMenu(List<MenuFuncionBean> listaMenu) {
		this.listaMenu = listaMenu;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getRazonSocialContrato() {
		return razonSocialContrato;
	}

	public void setRazonSocialContrato(String razonSocialContrato) {
		this.razonSocialContrato = razonSocialContrato;
	}

	public String getCifContrato() {
		return cifContrato;
	}

	public void setCifContrato(String cifContrato) {
		this.cifContrato = cifContrato;
	}

	public DetalleUsuarioBean getDetalleUsuario() {
		return detalleUsuario;
	}

	public void setDetalleUsuario(DetalleUsuarioBean detalleUsuario) {
		this.detalleUsuario = detalleUsuario;
	}

	public void addIdContratoToListaIdsContratos(Long idContrato){
		if(listaIdsContratos == null || listaIdsContratos.isEmpty()){
			listaIdsContratos = new ArrayList<>();
		}
		listaIdsContratos.add(idContrato);
	}
	
	public void addListaComboContratos(Long idContrato, String descripcion){
		if(listaComboContratos == null || listaComboContratos.isEmpty()){
			listaComboContratos = new ArrayList<>();
		}
		DatoMaestroBean contrato = new DatoMaestroBean();
		contrato.setCodigo(idContrato.toString());
		contrato.setDescripcion(descripcion);
		listaComboContratos.add(contrato);
	}
	
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public List<Long> getListaIdsContratos() {
		return listaIdsContratos;
	}
	public void setListaIdsContratos(List<Long> listaIdsContratos) {
		this.listaIdsContratos = listaIdsContratos;
	}
	public Long getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public List<PermisoUsuarioBean> getListaPermisos() {
		return listaPermisos;
	}

	public void setListaPermisos(List<PermisoUsuarioBean> listaPermisos) {
		this.listaPermisos = listaPermisos;
	}

	public List<Menu> getListaFavoritos() {
		return listaFavoritos;
	}

	public void setListaFavoritos(List<Menu> listaFavoritos) {
		this.listaFavoritos = listaFavoritos;
	}

	public Boolean getTienePermisoAdministracion() {
		return tienePermisoAdministracion;
	}

	public void setTienePermisoAdministracion(Boolean tienePermisoAdministracion) {
		this.tienePermisoAdministracion = tienePermisoAdministracion;
	}

	public Boolean getTienePermisoColegio() {
		return tienePermisoColegio;
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


	public String getNumColegiadoNacional() {
		return numColegiadoNacional;
	}


	public void setNumColegiadoNacional(String numColegiadoNacional) {
		this.numColegiadoNacional = numColegiadoNacional;
	}


	public List<DatoMaestroBean> getListaComboContratos() {
		return listaComboContratos;
	}


	public void setListaComboContratos(List<DatoMaestroBean> listaComboContratos) {
		this.listaComboContratos = listaComboContratos;
	}


	public UserAuthorizations getUserAuthorizations() {
		return userAuthorizations;
	}


	public void setUserAuthorizations(UserAuthorizations userAuthorizations) {
		this.userAuthorizations = userAuthorizations;
	}


	public String getIdJefatura() {
		return idJefatura;
	}


	public void setIdJefatura(String idJefatura) {
		this.idJefatura = idJefatura;
	}


	public String getAliasColegiado() {
		return aliasColegiado;
	}


	public void setAliasColegiado(String aliasColegiado) {
		this.aliasColegiado = aliasColegiado;
	}


	public String getNombreProvinciaContrato() {
		return nombreProvinciaContrato;
	}


	public void setNombreProvinciaContrato(String nombreProvinciaContrato) {
		this.nombreProvinciaContrato = nombreProvinciaContrato;
	}


	public String getIdProvinciaContrato() {
		return idProvinciaContrato;
	}


	public void setIdProvinciaContrato(String idProvinciaContrato) {
		this.idProvinciaContrato = idProvinciaContrato;
	}


	public String getColegio() {
		return colegio;
	}


	public void setColegio(String colegio) {
		this.colegio = colegio;
	}
	
}
