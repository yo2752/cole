package org.gestoresmadrid.core.trafico.materiales.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IncidenciaSerialVOId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5636750371690754138L;

	@Column(name = "INCIDENCIA_ID")
    private Long incidenciaId;
	
	@Column(name = "NUM_SERIE")
    private String numSerie;

	public IncidenciaSerialVOId() {	super(); }

	public IncidenciaSerialVOId(Long incidenciaId, String numSerie) {
		super();
		this.incidenciaId = incidenciaId;
		this.numSerie = numSerie;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((incidenciaId == null) ? 0 : incidenciaId.hashCode());
		result = prime * result + ((numSerie == null) ? 0 : numSerie.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IncidenciaSerialVOId other = (IncidenciaSerialVOId) obj;
		if (incidenciaId == null) {
			if (other.incidenciaId != null)
				return false;
		} else if (!incidenciaId.equals(other.incidenciaId))
			return false;
		if (numSerie == null) {
			if (other.numSerie != null)
				return false;
		} else if (!numSerie.equals(other.numSerie))
			return false;
		return true;
	}

	public Long getIncidenciaId() {
		return incidenciaId;
	}

	public void setIncidenciaId(Long incidenciaId) {
		this.incidenciaId = incidenciaId;
	}

	public String getNumSerie() {
		return numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}
	
}
