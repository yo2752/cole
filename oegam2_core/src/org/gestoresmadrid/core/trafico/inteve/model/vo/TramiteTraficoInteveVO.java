package org.gestoresmadrid.core.trafico.inteve.model.vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;

@Entity
@DiscriminatorValue("T10")
public class TramiteTraficoInteveVO extends TramiteTraficoVO{

	private static final long serialVersionUID = -4726664549634555773L;

	@OneToMany(mappedBy = "tramiteTraficoInteve")
	private Set<TramiteTraficoSolInteveVO> tramitesInteves;

	public Set<TramiteTraficoSolInteveVO> getTramitesInteves() {
		return tramitesInteves;
	}

	public void setTramitesInteves(Set<TramiteTraficoSolInteveVO> tramitesInteves) {
		this.tramitesInteves = tramitesInteves;
	}

	public List<TramiteTraficoSolInteveVO> getTramitesInteveAsList() {
		List<TramiteTraficoSolInteveVO> lista;
		if (tramitesInteves != null) {
			// Map from Set to List
			lista = new ArrayList<TramiteTraficoSolInteveVO>(tramitesInteves);
		} else {
			lista = Collections.emptyList();
		}
		return lista;
	}

	public void setTramitesInteves(List<TramiteTraficoSolInteveVO> tramitesTrafico) {
		if (tramitesTrafico == null) {
			this.tramitesInteves = null;
		} else {
			// Map from List to Set
			this.tramitesInteves = new HashSet<TramiteTraficoSolInteveVO>(tramitesTrafico);
		}
	}
}
