package org.gestoresmadrid.core.trafico.materiales.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "GSM_PED_PAQUETE")
public class PedPaqueteVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3702838295240615748L;

	@Id
	@SequenceGenerator(name = "ped_paquete_secuencia", sequenceName = "GSM_ID_PED_PAQ_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ped_paquete_secuencia")
	@Column(name = "PED_PAQUETE_ID")
	private Long pedPaqueteId;
	
	@Column(name = "SERIAL_INICIAL")
    private String numSerieIncial;

	@Column(name = "SERIAL_FINAL")
	private String numSerieFin;

	@Column(name = "SERIAL_ACTUAL")
	private String numSerieActual;

	@Column(name = "ESTADO")
	private Long estado;

	@Column(name = "FECHA_BAJA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaBaja;

	@Column(name = "ID_USUARIO_BAJA")
	private Long usuarioBaja;

	@ManyToOne
    @JoinColumn(name="PEDIDO_ID")
    private PedidoVO pedidoVO;
	
	public Long getPedPaqueteId() {
		return pedPaqueteId;
	}

	public void setPedPaqueteId(Long pedPaqueteId) {
		this.pedPaqueteId = pedPaqueteId;
	}

	public String getNumSerieIncial() {
		return numSerieIncial;
	}

	public void setNumSerieIncial(String numSerieIncial) {
		this.numSerieIncial = numSerieIncial;
	}

	public String getNumSerieFin() {
		return numSerieFin;
	}

	public void setNumSerieFin(String numSerieFin) {
		this.numSerieFin = numSerieFin;
	}

	public String getNumSerieActual() {
		return numSerieActual;
	}

	public void setNumSerieActual(String numSerieActual) {
		this.numSerieActual = numSerieActual;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public Long getUsuarioBaja() {
		return usuarioBaja;
	}

	public void setUsuarioBaja(Long usuarioBaja) {
		this.usuarioBaja = usuarioBaja;
	}

	public PedidoVO getPedidoVO() {
		return pedidoVO;
	}

	public void setPedidoVO(PedidoVO pedidoVO) {
		this.pedidoVO = pedidoVO;
	}

	@Override
	public String toString() {
		return "PedPaqueteVO [pedPaqueteId=" + pedPaqueteId + ", pedidoId=" + pedidoVO.getPedidoId() + ", numSerieIncial="
				+ numSerieIncial + ", numSerieFin=" + numSerieFin + ", numSerieActual=" + numSerieActual + ", estado="
				+ estado + ", fechaBaja=" + fechaBaja + ", usuarioBaja=" + usuarioBaja + "]";
	}

}
