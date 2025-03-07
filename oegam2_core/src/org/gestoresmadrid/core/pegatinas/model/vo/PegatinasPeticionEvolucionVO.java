package org.gestoresmadrid.core.pegatinas.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PEGATINAS_STOCK_PETI_EVO")
public class PegatinasPeticionEvolucionVO implements Serializable {

	private static final long serialVersionUID = -2958855025276048714L;

	public PegatinasPeticionEvolucionVO() {
	}

	@Id
	@Column(name = "ID_EVOLUCION")
	@SequenceGenerator(name = "secuencia_pegatinas_peti_evo", sequenceName = "PEGATINAS_STOCK_PETI_EVO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "secuencia_pegatinas_peti_evo")
	private Integer idEvolucion;

	@Column(name = "ID_STOCK_PETI")
	private Integer idStockPeti;
	
	@Column(name = "ESTADO")
	private String estado;
	
	@Column(name = "FECHA")
	private Date fecha;
	
	@Column(name = "OBSERVACIONES")
	private String observaciones;
	
	public Integer getIdEvolucion() {
		return idEvolucion;
	}

	public void setIdEvolucion(Integer idEvolucion) {
		this.idEvolucion = idEvolucion;
	}

	public Integer getIdStockPeti() {
		return idStockPeti;
	}

	public void setIdStockPeti(Integer idStockPeti) {
		this.idStockPeti = idStockPeti;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
}