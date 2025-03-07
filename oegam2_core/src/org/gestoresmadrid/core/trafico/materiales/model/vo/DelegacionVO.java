package org.gestoresmadrid.core.trafico.materiales.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;

@Entity
@Table(name = "GSM_DELEGACION")
public class DelegacionVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -61479284063606266L;

	@Id
	@Column(name = "DELEGACION_ID")
	private Long   delegacionId;
	
	@Column(name = "DELEGACION_DES")
	private String delegacionDes;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="JEFATURA_PROVINCIAL")
	private JefaturaTraficoVO jefaturaProvincial;
	
	public JefaturaTraficoVO getJefaturaProvincial() {
		return jefaturaProvincial;
	}

	public void setJefaturaProvincial(JefaturaTraficoVO jefaturaProvincial) {
		this.jefaturaProvincial = jefaturaProvincial;
	}

	public Long getDelegacionId() {
		return delegacionId;
	}

	public void setDelegacionId(Long delegacionId) {
		this.delegacionId = delegacionId;
	}

	public String getDelegacionDes() {
		return delegacionDes;
	}

	public void setDelegacionDes(String delegacionDes) {
		this.delegacionDes = delegacionDes;
	}

}
