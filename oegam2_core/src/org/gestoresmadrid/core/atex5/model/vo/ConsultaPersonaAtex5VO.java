package org.gestoresmadrid.core.atex5.model.vo;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;

@Entity
@Table(name="CONSULTA_PERSONA_ATEX5")
public class ConsultaPersonaAtex5VO implements Serializable{

	private static final long serialVersionUID = -2232999764700788044L;

	@Id
	@Column(name = "ID_CONSULTA_PERSONA")
	@SequenceGenerator(name = "consulta_persona_atex5_secuencia", sequenceName = "ID_CONSULTA_PERSONA_ATEX5_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "consulta_persona_atex5_secuencia")
	private Long idConsultaPersonaAtex5;
	
	@Column(name="ID_CONSULTA_MASIVA")
	private Long idConsultaMasivaAtex5;
	
	@Column(name="NUM_COLEGIADO")
	private String numColegiado;
	
	@Column(name="NUM_EXPEDIENTE")
	private BigDecimal numExpediente;
	
	@Column(name="ID_CONTRATO")
	private Long idContrato;
	
	@Column(name="NIF")
	private String nif;
	
	@Column(name="APELLIDO1")
	private String apellido1;
	
	@Column(name="RAZON_SOCIAL")
	private String razonSocial;
	
	@Column(name="APELLIDO2")
	private String apellido2;
	
	@Column(name="NOMBRE")
	private String nombre;
	
	@Column(name="ANIO_NAC")
	private String anioNacimiento;
	
	@Column(name="FECHA_NAC")
	private Date fechaNacimiento;
	
	@Column(name="CODIGO_TASA")
	private String codigoTasa;
	
	@Column(name="ESTADO")
	private BigDecimal estado;
	
	@Column(name="FECHA_ALTA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlta;
	
	@Column(name="RESPUESTA")
	private String respuesta;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CONTRATO",insertable=false,updatable=false)
	private ContratoVO contrato;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CODIGO_TASA",insertable=false,updatable=false)
	private TasaVO tasa;

	public Long getIdConsultaPersonaAtex5() {
		return idConsultaPersonaAtex5;
	}

	public void setIdConsultaPersonaAtex5(Long idConsultaPersonaAtex5) {
		this.idConsultaPersonaAtex5 = idConsultaPersonaAtex5;
	}

	public Long getIdConsultaMasivaAtex5() {
		return idConsultaMasivaAtex5;
	}

	public void setIdConsultaMasivaAtex5(Long idConsultaMasivaAtex5) {
		this.idConsultaMasivaAtex5 = idConsultaMasivaAtex5;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAnioNacimiento() {
		return anioNacimiento;
	}

	public void setAnioNacimiento(String anioNacimiento) {
		this.anioNacimiento = anioNacimiento;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
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

	public ContratoVO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}

	public TasaVO getTasa() {
		return tasa;
	}

	public void setTasa(TasaVO tasa) {
		this.tasa = tasa;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

}
