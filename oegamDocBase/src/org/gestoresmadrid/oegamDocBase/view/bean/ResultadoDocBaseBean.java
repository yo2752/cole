package org.gestoresmadrid.oegamDocBase.view.bean;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.docbase.model.vo.DocumentoBaseVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;

public class ResultadoDocBaseBean implements Serializable{

	private static final long serialVersionUID = -1943650789285732367L;
	
	private Boolean error;
	private Boolean existeErroresVal;
	private Exception excepcion;
	private String mensaje;
	private List<String> listaMensaje;
	private ResumenDocBaseBean resumen;
	private List<TramiteTraficoVO> listaCarpetaCtit;
	private List<TramiteTraficoVO> listaCarpetaTipoA;
	private List<TramiteTraficoVO> listaCarpetaTipoB;
	private List<TramiteTraficoVO> listaCarpetaTipoI;
	private List<TramiteTraficoVO> listaCarpetaFusion;
	private List<TramiteTraficoVO> listaCarpetaMate;
	private List<TramiteTraficoVO> listaCarpetaMatrPdf;
	private List<TramiteTraficoVO> listaCarpetaMateTipoA;
	private List<TramiteTraficoVO> listaCarpetaBtv;
	private List<TramiteTraficoVO> listaCarpetaDuplicado;
	private Long idContrato;
	private String tipoCarpeta;
	private DocumentoBaseVO documentoBase;
	private DocBaseBean docBaseFinal;
	private List<TramiteTraficoVO> listaTramites;
	private List<DocumentoBaseBean> listaDocBaseBean;
	private File fichero;
	private String nombreFichero;
	private Boolean esZip;
	
	public ResultadoDocBaseBean(Boolean error) {
		super();
		this.error = error;
		this.existeErroresVal = Boolean.FALSE;
		this.esZip = Boolean.FALSE;
	}

	public void addListaDocumentoBase(DocumentoBaseBean docBaseBean){
		if(listaDocBaseBean == null){
			listaDocBaseBean = new ArrayList<DocumentoBaseBean>();
		}
		listaDocBaseBean.add(docBaseBean);
	}
	
	public void addListaCarpetaCtit(TramiteTraficoVO tramite){
		if(listaCarpetaCtit == null){
			listaCarpetaCtit = new ArrayList<TramiteTraficoVO>();
		}
		listaCarpetaCtit.add(tramite);
	}
	
	public void addListaCarpetaTipoA(TramiteTraficoVO tramite){
		if(listaCarpetaTipoA == null){
			listaCarpetaTipoA = new ArrayList<TramiteTraficoVO>();
		}
		listaCarpetaTipoA.add(tramite);
	}
	
	public void addListaCarpetaTipoB(TramiteTraficoVO tramite){
		if(listaCarpetaTipoB == null){
			listaCarpetaTipoB = new ArrayList<TramiteTraficoVO>();
		}
		listaCarpetaTipoB.add(tramite);
	}
	
	public void addListaCarpetaTipoI(TramiteTraficoVO tramite){
		if(listaCarpetaTipoI == null){
			listaCarpetaTipoI = new ArrayList<TramiteTraficoVO>();
		}
		listaCarpetaTipoI.add(tramite);
	}
	
	public void addListaCarpetaFusion(TramiteTraficoVO tramite){
		if(listaCarpetaFusion == null){
			listaCarpetaFusion = new ArrayList<TramiteTraficoVO>();
		}
		listaCarpetaFusion.add(tramite);
	}
	
	public void addListaCarpetaMate(TramiteTraficoVO tramite){
		if(listaCarpetaMate == null){
			listaCarpetaMate = new ArrayList<TramiteTraficoVO>();
		}
		listaCarpetaMate.add(tramite);
	}
	
	public void addListaCarpetaMatrPdf(TramiteTraficoVO tramite){
		if(listaCarpetaMatrPdf == null){
			listaCarpetaMatrPdf = new ArrayList<TramiteTraficoVO>();
		}
		listaCarpetaMatrPdf.add(tramite);
	}
	
	public void addListaCarpetaMateTipoA(TramiteTraficoVO tramite){
		if(listaCarpetaMateTipoA == null){
			listaCarpetaMateTipoA = new ArrayList<TramiteTraficoVO>();
		}
		listaCarpetaMateTipoA.add(tramite);
	}
	
	public void addListaCarpetaBtv(TramiteTraficoVO tramite){
		if(listaCarpetaBtv == null){
			listaCarpetaBtv = new ArrayList<TramiteTraficoVO>();
		}
		listaCarpetaBtv.add(tramite);
	}
	
	public void addListaCarpetaDuplicado(TramiteTraficoVO tramite){
		if(listaCarpetaDuplicado == null){
			listaCarpetaDuplicado = new ArrayList<TramiteTraficoVO>();
		}
		listaCarpetaDuplicado.add(tramite);
	}
	
	public void addListaMensaje(String mensaje){
		if(listaMensaje == null){
			listaMensaje = new ArrayList<String>();
		}
		listaMensaje.add(mensaje);
	}
	
	public void addResumenOK(String mensaje){
		if(resumen == null){
			resumen = new ResumenDocBaseBean();
		}
		resumen.addListaOk(mensaje);
	}
	
	public void addResumenError(String mensaje){
		if(resumen == null){
			resumen = new ResumenDocBaseBean();
		}
		resumen.addListaError(mensaje);
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

	public List<String> getListaMensaje() {
		return listaMensaje;
	}

	public void setListaMensaje(List<String> listaMensaje) {
		this.listaMensaje = listaMensaje;
	}

	public ResumenDocBaseBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenDocBaseBean resumen) {
		this.resumen = resumen;
	}

	public List<TramiteTraficoVO> getListaCarpetaCtit() {
		return listaCarpetaCtit;
	}

	public void setListaCarpetaCtit(List<TramiteTraficoVO> listaCarpetaCtit) {
		this.listaCarpetaCtit = listaCarpetaCtit;
	}

	public List<TramiteTraficoVO> getListaCarpetaTipoA() {
		return listaCarpetaTipoA;
	}

	public void setListaCarpetaTipoA(List<TramiteTraficoVO> listaCarpetaTipoA) {
		this.listaCarpetaTipoA = listaCarpetaTipoA;
	}

	public List<TramiteTraficoVO> getListaCarpetaTipoB() {
		return listaCarpetaTipoB;
	}

	public void setListaCarpetaTipoB(List<TramiteTraficoVO> listaCarpetaTipoB) {
		this.listaCarpetaTipoB = listaCarpetaTipoB;
	}

	public List<TramiteTraficoVO> getListaCarpetaTipoI() {
		return listaCarpetaTipoI;
	}

	public void setListaCarpetaTipoI(List<TramiteTraficoVO> listaCarpetaTipoI) {
		this.listaCarpetaTipoI = listaCarpetaTipoI;
	}

	public List<TramiteTraficoVO> getListaCarpetaFusion() {
		return listaCarpetaFusion;
	}

	public void setListaCarpetaFusion(List<TramiteTraficoVO> listaCarpetaFusion) {
		this.listaCarpetaFusion = listaCarpetaFusion;
	}

	public List<TramiteTraficoVO> getListaCarpetaMate() {
		return listaCarpetaMate;
	}

	public void setListaCarpetaMate(List<TramiteTraficoVO> listaCarpetaMate) {
		this.listaCarpetaMate = listaCarpetaMate;
	}

	public List<TramiteTraficoVO> getListaCarpetaMatrPdf() {
		return listaCarpetaMatrPdf;
	}

	public void setListaCarpetaMatrPdf(List<TramiteTraficoVO> listaCarpetaMatrPdf) {
		this.listaCarpetaMatrPdf = listaCarpetaMatrPdf;
	}

	public List<TramiteTraficoVO> getListaCarpetaMateTipoA() {
		return listaCarpetaMateTipoA;
	}

	public void setListaCarpetaMateTipoA(List<TramiteTraficoVO> listaCarpetaMateTipoA) {
		this.listaCarpetaMateTipoA = listaCarpetaMateTipoA;
	}

	public List<TramiteTraficoVO> getListaCarpetaBtv() {
		return listaCarpetaBtv;
	}

	public void setListaCarpetaBtv(List<TramiteTraficoVO> listaCarpetaBtv) {
		this.listaCarpetaBtv = listaCarpetaBtv;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public Exception getExcepcion() {
		return excepcion;
	}

	public void setExcepcion(Exception excepcion) {
		this.excepcion = excepcion;
	}

	public List<TramiteTraficoVO> getListaTramites() {
		return listaTramites;
	}

	public void setListaTramites(List<TramiteTraficoVO> listaTramites) {
		this.listaTramites = listaTramites;
	}

	public List<DocumentoBaseBean> getListaDocBaseBean() {
		return listaDocBaseBean;
	}

	public void setListaDocBaseBean(List<DocumentoBaseBean> listaDocBaseBean) {
		this.listaDocBaseBean = listaDocBaseBean;
	}

	public String getTipoCarpeta() {
		return tipoCarpeta;
	}

	public void setTipoCarpeta(String tipoCarpeta) {
		this.tipoCarpeta = tipoCarpeta;
	}

	public DocumentoBaseVO getDocumentoBase() {
		return documentoBase;
	}

	public void setDocumentoBase(DocumentoBaseVO documentoBase) {
		this.documentoBase = documentoBase;
	}

	public DocBaseBean getDocBaseFinal() {
		return docBaseFinal;
	}

	public void setDocBaseFinal(DocBaseBean docBaseFinal) {
		this.docBaseFinal = docBaseFinal;
	}

	public Boolean getExisteErroresVal() {
		return existeErroresVal;
	}

	public void setExisteErroresVal(Boolean existeErroresVal) {
		this.existeErroresVal = existeErroresVal;
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

	public List<TramiteTraficoVO> getListaCarpetaDuplicado() {
		return listaCarpetaDuplicado;
	}

	public void setListaCarpetaDuplicado(List<TramiteTraficoVO> listaCarpetaDuplicado) {
		this.listaCarpetaDuplicado = listaCarpetaDuplicado;
	}
	
}
