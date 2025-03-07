package org.gestoresmadrid.core.usoRustico.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USO_RUSTICO")
public class UsoRusticoVO implements Serializable{

	private static final long serialVersionUID = 392252405729555587L;

	@Id
	@Column(name="USO_RUSTICO")
	private String idUsoRustico;

	@Column(name="DESC_USO_RUSTICO")
	private String descUsoRustico;

	@Column(name="TIPO_USO")
	private String tipoUso;

	public String getIdUsoRustico() {
		return idUsoRustico;
	}

	public void setIdUsoRustico(String idUsoRustico) {
		this.idUsoRustico = idUsoRustico;
	}

	public String getDescUsoRustico() {
		return descUsoRustico;
	}

	public void setDescUsoRustico(String descUsoRustico) {
		this.descUsoRustico = descUsoRustico;
	}

	public String getTipoUso() {
		return tipoUso;
	}

	public void setTipoUso(String tipoUso) {
		this.tipoUso = tipoUso;
	}

}