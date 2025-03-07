package org.gestoresmadrid.oegamDocBase.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DocumentoBaseBean implements Serializable{

	private static final long serialVersionUID = -2096399747012363127L;
	
	private List<TramiteBaseBean> listaTramitesBase;
	private List<TramiteBaseBean> listaTramitesBaseSimultaneas;
	private List<BigDecimal> listaTramites;
	
	public void addListaTramitesBaseSimultaneas(TramiteBaseBean tramite){
		if (listaTramitesBaseSimultaneas == null){
			listaTramitesBaseSimultaneas = new ArrayList<TramiteBaseBean>();
		}
		listaTramitesBaseSimultaneas.add(tramite);
	}
	
	public void addListaTramitesBase(TramiteBaseBean tramite){
		if (listaTramitesBase == null){
			listaTramitesBase = new ArrayList<TramiteBaseBean>();
		}
		listaTramitesBase.add(tramite);
	}
	
	public void addListaTramites(BigDecimal tramite){
		if (listaTramites == null){
			listaTramites = new ArrayList<BigDecimal>();
		}
		listaTramites.add(tramite);
	}
	
	public List<BigDecimal> getListaTramites() {
		return listaTramites;
	}
	public void setListaTramites(List<BigDecimal> listaTramites) {
		this.listaTramites = listaTramites;
	}

	public List<TramiteBaseBean> getListaTramitesBase() {
		return listaTramitesBase;
	}

	public void setListaTramitesBase(List<TramiteBaseBean> listaTramitesBase) {
		this.listaTramitesBase = listaTramitesBase;
	}

	public List<TramiteBaseBean> getListaTramitesBaseSimultaneas() {
		return listaTramitesBaseSimultaneas;
	}

	public void setListaTramitesBaseSimultaneas(List<TramiteBaseBean> listaTramitesBaseSimultaneas) {
		this.listaTramitesBaseSimultaneas = listaTramitesBaseSimultaneas;
	}

}