package org.gestoresmadrid.oegamDuplicadoPermisoConducir.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.vo.DuplicadoPermisoConducirVO;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.bean.ConsultaDuplicadoPermConducirBean;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.bean.ResultadoDuplPermCondBean;

public interface ServicioConsultaDuplPermCond extends Serializable {

	List<ConsultaDuplicadoPermConducirBean> convertirListaEnBeanPantallaConsulta(List<DuplicadoPermisoConducirVO> lista);

	ResultadoDuplPermCondBean validarTramites(String codSeleccionados, Long idUsuario);

	ResultadoDuplPermCondBean eliminarTramites(String codSeleccionados, Long idUsuario);

	ResultadoDuplPermCondBean cambiarEstadoTramites(String codSeleccionados, String estadoNuevo, Long idUsuario);

	ResultadoDuplPermCondBean tramitarTramites(String codSeleccionados, Long idUsuario);

	ResultadoDuplPermCondBean imprimirTramites(String codSeleccionados, Long idUsuario);
}
