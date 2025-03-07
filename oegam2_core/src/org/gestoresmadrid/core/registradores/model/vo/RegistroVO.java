package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the REGISTRO database table.
 */
@Entity
@Table(name = "REGISTRO")
public class RegistroVO implements Serializable {

	private static final long serialVersionUID = -847585495037216492L;

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "registro_secuencia", sequenceName = "REGISTRO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "registro_secuencia")
	private long id;
	
	@Column(name = "ID_REGISTRO")
	private String idRegistro;

	@Column(name = "ID_PROVINCIA")
	private String idProvincia;

	@Column(name = "NOMBRE")
	private String nombre;

	@Column(name = "ESTADO")
	private BigDecimal estado;

	@Column(name = "TIPO")
	private String tipo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(String idRegistro) {
		this.idRegistro = idRegistro;
	}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}