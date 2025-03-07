package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.personas.model.vo.PersonaVO;

/**
 * The persistent class for the SOCIEDAD_CARGO database table.
 */
@Entity
@Table(name = "SOCIEDAD_CARGO")
public class SociedadCargoVO implements Serializable {

	private static final long serialVersionUID = 6575902047556724563L;

	@EmbeddedId
	private SociedadCargoPK id;

	private Long anios;

	private Long meses;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_INICIO")
	private Date fechaInicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_FIN")
	private Date fechaFin;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_VALIDEZ_INICIAL")
	private Date fechaValidezInicial;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_VALIDEZ_FINAL")
	private Date fechaValidezFinal;

	private String indefinido;

	@Column(name = "LISTA_EJERCICIOS")
	private String listaEjercicios;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JoinColumns({ @JoinColumn(name = "NUM_COLEGIADO", referencedColumnName = "NUM_COLEGIADO", insertable = false, updatable = false),
			@JoinColumn(name = "NIF_CARGO", referencedColumnName = "NIF", insertable = false, updatable = false) })
	private PersonaVO personaCargo;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JoinColumns({ @JoinColumn(name = "NUM_COLEGIADO", referencedColumnName = "NUM_COLEGIADO", insertable = false, updatable = false),
			@JoinColumn(name = "CIF_SOCIEDAD", referencedColumnName = "NIF", insertable = false, updatable = false) })
	private PersonaVO personaSociedad;

	public SociedadCargoPK getId() {
		return id;
	}

	public void setId(SociedadCargoPK id) {
		this.id = id;
	}

	public Long getAnios() {
		return anios;
	}

	public void setAnios(Long anios) {
		this.anios = anios;
	}

	public Long getMeses() {
		return meses;
	}

	public void setMeses(Long meses) {
		this.meses = meses;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaValidezInicial() {
		return fechaValidezInicial;
	}

	public void setFechaValidezInicial(Date fechaValidezInicial) {
		this.fechaValidezInicial = fechaValidezInicial;
	}

	public Date getFechaValidezFinal() {
		return fechaValidezFinal;
	}

	public void setFechaValidezFinal(Date fechaValidezFinal) {
		this.fechaValidezFinal = fechaValidezFinal;
	}

	public String getIndefinido() {
		return indefinido;
	}

	public void setIndefinido(String indefinido) {
		this.indefinido = indefinido;
	}

	public String getListaEjercicios() {
		return listaEjercicios;
	}

	public void setListaEjercicios(String listaEjercicios) {
		this.listaEjercicios = listaEjercicios;
	}

	public PersonaVO getPersonaCargo() {
		return personaCargo;
	}

	public void setPersonaCargo(PersonaVO personaCargo) {
		this.personaCargo = personaCargo;
	}

	public PersonaVO getPersonaSociedad() {
		return personaSociedad;
	}

	public void setPersonaSociedad(PersonaVO personaSociedad) {
		this.personaSociedad = personaSociedad;
	}
}