package org.gestoresmadrid.oegam2comun.atex5.model.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.atex5.model.vo.ConsultaVehiculoAtex5VO;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ConsultaVehiculoAtex5Bean;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResultadoAtex5Bean;

public interface ServicioConsultaVehiculoAtex5 extends Serializable {

	List<ConsultaVehiculoAtex5Bean> convertirListaEnBeanPantalla(List<ConsultaVehiculoAtex5VO> lista);

	ResultadoAtex5Bean cambiarEstado(String codSeleccionados, String estadoNuevo, BigDecimal idUsuario);

	ResultadoAtex5Bean eliminar(String codSeleccionados, BigDecimal idUsuario);

	ResultadoAtex5Bean consultar(String codSeleccionados, BigDecimal idUsuario);

	ResultadoAtex5Bean imprimirPdf(String codSeleccionados);

	ResultadoAtex5Bean asignarTasa(String codSeleccionados, String codigoTasa, BigDecimal idUsuarioDeSesion);

	ResultadoAtex5Bean desasignarTasa(String codSeleccionados, String tasaSeleccionada, BigDecimal idUsuarioDeSesion);

	void borrarFichero(File fichero);
}
