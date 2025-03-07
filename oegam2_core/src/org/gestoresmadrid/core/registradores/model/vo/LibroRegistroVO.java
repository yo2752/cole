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
@Table(name = "LIBRO_REGISTRO")
public class LibroRegistroVO implements Serializable {

	private static final long serialVersionUID = 5643857176572790264L;

	@Id
	@SequenceGenerator(name = "libro_registro_secuencia", sequenceName = "LIBRO_REGISTRO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "libro_registro_secuencia")
	@Column(name = "ID_LIBRO")
	private long idLibro;

	@Column(name = "NOMBRE")
	private String nombreLibro;

	@Column(name = "NOMBRE_FICHERO")
	private String nombreFichero;

	@Column(name = "NUMERO")
	private BigDecimal numero;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_APERTURA")
	private Date fecApertura;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_CIERRE")
	private Date fecCierre;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_CIERRE_ANTERIOR")
	private Date fecCierreAnterior;

	@Column(name = "ID_TRAMITE_REGISTRO")
	private BigDecimal idTramiteRegistro;

	// bi-directional many-to-one association to tramiteRegRbm
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TRAMITE_REGISTRO", referencedColumnName = "ID_TRAMITE_REGISTRO", insertable = false, updatable = false)
	private TramiteRegistroVO tramiteRegistro;

	public LibroRegistroVO() {}

	public long getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(long idLibro) {
		this.idLibro = idLibro;
	}

	public String getNombreLibro() {
		return nombreLibro;
	}

	public void setNombreLibro(String nombreLibro) {
		this.nombreLibro = nombreLibro;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public BigDecimal getNumero() {
		return numero;
	}

	public void setNumero(BigDecimal numero) {
		this.numero = numero;
	}

	public Date getFecApertura() {
		return fecApertura;
	}

	public void setFecApertura(Date fecApertura) {
		this.fecApertura = fecApertura;
	}

	public Date getFecCierre() {
		return fecCierre;
	}

	public void setFecCierre(Date fecCierre) {
		this.fecCierre = fecCierre;
	}

	public Date getFecCierreAnterior() {
		return fecCierreAnterior;
	}

	public void setFecCierreAnterior(Date fecCierreAnterior) {
		this.fecCierreAnterior = fecCierreAnterior;
	}

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

}