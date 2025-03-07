package facturacion.beans;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaFacturaBean {
	private String numColegiado;
	private String numExpediente;
	private String nif;
	private String numSerie;
	private FechaFraccionada fecha;
	private String numFactura;
	private String numCodigo;
	private String checkPDF;

	private String mensajeBorrador;
	private String mensajePDF;
	private String mensajeXML;
	private String mensajeImprimir;

	private String emisor;
	private String receptor;
	private String importe;

	private String fecAnulada;

	public ConsultaFacturaBean() {

		fecha = new FechaFraccionada();
		checkPDF		= "";
		numColegiado	= "";
		numExpediente	= "";
		nif				= "";
		numSerie		= "";
		numFactura		= "";
		numCodigo		= "";
		mensajeBorrador	= "";
		mensajePDF		= "";
		mensajeImprimir	= "";
		emisor			= "";
		receptor		= "";
		importe			= "";
		fecAnulada		= "";
	}

	/**
	 * @return the numColegiado
	 */
	public String getNumColegiado() {
		return numColegiado;
	}
	/**
	 * @param numColegiado the numColegiado to set
	 */
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	/**
	 * @return the numExpediente
	 */
	public String getNumExpediente() {
		return numExpediente;
	}
	/**
	 * @param numExpediente the numExpediente to set
	 */
	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}
	/**
	 * @return the nif
	 */
	public String getNif() {
		return nif;
	}
	/**
	 * @param nif the nif to set
	 */
	public void setNif(String nif) {
		this.nif = nif;
	}
	/**
	 * @return the numSerie
	 */
	public String getNumSerie() {
		return numSerie;
	}
	/**
	 * @param numSerie the numSerie to set
	 */
	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}
	/**
	 * @return the fechaDesde
	 */
	public FechaFraccionada getFecha() {
		return fecha;
	}
	/**
	 * @param fechaDesde the fechaDesde to set
	 */
	public void setFecha(FechaFraccionada fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return the numFactura
	 */
	public String getNumFactura() {
		return numFactura;
	}
	/**
	 * @param numFactura the numFactura to set
	 */
	public void setNumFactura(String numFactura) {
		this.numFactura = numFactura;
	}
	/**
	 * @return the numCodigo
	 */
	public String getNumCodigo() {
		return numCodigo;
	}
	/**
	 * @param numCodigo the numCodigo to set
	 */
	public void setNumCodigo(String numCodigo) {
		this.numCodigo = numCodigo;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ConsultaFacturaBean [fecha=" + fecha + ", nif=" + nif
				+ ", numCodigo=" + numCodigo + ", numColegiado=" + numColegiado
				+ ", numExpediente=" + numExpediente + ", numFactura="
				+ numFactura + ", numSerie=" + numSerie + "]";
	}
	public String getCheckPDF() {
		return checkPDF;
	}
	public void setCheckPDF(String checkPDF) {
		this.checkPDF = checkPDF;
	}
	public String getMensajeBorrador() {
		return mensajeBorrador;
	}
	public void setMensajeBorrador(String mensajeBorrador) {
		this.mensajeBorrador = mensajeBorrador;
	}
	public String getMensajePDF() {
		return mensajePDF;
	}
	public void setMensajePDF(String mensajePDF) {
		this.mensajePDF = mensajePDF;
	}
	public String getMensajeImprimir() {
		return mensajeImprimir;
	}
	public void setMensajeImprimir(String mensajeImprimir) {
		this.mensajeImprimir = mensajeImprimir;
	}
	public String getEmisor() {
		return emisor;
	}
	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}
	public String getReceptor() {
		return receptor;
	}
	public void setReceptor(String receptor) {
		this.receptor = receptor;
	}
	public String getImporte() {
		return importe;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}
	public String getFecAnulada() {
		return fecAnulada;
	}
	public void setFecAnulada(String fecAnulada) {
		this.fecAnulada = fecAnulada;
	}

	public String getMensajeXML() {
		return mensajeXML;
	}

	public void setMensajeXML(String mensajeXML) {
		this.mensajeXML = mensajeXML;
	}
}