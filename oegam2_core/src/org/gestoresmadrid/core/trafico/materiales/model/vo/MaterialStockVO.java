package org.gestoresmadrid.core.trafico.materiales.model.vo;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;

@Entity
@Table(name = "GSM_MATERIAL_STOCK",
       uniqueConstraints={ @UniqueConstraint(columnNames = {"JEFATURA_PROVINCIAL", "MATERIAL_ID"})})
public class MaterialStockVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2542718957217435165L;
	
	@Id
	@SequenceGenerator(name = "material_stock_secuencia", sequenceName = "GSM_ID_MATERIAL_STOCK_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "material_stock_secuencia")
	@Column(name = "MATERIAL_STOCK_ID")
	private Long materialStockId;
	
	@Column(name = "STOCKINV_ID")
	private Long stockInvId;
	
	@Column(name = "UNIDADES")
	private Long unidades;
	
	@Column(name = "OBSERVACIONES")
	private String observaciones;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MATERIAL_ID")
    private MaterialVO materialVO;
	
	@Column(name = "FEC_ULT_RECARGA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecUltRecarga;

	@Column(name = "FEC_ULT_CONSUMO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecUltConsumo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="JEFATURA_PROVINCIAL")
	private JefaturaTraficoVO jefaturaProvincial;
	
	@Column(name="TIPO")
	private String tipo;
	
	
	
	public MaterialVO getMaterialVO() {
		return materialVO;
	}
	public void setMaterialVO(MaterialVO materialVO) {
		this.materialVO = materialVO;
	}
	public JefaturaTraficoVO getJefaturaProvincial() {
		return jefaturaProvincial;
	}
	public void setJefaturaProvincial(JefaturaTraficoVO jefaturaProvincial) {
		this.jefaturaProvincial = jefaturaProvincial;
	}
	public Long getUnidades() {
		return unidades;
	}
	public void setUnidades(Long unidades) {
		this.unidades = unidades;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public Long getMaterialStockId() {
		return materialStockId;
	}
	public void setMaterialStockId(Long materialStockId) {
		this.materialStockId = materialStockId;
	}
	public Long getStockInvId() {
		return stockInvId;
	}
	public void setStockInvId(Long stockInvId) {
		this.stockInvId = stockInvId;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Date getFecUltRecarga() {
		return fecUltRecarga;
	}
	public void setFecUltRecarga(Date fecUltRecarga) {
		this.fecUltRecarga = fecUltRecarga;
	}
	public Date getFecUltConsumo() {
		return fecUltConsumo;
	}
	public void setFecUltConsumo(Date fecUltConsumo) {
		this.fecUltConsumo = fecUltConsumo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((materialVO == null) ? 0 : materialVO.hashCode());
		result = prime * result + ((observaciones == null) ? 0 : observaciones.hashCode());
		result = prime * result + ((stockInvId == null) ? 0 : stockInvId.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((unidades == null) ? 0 : unidades.hashCode());
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
		MaterialStockVO other = (MaterialStockVO) obj;
		
		if (materialVO == null) {
			if (other.materialVO != null)
				return false;
		} else if (!materialVO.equals(other.materialVO))
			return false;
		if (observaciones == null) {
			if (other.observaciones != null)
				return false;
		} else if (!observaciones.equals(other.observaciones))
			return false;
		if (stockInvId == null) {
			if (other.stockInvId != null)
				return false;
		} else if (!stockInvId.equals(other.stockInvId))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (unidades == null) {
			if (other.unidades != null)
				return false;
		} else if (!unidades.equals(other.unidades))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "MaterialStockVO [materialStockId=" + materialStockId + ", stockInvId=" + stockInvId + ", unidades="
				+ unidades + ", observaciones=" + observaciones + ", fecUltRecarga=" + fecUltRecarga
				+ ", fecUltConsumo=" + fecUltConsumo + ", tipo=" + tipo + "]";
	}
	
}
