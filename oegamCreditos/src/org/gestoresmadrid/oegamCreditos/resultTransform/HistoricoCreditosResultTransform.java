package org.gestoresmadrid.oegamCreditos.resultTransform;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.transform.ResultTransformer;

public class HistoricoCreditosResultTransform implements ResultTransformer {
	private static final long serialVersionUID = 1L;
	private String tipoTramite;
	private BigDecimal creditosSumados;
	private BigDecimal creditosRestados;

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		HistoricoCreditosResultTransform his = new HistoricoCreditosResultTransform();
		his.setTipoTramite((String) tuple[0]);
		his.setCreditosSumados((BigDecimal) tuple[1]);
		his.setCreditosRestados((BigDecimal) tuple[2]);

		return his;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List transformList(List collection) {
		return collection;
	}

	public BigDecimal getCreditosSumados() {
		return creditosSumados;
	}

	public void setCreditosSumados(BigDecimal creditosSumados) {
		this.creditosSumados = creditosSumados;
	}

	public BigDecimal getCreditosRestados() {
		return creditosRestados;
	}

	public void setCreditosRestados(BigDecimal creditosRestados) {
		this.creditosRestados = creditosRestados;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

}
