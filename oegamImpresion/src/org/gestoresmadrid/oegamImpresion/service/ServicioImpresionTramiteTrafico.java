package org.gestoresmadrid.oegamImpresion.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.impresion.model.vo.ImpresionTramiteTraficoVO;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;

public interface ServicioImpresionTramiteTrafico extends Serializable {

	ResultadoImpresionBean crearImpresionTramiteTrafico(String[] numExpedientes, Long idImpresion);

	List<BigDecimal> obtenerNumExpedientes(Long idImpresion);

	List<ImpresionTramiteTraficoVO> obtenerImpresionTramiteTrafico(Long idImpresion);

	void eliminarImpresionTramiteTrafPorIdImpresion(Long idImpresion);
}
