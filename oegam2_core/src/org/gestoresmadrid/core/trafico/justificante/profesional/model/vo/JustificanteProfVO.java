package org.gestoresmadrid.core.trafico.justificante.profesional.model.vo;

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

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;

/**
 * The persistent class for the JUSTIFICANTE_PROF database table.
 */
@Entity
@Table(name = "JUSTIFICANTE_PROF")
public class JustificanteProfVO implements Serializable {

	private static final long serialVersionUID = 6462742421211854537L;

	@Id
	@Column(name = "ID_JUSTIFICANTE_INTERNO")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "secuencia_justificante")
	@SequenceGenerator(name = "secuencia_justificante", sequenceName = "ID_JUSTIFICANTE_SEQ")
	private Long idJustificanteInterno;

	@Column(name = "CODIGO_VERIFICACION")
	private String codigoVerificacion;

	@Column(name = "DIAS_VALIDEZ")
	private BigDecimal diasValidez;

	private String documentos;

	private BigDecimal estado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_FIN")
	private Date fechaFin;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_INICIO")
	private Date fechaInicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;

	@Column(name = "ID_JUSTIFICANTE")
	private BigDecimal idJustificante;

	private String verificado;

	// bi-directional many-to-one association to TramiteTrafico
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NUM_EXPEDIENTE", referencedColumnName = "NUM_EXPEDIENTE", insertable = false, updatable = false)
	private TramiteTraficoVO tramiteTrafico;

	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	private String observaciones;

	@Column(name = "MOTIVO")
	private String motivo;

	@Column(name = "RESPUESTA")
	private String respuesta;

	@Column(name = "ID_DIRECCION")
	private Long idDireccion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DIRECCION", insertable = false, updatable = false)
	private DireccionVO direccion;

	@Column(name = "NOMBRE")
	private String nombre;

	@Column(name = "APELLIDO1_RAZON_SOCIAL")
	private String apellido1;

	@Column(name = "APELLIDO2")
	private String apellido2;

	@Column(name = "NIF")
	private String nif;

	@Column(name = "ID_CONTRATO")
	private Long idContrato;

	@Column(name = "MARCA")
	private String marca;

	@Column(name = "MODELO")
	private String modelo;

	@Column(name = "MATRICULA")
	private String matricula;

	@Column(name = "BASTIDOR")
	private String bastidor;

	@Column(name = "TIPO_VEHICULO")
	private String tipoVehiculo;

	@Column(name = "REF_PROPIA")
	private String refPropia;

	public JustificanteProfVO() {}

	public Long getIdJustificanteInterno() {
		return this.idJustificanteInterno;
	}

	public void setIdJustificanteInterno(Long idJustificanteInterno) {
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

	public TramiteTraficoVO getTramiteTrafico() {
		return this.tramiteTrafico;
	}

	public void setTramiteTrafico(TramiteTraficoVO tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public DireccionVO getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionVO direccion) {
		this.direccion = direccion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public String getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}

	public Long getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
	}
}