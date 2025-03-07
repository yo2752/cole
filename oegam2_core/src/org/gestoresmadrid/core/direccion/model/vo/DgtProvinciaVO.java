package org.gestoresmadrid.core.direccion.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the DGT_PROVINCIA database table.
 */
@Entity
@Table(name = "DGT_PROVINCIA")
public class DgtProvinciaVO implements Serializable {

	private static final long serialVersionUID = 3153880944667221849L;

	@Id
	@Column(name = "ID_DGT_PROVINCIA")
	private String idDgtProvincia;

	@Column(name = "PREFIJO_TLF")
	private String prefijoTlf;

	private String provincia;

	private String sigla;

	public String getIdDgtProvincia() {
		return idDgtProvincia;
	}

	public void setIdDgtProvincia(String idDgtProvincia) {
		this.idDgtProvincia = idDgtProvincia;
	}

	public String getPrefijoTlf() {
		return prefijoTlf;
	}

	public void setPrefijoTlf(String prefijoTlf) {
		this.prefijoTlf = prefijoTlf;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
}