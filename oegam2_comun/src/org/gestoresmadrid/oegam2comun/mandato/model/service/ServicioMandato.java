package org.gestoresmadrid.oegam2comun.mandato.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.mandato.model.vo.MandatoVO;
import org.gestoresmadrid.oegam2comun.mandato.view.dto.MandatoDto;

import escrituras.beans.ResultBean;

public interface ServicioMandato extends Serializable {

	MandatoVO getMandato(Long idMandato);

	MandatoDto getMandatoDto(Long idMandato);

	ResultBean guardarMandato(MandatoDto mandatoDtoo, BigDecimal idUsuario);

	public ResultBean eliminar(Long idMandato, BigDecimal idUsuario);

	ResultBean eliminarVariosMandatos(String[] idMandatos, BigDecimal idUsuario);
}
