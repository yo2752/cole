package org.gestoresmadrid.core.trafico.materiales.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "GSM_EVOLUCION_MATERIAL")
public class EvolucionMaterialVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 358900141207940378L;

	@Id
	@SequenceGenerator(name = "evolucion_material_secuencia", sequenceName = "GSM_ID_EVOLUCION_MATERIAL_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "evolucion_material_secuencia")
	@Column(name = "EVOLUCION_MATERIAL_ID")
	private Long evolucionMaterialId;
	
	@Column(name = "MATERIAL_ID")
	private Long materialId;
	
	@Column(name = "TIPO")
	private String tipo;
	
	@Column(name = "NOMBRE")
	private String name;
	
	@Column(name = "DESCRIPCION")
	private String descripcion;
	
	@Column(name = "PRECIO")
	private Double precio;
	
	@Column(name = "COLOR")
	private String color;
	
	@Column(name="ESTADO")
	private String estado;
	
	@Column(name = "FECHA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlta;

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Long getEvolucionMaterialId() {
		return evolucionMaterialId;
	}

	public void setEvolucionMaterialId(Long evolucionMaterialId) {
		this.evolucionMaterialId = evolucionMaterialId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
