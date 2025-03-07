package org.gestoresmadrid.core.bien.model.vo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.situacion.model.vo.SituacionVO;

@Entity
@Table(name = "BIEN_URBANO")
@DiscriminatorValue(value = "URBANO")
public class BienUrbanoVO extends BienVO {

	private static final long serialVersionUID = 5071452230864868989L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SITUACION")
	private SituacionVO situacion;

	@Column(name = "ARRENDAMIENTO")
	private BigDecimal arrendamiento;

	@Column(name = "VIVIENDA_PROTECCION_OFICIAL")
	private BigDecimal viviendaProtOficial;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_CONSTRUCCION")
	private Date fechaConstruccion;

	@Column(name = "DUP_TRI")
	private String dupTri;

	@Column(name = "SUPERFICIE_CONSTRUCCION")
	private BigDecimal superficieConst;

	@Column(name = "ANIO_CONTRATO")
	private BigDecimal anioContratacion;

	@Column(name = "DESCALIFICADO")
	private BigDecimal descalificado;

	@Column(name = "PRECIO_MAXIMO")
	private BigDecimal precioMaximo;

	@Column(name = "VIVIENDA_HABITUAL")
	private BigDecimal viHabitual;

	public SituacionVO getSituacion() {
		return situacion;
	}

	public void setSituacion(SituacionVO situacion) {
		this.situacion = situacion;
	}

	public BigDecimal getArrendamiento() {
		return arrendamiento;
	}

	public void setArrendamiento(BigDecimal arrendamiento) {
		this.arrendamiento = arrendamiento;
	}

	public BigDecimal getViviendaProtOficial() {
		return viviendaProtOficial;
	}

	public void setViviendaProtOficial(BigDecimal viviendaProtOficial) {
		this.viviendaProtOficial = viviendaProtOficial;
	}

	public Date getFechaConstruccion() {
		return fechaConstruccion;
	}

	public void setFechaConstruccion(Date fechaConstruccion) {
		this.fechaConstruccion = fechaConstruccion;
	}

	public String getDupTri() {
		return dupTri;
	}

	public void setDupTri(String dupTri) {
		this.dupTri = dupTri;
	}

	public BigDecimal getSuperficieConst() {
		return superficieConst;
	}

	public void setSuperficieConst(BigDecimal superficieConst) {
		this.superficieConst = superficieConst;
	}

	public BigDecimal getAnioContratacion() {
		return anioContratacion;
	}

	public void setAnioContratacion(BigDecimal anioContratacion) {
		this.anioContratacion = anioContratacion;
	}

	public BigDecimal getDescalificado() {
		return descalificado;
	}

	public void setDescalificado(BigDecimal descalificado) {
		this.descalificado = descalificado;
	}

	public BigDecimal getPrecioMaximo() {
		return precioMaximo;
	}

	public void setPrecioMaximo(BigDecimal precioMaximo) {
		this.precioMaximo = precioMaximo;
	}

	public BigDecimal getViHabitual() {
		return viHabitual;
	}

	public void setViHabitual(BigDecimal viHabitual) {
		this.viHabitual = viHabitual;
	}

}
