package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the REUNION database table.
 */
@Entity
@Table(name = "REUNION")
public class ReunionVO implements Serializable {

	private static final long serialVersionUID = -6742060503350210797L;

	@Id
	@SequenceGenerator(name = "reunion_secuencia", sequenceName = "ID_REUNION_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "reunion_secuencia")
	@Column(name = "ID_REUNION")
	private Long idReunion;

	@Column(name = "AMBITO")
	private String ambito;

	@Column(name = "CARACTER")
	private String caracter;

	@Column(name = "CONTENIDO_CONVO")
	private String contenidoConvo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA")
	private Date fecha;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_APRO_ACTA")
	private Date fechaAproActa;

	@Column(name = "FORMA_APRO_ACTA")
	private String formaAproActa;

	@Column(name = "LUGAR")
	private String lugar;

	@Column(name = "PORCENTAJE_CAPITAL")
	private Long porcentajeCapital;

	@Column(name = "PORCENTAJE_SOCIOS")
	private Long porcentajeSocios;

	@Column(name = "TIPO_REUNION")
	private String tipoReunion;

	@Column(name = "ID_TRAMITE_REGISTRO")
	private BigDecimal idTramiteRegistro;
	
	@Transient
	private ConvocatoriaVO convocatoria;

	@Transient
	private List<MedioConvocatoriaVO> medios;
	
	public Long getIdReunion() {
		return idReunion;
	}

	public void setIdReunion(Long idReunion) {
		this.idReunion = idReunion;
	}

	public String getAmbito() {
		return ambito;
	}

	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

	public String getCaracter() {
		return caracter;
	}

	public void setCaracter(String caracter) {
		this.caracter = caracter;
	}

	public String getContenidoConvo() {
		return contenidoConvo;
	}

	public void setContenidoConvo(String contenidoConvo) {
		this.contenidoConvo = contenidoConvo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechaAproActa() {
		return fechaAproActa;
	}

	public void setFechaAproActa(Date fechaAproActa) {
		this.fechaAproActa = fechaAproActa;
	}

	public String getFormaAproActa() {
		return formaAproActa;
	}

	public void setFormaAproActa(String formaAproActa) {
		this.formaAproActa = formaAproActa;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public Long getPorcentajeCapital() {
		return porcentajeCapital;
	}

	public void setPorcentajeCapital(Long porcentajeCapital) {
		this.porcentajeCapital = porcentajeCapital;
	}

	public Long getPorcentajeSocios() {
		return porcentajeSocios;
	}

	public void setPorcentajeSocios(Long porcentajeSocios) {
		this.porcentajeSocios = porcentajeSocios;
	}

	public String getTipoReunion() {
		return tipoReunion;
	}

	public void setTipoReunion(String tipoReunion) {
		this.tipoReunion = tipoReunion;
	}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}

	public ConvocatoriaVO getConvocatoria() {
		return convocatoria;
	}

	public void setConvocatoria(ConvocatoriaVO convocatoria) {
		this.convocatoria = convocatoria;
	}

	public List<MedioConvocatoriaVO> getMedios() {
		return medios;
	}

	public void setMedios(List<MedioConvocatoriaVO> medios) {
		this.medios = medios;
	}
}