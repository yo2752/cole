package hibernate.entities.general;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the TIPO_TRAMITE_RENOVACION database table.
 * 
 */
@Entity
@Table(name = "TIPO_TRAMITE_RENOVACION")
public class TipoTramiteRenovacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_TIPO_TRAMITE_RENOVACION")
	private String idTipoTramiteRenovacion;

	private String descripcion;

	@Column(name = "IMPORTE_LABORABLE")
	private BigDecimal importeLaborable;

	@Column(name = "IMPORTE_NO_LABORABLE")
	private BigDecimal importeNoLaborable;

	// bi-directional many-to-one association to ReconocimientoMedico
	@OneToMany(mappedBy = "tipoTramiteRenovacion", fetch = FetchType.LAZY)
	private List<ReconocimientoMedico> reconocimientos;

	public TipoTramiteRenovacion() {
	}

	public String getIdTipoTramiteRenovacion() {
		return this.idTipoTramiteRenovacion;
	}

	public void setIdTipoTramiteRenovacion(String idTipoTramiteRenovacion) {
		this.idTipoTramiteRenovacion = idTipoTramiteRenovacion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getImporteLaborable() {
		return this.importeLaborable;
	}

	public void setImporteLaborable(BigDecimal importeLaborable) {
		this.importeLaborable = importeLaborable;
	}

	public BigDecimal getImporteNoLaborable() {
		return this.importeNoLaborable;
	}

	public void setImporteNoLaborable(BigDecimal importeNoLaborable) {
		this.importeNoLaborable = importeNoLaborable;
	}

	public List<ReconocimientoMedico> getReconocimientos() {
		return reconocimientos;
	}

	public void setReconocimientos(List<ReconocimientoMedico> reconocimientos) {
		this.reconocimientos = reconocimientos;
	}

}