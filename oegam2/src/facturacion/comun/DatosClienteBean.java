package facturacion.comun;

import java.text.SimpleDateFormat;
import java.util.List;

import hibernate.entities.facturacion.Factura;
import hibernate.entities.facturacion.FacturaConcepto;

public class DatosClienteBean {
	//Tipo de Factura (F=Factura o R=Rectificada);
	//private String TipoFactura;

	// Recupera los datos del Cliente
	private Factura factura;

	private FacturaConcepto facturaConceptoCargado;

	// Número de decimales
	private String numDecimales;

	// Mensajes para imprimir pdf o borrador
	private String mensajeBorrador;
	private String mensajePDF;
	private String mensajeImprimir;
	private String mensajeClave;

	// Comprobar estado al generar o imprimir PDF
	private String estadoPDF;
	private String tipoPDF;

	private String mensajeXML;
	private String estadoXML;

	private List<String> listaExpedientes; // Lista Expedientes de la factura

	// Doy formato a la fecha actual del sistema
	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

	// Pantalla
	// Se utiliza para saber si estas en la pantalla de Alta o Modificacion, y en función de la pantalla muestras unos u otros botones
	private Boolean isPantallaAlta;
	// Se utiliza para saber si estas si la factura es Nueva o Generada, y en función del estado muestras unos u otros botones
	private Boolean isRectificativa;
	// Se utiliza para saber si mostrar unos u otros botones habilitados y deshabilitados
	private Boolean isError;

	// Clave para modificar
	private String numClave;

	// ******************* GETTERS & SETTERS *****************
	public Boolean getIsError() {
		return isError;
	}
	public void setIsError(Boolean isError) {
		this.isError = isError;
	}

	public Factura getFactura() {
		return factura;
	}
	public void setFactura(Factura factura) {
		this.factura = factura;
	}
	public String getNumDecimales() {
		return numDecimales;
	}
	public void setNumDecimales(String numDecimales) {
		this.numDecimales = numDecimales;
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
	public String getMensajeClave() {
		return mensajeClave;
	}
	public void setMensajeClave(String mensajeClave) {
		this.mensajeClave = mensajeClave;
	}
	public String getEstadoPDF() {
		return estadoPDF;
	}
	public void setEstadoPDF(String estadoPDF) {
		this.estadoPDF = estadoPDF;
	}
	public String getTipoPDF() {
		return tipoPDF;
	}
	public void setTipoPDF(String tipoPDF) {
		this.tipoPDF = tipoPDF;
	}
	public SimpleDateFormat getFormato() {
		return formato;
	}
	public void setFormato(SimpleDateFormat formato) {
		this.formato = formato;
	}
	public Boolean getIsPantallaAlta() {
		return isPantallaAlta;
	}
	public void setIsPantallaAlta(Boolean isPantallaAlta) {
		this.isPantallaAlta = isPantallaAlta;
	}
	public Boolean getIsRectificativa() {
		return isRectificativa;
	}
	public void setIsRectificativa(Boolean isRectificativa) {
		this.isRectificativa = isRectificativa;
	}

	public List<String> getListaExpedientes() {
		return listaExpedientes;
	}
	public void setListaExpedientes(List<String> listaExpedientes) {
		this.listaExpedientes = listaExpedientes;
	}
	public String getNumClave() {
		return numClave;
	}
	public void setNumClave(String numClave) {
		this.numClave = numClave;
	}

	public FacturaConcepto getFacturaConceptoCargado() {
		return facturaConceptoCargado;
	}
	public void setFacturaConceptoCargado(FacturaConcepto facturaConceptoCargado) {
		this.facturaConceptoCargado = facturaConceptoCargado;
	}
	public String getMensajeXML() {
		return mensajeXML;
	}
	public void setMensajeXML(String mensajeXML) {
		this.mensajeXML = mensajeXML;
	}
	public String getEstadoXML() {
		return estadoXML;
	}
	public void setEstadoXML(String estadoXML) {
		this.estadoXML = estadoXML;
	}
}