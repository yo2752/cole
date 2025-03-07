package org.gestoresmadrid.core.tasas.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TASAS_DGT")
public class TasaDgtVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5888104193764642867L;

	@Id
	@Column(name = "CODIGO_TASA")
	private String codigoTasa;

	@Column(name = "GRUPO")
	private Integer grupo;

	@Column(name = "TIPO")
	private String tipo;

	@Column(name = "TRAMITE")
	private String tramite;

	@Column(name = "DESCRIPCION")
	private String descripcion;

	@Column(name = "IMPORTE")
	private BigDecimal importe;

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public Integer getGrupo() {
		return grupo;
	}

	public void setGrupo(Integer grupo) {
		this.grupo = grupo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTramite() {
		return tramite;
	}

	public void setTramite(String tramite) {
		this.tramite = tramite;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}


}
