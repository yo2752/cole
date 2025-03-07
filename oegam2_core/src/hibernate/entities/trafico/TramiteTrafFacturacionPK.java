package hibernate.entities.trafico;

import java.io.Serializable;

import javax.persistence.Column;

public class TramiteTrafFacturacionPK implements Serializable {
	// Default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "NUM_EXPEDIENTE")
	private Long numExpediente;

	@Column(name = "TIPO_TRAMITE")
	private String tipoTramite;

	@Column(name = "CODIGO_TASA")
	private String codigoTasa;

	public TramiteTrafFacturacionPK() {
	}

	public Long getNumExpediente() {
		return this.numExpediente;
	}

	public void setNumExpediente(Long numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getTipoTramite() {
		return this.tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getCodigoTasa() {
		return this.codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TramiteTrafFacturacionPK)) {
			return false;
		}
		TramiteTrafFacturacionPK castOther = (TramiteTrafFacturacionPK) other;
		return (this.numExpediente == castOther.numExpediente) && this.tipoTramite.equals(castOther.tipoTramite)
				&& (this.codigoTasa.equals(castOther.codigoTasa));

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.numExpediente ^ (this.numExpediente >>> 32)));
		hash = hash * prime + this.tipoTramite.hashCode();
		hash = hash * prime + this.codigoTasa.hashCode();

		return hash;
	}
}