package org.gestoresmadrid.oegamComun.evolucionUsuario.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.gestoresmadrid.core.evolucionUsuario.model.vo.EvolucionUsuarioVO;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.oegamComun.evolucionUsuario.view.bean.EvolucionUsuarioBean;

public interface ServicioEvolucionUsuario extends Serializable{

	void guardarEvolucionUsuario(BigDecimal idUsuario,Timestamp fecha,Long idContrato,TipoActualizacion tipoActualizacion, BigDecimal estadoNuevo,
			BigDecimal estadoAnt,Long contratoAnt,String nif);

	List<EvolucionUsuarioBean> convertirListaEnBeanPantalla(List<EvolucionUsuarioVO> list);

}