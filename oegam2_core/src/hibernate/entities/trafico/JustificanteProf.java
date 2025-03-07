package hibernate.entities.trafico;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the JUSTIFICANTE_PROF database table.
 * 
 */
@Entity
@Table(name = "JUSTIFICANTE_PROF")
public class JustificanteProf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_JUSTIFICANTE_INTERNO")
	private long idJustificanteInterno;

	@Column(name = "CODIGO_VERIFICACION")
	private String codigoVerificacion;

	@Column(name = "DIAS_VALIDEZ")
	private BigDecimal diasValidez;

	private String documentos;

	@Column(name = "MOTIVO")
	private String motivo;

	private BigDecimal estado;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_FIN")
	private Date fechaFin;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_INICIO")
	private Date fechaInicio;

	@Column(name = "ID_JUSTIFICANTE")
	private BigDecimal idJustificante;

	private String verificado;

	// bi-directional many-to-one association to TramiteTrafico
	@ManyToOne
	@JoinColumn(name = "NUM_EXPEDIENTE")
	private TramiteTrafico tramiteTrafico;

	public JustificanteProf() {
	}

	public long getIdJustificanteInterno() {
		return this.idJustificanteInterno;
	}

	public void setIdJustificanteInterno(long idJustificanteInterno) {
		this.idJustificanteInterno = idJustificanteInterno;
	}

	public String getCodigoVerificacion() {
		return this.codigoVerificacion;
	}

	public void setCodigoVerificacion(String codigoVerificacion) {
		this.codigoVerificacion = codigoVerificacion;
	}

	public BigDecimal getDiasValidez() {
		return this.diasValidez;
	}

	public void setDiasValidez(BigDecimal diasValidez) {
		this.diasValidez = diasValidez;
	}

	public String getDocumentos() {
		return this.documentos;
	}

	public void setDocumentos(String documentos) {
		this.documentos = documentos;
	}

	public BigDecimal getEstado() {
		return this.estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public BigDecimal getIdJustificante() {
		return this.idJustificante;
	}

	public void setIdJustificante(BigDecimal idJustificante) {
		this.idJustificante = idJustificante;
	}

	public String getVerificado() {
		return this.verificado;
	}

	public void setVerificado(String verificado) {
		this.verificado = verificado;
	}

	public TramiteTrafico getTramiteTrafico() {
		return this.tramiteTrafico;
	}

	public void setTramiteTrafico(TramiteTrafico tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

}