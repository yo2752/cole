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

@Entity
@Table(name = "GSM_EVOLUCION_PEDIDO")
public class EvolucionPedidoVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3934965356945850957L;

	@Id
	@SequenceGenerator(name = "Evolucion_Pedido_secuencia", sequenceName = "GSM_ID_EVOLUCION_PEDIDO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "Evolucion_Pedido_secuencia")
	@Column(name = "EVOLUCIONPEDIDO_ID")
	private Long evolucionPedidoId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PEDIDO_ID")
    private PedidoVO pedidoVO;
	
	@Column(name = "PEDIDOINV_ID")
	private Long pedidoInvId;
	
	@Column(name = "ESTADO_INICIAL")
	private Long estadoInicial;
	
	@Column(name = "ESTADO_FINAL")
	private Long estadoFinal;

	@Column(name = "FECHA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	public Long getEvolucionPedidoId() {
		return evolucionPedidoId;
	}

	public void setEvolucionPedidoId(Long evolucionPedidoId) {
		this.evolucionPedidoId = evolucionPedidoId;
	}

	public Long getEstadoInicial() {
		return estadoInicial;
	}

	public void setEstadoInicial(Long estadoInicial) {
		this.estadoInicial = estadoInicial;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public PedidoVO getPedidoVO() {
		return pedidoVO;
	}

	public void setPedidoVO(PedidoVO pedidoVO) {
		this.pedidoVO = pedidoVO;
	}

	public Long getPedidoInvId() {
		return pedidoInvId;
	}

	public void setPedidoInvId(Long pedidoInvId) {
		this.pedidoInvId = pedidoInvId;
	}

	public Long getEstadoFinal() {
		return estadoFinal;
	}

	public void setEstadoFinal(Long estadoFinal) {
		this.estadoFinal = estadoFinal;
	}
	
}
