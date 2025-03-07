package hibernate.entities.trafico;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the TIPO_CREACION database table.
 * 
 */
@Entity
@Table(name = "TIPO_CREACION")
public class TipoCreacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_TIPO_CREACION")
	private BigDecimal idTipoCreacion;

	@Column(name = "DESCRIPCION_CREACION")
	private String descripcionCreacion;

	// bi-directional many-to-one association to TramiteTrafico
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_CREACION")
	private List<TramiteTrafico> tramiteTraficos;

	public TipoCreacion() {
	}

	public BigDecimal getIdTipoCreacion() {
		return this.idTipoCreacion;
	}

	public void setIdTipoCreacion(BigDecimal idTipoCreacion) {
		this.idTipoCreacion = idTipoCreacion;
	}

	public String getDescripcionCreacion() {
		return this.descripcionCreacion;
	}

	public void setDescripcionCreacion(String descripcionCreacion) {
		this.descripcionCreacion = descripcionCreacion;
	}

	public List<TramiteTrafico> getTramiteTraficos() {
		return this.tramiteTraficos;
	}

	public void setTramiteTraficos(List<TramiteTrafico> tramiteTraficos) {
		this.tramiteTraficos = tramiteTraficos;
	}

}