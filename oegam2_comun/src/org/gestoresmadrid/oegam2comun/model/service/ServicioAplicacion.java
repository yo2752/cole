package org.gestoresmadrid.oegam2comun.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.AplicacionVO;
import org.gestoresmadrid.core.general.model.vo.ContratoAplicacionVO;
import org.gestoresmadrid.oegamComun.accesos.view.dto.AplicacionDto;

import escrituras.beans.ResultBean;

public interface ServicioAplicacion extends Serializable {

	List<AplicacionVO> getAplicacionByCodigo(String codigoAplicacion);

	List<ContratoAplicacionVO> getAplicacionesPorContrato(Long idContrato);

	List<String> getCodigosAplicacionPorContrato(Long idContrato);

	List<AplicacionDto> getAplicaciones();

	ResultBean asociarAplicacionContrato(String codigoAplicacion, BigDecimal idContrato, String numColegiado, boolean asignar);
}
