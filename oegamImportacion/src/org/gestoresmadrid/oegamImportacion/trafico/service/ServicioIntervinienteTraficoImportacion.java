package org.gestoresmadrid.oegamImportacion.trafico.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoBean;

public interface ServicioIntervinienteTraficoImportacion extends Serializable {

	public static String INTERVINIENTE = "INTERVINIENTE";

	public static String CONVERSION_CONSULTA_INTERVINIENTE = "consultaInterviniente";

	ResultadoBean guardarInterviniente(IntervinienteTraficoVO intervTrafVO, BigDecimal numExpediente, ContratoVO contrato, Long idUsuario, String tipoTramite);

	IntervinienteTraficoVO getIntervinienteTrafico(BigDecimal numExpediente, String tipoInterviniente, String nif, String numColegiado);
}
