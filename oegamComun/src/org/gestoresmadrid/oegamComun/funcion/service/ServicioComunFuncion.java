package org.gestoresmadrid.oegamComun.funcion.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.ContratoAplicacionVO;
import org.gestoresmadrid.core.general.model.vo.FuncionVO;

public interface ServicioComunFuncion extends Serializable {

	List<ContratoAplicacionVO> obtenerListadoAplicacionesContrato(Long idContrato);

	List<FuncionVO> obtenerListadoFuncionesMenuPorAplicacion(String codigoAplicacion);

}
