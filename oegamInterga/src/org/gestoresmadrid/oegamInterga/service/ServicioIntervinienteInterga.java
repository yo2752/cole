package org.gestoresmadrid.oegamInterga.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.trafico.interga.model.vo.IntervinienteIntergaVO;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;

public interface ServicioIntervinienteInterga extends Serializable {

	IntervinienteIntergaVO getIntervinienteTramite(BigDecimal numExpediente, String tipoTramiteInterga);

	IntervinienteIntergaVO getIntervinienteTrafico(BigDecimal numExpediente, String nif, String numColegiado, String tipoTramiteInterga);

	void eliminar(IntervinienteIntergaVO interviniente, String tipoTramiteInterga);

	ResultadoBean guardarActualizar(IntervinienteIntergaVO intervinientePantalla, String tipoTramiteInterga);

	IntervinienteIntergaVO crearIntervinienteNif(String nif, String numColegiado);
}
