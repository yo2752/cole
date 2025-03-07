package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TIPO_OTRO_IMPORTE database table.
 * 
 */
@Entity
@Table(name="TIPO_OTRO_IMPORTE")
public class TipoOtroImporteVO implements Serializable {

	private static final long serialVersionUID = 6787090538987204061L;

	@Id
	private String codigo;

	private String nombre;

	public TipoOtroImporteVO() {
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}