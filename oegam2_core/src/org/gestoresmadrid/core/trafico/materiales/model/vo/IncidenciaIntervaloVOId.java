package org.gestoresmadrid.core.trafico.materiales.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IncidenciaIntervaloVOId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8247337650739942318L;

	@Column(name = "INCIDENCIA_ID")
    private Long incidenciaId;
	
	@Column(name = "NUM_SERIE_INI")
    private String numSerieIni;

	public IncidenciaIntervaloVOId() { super(); }

	public IncidenciaIntervaloVOId(Long incidenciaId, String numSerieIni) {
		super();
		this.incidenciaId = incidenciaId;
		this.numSerieIni = numSerieIni;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((incidenciaId == null) ? 0 : incidenciaId.hashCode());
		result = prime * result + ((numSerieIni == null) ? 0 : numSerieIni.hashCode());
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
		IncidenciaIntervaloVOId other = (IncidenciaIntervaloVOId) obj;
		if (incidenciaId == null) {
			if (other.incidenciaId != null)
				return false;
		} else if (!incidenciaId.equals(other.incidenciaId))
			return false;
		if (numSerieIni == null) {
			if (other.numSerieIni != null)
				return false;
		} else if (!numSerieIni.equals(other.numSerieIni))
			return false;
		return true;
	}

	public Long getIncidenciaId() {
		return incidenciaId;
	}

	public void setIncidenciaId(Long incidenciaId) {
		this.incidenciaId = incidenciaId;
	}

	public String getNumSerieIni() {
		return numSerieIni;
	}

	public void setNumSerieIni(String numSerieIni) {
		this.numSerieIni = numSerieIni;
	}

}
