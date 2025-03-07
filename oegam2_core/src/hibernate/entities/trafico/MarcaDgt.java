package hibernate.entities.trafico;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the MARCA_DGT database table.
 * 
 */
@Entity
@Table(name = "MARCA_DGT")
@NamedQuery(name = "MarcaDgt.findAll", query = "SELECT m FROM MarcaDgt m")
public class MarcaDgt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "idMarcaDgtSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "idMarcaDgtSeq", sequenceName = "ID_MARCA_DGT_SEQ", allocationSize = 1)
	@Column(name = "CODIGO_MARCA", unique = true, nullable = false, precision = 22)
	private long codigoMarca;

	@Column(precision = 1)
	private BigDecimal version;

	@Column(nullable = false, length = 100)
	private String marca;

	@Column(name = "MARCA_SIN_EDITAR", length = 100)
	private String marcaSinEditar;

	@Transient
	private boolean mate;
	@Transient
	private boolean matw;
	@Transient
	private String fabricante;

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public MarcaDgt() {
	}

	public long getCodigoMarca() {
		return this.codigoMarca;
	}

	public void setCodigoMarca(long codigoMarca) {
		this.codigoMarca = codigoMarca;
	}

	public BigDecimal getVersion() {
		return this.version;
	}

	public void setVersion(BigDecimal version) {
		this.version = version;
	}

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getMarcaSinEditar() {
		return this.marcaSinEditar;
	}

	public void setMarcaSinEditar(String marcaSinEditar) {
		this.marcaSinEditar = marcaSinEditar;
	}

	public boolean isMate() {
		return mate;
	}

	public void setMate(boolean mate) {
		this.mate = mate;
	}

	public boolean isMatw() {
		return matw;
	}

	public void setMatw(boolean matw) {
		this.matw = matw;
	}

}