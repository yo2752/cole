package org.gestoresmadrid.core.trafico.materiales.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "GSM_INCI_INTER")
public class IncidenciaIntervaloVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4085379274475015948L;

	@EmbeddedId
	IncidenciaIntervaloVOId pk;	

    @ManyToOne
    @JoinColumn(name="INCIDENCIA_ID", insertable = false, updatable = false)
    private IncidenciaVO incidenciaVO;

    @Column(name = "NUM_SERIE_FIN")
	private String numSerieFin;

	public IncidenciaIntervaloVOId getPk() {
		return pk;
	}

	public void setPk(IncidenciaIntervaloVOId pk) {
		this.pk = pk;
	}

	public String getNumSerieFin() {
		return numSerieFin;
	}

	public void setNumSerieFin(String numSerieFin) {
		this.numSerieFin = numSerieFin;
	}

	public IncidenciaVO getIncidenciaVO() {
		return incidenciaVO;
	}

	public void setIncidenciaVO(IncidenciaVO incidenciaVO) {
		this.incidenciaVO = incidenciaVO;
	}
}
