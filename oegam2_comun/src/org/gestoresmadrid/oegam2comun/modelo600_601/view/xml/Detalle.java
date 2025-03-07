package org.gestoresmadrid.oegam2comun.modelo600_601.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"oficinaLiquidadora","oficinaLiquidadoraDesc","numSujetos","numTransmitentes",
		"fechaDevengo","docPublico","docPrivado","docJudicial","docAdministrativo","codNotaria","apellidoNotario"
		,"nombreNotario","codNotario","numProtocolo","numProtocoloBis","fechaDocumento","expAbreviada","concepto"})
@XmlRootElement(name = "Detalle")
public class Detalle {

	@XmlElement(name = "OficinaLiquidadora", required = true)
	protected String oficinaLiquidadora;
	@XmlElement(name = "OficinaLiquidadoraDesc", required = true)
	protected String oficinaLiquidadoraDesc;
	@XmlElement(name = "Num_Sujetos", required = true)
	protected String numSujetos;
	@XmlElement(name = "Num_Transmitentes", required = true)
	protected String numTransmitentes;
	@XmlElement(name = "FechaDevengo", required = true)
	protected String fechaDevengo;
	@XmlElement(name = "DocPublico", required = true)
	protected String docPublico;
	@XmlElement(name = "DocPrivado", required = true)
	protected String docPrivado;
	@XmlElement(name = "DocJudicial", required = true)
	protected String docJudicial;
	@XmlElement(name = "DocAdministrativo", required = true)
	protected String docAdministrativo;
	@XmlElement(name = "CodNotaria", required = true)
	protected String codNotaria;
	@XmlElement(name = "ApellidoNotario", required = true)
	protected String apellidoNotario;
	@XmlElement(name = "NombreNotario", required = true)
	protected String nombreNotario;
	@XmlElement(name = "CodNotario", required = true)
	protected String codNotario;
	@XmlElement(name = "NumProtocolo", required = true)
	protected String numProtocolo;
	@XmlElement(name = "NumProtocoloBis", required = true)
	protected String numProtocoloBis;
	@XmlElement(name = "FechaDocumento", required = true)
	protected String fechaDocumento;
	@XmlElement(name = "ExpAbreviada", required = true)
	protected String expAbreviada;
	@XmlElement(name = "Concepto", required = true)
	protected String concepto;
	 
	public String getOficinaLiquidadora() {
		return oficinaLiquidadora;
	}
	public void setOficinaLiquidadora(String oficinaLiquidadora) {
		this.oficinaLiquidadora = oficinaLiquidadora;
	}
	public String getOficinaLiquidadoraDesc() {
		return oficinaLiquidadoraDesc;
	}
	public void setOficinaLiquidadoraDesc(String oficinaLiquidadoraDesc) {
		this.oficinaLiquidadoraDesc = oficinaLiquidadoraDesc;
	}
	public String getNumSujetos() {
		return numSujetos;
	}
	public void setNumSujetos(String numSujetos) {
		this.numSujetos = numSujetos;
	}
	public String getNumTransmitentes() {
		return numTransmitentes;
	}
	public void setNumTransmitentes(String numTransmitentes) {
		this.numTransmitentes = numTransmitentes;
	}
	public String getFechaDevengo() {
		return fechaDevengo;
	}
	public void setFechaDevengo(String fechaDevengo) {
		this.fechaDevengo = fechaDevengo;
	}
	public String getDocPublico() {
		return docPublico;
	}
	public void setDocPublico(String docPublico) {
		this.docPublico = docPublico;
	}
	public String getDocJudicial() {
		return docJudicial;
	}
	public void setDocJudicial(String docJudicial) {
		this.docJudicial = docJudicial;
	}
	public String getDocAdministrativo() {
		return docAdministrativo;
	}
	public void setDocAdministrativo(String docAdministrativo) {
		this.docAdministrativo = docAdministrativo;
	}
	public String getCodNotaria() {
		return codNotaria;
	}
	public void setCodNotaria(String codNotaria) {
		this.codNotaria = codNotaria;
	}
	public String getApellidoNotario() {
		return apellidoNotario;
	}
	public void setApellidoNotario(String apellidoNotario) {
		this.apellidoNotario = apellidoNotario;
	}
	public String getNombreNotario() {
		return nombreNotario;
	}
	public void setNombreNotario(String nombreNotario) {
		this.nombreNotario = nombreNotario;
	}
	public String getCodNotario() {
		return codNotario;
	}
	public void setCodNotario(String codNotario) {
		this.codNotario = codNotario;
	}
	public String getNumProtocolo() {
		return numProtocolo;
	}
	public void setNumProtocolo(String numProtocolo) {
		this.numProtocolo = numProtocolo;
	}
	public String getNumProtocoloBis() {
		return numProtocoloBis;
	}
	public void setNumProtocoloBis(String numProtocoloBis) {
		this.numProtocoloBis = numProtocoloBis;
	}
	public String getFechaDocumento() {
		return fechaDocumento;
	}
	public void setFechaDocumento(String fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}
	public String getExpAbreviada() {
		return expAbreviada;
	}
	public void setExpAbreviada(String expAbreviada) {
		this.expAbreviada = expAbreviada;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getDocPrivado() {
		return docPrivado;
	}
	public void setDocPrivado(String docPrivado) {
		this.docPrivado = docPrivado;
	}
	
}
