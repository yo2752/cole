package hibernate.entities.trafico;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * The persistent class for the TIPO_INTERVINIENTE database table.
 * 
 */
@Entity
@Table(name = "TIPO_INTERVINIENTE")
public class TipoInterviniente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TIPO_INTERVINIENTE")
	private String tipoInterviniente;

	@Column(name = "DESC_TIPO_INTERV")
	private String descTipoInterv;

	private BigDecimal unico;

	// bi-directional many-to-one association to IntervinienteTrafico
	@OneToMany(mappedBy = "tipoIntervinienteBean")
	private List<IntervinienteTrafico> intervinienteTraficos;

	public TipoInterviniente() {
	}

	public String getTipoInterviniente() {
		return this.tipoInterviniente;
	}

	public void setTipoInterviniente(String tipoInterviniente) {
		this.tipoInterviniente = tipoInterviniente;
	}

	public String getDescTipoInterv() {
		return this.descTipoInterv;
	}

	public void setDescTipoInterv(String descTipoInterv) {
		this.descTipoInterv = descTipoInterv;
	}

	public BigDecimal getUnico() {
		return this.unico;
	}

	public void setUnico(BigDecimal unico) {
		this.unico = unico;
	}

	public List<IntervinienteTrafico> getIntervinienteTraficos() {
		return this.intervinienteTraficos;
	}

	public void setIntervinienteTraficos(List<IntervinienteTrafico> intervinienteTraficos) {
		this.intervinienteTraficos = intervinienteTraficos;
	}

}