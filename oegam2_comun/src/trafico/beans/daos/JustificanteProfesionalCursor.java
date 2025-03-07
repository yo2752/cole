package trafico.beans.daos;

import java.math.BigDecimal;

import java.sql.Timestamp;

public class JustificanteProfesionalCursor {

	private BigDecimal id_justificante;

	private BigDecimal num_expediente;

	private BigDecimal dias_validez;

	private String tipo_tramite;

	private Timestamp fecha_inicio;

	private Timestamp fecha_fin;

	private String documentos;

	private String matricula;

	private BigDecimal estado;

	public BigDecimal getId_justificante() {
		return id_justificante;
	}

	public void setId_justificante(BigDecimal Id_justificante) {
		this.id_justificante = Id_justificante;
	}

	public BigDecimal getNum_expediente() {
		return num_expediente;
	}

	public void setNum_expediente(BigDecimal Num_expediente) {
		this.num_expediente = Num_expediente;
	}

	public BigDecimal getDias_validez() {
		return dias_validez;
	}

	public void setDias_validez(BigDecimal Dias_validez) {
		this.dias_validez = Dias_validez;
	}

	public String getDocumentos() {
		return documentos;
	}

	public void setDocumentos(String Documentos) {
		this.documentos = Documentos;
	}

	public Timestamp getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(Timestamp Fecha_inicio) {
		this.fecha_inicio = Fecha_inicio;
	}

	public Timestamp getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(Timestamp Fecha_fin) {
		this.fecha_fin = Fecha_fin;
	}

	public String getTipo_tramite() {
		return tipo_tramite;
	}

	public void setTipo_tramite(String tipoTramite) {
		tipo_tramite = tipoTramite;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

}