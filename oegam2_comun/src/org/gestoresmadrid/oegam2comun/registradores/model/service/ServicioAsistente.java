package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.registradores.model.vo.AsistenteVO;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.AsistenteDto;

public interface ServicioAsistente extends Serializable {

	AsistenteVO getAsistente(BigDecimal idTramiteRegistro, String cifSociedad, String nifCargo, String codigoCargo, Long idReunion);

	AsistenteDto getAsistenteDto(BigDecimal idTramiteRegistro, String cifSociedad, String nifCargo, String codigoCargo, Long idReunion);

	List<AsistenteDto> getAsistentes(BigDecimal idTramiteRegistro);

	void guardarAsistente(AsistenteDto asistenteDto);

	void eliminarAsistente(BigDecimal idTramiteRegistro, String cifSociedad, String nifCargo, String codigoCargo);
}
