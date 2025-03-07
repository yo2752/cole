package org.gestoresmadrid.oegamComun.impr.view.bean;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegamComun.impr.model.dto.DetalleDocImprDTO;
import org.gestoresmadrid.oegamComun.impr.model.dto.DocDGTImprDTO;
import org.gestoresmadrid.oegamComun.impr.model.dto.EvolucionDocImprDTO;



public class ResultadoDocImprBean implements Serializable{
	
	private static final long serialVersionUID = 2897541280123420598L;
	
	Boolean error, esZip;
	String mensaje, sDocId, nombreFichero;
	Long docId;
	File fichero;
	ByteArrayInputStream ficheroArray;
	List<DocDGTImprDTO> listaDocImprDTO;
	List<String> listaMensajeOk, listaMensajeError;
	List<DocImprGestionBean> listaDocImprPantalla;
	List<DetalleDocImprDTO> listaDetalleDocImpr;
	List<EvolucionDocImprDTO> listaEvolucionDocImpr;
	List<BigDecimal> listaExpedientes;
	List<Long> listaIdsDupDstv;
	private ResumenImprBean resumen;
	
	public ResultadoDocImprBean(Boolean error) {
		super();
		this.error = error;
		this.esZip = Boolean.FALSE;
	}
	
	public void addListaEvolucionDocImpr(EvolucionDocImprDTO evolucionDocImprDTO){
		if(listaEvolucionDocImpr == null || listaEvolucionDocImpr.isEmpty()){
			listaEvolucionDocImpr = new ArrayList<>();
		}
		listaEvolucionDocImpr.add(evolucionDocImprDTO);
	}
	
	public void addListaDocImprPantalla(DocImprGestionBean docImprGestionBean){
		if(listaDocImprPantalla == null || listaDocImprPantalla.isEmpty()){
			listaDocImprPantalla = new ArrayList<>();
		}
		listaDocImprPantalla.add(docImprGestionBean);
	}
	
	public void addListaDetalleDocImpr(DetalleDocImprDTO detalle){
		if(listaDetalleDocImpr == null || listaDetalleDocImpr.isEmpty()){
			listaDetalleDocImpr = new ArrayList<>();
		}
		listaDetalleDocImpr.add(detalle);
	}
	
	public void addListaMensajeOk(String mensajeOk){
		if(listaMensajeOk == null || listaMensajeOk.isEmpty()){
			listaMensajeOk =  new ArrayList<>();
		}
		listaMensajeOk.add(mensajeOk);
	}
	
	public void addListaMensajeError(String mensajeError){
		if(listaMensajeError == null || listaMensajeError.isEmpty()){
			listaMensajeError =  new ArrayList<>();
		}
		listaMensajeError.add(mensajeError);
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
	public Long getDocId() {
		return docId;
	}
	public void setDocId(Long docId) {
		this.docId = docId;
	}
	public String getsDocId() {
		return sDocId;
	}
	public void setsDocId(String sDocId) {
		this.sDocId = sDocId;
	}

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public Boolean getEsZip() {
		return esZip;
	}

	public void setEsZip(Boolean esZip) {
		this.esZip = esZip;
	}

	public List<DocDGTImprDTO> getListaDocImprDTO() {
		return listaDocImprDTO;
	}

	public void setListaDocImprDTO(List<DocDGTImprDTO> listaDocImprDTO) {
		this.listaDocImprDTO = listaDocImprDTO;
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

	public List<DocImprGestionBean> getListaDocImprPantalla() {
		return listaDocImprPantalla;
	}

	public void setListaDocImprPantalla(List<DocImprGestionBean> listaDocImprPantalla) {
		this.listaDocImprPantalla = listaDocImprPantalla;
	}

	public List<DetalleDocImprDTO> getListaDetalleDocImpr() {
		return listaDetalleDocImpr;
	}

	public void setListaDetalleDocImpr(List<DetalleDocImprDTO> listaDetalleDocImpr) {
		this.listaDetalleDocImpr = listaDetalleDocImpr;
	}

	public List<EvolucionDocImprDTO> getListaEvolucionDocImpr() {
		return listaEvolucionDocImpr;
	}

	public void setListaEvolucionDocImpr(List<EvolucionDocImprDTO> listaEvolucionDocImpr) {
		this.listaEvolucionDocImpr = listaEvolucionDocImpr;
	}

	public ByteArrayInputStream getFicheroArray() {
		return ficheroArray;
	}

	public void setFicheroArray(ByteArrayInputStream ficheroArray) {
		this.ficheroArray = ficheroArray;
	}

	public List<BigDecimal> getListaExpedientes() {
		return listaExpedientes;
	}

	public void setListaExpedientes(List<BigDecimal> listaExpedientes) {
		this.listaExpedientes = listaExpedientes;
	}

	public List<Long> getListaIdsDupDstv() {
		return listaIdsDupDstv;
	}

	public void setListaIdsDupDstv(List<Long> listaIdsDupDstv) {
		this.listaIdsDupDstv = listaIdsDupDstv;
	}

	public ResumenImprBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenImprBean resumen) {
		this.resumen = resumen;
	}

}
