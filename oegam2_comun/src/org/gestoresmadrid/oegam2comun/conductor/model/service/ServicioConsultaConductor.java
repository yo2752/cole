package org.gestoresmadrid.oegam2comun.conductor.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.conductor.model.vo.ConsultaConductorVO;
import org.gestoresmadrid.oegam2comun.conductor.view.beans.ConsultaConductorBean;
import org.gestoresmadrid.oegam2comun.conductor.view.beans.ResultConsultaConductorBean;

import utilidades.estructuras.Fecha;

public interface ServicioConsultaConductor extends Serializable {

	public List<ConsultaConductorBean> convertirListaEnBeanPantalla(List<ConsultaConductorVO> list);

	ResultConsultaConductorBean cambiarEstado(String codSeleccionados, String estadoNuevo, BigDecimal idUsuario);

	ResultConsultaConductorBean eliminar(String codSeleccionados, BigDecimal idUsuario);

	public ResultConsultaConductorBean consultar(String codSeleccionados, BigDecimal idUsuarioDeSesion);

	ResultConsultaConductorBean validar(String codSeleccionados, BigDecimal idUsuario);

	ResultConsultaConductorBean modificarFechas(String codSeleccionados, BigDecimal idUsuario, Fecha fecha,
			Fecha fecha2);

}