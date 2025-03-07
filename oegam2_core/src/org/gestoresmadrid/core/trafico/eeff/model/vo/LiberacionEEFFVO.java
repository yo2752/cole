package org.gestoresmadrid.core.trafico.eeff.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="EEFF_LIBERACION")
public class LiberacionEEFFVO implements Serializable{

	private static final long serialVersionUID = 3445724833150200484L;
	
	@Id
	@Column(name="NUM_EXPEDIENTE")
	private BigDecimal numExpediente;
	
	@Column(name="N_FACTURA")
	private String numFactura;
	
	@Column(name="FECHA_FACTURA")
	private Date fechaFactura;
	
	@Column(name="IMPORTE")
	private BigDecimal importe ;
	
	@Column(name="EXENTO")
	private Boolean exento;	
	
	@Column(name="NUM_COLEGIADO")
	private String numColegiado;

	@Column(name="REALIZADO")
	private Boolean realizado;

	@Column(name="FECHA_REALIZACION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRealizacion;

	@Column(name="TARJETA_BASTIDOR")
	private String tarjetaBastidor;

	@Column(name="TARJETA_NIVE")
	private String tarjetaNive;

	@Column(name="FIR_CIF")
	private String firCif;

	@Column(name="FIR_MARCA")
	private String firMarca;

	@Column(name="RESPUESTA")
	private String respuesta;
	
	@Column(name="NIF_REPRESENTADO")
	private String nifRepresentado;
	
	@Column(name="NOMBRE_REPRESENTADO")
	private String nombreRepresentado;
	
	@Column(name="NIF")
	private String nif;

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
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

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public Boolean getExento() {
		return exento;
	}

	public void setExento(Boolean exento) {
		this.exento = exento;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Boolean getRealizado() {
		return realizado;
	}

	public void setRealizado(Boolean realizado) {
		this.realizado = realizado;
	}

	public Date getFechaRealizacion() {
		return fechaRealizacion;
	}

	public void setFechaRealizacion(Date fechaRealizacion) {
		this.fechaRealizacion = fechaRealizacion;
	}

	public String getTarjetaBastidor() {
		return tarjetaBastidor;
	}

	public void setTarjetaBastidor(String tarjetaBastidor) {
		this.tarjetaBastidor = tarjetaBastidor;
	}

	public String getTarjetaNive() {
		return tarjetaNive;
	}

	public void setTarjetaNive(String tarjetaNive) {
		this.tarjetaNive = tarjetaNive;
	}

	public String getFirCif() {
		return firCif;
	}

	public void setFirCif(String firCif) {
		this.firCif = firCif;
	}

	public String getFirMarca() {
		return firMarca;
	}

	public void setFirMarca(String firMarca) {
		this.firMarca = firMarca;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getNifRepresentado() {
		return nifRepresentado;
	}

	public void setNifRepresentado(String nifRepresentado) {
		this.nifRepresentado = nifRepresentado;
	}

	public String getNombreRepresentado() {
		return nombreRepresentado;
	}

	public void setNombreRepresentado(String nombreRepresentado) {
		this.nombreRepresentado = nombreRepresentado;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

}
