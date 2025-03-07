package org.gestoresmadrid.oegam2comun.atex5.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.atex5.model.vo.ConsultaPersonaAtex5VO;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ConsultaPersonaAtex5Bean;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResultadoAtex5Bean;

public interface ServicioConsultaPersonaAtex5 extends Serializable{

	List<ConsultaPersonaAtex5Bean> convertirListaEnBeanPantalla(List<ConsultaPersonaAtex5VO> lista);

	ResultadoAtex5Bean cambiarEstado(String codSeleccionados, String estadoNuevo, BigDecimal idUsuario);

	ResultadoAtex5Bean eliminar(String codSeleccionados, BigDecimal idUsuario);

	ResultadoAtex5Bean consultar(String codSeleccionados, BigDecimal idUsuario);

	ResultadoAtex5Bean imprimirPdfs(String codSeleccionados);

}
