package org.gestoresmadrid.oegamComun.impr.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.enumerados.TipoImpr;

public class ResultadoImprBean implements Serializable{

	private static final long serialVersionUID = 660941426285694614L;

	private Boolean error;
	private String mensaje;
	private String codigoError;
	private List<String> listaMensajes;
	private ResumenImprBean resumen;
	List<String> listaMensajeOk;
	List<String> listaMensajeError;
	List<CarpetaImprBean> listaEncarpetado;
	Boolean existeListaMensajeOk;
	Boolean existeListaMensajeError;
	List<BigDecimal> listaIdOk;
	Long idContrato;
	
	
	public void aniadirCarpeta(String carpeta, String tipoImpr, String tipoTramite, String tipoVehiculoImpr, BigDecimal numExpediente, Long idImpr, Long idContrato) {
		Boolean existeCarpetaImpr = Boolean.FALSE;
		if(listaEncarpetado != null && !listaEncarpetado.isEmpty()) {
			for(CarpetaImprBean carpetaImprBean : listaEncarpetado) {
				if(carpetaImprBean.getCarpeta().equals(carpeta)
					&& carpetaImprBean.getTipoImpr().equals(tipoImpr)
					&& carpetaImprBean.getIdContrato().equals(idContrato)
					&& carpetaImprBean.getTipoTramite().equals(tipoTramite)) {
					if(TipoImpr.Ficha_Tecnica.getValorEnum().equals(tipoImpr)) {
						if(tipoVehiculoImpr.equals(carpetaImprBean.getTipoVehiculoImpr())) {
							carpetaImprBean.addListaImprCapeta(new ImprExpBean(numExpediente, idImpr));
							existeCarpetaImpr = Boolean.TRUE;
							break;
						}
					} else {
						carpetaImprBean.addListaImprCapeta(new ImprExpBean(numExpediente, idImpr));
						existeCarpetaImpr = Boolean.TRUE;
						break;
					}
				}
			}
			
		} else {
			listaEncarpetado = new ArrayList<CarpetaImprBean>();
		}
		if(!existeCarpetaImpr) {
			CarpetaImprBean carpetaImprBean = new CarpetaImprBean(carpeta,tipoImpr, tipoVehiculoImpr, tipoTramite,idContrato);
			carpetaImprBean.addListaImprCapeta(new ImprExpBean(numExpediente, idImpr));
			listaEncarpetado.add(carpetaImprBean);
		} 
	}
	
	public ResultadoImprBean(Boolean error) {
		super();
		this.error = error;
		this.existeListaMensajeOk = Boolean.FALSE;
		this.existeListaMensajeError = Boolean.FALSE;
	}

	public void addResumenOK(String mensaje) {
		if (resumen == null) {
			resumen = new ResumenImprBean();
		}
		resumen.addListaResumenOK(mensaje);
	}

	public void addResumenError(String mensaje) {
		if (resumen == null) {
			resumen = new ResumenImprBean();
		}
		resumen.addListaResumenError(mensaje);
	}
	
	public void addListaEncarpetado(CarpetaImprBean carpeta){
		if(listaEncarpetado == null || listaEncarpetado.isEmpty()){
			listaEncarpetado = new ArrayList<>();
		}
		listaEncarpetado.add(carpeta);
	}
	
	public void addListaMensajeOk(List<String> listaMensajesOks){
		for(String mensajeOk : listaMensajesOks) {
			addListaMensajeOk(mensajeOk);
		}
	}
	
	public void addListaMensajeError(List<String> listaMensajesErroress){
		for(String mensajeError : listaMensajesErroress) {
			addListaMensajeError(mensajeError);
		}
	}
	
	public void addListaMensajeOk(String mensajeOk){
		if(listaMensajeOk == null || listaMensajeOk.isEmpty()){
			listaMensajeOk =  new ArrayList<>();
		}
		listaMensajeOk.add(mensajeOk);
		existeListaMensajeOk = Boolean.TRUE;
	}
	
	public void addListaMensajeError(String mensajeError){
		if(listaMensajeError == null || listaMensajeError.isEmpty()){
			listaMensajeError =  new ArrayList<>();
		}
		listaMensajeError.add(mensajeError);
		existeListaMensajeError = Boolean.TRUE;
	}
	
	public void addListaIdsOk(BigDecimal id){
		if(listaIdOk == null){
			listaIdOk = new ArrayList<BigDecimal>();
		}
		listaIdOk.add(id);
	}
	
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getCodigoError() {
		return codigoError;
	}
	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}
	public List<String> getListaMensajes() {
		return listaMensajes;
	}
	public void setListaMensajes(List<String> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}
	public ResumenImprBean getResumen() {
		return resumen;
	}
	public void setResumen(ResumenImprBean resumen) {
		this.resumen = resumen;
	}

	public List<CarpetaImprBean> getListaEncarpetado() {
		return listaEncarpetado;
	}

	public void setListaEncarpetado(List<CarpetaImprBean> listaEncarpetado) {
		this.listaEncarpetado = listaEncarpetado;
	}

	public List<String> getListaMensajeOk() {
		return listaMensajeOk;
	}

	public void setListaMensajeOk(List<String> listaMensajeOk) {
		this.listaMensajeOk = listaMensajeOk;
	}

	public List<String> getListaMensajeError() {
		return listaMensajeError;
	}

	public void setListaMensajeError(List<String> listaMensajeError) {
		this.listaMensajeError = listaMensajeError;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public Boolean getExisteListaMensajeOk() {
		return existeListaMensajeOk;
	}

	public void setExisteListaMensajeOk(Boolean existeListaMensajeOk) {
		this.existeListaMensajeOk = existeListaMensajeOk;
	}

	public Boolean getExisteListaMensajeError() {
		return existeListaMensajeError;
	}

	public void setExisteListaMensajeError(Boolean existeListaMensajeError) {
		this.existeListaMensajeError = existeListaMensajeError;
	}

	public List<BigDecimal> getListaIdOk() {
		return listaIdOk;
	}

	public void setListaIdOk(List<BigDecimal> listaIdOk) {
		this.listaIdOk = listaIdOk;
	}

}
