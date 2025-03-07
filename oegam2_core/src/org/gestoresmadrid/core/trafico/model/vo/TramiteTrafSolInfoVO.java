package org.gestoresmadrid.core.trafico.model.vo;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("T4")
public class TramiteTrafSolInfoVO extends TramiteTraficoVO {

	private static final long serialVersionUID = -5340602966527063477L;

	@OneToMany(fetch=FetchType.LAZY, mappedBy = "tramiteTrafico")
	private List<TramiteTrafSolInfoVehiculoVO> solicitudes;

	public List<TramiteTrafSolInfoVehiculoVO> getSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(List<TramiteTrafSolInfoVehiculoVO> solicitudes) {
		this.solicitudes = solicitudes;
	}

}