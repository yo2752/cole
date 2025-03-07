package org.gestoresmadrid.oegamPermisoInternacional.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.trafico.permiso.internacional.model.vo.PermisoInternacionalVO;
import org.gestoresmadrid.oegamPermisoInternacional.view.bean.ConsultaPermisoInternacionalBean;
import org.gestoresmadrid.oegamPermisoInternacional.view.bean.ResultadoPermInterBean;

public interface ServicioConsultaPermisoInternacional extends Serializable{

	List<ConsultaPermisoInternacionalBean> convertirListaEnBeanPantallaConsulta(List<PermisoInternacionalVO> list);

	ResultadoPermInterBean validarTramites(String codSeleccionados, Long idUsuario);

	ResultadoPermInterBean eliminarTramites(String codSeleccionados, Long idUsuario);

	ResultadoPermInterBean cambiarEstadoTramites(String codSeleccionados, String estadoNuevo, Long idUsuario);

	ResultadoPermInterBean tramitarTramites(String codSeleccionados, Long idUsuario);

	ResultadoPermInterBean imprimirTramites(String codSeleccionados, Long idUsuario, Boolean tienePermisoAdmin);

}
