package org.gestoresmadrid.core.trafico.materiales.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "GSM_INCI_SERIAL")
public class IncidenciaSerialVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4085379274475015948L;
	
	@EmbeddedId
	IncidenciaSerialVOId pk;
	
	@Column(name = "INCIDENCIAINV_ID")
	private Long incidenciaInvId;
	
	@ManyToOne
    @JoinColumn(name="INCIDENCIA_ID", insertable = false, updatable = false)
    private IncidenciaVO incidenciaVO;

	public IncidenciaSerialVOId getPk() {
		return pk;
	}

	public void setPk(IncidenciaSerialVOId pk) {
		this.pk = pk;
	}

	public IncidenciaVO getIncidenciaVO() {
		return incidenciaVO;
	}

	public void setIncidenciaVO(IncidenciaVO incidenciaVO) {
		this.incidenciaVO = incidenciaVO;
	}

    public Long getIncidenciaInvId() {
		return incidenciaInvId;
	}

	public void setIncidenciaInvId(Long incidenciaInvId) {
		this.incidenciaInvId = incidenciaInvId;
	}
	
}
