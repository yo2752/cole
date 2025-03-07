package org.gestoresmadrid.core.ficheroAEAT.bean;

import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;

public class FicheroAEATBean {

	
	private TramiteTraficoVO tramiteTraficoVO;
	private PersonaVO datosColegiado;
	private PersonaVO datosTitular;
	
	public PersonaVO getDatosTitular() {
		return datosTitular;
	}

	public void setDatosTitular(PersonaVO datosTitular) {
		this.datosTitular = datosTitular;
	}

	public PersonaVO getDatosColegiado() {
		return datosColegiado;
	}

	public void setDatosColegiado(PersonaVO datosColegiado) {
		this.datosColegiado = datosColegiado;
	}

	public TramiteTraficoVO getTramiteTraficoVO() {
		return tramiteTraficoVO;
	}

	public void setTramiteTraficoVO(TramiteTraficoVO tramiteTraficoVO) {
		this.tramiteTraficoVO = tramiteTraficoVO;
	}		
}