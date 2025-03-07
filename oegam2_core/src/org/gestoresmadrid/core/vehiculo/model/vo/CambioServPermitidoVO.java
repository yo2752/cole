package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CAMBIO_SERVICIO_PERMITIDO")
public class CambioServPermitidoVO implements Serializable{
	
	private static final long serialVersionUID = -6934103648127142449L;

	@EmbeddedId
	CambioServPermitidoPK id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SERVICIO_NUEVO",insertable=false, updatable=false)
	ServicioTraficoVO servicioTraficoNuevo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SERVICIO_NUEVO",insertable=false, updatable=false)
	ServicioTraficoVO servicioTraficoActual;

	public CambioServPermitidoPK getId() {
		return id;
	}

	public void setId(CambioServPermitidoPK id) {
		this.id = id;
	}

	public ServicioTraficoVO getServicioTraficoNuevo() {
		return servicioTraficoNuevo;
	}

	public void setServicioTraficoNuevo(ServicioTraficoVO servicioTraficoNuevo) {
		this.servicioTraficoNuevo = servicioTraficoNuevo;
	}

	public ServicioTraficoVO getServicioTraficoActual() {
		return servicioTraficoActual;
	}

	public void setServicioTraficoActual(ServicioTraficoVO servicioTraficoActual) {
		this.servicioTraficoActual = servicioTraficoActual;
	}
	
}
