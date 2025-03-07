package org.gestoresmadrid.oegam2comun.trafico.eeff.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.trafico.eeff.model.vo.ConsultaEEFFVO;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.beans.ConsultaEEFFBean;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.beans.ResultadoConsultaEEFF;

public interface ServicioConsultaEEFF extends Serializable {

	List<ConsultaEEFFBean> convertirListaEnBeanPantalla(List<ConsultaEEFFVO> lista);

	ResultadoConsultaEEFF consultarBloque(String numsExpedientes, BigDecimal idUsuario);

	ResultadoConsultaEEFF cambiarEstadoBloque(String codSeleccionados, String estado, BigDecimal idUsuario);

	ResultadoConsultaEEFF eliminar(String codSeleccionados, BigDecimal idUsuario);

}