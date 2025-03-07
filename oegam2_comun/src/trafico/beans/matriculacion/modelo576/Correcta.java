package trafico.beans.matriculacion.modelo576;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSetter;

import trafico.utiles.constantes.ConstantesAEAT;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class Correcta {

	private static final ILoggerOegam log_ = LoggerOegam.getLogger(Correcta.class);

	@JsonProperty(value = "FormaPago")
	private String formaPago;
	@JsonProperty(value = "CodigoSeguroVerificacion")
	private String codigoSeguroVerificacion;
	@JsonProperty(value = "Fecha")
	private String fecha;
	@JsonProperty(value = "Hora")
	private String hora;
	@JsonProperty(value = "Expediente")
	private String expediente;
	@JsonProperty(value = "NIFPresentador")
	private String nifPresentador;
	@JsonProperty(value = "ApellidosNombrePresentador")
	private String apellidosNombrePresentador;
	@JsonProperty(value = "TipoRepresentacion")
	private String tipoRepresentacion;
	@JsonProperty(value = "NIFDeclarante")
	private String nifDeclarante;
	@JsonProperty(value = "ApellidosNombreDeclarante")
	private String apellidosNombreDeclarante;
	@JsonProperty(value = "Modelo")
	private String modelo;
	@JsonProperty(value = "Ejercicio")
	private String ejercicio;
	@JsonProperty(value = "Periodo")
	private String periodo;
	@JsonProperty(value = "Justificante")
	private String justificante;
	@JsonProperty(value = "NRCPago")
	private String nrcpago;
	@JsonProperty(value = "ImporteAIngresar")
	private String importeaingresar;
	@JsonProperty(value = "Idioma")
	private String idioma;
	@JsonProperty(value = "urlPdf")
	private String urlpPdf;
	@JsonProperty(value = "avisos")
	private List<String> avisos;
	@JsonProperty(value = "advertencias")
	private List<String> advertencias;
	@JsonProperty(value = "Log")
	private String log;

	private String cem;

	public Correcta() {

	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getCodigoSeguroVerificacion() {
		return codigoSeguroVerificacion;
	}

	public void setCodigoSeguroVerificacion(String codigoSeguroVerificacion) {
		this.codigoSeguroVerificacion = codigoSeguroVerificacion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getExpediente() {
		return expediente;
	}

	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

	public String getNifPresentador() {
		return nifPresentador;
	}

	public void setNifPresentador(String nifPresentador) {
		this.nifPresentador = nifPresentador;
	}

	public String getApellidosNombrePresentador() {
		return apellidosNombrePresentador;
	}

	public void setApellidosNombrePresentador(String apellidosNombrePresentador) {
		this.apellidosNombrePresentador = apellidosNombrePresentador;
	}

	public String getTipoRepresentacion() {
		return tipoRepresentacion;
	}

	public void setTipoRepresentacion(String tipoRepresentacion) {
		this.tipoRepresentacion = tipoRepresentacion;
	}

	public String getNifDeclarante() {
		return nifDeclarante;
	}

	public void setNifDeclarante(String nifDeclarante) {
		this.nifDeclarante = nifDeclarante;
	}

	public String getApellidosNombreDeclarante() {
		return apellidosNombreDeclarante;
	}

	public void setApellidosNombreDeclarante(String apellidosNombreDeclarante) {
		this.apellidosNombreDeclarante = apellidosNombreDeclarante;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(String ejercicio) {
		this.ejercicio = ejercicio;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getJustificante() {
		return justificante;
	}

	public void setJustificante(String justificante) {
		this.justificante = justificante;
	}

	public String getNrcpago() {
		return nrcpago;
	}

	public void setNrcpago(String nrcpago) {
		this.nrcpago = nrcpago;
	}

	public String getImporteaingresar() {
		return importeaingresar;
	}

	public void setImporteaingresar(String importeaingresar) {
		this.importeaingresar = importeaingresar;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getUrlpPdf() {
		return urlpPdf;
	}

	public void setUrlpPdf(String urlpPdf) {
		this.urlpPdf = urlpPdf;
	}

	public List<String> getAvisos() {
		return avisos;
	}

	public void setAvisos(List<String> avisos) {
		this.avisos = avisos;
	}

	public List<String> getAdvertencias() {
		return advertencias;
	}

	public void setAdvertencias(List<String> advertencias) {
		this.advertencias = advertencias;
	}

	public String getLog() {
		return log;
	}

	@JsonSetter("Log")
	public void setLog(String log) {
		this.log = log;
		// Se intenta extraer el Código Electrónico para la Matriculación del campo Log
		try {
			this.cem = log.substring(ConstantesAEAT.INICIO_CADENA_CEM, ConstantesAEAT.FINAL_CADENA_CEM);
		} catch (Exception e) {
			log_.error("Ocurrió un error al extraer el CEM del valor del campo LOG del JSON de respuesta", e);
		}
	}

	public String getCem() {
		return cem;
	}

	public void setCem(String cem) {
		this.cem = cem;
	}

}
