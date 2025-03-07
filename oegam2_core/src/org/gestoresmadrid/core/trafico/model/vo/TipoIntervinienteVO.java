package org.gestoresmadrid.core.trafico.model.vo;

import java.io.Serializable;

import javax.persistence.*;

import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.utilidades.listas.ListsOperator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The persistent class for the TIPO_INTERVINIENTE database table.
 */
@Entity
@Table(name = "TIPO_INTERVINIENTE")
public class TipoIntervinienteVO implements Serializable {

	private static final long serialVersionUID = 2615909000159682455L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TIPO_INTERVINIENTE")
	private String tipoInterviniente;

	@Column(name = "DESC_TIPO_INTERV")
	private String descTipoInterv;

	private BigDecimal unico;

	// bi-directional many-to-one association to IntervinienteTrafico
	@OneToMany(mappedBy = "tipoIntervinienteVO")
	private Set<IntervinienteTraficoVO> intervinienteTraficos;

	public TipoIntervinienteVO() {}

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

	public Set<IntervinienteTraficoVO> getIntervinienteTraficos() {
		return intervinienteTraficos;
	}

	public List<IntervinienteTraficoVO> getIntervinienteTraficosAsList() {
		// Map from Set to List
		List<IntervinienteTraficoVO> lista = new ArrayList<>();
		if (intervinienteTraficos != null) {
			lista.addAll(intervinienteTraficos);
		}
		return lista;
	}

	public IntervinienteTraficoVO getFirstElementIntervinienteTrafico() {
		ListsOperator<IntervinienteTraficoVO> listOperator = new ListsOperator<>();
		return listOperator.getFirstElement(getIntervinienteTraficos());
	}

	public void setIntervinienteTraficos(Set<IntervinienteTraficoVO> intervinienteTraficos) {
		this.intervinienteTraficos = intervinienteTraficos;
	}

	public void setIntervinienteTraficos(List<IntervinienteTraficoVO> intervinienteTraficos) {
		if (intervinienteTraficos == null) {
			this.intervinienteTraficos = null;
		} else {
			// Map from List to Set
			Set<IntervinienteTraficoVO> set = new HashSet<IntervinienteTraficoVO>();
			set.addAll(intervinienteTraficos);
			this.intervinienteTraficos = set;
		}
	}
}