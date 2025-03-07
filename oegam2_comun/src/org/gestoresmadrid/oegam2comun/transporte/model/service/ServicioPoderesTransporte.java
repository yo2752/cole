package org.gestoresmadrid.oegam2comun.transporte.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.transporte.model.vo.PoderTransporteVO;
import org.gestoresmadrid.oegam2comun.transporte.view.beans.ResultadoTransporteBean;
import org.gestoresmadrid.oegam2comun.transporte.view.dto.PoderTransporteDto;

public interface ServicioPoderesTransporte extends Serializable{

	ResultadoTransporteBean generarPoderTransporte(PoderTransporteDto poderTransporte, BigDecimal idContrato, BigDecimal idUsuario);

	ResultadoTransporteBean descargarPdf(PoderTransporteDto poderTransporte);

	PoderTransporteDto getPoderTransporteDto(Long idPoderTransporte);

	PoderTransporteVO getPoderTransporteVO(Long idPoderTransporte);

	ResultadoTransporteBean getPoderTransportePantalla(PoderTransporteDto poderTransporte);

}
