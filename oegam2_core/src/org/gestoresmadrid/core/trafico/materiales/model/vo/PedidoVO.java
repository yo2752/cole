package org.gestoresmadrid.core.trafico.materiales.model.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;

@Entity
@Table(name = "GSM_PEDIDO")
public class PedidoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8888896332569780250L;
	
	@Id
	@SequenceGenerator(name = "pedido_secuencia", sequenceName = "GSM_ID_PEDIDO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "pedido_secuencia")
	@Column(name = "PEDIDO_ID")
	private Long pedidoId;

	@Column(name = "PEDIDOINV_ID")
	private Long pedidoInvId;
	
	@Column(name = "FECHA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	@Column(name = "CODIGO")
	private String codigo;
	
	@Column(name = "ESTADO")
	private Long estado;
	
	@Column(name = "OBSERVACIONES")
	private String observaciones;
	
	@Column(name = "PEDIDO_DGT")
	private String pedidoDgt;
	
	@Column(name = "CODIGO_INICIAL")
	private String codigoInicial;
	
	@Column(name = "CODIGO_FINAL")
	private String codigoFinal;
	
	//@Transient
	@ManyToOne(fetch = FetchType.LAZY, cascade={CascadeType.PERSIST})
    @JoinColumn(name = "MATERIAL_ID")
    private MaterialVO materialVO;
	
	@Column(name = "UNIDADES")
	private Long unidades;

	//@Transient
	@ManyToOne(fetch = FetchType.LAZY, cascade={CascadeType.PERSIST})
    @JoinColumn(name = "AUTOR_ID")
    private AutorVO autorVO;
	
	//@Transient
	@ManyToOne(fetch = FetchType.LAZY, cascade={CascadeType.PERSIST})
	@JoinColumn(name="JEFATURA_PROVINCIAL")
	private JefaturaTraficoVO jefaturaProvincial;
	
	@Column(name = "TOTAL")
	private Double total;
	
    @OneToMany(mappedBy="pedidoVO")
    private List<PedPaqueteVO> paquetes;

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getCodigoInicial() {
		return codigoInicial;
	}

	public void setCodigoInicial(String codigoInicial) {
		this.codigoInicial = codigoInicial;
	}

	public String getCodigoFinal() {
		return codigoFinal;
	}

	public void setCodigoFinal(String codigoFinal) {
		this.codigoFinal = codigoFinal;
	}

	public JefaturaTraficoVO getJefaturaProvincial() {
		return jefaturaProvincial;
	}

	public void setJefaturaProvincial(JefaturaTraficoVO jefaturaProvincial) {
		this.jefaturaProvincial = jefaturaProvincial;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getPedidoDgt() {
		return pedidoDgt;
	}

	public void setPedidoDgt(String pedidoDgt) {
		this.pedidoDgt = pedidoDgt;
	}
	public Long getPedidoInvId() {
		return pedidoInvId;
	}

	public void setPedidoInvId(Long pedidoInvId) {
		this.pedidoInvId = pedidoInvId;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public MaterialVO getMaterialVO() {
		return materialVO;
	}

	public void setMaterialVO(MaterialVO materialVO) {
		this.materialVO = materialVO;
	}

	public Long getUnidades() {
		return unidades;
	}

	public void setUnidades(Long unidades) {
		this.unidades = unidades;
	}

	public AutorVO getAutorVO() {
		return autorVO;
	}

	public void setAutorVO(AutorVO autorVO) {
		this.autorVO = autorVO;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	public List<PedPaqueteVO> getPaquetes() {
		return paquetes;
	}

	public void setPaquetes(List<PedPaqueteVO> paquetes) {
		this.paquetes = paquetes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((codigoFinal == null) ? 0 : codigoFinal.hashCode());
		result = prime * result + ((codigoInicial == null) ? 0 : codigoInicial.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((materialVO == null) ? 0 : materialVO.hashCode());
		result = prime * result + ((observaciones == null) ? 0 : observaciones.hashCode());
		result = prime * result + ((pedidoDgt == null) ? 0 : pedidoDgt.hashCode());
		result = prime * result + ((pedidoInvId == null) ? 0 : pedidoInvId.hashCode());
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
		PedidoVO other = (PedidoVO) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
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
		if (pedidoDgt == null) {
			if (other.pedidoDgt != null)
				return false;
		} else if (!pedidoDgt.equals(other.pedidoDgt))
			return false;
		if (pedidoInvId == null) {
			if (other.pedidoInvId != null)
				return false;
		} else if (!pedidoInvId.equals(other.pedidoInvId))
			return false;
		if (unidades == null) {
			if (other.unidades != null)
				return false;
		} else if (!unidades.equals(other.unidades))
			return false;
		return true;
	}

}
