package org.gestoresmadrid.core.importacionFichero.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CONTROL_PETICIONES_IMP")
public class ControlPeticionesImpVO implements Serializable {

	private static final long serialVersionUID = 4484510388046357669L;

	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "TIPO")
	private String tipo;

	@Column(name = "NUMERO_PETTICIONES")
	private Long numeroPeticiones;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getNumeroPeticiones() {
		return numeroPeticiones;
	}

	public void setNumeroPeticiones(Long numeroPeticiones) {
		this.numeroPeticiones = numeroPeticiones;
	}
}
