package org.gestoresmadrid.oegam2comun.contrato.model.service;

import java.io.Serializable;

import org.gestoresmadrid.core.contrato.model.vo.CorreoContratoTramiteVO;
import org.gestoresmadrid.oegamComun.contrato.view.dto.CorreoContratoTramiteDto;

import escrituras.beans.ResultBean;

public interface ServicioCorreoContratoTramite extends Serializable {

	ResultBean guardarOActualizarCorreoContratoTramite(CorreoContratoTramiteDto correoContratoTramiteDto);

	ResultBean getCorreosPorContrato(long idContrato);

	void eliminarCorreoContratoTramite(Long idCorreo);

	CorreoContratoTramiteVO getCorreoContratoTramite(Long idCorreo);

	CorreoContratoTramiteDto getCorreoContratoTramiteDto(Long idCorreo);

}