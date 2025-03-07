package org.gestoresmadrid.oegamPermisoInternacional.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.trafico.permiso.internacional.model.vo.EvolucionPermisoInterVO;
import org.gestoresmadrid.oegamPermisoInternacional.view.bean.EvolucionPermisoInterBean;

public interface ServicioEvolucionPermisoInternacional extends Serializable {

	void guardar(Long idPermiso, String estadoAnterior, String estadoNuevo, Long idUsuario, String tipoActualizacion);

	List<EvolucionPermisoInterBean> convertirListaEnBeanPantalla(List<EvolucionPermisoInterVO> lista);
}
