package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.registradores.model.vo.MedioConvocatoriaVO;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.MedioConvocatoriaDto;

import escrituras.beans.ResultBean;

public interface ServicioMedioConvocatoria extends Serializable {

	MedioConvocatoriaVO getMediosConvocatoria(Long idMedio, BigDecimal idTramiteRegistro, Long idReunion);

	List<MedioConvocatoriaDto> getMediosConvocatorias(BigDecimal idTramiteRegistro);

	ResultBean guardarMedioConvocatoria(MedioConvocatoriaDto medioConvocatoriaDto, BigDecimal idTramiteRegistro, Long idReunion);

	void eliminarMedio(Long idMedio, BigDecimal idTramiteRegistro, Long idReunion);
}
