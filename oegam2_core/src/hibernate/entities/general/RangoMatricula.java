package hibernate.entities.general;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the RANGO_MATRICULAS database table.
 * 
 */
@Entity
@Table(name = "RANGO_MATRICULAS")
public class RangoMatricula implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String matricula_num_ini;
	private String matricula_num_fin;
	private String matricula_let_ini;
	private String matricula_let_fin;

	private BigDecimal anyo;

	private String mes;

	public RangoMatricula() {
	}

	public BigDecimal getAnyo() {
		return this.anyo;
	}

	public void setAnyo(BigDecimal anyo) {
		this.anyo = anyo;
	}

	public String getMes() {
		return this.mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getMatricula_num_ini() {
		return matricula_num_ini;
	}

	public void setMatricula_num_ini(String matriculaNumIni) {
		matricula_num_ini = matriculaNumIni;
	}

	public String getMatricula_num_fin() {
		return matricula_num_fin;
	}

	public void setMatricula_num_fin(String matriculaNumFin) {
		matricula_num_fin = matriculaNumFin;
	}

	public String getMatricula_let_ini() {
		return matricula_let_ini;
	}

	public void setMatricula_let_ini(String matriculaLetIni) {
		matricula_let_ini = matriculaLetIni;
	}

	public String getMatricula_let_fin() {
		return matricula_let_fin;
	}

	public void setMatricula_let_fin(String matriculaLetFin) {
		matricula_let_fin = matriculaLetFin;
	}
}