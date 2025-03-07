package org.gestoresmadrid.oegamComun.impr.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegamComun.impr.view.bean.ResultadoImprBean;

public interface ServicioImprFichas extends Serializable{

	void generarColaExpedienteImprNocturno(List<BigDecimal> listaExpedientesImprFCT, Long idContrato, String tipoTramite, Long idUsuario, String jefatura, String referenciaDocumento,
			Boolean esEntornoAm, ResultadoImprBean resultado);

}
