package hibernate.entities.general;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * The persistent class for the COLEGIADO database table.
 */
@Entity
public class Colegiado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	private String alias;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO")
	private Usuario usuario;

	@Column(name = "CLAVE_FACTURACION")
	private String claveFacturacion;

	@Column(name = "NUM_COLEGIADO_NACIONAL")
	private String numColegiadoNacional;

	public Colegiado() {}

	public String getNumColegiado() {
		return this.numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getClaveFacturacion() {
		return claveFacturacion;
	}

	public void setClaveFacturacion(String claveFacturacion) {
		this.claveFacturacion = claveFacturacion;
	}

	public String getNumColegiadoNacional() {
		return numColegiadoNacional;
	}

	public void setNumColegiadoNacional(String numColegiadoNacional) {
		this.numColegiadoNacional = numColegiadoNacional;
	}
}