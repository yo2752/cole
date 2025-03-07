package org.gestoresmadrid.oegam2comun.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.FuncionVO;
import org.gestoresmadrid.oegamComun.accesos.view.dto.FuncionDto;

public interface ServicioFuncion extends Serializable {

	FuncionVO getFuncion(String codigoAplicacion, String codigoFuncion);

	List<FuncionDto> getFuncionesPorAplicacion(String codigoAplicacion);

	List<FuncionVO> getFuncionesHijos(String codigoAplicacion, String codigoFuncion);

	List<FuncionDto> getFunciones();

	List<FuncionVO> getFuncionesHijosYPadre(String codigoAplicacion, String codigoFuncion);
}
