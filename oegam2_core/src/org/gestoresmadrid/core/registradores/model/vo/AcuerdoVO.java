package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the ACUERDO database table.
 */
@Entity
@Table(name = "ACUERDO")
public class AcuerdoVO implements Serializable {

	private static final long serialVersionUID = 1789828322915779449L;

	@Id
	@SequenceGenerator(name = "acuerdo_secuencia", sequenceName = "ID_ACUERDO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "acuerdo_secuencia")
	@Column(name = "ID_ACUERDO")
	private Long idAcuerdo;

	@Column(name = "ACEPTACION")
	private String aceptacion;

	@Column(name = "CIF_SOCIEDAD")
	private String cifSociedad;

	@Column(name = "CIRCUNSTANCIA")
	private String circunstancia;

	@Column(name = "CODIGO_CARGO")
	private String codigoCargo;

	@Column(name = "FACULTADES")
	private String facultades;

	@Column(name = "NIF_CARGO")
	private String nifCargo;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	@Column(name = "TIPO_ACUERDO")
	private String tipoAcuerdo;

	@Column(name = "ID_REUNION")
	private Long idReunion;

	@Column(name = "ID_TRAMITE_REGISTRO")
	private BigDecimal idTramiteRegistro;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumns({ @JoinColumn(name = "NIF_CARGO", referencedColumnName = "NIF_CARGO", insertable = false, updatable = false),
			@JoinColumn(name = "CIF_SOCIEDAD", referencedColumnName = "CIF_SOCIEDAD", insertable = false, updatable = false),
			@JoinColumn(name = "CODIGO_CARGO", referencedColumnName = "CODIGO_CARGO", insertable = false, updatable = false),
			@JoinColumn(name = "NUM_COLEGIADO", referencedColumnName = "NUM_COLEGIADO", insertable = false, updatable = false) })
	private SociedadCargoVO sociedadCargo;

	public Long getIdAcuerdo() {
		return idAcuerdo;
	}

	public void setIdAcuerdo(Long idAcuerdo) {
		this.idAcuerdo = idAcuerdo;
	}

	public String getAceptacion() {
		return aceptacion;
	}

	public void setAceptacion(String aceptacion) {
		this.aceptacion = aceptacion;
	}

	public String getCifSociedad() {
		return cifSociedad;
	}

	public void setCifSociedad(String cifSociedad) {
		this.cifSociedad = cifSociedad;
	}

	public String getCircunstancia() {
		return circunstancia;
	}

	public void setCircunstancia(String circunstancia) {
		this.circunstancia = circunstancia;
	}

	public String getCodigoCargo() {
		return codigoCargo;
	}

	public void setCodigoCargo(String codigoCargo) {
		this.codigoCargo = codigoCargo;
	}

	public String getFacultades() {
		return facultades;
	}

	public void setFacultades(String facultades) {
		this.facultades = facultades;
	}

	public String getNifCargo() {
		return nifCargo;
	}

	public void setNifCargo(String nifCargo) {
		this.nifCargo = nifCargo;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getTipoAcuerdo() {
		return tipoAcuerdo;
	}

	public void setTipoAcuerdo(String tipoAcuerdo) {
		this.tipoAcuerdo = tipoAcuerdo;
	}

	public Long getIdReunion() {
		return idReunion;
	}

	public void setIdReunion(Long idReunion) {
		this.idReunion = idReunion;
	}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}

	public SociedadCargoVO getSociedadCargo() {
		return sociedadCargo;
	}

	public void setSociedadCargo(SociedadCargoVO sociedadCargo) {
		this.sociedadCargo = sociedadCargo;
	}
}