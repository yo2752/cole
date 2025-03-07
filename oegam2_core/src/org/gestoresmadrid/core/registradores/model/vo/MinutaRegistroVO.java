package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the DATO_FIRMA database table.
 */
@Entity
@Table(name = "MINUTA_REGISTRO")
public class MinutaRegistroVO implements Serializable {

	private static final long serialVersionUID = -8888542386395656311L;

	@Id
	@SequenceGenerator(name = "minuta_registro_secuencia", sequenceName = "MINUTA_REGISTRO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "minuta_registro_secuencia")
	@Column(name = "ID_MINUTA")
	private long idMinuta;

	@Column(name = "ACEPTADA")
	private String aceptada;

	@Column(name = "NUMERO_MINUTA")
	private String numeroMinuta;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_MINUTA")
	private Date fechaMinuta;

	@Column(name = "ID_TRAMITE_REGISTRO")
	private BigDecimal idTramiteRegistro;

	// bi-directional many-to-one association to tramiteRegRbm
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TRAMITE_REGISTRO", referencedColumnName = "ID_TRAMITE_REGISTRO", insertable = false, updatable = false)
	private TramiteRegistroVO tramiteRegistro;

	public MinutaRegistroVO() {}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}

	public TramiteRegistroVO getTramiteRegistro() {
		return tramiteRegistro;
	}

	public void setTramiteRegistro(TramiteRegistroVO tramiteRegistro) {
		this.tramiteRegistro = tramiteRegistro;
	}

	public long getIdMinuta() {
		return idMinuta;
	}

	public void setIdMinuta(long idMinuta) {
		this.idMinuta = idMinuta;
	}

	public String getAceptada() {
		return aceptada;
	}

	public void setAceptada(String aceptada) {
		this.aceptada = aceptada;
	}

	public String getNumeroMinuta() {
		return numeroMinuta;
	}

	public void setNumeroMinuta(String numeroMinuta) {
		this.numeroMinuta = numeroMinuta;
	}

	public Date getFechaMinuta() {
		return fechaMinuta;
	}

	public void setFechaMinuta(Date fechaMinuta) {
		this.fechaMinuta = fechaMinuta;
	}

}