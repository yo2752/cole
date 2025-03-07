package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.registradores.model.vo.InmuebleVO;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.InmuebleDto;

import escrituras.beans.ResultBean;

public interface ServicioInmueble extends Serializable {

	InmuebleVO getInmueble(Long idInmueble);

	InmuebleDto getInmuebleDto(Long idInmueble);

	List<InmuebleDto> getInmuebles(BigDecimal idTramiteRegistro);

	ResultBean guardarInmueble(InmuebleDto inmueble, BigDecimal idTramiteRegistro);

	void eliminarInmueble(Long idInmueble);

	List<InmuebleDto> getInmuebles(Long idBien);

	void eliminarInmueblesPorExpediente(BigDecimal idTramiteRegistro);
}
