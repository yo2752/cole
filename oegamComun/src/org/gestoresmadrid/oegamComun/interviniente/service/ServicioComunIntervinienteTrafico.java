package org.gestoresmadrid.oegamComun.interviniente.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;

public interface ServicioComunIntervinienteTrafico extends Serializable {

	IntervinienteTraficoVO getIntervinienteTramite(BigDecimal numExpediente, String tipoInterviniente);

	void eliminarInterviniente(BigDecimal numExpediente, String tipoInterviniente);

	void actualizarIntervinienteInteve(IntervinienteTraficoVO intervinienteTraficoVO);

	List<IntervinienteTraficoVO> getListaIntervinientesExpediente(BigDecimal numExpediente);

	ResultadoBean guardarIntervinienteTrafico(IntervinienteTraficoVO interviniente, Long idUsuario, String tipoTramite, String conversion, String conversionDireccion);

	void eliminarIntervinienteVO(IntervinienteTraficoVO intervinienteTraficoBBDD);
}
