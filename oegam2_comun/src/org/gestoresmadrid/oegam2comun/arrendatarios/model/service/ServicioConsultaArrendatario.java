package org.gestoresmadrid.oegam2comun.arrendatarios.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.arrendatarios.model.vo.ArrendatarioVO;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.beans.ConsultaArrendatarioBean;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.beans.ResultConsultaArrendatarioBean;

public interface ServicioConsultaArrendatario extends Serializable {

	List<ConsultaArrendatarioBean> convertirListaEnBeanPantalla(List<ArrendatarioVO> list);

	ResultConsultaArrendatarioBean eliminar(String codSeleccionados, BigDecimal idUsuario);

	ResultConsultaArrendatarioBean cambiarEstado(String codSeleccionados, String estadoNuevo, BigDecimal idUsuario);

	ResultConsultaArrendatarioBean consultar(String codSeleccionados, BigDecimal idUsuarioDeSesion);

	ResultConsultaArrendatarioBean validar(String codSeleccionados, BigDecimal idUsuario);

	ResultConsultaArrendatarioBean modificar(String codSeleccionados, BigDecimal idUsuario, Date fechaFin, Date fechaSinHora2);

}