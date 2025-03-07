package org.gestoresmadrid.oegamDuplicadoPermisoConducir.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.vo.EvolucionDuplicadoPermCondVO;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.bean.EvolucionDuplPermCondBean;

public interface ServicioEvoDuplicadoPermisoConducir extends Serializable {

	void guardar(Long idDuplicadoPermCond, String estadoAnterior, String estadoNuevo, Long idUsuario, String tipoActualizacion);

	List<EvolucionDuplPermCondBean> convertirListaEnBeanPantalla(List<EvolucionDuplicadoPermCondVO> lista);
}
