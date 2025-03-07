package org.gestoresmadrid.oegam2comun.consultaDev.model.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import org.gestoresmadrid.core.consultaDev.model.vo.ConsultaDevVO;
import org.gestoresmadrid.oegam2comun.consultaDev.model.bean.ConsultaDevBean;
import org.gestoresmadrid.oegam2comun.consultaDev.model.bean.ResultadoConsultaDev;
import escrituras.beans.ResultBean;

public interface ServicioConsultaDev extends Serializable{

	List<ConsultaDevBean> convertirListaEnBeanPantalla(List<ConsultaDevVO> list);

	ResultadoConsultaDev cambiarEstado(String codSeleccionados, String estadoNuevo, BigDecimal idUsuario);

	ResultadoConsultaDev consultar(String codSeleccionados, BigDecimal idUsuario);

	ResultadoConsultaDev eliminar(String codSeleccionados, BigDecimal idUsuario);
	
	ResultBean exportar (String codSeleccionados, BigDecimal idUsuario);

	Boolean getEstadoEstadoAnulado(Long idConsultaDev);

	ResultadoConsultaDev altaMasivaConsultasDev(File fichero, String nombreFichero, String idContrato, BigDecimal idUsuario);

}
