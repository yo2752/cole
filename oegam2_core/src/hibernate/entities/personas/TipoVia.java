package hibernate.entities.personas;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the TIPO_VIA database table.
 * 
 */
@Entity
@Table(name="TIPO_VIA")
@NamedQuery(name="TipoVia.findAll", query="SELECT t FROM TipoVia t")
public class TipoVia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_TIPO_VIA")
	private String idTipoVia;

	@Column(name="ID_TIPO_DGT")
	private String idTipoDgt;

	@Column(name="ID_TIPO_VIA_DGT")
	private String idTipoViaDgt;

	private String nombre;

	private String obsoleto;

	public TipoVia() {
	}

	public String getIdTipoVia() {
		return this.idTipoVia;
	}

	public void setIdTipoVia(String idTipoVia) {
		this.idTipoVia = idTipoVia;
	}

	public String getIdTipoDgt() {
		return this.idTipoDgt;
	}

	public void setIdTipoDgt(String idTipoDgt) {
		this.idTipoDgt = idTipoDgt;
	}

	public String getIdTipoViaDgt() {
		return this.idTipoViaDgt;
	}

	public void setIdTipoViaDgt(String idTipoViaDgt) {
		this.idTipoViaDgt = idTipoViaDgt;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObsoleto() {
		return this.obsoleto;
	}

	public void setObsoleto(String obsoleto) {
		this.obsoleto = obsoleto;
	}

}