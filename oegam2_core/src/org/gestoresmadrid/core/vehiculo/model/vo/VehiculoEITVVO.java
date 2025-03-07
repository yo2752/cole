package org.gestoresmadrid.core.vehiculo.model.vo;

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

/**
 * The persistent class for the VEHICULO_EITV database table.
 */
@Entity
@Table(name = "VEHICULO_EITV")
public class VehiculoEITVVO implements Serializable, Cloneable {

	private static final long serialVersionUID = 6601784582032318698L;

	@Id
	@SequenceGenerator(name = "vehiculo_eitv_secuencia", sequenceName = "ID_VEHICULO_EITV")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "vehiculo_eitv_secuencia")
	@Column(name = "ID")
	private Long id;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	private String estado;

	private String cif;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public Object clone() {
		Object obj = null;
		try {
			obj = super.clone();
		} catch (CloneNotSupportedException ex) {
			System.out.println(" no se puede duplicar");
		}
		return obj;
	}
}