package org.gestoresmadrid.oegam2comun.trafico.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;

public interface ServicioJefaturaTrafico extends Serializable {

	JefaturaTraficoDto getJefatura(String jefatura);

	JefaturaTraficoDto getJefaturaPorDescripcion(String descripcion);

	List<JefaturaTraficoDto> listadoJefaturas(String idProvincia);

	JefaturaTraficoVO getJefaturaTrafico(String jefaturaProvincial);

	List<JefaturaTraficoVO> getJefaturasPorContrato(Long idContrato);

}
