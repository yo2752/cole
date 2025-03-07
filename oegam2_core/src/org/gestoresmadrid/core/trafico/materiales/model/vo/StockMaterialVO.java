package org.gestoresmadrid.core.trafico.materiales.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;

@Entity
@Table(name = "STOCK_MATERIALES")
public class StockMaterialVO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5095485951533980675L;
	
	
	@Id
	@SequenceGenerator(name = "STOCK_MATERIALES_SEQUENCE", sequenceName = "SEQ_ID_STOCK_MATERIALES")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "STOCK_MATERIALES_SEQUENCE")
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "UNIDADES")
	private BigDecimal unidades;
	
	@Column(name="JEFATURA_PROVINCIAL")
	private String jefatura;
	
	@Column(name = "TIPO_MATERIAL")
	private String tipoMaterial;
	 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="JEFATURA_PROVINCIAL", insertable=false, updatable=false)
	private JefaturaTraficoVO jefaturaProvincial;
	


	public BigDecimal getUnidades() {
		return unidades;
	}

	public void setUnidades(BigDecimal unidades) {
		this.unidades = unidades;
	}

	public JefaturaTraficoVO getJefaturaProvincial() {
		return jefaturaProvincial;
	}

	public void setJefaturaProvincial(JefaturaTraficoVO jefaturaProvincial) {
		this.jefaturaProvincial = jefaturaProvincial;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public String getTipoMaterial() {
		return tipoMaterial;
	}

	public void setTipoMaterial(String tipoMaterial) {
		this.tipoMaterial = tipoMaterial;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
