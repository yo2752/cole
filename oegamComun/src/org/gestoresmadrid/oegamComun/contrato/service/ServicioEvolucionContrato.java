package org.gestoresmadrid.oegamComun.contrato.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.evolucionContrato.model.vo.EvolucionContratoVO;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.oegamComun.evolucionContrato.view.bean.EvolucionContratoBean;

import utilidades.estructuras.FechaFraccionada;

public interface ServicioEvolucionContrato extends Serializable{

	void guardarEvolucionContrato(Long idContrato, BigDecimal idUsuario, String motivo, String solicitante, 
			Date fecha, TipoActualizacion tipoActualizacion, BigDecimal estadoNuevo, BigDecimal estadoAnt);
	void guardarEvolucionContratoAlias(Long idContrato, BigDecimal idUsuario,FechaFraccionada fecha, TipoActualizacion tipoActualizacion, BigDecimal estadoNuevo, BigDecimal estadoAnt);

	List<EvolucionContratoBean> convertirListaEnBeanPantalla(List<EvolucionContratoVO> list);

}
