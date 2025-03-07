package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the CONVOCATORIA database table.
 */
@Entity
@Table(name = "CONVOCATORIA")
public class ConvocatoriaVO implements Serializable {

	private static final long serialVersionUID = 111700183332184017L;

	@EmbeddedId
	private ConvocatoriaPK id;

	@Column(name = "NUMERO_CONVO")
	private String numeroConvo;

	@Column(name = "PORCENTAJE_CAPITAL")
	private Long porcentajeCapital;

	@Column(name = "PORCENTAJE_SOCIOS")
	private Long porcentajeSocios;

	public ConvocatoriaPK getId() {
		return id;
	}

	public void setId(ConvocatoriaPK id) {
		this.id = id;
	}

	public String getNumeroConvo() {
		return numeroConvo;
	}

	public void setNumeroConvo(String numeroConvo) {
		this.numeroConvo = numeroConvo;
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
}