package hibernate.entities.general;

import hibernate.entities.trafico.TramiteTrafico;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the JEFATURA_TRAFICO database table.
 * 
 */
@Entity
@Table(name = "JEFATURA_TRAFICO")
public class JefaturaTrafico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "JEFATURA_PROVINCIAL")
	private String jefaturaProvincial;

	private String descripcion;

	private String jefatura;

	private String sucursal;

	// bi-directional many-to-one association to Contrato
	@OneToMany(mappedBy = "jefaturaTrafico")
	private List<Contrato> contratos;

	// bi-directional many-to-one association to Provincia
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PROVINCIA")
	private Provincia provincia;

	// bi-directional many-to-one association to TramiteTrafico
	@OneToMany(mappedBy = "jefaturaTrafico")
	private List<TramiteTrafico> tramiteTraficos;

	public JefaturaTrafico() {
	}

	public String getJefaturaProvincial() {
		return this.jefaturaProvincial;
	}

	public void setJefaturaProvincial(String jefaturaProvincial) {
		this.jefaturaProvincial = jefaturaProvincial;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getJefatura() {
		return this.jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public String getSucursal() {
		return this.sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public List<Contrato> getContratos() {
		return this.contratos;
	}

	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}

	public Provincia getProvincia() {
		return this.provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public List<TramiteTrafico> getTramiteTraficos() {
		return this.tramiteTraficos;
	}

	public void setTramiteTraficos(List<TramiteTrafico> tramiteTraficos) {
		this.tramiteTraficos = tramiteTraficos;
	}

}