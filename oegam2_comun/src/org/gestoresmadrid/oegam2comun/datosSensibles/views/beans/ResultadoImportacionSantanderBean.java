package org.gestoresmadrid.oegam2comun.datosSensibles.views.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegam2comun.datosSensibles.views.dto.ImportarBastidorDto;

import es.globaltms.gestorDocumentos.bean.FicheroBean;

public class ResultadoImportacionSantanderBean implements Serializable{

	private static final long serialVersionUID = -6544332419184679087L;
	
	private Boolean error;
	private Exception excepcion;
	private String mensajeError;
	private List<ImportarBastidorDto> listaBastidoresImportar;
	
	private List<String> listaLineasErroneas;
	private List<ResultadoAltaBastidorBean> listaResultadoAltaBastCart;
	private Long numAltaBastCartOk;
	private Long numAltaBastCartError;

	private List<ResultadoAltaBastidorBean> listaResultadoBajaBastCart;
	private Long numBajaBastCartOk;
	private Long numBajaBastCartError;
	private List<ResultadoAltaBastidorBean> listaResultadoAltaBastRetail;
	private Long numAltaBastRetailOk;
	private Long numAltaBastRetailError;
	private List<ResultadoAltaBastidorBean> listaResultadoBajaBastRetail;
	private Long numBajaBastRetailOk;
	private Long numBajaBastRetailError;
	
	private String nombreFichAltaCart;
	private String nombreFichBajaCart;
	private String nombreFichAltaRetail;
	private String nombreFichBajaRetail;
	
	
	private FicheroBean ficheroExcelCart;
	private FicheroBean ficheroExcelRetail;
	
	private Long numBastidoresCart;
	private Long numBastidoresRetail;
	
	private StringBuilder textoCorreoCart;
	private StringBuilder textoCorreoRetail;
	
	
	public void addTextoCorreoCart(String mensaje){
		if(textoCorreoCart == null){
			textoCorreoCart = new StringBuilder();
		}
		textoCorreoCart.append(mensaje);
	}
	
	public void addTextoCorreoRetail(String mensaje){
		if(textoCorreoRetail == null){
			textoCorreoRetail = new StringBuilder();
		}
		textoCorreoRetail.append(mensaje);
	}
	
	public void addListaLineaError(String mensaje){
		if(listaLineasErroneas == null){
			listaLineasErroneas = new ArrayList<String>();
		}
		listaLineasErroneas.add(mensaje);
	}
	
	public void addListaBastidoresImportar(ImportarBastidorDto bastidor){
		if(listaBastidoresImportar == null){
			listaBastidoresImportar = new ArrayList<ImportarBastidorDto>();
		}
		listaBastidoresImportar.add(bastidor);
	}
	
	public void addNumBastidoresCart(){
		if(numBastidoresCart == null){
			numBastidoresCart = new Long(0);
		}
		numBastidoresCart++;
	}
	
	public void addNumBastidoresRetail(){
		if(numBastidoresRetail == null){
			numBastidoresRetail = new Long(0);
		}
		numBastidoresRetail++;
	}
	
	public void addNumAltaBastCartOK(){
		if(numAltaBastCartOk == null){
			numAltaBastCartOk = new Long(0);
		}
		numAltaBastCartOk++;
	}
	
	public void addNumBajaBastCartOK(){
		if(numAltaBastCartOk == null){
			numAltaBastCartOk = new Long(0);
		}
		numAltaBastCartOk++;
	}
	
	public void addNumAltaBastRetailOK(){
		if(numAltaBastRetailOk == null){
			numAltaBastRetailOk = new Long(0);
		}
		numAltaBastRetailOk++;
	}
	
	public void addNumBajaBastRetailOK(){
		if(numBajaBastRetailOk == null){
			numBajaBastRetailOk = new Long(0);
		}
		numBajaBastRetailOk++;
	}
	
	public void addNumAltaBastCartError(){
		if(numAltaBastCartError == null){
			numAltaBastCartError = new Long(0);
		}
		numAltaBastCartError++;
	}
	
	public void addNumBajaBastCartError(){
		if(numBajaBastCartError == null){
			numBajaBastCartError = new Long(0);
		}
		numBajaBastCartError++;
	}
	
	public void addNumAltaBastRetailError(){
		if(numAltaBastRetailError == null){
			numAltaBastRetailError = new Long(0);
		}
		numAltaBastRetailError++;
	}
	
	public void addNumBajaBastRetailError(){
		if(numBajaBastRetailError == null){
			numBajaBastRetailError = new Long(0);
		}
		numBajaBastRetailError++;
	}
	
	
	public ResultadoImportacionSantanderBean(Boolean error) {
		super();
		this.error = error;
	}
	
	public void addResultadoAltaBastCart(ResultadoAltaBastidorBean resultado){
		if(listaResultadoAltaBastCart == null) {
			listaResultadoAltaBastCart = new ArrayList<ResultadoAltaBastidorBean>();
		}
		listaResultadoAltaBastCart.add(resultado);
	}
	
	public void addResultadoBajaBastCart(ResultadoAltaBastidorBean resultado){
		if(listaResultadoBajaBastCart == null) {
			listaResultadoBajaBastCart = new ArrayList<ResultadoAltaBastidorBean>();
		}
		listaResultadoBajaBastCart.add(resultado);
	}
	
	public void addResultadoAltaBastRetail(ResultadoAltaBastidorBean resultado){
		if(listaResultadoAltaBastRetail == null) {
			listaResultadoAltaBastRetail = new ArrayList<ResultadoAltaBastidorBean>();
		}
		listaResultadoAltaBastRetail.add(resultado);
	}
	
	public void addResultadoBajaBastRetail(ResultadoAltaBastidorBean resultado){
		if(listaResultadoBajaBastRetail == null) {
			listaResultadoBajaBastRetail = new ArrayList<ResultadoAltaBastidorBean>();
		}
		listaResultadoBajaBastRetail.add(resultado);
	}
	
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public Exception getExcepcion() {
		return excepcion;
	}
	public void setExcepcion(Exception excepcion) {
		this.excepcion = excepcion;
	}
	public String getMensajeError() {
		return mensajeError;
	}
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public List<ResultadoAltaBastidorBean> getListaResultadoAltaBastCart() {
		return listaResultadoAltaBastCart;
	}

	public void setListaResultadoAltaBastCart(List<ResultadoAltaBastidorBean> listaResultadoAltaBastCart) {
		this.listaResultadoAltaBastCart = listaResultadoAltaBastCart;
	}

	public List<ResultadoAltaBastidorBean> getListaResultadoBajaBastCart() {
		return listaResultadoBajaBastCart;
	}

	public void setListaResultadoBajaBastCart(List<ResultadoAltaBastidorBean> listaResultadoBajaBastCart) {
		this.listaResultadoBajaBastCart = listaResultadoBajaBastCart;
	}

	public List<ResultadoAltaBastidorBean> getListaResultadoAltaBastRetail() {
		return listaResultadoAltaBastRetail;
	}

	public void setListaResultadoAltaBastRetail(List<ResultadoAltaBastidorBean> listaResultadoAltaBastRetail) {
		this.listaResultadoAltaBastRetail = listaResultadoAltaBastRetail;
	}

	public List<ResultadoAltaBastidorBean> getListaResultadoBajaBastRetail() {
		return listaResultadoBajaBastRetail;
	}

	public void setListaResultadoBajaBastRetail(List<ResultadoAltaBastidorBean> listaResultadoBajaBastRetail) {
		this.listaResultadoBajaBastRetail = listaResultadoBajaBastRetail;
	}

	public String getNombreFichAltaCart() {
		return nombreFichAltaCart;
	}

	public void setNombreFichAltaCart(String nombreFichAltaCart) {
		this.nombreFichAltaCart = nombreFichAltaCart;
	}

	public String getNombreFichBajaCart() {
		return nombreFichBajaCart;
	}

	public void setNombreFichBajaCart(String nombreFichBajaCart) {
		this.nombreFichBajaCart = nombreFichBajaCart;
	}

	public String getNombreFichAltaRetail() {
		return nombreFichAltaRetail;
	}

	public void setNombreFichAltaRetail(String nombreFichAltaRetail) {
		this.nombreFichAltaRetail = nombreFichAltaRetail;
	}

	public String getNombreFichBajaRetail() {
		return nombreFichBajaRetail;
	}

	public void setNombreFichBajaRetail(String nombreFichBajaRetail) {
		this.nombreFichBajaRetail = nombreFichBajaRetail;
	}

	public Long getNumAltaBastCartOk() {
		return numAltaBastCartOk;
	}

	public void setNumAltaBastCartOk(Long numAltaBastCartOk) {
		this.numAltaBastCartOk = numAltaBastCartOk;
	}

	public Long getNumAltaBastCartError() {
		return numAltaBastCartError;
	}

	public void setNumAltaBastCartError(Long numAltaBastCartError) {
		this.numAltaBastCartError = numAltaBastCartError;
	}

	public Long getNumBajaBastCartOk() {
		return numBajaBastCartOk;
	}

	public void setNumBajaBastCartOk(Long numBajaBastCartOk) {
		this.numBajaBastCartOk = numBajaBastCartOk;
	}

	public Long getNumBajaBastCartError() {
		return numBajaBastCartError;
	}

	public void setNumBajaBastCartError(Long numBajaBastCartError) {
		this.numBajaBastCartError = numBajaBastCartError;
	}

	public Long getNumAltaBastRetailOk() {
		return numAltaBastRetailOk;
	}

	public void setNumAltaBastRetailOk(Long numAltaBastRetailOk) {
		this.numAltaBastRetailOk = numAltaBastRetailOk;
	}

	public Long getNumAltaBastRetailError() {
		return numAltaBastRetailError;
	}

	public void setNumAltaBastRetailError(Long numAltaBastRetailError) {
		this.numAltaBastRetailError = numAltaBastRetailError;
	}

	public Long getNumBajaBastRetailOk() {
		return numBajaBastRetailOk;
	}

	public void setNumBajaBastRetailOk(Long numBajaBastRetailOk) {
		this.numBajaBastRetailOk = numBajaBastRetailOk;
	}

	public Long getNumBajaBastRetailError() {
		return numBajaBastRetailError;
	}

	public void setNumBajaBastRetailError(Long numBajaBastRetailError) {
		this.numBajaBastRetailError = numBajaBastRetailError;
	}

	public Long getNumBastidoresCart() {
		return numBastidoresCart;
	}

	public void setNumBastidoresCart(Long numBastidoresCart) {
		this.numBastidoresCart = numBastidoresCart;
	}

	public Long getNumBastidoresRetail() {
		return numBastidoresRetail;
	}

	public void setNumBastidoresRetail(Long numBastidoresRetail) {
		this.numBastidoresRetail = numBastidoresRetail;
	}

	public FicheroBean getFicheroExcelCart() {
		return ficheroExcelCart;
	}

	public void setFicheroExcelCart(FicheroBean ficheroExcelCart) {
		this.ficheroExcelCart = ficheroExcelCart;
	}

	public FicheroBean getFicheroExcelRetail() {
		return ficheroExcelRetail;
	}

	public void setFicheroExcelRetail(FicheroBean ficheroExcelRetail) {
		this.ficheroExcelRetail = ficheroExcelRetail;
	}


	public List<String> getListaLineasErroneas() {
		return listaLineasErroneas;
	}

	public void setListaLineasErroneas(List<String> listaLineasErroneas) {
		this.listaLineasErroneas = listaLineasErroneas;
	}

	public List<ImportarBastidorDto> getListaBastidoresImportar() {
		return listaBastidoresImportar;
	}

	public void setListaBastidoresImportar(List<ImportarBastidorDto> listaBastidoresImportar) {
		this.listaBastidoresImportar = listaBastidoresImportar;
	}

	public StringBuilder getTextoCorreoCart() {
		return textoCorreoCart;
	}

	public void setTextoCorreoCart(StringBuilder textoCorreoCart) {
		this.textoCorreoCart = textoCorreoCart;
	}

	public StringBuilder getTextoCorreoRetail() {
		return textoCorreoRetail;
	}

	public void setTextoCorreoRetail(StringBuilder textoCorreoRetail) {
		this.textoCorreoRetail = textoCorreoRetail;
	}

}
