package org.gestoresmadrid.core.modelo600_601.model.vo;

import java.io.Serializable;
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

@Entity
@Table(name = "RESULTADO_MODELOS_600_601")
public class ResultadoModelo600601VO implements Serializable {

	private static final long serialVersionUID = 4699996138617444767L;

	@Id
	@Column(name = "ID_RESULTADO")
	@SequenceGenerator(name = "secuencia_respuesta_modelos", sequenceName = "RESULTADO_MOD_600_601_SS_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "secuencia_respuesta_modelos")
	private Integer idResultado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_MODELO", referencedColumnName="ID_MODELO")
	private Modelo600_601VO modelo600601;
	
	@Column(name = "CODIGO_RESULTADO")
	private String codigoResultado;
	
	@Column(name = "JUSTIFICANTE")
	private String justificante;
	
	@Column(name = "NCCM")
	private String nccm;
	
	@Column(name = "CSO")
	private String cso;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_PRESENTACION")
	private Date fechaPresentacion;
	
	@Column(name = "EXPEDIENTE_CAM")
	private String expedienteCAM;
	
	@Column(name = "CARTA_PAGO")
	private String cartaPago;
	
	@Column(name = "DILIGENCIA")
	private String diligencia;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_EJECUCION")
	private Date fechaEjecucion;

	public Integer getIdResultado() {
		return idResultado;
	}

	public void setIdResultado(Integer idResultado) {
		this.idResultado = idResultado;
	}

	public Modelo600_601VO getModelo600601() {
		return modelo600601;
	}

	public void setModelo600601(Modelo600_601VO modelo600601) {
		this.modelo600601 = modelo600601;
	}

	public String getCodigoResultado() {
		return codigoResultado;
	}

	public void setCodigoResultado(String codigoResultado) {
		this.codigoResultado = codigoResultado;
	}

	public String getJustificante() {
		return justificante;
	}

	public void setJustificante(String justificante) {
		this.justificante = justificante;
	}

	public String getNccm() {
		return nccm;
	}

	public void setNccm(String nccm) {
		this.nccm = nccm;
	}

	public String getCso() {
		return cso;
	}

	public void setCso(String cso) {
		this.cso = cso;
	}

	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public String getExpedienteCAM() {
		return expedienteCAM;
	}

	public void setExpedienteCAM(String expedienteCAM) {
		this.expedienteCAM = expedienteCAM;
	}

	public String getCartaPago() {
		return cartaPago;
	}

	public void setCartaPago(String cartaPago) {
		this.cartaPago = cartaPago;
	}

	public String getDiligencia() {
		return diligencia;
	}

	public void setDiligencia(String diligencia) {
		this.diligencia = diligencia;
	}

	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}

	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

}
