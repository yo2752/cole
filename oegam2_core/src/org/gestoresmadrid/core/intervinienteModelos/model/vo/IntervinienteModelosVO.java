package org.gestoresmadrid.core.intervinienteModelos.model.vo;

import java.io.Serializable;

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
import org.gestoresmadrid.core.modelo600_601.model.vo.Modelo600_601VO;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.remesa.model.vo.RemesaVO;

@Entity
@Table(name="INTERVINIENTE_MODELOS")
public class IntervinienteModelosVO implements Serializable{
	
	private static final long serialVersionUID = 2556090090926582241L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="SEC_ID_INTERVINIENTE")
	@SequenceGenerator(name="SEC_ID_INTERVINIENTE", sequenceName="ID_INTERV_MODELOS_SEQ")
	@Column(name = "ID_INTERVINIENTE")
	private Long idInterviniente;
	
	@Column(name="TIPO_INTERVINIENTE")
	private String tipoInterviniente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_MODELO",referencedColumnName="ID_MODELO",insertable=false,updatable=false)
	private Modelo600_601VO modelo600_601;

	@Column(name="ID_MODELO")
	private Long idModelo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_REMESA",referencedColumnName = "ID_REMESA",insertable=false,updatable=false)
	private RemesaVO remesa;
	
	@Column(name="ID_REMESA")
	private Long idRemesa;
	
	@Column(name = "NIF")
	private String nif;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="NIF",referencedColumnName="NIF",insertable=false,updatable=false),
		@JoinColumn(name="NUM_COLEGIADO",referencedColumnName="NUM_COLEGIADO",insertable=false, updatable=false)})
	private PersonaVO persona;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DIRECCION", referencedColumnName = "ID_DIRECCION",insertable=false,updatable=false)
	private DireccionVO direccion;
	
	@Column(name="ID_DIRECCION")
	private Long idDireccion;
	
	public PersonaVO getPersona() {
		return persona;
	}

	public void setPersona(PersonaVO persona) {
		this.persona = persona;
	}

	public Long getIdInterviniente() {
		return idInterviniente;
	}

	public void setIdInterviniente(Long idInterviniente) {
		this.idInterviniente = idInterviniente;
	}

	public RemesaVO getRemesa() {
		return remesa;
	}

	public void setRemesa(RemesaVO remesa) {
		this.remesa = remesa;
	}

	public String getTipoInterviniente() {
		return tipoInterviniente;
	}

	public void setTipoInterviniente(String tipoInterviniente) {
		this.tipoInterviniente = tipoInterviniente;
	}

	public DireccionVO getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionVO direccion) {
		this.direccion = direccion;
	}

	public Modelo600_601VO getModelo600_601() {
		return modelo600_601;
	}

	public void setModelo600_601(Modelo600_601VO modelo600_601) {
		this.modelo600_601 = modelo600_601;
	}

	public Long getIdModelo() {
		return idModelo;
	}

	public void setIdModelo(Long idModelo) {
		this.idModelo = idModelo;
	}

	public Long getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Long getIdRemesa() {
		return idRemesa;
	}

	public void setIdRemesa(Long idRemesa) {
		this.idRemesa = idRemesa;
	}

}
