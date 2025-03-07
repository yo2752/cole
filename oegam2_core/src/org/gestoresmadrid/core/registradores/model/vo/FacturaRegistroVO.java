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
@Table(name = "FACTURA_REGISTRO")
public class FacturaRegistroVO implements Serializable {

	private static final long serialVersionUID = 7384137888470562571L;

	@Id
	@SequenceGenerator(name = "factura_registro_secuencia", sequenceName = "FACTURA_REGISTRO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "factura_registro_secuencia")
	@Column(name = "ID_FACTURA")
	private long idFactura;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_PAGO")
	private Date fechaPago;

	@Column(name = "ID_TRANSFERENCIA")
	private String idTransferencia;

	@Column(name = "CIF_EMISOR")
	private String cifEmisor;

	@Column(name = "NUM_SERIE")
	private String numSerie;

	@Column(name = "EJERCICIO")
	private String ejercicio;

	@Column(name = "NUM_FACTURA")
	private String numFactura;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_FACTURA")
	private Date fechaFactura;

	@Column(name = "ID_TRAMITE_REGISTRO")
	private BigDecimal idTramiteRegistro;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ENVIO")
	private Date fechaEnvio;

	// bi-directional many-to-one association to tramiteRegRbm
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TRAMITE_REGISTRO", referencedColumnName = "ID_TRAMITE_REGISTRO", insertable = false, updatable = false)
	private TramiteRegistroVO tramiteRegistro;

	public FacturaRegistroVO() {}

	public long getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(long idFactura) {
		this.idFactura = idFactura;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public String getIdTransferencia() {
		return idTransferencia;
	}

	public void setIdTransferencia(String idTransferencia) {
		this.idTransferencia = idTransferencia;
	}

	public String getCifEmisor() {
		return cifEmisor;
	}

	public void setCifEmisor(String cifEmisor) {
		this.cifEmisor = cifEmisor;
	}

	public String getNumSerie() {
		return numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	public String getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(String ejercicio) {
		this.ejercicio = ejercicio;
	}

	public String getNumFactura() {
		return numFactura;
	}

	public void setNumFactura(String numFactura) {
		this.numFactura = numFactura;
	}

	public Date getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
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

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

}