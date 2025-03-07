package org.gestoresmadrid.core.pegatinas.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PEGATINAS_EVOLUCION")
public class PegatinasEvolucionVO implements Serializable {

	private static final long serialVersionUID = -2958855025276048714L;

	public PegatinasEvolucionVO() {
	}

	@Id
	@Column(name = "ID_EVOLUCION")
	@SequenceGenerator(name = "secuencia_pegatinas_evo", sequenceName = "PEGATINAS_EVOLUCION_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "secuencia_pegatinas_evo")
	private Integer idEvolucion;

	@Column(name = "ID_PEGATINA")
	private Integer idPegatina;
	
	@Column(name = "ESTADO")
	private String estado;
	
	@Column(name = "FECHA")
	private Date fecha;
	
	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	public Integer getIdEvolucion() {
		return idEvolucion;
	}

	public void setIdEvolucion(Integer idEvolucion) {
		this.idEvolucion = idEvolucion;
	}
	
	public Integer getIdPegatina() {
		return idPegatina;
	}

	public void setIdPegatina(Integer idPegatina) {
		this.idPegatina = idPegatina;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	
}