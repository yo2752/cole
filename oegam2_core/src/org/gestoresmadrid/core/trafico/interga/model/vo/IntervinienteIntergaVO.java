package org.gestoresmadrid.core.trafico.interga.model.vo;

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

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;

@Entity
@Table(name = "INTERVINIENTE_INTERGA")
public class IntervinienteIntergaVO implements Serializable, Cloneable {

	private static final long serialVersionUID = -7628642018962489073L;

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "inter_permiso_internacional_secuencia", sequenceName = "INTER_PERM_INTER_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "inter_permiso_internacional_secuencia")
	private Long id;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	@Column(name = "NIF")
	private String nif;

	@Column(name = "TIPO_TRAMITE_INTERGA")
	private String tipoTramiteInterga;

	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	@Column(name = "ID_DIRECCION")
	private Long idDireccion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DIRECCION", referencedColumnName = "ID_DIRECCION", insertable = false, updatable = false)
	private DireccionVO direccion;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumns({ @JoinColumn(name = "NUM_COLEGIADO", referencedColumnName = "NUM_COLEGIADO", insertable = false, updatable = false),
			@JoinColumn(name = "NIF", referencedColumnName = "NIF", insertable = false, updatable = false) })
	private PersonaVO persona;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
	}

	public DireccionVO getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionVO direccion) {
		this.direccion = direccion;
	}

	public PersonaVO getPersona() {
		return persona;
	}

	public void setPersona(PersonaVO persona) {
		this.persona = persona;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getTipoTramiteInterga() {
		return tipoTramiteInterga;
	}

	public void setTipoTramiteInterga(String tipoTramiteInterga) {
		this.tipoTramiteInterga = tipoTramiteInterga;
	}
}