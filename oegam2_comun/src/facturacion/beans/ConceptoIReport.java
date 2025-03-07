package facturacion.beans;

public class ConceptoIReport {
	private String concepto;
	private String titulo;
	private String cantidad;
	private String importe;
	private String ivaPorcentaje;
	private String ivaImporte;
	private String descuento;
	private String total;
	private Boolean isPrinted;
	private Boolean isLastLine;
	private String baseImponible;
	private String ivaPercentage;
	private String ivaTotal;
	private String baseImpRetencion;
	private String irpfPercentage;
	private String irpfTotal;
	private Boolean isSummary;
	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	/**
	 * @return the importe
	 */
	public String getImporte() {
		return importe;
	}
	/**
	 * @param importe the importe to set
	 */
	public void setImporte(String importe) {
		this.importe = importe;
	}
	/**
	 * @return the cantidad
	 */
	public String getCantidad() {
		return cantidad;
	}
	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	/**
	 * @return the ivaPorcentaje
	 */
	public String getIvaPorcentaje() {
		return ivaPorcentaje;
	}
	/**
	 * @param ivaPorcentaje the ivaPorcentaje to set
	 */
	public void setIvaPorcentaje(String ivaPorcentaje) {
		this.ivaPorcentaje = ivaPorcentaje;
	}
	/**
	 * @return the ivaImporte
	 */
	public String getIvaImporte() {
		return ivaImporte;
	}
	/**
	 * @param ivaImporte the ivaImporte to set
	 */
	public void setIvaImporte(String ivaImporte) {
		this.ivaImporte = ivaImporte;
	}
	/**
	 * @return the descuento
	 */
	public String getDescuento() {
		return descuento;
	}
	/**
	 * @param descuento the descuento to set
	 */
	public void setDescuento(String descuento) {
		this.descuento = descuento;
	}
	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}
	/**
	 * @return the isPrinted
	 */
	public Boolean getIsPrinted() {
		return isPrinted;
	}
	/**
	 * @param isPrinted the isPrinted to set
	 */
	public void setIsPrinted(Boolean isPrinted) {
		this.isPrinted = isPrinted;
	}
	/**
	 * @return the facturaRectificativa
	 */
	public String getFacturaRectificativa() {
		return facturaRectificativa;
	}
	/**
	 * @param facturaRectificativa the facturaRectificativa to set
	 */
	public void setFacturaRectificativa(String facturaRectificativa) {
		this.facturaRectificativa = facturaRectificativa;
	}
	private String facturaRectificativa;

	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	/**
	 * @return the isLastLine
	 */
	public Boolean getIsLastLine() {
		return isLastLine;
	}
	/**
	 * @param isLastLine the isLastLine to set
	 */
	public void setIsLastLine(Boolean isLastLine) {
		this.isLastLine = isLastLine;
	}
	
	public String getBaseImpRetencion() {
		return baseImpRetencion;
	}
	public void setBaseImpRetencion(String baseImpRetencion) {
		this.baseImpRetencion = baseImpRetencion;
	}
	public String getIrpfPercentage() {
		return irpfPercentage;
	}
	public void setIrpfPercentage(String irpfPercentage) {
		this.irpfPercentage = irpfPercentage;
	}
	public String getIrpfTotal() {
		return irpfTotal;
	}
	public void setIrpfTotal(String irpfTotal) {
		this.irpfTotal = irpfTotal;
	}
	
	public String getBaseImponible() {
		return baseImponible;
	}
	public void setBaseImponible(String baseImponible) {
		this.baseImponible = baseImponible;
	}
	public String getIvaPercentage() {
		return ivaPercentage;
	}
	public void setIvaPercentage(String ivaPercentage) {
		this.ivaPercentage = ivaPercentage;
	}
	public String getIvaTotal() {
		return ivaTotal;
	}
	public void setIvaTotal(String ivaTotal) {
		this.ivaTotal = ivaTotal;
	}
	/**
	 * @return the isSummary
	 */
	public Boolean getIsSummary() {
		return isSummary;
	}
	/**
	 * @param isSummary the isSummary to set
	 */
	public void setIsSummary(Boolean isSummary) {
		this.isSummary = isSummary;
	}

}