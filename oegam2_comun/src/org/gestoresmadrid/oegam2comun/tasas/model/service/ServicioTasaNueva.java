package org.gestoresmadrid.oegam2comun.tasas.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResultadoConsultaTasasBean;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;

import utilidades.web.OegamExcepcion;

public interface ServicioTasaNueva extends Serializable{

	ResultadoConsultaTasasBean exportarTasas(List<TasaDto> listaTasasDto) throws OegamExcepcion;

	ResultadoConsultaTasasBean desasignarTasaBloque(String idstasaSeleccion, BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal idContrato, Boolean esAdmin);

	ResultadoConsultaTasasBean eliminaTasaBloque(String idstasaSeleccion, BigDecimal idUsuario,BigDecimal idContrato, Boolean esAdmin);

	ResultadoConsultaTasasBean getTasaDto(String idstasaSeleccion);

	TasaVO getTasaVO(String idCodigoTasa);

	ResultadoConsultaTasasBean validarTasaExportacion(TasaDto tasaDto, BigDecimal idContrato, Boolean esAdmin);

	ResultadoConsultaTasasBean generarCertificadoTasa(String idstasaSeleccion, BigDecimal idUsuarioDeSesion);

}
