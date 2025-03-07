package org.gestoresmadrid.oegam2comun.participacion.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.remesa.model.vo.RemesaVO;
import org.gestoresmadrid.oegam2comun.participacion.view.dto.ParticipacionDto;
import org.gestoresmadrid.oegam2comun.remesa.view.dto.RemesaDto;

import escrituras.beans.ResultBean;

public interface ServicioParticipacion extends Serializable{

	ResultBean guardarCoefParticipacion(String codigoPorct, String porcentajes, Long idRemesa);

	ResultBean convertirListaCoefParticipacionVoToDto(RemesaDto remesaDto, RemesaVO remesaVO);

	ResultBean eliminarCoefPartRemesaBien(Long idRemesa, Long idBien);

	ResultBean eliminarCoefPartRemesaInterviniente(Long idRemesa, Long idInterviniente);

	ResultBean guardarCoefParticipacionInterviniente(Long idInterviniente, Long idRemesa, BigDecimal porcentaje);

	ResultBean comprobarParticipacionesExistentes(Long idRemesa, boolean esConceptoBienes);

	ResultBean convertirListaCoefPartIntervVOToDto(RemesaDto remesaDto, RemesaVO remesaVO);

	ResultBean validarGenerarModelosCoefParticipacion(RemesaVO remesaVO);

	List<ParticipacionDto> getListaParticipacionIntervinientesRemesa(Long idInterviniente, Long idRemesa);

}
