package trafico.beans;
 
import java.math.BigDecimal;

import escrituras.utiles.enumerados.Decision;

import trafico.utiles.enumerados.EstadoJustificante;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.estructuras.Fecha;
 
public class JustificanteProfesional{
 
 
	private BigDecimal id_justificante;
 
	private BigDecimal num_expediente;
 
	private BigDecimal dias_validez;
 
	private String documentos;
 
	private Fecha fecha_inicio;
 
	private Fecha fecha_fin;
 
	private String matricula;
	
	private BigDecimal id_justificante_interno;
	
	private TipoTramiteTrafico tipoTramite; 
	
	private EstadoJustificante estado;
	
	private Boolean verificado;
	
	private String codigoVerificacion;
	
	public EstadoJustificante getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = EstadoJustificante.convertir(estado);
	}
	
	public BigDecimal getId_justificante(){
		return id_justificante;}
 
	public void setId_justificante(BigDecimal Id_justificante){
		this.id_justificante=Id_justificante;}
 
	public BigDecimal getNum_expediente(){
		return num_expediente;}
 
	public void setNum_expediente(BigDecimal Num_expediente){
		this.num_expediente=Num_expediente;}
 
	public BigDecimal getDias_validez(){
		return dias_validez;}
 
	public void setDias_validez(BigDecimal Dias_validez){
		this.dias_validez=Dias_validez;}
 
	public String getDocumentos(){
		return documentos;}
 
	public void setDocumentos(String Documentos){
		this.documentos=Documentos;}

	public Fecha getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(Fecha fechaInicio) {
		fecha_inicio = fechaInicio;
	}

	public Fecha getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(Fecha fechaFin) {
		fecha_fin = fechaFin;
	}
	
	public TipoTramiteTrafico getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = TipoTramiteTrafico.convertir(tipoTramite);
	}

	public String getMatricula(){
		return matricula;
	}
	
	public void setMatricula(String matricula){
		this.matricula = matricula;
	}
	
	public BigDecimal getId_justificante_interno() {
		return id_justificante_interno;
	}

	public void setId_justificante_interno(BigDecimal id_justificante_interno) {
		this.id_justificante_interno = id_justificante_interno;
	}

	public Boolean getVerificado() {
		return verificado;
	}

	public void setVerificado(String verificado) {
		this.verificado = Decision.Si.getNombreBD().equals(verificado)? true:false;
	}

	public void setVerificado(Boolean verificado) {
		this.verificado = verificado;	
	}
	
	public String getCodigoVerificacion() {
		return codigoVerificacion;
	}

	public void setCodigoVerificacion(String codigoVerificacion) {
		this.codigoVerificacion = codigoVerificacion;
	}
}