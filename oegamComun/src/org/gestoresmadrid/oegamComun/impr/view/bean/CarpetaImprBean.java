package org.gestoresmadrid.oegamComun.impr.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CarpetaImprBean implements Serializable{

	private static final long serialVersionUID = 6256305786255407307L;
	
	String carpeta;
	String tipoImpr;
	String tipoVehiculoImpr;
	String tipoTramite;
	Long idContrato;
	List<ImprExpBean> listaImprCarpeta;
	
	public CarpetaImprBean(String carpeta, String tipoImpr, String tipoVehiculoImpr, String tipoTramite,Long idContrato) {
		super();
		this.carpeta = carpeta;
		this.tipoImpr = tipoImpr;
		this.tipoVehiculoImpr = tipoVehiculoImpr;
		this.tipoTramite = tipoTramite;
		this.idContrato = idContrato;
	}
	
	public void addListaImprCapeta(ImprExpBean impr){
		if(listaImprCarpeta == null || listaImprCarpeta.isEmpty()){
			listaImprCarpeta = new ArrayList<>();
		}
		listaImprCarpeta.add(impr);
	}
	
	public String getCarpeta() {
		return carpeta;
	}
	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}
	public String getTipoImpr() {
		return tipoImpr;
	}
	public void setTipoImpr(String tipoImpr) {
		this.tipoImpr = tipoImpr;
	}
	public String getTipoVehiculoImpr() {
		return tipoVehiculoImpr;
	}
	public void setTipoVehiculoImpr(String tipoVehiculoImpr) {
		this.tipoVehiculoImpr = tipoVehiculoImpr;
	}
	public String getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public List<ImprExpBean> getListaImprCarpeta() {
		return listaImprCarpeta;
	}
	public void setListaImprCarpeta(List<ImprExpBean> listaImprCarpeta) {
		this.listaImprCarpeta = listaImprCarpeta;
	}
	public Long getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}
}
