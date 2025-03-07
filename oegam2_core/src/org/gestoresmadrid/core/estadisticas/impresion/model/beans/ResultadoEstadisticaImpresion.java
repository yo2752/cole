package org.gestoresmadrid.core.estadisticas.impresion.model.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class ResultadoEstadisticaImpresion implements Serializable {
	
	
	private static final long serialVersionUID = 5536734336105223399L;

	private BigDecimal totalDistintivo;
	
	private String tipoDistintivo;
	
	private String jefatura;

	public BigDecimal getTotalDistintivo() {
		return totalDistintivo;
	}

	public void setTotalDistintivo(BigDecimal totalDistintivo) {
		this.totalDistintivo = totalDistintivo;
	}

	public String getTipoDistintivo() {
		return tipoDistintivo;
	}

	public void setTipoDistintivo(String tipoDistintivo) {
		this.tipoDistintivo = tipoDistintivo;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}
	

}
