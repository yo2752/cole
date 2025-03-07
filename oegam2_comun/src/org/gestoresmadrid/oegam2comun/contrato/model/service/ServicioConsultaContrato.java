package org.gestoresmadrid.oegam2comun.contrato.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.oegam2comun.contrato.view.bean.ContratoBean;
import org.gestoresmadrid.oegam2comun.contrato.view.bean.ResultadoConsultaContratoBean;

import utilidades.estructuras.FechaFraccionada;

public interface ServicioConsultaContrato extends Serializable {

	List<ContratoBean> convertirListaEnBeanPantalla(List<ContratoVO> lista);

	ResultadoConsultaContratoBean habilitarListaContratos(String codSeleccionados, String motivo, String solicitante, BigDecimal idUsuarioDeSesion);

	ResultadoConsultaContratoBean deshabilitarListaContratos(String codSeleccionados, String motivo, String solicitante, BigDecimal idUsuario);

	ResultadoConsultaContratoBean eliminarListaContratos(String codSeleccionados, String motivo, String solicitante, BigDecimal idUsuario);

	ResultadoConsultaContratoBean actualizarListaContratos(String codSeleccionados, String alias, FechaFraccionada fecha, BigDecimal idUsuario);

}