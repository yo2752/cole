package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.registradores.view.dto.ConvocatoriaDto;

import escrituras.beans.ResultBean;

public interface ServicioConvocatoria extends Serializable {

	ConvocatoriaDto getConvocatoria(BigDecimal idTramiteRegistro, Long idReunion);

	ResultBean guardarConvocatoria(ConvocatoriaDto convocatoria, BigDecimal idTramiteRegistro, Long idReunion);
}
