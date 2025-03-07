package org.gestoresmadrid.oegam2comun.modelo600_601.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"valorDeclarado","prescrita","exento","noSujeto","fundamentoLegal","fundamentoLegalDesc",
		"liquidacionComplementaria","numJustificantePrimeraLiquidacion","fecha","anoDocumento","tipoImpuesto",
		"numDocumento","baseImponible","reduccion","reduccImporte","baseLiquidable","tipo",
		"cuota","bonificacionCodigo","bonificacionDescripcion","bonificacionCuota","bonificacionCuotaImporte","aIngresar","auxiliar1","auxiliar2","totalIngresar"})
@XmlRootElement(name = "liquidacion")
public class Liquidacion {

	@XmlElement(name = "ValorDeclarado", required = true)
	protected String valorDeclarado;
	@XmlElement(name = "Prescrita", required = true)
	protected String prescrita;
	@XmlElement(name = "Exento", required = true)
	protected String exento;
	@XmlElement(name = "NoSujeto", required = true)
	protected String noSujeto;
	@XmlElement(name = "FundamentoLegal", required = true)
	protected String fundamentoLegal;
	@XmlElement(name = "FundamentoLegalDesc", required = true)
	protected String fundamentoLegalDesc;
	@XmlElement(name = "LiquidacionComplementaria", required = true)
	protected String liquidacionComplementaria;
	@XmlElement(name = "NumJustificantePrimeraLiquidacion", required = true)
	protected String numJustificantePrimeraLiquidacion;
	@XmlElement(name = "Fecha", required = true)
	protected String fecha;
	@XmlElement(name = "anoDocumento", required = true)
	protected String anoDocumento;
	@XmlElement(name = "TipoImpuesto", required = true)
	protected String tipoImpuesto;
	@XmlElement(name = "NumDocumento", required = true)
	protected String numDocumento;
	@XmlElement(name = "BaseImponible", required = true)
	protected String baseImponible;
	@XmlElement(name = "Reduccion", required = true)
	protected String reduccion;
	@XmlElement(name = "ReduccImporte", required = true)
	protected String reduccImporte;
	@XmlElement(name = "BaseLiquidable", required = true)
	protected String baseLiquidable;
	@XmlElement(name = "Tipo", required = true)
	protected String tipo;
	@XmlElement(name = "Cuota", required = true)
	protected String cuota;
	@XmlElement(name = "BonificacionCodigo", required = true)
	protected String bonificacionCodigo;
	@XmlElement(name = "BonificacionDescripcion", required = true)
	protected String bonificacionDescripcion;
	@XmlElement(name = "BonificacionCuota", required = true)
	protected String bonificacionCuota;
	@XmlElement(name = "BonificacionCuotaImporte", required = true)
	protected String bonificacionCuotaImporte;
	@XmlElement(name = "Aingresar", required = true)
	protected String aIngresar;
	@XmlElement(name = "Auxiliar1", required = true)
	protected String auxiliar1;
	@XmlElement(name = "Auxiliar2", required = true)
	protected String auxiliar2;
	@XmlElement(name = "TotalIngresar", required = true)
	protected String totalIngresar;
	
	public String getValorDeclarado() {
		return valorDeclarado;
	}
	public void setValorDeclarado(String valorDeclarado) {
		this.valorDeclarado = valorDeclarado;
	}
	public String getPrescrita() {
		return prescrita;
	}
	public void setPrescrita(String prescrita) {
		this.prescrita = prescrita;
	}
	public String getExento() {
		return exento;
	}
	public void setExento(String exento) {
		this.exento = exento;
	}
	public String getNoSujeto() {
		return noSujeto;
	}
	public void setNoSujeto(String noSujeto) {
		this.noSujeto = noSujeto;
	}
	public String getFundamentoLegal() {
		return fundamentoLegal;
	}
	public void setFundamentoLegal(String fundamentoLegal) {
		this.fundamentoLegal = fundamentoLegal;
	}
	public String getFundamentoLegalDesc() {
		return fundamentoLegalDesc;
	}
	public void setFundamentoLegalDesc(String fundamentoLegalDesc) {
		this.fundamentoLegalDesc = fundamentoLegalDesc;
	}
	public String getLiquidacionComplementaria() {
		return liquidacionComplementaria;
	}
	public void setLiquidacionComplementaria(String liquidacionComplementaria) {
		this.liquidacionComplementaria = liquidacionComplementaria;
	}
	public String getNumJustificantePrimeraLiquidacion() {
		return numJustificantePrimeraLiquidacion;
	}
	public void setNumJustificantePrimeraLiquidacion(
			String numJustificantePrimeraLiquidacion) {
		this.numJustificantePrimeraLiquidacion = numJustificantePrimeraLiquidacion;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getAnoDocumento() {
		return anoDocumento;
	}
	public void setAnoDocumento(String anoDocumento) {
		this.anoDocumento = anoDocumento;
	}
	public String getTipoImpuesto() {
		return tipoImpuesto;
	}
	public void setTipoImpuesto(String tipoImpuesto) {
		this.tipoImpuesto = tipoImpuesto;
	}
	public String getNumDocumento() {
		return numDocumento;
	}
	public void setNumDocumento(String numDocumento) {
		this.numDocumento = numDocumento;
	}
	public String getBaseImponible() {
		return baseImponible;
	}
	public void setBaseImponible(String baseImponible) {
		this.baseImponible = baseImponible;
	}
	public String getReduccion() {
		return reduccion;
	}
	public void setReduccion(String reduccion) {
		this.reduccion = reduccion;
	}
	public String getReduccImporte() {
		return reduccImporte;
	}
	public void setReduccImporte(String reduccImporte) {
		this.reduccImporte = reduccImporte;
	}
	public String getBaseLiquidable() {
		return baseLiquidable;
	}
	public void setBaseLiquidable(String baseLiquidable) {
		this.baseLiquidable = baseLiquidable;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getCuota() {
		return cuota;
	}
	public void setCuota(String cuota) {
		this.cuota = cuota;
	}
	public String getBonificacionCuota() {
		return bonificacionCuota;
	}
	public void setBonificacionCuota(String bonificacionCuota) {
		this.bonificacionCuota = bonificacionCuota;
	}
	public String getBonificacionCuotaImporte() {
		return bonificacionCuotaImporte;
	}
	public void setBonificacionCuotaImporte(String bonificacionCuotaImporte) {
		this.bonificacionCuotaImporte = bonificacionCuotaImporte;
	}
	public String getaIngresar() {
		return aIngresar;
	}
	public void setaIngresar(String aIngresar) {
		this.aIngresar = aIngresar;
	}
	public String getAuxiliar1() {
		return auxiliar1;
	}
	public void setAuxiliar1(String auxiliar1) {
		this.auxiliar1 = auxiliar1;
	}
	public String getAuxiliar2() {
		return auxiliar2;
	}
	public void setAuxiliar2(String auxiliar2) {
		this.auxiliar2 = auxiliar2;
	}
	public String getTotalIngresar() {
		return totalIngresar;
	}
	public void setTotalIngresar(String totalIngresar) {
		this.totalIngresar = totalIngresar;
	}
	public String getBonificacionCodigo() {
		return bonificacionCodigo;
	}
	public void setBonificacionCodigo(String bonificacionCodigo) {
		this.bonificacionCodigo = bonificacionCodigo;
	}
	public String getBonificacionDescripcion() {
		return bonificacionDescripcion;
	}
	public void setBonificacionDescripcion(String bonificacionDescripcion) {
		this.bonificacionDescripcion = bonificacionDescripcion;
	}
}
